# Atelier 6 : streams — questionnaire à choix multiple

20 questions à réponse unique, tirées de `06A_1_theorie.md` (questions 1 à 14) et `06B_1_theorie.md` (questions 15 à 20).

---

### Question 1 — Programmation fonctionnelle

Qu'est-ce qui caractérise l'approche fonctionnelle par rapport à l'approche itérative ?

- A) On écrit des boucles plus courtes
- B) On travaille uniquement sur des nombres
- C) On évite les objets
- D) On décrit le résultat souhaité, pas la manière de l'obtenir

**Réponse : D** — Le parallèle donné dans le cours est SQL : on exprime une requête, et c'est le moteur qui choisit comment produire le résultat.

---

### Question 2 — Streams et données source

Que fait un stream à la collection dont il est issu ?

- A) Il en fait une copie complète en mémoire
- B) Il la trie sur place
- C) Il la vide au fur et à mesure du traitement
- D) Il ne la modifie pas : il fournit uniquement un résultat

**Réponse : D** — Un stream ne possède pas les données qu'il traite et s'interdit de les modifier. Il les traite en une passe, en conservant leur ordre.

---

### Question 3 — Opérations intermédiaires et terminales

Qu'est-ce qui distingue une opération intermédiaire d'une opération terminale ?

- A) L'intermédiaire ne peut apparaître qu'une fois par pipeline
- B) La terminale ne peut pas renvoyer de valeur
- C) L'intermédiaire renvoie un stream, ce qui permet de les enchaîner ; la terminale produit un résultat qui n'est pas un stream
- D) L'intermédiaire est plus rapide

**Réponse : C** — `filter`, `map`, `sorted`, `distinct`, `limit` sont intermédiaires ; `collect`, `forEach`, `count` sont terminales.

---

### Question 4 — Prédicat

Comment appelle-t-on une lambda expression qui renvoie un booléen ?

- A) Un prédicat
- B) Un opérateur binaire
- C) Un consommateur
- D) Un collecteur

**Réponse : A** — C'est ce que `filter` attend : le prédicat est appliqué à chaque élément du stream pour décider s'il est conservé.

---

### Question 5 — Type du paramètre d'une lambda

Dans `.filter(e -> e.getGenre() == Genre.HOMME)` appliqué à un stream d'`Employe`, d'où vient le type de `e` ?

- A) Il est déduit du contexte : le stream contient des `Employe`
- B) Il est déterminé à l'exécution
- C) Il doit être déclaré explicitement, sinon le code ne compile pas
- D) C'est toujours `Object`

**Réponse : A** — L'inférence dispense de répéter un type que le compilateur connaît déjà.

---

### Question 6 — `collect`

À quoi sert la méthode `collect` ?

- A) À compter les éléments du stream
- B) À supprimer les doublons
- C) À fusionner deux streams
- D) À transformer le stream en autre chose, par exemple une liste, via un `Collector` passé en paramètre

**Réponse : D** — `collect(Collectors.toList())` produit une `List` à partir du stream.

---

### Question 7 — Le mot-clé `var`

Que dire de `var s = transactions.stream();` ?

- A) `s` est de type `Object`
- B) `s` a un type dynamique, qui peut changer en cours d'exécution
- C) Le type est déduit à la compilation à partir de l'initialisation, et ne change plus ensuite
- D) `var` n'est autorisé que pour les streams

**Réponse : C** — L'inférence de type à la compilation n'est pas le typage dynamique de JavaScript ou Python : le type doit être déterminable dès l'initialisation.

---

### Question 8 — Référence de méthode

Qu'est-ce que `System.out::println` ?

- A) Une référence de méthode, raccourci pour la lambda `e -> System.out.println(e)`
- B) Un appel immédiat à `println`
- C) Une erreur de syntaxe
- D) Un `Collector`

**Réponse : A** — Une référence de méthode s'utilise quand la lambda se contente d'appeler une seule méthode.

---

### Question 9 — `map` du stream

Quel piège de vocabulaire l'opération `map` d'un stream comporte-t-elle ?

- A) Elle ne fonctionne que sur des `Map`
- B) Elle trie les éléments
- C) Elle n'a rien à voir avec la structure de données `Map`
- D) Elle renvoie une `HashMap`

**Réponse : C** — `map` applique une fonction à chaque élément et renvoie un nouveau stream, dont le type des éléments est celui du retour de la fonction.

---

### Question 10 — `sorted()` sans argument

Sur quoi repose `sorted()` appelée sans paramètre ?

- A) Sur l'ordre d'insertion
- B) Sur l'ordre naturel des éléments, celui défini par `compareTo`
- C) Sur l'ordre alphabétique du `toString`
- D) Sur un ordre aléatoire

**Réponse : B** — Pour un autre ordre, il faut fournir un `Comparator`, par exemple avec `Comparator.comparing(...)`.

---

### Question 11 — Inverser un tri

Comment trie-t-on les employés par taille décroissante ?

- A) `sorted(Comparator.comparingInt(Employe::getTaille), false)`
- B) `sortedDescending(Employe::getTaille)`
- C) `sorted().reverse()`
- D) `sorted(Comparator.comparingInt(Employe::getTaille).reversed())`

**Réponse : D** — `reversed()` s'applique au comparateur, pas au stream.

---

### Question 12 — Corps de lambda

Une lambda peut-elle contenir plusieurs instructions ?

- A) Non, une seule expression est autorisée
- B) Non, il faut alors écrire une classe anonyme
- C) Oui : on écrit son corps entre accolades, comme celui d'une méthode, avec un `return`
- D) Oui, mais uniquement dans un `forEach`

**Réponse : C** — La forme compacte `e -> expression` n'est qu'un raccourci de la forme à accolades.

---

### Question 13 — `reduce` et valeur neutre

À quoi sert la valeur neutre passée en premier argument de `reduce` ?

- A) À trier les éléments avant réduction
- B) À fixer le nombre d'éléments à traiter
- C) À fournir un résultat valable si le stream est vide, ce qui évite les erreurs
- D) À indiquer le type de retour

**Réponse : C** — `numbers.stream().reduce(0, (a, b) -> a + b)` renvoie 0 sur un stream vide plutôt que d'échouer.

---

### Question 14 — Lambda à deux paramètres

Dans quel contexte utilise-t-on une lambda de la forme `(a, b) -> a + b` ?

- A) Dans l'opération de réduction `reduce`, qui combine les éléments deux à deux
- B) Dans `forEach`
- C) Dans `map`
- D) Dans `filter`

**Réponse : A** — `reduce` attend un opérateur binaire, donc une lambda à deux paramètres.

---

### Question 15 — `findAny` et `Optional`

Quel est le type de retour de `findAny()` ?

- A) L'élément trouvé, ou `null`
- B) `Optional<T>`, un conteneur qui indique la présence ou l'absence de résultat
- C) Une `List<T>` d'un seul élément
- D) `boolean`

**Réponse : B** — `Optional` a justement été introduit pour éviter les bugs dus à `null` quand le stream est vide, à l'entrée ou après filtrage.

---

### Question 16 — `Optional.get()`

Que se passe-t-il si on appelle `get()` sur un `Optional` vide ?

- A) Il renvoie `null`
- B) Il lève une exception
- C) Il bloque le programme
- D) Il renvoie une valeur par défaut

**Réponse : B** — D'où l'usage de `isPresent()` avant `get()`, ou mieux, de `orElse(...)` pour rester en style fonctionnel.

---

### Question 17 — `orElse`

Que fait `orElse(valeur)` sur un `Optional` ?

- A) Il lève une exception si l'`Optional` est vide
- B) Il renvoie `valeur` si l'`Optional` est vide, sinon son contenu
- C) Il remplace toujours le contenu par `valeur`
- D) Il ajoute `valeur` au stream

**Réponse : B** — Cela évite le `if (isPresent())` et garde le pipeline lisible.

---

### Question 18 — `reduce` sans valeur neutre

Que renvoie `reduce` quand on l'appelle sans fournir de valeur neutre ?

- A) `null` si le stream est vide
- B) Un `Optional`
- C) `0`
- D) Une exception si le stream est vide

**Réponse : B** — Sans valeur neutre, il n'y a pas de résultat garanti sur un stream vide : le retour est donc un `Optional`.

---

### Question 19 — `groupingBy`

Que produit `menu.stream().collect(Collectors.groupingBy(Dish::getType))` ?

- A) Une `Map<Type, List<Dish>>`
- B) Une `Map<Dish, Type>`
- C) Une `List<Type>`
- D) Un `Set<Type>`

**Réponse : A** — `groupingBy` est une fonction de classification : la clé est la valeur renvoyée par la fonction, la valeur est la liste des éléments de ce groupe.

---

### Question 20 — Collecteur en aval

Que produit `menu.stream().collect(Collectors.groupingBy(Dish::getType, counting()))` ?

- A) Le nombre total de plats
- B) Une `List<Long>`
- C) Une `Map<Type, List<Dish>>`
- D) Une `Map<Type, Long>` donnant le nombre de plats par type

**Réponse : D** — Le second argument de `groupingBy` accepte n'importe quel autre collecteur, appliqué à chaque groupe.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
