import java.util.ArrayList;
import java.util.List;

/**
 * Liste de tâches (de simples {@link String}) que l'on peut ajouter et rechercher.
 */
public class TodoList {

    private List<String> tasks = new ArrayList<>();

    /**
     * Ajoute une tâche à la liste.
     *
     * @param task tâche à ajouter
     * @return {@code true} si la tâche a été ajoutée, {@code false} si elle est nulle, vide ou déjà présente
     */
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

    /**
     * Indique si la tâche est présente dans la liste.
     *
     * @param task tâche recherchée
     * @return {@code true} si la tâche est présente
     */
    public boolean containsTask(String task) {
        return tasks.contains(task);
    }
}
