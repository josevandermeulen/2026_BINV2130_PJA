# Atelier 7 : Programmation fonctionnelle – partie 2

## Objectif

L'objectif de cet atelier est de maîtriser l'opération `flatMap` sur des données hiérarchiques et de s'entraîner sur des questions d'examen utilisant l'API Stream de bout en bout.

## Concepts

1. `flatMap`
2. Collections imbriquées
3. `Map.entry`
4. Streams sur un modèle hiérarchique
5. `distinct`, `max`, `groupingBy` (révision)

## Vidéos

1. [Comment aplatir les collections imbriquées en Java avec flatMap](https://www.youtube.com/watch?v=aZHDUchqjyk)
2. [Immuabilité Java](https://www.youtube.com/watch?v=u0j64SmIoc4)
3. [Mise en pratique du design fonctionnel en Java partie 1 - L'immuabilité](https://www.youtube.com/watch?v=s6Zwdeg3NdI)
4. [Programmation fonctionnelle cas pratique en Java partie 2 - Les exceptions](https://www.youtube.com/watch?v=g0EVQYClhtA)

## Exercices

### Introduction

Cet atelier applique `flatMap` à des données hiérarchiques : des portefeuilles d'actions ([`Portfolio`](01-code-java/src/main/java/domaine/Portfolio.java), package `domaine`), puis un cas d'examen complet sur un catalogue de séries TV (`vod`).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier07_partie2` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `main`, `vod` — le package `football` sert à la question optionnelle) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez la dépendance JUnit 5 à votre `pom.xml`. Résultat attendu de l'exercice flatMap : `01-code-java/affichage_ExercicePortfolio.txt`.

### `flatMap`

La classe `Portfolio` représente un trader avec la liste d'actions qu'il possède : un [`Trader`](01-code-java/src/main/java/domaine/Trader.java) (package `domaine`) et une liste de `String` (les symboles des actions détenues). La classe [`ExercicePortfolio`](01-code-java/src/main/java/main/ExercicePortfolio.java) (package `main`) possède un attribut statique `PRICES`, une `Map` associant chaque symbole à son prix. Le résultat attendu se trouve dans `01-code-java/affichage_ExercicePortfolio.txt`.

**Question 1** :

✏️ *A corriger au tableau*

Dans la méthode `actions`, renvoyer la liste des actions de tous les portefeuilles (avec doublons).

**Question 2** :
Dans la méthode `portfolioToAction`, renvoyer une liste des actions uniques avec leur prix, triée par prix croissant (puis par symbole en cas d'égalité). Utilisez `Map.entry(k, v)` pour construire les paires clé/valeur plutôt que de créer une classe dédiée.

### Statistiques VOD

Les Questions 3 à 8 sont issues de l'examen de janvier 2026 (question « Les streams », 6 points, ± 40 minutes — soit environ 1 point et ± 7 minutes par méthode).

Vous développez [`AnalyticsService`](01-code-java/src/main/java/vod/AnalyticsService.java) (package `vod`), un module de statistiques destiné à une plateforme de VOD. Le modèle de données, lui aussi dans le package `vod`, est hiérarchique : [`TvSeries`](01-code-java/src/main/java/vod/TvSeries.java) (`title`, `genre`, `episodes`) → [`Episode`](01-code-java/src/main/java/vod/Episode.java) (`title`, `duration`, `cast`) → [`Actor`](01-code-java/src/main/java/vod/Actor.java) (`name`, `nationality`).

Implémentez les méthodes de `AnalyticsService`, en remplaçant chaque `throw new UnsupportedOperationException` par votre implémentation. Utilisez exclusivement l'API Stream (aucune boucle `for`/`while`) et ne modifiez pas la signature des méthodes. Vous pouvez vérifier votre implémentation en lançant le `main` de `ExamRunner` (fourni dans le package `vod`) et en comparant la sortie avec celle donnée ci-dessous.

**Question 3** :
Dans la méthode `findSeriesWithTitle`, renvoyer toutes les séries dont le titre contient le mot « Day » (sensible à la casse).

**Question 4** :
Dans la méthode `getEpisodeDurations`, renvoyer, à partir d'une série, la liste des durées (en minutes) de chaque épisode.

**Question 5** :
Dans la méthode `getAllActorsInSeries`, renvoyer la liste de tous les acteurs ayant joué dans une série donnée (doublons autorisés : un acteur jouant dans 2 épisodes apparaît 2 fois).

**Question 6** :
Dans la méthode `getDistinctNationalities`, renvoyer, à partir d'une liste d'acteurs, la liste des nationalités présentes, sans doublon.

**Question 7** :
Dans la méthode `findLongestEpisodeDuration`, renvoyer la durée (en minutes) de l'épisode le plus long de la liste fournie, sous forme d'`Optional<Integer>` (vide si la liste est vide).

**Question 8** :
Dans la méthode `countSeriesByGenre`, grouper les séries par genre et compter le nombre de séries par genre, sous forme de `Map<String, Long>`.

Sortie attendue (`ExamRunner`) :

```
--- Question 3: Series with 'Day' ---
Independence Day

--- Question 4: Durations (Breaking Bad) ---
58 min
48 min

--- Question 5: Actors (Breaking Bad) ---
Bryan Cranston (USA)
Cillian Murphy (Ireland)
Bryan Cranston (USA)
Omar Sy (France)

--- Question 6: Distinct Nationalities ---
USA
Ireland
France

--- Question 7: Longest Episode Duration ---
Max duration: 145

--- Question 8: Group by Genre ---
Sci-Fi : 4
Drama : 3
Fantasy : 2
Comedy : 1
```

## Parties optionnelles

### Couples action/trader

Le résultat attendu se trouve dans `01-code-java/affichage_ExercicePortfolio.txt`.

**Question 9** :
Dans la méthode `actionTraderPairs` de la classe `ExercicePortfolio`, renvoyer une liste de couples `(action, trader)` — un couple par action détenue par un trader. Utilisez `Map.entry` pour les couples.

**Question 10** :
Dans la méthode `tradersByAction`, renvoyer une `Map` associant à chaque action la liste des traders qui la possèdent.

### Tournois de football

Les Questions 11 à 16 sont issues de l'examen de septembre 2026 (question « Les streams », 6 points, ± 40 minutes). Utilisez exclusivement l'API Stream (aucune boucle `for`/`while`) et ne modifiez pas la signature des méthodes. Récupérez le package `football` (fourni dans `01-code-java/`) et mettez-le dans vos packages. Vous pouvez vérifier votre implémentation en lançant le `main` de `ExamRunner` (fourni) et en comparant la sortie avec celle donnée ci-dessous.

Vous développez [`StatsService`](01-code-java/src/main/java/football/StatsService.java) (package `football`), un module de statistiques destiné à une plateforme d'analyse de tournois de football. Le modèle de données, dans le même package, est hiérarchique : [`Tournament`](01-code-java/src/main/java/football/Tournament.java) (`competition`, `matches`) → [`Match`](01-code-java/src/main/java/football/Match.java) (`goalsHome`, `goalsAway`, `scorers`) → [`Player`](01-code-java/src/main/java/football/Player.java) (`name`, `nationality`).

**Question 11** :
Dans la méthode `findTournamentsWithName`, renvoyer tous les tournois dont le nom contient le mot « Cup » (sensible à la casse).

**Question 12** :
Dans la méthode `getMatchGoals`, renvoyer, à partir d'un tournoi, la liste du nombre total de buts de chaque match (`goalsHome` + `goalsAway`).

**Question 13** :
Dans la méthode `getAllScorersInTournament`, renvoyer la liste de tous les buteurs ayant joué dans un tournoi donné (doublons autorisés : un joueur ayant marqué dans 2 matchs apparaît 2 fois).

**Question 14** :
Dans la méthode `getDistinctNationalities`, renvoyer, à partir d'une liste de joueurs, la liste des nationalités présentes, sans doublon (dans l'ordre d'apparition).

**Question 15** :
Dans la méthode `findHighestGoalCount`, renvoyer le nombre total de buts (`goalsHome` + `goalsAway`) du match le plus prolifique, sous forme d'`Optional<Integer>` (vide si la liste est vide).

**Question 16** :
Dans la méthode `countTournamentsByCompetition`, grouper les tournois par type de compétition et compter le nombre de tournois par type, sous forme de `Map<String, Long>`.

Sortie attendue (`ExamRunner`) :

```
--- Question 11: Tournaments with 'Cup' ---
World Cup
FA Cup
French Cup

--- Question 12: Goals per match (Champions League) ---
3 goals
2 goals

--- Question 13: Scorers (Champions League) ---
Mbappe (France)
Haaland (Norway)
Bellingham (England)
Mbappe (France)
Salah (Egypt)

--- Question 14: Distinct Nationalities ---
France
Norway
England
Egypt

--- Question 15: Highest Goal Count ---
Max goals: 5

--- Question 16: Count by Competition ---
League : 4
Cup : 3
Continental : 2
Friendly : 1
```

Note : l'ordre des clés dans la `Map` de la Question 16 n'est pas garanti (itération d'une `HashMap`) — seuls les comptes par compétition comptent, pas l'ordre d'affichage.

---

*Passez à la [théorie suivante](../../08-lecture-fichiers/01-partie1/08A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/07-programmation-fonctionnelle-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
