import java.util.ArrayList;
import java.util.List;

/**
 * Liste de tâches (Task) que l'on peut ajouter, retrouver, mettre à jour, compter et vider.
 */
public class TodoList {

    private List<Task> tasks = new ArrayList<>();

    // Question 1

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

    // Question 3

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

    // Question 4

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

    // Question 5

    /**
     * @return le nombre de tâches contenues dans la liste
     */
    public int countTasks() {
        return tasks.size();
    }

    // Question 6

    /**
     * Vide la liste de toutes ses tâches.
     */
    public void clearTasks() {
        tasks.clear();
    }
}
