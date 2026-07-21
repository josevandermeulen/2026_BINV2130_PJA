# Atelier 3 - partie 1 : JUnit – Concepts

Ce document reprend les notions utiles pour réaliser l'exercice.

## Table des matières

1. [Vidéos](#vidéos)
2. [Pourquoi tester ?](#pourquoi-tester-)
3. [Une classe de test JUnit 5](#une-classe-de-test-junit-5)
4. [`@BeforeEach` et les fixtures de test](#beforeeach-et-les-fixtures-de-test)
5. [Assertions courantes](#assertions-courantes)
6. [Grouper des assertions avec `assertAll`](#grouper-des-assertions-avec-assertall)
7. [Tester qu'une exception est lancée](#tester-quune-exception-est-lancée)
8. [Tests paramétrés](#tests-paramétrés)
9. [Développer des tests avec l'IA](#développer-des-tests-avec-lia)
10. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Tests unitaires introduction](https://www.youtube.com/watch?v=UKUbHaxJjRw)
2. [JUnit premiers tests](https://www.youtube.com/watch?v=TkvuBBG-V9o)
3. [Les asserts en JUnit](https://www.youtube.com/watch?v=5swvHf57od8)
4. [Les tests paramétrés en JUnit](https://www.youtube.com/watch?v=fPKqt6oPMdY)
5. [Bonnes pratiques tests unitaires](https://www.youtube.com/watch?v=WwKmx9ghJHU)

## Pourquoi tester ?

Un test unitaire vérifie automatiquement qu'une méthode se comporte comme attendu, sans devoir relancer le programme entier et vérifier "à l'œil" la sortie console.

Un test qui passe reste vrai tant que le code ne change pas. Si quelqu'un modifie une classe et casse son comportement, le test échoue immédiatement : on parle de *non-régression*.

## Une classe de test JUnit 5

Une classe de test est une classe normale, avec des méthodes annotées `@Test`.

```java
class PrixTest {

    @Test
    void testGetValeurPromoParDefaut() {
        Prix prix = new Prix();
        assertEquals(0, prix.getValeurPromo());
    }
}
```

Chaque méthode annotée `@Test` est exécutée indépendamment par JUnit. L'annotation `@DisplayName` permet de donner un nom lisible au test, affiché dans les résultats :

```java
@Test
@DisplayName("La valeur de la promo est 0 par défaut")
void testGetValeurPromoParDefaut() {
    ...
}
```

## `@BeforeEach` et les fixtures de test

Beaucoup de tests ont besoin des mêmes objets de départ (la *fixture*). Plutôt que de la recréer dans chaque méthode, on la construit une fois dans une méthode annotée `@BeforeEach` : elle est exécutée automatiquement **avant chaque** `@Test`, avec des objets neufs à chaque fois.

```java
class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixPub = new Prix(TypePromo.PUB, 10);
        prixAucune.definirPrix(1, 20);
    }

    @Test
    void testUnTest() {
        // prixAucune et prixPub sont déjà prêts ici
    }
}
```

Comme `setUp()` s'exécute avant chaque test, un test ne peut pas "polluer" un autre test en modifiant la fixture : chacun repart d'un état identique et indépendant.

## Assertions courantes

Une assertion vérifie qu'une condition est vraie et fait échouer le test (avec un message clair) si ce n'est pas le cas — c'est elle qui transforme un simple appel de méthode en véritable vérification automatisée. Sans assertion, un test qui s'exécute sans planter ne prouve rien : il faut explicitement comparer le résultat obtenu à ce qui était attendu.

Les assertions se trouvent dans `org.junit.jupiter.api.Assertions` (import statique `import static org.junit.jupiter.api.Assertions.*;`) : cet import permet d'appeler `assertEquals(...)` directement plutôt que `Assertions.assertEquals(...)`.

```java
assertEquals(20, prix.getPrix(1));      // valeur attendue, puis valeur obtenue
assertTrue(condition);
assertFalse(condition);
assertNull(objet);
assertNotNull(objet);
assertSame(TypePromo.PUB, prix.getTypePromo());   // ==, égalité de référence
assertNotEquals(prix1, prix2);
```

`assertEquals` compare avec `equals` ; `assertSame` compare avec `==`. Pour un `enum` comme `TypePromo`, les deux donnent le même résultat (une seule instance par constante), mais `assertEquals` est préférable pour des objets normaux.

## Grouper des assertions avec `assertAll`

Quand plusieurs vérifications indépendantes portent sur le même test, `assertAll` permet de toutes les exécuter et de rapporter tous les échecs en une fois (au lieu de s'arrêter à la première assertion qui échoue) :

```java
@Test
void testGetters() {
    assertAll(
        () -> assertEquals(0, prixAucune.getValeurPromo()),
        () -> assertNull(prixAucune.getTypePromo())
    );
}
```

## Tester qu'une exception est lancée

`assertThrows` vérifie qu'un bout de code lance bien l'exception attendue. Le code à tester est passé sous forme de lambda :

```java
@Test
void testConstructeurPromoNull() {
    assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15));
}
```

Si le constructeur ne lance pas d'exception (ou lance le mauvais type), le test échoue.

`assertThrows` **renvoie** l'exception capturée. On peut donc la récupérer dans une variable pour vérifier en plus son message ou son contenu — utile quand une même méthode lève le même type d'exception pour plusieurs raisons et qu'on veut s'assurer qu'on tombe bien sur le bon cas :

```java
@Test
void testConstructeurPromoNull() {
    IllegalArgumentException e = assertThrows(
            IllegalArgumentException.class,
            () -> new Prix(null, 15));
    assertEquals("L'objet ne peut pas être null", e.getMessage());
}
```

Vérifier le message reste optionnel : le type d'exception suffit souvent. Ne le faites que si le message fait partie du comportement attendu — sinon un simple changement de texte casserait le test sans que le comportement ait changé.

## Tests paramétrés

Quand on veut exécuter le même test avec plusieurs valeurs, `@ParameterizedTest` évite de dupliquer la méthode :

```java
@ParameterizedTest
@ValueSource(doubles = {-7, -4, 0})
void testConstructeurValeurInvalide(double valeur) {
    assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.SOLDE, valeur));
}
```

JUnit exécute cette méthode une fois par valeur de `@ValueSource`, comme trois tests distincts. `@ValueSource` accepte aussi `ints`, `strings`, etc.

## Développer des tests avec l'IA

Un assistant IA (Claude, ChatGPT, Copilot, …) peut faire gagner du temps sur les tests : proposer des cas limites auxquels on n'a pas pensé, générer la répétition mécanique d'un test paramétré, ou reformuler un `@DisplayName` plus clair. C'est un bon usage : l'IA complète la réflexion, elle ne la remplace pas.

Ce qui ne marche pas : demander à l'IA d'écrire les tests sans avoir compris soi-même ce que la méthode testée est censée faire. L'IA n'a pas accès au comportement métier voulu par l'énoncé — seulement au code qu'on lui montre — et peut donc :

- halluciner une méthode ou un attribut qui n'existe pas dans la classe ;
- se tromper sur une règle métier (par exemple le plancher à 1 euro de `DESTOCKAGE`) si elle n'est pas explicitement donnée ;
- écrire un test qui passe sans vraiment vérifier le bon comportement (un test qui recopie l'implémentation au lieu de vérifier le résultat attendu ne détecte aucune régression).

Un test généré par IA doit donc être relu et compris ligne par ligne avant d'être gardé, exactement comme un test écrit à la main. Un bon réflexe pour vérifier qu'un test teste vraiment quelque chose : casser volontairement la méthode testée (inverser une condition, changer une valeur) et vérifier que le test passe au rouge. S'il reste vert alors que le code est cassé, le test ne sert à rien, qu'il vienne d'une IA ou non.

## Points d'attention pour l'exercice

1. La fixture (`prixAucune`, `prixPub`, `prixSolde`) est construite dans `@BeforeEach`, pas dans le constructeur de la classe de test.
2. Un test = une méthode annotée `@Test` (ou `@ParameterizedTest`), avec un nom explicite (`@DisplayName` recommandé).
3. `assertThrows` teste le comportement d'erreur ; ne pas oublier de tester aussi les cas où les valeurs sont valides.
4. Un test paramétré remplace plusieurs tests presque identiques qui ne différent que par une valeur d'entrée.
5. Après avoir écrit un test, il faut l'exécuter et vérifier qu'il passe (vert) avant de passer au suivant.

---

*Passez aux [exercices](03A_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/03-junit-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
