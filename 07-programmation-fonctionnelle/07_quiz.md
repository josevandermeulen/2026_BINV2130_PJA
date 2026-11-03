# Atelier 7 : programmation fonctionnelle — questionnaire à choix multiple

20 questions à réponse unique, tirées de `07A_1_theorie.md` (questions 1 à 11) et `07B_1_theorie.md` (questions 12 à 20).

---

### Question 1 — Les méthodes `default`

Quel problème les méthodes `default` des interfaces résolvent-elles ?

- A) Elles rendent les interfaces instanciables
- B) Elles accélèrent l'appel des méthodes d'interface
- C) Elles permettent d'ajouter une méthode à une interface sans casser toutes les classes qui l'implémentent déjà
- D) Elles remplacent les classes abstraites

**Réponse : C** — Sans elles, ajouter `stream()` à `Collection` aurait obligé à modifier toutes les implémentations existantes.

---

### Question 2 — Un exemple de méthode `default`

Pourquoi peut-on appeler `sort()` sur une `ArrayList` alors que cette classe n'en fournit aucune implémentation ?

- A) Parce que le compilateur la génère automatiquement
- B) Parce que l'interface `List` en propose une implémentation par défaut
- C) Parce que `sort` est une méthode statique de `Arrays`
- D) Parce que `ArrayList` hérite de `Collections`

**Réponse : B** — L'interface permet donc d'ajouter le comportement d'une méthode à une classe sans modifier cette classe.

---

### Question 3 — `Comparator<? super E>`

Que signifie le type `Comparator<? super E>` dans `default void sort(Comparator<? super E> c)` ?

- A) Le comparateur ne compare que des `Object`
- B) Le comparateur doit comparer exactement des `E`
- C) Le comparateur peut comparer des `E` ou n'importe laquelle de leurs superclasses
- D) Le comparateur peut comparer des sous-classes de `E`

**Réponse : C** — Cela offre plus de flexibilité : une `List<Dog>` peut être triée par un `Comparator<Animal>`, puisque tout `Dog` est un `Animal`.

---

### Question 4 — Interface générique

Dans `public interface List<E> extends Collection<E>`, que représente `E` si on manipule une `List<String>` ?

- A) Un type déterminé à l'exécution
- B) `Collection`
- C) `Object`
- D) `String`

**Réponse : D** — La signature `boolean add(E e)` doit alors se lire `boolean add(String e)`.

---

### Question 5 — Interface fonctionnelle

Qu'est-ce qu'une interface fonctionnelle ?

- A) Une interface qui hérite de `Function`
- B) Une interface qui ne possède qu'une seule méthode abstraite
- C) Une interface qui ne contient que des méthodes `default`
- D) Une interface qui ne contient que des constantes

**Réponse : B** — L'annotation `@FunctionalInterface` fait vérifier cette contrainte par le compilateur. L'interface représente ainsi la définition d'une fonction.

---

### Question 6 — Ce que cache une lambda

Que représente réellement la lambda `e -> e.getGenre() == Genre.HOMME` passée à `filter` ?

- A) Un `Comparator`
- B) Une méthode statique générée par le compilateur
- C) L'implémentation implicite de la méthode `test` de l'interface `Predicate`
- D) Une classe anonyme sans type

**Réponse : C** — La lambda est un raccourci pour l'implémentation et l'instanciation de `Predicate`. `filter` appelle ensuite `test()` sur chaque élément.

---

### Question 7 — `Predicate`

Quelle est la signature de la méthode abstraite de `Predicate<T>` ?

- A) `boolean test(T t)`
- B) `T get()`
- C) `void accept(T t)`
- D) `T apply(T t)`

**Réponse : A** — C'est ce qui en fait l'interface attendue par `filter`.

---

### Question 8 — `Function`

Que représente une `Function<T, R>` ?

- A) Une fonction qui reçoit un `T` et renvoie un `R`
- B) Une fonction qui reçoit un `R` et renvoie un `T`
- C) Une fonction qui ne renvoie rien
- D) Une fonction sans paramètre qui renvoie un `T`

**Réponse : A** — Sa méthode abstraite est `R apply(T t)`. La lambda `e -> e.getNom()` passée à `map` sur des employés est une `Function<Employe, String>`.

---

### Question 9 — `Consumer`

Qu'est-ce qui caractérise un `Consumer<T>` ?

- A) Il reçoit un `T` et ne renvoie rien : il produit un effet
- B) Il reçoit un `T` et renvoie un booléen
- C) Il combine deux `T` en un seul
- D) Il ne prend aucun paramètre et renvoie un `T`

**Réponse : A** — Sa méthode abstraite est `void accept(T t)`. C'est ce qu'attend `forEach` : afficher, ajouter à une liste, écrire dans un fichier…

---

### Question 10 — `Supplier`

Que fait un `Supplier` ?

- A) Il ne prend aucun paramètre et renvoie un élément d'un type donné
- B) Il transforme un élément en un autre
- C) Il consomme un élément sans rien renvoyer
- D) Il teste une condition

**Réponse : A** — Sa méthode est `get()`. Comme son nom l'indique, il sert à fournir des données, par exemple lors de la lecture d'un fichier.

---

### Question 11 — Lambdas hors des streams

Les lambdas sont-elles réservées à l'API Stream ?

- A) Oui, elles n'ont de sens que dans un pipeline
- B) Non, mais uniquement dans les collections
- C) Oui, sauf pour les `Consumer`
- D) Non : toute interface fonctionnelle peut être implémentée par une lambda, par exemple un `Comparator`

**Réponse : D** — `Comparator` n'a qu'une méthode abstraite, `int compare(T o1, T o2)` : on peut donc écrire `employes.sort((e1, e2) -> e2.getTaille() - e1.getTaille());`.

---

### Question 12 — `map` : combien d'éléments en sortie ?

Un stream de 4 éléments passe par un `map`. Combien d'éléments en sortie ?

- A) Au moins 4
- B) Entre 0 et 4
- C) Toujours 4
- D) Cela dépend de la lambda

**Réponse : C** — `map` est une transformation 1 → 1 : le nombre d'éléments ne change jamais.

---

### Question 13 — `flatMap`

Que fait `flatMap` ?

- A) Il trie le stream avant de le transformer
- B) Il transforme chaque élément en un stream, puis concatène ces mini-streams en un seul stream aplati
- C) Il supprime les doublons
- D) Il transforme un stream en `Map`

**Réponse : B** — Chaque élément peut produire 0, 1 ou plusieurs éléments : le nombre de sortie n'a aucun rapport garanti avec celui d'entrée.

---

### Question 14 — Forme de la lambda

Quelle forme doit avoir la lambda passée à `flatMap` ?

- A) `T -> R`
- B) `T -> Stream<R>`
- C) `() -> T`
- D) `T -> boolean`

**Réponse : B** — À comparer avec `map`, qui attend `T -> R`. D'où l'écriture `.flatMap(trader -> trader.getActions().stream())`.

---

### Question 15 — Quand utiliser `flatMap`

Dans quelle situation `flatMap` est-il l'outil naturel ?

- A) Quand on veut trier des objets
- B) Quand on veut filtrer selon deux critères
- C) Quand on veut compter les éléments
- D) Quand le modèle de données est hiérarchique : une liste d'objets contenant chacun une liste

**Réponse : D** — Chaque `Trader` a 0, 1 ou plusieurs actions, et on veut un seul stream plat de toutes les actions de tous les traders.

---

### Question 16 — Rendre une classe immuable

Quelles conditions une classe doit-elle remplir pour être réellement immuable ?

- A) Attributs `final` assignés dans le constructeur, aucun setter, et aucune fuite de référence sur les attributs mutables
- B) Classe déclarée `final`
- C) Attributs `private` et getters uniquement
- D) Aucun attribut de type collection

**Réponse : A** — Ce sont les trois points de la fiche. `Portfolio` respecte les deux premiers mais pas le troisième.

---

### Question 17 — Fuite de référence

Le constructeur de `Portfolio` stocke directement la liste reçue et son getter la renvoie telle quelle. Quelle est la conséquence ?

- A) Aucune, les attributs sont `final`
- B) Le `Portfolio` ne compile pas
- C) Le code appelant peut modifier la liste après coup et changer l'état du `Portfolio` à son insu
- D) La liste devient automatiquement non modifiable

**Réponse : C** — `final` empêche de réassigner l'attribut, pas de modifier l'objet qu'il référence. `List.copyOf(actions)` corrige le problème.

---

### Question 18 — Bénéfice de l'immuabilité

Quel avantage l'immuabilité apporte-t-elle au raisonnement sur le code ?

- A) Les objets se construisent plus vite
- B) Les tests deviennent inutiles
- C) Le programme consomme moins de mémoire
- D) Une méthode qui reçoit un objet immuable a la garantie qu'il ne changera pas : le raisonnement devient local

**Réponse : D** — S'y ajoutent le partage sans copie défensive et la compatibilité avec les streams, y compris parallèles.

---

### Question 19 — Exception vérifiée dans une lambda

Pourquoi `.map(f -> Files.readString(f))` ne compile-t-il pas ?

- A) Parce qu'il manque un `collect`
- B) Parce que `Function.apply` ne déclare aucun `throws` : une lambda ne peut pas laisser sortir une exception vérifiée
- C) Parce que `map` n'accepte pas de référence de fichier
- D) Parce que `Files.readString` est statique

**Réponse : B** — La solution habituelle est d'encapsuler l'exception vérifiée dans une exception non vérifiée, en chaînant l'exception d'origine : `throw new RuntimeException("Impossible de lire " + f, e);`.

---

### Question 20 — Streams parallèles

Pourquoi les streams se prêtent-ils bien au traitement parallèle ?

- A) Parce qu'ils utilisent moins de mémoire
- B) Parce qu'ils suppriment les doublons
- C) Parce qu'ils trient automatiquement les données
- D) Parce que les opérations ne modifient pas le stream d'origine : il n'y a pas de risque de modifications concurrentes

**Réponse : D** — C'est aussi pourquoi travailler avec des objets immuables prolonge cette logique et rend le parallélisme sûr.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
