# Atelier 9 – séance 1 : réponses aux questions d'observation

Ce document reprend les questions de réflexion (Questions 1, 4, 7, 8, 11, 13 et 15) de la fiche d'exercices avec leur réponse. Réfléchissez et testez avant de le consulter.

## Question 1 : démarrage en synchrone

**À combien de temps vous attendez-vous pour l'exécution du programme ?**

On s'attendrait à environ 400 ms… Il y a donc environ 100 ms en plus qui sont associées aux opérations d'affichage d'info dans le terminal (println), à l'appel de la méthode now(), et à la gestion de Thread.sleep(10). Quel est le plus gros contributeur ? En testant cela, on peut remarquer que le gros contributeur, c'est « sleep » (environ 20 ms de temps supplémentaire accumulé pour chaque compteur).

## Question 4 : classe Thread

**Observer le temps pour atteindre la dernière instruction du programme principal. Qu'en conclure ?**

L'asynchrone est plus rapide que le synchrone pour ce type de traitements… Cela prend plus de temps que si tout était exécuté en parallèle (prendrait environ 100 ms). Le temps d'exécution n'est pas directement divisé par le nombre de cœurs du processeur.

## Question 7 : interface Runnable — avantage sur `Thread`

**Quel avantage y a-t-il à passer par `Runnable` plutôt que par l'héritage de `Thread` ?**

1. Java n'autorise qu'un seul héritage : une classe qui étend `Thread` ne peut plus étendre autre chose. Avec `Runnable`, la classe reste libre d'hériter de ce qu'elle veut.
2. La séparation des responsabilités est plus propre : le `Runnable` décrit **la tâche à exécuter**, le `Thread` s'occupe de **la mécanique d'exécution**. C'est d'ailleurs la même philosophie que `CompletableFuture` (séance 2), à qui on passe aussi des lambdas décrivant la tâche.
3. `Runnable` étant une interface fonctionnelle, une simple lambda suffit — pas besoin de classe dédiée.

## Question 8 : interface Runnable — lambda

**Lancer un thread qui affiche un message, en une seule instruction ?**

```java
new Thread(() -> System.out.println("Coucou depuis un autre thread !")).start();
```

## Question 11 : race condition

**Un problème devrait apparaître. Quel est-il, d'où vient-il ?**

Il y a plusieurs gagnants déterminés… En effet, nous avons des problèmes de concurrence car un thread peut lire une valeur du gagnant qui, sans qu'il le sache, a déjà été mise à jour par un autre thread au moment où il tente de modifier le gagnant. La correction (Question 12) consiste à rendre la séquence lecture-puis-écriture atomique avec un bloc `synchronized` sur un verrou commun, dans `CompteurThreadWithRaceCondition`.

## Question 13 : pourquoi le deadlock

**Pourquoi les deux threads se bloquent-ils mutuellement ?**

Le thread « A → B » verrouille le compte source A, puis attend le compte destination B ; le thread « B → A » verrouille B, puis attend A. Chacun détient le verrou que l'autre attend, et aucun ne relâchera le sien : attente circulaire, le programme est figé. Le thread dump montre les deux threads dans l'état `BLOCKED`, chacun sur le moniteur détenu par l'autre (IntelliJ affiche même « deadlock detected »).

## Question 15 : condition de Coffman cassée

**Laquelle des quatre conditions de Coffman la correction (Question 14) a-t-elle cassée ?**

L'**attente circulaire** (condition 4) : en verrouillant toujours les comptes dans le même ordre global (numéro croissant), les deux threads convoitent leur premier verrou dans le même ordre — le cycle « A attend B qui attend A » ne peut plus se former. Les trois autres conditions (exclusion mutuelle, rétention et attente, pas de préemption) restent vraies, mais une seule condition cassée suffit.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
