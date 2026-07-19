import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TodoList {

  private List<String> tasks = new ArrayList<>();
  private Set<String> completedTasks = new HashSet<>();

  public boolean addTask(String task) {
    if (task == null) {
      return false;
    }
    if (task.isBlank()) {
      return false;
    }
    if (containsTask(task)) {
      return false;
    }
    return tasks.add(task);
  }

  public boolean containsTask(String task) {
    return tasks.contains(task);
  }

  // Question 1

  public boolean removeTask(String task) {
    completedTasks.remove(task);
    return tasks.remove(task);
  }

  // Question 2

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

  // Question 3

  public boolean completeTask(String task) {
    if (!containsTask(task)) {
      return false;
    }
    return completedTasks.add(task);
  }

  public boolean isCompleted(String task) {
    return completedTasks.contains(task);
  }

  // Question 6

  public int countTasks() {
    return tasks.size();
  }

  public void clearTasks() {
    tasks.clear();
    completedTasks.clear();
  }
}
