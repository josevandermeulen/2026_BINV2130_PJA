# Atelier 5 : Mocks – séance 1

## Objectif

L'objectif de cet atelier est de tester unitairement une classe qui dépend d'un service extérieur — ici une API d'open data interrogée par Internet — en remplaçant ce service par un stub.

## Concepts

1. Interfaces
2. Couplage faible
3. Abstraction des dépendances
4. Stubs
5. Tests unitaires
6. Organisation des tests avec `@Nested` (vu à l'atelier 3 séance 2)

## Vidéos

1. [Tests unitaires - les stubs](https://www.youtube.com/watch?v=aBwQRyDWl1Y)
2. [Tests unitaires - Mock Objects](https://www.youtube.com/watch?v=d5xIDKCBfyI)
3. [Maven - Mockito](https://www.youtube.com/watch?v=BSIrMuM4UHM)
4. [Tests unitaires - Mockito](https://www.youtube.com/watch?v=oZi6CzLGBY8)

## Exercices

### Introduction

La Fédération Wallonie-Bruxelles publie la liste de ses bibliothèques publiques sur ODWB, son portail d'open data (https://www.odwb.be) : nom, adresse, catégorie, thématiques de recherche, fonds spécialisés et coordonnées GPS de plus de 400 bibliothèques. Ces données sont librement réutilisables (licence CC BY) et interrogeables par Internet.

Une petite application exploite ces données pour aider un lecteur à trouver une bibliothèque près de chez lui. Elle est fournie entièrement écrite et commentée en javadoc :

- la classe [`Bibliotheque`](src/domaine/Bibliotheque.java) (package `domaine`) est une simple donnée : le nom, le code postal, la ville, les thématiques et les coordonnées d'une bibliothèque ;
- l'interface [`SourceBibliotheques`](src/source/SourceBibliotheques.java) (package `source`) représente « quelque chose à qui l'on peut demander des bibliothèques » ; sa seule méthode, `rechercher`, reçoit un filtre (par exemple `cp="5000"`) et renvoie les bibliothèques qui y répondent ;
- la classe [`OdwbClient`](src/source/OdwbClient.java) implémente cette interface en interrogeant réellement l'API d'ODWB par Internet ; si l'API ne peut pas répondre, elle lève une [`SourceIndisponibleException`](src/source/SourceIndisponibleException.java) ;
- la classe [`BibliothequeService`](src/domaine/BibliothequeService.java) offre les recherches utiles au lecteur : les bibliothèques d'un code postal (`parCodePostal`), la plus proche d'un point (`plusProche`) et celles qui traitent d'une thématique (`parThematique`). Elle reçoit sa source dans son constructeur.

```
BibliothequeService ──utilise──► SourceBibliotheques (interface)
                                        ▲
                                        │ implémente
                                   OdwbClient ──► API d'ODWB (Internet)
```

Votre travail aujourd'hui consiste à tester la classe `BibliothequeService`, et elle seule : ni `OdwbClient`, ni l'API d'ODWB.

Vous pouvez lancer la classe [`Main`](src/main/Main.java) (package `main`) pour voir l'application tourner sur les vraies données de Namur. Il vous faut pour cela une connexion Internet.

### Consignes

Dans IntelliJ, créez un projet intitulé `AJ_atelier05_seance1`. Récupérez les classes fournies dans `AJ_atelier05_seance1/src/` (packages `domaine`, `source`, `main` et `util`) dans un dossier `src` de votre projet, marqué Sources Root.

Créez ensuite un dossier pour les tests :

1. Créez, dans votre projet, un nouveau dossier intitulé `test` (clic droit sur le projet et choisir New → Directory).
2. Faites un clic droit sur le dossier `test` et sélectionnez Mark Directory as → Test Sources Root.

Comme à l'atelier 3 séance 2, regroupez vos méthodes de test par thème dans des classes internes `@Nested` (par exemple : recherche par code postal, bibliothèque la plus proche, recherche par thématique), la fixture et le `@BeforeEach` restant sur la classe externe.

### Pourquoi ne pas tester avec la vraie source ?

**Question 1** :

✏️ *A corriger au tableau*

Lisez le constructeur et la méthode `parCodePostal` de `BibliothequeService`, puis la javadoc de la classe `OdwbClient`. Donnez au moins trois raisons pour lesquelles des tests de `BibliothequeService` qui utiliseraient un `OdwbClient` ne seraient pas de bons tests unitaires.

Réponses aux questions 1 et 13 dans [`05A_reponses-observations.md`](05A_reponses-observations.md).

### Le stub de la source

Pour tester `BibliothequeService` sans Internet, nous allons lui donner une autre implémentation de `SourceBibliotheques` : un stub, qui renvoie une réponse fixée à l'avance par le test, sans rien calculer.

**Question 2** :

✏️ *A corriger au tableau*

Dans le package `domaine` du dossier `test`, écrivez la classe `SourceStub`, qui implémente `SourceBibliotheques`. Elle doit pouvoir être créée de deux façons : avec une liste de bibliothèques, que `rechercher` renverra à chaque appel quel que soit le filtre reçu ; ou avec une `SourceIndisponibleException`, que `rechercher` lèvera à chaque appel, pour simuler une panne de l'API. Respectez la définition d'un stub donnée dans la théorie : aucune logique, aucun filtrage.

Créez maintenant la classe de tests JUnit `BibliothequeServiceTest` pour la classe `BibliothequeService` de cette façon-ci :

1. Ouvrir la classe `BibliothequeService` que vous voulez tester.
2. Se positionner sur le nom de la classe au sein du code, faire un clic droit et choisir « Show Context Actions ».
3. Choisir « Create Test ».
4. Dans Testing Library, il faut choisir « JUnit5 ». Si le message « JUnit5 library not found in the module » apparaît, cliquez sur Fix et ensuite sur OK.
5. Vérifiez que le package de destination indiqué est bien le même que le package de la classe à tester (package `domaine`).
6. Dans Generate, sélectionnez setUp/@Before.
7. Sélectionnez ensuite les méthodes que vous voulez tester (`parCodePostal`, `plusProche` et `parThematique`) et cliquez sur OK. Cela devrait générer votre classe de test dans `test/domaine/BibliothequeServiceTest`.

**Question 3** :
Écrivez la méthode `setUp` de `BibliothequeServiceTest`. Elle crée trois bibliothèques de Namur, inspirées des données réelles d'ODWB (simplifiées pour l'exercice), qui serviront dans la plupart des scénarios, puis un `BibliothequeService` qui utilise un `SourceStub` renvoyant ces trois bibliothèques **dans le désordre** (dans l'ordre du tableau ci-dessous) :

| Dénomination | Code postal | Ville | Catégorie | Thématiques | Fonds spécialisés | Latitude | Longitude |
|---|---|---|---|---|---|---|---|
| Réseau Namurois de Lecture publique | 5000 | Namur | réseau de bibliothèques | Arts du spectacle, Bandes dessinées | Fonds Cinéma, Mangas | 50.4652 | 4.8666 |
| Baie des tecks | 5000 | Namur | bibliothèque locale | *(aucune)* | *(aucun)* | 50.4728 | 4.8628 |
| Bibliothèque libre de Bomel | 5000 | Namur | bibliothèque locale | Fonds d'histoire locale | *(aucun)* | 50.4717 | 4.8669 |

La province de ces trois bibliothèques est Namur. Une thématique ou un fonds « aucun » se passe au constructeur sous la forme `null`.

### Le plan de tests

Lisez la javadoc des trois méthodes publiques de `BibliothequeService` avant de découvrir le plan de tests. Sauf mention contraire, le service utilise le stub du `setUp`.

| TC# | Méthode | Réponse du stub | Paramètres | Résultat attendu |
|---|---|---|---|---|
| 1 | `parCodePostal` | les trois bibliothèques du `setUp` | `"5000"` | les trois bibliothèques, triées par ordre alphabétique de dénomination |
| 2 | `parCodePostal` | une liste vide | `"5000"` | une liste vide |
| 3 | `parCodePostal` | une panne (`SourceIndisponibleException`) | `"5000"` | `SourceIndisponibleException` |
| 4 | `plusProche` | les trois bibliothèques du `setUp` | `"5000"`, point situé juste à côté de Bomel (50.4718, 4.8670) | la bibliothèque libre de Bomel |
| 5 | `parCodePostal` | les trois bibliothèques du `setUp` | `"50A0"` | `IllegalArgumentException` |
| 6 | `plusProche` | Bomel, puis une « Annexe de Bomel » située exactement au même endroit | `"5000"`, même point qu'au TC4 | l'Annexe de Bomel (à égalité de distance, la première par ordre alphabétique) |
| 7 | `plusProche` | une liste vide | `"5000"`, un point quelconque | `null` |
| 8 | `parThematique` | les trois bibliothèques du `setUp` | `"5000"`, `"BANDES DESSINÉES"` | le Réseau Namurois seul (majuscules et minuscules ignorées) |
| 9 | `parThematique` | les trois bibliothèques du `setUp` | `"5000"`, `"astronomie"` | une liste vide |

D'un scénario à l'autre, les paramètres sont souvent les mêmes (`"5000"`) et pourtant le résultat attendu change : c'est la réponse du stub qui fait la différence. Quand cette réponse n'est pas celle du `setUp` (TC2, TC3, TC6 et TC7), le test commence par remplacer le service du `setUp` par un nouveau `BibliothequeService`, construit sur un nouveau `SourceStub` qui renvoie la réponse voulue.

Nommez chaque méthode de test d'après son cas : `testServiceTC1`, `testServiceTC2`, …

Les quatre premiers scénarios couvrent chacun une façon différente de préparer le stub ou une méthode différente du service : écrivez-les vous-même.

**Question 4** :

✏️ *A corriger au tableau*

Écrivez le scénario TC1 (le résultat est trié).

**Question 5** :
Écrivez le scénario TC2 (aucune bibliothèque). Il faut pour cela un service qui utilise un autre stub que celui du `setUp`.

**Question 6** :

✏️ *A corriger au tableau*

Écrivez le scénario TC3 (la source est en panne). C'est le second constructeur de votre `SourceStub` qui sert ici.

**Question 7** :
Écrivez le scénario TC4 (la bibliothèque la plus proche).

### 🤖 À partir d'ici, générez les tests avec l'IA

Les scénarios restants reprennent les façons de préparer le stub que vous venez d'écrire : une liste de bibliothèques (celle du `setUp` ou une autre), puis un appel au service et une vérification du résultat. Aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour les générer à partir du plan de tests, mais relisez et exécutez chaque test généré : vérifiez qu'il teste bien le cas décrit, et qu'il échouerait si le service se trompait.

**Question 8** :
Écrivez le scénario TC5 (code postal invalide).

**Question 9** :
Écrivez le scénario TC6 (égalité de distance).

**Question 10** :
Écrivez le scénario TC7 (aucune bibliothèque, donc aucune plus proche).

**Question 11** :
Écrivez le scénario TC8 (recherche par thématique, majuscules et minuscules ignorées).

**Question 12** :
Écrivez le scénario TC9 (aucune bibliothèque pour cette thématique).

### Et le cache ?

La javadoc de `BibliothequeService` annonce un cache : une fois les bibliothèques d'un code postal obtenues, le service ne redemande plus jamais ce code postal à la source.

**Question 13** :
Aucun des scénarios TC1 à TC9 ne vérifie ce cache. Que faudrait-il ajouter à `SourceStub` pour pouvoir écrire un test qui vérifie qu'un deuxième appel à `parCodePostal("5000")` n'interroge pas la source ? Pourquoi est-ce déjà un pas au-delà de ce qu'est un stub ?

## Parties optionnelles

### Données incomplètes

Dans les vraies données d'ODWB, près de trois bibliothèques sur quatre ne déclarent ni thématique ni fonds spécialisé.

**Question 14** :
Écrivez un scénario TC10 qui vérifie qu'une recherche `parThematique("5000", "histoire")` sur les trois bibliothèques du `setUp` renvoie uniquement la bibliothèque libre de Bomel : la Baie des tecks, qui n'a ni thématique ni fonds, doit être simplement ignorée.

---

*Passez à la [théorie suivante](../../02-seance2/AJ_atelier05_seance2/05B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
