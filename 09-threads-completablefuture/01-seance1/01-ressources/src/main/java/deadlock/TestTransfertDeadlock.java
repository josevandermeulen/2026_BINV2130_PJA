package deadlock;

public class TestTransfertDeadlock {

    private static final int NB_TRANSFERTS = 1000;

    public static void main(String[] args) throws InterruptedException {
        Compte compteA = new Compte(1, 10_000);
        Compte compteB = new Compte(2, 10_000);
        GestionnaireTransferts gestionnaire = new GestionnaireTransferts();

        Thread versB = new Thread(() -> {
            for (int i = 0; i < NB_TRANSFERTS; i++) {
                gestionnaire.transferer(compteA, compteB, 1);
            }
        });
        Thread versA = new Thread(() -> {
            for (int i = 0; i < NB_TRANSFERTS; i++) {
                gestionnaire.transferer(compteB, compteA, 1);
            }
        });

        versB.start();
        versA.start();
        versB.join();
        versA.join();

        System.out.println("Transferts terminés !");
        System.out.println("Solde du compte 1 : " + compteA.getSolde());
        System.out.println("Solde du compte 2 : " + compteB.getSolde());
    }

}
