# Cheat sheets hors ligne — questionnaire à choix multiple

<!-- categorie: 2026-Cheat-sheets-hors-ligne -->
<!-- points: 4 -->
<!-- prefixe: Cheat sheets hors ligne -->

5 questions à réponse unique, cotées 4 points chacune, sans points négatifs.
Ce QCM a fermé le lundi 21/09/2026 à 20h, en même temps que celui de la
semaine 2.

Il ne portait sur aucune matière : il vérifiait que vous savez **ouvrir les
cheat sheets depuis votre clone, sans connexion Internet** — les conditions de
l'examen de janvier. Les questions nomment la fiche et la section : la réponse
s'y lit directement, à condition d'avoir le dossier `cheat-sheets/` sous la
main. Si l'une d'elles vous a coûté un point, le geste à refaire est celui-là :
couper le Wi-Fi, ouvrir `cheat-sheets/index.html` depuis votre clone, et
vérifier que tout s'affiche.

---

### Question 1 — Le cycle red-green-refactor

Ouvrez `04-tdd-fr.html`, section *Le cycle red-green-refactor*. Quel est le
titre de la **troisième** étape ?

- A) `Refactor — améliorer sans changer le comportement`
- B) `Refactor — réécrire le test`
- C) `Green — écrire le code minimum`
- D) `Red — écrire un test qui échoue`

**Réponse : A** — Les trois étapes sont, dans l'ordre : `1. Red — écrire un test qui échoue`, `2. Green — écrire le code minimum`, `3. Refactor — améliorer sans changer le comportement`. Les propositions C et D sont les deux premières étapes, pas la troisième.

---

### Question 2 — Ce que la fiche ne couvre pas

Toujours dans `04-tdd-fr.html`, la ligne *Couvre :* en tête de page. Elle
renvoie les API JUnit (annotations, assertions) vers une autre fiche : laquelle ?

- A) Celle de la semaine 3
- B) Celle de la semaine 5
- C) Celle de la semaine 6
- D) Aucune : les API JUnit sont couvertes par cette fiche

**Réponse : A** — La ligne se termine par « Les API JUnit (annotations, assertions) sont couvertes par le cheat sheet de la semaine 3 ». La fiche 04 s'en tient au cycle red-green-refactor, à la spécification des scénarios et au TDD sur du code existant.

---

### Question 3 — L'ordre des sections

Ouvrez `06-streams-fr.html` et lisez son sommaire. Quelle section vient
**juste après** `Collectors` ?

- A) `Opérations terminales`
- B) `Optional`
- C) `Exemples complets`
- D) `Référence rapide`

**Réponse : B** — L'ordre du sommaire est `Opérations intermédiaires`, `Opérations terminales`, `Collectors`, `Optional`, `Exemples complets`, `Pièges fréquents`, `Référence rapide`. La proposition A précède `Collectors` au lieu de la suivre.

---

### Question 4 — La barre de navigation

Toujours dans `06-streams-fr.html`, regardez la barre de navigation. Que lit-on
sur la flèche de **droite** ?

- A) `Semaine 5 →`
- B) `Semaine 7 →`
- C) `Index →`
- D) `Semaine 6 →`

**Réponse : B** — La flèche de droite mène à la fiche suivante, `Semaine 7` (Programmation fonctionnelle). La flèche de gauche, elle, renvoie à `← Semaine 5`, la fiche précédente ; la proposition D désigne la fiche que vous avez sous les yeux.

---

### Question 5 — L'index des fiches

Ouvrez `index.html`, la page d'accueil des cheat sheets. Quel est le thème de
la **semaine 11** ?

- A) `Injection de dépendances`
- B) `Threads et CompletableFuture`
- C) `Introspection Java`
- D) `HyperLogLog et asynchrone`

**Réponse : C** — L'index annonce `Semaine 11 — Introspection Java`. Les trois autres propositions sont les thèmes des semaines 12, 9 et 10 : l'index les liste toutes les douze, dans l'ordre.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
