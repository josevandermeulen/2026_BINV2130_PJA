# Atelier 9 : Threads & CompletableFuture – partie 1

## Objectif

L'objectif de cette partie est de comprendre ce qu'est un flux d'exécution (thread), de lancer des traitements de manière asynchrone avec les deux approches classiques (hériter de `Thread`, implémenter `Runnable`), puis de mettre en évidence — et corriger — les deux grands pièges de la programmation concurrente : la race condition et le deadlock.

## Concepts

1. Programmation synchrone et asynchrone
2. `Thread`
3. `Runnable`
4. Exécution concurrente
5. `sleep`
6. Race condition et synchronisation (`synchronized`)
7. Deadlock et ordre d'acquisition des verrous
8. Mesure du temps d'exécution

## Vidéos

1. [Les Threads - Bases](https://www.youtube.com/watch?v=4cYaMxE5a58)
2. [Threads - Race Condition](https://www.youtube.com/watch?v=G-BEqoP57Lg)
3. [Threads - Deadlock](https://www.youtube.com/watch?v=SabJvW2oPxQ)
4. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
5. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
6. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
7. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)

## Exercices

### Introduction

On va implémenter une classe dont l'objectif est de faire une course de compteurs. Un compteur a un nom et permet de compter de 1 à max, avec une pause de 10 ms entre chaque nombre (par exemple, `"Bolt : 7"`). À la fin, il affiche `"Bolt a fini de compter jusqu'à xx à 2024-10-25T14:44:55.317850"`.

Réponses aux questions de réflexion dans [`02-solution/09A_solutions-observations.md`](02-solution/09A_solutions-observations.md) : réfléchissez (et testez !) avant de la consulter.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier09_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `sync`, `compteur_thread` et `deadlock`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml` (voir le `pom.xml` fourni).

NB : le `pom.xml` fourni a été configuré pour la version 21 du JDK. Cette version se change facilement pour correspondre à la version présente sur votre laptop, en mettant à jour `pom.xml`. Si vous souhaitez installer une nouvelle version du JDK, vous pouvez le faire via : File → Project Structure… → Project → SDK → Download JDK…

### Démarrage en synchrone

Dans le package `sync`, la classe [`Compteur`](01-code-java/src/main/java/sync/Compteur.java) compte de manière synchrone. Exécutez [`TestCompteurSync`](01-code-java/src/main/java/sync/TestCompteurSync.java) et observez le temps d'exécution.

**Question 1** :

✏️ *A corriger au tableau*

À combien de temps vous attendez-vous pour l'exécution du programme ?

### La classe `Thread`

**Question 2** :
Faites hériter [`CompteurThread`](01-code-java/src/main/java/compteur_thread/CompteurThread.java) (package `compteur_thread`) de `Thread`, et complétez sa méthode `run`.

**Question 3** :
Complétez [`TestCompteurThread`](01-code-java/src/main/java/compteur_thread/TestCompteurThread.java) : lancez chaque compteur, puis attendez la fin de tous (celui qui finit le plus vite gagne).

**Question 4** :
Observez le temps pour atteindre la dernière instruction du programme principal. Qu'en conclure ?

### L'interface `Runnable`

✏️ *A corriger au tableau*

**Question 5** :
Faites implémenter `Runnable` à [`CompteurRunnable`](01-code-java/src/main/java/compteur_thread/CompteurRunnable.java) — la méthode `count` est déjà fournie.

**Question 6** :
Complétez [`TestCompteurRunnable`](01-code-java/src/main/java/compteur_thread/TestCompteurRunnable.java) : relancez la même course, mais avec des threads construits à partir des `Runnable` (`new Thread(compteur)`), puis attendez leur fin.

**Question 7** :
Le comportement observé est identique à celui des Questions 2 et 3. Quel avantage y a-t-il alors à passer par `Runnable` plutôt que par l'héritage de `Thread` ?

**Question 8** :
`Runnable` est une interface fonctionnelle : comment lanceriez-vous un thread qui affiche un simple message, en une seule instruction, sans créer de classe ?

### Race Condition

**Question 9** :
Complétez `count` dans [`CompteurThreadWithRaceCondition`](01-code-java/src/main/java/compteur_thread/CompteurThreadWithRaceCondition.java) : déterminez le compteur gagnant (pause supplémentaire de 10 ms avant l'enregistrement), et affichez-le au sein du compteur.

**Question 10** :
Complétez [`TestCompteurThreadWithRaceCondition`](01-code-java/src/main/java/compteur_thread/TestCompteurThreadWithRaceCondition.java) : lancez les compteurs, attendez leur fin, puis affichez le nom du gagnant. Conservez-le dans un attribut de classe `CompteurThreadWithRaceCondition` — attention, cet attribut peut être lu/modifié par plusieurs threads en même temps.

**Question 11** :
Un problème devrait apparaître. Quel est-il, d'où vient-il ?

**Question 12** :
Corrigez ce problème.

### Deadlock

Le package `deadlock` contient une petite application bancaire fournie : des [`Compte`](01-code-java/src/main/java/deadlock/Compte.java), un [`GestionnaireTransferts`](01-code-java/src/main/java/deadlock/GestionnaireTransferts.java) dont la méthode `transferer` synchronise sur les deux comptes concernés, et un [`TestTransfertDeadlock`](01-code-java/src/main/java/deadlock/TestTransfertDeadlock.java) qui lance deux threads faisant chacun 1 000 transferts... en sens inverse l'un de l'autre.

**Question 13** :
Exécutez `TestTransfertDeadlock`. Le programme se fige : pas d'exception, pas de message, plus rien — c'est un **deadlock** (voir [`09A_1_theorie.md`](09A_1_theorie.md)). En relisant `transferer`, expliquez précisément pourquoi les deux threads se bloquent mutuellement. Pendant que le programme est figé, faites un *thread dump* (bouton « Get thread dump » d'IntelliJ, dans l'onglet Run, ou la commande `jstack <pid>`) et retrouvez les deux threads bloqués et les verrous qu'ils détiennent/attendent.

**Question 14** :
Corrigez `transferer` **sans supprimer les blocs `synchronized`**. Relancez : le programme doit se terminer, et les soldes finaux doivent valoir 10 000 chacun.

Le test JUnit fourni ([`GestionnaireTransfertsTest`](01-code-java/src/test/java/deadlock/GestionnaireTransfertsTest.java)) vérifie cette question : tant que `transferer` n'est pas corrigée, il échoue par timeout (après 10 secondes, sans erreur de compilation) plutôt que par une assertion classique — signe caractéristique d'un deadlock.

**Question 15** :
Quelle condition, parmi celles qui rendent un deadlock possible (voir la théorie), votre correction a-t-elle cassée ?

---

*Passez à la [théorie suivante](../02-partie2/09B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/09-threads-completablefuture-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
