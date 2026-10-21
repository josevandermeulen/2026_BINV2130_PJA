# Atelier 6 : Streams – les bases – séance 1 : solutions

*Énoncé : [`06A_2_exercices.md`](../AJ_atelier06_seance1/06A_2_exercices.md) — théorie : [`06A_1_theorie.md`](../AJ_atelier06_seance1/06A_1_theorie.md).*

## Écrivons quelques prédicats

### Question 1

**Énoncé :** Dans la méthode `predicats1`, construire la liste de toutes les transactions de 2011.

*transactions de 2011* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Filtre les transactions effectuées en 2011.
     *
     * @return la liste des transactions dont l'année vaut 2011
     */
    public List<Transaction> predicats1() {
        return transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question1`).*

### Question 2

**Énoncé :** Dans la méthode `predicats2`, construire la liste de toutes les transactions dont la valeur est > 600.

*transactions de valeur > 600* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Filtre les transactions de valeur strictement supérieure à 600.
     *
     * @return la liste des transactions dont la valeur est > 600
     */
    public List<Transaction> predicats2() {
        return transactions.stream()
                .filter(t -> t.getValue() > 600)
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question2`).*

### Question 3

**Énoncé :** Dans la méthode `predicats3`, construire la liste de toutes les transactions de Raoul.

*transactions de Raoul* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Filtre les transactions effectuées par le trader Raoul.
     *
     * @return la liste des transactions dont le trader se nomme Raoul
     */
    public List<Transaction> predicats3() {
        return transactions.stream()
                .filter(t -> t.getTrader().getName().equals("Raoul"))
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question3`).*

## Comprendre la boucle implicite des `Stream`

### Question 4

**Énoncé :** Ouvrez la classe `ExercicesEmployes`. La méthode `listeDesHommes` est à écrire ; en voici la version itérative :

*boucle implicite* — [`src/main/java/main/ExercicesEmployes.java`](src/main/java/main/ExercicesEmployes.java) :

```java
public class ExercicesEmployes {
    // ...
    /**
     * Filtre les employés de genre masculin.
     *
     * @param employes la liste des employés à filtrer
     * @return la liste des employés dont le genre est HOMME
     */
    public List<Employe> listeDesHommes(List<Employe> employes) {
        return employes.stream()
                .filter(e -> e.getGenre() == Genre.HOMME)
                .collect(Collectors.toList());
    }
}
```

*Vérifié par : `ExercicesEmployesTest` (classe `Question4`).*

## Transformer (`map`)

### Question 5

**Énoncé :** Dans la méthode `map1`, construire la liste des villes où travaillent les courtiers (sans doublon).

*villes sans doublon* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Extrait les villes où travaillent les courtiers, sans doublon.
     *
     * @return la liste des villes distinctes
     */
    public List<String> map1() {
        return transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question5`).*

### Question 6

**Énoncé :** Dans la méthode `map2`, construire la liste de tous les courtiers de Cambridge (sans doublon).

*courtiers de Cambridge* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Extrait les courtiers travaillant à Cambridge, sans doublon.
     *
     * @return la liste des traders distincts dont la ville est Cambridge
     */
    public List<Trader> map2() {
        return transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question6`).*

### Question 7

**Énoncé :** Dans la méthode `map3`, construire une `String` contenant tous les noms des traders (sans doublon) séparés par une virgule. Comme il s'agit d'une `String` et non d'une liste, utilisez la méthode `joining` dans votre `collect`.

*noms des traders joints* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Assemble les noms des traders, sans doublon, en une seule chaîne.
     *
     * @return les noms distincts séparés par une virgule et une espace
     */
    public String map3() {
        return transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .collect(Collectors.joining(", "));
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question7`).*

## Trier

### Question 8

**Énoncé :** Dans la méthode `sort1`, construire la liste de toutes les transactions triées par ordre croissant de valeurs.

*transactions triées par valeur* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Trie les transactions par valeur croissante.
     *
     * @return la liste des transactions triées par ordre croissant de valeur
     */
    public List<Transaction> sort1() {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question8`).*

### Question 9

**Énoncé :** Dans la méthode `sort2`, construire une `String` contenant tous les noms de traders (sans doublon) triés par ordre alphabétique.

*noms triés alphabétiquement* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Assemble les noms des traders, sans doublon, triés par ordre alphabétique.
     *
     * @return les noms distincts triés, séparés par une virgule et une espace
     */
    public String sort2() {
        return transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining(", "));
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question9`).*

## Réduire (`reduce`)

### Question 10

**Énoncé :** Dans la méthode `reduce1`, renvoyer la valeur max des transactions.

*valeur max (reduce)* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche la valeur de transaction la plus élevée, par réduction.
     *
     * @return la valeur maximale, ou 0 si la liste est vide
     */
    public Integer reduce1() {
        return transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
    }
    // ...
}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question10`).*

### Question 11

**Énoncé :** Dans la méthode `reduce2`, renvoyer la transaction dont la valeur est la plus petite. Attention : on demande bien de renvoyer la transaction et non sa valeur ! Vous ne pouvez pas utiliser la méthode `min`. Au besoin, créez une transaction « neutre » avec […]

*transaction de valeur minimale (reduce)* — [`src/main/java/main/ExercicesDeBase.java`](src/main/java/main/ExercicesDeBase.java) :

```java
public class ExercicesDeBase {
    private final List<Transaction> transactions;
    // ...
    /**
     * Cherche la transaction de valeur la plus faible, par réduction.
     *
     * @return la transaction de valeur minimale, ou la transaction neutre si la liste est vide
     */
    public Transaction reduce2() {
        Transaction transNeutre = new Transaction(null, 20000, Integer.MAX_VALUE);
        return transactions.stream()
                .reduce(transNeutre, (t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    }

}
```

*Vérifié par : `ExercicesDeBaseTest` (classe `Question11`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
