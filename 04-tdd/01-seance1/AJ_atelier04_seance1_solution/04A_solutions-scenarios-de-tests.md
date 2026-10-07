# Scénarios de tests — Solution

## Question 4 : Classes d'équivalence

Pour l'argument `newTask` de `renameTask` :

| Classe d'équivalence | Scénario de test qui la couvre |
|---|---|
| Nom vide (nul ou constitué uniquement de caractères « blancs ») | `renameTaskToEmptyTask` |
| Nom valide et déjà présent dans la liste | `renameTaskToExistingTask` |
| Nom valide et absent de la liste | `renameTask` |

Pour l'argument de `removeTask` :

| Classe d'équivalence | Scénario de test qui la couvre |
|---|---|
| Tâche présente dans la liste | `removeTask` |
| Tâche absente de la liste | `removeUnexistingTask` |

Une tâche nulle ou vide ne constitue pas une classe à part : elle ne peut jamais avoir été ajoutée à la liste (voir `addEmptyTask`), elle appartient donc, du point de vue du code, à la classe « tâche absente de la liste ». Chaque classe est déjà couverte par un scénario existant : aucun scénario à ajouter.

## Question 5 : Scénarios de tests des UC existantes (UC1 à UC5)

1. **(UC1) Ajouter une tâche à la liste :**
   1. `addTask` : la tâche est contenue dans la liste, on informe du succès de l'opération
   2. `addNullTask` : on tente d'ajouter une tâche qui est nulle, la tâche n'est pas contenue dans la liste, on informe de l'échec de l'opération
   3. `addExistingTask` : on tente d'ajouter une tâche déjà présente (autre tâche avec même titre et même description), on informe de l'échec de l'opération
2. **(UC2) Vérifier qu'une tâche est contenue dans la liste :**
   1. La tâche est présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
   2. La tâche n'est pas présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
3. **(UC3) Supprimer une tâche de la liste :**
   1. `removeTask` : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
   2. `removeUnexistingTask` : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération
4. **(UC4) Mettre à jour le titre d'une tâche :**
   1. `updateTitle` : le titre est mis à jour, on informe du succès de l'opération
   2. `updateTitleWhenCompletedTask` : on tente de mettre à jour le titre alors que la tâche est déjà terminée, on informe de l'échec de l'opération
   3. `updateTitleToEmptyOrNullString` : on tente de mettre à jour le titre vers une string vide ou nulle, on informe de l'échec de l'opération
5. **(UC5) Terminer une tâche :**
   1. `completeTask` : la tâche est terminée, on informe du succès de l'opération
   2. `completeAlreadyCompletedTask` : on tente de terminer une tâche qui est déjà terminée, on informe de l'échec de l'opération

## Question 6 : Scénarios de tests des UC nouvelles (UC6 à UC9)

Points à vérifier en priorité dans la liste proposée par l'IA : la description peut être vide mais pas nulle (UC6, UC7) ; une tâche terminée ne peut plus être modifiée, y compris via la `TodoList` (UC7, UC9) ; `findTask` sur une tâche absente renvoie `null` (UC8) ; une tâche nulle peut être donnée de chaque côté de `updateTask` (UC9).

6. **(UC6) Créer une tâche :**
   1. `createTask` : en donnant un titre et une description, la tâche est créée
   2. `createNullFieldsTask` : on lance une exception si le titre ou la description est nulle
   3. `createEmptyTitleTask` : on lance une exception si le titre est vide (constitué uniquement de caractères « blancs »)
7. **(UC7) Mettre à jour la description d'une tâche :**
   1. `updateDescription` : la description est mise à jour, on informe du succès de l'opération
   2. `updateDescriptionWhenCompletedTask` : on tente de mettre à jour la description alors que la tâche est déjà terminée, on informe de l'échec de l'opération
   3. `updateDescriptionToNull` : on tente de mettre à jour la description vers une string nulle, on informe de l'échec de l'opération
8. **(UC8) Renvoyer une tâche se trouvant dans la TodoList :**
   1. `findTask` : après avoir ajouté une ou plusieurs tâche(s), on renvoie la tâche demandée
   2. `findUnexistingTask` : on demande de trouver une tâche qui n'existe pas et on retourne null
9. **(UC9) Mettre à jour la TodoList en indiquant la tâche à mettre à jour et les données à mettre à jour :**
   1. `updateTodoListTask` : la tâche est mise à jour, elle est contenue dans la liste et on informe du succès de l'opération
   2. `updateTodoListUnexistingTask` : on demande de modifier une tâche qui n'existe pas et on informe de l'échec de l'opération
   3. `updateTodoListWithNullTask` : la tâche n'est pas mise à jour car on donne une tâche nulle pour la nouvelle tâche ou pour la tâche à mettre à jour, on informe de l'échec de l'opération
   4. `updateTodoListWhenCompletedTask` : la tâche n'est pas mise à jour car on tente de mettre à jour une tâche qui est terminée, on informe de l'échec de l'opération
