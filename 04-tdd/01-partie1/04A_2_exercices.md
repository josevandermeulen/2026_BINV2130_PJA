# Atelier 4 : Test Driven Development (TDD) – partie 1

## Objectif

Découvrir le Test Driven Development en pratiquant le cycle *red-green-refactor* sur une application de gestion de tâches, puis apprendre à spécifier des scénarios de tests avant toute implémentation.

## Concepts

1. Le cycle TDD : écrire un test qui échoue, écrire le code minimum pour le faire passer, refactorer
2. Spécifier des scénarios de tests à partir des cas d'utilisation (UC)
3. Cas positifs, cas négatifs, cas limites et classes d'équivalence
4. Organisation des tests avec `@Nested` (vu à l'atelier 3 partie 2)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Exercices

### Introduction

Nous développons une application de gestion de tâches : une [`TodoList`](01-code-java/src/TodoList.java) contient des tâches (de simples `String` pour l'instant) qu'on peut ajouter et rechercher. La théorie (introduction au TDD, spécification des tests, tutoriel complet d'implémentation, résumé) se trouve dans `04A_1_theorie.md`. L'état du projet à la fin du tutoriel vous est fourni dans `01-code-java/`.

### Consignes

Assurez-vous d'abord d'avoir lu l'intégralité de la théorie (`04A_1_theorie.md`) et visionné les vidéos ci-dessus. Dans IntelliJ, créez un projet intitulé `AJ_atelier04_partie1`. Récupérez les classes fournies dans `01-code-java/` : `TodoList.java` dans un dossier `src` (package par défaut) et `TodoListTest.java` dans un dossier `tests`, tous deux marqués respectivement Sources Root et Test Sources Root — c'est l'état où le tutoriel de la théorie les laisse. Pour chaque question, respectez scrupuleusement le cycle TDD : le test d'abord, il doit échouer pour de bonnes raisons, puis le code minimum, puis le refactor éventuel.

### Supprimer une tâche

**Question 1** : Veuillez faire du TDD pour ces deux scénarios de test :

✏️ *A corriger au tableau*

1. `removeTask` — Supprimer une tâche de la liste : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
2. `removeUnexistingTask` — Supprimer une tâche de la liste : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération

### Renommer une tâche

**Question 2** : Nous voulons pouvoir renommer une tâche existante : `renameTask(String existingTask, String newTask)`. Veuillez faire du TDD pour ces scénarios :

1. `renameTask` — la tâche est renommée : l'ancien nom n'est plus contenu dans la liste, le nouveau l'est, on informe du succès de l'opération
2. `renameUnexistingTask` — on tente de renommer une tâche inexistante, on informe de l'échec de l'opération
3. `renameTaskToExistingTask` — on tente de renommer une tâche vers un nom déjà présent dans la liste, la tâche d'origine reste inchangée, on informe de l'échec de l'opération
4. `renameTaskToEmptyTask` — on tente de renommer une tâche vers un nom vide (constitué uniquement de caractères « blancs » ou nul), la tâche d'origine reste inchangée, on informe de l'échec de l'opération

### Terminer une tâche

**Question 3** : Nous voulons pouvoir marquer une tâche comme terminée (`completeTask`) et vérifier si une tâche est terminée (`isCompleted`). Une tâche terminée ne peut plus être renommée. Cette fois, avant d'écrire le moindre code, identifiez vous-même les scénarios de tests (cas positifs, cas négatifs, cas limites — appuyez-vous sur les vidéos), puis faites du TDD pour chacun d'eux.

Pensez notamment à ce qui doit se passer quand on tente de terminer une tâche inexistante, de terminer une tâche déjà terminée, ou de renommer une tâche terminée.

### À partir d'ici, faites du TDD avec l'IA

À partir de la question 4, aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour faire du TDD : respectez scrupuleusement le cycle red-green-refactor étape par étape — demandez d'abord le test seul, vérifiez qu'il échoue pour de bonnes raisons, puis demandez le code minimal, vérifiez qu'il passe, puis le refactor si besoin.

### Classes d'équivalence

**Question 4** : Pour l'argument `newTask` de `renameTask` (question 2), partitionnez les valeurs possibles en classes d'équivalence (par exemple : nom vide/blanc, nom valide et déjà présent dans la liste, nom valide et absent de la liste). Pour chaque classe, indiquez quel scénario de test de la question 2 la couvre déjà.

Faites de même pour l'argument `existingTask` de `removeTask` (question 1) : identifiez les classes d'équivalence de son domaine de valeurs, puis vérifiez qu'un scénario de test existe déjà pour chacune. S'il en manque un, ajoutez-le en TDD.

### Spécifier les tests

**Question 5** : Dans cet exercice, vous n'allez pas écrire de code mais compléter la liste de scénarios de tests ci-dessous pour spécifier des nouvelles UCs & des scénarios de tests.

Nous souhaitons faire évoluer l'application de gestion de tâches. Il doit être possible :

1. De créer des tâches en donnant ces informations : un titre (ne peut pas être vide ou null), une description (ne peut pas être nulle).
2. D'ajouter une tâche qui a un même titre au sein d'une TodoList. Cela revient à ajouter une tâche déjà présente, on informe de l'échec de l'opération.
3. De terminer une tâche.
4. De modifier le titre d'une tâche seulement si cette tâche n'est pas déjà terminée ; notons que le titre ne peut pas être vide ou nul…
5. De modifier la description d'une tâche seulement si cette tâche n'est pas déjà terminée ; notons que la description peut être vide.
6. De renvoyer une tâche qui se trouve au sein de la TodoList en donnant une tâche qui contiendrait son titre et sa description.
7. De modifier une TodoList en indiquant une tâche à modifier et une nouvelle tâche incluant les nouvelles données.

Veuillez mettre à jour les scénarios de test existants en identifiant les changements, et en listant les nouvelles UCs ci-dessous :

1. **(UC1) Ajouter une tâche à la liste :**
   1. `addTask` : la tâche est contenue dans la liste, on informe du succès de l'opération
   2. `addEmptyTask` : on tente d'ajouter une tâche vide (constituée uniquement de caractères « blancs » ou nulle), la tâche n'est pas contenue dans la liste, on informe de l'échec de l'opération
   3. `addExistingTask` : on tente d'ajouter une tâche déjà présente, on informe de l'échec de l'opération
2. **(UC2) Vérifier qu'une tâche est contenue dans la liste :**
   1. La tâche est présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
   2. La tâche n'est pas présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
3. **(UC3) Supprimer une tâche de la liste :**
   1. `removeTask` : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
   2. `removeUnexistingTask` : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération

### Compter et vider la liste

**Question 6** : Vous allez implémenter deux nouvelles fonctionnalités sur la `TodoList`.

1. **(UC4) Compter le nombre de tâches.** `countTasks` :
   1. `countTasksEmpty` : sur une liste vide, `countTasks` renvoie `0`.
   2. `countTasksAfterAdd` : après avoir ajouté 2 tâches, `countTasks` renvoie `2`.
   3. `countTasksAfterRemove` : après avoir supprimé une tâche, `countTasks` diminue de 1.
2. **(UC5) Vider la liste.** `clearTasks` :
   1. `clearTasks` : après avoir ajouté des tâches puis appelé `clearTasks`, la liste ne contient plus aucune des tâches ajoutées.
   2. `clearEmptyTasks` : appeler `clearTasks` sur une liste déjà vide ne lève pas d'erreur et la liste reste vide.

---

*Passez à la [théorie suivante](../02-partie2/04B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
