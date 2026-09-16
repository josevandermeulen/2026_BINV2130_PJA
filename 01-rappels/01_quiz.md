# Atelier 1 : rappels — questionnaire à choix multiple

20 questions à réponse unique, tirées de `01A_1_theorie.md` (questions 1 à 15) et `01B_1_theorie.md` (questions 16 à 20).

---

### Question 1 — Représentation UML

Dans le diagramme UML ci-dessous, que signifie le signe `-` devant `titre` ?

```text
Livre
--------------------
- titre : String
--------------------
+ getTitre() : String
```

- A) L'attribut est `private`
- B) L'attribut est `final`
- C) L'attribut est facultatif
- D) L'attribut est `static`

**Réponse : A** — En UML, `-` signifie `private` et `+` signifie `public`.

---

### Question 2 — Attribut statique

Que produit ce programme ?

```java
public class Badge {
    private static int numeroSuivant = 1;
    private int numero;

    public Badge() {
        numero = numeroSuivant;
        numeroSuivant++;
    }

    public int getNumero() { return numero; }
}

Badge b1 = new Badge();
Badge b2 = new Badge();
Badge b3 = new Badge();
System.out.println(b1.getNumero() + " " + b3.getNumero());
```

- A) `1 3`
- B) `1 1`
- C) `1 2`
- D) `3 3`

**Réponse : A** — `numeroSuivant` est statique, donc partagé par tous les badges : il vaut 1, puis 2, puis 3 au fil des créations. `numero` est un attribut d'instance : chaque badge garde la valeur qu'il a reçue.

---

### Question 3 — Constructeur

Quelle affirmation sur les constructeurs est correcte ?

- A) Un constructeur porte le même nom que la classe et n'a pas de type de retour
- B) Une classe ne peut avoir qu'un seul constructeur
- C) Un constructeur peut porter n'importe quel nom s'il est déclaré `public`
- D) Un constructeur porte le même nom que la classe et renvoie `void`

**Réponse : A** — Un constructeur ne déclare aucun type de retour, pas même `void`. Écrire `void` en ferait une méthode ordinaire, et l'appel avec `new` ne compilerait plus.

---

### Question 4 — Surcharge

Qu'est-ce que la surcharge (*overloading*) ?

- A) Une sous-classe qui remplace le comportement d'une méthode héritée
- B) Un constructeur qui en appelle un autre avec `this(...)`
- C) Plusieurs méthodes ou constructeurs de même nom, mais avec des paramètres différents
- D) Une méthode qui appelle sa version parent avec `super`

**Réponse : C** — Le compilateur choisit la version à appeler d'après les arguments fournis. La proposition A décrit la redéfinition (*overriding*), qui est une notion différente.

---

### Question 5 — Chaînage de constructeurs

Pourquoi ce code ne compile-t-il pas ?

```java
public Livre(String titre, String auteur) {
    this.titre = titre;
    this(titre, auteur, 0);
}
```

- A) Il manque le mot-clé `new` devant `this(...)`
- B) `this(...)` doit être la première instruction du constructeur
- C) `this(...)` ne peut pas être utilisé dans un constructeur
- D) Un constructeur ne peut pas avoir trois paramètres

**Réponse : B** — L'appel à un autre constructeur de la même classe doit précéder toute autre instruction. Même règle pour `super(...)`.

---

### Question 6 — Égalité référentielle et structurelle

Qu'affiche ce code ?

```java
String a = new String("Dune");
String b = new String("Dune");
System.out.println(a == b);
System.out.println(a.equals(b));
```

- A) `false` puis `false`
- B) `false` puis `true`
- C) `true` puis `false`
- D) `true` puis `true`

**Réponse : B** — `==` compare les références : `new` crée deux objets distincts, donc `false`. `equals` compare le contenu selon la logique définie par la classe `String`, donc `true`.

---

### Question 7 — Contrat `equals` / `hashCode`

Vous redéfinissez `equals` dans une classe `Etudiant`. Que faut-il faire de plus ?

- A) Redéfinir aussi `toString`
- B) Rien, `equals` suffit
- C) Redéfinir aussi `hashCode`, sinon l'objet se comportera mal dans certaines collections
- D) Déclarer la classe `final`

**Réponse : C** — Deux objets égaux selon `equals` doivent renvoyer le même `hashCode`. Sans cela, les collections basées sur le hachage (`HashSet`, `HashMap`) ne retrouvent pas les objets.

---

### Question 8 — `ArrayList` et `contains`

Sur quoi la méthode `contains` d'une `ArrayList` s'appuie-t-elle pour comparer les éléments ?

- A) Sur `hashCode` uniquement
- B) Sur `equals`
- C) Sur `==`
- D) Sur `toString`

**Réponse : B** — C'est la raison pour laquelle une classe dont on cherche les instances dans une liste doit redéfinir `equals`.

---

### Question 9 — Copie défensive

Quel est le problème de ce constructeur ?

```java
public Evenement(ArrayList<String> participants) {
    this.participants = participants;
}
```

- A) Le code ne compile pas sans `new ArrayList<>()`
- B) Le code appelant garde une référence vers la même liste et peut donc modifier l'état interne de l'objet
- C) La liste sera vide après la construction
- D) Les doublons sont automatiquement supprimés

**Réponse : B** — L'objet et le code extérieur partagent la même liste. Copier les éléments dans une nouvelle liste protège l'encapsulation.

---

### Question 10 — Classe abstraite

Soit `public abstract class Forme`. Que se passe-t-il si on écrit `new Forme("rouge")` ?

- A) Le code compile et renvoie `null`
- B) Le code compile et crée un objet `Forme`
- C) Le code ne compile pas : une classe abstraite ne peut pas être instanciée
- D) Le code compile mais lance une exception à l'exécution

**Réponse : C** — Le compilateur refuse l'instanciation d'une classe abstraite. Il faut instancier une sous-classe concrète.

---

### Question 11 — Méthode abstraite

Une classe abstraite `Forme` déclare `public abstract double calculerAire();`. Qu'impose cette déclaration à une sous-classe concrète ?

- A) La sous-classe doit redéfinir toutes les méthodes de `Forme`
- B) Rien, la méthode est optionnelle
- C) La sous-classe doit être abstraite elle aussi
- D) La sous-classe doit fournir une implémentation de `calculerAire`

**Réponse : D** — Une sous-classe concrète doit implémenter toutes les méthodes abstraites héritées, sinon elle doit être déclarée abstraite à son tour.

---

### Question 12 — `super(...)`

La classe `Forme` ne possède qu'un constructeur `Forme(String couleur)`. Que se passe-t-il si le constructeur de `Rectangle` n'appelle pas explicitement `super(...)` ?

- A) Erreur à l'exécution seulement
- B) Le code compile, `couleur` vaut `""`
- C) Erreur de compilation : Java insère un appel à `super()` sans paramètre, qui n'existe pas
- D) Le code compile, `couleur` vaut `null`

**Réponse : C** — En l'absence d'appel explicite, Java insère `super()`. Comme `Forme` n'a pas de constructeur sans paramètre, la compilation échoue.

---

### Question 13 — `@Override`

Que dire de l'annotation `@Override` ?

- A) Elle est facultative, mais permet au compilateur de vérifier qu'on redéfinit bien une méthode existante
- B) Elle empêche les sous-classes de redéfinir la méthode à leur tour
- C) Elle rend la méthode abstraite
- D) Elle est obligatoire pour redéfinir une méthode

**Réponse : A** — Sans elle, une faute de frappe dans le nom ou une signature erronée crée silencieusement une nouvelle méthode au lieu d'en redéfinir une.

---

### Question 14 — Exceptions unchecked

Quelle affirmation concernant `IllegalArgumentException` est correcte ?

- A) Elle ne peut être lancée que depuis une méthode, jamais depuis un constructeur
- B) Elle doit obligatoirement être capturée par un `try` / `catch`
- C) Elle indique qu'une opération existe mais n'est pas autorisée
- D) Elle hérite de `RuntimeException` et n'a pas besoin d'être déclarée avec `throws`

**Réponse : D** — C'est une exception *unchecked*. La proposition C décrit `UnsupportedOperationException` ; la A est fausse, un constructeur peut refuser de créer l'objet en lançant une exception.

---

### Question 15 — Constantes d'interface

Dans une interface, quelle est la portée réelle de cette déclaration ?

```java
public interface Couleurs {
    String ROUGE = "rouge";
}
```

- A) `public` seulement, la valeur reste modifiable
- B) `public final` seulement
- C) `private static final`
- D) `public static final`

**Réponse : D** — Dans une interface, les attributs sont implicitement `public static final`. On y accède directement par `Couleurs.ROUGE`.

---

### Question 16 — `toString`

Qu'affiche `System.out.println(livre)` si la classe `Livre` redéfinit `toString` par `return titre + " - " + auteur;` ?

- A) L'adresse mémoire de l'objet
- B) Une erreur de compilation
- C) Le résultat de `toString`, par exemple `Dune - Frank Herbert`
- D) `Livre@1b6d3586`

**Réponse : C** — `println` appelle automatiquement `toString` sur l'objet. Sans redéfinition, c'est l'implémentation par défaut qui produirait quelque chose comme `Livre@1b6d3586`.

---

### Question 17 — Interface `Iterable`

Pourquoi faire implémenter `Iterable<Livre>` à une classe `Bibliotheque` plutôt que d'ajouter un `getLivres()` renvoyant la liste interne ?

- A) Parce qu'une `ArrayList` ne peut pas être un attribut privé
- B) Parce que c'est plus rapide à l'exécution
- C) Parce que `Iterable` trie automatiquement les éléments
- D) Parce que cela permet le parcours par foreach sans exposer la liste interne à la modification

**Réponse : D** — Le code extérieur peut parcourir les livres, mais n'obtient pas la référence de la liste, donc il ne peut ni ajouter ni supprimer d'élément. L'encapsulation est préservée.

---

### Question 18 — Méthode statique

Que peut-on dire de `LocalDateTime.now()` ?

- A) C'est une méthode statique : elle s'appelle sur la classe, pas sur un objet
- B) C'est un attribut public de la classe
- C) C'est un constructeur
- D) C'est une méthode d'instance : il faut d'abord créer un `LocalDateTime`

**Réponse : A** — Une méthode statique appartient à la classe. On l'appelle avec le nom de la classe, comme `Math.ceil(12.3)`.

---

### Question 19 — Méthode renvoyant un `boolean`

Une méthode `prolonger(int jours)` renvoie un `boolean`. Quel comportement est attendu quand la prolongation est impossible ?

- A) Elle renvoie `false` après avoir quand même modifié la date
- B) Elle lance une exception et ne renvoie rien
- C) Elle renvoie `true` mais affiche un message d'erreur
- D) Elle renvoie `false` et laisse l'état de l'objet inchangé

**Réponse : D** — Le `boolean` signale si l'opération a été effectuée. En cas de refus, rien ne doit être modifié.

---

### Question 20 — Multiplicités UML

Sur un diagramme de classes, comment traduit-on le plus souvent une multiplicité `0..1` en Java ?

- A) Par deux attributs, un booléen et une valeur
- B) Par un attribut simple qui peut valoir `null`
- C) Par un attribut `static`
- D) Par une `ArrayList` qui contient au maximum un élément

**Réponse : B** — `0..1` signifie « zéro ou un » : l'absence de lien se représente par `null`. Une multiplicité `*` se traduit, elle, par une collection.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
