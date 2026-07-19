# Atelier 1 - partie 2 : rappels de théorie

Ce document reprend les notions utiles pour réaliser l'exercice.

Les exemples ci-dessous sont volontairement différents de l'exercice. Ils servent à rappeler les mécanismes Java sans donner directement la structure de la solution.

Il s'agit de rappels : la théorie correspondante a été vue dans les cours précédents (COO). Pas de nouvelle matière pour cet atelier.

## Table des matières

1. [Vidéos](#vidéos)
2. [Association entre objets](#association-entre-objets)
3. [Association bidirectionnelle](#association-bidirectionnelle)
4. [Multiplicités en UML](#multiplicités-en-uml)
5. [Collection d'objets](#collection-dobjets)
6. [Méthodes renvoyant un boolean](#méthodes-renvoyant-un-boolean)
7. [Interface `Iterable` et méthode `iterator`](#interface-iterable-et-méthode-iterator)
8. [Parcours avec un foreach](#parcours-avec-un-foreach)
9. [Numérotation automatique avec un attribut statique](#numérotation-automatique-avec-un-attribut-statique)
10. [Dates avec `LocalDateTime`](#dates-avec-localdatetime)
11. [Égalité structurelle sur plusieurs attributs](#égalité-structurelle-sur-plusieurs-attributs)
12. [`IllegalArgumentException` dans un constructeur](#illegalargumentexception-dans-un-constructeur)
13. [Construire une chaîne de caractères](#construire-une-chaîne-de-caractères)
14. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Introduction au cours de Java avancé](https://www.youtube.com/watch?v=c69iGzsd1Pc)

## Association entre objets

Une association relie deux classes. En Java, elle se traduit par un attribut qui référence un autre objet.

Exemple : un emprunt est fait par un lecteur.

```java
public class Emprunt {
    private Lecteur lecteur;

    public Emprunt(Lecteur lecteur) {
        this.lecteur = lecteur;
    }

    public Lecteur getLecteur() {
        return lecteur;
    }
}
```

L'objet `Emprunt` connaît son `Lecteur`. On dit que l'association est navigable de `Emprunt` vers `Lecteur`.

## Association bidirectionnelle

Une association est bidirectionnelle quand chaque objet connaît l'autre.

Exemple : le lecteur connaît son emprunt en cours et l'emprunt connaît son lecteur.

```java
public class Lecteur {
    private Emprunt empruntEnCours;

    public Emprunt getEmpruntEnCours() {
        return empruntEnCours;
    }
}
```

Le point délicat est de garder les deux côtés cohérents : si un emprunt référence un lecteur, ce lecteur doit référencer cet emprunt.

Une manière de faire est de confier la mise en place du lien à un seul endroit du code (par exemple le constructeur de `Emprunt`), qui met à jour les deux côtés. Les autres méthodes vérifient la cohérence avant d'agir.

```java
public class Emprunt {
    private Lecteur lecteur;

    public Emprunt(Lecteur lecteur) {
        this.lecteur = lecteur;
        if (!lecteur.enregistrer(this)) {
            throw new IllegalArgumentException("Ce lecteur a déjà un emprunt en cours");
        }
    }
}
```

## Multiplicités en UML

Sur un diagramme de classes, les multiplicités indiquent combien d'objets peuvent être liés entre eux.

Exemples :

1. `1` : exactement un ;
2. `0..1` : zéro ou un ;
3. `*` : zéro ou plusieurs.

Une multiplicité `0..1` se traduit souvent par un attribut qui peut valoir `null`. Une multiplicité `*` se traduit par une collection (par exemple une `ArrayList`).

## Collection d'objets

Une `ArrayList` peut contenir des objets de n'importe quelle classe.

```java
ArrayList<Emprunt> emprunts = new ArrayList<>();
emprunts.add(new Emprunt(lecteur));
```

Pour retrouver un élément dans la liste, on la parcourt et on compare les objets.

```java
public Emprunt chercher(Livre livre) {
    for (Emprunt emprunt : emprunts) {
        if (emprunt.getLivre().equals(livre)) {
            return emprunt;
        }
    }
    return null;
}
```

Cette technique permet, par exemple, de vérifier si un élément existe déjà avant d'en créer un nouveau.

## Méthodes renvoyant un boolean

Une méthode peut signaler si son opération a pu être effectuée en renvoyant un `boolean` :

1. elle renvoie `false` si une condition empêche l'opération, sans rien modifier ;
2. elle renvoie `true` si l'opération a été effectuée.

Exemple :

```java
public boolean prolonger(int nombreDeJours) {
    if (nombreDeJours <= 0 || estEnRetard()) {
        return false;
    }

    dateRetour = dateRetour.plusDays(nombreDeJours);
    return true;
}
```

Le code appelant peut alors tester le résultat :

```java
if (!emprunt.prolonger(7)) {
    System.out.println("Prolongation impossible");
}
```

## Interface `Iterable` et méthode `iterator`

Une classe qui contient une collection peut permettre de la parcourir sans donner accès à la liste interne. Pour cela, elle implémente l'interface `Iterable` et fournit une méthode `iterator`.

```java
public class Bibliotheque implements Iterable<Livre> {
    private ArrayList<Livre> livres = new ArrayList<>();

    @Override
    public Iterator<Livre> iterator() {
        return livres.iterator();
    }
}
```

La méthode `iterator` renvoie l'itérateur de la liste interne. Le code extérieur peut parcourir les livres, mais ne peut pas ajouter ou supprimer d'élément directement dans la liste : l'encapsulation est préservée.

Il faut importer `java.util.Iterator`.

## Parcours avec un foreach

Le foreach permet de parcourir tout objet `Iterable` (donc aussi toute `ArrayList`).

```java
for (Livre livre : bibliotheque) {
    System.out.println(livre);
}
```

Derrière ce code, Java utilise la méthode `iterator` de l'objet parcouru.

## Numérotation automatique avec un attribut statique

Pour attribuer automatiquement un numéro unique à chaque objet créé, on utilise un attribut statique partagé par tous les objets de la classe.

```java
public class Facture {
    private static int numeroSuivant = 1;

    private int numero;

    public Facture() {
        numero = numeroSuivant;
        numeroSuivant++;
    }
}
```

La première facture créée reçoit le numéro 1, la deuxième le numéro 2, et ainsi de suite.

## Dates avec `LocalDateTime`

La classe `LocalDateTime` (package `java.time`) représente une date et une heure.

```java
LocalDateTime maintenant = LocalDateTime.now();
```

La méthode statique `now()` renvoie la date et l'heure courantes. On peut l'utiliser pour initialiser un attribut au moment de la création d'un objet.

```java
public class Facture {
    private LocalDateTime date;

    public Facture() {
        date = LocalDateTime.now();
    }
}
```

Deux objets `LocalDateTime` se comparent avec `equals`, `isBefore` ou `isAfter`.

## Égalité structurelle sur plusieurs attributs

Pour rappel, l'égalité structurelle se définit en redéfinissant `equals` et `hashCode`.

L'égalité peut porter sur plusieurs attributs à la fois.

```java
@Override
public boolean equals(Object o) {
    if (this == o) {
        return true;
    }
    if (o == null || getClass() != o.getClass()) {
        return false;
    }

    Seance seance = (Seance) o;
    return titre.equals(seance.titre) && date.equals(seance.date);
}

@Override
public int hashCode() {
    return Objects.hash(titre, date);
}
```

Une sous-classe peut redéfinir `equals` et `hashCode` pour utiliser d'autres attributs que sa classe parent, si sa logique d'identification est différente.

## `IllegalArgumentException` dans un constructeur

Un constructeur peut refuser de créer un objet en lançant une exception. L'objet n'est alors pas créé.

```java
public Emprunt(Lecteur lecteur) {
    if (lecteur == null) {
        throw new IllegalArgumentException("Le lecteur ne peut pas être null");
    }

    this.lecteur = lecteur;
}
```

Le code appelant peut récupérer l'exception avec un bloc `try` / `catch` (voir la partie 1).

## Construire une chaîne de caractères

Pour construire une chaîne à partir de plusieurs éléments, on peut concaténer dans une boucle.

```java
public String lister() {
    String resultat = "";
    for (Livre livre : livres) {
        resultat += livre + "\n";
    }
    return resultat;
}
```

Chaque élément est ajouté à la chaîne, suivi d'un retour à la ligne `\n`.

## Points d'attention pour l'exercice

Dans l'exercice, il faudra appliquer ces notions au domaine donné dans l'énoncé.

Avant de terminer, vérifiez que :

1. Les deux côtés des associations bidirectionnelles restent cohérents.
2. Le numéro de la commande est attribué automatiquement.
3. La date est initialisée au moment de la création.
4. Les méthodes qui peuvent échouer renvoient `false` sans modifier l'état, et `true` après avoir effectué l'opération.
5. La méthode `iterator` est utilisée pour parcourir les lignes sans exposer la liste interne.
6. Les égalités structurelles demandées sont définies avec `equals` et `hashCode`.
7. L'exception demandée est lancée avec le bon message.
8. Le programme principal donne le même affichage que le fichier attendu.

---

*Passez aux [exercices](01B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/01-rappels-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
