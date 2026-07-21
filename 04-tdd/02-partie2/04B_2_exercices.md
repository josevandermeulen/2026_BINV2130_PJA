# Atelier 4 : Test Driven Development (TDD) – partie 2

## Objectif

Appliquer le TDD à l'évolution d'un code existant : transformer les tâches de simples `String` en objets `Task`, en s'appuyant sur les tests de la partie 1 comme filet de sécurité, puis implémenter les nouvelles fonctionnalités spécifiées en partie 1.

## Concepts

1. Les tests existants comme filet de sécurité lors d'un refactoring
2. TDD lors de la mise à jour de fonctionnalités existantes
3. TDD pour de nouvelles fonctionnalités à partir d'une spécification
4. Organisation des tests avec `@Nested` (vu à l'atelier 3 partie 2)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Exercices

### Introduction

En partie 1, vous avez constaté que gérer l'état d'une tâche à côté d'une simple `String` devient inconfortable, et vous avez spécifié les scénarios de tests des nouvelles fonctionnalités : une tâche doit maintenant être un objet avec un titre et une description. Dans cette partie, vous faites évoluer l'application vers cette classe `Task`, en TDD.

### Consignes

Dans IntelliJ, créez un projet intitulé `AJ_atelier04_partie2`. Récupérez les classes fournies dans `01-code-java/` : `TodoList.java` dans un dossier `src` (package par défaut) et `TodoListTest.java` dans un dossier `tests`, tous deux marqués respectivement Sources Root et Test Sources Root — c'est l'état de la solution de la partie 1 (voir aussi `../01-partie1/02-solution/`). Appuyez-vous sur votre spécification complétée de la partie 1 ; une solution de cette spécification est fournie dans `../01-partie1/02-solution/04A_solutions-scenarios-de-tests.md`.

### TDD lors de la mise à jour de fonctionnalités existantes

**Question 1** : Veuillez faire du TDD pour les scénarios que vous avez dû modifier pour prendre en compte le fait qu'une tâche ne doit maintenant plus être juste une `String` : une tâche est maintenant un objet avec un titre et une description.

✏️ *A corriger au tableau*

Tips : commencez par mettre à jour les scénarios de tests de [`TodoListTest`](01-code-java/tests/TodoListTest.java) associés à l'ajout de tâches au sein d'une TodoList. Comme une tâche est un objet très simple, il n'est pas utile de créer des Mock objects de ceux-ci. Vous pouvez créer et directement utiliser un constructeur de tâches…

Faites cette mise à jour avec un assistant IA pour au moins un des scénarios existants, en respectant le cycle red-green-refactor : demandez d'abord uniquement l'adaptation du test à la nouvelle exigence, vérifiez qu'il échoue pour de bonnes raisons, puis seulement ensuite demandez la mise à jour du code.

💭 Dans cet exercice, vous devriez vous rendre compte à quel point il est confortable de mettre à jour des fonctionnalités existantes lorsqu'on a déjà des tests existants. Le jeu consiste à mettre à jour le code pour faire passer tous les anciens tests. Bien sûr, parfois, il est aussi utile de créer des nouveaux tests pour bien couvrir les mises à jour des fonctionnalités.

Ajoutez un test qui vérifie que `removeTask` sur une tâche qui a été clonée a le même comportement que s'il est appelé sur la tâche initiale.

### TDD pour les nouvelles fonctionnalités de la classe `Task`

**Question 2** : Veuillez faire du TDD pour les scénarios identifiés lors de la spécification de la partie 1 (« nouveaux scénarios de tests »). Vous aurez besoin d'une nouvelle classe `TaskTest`.

Si vous souhaitez exécuter tous les tests se trouvant dans les différentes classes de tests situées dans le dossier `tests` en une seule fois, vous pouvez le faire ainsi : clic droit sur `tests`, Run 'All Tests'.

### À partir d'ici, faites du TDD avec l'IA

À partir de la question 3, aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour faire du TDD : respectez scrupuleusement le cycle red-green-refactor étape par étape — demandez d'abord le test seul, vérifiez qu'il échoue pour de bonnes raisons, puis demandez le code minimal, vérifiez qu'il passe, puis le refactor si besoin.

### TDD pour les nouvelles fonctionnalités de la classe `TodoList`

**Question 3** : Il est temps de s'occuper de l'opération permettant de retrouver une tâche au sein de la TodoList.

De plus, il devrait être possible de modifier une tâche par le biais de la TodoList en indiquant tant la tâche que l'on souhaite mettre à jour que les nouvelles données de cette tâche.

Veuillez faire du TDD pour les nouveaux scénarios au sein de la classe `TodoListTest`.

### Compter et vider la liste

**Question 4** : Reprenez les scénarios `countTasksEmpty`, `countTasksAfterAdd` et `countTasksAfterRemove` de la partie 1 et adaptez-les pour qu'ils utilisent des `Task` plutôt que de simples `String`.

**Question 5** : Faites de même pour `clearTasks` : adaptez `clearTasks` et `clearEmptyTasks` aux objets `Task`.

---

*Passez à la [théorie suivante](../../05-mocks/01-partie1/05A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
