# Atelier 10 : HyperLogLog appliqué (programmation asynchrone) – partie 2 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [Fusionner deux HyperLogLog](#fusionner-deux-hyperloglog)
4. [Un CompletableFuture par routeur](#un-completablefuture-par-routeur)
5. [Pourquoi le résultat est déterministe](#pourquoi-le-résultat-est-déterministe)

## Vidéos

1. [Threads - Completable Future](https://www.youtube.com/watch?v=EE6a93t2c3E)
2. [CompletableFuture - thenCompose vs thenApply](https://www.youtube.com/watch?v=Wz-novO_zlk)
3. [thenCombine](https://www.youtube.com/watch?v=m_AEDUA774Q)
4. [CompletableFuture - Résumé](https://www.youtube.com/watch?v=Pw5OEjXyrSI)
5. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Introduction

Dans la partie 1, un seul flux de fichiers était chargé puis estimé : la chaîne asynchrone restait linéaire. Cette partie ajoute la vraie raison pour laquelle HyperLogLog et la programmation asynchrone se marient si bien : la structure se **fusionne**. Chaque source de données (ici, un routeur réseau) peut construire son propre petit estimateur dans son coin — donc dans son propre thread — et les résultats se combinent à la fin, sans perte de précision.

C'est exactement ainsi que les bases de données distribuées (Redis avec `PFMERGE`/`PFCOUNT`, BigQuery, Redshift...) comptent des cardinalités sur des données réparties sur des dizaines de machines.

## Fusionner deux HyperLogLog

Rappel de l'atelier 8 : un HyperLogLog n'est qu'un tableau de registres, et chaque registre retient le **maximum** de zéros de tête observé pour les valeurs qui lui sont tombées dessus.

Conséquence directe : pour fusionner deux estimateurs (de même taille) qui ont vu des ensembles de valeurs différents, il suffit de prendre, registre par registre, **le maximum des deux** :

```
fusion.registres[i] = max(premier.registres[i], second.registres[i])
```

Le tableau obtenu est exactement celui qu'on aurait eu en ajoutant toutes les valeurs des deux ensembles dans un seul estimateur. Ajouter une valeur ne fait jamais que « pousser un registre vers le haut » ; peu importe donc quel estimateur l'a vue, ni combien de fois.

Deux propriétés découlent de ce `max` :

1. **Commutativité** : `fusionner(a, b)` et `fusionner(b, a)` donnent le même tableau.
2. **Associativité** : fusionner dans n'importe quel ordre de regroupement donne le même tableau.

## Un CompletableFuture par routeur

Le schéma de cette partie est un **éventail** (*scatter/gather*) plutôt qu'une chaîne :

```
                 ┌─ supplyAsync(HLL du routeur 0) ─┐
index des        ├─ supplyAsync(HLL du routeur 1) ─┤   fusion des résultats
fichiers ──────> ├─ supplyAsync(HLL du routeur 2) ─┼─> (thenCombine en cascade
                 ├─ ...                            │    ou allOf puis boucle)
                 └─ supplyAsync(HLL du routeur N) ─┘
```

Chaque fichier routeur reçoit son propre `CompletableFuture<HyperLogLog>` : lecture du fichier, construction d'un estimateur local. Pour rassembler, `thenCombine` combine deux futures dès qu'elles sont toutes les deux complétées :

```java
CompletableFuture<HyperLogLog> resultat = CompletableFuture.completedFuture(new HyperLogLog(hasher, 4));
for (String nomFichier : fichiers) {
    resultat = resultat.thenCombine(hllPourFichierAsync(dossier, nomFichier),
            (fusion, hllRouteur) -> {
                fusion.fusionner(hllRouteur);
                return fusion;
            });
}
```

`CompletableFuture.completedFuture(valeur)` crée une future déjà complétée — le point de départ neutre de la cascade (un estimateur vide).

## Pourquoi le résultat est déterministe

Avec des threads, l'ordre d'exécution est imprévisible : le routeur 7 peut finir avant le routeur 2 aujourd'hui, et après lui demain. En général, c'est la porte ouverte aux résultats non reproductibles (les race conditions de l'atelier 9).

Ici, non : la fusion étant commutative et associative, **l'ordre et le regroupement des fusions n'ont aucune influence** sur le tableau final. Le résultat asynchrone est identique au résultat séquentiel, à chaque exécution. C'est ce qui rend ce traitement parfaitement testable en JUnit, sans aléa — et c'est un critère de conception à retenir : les traitements parallèles les plus sûrs sont ceux dont l'étape de combinaison est insensible à l'ordre.

---

*Passez aux [exercices](10B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/10-hyperloglog-async-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
