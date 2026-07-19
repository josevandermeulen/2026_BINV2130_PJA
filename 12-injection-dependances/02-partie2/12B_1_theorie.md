# Atelier 12 : Injection de dépendances – partie 2 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [La configuration par fichiers `.properties`](#la-configuration-par-fichiers-properties)
4. [L'injection par setter](#linjection-par-setter)
5. [L'injection automatique par annotations et réflexion](#linjection-automatique-par-annotations-et-réflexion)

## Vidéos

1. [Interfaces](https://www.youtube.com/watch?v=tLvlrRXwyUk)
2. [Factory method](https://www.youtube.com/watch?v=c7TR0Y_mey8)
3. [Factory Configuration](https://www.youtube.com/watch?v=FdMuh9MQXMI)
4. [Abstract Factory et injection de dépendance via les constructeurs](https://www.youtube.com/watch?v=eZRXt9l4GVM)

## Introduction

La partie 1 a éliminé les dépendances concrètes du proxy avec une factory et l'injection par constructeur. Cette partie pousse la même logique plus loin : configurer l'application **sans la recompiler** (fichiers `.properties`), puis automatiser l'injection elle-même, comme le font les frameworks type Spring. La réflexion utilisée pour cela a été vue à l'atelier 11.

## La configuration par fichiers `.properties`

Une valeur codée en dur (une liste de domaines interdits, une URL de base de données...) oblige à recompiler à chaque changement. Le remède : la sortir du code, dans un fichier texte de paires clé/valeur que l'application lit au démarrage.

```properties
blacklistedDomains=google.be;facebook.com;instagram.com
```

La classe `java.util.Properties` lit ce format en deux lignes :

```java
Properties properties = new Properties();
try (FileInputStream input = new FileInputStream("blacklist.properties")) {
    properties.load(input);
}
String domains = properties.getProperty("blacklistedDomains");
```

Le fichier vit **à côté** de l'application (racine du projet, puis à côté du JAR une fois l'application packagée) — jamais dans `src` : il doit rester modifiable sans recompilation.

## L'injection par setter

L'injection par constructeur (partie 1) fixe la dépendance une fois pour toutes à la création de l'objet. La variante par **setter** l'injecte après coup :

```java
public class ProxyServer {
    private QueryFactory queryFactory;

    public void setQueryFactory(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
```

Le `main` devient : créer l'objet, puis injecter, puis démarrer. Le setter convient quand la dépendance peut changer après l'instanciation ; le constructeur reste préférable quand elle est obligatoire et définitive (le champ peut alors être `final`). Les deux formes partagent le même principe : la classe **reçoit** ses dépendances, elle ne les crée pas.

## L'injection automatique par annotations et réflexion

Écrire les `new` et les `set...` à la main dans le `main` devient vite répétitif. Les conteneurs **IoC** (*Inversion of Control*, cœur de Spring) automatisent cet assemblage : on **annote** les champs à injecter, et un injecteur les remplit par réflexion (atelier 11).

```java
public class ProxyServer {
    @Inject(QueryFactoryImpl.class)
    private QueryFactory queryFactory;
}
```

L'injecteur parcourt les champs de l'objet, repère l'annotation, instancie l'implémentation qu'elle désigne et affecte le champ :

```java
for (Field field : target.getClass().getDeclaredFields()) {
    if (field.isAnnotationPresent(Inject.class)) {
        Class<?> implementation = field.getAnnotation(Inject.class).value();
        field.setAccessible(true);
        field.set(target, implementation.getDeclaredConstructor().newInstance());
    }
}
```

Deux conditions pour que cela fonctionne : l'annotation doit être conservée à l'exécution (`@Retention(RetentionPolicy.RUNTIME)`) et ciblée sur les champs (`@Target(ElementType.FIELD)`). Le `main` n'assemble plus rien — il délègue à l'injecteur. C'est exactement le principe de Spring, en miniature : l'objectif est de comprendre le mécanisme, pas de reproduire le framework.

---

*Passez aux [exercices](12B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/12-injection-dependances-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
