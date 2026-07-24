# Atelier 8 : Lecture de fichiers, try-with-resources – Concepts

Les exercices associés à ces concepts se trouvent dans [`08A_2_exercices.md`](08A_2_exercices.md).

## Table des matières

1. [Vidéos](#vidéos)
2. [Lire un fichier texte](#lire-un-fichier-texte)
3. [Exceptions vérifiées et E/S](#exceptions-vérifiées-et-es)
4. [try-with-resources](#try-with-resources)
5. [Écrire un fichier texte](#écrire-un-fichier-texte)
6. [Petit résumé](#petit-résumé)

## Vidéos

1. [Apprendre Java #15 Fichier (File / Reader / Writer / Buffer)](https://www.youtube.com/watch?v=iCobhKfvf3M)
2. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Lire un fichier texte

Pour lire le contenu d'un fichier texte ligne par ligne, Java propose la classe `BufferedReader`, qu'on construit généralement à partir d'un `FileReader` :

```java
FileReader fileReader = new FileReader("logs/index.txt");
BufferedReader reader = new BufferedReader(fileReader);

String ligne = reader.readLine();
while (ligne != null) {
    System.out.println(ligne);
    ligne = reader.readLine();
}

reader.close();
```

`FileReader` ouvre le fichier ; `BufferedReader` l'enveloppe (on parle de *décorateur*) pour offrir la méthode `readLine()`, qui lit une ligne complète et renvoie `null` lorsqu'il n'y a plus rien à lire.

Le chemin `"logs/index.txt"` est **relatif au répertoire de travail** du programme — dans IntelliJ, il s'agit par défaut de la racine du projet. Placez donc vos fichiers de données à la racine du projet, pas dans un sous-dossier de packages.

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

## try-with-resources

Le code de lecture ci-dessus a un défaut : si une exception survient **entre** l'ouverture et le `reader.close()`, le fichier ne sera jamais fermé. Sur un programme qui tourne longtemps, cela finit par épuiser les descripteurs de fichiers disponibles.

Le bloc **try-with-resources** (introduit en Java 7) résout ce problème : toute ressource déclarée entre parenthèses après `try` sera automatiquement fermée à la sortie du bloc, que celui-ci se termine normalement ou par une exception.

```java
try (BufferedReader reader = new BufferedReader(new FileReader("logs/index.txt"))) {
    String ligne;
    while ((ligne = reader.readLine()) != null) {
        System.out.println(ligne);
    }
}
```

Ici, `reader.close()` est appelé automatiquement, même si `readLine()` lève une exception au milieu de la boucle. C'est équivalent à un `try { ... } finally { if (reader != null) reader.close(); }` écrit à la main (le `if` protège le cas où la construction de la ressource échouerait avant assignation), mais plus lisible et moins sujet à l'oubli.

On peut déclarer plusieurs ressources séparées par `;` dans le try-with-resources ; elles seront fermées dans l'ordre inverse de leur déclaration :

```java
try (BufferedReader reader = new BufferedReader(new FileReader("logs/index.txt"));
     BufferedWriter writer = new BufferedWriter(new FileWriter("rapport.txt"))) {
    // ...
}
```

### AutoCloseable

Une classe peut être utilisée dans un try-with-resources si elle implémente l'interface `AutoCloseable` (une seule méthode : `close()`). `BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter` l'implémentent tous, ce qui explique qu'on peut les utiliser directement après `try`.

### Ne pas mélanger validation métier et gestion d'erreurs techniques

Dans cet atelier, une ligne du fichier peut être syntaxiquement lisible (`readLine()` réussit) mais représenter des données invalides (un champ manquant, un nom d'utilisateur vide, par exemple). Ce n'est pas une `IOException` : c'est une erreur de validation, qui remonte typiquement sous forme d'`IllegalArgumentException` (levée par le constructeur d'`Acces` via `Util`). Ces exceptions ne sont pas vérifiées : rien n'oblige à les traiter, mais on choisit ici de les attraper pour ignorer la ligne fautive sans interrompre tout le programme.

```java
try {
    acces.add(parserLigne(ligne));
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

## Petit résumé

1. `FileReader`/`FileWriter` ouvrent un flux brut sur un fichier ; `BufferedReader`/`BufferedWriter` l'enveloppent pour offrir `readLine()`/`write()` plus pratiques.
2. `IOException` est une exception **vérifiée** : le compilateur impose de la traiter (`try/catch` ou `throws`).
3. Le bloc **try-with-resources** garantit la fermeture automatique des ressources `AutoCloseable`, même en cas d'exception.
4. Une donnée invalide dans un fichier par ailleurs lisible n'est pas une erreur d'E/S : c'est une erreur de validation, à traiter séparément.

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 09/11/2026 à 20h.*

*Passez aux [exercices](08A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
