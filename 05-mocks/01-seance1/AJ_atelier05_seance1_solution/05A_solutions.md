# Atelier 5 : Mocks – séance 1 : solutions

*Énoncé : [`05A_2_exercices.md`](../AJ_atelier05_seance1/05A_2_exercices.md) — théorie : [`05A_1_theorie.md`](../AJ_atelier05_seance1/05A_1_theorie.md).*

## Pourquoi ne pas tester avec la vraie source ?

### Question 1

**Énoncé :** Lisez le constructeur et la méthode `parCodePostal` de `BibliothequeService`, puis la javadoc de la classe `OdwbClient`. Donnez au moins trois raisons pour lesquelles des tests de `BibliothequeService` qui utiliseraient un `OdwbClient` ne seraient pas de […]

**pourquoi ne pas tester avec `OdwbClient`**

Un test unitaire doit être rapide, reproductible et ne tester qu'une classe. Avec un `OdwbClient`, aucune de ces trois propriétés ne tient :

- **le test dépend du réseau** : sans connexion (examen hors ligne, wifi en panne, serveur d'ODWB en maintenance), il échoue alors que `BibliothequeService` n'a aucun défaut ;
- **le résultat attendu change** : le jeu de données est mis à jour par la Fédération Wallonie-Bruxelles ; un test qui affirme « Namur a six bibliothèques » devient faux le jour où une bibliothèque ouvre ou ferme, sans qu'une seule ligne de code ait changé ;
- **le test est lent** : chaque appel prend de l'ordre d'une seconde ; avec des dizaines de tests lancés à chaque modification, l'attente devient pénible ;
- **ce n'est plus un test unitaire** : un échec peut venir de `BibliothequeService`, de `OdwbClient`, du réseau ou de l'API ; on teste plusieurs choses à la fois, c'est un test d'intégration ;
- **certains cas sont impossibles à provoquer** : on ne peut pas demander à ODWB de tomber en panne au moment du test, ni de renvoyer une liste vide pour Namur. Or ce sont justement des cas que le service doit bien gérer.

C'est possible ici parce que `BibliothequeService` ne connaît que l'interface `SourceBibliotheques` et reçoit sa source dans son constructeur : on peut lui en donner une autre sans modifier une seule ligne du service.

## Le stub de la source

### Question 2

**Énoncé :** Dans le package `domaine` du dossier `test`, écrivez la classe `SourceStub`, qui implémente `SourceBibliotheques`. Elle doit pouvoir être créée de deux façons : avec une liste de bibliothèques, que `rechercher` renverra à chaque appel quel que soit le […]

**le stub de la source**

`SourceStub` ne regarde jamais le filtre qu'il reçoit : il renvoie la liste fixée à sa
création, que le service demande `cp="5000"` ou autre chose. C'est voulu. Si le stub filtrait
par code postal, il contiendrait de la logique, qu'il faudrait à son tour tester, et une erreur
dans ce filtrage ferait échouer les tests du service sans que le service soit en cause.

Le stub renvoie une **copie** de sa liste : simple prudence, pour que personne ne puisse modifier
la liste fixée à la création (le service, lui, copie déjà ce qu'il reçoit avant de le trier).

[`test/domaine/SourceStub.java`](test/domaine/SourceStub.java) :

```java
package domaine;


/**
 * Stub de SourceBibliotheques : renvoie toujours la réponse fixée à sa création, quel que soit le
 * filtre reçu. Il ne filtre rien, ne calcule rien et ne fait aucun appel réseau.
 */
public class SourceStub implements SourceBibliotheques {

    private final List<Bibliotheque> bibliotheques;

    private final SourceIndisponibleException panne;

    /**
     * Crée un stub qui renvoie toujours les bibliothèques indiquées.
     *
     * @param bibliotheques les bibliothèques à renvoyer
     */
    public SourceStub(List<Bibliotheque> bibliotheques) {
        this.bibliotheques = bibliotheques;
        this.panne = null;
    }

    /**
     * Crée un stub qui simule une source en panne : il lève toujours l'exception indiquée.
     *
     * @param panne l'exception à lever
     */
    public SourceStub(SourceIndisponibleException panne) {
        this.bibliotheques = null;
        this.panne = panne;
    }

    /**
     * Renvoie les bibliothèques fixées à la création, ou lève l'exception fixée à la création.
     *
     * @param filtre ignoré
     * @return une copie des bibliothèques fixées à la création
     * @throws SourceIndisponibleException si le stub a été créé pour simuler une panne
     */
    @Override
    public List<Bibliotheque> rechercher(String filtre) {
        if (panne != null) {
            throw panne;
        }
        return new ArrayList<>(bibliotheques);
    }
}
```

### Question 3

**Énoncé :** Écrivez la méthode `setUp` de `BibliothequeServiceTest`. Elle crée trois bibliothèques de Namur, inspirées des données réelles d'ODWB (simplifiées pour l'exercice), qui serviront dans la plupart des scénarios, puis un `BibliothequeService` qui utilise un […]

[`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...

    private Bibliotheque reseauNamurois;

    private Bibliotheque baieDesTecks;

    private Bibliotheque bomel;

    private BibliothequeService service;

    @BeforeEach
    void setUp() {
        reseauNamurois = new Bibliotheque("Réseau Namurois de Lecture publique", "Namur", "5000", "Namur",
                "réseau de bibliothèques", "Arts du spectacle, Bandes dessinées", "Fonds Cinéma, Mangas",
                50.4652, 4.8666);
        baieDesTecks = new Bibliotheque("Baie des tecks", "Namur", "5000", "Namur",
                "bibliothèque locale", null, null, 50.4728, 4.8628);
        bomel = new Bibliotheque("Bibliothèque libre de Bomel", "Namur", "5000", "Namur",
                "bibliothèque locale", "Fonds d'histoire locale", null, 50.4717, 4.8669);

        service = new BibliothequeService(new SourceStub(List.of(reseauNamurois, baieDesTecks, bomel)));
    }
    // ...
}
```

## Le plan de tests

### Question 4

**Énoncé :** Écrivez le scénario TC1 (le résultat est trié).

*TC1 — les bibliothèques renvoyées sont triées par dénomination* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class ParCodePostal {
        // ...

        @Test
        void testServiceTC1() {
            assertEquals(List.of(baieDesTecks, bomel, reseauNamurois), service.parCodePostal("5000"));
        }
        // ...
    }
}
```

### Question 5

**Énoncé :** Écrivez le scénario TC2 (aucune bibliothèque). Il faut pour cela un service qui utilise un autre stub que celui du `setUp`.

*TC2 — aucune bibliothèque pour ce code postal* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class ParCodePostal {
        // ...

        @Test
        void testServiceTC2() {
            service = new BibliothequeService(new SourceStub(new ArrayList<>()));

            assertTrue(service.parCodePostal("5000").isEmpty());
        }
        // ...
    }
}
```

### Question 6

**Énoncé :** Écrivez le scénario TC3 (la source est en panne). C'est le second constructeur de votre `SourceStub` qui sert ici.

**la source en panne**

C'est le scénario qui justifie le mieux le stub : avec la vraie API, impossible de provoquer
une panne au moment voulu. Le second constructeur de `SourceStub` reçoit l'exception à lever ;
le test vérifie qu'elle remonte jusqu'à l'appelant (`assertThrows`), puisque la javadoc de
`parCodePostal` annonce qu'elle n'est pas attrapée quand le code postal n'est pas en cache.

*TC3 — la panne de la source remonte jusqu'à l'appelant* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class SourceIndisponible {
        // ...

        @Test
        void testServiceTC3() {
            service = new BibliothequeService(new SourceStub(new SourceIndisponibleException("réseau coupé")));

            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("5000"));
        }
        // ...
    }
}
```

### Question 7

**Énoncé :** Écrivez le scénario TC4 (la bibliothèque la plus proche).

*TC4 — la plus proche d'un point situé à côté de Bomel* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class PlusProche {
        // ...

        @Test
        void testServiceTC4() {
            assertEquals(bomel, service.plusProche("5000", 50.4718, 4.8670));
        }
        // ...
    }
}
```

## 🤖 À partir d'ici, générez les tests avec l'IA

### Question 8

**Énoncé :** Écrivez le scénario TC5 (code postal invalide).

*TC5 — code postal invalide* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class ParCodePostal {
        // ...

        @Test
        void testServiceTC5() {
            assertThrows(IllegalArgumentException.class, () -> service.parCodePostal("50A0"));
        }
        // ...
    }
}
```

### Question 9

**Énoncé :** Écrivez le scénario TC6 (égalité de distance).

**égalité de distance**

Le piège : un test qui donnerait au stub l'« Annexe de Bomel » **avant** la bibliothèque libre
de Bomel passerait même si le service ne triait pas. Le stub doit donc la recevoir en second :
le test ne réussit alors que si le service trie la liste par dénomination **et** ne remplace
la bibliothèque retenue que par une strictement plus proche (`<` et non `<=`).

*TC6 — deux bibliothèques à égale distance : la première par ordre alphabétique* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class PlusProche {
        // ...

        @Test
        void testServiceTC6() {
            Bibliotheque jumelle = new Bibliotheque("Annexe de Bomel", "Namur", "5000", "Namur",
                    "bibliothèque locale", null, null, 50.4717, 4.8669);
            service = new BibliothequeService(new SourceStub(List.of(bomel, jumelle)));

            assertEquals(jumelle, service.plusProche("5000", 50.4718, 4.8670));
        }
        // ...
    }
}
```

### Question 10

**Énoncé :** Écrivez le scénario TC7 (aucune bibliothèque, donc aucune plus proche).

*TC7 — aucune bibliothèque pour ce code postal* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class PlusProche {
        // ...

        @Test
        void testServiceTC7() {
            service = new BibliothequeService(new SourceStub(new ArrayList<>()));

            assertNull(service.plusProche("5000", 50.4686, 4.8620));
        }
        // ...
    }
}
```

### Question 11

**Énoncé :** Écrivez le scénario TC8 (recherche par thématique, majuscules et minuscules ignorées).

*TC8 — la recherche ignore les majuscules et minuscules* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class ParThematique {
        // ...

        @Test
        void testServiceTC8() {
            assertEquals(List.of(reseauNamurois), service.parThematique("5000", "BANDES DESSINÉES"));
        }
        // ...
    }
}
```

### Question 12

**Énoncé :** Écrivez le scénario TC9 (aucune bibliothèque pour cette thématique).

*TC9 — aucun résultat* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class ParThematique {
        // ...

        @Test
        void testServiceTC9() {
            assertTrue(service.parThematique("5000", "astronomie").isEmpty());
        }
        // ...
    }
}
```

## Et le cache ?

### Question 13

**Énoncé :** Aucun des scénarios TC1 à TC9 ne vérifie ce cache. Que faudrait-il ajouter à `SourceStub` pour pouvoir écrire un test qui vérifie qu'un deuxième appel à `parCodePostal("5000")` n'interroge pas la source ? Pourquoi est-ce déjà un pas au-delà de ce qu'est un […]

**vérifier le cache**

Pour vérifier qu'un deuxième appel à `parCodePostal("5000")` n'interroge pas la source, le test doit pouvoir **compter les appels** : il faudrait ajouter à `SourceStub` un compteur, incrémenté dans `rechercher`, et une méthode pour le lire. Le test appellerait `parCodePostal("5000")` deux fois puis vérifierait que le compteur vaut 1.

Ce n'est plus vraiment un stub : un stub fournit des réponses pour qu'on puisse **vérifier l'état** de l'objet testé (ce qu'il renvoie). Ici, on veut **vérifier son comportement** : comment il a interagi avec sa dépendance, combien de fois, avec quels arguments. Un stub qui enregistre ses appels s'appelle un *spy* (voir la théorie) ; et vérifier les interactions est précisément le rôle des mock objects. Plutôt que d'écrire ces compteurs à la main pour chaque méthode et chaque argument, la séance 2 utilisera Mockito, qui les fournit tout faits (`verify`, `times`, `never`).

## Parties optionnelles

### Données incomplètes

#### Question 14

**Énoncé :** Écrivez un scénario TC10 qui vérifie qu'une recherche `parThematique("5000", "histoire")` sur les trois bibliothèques du `setUp` renvoie uniquement la bibliothèque libre de Bomel : la Baie des tecks, qui n'a ni thématique ni fonds, doit être simplement […]

**données incomplètes**

Ce n'est pas `BibliothequeService` qui évite de planter sur une bibliothèque sans thématique ni
fonds : c'est le constructeur de `Bibliotheque`, qui remplace un `null` par une chaîne vide. Le
test TC10 reste utile : il protège ce comportement et échouerait si cette garantie disparaissait
(par exemple si le constructeur conservait le `null`).

*TC10 — une bibliothèque sans thématique ni fonds est simplement ignorée* — [`test/domaine/BibliothequeServiceTest.java`](test/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class DonneesIncompletes {
        // ...

        @Test
        void testServiceTC10() {
            assertEquals(List.of(bomel), service.parThematique("5000", "histoire"));
        }
    }
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
