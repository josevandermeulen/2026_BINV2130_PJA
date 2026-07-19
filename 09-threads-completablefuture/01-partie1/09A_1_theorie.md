# Atelier 9 : Threads & CompletableFuture – partie 1 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [Thread](#thread)
4. [Créer un thread en Java](#créer-un-thread-en-java)
5. [Race condition](#race-condition)
6. [Deadlock](#deadlock)

## Vidéos

1. [Les Threads - Bases](https://www.youtube.com/watch?v=4cYaMxE5a58)
2. [Threads - Race Condition](https://www.youtube.com/watch?v=G-BEqoP57Lg)
3. [Threads - Deadlock](https://www.youtube.com/watch?v=SabJvW2oPxQ)
4. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
5. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
6. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
7. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)

## Introduction

La programmation que nous avons abordée jusqu'à présent repose sur un seul flux d'exécution. Cette approche pose des limites dans certains cas :

1. Lorsque le programme interagit avec le monde extérieur, il se retrouve souvent à attendre que ce monde extérieur réagisse ; par exemple, pour écrire un bout de fichier sur un disque ou encore pour obtenir une réponse d'un utilisateur.
2. De plus en plus, les ordinateurs sont équipés de plusieurs processeurs. Chaque processeur peut traiter son propre flux d'exécution, mais si le programme n'en possède qu'un, un seul processeur sera réellement utilisé et le programme n'utilise qu'une fraction de la puissance de la machine.
3. Certains programmes s'expriment naturellement beaucoup mieux à l'aide de plusieurs flux d'exécution. Un serveur web pourra traiter chaque requête dans un flux indépendant des autres requêtes en cours de traitement.

Utiliser des processus du système d'exploitation pour résoudre ceci génèrerait notamment beaucoup de problèmes de collaboration entre eux car ils seraient indépendants et gérés par le système d'exploitation.

## Thread

En programmation, peu importe le langage, on peut procéder à l'exécution de différents codes dans différents flux d'exécution en même temps (ou à des moments différés). Un flux d'exécution est appelé un **thread** en informatique.

Un thread est une version allégée de processus. L'idée est qu'un seul processus exécutera directement plusieurs threads. L'avantage de cette approche est multiple :

1. Les threads sont légers comparés aux processus : beaucoup plus rapides à démarrer, beaucoup moins coûteux en mémoire.
2. Les threads partagent le même espace d'adressage et peuvent donc se partager des données et du code. Donc le coût de communication entre les threads est relativement bas par rapport à celui entre les processus.
3. Le fait pour un processeur de passer de l'exécution d'un thread à un autre (*context switch* en anglais) est moins coûteux que pour les processus.

## Créer un thread en Java

Java offre deux façons classiques de définir le code à exécuter dans un thread :

1. **Étendre la classe `Thread`** et redéfinir sa méthode `run()`. On démarre alors le thread en appelant `start()` (et non `run()` directement : appeler `run()` exécuterait le code dans le thread courant, de manière synchrone).

   ```java
   public class MonThread extends Thread {
       @Override
       public void run() {
           // code exécuté dans le nouveau thread
       }
   }

   new MonThread().start();
   ```

2. **Implémenter l'interface fonctionnelle `Runnable`** et la passer au constructeur de `Thread`. Cette approche est plus souple : la classe reste libre d'hériter d'autre chose, et un simple lambda suffit.

   ```java
   Thread t = new Thread(() -> {
       // code exécuté dans le nouveau thread
   });
   t.start();
   ```

Quelques méthodes utiles :

| Méthode | Rôle |
|---|---|
| `start()` | démarre le thread : la JVM appelle `run()` dans un nouveau flux d'exécution |
| `Thread.sleep(ms)` | met le thread **courant** en pause pendant `ms` millisecondes (lance une `InterruptedException` à gérer) |
| `join()` | bloque le thread appelant jusqu'à ce que le thread visé ait terminé |

Le thread qui exécute `main` est un thread comme un autre : si le programme démarre d'autres threads, `main` peut se terminer avant eux — la JVM ne s'arrête que lorsque tous les threads (non *daemon*) ont fini.

## Race condition

Dès que plusieurs threads lisent **et** modifient une même donnée partagée, l'ordre exact des opérations entre threads devient imprévisible : deux exécutions du même programme peuvent donner des résultats différents. C'est ce qu'on appelle une **race condition** (situation de compétition).

Exemple typique : deux threads exécutent chacun `if (gagnant == null) gagnant = this;`. Les deux peuvent lire `gagnant == null` *avant* que l'un des deux n'ait écrit sa valeur — et les deux se croient alors gagnants.

En Java, la solution de base est le mot-clé **`synchronized`** : un bloc (ou une méthode) `synchronized` sur un même objet-verrou ne peut être exécuté que par un seul thread à la fois ; les autres attendent leur tour.

```java
synchronized (verrou) {
    if (gagnant == null) {
        gagnant = this;
    }
}
```

La lecture et l'écriture forment ainsi une opération **atomique** : plus de race condition. Attention en revanche à ne pas abuser des sections synchronisées : elles resynchronisent l'exécution (perte de parallélisme) et, mal utilisées, peuvent mener à des interblocages (*deadlocks* — voir la section suivante).

## Deadlock

Un **deadlock** (interblocage) survient quand deux threads (ou plus) s'attendent mutuellement, chacun détenant un verrou dont l'autre a besoin. Plus aucun des deux ne peut avancer : le programme est figé, sans exception, sans message d'erreur — il ne se passe simplement plus rien.

Le scénario classique demande deux verrous pris **dans un ordre différent** par deux threads :

```java
Object verrouA = new Object();
Object verrouB = new Object();

// Thread 1
synchronized (verrouA) {          // 1. prend A
    Thread.sleep(50);
    synchronized (verrouB) {      // 3. attend B... détenu par le thread 2
        // ...
    }
}

// Thread 2
synchronized (verrouB) {          // 2. prend B
    Thread.sleep(50);
    synchronized (verrouA) {      // 4. attend A... détenu par le thread 1
        // ...
    }
}
```

Le thread 1 détient `verrouA` et attend `verrouB` ; le thread 2 détient `verrouB` et attend `verrouA`. Aucun des deux ne relâchera jamais ce que l'autre attend : interblocage.

Quatre conditions doivent être réunies simultanément (conditions de Coffman) pour qu'un deadlock soit possible :

1. **Exclusion mutuelle** : les ressources (verrous) ne peuvent être détenues que par un thread à la fois ;
2. **Rétention et attente** : un thread garde ses verrous pendant qu'il en attend d'autres ;
3. **Pas de préemption** : un verrou ne peut pas être retiré de force à son détenteur ;
4. **Attente circulaire** : chaque thread attend une ressource détenue par le suivant, en boucle fermée.

Casser une seule de ces conditions suffit à rendre le deadlock impossible. En pratique, la parade la plus simple casse l'attente circulaire : **toujours acquérir les verrous dans le même ordre global** (par exemple, toujours `verrouA` avant `verrouB`, dans tous les threads). Autres bonnes habitudes :

1. garder les sections synchronisées **courtes** et éviter d'y appeler du code qui prend lui-même des verrous ;
2. ne jamais faire d'appel bloquant (`sleep`, entrée/sortie, `join`...) en détenant un verrou ;
3. préférer un seul verrou à plusieurs quand c'est possible — pas de deuxième verrou, pas de cycle.

À noter : un deadlock n'est pas propre aux threads Java — on retrouve exactement le même phénomène entre transactions dans une base de données. Et contrairement à une race condition (résultat faux, mais le programme continue), un deadlock se **voit** : le programme est suspendu. Un *thread dump* (`jstack`, ou le bouton de dump de threads d'IntelliJ) montre alors explicitement quels threads se bloquent mutuellement et sur quels verrous.

---

*Passez aux [exercices](09A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/09-threads-completablefuture-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
