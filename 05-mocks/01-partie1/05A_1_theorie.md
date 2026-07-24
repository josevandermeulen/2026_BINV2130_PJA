# Atelier 5 – partie 1 : Concepts (stubs)

## Table des matières

1. [Vidéos](#vidéos)
2. [Réalisation de tests unitaires](#réalisation-de-tests-unitaires)
3. [Abstraction de l'implémentation des classes](#abstraction-de-limplémentation-des-classes)
4. [Les stubs](#les-stubs)

## Vidéos

1. [Tests unitaires - les stubs](https://www.youtube.com/watch?v=aBwQRyDWl1Y)
2. [Tests unitaires - Mock Objects](https://www.youtube.com/watch?v=d5xIDKCBfyI)
3. [Maven - Mockito](https://www.youtube.com/watch?v=BSIrMuM4UHM)
4. [Tests unitaires - Mockito](https://www.youtube.com/watch?v=oZi6CzLGBY8)

## Réalisation de tests unitaires

On parle de vrais tests unitaires lorsque l'on teste qu'une seule classe, sans tester ses dépendances.

Par exemple, si l'on teste une classe qui a des attributs qui sont des instances d'autres classes, alors il est important de ne pas tester le code de ces autres classes. Nous parlerions de tests d'intégration si l'on testait le fonctionnement de plusieurs classes qui communiquent.

Pour faire de vrais tests unitaires, nous allons simuler le comportement de toutes les instances qui n'appartiennent pas au type de la classe testée.

Il existe différents types de faux objets, nous n'en examinerons que deux dans nos ateliers :

1. les stubs ;
2. les mock objects.

NB : on utilise aussi, en fonction des besoins, des fake objects, ou des spies :

- un **fake object** est une implémentation fonctionnelle simplifiée d'une dépendance, qui prend un raccourci la rendant inadaptée à la production (ex. une base de données en mémoire à la place d'une vraie base de données) ;
- un **spy** est un stub qui enregistre en plus les informations sur la façon dont il a été appelé (nombre d'appels, paramètres reçus, …), afin de les vérifier après coup dans le test.

(Référence : *xUnit Test Patterns*, Gerard Meszaros, http://xunitpatterns.com/Test%20Double.html)

Dans cette partie, nous nous concentrons sur les stubs ; les mock objects (via la librairie Mockito) feront l'objet de la partie 2.

## Abstraction de l'implémentation des classes

Lorsque nous souhaitons créer du code qui peut être facilement testable de façon unitaire, nous allons abstraire l'implémentation des classes par l'utilisation d'interfaces ; ainsi, lors d'interactions entre des objets de différents types, on ne doit pas se soucier de l'implémentation des classes ; l'auteur d'une classe peut mettre à jour son implémentation sans casser les interactions avec celle-ci ; cela participe à la mise en place d'un couplage faible entre objets.

Un faible couplage entre objets facilite grandement les tests unitaires car nous allons pouvoir facilement créer des faux objets pour tous les types qui ne correspondent pas à la classe sous tests.

## Les stubs

Un stub est un faux objet « sans intelligence » : il ne contient aucune logique de traitement et ne fait aucun calcul à partir des arguments qu'on lui passe. Pour un scénario de test donné, il est configuré (typiquement via son constructeur) pour renvoyer une réponse fixe et prédéterminée, peu importe les paramètres reçus lors de l'appel — c'est le test qui décide à l'avance ce que le stub doit répondre, pas le stub qui le calcule. Les stubs servent à **tester l'état** d'un objet : on vérifie le résultat produit par l'objet sous test (sa valeur de retour, son état après l'appel), pas la façon dont il a interagi avec ses collaborateurs.

Le stub est utile lorsque, pour effectuer un test, on a besoin des réponses données par ces objets mais que le test ne porte pas sur notre interaction avec ces objets.

Exemple : on doit traiter des objets provenant d'une base de données. Le test ne porte pas sur notre interface avec la base de données mais sur le traitement des objets. Les stubs nous renverront des objets à traiter.

Voici le cycle de vie d'un test avec des stubs (modèle AAA) :

1. **Arrange – Set context** : préparez l'objet sous tests, ses collaborateurs « stubs » et connectez-les ;
2. **Act** : testez un comportement de l'objet sous tests, appelez la méthode à tester ;
3. **Interact** : l'objet sous tests fait son job en collaborant avec d'autres objets ;
4. **Assert** : utilisez des asserts pour vérifier l'état de l'objet, pour vérifier que la méthode testée a les effets désirés.

(Référence : *Fake and Mock Objects in Pictures*, Bill Wake, https://www.industriallogic.com/blog/mock-objects-pictures/)

### Exemple complet

Supposons que vous ayez une classe `Calculator` que vous souhaitez tester. Cette classe a une méthode `addToX` qui additionne deux nombres à une donnée externe (x). Vous voulez tester cette méthode en utilisant un stub pour simuler la dépendance à une source de données externe.

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

Vous pourriez créer un stub en implémentant votre propre classe qui implémente l'interface `DataService`.

Tout d'abord, on créerait une classe `DataServiceStub` qui implémente l'interface `DataService` :

```java
public class DataServiceStub implements DataService {
  private int data;

  public DataServiceStub(int data) {
    this.data = data;
  }

  @Override
  public int getData() {
    return data;
  }
}
```

Voici un exemple de test unitaire en utilisant JUnit et le cycle de vie donné ci-dessus :

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

  private Calculator calculator;
  private DataServiceStub dataServiceStub;

  @BeforeEach
  void setUp() {
    // Arrange
    dataServiceStub = new DataServiceStub(5); // Crée un stub personnalisé avec une valeur de 5

    calculator = new Calculator(dataServiceStub);
  }

  @Test
  void testAddToX() {
    // Arrange (optionnel, car déjà fait dans le setUp)

    // Act
    int result = calculator.addToX(3, 4);

    // Interact (pas besoin de vérifier l'interaction car c'est un stub personnalisé)

    // Assert
    assertEquals(12, result); // Vérifie que le résultat est correct
  }
}
```

Dans cet exemple :

1. La section **Arrange** consiste à configurer l'environnement de test. Nous créons un stub pour `DataService` à l'aide de notre classe `DataServiceStub` et définissons son comportement à renvoyer la valeur 5 lorsqu'il est appelé.
2. Dans la section **Act**, nous appelons la méthode `addToX` de la classe `Calculator` que nous testons.
3. Dans la section **Interact**, il n'y a rien à faire car nous utilisons un stub personnalisé (nous savons que la fonction `getData` du stub sera appelée).
4. Enfin, dans la section **Assert**, nous vérifions que le résultat de la méthode `addToX` est conforme à nos attentes à l'aide d'une assertion.

Cela illustre comment structurer un test unitaire en Java avec un stub en utilisant le modèle AAA pour tester la classe `Calculator`.

---

*QCM de cette semaine sur mooVin : à compléter pour le lundi 12/10/2026 à 20h.*

*Passez aux [exercices](05A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
