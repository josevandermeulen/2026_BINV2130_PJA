import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

// Question 3 : la classe Client — déclaration et attributs
/**
 * Un client de la pizzeria, avec sa commande en cours et l'historique de ses commandes passées.
 *
 * Chaque client reçoit à sa création un numéro unique, attribué automatiquement par un compteur
 * partagé par toutes les instances ; c'est ce numéro, et lui seul, qui identifie un client. Il ne
 * peut avoir qu'une commande en cours à la fois : elle doit être clôturée avant qu'une autre puisse
 * être enregistrée. Le client est itérable sur ses commandes passées.
 */
public class Client implements Iterable<Commande> {
    private static int numeroSuivant = 1;

    private int numero;

    private String nom;

    private String prenom;

    private String telephone;

    private Commande commandeEnCours;
    // FinQuestion

    // (Question 7) historique des commandes passées
    private ArrayList<Commande> commandesPassees = new ArrayList<>();

    /**
     * Crée un client et lui attribue le numéro suivant.
     *
     * @param nom       le nom du client
     * @param prenom    le prénom du client
     * @param telephone le numéro de téléphone du client
     * @throws IllegalArgumentException si l'un des paramètres est null ou vide
     */
    public Client(String nom, String prenom, String telephone) {
        Util.checkString(nom);
        Util.checkString(prenom);
        Util.checkString(telephone);

        this.numero = numeroSuivant;
        numeroSuivant++;

        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    /**
     * Renvoie le numéro identifiant le client.
     *
     * @return le numéro du client
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Renvoie le nom du client.
     *
     * @return le nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le prénom du client.
     *
     * @return le prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Renvoie le numéro de téléphone du client.
     *
     * @return le numéro de téléphone du client
     */
    public String getTelephone() {
        return telephone;
    }

    // Question 3 : accès à la commande en cours
    /**
     * Renvoie la commande que le client n'a pas encore clôturée.
     *
     * @return la commande en cours, ou null si le client n'en a aucune
     */
    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }
    // FinQuestion

    // Question 3 : enregistrer et clôturer la commande en cours
    /**
     * Enregistre une commande comme commande en cours du client.
     *
     * L'enregistrement échoue si le client a déjà une commande en cours, si la commande a été
     * passée par un autre client, ou si elle figure déjà dans l'historique de ses commandes.
     *
     * @param commande la commande à enregistrer
     * @return true si la commande est devenue la commande en cours
     * @throws IllegalArgumentException si la commande est null
     */
    public boolean enregistrer(Commande commande) {
        Util.checkObject(commande);
        if (this.commandeEnCours != null) {
            return false;
        }

        if (!commande.getClient().equals(this)) {
            return false;
        }

        // (Question 7) refuser l'enregistrement d'une commande déjà passée
        if (commandesPassees.contains(commande)) {
            return false;
        }
        this.commandeEnCours = commande;
        return true;
    }

    /**
     * Clôture la commande en cours et l'archive dans l'historique des commandes passées.
     *
     * @return true si une commande a été clôturée, false si le client n'en avait aucune en cours
     */
    public boolean cloturerCommandeEnCours() {
        if (this.commandeEnCours == null) {
            return false;
        }

        // (Question 7) archiver la commande clôturée dans l'historique
        this.commandesPassees.add(this.commandeEnCours);
        this.commandeEnCours = null;
        return true;
    }
    // FinQuestion

    // (Question 7) parcours de l'historique des commandes passées
    /**
     * Renvoie un itérateur sur les commandes déjà clôturées par le client.
     *
     * @return un itérateur sur l'historique des commandes
     */
    @Override
    public Iterator<Commande> iterator() {
        return commandesPassees.iterator();
    }

    /**
     * Renvoie le numéro, l'identité et le téléphone du client.
     *
     * @return la représentation textuelle du client
     */
    @Override
    public String toString() {
        return "client n° " + numero + " (" + prenom + " " + nom + ", telephone : " + telephone + ")";
    }

    /**
     * Compare deux clients sur leur seul numéro.
     *
     * @param o l'objet à comparer à ce client
     * @return true si o est un client portant le même numéro
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return numero == client.numero;
    }

    /**
     * Renvoie un code de hachage calculé sur le numéro, cohérent avec equals(Object).
     *
     * @return le code de hachage du client
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
