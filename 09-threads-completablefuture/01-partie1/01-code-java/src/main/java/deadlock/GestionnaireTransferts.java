package deadlock;

public class GestionnaireTransferts {

    // TODO Question 13 : ce code peut provoquer un deadlock — comprenez pourquoi (voir Question 13)
    // TODO Question 14 : corrigez-le sans supprimer les blocs synchronized (la théorie vous donne la piste)
    public void transferer(Compte source, Compte destination, int montant) {
        synchronized (source) {
            attendreUnPeu(); // laisse le temps à l'autre thread de prendre son premier verrou
            synchronized (destination) {
                source.debiter(montant);
                destination.crediter(montant);
            }
        }
    }

    private void attendreUnPeu() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException("Transfert interrompu pendant l'attente", e);
        }
    }

}
