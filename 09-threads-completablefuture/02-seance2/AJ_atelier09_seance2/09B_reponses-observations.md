# Atelier 9 – séance 2 : réponses aux questions d'observation

Ce document reprend les questions de réflexion (Questions 7 à 10) de la fiche d'exercices (section « Appels asynchrones à une API REST ») avec leur réponse. Réfléchissez et testez avant de le consulter.

## Question 7 : l'affichage est-il correct ?

Sans précaution, non : chaque post lance ses deux requêtes (commentaires, utilisateur) dans des futures indépendantes, et plusieurs posts sont traités en parallèle. Les trois lignes `System.out.println` d'un même post (post, commentaires, utilisateur) peuvent donc se retrouver entrelacées avec celles d'un autre post en cours de traitement — l'affichage global reste asynchrone même après que les données d'un post précis ont été récupérées.

La correction consiste à rendre atomique, à l'affichage, le groupe des trois lignes d'un même post : un bloc `synchronized` sur un verrou commun à tous les threads (par exemple `System.out` lui-même, puisqu'il est partagé) empêche un autre thread d'intercaler ses propres lignes pendant que ce groupe s'affiche :

```java
synchronized (System.out) {
    System.out.println("Post (postId:" + postId + ") : " + post);
    System.out.println("Comments: (postId:" + postId + ") : " + comments);
    System.out.println("User: (postId:" + postId + ") : " + user);
}
```

Cela ne change rien à l'ordre **relatif** des posts entre eux (toujours asynchrone), seulement à la cohérence de chaque groupe de trois lignes.

## Question 8 : nombre de requêtes

Comme il y a 100 posts : une requête pour obtenir tous les posts, et par post, deux requêtes (commentaires + utilisateur) → 201 requêtes.

## Question 9 : délai d'attente par requête

100 ms (voir le code d'`ApiService`), afin d'éviter que toutes les requêtes soient faites en même temps et que l'API bloque les réponses.

## Question 10 : le temps total correspond-il à un traitement parallélisé ?

Oui — cela prendrait plus de 20 secondes si les 201 requêtes étaient faites de manière synchrone (201 × 100 ms), alors que l'exécution asynchrone les chevauche largement.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
