# Atelier 4 : Test Driven Development (TDD) – séance 1 : solutions

*Énoncé : [`04A_2_exercices.md`](../AJ_atelier04_seance1/04A_2_exercices.md) — théorie : [`04A_1_theorie.md`](../AJ_atelier04_seance1/04A_1_theorie.md).*

## Supprimer une tâche

### Question 1

**Énoncé :** *(UC3 « Supprimer une tâche de la liste »)* : Veuillez faire du TDD pour ces deux scénarios de test :

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    // ...
    @Nested
    @DisplayName("Supprimer une tâche")
    class RemoveTask {

        @Test
        void removeTask() {
            todoList.addTask("task 1");
            assertAll(
                    () -> assertTrue(todoList.removeTask("task 1")),
                    () -> assertFalse(todoList.containsTask("task 1"))
            );
        }

        @Test
        void removeUnexistingTask() {
            todoList.addTask("task 1");
            assertFalse(todoList.removeTask("task 2"));
        }
    }
    // ...
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

> ⚠️ **Extrait montré dans son état à ce stade** : `completedTasks` n'existe pas encore, il arrive avec la Question 3. Le fichier lié, lui, porte l'état final, atteint à la **Question 3** — où il est repris.

```java
public class TodoList {
    // ...

    /**
     * Supprime une tâche de la liste.
     *
     * @param task tâche à supprimer
     * @return `true` si la tâche était présente et a été supprimée
     */
    public boolean removeTask(String task) {
        return tasks.remove(task);
    }
    // ...
}
```

## Renommer une tâche

### Question 2

**Énoncé :** *(UC « Renommer une tâche » — deviendra l'UC4 « Modifier le titre d'une tâche » après l'évolution de la question 5)* : Nous voulons pouvoir renommer une tâche existante : `renameTask(String existingTask, String newTask)`. Veuillez faire du TDD pour ces […]

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    // ...
    @Nested
    @DisplayName("Renommer une tâche")
    class RenameTask {

        @Test
        void renameTask() {
            todoList.addTask("task 1");
            assertAll(
                    () -> assertTrue(todoList.renameTask("task 1", "task one")),
                    () -> assertTrue(todoList.containsTask("task one")),
                    () -> assertFalse(todoList.containsTask("task 1"))
            );
        }

        @Test
        void renameUnexistingTask() {
            assertFalse(todoList.renameTask("task 1", "task one"));
        }

        @Test
        void renameTaskToExistingTask() {
            todoList.addTask("task 1");
            todoList.addTask("task 2");
            assertAll(
                    () -> assertFalse(todoList.renameTask("task 1", "task 2")),
                    () -> assertTrue(todoList.containsTask("task 1"))
            );
        }

        @Test
        void renameTaskToEmptyTask() {
            todoList.addTask("task 1");
            assertAll(
                    () -> assertFalse(todoList.renameTask("task 1", "")),
                    () -> assertFalse(todoList.renameTask("task 1", null)),
                    () -> assertTrue(todoList.containsTask("task 1"))
            );
        }
    }
    // ...
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

> ⚠️ **Extrait montré dans son état à ce stade** : `isCompleted` n'existe pas encore, il arrive avec la Question 3. Le fichier lié, lui, porte l'état final, atteint à la **Question 3** — où il est repris.

```java
public class TodoList {
    // ...

    /**
     * Renomme une tâche existante, sauf si le nouveau nom est invalide ou déjà pris.
     *
     * @param existingTask tâche à renommer
     * @param newTask nouveau nom (ne peut être ni nul ni vide)
     * @return `true` si la tâche a été renommée, `false` sinon
     */
    public boolean renameTask(String existingTask, String newTask) {
        if (newTask == null || newTask.isBlank()) {
            return false;
        }
        if (!containsTask(existingTask)) {
            return false;
        }
        if (containsTask(newTask)) {
            return false;
        }

        tasks.set(tasks.indexOf(existingTask), newTask);
        return true;
    }
    // ...
}
```

## Terminer une tâche

### Question 3

**Énoncé :** *(UC « Terminer une tâche » — deviendra l'UC5 après l'évolution de la question 5)* :

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    // ...
    @Nested
    @DisplayName("Terminer une tâche")
    class CompleteTask {

        @Test
        void renameCompletedTask() {
            todoList.addTask("task 1");
            todoList.completeTask("task 1");
            assertAll(
                    () -> assertFalse(todoList.renameTask("task 1", "task one")),
                    () -> assertTrue(todoList.containsTask("task 1"))
            );
        }

        @Test
        void completeTask() {
            todoList.addTask("task 1");
            assertAll(
                    () -> assertTrue(todoList.completeTask("task 1")),
                    () -> assertTrue(todoList.isCompleted("task 1"))
            );
        }

        @Test
        void completeUnexistingTask() {
            assertFalse(todoList.completeTask("task 1"));
        }

        @Test
        void completeAlreadyCompletedTask() {
            todoList.addTask("task 1");
            todoList.completeTask("task 1");
            assertFalse(todoList.completeTask("task 1"));
        }

        @Test
        void removedTaskIsNoLongerCompleted() {
            todoList.addTask("task 1");
            todoList.completeTask("task 1");
            todoList.removeTask("task 1");
            todoList.addTask("task 1");
            assertFalse(todoList.isCompleted("task 1"));
        }
    }
    // ...
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * Marque une tâche existante comme terminée.
     *
     * @param task tâche à terminer
     * @return `true` si la tâche vient d'être terminée, `false` si elle est absente ou déjà terminée
     */
    public boolean completeTask(String task) {
        if (!containsTask(task)) {
            return false;
        }

        return completedTasks.add(task);
    }

    /**
     * Indique si une tâche est terminée.
     *
     * @param task tâche testée
     * @return `true` si la tâche est terminée
     */
    public boolean isCompleted(String task) {
        return completedTasks.contains(task);
    }
    // ...
}
```

*une tâche supprimée n'est plus terminée (`removedTaskIsNoLongerCompleted`)* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * Supprime une tâche de la liste.
     *
     * @param task tâche à supprimer
     * @return `true` si la tâche était présente et a été supprimée
     */
    public boolean removeTask(String task) {
        completedTasks.remove(task);
        return tasks.remove(task);
    }
    // ...
}
```

*une tâche terminée ne peut plus être renommée (`renameCompletedTask`)* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * Renomme une tâche existante, sauf si elle est terminée ou si le nouveau nom est invalide ou déjà pris.
     *
     * @param existingTask tâche à renommer
     * @param newTask nouveau nom (ne peut être ni nul ni vide)
     * @return `true` si la tâche a été renommée, `false` sinon
     */
    public boolean renameTask(String existingTask, String newTask) {
        if (newTask == null || newTask.isBlank()) {
            return false;
        }
        if (!containsTask(existingTask)) {
            return false;
        }
        if (containsTask(newTask)) {
            return false;
        }
        if (isCompleted(existingTask)) {
            return false;
        }

        tasks.set(tasks.indexOf(existingTask), newTask);
        return true;
    }
    // ...
}
```

## Classes d'équivalence

### Question 4

**Énoncé :** *(pas une UC — technique de test appliquée aux UC déjà identifiées)* :

**Classes d'équivalence**

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

## Spécifier les tests

### Question 5

**Énoncé :** Dans cet exercice et le suivant, vous n'écrivez pas de code : vous complétez une spécification, sous la forme d'une liste de scénarios de tests regroupés par UC (cas d'utilisation).

**Scénarios de tests des UC existantes (UC1 à UC5)**

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

## Spécifier les UC nouvelles

### Question 6

**Énoncé :** L'évolution de l'application comporte aussi des fonctionnalités nouvelles. Il doit être possible :

**Scénarios de tests des UC nouvelles (UC6 à UC9)**

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

## Compter les tâches

### Question 7

**Énoncé :** *(UC10)* :

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    // ...
    @Nested
    @DisplayName("Compter les tâches")
    class CountTasks {

        @Test
        void countTasksEmpty() {
            assertEquals(0, todoList.countTasks());
        }

        @Test
        void countTasksAfterAdd() {
            todoList.addTask("task 1");
            todoList.addTask("task 2");
            assertEquals(2, todoList.countTasks());
        }

        @Test
        void countTasksAfterRemove() {
            todoList.addTask("task 1");
            todoList.addTask("task 2");
            todoList.removeTask("task 1");
            assertEquals(1, todoList.countTasks());
        }
    }
    // ...
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * @return le nombre de tâches contenues dans la liste
     */
    public int countTasks() {
        return tasks.size();
    }
    // ...
}
```

## Vider la liste

### Question 8

**Énoncé :** *(UC11)* :

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    // ...
    @Nested
    @DisplayName("Vider la liste")
    class ClearTasks {

        @Test
        void clearTasks() {
            todoList.addTask("task 1");
            todoList.addTask("task 2");
            todoList.clearTasks();
            assertAll(
                    () -> assertFalse(todoList.containsTask("task 1")),
                    () -> assertFalse(todoList.containsTask("task 2")),
                    () -> assertEquals(0, todoList.countTasks())
            );
        }

        @Test
        void clearEmptyTasks() {
            assertDoesNotThrow(() -> todoList.clearTasks());
            assertEquals(0, todoList.countTasks());
        }
    }
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * Vide la liste de toutes ses tâches.
     */
    public void clearTasks() {
        tasks.clear();
        completedTasks.clear();
    }
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
