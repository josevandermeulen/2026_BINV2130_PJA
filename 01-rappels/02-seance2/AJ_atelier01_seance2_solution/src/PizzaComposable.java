import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

/**
 * Une pizza composée par un client, qui choisit lui-même ses ingrédients.
 *
 * Contrairement à une PizzaComposee, elle part sans aucun ingrédient et reste modifiable.
 * Elle retient son créateur et l'instant de sa création, tous deux repris dans son titre et sa
 * description.
 */
public class PizzaComposable extends Pizza {
    private LocalDateTime date;

    private Client createur;

    /**
     * Crée une pizza composable vide, au nom de son créateur et datée de l'instant présent.
     *
     * @param createur le client qui compose la pizza
     * @throws IllegalArgumentException si le créateur est null
     */
    public PizzaComposable(Client createur) {
        // createur est validé avant l'appel à super(), qui doit rester la
        // première instruction : sans ce contrôle, un createur null lèverait
        // une NullPointerException au lieu de l'IllegalArgumentException attendue.
        super("Pizza composable du client " + checkCreateur(createur).getNumero(),
                "Pizza de " + createur.getNom() + " " + createur.getPrenom());

        this.date = LocalDateTime.now();
        this.createur = createur;
    }

    private static Client checkCreateur(Client createur) {
        Util.checkObject(createur);
        return createur;
    }

    /**
     * Renvoie l'instant de création de la pizza.
     *
     * @return la date et l'heure de création
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Renvoie le client qui a composé la pizza.
     *
     * @return le créateur de la pizza
     */
    public Client getCreateur() {
        return createur;
    }

    /**
     * Renvoie la description de la pizza, complétée de sa date de création.
     *
     * @return la description textuelle de la pizza
     */
    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return super.toString() + "\nPizza créée le " + formater.format(date);
    }

    // Question 6 : equals/hashCode (titre + date)
    /**
     * Compare deux pizzas composables sur leur titre et leur créateur.
     *
     * @param o l'objet à comparer à cette pizza
     * @return true si o est une pizza composable équivalente
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PizzaComposable that = (PizzaComposable) o;
        return date.equals(that.date);
    }

    /**
     * Renvoie un code de hachage cohérent avec equals(Object).
     *
     * @return le code de hachage de la pizza composable
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
