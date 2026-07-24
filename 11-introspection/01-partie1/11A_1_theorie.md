# Atelier 11 : Introspection – partie 1 – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction](#introduction)
3. [Rappels de réflexion](#rappels-de-réflexion)
4. [Les annotations](#les-annotations)

## Vidéos

1. [Introspection - Machine Virtuelle Java JVM](https://www.youtube.com/watch?v=hozaDXJ7J2Y)
2. [Introspection - typage statique](https://www.youtube.com/watch?v=aFIYXPp-5MY)
3. [Introspection - Inspection d'une classe](https://www.youtube.com/watch?v=s8D-yfzdkSI)
4. [Introspection - Inspection et instanciation d'objets](https://www.youtube.com/watch?v=W7EYb072Rgc)
5. [Introspection - Annotation et mini JUnit](https://www.youtube.com/watch?v=Vo-nmSstLF8)

## Introduction

La théorie sur l'introspection a été vue en cours de Concepts Orientés Objet et ne sera pas détaillée ici. Cependant, n'hésitez pas à utiliser également les ressources à votre disposition, à savoir :

1. La javadoc officielle : https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/package-summary.html
2. Les guides techniques fournis par Oracle : https://www.oracle.com/technical-resources/articles/java/javareflection.html

Les rappels ci-dessous se limitent aux briques utilisées dans l'exercice : inspecter une classe, invoquer une méthode dynamiquement, instancier un objet, et créer une annotation personnalisée.

## Rappels de réflexion

La **réflexion** (ou introspection) permet à un programme d'examiner et de manipuler ses propres classes à l'exécution. Tout part d'un objet `Class<?>` :

```java
Class<?> classe = MaClasse.class;              // à la compilation
Class<?> classe = monInstance.getClass();      // depuis une instance
Class<?> classe = Class.forName("be.vinci.MaClasse");   // depuis un nom (String)
```

À partir de là, le package `java.lang.reflect` donne accès aux membres :

| Méthode | Rôle |
|---|---|
| `getDeclaredMethods()` | toutes les méthodes déclarées **directement** dans la classe (héritage exclu) — attention, l'ordre du tableau renvoyé n'est **pas garanti** |
| `getDeclaredConstructor()` | le constructeur sans paramètre |
| `methode.getName()` | le nom d'une `Method` |
| `methode.invoke(instance, args...)` | invoque la méthode sur l'instance donnée |
| `constructeur.newInstance()` | crée une nouvelle instance |

Deux points d'attention pour l'invocation dynamique :

1. `invoke` et `newInstance` déclarent des exceptions *checked* (`ReflectiveOperationException` et ses sous-classes) : à gérer.
2. Si la méthode invoquée lance elle-même une exception, `invoke` ne la propage pas telle quelle : elle arrive **emballée** dans une `InvocationTargetException`, et l'exception d'origine se récupère avec `getCause()`.

## Les annotations

Une **annotation** est une étiquette qu'on appose sur un élément du code (classe, méthode, attribut...). Seule, elle ne fait rien : c'est un outil (compilateur, framework... ou votre code, par réflexion) qui lui donne un sens. Vous en utilisez depuis des semaines : `@Override`, `@Test`, `@BeforeEach`, `@DisplayName`...

Déclarer une annotation personnalisée ressemble à déclarer une interface, avec `@interface` :

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MonAnnotation {
}
```

Les deux **méta-annotations** sont essentielles :

1. `@Retention(RetentionPolicy.RUNTIME)` : l'annotation est conservée dans le bytecode **et visible à l'exécution**. Sans elle, la réflexion ne la voit pas (par défaut, une annotation est retenue dans le `.class` mais invisible à l'exécution).
2. `@Target(ElementType.METHOD)` : l'annotation ne peut être apposée que sur des méthodes (le compilateur refuse les autres emplacements).

Côté lecture, la réflexion permet de tester la présence d'une annotation :

```java
for (Method methode : classe.getDeclaredMethods()) {
    if (methode.isAnnotationPresent(MonAnnotation.class)) {
        // cette méthode porte l'étiquette
    }
}
```

C'est exactement ainsi que JUnit trouve vos méthodes `@Test` : il ne connaît pas vos classes à l'avance, il les inspecte par réflexion et invoque dynamiquement les méthodes annotées. L'exercice de cet atelier consiste à reconstruire ce mécanisme.

### Annotations à élément

Une annotation peut porter des **éléments** : des valeurs fournies au moment où on l'appose. Vous en utilisez déjà : `@DisplayName("Question 1 : ...")` en JUnit porte un élément `String`. Un élément se déclare comme une méthode sans corps dans le `@interface` :

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MonAnnotation {

    String valeur();

}
```

À l'utilisation, la valeur se donne entre parenthèses, avec le nom de l'élément :

```java
@MonAnnotation(valeur = "un libellé lisible")
public void maMethode() { ... }
```

Un élément déclaré sans valeur par défaut est **obligatoire** : le compilateur refuse `@MonAnnotation` tout court. On peut le rendre facultatif avec `default` (`String valeur() default "";`).

Côté lecture, `isAnnotationPresent` ne suffit plus : il faut récupérer l'**instance** de l'annotation avec `getAnnotation`, puis appeler l'élément comme une méthode :

```java
if (methode.isAnnotationPresent(MonAnnotation.class)) {
    String libelle = methode.getAnnotation(MonAnnotation.class).valeur();
}
```

Les éléments ne sont pas limités aux `String` : `int fois();`, par exemple, se lit de la même façon (`getAnnotation(Repeter.class).fois()`).

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 30/11/2026 à 20h.*

*Passez aux [exercices](11A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/11-introspection-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
