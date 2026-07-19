# Atelier 3 - partie 2 : JUnit – Concepts

Ce document reprend les notions utiles pour réaliser l'exercice. Il suppose acquises les notions de la partie 1 (`@Test`, `@BeforeEach`, assertions de base, `assertThrows`, tests paramétrés).

## Table des matières

1. [Vidéos](#vidéos)
2. [Tester `equals` et `hashCode`](#tester-equals-et-hashcode)
3. [Fixtures avec des dates](#fixtures-avec-des-dates)
4. [Tester des exceptions métier personnalisées](#tester-des-exceptions-métier-personnalisées)
5. [Plusieurs classes de test dans un même projet](#plusieurs-classes-de-test-dans-un-même-projet)
6. [Développer des tests avec l'IA](#développer-des-tests-avec-lia)
7. [Points d'attention pour l'exercice](#points-dattention-pour-lexercice)

## Vidéos

1. [Tests unitaires introduction](https://www.youtube.com/watch?v=UKUbHaxJjRw)
2. [JUnit premiers tests](https://www.youtube.com/watch?v=TkvuBBG-V9o)
3. [Les asserts en JUnit](https://www.youtube.com/watch?v=5swvHf57od8)
4. [Les tests paramétrés en JUnit](https://www.youtube.com/watch?v=fPKqt6oPMdY)
5. [Bonnes pratiques tests unitaires](https://www.youtube.com/watch?v=WwKmx9ghJHU)

## Tester `equals` et `hashCode`

Quand une classe redéfinit `equals`, on teste que deux instances différentes mais avec les mêmes valeurs sont bien considérées égales, et que des instances qui diffèrent par un seul attribut ne le sont pas :

```java
@Test
@DisplayName("Deux produits avec même nom, marque et rayon sont égaux")
void testEquals1() {
    Produit produit = new Produit("nom2", "marque2", "rayon2");
    assertEquals(produitAvecPrix, produit);
}

@Test
@DisplayName("Deux produits avec des noms différents ne sont pas égaux")
void testEquals2() {
    Produit produit = new Produit("nom", "marque2", "rayon2");
    assertNotEquals(produitAvecPrix, produit);
}
```

Quand `equals` est redéfini, `hashCode` doit l'être aussi (deux objets égaux doivent avoir le même `hashCode`) : c'est ce qu'on vérifie avec un test dédié.

```java
@Test
void testHashCode() {
    Produit produit = new Produit("nom2", "marque2", "rayon2");
    assertEquals(produitAvecPrix.hashCode(), produit.hashCode());
}
```

## Fixtures avec des dates

Quand une fixture a besoin de dates relatives à "maintenant" (par exemple l'historique de prix d'un produit), on les calcule une fois, en général comme constantes de la classe de test, pour que tous les tests utilisent exactement les mêmes valeurs :

```java
private static final LocalDate DATE_ANNEE_PASSEE = LocalDate.now().minusYears(1);
private static final LocalDate DATE_MOIS_PASSEE = LocalDate.now().minusMonths(1);
private static final LocalDate DATE_AUJOURDHUI = LocalDate.now();
```

Utiliser `LocalDate.now()` directement dans chaque test rendrait les dates légèrement différentes d'un test à l'autre (ou d'une exécution à l'autre) ; les calculer une seule fois évite ce problème.

## Tester des exceptions métier personnalisées

`assertThrows` fonctionne aussi bien avec les exceptions du JDK (`IllegalArgumentException`) qu'avec des exceptions métier définies dans le projet (par exemple `DateDejaPresenteException`, `PrixNonDisponibleException`) :

```java
@Test
void testAjouterPrixDateDejaPresente() {
    assertThrows(DateDejaPresenteException.class,
            () -> produitAvecPrix.ajouterPrix(DATE_MOIS_PASSEE, new Prix()));
}
```

## Plusieurs classes de test dans un même projet

Rien n'empêche d'avoir une classe de test par classe métier (`PrixTest`, `ProduitTest`, …) dans le même dossier `tests`, tant que chacune reste dans le package correspondant à la classe qu'elle teste (ici `domaine`). Chaque classe de test a sa propre fixture et son propre `@BeforeEach` : elles sont totalement indépendantes les unes des autres.

## Développer des tests avec l'IA

Comme vu en partie 1, un assistant IA peut aider à écrire des tests, mais chaque test généré doit être relu, exécuté, et vérifié en cassant volontairement le code testé (le test doit alors passer au rouge). Sur cette partie, deux pièges supplémentaires à surveiller :

- Le contrat `equals`/`hashCode` a deux moitiés : un test généré par IA peut ne couvrir que l'égalité et oublier `hashCode`, ou l'inverse. Vérifiez que les deux sont bien testées.
- La distinction égalité/identité (voir question 4 de l'exercice) est subtile : un test généré par IA peut réutiliser la même référence là où l'énoncé demande justement un objet égal (`equals`) mais de référence différente — ce test-là ne vérifierait pas la bonne chose.

## Points d'attention pour l'exercice

1. Une fixture peut réutiliser des objets construits pour une autre classe de test (ici, les `Prix` définis dans `PrixTest` servent aussi à construire un `Produit`) — mais chaque classe de test garde sa propre copie.
2. Attention à la contrainte métier : on ne peut pas ajouter deux prix à la même date pour un même produit.
3. Un test d'`equals` doit couvrir : l'égalité attendue, et chaque attribut qui, s'il diffère, doit rendre les objets différents.
4. Un test de `hashCode` vérifie la cohérence avec `equals`, pas une valeur de hash précise.

---

*Passez aux [exercices](03B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/03-junit-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
