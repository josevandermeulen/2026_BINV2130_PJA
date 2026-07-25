package compteur_thread;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

// TODO Question 2 : faire hériter cette classe de Thread et implémenter la méthode run

public class CompteurThread {

    private final String nom;
    private final int max;

    public CompteurThread(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

    public void count() {
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

}
