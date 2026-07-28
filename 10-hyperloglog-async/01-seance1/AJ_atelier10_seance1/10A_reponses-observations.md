# Atelier 10 – séance 1 : réponses aux questions d'observation

Ce document reprend la question de réflexion (Question 4) de la fiche d'exercices avec sa réponse. Réfléchissez et testez avant de le consulter.

## Question 4 : pourquoi le premier message s'affiche-t-il avant l'estimation ?

Parce que `supplyAsync` rend la main immédiatement : le chargement s'exécute dans un thread du pool pendant que `main` continue ; seul `join` bloque, au moment où la valeur est vraiment nécessaire.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
