# Atelier 4 : Test Driven Development (TDD) – séance 1

## Objectif

Découvrir le Test Driven Development en pratiquant le cycle *red-green-refactor* sur une application de gestion de tâches, puis apprendre à spécifier des scénarios de tests avant toute implémentation.

## Concepts

1. Le cycle TDD : écrire un test qui échoue, écrire le code minimum pour le faire passer, refactorer
2. Spécifier des scénarios de tests à partir des cas d'utilisation (UC)
3. Cas positifs, cas négatifs, cas limites et classes d'équivalence
4. Organisation des tests avec `@Nested` (vu à l'atelier 3 séance 2)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Exercices

### Introduction

Nous développons une application de gestion de tâches : une [`TodoList`](src/TodoList.java) contient des tâches (de simples `String` pour l'instant) qu'on peut ajouter et rechercher. La théorie (introduction au TDD, spécification des tests, tutoriel complet d'implémentation, résumé) se trouve dans [`04A_1_theorie.md`](04A_1_theorie.md). L'état du projet à la fin du tutoriel vous est fourni dans `AJ_atelier04_seance1/`.

### Consignes

Assurez-vous d'abord d'avoir lu l'intégralité de la théorie ([`04A_1_theorie.md`](04A_1_theorie.md)) et visionné les vidéos ci-dessus. Dans IntelliJ, ouvrez le dossier `AJ_atelier04_seance1` (File → Open…). Il contient déjà `TodoList.java` dans `src` (package par défaut) et `TodoListTest.java` dans `test` — c'est l'état où le tutoriel de la théorie les laisse. Vérifiez que `src` et `test` sont marqués respectivement Sources Root et Test Sources Root, et que JUnit 5 est ajouté au projet. Pour chaque question, respectez scrupuleusement le cycle TDD : le test d'abord, il doit échouer pour de bonnes raisons, puis le code minimum, puis le refactor éventuel.

Le projet tel que le tutoriel de la théorie le laisse fait l'objet d'une remise cotée, à déposer sur mooVin avant la séance sous la forme d'une archive zip, dont la structure est décrite dans [`04_zip-a-rendre.md`](04_zip-a-rendre.md). Les exercices ci-dessous n'en font pas partie.

### Supprimer une tâche

**Question 1** *(UC3 « Supprimer une tâche de la liste »)* : Veuillez faire du TDD pour ces deux scénarios de test :

✏️ *A corriger au tableau*

1. `removeTask` — Supprimer une tâche de la liste : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
2. `removeUnexistingTask` — Supprimer une tâche de la liste : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération

### Renommer une tâche

**Question 2** *(UC « Renommer une tâche » — deviendra l'UC4 « Modifier le titre d'une tâche » après l'évolution de la question 5)* : Nous voulons pouvoir renommer une tâche existante : `renameTask(String existingTask, String newTask)`. Veuillez faire du TDD pour ces scénarios :

1. `renameTask` — la tâche est renommée : l'ancien nom n'est plus contenu dans la liste, le nouveau l'est, on informe du succès de l'opération
2. `renameUnexistingTask` — on tente de renommer une tâche inexistante, on informe de l'échec de l'opération
3. `renameTaskToExistingTask` — on tente de renommer une tâche vers un nom déjà présent dans la liste, la tâche d'origine reste inchangée, on informe de l'échec de l'opération
4. `renameTaskToEmptyTask` — on tente de renommer une tâche vers un nom vide (constitué uniquement de caractères « blancs » ou nul), la tâche d'origine reste inchangée, on informe de l'échec de l'opération

### Terminer une tâche

**Question 3** *(UC « Terminer une tâche » — deviendra l'UC5 après l'évolution de la question 5)* :

Nous voulons pouvoir marquer une tâche comme terminée (`completeTask`) et vérifier si une tâche est terminée (`isCompleted`). Une tâche terminée ne peut plus être renommée. Cette fois, avant d'écrire le moindre code, identifiez vous-même les scénarios de tests (cas positifs, cas négatifs, cas limites — appuyez-vous sur les vidéos), puis faites du TDD pour chacun d'eux.

Pensez notamment à ce qui doit se passer quand on tente de terminer une tâche inexistante, de terminer une tâche déjà terminée, ou de renommer une tâche terminée.

Une fois vos scénarios écrits et vos tests au vert, demandez à un assistant IA quels scénarios de tests il proposerait pour `completeTask` et `isCompleted`. Comparez avec les vôtres : en a-t-il trouvé un que vous aviez oublié ? En propose-t-il un inutile, ou dont le comportement attendu est faux ?

### Classes d'équivalence

**Question 4** *(pas une UC — technique de test appliquée aux UC déjà identifiées)* :

Pour l'argument `newTask` de `renameTask` (question 2), partitionnez les valeurs possibles en classes d'équivalence (par exemple : nom vide/blanc, nom valide et déjà présent dans la liste, nom valide et absent de la liste). Pour chaque classe, indiquez quel scénario de test de la question 2 la couvre déjà.

Faites de même pour l'argument de `removeTask` (question 1) : identifiez les classes d'équivalence de son domaine de valeurs, puis vérifiez qu'un scénario de test existe déjà pour chacune. S'il en manque un, ajoutez-le en TDD.

### Spécifier les tests

**Question 5** :

Dans cet exercice et le suivant, vous n'écrivez pas de code : vous complétez une spécification, sous la forme d'une liste de scénarios de tests regroupés par UC (cas d'utilisation).

Nous souhaitons faire évoluer l'application de gestion de tâches. Les tâches ne sont plus de simples `String` : une tâche a désormais un titre et une description (sa création est spécifiée à la question 6). Les règles qui suivent font évoluer des UC existantes ; il doit être possible :

1. De refuser d'ajouter une tâche qui a le même titre et la même description qu'une tâche de la TodoList. Cela revient à ajouter une tâche déjà présente, on informe de l'échec de l'opération. *(fait évoluer l'UC1 « Ajouter une tâche à la liste » ci-dessous)*
2. De terminer une tâche. *(fait évoluer l'UC identifiée à la question 3)*
3. De modifier le titre d'une tâche seulement si cette tâche n'est pas déjà terminée ; notons que le titre ne peut pas être vide ou nul… *(fait évoluer l'UC « renommer une tâche » identifiée à la question 2)*

Voici les trois premières UC telles qu'elles existaient avant cette évolution — elles montrent le format attendu, mais ne sont pas nécessairement encore correctes :

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

Mettez à jour la spécification des UC existantes : corrigez UC1 à UC3, puis ajoutez l'UC4 « modifier le titre d'une tâche » (évolution de la question 2) et l'UC5 « terminer une tâche » (question 3), en les adaptant aux nouvelles règles. Reprenez chaque scénario existant un par un : il peut rester inchangé, être renommé, voir son comportement attendu modifié, ou devenir obsolète si la vérification qu'il couvrait se fait désormais ailleurs. Quelques pistes :

- **UC1** : un titre vide est désormais refusé dès la création de la tâche (question 6). Que reste-t-il à vérifier dans `addTask` ?
- **UC4 et UC5** : on modifie le titre et on termine une tâche sur la tâche elle-même, et non plus en passant par la `TodoList`. Parmi les scénarios des questions 2 et 3, lesquels gardent un sens ?

### 🤖 À partir d'ici, travaillez avec l'IA

À partir de la question 6, aidez-vous d'un assistant IA (Claude Code, Copilot, …). Pour le TDD (questions 7 et 8), respectez scrupuleusement le cycle red-green-refactor étape par étape — demandez d'abord le test seul, vérifiez qu'il échoue pour de bonnes raisons, puis demandez le code minimal, vérifiez qu'il passe, puis le refactor si besoin.

### Spécifier les UC nouvelles

**Question 6** :

L'évolution de l'application comporte aussi des fonctionnalités nouvelles. Il doit être possible :

1. De créer des tâches en donnant ces informations : un titre (ne peut pas être vide ou null), une description (ne peut pas être nulle).
2. De modifier la description d'une tâche seulement si cette tâche n'est pas déjà terminée ; notons que la description peut être vide. *(à rapprocher du point 3 de la question 5)*
3. De renvoyer une tâche qui se trouve au sein de la TodoList en donnant une tâche qui contiendrait son titre et sa description.
4. De modifier une TodoList en indiquant une tâche à modifier et une nouvelle tâche incluant les nouvelles données.

Spécifiez ces UC nouvelles, numérotées UC6 à UC9 dans l'ordre des points ci-dessus, dans le même format. Donnez à l'assistant IA ces quatre points et votre spécification de la question 5 comme exemple de format, et demandez-lui la liste des scénarios de tests. Relisez ensuite chaque scénario proposé en le confrontant à l'énoncé — en particulier ce que l'énoncé dit des valeurs vides et nulles, des tâches terminées et des tâches absentes de la liste —, et vérifiez qu'aucune règle n'est restée sans scénario. Corrigez la liste obtenue, en marquant d'une courte note chaque scénario que vous avez dû corriger, ajouter ou supprimer.

### Compter les tâches

**Question 7** *(UC10)* :

Implémentez `countTasks`, qui renvoie le nombre de tâches de la `TodoList`. Faites du TDD pour ces scénarios :

1. `countTasksEmpty` : sur une liste vide, `countTasks` renvoie `0`.
2. `countTasksAfterAdd` : après avoir ajouté 2 tâches, `countTasks` renvoie `2`.
3. `countTasksAfterRemove` : après avoir supprimé une tâche, `countTasks` diminue de 1.

### Vider la liste

**Question 8** *(UC11)* :

Implémentez `clearTasks`, qui vide la `TodoList`. Faites du TDD pour ces scénarios :

1. `clearTasks` : après avoir ajouté des tâches puis appelé `clearTasks`, la liste ne contient plus aucune des tâches ajoutées.
2. `clearEmptyTasks` : appeler `clearTasks` sur une liste déjà vide ne lève pas d'erreur et la liste reste vide.

---

*Passez à la [théorie suivante](../../02-seance2/AJ_atelier04_seance2/04B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
