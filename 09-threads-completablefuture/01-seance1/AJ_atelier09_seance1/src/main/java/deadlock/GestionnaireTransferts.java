package deadlock;

/**
 * Les virements entre comptes, verrouillés de façon à ne pas s'interbloquer.
 * <p>
 * Deux transferts croisés qui verrouilleraient chacun leur compte source puis leur compte
 * destination s'attendraient mutuellement pour toujours. La parade tient en une règle : acquérir
 * toujours les verrous dans le même ordre global, ici celui des numéros de compte.
 */
public class GestionnaireTransferts {

    // TODO Question 13 : ce code peut provoquer un deadlock — comprenez pourquoi (voir Question 13)
    // TODO Question 14 : corrigez-le sans supprimer les blocs synchronized (la théorie vous donne la piste)
    /**
     * Transfère un montant d'un compte vers un autre.
     *
     * @param source      le compte débité
     * @param destination le compte crédité
     * @param montant     le montant transféré
     */
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
