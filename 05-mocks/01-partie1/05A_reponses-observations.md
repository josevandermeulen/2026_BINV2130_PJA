# Atelier 5 – partie 1 : réponses aux questions d'observation

Ce document reprend la question de réflexion (Question 2) de la fiche d'exercices (section « Les stubs ») avec sa réponse. Réfléchissez (et testez !) avant de le consulter.

## Question 2 : le second stub

`StageStub`. La méthode testée, `ajouterStage` de `MoniteurImpl`, reçoit un paramètre de type `Stage` : pour tester unitairement le moniteur, il faut contrôler ce que ce stage répond (`getNumeroDeSemaine`, `getSport`, `getMoniteur`) sans dépendre de la vraie classe `StageImpl`. `SportStub` était déjà fourni pour la même raison, à cause de l'appel `stage.getSport().contientMoniteur(this)`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
