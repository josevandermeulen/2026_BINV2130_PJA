# Atelier 7 : Programmation fonctionnelle – séance 2 : solutions

*Énoncé : [`07B_2_exercices.md`](../AJ_atelier07_seance2/07B_2_exercices.md) — théorie : [`07B_1_theorie.md`](../AJ_atelier07_seance2/07B_1_theorie.md).*

## `flatMap`

### Question 1

**Énoncé :** Dans la méthode `actions`, renvoyer la liste des actions de tous les portefeuilles (avec doublons).

[`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    public static void main(String[] args) {
        // ...
        System.out.println("===== Question 1 =====");
        System.out.println("Actions de tous les portfolios (avec doublons):");

        List<String> actions = actions(portfolios);
        System.out.println("Il y a " + actions.size() + " actions au total dans tous les portfolios.");
        actions.forEach(System.out::println);
        // ...
    }
}
```

*actions — flatMap pour aplatir les listes d'actions* — [`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    /**
     * Renvoie la liste des actions de tous les portefeuilles (avec doublons).
     */
    static List<String> actions(List<Portfolio> portfolios) {
        return portfolios.stream()
                .flatMap(portfolio -> portfolio.getActions().stream())
                .toList();
    }
    // ...
}
```

*Vérifié par : `ExercicePortfolioTest` (classe `Question1`).*

### Question 2

**Énoncé :** Dans la méthode `portfolioToAction`, renvoyer une liste des actions uniques avec leur prix, triée par prix croissant (puis par symbole en cas d'égalité). Utilisez `Map.entry(k, v)` pour construire les paires clé/valeur plutôt que de créer une classe dédiée.

[`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    public static void main(String[] args) {
        // ...
        System.out.println("\n===== Question 2 =====");
        System.out.println("\nActions uniques avec leur prix, triées par prix croissant:");
        List<Map.Entry<String, Double>> entries = portfolioToAction(portfolios, PRICES);
        System.out.println("Il y a " + entries.size() + " actions uniques au total.");
        entries.forEach(System.out::println);
        // ...
    }
}
```

*portfolioToAction — distinct, Map.entry et tri composé* — [`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    /**
     * Renvoie une liste des actions uniques avec leur prix, triée par prix croissant
     * (puis par symbole en cas d'égalité).
     *
     * Au lieu de créer une classe ActionPrice, on utilise ici la classe utilitaire
     * Map.entry fournie par Java. Map.entry(k, v) construit un couple clé/valeur,
     * pratique pour manipuler des paires simples sans créer une nouvelle classe.
     */
    static List<Map.Entry<String, Double>> portfolioToAction(List<Portfolio> portfolios, Map<String, Double> prices) {
        return portfolios.stream()
                .flatMap(portfolio -> portfolio.getActions().stream())
                .distinct()
                .filter(prices::containsKey)
                .map(symbol -> Map.entry(symbol, prices.get(symbol)))
                .sorted(Comparator.comparingDouble((Map.Entry<String, Double> e) -> e.getValue())
                        .thenComparing(Map.Entry::getKey))
                .toList();
    }
    // ...
}
```

*Vérifié par : `ExercicePortfolioTest` (classe `Question2`).*

## Statistiques VOD

### Question 3

**Énoncé :** Dans la méthode `findSeriesWithTitle`, renvoyer toutes les séries dont le titre contient le mot « Day » (sensible à la casse).

*findSeriesWithTitle* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * Récupère toutes les séries dont le titre contient le mot "Day" (sensible à la casse).
     *
     * @param database La liste complète des séries.
     * @return Une liste filtrée de séries.
     */
    public List<TvSeries> findSeriesWithTitle(List<TvSeries> database) {
        return database.stream()
                .filter(series -> series.getTitle().contains("Day"))
                .toList();
    }
    // ...
}
```

### Question 4

**Énoncé :** Dans la méthode `getEpisodeDurations`, renvoyer, à partir d'une série, la liste des durées (en minutes) de chaque épisode.

*getEpisodeDurations* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * À partir d'une série, retourne la liste des durées de chaque épisode.
     *
     * @param series La série à analyser.
     * @return Une liste d'entiers (minutes).
     */
    public List<Integer> getEpisodeDurations(TvSeries series) {
        return series.getEpisodes().stream()
                .map(Episode::getDuration)
                .toList();
    }
    // ...
}
```

### Question 5

**Énoncé :** Dans la méthode `getAllActorsInSeries`, renvoyer la liste de tous les acteurs ayant joué dans une série donnée (doublons autorisés : un acteur jouant dans 2 épisodes apparaît 2 fois).

*getAllActorsInSeries* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * Récupère la liste de tous les acteurs ayant joué dans une série donnée.
     * Note : les doublons sont autorisés (si un acteur joue dans 2 épisodes, il apparaît 2 fois).
     *
     * @param series La série source.
     * @return La liste complète des acteurs.
     */
    public List<Actor> getAllActorsInSeries(TvSeries series) {
        return series.getEpisodes().stream()
                .flatMap(episode -> episode.getCast().stream())
                .toList();
    }
    // ...
}
```

### Question 6

**Énoncé :** Dans la méthode `getDistinctNationalities`, renvoyer, à partir d'une liste d'acteurs, la liste des nationalités présentes, sans doublon.

*getDistinctNationalities* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * À partir d'une liste d'acteurs, récupère la liste des nationalités présentes.
     * La liste retournée ne doit pas contenir de doublons.
     *
     * @param actors Une liste d'acteurs.
     * @return La liste des nationalités uniques.
     */
    public List<String> getDistinctNationalities(List<Actor> actors) {
        return actors.stream()
                .map(Actor::getNationality)
                .distinct()
                .toList();
    }
    // ...
}
```

### Question 7

**Énoncé :** Dans la méthode `findLongestEpisodeDuration`, renvoyer la durée (en minutes) de l'épisode le plus long de la liste fournie, sous forme d'`Optional<Integer>` (vide si la liste est vide).

*findLongestEpisodeDuration* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * Trouve la durée (en minutes) de l'épisode le plus long de la liste fournie.
     *
     * @param episodes Une liste plate d'épisodes.
     * @return Un `Optional` contenant la durée maximale (ou vide si la liste est vide).
     */
    public Optional<Integer> findLongestEpisodeDuration(List<Episode> episodes) {
        return episodes.stream()
                .map(Episode::getDuration)
                .max(Integer::compareTo);
    }
    // ...
}
```

### Question 8

**Énoncé :** Dans la méthode `countSeriesByGenre`, grouper les séries par genre et compter le nombre de séries par genre, sous forme de `Map<String, Long>`.

*countSeriesByGenre* — [`src/main/java/vod/AnalyticsService.java`](src/main/java/vod/AnalyticsService.java) :

```java
public class AnalyticsService {
    // ...
    /**
     * Groupe les séries par leur genre et compte le nombre de séries par genre.
     *
     * @param database La liste complète des séries.
     * @return Une `Map` où la clé est le genre et la valeur le nombre de séries de ce genre.
     */
    public Map<String, Long> countSeriesByGenre(List<TvSeries> database) {
        return database.stream()
                .collect(Collectors.groupingBy(TvSeries::getGenre, Collectors.counting()));
    }
}
```

## Parties optionnelles

### Tournois de football

#### Question 9

**Énoncé :** Dans la méthode `findTournamentsWithName`, renvoyer tous les tournois dont le nom contient le mot « Cup » (sensible à la casse).

*findTournamentsWithName* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * Récupère tous les tournois dont le nom contient le mot "Cup" (sensible à la casse).
     *
     * @param database La liste complète des tournois.
     * @return Une liste filtrée de tournois.
     */
    public List<Tournament> findTournamentsWithName(List<Tournament> database) {
        return database.stream()
                .filter(tournament -> tournament.getName().contains("Cup"))
                .toList();
    }
    // ...
}
```

#### Question 10

**Énoncé :** Dans la méthode `getMatchGoals`, renvoyer, à partir d'un tournoi, la liste du nombre total de buts de chaque match (`goalsHome` + `goalsAway`).

*getMatchGoals* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * À partir d'un tournoi, retourne la liste du nombre total de buts de chaque match
     * (`goalsHome` + `goalsAway`).
     *
     * @param tournament Le tournoi à analyser.
     * @return Une liste d'entiers (nombre total de buts par match).
     */
    public List<Integer> getMatchGoals(Tournament tournament) {
        return tournament.getMatches().stream()
                .map(match -> match.getGoalsHome() + match.getGoalsAway())
                .toList();
    }
    // ...
}
```

#### Question 11

**Énoncé :** Dans la méthode `getAllScorersInTournament`, renvoyer la liste de tous les buteurs ayant joué dans un tournoi donné (doublons autorisés : un joueur ayant marqué dans 2 matchs apparaît 2 fois).

*getAllScorersInTournament* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * Récupère la liste de tous les buteurs ayant joué dans un tournoi donné.
     * Note : les doublons sont autorisés (si un joueur a marqué dans 2 matchs, il apparaît 2 fois).
     *
     * @param tournament Le tournoi source.
     * @return La liste complète des buteurs (avec doublons éventuels).
     */
    public List<Player> getAllScorersInTournament(Tournament tournament) {
        return tournament.getMatches().stream()
                .flatMap(match -> match.getScorers().stream())
                .toList();
    }
    // ...
}
```

#### Question 12

**Énoncé :** Dans la méthode `getDistinctNationalities`, renvoyer, à partir d'une liste de joueurs, la liste des nationalités présentes, sans doublon (dans l'ordre d'apparition).

*getDistinctNationalities* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * À partir d'une liste de joueurs, récupère la liste des nationalités présentes.
     * La liste retournée ne doit pas contenir de doublons.
     *
     * @param players Une liste de joueurs.
     * @return La liste des nationalités uniques (dans l'ordre d'apparition).
     */
    public List<String> getDistinctNationalities(List<Player> players) {
        return players.stream()
                .map(Player::getNationality)
                .distinct()
                .toList();
    }
    // ...
}
```

#### Question 13

**Énoncé :** Dans la méthode `findHighestGoalCount`, renvoyer le nombre total de buts (`goalsHome` + `goalsAway`) du match le plus prolifique, sous forme d'`Optional<Integer>` (vide si la liste est vide).

*findHighestGoalCount* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * Trouve le nombre total de buts (`goalsHome` + `goalsAway`) du match le plus prolifique.
     *
     * @param matches Une liste plate de matchs.
     * @return Un `Optional` contenant le nombre de buts maximal (ou vide si la liste est vide).
     */
    public Optional<Integer> findHighestGoalCount(List<Match> matches) {
        return matches.stream()
                .map(match -> match.getGoalsHome() + match.getGoalsAway())
                .max(Integer::compareTo);
    }
    // ...
}
```

#### Question 14

**Énoncé :** Dans la méthode `countTournamentsByCompetition`, grouper les tournois par type de compétition et compter le nombre de tournois par type, sous forme de `Map<String, Long>`.

*countTournamentsByCompetition* — [`src/main/java/football/StatsService.java`](src/main/java/football/StatsService.java) :

```java
public class StatsService {
    // ...
    /**
     * Groupe les tournois par leur type de compétition et compte le nombre de tournois par type.
     *
     * @param database La liste complète des tournois.
     * @return Une `Map` où la clé est le type de compétition et la valeur le nombre de tournois de ce type.
     */
    public Map<String, Long> countTournamentsByCompetition(List<Tournament> database) {
        return database.stream()
                .collect(Collectors.groupingBy(Tournament::getCompetition, Collectors.counting()));
    }
}
```

### Couples action/trader

#### Question 15

**Énoncé :** Dans la méthode `actionTraderPairs` de la classe `ExercicePortfolio`, renvoyer une liste de couples `(action, trader)` — un couple par action détenue par un trader. Utilisez `Map.entry` pour les couples.

[`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    public static void main(String[] args) {
        // ...
        System.out.println("\n===== Question 15 =====");
        System.out.println("\nCouples (action, trader):");
        actionTraderPairs(portfolios).forEach(System.out::println);
        // ...
    }
}
```

*actionTraderPairs — flatMap avec un map imbriqué* — [`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    /**
     * Renvoie une liste de couples (action, trader). Chaque couple indique qu'un
     * trader possède une action donnée.
     */
    static List<Map.Entry<String, Trader>> actionTraderPairs(List<Portfolio> portfolios) {
        return portfolios.stream()
                .flatMap(portfolio -> portfolio.getActions().stream()
                        .map(action -> Map.entry(action, portfolio.getTrader())))
                .toList();
    }
    // ...
}
```

*Vérifié par : `ExercicePortfolioTest` (classe `Question15`).*

#### Question 16

**Énoncé :** Dans la méthode `tradersByAction`, renvoyer une `Map` associant à chaque action la liste des traders qui la possèdent.

[`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    public static void main(String[] args) {
        // ...
        System.out.println("\n===== Question 16 =====");
        System.out.println("\nMap associant à chaque action la liste des traders qui la possèdent:");
        Map<String, List<Trader>> actionsMap = tradersByAction(portfolios);
        actionsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        System.out.println("\nIl y a " + actionsMap.size() + " actions au total dans tous les portfolios.");
        double moyenne = actionsMap.values().stream()
                .mapToInt(List::size)
                .average()
                .orElse(0.0);

        System.out.printf("Chaque action est possédée en moyenne par %.2f traders.%n", moyenne);
    }
    // ...
}
```

*tradersByAction — groupingBy + mapping* — [`src/main/java/main/ExercicePortfolio.java`](src/main/java/main/ExercicePortfolio.java) :

```java
public class ExercicePortfolio {
    // ...
    /**
     * Renvoie une map associant à chaque action la liste des traders qui la possèdent.
     */
    static Map<String, List<Trader>> tradersByAction(List<Portfolio> portfolios) {
        return actionTraderPairs(portfolios).stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    }
}
```

*Vérifié par : `ExercicePortfolioTest` (classe `Question16`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
