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
}
