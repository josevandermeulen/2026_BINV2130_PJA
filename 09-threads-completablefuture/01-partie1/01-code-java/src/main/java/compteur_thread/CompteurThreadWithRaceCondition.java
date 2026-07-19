package compteur_thread;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class CompteurThreadWithRaceCondition extends Thread {

    private final String nom;
    private final int max;

    // TODO Question 9 : utilisez la variable de classe ci-dessous permettant de retenir le gagnant : le
    //  CompteurThread qui a fini de compter le premier.
    private static CompteurThreadWithRaceCondition gagnant;

    public CompteurThreadWithRaceCondition(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void run() {
        count();
    }

    public void count() {
        // TODO Question 9 : modifier ce code pour déterminer le gagnant (le 1er qui a fini de compter)
        //  et lors de l'enregistrement du gagnant, veuillez attendre 10 ms avant de l'enregistrer et afficher
        //  le nom du gagnant sous cette forme : "Le compteur gagnant est XXX à 2024-10-25T15:20:16.109588".
        IntStream.rangeClosed(1, max).forEach(i -> {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(nom + " a finit de compter jusqu'à " + max + " à " + LocalDateTime.now());
    }

    public static CompteurThreadWithRaceCondition getGagnant() {
        return gagnant;
    }
}
