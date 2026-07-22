# Atelier 5 : Mocks – partie 2

## Objectif

L'objectif de cet atelier est d'utiliser Mockito pour créer des mocks et tester des interactions entre objets dans un projet Maven.

## Concepts

1. Mocks
2. Mockito
3. Maven
4. Dépendances de test
5. Vérification d'interactions
6. Injection de dépendances pour les tests
7. Tests unitaires isolés
8. Organisation des tests avec `@Nested` (vu à l'atelier 3 partie 2)

## Vidéos

1. [Tests unitaires - les stubs](https://www.youtube.com/watch?v=aBwQRyDWl1Y)
2. [Tests unitaires - Mock Objects](https://www.youtube.com/watch?v=d5xIDKCBfyI)
3. [Maven - Mockito](https://www.youtube.com/watch?v=BSIrMuM4UHM)
4. [Tests unitaires - Mockito](https://www.youtube.com/watch?v=oZi6CzLGBY8)

## Exercices

### Introduction

Nous continuons l'application de gestion de stages sportifs de la partie 1. Cette fois, plutôt que d'écrire des stubs à la main, nous utilisons le framework Mockito (https://site.mockito.org/) pour créer les mock objects et vérifier les interactions entre le moniteur et ses stages.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier05_partie2`. Pour ce faire :

1. Dans IntelliJ, cliquez sur New Project (File → New → Project si vous n'êtes pas dans la fenêtre de départ).
2. Donnez le nom `AJ_atelier05_partie2` à votre projet dans Name.
3. Choisissez l'endroit où vous voulez créer votre projet dans Location.
4. Choisissez Maven (et non IntelliJ !) comme Build system.
5. N'hésitez pas, dans Advanced Settings, à donner un GroupId comme `be.vinci` par exemple.
6. Cliquez sur Create.

Le [tutoriel Maven](05B_3_tutoriel-maven.md) reprend ces étapes en détail et montre comment ajouter les dépendances JUnit 5 et Mockito au `pom.xml` puis vérifier que tout fonctionne.

Nous allons reprendre les mêmes classes et interfaces que celles utilisées lors de la partie 1 de cet atelier 5 : copiez/collez les packages `be.vinci.mocks.domaine` et `be.vinci.mocks.util` (fournis dans `01-code-java/src/main/java/`, c'est l'état de la solution de la partie 1 — voir aussi `../01-partie1/02-solution/`) au sein de votre nouveau projet dans `/src/main/java`, en conservant l'arborescence `be/vinci/mocks/`.

Comme à l'atelier 3 partie 2, regroupez vos méthodes de test par thème dans des classes internes `@Nested` (la fixture et le `@BeforeEach` restant sur la classe externe).

### Ajout des dépendances à un projet Maven

✏️ *A corriger au tableau*

Mockito va donc être intégré au projet via Maven, via le fichier `pom.xml` qui décrit le projet.

L'artifact ID, combiné au group ID, est l'identifiant unique de notre projet. C'est grâce à lui qu'on peut différencier notre projet des autres.

Comme montré dans le [tutoriel Maven](05B_3_tutoriel-maven.md), demandez à un assistant IA de générer les dépendances JUnit 5 et Mockito pour votre `pom.xml`.

Dans votre fichier `pom.xml` devrait maintenant se trouver ceci (en dessous de `<version>…`) qui indique les dépendances ajoutées (le scope `test` indique que ces librairies ne servent qu'aux tests) :

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>5.21.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Cliquez sur le symbole apparu en haut à droite pour charger les changements dans Maven (ou raccourci clavier `Ctrl+Maj+O`). Les dépendances apparaîtront alors également dans les External Libraries au sein de l'explorateur du projet.

### Les mocks via Mockito

Les tests que nous allons maintenant effectuer portent sur les transitions d'états ; l'état correspond toujours au nombre de stages associés à un moniteur ! Il faut tester si les méthodes interagissent correctement avec les autres objets et leur donnent les valeurs attendues. Il faut implémenter ces tests avec des mock objects.

Nous avons déjà établi un plan de tests pour la méthode `ajouterStage(Stage stage)` :

| TC# | État | Méthode | Paramètres | État suivant | Résultat attendu | Valeur retour | Interaction attendue sur le mock stage |
|---|---|---|---|---|---|---|---|
| 1 | 0 | ajouterStage | stage valide | 1 | Le stage est ajouté | true | `enregistrerMoniteur` appelée avec le moniteur |
| 2 | 1 | ajouterStage | stage valide semaine libre | 2 | Le stage est ajouté | true | `enregistrerMoniteur` appelée avec le moniteur |
| 3 | 2 | ajouterStage | stage valide semaine libre | 3 | Le stage est ajouté | true | `enregistrerMoniteur` appelée avec le moniteur |
| 4 | 3 | ajouterStage | stage valide semaine libre | 4 | Le stage est ajouté | true | `enregistrerMoniteur` appelée avec le moniteur |
| 5 | 4 | ajouterStage | stage déjà présent | 4 | stage non ajouté | false | `enregistrerMoniteur` appelée une seule fois |
| 6 | 4 | ajouterStage | semaine déjà prise | 4 | stage non ajouté | false | `enregistrerMoniteur` jamais appelée |
| 7 | 4 | ajouterStage | stage possédant déjà un autre moniteur | 4 | stage non ajouté | false | `enregistrerMoniteur` jamais appelée |
| 8 | 4 | ajouterStage | stage non présent avec comme moniteur celui auquel on veut ajouter le stage (semaine libre, …) | 5 | stage ajouté | true | `enregistrerMoniteur` jamais appelée |
| 9 | 0 | ajouterStage | stage sans moniteur pour un sport pour lequel le moniteur n'est pas compétent | 0 | stage non ajouté | false | `enregistrerMoniteur` jamais appelée |

Comme nous devons vérifier que la méthode `ajouterStage` de la classe [`MoniteurImpl`](01-code-java/src/main/java/be/vinci/mocks/domaine/MoniteurImpl.java) (package `be.vinci.mocks.domaine`) met bien en place les deux côtés de l'association en appelant la méthode `enregistrerMoniteur` de l'objet stage, nous allons devoir construire un (ou plusieurs) mock(s) object(s) pour la classe [`Stage`](01-code-java/src/main/java/be/vinci/mocks/domaine/Stage.java). La raison principale d'un mock object est de vérifier que l'on appelle bien la ou les méthodes attendues avec le ou les bons paramètres.

Créez une classe JUnit `MoniteurImplTest`.

**Question 1** :
✏️ *A corriger au tableau*

Relisez le code de `ajouterStage` dans `MoniteurImpl` : pour qu'un stage soit ajouté avec succès, il faut que `stage.getSport().contientMoniteur(this)` renvoie `true`, que `stage.getMoniteur()` renvoie `null` (ou le moniteur lui-même) et que la semaine (`stage.getNumeroDeSemaine()`) soit libre. Il faut donc, dans chaque test, un `Stage` (et le [`Sport`](01-code-java/src/main/java/be/vinci/mocks/domaine/Sport.java) qu'il renvoie) configurés pour satisfaire ce scénario de base — c'est ce que nous allons préparer une bonne fois dans `setUp` plutôt que de le répéter dans chaque test.

Écrivez la méthode `setUp` :

```java
@BeforeEach
void setUp() {
    // ...
}
```

Elle doit créer :

- un `moniteur` (`MoniteurImpl` réel, pas un mock — c'est lui l'objet sous test) ;
- un mock `sportCompetent` pour lequel `contientMoniteur(moniteur)` renvoie `true` ;
- un mock `stageValide` dont `getSport` renvoie `sportCompetent`, `getMoniteur` renvoie `null`, et `getNumeroDeSemaine` renvoie une semaine libre (par exemple 8, puisque les tests n'utiliseront jamais plus de stages que de semaines libres avant celle-ci).

**Question 2** :
✏️ *A corriger au tableau*

Comme en partie 1, plusieurs scénarios du plan de tests partent d'un moniteur ayant déjà un certain nombre de stages (l'« état »). Plutôt que de répéter cette préparation dans chaque test, écrivez une méthode privée `preparerMoniteurAvecNStages(int nombreDeStages)` qui amène le `moniteur` à l'état voulu : à chaque itération, créez un nouveau mock de `Stage` configuré comme `stageValide` (même sport, pas de moniteur), mais avec un numéro de semaine différent à chaque fois (sinon `ajouterStage` échouerait, la semaine étant déjà prise), et ajoutez-le au moniteur.

```java
private void preparerMoniteurAvecNStages(int nombreDeStages) {
    for (int numSemaine = 1; numSemaine <= nombreDeStages; numSemaine++) {
        // Création d'un mock de Stage pour chaque stage à ajouter
    }
}
```

Si vous avez besoin d'inspiration pour configurer vos mocks, n'hésitez pas à (re)lire la théorie sur Mockito.

Implémentez ensuite un test par scénario du plan de tests ci-dessus, nommé en fonction du cas décrit (par exemple `testMoniteurTC1`) :

**Question 3** :

✏️ *A corriger au tableau*

Écrivez `testMoniteurTC1`, qui utilise directement `stageValide` (état 0).

**Question 4** :

✏️ *A corriger au tableau*

Écrivez `testMoniteurTC2`, qui utilise `preparerMoniteurAvecNStages` pour amener le moniteur à l'état 1, puis ajoute `stageValide`.

### À partir d'ici, générez les tests avec l'IA

À partir de la question 5, les scénarios restants (TC3 à TC9) suivent tous le même moule que TC1 et TC2 : c'est répétitif, un bon usage de l'IA. Aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour générer ces tests à partir du plan de tests, mais relisez et exécutez chaque test généré, et vérifiez qu'il passe bien au rouge si vous cassez volontairement le code testé.

**Question 5** : Écrivez `testMoniteurTC3`, qui amène le moniteur à l'état 2 avant d'ajouter `stageValide`.

**Question 6** : Écrivez `testMoniteurTC4`, qui amène le moniteur à l'état 3 avant d'ajouter `stageValide`.

**Question 7** : Écrivez `testMoniteurTC5`, qui amène le moniteur à l'état 4 (en y incluant `stageValide`) avant de tenter de ré-ajouter `stageValide`.

**Question 8** : Écrivez `testMoniteurTC6`, qui amène le moniteur à l'état 4 avant d'ajouter un nouveau mock de `Stage` dont `getNumeroDeSemaine` renvoie une semaine déjà occupée.

**Question 9** : Écrivez `testMoniteurTC7`, qui amène le moniteur à l'état 4 avant d'ajouter un mock de `Stage` dont `getMoniteur` renvoie un autre moniteur (mocké ou non).

**Question 10** : Écrivez `testMoniteurTC8`, qui amène le moniteur à l'état 4 avant d'ajouter un mock de `Stage` dont `getMoniteur` renvoie déjà ce `moniteur` (semaine libre).

**Question 11** : Écrivez `testMoniteurTC9`, qui, sans aucun stage préalable, ajoute un mock de `Stage` dont le sport (un autre mock) renvoie `false` à `contientMoniteur`.

## Parties optionnelles

### Questions d'examen

Les deux questions suivantes sont issues des examens de janvier et de septembre 2026 (question « Tests unitaires », 8 points, ± 40 minutes chacune). Elles se font dans le même projet : copiez les packages `be.vinci.mocks.voiture` et `be.vinci.mocks.club` (fournis dans `01-code-java/src/main/java/`) dans `/src/main/java`, en conservant l'arborescence `be/vinci/mocks/`.

**Question 12** (examen de janvier) : Tests unitaires de [`CarService`](01-code-java/src/main/java/be/vinci/mocks/voiture/CarService.java) (package `be.vinci.mocks.voiture`)

Vous développez une application de gestion de stock de voitures. L'application permet d'ajouter des voitures et de gérer les marques associées. Les voitures ([`Car`](01-code-java/src/main/java/be/vinci/mocks/voiture/Car.java) : `String brand`, `String model`) sont enregistrées dans un [`CarRepository`](01-code-java/src/main/java/be/vinci/mocks/voiture/CarRepository.java) qui simule une base de données.

`CarService` → `CarRepository` → `Car`

Consignes :

- Implémentez des tests unitaires pour la méthode `addCar` de la classe `CarService`, conformément au plan de tests ci-dessous. Lisez d'abord la javadoc de `addCar`.
- Les deux premiers cas de test sont sans mock. Les deux suivants sont avec mock : utilisez Mockito pour mocker `CarRepository`. Vous ne devez pas créer d'interface pour `CarRepository`.
- Vérifiez uniquement les résultats attendus indiqués dans le plan de tests. Considérez que toutes les autres méthodes de l'application sont correctes et déjà testées.
- Le nom de la classe de test doit être `CarServiceTest`.

| ID scénario | Scénario | Résultats attendus à vérifier |
|---|---|---|
| TC1 (sans mock) | Tentative d'ajout d'une voiture dont la marque est `null` | `IllegalArgumentException` |
| TC2 (sans mock) | Ajout d'une voiture dont la marque existe déjà | La méthode indique que l'opération est un succès. La voiture est présente dans le `carService`. |
| TC3 (avec mock) | Ajout d'une voiture pour une marque pas encore présente dans le stock | La méthode `addBrand` de `CarRepository` est appelée une fois. La méthode `addCar` de `CarRepository` est appelée une fois. |
| TC4 (avec mock) | Ajout d'une voiture pour une marque déjà présente dans le stock | La méthode `addBrand` de `CarRepository` n'est pas appelée. La méthode `addCar` de `CarRepository` est appelée une fois. |

**Question 13** (examen de septembre) : Tests unitaires de [`EquipmentManager`](01-code-java/src/main/java/be/vinci/mocks/club/EquipmentManager.java) (package `be.vinci.mocks.club`)

Vous développez une application de gestion du matériel sportif d'un club. Les équipements sont enregistrés dans un [`ClubStock`](01-code-java/src/main/java/be/vinci/mocks/club/ClubStock.java), qui simule une source de données lente utilisée par le club.

`EquipmentManager` → `ClubStock` → [`Equipment`](01-code-java/src/main/java/be/vinci/mocks/club/Equipment.java)

Consignes :

- Implémentez des tests unitaires pour la méthode `addEquipment` de la classe `EquipmentManager`, conformément au plan de tests ci-dessous.
- Les deux premiers cas de test sont sans mock. Les deux suivants sont avec mock : utilisez Mockito pour mocker `ClubStock`. Vous ne devez pas créer d'interface pour `ClubStock`.
- Vérifiez uniquement les résultats attendus indiqués dans le plan de tests. Considérez que toutes les autres méthodes de l'application sont correctes et déjà testées.
- Le nom de la classe de test doit être `EquipmentManagerTest`.

| ID scénario | Scénario | Résultats attendus à vérifier |
|---|---|---|
| TC1 (sans mock) | Tentative d'emprunt d'un équipement existant avec un nom de membre vide | `IllegalArgumentException` |
| TC2 (sans mock) | Emprunt d'un équipement disponible, identifié par son code | La méthode indique que l'opération est un succès. Vérifiez que l'objet n'est plus empruntable. |
| TC3 (avec mock) | Emprunt d'un équipement disponible, identifié par son code | La méthode `markAsBorrowed` de `ClubStock` est appelée une fois avec les bons arguments. |
| TC4 (avec mock) | Emprunt d'un équipement déjà emprunté | La méthode indique que l'opération a échoué. La méthode `markAsBorrowed` de `ClubStock` n'est pas appelée. |

---

*Passez à la [théorie suivante](../../06-streams/01-partie1/06A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
