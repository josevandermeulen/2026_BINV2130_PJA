# Atelier 5 – partie 2 : Concepts (mocks, Mockito, dépendances)

## Table des matières

1. [Vidéos](#vidéos)
2. [Les mocks](#les-mocks)
3. [Gestion des dépendances d'une application Java](#gestion-des-dépendances-dune-application-java)

## Vidéos

1. [Tests unitaires - les stubs](https://www.youtube.com/watch?v=aBwQRyDWl1Y)
2. [Tests unitaires - Mock Objects](https://www.youtube.com/watch?v=d5xIDKCBfyI)
3. [Maven - Mockito](https://www.youtube.com/watch?v=BSIrMuM4UHM)
4. [Tests unitaires - Mockito](https://www.youtube.com/watch?v=oZi6CzLGBY8)

## Les mocks

### Généralités

Dans certains cas, les tests consistent à vérifier si l'on a bien appelé les bonnes méthodes avec les bonnes données. Les mocks servent à **tester le comportement** d'un objet.

Exemple : on veut faire persister des objets dans une base de données. Il faut vérifier que l'on envoie bien les bonnes données au DAO (Data Access Object, un objet permettant de regrouper les opérations vers une base de données).

Dans ce cas le mock object vérifiera que l'on appelle bien les bonnes méthodes dans le bon ordre avec les bonnes données. Si l'on n'appelle pas la bonne méthode au bon moment, le mock object déclarera le test échoué.

À la fin, il faut encore vérifier que l'on a bien appelé toutes les méthodes nécessaires (on pourrait ne rien avoir fait). C'est pourquoi l'on a une méthode de vérification finale qui déclarera le test échoué si au moins une des méthodes devant être appelée ne l'a pas été.

Voici le cycle de vie d'un test avec des mocks :

1. **Arrange – Set context** : préparez l'objet sous tests ;
2. **Arrange – Configure the mock** : préparez les expectations (les attentes) dans le mock qui est utilisé par l'objet sous tests, principalement indiquez au mock quoi attendre en termes d'appels de méthodes et leurs arguments ;
3. **Act** : testez un comportement de l'objet sous tests, appelez la méthode à tester ;
4. **Interact** : l'objet sous tests fait son job en collaborant avec d'autres objets ;
5. **Mock remembers its interactions** : le mock retient ses interactions, les appels de méthodes, les arguments associés, … ;
6. **Assert expectations** : vérifiez que les méthodes correctes ont été appelées dans le mock (avec éventuellement les données correctes en arguments) ; vous pouvez toujours utiliser des asserts pour vérifier l'état de l'objet sous tests, pour vérifier que la méthode testée a les effets désirés.

(Référence : *Fake and Mock Objects in Pictures*, Bill Wake, https://www.industriallogic.com/blog/mock-objects-pictures/)

Il est à noter que, contrairement au cas des stubs, la vérification ne se fait pas dans la méthode de test mais bien dans le mock object !

L'écriture d'objets de type mock peut s'avérer longue et fastidieuse : il s'agit de configurer chaque objet mocké pour qu'il réagisse comme souhaité. Cette configuration concerne :

1. les méthodes invoquées : paramètres d'appel et valeurs de retour ;
2. l'ordre d'invocation de ces méthodes ;
3. le nombre d'invocations de ces méthodes.

Des librairies ont donc été conçues pour rendre la création de ces objets fiable et rapide ; elles permettent de créer dynamiquement des objets généralement à partir d'interfaces ou de classes. Elles proposent fréquemment des fonctionnalités très utiles au-delà de la simple simulation d'une valeur de retour comme :

1. la simulation de cas d'erreurs en levant des exceptions ;
2. la validation des appels de méthodes ;
3. la validation de l'ordre de ces appels ;
4. la validation des appels avec un timeout.

### Exemple avec Mockito

Reprenons l'exemple de la fiche précédente avec la classe `Calculator` et le cycle de vie d'un test donné ci-dessus. Nous allons utiliser Mockito pour créer un stub pour la classe `DataService`. Nous ne devons donc plus créer de stub personnalisé et l'ancienne classe `DataServiceStub` va donc être remplacée par un mock object.

La classe `Calculator` reste la même. Pour rappel, voici son contenu :

```java
public class Calculator {
  private DataService dataService;

  public Calculator(DataService dataService) {
    this.dataService = dataService;
  }

  public int addToX(int a, int b) {
    int x = dataService.getData();
    return a + b + x;
  }
}

public interface DataService {
  int getData();
}
```

Voici le code mis à jour pour `CalculatorTest` en utilisant la librairie Mockito :

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CalculatorTest {

  private DataService dataService; // Le mock

  private Calculator calculator;

  @BeforeEach
  void setUp() {
    // Arrange and Configure the Mock
    dataService = Mockito.mock(DataService.class); // Crée un mock de DataService
    calculator = new Calculator(dataService);
  }

  @Test
  void testAddToX() {
    // Arrange
    int a = 3;
    int b = 4;
    int x = 5; // Valeur simulée pour getData
    Mockito.when(dataService.getData())
        .thenReturn(x); // Configuration du mock pour renvoyer la valeur x

    // Act
    int result = calculator.addToX(a, b);

    // Interact with Collaborators & Mock Remembers its Interactions

    Mockito.verify(dataService).getData(); // Vérifie que la méthode getData du mock a été appelée

    // Assert
    assertEquals(12, result); // Vérifie que le résultat est correct
  }
}
```

Dans cet exemple :

1. **Arrange** : on prépare le contexte du test, la préparation de l'objet à tester (`calculator`), et des paramètres qui seront donnés à la méthode testée `addToX`.
2. **Configure the Mock** : dans la méthode `setUp`, nous utilisons la méthode `Mockito.mock(DataService.class)` pour créer le mock, que nous stockons dans la variable `dataService`. Ensuite, nous configurons le comportement du mock en utilisant `Mockito.when` pour qu'il renvoie une valeur simulée lors de l'appel à `getData`.
3. **Act** : dans notre test `testAddToX`, nous appelons la méthode `addToX` de `Calculator` avec les valeurs `a` et `b`.
4. **Interact with Collaborators** : nous utilisons `Mockito.verify` pour vérifier que la méthode `getData` du mock a bien été appelée.
5. **Mock Remembers its Interactions** : cette étape est vide car Mockito se souvient automatiquement des interactions avec les mocks.
6. **Assert** : enfin, nous utilisons une assertion pour vérifier que le résultat de la méthode `addToX` correspond à nos attentes.

### Mockito

Dans ce cours, nous utiliserons la librairie Mockito pour gérer nos mocks.

Voici quelques instructions qui vous seront utiles :

1. Création d'un mock object pour l'interface `Stage` :

```java
Stage stage = Mockito.mock(Stage.class);
```

2. Configurer les valeurs renvoyées lors d'appels de méthode sur un mock (`stage`) :

```java
Mockito.when(stage.enregistrerMoniteur(moniteur)).thenReturn(true);
```

→ signifie que l'appel de la méthode `enregistrerMoniteur` (avec comme paramètre `moniteur`) sur le mock renverra `true`.

```java
Mockito.when(stage.enregistrerMoniteur(moniteur)).thenReturn(true).thenReturn(false);
```

→ signifie que l'appel de la méthode `enregistrerMoniteur` (avec comme paramètre `moniteur`) sur le mock renverra `true` la première fois puis `false`.

```java
Mockito.when(stage.enregistrerMoniteur(null))
    .thenThrow(IllegalArgumentException.class);
```

→ signifie que l'appel de la méthode `enregistrerMoniteur` avec un paramètre `null` lance ladite exception.

3. Vérifier qu'une méthode a été appelée (avec le bon paramètre) ou non sur un mock (`stage`) :

```java
Mockito.verify(stage).enregistrerMoniteur(moniteur);
```

→ vérifie que la méthode `enregistrerMoniteur` a bien été invoquée, avec comme paramètre `moniteur`, une et une seule fois.

```java
Mockito.verify(stage, Mockito.times(nb)).enregistrerMoniteur(moniteur);
```

→ vérifie que la méthode `enregistrerMoniteur` a bien été invoquée, avec comme paramètre `moniteur`, exactement `nb` fois.

```java
Mockito.verify(stage, Mockito.never()).enregistrerMoniteur(moniteur);
```

→ vérifie que la méthode `enregistrerMoniteur`, avec comme paramètre `moniteur`, n'a pas été invoquée.

```java
Mockito.verify(stage, Mockito.atLeastOnce()).enregistrerMoniteur(moniteur);
```

→ vérifie que la méthode `enregistrerMoniteur` a bien été invoquée, avec comme paramètre `moniteur`, au moins une fois.

4. D'autres possibilités à découvrir dans la doc : https://www.javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html

## Gestion des dépendances d'une application Java

### Généralités

Lors de la création d'un projet sous IntelliJ, il est possible de choisir un gestionnaire qui va faciliter le build de nos applications, comme Maven.

La partie qui nous intéresse le plus dans ces outils qui facilitent le build de nos projets, c'est la gestion des dépendances de nos programmes.

Maven permet de gérer facilement les librairies associées à nos applications via :

1. un ou plusieurs fichier(s) de configuration identifiant notamment les versions des librairies à installer ;
2. des commandes (pour télécharger, mettre à jour, supprimer des librairies, …) ;
3. et des repositories distants contenant les librairies à télécharger.

Pour nos ateliers, nous avons décidé que Maven serait le gestionnaire de dépendances de nos applications. Maven contient un repository centralisé qui contient plein de projets que des développeurs comme nous ont voulu y mettre.

Pour un projet Maven, les dépendances d'une application seront indiquées dans le fichier `pom.xml`.

Nous verrons dans les exercices comment utiliser IntelliJ pour télécharger les dépendances indiquées dans `pom.xml`. Le [tutoriel Maven](05B_3_tutoriel-maven.md) montre la mise en place complète d'un projet Maven avec JUnit 5 et Mockito.

### Git et les dépendances

Lorsqu'on gère ses dépendances avec Maven, on ne met jamais sous configuration le code source de ces dépendances via Git : il ne nous appartient pas, et il est déjà versionné par Maven lui-même. Si vous utilisez un repo GitHub ou GitLab, pensez donc à ajouter un fichier `.gitignore` pour l'exclure du suivi.

On ne mettra en configuration que le fichier `pom.xml`, qui liste les dépendances à installer, jamais les répertoires contenant le code des librairies elles-mêmes. Pour générer un bon `.gitignore`, demandez simplement à un assistant IA de vous en générer un adapté à Maven et IntelliJ.

---

*Passez aux [exercices](05B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
