import java.util.Objects;

// Question 1 : la classe Client — numéro attribué automatiquement
/**
 * Un client de la pizzeria.
 * <p>
 * Chaque client reçoit à sa création un numéro unique, attribué automatiquement par un compteur
 * partagé par toutes les instances. C'est ce numéro, et lui seul, qui identifie un client : deux
 * homonymes restent deux clients distincts.
 */
public class Client {
    private static int numeroSuivant = 1;

    private int numero;

    private String nom;

    private String prenom;

    private String telephone;

    /**
     * Crée un client et lui attribue le numéro suivant.
     *
     * @param nom       le nom du client
     * @param prenom    le prénom du client
     * @param telephone le numéro de téléphone du client
     */
    public Client(String nom, String prenom, String telephone) {
        this.numero = numeroSuivant;
        numeroSuivant++;

        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }
    // FinQuestion

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

    /**
     * Renvoie le numéro, l'identité et le téléphone du client.
     *
     * @return la représentation textuelle du client
     */
    @Override
    public String toString() {
        return "client n° " + numero + " (" + prenom + " " + nom + ", telephone : " + telephone + ")";
    }

    // Question 2 : equals/hashCode
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
     * Renvoie un code de hachage calculé sur le numéro, cohérent avec {@link #equals(Object)}.
     *
     * @return le code de hachage du client
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
