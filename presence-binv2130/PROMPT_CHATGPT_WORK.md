Je veux reconstruire mon site public de comptage des étudiants BINV2130 en dehors de ChatGPT Sites, afin de le déployer sur Netlify.

Le projet doit être créé dans mon dépôt GitHub existant :

`josevandermeulen/2026_JAVA2_seances`

dans le sous-répertoire :

`presence-binv2130/`

Ne modifie pas inutilement le reste du dépôt.

Le site doit reprendre au plus près le fonctionnement de mon site actuel de suivi des présences / de l'affluence BINV2130.

## Objectif

Le site sert à mesurer et afficher l'affluence des étudiants aux différentes séances du cours BINV2130.

Je veux pouvoir encoder très rapidement, pour chaque séance :

- la date ;
- la série / le groupe ;
- le nombre de participants présents.

Le site doit ensuite afficher publiquement l'évolution de la fréquentation au fil des séances.

Le but n'est pas de gérer des présences nominatives. Je ne veux pas stocker les noms des étudiants ni leur identité.

## Structure générale

Prévoir deux parties :

1. une page publique de consultation ;
2. une page privée ou protégée pour l'encodage.

Le site doit être simple, très rapide à utiliser et parfaitement utilisable sur téléphone.

## Encodage privé

Je veux une interface d'encodage minimale.

Pour chaque nouvelle séance, je dois pouvoir choisir :

- la date ;
- la série ;
- le nombre de participants présents.

Je ne veux pas devoir encoder d'autres informations inutiles.

L'encodage doit être rapide : quelques secondes maximum.

Prévoir un bouton clair pour enregistrer la séance.

Après enregistrement, afficher une confirmation et permettre d'ajouter immédiatement une autre séance.

## Séries

Les séries doivent être gérées proprement.

Inspecte le dépôt pour voir si les séries ou groupes BINV2130 sont déjà décrits quelque part.

Si aucune source claire n'existe, crée une configuration simple dans le projet, facile à modifier, par exemple :

`src/config/series.ts`

ou un équivalent JSON.

Je veux pouvoir ajouter, renommer ou supprimer une série facilement sans devoir modifier beaucoup de code.

## Données

Chaque enregistrement doit au minimum contenir :

- un identifiant unique ;
- la date de la séance ;
- la série ;
- le nombre de participants ;
- éventuellement la date/heure d'encodage pour audit technique.

Ne stocke aucune donnée nominative sur les étudiants.

## Page publique

La page publique doit montrer clairement l'évolution des présences.

Je veux au minimum :

- le nombre de participants pour chaque séance ;
- la date ;
- la série ;
- éventuellement un pourcentage si le nombre total d'inscrits est disponible dans une configuration.

L'interface doit rester lisible même lorsque le nombre de séances augmente.

## Graphique principal

Je veux un histogramme clair.

Le graphique doit afficher :

- une barre par séance ;
- les séances regroupées par jour lorsque plusieurs séries ont cours le même jour ;
- la hauteur de la barre = nombre de participants ;
- un libellé suffisamment clair pour identifier la date et la série.

Si plusieurs séries sont présentes le même jour, elles doivent apparaître côte à côte ou visuellement regroupées.

Le graphique doit être responsive et bien fonctionner sur téléphone.

Évite un graphique surchargé.

Si une bibliothèque de graphiques est utilisée, choisis une solution légère et maintenable.

## Indicateurs complémentaires

En plus du graphique, tu peux afficher de manière simple :

- nombre total de séances encodées ;
- moyenne de participants par séance ;
- maximum observé ;
- dernière séance encodée.

Ne surcharge pas la page.

## Pourcentage de présence

Si une configuration contient le nombre total d'étudiants inscrits pour une série, tu peux afficher :

`présents / inscrits`

et le pourcentage correspondant.

Mais la valeur importante reste le nombre brut de participants.

Si le nombre d'inscrits n'est pas disponible ou fiable, le site doit fonctionner parfaitement sans lui.

## Historique

Afficher sous le graphique une liste ou un tableau des séances récentes avec :

- date ;
- série ;
- participants.

Prévoir un ordre chronologique inverse par défaut, les séances les plus récentes en premier.

## Modification / suppression

Dans la partie privée, je veux pouvoir corriger une erreur d'encodage.

Prévoir :

- modification d'une séance existante ;
- suppression d'une séance avec confirmation.

Évite toute suppression accidentelle.

## Remise à zéro

Je veux pouvoir remettre toutes les données à zéro si nécessaire.

Cette action doit être disponible uniquement dans la partie privée.

Elle doit demander une confirmation explicite avant de supprimer toutes les données.

Ne mets jamais cette fonction sur la page publique.

## Accès privé

L'encodage ne doit pas être accessible publiquement sans protection.

Choisis une solution simple compatible avec Netlify.

Je ne veux pas une infrastructure lourde.

Une authentification légère ou une protection par secret côté serveur peut convenir, à condition que le secret ne soit jamais exposé dans le JavaScript public.

Explique clairement la solution choisie dans le README.

## Stockage

Le site sera déployé sur Netlify.

Choisis une solution simple pour stocker les séances.

Le stockage doit permettre :

- lecture publique des données nécessaires à l'affichage ;
- ajout privé ;
- modification privée ;
- suppression privée ;
- remise à zéro privée.

Évite une architecture inutilement compliquée.

Si tu utilises une base externe, privilégie une solution facile à administrer et adaptée à un petit volume de données.

Le site public doit continuer à s'afficher proprement si le backend est momentanément indisponible, avec un message clair plutôt qu'une page cassée.

## Confidentialité

Le site ne doit pas enregistrer :

- nom ;
- prénom ;
- matricule ;
- email ;
- identifiant étudiant.

On mesure uniquement l'affluence globale par séance.

## Design

Je veux une interface :

- sobre ;
- moderne ;
- claire ;
- rapide ;
- responsive ;
- très lisible sur ordinateur ;
- très simple à utiliser sur téléphone.

Le site est avant tout un outil pratique.

Ne surcharge pas le design.

## Technologies

Choisis une stack adaptée à un petit site Netlify.

Tu peux utiliser :

- HTML/CSS/JavaScript ;
- Vite ;
- React si cela simplifie réellement le projet.

Mais n'ajoute pas de framework lourd sans raison.

L'objectif est :

- faible complexité ;
- maintenance facile ;
- déploiement Netlify simple ;
- code compréhensible.

## Structure du dépôt

Tout le nouveau projet doit rester dans :

`presence-binv2130/`

Le sous-répertoire doit être autonome.

Netlify doit pouvoir utiliser ce dossier comme base de déploiement.

Ne déplace pas les fichiers existants du dépôt.

Ne modifie le reste du dépôt que si cela est réellement nécessaire.

## Configuration Netlify

Prépare le projet pour un déploiement Netlify.

Documente au minimum :

- base directory ;
- build command ;
- publish directory ;
- éventuelles Netlify Functions ;
- variables d'environnement ;
- stockage utilisé ;
- configuration de l'accès privé.

Je veux pouvoir connecter directement le dépôt GitHub :

`josevandermeulen/2026_JAVA2_seances`

et utiliser :

`presence-binv2130/`

comme base du site Netlify.

## README

Créer :

`presence-binv2130/README.md`

Il doit expliquer :

- ce qu'est le site ;
- comment l'installer ;
- comment le lancer localement ;
- comment configurer les séries ;
- comment encoder une séance ;
- comment modifier ou supprimer une séance ;
- comment remettre les données à zéro ;
- comment fonctionne le stockage ;
- comment fonctionne la protection de la partie privée ;
- quelles variables d'environnement sont nécessaires ;
- comment déployer sur Netlify ;
- comment connecter le dépôt GitHub au site Netlify.

## Tests

Teste au minimum :

- ajout d'une séance ;
- affichage immédiat sur la page publique ;
- plusieurs séries le même jour ;
- regroupement correct des barres par jour dans l'histogramme ;
- modification d'une séance ;
- suppression d'une séance ;
- confirmation avant remise à zéro ;
- impossibilité d'écrire depuis la partie publique ;
- comportement sur mobile ;
- absence de données nominatives ;
- affichage correct si aucune donnée n'existe ;
- affichage correct si le backend est temporairement indisponible.

## Référence au site actuel

Si tu peux retrouver dans le dépôt des documents, anciens fichiers ou notes concernant le site de présences BINV2130, lis-les avant de développer.

Le fonctionnement actuel à conserver en priorité est :

- saisie par séance ;
- choix de la date ;
- choix de la série ;
- saisie du nombre de participants ;
- page publique ;
- histogramme avec une barre par séance ;
- regroupement visuel des séances par jour ;
- possibilité de remettre les données à zéro.

L'objectif est de reconstruire proprement le site existant, pas d'en faire une application de gestion complexe.

## Travail attendu

Je ne veux pas uniquement un plan.

Je veux que tu effectues réellement le travail dans le dépôt GitHub.

Commence par inspecter le dépôt.

Ensuite :

1. crée `presence-binv2130/` ;
2. implémente la page publique ;
3. implémente l'histogramme ;
4. implémente l'encodage privé ;
5. implémente le stockage ;
6. implémente modification, suppression et remise à zéro ;
7. prépare Netlify ;
8. ajoute le README ;
9. teste le fonctionnement ;
10. commit les modifications dans le dépôt.

Ne détruis aucun fichier existant.

Ne modifie pas inutilement le reste du dépôt.

À la fin, donne-moi un résumé très concret de ce qui a été créé, les variables d'environnement éventuelles à configurer et les étapes exactes restantes côté Netlify.
