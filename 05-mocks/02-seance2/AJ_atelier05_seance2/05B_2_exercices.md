# Atelier 5 : Mocks – séance 2

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
8. Organisation des tests avec `@Nested` (vu à l'atelier 3 séance 2)

## Vidéos

1. [Tests unitaires - les stubs](https://www.youtube.com/watch?v=aBwQRyDWl1Y)
2. [Tests unitaires - Mock Objects](https://www.youtube.com/watch?v=d5xIDKCBfyI)
3. [Maven - Mockito](https://www.youtube.com/watch?v=BSIrMuM4UHM)
4. [Tests unitaires - Mockito](https://www.youtube.com/watch?v=oZi6CzLGBY8)

## Exercices

### Introduction

Nous continuons l'application de recherche de bibliothèques de la séance 1. En séance 1, le stub permettait de vérifier ce que `BibliothequeService` **renvoie**. Mais la javadoc du service promet aussi quelque chose sur la façon dont il **utilise** sa source : un cache, qui évite de réinterroger l'API d'ODWB pour un code postal déjà demandé. Pour le vérifier, il faut compter les appels à la source et contrôler leurs arguments : c'est le rôle des mock objects, que nous créons avec le framework Mockito (https://site.mockito.org/).

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier05_seance2`. Pour ce faire :

1. Dans IntelliJ, cliquez sur New Project (File → New → Project si vous n'êtes pas dans la fenêtre de départ).
2. Donnez le nom `AJ_atelier05_seance2` à votre projet dans Name.
3. Choisissez l'endroit où vous voulez créer votre projet dans Location.
4. Choisissez Maven (et non IntelliJ !) comme Build system.
5. N'hésitez pas, dans Advanced Settings, à donner un GroupId comme `be.vinci` par exemple.
6. Cliquez sur Create.

Le [tutoriel Maven](05B_3_tutoriel-maven.md) reprend ces étapes en détail et montre comment ajouter les dépendances JUnit 5 et Mockito au `pom.xml` puis vérifier que tout fonctionne.

Votre projet est vide : récupérez les classes fournies dans `AJ_atelier05_seance2/src/main/java/` et copiez-les dans le dossier `src/main/java` de votre projet, en conservant l'arborescence `be/vinci/mocks/` (packages `domaine`, `source`, `main` et `util`). Ce sont les mêmes classes qu'en séance 1, seul le nom des packages change.

La classe à tester est toujours [`BibliothequeService`](src/main/java/be/vinci/mocks/domaine/BibliothequeService.java) (package `be.vinci.mocks.domaine`), et la dépendance à remplacer est l'interface [`SourceBibliotheques`](src/main/java/be/vinci/mocks/source/SourceBibliotheques.java) (package `be.vinci.mocks.source`).

Comme à l'atelier 3 séance 2, regroupez vos méthodes de test par thème dans des classes internes `@Nested` (la fixture et le `@BeforeEach` restant sur la classe externe).

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

Les exemples de syntaxe de la théorie portent sur un autre domaine (des stages sportifs, avec `Stage` et `enregistrerMoniteur`) : transposez-les à `SourceBibliotheques` et à sa méthode `rechercher`. La [cheat sheet de la semaine](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html) rassemble les mêmes appels, ainsi que `Mockito.anyString()`, qui accepte n'importe quel argument de type `String`.

Rappel : pour le code postal `5000`, le service appelle `rechercher` avec le filtre `cp="5000"`, c'est-à-dire la chaîne Java `"cp=\"5000\""`.

Plan de tests de `BibliothequeService`. Sauf mention contraire, le mock de la source renvoie, pour le filtre `cp="5000"`, les deux bibliothèques du `setUp` :

| TC# | Scénario | Résultat attendu | Interaction attendue sur le mock |
|---|---|---|---|
| 1 | Une première recherche `parCodePostal("5000")` | les deux bibliothèques, triées | `rechercher` appelée une fois, avec `cp="5000"` |
| 2 | Deux recherches `parCodePostal("5000")` de suite | la seconde renvoie les deux bibliothèques, triées | `rechercher` appelée une seule fois, avec `cp="5000"` |
| 3 | La source tombe en panne au premier appel, puis répond normalement ; deux recherches `parCodePostal("5000")` | la première lève `SourceIndisponibleException`, la seconde renvoie les deux bibliothèques, triées | `rechercher` appelée deux fois, avec `cp="5000"` |
| 4 | Une recherche `parCodePostal("5000")` puis une recherche `parCodePostal("1000")` (le mock renvoie une liste vide pour `cp="1000"`) | — | `rechercher` appelée une fois avec `cp="5000"` et une fois avec `cp="1000"` |
| 5 | Une recherche `parCodePostal("50A0")` | `IllegalArgumentException` | `rechercher` jamais appelée, quel que soit l'argument |
| 6 | `parCodePostal("5000")`, puis `plusProche("5000", …)`, puis `parThematique("5000", …)` | — | `rechercher` appelée une seule fois, avec `cp="5000"` |
| 7 | La source tombe en panne pour `cp="1000"` ; recherches `parCodePostal("5000")`, `parCodePostal("1000")`, puis à nouveau `parCodePostal("5000")` | la recherche `1000` lève `SourceIndisponibleException`, la dernière renvoie les deux bibliothèques, triées | `rechercher` appelée une seule fois avec `cp="5000"` |

Créez une classe JUnit `BibliothequeServiceTest` dans `src/test/java`, dans le package `be.vinci.mocks.domaine`. Nommez chaque méthode de test d'après son cas : `testServiceTC1`, `testServiceTC2`, …

**Question 1** :

✏️ *A corriger au tableau*

Écrivez la méthode `setUp`. Elle doit créer deux bibliothèques inspirées des données réelles d'ODWB (par exemple la Baie des tecks et la bibliothèque libre de Bomel, reprises de la séance 1), un mock de `SourceBibliotheques` qui renvoie ces deux bibliothèques, **dans le désordre**, quand on appelle `rechercher` avec le filtre `cp="5000"`, et un `BibliothequeService` réel qui utilise ce mock : c'est lui l'objet sous test, il ne doit pas être mocké.

**Question 2** :

✏️ *A corriger au tableau*

Écrivez le scénario TC1 (une recherche, un appel).

**Question 3** :
Écrivez le scénario TC2 (le cache évite un deuxième appel).

**Question 4** :

✏️ *A corriger au tableau*

Écrivez le scénario TC3 (une panne n'est pas mise en cache). Il faut configurer le mock pour qu'il lève une exception au premier appel, puis renvoie les bibliothèques au second : la théorie montre comment enchaîner deux réponses (`thenReturn(…).thenReturn(…)`) et comment lever une exception (`thenThrow`) ; combinez les deux.

### 🤖 À partir d'ici, générez les tests avec l'IA

Les scénarios restants reprennent les configurations du mock que vous venez d'écrire : une réponse normale, une panne, et des vérifications du nombre d'appels. Aidez-vous d'un assistant IA (Claude Code, Copilot, …) pour les générer à partir du plan de tests, mais relisez et exécutez chaque test généré : vérifiez qu'il teste bien le cas décrit, et qu'il échouerait si le service se trompait.

**Question 5** :
Écrivez le scénario TC4 (deux codes postaux, un appel chacun).

**Question 6** :
Écrivez le scénario TC5 (code postal invalide, aucun appel).

**Question 7** :
Écrivez le scénario TC6 (le cache sert aussi à `plusProche` et à `parThematique`).

**Question 8** :
Écrivez le scénario TC7 (la panne d'une autre recherche ne vide pas le cache).

## Parties optionnelles

### Questions d'examen

Les deux questions suivantes sont issues des examens de janvier et de septembre 2026 (question « Tests unitaires », 8 points, ± 40 minutes chacune). Elles se font dans le même projet : copiez les packages `be.vinci.mocks.voiture` et `be.vinci.mocks.club` (fournis dans `AJ_atelier05_seance2/src/main/java/`) dans `/src/main/java`, en conservant l'arborescence `be/vinci/mocks/`.

**Question 9** (examen de janvier) : Tests unitaires de [`CarService`](src/main/java/be/vinci/mocks/voiture/CarService.java) (package `be.vinci.mocks.voiture`)

Vous développez une application de gestion de stock de voitures. L'application permet d'ajouter des voitures et de gérer les marques associées. Les voitures ([`Car`](src/main/java/be/vinci/mocks/voiture/Car.java) : `String brand`, `String model`) sont enregistrées dans un [`CarRepository`](src/main/java/be/vinci/mocks/voiture/CarRepository.java) qui simule une base de données.

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

**Question 10** (examen de septembre) : Tests unitaires de [`EquipmentManager`](src/main/java/be/vinci/mocks/club/EquipmentManager.java) (package `be.vinci.mocks.club`)

Vous développez une application de gestion du matériel sportif d'un club. Les équipements sont enregistrés dans un [`ClubStock`](src/main/java/be/vinci/mocks/club/ClubStock.java), qui simule une source de données lente utilisée par le club.

`EquipmentManager` → `ClubStock` → [`Equipment`](src/main/java/be/vinci/mocks/club/Equipment.java)

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

*Passez à la [théorie suivante](../../../06-streams/01-seance1/AJ_atelier06_seance1/06A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
