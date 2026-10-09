import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TaskTest {

    private Task task1;

    @BeforeEach
    void setUp() {
        task1 = new Task("title1", "description1");
    }

    // Question 2
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

    // Question 2 : terminer, modifier le titre et la description
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
