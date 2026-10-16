# Atelier 5 : Mocks – séance 2 : solutions

*Énoncé : [`05B_2_exercices.md`](../AJ_atelier05_seance2/05B_2_exercices.md) — théorie : [`05B_1_theorie.md`](../AJ_atelier05_seance2/05B_1_theorie.md).*

## Les mocks via Mockito

### Question 1

**Énoncé :** Écrivez la méthode `setUp`. Elle doit créer deux bibliothèques inspirées des données réelles d'ODWB (par exemple la Baie des tecks et la bibliothèque libre de Bomel, reprises de la séance 1), un mock de `SourceBibliotheques` qui renvoie ces deux […]

*setUp — service réel, source mockée* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...

    private static final String FILTRE_NAMUR = "cp=\"5000\"";

    private static final String FILTRE_BRUXELLES = "cp=\"1000\"";

    private Bibliotheque baieDesTecks;

    private Bibliotheque bomel;

    private SourceBibliotheques source;

    private BibliothequeService service;

    @BeforeEach
    void setUp() {
        baieDesTecks = new Bibliotheque("Baie des tecks", "Namur", "5000", "Namur",
                "bibliothèque locale", null, null, 50.4728, 4.8628);
        bomel = new Bibliotheque("Bibliothèque libre de Bomel", "Namur", "5000", "Namur",
                "bibliothèque locale", "Fonds d'histoire locale", null, 50.4717, 4.8669);

        source = Mockito.mock(SourceBibliotheques.class);
        Mockito.when(source.rechercher(FILTRE_NAMUR)).thenReturn(List.of(bomel, baieDesTecks));

        service = new BibliothequeService(source);
    }
    // ...
}
```

### Question 2

**Énoncé :** Écrivez le scénario TC1 (une recherche, un appel).

*TC1 — une première recherche interroge la source une fois, avec le bon filtre* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class AppelsALaSource {
        // ...

        @Test
        void testServiceTC1() {
            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
        // ...
    }
}
```

### Question 3

**Énoncé :** Écrivez le scénario TC2 (le cache évite un deuxième appel).

*TC2 — la même recherche deux fois : un seul appel à la source* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class Cache {
        // ...

        @Test
        void testServiceTC2() {
            service.parCodePostal("5000");

            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
        // ...
    }
}
```

### Question 4

**Énoncé :** Écrivez le scénario TC3 (une panne n'est pas mise en cache). Il faut configurer le mock pour qu'il lève une exception au premier appel, puis renvoie les bibliothèques au second : la théorie montre comment enchaîner deux réponses […]

*TC3 — une panne n'est pas mise en cache : la recherche suivante réinterroge la source* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class Panne {
        // ...

        @Test
        void testServiceTC3() {
            Mockito.when(source.rechercher(FILTRE_NAMUR))
                    .thenThrow(new SourceIndisponibleException("réseau coupé"))
                    .thenReturn(List.of(bomel, baieDesTecks));

            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("5000"));
            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(2)).rechercher(FILTRE_NAMUR);
        }
        // ...
    }
}
```

## 🤖 À partir d'ici, générez les tests avec l'IA

### Question 5

**Énoncé :** Écrivez le scénario TC4 (deux codes postaux, un appel chacun).

*TC4 — deux codes postaux différents : un appel pour chacun* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class AppelsALaSource {
        // ...

        @Test
        void testServiceTC4() {
            Mockito.when(source.rechercher(FILTRE_BRUXELLES)).thenReturn(List.of());

            service.parCodePostal("5000");
            service.parCodePostal("1000");

            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_BRUXELLES);
        }
        // ...
    }
}
```

### Question 6

**Énoncé :** Écrivez le scénario TC5 (code postal invalide, aucun appel).

*TC5 — code postal invalide : la source n'est jamais interrogée* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class AppelsALaSource {
        // ...

        @Test
        void testServiceTC5() {
            assertThrows(IllegalArgumentException.class, () -> service.parCodePostal("50A0"));
            Mockito.verify(source, Mockito.never()).rechercher(Mockito.anyString());
        }
        // ...
    }
}
```

### Question 7

**Énoncé :** Écrivez le scénario TC6 (le cache sert aussi à `plusProche` et à `parThematique`).

*TC6 — le cache est partagé par toutes les recherches d'un même code postal* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class Cache {
        // ...

        @Test
        void testServiceTC6() {
            service.parCodePostal("5000");
            service.plusProche("5000", 50.4718, 4.8670);
            service.parThematique("5000", "histoire");

            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
        // ...
    }
}
```

### Question 8

**Énoncé :** Écrivez le scénario TC7 (la panne d'une autre recherche ne vide pas le cache).

*TC7 — la panne d'une autre recherche ne vide pas le cache* — [`src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java`](src/test/java/be/vinci/mocks/domaine/BibliothequeServiceTest.java) :

```java
class BibliothequeServiceTest {
    // ...
    class Panne {
        // ...

        @Test
        void testServiceTC7() {
            Mockito.when(source.rechercher(FILTRE_BRUXELLES))
                    .thenThrow(new SourceIndisponibleException("réseau coupé"));

            service.parCodePostal("5000");
            assertThrows(SourceIndisponibleException.class, () -> service.parCodePostal("1000"));

            assertEquals(List.of(baieDesTecks, bomel), service.parCodePostal("5000"));
            Mockito.verify(source, Mockito.times(1)).rechercher(FILTRE_NAMUR);
        }
    }
}
```

## Parties optionnelles

### Questions d'examen

#### Question 9

**Énoncé :** (examen de janvier) : Tests unitaires de `CarService` (package `be.vinci.mocks.voiture`)

*examen janvier — TC1 et TC2, sans mock* — [`src/test/java/be/vinci/mocks/voiture/CarServiceTest.java`](src/test/java/be/vinci/mocks/voiture/CarServiceTest.java) :

```java
class CarServiceTest {
    // ...
    @Nested
    @DisplayName("Sans mock")
    class SansMock {

        // TC1 (sans mock) : marque null -> IllegalArgumentException
        @Test
        void testAddCarTC1() {
            CarService service = new CarService(new CarRepository());
            Car voitureSansMarque = new Car("Camry", null);
            assertThrows(IllegalArgumentException.class, () -> service.addCar(voitureSansMarque));
        }

        // TC2 (sans mock) : marque déjà existante -> succès, voiture présente dans le carService
        @Test
        void testAddCarTC2() {
            CarService service = new CarService(new CarRepository());
            Car voiture = new Car("Yaris", "Toyota");
            assertAll(() -> assertTrue(service.addCar(voiture)),
                      () -> assertTrue(service.getAllCars().contains(voiture))
            );
        }
    }
    // ...
}
```

*TC3 et TC4, avec mock du CarRepository (verify times/never)* — [`src/test/java/be/vinci/mocks/voiture/CarServiceTest.java`](src/test/java/be/vinci/mocks/voiture/CarServiceTest.java) :

```java
class CarServiceTest {
    // ...
    @Nested
    @DisplayName("Avec mock du CarRepository")
    class AvecMock {

        // TC3 (avec mock) : marque pas encore présente -> addBrand appelée une fois, addCar appelée une fois
        @Test
        void testAddCarTC3() {
            CarRepository repository = Mockito.mock(CarRepository.class);
            Mockito.when(repository.findAllCars()).thenReturn(List.of());
            Mockito.when(repository.getBrands()).thenReturn(List.of("Toyota"));
            CarService service = new CarService(repository);

            Car voiture = new Car("Model 3", "Tesla");
            service.addCar(voiture);
            assertAll(() -> Mockito.verify(repository, Mockito.times(1)).addBrand("Tesla"),
                      () -> Mockito.verify(repository, Mockito.times(1)).addCar(voiture)
            );
        }

        // TC4 (avec mock) : marque déjà présente -> addBrand jamais appelée, addCar appelée une fois
        @Test
        void testAddCarTC4() {
            CarRepository repository = Mockito.mock(CarRepository.class);
            Mockito.when(repository.findAllCars()).thenReturn(List.of());
            Mockito.when(repository.getBrands()).thenReturn(List.of("Toyota"));
            CarService service = new CarService(repository);

            Car voiture = new Car("Yaris", "Toyota");
            service.addCar(voiture);
            assertAll(() -> Mockito.verify(repository, Mockito.never()).addBrand(Mockito.any()),
                      () -> Mockito.verify(repository, Mockito.times(1)).addCar(voiture)
            );
        }
    }
}
```

#### Question 10

**Énoncé :** (examen de septembre) : Tests unitaires de `EquipmentManager` (package `be.vinci.mocks.club`)

*examen septembre — TC1 et TC2, sans mock* — [`src/test/java/be/vinci/mocks/club/EquipmentManagerTest.java`](src/test/java/be/vinci/mocks/club/EquipmentManagerTest.java) :

```java
class EquipmentManagerTest {
    // ...
    @Nested
    @DisplayName("Sans mock")
    class SansMock {

        // TC1 (sans mock) : nom de membre vide -> IllegalArgumentException
        @Test
        void testAddEquipmentTC1() {
            Equipment ballon = new Equipment("BALL01", "Ballon");
            EquipmentManager manager = new EquipmentManager(new ClubStock(List.of(ballon)));
            assertThrows(IllegalArgumentException.class, () -> manager.addEquipment("BALL01", ""));
        }

        // TC2 (sans mock) : équipement disponible -> succès, objet plus empruntable
        @Test
        void testAddEquipmentTC2() {
            Equipment ballon = new Equipment("BALL01", "Ballon");
            EquipmentManager manager = new EquipmentManager(new ClubStock(List.of(ballon)));
            assertAll(() -> assertTrue(manager.addEquipment("BALL01", "Alice")),
                      () -> assertTrue(ballon.isBorrowed())
            );
        }
    }
    // ...
}
```

*TC3 et TC4, avec mock du ClubStock (verify times/never)* — [`src/test/java/be/vinci/mocks/club/EquipmentManagerTest.java`](src/test/java/be/vinci/mocks/club/EquipmentManagerTest.java) :

```java
class EquipmentManagerTest {
    // ...
    @Nested
    @DisplayName("Avec mock du ClubStock")
    class AvecMock {

        // TC3 (avec mock) : équipement disponible -> markAsBorrowed appelée une fois avec les bons arguments
        @Test
        void testAddEquipmentTC3() {
            ClubStock stock = Mockito.mock(ClubStock.class);
            Equipment ballon = new Equipment("BALL01", "Ballon");
            Mockito.when(stock.findByCode("BALL01")).thenReturn(ballon);
            EquipmentManager manager = new EquipmentManager(stock);

            assertAll(() -> assertTrue(manager.addEquipment("BALL01", "Alice")),
                      () -> Mockito.verify(stock, Mockito.times(1)).markAsBorrowed(ballon, "Alice")
            );
        }

        // TC4 (avec mock) : équipement déjà emprunté -> échec, markAsBorrowed jamais appelée
        @Test
        void testAddEquipmentTC4() {
            ClubStock stock = Mockito.mock(ClubStock.class);
            Equipment ballon = new Equipment("BALL01", "Ballon");
            ballon.setBorrowed(true);
            Mockito.when(stock.findByCode("BALL01")).thenReturn(ballon);
            EquipmentManager manager = new EquipmentManager(stock);

            assertAll(() -> assertFalse(manager.addEquipment("BALL01", "Alice")),
                      () -> Mockito.verify(stock, Mockito.never()).markAsBorrowed(Mockito.any(), Mockito.any())
            );
        }
    }
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
