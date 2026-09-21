# Atelier 2 : collections et énumérés — questionnaire à choix multiple

20 questions à réponse unique, tirées de `02A_1_theorie.md` (questions 1 à 11) et `02B_1_theorie.md` (questions 12 à 20).

---

### Question 1 — Comparer deux constantes d'énuméré

Comment compare-t-on correctement deux valeurs d'un énuméré ?

- A) Avec `==`, car la comparaison se fait sur la référence et `equals` ne peut pas être redéfini dans un énuméré
- B) Avec `compareTo` uniquement
- C) Avec `String.valueOf(...)` puis `equals`
- D) Avec `equals`, comme pour tout objet

**Réponse : A** — Il est interdit de redéfinir `equals`/`hashCode` dans un énuméré. Chaque constante est une instance unique, donc `==` est correct et suffisant.

---

### Question 2 — `ordinal()`

Soit `public enum JourDeLaSemaine { LUNDI, MARDI, MERCREDI, JEUDI; }`. Que renvoie `JourDeLaSemaine.MERCREDI.ordinal()` ?

- A) `1`
- B) `2`
- C) `3`
- D) `"MERCREDI"`

**Réponse : B** — `ordinal()` renvoie la position dans l'ordre de déclaration, en commençant à 0 : `LUNDI` → 0, `MARDI` → 1, `MERCREDI` → 2.

---

### Question 3 — `valueOf`

Que se passe-t-il à l'exécution de `JourDeLaSemaine.valueOf("lundi")`, l'énuméré déclarant la constante `LUNDI` ?

- A) `null` est renvoyé
- B) Une `IllegalArgumentException` est lancée
- C) La constante `LUNDI` est renvoyée, la casse est ignorée
- D) Le code ne compile pas

**Réponse : B** — `valueOf` exige le nom exact de la constante. Aucune constante ne s'appelle `lundi`, donc l'exception est lancée à l'exécution.

---

### Question 4 — Constructeur d'énuméré

Quelle visibilité un constructeur d'énuméré peut-il avoir ?

- A) N'importe laquelle
- B) `protected`, pour les sous-classes
- C) `public`, pour permettre la création de nouvelles constantes
- D) `private` uniquement — c'est la valeur par défaut, qu'on ne précise donc pas

**Réponse : D** — Un énuméré a un nombre fini de valeurs fixées à la déclaration. Le constructeur est nécessairement privé, ce qui interdit toute création d'instance supplémentaire.

---

### Question 5 — Choisir `List` ou `Set`

Vous devez stocker les étapes d'une recette, dans l'ordre, en autorisant qu'une même opération revienne plusieurs fois. Quelle structure choisir ?

- A) Une `ArrayList`, qui conserve l'ordre d'insertion et accepte les doublons
- B) Un `TreeSet`, qui trie automatiquement
- C) Un `HashSet`, plus rapide
- D) Une `Map`, avec le numéro d'étape comme clé

**Réponse : A** — Une `List` conserve l'ordre d'insertion, permet l'accès par position et accepte les doublons. Un `Set` refuserait la répétition et ne garantirait pas l'ordre.

---

### Question 6 — Positions dans une `List`

Une liste contient 3 éléments. Que produit `liste.get(3)` ?

- A) Une `IndexOutOfBoundsException`
- B) Le dernier élément
- C) `null`
- D) Une `IllegalArgumentException`

**Réponse : A** — Les positions vont de 0 à `size() - 1`, donc de 0 à 2 ici. Si l'énoncé d'un exercice demande une `IllegalArgumentException`, il faut tester la position soi-même avant d'accéder à la liste.

---

### Question 7 — Ajout dans un `Set`

Que renvoie `participants.add("Alice")` si `"Alice"` est déjà dans l'ensemble ?

- A) Une exception est lancée
- B) `null`
- C) `true`, l'élément est ajouté une seconde fois
- D) `false`, et l'ensemble n'est pas modifié

**Réponse : D** — Le `boolean` renvoyé indique si l'ensemble a effectivement changé. C'est pratique pour détecter un doublon sans test préalable.

---

### Question 8 — `HashSet` et objets métier

Vous placez des objets `Ingredient` dans un `HashSet`. Que doit fournir la classe `Ingredient` pour que les doublons soient correctement détectés ?

- A) `equals` et `hashCode`
- B) Rien, `HashSet` compare les références
- C) `compareTo`
- D) `toString`

**Réponse : A** — Un `HashSet` détecte les doublons via `hashCode` puis `equals`. Sans redéfinition, deux ingrédients identiques mais distincts en mémoire seront tous les deux acceptés.

---

### Question 9 — Vue non modifiable

Que se passe-t-il si on appelle `add` sur la liste renvoyée par `Collections.unmodifiableList(etapes)` ?

- A) Une `IllegalArgumentException` est lancée
- B) L'élément est ajouté à la liste interne
- C) Rien, l'appel est ignoré silencieusement
- D) Une `UnsupportedOperationException` est lancée

**Réponse : D** — La lecture et le parcours restent possibles, toute modification échoue. C'est ce qui permet à un getter d'exposer une collection sans casser l'encapsulation.

---

### Question 10 — `Duration` est immuable

Que vaut `duree` après ce code ?

```java
Duration duree = Duration.ofMinutes(90);
duree.plusMinutes(30);
```

- A) 30 minutes
- B) Le code ne compile pas
- C) 90 minutes
- D) 120 minutes

**Réponse : C** — `plusMinutes` ne modifie pas l'objet, il en renvoie un nouveau. Sans réaffectation (`duree = duree.plusMinutes(30);`), le résultat est perdu.

---

### Question 11 — Énuméré interne

`Meteo` déclare un énuméré interne `Tendance`. Comment y accède-t-on depuis une autre classe ?

- A) `new Meteo().Tendance.NUAGEUX`
- B) `Tendance.NUAGEUX`
- C) `Meteo.Tendance.NUAGEUX`
- D) C'est impossible, un énuméré interne est privé

**Réponse : C** — On préfixe le type interne par la classe englobante. Un énuméré interne est implicitement `static` : nul besoin d'une instance de `Meteo`.

---

### Question 12 — `Map` : clé absente

Que renvoie `dictionnaire.get("souris")` si cette clé n'est pas dans la `Map` ?

- A) La première valeur de la map
- B) `null`
- C) Une `NoSuchElementException`
- D) Une chaîne vide

**Réponse : B** — D'où la nécessité de tester le résultat (ou d'utiliser `containsKey`, ou `getOrDefault`) avant de s'en servir.

---

### Question 13 — Les vues d'une `Map`

Pourquoi `values()` renvoie-t-elle une `Collection` et non un `Set` ?

- A) Parce que les valeurs ne sont pas triables
- B) Parce qu'un `Set` serait trop lent
- C) Parce que `values()` renvoie une copie et non une vue
- D) Parce que deux clés différentes peuvent porter la même valeur : les doublons sont possibles

**Réponse : D** — Les clés sont uniques, donc `keySet()` renvoie un `Set`. Les valeurs, elles, peuvent être dupliquées, ce qu'un `Set` interdirait.

---

### Question 14 — `TreeSet` sans ordre

Vous créez `new TreeSet<Boisson>()` sans `Comparator`, et `Boisson` n'implémente pas `Comparable`. Quand le problème apparaît-il ?

- A) À la construction du `TreeSet`
- B) Jamais, l'ordre d'insertion est utilisé
- C) À la compilation
- D) À l'ajout du premier élément, par une `ClassCastException`

**Réponse : D** — Le `TreeSet` doit comparer les éléments pour les ranger. Sans `Comparator` ni ordre naturel, il ne le peut pas, et l'erreur ne se manifeste qu'à l'ajout.

---

### Question 15 — Doublons dans un `TreeSet`

Un `TreeSet<Boisson>` reçoit un `Comparator` qui ne compare que la contenance. On y ajoute deux boissons différentes de 25 cl. Que contient l'ensemble ?

- A) Les deux boissons
- B) Les deux, mais `contains` n'en trouvera qu'une
- C) Une seule : le second ajout est refusé, car la comparaison renvoie 0
- D) Une `IllegalArgumentException` est lancée

**Réponse : C** — Dans un `TreeSet`, l'égalité est déterminée par `compare`/`compareTo`, pas par `equals`. D'où la règle : la comparaison doit départager les objets distincts, en ajoutant au besoin un second critère.

---

### Question 16 — `Comparable` ou `Comparator`

Quelle différence essentielle sépare les deux interfaces ?

- A) `Comparable` renvoie un `boolean`, `Comparator` un `int`
- B) `Comparable` définit l'ordre naturel dans la classe elle-même ; `Comparator` définit un ordre externe, ce qui permet plusieurs ordres pour une même classe
- C) Elles sont interchangeables
- D) `Comparable` sert aux nombres, `Comparator` aux chaînes

**Réponse : B** — Avec `Comparable`, c'est la classe qui décide comment elle se compare, et modifier son `compareTo` affecte toutes les structures triées. Avec `Comparator`, la responsabilité passe à la structure de données.

---

### Question 17 — Classe anonyme

À quoi sert une classe anonyme lorsqu'on construit un `TreeSet` ?

- A) À fournir sur place l'implémentation d'un `Comparator` utilisé à un seul endroit, sans créer de classe nommée
- B) À trier plus rapidement
- C) À masquer le type des éléments
- D) À rendre le `TreeSet` immuable

**Réponse : A** — On instancie l'interface en donnant directement le corps de sa méthode. La classe créée n'a pas de nom parce qu'elle ne sert qu'à cet endroit.

---

### Question 18 — `==` sur des wrappers

Qu'affiche ce code ?

```java
Integer a = 127;
Integer b = 127;
Integer c = 200;
Integer d = 200;
System.out.println(a == b);
System.out.println(c == d);
```

- A) `false` puis `true`
- B) `true` puis `true`
- C) `true` puis `false`
- D) `false` puis `false`

**Réponse : C** — `Integer.valueOf` met en cache les instances de -128 à 127 : `a` et `b` référencent le même objet. Au-delà, chaque autoboxing crée une instance distincte. Conclusion : comparer les wrappers avec `equals`, jamais avec `==`.

---

### Question 19 — Unboxing d'un `null`

Que produit ce code si la clé `"pommes"` est absente de la map ?

```java
Integer valeur = stock.get("pommes");
int total = valeur + 1;
```

- A) Une erreur de compilation
- B) `total` vaut 0
- C) Une `NullPointerException`
- D) `total` vaut 1

**Réponse : C** — `get` renvoie `null`, et le `+ 1` force l'unboxing de ce `null`. La parade concise est `stock.getOrDefault("pommes", 0) + 1`.

---

### Question 20 — Clone superficiel et clone profond

Une classe `Commande` a un attribut `List<String> articles` et sa méthode `clone()` se contente de `return (Commande) super.clone();`. Quelle est la conséquence ?

- A) Le clone reçoit une liste vide
- B) Le clone et l'original partagent la même liste : ajouter un article à l'un modifie l'autre
- C) La compilation échoue
- D) Le clone reçoit une copie indépendante de la liste

**Réponse : B** — `super.clone()` fait une copie superficielle : pour un champ objet, seule la référence est recopiée. Pour un clone profond, il faut remplacer chaque champ mutable, par exemple `copie.articles = new ArrayList<>(this.articles);`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
