# Atelier 8 : lecture de fichiers et rappels du langage — questionnaire à choix multiple

20 questions à réponse unique, tirées de `08A_1_theorie.md` (questions 1 à 11) et `08B_1_theorie.md` (questions 12 à 20).

---

### Question 1 — Lire un fichier ligne par ligne

Quel rôle joue `BufferedReader` par rapport à `FileReader` ?

- A) Il convertit le fichier en tableau d'octets
- B) Il n'a aucun intérêt, `FileReader` suffit
- C) Il enveloppe le `FileReader` (décorateur) pour offrir la méthode `readLine()`
- D) Il ouvre le fichier ; `FileReader` le referme

**Réponse : C** — `FileReader` ouvre le flux brut, `BufferedReader` fournit la lecture ligne par ligne.

---

### Question 2 — Fin de fichier

Que renvoie `readLine()` quand il n'y a plus rien à lire ?

- A) `-1`
- B) Une `EOFException`
- C) Une chaîne vide
- D) `null`

**Réponse : D** — D'où la boucle `while ((ligne = reader.readLine()) != null)`.

---

### Question 3 — Chemin d'accès

Par rapport à quoi le chemin `"commandes/index.txt"` est-il interprété ?

- A) Au dossier contenant la classe compilée
- B) Au dossier `src`
- C) À la racine du disque
- D) Au répertoire de travail du programme — dans IntelliJ, la racine du projet par défaut

**Réponse : D** — C'est pourquoi les fichiers de données se placent à la racine du projet, pas dans un sous-dossier de packages.

---

### Question 4 — Exception vérifiée

Qu'est-ce qui distingue `IOException` d'`IllegalArgumentException` ?

- A) `IOException` est non vérifiée, contrairement à `IllegalArgumentException`
- B) `IOException` ne peut pas être attrapée
- C) `IOException` est vérifiée : le compilateur impose de la traiter
- D) Les deux sont vérifiées

**Réponse : C** — Une opération d'E/S peut échouer pour des raisons indépendantes du code (fichier absent, droits insuffisants, disque plein) : Java force à décider explicitement quoi faire.

---

### Question 5 — Traiter une exception vérifiée

Quelles sont les deux façons de satisfaire le compilateur face à une `IOException` ?

- A) L'attraper, ou déclarer la classe `throws`
- B) L'attraper avec `try` / `catch`, ou la propager avec `throws IOException`
- C) La déclarer `static`, ou l'attraper
- D) L'ignorer, ou la convertir en `RuntimeException`

**Réponse : B** — Ce sont les deux seules options offertes par le langage.

---

### Question 6 — Le défaut du `close()` manuel

Quel problème pose un `reader.close()` écrit en fin de méthode ?

- A) Si une exception survient avant, le fichier n'est jamais fermé — ce qui finit par épuiser les descripteurs de fichiers
- B) Il ralentit la lecture
- C) Il n'est pas autorisé sur un `BufferedReader`
- D) Il ferme le fichier trop tôt

**Réponse : A** — C'est exactement ce que le try-with-resources vient corriger.

---

### Question 7 — try-with-resources

Que garantit un bloc try-with-resources ?

- A) Que toute ressource déclarée entre parenthèses est fermée à la sortie du bloc, normale ou exceptionnelle
- B) Que le fichier est verrouillé pendant la lecture
- C) Que la lecture est plus rapide
- D) Que l'exception ne sera jamais levée

**Réponse : A** — C'est l'équivalent d'un `try { ... } finally { if (reader != null) reader.close(); }` écrit à la main, mais plus lisible et moins sujet à l'oubli.

---

### Question 8 — Plusieurs ressources

Dans quel ordre sont fermées deux ressources déclarées dans le même try-with-resources ?

- A) Dans l'ordre inverse de leur déclaration
- B) Dans un ordre indéterminé
- C) Seule la première est fermée
- D) Dans l'ordre de déclaration

**Réponse : A** — Elles se séparent par `;` et sont refermées en sens inverse, comme une pile.

---

### Question 9 — `AutoCloseable`

À quelle condition une classe peut-elle être utilisée dans un try-with-resources ?

- A) Elle doit hériter de `FileReader`
- B) Elle doit être déclarée `final`
- C) Elle doit implémenter `Iterable`
- D) Elle doit implémenter `AutoCloseable`, dont l'unique méthode est `close()`

**Réponse : D** — `BufferedReader`, `BufferedWriter`, `FileReader` et `FileWriter` l'implémentent tous.

---

### Question 10 — Donnée invalide dans un fichier lisible

Une ligne est lue sans problème, mais son contenu est invalide (champ manquant). De quel type d'erreur s'agit-il ?

- A) D'une erreur de compilation
- B) D'une erreur de validation, qui remonte typiquement en `IllegalArgumentException`
- C) D'une `FileNotFoundException`
- D) D'une `IOException`

**Réponse : B** — Il ne faut pas mélanger validation métier et gestion d'erreurs techniques. Rien n'oblige à traiter cette exception non vérifiée, mais on choisit ici de l'attraper pour ignorer la ligne fautive sans interrompre le programme.

---

### Question 11 — Écraser ou compléter un fichier

Que fait `new FileWriter("rapport.txt")` sur un fichier qui existe déjà ?

- A) Il ajoute à la suite du contenu existant
- B) Il crée un fichier `rapport(1).txt`
- C) Il écrase le fichier
- D) Il lève une exception

**Réponse : C** — Pour ajouter à la suite, il faut le constructeur à deux arguments : `new FileWriter(chemin, true)`.

---

### Question 12 — Un énuméré est une classe

Que désigne `Categorie.VIANDE` dans l'énuméré `Categorie` ?

- A) Une chaîne de caractères `"VIANDE"`
- B) Un entier, sa position dans l'énuméré
- C) Un objet, instance de `Categorie`, créé une seule fois par le constructeur de l'énuméré
- D) Une nouvelle instance à chaque utilisation

**Réponse : C** — Chaque constante est un objet, avec ses attributs, sur lequel on appelle des méthodes : `Categorie.VIANDE.getLibelle()`. Il n'en existe qu'un exemplaire, d'où la comparaison avec `==`.

---

### Question 13 — Le constructeur d'un énuméré

Peut-on écrire `new Categorie("fromage")` en dehors de l'énuméré `Categorie` ?

- A) Non : le constructeur d'un énuméré est toujours privé, ses seules instances sont ses constantes
- B) Oui, s'il est déclaré `public`
- C) Oui, mais seulement dans le même package
- D) Oui, à condition de donner autant d'arguments que les constantes

**Réponse : A** — Le constructeur ne sert qu'à créer les constantes déclarées en tête de l'énuméré. Le déclarer `public` est une erreur de compilation.

---

### Question 14 — Une `Map` dont la valeur est une collection

Que fait `carte.computeIfAbsent(categorie, c -> new TreeSet<>()).add(pizza)` sur une `Map<Categorie, SortedSet<Pizza>>` ?

- A) Elle remplace l'ensemble de la catégorie par un ensemble ne contenant que la pizza
- B) Elle crée l'ensemble de la catégorie s'il n'existe pas encore, l'enregistre dans la `Map`, puis y ajoute la pizza
- C) Elle lève une exception si la catégorie n'a pas encore d'ensemble
- D) Elle ajoute la pizza seulement si la catégorie n'existe pas encore

**Réponse : B** — `computeIfAbsent` renvoie la valeur associée à la clé, après l'avoir créée avec la fonction reçue si elle manquait : c'est l'équivalent du `get`, du test à `null` et du `put` écrits à la main.

---

### Question 15 — `Comparable` ou `Comparator`

Quelle différence entre `Comparable<Pizza>` et `Comparator<Pizza>` ?

- A) Aucune, ce sont deux noms de la même interface
- B) `Comparable` sert aux listes, `Comparator` aux ensembles
- C) `Comparator` ne peut s'écrire qu'en lambda
- D) `Comparable` donne à la classe son ordre naturel, unique ; un `Comparator` est un ordre extérieur, et on peut en écrire autant qu'on veut

**Réponse : D** — `Pizza` implémente `Comparable<Pizza>` (`compareTo`, l'ordre des titres) ; trier par prix ou par nombre d'ingrédients se fait avec un `Comparator` (`compare`) passé à `sort`.

---

### Question 16 — Classe anonyme

Qu'est-ce qu'une classe anonyme, comme `new Comparator<Pizza>() { ... }` ?

- A) Une classe déclarée et instanciée à l'endroit où on l'utilise, sans lui donner de nom
- B) Une classe abstraite qu'on ne peut pas instancier
- C) Une classe sans attribut
- D) Une lambda écrite avec des accolades

**Réponse : A** — Elle implémente l'interface sur place, avec `@Override` sur ses méthodes. Quand l'interface est fonctionnelle, comme `Comparator`, une lambda dit la même chose plus court.

---

### Question 17 — Varargs

Dans `public int ajouter(Ingredient... ingredients)`, quel est le type de `ingredients` dans le corps de la méthode ?

- A) `List<Ingredient>`
- B) `Ingredient`
- C) `Ingredient[]`, un tableau, vide si on appelle la méthode sans argument
- D) `Set<Ingredient>`

**Réponse : C** — À l'appel, on passe de zéro à n arguments séparés par des virgules, ou directement un tableau. Le varargs doit être le dernier paramètre de la méthode.

---

### Question 18 — Type de retour covariant

`Pizza` déclare `public abstract Pizza copier()`. Laquelle de ces redéfinitions dans `PizzaComposee` compile ?

- A) `public Object copier()`
- B) `public PizzaComposee copier()`
- C) `public PizzaComposee copier(String titre)`, avec `@Override`
- D) `public void copier()`

**Réponse : B** — Une redéfinition peut restreindre son type de retour à un sous-type (type de retour covariant). Élargir vers `Object` ou changer pour `void` est refusé, et changer les paramètres donne une surcharge, que `@Override` signale comme une erreur.

---

### Question 19 — Classe interne statique

Que ne peut pas faire une classe interne statique, comme `Pizzeria.Bilan` ?

- A) Avoir un constructeur
- B) Être instanciée depuis l'extérieur de `Pizzeria`
- C) Avoir des attributs privés
- D) Lire directement les attributs d'instance d'une `Pizzeria`

**Réponse : D** — Elle n'est rattachée à aucune instance de `Pizzeria` : elle reçoit ses valeurs par son constructeur, et s'instancie de l'extérieur par `new Pizzeria.Bilan(...)`.

---

### Question 20 — Classe interne non statique

Dans la classe interne non statique `Pizzeria.Fidelite`, que désigne l'attribut `commandes` ?

- A) Une copie des commandes, faite à la création de la `Fidelite`
- B) L'attribut `commandes` de la `Pizzeria` qui a créé cette `Fidelite`, c'est-à-dire `Pizzeria.this.commandes`
- C) Rien : il faut passer par `getCommandes`
- D) Un attribut statique partagé par toutes les pizzerias

**Réponse : B** — Une instance de classe interne non statique est rattachée à l'instance englobante qui l'a créée, dont elle lit directement tous les attributs, même privés.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
