# Atelier 3 : JUnit – partie 1

## Objectif

L'objectif de cet atelier est d'apprendre à écrire des tests automatisés avec JUnit pour vérifier le comportement de classes métier existantes. Cette première partie se concentre sur les tests de la classe [`Prix`](01-code-java/domaine/Prix.java) (package `domaine`).

## Concepts

1. Tests unitaires
2. Fixtures de test (`@BeforeEach`)
3. JUnit — assertions de base
4. Tests d'exceptions (`assertThrows`)
5. Tests paramétrés (`@ValueSource`, `@CsvSource`)
6. Regroupement d'assertions (`assertAll`)
7. Tests d'effets de bord (état avant/après)
8. Couverture de comportements métier
9. Tests d'une méthode dont le comportement se ramifie selon un enum ([`TypePromo`](01-code-java/domaine/TypePromo.java))
10. Relecture critique de tests générés par IA

## Vidéos

1. [Tests unitaires introduction](https://www.youtube.com/watch?v=UKUbHaxJjRw)
2. [JUnit premiers tests](https://www.youtube.com/watch?v=TkvuBBG-V9o)
3. [Les asserts en JUnit](https://www.youtube.com/watch?v=5swvHf57od8)
4. [Les tests paramétrés en JUnit](https://www.youtube.com/watch?v=fPKqt6oPMdY)
5. [Bonnes pratiques tests unitaires](https://www.youtube.com/watch?v=WwKmx9ghJHU)

## Exercices

### Introduction

Afin de gérer l'uniformité de ses prix dans l'ensemble de ses magasins, une chaîne commerciale a développé une application. Les prix varient au cours du temps et en fonction de la quantité achetée. À cette fin, elle a déjà implémenté les classes suivantes :

- **`TypePromo`** : un énuméré reprenant les différents types de promotions applicables à un produit.
- **`Prix`** : représente les prix appliqués selon la quantité achetée à un moment donné.
  - Possède éventuellement un `TypePromo` et, si c'est le cas, une valeur réelle strictement positive `valeurPromo` permettant de quantifier la promotion. La signification de `valeurPromo` dépend du type : montant fixe en euros pour `PUB`, pourcentage de réduction pour `SOLDE` et `DESTOCKAGE` (voir la javadoc de la classe fournie).
  - Les prix unitaires sont gardés dans une `SortedMap<Integer, Double>` : la clé est la quantité minimale à partir de laquelle le prix (la valeur) s'applique. Exemple : 15 euros à partir d'une unité, 12 euros à partir de 10 unités.
  - Il est interdit d'acheter pour une quantité qui est inférieure à la plus petite quantité renseignée dans la `SortedMap`.
  - `getPrix` renvoie le prix brut selon la quantité ; `getPrixPromo` renvoie ce même prix après application de la promotion en cours (ou le prix inchangé s'il n'y a pas de promo).
- **[`Produit`](01-code-java/domaine/Produit.java)** : possède un nom, une marque et un rayon.
  - Deux produits ayant les mêmes valeurs pour ces attributs sont considérés comme étant identiques.
  - Garde aussi l'historique des prix dans une `SortedMap<LocalDate, Prix>` (triée par date décroissante) : la clé est la date à partir de laquelle le prix (la valeur) s'applique.
- **[`ListeProduits`](01-code-java/usecase/ListeProduits.java)** (package `usecase`) : garde la liste des produits triés par ordre croissant de leur nom, puis de leur marque et enfin de leur rayon.

Pour voir les méthodes existantes dans ces différentes classes et savoir plus précisément ce qu'elles font, consultez directement les classes fournies.

Cette première partie se limite aux tests de la classe `Prix` ; les classes `Produit` et `ListeProduits` seront testées dans la partie 2.

### Consignes

**Créer le projet :**

Dans IntelliJ, créez un projet intitulé `AJ_atelier03_partie1`. Récupérez les classes `Prix`, `Produit`, `TypePromo`, `ListeProduits`, les exceptions, la classe [`Main`](01-code-java/main/Main.java) et l'interface [`Util`](01-code-java/util/Util.java) (fournies dans `01-code-java/`) et mettez-les dans les packages adéquats. Il faudra éventuellement ajouter la SDK. Pour cela :

1. Allez dans File → Project Structure…
2. Sélectionnez Project dans Project Settings.
3. Sélectionnez votre SDK dans le menu déroulant situé dans la section Project SDK et cliquez sur Apply et ensuite sur OK.

Vérifiez que tout fonctionne bien en exécutant la classe `Main` du package `main`.

**Créer un dossier pour les tests :**

1. Créez, dans votre projet, un nouveau dossier intitulé `tests` (clic droit sur le projet et choisir New → Directory).
2. Faites un clic droit sur le dossier `tests` et sélectionnez Mark Directory as → Test Sources Root.

**Créer une classe de test :**

Suivez le [tutoriel pas à pas avec captures d'écran](03A_3_tutoriel-creer-test.md) pour créer une classe de test JUnit 5 pour la classe `Prix`. Elle doit s'appeler `PrixTest`, être placée dans le package `domaine` du répertoire `tests`, et contenir une méthode `setUp` annotée avec `@BeforeEach`.

### Tests de la classe `Prix`

**Question 1** : Préparation du test

✏️ *A corriger au tableau*

Déclarez 3 attributs (`prixAucune`, `prixPub` et `prixSolde`) de type `Prix` qui permettront de faire tous les tests. Ces trois attributs seront instanciés dans la méthode annotée `@BeforeEach` :

- `prixAucune` : sans promo. Définissez-y 2 prix : l'un pour signifier qu'à partir d'une unité, le prix unitaire est de 20 euros, l'autre qu'à partir de 10 unités (quantité), le prix unitaire devient 10 euros.
- `prixPub` : avec `TypePromo.PUB` et une `valeurPromo` de 10. Définissez-y 2 prix : à partir de 3 unités, 15 euros ; à partir de 10 unités, 8 euros.
- `prixSolde` : avec `TypePromo.SOLDE` et une `valeurPromo` de 30. Définissez-y 2 prix : à partir de 2 unités, 18 euros ; à partir de 10 unités, 9 euros.

N'oubliez pas que chaque test doit se trouver dans une méthode annotée. Nous vous recommandons d'utiliser l'annotation `@DisplayName` afin d'avoir un affichage clair sur ce qui est testé dans la méthode. Vous pouvez aussi faire plusieurs méthodes de tests pour une même question.

Après avoir écrit une méthode de test, exécutez-la et vérifiez que le test réussit !

**Question 2** : Test des « getters » (utilisez les attributs définis)

On commence simple : pas d'exception à gérer ici, juste vérifier que l'état d'un objet correspond à ce qu'on attend. Une seule assertion par méthode de test suffit ici (pas besoin d'`assertAll`, ça arrive à la question 4).

Sur `prixAucune`, le constructeur sans paramètre doit produire un prix « neutre » : `getValeurPromo` renvoie 0 et `getTypePromo` renvoie `null`. Écrivez une méthode de test par getter.

**Question 3** : Test du constructeur

Il s'agit ici de tester qu'une méthode **lève une exception**. Le constructeur avec paramètres n'accepte pas n'importe quoi : un `TypePromo` à `null` n'a pas de sens (aucune indication du type de promo). Écrivez un test qui utilise `assertThrows` pour vérifier qu'une `IllegalArgumentException` est levée quand le `TypePromo` fourni est `null`.

**Question 4** : Regroupement d'assertions

Sur `prixSolde`, vérifiez `getValeurPromo` et `getTypePromo` **dans une seule méthode de test**, en regroupant vos deux assertions avec `assertAll` — si l'une des deux échoue, vous voulez quand même voir le résultat de l'autre, plutôt que de vous arrêter à la première.

### À partir d'ici, testez avec l'IA

À partir de la question 5, aidez-vous d'un assistant IA pour écrire vos tests (voir la section « Développer des tests avec l'IA » de la théorie) : relisez et exécutez chaque test généré, et vérifiez qu'il passe bien au rouge si vous cassez volontairement le code testé.

### Tests de `getPrixPromo`

La classe `Prix` propose aussi `getPrixPromo`, qui applique la promotion en cours au prix de base. Le mode de calcul diffère selon le `TypePromo` (voir la javadoc de la classe) : les questions suivantes testent chaque branche séparément, plus le cas sans promo.

**Question 5** : `getPrixPromo` sans promo

Sur `prixAucune` (pas de promo), vérifiez que `getPrixPromo` renvoie exactement le même résultat que `getPrix`, pour au moins une quantité.

**Question 6** : `getPrixPromo` avec promo `PUB`

Sur `prixPub` (promo `PUB`), vérifiez que le montant `valeurPromo` est bien soustrait du prix de base.

**Question 7** : `getPrixPromo` avec promo `SOLDE`

Sur `prixSolde` (promo `SOLDE`), vérifiez que la réduction appliquée correspond bien au pourcentage `valeurPromo`.

**Question 8** : `getPrixPromo` et le plancher de `DESTOCKAGE`

Créez un nouveau `Prix` avec `TypePromo.DESTOCKAGE` et une `valeurPromo` assez élevée pour que le prix calculé descende sous 1 euro : vérifiez que `getPrixPromo` renvoie bien 1 euro (le plancher), et non une valeur négative ou nulle.

**Question 9** : `getPrixPromo` avec une quantité invalide

Vérifiez qu'une quantité négative ou nulle lève une `IllegalArgumentException`, avec la même technique `assertThrows` qu'à la question 3.

### Tests paramétrés et de `definirPrix`

Utilisez un **test paramétré** pour couvrir plusieurs valeurs invalides en une seule méthode de test.

**Question 10** : Constructeur — valeur de promo invalide

Complétez le test du constructeur : une valeur de promo qui ne serait pas strictement positive doit aussi lever une `IllegalArgumentException`. Avec `@ParameterizedTest` et `@ValueSource`, couvrez plusieurs valeurs invalides (négative, nulle) en une seule méthode de test.

**Question 11** : `definirPrix` — quantité invalide

Utilisez le même outil pour `definirPrix` : une quantité nulle ou négative doit être rejetée par une `IllegalArgumentException`.

**Question 12** : `definirPrix` — prix unitaire invalide

Un prix unitaire nul ou négatif doit également être rejeté par une `IllegalArgumentException`.

**Question 13** : `definirPrix` — remplacement d'un palier existant

Cas nouveau, avec un **effet de bord** à vérifier : sur `prixAucune`, redéfinissez le prix à 10 unités avec une nouvelle valeur (6 euros), puis vérifiez via `getPrix` que l'ancien prix a bien été remplacé (pas juste ajouté à côté). Ici, il ne suffit plus de vérifier un rejet : il faut appeler la méthode, puis constater un changement d'état.

### Tests de `getPrix`

Les questions suivantes combinent tout ce qui précède et ajoutent le test paramétré **à plusieurs arguments liés** (entrée + résultat attendu), avec `@CsvSource`/`@CsvFileSource` plutôt que `@ValueSource`.

**Question 14** : `getPrix` — quantité invalide

Une quantité négative ou nulle doit lever une `IllegalArgumentException` (`assertThrows`, comme aux questions 3 et 10).

**Question 15** : `getPrix` — balayage des paliers

Sur `prixAucune`, balayez les paliers définis en question 1 avec un test paramétré associant chaque quantité au prix attendu : 1, 5, 9, 10, 15, 20 et 25 unités. Le palier à 10 unités change le prix — une seule valeur ne suffirait pas à couvrir la logique de la `SortedMap`, d'où l'intérêt d'un test à plusieurs arguments liés pour les couvrir toutes en une méthode. Vous pouvez inscrire les paires quantité/prix directement dans `@CsvSource`, ou dans un fichier `.csv` à côté de la classe de test et lu via `@CsvFileSource`.

**Question 16** : `getPrix` — seuil minimal d'une promo

Une promo a un seuil minimal d'achat en-dessous duquel elle n'est pas applicable : demander le prix pour 2 unités sur `prixPub` doit lever une [`QuantiteNonAutoriseeException`](01-code-java/exceptions/QuantiteNonAutoriseeException.java) (package `exceptions`).

**Question 17** : `getPrix` — seuil minimal, deuxième cas

Même vérification côté `prixSolde`, cette fois pour 1 unité — à vous de retrouver, à partir des valeurs choisies en question 1, quelle quantité déclenche bien ce cas.

### Test de scénario sur plusieurs paliers

**Question 18** : Test de scénario sur plusieurs paliers

Dans une méthode de test dédiée, construisez un prix plus complet que ceux utilisés jusqu'ici. L'objectif est de tester un comportement métier sur plusieurs étapes, pas seulement une valeur isolée. Créez un nouveau `Prix` sans promo et définissez-y au moins trois paliers, par exemple 3 unités à 20 euros, 5 unités à 18 euros et 10 unités à 15 euros. Vérifiez ensuite avec `assertAll` que le bon prix est renvoyé pour une quantité exacte sur chaque palier, puis pour les quantités situées entre deux paliers : par exemple 4 unités doivent encore utiliser le palier de 3 unités, et 9 unités celui de 5 unités. Vérifiez aussi qu'une quantité inférieure au premier palier lance bien une `QuantiteNonAutoriseeException`. Redéfinissez enfin un des paliers déjà existants, puis vérifiez que les quantités concernées utilisent bien la nouvelle valeur.

Ce test doit montrer que vous comprenez la logique complète de recherche dans la `SortedMap` : valeur exacte, valeur intermédiaire, cas interdit et remplacement d'un palier.

---

*Passez à la [théorie suivante](../02-partie2/03B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/03-junit-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
