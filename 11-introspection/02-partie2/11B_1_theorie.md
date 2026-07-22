# Atelier 11 : Introspection – partie 2 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [La réflexion sur les champs](#la-réflexion-sur-les-champs)
4. [Annotations sur les champs](#annotations-sur-les-champs)
5. [La validation déclarative](#la-validation-déclarative)

## Vidéos

1. [Introspection - Machine Virtuelle Java JVM](https://www.youtube.com/watch?v=hozaDXJ7J2Y)
2. [Introspection - typage statique](https://www.youtube.com/watch?v=aFIYXPp-5MY)
3. [Introspection - Inspection d'une classe](https://www.youtube.com/watch?v=s8D-yfzdkSI)
4. [Introspection - Inspection et instanciation d'objets](https://www.youtube.com/watch?v=W7EYb072Rgc)
5. [Introspection - Annotation et mini JUnit](https://www.youtube.com/watch?v=Vo-nmSstLF8)

## Introduction

La partie 1 a utilisé la réflexion sur les **méthodes** : découvrir les méthodes annotées d'une classe et les invoquer dynamiquement. Cette partie applique le même mécanisme aux **champs** (attributs) : découvrir les champs annotés d'un objet et lire leur valeur dynamiquement. Les briques de base (déclaration d'une annotation, méta-annotations, éléments d'annotation) sont dans [`11A_1_theorie.md`](../01-partie1/11A_1_theorie.md) — seules les nouveautés sont reprises ici.

## La réflexion sur les champs

Comme pour les méthodes, tout part de l'objet `Class<?>`. Le miroir de `getDeclaredMethods` pour les attributs est `getDeclaredFields` :

| Méthode | Rôle |
|---|---|
| `getDeclaredFields()` | tous les champs déclarés **directement** dans la classe (héritage exclu) — l'ordre du tableau renvoyé n'est **pas garanti** |
| `champ.getName()` | le nom d'un `Field` |
| `champ.get(instance)` | lit la valeur du champ sur l'instance donnée |

Un point d'attention : contrairement aux méthodes de test de la partie 1, qui étaient `public`, les champs qu'on veut lire sont presque toujours `private`. Par défaut, la réflexion respecte les modificateurs d'accès : `champ.get(instance)` sur un champ privé lance une `IllegalAccessException`. Il faut d'abord désactiver ce contrôle :

```java
Field champ = classe.getDeclaredField("nom");
champ.setAccessible(true);                    // sans cette ligne : IllegalAccessException
Object valeur = champ.get(instance);
```

Pour un champ de type primitif (`int`, `double`...), `champ.get` renvoie la valeur **emballée** (`Integer`, `Double`...) — toutes ces classes héritent de `Number`.

## Annotations sur les champs

Une annotation destinée aux champs se déclare comme en partie 1, avec une seule différence : la cible. `ElementType.METHOD` devient `ElementType.FIELD` :

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MonAnnotationDeChamp {
}
```

La lecture est identique à celle vue en partie 1 : `isAnnotationPresent` pour tester la présence, `getAnnotation(X.class).valeur()` pour lire un élément (voir la section « Annotations à élément » de [`11A_1_theorie.md`](../01-partie1/11A_1_theorie.md)).

## La validation déclarative

Depuis le début du quadrimestre, vos constructeurs valident leurs paramètres **impérativement**, par des appels explicites (`Util.checkObject`, `Util.checkString`...). L'exercice de cette partie construit l'approche inverse, **déclarative** : la règle est posée sur le champ sous forme d'annotation (`@NonNul`, `@LongueurMin(valeur = 3)`), et un mécanisme générique — un validateur, via la réflexion — la fait respecter pour n'importe quelle classe.

C'est l'approche du standard **Jakarta Bean Validation** (`@NotNull`, `@Size`, `@Positive`, `@Valid`...), utilisé notamment par les frameworks web pour valider automatiquement les objets reçus du client. L'exercice en reconstruit le cœur.

---

*Passez aux [exercices](11B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/11-introspection-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
