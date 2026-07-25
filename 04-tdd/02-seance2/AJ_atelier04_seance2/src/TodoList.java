import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Liste de tâches (de simples {@link String}) que l'on peut ajouter, supprimer, renommer,
 * marquer comme terminées, compter et vider.
 */
public class TodoList {

    private List<String> tasks = new ArrayList<>();

    private Set<String> completedTasks = new HashSet<>();

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

    // Question 1

    /**
     * Supprime une tâche de la liste.
     *
     * @param task tâche à supprimer
     * @return {@code true} si la tâche était présente et a été supprimée
     */
    public boolean removeTask(String task) {
        completedTasks.remove(task);
        return tasks.remove(task);
    }

    // Question 2

    /**
     * Renomme une tâche existante, sauf si elle est terminée ou si le nouveau nom est invalide ou déjà pris.
     *
     * @param existingTask tâche à renommer
     * @param newTask nouveau nom (ne peut être ni nul ni vide)
     * @return {@code true} si la tâche a été renommée, {@code false} sinon
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

    // Question 3

    /**
     * Marque une tâche existante comme terminée.
     *
     * @param task tâche à terminer
     * @return {@code true} si la tâche vient d'être terminée, {@code false} si elle est absente ou déjà terminée
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
     * @return {@code true} si la tâche est terminée
     */
    public boolean isCompleted(String task) {
        return completedTasks.contains(task);
    }

    // Question 6

    /**
     * @return le nombre de tâches contenues dans la liste
     */
    public int countTasks() {
        return tasks.size();
    }

    // Question 7

    /**
     * Vide la liste de toutes ses tâches.
     */
    public void clearTasks() {
        tasks.clear();
        completedTasks.clear();
    }
}
