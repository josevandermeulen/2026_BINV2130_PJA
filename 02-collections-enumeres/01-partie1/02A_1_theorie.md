# Atelier 2 - partie 1 : rappels de théorie

Ce document reprend les notions utiles pour réaliser l'exercice.

Les exemples ci-dessous sont volontairement différents de l'exercice. Ils servent à rappeler les mécanismes Java sans donner directement la structure de la solution.

## Table des matières

1. [Vidéos](#vidéos)
2. [Package](#package)
3. [Énumérés](#énumérés)
4. [Collections](#collections)
5. [Classes internes](#classes-internes)
6. [Durées avec `Duration`](#durées-avec-duration)
7. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Interface Comparable en Java](https://www.youtube.com/watch?v=PCjAg07fivY)
2. [Interface Comparator et classe anonyme en Java](https://www.youtube.com/watch?v=gLTAIkKdsK8)
3. [Clone, copie superficielle et profonde en Java](https://www.youtube.com/watch?v=la-CVGl3k7E)

## Package

Un package regroupe des classes qui vont logiquement ensemble (par exemple `domaine`, `main`, `util`). Il se traduit par un dossier dans le répertoire des sources, et chaque classe du package commence par la déclaration :

```java
package domaine;
```

Pour créer un package avec IntelliJ, faites un clic droit sur le dossier source devant le contenir, sélectionnez New → Package, entrez le nom du package et appuyez sur Enter. Un nouveau dossier apparaît ayant comme nom celui du package créé. Pour créer une classe dans ce package, faites un clic droit sur le dossier du package et sélectionnez New → Java Class.

Pour utiliser une classe d'un autre package, il faut l'importer :

```java
import domaine.JourDeLaSemaine;
```

## Énumérés

### Définition d'un énuméré en Java

Un énuméré est en fait une classe particulière qui permet de définir une énumération c.-à-d. un nouveau type de données ayant un nombre fini de valeurs qui sont connues à l'avance. Par exemple, on peut définir un énuméré pour les jours de la semaine comme suit :

```java
//Le mot clé enum indique qu'il s'agit d'un énuméré
public enum JourDeLaSemaine {
    //déclaration de toutes les instances (constantes) de l'énuméré
    LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE;
}
```

`LUNDI`, `MARDI`, …, `DIMANCHE` sont des constantes (`public static final`) de type `JourDeLaSemaine`, initialisées via le constructeur sans paramètre (implicite tant qu'aucun constructeur n'est déclaré ; dans un énuméré, il est privé). Il n'est pas possible de créer d'autres objets de type `JourDeLaSemaine`.

Remarque : il est obligatoire que la déclaration des constantes se fasse comme dans l'exemple ci-dessus c.-à-d. que :

1. Elle doit se situer juste après l'en-tête.
2. Les constantes sont séparées par une virgule.
3. Un point-virgule est mis pour indiquer qu'il n'y a plus d'autres constantes.

Comme dit précédemment, un énuméré est une classe. On peut donc y définir des attributs, des constructeurs, des méthodes, … comme dans une autre classe. Ajoutons à notre énuméré `JourDeLaSemaine` deux attributs (le premier correspondra à un numéro de jour et le deuxième au nom du jour en anglais), un constructeur prenant en paramètres les valeurs pour ces deux attributs et des getters.

```java
public enum JourDeLaSemaine {
    LUNDI(1, "monday"), MARDI(2, "tuesday"), MERCREDI(3, "wednesday"),
    JEUDI(4, "thursday"), VENDREDI(5, "friday"), SAMEDI(6, "saturday"),
    DIMANCHE(7, "sunday");

    private int numeroJour;

    private String nomAnglais;

    JourDeLaSemaine(int numeroJour, String nomAnglais) {
        this.numeroJour = numeroJour;
        this.nomAnglais = nomAnglais;
    }

    public int getNumeroJour() {
        return numeroJour;
    }

    public String getNomAnglais() {
        return nomAnglais;
    }
}
```

Les constantes restent notées en premier lieu ; les valeurs entre parenthèses après chaque constante sont les paramètres passés au constructeur qui l'initialise (ici, un constructeur à deux paramètres).

Pour le reste, cela se fait comme dans une classe habituelle, avec cependant des restrictions :

1. Les constructeurs doivent être `private`. C'est la raison pour laquelle il n'est pas nécessaire de préciser la visibilité car, dans un énuméré, ils sont par défaut `private`.
2. Il est interdit de mettre des méthodes abstraites.
3. Il est interdit de redéfinir les méthodes `equals`/`hashCode` dans un énuméré. La comparaison se fait toujours sur la référence. Par conséquent, on peut utiliser `==` pour comparer deux instances d'un énuméré.

On peut aussi redéfinir la méthode `toString` dans un énuméré, par exemple pour renvoyer une abréviation ou un libellé plus lisible que le nom de la constante.

### Méthodes d'un énuméré

Tout énuméré hérite de la classe `Enum`. Ce qui ajoute des méthodes et modifie le comportement de certaines par rapport à la classe `Object`.

Les méthodes statiques ci-dessous sont définies pour tous les énumérés :

1. `values()` qui renvoie un tableau contenant toutes les constantes, dans l'ordre de leur déclaration, de l'énuméré.
2. `valueOf(String)` qui renvoie, si elle existe, la constante ayant pour nom la valeur passée en paramètre et lance une `IllegalArgumentException` sinon.

Les méthodes d'instance suivantes sont aussi définies pour tout énuméré :

1. `ordinal()` qui renvoie un `int` correspondant au numéro d'ordre de la constante dans la déclaration (la première ayant le numéro 0).
2. `compareTo(XXX)`, où XXX correspond au type de l'énuméré, qui repose sur l'ordre dans lequel les constantes ont été déclarées. Avec notre exemple, `JourDeLaSemaine.LUNDI.compareTo(JourDeLaSemaine.JEUDI)` renverra un négatif. Cette méthode ne peut pas non plus être redéfinie.

De plus, dans la classe `Enum`, la méthode `toString()` a été redéfinie afin de renvoyer le nom de la constante sur laquelle on l'appelle. Avec notre exemple, `JourDeLaSemaine.JEUDI.toString()` renverra `"JEUDI"`.

### Switch … case

Le switch peut se faire pour des énumérés. Considérons la classe `Date` ci-dessous :

```java
public class Date {
    private JourDeLaSemaine jourDeLaSemaine;
    //…

    public Date(JourDeLaSemaine jourDeLaSemaine /*, …*/) {
        this.jourDeLaSemaine = jourDeLaSemaine;
        //…
    }

    public JourDeLaSemaine getJourDeLaSemaine() {
        return jourDeLaSemaine;
    }
    //…
}
```

Si `date` est une variable de type `Date`, on peut donc écrire le code suivant pour décider du traitement à faire selon le jour de la semaine :

```java
switch (date.getJourDeLaSemaine()) {
    case LUNDI, MARDI, JEUDI, VENDREDI:
        //…
        break;
    case MERCREDI:
        //…
        break;
    case SAMEDI, DIMANCHE:
        //…
        break;
}
```

## Collections

Les notions essentielles sont reprises ci-dessous.

### Les interfaces `List` et `Set`

Java fournit plusieurs types de collections. Le choix se fait selon les besoins :

1. Une `List` conserve l'ordre d'insertion, est accessible par position et accepte les doublons. Implémentation courante : `ArrayList`.
2. Un `Set` représente un ensemble : pas de doublons, pas d'ordre garanti. Implémentation courante : `HashSet`.

On déclare la variable avec le type de l'interface et on instancie avec l'implémentation :

```java
List<String> etapes = new ArrayList<>();
Set<String> participants = new HashSet<>();
```

### Manipuler une `List`

```java
etapes.add("préparer");             // ajoute en dernier
etapes.add(0, "lire la notice");    // insère à la position 0
etapes.get(1);                      // renvoie l'élément en position 1
etapes.set(1, "mélanger");          // remplace l'élément en position 1, renvoie l'ancien
etapes.remove(0);                   // supprime l'élément en position 0, renvoie-le
etapes.size();                      // nombre d'éléments
```

Attention : les positions d'une `List` commencent à 0. Si l'énoncé d'un exercice compte à partir de 1, il faut faire la conversion.

Un accès à une position invalide (négative ou trop grande) lance une `IndexOutOfBoundsException`. Si l'exercice demande une `IllegalArgumentException`, il faut tester la position soi-même avant d'accéder à la liste.

### Manipuler un `Set`

```java
participants.add("Alice");      // renvoie false si l'élément est déjà présent
participants.remove("Alice");   // renvoie false si l'élément n'est pas présent
participants.contains("Bilal"); // teste la présence
```

Un `HashSet` utilise `equals` et `hashCode` pour détecter les doublons : les objets qu'on y place doivent donc redéfinir ces deux méthodes correctement.

### Parcourir une collection

Le foreach fonctionne pour toutes les collections :

```java
for (String etape : etapes) {
    System.out.println(etape);
}
```

### Renvoyer une collection non modifiable

Si un getter renvoie directement la collection interne, le code extérieur peut la modifier sans passer par les méthodes de la classe : l'encapsulation est cassée.

Une solution est de renvoyer une vue non modifiable de la collection :

```java
public List<String> etapes() {
    return Collections.unmodifiableList(etapes);
}
```

Toute tentative de modification de la liste renvoyée lance une `UnsupportedOperationException`, mais la lecture et le parcours restent possibles. Il existe aussi `Collections.unmodifiableSet` pour les ensembles.

## Classes internes

Les notions essentielles sont reprises ci-dessous.

Une classe (ou un énuméré) peut être définie à l'intérieur d'une autre classe. On utilise cette technique quand le type interne n'a de sens que pour la classe qui le contient.

```java
public class Meteo {

    public enum Tendance {
        ENSOLEILLE, NUAGEUX, PLUVIEUX;
    }

    private Tendance tendance;

    public Meteo(Tendance tendance) {
        this.tendance = tendance;
    }
}
```

Depuis l'extérieur de la classe, on accède au type interne en le préfixant par le nom de la classe englobante :

```java
Meteo meteo = new Meteo(Meteo.Tendance.NUAGEUX);
```

Un énuméré interne est implicitement `static` : il ne dépend d'aucune instance de la classe englobante.

## Durées avec `Duration`

La classe `Duration` (package `java.time`) représente une durée. Quelques méthodes utiles :

```java
Duration duree = Duration.ofMinutes(90);   // méthode statique de création

duree.plusMinutes(30);   // renvoie une NOUVELLE durée augmentée de 30 minutes
duree.minusMinutes(15);  // renvoie une NOUVELLE durée diminuée de 15 minutes

duree.toHours();         // 1  (nombre d'heures complètes)
duree.toMinutes();       // 90 (durée totale en minutes)
duree.toMinutesPart();   // 30 (minutes restantes une fois les heures retirées)
```

Attention : un objet `Duration` est immuable. Les méthodes `plusXXX` et `minusXXX` ne modifient pas l'objet, elles en renvoient un nouveau. Il faut donc récupérer le résultat :

```java
duree = duree.plusMinutes(30);
```

## Points d'attention pour l'exercice

Dans l'exercice, il faudra appliquer ces notions au domaine donné dans l'énoncé.

Avant de terminer, vérifiez que :

1. Les classes sont dans les bons packages.
2. Les énumérés utilisent un constructeur privé pour initialiser leurs attributs et redéfinissent `toString` quand un affichage particulier est demandé.
3. Le type de collection choisi correspond au besoin : `List` quand l'ordre compte, `Set` quand les doublons sont interdits.
4. Les positions reçues en paramètre sont validées avant d'accéder à la liste (les positions de l'énoncé commencent à 1, celles de `List` à 0).
5. Les getters de collections renvoient une vue non modifiable.
6. Les durées sont bien réaffectées après un `plusXXX`/`minusXXX` (`Duration` est immuable).
7. Tous les constructeurs et méthodes testent leurs arguments et lancent une `IllegalArgumentException` en cas de paramètre invalide.
8. Le programme principal donne le même affichage que celui attendu.

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 21/09/2026 à 20h.*

*Passez aux [exercices](02A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/02-collections-enumeres-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
