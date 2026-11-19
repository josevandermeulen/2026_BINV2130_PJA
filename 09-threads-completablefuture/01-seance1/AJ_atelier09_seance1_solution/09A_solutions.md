# Atelier 9 : Threads & CompletableFuture – séance 1 : solutions

*Énoncé : [`09A_2_exercices.md`](../AJ_atelier09_seance1/09A_2_exercices.md) — théorie : [`09A_1_theorie.md`](../AJ_atelier09_seance1/09A_1_theorie.md).*

## Démarrage en synchrone

### Question 1

**Énoncé :** À combien de temps vous attendez-vous pour l'exécution du programme ?

**démarrage en synchrone**

**À combien de temps vous attendez-vous pour l'exécution du programme ?**

On s'attendrait à environ 400 ms… Il y a donc environ 100 ms en plus qui sont associées aux opérations d'affichage d'info dans le terminal (println), à l'appel de la méthode now(), et à la gestion de Thread.sleep(10). Quel est le plus gros contributeur ? En testant cela, on peut remarquer que le gros contributeur, c'est « sleep » (environ 20 ms de temps supplémentaire accumulé pour chaque compteur).

## La classe `Thread`

### Question 2

**Énoncé :** Faites hériter `CompteurThread` (package `compteur_thread`) de `Thread`, et complétez sa méthode `run`.

[`src/main/java/compteur_thread/CompteurThread.java`](src/main/java/compteur_thread/CompteurThread.java) :

```java
public class CompteurThread extends Thread {
    // ...
    /**
     * Lance le comptage dans le thread courant.
     */
    @Override
    public void run() {
        count();
    }
}
```

### Question 3

**Énoncé :** Complétez `TestCompteurThread` : lancez chaque compteur, puis attendez la fin de tous (celui qui finit le plus vite gagne).

[`src/main/java/compteur_thread/TestCompteurThread.java`](src/main/java/compteur_thread/TestCompteurThread.java) :

```java
public class TestCompteurThread {
    // ...
    public static void main(String[] args) {
        CompteurThread[] compteurs = {
                new CompteurThread("Bolt", 10),
                new CompteurThread("Jakson", 10),
                new CompteurThread("Robert", 10),
                new CompteurThread("Stéphanie", 10)
        };

        long start = System.currentTimeMillis();
        for (int i = 0; i < compteurs.length; i++) {
            compteurs[i].start();
        }

        for (int i = 0; i < compteurs.length; i++) {
            try {
                compteurs[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : "
                + duration + " ms");

    }

}
```

### Question 4

**Énoncé :** Observez le temps pour atteindre la dernière instruction du programme principal. Qu'en conclure ?

**classe Thread**

**Observer le temps pour atteindre la dernière instruction du programme principal. Qu'en conclure ?**

L'asynchrone est plus rapide que le synchrone pour ce type de traitements… Cela prend plus de temps que si tout était exécuté en parallèle (prendrait environ 100 ms). Le temps d'exécution n'est pas directement divisé par le nombre de cœurs du processeur.

## L'interface `Runnable`

### Question 5

**Énoncé :** Faites implémenter `Runnable` à `CompteurRunnable` — la méthode `count` est déjà fournie.

[`src/main/java/compteur_thread/CompteurRunnable.java`](src/main/java/compteur_thread/CompteurRunnable.java) :

```java
package compteur_thread;

/**
 * Un compteur exécutable en parallèle, écrit en implémentant Runnable.
 *
 * Variante à préférer à CompteurThread : implémenter Runnable laisse la classe libre
 * d'hériter d'autre chose, et sépare ce qui est exécuté du fil qui l'exécute.
 */
public class CompteurRunnable implements Runnable {

    private final String nom;
    private final int max;

    /**
     * Crée un compteur.
     *
     * @param nom le nom affiché devant chaque nombre
     * @param max le nombre jusqu'auquel compter
     */
    public CompteurRunnable(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    /**
     * Renvoie le nom du compteur.
     *
     * @return le nom du compteur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Compte de 1 au maximum en affichant chaque valeur, avec une pause entre deux nombres.
     */
    public void count() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
        }
        System.out.println(nom + " a fini de compter jusqu'à " + max + " à " + LocalDateTime.now());
    }

    /**
     * Lance le comptage dans le thread courant.
     */
    @Override
    public void run() {
        count();
    }

}
```

### Question 6

**Énoncé :** Complétez `TestCompteurRunnable` : relancez la même course, mais avec des threads construits à partir des `Runnable` (`new Thread(compteur)`), puis attendez leur fin.

[`src/main/java/compteur_thread/TestCompteurRunnable.java`](src/main/java/compteur_thread/TestCompteurRunnable.java) :

```java
public class TestCompteurRunnable {
    // ...
    public static void main(String[] args) {
        CompteurRunnable[] compteurs = {
                new CompteurRunnable("Bolt", 10),
                new CompteurRunnable("Jakson", 10),
                new CompteurRunnable("Robert", 10),
                new CompteurRunnable("Stéphanie", 10)
        };

        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[compteurs.length];
        for (int i = 0; i < compteurs.length; i++) {
            threads[i] = new Thread(compteurs[i]);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : "
                + duration + " ms");
    }

}
```

### Question 7

**Énoncé :** Le comportement observé est identique à celui des Questions 2 et 3. Quel avantage y a-t-il alors à passer par `Runnable` plutôt que par l'héritage de `Thread` ?

**interface Runnable — avantage sur `Thread`**

**Quel avantage y a-t-il à passer par `Runnable` plutôt que par l'héritage de `Thread` ?**

1. Java n'autorise qu'un seul héritage : une classe qui étend `Thread` ne peut plus étendre autre chose. Avec `Runnable`, la classe reste libre d'hériter de ce qu'elle veut.
2. La séparation des responsabilités est plus propre : le `Runnable` décrit **la tâche à exécuter**, le `Thread` s'occupe de **la mécanique d'exécution**. C'est d'ailleurs la même philosophie que `CompletableFuture` (séance 2), à qui on passe aussi des lambdas décrivant la tâche.
3. `Runnable` étant une interface fonctionnelle, une simple lambda suffit — pas besoin de classe dédiée.

### Question 8

**Énoncé :** `Runnable` est une interface fonctionnelle : comment lanceriez-vous un thread qui affiche un simple message, en une seule instruction, sans créer de classe ?

**interface Runnable — lambda**

**Lancer un thread qui affiche un message, en une seule instruction ?**

```java
new Thread(() -> System.out.println("Coucou depuis un autre thread !")).start();
```

## Race Condition

### Question 9

**Énoncé :** Complétez `count` dans `CompteurThreadWithRaceCondition` : déterminez le compteur gagnant (pause supplémentaire de 10 ms avant l'enregistrement), et affichez-le au sein du compteur. Conservez-le dans l'attribut de classe `gagnant` de `CompteurThreadWithRaceCondition` — attention, cet attribut peut être lu/modifié par plusieurs threads en même temps.

[`src/main/java/compteur_thread/CompteurThreadWithRaceCondition.java`](src/main/java/compteur_thread/CompteurThreadWithRaceCondition.java) :

> ⚠️ **Extrait montré dans son état à ce stade** : version sans synchronisation, celle qui fait apparaître la race condition. Le fichier lié, lui, porte l'état final, atteint à la **Question 12** — où il est repris.

```java
public class CompteurThreadWithRaceCondition extends Thread {
    private final String nom;
    private final int max;
    private static CompteurThreadWithRaceCondition gagnant;
    // ...
    /**
     * Compte de 1 au maximum avec une pause entre deux nombres, puis s'enregistre comme gagnant
     * si aucun autre compteur ne l'a fait avant lui.
     */
    public void count() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
        }
        System.out.println(nom + " a fini de compter jusqu'à " + max + " à " + LocalDateTime.now());

        // Pas encore protégé : plusieurs compteurs peuvent passer ce test avant que l'un d'eux
        // n'ait eu le temps d'enregistrer son nom (Question 11).
        if (gagnant == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
            gagnant = this;
            System.out.println("Le compteur gagnant est " + nom + " à " + LocalDateTime.now());
        }
    }
}
```

### Question 10

**Énoncé :** Complétez `TestCompteurThreadWithRaceCondition` : lancez les compteurs, attendez leur fin, puis affichez le nom du gagnant.

[`src/main/java/compteur_thread/TestCompteurThreadWithRaceCondition.java`](src/main/java/compteur_thread/TestCompteurThreadWithRaceCondition.java) :

```java
public class TestCompteurThreadWithRaceCondition {
    public static void main(String[] args) {
        CompteurThreadWithRaceCondition[] compteurs = {
                new CompteurThreadWithRaceCondition("Bolt", 10),
                new CompteurThreadWithRaceCondition("Jakson", 10),
                new CompteurThreadWithRaceCondition("Robert", 10),
                new CompteurThreadWithRaceCondition("Stéphanie", 10)
        };
        long start = System.currentTimeMillis();

        for (int i = 0; i < compteurs.length; i++) {
            compteurs[i].start();
        }

        for (int i = 0; i < compteurs.length; i++) {
            try {
                compteurs[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Le(la) gagnant(e) est " + CompteurThreadWithRaceCondition.getGagnant().getNom());
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : "
                + duration + " ms");
    }
}
```

### Question 11

**Énoncé :** Un problème devrait apparaître. Quel est-il, d'où vient-il ?

**race condition**

**Un problème devrait apparaître. Quel est-il, d'où vient-il ?**

Il y a plusieurs gagnants déterminés… En effet, nous avons des problèmes de concurrence car un thread peut lire une valeur du gagnant qui, sans qu'il le sache, a déjà été mise à jour par un autre thread au moment où il tente de modifier le gagnant. La correction (Question 12) consiste à rendre la séquence lecture-puis-écriture atomique avec un bloc `synchronized` sur un verrou commun, dans `CompteurThreadWithRaceCondition`.

### Question 12

**Énoncé :** Corrigez ce problème.

*correction de la race condition (bloc synchronized)* — [`src/main/java/compteur_thread/CompteurThreadWithRaceCondition.java`](src/main/java/compteur_thread/CompteurThreadWithRaceCondition.java) :

```java
public class CompteurThreadWithRaceCondition extends Thread {
    private final String nom;
    private final int max;
    private static CompteurThreadWithRaceCondition gagnant;
    // ...
    /**
     * Compte de 1 au maximum avec une pause entre deux nombres, puis s'enregistre comme gagnant
     * si aucun autre compteur ne l'a fait avant lui.
     */
    public void count() {
        for (int i = 1; i <= max; i++) {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException("Compteur interrompu pendant l'attente", e);
            }
        }
        System.out.println(nom + " a fini de compter jusqu'à " + max + " à " + LocalDateTime.now());

        // Correction de la race condition : le test « personne n'a encore gagné ? » et
        // l'enregistrement du gagnant forment un tout (atomique). Sans ce bloc synchronized,
        // plusieurs compteurs lisent gagnant à null pendant la pause et se déclarent tous gagnants.
        synchronized (CompteurThreadWithRaceCondition.class) {
            if (gagnant == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Compteur interrompu pendant l'attente", e);
                }
                gagnant = this;
                System.out.println("Le compteur gagnant est " + nom + " à " + LocalDateTime.now());
            }
        }
    }

    /**
     * Renvoie le compteur qui a fini le premier.
     *
     * Sans synchronisation autour de son affectation, deux compteurs peuvent se croire gagnants :
     * c'est précisément le défaut que la question fait observer.
     *
     * @return le compteur gagnant, ou null si aucun n'est encore arrivé
     */
    public static CompteurThreadWithRaceCondition getGagnant() {
        return gagnant;
    }
}
```

## Deadlock

### Question 13

**Énoncé :** Exécutez `TestTransfertDeadlock`. Le programme se fige : pas d'exception, pas de message, plus rien — c'est un **deadlock** (voir `09A_1_theorie.md`). En relisant `transferer`, expliquez précisément pourquoi les deux threads se bloquent mutuellement. Pendant que le programme est figé, faites un *thread dump* (bouton « Dump Threads » d'IntelliJ, l'icône en forme d'appareil photo de l'onglet Run, ou la commande `jstack <pid>`) et retrouvez les deux threads bloqués et les verrous qu'ils détiennent/attendent.

**pourquoi le deadlock**

**Pourquoi les deux threads se bloquent-ils mutuellement ?**

Le thread « A → B » verrouille le compte source A, puis attend le compte destination B ; le thread « B → A » verrouille B, puis attend A. Chacun détient le verrou que l'autre attend, et aucun ne relâchera le sien : attente circulaire, le programme est figé. Le thread dump montre les deux threads dans l'état `BLOCKED`, chacun sur le moniteur détenu par l'autre (IntelliJ affiche même « deadlock detected »).

### Question 14

**Énoncé :** Corrigez `transferer` **sans supprimer les blocs `synchronized`**. Relancez : le programme doit se terminer, et les soldes finaux doivent valoir 10 000 chacun.

*deadlock corrigé — les verrous sont toujours acquis dans le même* — [`src/main/java/deadlock/GestionnaireTransferts.java`](src/main/java/deadlock/GestionnaireTransferts.java) :

```java
public class GestionnaireTransferts {
    // ...
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
```

*Vérifié par : `GestionnaireTransfertsTest` (classe `Question14`).*

### Question 15

**Énoncé :** Quelle condition, parmi celles qui rendent un deadlock possible (voir la théorie), votre correction a-t-elle cassée ?

**condition de Coffman cassée**

**Laquelle des quatre conditions de Coffman la correction (Question 14) a-t-elle cassée ?**

L'**attente circulaire** (condition 4) : en verrouillant toujours les comptes dans le même ordre global (numéro croissant), les deux threads convoitent leur premier verrou dans le même ordre — le cycle « A attend B qui attend A » ne peut plus se former. Les trois autres conditions (exclusion mutuelle, rétention et attente, pas de préemption) restent vraies, mais une seule condition cassée suffit.

Autre correction acceptée : ne plus imbriquer les deux blocs, mais les enchaîner — `synchronized (source)` pour débiter, **puis** `synchronized (destination)` pour créditer. Un thread ne détient alors jamais plus d'un verrou à la fois : c'est la **rétention et attente** (condition 2) qui est cassée, et le deadlock disparaît aussi. Le prix : le transfert n'est plus atomique. Entre les deux blocs, le montant a quitté la source sans être encore arrivé sur la destination ; un autre thread qui additionne les deux soldes à ce moment-là voit de l'argent disparaître. L'ordre global garde les deux verrous pendant tout le transfert et n'a pas ce défaut.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
