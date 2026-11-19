package deadlock;

/**
 * Les virements entre comptes, verrouillés de façon à ne pas s'interbloquer.
 *
 * Deux transferts croisés qui verrouilleraient chacun leur compte source puis leur compte
 * destination s'attendraient mutuellement pour toujours. La parade tient en une règle : acquérir
 * toujours les verrous dans le même ordre global, ici celui des numéros de compte.
 */
public class GestionnaireTransferts {

    // Question 14 : deadlock corrigé — les verrous sont toujours acquis dans le même
    // ordre global (numéro de compte croissant), ce qui casse l'attente circulaire
    /**
     * Transfère un montant d'un compte vers un autre.
     *
     * @param source      le compte débité
     * @param destination le compte crédité
     * @param montant     le montant transféré
     */
    public void transferer(Compte source, Compte destination, int montant) {
        Compte premierVerrou = source.getNumero() < destination.getNumero() ? source : destination;
        Compte secondVerrou = premierVerrou == source ? destination : source;

        synchronized (premierVerrou) {
            attendreUnPeu(); // laisse le temps à l'autre thread de prendre son premier verrou
            synchronized (secondVerrou) {
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
