# Atelier 11 : Introspection – partie 1

## Objectif

L'objectif de cet atelier est de reconstruire le cœur de JUnit — un **mini-JUnit** — à l'aide de la réflexion : une annotation personnalisée `@MonTest` marque les méthodes de test, et un [`TestRunner`](01-code-java/src/main/java/be/vinci/minijunit/TestRunner.java) (package `be.vinci.minijunit`) les découvre dynamiquement, les invoque et produit un rapport d'exécution. Le runner est ensuite enrichi de fonctionnalités que vous connaissez du vrai JUnit : préparation avant chaque test, libellés lisibles dans le rapport, répétition d'un test.

## Concepts

1. Introspection / réflexion Java (`Class`, `Method`)
2. Découverte de méthodes (`getDeclaredMethods`, `isAnnotationPresent`)
3. Instanciation et invocation dynamiques (`newInstance`, `invoke`, `InvocationTargetException`)
4. Annotations personnalisées (`@interface`, `@Retention`, `@Target`)
5. Annotations à élément (`getAnnotation`, lecture de la valeur)
6. Fonctionnement interne d'un framework de tests

## Vidéos

1. [Introspection - Machine Virtuelle Java JVM](https://www.youtube.com/watch?v=hozaDXJ7J2Y)
2. [Introspection - typage statique](https://www.youtube.com/watch?v=aFIYXPp-5MY)
3. [Introspection - Inspection d'une classe](https://www.youtube.com/watch?v=s8D-yfzdkSI)
4. [Introspection - Inspection et instanciation d'objets](https://www.youtube.com/watch?v=W7EYb072Rgc)
5. [Introspection - Annotation et mini JUnit](https://www.youtube.com/watch?v=Vo-nmSstLF8)

## Exercices

### Introduction

Depuis l'atelier 3, vous écrivez des classes de tests : vous les annotez `@Test`, vous cliquez sur ▶, et JUnit trouve vos méthodes et les exécute. Mais comment fait-il ? Il ne connaît pas vos classes à l'avance, et vous n'écrivez jamais `new MaClasseDeTest()` ni `maClasseDeTest.monTest()`...

La réponse tient en un mot : la **réflexion**. Dans cet atelier, vous allez reconstruire ce mécanisme vous-même :

1. une annotation personnalisée `@MonTest` (l'équivalent de `@Test`) ;
2. un `TestRunner` qui découvre par réflexion les méthodes annotées d'une classe, les invoque dynamiquement et classe chaque résultat (réussite, échec d'assertion, erreur inattendue) dans un [`RapportExecution`](01-code-java/src/main/java/be/vinci/minijunit/RapportExecution.java) ;
3. le support de trois annotations supplémentaires, équivalentes à `@BeforeEach`, `@DisplayName` et `@RepeatedTest` : `@AvantChaqueTest`, `@Affichage` et `@Repeter`.

Plusieurs classes vous sont fournies pour cadrer le travail : [`Verifications`](01-code-java/src/main/java/be/vinci/minijunit/Verifications.java) (les assertions maison, qui lancent une `AssertionError` en cas d'échec), [`ResultatTest`](01-code-java/src/main/java/be/vinci/minijunit/ResultatTest.java) et `RapportExecution` (la structure du rapport), les annotations [`AvantChaqueTest`](01-code-java/src/main/java/be/vinci/minijunit/AvantChaqueTest.java), [`Affichage`](01-code-java/src/main/java/be/vinci/minijunit/Affichage.java), [`Repeter`](01-code-java/src/main/java/be/vinci/minijunit/Repeter.java) et [`TestDesactive`](01-code-java/src/main/java/be/vinci/minijunit/TestDesactive.java) (complètes — votre travail porte sur leur prise en charge dans le `TestRunner`, pas sur leur déclaration), ainsi qu'une petite classe métier [`Calculatrice`](01-code-java/src/main/java/be/vinci/calculatrice/Calculatrice.java) et sa classe de tests mini-JUnit [`CalculatriceTest`](01-code-java/src/main/java/be/vinci/calculatrice/CalculatriceTest.java) (package `be.vinci.calculatrice`) pour la démonstration finale.

Petit vertige assumé : votre mini-JUnit est lui-même vérifié... par le vrai JUnit ([`TestRunnerTest`](01-code-java/src/test/java/be/vinci/minijunit/TestRunnerTest.java), fourni), qui exécute votre `TestRunner` sur des classes de tests factices ([`ExempleTests`](01-code-java/src/test/java/be/vinci/minijunit/ExempleTests.java), [`ExempleTestsAvecAvant`](01-code-java/src/test/java/be/vinci/minijunit/ExempleTestsAvecAvant.java), [`ExempleTestsAvecAffichage`](01-code-java/src/test/java/be/vinci/minijunit/ExempleTestsAvecAffichage.java), [`ExempleTestsAvecRepetition`](01-code-java/src/test/java/be/vinci/minijunit/ExempleTestsAvecRepetition.java), [`ExempleTestsAvecDesactive`](01-code-java/src/test/java/be/vinci/minijunit/ExempleTestsAvecDesactive.java)) et vérifie le rapport produit.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier11_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `be.vinci.minijunit`, `be.vinci.calculatrice`, `be.vinci.main` — le package `be.vinci.minitest` sert à la question d'examen optionnelle) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml` (voir le `pom.xml` fourni).

Chaque question est vérifiée par les tests JUnit fournis (`TestRunnerTest`) : exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

### L'annotation `@MonTest`

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez l'annotation [`MonTest`](01-code-java/src/main/java/be/vinci/minijunit/MonTest.java) : elle est déclarée, mais il lui manque ses méta-annotations. Complétez-la pour qu'elle soit conservée à l'exécution (sans quoi la réflexion ne la verra jamais) et réservée aux méthodes. La théorie ([`11A_1_theorie.md`](11A_1_theorie.md)) reprend la syntaxe exacte.

### Découvrir les méthodes de test

**Question 2** :
Complétez `trouverMethodesDeTest(Class<?>)` dans la classe `TestRunner` : elle renvoie la liste des méthodes déclarées de la classe qui portent l'annotation `@MonTest` (`getDeclaredMethods`, `isAnnotationPresent`), triées par nom — l'ordre renvoyé par `getDeclaredMethods` n'est pas garanti, le tri rend le rapport reproductible. Les méthodes non annotées (comme `pasUnTest` dans `ExempleTests`) ne doivent jamais être renvoyées.

### Exécuter les tests

**Question 3** :
Complétez `executerTests(Class<?>)` : pour chaque méthode de test trouvée à la Question 2, elle crée une **nouvelle instance** de la classe (`getDeclaredConstructor().newInstance()` — une instance par test, comme le vrai JUnit, pour que les tests ne partagent aucun état), invoque la méthode, et ajoute au rapport un `ResultatTest` :

1. `REUSSITE` si l'invocation se termine normalement ;
2. `ECHEC` si la méthode a lancé une `AssertionError` — attention, `invoke` l'emballe dans une `InvocationTargetException`, l'exception d'origine est dans `getCause` ; reprenez son message dans le `ResultatTest` ;
3. `ERREUR` pour toute autre exception.

**Question 4** :
Exécutez ensuite [`Main`](01-code-java/src/main/java/be/vinci/main/Main.java) (package `be.vinci.main`) (fourni) : il lance votre `TestRunner` sur `CalculatriceTest`, la classe de tests écrite pour le mini-JUnit. Les trois tests doivent réussir... alors qu'aucune ligne de votre code ne mentionne `CalculatriceTest` ni ses méthodes. Qu'est-ce qui a permis cela ?

Réponses aux Questions 4 et 8 dans [`02-solution/11A_solutions-observations.md`](02-solution/11A_solutions-observations.md) : réfléchissez (et testez !) avant de les consulter.

### `@AvantChaqueTest` : préparer chaque test

**Question 5** :
L'annotation `AvantChaqueTest` (fournie, équivalent de `@BeforeEach`) marque une méthode de préparation. Modifiez `executerTests` pour qu'avant chaque test, la ou les méthodes annotées `@AvantChaqueTest` de la classe soient invoquées sur l'instance fraîchement créée — regardez `CalculatriceTest` : c'est ainsi que son attribut `calculatrice` est initialisé avant chaque test.

### `@Affichage` : un libellé dans le rapport

**Question 6** :
L'annotation `Affichage` (fournie, équivalent de `@DisplayName`) porte un élément `String valeur` : un libellé lisible pour une méthode de test — la théorie ([`11A_1_theorie.md`](11A_1_theorie.md), section « Annotations à élément ») montre comment lire la valeur d'un élément. Modifiez `executerTests` pour que le rapport affiche ce libellé à la place du nom de la méthode quand l'annotation est présente : `ResultatTest` (fourni) a un constructeur à quatre paramètres qui reçoit le libellé en plus du nom de la méthode. Regardez `CalculatriceTest` : deux de ses méthodes portent déjà `@Affichage`, la sortie de `Main` doit maintenant montrer leurs libellés.

### `@Repeter` : répéter un test

**Question 7** :
L'annotation `Repeter` (fournie, équivalent de `@RepeatedTest`) porte un élément `int fois`. Modifiez `executerTests` pour qu'une méthode de test annotée `@Repeter` soit invoquée `fois` fois : une **nouvelle instance** de la classe pour chaque répétition, et un `ResultatTest` ajouté au rapport par répétition.

**Question 8** :
Regardez la classe de tests factice `ExempleTestsAvecRepetition` : son test incrémente un attribut `compteur` et vérifie qu'il vaut 1. Pourquoi exiger une instance fraîche par répétition — que se passerait-il si les trois répétitions partageaient la même instance ?

## Parties optionnelles

### Question d'examen : mini framework de tests

La question suivante est issue de l'examen de septembre 2026 (question « Introspection - Mini Framework de Tests », 8 points, ± 40 minutes). Elle se fait dans un nouveau package `be.vinci.minitest`, indépendant du mini-JUnit ci-dessus : un mini framework de tests similaire, mais avec ses propres classes.

Les classes `Test` (l'annotation), `TestResult`, `SampleTests` et `Main` (package `be.vinci.minitest`) vous sont fournies telles quelles. Votre travail porte uniquement sur [`TestRunner`](01-code-java/src/main/java/be/vinci/minitest/TestRunner.java) (package `be.vinci.minitest`) — ne confondez pas avec le `TestRunner` du mini-JUnit ci-dessus, dans un package différent.

**Question 9** :
Complétez la méthode `run(Class<?> testClass)` de `TestRunner` : elle instancie la classe reçue en paramètre via son constructeur sans argument, découvre toutes les méthodes annotées `@Test`, invoque chacune d'elles, capture les exceptions pour distinguer succès et échec — `invoke` emballe l'exception du test dans une `InvocationTargetException`, l'exception d'origine est dans `getCause` ; reprenez son message dans le `TestResult` — et renvoie la liste des `TestResult` obtenus, un par méthode `@Test` trouvée.

Les six blocs à compléter sont balisés par des commentaires `TODO Question 9 (étape 1)` à `(étape 6)` dans `run` et dans la méthode privée `invoque` (appelée depuis `run`). Vérifiez votre implémentation en lançant le `main` de `Main` (package `be.vinci.minitest`) et en comparant la sortie avec celle ci-dessous (l'ordre des tests peut varier, la réflexion ne garantit pas l'ordre de `getDeclaredMethods`) :

```
=== Rapport de Tests : SampleTests ===

[PASS] testAddition                   - Addition simple : 1 + 1 doit valoir 2
[PASS] testStringLength               - Longueur de la chaîne "hello" vaut 5
[PASS] testListNotNull                - Une ArrayList vide n'est pas nulle
[FAIL] testIntentionalFailure         - (aucune description)
       Erreur : Ce test échoue intentionnellement

Résultat : 3 réussi(s), 1 échoué(s), 4 au total
```

### `@TestDesactive` : ignorer un test

**Question 10** :
L'annotation `TestDesactive` (fournie, équivalent de `@Disabled`) désactive une méthode de test. Modifiez `executerTests` (package `be.vinci.minijunit`) pour qu'une méthode annotée `@TestDesactive` ne soit **jamais invoquée** : elle apparaît dans le rapport avec le statut `IGNORE` (message `null`), et le compteur « ignorés » du rapport en tient compte. La classe de tests factice `ExempleTestsAvecDesactive` vérifie que la méthode désactivée n'est pas exécutée : elle lancerait une exception si elle l'était.

---

*Passez à la [théorie suivante](../02-partie2/11B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/11-introspection-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
