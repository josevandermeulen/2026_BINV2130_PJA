# Atelier 10 : HyperLogLog appliqué (programmation asynchrone) – partie 1

## Objectif

L'objectif de cette partie est d'appliquer `CompletableFuture` (atelier 9, partie 2) à un traitement concret déjà connu : l'estimation du nombre d'adresses IP uniques avec HyperLogLog (atelier 8, partie 2). Le chargement des fichiers de logs et l'estimation sont lancés de manière asynchrone, puis chaînés sans jamais bloquer.

## Concepts

1. `CompletableFuture.supplyAsync` (rappel de l'atelier 9)
2. `thenApply`, `thenCombine`, `join`
3. Gestion des exceptions checked dans une lambda asynchrone
4. `exceptionally`
5. `allOf` : paralléliser plusieurs traitements asynchrones
6. Mesure du temps d'exécution (séquentiel vs parallèle)
7. Réutilisation de HyperLogLog et de la lecture de fichiers (atelier 8 — aucune nouvelle théorie)

## Vidéos

1. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
2. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
3. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
4. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)
5. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Exercices

### Introduction

L'équipe sécurité de l'atelier 8 est satisfaite de son estimateur HyperLogLog... mais plus les fichiers de logs grossissent, plus leur chargement immobilise le programme : tout est figé tant que la lecture n'est pas finie. Vous allez rendre cette analyse **asynchrone** : le chargement et l'estimation s'exécutent dans un thread du pool pendant que le programme principal continue.

Toutes les classes de l'atelier 8 vous sont fournies **déjà implémentées** ([`HyperLogLog`](01-code-java/src/main/java/domaine/HyperLogLog.java), [`Hasher`](01-code-java/src/main/java/domaine/Hasher.java), [`DefaultHasher`](01-code-java/src/main/java/domaine/DefaultHasher.java), [`Acces`](01-code-java/src/main/java/domaine/Acces.java), [`AnalyseLogs`](01-code-java/src/main/java/main/AnalyseLogs.java), [`AnalyseIp`](01-code-java/src/main/java/main/AnalyseIp.java), la lecture de fichiers) : votre travail porte uniquement sur la nouvelle classe [`AnalyseIpAsync`](01-code-java/src/main/java/main/AnalyseIpAsync.java) (package `main`).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier10_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `util`, `main`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml` (voir le `pom.xml` fourni). Copiez également le dossier `logs/` à la racine de votre projet (pas dans un package ni dans `src/`).

Chaque question est vérifiée par les tests JUnit fournis ([`AnalyseIpAsyncTest`](01-code-java/src/test/java/main/AnalyseIpAsyncTest.java)) : exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

### Estimer de manière asynchrone

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez la classe `AnalyseIpAsync` et complétez `estimerIpUniquesAsync(List<Acces>, HyperLogLog)` : elle lance `AnalyseIp.estimerIpUniques` dans un traitement asynchrone à l'aide de `CompletableFuture.supplyAsync` et renvoie le `CompletableFuture<Long>` obtenu — sans jamais appeler `join` (c'est l'appelant qui décidera quand bloquer).

### Charger les fichiers de logs de manière asynchrone

**Question 2** :
Complétez `chargerAccesAsync(String dossier)` : elle lance `analyse.chargerTousLesAcces(dossier)` dans un traitement asynchrone et renvoie un `CompletableFuture<List<Acces>>`.

Attention : `chargerTousLesAcces` déclare `throws IOException`, une exception *checked* qu'une lambda `Supplier` ne peut pas laisser s'échapper. Attrapez-la et relancez une `RuntimeException` (en lui passant l'`IOException` comme cause) : la future sera alors complétée exceptionnellement, comme vu à l'atelier 9.

### Chaîner chargement et estimation

**Question 3** :
Complétez `estimerDepuisDossierAsync(String dossier, HyperLogLog)` : elle chaîne le chargement asynchrone de la Question 2 et l'estimation (`AnalyseIp.estimerIpUniques`) à l'aide de `thenApply`, et renvoie un `CompletableFuture<Long>`. Toujours aucun `join` : la chaîne complète est décrite sans bloquer.

**Question 4** :
Exécutez [`Main`](01-code-java/src/main/java/main/Main.java) (fourni) : il lance `estimerDepuisDossierAsync` sur le dossier `logs/`, affiche immédiatement un message montrant que le programme principal n'est pas bloqué, puis appelle `join` pour afficher l'estimation. Pourquoi le premier message s'affiche-t-il après quelques millisecondes seulement, alors que l'estimation n'arrive que plus tard ?

Réponse dans [`02-solution/10A_solutions-observations.md`](02-solution/10A_solutions-observations.md) : réfléchissez (et testez !) avant de la consulter.

### Gérer les erreurs avec une valeur de repli

**Question 5** :
Complétez `estimerDepuisDossierAsyncAvecRepli(String dossier, HyperLogLog, long valeurRepli)` : même chaîne que la Question 3, mais si elle se termine en erreur (dossier inexistant, par exemple), la future renvoie `valeurRepli` au lieu de propager l'exception. Utilisez `exceptionally`.

### Paralléliser deux dossiers avec `thenCombine`

**Question 6** :
Complétez `estimerDeuxDossiersAsync(String dossier1, String dossier2, HyperLogLog)` : chargez les deux dossiers en parallèle (deux appels à `chargerAccesAsync`), combinez les deux listes d'accès obtenues avec `thenCombine`, puis estimez le nombre d'IP uniques sur la liste combinée avec le `HyperLogLog` fourni. Toujours aucun `join`.

### Paralléliser plusieurs dossiers avec `allOf`

**Question 7** :
Complétez `estimerPlusieursDossiersAsync(List<String> dossiers, Hasher)` : pour chaque dossier, lancez `estimerDepuisDossierAsync` avec sa **propre** instance `HyperLogLog` (pas de partage entre dossiers, pour éviter que plusieurs threads modifient le même objet en même temps), puis attendez que toutes les futures soient terminées avec `CompletableFuture.allOf` avant de renvoyer un `CompletableFuture<List<Long>>` contenant une estimation par dossier, dans le même ordre que la liste reçue.

### Comparer les temps d'exécution séquentiel et parallèle

**Question 8** :
Complétez `estimerPlusieursDossiersSequentiellement(List<String> dossiers, Hasher)` : même résultat que la Question 7 (une estimation par dossier, dans l'ordre), mais en traitant les dossiers **un par un**, en appelant `join` sur chaque dossier avant de passer au suivant (`estimerDepuisDossierAsync(...).join()` dans une boucle) — aucun parallélisme ici, c'est la version de référence.

Exécutez à nouveau `Main` (mis à jour) : il compare le temps total des deux versions sur plusieurs dossiers (`logs/` réutilisé plusieurs fois pour la démonstration) et affiche les deux durées. Le temps séquentiel est-il proche de la somme des temps individuels ? Et le temps parallèle ?

---

*Passez à la [théorie suivante](../02-partie2/10B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/10-hyperloglog-async-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
