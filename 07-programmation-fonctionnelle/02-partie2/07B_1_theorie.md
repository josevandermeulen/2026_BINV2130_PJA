# Atelier 7 : Programmation fonctionnelle – partie 2 – Concepts

Les exercices associés à ces concepts se trouvent dans `07B_2_exercices.md`.

## Table des matières

1. [Vidéos](#vidéos)
2. [Les flatMap](#les-flatmap)
3. [Le design fonctionnel](#le-design-fonctionnel)
4. [Pour aller plus loin](#pour-aller-plus-loin)

## Vidéos

1. [Comment aplatir les collections imbriquées en Java avec flatMap](https://www.youtube.com/watch?v=aZHDUchqjyk)
2. [Immuabilité Java](https://www.youtube.com/watch?v=u0j64SmIoc4)
3. [Mise en pratique du design fonctionnel en Java partie 1 - L'immuabilité](https://www.youtube.com/watch?v=s6Zwdeg3NdI)
4. [Programmation fonctionnelle cas pratique en Java partie 2 - Les exceptions](https://www.youtube.com/watch?v=g0EVQYClhtA)

## Les flatMap

### map : une transformation 1 → 1

`map` prend chaque élément du stream et le transforme en **exactement un** élément de sortie. Le nombre d'éléments ne change jamais : un stream de 4 éléments reste un stream de 4 éléments après un `map`.

```
entrée (stream de carrés)      map(carré -> rond)      sortie (stream de ronds)

   [■]  [■]  [■]  [■]     ────────────────────►     (●)  (●)  (●)  (●)
```

Chaque `■` devient un `●` : une flèche par élément, jamais plus, jamais moins. C'est le cas par exemple de `.map(Trader::getNom)` : un `Trader` en entrée, un `String` en sortie, un-pour-un.

### flatMap : une transformation 1 → 0..N

`flatMap` prend chaque élément et le transforme en **un stream** d'éléments de sortie — potentiellement vide, avec un seul élément, ou avec plusieurs. Ces mini-streams sont ensuite concaténés (« aplatis ») en un seul stream de sortie. Contrairement à `map`, le nombre d'éléments en sortie n'a **aucun rapport garanti** avec le nombre d'éléments en entrée.

```
entrée (stream de carrés)      flatMap(carré -> stream de ronds)      sortie (un seul stream aplati)

   [■]   ──────────────────────────────────────►   (●) (●)        (2 ronds)
   [■]   ──────────────────────────────────────►                  (0 rond)
   [■]   ──────────────────────────────────────►   (●)             (1 rond)
   [■]   ──────────────────────────────────────►   (●) (●) (●)     (3 ronds)

                                                     ▼ concaténation en un seul stream

                                    (●) (●) (●) (●) (●) (●)
```

Chaque carré peut produire zéro, un ou plusieurs ronds ; le résultat final est la concaténation de tous ces petits streams. C'est l'outil naturel dès que le modèle de données est hiérarchique : une liste d'objets contenant chacun une liste (ex. `.flatMap(trader -> trader.getActions().stream())` — chaque `Trader` a 0, 1 ou plusieurs actions, et on veut un seul stream plat de toutes les actions de tous les traders).

Résumé :

| | entrée → sortie par élément | forme de la lambda |
|---|---|---|
| `map` | 1 → 1 | `T -> R` |
| `flatMap` | 1 → 0..N | `T -> Stream<R>` |

Regardez la vidéo « Comment aplatir les collections imbriquées en Java avec flatMap » ci-dessus : elle explique le principe en images. La théorie détaillée se trouve également sur MooVin, qui contient un document Word intitulé « Les flatMap ».

## Le design fonctionnel

La programmation fonctionnelle ne se limite pas à l'API Stream : c'est aussi un style de conception. Les vidéos « Immuabilité Java », « Mise en pratique du design fonctionnel en Java partie 1 » et « cas pratique partie 2 - Les exceptions » ci-dessus illustrent deux piliers de ce style, résumés ici.

### L'immuabilité

Un objet immuable ne peut plus changer d'état après sa construction. Pour rendre une classe immuable :

1. tous les attributs sont `final` et assignés dans le constructeur ;
2. aucun setter ;
3. si un attribut est une collection ou un objet mutable, on n'expose jamais la référence d'origine : le constructeur en fait une copie, et le getter renvoie une copie (ou une vue non modifiable).

La classe `Portfolio` de l'exercice respecte les deux premiers points (attributs `final`, pas de setter) mais pas le troisième : son constructeur stocke la référence de la liste reçue, et son getter la renvoie telle quelle. Le code appelant peut donc modifier la liste après coup et changer l'état du `Portfolio` à son insu — c'est ce qu'on appelle une fuite de référence. Pour la rendre réellement immuable, il suffirait d'écrire :

```java
public Portfolio(Trader trader, List<String> actions) {
    this.trader = trader;
    this.actions = List.copyOf(actions);
}
```

`List.copyOf` produit une copie non modifiable : le getter peut alors renvoyer l'attribut directement.

Pourquoi s'imposer cette discipline ?

- **Pas d'effet de bord** : une méthode qui reçoit un objet immuable a la garantie qu'il ne changera pas pendant (ni après) le traitement ; le raisonnement sur le code devient local.
- **Partage sans risque** : le même objet peut être référencé par plusieurs structures sans copie défensive supplémentaire.
- **Compatible avec les streams** : les opérations de l'API Stream ne modifient jamais la collection d'origine ; elles produisent de nouvelles valeurs. Travailler avec des objets immuables prolonge cette logique — et c'est ce qui rend les streams parallèles (voir plus bas) sûrs.

Remarquez que vous connaissez déjà des classes immuables : `String`, les wrappers (`Integer`, `Double`…) et `LocalDate` le sont. `toUpperCase()` ne modifie pas la chaîne, elle en renvoie une nouvelle.

### Les exceptions en style fonctionnel

Les méthodes abstraites des interfaces fonctionnelles de l'API Stream (`Function.apply`, `Predicate.test`…) ne déclarent aucun `throws`. Une lambda passée à `map` ou `filter` ne peut donc pas laisser sortir une exception vérifiée (checked) :

```java
fichiers.stream()
        .map(f -> Files.readString(f)) // erreur de compilation : IOException non gérée
        .toList();
```

La solution habituelle est d'encapsuler l'exception vérifiée dans une exception non vérifiée :

```java
fichiers.stream()
        .map(f -> {
            try {
                return Files.readString(f);
            } catch (IOException e) {
                throw new RuntimeException("Impossible de lire " + f, e);
            }
        })
        .toList();
```

Notez le second argument `e` : on chaîne l'exception d'origine pour ne pas perdre sa trace. En pratique, dès que le bloc devient long, on l'extrait dans une méthode privée et la lambda redevient une simple référence de méthode.

## Pour aller plus loin

### Les Streams parallèles

Si on réfléchit bien, la plupart des traitements effectués par les streams pourraient être exécutés indépendamment sur différents cœurs de processeur. En effet, vu que les opérations sur un stream ne modifient pas le stream d'origine, il n'y a pas de risque de modifications concurrentes. Une version parallèle des streams est présente dans l'API. Pour en savoir plus, voir https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html.

Un stream parallèle va traiter en parallèle les différents éléments de la collection qu'il représente.

---

*Passez aux [exercices](07B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
