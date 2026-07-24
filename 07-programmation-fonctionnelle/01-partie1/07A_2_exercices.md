# Atelier 7 : Programmation fonctionnelle – partie 1

## Objectif

L'objectif de cet atelier est d'approfondir la programmation fonctionnelle en Java en manipulant les interfaces fonctionnelles et les lambdas.

## Concepts

1. Interfaces fonctionnelles
2. Expressions lambda
3. `Predicate`
4. `Function`
5. `Consumer`
6. `Comparator`
7. Références de méthodes

## Vidéos

1. [Comment aplatir les collections imbriquées en Java avec flatMap](https://www.youtube.com/watch?v=aZHDUchqjyk)
2. [Immuabilité Java](https://www.youtube.com/watch?v=u0j64SmIoc4)
3. [Mise en pratique du design fonctionnel en Java partie 1 - L'immuabilité](https://www.youtube.com/watch?v=s6Zwdeg3NdI)
4. [Programmation fonctionnelle cas pratique en Java partie 2 - Les exceptions](https://www.youtube.com/watch?v=g0EVQYClhtA)

## Exercices

### Introduction

Cet atelier approfondit les streams : on ouvre le capot des interfaces fonctionnelles (`Function`, `Consumer`, `Predicate`, `Comparator`) pour comprendre ce que sont réellement les lambdas.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier07_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `main`, `lambda`, `code_theorie`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml`.

### Trouver le type

Pour les huit questions suivantes, aucun code n'est à écrire : déterminez sur papier le type exact (générique compris) demandé, puis vérifiez votre réponse dans IntelliJ (`Ctrl+P` dans les parenthèses d'une méthode pour voir le type attendu du paramètre, `Ctrl+Maj+P` sur une expression pour voir son type).

C'est un échauffement : comptez ~3 minutes par question, 30 minutes maximum pour la section. Si une réponse vous résiste, vérifiez-la dans IntelliJ, notez-la, et passez à la suivante — la correction au tableau y reviendra, et le reste de l'atelier (où vous écrivez du code) est plus important.

✏️ *A corriger au tableau*

**Question 1** :
Quel est le type du paramètre de la méthode `filter` dans le code suivant ?

```java
long nbGrands = employes.stream()
        .filter(e -> e.getTaille() > 180)
        .count();
```

**Question 2** :
Quel est le type du paramètre de la méthode `map` dans le code suivant ? Attention, la référence de méthode ne suffit pas : donnez l'interface fonctionnelle complète avec ses types génériques.

```java
List<Integer> tailles = employes.stream()
        .map(Employe::getTaille)
        .toList();
```

**Question 3** :
Quel est le type du paramètre de la méthode `Stream.generate` dans le code suivant ?

```java
List<Employe> interimaires = Stream.generate(() -> new Employe(Genre.HOMME, 180, "Intérimaire"))
        .limit(3)
        .toList();
```

**Question 4** :
Quel est le type du second paramètre de la méthode `reduce` dans le code suivant ?

```java
int sommeTailles = employes.stream()
        .map(Employe::getTaille)
        .reduce(0, (total, taille) -> total + taille);
```

Les quatre questions suivantes portent cette fois sur le type de retour : quel est le type de la variable `resultat` dans chaque extrait ?

**Question 5** :

```java
var resultat = employes.stream()
        .map(Employe::getNom);
```

**Question 6** :

```java
var resultat = employes.stream()
        .filter(e -> e.getGenre() == Genre.FEMME);
```

**Question 7** :

```java
var resultat = employes.stream()
        .filter(e -> e.getTaille() > 200)
        .findFirst();
```

**Question 8** :

```java
var resultat = employes.stream()
        .map(Employe::getNom)
        .count();
```

### Paramètre de la méthode `map`

**Question 9** :
Ouvrez la classe [`ExerciceFunctionalInterface`](01-code-java/src/main/java/main/ExerciceFunctionalInterface.java) située dans le package `main` et dans laquelle se trouve la méthode `exMap` qui contient le code suivant :

```java
Stream<String> listeNom = employes.stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .sorted(Comparator.comparingInt(Employe::getTaille)
                .reversed())
        .map(e -> e.getNom());
```

Trouvez le type du paramètre de la méthode `map`.

**Question 10** :
Transformez le code de `exMap` en implémentant cette interface plutôt que de passer un lambda.

### Paramètre de la méthode `forEach`

**Question 11** :
Toujours dans `ExerciceFunctionalInterface`, la méthode `exForEach` contient le code suivant :

```java
List<String> noms = new ArrayList<>();
employes.forEach(e -> noms.add(e.getNom()));
```

`employes` est une `List<Employe>`. Avec IntelliJ (clic droit sur `forEach` → *Go to* → *Declaration or Usages*), regardez sa signature :

```java
void forEach(Consumer<? super T> action)
```

Trouvez le type du paramètre de la méthode `forEach`.

**Question 12** :
Faites le même raisonnement qu'au point précédent et transformez le code en implémentant cette interface plutôt que de passer un lambda.

### Des lambdas dans un autre contexte

**Question 13** :
Dans la classe `ExerciceFunctionalInterface`, prenez la méthode `exComparator`. Celle-ci utilise un `Comparator` en créant une classe et en l'instanciant. Modifiez le code pour remplacer le comparator par une lambda expression.

### Quelques Predicate et Function

Dans cet exercice, vous utiliserez des `Predicate` et des `Function`. N'utilisez pas de Stream pour les Questions 14 à 16 ! Vous devrez utiliser la méthode `test` des `Predicate` et la méthode `apply` des `Function`.

**Question 14** :
Dans la classe [`Lambda`](01-code-java/src/main/java/lambda/Lambda.java) du package `lambda`, implémentez la méthode `allMatches` en respectant la javadoc fournie. Testez-la via la classe [`TestLambda`](01-code-java/src/main/java/lambda/TestLambda.java) en fournissant des expressions lambda qui correspondent aux indications en commentaires.

**Question 15** :
Implémentez de la même façon la méthode `transformAll` et testez-la via `TestLambda`.

## Parties optionnelles

### Généricité

**Question 16** :
Modifiez vos méthodes `allMatches` et `transformAll` pour qu'elles puissent maintenant accepter des `List` de n'importe quel type (au lieu de uniquement des `Integer`). Dans la classe `TestLambda`, décommentez la deuxième partie et complétez les expressions lambda.

### Les mêmes traitements avec l'API Stream

**Question 17** :
Ajoutez à votre classe `Lambda` une méthode `filter` qui fait exactement la même chose que `allMatches`, mais en utilisant l'API Stream. Dans la classe `TestLambda`, effectuez les mêmes tests que pour `allMatches`.

**Question 18** :
Ajoutez de même une méthode `map` qui fait exactement la même chose que `transformAll`, mais en utilisant l'API Stream. Testez-la comme `transformAll`.

---

*Passez à la [théorie suivante](../02-partie2/07B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
