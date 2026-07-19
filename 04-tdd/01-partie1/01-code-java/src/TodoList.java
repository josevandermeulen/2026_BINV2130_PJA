import java.util.ArrayList;
import java.util.List;

public class TodoList {

  private List<String> tasks = new ArrayList<>();

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
}
