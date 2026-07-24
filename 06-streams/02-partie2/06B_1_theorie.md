# Atelier 6 : Streams – les bases – partie 2 – Concepts

Les exercices associés à ces concepts se trouvent dans [`06B_2_exercices.md`](06B_2_exercices.md).

## Table des matières

1. [Vidéos](#vidéos)
2. [Concepts (un peu) plus avancés](#concepts-un-peu-plus-avancés)

## Vidéos

1. [Les Streams Java - Introduction](https://www.youtube.com/watch?v=q0zMS0_XRSs)
2. [Les Streams - les lambdas expressions en Java](https://www.youtube.com/watch?v=k3kLeB7dd48)
3. [Introduction à la programmation fonctionnelle](https://www.youtube.com/watch?v=rU_O2mrT8SM)
4. [Streams - Reduce](https://www.youtube.com/watch?v=LiZTza_KjnI)
5. [Les Streams - Boucles implicites](https://www.youtube.com/watch?v=jZeB6BKyzTM)
6. [Streams - Gestion du vide](https://www.youtube.com/watch?v=Oof31l4Raiw)
7. [Les Streams - GroupingBy](https://www.youtube.com/watch?v=AjMDMp0Dw84)
8. [Les Streams - debugger les Streams](https://www.youtube.com/watch?v=4hCoZ7HwZsU)
9. [Les Streams un peu de tout](https://www.youtube.com/watch?v=n3P4iniDhOA)

## Concepts (un peu) plus avancés

### Introduction

Il est possible de faire tout ce que l'on faisait en programmation classique avec l'API Stream. Dans la partie 1 vous avez vu les principes de base. Autour de ceux-ci vont se greffer une multitude de méthodes utilitaires et types. Nous n'allons lever qu'un tout petit coin du voile.

### Gestion du vide (type `Optional`)

Supposons par exemple que nous ayons besoin d'un employé mesurant au moins 2m10 pour changer une ampoule. Nous n'avons pas besoin d'une liste ici mais d'un seul objet.

L'API Stream fournit les méthodes `findFirst` et `findAny` permettant respectivement de renvoyer le premier élément d'un stream ou n'importe quel élément.

On peut donc écrire ceci pour notre recherche de géant :

```java
Employe geant = employes.stream()
        .filter(e -> e.getTaille() > 210)
        .findAny();
```

Malheureusement, il se pourrait qu'en entrée ou après filtrage on se retrouve avec un stream vide. Heureusement, l'API Stream, pour éviter les bugs dus à `null`, a introduit la classe `Optional` qui permet de représenter l'existence ou l'absence de résultat. Il s'agit en fait d'un conteneur de réponse qui indique s'il y a une réponse (`isPresent`) ou encore fournit la réponse (`get`).

Notre code précédent ne compilait pas en fait, car le type de retour de `findAny` est `Optional<Employe>`. Pour corriger cela, on peut donc écrire maintenant ceci :

```java
Optional<Employe> geantEnOption = employes.stream()
        .filter(e -> e.getTaille() > 210)
        .findAny();

if (geantEnOption.isPresent()) {
    System.out.println("Le géant: " + geantEnOption.get());
}
```

Notez que `get` lève une exception s'il n'y a rien dans l'`Optional`.

La méthode `reduce` renvoie également un `Optional` si elle est utilisée sans fournir de valeur neutre.

Si l'on veut rester en fonctionnel pur, on peut utiliser la méthode `orElse` qui renverra la valeur passée en paramètre si l'`Optional` est vide :

```java
Employe geante = employes.stream()
        .filter(e -> e.getTaille() > 210)
        .findAny()
        .orElse(new Employe(Genre.FEMME, 220, "La géante"));
```

### Grouper

Le regroupement (group by) est une opération fréquente en base de données mais elle devient vite complexe en programmation traditionnelle. Par exemple, pour regrouper les plats selon leur type :

```java
ArrayList<Dish> menu = new ArrayList<>();
Map<Type, List<Dish>> platsGroupes = new HashMap<>();
for (Dish d : menu) {
    Type t = d.getType();
    if (platsGroupes.get(t) == null)
        platsGroupes.put(t, new ArrayList<Dish>());
    platsGroupes.get(t).add(d);
}
```

En Java 8, la ligne de code suivante suffit :

```java
Map<Type, List<Dish>> pGroup2 =
        menu.stream().collect(Collectors.groupingBy(Dish::getType));
```

`groupingBy` est une fonction de classification. Elle peut reposer sur une propriété de l'élément mais aussi sur des critères plus complexes.

Considérant l'`enum CaloricLevel { DIET, NORMAL, FAT }` :

```java
menu.stream().collect(
        groupingBy(dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        }));
```

permet de construire une `Map<CaloricLevel, List<Dish>>`.

On peut également transmettre en second argument de la méthode `groupingBy` n'importe quel autre collector. Par exemple :

```java
Map<Type, Long> pg = menu.stream()
        .collect(Collectors.groupingBy(Dish::getType, counting()));
```

compte le nombre de plats de chaque type.

---

*Passez aux [exercices](06B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/06-streams-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
