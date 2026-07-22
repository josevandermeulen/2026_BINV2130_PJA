import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TodoListTest {

    private TodoList todoList;

    @BeforeEach
    void setUp() {
        todoList = new TodoList();
    }

    @Nested
    @DisplayName("Ajouter une tâche")
    class AddTask {

        @Test
        void addTask() {
            assertAll(
                    () -> assertTrue(todoList.addTask("task 1")),
                    () -> assertTrue(todoList.containsTask("task 1"))
            );
        }

        @Test
        void addEmptyTask() {
            assertAll(
                    () -> assertFalse(todoList.addTask("")),
                    () -> assertFalse(todoList.containsTask("")),
                    () -> assertFalse(todoList.addTask(null)),
                    () -> assertFalse(todoList.containsTask(null))
            );
        }

        @Test
        void addExistingTask() {
            todoList.addTask("task 1");
            assertFalse(todoList.addTask("task 1"));
        }
    }

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
