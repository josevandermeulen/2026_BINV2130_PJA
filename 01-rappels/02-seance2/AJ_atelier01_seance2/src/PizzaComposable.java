import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// Question 5 : la sous-classe PizzaComposable
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
     */
    public PizzaComposable(Client createur) {
        super("Pizza composable du client " + createur.getNumero(),
                "Pizza de " + createur.getNom() + " " + createur.getPrenom());

        this.date = LocalDateTime.now();
        this.createur = createur;
    }

    /**
     * Renvoie l'instant de création de la pizza.
     *
     * @return la date et l'heure de création
     */
    public LocalDateTime getDate() {
        return date;
    }
    // FinQuestion

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
}
