# Atelier 10 : HyperLogLog appliqué (programmation asynchrone) – partie 2

## Objectif

L'objectif de cette partie est de passer à l'échelle : les logs proviennent maintenant de plusieurs routeurs (un fichier par routeur), et chaque routeur construit son propre HyperLogLog dans son propre `CompletableFuture`. Une nouvelle opération `fusionner` combine les estimateurs locaux en un résultat global — identique au résultat séquentiel, l'ordre des traitements n'ayant aucune importance.

## Concepts

1. Fusion de deux HyperLogLog (max registre par registre — commutative et associative)
2. Un `CompletableFuture` par source de données (*scatter/gather*)
3. `thenCombine`, `completedFuture`
4. Déterminisme d'un traitement parallèle
5. Comparaison des temps séquentiel et asynchrone

## Vidéos

1. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
2. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
3. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
4. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)
5. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Exercices

### Introduction

La plateforme s'est agrandie : le trafic passe désormais par plusieurs **routeurs**, et chacun écrit son propre fichier de log (`routeur-00.log`, `routeur-01.log`, ..., listés dans `index.txt`). L'équipe sécurité veut toujours le nombre d'adresses IP uniques **global** — une même adresse peut apparaître chez plusieurs routeurs, additionner les comptages par routeur serait donc faux.

La bonne nouvelle (voir `10B_1_theorie.md`) : deux HyperLogLog se **fusionnent** en prenant le maximum registre par registre, et le résultat est identique à celui d'un estimateur unique qui aurait tout vu. Chaque routeur peut donc être traité dans son propre `CompletableFuture`, les estimateurs locaux étant fusionnés au fur et à mesure.

Les classes de la partie 1 vous sont fournies (elles reprennent celles de l'atelier 8), plus deux petites méthodes ajoutées à [`AnalyseLogs`](01-code-java/src/main/java/main/AnalyseLogs.java) (`listerFichiers`, `chargerAcces`) et un [`GenerateurLogs`](01-code-java/src/main/java/main/GenerateurLogs.java) adapté qui produit un fichier par routeur. Votre travail porte sur `HyperLogLog.fusionner` et la nouvelle classe [`AnalyseRouteurs`](01-code-java/src/main/java/main/AnalyseRouteurs.java) (package `main`).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier10_partie2` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `util`, `main`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml` (voir le `pom.xml` fourni). Aucun dossier de logs à copier : les tests créent leurs propres fichiers temporaires, et [`MainGrandeEchelleRouteurs`](01-code-java/src/main/java/main/MainGrandeEchelleRouteurs.java) génère les siens au premier lancement.

Chaque question est vérifiée par les tests JUnit fournis ([`HyperLogLogTest`](01-code-java/src/test/java/domaine/HyperLogLogTest.java), [`AnalyseRouteursTest`](01-code-java/src/test/java/main/AnalyseRouteursTest.java)) : exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure.

### Fusionner deux `HyperLogLog`

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez la classe [`HyperLogLog`](01-code-java/src/main/java/domaine/HyperLogLog.java) (package `domaine`) et complétez `fusionner(HyperLogLog autre)` : validez le paramètre (`Util.checkObject`) et vérifiez que les deux estimateurs ont le même nombre de registres — sinon `IllegalArgumentException` — puis remplacez chaque registre de l'estimateur courant par le maximum entre sa valeur et celle du registre correspondant de `autre` (qui ne doit pas être modifié).

### Fusion séquentielle des routeurs

**Question 2** :
Ouvrez la classe `AnalyseRouteurs` et complétez `hllPourFichier(String dossier, String nomFichier)` : elle construit un HyperLogLog local (avec le `hasher` du constructeur et `NB_BITS_INDEX` bits d'index) et y ajoute l'adresse IP de chaque accès du fichier (`analyse.chargerAcces`).

**Question 3** :
Complétez `fusionnerTousLesRouteurs(String dossier)` : elle parcourt les fichiers de l'index (`analyse.listerFichiers`), construit le HyperLogLog de chacun (Question 2) et les fusionne un à un dans un estimateur résultat.

Cette version est entièrement **synchrone** : un routeur après l'autre. C'est la référence à laquelle la version asynchrone sera comparée.

### Un `CompletableFuture` par routeur

**Question 4** :
Complétez `hllPourFichierAsync(String dossier, String nomFichier)` : elle lance `hllPourFichier` dans un traitement asynchrone (`CompletableFuture.supplyAsync`) et renvoie un `CompletableFuture<HyperLogLog>`. Comme à la partie 1, l'`IOException` doit être attrapée et relancée en `RuntimeException`.

**Question 5** :
Complétez `fusionnerTousLesRouteursAsync(String dossier)` : un `CompletableFuture` par fichier routeur (Question 4), toutes les futures combinées en cascade avec `thenCombine` pour fusionner les estimateurs locaux dans un seul résultat — sans jamais appeler `join`. Partez d'une future déjà complétée contenant un estimateur vide (`CompletableFuture.completedFuture(...)`), comme montré dans la théorie.

Réponses aux Questions 6 à 8 dans [`02-solution/10B_solutions-observations.md`](02-solution/10B_solutions-observations.md) : réfléchissez (et testez !) avant de les consulter.

**Question 6** :
Le test `resultatDeterministeSurPlusieursExecutions` passe alors que l'ordre de fin des futures change d'une exécution à l'autre. Pourquoi ?

**Question 7** :
En quoi cette situation diffère-t-elle de la race condition de l'atelier 9 ?

### Passage à l'échelle

Exécutez `MainGrandeEchelleRouteurs` (fourni) : au premier lancement, il génère 20 fichiers routeurs de 50 000 accès dans `logs-routeurs/`, puis compare la fusion séquentielle (Question 3) et la fusion asynchrone (Question 5) — mêmes estimations, temps affichés.

**Question 8** :
Comparez les deux estimations et les deux temps affichés. Pourquoi les estimations sont-elles identiques, et pourquoi la version asynchrone est-elle nettement plus rapide alors que le travail total effectué est exactement le même ?

---

*Passez à la [théorie suivante](../../11-introspection/01-partie1/11A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/10-hyperloglog-async-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
