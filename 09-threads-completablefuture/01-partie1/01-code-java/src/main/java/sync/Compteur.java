package sync;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class Compteur {

    private final String nom;
    private final int max;

    public Compteur(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

    public int getMax() {
        return max;
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
