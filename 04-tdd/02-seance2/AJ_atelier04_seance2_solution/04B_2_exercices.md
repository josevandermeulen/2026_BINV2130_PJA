# Atelier 4 : Test Driven Development (TDD) – séance 2

## Objectif

Appliquer le TDD à l'évolution d'un code existant : transformer les tâches de simples `String` en objets `Task`, en s'appuyant sur les tests de la séance 1 comme filet de sécurité, puis implémenter les nouvelles fonctionnalités spécifiées en séance 1.

## Concepts

1. Les tests existants comme filet de sécurité lors d'un refactoring
2. TDD lors de la mise à jour de fonctionnalités existantes
3. TDD pour de nouvelles fonctionnalités à partir d'une spécification
4. Organisation des tests avec `@Nested` (vu à l'atelier 3 séance 2)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Exercices

### Introduction

En partie 1, vous avez constaté que gérer l'état d'une tâche à côté d'une simple `String` devient inconfortable, et vous avez spécifié les scénarios de tests des nouvelles fonctionnalités : une tâche doit maintenant être un objet avec un titre et une description. Dans cette séance, vous faites évoluer l'application vers cette classe `Task`, en TDD.

### Consignes

Dans IntelliJ, ouvrez le dossier `AJ_atelier04_seance2` (File → Open…). Il contient déjà [`TodoList.java`](src/TodoList.java) dans `src` (package par défaut) et `TodoListTest.java` dans `test` — c'est l'état de la solution de la séance 1 (voir aussi [`AJ_atelier04_seance1_solution/`](../../01-seance1/AJ_atelier04_seance1_solution/)). Les scénarios de tests à implémenter, issus de la spécification de la séance 1, sont rappelés dans chaque question ; la solution complète de cette spécification est dans [`04A_solutions-scenarios-de-tests.md`](../../01-seance1/AJ_atelier04_seance1_solution/04A_solutions-scenarios-de-tests.md). Comme en séance 1, vérifiez que `src` et `test` sont marqués respectivement Sources Root et Test Sources Root, et que JUnit 5 est ajouté au projet.

### TDD lors de la mise à jour de fonctionnalités existantes

**Question 1** : Veuillez faire du TDD pour les scénarios que vous avez dû modifier pour prendre en compte le fait qu'une tâche ne doit maintenant plus être juste une `String` : une tâche est maintenant un objet avec un titre et une description.

✏️ *A corriger au tableau*

Tips : commencez par mettre à jour les scénarios de tests de [`TodoListTest`](test/TodoListTest.java) associés à l'ajout de tâches au sein d'une `TodoList`. Une tâche est un objet très simple : créez-la directement avec son constructeur.

*(UC1) Ajouter une tâche à la liste :*

1. `addTask` : la tâche est contenue dans la liste, on informe du succès de l'opération
2. `addNullTask` : on tente d'ajouter une tâche qui est nulle, la tâche n'est pas contenue dans la liste, on informe de l'échec de l'opération (remplace `addEmptyTask` : une tâche n'est plus une `String`, la chaîne vide n'a plus de sens)
3. `addExistingTask` : on tente d'ajouter une tâche déjà présente (autre tâche avec même titre et même description), on informe de l'échec de l'opération

*(UC3) Supprimer une tâche de la liste :*

1. `removeTask` : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
2. `removeUnexistingTask` : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération
3. `removeClonedTask` (nouveau) : supprimer une autre instance de même titre et même description que la tâche présente la supprime bien, on informe du succès de l'opération

Faites cette mise à jour avec un assistant IA pour au moins un des scénarios existants, en respectant le cycle red-green-refactor : demandez d'abord uniquement l'adaptation du test à la nouvelle exigence, vérifiez qu'il échoue pour de bonnes raisons, puis seulement ensuite demandez la mise à jour du code.

💭 Dans cet exercice, vous devriez vous rendre compte à quel point il est confortable de mettre à jour des fonctionnalités existantes lorsqu'on a déjà des tests existants. Le jeu consiste à mettre à jour le code pour faire passer tous les anciens tests. Bien sûr, parfois, il est aussi utile de créer des nouveaux tests pour bien couvrir les mises à jour des fonctionnalités.

### TDD pour les nouvelles fonctionnalités de la classe `Task`

**Question 2** : Veuillez faire du TDD pour les scénarios ci-dessous. Vous aurez besoin d'une nouvelle classe `TaskTest`.

Terminer une tâche et en modifier le titre (UC5 et UC4) existaient déjà, mais sur une simple `String` : ces opérations passent maintenant dans `Task`. Créer une tâche et en modifier la description (UC6 et UC7) sont nouvelles.

Les UC sont données dans l'ordre où les implémenter : chaque groupe s'appuie sur le précédent (tous les tests ont besoin du constructeur, et les tests « tâche terminée » de UC4 et UC7 ont besoin de pouvoir terminer une tâche).

*(UC6) Créer une tâche :*

1. `createTask` : en donnant un titre et une description, la tâche est créée
2. `createNullFieldsTask` : on lance une exception si le titre ou la description est nulle
3. `createEmptyTitleTask` : on lance une exception si le titre est vide (constitué uniquement de caractères « blancs »)

*(UC5) Terminer une tâche :*

1. `completeTask` : la tâche est terminée, on informe du succès de l'opération
2. `completeAlreadyCompletedTask` : on tente de terminer une tâche qui est déjà terminée, on informe de l'échec de l'opération

*(UC4) Mettre à jour le titre d'une tâche :*

1. `updateTitle` : le titre est mis à jour, on informe du succès de l'opération
2. `updateTitleWhenCompletedTask` : on tente de mettre à jour le titre alors que la tâche est déjà terminée, on informe de l'échec de l'opération
3. `updateTitleToEmptyOrNullString` : on tente de mettre à jour le titre vers une string vide ou nulle, on informe de l'échec de l'opération

*(UC7) Mettre à jour la description d'une tâche :*

1. `updateDescription` : la description est mise à jour, on informe du succès de l'opération
2. `updateDescriptionWhenCompletedTask` : on tente de mettre à jour la description alors que la tâche est déjà terminée, on informe de l'échec de l'opération
3. `updateDescriptionToNull` : on tente de mettre à jour la description vers une string nulle, on informe de l'échec de l'opération

Si vous souhaitez exécuter tous les tests se trouvant dans les différentes classes de tests situées dans le dossier `test` en une seule fois, vous pouvez le faire ainsi : clic droit sur `test`, Run 'All Tests'.

### TDD pour les nouvelles fonctionnalités de la classe `TodoList`

**Question 3** :

Il est temps de s'occuper de l'opération permettant de retrouver une tâche au sein de la `TodoList` (`findTask`). Veuillez faire du TDD pour ces scénarios au sein de la classe `TodoListTest`.

*(UC8) Renvoyer une tâche se trouvant dans la TodoList :*

1. `findTask` : après avoir ajouté une ou plusieurs tâche(s), on renvoie la tâche demandée
2. `findUnexistingTask` : on demande de trouver une tâche qui n'existe pas et on retourne `null`

**Question 4** :

Il devrait aussi être possible de modifier une tâche par le biais de la `TodoList` (`updateTask`), en indiquant tant la tâche que l'on souhaite mettre à jour que les nouvelles données de cette tâche. Veuillez faire du TDD pour ces scénarios au sein de la classe `TodoListTest`.

*(UC9) Mettre à jour la TodoList en indiquant la tâche à mettre à jour et les données à mettre à jour :*

1. `updateTodoListTask` : la tâche est mise à jour, elle est contenue dans la liste et on informe du succès de l'opération
2. `updateTodoListUnexistingTask` : on demande de modifier une tâche qui n'existe pas et on informe de l'échec de l'opération
3. `updateTodoListWithNullTask` : la tâche n'est pas mise à jour car on donne une tâche nulle pour la nouvelle tâche ou pour la tâche à mettre à jour, on informe de l'échec de l'opération
4. `updateTodoListWhenCompletedTask` : la tâche n'est pas mise à jour car on tente de mettre à jour une tâche qui est terminée, on informe de l'échec de l'opération

### 🤖 À partir d'ici, faites du TDD avec l'IA

À partir de la question 5, aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour faire du TDD : respectez scrupuleusement le cycle red-green-refactor étape par étape — demandez d'abord le test seul, vérifiez qu'il échoue pour de bonnes raisons, puis demandez le code minimal, vérifiez qu'il passe, puis le refactor si besoin.

### Compter et vider la liste

**Question 5** :

Reprenez les scénarios `countTasksEmpty`, `countTasksAfterAdd` et `countTasksAfterRemove` de la séance 1 et adaptez-les pour qu'ils utilisent des `Task` plutôt que de simples `String`.

**Question 6** :

Faites de même pour `clearTasks` : adaptez `clearTasks` et `clearEmptyTasks` aux objets `Task`.

---

*Passez à la [théorie suivante](../../../05-mocks/01-seance1/AJ_atelier05_seance1/05A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
