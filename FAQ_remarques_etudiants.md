# Remarques des étudiants et réponses

Ce document reprend les remarques envoyées par les étudiants sur le contenu du cours, et la réponse apportée à chacune.

## Remarque 1

- **Date :** 14/09/2026
- **Fichier concerné :** [01A_1_theorie.md](https://github.com/josevandermeulen/2026_BINV2130_PJA/blob/main/01-rappels/01-seance1/AJ_atelier01_seance1/01A_1_theorie.md), section « Chaîner des constructeurs avec `this(...)` »

> Dans la surcharge du constructeur de la séance une, dans la théorie, vous avez inversé l'ordre des constructeurs ; vous avez appelé un constructeur avec `this()`, alors qu'il n'était pas encore créé.

**Réponse**

La remarque porte sur la classe `Livre` : dans la partie sur la surcharge des constructeurs, un constructeur est appelé avec `this(...)` alors que le constructeur correspondant est déclaré plus bas dans la classe.

En Java, l'ordre de déclaration des constructeurs (comme celui des méthodes) dans une classe n'a pas d'importance. Le compilateur analyse l'ensemble de la classe avant de résoudre les appels.

Dans l'exemple de la théorie, le constructeur :

```java
Livre(String titre, String auteur)
```

appelle :

```java
this(titre, auteur, 0);
```

Cet appel fait référence au constructeur :

```java
Livre(String titre, String auteur, int nombrePages)
```

même si celui-ci est déclaré juste en dessous.

La seule contrainte concernant `this(...)` est qu'il doit être la première instruction du constructeur. Il n'y a aucune contrainte concernant la position, dans le fichier, du constructeur appelé.

> **Remarque (Java 25)** : depuis Java 25 (JEP 513, *Flexible Constructor Bodies*), un constructeur peut contenir des instructions *avant* l'appel à `this(...)` ou `super(...)` — par exemple valider un argument —, tant qu'elles n'utilisent pas `this`.

Ce qui peut éventuellement prêter à confusion, c'est que dans certains langages ou certains contextes, l'ordre de déclaration peut avoir son importance. Ce n'est pas le cas ici en Java, à l'intérieur d'une classe.
