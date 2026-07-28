# Atelier 11 – séance 1 : réponses aux questions d'observation

Ce document reprend les questions de réflexion (Questions 4 et 8) de la fiche d'exercices avec leur réponse. Réfléchissez et testez avant de le consulter.

## Question 4 : qu'est-ce qui a permis d'exécuter `CalculatriceTest` sans la nommer ?

La réflexion : la classe est inspectée à l'exécution, ses méthodes annotées sont découvertes et invoquées dynamiquement — exactement ce que fait JUnit avec vos classes de tests depuis l'atelier 3.

## Question 8 : pourquoi une instance fraîche par répétition ?

Pour que chaque répétition parte du même état initial. Si les répétitions partageaient la même instance, tout ce que la première exécution modifie dans les attributs resterait visible pour les suivantes : le résultat d'une répétition dépendrait alors de celles d'avant, et un test répété pourrait réussir la première fois puis échouer les suivantes (ou l'inverse). C'est visible dans `ExempleTestsAvecRepetition` : le test incrémente un attribut `compteur` et vérifie qu'il vaut 1 — il ne réussit ses trois répétitions que si chacune s'exécute sur une nouvelle instance. C'est la même règle d'isolation que le « une nouvelle instance par test » de la Question 3, appliquée au niveau de chaque répétition — et c'est aussi ce que fait le vrai JUnit avec `@RepeatedTest`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
