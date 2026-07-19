import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class PizzaComposable extends Pizza {
    // Question 5
    private LocalDateTime date;

    private Client createur;

    public PizzaComposable(Client createur) {
        super("Pizza composable du client " + createur.getNumero(), "Pizza de " + createur.getNom() + " " + createur.getPrenom());

        this.date = LocalDateTime.now();
        this.createur = createur;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Client getCreateur() {
        return createur;
    }

    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return super.toString() + "\nPizza créée le " + formater.format(date);
    }
}
