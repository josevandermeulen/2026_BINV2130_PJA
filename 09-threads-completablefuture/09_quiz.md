# Atelier 9 : threads et CompletableFuture — questionnaire à choix multiple

20 questions à réponse unique, tirées de `09A_1_theorie.md` (questions 1 à 10) et `09B_1_theorie.md` (questions 11 à 20).

---

### Question 1 — Thread ou processus

Quel avantage les threads offrent-ils par rapport aux processus du système d'exploitation ?

- A) Ils s'exécutent toujours sur un processeur différent
- B) Ils ne peuvent pas planter
- C) Ils sont gérés par le compilateur et non par la JVM
- D) Ils sont plus légers, partagent le même espace d'adressage, et le changement de contexte y est moins coûteux

**Réponse : D** — Le partage de l'espace d'adressage rend en particulier la communication entre threads bien moins coûteuse qu'entre processus.

---

### Question 2 — Pourquoi plusieurs flux d'exécution

Laquelle de ces situations ne justifie **pas** le recours aux threads selon la fiche ?

- A) Exploiter plusieurs processeurs
- B) Attendre une réponse du monde extérieur (disque, utilisateur)
- C) Réduire la taille du code source
- D) Traiter chaque requête d'un serveur web indépendamment

**Réponse : C** — Les trois autres sont les motivations données : attente d'E/S, parallélisme matériel, et modèles qui s'expriment naturellement en flux indépendants.

---

### Question 3 — `start()` ou `run()`

Que se passe-t-il si on appelle `run()` au lieu de `start()` sur un `Thread` ?

- A) Le code s'exécute dans le thread courant, de manière synchrone : aucun nouveau thread n'est créé
- B) Une exception est levée
- C) Le thread démarre puis s'arrête immédiatement
- D) Rien, les deux sont équivalents

**Réponse : A** — `start()` demande à la JVM de créer un nouveau flux d'exécution, qui appellera `run()`.

---

### Question 4 — `Runnable` plutôt qu'hériter de `Thread`

Pourquoi l'approche `Runnable` est-elle plus souple ?

- A) La classe reste libre d'hériter d'autre chose, et un simple lambda suffit
- B) Elle ne nécessite pas d'appeler `start()`
- C) Elle s'exécute plus vite
- D) Elle gère automatiquement les exceptions

**Réponse : A** — `Runnable` est une interface fonctionnelle : elle n'occupe pas l'unique place d'héritage de la classe.

---

### Question 5 — `join()`

Que fait `join()` ?

- A) Il bloque le thread appelant jusqu'à ce que le thread visé ait terminé
- B) Il met le thread visé en pause
- C) Il démarre le thread
- D) Il fusionne deux threads en un

**Réponse : A** — À ne pas confondre avec `Thread.sleep(ms)`, qui met en pause le thread **courant**.

---

### Question 6 — Fin du `main`

Le thread qui exécute `main` se termine avant les threads qu'il a lancés. Que se passe-t-il ?

- A) Les autres threads passent en pause
- B) Les autres threads sont interrompus immédiatement
- C) La JVM continue jusqu'à ce que tous les threads non *daemon* aient fini
- D) Une exception est levée

**Réponse : C** — Le thread `main` est un thread comme un autre : sa fin n'arrête pas la JVM.

---

### Question 7 — Race condition

Quand une race condition apparaît-elle ?

- A) Uniquement quand un thread lit une donnée
- B) Uniquement en cas de deadlock
- C) Dès que deux threads s'exécutent en parallèle
- D) Dès que plusieurs threads lisent **et** modifient une même donnée partagée, rendant l'ordre des opérations imprévisible

**Réponse : D** — Deux exécutions du même programme peuvent alors donner des résultats différents.

---

### Question 8 — `synchronized`

Que garantit un bloc `synchronized` sur un même objet-verrou ?

- A) Que les threads s'exécutent dans l'ordre de leur création
- B) Qu'un seul thread à la fois peut l'exécuter, les autres attendant leur tour
- C) Qu'aucune exception ne peut y survenir
- D) Que le bloc s'exécute plus vite

**Réponse : B** — La lecture et l'écriture forment ainsi une opération atomique. Attention toutefois : les sections synchronisées font perdre du parallélisme et peuvent mener à des deadlocks.

---

### Question 9 — Deadlock

Comment reconnaît-on un deadlock ?

- A) Le résultat est faux mais le programme continue
- B) Le programme est figé, sans exception ni message d'erreur
- C) La JVM redémarre automatiquement
- D) Une exception `DeadlockException` est levée

**Réponse : B** — C'est ce qui le distingue d'une race condition, qui donne un résultat faux sans bloquer le programme. Un *thread dump* montre quels threads se bloquent et sur quels verrous.

---

### Question 10 — Éviter les deadlocks

Quelle est la parade la plus simple contre l'attente circulaire ?

- A) Utiliser `Thread.sleep` avant chaque verrou
- B) Toujours acquérir les verrous dans le même ordre global, dans tous les threads
- C) Augmenter le nombre de threads
- D) Supprimer tous les `synchronized`

**Réponse : B** — Casser une seule des quatre conditions de Coffman suffit. Autres bonnes habitudes : sections synchronisées courtes, pas d'appel bloquant en détenant un verrou, un seul verrou plutôt que plusieurs.

---

### Question 11 — Pourquoi `CompletableFuture`

Quelle limite des `Thread` bruts `CompletableFuture` vient-il lever ?

- A) Les threads ne peuvent pas s'exécuter en parallèle
- B) Récupérer un résultat, chaîner des traitements et gérer les erreurs demandent beaucoup de code fragile
- C) Les threads consomment trop de mémoire
- D) Les threads ne fonctionnent pas sur plusieurs processeurs

**Réponse : B** — On décrit la chaîne de traitements à appliquer à la valeur quand elle arrivera, plutôt que de gérer les threads à la main.

---

### Question 12 — `supplyAsync` et `runAsync`

Quelle est la différence entre les deux ?

- A) `supplyAsync` est bloquante, `runAsync` ne l'est pas
- B) `supplyAsync` s'exécute dans le thread courant
- C) `runAsync` crée un nouveau pool de threads
- D) `supplyAsync` prend un `Supplier` et renvoie une valeur ; `runAsync` prend un `Runnable` et n'en renvoie pas

**Réponse : D** — Dans les deux cas, l'appel rend la main immédiatement : le traitement s'exécute dans un autre thread.

---

### Question 13 — `join()` sur une future

Quand faut-il appeler `join()` ?

- A) Jamais, `join()` est déprécié
- B) Avant de démarrer le traitement
- C) Après chaque étape de la chaîne
- D) Au moment où l'on a vraiment besoin de la valeur, typiquement en fin de `main`

**Réponse : D** — `join()` bloque. Tout ce qui peut être décrit en chaîne asynchrone doit l'être plutôt que d'attendre.

---

### Question 14 — `thenApply`

Que fait `thenApply(fn)` ?

- A) Il consomme la valeur sans rien renvoyer
- B) Il transforme la valeur, comme `map` sur les streams
- C) Il rattrape les exceptions
- D) Il attend une seconde future

**Réponse : B** — Il produit un `CompletableFuture<U>` à partir d'une fonction `T -> U`.

---

### Question 15 — `thenApply` ou `thenCompose`

Que se passe-t-il si la fonction passée renvoie elle-même un `CompletableFuture` ?

- A) `thenApply` produirait un `CompletableFuture<CompletableFuture<U>>` imbriqué ; `thenCompose` aplatit la chaîne
- B) Les deux donnent le même résultat
- C) `thenCompose` produirait l'imbrication, `thenApply` l'aplatit
- D) Le code ne compile dans aucun des deux cas

**Réponse : A** — C'est exactement le rapport entre `map` et `flatMap` sur les streams.

---

### Question 16 — `thenAccept` et `thenRun`

Quelle différence sépare `thenAccept` de `thenRun` ?

- A) `thenRun` reçoit la valeur, `thenAccept` non
- B) `thenAccept` est bloquante
- C) Aucune, ce sont des synonymes
- D) `thenAccept` reçoit la valeur, `thenRun` exécute une action qui n'en a pas besoin

**Réponse : D** — Les deux renvoient un `CompletableFuture<Void>`.

---

### Question 17 — `thenCombine`

Que fait `thenCombine` ?

- A) Il exécute deux futures l'une après l'autre
- B) Il fusionne deux pools de threads
- C) Il attend les résultats de deux futures indépendantes, puis leur applique une fonction binaire
- D) Il annule la seconde future

**Réponse : C** — À distinguer de `thenCompose`, qui enchaîne un traitement asynchrone dépendant du résultat précédent.

---

### Question 18 — `allOf`

Que renvoie `CompletableFuture.allOf(futures)` ?

- A) La liste des résultats
- B) La première future terminée
- C) Un `CompletableFuture<Void>` complété quand toutes les futures passées le sont
- D) Un tableau de threads

**Réponse : C** — On l'utilise typiquement suivi de `.join()` pour attendre la fin de toutes les tâches.

---

### Question 19 — Gérer une erreur asynchrone

Que se passe-t-il si une exception survient dans la chaîne et qu'aucun `exceptionally` (ni `handle`, ni `whenComplete`) n'est présent ?

- A) L'exception est silencieusement ignorée
- B) La future renvoie `null`
- C) Elle ressort au moment du `join()`, sous forme de `CompletionException`
- D) Le programme s'arrête immédiatement

**Réponse : C** — `exceptionally` permet de la rattraper et de fournir une valeur de repli.

---

### Question 20 — `ExecutorService`

Pourquoi fournir son propre `ExecutorService` à `supplyAsync` ?

- A) Pour limiter le nombre de threads utilisés, au lieu du pool commun de la JVM
- B) Pour éviter les exceptions
- C) Pour garantir l'ordre d'exécution des tâches
- D) Pour rendre les tâches synchrones

**Réponse : A** — Avec `Executors.newFixedThreadPool(2)`, au plus deux tâches s'exécutent en même temps. Ne pas oublier `shutdown()`, sans quoi les threads du pool maintiennent la JVM en vie.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
