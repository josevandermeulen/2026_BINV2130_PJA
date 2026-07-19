# Atelier 1 - partie 1 : rappels de théorie

Ce document reprend les notions utiles pour réaliser l'exercice.

Les exemples ci-dessous sont volontairement différents de l'exercice. Ils servent à rappeler les mécanismes Java sans donner directement la structure de la solution.

## Table des matières

1. [Vidéos](#vidéos)
2. [Classe et objet](#classe-et-objet)
3. [Représentation UML d'une classe](#représentation-uml-dune-classe)
4. [Encapsulation](#encapsulation)
5. [Attributs d'instance et attributs statiques](#attributs-dinstance-et-attributs-statiques)
6. [Constructeurs](#constructeurs)
7. [Surcharge](#surcharge)
8. [Chaîner des constructeurs avec `this(...)`](#chaîner-des-constructeurs-avec-this)
9. [Égalité référentielle et égalité structurelle](#égalité-référentielle-et-égalité-structurelle)
10. [`ArrayList`](#arraylist)
11. [Copie d'une liste](#copie-dune-liste)
12. [Classes abstraites](#classes-abstraites)
13. [Héritage](#héritage)
14. [Appeler le constructeur parent avec `super(...)`](#appeler-le-constructeur-parent-avec-super)
15. [Redéfinition de méthode](#redéfinition-de-méthode)
16. [Appeler une méthode parent avec `super`](#appeler-une-méthode-parent-avec-super)
17. [Exceptions unchecked](#exceptions-unchecked)
18. [`try` / `catch`](#try--catch)
19. [Méthodes statiques](#méthodes-statiques)
20. [Constantes dans une interface](#constantes-dans-une-interface)
21. [`toString`](#tostring)
22. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Introduction au cours de Java avancé](https://www.youtube.com/watch?v=c69iGzsd1Pc)

## Classe et objet

Une classe décrit un type d'objet. Elle définit :

1. les données que l'objet possède ;
2. les opérations que l'objet peut effectuer.

Un objet est une instance d'une classe.

Exemple :

```java
Livre livre = new Livre("Dune", "Frank Herbert");
```

Ici :

1. `Livre` est la classe ;
2. `livre` est une variable qui référence un objet ;
3. `new Livre(...)` crée un nouvel objet.

## Représentation UML d'une classe

En UML, une classe est souvent représentée par un rectangle divisé en trois parties :

1. Le nom de la classe.
2. Les attributs.
3. Les méthodes.

Exemple simplifié :

```text
Livre
--------------------
- titre : String
- auteur : String
--------------------
+ getTitre() : String
+ getAuteur() : String
```

Les signes indiquent la visibilité :

1. `-` signifie `private` ;
2. `+` signifie `public`.

## Encapsulation

L'encapsulation consiste à protéger l'état interne d'un objet.

En Java, les attributs sont généralement `private`. Le code extérieur passe par des méthodes publiques pour consulter ou modifier certaines données.

Exemple :

```java
public class CompteBancaire {
    private double solde;

    public double getSolde() {
        return solde;
    }

    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }
}
```

Le code extérieur ne modifie pas directement `solde`. La classe garde le contrôle sur ses propres données.

## Attributs d'instance et attributs statiques

Un attribut d'instance appartient à un objet.

Exemple :

```java
private String nom;
private String prenom;
```

Chaque objet possède ses propres valeurs.

Un attribut statique appartient à la classe. Il est partagé par tous les objets de cette classe.

Exemple :

```java
private static int nombreObjetsCrees = 0;
```

On peut l'utiliser pour compter les objets créés ou pour générer automatiquement un identifiant.

```java
public class Badge {
    private static int numeroSuivant = 1;
    private int numero;

    public Badge() {
        numero = numeroSuivant;
        numeroSuivant++;
    }
}
```

## Constructeurs

Un constructeur est appelé quand on crée un objet avec `new`.

Il porte le même nom que la classe et n'a pas de type de retour.

Exemple :

```java
public class Livre {
    private String titre;
    private String auteur;

    public Livre(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
    }
}
```

Le mot-clé `this` désigne l'objet courant.

```java
this.titre = titre;
```

signifie : l'attribut `titre` de l'objet reçoit la valeur du paramètre `titre`.

## Surcharge

On parle de surcharge lorsque plusieurs constructeurs ou plusieurs méthodes ont le même nom, mais des paramètres différents.

Exemple :

```java
public class Livre {
    public Livre(String titre) {
        // ...
    }

    public Livre(String titre, String auteur) {
        // ...
    }
}
```

Le compilateur choisit le constructeur à appeler en fonction des arguments fournis.

## Chaîner des constructeurs avec `this(...)`

Quand plusieurs constructeurs partagent du code, il faut éviter de le recopier.

Un constructeur peut appeler un autre constructeur de la même classe avec `this(...)`.

Exemple :

```java
public class Livre {
    private String titre;
    private String auteur;
    private int nombrePages;

    public Livre(String titre, String auteur) {
        this(titre, auteur, 0);
    }

    public Livre(String titre, String auteur, int nombrePages) {
        this.titre = titre;
        this.auteur = auteur;
        this.nombrePages = nombrePages;
    }
}
```

L'appel à `this(...)` doit être la première instruction du constructeur.

## Égalité référentielle et égalité structurelle

L'égalité référentielle vérifie si deux variables référencent exactement le même objet en mémoire.

On l'écrit avec `==`.

```java
etudiant1 == etudiant2
```

L'égalité structurelle vérifie si deux objets doivent être considérés comme égaux selon une logique définie par la classe.

Exemple : deux étudiants peuvent être considérés comme égaux s'ils ont le même matricule.

En Java, on définit cette égalité en redéfinissant `equals`.

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Etudiant etudiant = (Etudiant) o;
    return matricule == etudiant.matricule;
}
```

Quand on redéfinit `equals`, il faut aussi redéfinir `hashCode`.

```java
@Override
public int hashCode() {
    return Objects.hash(matricule);
}
```

La classe `Objects` se trouve dans le package `java.util` : il faut donc ajouter l'import `java.util.Objects`.

C'est important pour que l'objet fonctionne correctement dans certaines collections Java.

## `ArrayList`

Une `ArrayList` permet de stocker plusieurs objets dans une liste.

Exemple :

```java
ArrayList<String> noms = new ArrayList<>();
noms.add("Alice");
noms.add("Bilal");
noms.add("Chloe");
```

Quelques méthodes utiles :

```java
noms.add("Dina");        // ajoute un élément
noms.remove("Bilal");    // supprime un élément
noms.contains("Alice");  // teste si l'élément est présent
```

La méthode `contains` utilise `equals` pour comparer les objets.

## Copie d'une liste

Quand un constructeur reçoit une liste en paramètre, il ne faut pas toujours stocker directement cette liste dans l'objet.

Exemple à éviter :

```java
this.participants = participants;
```

Le problème est que le code extérieur garde une référence vers la même liste. Il pourrait donc encore modifier la liste interne de l'objet sans passer par ses méthodes.

On préfère parfois créer une nouvelle liste et copier les éléments.

```java
this.participants = new ArrayList<>();

for (String participant : participants) {
    if (!this.participants.contains(participant)) {
        this.participants.add(participant);
    }
}
```

Cette technique permet de mieux protéger l'état interne de l'objet.

## Classes abstraites

Une classe abstraite est une classe qui ne sert pas à créer directement des objets.

Elle sert de base commune pour des sous-classes.

Elle peut contenir des méthodes abstraites : des méthodes déclarées sans corps, que chaque sous-classe concrète doit implémenter.

Exemple :

```java
public abstract class Forme {
    private String couleur;

    public Forme(String couleur) {
        this.couleur = couleur;
    }

    public abstract double calculerAire();
}
```

On ne peut pas créer une `Forme` générale (`new Forme(...)` est interdit par le compilateur). On crée plutôt des formes concrètes :

```java
public class Cercle extends Forme {
    // ...
}

public class Rectangle extends Forme {
    // ...
}
```

La classe abstraite contient le comportement commun ; les sous-classes fournissent le comportement spécifique.

## Héritage

L'héritage permet de créer une classe à partir d'une autre.

La sous-classe récupère les attributs et méthodes accessibles de sa classe parent.

Exemple :

```java
public class Rectangle extends Forme {
    private double largeur;
    private double hauteur;
}
```

Ici :

1. `Forme` est la classe parent ;
2. `Rectangle` est la sous-classe.

## Appeler le constructeur parent avec `super(...)`

Le constructeur d'une sous-classe commence par appeler un constructeur de sa classe parent.

Si on ne l'écrit pas explicitement, Java insère automatiquement un appel au constructeur sans paramètre du parent ; si le parent n'en possède pas, il faut écrire l'appel soi-même avec `super(...)`.

Exemple :

```java
public class Rectangle extends Forme {
    private double largeur;
    private double hauteur;

    public Rectangle(String couleur, double largeur, double hauteur) {
        super(couleur);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }
}
```

L'appel à `super(...)` doit être la première instruction du constructeur.

## Redéfinition de méthode

Une sous-classe peut redéfinir une méthode héritée pour changer son comportement.

On parle d'`overriding`.

Exemple : la classe `Rectangle` implémente la méthode abstraite `calculerAire` déclarée dans `Forme` (voir la section sur les classes abstraites).

```java
public class Rectangle extends Forme {
    private double largeur;
    private double hauteur;

    @Override
    public double calculerAire() {
        return largeur * hauteur;
    }
}
```

Redéfinir une méthode permet aussi de remplacer un comportement déjà défini dans la classe parent, comme dans l'exemple de la section suivante.

L'annotation `@Override` n'est pas obligatoire, mais elle est fortement conseillée. Elle permet au compilateur de vérifier que l'on redéfinit bien une méthode existante.

## Appeler une méthode parent avec `super`

Une sous-classe peut appeler une méthode de sa classe parent avec `super.nomDeMethode(...)`.

Exemple :

```java
public class Employe {
    public double calculerSalaire() {
        return 2000;
    }
}
```

```java
public class EmployeAvecPrime extends Employe {
    private double prime;

    @Override
    public double calculerSalaire() {
        return super.calculerSalaire() + prime;
    }
}
```

Ici, la sous-classe réutilise le calcul de base de la classe parent, puis ajoute son comportement spécifique.

## Exceptions unchecked

Une exception signale un problème pendant l'exécution du programme.

Dans cet atelier, on utilise des exceptions unchecked. Elles héritent de `RuntimeException`.

Exemples :

1. `IllegalArgumentException` ;
2. `UnsupportedOperationException`.

`IllegalArgumentException` indique qu'un paramètre donné à une méthode ou à un constructeur n'est pas valide.

Exemple :

```java
throw new IllegalArgumentException("L'âge ne peut pas être négatif");
```

`UnsupportedOperationException` indique qu'une opération existe, mais n'est pas autorisée dans ce cas.

Exemple :

```java
throw new UnsupportedOperationException("Cette opération n'est pas autorisée");
```

## `try` / `catch`

Un bloc `try` / `catch` permet de récupérer une exception et d'éviter l'arrêt brutal du programme.

Exemple :

```java
try {
    CompteBancaire compte = new CompteBancaire(-100);
} catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
}
```

Si le constructeur lance une `IllegalArgumentException`, le programme saute dans le bloc `catch`.

La méthode `getMessage()` permet de récupérer le message de l'exception.

## Méthodes statiques

Une méthode statique appartient à une classe, pas à un objet particulier.

On l'appelle avec le nom de la classe.

Exemple :

```java
Math.ceil(12.3)
```

ou :

```java
LocalDateTime.now()
```

Dans l'exercice, une méthode statique est utilisée pour obtenir la date et l'heure courantes.

## Constantes dans une interface

Une interface peut contenir des constantes.

Exemple :

```java
public interface Couleurs {
    public static final String ROUGE = "rouge";
    public static final String BLEU = "bleu";
}
```

Dans une interface, les attributs sont automatiquement `public static final`.

On peut donc utiliser directement :

```java
Couleurs.ROUGE
```

## `toString`

La méthode `toString` renvoie une représentation textuelle d'un objet.

Elle est appelée automatiquement quand on affiche un objet avec `System.out.println`.

Exemple :

```java
System.out.println(livre);
```

Java appelle en réalité :

```java
livre.toString()
```

Exemple de redéfinition :

```java
@Override
public String toString() {
    return titre + " - " + auteur;
}
```

## Points d'attention pour l'exercice

Dans l'exercice, il faudra appliquer ces notions au domaine donné dans l'énoncé.

Avant de terminer, vérifiez que :

1. Les attributs d'instance sont bien encapsulés.
2. Le numéro du client est attribué automatiquement.
3. `equals` et `hashCode` sont redéfinis quand une égalité structurelle est demandée.
4. La liste d'ingrédients est gérée sans doublon.
5. Le constructeur qui reçoit une liste protège l'objet contre les modifications externes.
6. La classe commune est bien abstraite.
7. Les constructeurs utilisent `this(...)` ou `super(...)` quand c'est utile.
8. Les sous-classes redéfinissent uniquement les méthodes dont le comportement change.
9. Les exceptions demandées sont lancées au bon moment.
10. Le programme principal donne le même affichage que le fichier attendu.

---

*Passez aux [exercices](01A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/01-rappels-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
