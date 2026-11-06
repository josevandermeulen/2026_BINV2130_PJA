# Atelier 8 : Lecture de fichiers et compléments Java – Concepts

Les exercices associés à ces concepts se trouvent dans [`08A_2_exercices.md`](08A_2_exercices.md).

## À rendre cette semaine

- **Le QCM de la semaine sur mooVin** — ferme le lundi 09/11/2026 à 20h.
- **Le QCM en Markdown sur les ateliers 1 à 7** — à déposer complété sur mooVin pour le lundi 09/11/2026 à 20h. Énoncé : [`08_qcm-a-rendre.md`](08_qcm-a-rendre.md).

## Vidéos

### Fichiers et exceptions

1. [Apprendre Java #15 Fichier (File / Reader / Writer / Buffer)](https://www.youtube.com/watch?v=iCobhKfvf3M)
2. [Java File Input/Output - It's Way Easier Than You Think](https://www.youtube.com/watch?v=ScUJx4aWRi0)
3. [Exception Handling in Java Tutorial](https://www.youtube.com/watch?v=1XAfapkBQjk)
4. [Checked vs. Unchecked Exceptions in Java Tutorial - What's The Difference?](https://www.youtube.com/watch?v=bCPClyGsVhc)

### Autres concepts de la semaine

1. [Inner Class Java Tutorial - Creating and Using Inner Classes](https://www.youtube.com/watch?v=BwkmIXjWWJc)
2. [Java Anonymous Inner Classes Explained in 6 Minutes](https://www.youtube.com/watch?v=DwtPWZn6T1A)
3. [Learn VARARGS in 6 minutes!](https://www.youtube.com/watch?v=2knxgAFqwKk)

## Lire un fichier texte

Pour lire le contenu d'un fichier texte ligne par ligne, Java propose la classe `BufferedReader`, qu'on construit généralement à partir d'un `FileReader` :

```java
FileReader fileReader = new FileReader("commandes/index.txt");
BufferedReader reader = new BufferedReader(fileReader);

String ligne = reader.readLine();
while (ligne != null) {
    System.out.println(ligne);
    ligne = reader.readLine();
}

reader.close();
```

`FileReader` ouvre le fichier ; `BufferedReader` l'enveloppe (on parle de *décorateur*) pour offrir la méthode `readLine()`, qui lit une ligne complète et renvoie `null` lorsqu'il n'y a plus rien à lire.

Le chemin `"commandes/index.txt"` est **relatif au répertoire de travail** du programme — dans IntelliJ, il s'agit par défaut de la racine du projet. Placez donc vos fichiers de données à la racine du projet, pas dans un sous-dossier de packages.

`FileReader` lit le fichier avec l'encodage par défaut de la machine. Depuis Java 11, on peut le préciser au constructeur, ce qui évite les accents mal lus d'un système à l'autre : `new FileReader(chemin, StandardCharsets.UTF_8)`.

### Lire toutes les lignes avec `lines`

Plutôt que d'appeler `readLine` en boucle, on peut demander au `BufferedReader` toutes ses lignes sous la forme d'un `Stream<String>` (atelier 6), avec la méthode `lines`. On dispose alors de toutes les opérations des streams :

```java
try (BufferedReader reader = new BufferedReader(new FileReader("commandes/index.txt"))) {
    long nombreDeFichiers = reader.lines()
            .filter(ligne -> !ligne.isBlank())
            .count();
}
```

Le stream lit le fichier au fur et à mesure qu'il est parcouru : il doit donc être entièrement consommé (par `count`, `toList`, `collect`…) **à l'intérieur** du bloc, avant que le `reader` ne soit fermé. Renvoyer le stream lui-même hors du try-with-resources donnerait un stream branché sur un fichier déjà fermé.

## Exceptions vérifiées et E/S

Contrairement à `IllegalArgumentException` (une exception non vérifiée, que le compilateur ne vous oblige pas à traiter), les opérations d'entrée/sortie peuvent lever `IOException`, qui est une **exception vérifiée** (*checked exception*). Le compilateur exige que vous la traitiez, soit :

1. en l'attrapant avec un `try/catch` ;
2. en la propageant avec `throws IOException` sur la méthode.

```java
public void afficherFichier(String chemin) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(chemin));
    // ...
}
```

Une méthode qui ouvre un fichier peut échouer pour de nombreuses raisons indépendantes de votre code (fichier absent, droits insuffisants, disque plein...). Java vous force donc à décider explicitement quoi faire dans ces cas.

Les exceptions rencontrées cette semaine se rangent ainsi :

```
Exception
├── IOException                      vérifiée
│   └── FileNotFoundException
└── RuntimeException                 non vérifiée
    ├── IllegalArgumentException
    │   └── NumberFormatException
    └── DateTimeException
        └── DateTimeParseException
```

Un `catch` attrape l'exception nommée **et toutes ses sous-classes** : `catch (IOException e)` attrape aussi une `FileNotFoundException`, et `catch (IllegalArgumentException e)` une `NumberFormatException`. En revanche, une `DateTimeParseException` n'est pas une `IllegalArgumentException` : elle lui échappe.

## try-with-resources

Le code de lecture ci-dessus a un défaut : si une exception survient **entre** l'ouverture et le `reader.close()`, le fichier ne sera jamais fermé. Sur un programme qui tourne longtemps, cela finit par épuiser les descripteurs de fichiers disponibles.

Le bloc **try-with-resources** (introduit en Java 7) résout ce problème : toute ressource déclarée entre parenthèses après `try` sera automatiquement fermée à la sortie du bloc, que celui-ci se termine normalement ou par une exception.

```java
try (BufferedReader reader = new BufferedReader(new FileReader("commandes/index.txt"))) {
    String ligne;
    while ((ligne = reader.readLine()) != null) {
        System.out.println(ligne);
    }
}
```

Ici, `reader.close()` est appelé automatiquement, même si `readLine()` lève une exception au milieu de la boucle. C'est équivalent à un `try { ... } finally { if (reader != null) reader.close(); }` écrit à la main (le `if` protège le cas où la construction de la ressource échouerait avant assignation), mais plus lisible et moins sujet à l'oubli.

On peut déclarer plusieurs ressources séparées par `;` dans le try-with-resources ; elles seront fermées dans l'ordre inverse de leur déclaration :

```java
try (BufferedReader reader = new BufferedReader(new FileReader("commandes/index.txt"));
     BufferedWriter writer = new BufferedWriter(new FileWriter("rapport.txt"))) {
    // ...
}
```

### AutoCloseable

Une classe peut être utilisée dans un try-with-resources si elle implémente l'interface `AutoCloseable` (une seule méthode : `close()`). `BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter` l'implémentent tous, ce qui explique qu'on peut les utiliser directement après `try`.

### try-with-resources et `catch`

Un try-with-resources peut, comme un `try` ordinaire, être suivi de blocs `catch`. Ils s'exécutent **après** la fermeture des ressources. Quand plusieurs `catch` se suivent, Java essaie le premier qui correspond : on les écrit donc de la plus précise à la plus générale.

```java
try (BufferedReader reader = new BufferedReader(new FileReader(chemin))) {
    System.out.println(reader.readLine());
} catch (FileNotFoundException e) {
    System.out.println("Fichier absent : " + chemin);
} catch (IOException e) {
    System.out.println("Erreur de lecture : " + e.getMessage());
}
```

Dans l'ordre inverse, le code ne compile pas : `catch (IOException e)` attraperait déjà toutes les `FileNotFoundException`, et le second bloc ne pourrait jamais être atteint.

Attraper une exception n'est pas toujours la bonne réponse. Une méthode qui ne sait pas quoi faire d'une erreur la laisse passer (`throws`) ; c'est l'appelant, qui connaît le contexte, qui décide. Un `catch` qui se contente d'afficher l'erreur et de continuer comme si de rien n'était cache le problème.

## Découper et convertir une ligne

Une ligne lue dans un fichier n'est qu'une `String`. Pour en extraire les champs, on la découpe avec `split`, qui renvoie un tableau :

```java
String[] champs = "margarita;2".split(";");
String titre = champs[0];                    // "margarita"
int quantite = Integer.parseInt(champs[1]);  // 2
```

Vérifiez la longueur du tableau avant d'accéder à ses cases : sur une ligne incomplète, `champs[1]` lèverait une `ArrayIndexOutOfBoundsException`. Attention aussi : `split` supprime les champs vides **en fin** de ligne, et `"margarita;;".split(";")` renvoie un tableau d'une seule case.

Chaque champ se convertit ensuite dans son type, et chaque conversion peut échouer :

- `Integer.parseInt("deux")` lève une `NumberFormatException`, une sous-classe d'`IllegalArgumentException` ;
- `LocalDate.parse("2026-13-45")` et `LocalDateTime.parse(...)` lèvent une `DateTimeParseException`, qui n'en est pas une.

Pour qu'une méthode signale toutes ses données invalides de la même façon, on peut attraper la seconde et la relancer sous la forme de la première, en lui passant l'exception d'origine comme cause :

```java
try {
    date = LocalDate.parse(texte);
} catch (DateTimeParseException e) {
    throw new IllegalArgumentException("Date invalide : " + texte, e);
}
```

## Ne pas mélanger validation métier et gestion d'erreurs techniques

Dans cet atelier, une ligne du fichier peut être syntaxiquement lisible (`readLine()` réussit) mais représenter des données invalides (un champ manquant, un client inconnu de la pizzeria, une quantité qui n'est pas un nombre, par exemple). Ce n'est pas une `IOException` : c'est une erreur de validation, qui remonte typiquement sous forme d'`IllegalArgumentException` (levée par `parserLigne`, ou par les méthodes de `Util`). Ces exceptions ne sont pas vérifiées : rien n'oblige à les traiter, mais on choisit ici de les attraper pour ignorer la ligne fautive sans interrompre tout le programme.

```java
try {
    commandes.add(parserLigne(ligne));
} catch (IllegalArgumentException e) {
    System.out.println("Ligne ignorée : " + ligne);
}
```

De la même façon, un fichier mentionné dans l'index mais absent du disque lève une `FileNotFoundException` (une sous-classe d'`IOException`, donc vérifiée). Si l'on veut que le chargement continue avec les fichiers suivants, on attrape spécifiquement cette exception-là — les autres `IOException`, elles, continuent de se propager.

## Écrire un fichier texte

L'écriture fonctionne de manière symétrique à la lecture, avec `FileWriter` et `BufferedWriter` :

```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter("rapport.txt"))) {
    writer.write("Bonjour");
    writer.newLine();
}
```

Par défaut, `new FileWriter(chemin)` **écrase** le fichier existant. Pour ajouter à la suite d'un fichier existant plutôt que l'écraser, on utilise le constructeur à deux arguments : `new FileWriter(chemin, true)`.

Deux pièges à l'écriture :

- `write` n'ajoute pas de retour à la ligne : c'est le rôle de `newLine`, à appeler après chaque ligne ;
- `BufferedWriter` accumule le texte en mémoire et ne l'écrit sur le disque que par paquets, et en tout cas à la fermeture. Un writer jamais fermé laisse un fichier vide ou tronqué : encore une raison d'utiliser le try-with-resources.

## Petit résumé

1. `FileReader`/`FileWriter` ouvrent un flux brut sur un fichier ; `BufferedReader`/`BufferedWriter` l'enveloppent pour offrir `readLine()`/`write()` plus pratiques. `lines` donne les lignes sous forme de stream, à consommer avant la fermeture.
2. `IOException` est une exception **vérifiée** : le compilateur impose de la traiter (`try/catch` ou `throws`). Un `catch` attrape aussi les sous-classes : les `catch` s'écrivent du plus précis au plus général.
3. Le bloc **try-with-resources** garantit la fermeture automatique des ressources `AutoCloseable`, même en cas d'exception.
4. Une ligne se découpe avec `split`, et ses champs se convertissent avec `Integer.parseInt`, `LocalDateTime.parse`… qui lèvent chacun leur exception en cas de donnée invalide.
5. Une donnée invalide dans un fichier par ailleurs lisible n'est pas une erreur d'E/S : c'est une erreur de validation, à traiter séparément.

---

*Passez aux [exercices](08A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-fichiers-complements-java-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
