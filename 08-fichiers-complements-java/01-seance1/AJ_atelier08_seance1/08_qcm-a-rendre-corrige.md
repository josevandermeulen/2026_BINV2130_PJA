# Atelier 8 : QCM en Markdown — corrigé

Corrigé du QCM en Markdown de l'évaluation continue, celui que vous avez complété et remis sous
la forme d'un fichier Markdown. Énoncé : [`08_qcm-a-rendre.md`](08_qcm-a-rendre.md).

Les 10 questions, cotées 2 points chacune, sont reprises des questionnaires des ateliers 1 à 7 :
chacune indique sa provenance, pour que vous puissiez y revenir dans son contexte d'origine.

---

### Question 1 — Attribut statique

**Réponse : A** — `numeroSuivant` est statique, donc partagé par tous les badges : il vaut 1, puis 2, puis 3 au fil des créations. `numero` est un attribut d'instance : chaque badge garde la valeur qu'il a reçue.

*Reprise de l'atelier 1, question 2.*

---

### Question 2 — Contrat `equals` / `hashCode`

**Réponse : C** — Deux objets égaux selon `equals` doivent renvoyer le même `hashCode`. Sans cela, les collections basées sur le hachage (`HashSet`, `HashMap`) ne retrouvent pas les objets.

*Reprise de l'atelier 1, question 7.*

---

### Question 3 — Unboxing d'un `null`

**Réponse : C** — `get` renvoie `null`, et le `+ 1` force l'unboxing de ce `null`. La parade concise est `stock.getOrDefault("pommes", 0) + 1`.

*Reprise de l'atelier 2, question 19.*

---

### Question 4 — `assertThrows`

**Réponse : B** — Le code à tester est passé sous forme de lambda. Le test échoue si aucune exception n'est lancée, ou si son type ne correspond pas.

*Reprise de l'atelier 3, question 9.*

---

### Question 5 — `@Nested`

**Réponse : B** — Une classe interne statique ne dépend d'aucune instance de la classe englobante, donc ne verrait pas ses attributs.

*Reprise de l'atelier 3, question 19.*

---

### Question 6 — Le cycle TDD

**Réponse : B** — C'est le cycle *red-green-refactor* : *red* pour le test qui échoue, *green* pour le test qui réussit une fois le code écrit, *refactor* pour l'amélioration.

*Reprise de l'atelier 4, question 1.*

---

### Question 7 — La règle de trois

**Réponse : C** — C'est un compromis entre créer de l'abstraction trop tôt et attendre trop longtemps. C'est une moyenne : si un motif va manifestement revenir, on peut factoriser plus tôt.

*Reprise de l'atelier 4, question 12.*

---

### Question 8 — Ce que vérifie un mock

**Réponse : A** — Exemple : vérifier qu'on envoie bien les bonnes données au DAO. Rien n'empêche d'ajouter en plus des assertions sur l'état.

*Reprise de l'atelier 5, question 10.*

---

### Question 9 — `groupingBy`

**Réponse : A** — `groupingBy` est une fonction de classification : la clé est la valeur renvoyée par la fonction, la valeur est la liste des éléments de ce groupe.

*Reprise de l'atelier 6, question 19.*

---

### Question 10 — `flatMap`

**Réponse : B** — Chaque élément peut produire 0, 1 ou plusieurs éléments : le nombre de sortie n'a aucun rapport garanti avec celui d'entrée.

*Reprise de l'atelier 7, question 13.*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
