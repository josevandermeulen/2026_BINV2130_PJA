package deadlock;

/**
 * Un compte bancaire, réduit à son numéro et à son solde.
 * <p>
 * Le numéro sert aussi d'ordre global entre comptes : c'est lui qui permet à
 * {@link GestionnaireTransferts} de toujours verrouiller dans le même sens.
 */
public class Compte {

    private final int numero;

    private int solde;

    /**
     * Crée un compte.
     *
     * @param numero       le numéro identifiant le compte
     * @param soldeInitial le solde de départ
     */
    public Compte(int numero, int soldeInitial) {
        this.numero = numero;
        this.solde = soldeInitial;
    }

    /**
     * Renvoie le numéro du compte.
     *
     * @return le numéro du compte
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Renvoie le solde courant du compte.
     *
     * @return le solde du compte
     */
    public int getSolde() {
        return solde;
    }

    /**
     * Retire un montant du compte.
     *
     * @param montant le montant à retirer
     */
    public void debiter(int montant) {
        solde -= montant;
    }

    /**
     * Ajoute un montant au compte.
     *
     * @param montant le montant à ajouter
     */
    public void crediter(int montant) {
        solde += montant;
    }

}
