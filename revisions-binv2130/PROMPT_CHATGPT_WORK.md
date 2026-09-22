Je veux reconstruire mon site de révision QCM BINV2130 en dehors de ChatGPT Sites, afin de le déployer sur Netlify.

Le projet doit être créé dans mon dépôt GitHub existant :

`josevandermeulen/2026_JAVA2_seances`

dans le sous-répertoire :

`revisions-binv2130/`

Ne modifie pas inutilement le reste du dépôt.

Le site doit reprendre au plus près le fonctionnement de mon site actuel `revisions-binv2130`.

Commence par inspecter le dépôt existant avant de coder, en particulier les fichiers suivants :

- `_moovin/quiz-moodle-2026.xml`
- `_moovin/mise-en-place.md`
- `_automatisation/site-revisions-binv2130.md`

Ces fichiers décrivent déjà une grande partie du fonctionnement attendu et doivent servir de source de vérité.

Le contenu pédagogique vit déjà dans le dépôt. Le nouveau site doit uniquement reconstruire l’outil de révision.

## Source des questions

Les questions proviennent du fichier :

`_moovin/quiz-moodle-2026.xml`

Ce fichier contient les QCM Moodle du cours.

Le site doit pouvoir exploiter ce XML pour construire les questions automatiquement, avec :

- l’énoncé ;
- les réponses proposées ;
- les bonnes réponses ;
- les mauvaises réponses ;
- les remarques ou feedbacks associés aux réponses ;
- la catégorie ou semaine correspondante.

Évite de recopier manuellement les questions dans le code si elles peuvent être extraites du XML.

Si nécessaire, crée un script d’import ou de génération qui transforme le XML Moodle en un format plus simple utilisé par le site, par exemple JSON.

Documente clairement cette transformation dans le README.

## Organisation du cours

Le cours contient 12 semaines principales :

- Semaine 1 — Rappels
- Semaine 2 — Collections, énumérés
- Semaine 3 — JUnit
- Semaine 4 — TDD
- Semaine 5 — Mocks
- Semaine 6 — Streams
- Semaine 7 — Programmation fonctionnelle
- Semaine 8 — Lecture de fichiers
- Semaine 9 — Threads & CompletableFuture
- Semaine 10 — HyperLogLog & asynchrone
- Semaine 11 — Introspection
- Semaine 12 — Injection de dépendances

Le XML peut contenir d’autres catégories, notamment le QCM « Cheat sheets hors ligne ». Inspecte le fichier avant de décider comment traiter ces catégories supplémentaires.

## Règle d’ouverture des semaines

La règle fonctionnelle est importante :

Une semaine apparaît sur le site de révision uniquement à la fermeture du QCM Moodle correspondant, jamais avant.

Ensuite, elle reste ouverte définitivement.

Les dates officielles sont décrites dans :

`_moovin/mise-en-place.md`

Le calendrier actuel d’ouverture du site de révision est :

- Semaine 1 — Rappels : 16/09/2026 à 20:00
- Semaine 2 — Collections, énumérés : 21/09/2026 à 20:00
- Semaine 3 — JUnit : 28/09/2026 à 20:00
- Semaine 4 — TDD : 05/10/2026 à 20:00
- Semaine 5 — Mocks : 12/10/2026 à 20:00
- Semaine 6 — Streams : 19/10/2026 à 20:00
- Semaine 7 — Programmation fonctionnelle : 03/11/2026 à 20:00
- Semaine 8 — Lecture de fichiers : 09/11/2026 à 20:00
- Semaine 9 — Threads & CompletableFuture : 16/11/2026 à 20:00
- Semaine 10 — HyperLogLog & asynchrone : 23/11/2026 à 20:00
- Semaine 11 — Introspection : 30/11/2026 à 20:00
- Semaine 12 — Injection de dépendances : 07/12/2026 à 20:00

Attention à l’exception de la semaine 7 : son QCM reste ouvert pendant le congé d’automne et ferme le mardi 03/11/2026 à 20h.

Prévois une configuration claire et facile à modifier, par exemple un fichier JSON, JavaScript ou TypeScript contenant les semaines et leurs dates d’ouverture.

Même si les dates sont stockées dans cette configuration, indique clairement dans le README que la source officielle est `_moovin/mise-en-place.md`.

Utilise le fuseau horaire belge / Europe-Brussels pour les dates d’ouverture.

## Page d’accueil

La page d’accueil doit être simple et rapide à comprendre.

Elle doit permettre de sélectionner les semaines que l’étudiant veut réviser.

Règles :

- seules les semaines déjà ouvertes sont proposées ;
- les semaines futures ne sont pas sélectionnables ;
- toutes les semaines déjà ouvertes sont sélectionnées par défaut ;
- l’étudiant peut décocher certaines semaines ;
- il peut ensuite choisir son mode de révision.

Prévoir au minimum deux modes :

- Aléatoire
- Dans l’ordre

Le mode doit être clairement visible avant de démarrer.

Il doit également y avoir un accès évident à la page des statistiques.

## Mode révision

Une session standard contient 20 questions.

L’étudiant doit pouvoir réviser une ou plusieurs semaines.

### Mode aléatoire

Les questions sont choisies parmi les semaines sélectionnées.

Les questions sont mélangées.

Une nouvelle série peut être générée après les 20 questions.

Le système doit permettre de continuer indéfiniment.

### Mode dans l’ordre

Les semaines doivent être parcourues dans l’ordre croissant.

À l’intérieur d’une semaine, les questions doivent suivre l’ordre du QCM Moodle lorsque cet ordre peut être retrouvé.

Après la dernière question disponible, le système peut recommencer une nouvelle série ou continuer naturellement selon le fonctionnement décrit dans `_automatisation/site-revisions-binv2130.md`.

Privilégie le comportement de l’ancien site si le document le décrit plus précisément.

## Affichage d’une question

Afficher une seule question à la fois.

L’interface doit être claire sur ordinateur comme sur téléphone.

Afficher au minimum :

- le numéro de la question ;
- la semaine ;
- l’énoncé ;
- les propositions de réponse ;
- éventuellement la progression dans la série.

Les propositions doivent être suffisamment grandes pour être utilisées facilement sur mobile.

## Validation et correction

La correction doit être immédiate après validation.

Une fois la réponse validée :

- indiquer clairement si la réponse est correcte ou incorrecte ;
- mettre en évidence les bonnes réponses ;
- mettre en évidence les réponses incorrectes choisies ;
- afficher les remarques ou feedbacks provenant du XML Moodle ;
- afficher les explications associées aux différentes propositions lorsqu’elles existent.

Ne génère pas artificiellement des explications absentes du XML.

Ne rajoute pas de phrase générique du style :

« pourquoi la question convient »

ou tout autre texte inutile.

Le site doit reprendre les explications existantes dans les sources.

## Navigation clavier

Le site doit être confortable à utiliser sans souris.

Prévoir :

- flèche droite : question suivante ;
- flèche gauche : question précédente ;
- Entrée : valider la réponse ou continuer selon le contexte.

Le comportement doit rester logique lorsqu’une question a déjà été corrigée.

Ajouter également un bouton Home pour revenir à l’accueil.

## Navigation mobile

Sur téléphone, tous les boutons et contrôles doivent être facilement accessibles.

Évite les éléments trop petits.

La navigation clavier est surtout destinée aux ordinateurs, mais l’interface tactile doit être pleinement fonctionnelle.

## Historique dans une série

Si l’étudiant revient à la question précédente avec la flèche gauche :

- conserver sa réponse ;
- conserver l’état corrigé ;
- ne pas compter une seconde fois la réponse dans les statistiques.

Évite les doubles comptages.

## Statistiques publiques

Le site doit avoir une partie statistiques publique.

Aucune connexion ne doit être nécessaire pour consulter les statistiques.

Le site ne doit pas stocker l’identité des étudiants.

Les statistiques sont anonymes.

Prévoir au minimum :

### Vue générale

Afficher :

- nombre total de réponses ;
- taux global de réussite ;
- réussite par semaine.

### Filtres temporels

Prévoir les filtres :

- aujourd’hui ;
- semaine ;
- mois ;
- tout.

### Réussite par question

Créer une vue permettant de voir :

- la question ;
- la semaine ;
- le nombre de réponses ;
- le nombre de bonnes réponses ;
- le taux de réussite.

Pouvoir trier les questions par taux de réussite.

Pouvoir cliquer sur une question pour voir davantage de détails.

### Questions les plus difficiles

Afficher une liste des questions ayant les taux de réussite les plus faibles.

Cette liste doit être cliquable.

### Semaines fermées

Les semaines qui ne sont pas encore ouvertes ne doivent pas apparaître dans les statistiques publiques.

## Stockage des statistiques

Le site sera déployé sur Netlify.

Choisis une solution simple, robuste et raisonnablement légère pour enregistrer les statistiques anonymes.

Le quiz doit continuer à fonctionner même si la partie statistiques est temporairement indisponible.

Évite une architecture inutilement complexe.

Une solution basée sur une fonction Netlify et un petit stockage externe peut convenir si elle reste simple à maintenir.

Si plusieurs solutions sont possibles, privilégie celle nécessitant le moins d’administration à long terme.

Ne stocke aucune donnée personnelle.

Ne demande aucun compte aux étudiants.

## Gestion et configuration

Je veux éviter une grosse interface d’administration si un simple fichier de configuration suffit.

Prévois une configuration claire dans le dépôt pour :

- les semaines ;
- les dates d’ouverture ;
- éventuellement le mapping catégories Moodle → semaines ;
- d’autres paramètres utiles.

Si une page `/admin` existait dans l’ancien système mais n’est pas nécessaire avec GitHub + Netlify, privilégie la simplicité.

Si tu conserves une interface d’administration, elle ne doit pas être publique.

## Design

Je veux une interface :

- sobre ;
- moderne ;
- claire ;
- rapide ;
- responsive ;
- agréable sur ordinateur ;
- agréable sur téléphone.

Ne surcharge pas l’interface.

Évite un framework lourd sans nécessité.

Privilégie la simplicité et la maintenabilité.

## Technologies

Choisis une stack adaptée à un petit site Netlify.

Tu peux utiliser par exemple :

- HTML/CSS/JavaScript ;
- Vite ;
- React si cela apporte un avantage réel.

Mais n’ajoute pas un framework simplement parce qu’il est populaire.

L’objectif est :

- faible complexité ;
- déploiement Netlify simple ;
- maintenance facile ;
- code compréhensible.

## Structure du dépôt

Tout le nouveau projet doit rester dans :

`revisions-binv2130/`

Le sous-répertoire doit être autonome.

Netlify doit pouvoir être configuré pour utiliser ce répertoire comme base du site.

Ne déplace pas le contenu existant du dépôt.

Ne modifie les autres fichiers que si cela est réellement nécessaire.

## Configuration Netlify

Prépare le projet pour un déploiement Netlify.

Ajoute les fichiers de configuration nécessaires si utile.

Documente :

- base directory ;
- build command ;
- publish directory ;
- variables d’environnement éventuelles ;
- fonctions Netlify éventuelles.

Je veux pouvoir connecter le dépôt GitHub à Netlify et choisir `revisions-binv2130/` comme projet du site.

## Import du XML Moodle

Je veux pouvoir mettre à jour les questions plus tard.

Prévois un mécanisme simple permettant de repartir du fichier :

`_moovin/quiz-moodle-2026.xml`

Par exemple :

1. je modifie ou régénère le XML Moodle ;
2. je lance une commande ;
3. le JSON utilisé par le site est régénéré ;
4. je commit ;
5. Netlify redéploie automatiquement.

Documente exactement la commande.

Si possible, ajoute une vérification indiquant clairement si le XML est invalide ou si une catégorie n’est pas reconnue.

## README

Créer un README complet dans :

`revisions-binv2130/README.md`

Il doit expliquer au minimum :

- ce qu’est le site ;
- comment installer les dépendances ;
- comment lancer le site localement ;
- comment générer les données depuis le XML Moodle ;
- comment modifier une date d’ouverture ;
- comment ajouter ou modifier une semaine ;
- comment fonctionnent les modes aléatoire et dans l’ordre ;
- comment fonctionnent les statistiques ;
- quelle solution de stockage est utilisée ;
- quelles variables d’environnement sont nécessaires ;
- comment tester localement ;
- comment déployer sur Netlify ;
- comment connecter le sous-répertoire au site Netlify ;
- comment mettre à jour les questions en cours d’année.

## Tests

Teste au minimum les points suivants :

- aucune semaine future ne peut être utilisée ;
- toutes les semaines ouvertes sont sélectionnées par défaut ;
- une semaine s’ouvre bien à la bonne date et heure ;
- les questions correspondent aux semaines attendues ;
- le mode aléatoire fonctionne ;
- le mode dans l’ordre fonctionne ;
- une série contient bien 20 questions lorsqu’il y en a suffisamment ;
- les corrections sont justes ;
- les feedbacks Moodle sont correctement affichés ;
- le retour à une question précédente fonctionne ;
- les statistiques ne sont pas comptées deux fois ;
- les statistiques restent anonymes ;
- le site reste utilisable si l’enregistrement statistique échoue ;
- l’interface fonctionne sur mobile.

## Référence à l’ancien site

Le fichier :

`_automatisation/site-revisions-binv2130.md`

décrit déjà le fonctionnement de l’ancien site.

Lis-le complètement avant de développer.

S’il contient des fonctionnalités ou des règles plus précises que ce prompt, conserve-les lorsqu’elles ne contredisent pas explicitement les instructions présentes ici.

L’objectif est de reproduire le comportement utile de l’ancien site, pas de réinventer le produit.

## Travail attendu

Je ne veux pas uniquement un plan ou des instructions.

Je veux que tu effectues réellement le travail dans le dépôt GitHub.

Commence par inspecter les fichiers existants.

Ensuite :

1. crée `revisions-binv2130/` ;
2. implémente le site ;
3. construis l’import du XML Moodle ;
4. configure les dates d’ouverture ;
5. implémente le quiz ;
6. implémente les statistiques ;
7. prépare Netlify ;
8. ajoute le README ;
9. teste le fonctionnement ;
10. commit les modifications dans le dépôt.

Ne détruis pas les fichiers existants.

Ne modifie pas inutilement le reste du dépôt.

À la fin, donne-moi un résumé très concret de ce qui a été créé, les éventuelles variables d’environnement à configurer et les étapes exactes restantes côté Netlify.
