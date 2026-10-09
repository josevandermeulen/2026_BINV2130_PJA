import java.util.Objects;

// Question 2 : la classe Task — déclaration, attributs et constructeur avec validations
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
    // FinQuestion

    /**
     * @return le titre de la tâche
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return la description de la tâche
     */
    public String getDescription() {
        return description;
    }

    // Question 1 : equals et hashCode — ce qui fait passer addExistingTask et removeClonedTask
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

    // Question 2 : complete et updateTitle (updateDescription est analogue, description vide admise)
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
    // FinQuestion

    /**
     * Met à jour la description de la tâche, sauf si elle est terminée.
     *
     * @param newDescription nouvelle description (ne peut être nulle, mais peut être vide)
     * @return `true` si la description a été mise à jour, `false` sinon
     */
    public boolean updateDescription(String newDescription) {
        if (newDescription == null) {
            return false;
        }
        if (completed) {
            return false;
        }

        description = newDescription;
        return true;
    }

    /**
     * @return `true` si la tâche est terminée
     */
    public boolean isCompleted() {
        return completed;
    }
}
