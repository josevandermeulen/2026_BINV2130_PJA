# Atelier 4 : TDD — questionnaire à choix multiple

20 questions à réponse unique, tirées de `04A_1_theorie.md` (questions 1 à 14) et `04B_1_theorie.md` (questions 15 à 20).

---

### Question 1 — Le cycle TDD

Dans quel ordre s'enchaînent les trois étapes du TDD ?

- A) Refactorer, écrire le test, écrire le code
- B) Écrire un test qui échoue, écrire le code pour le faire passer, refactorer
- C) Écrire le code, écrire le test, refactorer
- D) Écrire le test et le code ensemble, puis refactorer

**Réponse : B** — C'est le cycle *red-green-refactor* : *red* pour le test qui échoue, *green* pour le test qui réussit une fois le code écrit, *refactor* pour l'amélioration.

---

### Question 2 — Le rôle des tests en TDD

En TDD, à quoi servent les tests avant tout ?

- A) À remplacer la documentation utilisateur
- B) À mesurer les performances du code
- C) À servir de spécification qui guide la conception et l'écriture du code
- D) À vérifier le code une fois qu'il est terminé

**Réponse : C** — Le TDD est un processus où les tests sont utilisés comme spécification. C'est ce qui distingue le TDD d'un simple « écrire des tests après coup ».

---

### Question 3 — La phase *refactor*

Que doit-on impérativement préserver pendant l'étape de refactoring ?

- A) Les noms de toutes les variables
- B) Le comportement du code : tous les tests doivent continuer à passer
- C) Le nombre de lignes de code
- D) Rien, le refactoring peut tout changer

**Réponse : B** — On améliore la lisibilité, on élimine les duplications, on renomme — sans changer le comportement. Si un test casse, ce n'était pas un refactoring.

---

### Question 4 — Avantage du TDD

Quel bénéfice le TDD apporte-t-il au moment de modifier du code existant ?

- A) Il n'y a plus besoin de relire le code
- B) Le code s'exécute plus vite
- C) Tout changement peut se faire en confiance : si tous les tests passent, les modifications n'ont rien cassé
- D) Le code devient plus court

**Réponse : C** — Cela diminue la peur de changer son code, et encourage donc à l'améliorer.

---

### Question 5 — Cas positifs et cas négatifs

Que recouvre la distinction entre cas positif et cas négatif ?

- A) Les tests rapides et les tests lents
- B) Les tests qui passent et ceux qui échouent
- C) Le comportement attendu quand tout se passe bien, opposé à celui attendu quand une entrée est invalide ou qu'une opération échoue
- D) Les tests unitaires et les tests d'intégration

**Réponse : C** — C'est la distinction « habituel / inhabituel » : les deux doivent être spécifiés pour chaque cas d'utilisation.

---

### Question 6 — Cas limites

Pourquoi accorde-t-on une attention particulière aux cas limites ?

- A) Ils remplacent les cas négatifs
- B) Ils sont exigés par JUnit
- C) Ils sont plus rapides à tester
- D) Ce sont les valeurs à la frontière d'une condition, celles que le code traite rarement correctement du premier coup

**Réponse : D** — Une chaîne vide, une liste à un seul élément, la dernière tâche qu'on retire : ce sont ces valeurs-là qui révèlent les bugs.

---

### Question 7 — Classes d'équivalence

À quoi sert le découpage en classes d'équivalence ?

- A) À regrouper les valeurs qui devraient produire le même comportement, pour n'en tester qu'un représentant par classe
- B) À répartir les tests entre plusieurs développeurs
- C) À créer une classe Java par type de test
- D) À trier les tests par ordre d'importance

**Réponse : A** — Pour un nom de tâche, `null` et une chaîne blanche appartiennent à la même classe « nom vide » : inutile de tester toutes les valeurs possibles de cette classe.

---

### Question 8 — Cas d'utilisation couvert indirectement

Dans l'exemple de la TodoList, pourquoi l'UC « vérifier qu'une tâche est contenue dans la liste » ne reçoit-il pas de scénario de test propre ?

- A) Parce qu'il est déjà couvert par les scénarios associés à l'ajout de tâches
- B) Parce que `containsTask` est une méthode privée
- C) Parce qu'il est trop simple à tester
- D) Parce qu'il sera testé en séance 2

**Réponse : A** — Les scénarios de l'UC1 vérifient déjà la présence ou l'absence de la tâche après ajout. Écrire des scénarios séparés n'apporterait rien.

---

### Question 9 — Écrire le test à l'envers

Par quoi commence-t-on l'écriture d'un test en TDD ?

- A) Par la création des classes et méthodes nécessaires
- B) Par les assertions, pour des méthodes et objets qui n'existent souvent pas encore
- C) Par le `@BeforeEach`
- D) Par le `main`

**Réponse : B** — On identifie d'abord ce qu'on veut vérifier, puis on utilise l'éditeur pour générer le contexte : classes, objets et méthodes manquants.

---

### Question 10 — Échouer pour de bonnes raisons

Pourquoi ne suffit-il pas qu'un test échoue, encore faut-il qu'il échoue « pour de bonnes raisons » ?

- A) Parce que les erreurs de compilation ne comptent pas comme des échecs
- B) Parce qu'un test peut échouer à cause d'un setup incomplet, et non parce que le comportement testé est absent
- C) Parce qu'un test doit échouer exactement une fois
- D) Parce que JUnit distingue plusieurs types d'échecs

**Réponse : B** — Si `todoList` et ses méthodes n'existent pas encore, l'échec ne dit rien du comportement. On complète d'abord le setup — quitte à écrire des méthodes qui renvoient `false` — puis on observe un vrai rouge.

---

### Question 11 — Implémentation évidente

Que conseille le TDD au moment d'écrire le code qui fait passer le test ?

- A) Écrire l'implémentation évidente, en évitant le code trivial comme une valeur hardcodée qu'on sait devoir changer
- B) Écrire d'abord les getters et setters, puis les tester
- C) Écrire l'implémentation la plus générale possible, pour anticiper les tests suivants
- D) Toujours commencer par renvoyer une valeur en dur

**Réponse : A** — Si on sait qu'il faut une liste, autant la créer tout de suite. Et il est inutile de tester les getters et setters.

---

### Question 12 — La règle de trois

Que dit la règle de trois appliquée au refactoring ?

- A) Trois personnes doivent relire chaque refactoring
- B) Chaque méthode doit tenir en trois lignes
- C) On attend en moyenne trois duplications avant de factoriser le code
- D) Un refactoring doit se faire en trois étapes

**Réponse : C** — C'est un compromis entre créer de l'abstraction trop tôt et attendre trop longtemps. C'est une moyenne : si un motif va manifestement revenir, on peut factoriser plus tôt.

---

### Question 13 — Duplication dans les tests

Que faire quand la duplication apparaît dans les scénarios de test eux-mêmes ?

- A) Fusionner les tests concernés en un seul
- B) La supprimer systématiquement, comme dans le code de production
- C) L'ignorer, la duplication n'existe pas dans les tests
- D) Vérifier que les tests restent facilement lisibles — parfois, on préfère garder la duplication

**Réponse : D** — La lisibilité d'un scénario de test prime : un test factorisé à l'excès devient difficile à comprendre quand il échoue.

---

### Question 14 — TDD assisté par IA

Pourquoi demander à une IA de générer le test **et** son implémentation d'un seul coup va-t-il à l'encontre du TDD ?

- A) Parce qu'on ne voit jamais le test échouer, et qu'on perd la confirmation que le test vérifie bien ce qu'on croit
- B) Parce que le refactoring devient impossible
- C) Parce que l'IA ne connaît pas JUnit
- D) Parce que le code généré est toujours moins performant

**Réponse : A** — Un test qui n'a jamais échoué peut être un test qui ne teste rien. La parade : demander d'abord le test seul (*red*), puis le code minimal (*green*), puis le refactoring.

---

### Question 15 — Le filet de sécurité

Quand le bénéfice principal du TDD se manifeste-t-il vraiment ?

- A) Au moment où le code doit évoluer
- B) Au moment de la rédaction de la documentation
- C) Au moment de la première écriture du code
- D) Au moment de la livraison

**Réponse : A** — Sans ce filet, chaque évolution demanderait de re-vérifier manuellement tous les comportements existants — ce que personne ne fait réellement, et c'est ainsi que naissent les régressions.

---

### Question 16 — Faire évoluer une fonctionnalité

Une exigence change : une tâche n'est plus une `String` mais un objet. Par quoi commence-t-on ?

- A) Par supprimer les tests devenus obsolètes
- B) Par mettre à jour le code de production, puis les tests
- C) Par mettre à jour les tests existants, ce qui produit un rouge pour de bonnes raisons
- D) Par écrire une nouvelle classe de test à côté de l'ancienne

**Réponse : C** — Le cycle est le même qu'en séance 1, mais il démarre des tests existants plutôt que d'une page blanche.

---

### Question 17 — Un scénario qui n'a plus de sens

Que faire d'un ancien scénario de test devenu incompatible avec la nouvelle exigence ?

- A) Le garder tel quel et accepter qu'il soit rouge
- B) Le désactiver avec `@Disabled` et passer à la suite
- C) Le supprimer silencieusement
- D) Le mettre à jour : c'est la spécification qui a changé

**Réponse : D** — Supprimer un scénario sans le remplacer fait disparaître une partie de la spécification sans que personne ne s'en aperçoive.

---

### Question 18 — Quand ajouter de nouveaux tests

Dans quel cas faut-il créer de nouveaux scénarios plutôt que d'adapter les existants ?

- A) Jamais : adapter les tests existants suffit toujours
- B) Quand le nombre de tests devient inférieur au nombre de méthodes
- C) Uniquement quand une nouvelle classe est créée
- D) Quand la modification introduit un nouveau comportement, un nouveau cas limite ou une nouvelle règle de validation

**Réponse : D** — Le passage d'une `String` à un objet introduit par exemple la question de l'égalité entre deux objets distincts de même contenu : cela mérite son propre scénario.

---

### Question 19 — Une nouvelle règle de validation

Un constructeur refuse désormais un titre vide. Faut-il le tester ?

- A) Uniquement si l'exigence vient du client
- B) Oui, au même titre qu'une méthode
- C) Non, seules les méthodes se testent
- D) Uniquement si le constructeur est public

**Réponse : B** — Une règle de validation est un comportement comme un autre, quel que soit l'endroit où elle est implémentée.

---

### Question 20 — IA et évolution du code

Quel risque supplémentaire apparaît quand on demande à une IA d'adapter un test existant à une nouvelle exigence ?

- A) Elle risque de supprimer les tests qui passent
- B) Elle risque de renommer toutes les classes
- C) Elle risque de générer des tests plus lents
- D) Elle risque de mettre à jour le test **et** le code de production en même temps, ce qui escamote la phase *red*

**Réponse : D** — La parade est la même qu'en séance 1 : demander d'abord uniquement la mise à jour du test, l'exécuter, et seulement ensuite la mise à jour du code.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
