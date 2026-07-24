# Atelier 4 – partie 1 : TDD – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Introduction au Test Driven Development](#introduction-au-test-driven-development)
3. [Spécifier les tests](#spécifier-les-tests)
4. [Implémenter les tests et le code de l'application](#implémenter-les-tests-et-le-code-de-lapplication)
5. [TDD en résumé](#tdd-en-résumé)
6. [TDD assisté par IA](#tdd-assisté-par-ia)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Introduction au Test Driven Development

Ce document reprend les notions utiles avant le chapitre « Un exemple ».

Le TDD est un processus de développement de logiciel où les tests sont utilisés comme une spécification pour concevoir et écrire le code.

Les 3 étapes du TDD :

1. **Écrire un test qui échoue** : on écrit un test qui décrit ce que notre code devra faire.
2. **Écrire le code pour faire passer ce test** : on écrit le code le plus simple permettant le succès du test.
3. **Refactor du code** : on va considérer une mise à jour du code selon les bonnes pratiques de l'OO. S'il y a des aspects qui peuvent être améliorés, on va faire un refactor du code pour qu'il soit plus facilement maintenable, en éliminant les duplications, en diminuant les dépendances, en renommant des méthodes, variables, en augmentant l'efficacité… tout cela sans changer le comportement du code : le test doit toujours réussir.

Ce cycle est communément appelé **red-green-refactor** : *red* pour le test qui échoue (étape 1), *green* pour le test qui réussit une fois le code écrit (étape 2), *refactor* pour l'amélioration du code (étape 3).

Voici certains des avantages du TDD :

1. Le programmeur reçoit un rapide feedback sur ce qu'il produit :
  1. Tout changement de code peut se faire en toute confiance car si tous les tests passent, c'est que les modifications n'ont rien changé !
  2. Cela diminue donc la peur de changer son code et encourage à l'améliorer.
2. Le programmeur doit se mettre dans la peau de l'utilisateur lors de l'écriture du test permettant de produire du code répondant mieux aux besoins.
3. Le programmeur doit se focaliser sur l'écriture de classes concrètes, sans en faire trop, en évitant ainsi des généralisations et optimisations du code prématurées.

## Spécifier les tests

### Comment faire ?

Avant de spécifier des tests, il faut savoir ce que l'on souhaite développer : identifiez les fonctionnalités à développer sous forme de liste. Nous appellerons ces fonctionnalités les cas d'utilisation (use cases ou UC).

Puis, par cas d'utilisation, identifiez :

1. D'abord, le comportement attendu habituel ;
2. Puis, le comportement inhabituel.

Donnez un nom à chaque comportement spécifié : ce nom identifiera le scénario de test correspondant.

Pour identifier ces comportements de façon systématique plutôt qu'au hasard, appuyez-vous sur trois techniques (voir les vidéos associées) :

1. **Cas positifs et cas négatifs** : pour chaque UC, on distingue le comportement attendu quand tout se passe bien (cas positif) du comportement attendu quand une entrée est invalide ou qu'une opération échoue (cas négatif). C'est la distinction "habituel / inhabituel" évoquée ci-dessus.
2. **Cas limites** : ce sont les valeurs qui se trouvent à la frontière d'une condition (une chaîne vide, une liste qui ne contient qu'un seul élément, la dernière tâche qu'on retire d'une liste…). Ce sont souvent ces valeurs-là qui révèlent des bugs, car le code les traite rarement correctement du premier coup.
3. **Classes d'équivalence** : pour un même paramètre, on regroupe les valeurs possibles en classes qui, du point de vue du code, devraient produire le même comportement (par exemple, pour un nom de tâche : `null` et une chaîne blanche appartiennent à la même classe « nom vide »). Il suffit de tester un représentant par classe plutôt que toutes les valeurs possibles.

### Un exemple

Nous souhaitons développer une mini application qui permette de gérer une liste de tâches.

Nous allons donc identifier tous les cas d'utilisation et le comportement attendu :

1. **(UC1) Ajouter une tâche à la liste :**
  1. `addTask` : la tâche est contenue dans la liste, on informe du succès de l'opération
  2. `addEmptyTask` : on tente d'ajouter une tâche vide (constituée uniquement de caractères « blancs » ou nulle), la tâche n'est pas contenue dans la liste, on informe de l'échec de l'opération
  3. `addExistingTask` : on tente d'ajouter une tâche déjà présente, on informe de l'échec de l'opération
2. **(UC2) Vérifier qu'une tâche est contenue dans la liste :**
  1. La tâche est présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
  2. La tâche n'est pas présente et on l'indique (pas besoin d'identifier ce scénario de tests car c'est couvert par les scénarios associés à l'UC1)
3. **(UC3) Supprimer une tâche de la liste :**
  1. `removeTask` : la tâche n'est plus contenue dans la liste, on informe du succès de l'opération
  2. `removeUnexistingTask` : on tente de supprimer une tâche inexistante, on informe de l'échec de l'opération

Cette liste permet d'identifier les scénarios de tests et peut vite devenir longue. Si vous souhaitez travailler de manière incrémentale et aller plus rapidement dans votre code, n'hésitez pas à créer votre spécification de tests de manière incrémentale :

1. Vous identifiez tous les scénarios de tests associés à un UC.
2. Vous faites du TDD pour chacun des scénarios de tests.
3. Vous identifiez tous les scénarios de tests associés à une autre UC.
4. Vous faites du TDD pour chacun de ces nouveaux scénarios de tests.
5. …

L'idée est de commencer les tests avec l'UC et le scénario de test qui semble le plus important, un scénario qui nous encourage à mettre en place le squelette de notre application.

## Implémenter les tests et le code de l'application

### Écrire un test qui échoue

**Point-clé : travailler à l'envers à partir des assertions.**

On identifie d'abord ce que l'on veut vérifier, en écrivant les asserts attendus — pour une méthode et un objet qui souvent n'existent pas encore. Puis on utilise l'éditeur de code pour générer le contexte du test : les classes et objets nécessaires.

Si vous voulez plus de détails sur les fonctionnalités offertes par IntelliJ pour faire du TDD, vous pouvez consulter la documentation : *Test-driven development*. Nous allons reprendre ces fonctionnalités dans le tutoriel qui suit.

Sous IntelliJ, veuillez créer un projet nommé `AJ_atelier04_partie1`.

Commençons par le scénario de tests que nous trouvons le plus important : « (addTask) Ajouter une tâche à la liste : la tâche est contenue dans la liste, on informe du succès de l'opération ».

Nous allons d'abord créer la classe de tests `TodoListTest` :

1. Dans le dossier `tests` (marqué Test Sources Root) : clic droit, New | Java Class
2. Mettre son curseur dans le corps de la classe, `Alt+Insert`, Test…
3. Cliquer une fois sur *Fix* si JUnit5 n'est pas trouvé. Cela ajoutera la bibliothèque JUnit5 au projet. Attention : le message *Fix* reste même si vous avez ajouté la dépendance.
4. *Class name* doit garder le nom `TodoListTest` (cela fait en sorte de ne pas créer une nouvelle classe mais de mettre à jour la classe existante)
5. Cliquer sur OK.

Pour créer le scénario `addTask` :

1. Mettre son curseur dans le corps de la classe : `Alt+Insert`, Test Method. Il ne reste plus qu'à donner le nom `addTask` à la méthode de test et d'écrire les tests qu'on veut effectuer.

```java
@Test
void addTask() {

  assertAll(
    () -> assertTrue(todoList.addTask("task 1")),
    () -> assertTrue(todoList.containsTask("task 1"))
  );
}
```

**Point-clé : voir le test échouer pour les bonnes raisons.**

Est-il intéressant à ce stade-ci de voir le test échouer ?

On ne veut pas que le test échoue juste parce que le setup du test est mauvais. Ici, il est clairement mauvais, puisque `todoList` n'existe pas, tout comme les méthodes `addTask` & `containsTask` !

Cela signifie qu'avant d'écrire du code qui va faire réussir notre test, on doit s'assurer que le test échoue pour de bonnes raisons.

Nous allons donc continuer en écrivant le contexte du test, le setup.

En passant sur `todoList`, on peut accéder aux actions en cliquant dessus. Sinon, en laissant le curseur sur la variable non déclarée, on peut accéder aux actions en faisant `Alt+Entrée` : *Create local variable 'todoList'*. Donnons-lui le type `TodoList`.

```java
TodoList todoList;
```

Voici comment créer la classe `TodoList` :

1. Passer le curseur sur `TodoList` dans le scénario de test et choisir l'action *Create class 'TodoList'*.
2. Indiquer le dossier `src` (marqué Sources Root) pour *Target destination directory*.

La variable locale `todoList` doit maintenant devenir un objet de `TodoList`. On fait donc appel au constructeur par défaut.

```java
TodoList todoList = new TodoList();
```

Via IntelliJ, nous pouvons automatiquement générer les méthodes `addTask` et `containsTask` (via `Alt+Entrée` ou en passant la souris sur ces méthodes) :

1. *Create method 'addTask' in TodoList* : on fait en sorte que la méthode fasse échouer le test :

```java
public boolean addTask(String task) {
  return false;
}
```

2. *Create method 'containsTask' in TodoList* : on fait en sorte que la méthode fasse échouer le test :

```java
public boolean containsTask(String task) {
  return false;
}
```

Ça y est, nous avons un bon setup de test qui nous permet de voir échouer le test pour des bonnes raisons !

Voici à quoi ressemble le code du scénario de test :

```java
@Test
void addTask() {

  TodoList todoList = new TodoList();
  assertAll(
    () -> assertTrue(todoList.addTask("task 1")),
    () -> assertTrue(todoList.containsTask("task 1"))
  );
}
```

### Écrire le code pour faire passer le test

**Point-clé : écrire l'implémentation qui est évidente en évitant les étapes triviales de tests.**

Il est inutile d'écrire du code trop trivial comme renvoyer une valeur hardcodée dans une fonction quand on sait pertinemment que cette valeur va changer au cours du temps. Dans ce cas, autant directement créer un attribut. Idem si on doit ajouter un élément à une liste, autant créer cette liste. Il est notamment inutile de tester les getters & setters. Et quand une implémentation n'est pas si évidente, alors n'écrivez que le code permettant de passer le test…

Nous allons commencer par la méthode `addTask`. Voici l'implémentation évidente pour ajouter une tâche :

```java
public class TodoList {

  private List<String> tasks = new ArrayList<>();

  public boolean addTask(String task) {
    return tasks.add(task);
  }

  public boolean containsTask(String task) {
    return false;
  }
}
```

À ce stade-ci, l'exécution du test `addTask` passe le premier assert, mais pas le deuxième associé à la méthode `containsTask`.

Nous allons donc mettre à jour la méthode `containsTask` :

```java
public boolean containsTask(String task) {
  return tasks.contains(task);
}
```

Ça y est, nous avons passé le premier test unitaire !

### Refactor du code

**Point-clé : appliquer la règle de trois quand il y a de la duplication dans notre code avant de créer du code à réutiliser.**

La réutilisation de code gagne généralement sur la duplication. Car s'il y a plus de code, il y a plus de risque de bugs, et le code est moins lisible.

Néanmoins, afin de trouver un bon compromis entre créer de l'abstraction trop rapidement, et attendre trop longtemps avant de le faire, en moyenne, on conseille d'attendre qu'il y ait 3 duplications dans notre code avant de faire un refactoring.

Ceci doit être pris comme une moyenne : si vous sentez qu'il y a un pattern qui va souvent revenir dans votre code, vous pouvez bien sûr le faire plus rapidement.

Notons que la duplication peut se trouver tant dans le code des scénarios de test, que dans les objets à tester.

En cas de duplication dans vos scénarios de tests, vérifiez que vos tests restent facilement lisibles. Parfois cela amène à préférer de la duplication dans nos scénarios de tests.

Au niveau du tutoriel sur la gestion d'une TodoList, il n'y a pas encore de duplication de code.

### Continuer les itérations

Le but est de continuer à développer sur base des scénarios de tests jusqu'à ce que tous les UC soient implémentés.

Nous allons continuer le tutoriel avec ce scénario : « (addEmptyTask) Ajouter une tâche à la liste : on tente d'ajouter une tâche vide (constituée uniquement de caractères « blancs » ou nulle), la tâche n'est pas contenue dans la liste, on informe de l'échec de l'opération ».

1. Écrire un test qui échoue :

```java
@Test
void addEmptyTask() {
  TodoList todoList = new TodoList();
  assertAll(
      () -> assertFalse(todoList.addTask("")),
      () -> assertFalse(todoList.containsTask("")),
      () -> assertFalse(todoList.addTask(null)),
      () -> assertFalse(todoList.containsTask(null))
  );
}
```

En l'exécutant, ce test échoue bien.

2. Écrire le code pour faire passer ce test :

```java
public boolean addTask(String task) {
  if (task == null) {
    return false;
  }
  if (task.isBlank()) {
    return false;
  }
  return tasks.add(task);
}
```

Ce test passe ! Vérifiez que tous les tests continuent à passer !

3. Refactor :

Ici, on sent au niveau des scénarios de tests qu'il y a un pattern qui revient régulièrement : on aura quasi toujours besoin d'une TodoList vide pour commencer un scénario de test. Nous allons donc créer un attribut, et le réinitialiser avant chaque test au sein de la méthode `setUp` de la classe de test :

1. Dans `TodoListTest`, `Alt+Insert`, SetUp Method
2. Création d'un attribut et initialisation de celui-ci dans `setUp`
3. Utilisation de l'attribut `todoList` dans les scénarios de test

Voici le code à cette étape-ci :

```java
public class TodoListTest {

  private TodoList todoList;

  @BeforeEach
  void setUp() {
    todoList = new TodoList();
  }

  @Test
  void addTask() {
    assertAll(
        () -> assertTrue(todoList.addTask("task 1")),
        () -> assertTrue(todoList.containsTask("task 1"))
    );
  }

  @Test
  void addEmptyTask() {
    assertAll(
            () -> assertFalse(todoList.addTask("")),
            () -> assertFalse(todoList.containsTask("")),
            () -> assertFalse(todoList.addTask(null)),
            () -> assertFalse(todoList.containsTask(null))
    );
  }

}
```

Nous allons terminer le tutoriel avec ce scénario : « (addExistingTask) Ajouter une tâche à la liste : on tente d'ajouter une tâche déjà présente, on informe de l'échec de l'opération ».

1. Écrire un test qui échoue :

```java
@Test
void addExistingTask() {
  todoList.addTask("task 1");
  assertFalse(todoList.addTask("task 1"));
}
```

En l'exécutant, ce test échoue bien.

NB : notons que si l'on voulait avoir la certitude absolue que la tâche n'a pas été ajoutée, il faudrait créer une nouvelle méthode pour compter le nombre de tâches associées à la TodoList. Néanmoins, pour ce tutoriel, on se satisfait du fait que si la méthode `addTask` renvoie `false`, c'est que d'office le `tasks.add(task)` n'a pas été appelé !

2. Écrire le code pour faire passer ce test :

```java
public boolean addTask(String task) {
  if (task == null) {
    return false;
  }
  if (task.isBlank()) {
    return false;
  }
  if (containsTask(task)) {
    return false;
  }
  return tasks.add(task);
}
```

Ce test passe ! Vérifiez que tous les tests continuent à passer !

3. Refactor : pas de duplication de code à cette étape-ci.

NB : l'état du projet à la fin de ce tutoriel vous est fourni dans `01-code-java/`.

## TDD en résumé

1. Commencez par identifier les cas d'utilisation de votre application et identifier les scénarios de tests associés.
2. Pour chaque scénario de test :
  1. **Écrire un test qui échoue** — démarrez à partir des assertions et visualisez que le test échoue pour de bonnes raisons !
  2. **Écrire le code minimum pour faire passer le test** — écrivez une implémentation évidente tout en évitant le code trivial.
  3. **Refactor du code** — appliquez la règle de trois : dès qu'il y a 3 duplications dans votre code, remplacez celui-ci par du code réutilisable ; sinon, laissez votre code en l'état. Assurez-vous que tous les tests continuent à passer après le refactor.

## TDD assisté par IA

Les assistants de code basés sur l'IA (Claude Code, GitHub Copilot, …) peuvent aujourd'hui générer à la fois un test et son implémentation en une seule fois. Cela va à l'encontre du principe même du TDD : si le code et le test arrivent ensemble, on ne voit jamais le test échouer, et on perd le bénéfice principal du cycle — la confirmation que le test vérifie bien ce qu'on croit vérifier.

Pour tirer parti de l'IA sans perdre les bénéfices du TDD, il faut continuer à respecter le cycle red-green-refactor, étape par étape, même quand une IA écrit le code à votre place :

1. **Red** : demandez à l'IA de générer uniquement le scénario de test (pas l'implémentation). Exécutez-le et vérifiez qu'il échoue pour de bonnes raisons — les mêmes vérifications que dans la section *Écrire un test qui échoue* ci-dessus s'appliquent, que le test soit écrit par vous ou par l'IA.
2. **Green** : demandez ensuite à l'IA d'écrire le code minimal pour faire passer ce test précis (pas les suivants). Exécutez les tests et vérifiez qu'ils passent tous.
3. **Refactor** : demandez à l'IA d'appliquer la règle de trois si de la duplication est apparue. Relancez les tests pour confirmer qu'ils passent toujours après le refactor.

**Point-clé : ne jamais valider un test et son implémentation générés ensemble sans être passé par l'étape red.** Un test qui n'a jamais échoué peut être un test qui ne teste rien (mauvais setup, assertion toujours vraie, etc.) — le risque est le même qu'avec du code écrit à la main, mais plus facile à manquer quand tout est généré d'un coup.

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 05/10/2026 à 20h.*

*Passez aux [exercices](04A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
