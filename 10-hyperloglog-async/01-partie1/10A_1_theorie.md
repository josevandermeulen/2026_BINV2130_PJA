# Atelier 10 : HyperLogLog appliqué (programmation asynchrone) – partie 1 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [Rappel : CompletableFuture](#rappel--completablefuture)
4. [Appliquer l'asynchrone à un traitement de fichiers](#appliquer-lasynchrone-à-un-traitement-de-fichiers)

## Vidéos

1. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
2. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
3. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
4. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)
5. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Introduction

Cet atelier n'introduit **aucune nouvelle théorie** : il applique la programmation asynchrone de l'atelier 9 (partie 2) au cas HyperLogLog de l'atelier 8 (partie 2). Les deux fiches de référence restent :

1. [`../../09-threads-completablefuture/02-partie2/09B_1_theorie.md`](../../09-threads-completablefuture/02-partie2/09B_1_theorie.md) pour `CompletableFuture` ;
2. [`../../08-lecture-fichiers/02-partie2/08B_1_theorie.md`](../../08-lecture-fichiers/02-partie2/08B_1_theorie.md) pour HyperLogLog (registres, zéros de tête, formule d'estimation).

## Rappel : CompletableFuture

Les trois briques utilisées dans cette partie :

```java
// démarrer un traitement dans un autre thread, qui produira une valeur plus tard
CompletableFuture<List<Acces>> future = CompletableFuture.supplyAsync(() -> chargerLesAcces());

// décrire la suite du traitement, sans bloquer : transformer la valeur quand elle arrivera
CompletableFuture<Long> estimation = future.thenApply(acces -> estimer(acces));

// bloquer et récupérer la valeur — uniquement quand on en a vraiment besoin
Long valeur = estimation.join();
```

Deux pièges à garder en tête :

1. Une lambda passée à `supplyAsync` ne peut pas laisser s'échapper une exception *checked* comme `IOException` : il faut l'attraper et relancer une exception *unchecked* (`RuntimeException`).
2. Tant qu'on n'appelle pas `join()`, rien ne bloque : le programme principal continue pendant que le chargement s'exécute dans un autre thread. C'est tout l'intérêt.

## Appliquer l'asynchrone à un traitement de fichiers

Lire des fichiers est une opération d'**entrée/sortie** : le programme passe l'essentiel de son temps à attendre le disque. C'est le cas d'école de la programmation asynchrone vu en introduction de l'atelier 9 : pendant que le chargement s'exécute dans un thread du pool, le thread principal reste libre (pour d'autres traitements, une interface utilisateur, d'autres requêtes...).

Le schéma de cette partie est une simple **chaîne** :

```
supplyAsync(charger les accès) ──thenApply──> estimer les IP uniques ──join──> résultat
```

L'estimation elle-même ne change pas d'un iota par rapport à l'atelier 8 : mêmes classes `HyperLogLog`, `Hasher`, `AnalyseIp`. Seule la façon de **lancer** le traitement change.

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 23/11/2026 à 20h.*

*Passez aux [exercices](10A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/10-hyperloglog-async-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
