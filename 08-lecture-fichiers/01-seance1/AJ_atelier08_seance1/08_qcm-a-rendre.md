# Atelier 8 : QCM en Markdown — questionnaire à choix multiple

10 questions à réponse unique, portant sur la théorie et la pratique des ateliers 1 à 7. Chaque
question vaut **2 points**, pour un total de 20 points.

Ce questionnaire est le **treizième QCM** de l'évaluation continue. Il compte au même titre que
les douze QCM mooVin : la note d'évaluation continue retient les 11 meilleurs résultats sur 13,
pour 10 % de la note de l'unité d'enseignement. Un QCM non réalisé est coté 0.

## Ce qu'on vous demande

L'examen de janvier se déroule sur machine, et vous n'y disposez que des fichiers Markdown du
cours. Rédiger ses réponses dans un fichier Markdown est donc un geste à avoir acquis avant
janvier — c'est la raison d'être de ce QCM-ci, et c'est pour ça qu'il ne se passe pas sur mooVin.

1. Copiez ce fichier dans un dossier à vous. **Ne travaillez pas dans le dépôt cloné** : votre
   prochain `git pull` entrerait en conflit avec vos modifications.
2. Complétez votre copie.
3. Déposez-la sur mooVin, activité *QCM en Markdown — atelier 8*, **pour le lundi 09/11/2026 à
   20h**.

**Comment ouvrir ce fichier ?** Dans IntelliJ, les trois icônes en haut à droite de l'éditeur
basculent entre *Éditeur*, *Éditeur + aperçu* et *Aperçu* — l'aperçu affiche les cases à cocher
et les blocs de code, sans connexion. Vous répondez dans l'éditeur, pas dans l'aperçu. Pour
grossir ou réduire l'affichage : `Alt+Maj+=` agrandit, `Alt+Maj+-` réduit — utile pour voir une
question entière d'un coup d'œil. Les autres options (VS Code, Bloc-notes) et les pièges à éviter
sont dans le [README du dépôt](../../../README.md). N'utilisez pas Word : il enregistrerait votre copie
en `.docx`.

**Une seule case cochée par question.** Une question sans case cochée, ou avec plusieurs, est
comptée fausse. Ne modifiez ni les titres des questions, ni leur numérotation, ni le texte des
propositions : c'est ce qui permet de corriger votre copie.

```markdown
- [ ] A) Une proposition que vous ne retenez pas
- [x] B) Celle que vous choisissez
```

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

    public int getNumero() { return numero; }
}

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

### Question 3 — Unboxing d'un `null`

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

### Question 4 — `assertThrows`

Comment vérifie-t-on qu'un constructeur lance bien une `IllegalArgumentException` ?

- [ ] A) Avec `assertEquals(IllegalArgumentException.class, new Prix(null, 15))`
- [ ] B) Avec `assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15))`
- [ ] C) En entourant l'appel d'un `try` / `catch` et en appelant `fail()` dans le `try`
- [ ] D) Avec `assertNotNull(new Prix(null, 15))`

---

### Question 5 — `@Nested`

Pourquoi une classe interne annotée `@Nested` doit-elle être **non statique** ?

- [ ] A) Pour que ses tests s'exécutent en parallèle
- [ ] B) Parce que c'est ce qui lui donne accès à la fixture construite dans le `@BeforeEach` de la classe externe
- [ ] C) Parce qu'une classe statique ne peut pas contenir de méthodes `@Test`
- [ ] D) Parce que JUnit refuse d'instancier une classe statique

---

### Question 6 — Le cycle TDD

Dans quel ordre s'enchaînent les trois étapes du TDD ?

- [ ] A) Refactorer, écrire le test, écrire le code
- [ ] B) Écrire un test qui échoue, écrire le code pour le faire passer, refactorer
- [ ] C) Écrire le code, écrire le test, refactorer
- [ ] D) Écrire le test et le code ensemble, puis refactorer

---

### Question 7 — La règle de trois

Que dit la règle de trois appliquée au refactoring ?

- [ ] A) Trois personnes doivent relire chaque refactoring
- [ ] B) Chaque méthode doit tenir en trois lignes
- [ ] C) On attend en moyenne trois duplications avant de factoriser le code
- [ ] D) Un refactoring doit se faire en trois étapes

---

### Question 8 — Ce que vérifie un mock

Que vérifie-t-on principalement avec un mock ?

- [ ] A) Le comportement : que les bonnes méthodes ont été appelées, avec les bonnes données, dans le bon ordre
- [ ] B) L'état final de l'objet sous test
- [ ] C) Le temps d'exécution
- [ ] D) La couverture de code

---

### Question 9 — `groupingBy`

Que produit `menu.stream().collect(Collectors.groupingBy(Dish::getType))` ?

- [ ] A) Une `Map<Type, List<Dish>>`
- [ ] B) Une `Map<Dish, Type>`
- [ ] C) Une `List<Type>`
- [ ] D) Un `Set<Type>`

---

### Question 10 — `flatMap`

Que fait `flatMap` ?

- [ ] A) Il trie le stream avant de le transformer
- [ ] B) Il transforme chaque élément en un stream, puis concatène ces mini-streams en un seul stream aplati
- [ ] C) Il supprime les doublons
- [ ] D) Il transforme un stream en `Map`

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
