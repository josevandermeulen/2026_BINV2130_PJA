# Atelier 6 : Streams – les bases – séance 2 : solutions

*Énoncé : [`06B_2_exercices.md`](../AJ_atelier06_seance2/06B_2_exercices.md) — théorie : [`06B_1_theorie.md`](../AJ_atelier06_seance2/06B_1_theorie.md).*

## Gestion du vide (type `Optional`)

### Question 1

**Énoncé :** Dans la méthode `optional1` (exercice trouvant la valeur max), utilisez `orElse` et renvoyez -1 s'il n'y a pas de transactions.

*valeur max avec orElse* — [`src/main/java/main/ExercicesOptional.java`](src/main/java/main/ExercicesOptional.java) :

```java
public class ExercicesOptional {
    // ...
    /**
     * Cherche la valeur de transaction la plus élevée, par réduction sans valeur neutre.
     *
     * @param transactions la liste des transactions à réduire
     * @return la valeur maximale, ou -1 si la liste est vide
     */
    public Integer optional1(List<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .orElse(-1);
    }
    // ...
}
```

*Vérifié par : `ExercicesOptionalTest` (classe `Question1`).*

### Question 2

**Énoncé :** Dans la méthode `optional2` (transaction de valeur minimale), renvoyez directement l'`Optional<Transaction>` obtenu après réduction (vide s'il n'y a pas de transactions).

*transaction minimale en Optional* — [`src/main/java/main/ExercicesOptional.java`](src/main/java/main/ExercicesOptional.java) :

```java
public class ExercicesOptional {
    // ...
    /**
     * Cherche la transaction de valeur la plus faible, par réduction sans valeur neutre.
     *
     * @param transactions la liste des transactions à réduire
     * @return la transaction de valeur minimale, ou un Optional vide si la liste est vide
     */
    public Optional<Transaction> optional2(List<Transaction> transactions) {
        return transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

}
```

*Vérifié par : `ExercicesOptionalTest` (classe `Question2`).*

## Grouper

### Question 3

**Énoncé :** Dans la méthode `groupBy1`, construire une `Map<Trader, List<Transaction>>` — transactions du trader.

*transactions par trader* — [`src/main/java/main/ExerciceGroupingBy.java`](src/main/java/main/ExerciceGroupingBy.java) :

```java
public class ExerciceGroupingBy {
    private final List<Transaction> transactions;
    // ...
    /**
     * Regroupe les transactions par trader.
     *
     * @return une map associant à chaque trader la liste de ses transactions
     */
    public Map<Trader, List<Transaction>> groupBy1() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader));
    }
    // ...
}
```

*Vérifié par : `ExerciceGroupingByTest` (classe `Question3`).*

### Question 4

**Énoncé :** Dans la méthode `groupBy2`, construire une `Map<Trader, Long>` — nombre de transactions de ce trader. Pour ceci il faudra utiliser la méthode `counting` dans le collector.

*comptage par trader (counting)* — [`src/main/java/main/ExerciceGroupingBy.java`](src/main/java/main/ExerciceGroupingBy.java) :

```java
public class ExerciceGroupingBy {
    private final List<Transaction> transactions;
    // ...
    /**
     * Compte les transactions de chaque trader.
     *
     * @return une map associant à chaque trader son nombre de transactions
     */
    public Map<Trader, Long> groupBy2() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
    }
    // ...
}
```

*Vérifié par : `ExerciceGroupingByTest` (classe `Question4`).*

### Question 5

**Énoncé :** Dans la méthode `groupBy3`, construire une `Map<TransactionsLevel, List<Transaction>>` — considérant l'enum `TransactionsLevel {VERY_HI, HI, LO, ME}`, les transactions sont réparties selon leur valeur : `VERY_HI` à partir de 1000, `HI` de 800 à moins de […]

*regroupement par niveau* — [`src/main/java/main/ExerciceGroupingBy.java`](src/main/java/main/ExerciceGroupingBy.java) :

```java
public class ExerciceGroupingBy {
    private final List<Transaction> transactions;
    // ...
    /**
     * Répartit les transactions selon leur niveau de valeur.
     *
     * @return une map associant à chaque niveau la liste des transactions correspondantes
     */
    public Map<TransactionsLevel, List<Transaction>> groupBy3() {
        return transactions.stream()
                .collect(Collectors.groupingBy(tr -> {
                    if (tr.getValue() >= 1000) {
                        return TransactionsLevel.VERY_HI;
                    }

                    if (tr.getValue() >= 800) {
                        return TransactionsLevel.HI;
                    }

                    if (tr.getValue() >= 600) {
                        return TransactionsLevel.ME;
                    }

                    return TransactionsLevel.LO;
                }));
    }
    // ...
}
```

*Vérifié par : `ExerciceGroupingByTest` (classe `Question5`).*

## Exercices panachés

### Question 6

**Énoncé :** Dans la méthode `exercice1`, renvoyer la valeur de la transaction avec la plus grande valeur parmi celles effectuées à Cambridge, sous forme d'`Optional<Integer>` (vide si aucune transaction à Cambridge).

*valeur max à Cambridge* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche la plus grande valeur parmi les transactions effectuées à Cambridge.
     *
     * @return la valeur maximale, ou un Optional vide s'il n'y a aucune transaction à Cambridge
     */
    public Optional<Integer> exercice1() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer::max);
    }
    // ...
}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question6`).*

### Question 7

**Énoncé :** Dans la méthode `exercice2`, renvoyer combien de transactions chaque trader basé à Cambridge a effectuées, sous forme de `Map<Trader, Long>`.

*comptage par trader de Cambridge* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Compte les transactions de chaque trader basé à Cambridge.
     *
     * @return une map associant à chaque trader de Cambridge son nombre de transactions
     */
    public Map<Trader, Long> exercice2() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
    }
    // ...
}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question7`).*

### Question 8

**Énoncé :** Dans la méthode `exercice3`, renvoyer, parmi les transactions dont la valeur est supérieure à 500, le nom du trader ayant le nom le plus long, sous forme d'`Optional<String>`. En cas d'ex æquo, le premier trader de la liste.

*nom le plus long (valeur > 500)* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche, parmi les transactions de valeur supérieure à 500, le nom de trader le plus long.
     *
     * @return le nom le plus long, ou un Optional vide si aucune transaction ne dépasse 500
     */
    public Optional<String> exercice3() {
        return transactions.stream()
                .filter(t -> t.getValue() > 500)
                .map(t -> t.getTrader().getName())
                .sorted((n1, n2) -> Integer.compare(n2.length(), n1.length()))
                .findFirst();
    }
    // ...
}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question8`).*

### Question 9

**Énoncé :** Dans la méthode `exercice4`, renvoyer la moyenne des valeurs des transactions pour chaque ville où sont basés les traders, sous forme de `Map<String, Double>`.

*moyenne des valeurs par ville* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Calcule la moyenne des valeurs de transactions pour chaque ville où sont basés les traders.
     *
     * @return une map associant à chaque ville la moyenne des valeurs de ses transactions
     */
    public Map<String, Double> exercice4() {
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getTrader().getCity(),
                        Collectors.averagingInt(Transaction::getValue)
                ));
    }
    // ...
}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question9`).*

### Question 10

**Énoncé :** Dans la méthode `exercice5`, renvoyer la transaction de plus faible valeur parmi celles effectuées à Milan, sous forme d'`Optional<Transaction>` (vide si aucune transaction à Milan).

*transaction minimale à Milan* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche la transaction de plus faible valeur parmi celles effectuées à Milan.
     *
     * @return la transaction minimale, ou un Optional vide s'il n'y a aucune transaction à Milan
     */
    public Optional<Transaction> exercice5() {
        return transactions.stream()
                .filter(t -> t.getTrader().getCity().equals("Milan"))
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }
    // ...
}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question10`).*

### Question 11

**Énoncé :** Dans la méthode `exercice6`, renvoyer les transactions regroupées par année, sous forme de `Map<Integer, List<Transaction>>`.

*transactions par année* — [`src/main/java/main/ExercicesPanaches.java`](src/main/java/main/ExercicesPanaches.java) :

```java
public class ExercicesPanaches {
    private final List<Transaction> transactions;
    // ...
    /**
     * Regroupe les transactions par année.
     *
     * @return une map associant à chaque année la liste de ses transactions
     */
    public Map<Integer, List<Transaction>> exercice6() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear));
    }

}
```

*Vérifié par : `ExercicesPanachesTest` (classe `Question11`).*

## Parties optionnelles

### Total par trader

#### Question 12

**Énoncé :** Dans la méthode `traderValeurTotaleMax` de la classe `ExerciceGroupingBy`, renvoyer le trader ayant généré la plus grande valeur totale de transactions (somme des valeurs de toutes ses transactions) — et non celui possédant la transaction individuelle la […]

*trader à la plus grande valeur totale de transactions* — [`src/main/java/main/ExerciceGroupingBy.java`](src/main/java/main/ExerciceGroupingBy.java) :

```java
public class ExerciceGroupingBy {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche le trader dont la somme des valeurs de transactions est la plus élevée.
     *
     * @return le trader au total le plus élevé, ou null si la liste est vide
     */
    public Trader traderValeurTotaleMax() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.summingInt(Transaction::getValue)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
    // ...
}
```

*Vérifié par : `ExerciceGroupingByTest` (classe `Question12`).*

### Trader dominant par ville

#### Question 13

**Énoncé :** Dans la méthode `villeTraderMaxTotal` de la classe `ExerciceGroupingBy`, construire une `Map<String, Trader>` associant à chaque ville le trader y ayant généré la plus grande valeur totale de transactions. Combinez deux niveaux de `groupingBy` : un premier […]

*trader à la plus grande valeur totale par ville* — [`src/main/java/main/ExerciceGroupingBy.java`](src/main/java/main/ExerciceGroupingBy.java) :

```java
public class ExerciceGroupingBy {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche, pour chaque ville, le trader y ayant généré la plus grande valeur totale de transactions.
     *
     * @return une map associant à chaque ville le trader au total le plus élevé
     */
    public Map<String, Trader> villeTraderMaxTotal() {
        return transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getTrader().getCity(),
                        Collectors.collectingAndThen(
                                Collectors.groupingBy(Transaction::getTrader,
                                        Collectors.summingInt(Transaction::getValue)),
                                totalParTrader -> totalParTrader.entrySet().stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElse(null))));
    }
}
```

*Vérifié par : `ExerciceGroupingByTest` (classe `Question13`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
