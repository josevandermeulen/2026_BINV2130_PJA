# Atelier 4 : Test Driven Development (TDD) – séance 2 : solutions

*Énoncé : [`04B_2_exercices.md`](../AJ_atelier04_seance2/04B_2_exercices.md) — théorie : [`04B_1_theorie.md`](../AJ_atelier04_seance2/04B_1_theorie.md).*

## TDD lors de la mise à jour de fonctionnalités existantes

### Question 1

**Énoncé :** Veuillez faire du TDD pour les scénarios que vous avez dû modifier pour prendre en compte le fait qu'une tâche ne doit maintenant plus être juste une `String` : une tâche est maintenant un objet avec un titre et une description.

**la classe Task, créée à cette question**

Le projet de départ ne contient pas de classe `Task` : c'est à cette question qu'on la crée, avec
un constructeur qui reçoit le titre et la description — ses validations (titre ni nul ni vide,
description non nulle) viennent en TDD à la Question 2, où le constructeur est montré.

Ce qui fait passer les tests de cette question, ce sont `equals` et `hashCode` : `TodoList`
s'appuie sur `contains` et `remove` de sa liste, qui comparent avec `equals`. Sans eux, la
comparaison se fait sur les références, et `addExistingTask` comme `removeClonedTask` (une autre
instance, de même titre et de même description) échouent.

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    private TodoList todoListWithTask1Task2;
    private Task task1;
    private Task task2;
    // ...
    @BeforeEach
    void setUp() {
        todoList = new TodoList();
        task1 = new Task("title 1", "description task 1");
        task2 = new Task("title 2", "description task 2");
        todoListWithTask1Task2 = new TodoList();
        todoListWithTask1Task2.addTask(task1);
        todoListWithTask1Task2.addTask(task2);
    }

    @Nested
    @DisplayName("Ajouter une tâche")
    class AddTask {

        @Test
        void addTask() {
            assertAll(() -> assertTrue(todoList.addTask(task1)),
                      () -> assertTrue(todoList.containsTask(task1)));
        }

        @Test
        void addNullTask() {
            Task nullTask = null;
            assertAll(() -> assertFalse(todoList.addTask(nullTask)),
                      () -> assertFalse(todoList.containsTask(nullTask)));
        }

        @Test
        void addExistingTask() {
            todoList.addTask(task1);
            Task task1Bis = new Task("title 1", "description task 1");
            assertFalse(todoList.addTask(task1Bis));
        }
    }

    @Nested
    @DisplayName("Supprimer une tâche")
    class RemoveTask {

        @Test
        void removeTask() {
            todoList.addTask(task1);
            assertAll(() -> assertTrue(todoList.removeTask(task1)),
                      () -> assertFalse(todoList.containsTask(task1)));
        }

        @Test
        void removeUnexistingTask() {
            todoList.addTask(task1);
            assertFalse(todoList.removeTask(task2));
        }

        @Test
        void removeClonedTask() {
            todoList.addTask(task1);
            Task task1Cloned = new Task("title 1", "description task 1");

            assertAll(() -> assertTrue(todoList.removeTask(task1Cloned)),
                      () -> assertFalse(todoList.containsTask(task1)));
        }
    }
    // ...
}
```

*equals et hashCode — ce qui fait passer addExistingTask et removeClonedTask* — [`src/Task.java`](src/Task.java) :

```java
public class Task {
    private String title;
    private String description;
    // ...
    /**
     * Compare deux tâches sur leur titre et leur description.
     *
     * @param o l'objet à comparer à cette tâche
     * @return true si o est une tâche de même titre et de même description
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Task task = (Task) o;

        if (!Objects.equals(title, task.title)) {
            return false;
        }
        return Objects.equals(description, task.description);
    }

    /**
     * Renvoie un code de hachage calculé sur le titre et la description, cohérent avec
     * equals(Object).
     *
     * @return le code de hachage de la tâche
     */
    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
    // ...
}
```

*Implémentation* — [`src/TodoList.java`](src/TodoList.java) :

```java
public class TodoList {
    // ...

    /**
     * Ajoute une tâche à la liste.
     *
     * @param task tâche à ajouter
     * @return `true` si la tâche a été ajoutée, `false` si elle est nulle ou déjà présente
     */
    public boolean addTask(Task task) {
        if (task == null) {
            return false;
        }
        if (containsTask(task)) {
            return false;
        }

        return tasks.add(task);
    }

    /**
     * Indique si la tâche est présente dans la liste.
     *
     * @param task tâche recherchée
     * @return `true` si la tâche est présente
     */
    public boolean containsTask(Task task) {
        return tasks.contains(task);
    }

    /**
     * Supprime une tâche de la liste.
     *
     * @param task tâche à supprimer
     * @return `true` si la tâche était présente et a été supprimée
     */
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }
    // ...
}
```

## TDD pour les nouvelles fonctionnalités de la classe `Task`

### Question 2

**Énoncé :** Veuillez faire du TDD pour les scénarios ci-dessous. Vous aurez besoin d'une nouvelle classe `TaskTest`.

*Test* — [`test/TaskTest.java`](test/TaskTest.java) :

```java
class TaskTest {
    private Task task1;
    // ...
    @Nested
    @DisplayName("Créer une tâche")
    class CreateTask {

        @Test
        void createTask() {
            assertAll(() -> assertEquals("title1", task1.getTitle()),
                      () -> assertEquals("description1", task1.getDescription()));
        }

        @Test
        void createNullFieldsTask() {
            assertAll(() -> assertThrows(IllegalArgumentException.class,
                            () -> new Task(null, "description1"))
                    , () -> assertThrows(IllegalArgumentException.class,
                            () -> new Task("title1", null)
                    ));
        }

        @Test
        void createEmptyTitleTask() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Task(" ", "description1"));
        }

        @Test
        void createEmptyDescriptionTask() {
            Task task = new Task("title1", "");
            assertEquals("", task.getDescription());
        }
    }
    // ...
}
```

*terminer, modifier le titre et la description* — [`test/TaskTest.java`](test/TaskTest.java) :

```java
class TaskTest {
    private Task task1;
    // ...
    @Nested
    @DisplayName("Terminer une tâche")
    class CompleteTask {

        @Test
        void completeTask() {
            assertTrue(task1.complete());
            assertTrue(task1.isCompleted());
        }

        @Test
        void completeAlreadyCompletedTask() {
            task1.complete();
            assertFalse(task1.complete());
        }
    }

    @Nested
    @DisplayName("Modifier le titre")
    class UpdateTitle {

        @Test
        void updateTitle() {
            assertAll(() -> assertTrue(task1.updateTitle("title one")),
                      () -> assertEquals("title one", task1.getTitle()));
        }

        @Test
        void updateTitleWhenCompletedTask() {
            task1.complete();
            assertAll(() -> assertFalse(task1.updateTitle("title one")),
                      () -> assertEquals("title1", task1.getTitle()));
        }

        @Test
        void updateTitleToEmptyOrNullString() {
            assertAll(() -> assertFalse(task1.updateTitle("")),
                      () -> assertFalse(task1.updateTitle(null)));
        }
    }

    @Nested
    @DisplayName("Modifier la description")
    class UpdateDescription {

        @Test
        void updateDescription() {
            assertAll(() -> assertTrue(task1.updateDescription("description one")),
                      () -> assertEquals("description one", task1.getDescription()));
        }

        @Test
        void updateDescriptionWhenCompletedTask() {
            task1.complete();
            assertAll(() -> assertFalse(task1.updateDescription("title one")),
                      () -> assertEquals("description1", task1.getDescription()));
        }

        @Test
        void updateDescriptionToEmpty() {
            assertAll(() -> assertTrue(task1.updateDescription("")),
                      () -> assertEquals("", task1.getDescription()));
        }

        @Test
        void updateDescriptionToNull() {
            assertFalse(task1.updateDescription(null));
        }
    }
}
```

*la classe Task — déclaration, attributs et constructeur avec validations* — [`src/Task.java`](src/Task.java) :

```java
/**
 * Représente une tâche caractérisée par un titre et une description, et qui peut être terminée.
 */
public class Task {

    private String title;

    private String description;

    private boolean completed;

    /**
     * Crée une tâche.
     *
     * @param title titre de la tâche (ne peut être ni nul ni vide)
     * @param description description de la tâche (ne peut être nulle, mais peut être vide)
     * @throws IllegalArgumentException si le titre est nul ou vide, ou si la description est nulle
     */
    public Task(String title, String description) {
        if (title == null) {
            throw new IllegalArgumentException("title cannot be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("description cannot be null");
        }
        if (title.isBlank()) {
            throw new IllegalArgumentException("title cannot be empty");
        }

        this.title = title;
        this.description = description;
    }
    // ...
}
```

*complete et updateTitle (updateDescription est analogue, description vide admise)* — [`src/Task.java`](src/Task.java) :

```java
public class Task {
    private String title;
    private boolean completed;
    // ...
    /**
     * Marque la tâche comme terminée.
     *
     * @return `true` si la tâche vient d'être terminée, `false` si elle l'était déjà
     */
    public boolean complete() {
        if (completed) {
            return false;
        }

        completed = true;
        return true;
    }

    /**
     * Met à jour le titre de la tâche, sauf si elle est terminée.
     *
     * @param newTitle nouveau titre (ne peut être ni nul ni vide)
     * @return `true` si le titre a été mis à jour, `false` sinon
     */
    public boolean updateTitle(String newTitle) {
        if (completed) {
            return false;
        }
        if (newTitle == null || newTitle.isBlank()) {
            return false;
        }

        title = newTitle;
        return true;
    }
    // ...
}
```

## TDD pour les nouvelles fonctionnalités de la classe `TodoList`

### Question 3

**Énoncé :** Il est temps de s'occuper de l'opération permettant de retrouver une tâche au sein de la `TodoList` (`findTask`). Veuillez faire du TDD pour ces scénarios au sein de la classe `TodoListTest`.

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoListWithTask1Task2;
    private Task task1;
    // ...
    @Nested
    @DisplayName("Rechercher une tâche")
    class FindTask {

        @Test
        void findTask() {
            assertEquals(task1, todoListWithTask1Task2.findTask(task1));
        }

        @Test
        void findUnexistingTask() {
            Task task3 = new Task("title 3", "description task 3");

            assertNull(todoListWithTask1Task2.findTask(task3));
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
     * Retrouve dans la liste la tâche égale à celle fournie.
     *
     * @param task1 tâche recherchée (titre et description)
     * @return la tâche présente dans la liste, ou `null` si aucune ne correspond
     */
    public Task findTask(Task task1) {
        for (Task task : tasks) {
            if (task.equals(task1)) {
                return task;
            }
        }
        return null;
    }
    // ...
}
```

### Question 4

**Énoncé :** Il devrait aussi être possible de modifier une tâche par le biais de la `TodoList` (`updateTask`), en indiquant tant la tâche que l'on souhaite mettre à jour que les nouvelles données de cette tâche. Veuillez faire du TDD pour ces scénarios au sein de la […]

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoListWithTask1Task2;
    private Task task1;
    private Task task2;
    // ...
    @Nested
    @DisplayName("Mettre à jour une tâche")
    class UpdateTask {

        @Test
        void updateTodoListTask() {
            Task task1Clone = new Task("title 1", "description task 1");

            Task taskOne = new Task("title one", "description task one");

            assertAll(() -> assertTrue(todoListWithTask1Task2.updateTask(task1, taskOne)),
                      () -> assertTrue(todoListWithTask1Task2.containsTask(taskOne)),
                      () -> assertFalse(todoListWithTask1Task2.containsTask(task1Clone)));
        }

        @Test
        void updateTodoListUnexistingTask() {
            Task unexistingTask = new Task("title 4", "description task 4");

            Task taskOne = new Task("title one", "description task one");

            assertAll(() -> assertFalse(todoListWithTask1Task2.updateTask(unexistingTask, taskOne)),
                      () -> assertFalse(todoListWithTask1Task2.containsTask(unexistingTask)));
        }

        @Test
        void updateTodoListWithNullTask() {
            Task nullTask = null;

            assertAll(() -> assertFalse(todoListWithTask1Task2.updateTask(task2, nullTask)),
                      () -> assertFalse(todoListWithTask1Task2.updateTask(nullTask, task2)));
        }

        @Test
        void updateTodoListWhenCompletedTask() {
            Task completedTask = new Task("title", "description task");
            TodoList todoListWithCompletedTask = new TodoList();
            todoListWithCompletedTask.addTask(completedTask);
            completedTask.complete();

            Task updatedTask = new Task("title updated", "description task updated");

            assertAll(() -> assertFalse(todoListWithCompletedTask.updateTask(completedTask, updatedTask)),
                      () -> assertFalse(todoListWithCompletedTask.containsTask(updatedTask)),
                      () -> assertTrue(todoListWithCompletedTask.containsTask(completedTask)));
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
     * Met à jour une tâche de la liste avec les données d'une nouvelle tâche.
     *
     * @param existingTask tâche à mettre à jour
     * @param updatedTask tâche portant les nouvelles données
     * @return `true` si la mise à jour a réussi, `false` si un argument est nul,
     *         si la tâche est absente, ou si elle est terminée
     */
    public boolean updateTask(Task existingTask, Task updatedTask) {
        if (existingTask == null || updatedTask == null) {
            return false;
        }

        var taskFound = findTask(existingTask);
        if (taskFound == null) {
            return false;
        }
        if (!taskFound.updateTitle(updatedTask.getTitle())) {
            return false;
        }
        if (!taskFound.updateDescription(updatedTask.getDescription())) {
            return false;
        }

        return true;
    }
    // ...
}
```

## Compter et vider la liste

### Question 5

**Énoncé :** Reprenez les scénarios `countTasksEmpty`, `countTasksAfterAdd` et `countTasksAfterRemove` de la séance 1 et adaptez-les pour qu'ils utilisent des `Task` plutôt que de simples `String`.

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    private TodoList todoListWithTask1Task2;
    private Task task1;
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
            assertEquals(2, todoListWithTask1Task2.countTasks());
        }

        @Test
        void countTasksAfterRemove() {
            todoListWithTask1Task2.removeTask(task1);
            assertEquals(1, todoListWithTask1Task2.countTasks());
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

### Question 6

**Énoncé :** Faites de même pour `clearTasks` : adaptez `clearTasks` et `clearEmptyTasks` aux objets `Task`.

*Test* — [`test/TodoListTest.java`](test/TodoListTest.java) :

```java
public class TodoListTest {
    private TodoList todoList;
    private TodoList todoListWithTask1Task2;
    private Task task1;
    private Task task2;
    // ...
    @Nested
    @DisplayName("Vider la liste")
    class ClearTasks {

        @Test
        void clearTasks() {
            todoListWithTask1Task2.clearTasks();
            assertAll(
                    () -> assertFalse(todoListWithTask1Task2.containsTask(task1)),
                    () -> assertFalse(todoListWithTask1Task2.containsTask(task2)),
                    () -> assertEquals(0, todoListWithTask1Task2.countTasks())
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
    }
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
