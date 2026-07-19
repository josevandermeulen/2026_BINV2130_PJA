# Atelier 11 : Introspection – partie 2

## Objectif

L'objectif de cette partie est de construire un **mini validateur d'objets par annotations** — le cœur de Jakarta Bean Validation : des annotations posées sur les champs d'une classe (`@NonNul`, `@LongueurMin`) déclarent les contraintes, et un [`Validateur`](01-code-java/src/main/java/be/vinci/validation/Validateur.java) (package `be.vinci.validation`) découvre ces champs par réflexion, lit leur valeur et rapporte chaque contrainte enfreinte. Après la réflexion sur les méthodes en partie 1, c'est la réflexion sur les **champs** : `getDeclaredFields`, `setAccessible`, `Field.get`.

## Concepts

1. Réflexion sur les champs (`getDeclaredFields`, `Field.get`)
2. Accès aux champs privés (`setAccessible`) et limites de l'encapsulation
3. Annotations ciblant les champs (`ElementType.FIELD`)
4. Annotations à élément (`getAnnotation`, lecture de la valeur)
5. Validation déclarative (le principe de Jakarta Bean Validation)

## Vidéos

1. [Introspection - Machine Virtuelle Java JVM](https://www.youtube.com/watch?v=hozaDXJ7J2Y)
2. [Introspection - typage statique](https://www.youtube.com/watch?v=aFIYXPp-5MY)
3. [Introspection - Inspection d'une classe](https://www.youtube.com/watch?v=s8D-yfzdkSI)
4. [Introspection - Inspection et instanciation d'objets](https://www.youtube.com/watch?v=W7EYb072Rgc)
5. [Introspection - Annotation et mini JUnit](https://www.youtube.com/watch?v=Vo-nmSstLF8)

## Exercices

### Introduction

Depuis le début du quadrimestre, vos constructeurs valident leurs paramètres à coups de `Util.checkObject(nom)`, `Util.checkString(matricule)`... Chaque règle est une ligne de code impérative, répétée de constructeur en constructeur. Les frameworks font autrement : la règle est **déclarée** sur le champ, une bonne fois pour toutes, sous forme d'annotation — et un mécanisme générique la fait respecter pour n'importe quelle classe.

Dans cette partie, vous construisez ce mécanisme. La classe [`Etudiant`](01-code-java/src/main/java/be/vinci/domaine/Etudiant.java) (package `be.vinci.domaine`) déclare ses contraintes sur ses champs :

```java
@NonNul
@LongueurMin(valeur = 3)
private final String nom;
```

et votre `Validateur` les fait respecter : il découvre par réflexion les champs annotés, lit leur valeur (privée !) et produit un [`RapportValidation`](01-code-java/src/main/java/be/vinci/validation/RapportValidation.java) listant chaque [`Violation`](01-code-java/src/main/java/be/vinci/validation/Violation.java).

Cette partie est un projet **indépendant** de la partie 1 — rien à copier, tout le matériel est dans `01-code-java/`. Les annotations [`LongueurMin`](01-code-java/src/main/java/be/vinci/validation/LongueurMin.java), [`Positif`](01-code-java/src/main/java/be/vinci/validation/Positif.java) et [`Valide`](01-code-java/src/main/java/be/vinci/validation/Valide.java) sont fournies complètes (comme en partie 1, votre travail porte sur leur prise en charge dans le `Validateur`, pas sur leur déclaration) ; seule [`NonNul`](01-code-java/src/main/java/be/vinci/validation/NonNul.java) arrive sans ses méta-annotations. `Violation` et `RapportValidation` (la structure du rapport), la classe [`Adresse`](01-code-java/src/main/java/be/vinci/domaine/Adresse.java) (package `be.vinci.domaine`) et un [`Main`](01-code-java/src/main/java/be/vinci/main/Main.java) (package `be.vinci.main`) de démonstration sont également fournis. Chaque question est vérifiée par les tests JUnit fournis ([`ValidateurTest`](01-code-java/src/test/java/be/vinci/validation/ValidateurTest.java)).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier11_partie2` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `be.vinci.validation`, `be.vinci.domaine`, `be.vinci.main`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml` (voir le `pom.xml` fourni).

Exécutez les tests fournis directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

### L'annotation `@NonNul`

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez l'annotation `NonNul` : elle est déclarée, mais il lui manque ses méta-annotations. Complétez-la pour qu'elle soit conservée à l'exécution et réservée aux **champs** — la cible n'est plus la même qu'en partie 1, voir la théorie (`11B_1_theorie.md`).

### Découvrir les champs annotés

**Question 2** :
Complétez `trouverChampsAnnotes(Class<?>, Class<? extends Annotation>)` dans la classe `Validateur` : elle renvoie la liste des champs déclarés de la classe qui portent l'annotation reçue en paramètre (`getDeclaredFields`, `isAnnotationPresent`), triés par nom — l'ordre renvoyé par `getDeclaredFields` n'est pas garanti, le tri rend le rapport reproductible. Elle sert à toutes les questions suivantes : chacune la réutilise avec une annotation différente.

### Lire la valeur des champs

**Question 3** :
Complétez `validerNonNul(Object)` : pour chaque champ `@NonNul` de la classe de l'objet, elle lit la valeur du champ et ajoute une `Violation` (le nom du champ + un message) si cette valeur est nulle. Attention, les champs d'`Etudiant` sont privés : `champ.get(objet)` lance une `IllegalAccessException` tant que vous n'avez pas appelé `champ.setAccessible(true)`.

### Lire l'élément d'une annotation

**Question 4** :
Complétez `validerLongueurMin(Object)` : l'annotation `LongueurMin` porte un élément `int valeur` — le nombre minimal de caractères exigé. Pour chaque champ `@LongueurMin`, lisez ce minimum sur l'annotation (`getAnnotation(LongueurMin.class).valeur()`), puis la valeur du champ : si c'est une `String` plus courte que le minimum, ajoutez une `Violation`. Un champ nul n'est pas une violation de longueur — c'est l'affaire de `@NonNul`.

### Valider un objet complet

**Question 5** :
Complétez `valider(Object)` : elle renvoie un `RapportValidation` agrégeant **toutes** les violations de l'objet (`ajouterToutes`) — celles de la Question 3 et celles de la Question 4 (puis celles des Questions 8 et 9 si vous faites les parties optionnelles). Exécutez ensuite le `Main` fourni : il valide un étudiant correct puis un étudiant fautif et affiche les deux rapports.

### Observations

**Question 6** :
Retirez (temporairement) l'appel `setAccessible(true)` de votre Question 3 et relancez les tests : que se passe-t-il ? Pourquoi cet appel est-il nécessaire, et qu'en déduisez-vous sur ce que `private` protège — et ne protège pas — face à la réflexion ?

**Question 7** :
Comparez ce validateur au `Util.checkObject`/`checkString` que vos constructeurs appellent depuis l'atelier 1 : qu'est-ce qui change dans la façon d'exprimer la règle ? Cherchez « Jakarta Bean Validation » (`@NotNull`, `@Size`) : que reconnaissez-vous ?

Réponses aux Questions 6 et 7 dans [`02-solution/11B_solutions-observations.md`](02-solution/11B_solutions-observations.md) : réfléchissez (et testez !) avant de les consulter.

## Parties optionnelles

### `@Positif`

**Question 8** :
Complétez `validerPositif(Object)` : l'annotation `Positif` (fournie) marque un champ numérique dont la valeur doit être strictement positive. Pour chaque champ `@Positif` dont la valeur est un `Number`, ajoutez une `Violation` si `doubleValue()` n'est pas strictement positive — rappel de la théorie : pour un champ `int`, `champ.get` renvoie un `Integer`, qui est un `Number`. N'oubliez pas de raccorder cette validation à `valider` (Question 5).

### `@Valide`

**Question 9** :
Complétez `validerRecursivement(Object)` : l'annotation `Valide` (fournie) marque un champ objet à valider **récursivement** — dans `Etudiant`, le champ `adresse` la porte, et `Adresse` déclare ses propres contraintes. Pour chaque champ `@Valide` non nul, validez l'objet référencé (avec `valider`) et rapportez ses violations en préfixant leur champ du nom du champ porteur (par exemple `adresse.rue`). Un champ `@Valide` nul n'est pas validé récursivement — le signaler est l'affaire de `@NonNul`. Raccordez cette validation à `valider`, et observez le rapport de l'étudiant fautif du `Main` : la rue manquante de son adresse doit maintenant apparaître.

---

*Passez à la [théorie suivante](../../12-injection-dependances/01-partie1/12A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/11-introspection-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
