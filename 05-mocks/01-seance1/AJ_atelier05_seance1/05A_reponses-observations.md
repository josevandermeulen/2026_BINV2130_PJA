# Atelier 5 – séance 1 : réponses aux questions d'observation

Ce document reprend les deux questions de réflexion (Questions 1 et 13) de la fiche d'exercices avec leur réponse. Réfléchissez (et testez !) avant de le consulter.

## Question 1 : pourquoi ne pas tester avec `OdwbClient`

Un test unitaire doit être rapide, reproductible et ne tester qu'une classe. Avec un `OdwbClient`, aucune de ces trois propriétés ne tient :

- **le test dépend du réseau** : sans connexion (examen hors ligne, wifi en panne, serveur d'ODWB en maintenance), il échoue alors que `BibliothequeService` n'a aucun défaut ;
- **le résultat attendu change** : le jeu de données est mis à jour par la Fédération Wallonie-Bruxelles ; un test qui affirme « Namur a six bibliothèques » devient faux le jour où une bibliothèque ouvre ou ferme, sans qu'une seule ligne de code ait changé ;
- **le test est lent** : chaque appel prend de l'ordre d'une seconde ; avec des dizaines de tests lancés à chaque modification, l'attente devient pénible ;
- **ce n'est plus un test unitaire** : un échec peut venir de `BibliothequeService`, de `OdwbClient`, du réseau ou de l'API ; on teste plusieurs choses à la fois, c'est un test d'intégration ;
- **certains cas sont impossibles à provoquer** : on ne peut pas demander à ODWB de tomber en panne au moment du test, ni de renvoyer une liste vide pour Namur. Or ce sont justement des cas que le service doit bien gérer.

C'est possible ici parce que `BibliothequeService` ne connaît que l'interface `SourceBibliotheques` et reçoit sa source dans son constructeur : on peut lui en donner une autre sans modifier une seule ligne du service.

## Question 13 : vérifier le cache

Pour vérifier qu'un deuxième appel à `parCodePostal("5000")` n'interroge pas la source, le test doit pouvoir **compter les appels** : il faudrait ajouter à `SourceStub` un compteur, incrémenté dans `rechercher`, et une méthode pour le lire. Le test appellerait `parCodePostal("5000")` deux fois puis vérifierait que le compteur vaut 1.

Ce n'est plus vraiment un stub : un stub fournit des réponses pour qu'on puisse **vérifier l'état** de l'objet testé (ce qu'il renvoie). Ici, on veut **vérifier son comportement** : comment il a interagi avec sa dépendance, combien de fois, avec quels arguments. Un stub qui enregistre ses appels s'appelle un *spy* (voir la théorie) ; et vérifier les interactions est précisément le rôle des mock objects. Plutôt que d'écrire ces compteurs à la main pour chaque méthode et chaque argument, la séance 2 utilisera Mockito, qui les fournit tout faits (`verify`, `times`, `never`).

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
