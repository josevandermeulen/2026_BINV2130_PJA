# Atelier 10 – séance 2 : réponses aux questions d'observation

Ce document reprend les questions de réflexion (Questions 6 à 8) de la fiche d'exercices avec leur réponse. Réfléchissez et testez avant de le consulter.

## Question 6 : pourquoi le résultat est-il déterministe ?

La fusion est commutative et associative : peu importe quel routeur finit en premier, le tableau de registres final — donc l'estimation — est le même.

## Question 7 : en quoi diffère-t-on de la race condition de l'atelier 9 ?

À l'atelier 9, plusieurs threads modifiaient une même donnée partagée sans discipline, et le résultat dépendait de l'ordre. Ici chaque future travaille sur son propre estimateur local, et l'étape de combinaison est insensible à l'ordre.

## Question 8 : estimations identiques, version asynchrone plus rapide ?

Les estimations sont identiques par déterminisme de la fusion — voir Question 6. La version asynchrone est plus rapide dès que plusieurs cœurs sont disponibles : chaque routeur est lu et traité dans son propre thread du pool, au lieu d'un routeur après l'autre.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
