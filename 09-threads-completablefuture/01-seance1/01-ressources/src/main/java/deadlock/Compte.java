package deadlock;

public class Compte {

    private final int numero;

    private int solde;

    public Compte(int numero, int soldeInitial) {
        this.numero = numero;
        this.solde = soldeInitial;
    }

    public int getNumero() {
        return numero;
    }

    public int getSolde() {
        return solde;
    }

    public void debiter(int montant) {
        solde -= montant;
    }

    public void crediter(int montant) {
        solde += montant;
    }

}
