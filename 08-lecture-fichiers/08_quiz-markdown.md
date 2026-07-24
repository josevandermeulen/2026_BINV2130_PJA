# Atelier 8 : QCM en Markdown — questionnaire à choix multiple

**Nom :**
**Prénom :**

20 questions à réponse unique, portant sur la théorie et la pratique des ateliers 1 à 7.

Ce questionnaire est le **treizième QCM** de l'évaluation continue. Il compte au même titre que
les douze QCM mooVin : la note d'évaluation continue retient les 11 meilleurs résultats sur 13,
pour 10 % de la note de l'unité d'enseignement. Un QCM non réalisé est coté 0.

## Ce qu'on vous demande

L'examen de janvier se déroule sur machine, et vous n'y disposez que des fichiers Markdown du
cours. Rédiger ses réponses dans un fichier Markdown est donc un geste à avoir acquis avant
janvier — c'est la raison d'être de ce QCM-ci, et c'est pour ça qu'il ne se passe pas sur mooVin.

1. Copiez ce fichier dans un dossier à vous. **Ne travaillez pas dans le dépôt cloné** : votre
   prochain `git pull` entrerait en conflit avec vos modifications.
2. Renommez votre copie `NOM_Prenom.md` (votre nom en majuscules, votre prénom tel que dans
   mooVin).
3. Complétez-la : votre nom et votre prénom en tête, puis **une case cochée par question**.
   Cocher, c'est remplacer l'espace entre les crochets par un `x` :

   ```markdown
   - [ ] A) Une proposition que vous ne retenez pas
   - [x] B) Celle que vous choisissez
   ```

4. Déposez-la sur mooVin, activité *QCM en Markdown — atelier 8*, **pour le lundi 09/11/2026 à
   20h**.

**Comment ouvrir ce fichier ?** Dans IntelliJ, les trois icônes en haut à droite de l'éditeur
basculent entre *Éditeur*, *Éditeur + aperçu* et *Aperçu* — l'aperçu affiche les cases à cocher
et les blocs de code, sans connexion. Vous répondez dans l'éditeur, pas dans l'aperçu. Les
autres options (VS Code, Bloc-notes) et les pièges à éviter sont dans le
[README du dépôt](../README.md). N'utilisez pas Word : il enregistrerait votre copie en `.docx`.

**Une seule case cochée par question.** Une question sans case cochée, ou avec plusieurs, est
comptée fausse. Ne modifiez ni les titres des questions, ni leur numérotation, ni le texte des
propositions : c'est ce qui permet de corriger votre copie.

Pas de points négatifs : une réponse fausse et une absence de réponse valent zéro, autant
répondre partout. Vous travaillez en autonomie, avec vos notes et le dépôt du cours.

Le corrigé, avec la justification de chaque réponse, est publié dans ce dépôt après l'échéance.

---

### Question 1 — Attribut statique

Que produit ce programme ?

```java
public class Badge {
    private static int numeroSuivant = 1;
    private int numero;

    public Badge() {
        numero = numeroSuivant;
        numeroSuivant++;
    }

    public int getNumero() {
        return numero;
    }
}
```

```java
Badge b1 = new Badge();
Badge b2 = new Badge();
Badge b3 = new Badge();
System.out.println(b1.getNumero() + " " + b3.getNumero());
```

- [ ] A) `1 3`
- [ ] B) `1 1`
- [ ] C) `1 2`
- [ ] D) `3 3`

---

### Question 2 — Contrat `equals` / `hashCode`

Vous redéfinissez `equals` dans une classe `Etudiant`. Que faut-il faire de plus ?

- [ ] A) Redéfinir aussi `toString`
- [ ] B) Rien, `equals` suffit
- [ ] C) Redéfinir aussi `hashCode`, sinon l'objet se comportera mal dans certaines collections
- [ ] D) Déclarer la classe `final`

---

### Question 3 — Copie défensive

Quel est le problème de ce constructeur ?

```java
public Evenement(ArrayList<String> participants) {
    this.participants = participants;
}
```

- [ ] A) Le code ne compile pas sans `new ArrayList<>()`
- [ ] B) Le code appelant garde une référence vers la même liste et peut donc modifier l'état interne de l'objet
- [ ] C) La liste sera vide après la construction
- [ ] D) Les doublons sont automatiquement supprimés

---

### Question 4 — `HashSet` et objets métier

Vous placez des objets `Ingredient` dans un `HashSet`. Que doit fournir la classe `Ingredient` pour que les doublons soient correctement détectés ?

- [ ] A) `equals` et `hashCode`
- [ ] B) Rien, `HashSet` compare les références
- [ ] C) `compareTo`
- [ ] D) `toString`

---

### Question 5 — `Map` : clé absente

Que renvoie `dictionnaire.get("souris")` si cette clé n'est pas dans la `Map` ?

- [ ] A) La première valeur de la map
- [ ] B) `null`
- [ ] C) Une `NoSuchElementException`
- [ ] D) Une chaîne vide

---

### Question 6 — Unboxing d'un `null`

Que produit ce code si la clé `"pommes"` est absente de la map ?

```java
Integer valeur = stock.get("pommes");
int total = valeur + 1;
```

- [ ] A) Une erreur de compilation
- [ ] B) `total` vaut 0
- [ ] C) Une `NullPointerException`
- [ ] D) `total` vaut 1

---

### Question 7 — `assertThrows`

Comment vérifie-t-on qu'un constructeur lance bien une `IllegalArgumentException` ?

- [ ] A) Avec `assertEquals(IllegalArgumentException.class, new Prix(null, 15))`
- [ ] B) Avec `assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15))`
- [ ] C) En entourant l'appel d'un `try` / `catch` et en appelant `fail()` dans le `try`
- [ ] D) Avec `assertNotNull(new Prix(null, 15))`

---

### Question 8 — Comparer des listes

Que vérifie `assertEquals(List.of(pasCher, cher), resultat)` ?

- [ ] A) Le contenu **et** l'ordre des éléments
- [ ] B) Que les deux listes sont la même instance
- [ ] C) Uniquement la taille de la liste
- [ ] D) Uniquement que la liste contient les deux produits

---

### Question 9 — `@Nested`

Pourquoi une classe interne annotée `@Nested` doit-elle être **non statique** ?

- [ ] A) Pour que ses tests s'exécutent en parallèle
- [ ] B) Parce que c'est ce qui lui donne accès à la fixture construite dans le `@BeforeEach` de la classe externe
- [ ] C) Parce qu'une classe statique ne peut pas contenir de méthodes `@Test`
- [ ] D) Parce que JUnit refuse d'instancier une classe statique

---

### Question 10 — Le cycle TDD

Dans quel ordre s'enchaînent les trois étapes du TDD ?

- [ ] A) Refactorer, écrire le test, écrire le code
- [ ] B) Écrire un test qui échoue, écrire le code pour le faire passer, refactorer
- [ ] C) Écrire le code, écrire le test, refactorer
- [ ] D) Écrire le test et le code ensemble, puis refactorer

---

### Question 11 — Échouer pour de bonnes raisons

Pourquoi ne suffit-il pas qu'un test échoue, encore faut-il qu'il échoue « pour de bonnes raisons » ?

- [ ] A) Parce que les erreurs de compilation ne comptent pas comme des échecs
- [ ] B) Parce qu'un test peut échouer à cause d'un setup incomplet, et non parce que le comportement testé est absent
- [ ] C) Parce qu'un test doit échouer exactement une fois
- [ ] D) Parce que JUnit distingue plusieurs types d'échecs

---

### Question 12 — La règle de trois

Que dit la règle de trois appliquée au refactoring ?

- [ ] A) Trois personnes doivent relire chaque refactoring
- [ ] B) Chaque méthode doit tenir en trois lignes
- [ ] C) On attend en moyenne trois duplications avant de factoriser le code
- [ ] D) Un refactoring doit se faire en trois étapes

---

### Question 13 — Ce qu'est un stub

Comment un stub décide-t-il de ce qu'il renvoie ?

- [ ] A) Il calcule une réponse à partir des arguments reçus
- [ ] B) Il renvoie toujours `null`
- [ ] C) Il interroge la vraie dépendance
- [ ] D) Il renvoie une réponse fixe, prédéterminée par le test, quels que soient les paramètres reçus

---

### Question 14 — Ce que vérifie un mock

Que vérifie-t-on principalement avec un mock ?

- [ ] A) Le comportement : que les bonnes méthodes ont été appelées, avec les bonnes données, dans le bon ordre
- [ ] B) L'état final de l'objet sous test
- [ ] C) Le temps d'exécution
- [ ] D) La couverture de code

---

### Question 15 — Vérifier une absence d'appel

Comment vérifie-t-on qu'une méthode n'a **pas** été appelée sur un mock ?

- [ ] A) `assertFalse(stage.enregistrerMoniteur(moniteur));`
- [ ] B) `Mockito.verify(stage, Mockito.never()).enregistrerMoniteur(moniteur);`
- [ ] C) `Mockito.verify(stage, Mockito.times(0)).enregistrerMoniteur(moniteur);` uniquement
- [ ] D) `Mockito.verifyNot(stage).enregistrerMoniteur(moniteur);`

---

### Question 16 — `map` du stream

Quel piège de vocabulaire l'opération `map` d'un stream comporte-t-elle ?

- [ ] A) Elle ne fonctionne que sur des `Map`
- [ ] B) Elle trie les éléments
- [ ] C) Elle n'a rien à voir avec la structure de données `Map`
- [ ] D) Elle renvoie une `HashMap`

---

### Question 17 — `reduce` et valeur neutre

À quoi sert la valeur neutre passée en premier argument de `reduce` ?

- [ ] A) À trier les éléments avant réduction
- [ ] B) À fixer le nombre d'éléments à traiter
- [ ] C) À fournir un résultat valable si le stream est vide, ce qui évite les erreurs
- [ ] D) À indiquer le type de retour

---

### Question 18 — `groupingBy`

Que produit `menu.stream().collect(Collectors.groupingBy(Dish::getType))` ?

- [ ] A) Une `Map<Type, List<Dish>>`
- [ ] B) Une `Map<Dish, Type>`
- [ ] C) Une `List<Type>`
- [ ] D) Un `Set<Type>`

---

### Question 19 — `flatMap`

Que fait `flatMap` ?

- [ ] A) Il trie le stream avant de le transformer
- [ ] B) Il transforme chaque élément en un stream, puis concatène ces mini-streams en un seul stream aplati
- [ ] C) Il supprime les doublons
- [ ] D) Il transforme un stream en `Map`

---

### Question 20 — Fuite de référence

Le constructeur de `Portfolio` stocke directement la liste reçue et son getter la renvoie telle quelle. Quelle est la conséquence ?

- [ ] A) Aucune, les attributs sont `final`
- [ ] B) Le `Portfolio` ne compile pas
- [ ] C) Le code appelant peut modifier la liste après coup et changer l'état du `Portfolio` à son insu
- [ ] D) La liste devient automatiquement non modifiable

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
