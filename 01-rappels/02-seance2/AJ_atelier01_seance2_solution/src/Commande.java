import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

// Question 2 : la classe Commande — déclaration et attributs
/**
 * La commande d'un client, composée d'une ligne par pizza commandée.
 *
 * Un client n'a qu'une commande en cours à la fois : tant qu'elle n'est pas clôturée, aucune autre
 * ne peut être créée pour lui. Les méthodes de modification ne font donc rien si la commande n'est
 * plus celle en cours du client. Chaque commande reçoit un numéro unique, qui l'identifie.
 */
public class Commande implements Iterable<LigneDeCommande> {
    private static int numeroSuivant = 1;

    private int numero;

    private Client client;

    private LocalDateTime date;

    private ArrayList<LigneDeCommande> lignesCommandes = new ArrayList<>();

    // Question 4 : le constructeur
    /**
     * Crée une commande pour un client et l'enregistre comme sa commande en cours.
     *
     * @param client le client qui passe la commande
     * @throws IllegalArgumentException si le client est null, ou s'il a déjà une commande en cours
     */
    public Commande(Client client) {
        Util.checkObject(client);
        this.client = client;
        if (!client.enregistrer(this)) {
            throw new IllegalArgumentException(
                    "impossible de créer une commande pour un client ayant encore une commande en cours");
        }

        this.numero = numeroSuivant;
        numeroSuivant++;
        this.date = LocalDateTime.now();
    }
    // FinQuestion

    // Question 2 : getters
    /**
     * Renvoie le numéro identifiant la commande.
     *
     * @return le numéro de la commande
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Renvoie le client qui a passé la commande.
     *
     * @return le client de la commande
     */
    public Client getClient() {
        return client;
    }

    /**
     * Renvoie l'instant auquel la commande a été passée.
     *
     * @return la date et l'heure de la commande
     */
    public LocalDateTime getDate() {
        return date;
    }

    // Question 6 : equals/hashCode
    /**
     * Compare deux commandes sur leur seul numéro.
     *
     * @param o l'objet à comparer à cette commande
     * @return true si o est une commande portant le même numéro
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        return numero == commande.numero;
    }

    /**
     * Renvoie un code de hachage calculé sur le numéro, cohérent avec equals(Object).
     *
     * @return le code de hachage de la commande
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    // Question 5 : ajouter
    /**
     * Ajoute une quantité d'une pizza à la commande. Si la pizza y figure déjà, la quantité de sa
     * ligne est augmentée plutôt que de créer une seconde ligne.
     *
     * @param pizza    la pizza à commander
     * @param quantite le nombre d'exemplaires à ajouter, strictement positif
     * @return true si l'ajout a eu lieu, false si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null ou si la quantité n'est pas strictement
     *                                  positive
     */
    public boolean ajouter(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        Util.checkStrictlyPositive(quantite);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                l.setQuantite(l.getQuantite() + quantite);
                return true;
            }
        }
        return lignesCommandes.add(new LigneDeCommande(pizza, quantite));
    }

    /**
     * Ajoute un exemplaire d'une pizza à la commande.
     *
     * @param pizza la pizza à commander
     * @return true si l'ajout a eu lieu, false si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean ajouter(Pizza pizza) {
        return this.ajouter(pizza, 1);
    }

    // Question 8 : retirer/supprimer
    /**
     * Retire une quantité d'une pizza de la commande. La ligne disparaît si la quantité retirée est
     * exactement celle commandée ; rien n'est retiré si elle est supérieure.
     *
     * @param pizza    la pizza concernée
     * @param quantite le nombre d'exemplaires à retirer, strictement positif
     * @return true si le retrait a eu lieu, false si la pizza n'est pas commandée en quantité
     *         suffisante ou si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null ou si la quantité n'est pas strictement
     *                                  positive
     */
    public boolean retirer(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        Util.checkStrictlyPositive(quantite);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                if (l.getQuantite() < quantite) {
                    return false;
                }
                if (l.getQuantite() == quantite) {
                    lignesCommandes.remove(l);
                } else {
                    l.setQuantite(l.getQuantite() - quantite);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retire un exemplaire d'une pizza de la commande.
     *
     * @param pizza la pizza concernée
     * @return true si le retrait a eu lieu
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean retirer(Pizza pizza) {
        return this.retirer(pizza, 1);
    }

    // Question 8 : supprimer
    /**
     * Retire de la commande la ligne d'une pizza, quelle que soit la quantité commandée.
     *
     * @param pizza la pizza à retirer de la commande
     * @return true si une ligne a été supprimée, false si la pizza n'était pas commandée ou si la
     *         commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean supprimer(Pizza pizza) {
        Util.checkObject(pizza);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                //on peut supprimer directement car on arrête le parcours juste après la suppression
                lignesCommandes.remove(l);
                return true;
            }
        }
        return false;
    }

    // Question 5 : calculerMontantTotal/detailler
    /**
     * Calcule le montant de la commande, soit la somme du prix total de chacune de ses lignes.
     *
     * @return le montant total de la commande, en euros
     */
    public double calculerMontantTotal() {
        double total = 0;
        for (LigneDeCommande l : lignesCommandes) {
            total += l.calculerPrixTotal();
        }
        return total;
    }

    /**
     * Renvoie le détail des lignes de la commande, une par ligne de texte.
     *
     * @return le détail de la commande, ou une chaîne vide si la commande ne contient aucune ligne
     */
    public String detailler() {
        String lignes = "";
        for (LigneDeCommande l : lignesCommandes) {
            lignes += l.toString() + "\n";
        }
        if (lignes.equals("")) {
            return lignes;
        }
        return lignes.substring(0, lignes.length() - 1);
    }

    // Question 2 : iterator
    /**
     * Renvoie un itérateur sur les lignes de la commande.
     *
     * @return un itérateur sur les lignes de commande
     */
    @Override
    public Iterator<LigneDeCommande> iterator() {
        return lignesCommandes.iterator();
    }
    // FinQuestion

    /**
     * Renvoie le numéro de la commande, le client concerné et la date, en signalant le cas échéant
     * qu'il s'agit de la commande en cours.
     *
     * @return la représentation textuelle de la commande
     */
    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this) {
            encours = " (en cours)";
        }
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
