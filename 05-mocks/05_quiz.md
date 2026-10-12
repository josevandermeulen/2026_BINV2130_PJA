# Atelier 5 : stubs et mocks — questionnaire à choix multiple

20 questions à réponse unique, tirées de `05A_1_theorie.md` (questions 1 à 9) et `05B_1_theorie.md` (questions 10 à 20).

---

### Question 1 — Test unitaire ou test d'intégration

Quand parle-t-on d'un vrai test unitaire ?

- A) Quand il ne contient qu'une seule assertion
- B) Quand il teste le fonctionnement de plusieurs classes qui communiquent
- C) Quand le test s'exécute en moins d'une seconde
- D) Quand il ne teste qu'une seule classe, sans tester ses dépendances

**Réponse : D** — La proposition B décrit un test d'intégration. Pour rester unitaire, on simule le comportement des instances qui n'appartiennent pas au type de la classe testée.

---

### Question 2 — Pourquoi des interfaces

En quoi l'abstraction par interfaces facilite-t-elle les tests unitaires ?

- A) Elle produit un couplage faible, ce qui permet de créer facilement des faux objets pour tous les types autres que la classe sous test
- B) Elle évite d'écrire des constructeurs
- C) Elle supprime le besoin de tests
- D) Elle rend le code plus rapide à l'exécution

**Réponse : A** — L'auteur d'une classe peut alors changer son implémentation sans casser les interactions, et le test peut substituer un faux objet à la vraie dépendance.

---

### Question 3 — Ce qu'est un stub

Comment un stub décide-t-il de ce qu'il renvoie ?

- A) Il calcule une réponse à partir des arguments reçus
- B) Il renvoie toujours `null`
- C) Il interroge la vraie dépendance
- D) Il renvoie une réponse fixe, prédéterminée par le test, quels que soient les paramètres reçus

**Réponse : D** — Un stub est un faux objet « sans intelligence » : aucune logique, aucun calcul. C'est le test qui décide à l'avance ce qu'il doit répondre, typiquement via son constructeur.

---

### Question 4 — Ce que vérifie un test avec stub

Que vérifie-t-on dans un test qui utilise des stubs ?

- A) Les deux à parts égales
- B) La performance de l'objet sous test
- C) Le **comportement** : la façon dont l'objet a interagi avec ses collaborateurs
- D) L'**état** : le résultat produit par l'objet sous test

**Réponse : D** — Les stubs servent à tester l'état. Vérifier les interactions est le rôle des mocks, vus en séance 2.

---

### Question 5 — Quand utiliser un stub

Dans quelle situation un stub est-il l'outil adapté ?

- A) Quand la dépendance n'a pas d'interface
- B) Quand on a besoin des réponses d'un collaborateur, mais que le test ne porte pas sur l'interaction avec lui
- C) Quand on veut vérifier qu'une méthode du collaborateur a bien été appelée
- D) Quand on veut mesurer le temps d'exécution

**Réponse : B** — Exemple : le test porte sur le traitement d'objets venant d'une base de données, pas sur l'interface avec la base. Le stub fournit simplement les objets à traiter.

---

### Question 6 — Le modèle AAA

Que désignent les étapes du cycle de vie d'un test avec stubs ?

- A) Arrange, Act, Interact, Assert
- B) Act, Assert, Archive
- C) Analyse, Application, Assertion
- D) Arrange, Assert, Act

**Réponse : A** — *Arrange* prépare l'objet sous test et ses stubs, *Act* appelle la méthode testée, *Interact* est la collaboration effectuée par l'objet, *Assert* vérifie son état.

---

### Question 7 — Fake object

Qu'est-ce qu'un *fake object* ?

- A) Un synonyme de stub
- B) Une implémentation fonctionnelle simplifiée d'une dépendance, avec un raccourci qui la rend inadaptée à la production
- C) Un faux objet qui enregistre la façon dont il a été appelé
- D) Un objet qui lance systématiquement une exception

**Réponse : B** — Exemple typique : une base de données en mémoire à la place d'une vraie base de données.

---

### Question 8 — Spy

Qu'est-ce qu'un *spy* ?

- A) Un objet qui remplace la classe testée
- B) Un outil de mesure de couverture de code
- C) Un stub qui enregistre en plus les informations sur la façon dont il a été appelé, pour les vérifier après coup
- D) Un mock qui échoue systématiquement

**Réponse : C** — Nombre d'appels, paramètres reçus : le spy garde la trace de ces informations, que le test peut ensuite vérifier.

---

### Question 9 — Résultat d'un test avec stub

Dans l'exemple du cours, `DataServiceStub` est construit avec la valeur 5 et le test appelle `calculator.addToX(3, 4)`. Que doit renvoyer l'appel ?

- A) 7
- B) 5
- C) 12
- D) 0

**Réponse : C** — `addToX` renvoie `a + b + x`, soit `3 + 4 + 5`. Le stub fournit toujours 5, quels que soient les arguments.

---

### Question 10 — Ce que vérifie un mock

Que vérifie-t-on principalement avec un mock ?

- A) Le comportement : que les bonnes méthodes ont été appelées, avec les bonnes données, dans le bon ordre
- B) L'état final de l'objet sous test
- C) Le temps d'exécution
- D) La couverture de code

**Réponse : A** — Exemple : vérifier qu'on envoie bien les bonnes données au DAO. Rien n'empêche d'ajouter en plus des assertions sur l'état.

---

### Question 11 — Où se fait la vérification

Différence essentielle entre stub et mock quant à la vérification :

- A) Un mock ne permet aucune vérification
- B) Avec un mock, la vérification porte sur les interactions retenues par le mock, et non uniquement sur le résultat calculé dans la méthode de test
- C) Un stub vérifie l'ordre des appels
- D) Avec un stub comme avec un mock, tout se vérifie dans la méthode de test

**Réponse : B** — Contrairement au cas des stubs, la vérification ne porte pas seulement sur l'état : le mock retient ses interactions et le test les contrôle.

---

### Question 12 — La vérification finale

Pourquoi une vérification finale est-elle nécessaire dans un test avec mocks ?

- A) Pour libérer la mémoire
- B) Pour réinitialiser le mock avant le test suivant
- C) Parce que sans elle, un code qui n'aurait rien fait du tout passerait le test
- D) Parce que JUnit l'exige

**Réponse : C** — Il faut vérifier qu'on a bien appelé toutes les méthodes nécessaires : la vérification finale fait échouer le test si une méthode attendue n'a pas été appelée.

---

### Question 13 — Créer un mock avec Mockito

Comment crée-t-on un mock de l'interface `Stage` ?

- A) `new Stage()`
- B) `Stage.mock()`
- C) `Mockito.mock(Stage.class)`
- D) `Mockito.create(Stage)`

**Réponse : C** — Mockito crée dynamiquement l'objet à partir de l'interface, ce qui évite d'écrire une classe stub à la main.

---

### Question 14 — Configurer une valeur de retour

Que signifie `Mockito.when(stage.enregistrerMoniteur(moniteur)).thenReturn(true);` ?

- A) La méthode sera appelée automatiquement avec `moniteur`
- B) Le test échouera si la méthode renvoie `true`
- C) La vraie implémentation de `enregistrerMoniteur` sera exécutée
- D) L'appel de `enregistrerMoniteur` avec le paramètre `moniteur` renverra `true`

**Réponse : D** — C'est la configuration du mock : on lui dicte sa réponse pour un appel donné.

---

### Question 15 — Deux retours successifs

Que fait `Mockito.when(stage.enregistrerMoniteur(moniteur)).thenReturn(true).thenReturn(false);` ?

- A) Elle renvoie `true` au premier appel, puis `false` aux appels suivants
- B) Le code ne compile pas
- C) Elle renvoie `true` et `false` en même temps
- D) Elle renvoie `false` au premier appel, puis `true`

**Réponse : A** — Utile pour simuler un premier enregistrement accepté puis refusé, sans écrire deux tests distincts.

---

### Question 16 — Simuler une erreur

Comment fait-on qu'un appel sur un mock lance une exception ?

- A) C'est impossible avec Mockito
- B) `assertThrows` suffit, le mock n'a rien à configurer
- C) `Mockito.when(stage.enregistrerMoniteur(null)).thenThrow(IllegalArgumentException.class);`
- D) `Mockito.throwOn(stage).enregistrerMoniteur(null);`

**Réponse : C** — La simulation de cas d'erreur est justement l'une des fonctionnalités qui rendent ces librairies utiles au-delà de la simple valeur de retour.

---

### Question 17 — `Mockito.verify` sans option

Que vérifie exactement `Mockito.verify(stage).enregistrerMoniteur(moniteur);` ?

- A) Que la méthode a été appelée avec ce paramètre une et une seule fois
- B) Que la méthode n'a jamais été appelée
- C) Que la méthode renvoie `true`
- D) Que la méthode a été appelée au moins une fois

**Réponse : A** — Sans mode de vérification explicite, `verify` équivaut à `Mockito.times(1)`.

---

### Question 18 — Vérifier une absence d'appel

Comment vérifie-t-on qu'une méthode n'a **pas** été appelée sur un mock ?

- A) `assertFalse(stage.enregistrerMoniteur(moniteur));`
- B) `Mockito.verify(stage, Mockito.never()).enregistrerMoniteur(moniteur);`
- C) `Mockito.verify(stage, Mockito.times(0)).enregistrerMoniteur(moniteur);` uniquement
- D) `Mockito.verifyNot(stage).enregistrerMoniteur(moniteur);`

**Réponse : B** — `never()` exprime directement l'intention. C'est le pendant de `atLeastOnce()` et de `times(nb)`.

---

### Question 19 — Rôle de Maven

Quel est l'intérêt principal de Maven dans ces ateliers ?

- A) Générer automatiquement les tests unitaires
- B) Accélérer l'exécution des tests
- C) Remplacer IntelliJ
- D) Gérer les dépendances : les librairies à installer sont déclarées dans `pom.xml` et téléchargées depuis un repository

**Réponse : D** — Maven repose sur des fichiers de configuration listant les versions, des commandes, et des repositories distants contenant les librairies.

---

### Question 20 — Git et les dépendances

Que met-on sous gestion de version dans un projet Maven ?

- A) Rien, Maven gère lui-même le versionnement du projet
- B) Le `pom.xml` uniquement ; le code des dépendances est exclu via un `.gitignore`
- C) Uniquement le code des dépendances
- D) Le `pom.xml` et le code des librairies téléchargées

**Réponse : B** — Le code des dépendances ne nous appartient pas et est déjà versionné par Maven. Seul `pom.xml`, qui liste ce qu'il faut installer, est suivi.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
