# Atelier 1 : Rappel orienté objet – séance 1 : solutions

*Énoncé : [`01A_2_exercices.md`](../AJ_atelier01_seance1/01A_2_exercices.md) — théorie : [`01A_1_theorie.md`](../AJ_atelier01_seance1/01A_1_theorie.md).*

## Les classes `Ingredient` et `Client`

### Question 1

**Énoncé :** Implémentez les classes `Ingredient` et `Client` conformément au diagramme de classes. Le numéro du client doit lui être attribué automatiquement par la classe (le premier créé recevant le numéro 1, le deuxième le numéro 2, …).

*la classe Client — numéro attribué automatiquement* — [`src/Client.java`](src/Client.java) :

```java
/**
 * Un client de la pizzeria.
 *
 * Chaque client reçoit à sa création un numéro unique, attribué automatiquement par un compteur
 * partagé par toutes les instances. C'est ce numéro, et lui seul, qui identifie un client : deux
 * homonymes restent deux clients distincts.
 */
public class Client {
    private static int numeroSuivant = 1;

    private int numero;

    private String nom;

    private String prenom;

    private String telephone;

    /**
     * Crée un client et lui attribue le numéro suivant.
     *
     * @param nom       le nom du client
     * @param prenom    le prénom du client
     * @param telephone le numéro de téléphone du client
     */
    public Client(String nom, String prenom, String telephone) {
        this.numero = numeroSuivant;
        numeroSuivant++;

        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }
    // ...
}
```

*la classe Ingredient* — [`src/Ingredient.java`](src/Ingredient.java) :

```java
/**
 * Un ingrédient pouvant composer une pizza, avec le prix qu'il ajoute à celle-ci.
 *
 * Deux ingrédients sont considérés comme identiques dès qu'ils portent le même nom : c'est ce qui
 * empêche une pizza de contenir deux fois le même ingrédient, quel qu'en soit le prix.
 */
public class Ingredient {
    private String nom;

    private double prix;

    /**
     * Crée un ingrédient.
     *
     * @param nom  le nom de l'ingrédient
     * @param prix le prix que l'ingrédient ajoute à la pizza, en euros
     */
    public Ingredient(String nom, double prix) {
        this.nom = nom;
        setPrix(prix);
    }

    /**
     * Renvoie le nom de l'ingrédient.
     *
     * @return le nom de l'ingrédient
     */
    public String getNom() {
        return nom;
    }

    /**
     * Renvoie le prix de l'ingrédient.
     *
     * @return le prix de l'ingrédient, en euros
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Modifie le prix de l'ingrédient.
     *
     * @param prix le nouveau prix, en euros
     */
    public void setPrix(double prix) {
        this.prix = prix;
    }
    // ...
}
```

## Égalités référentielles et structurelles

### Question 2

**Énoncé :** Complétez votre implémentation des classes `Ingredient` et `Client` pour faire en sorte qu'un ingrédient soit identifié par son nom et un client par son numéro. Pour cela, redéfinissez les méthodes `equals` et `hashCode` dans les deux classes.

**equals/hashCode**

Ces deux méthodes n'ont pas à être écrites à la main : IntelliJ les génère automatiquement. Placez le curseur dans la classe, ouvrez le menu *Generate* (`Alt+Inser`, ou *Code > Generate*), choisissez *equals() and hashCode()*, puis sélectionnez le(s) champ(s) identifiant(s) — `numero` pour `Client`, `nom` pour `Ingredient`. IntelliJ produit le squelette classique (test `this == o`, test `getClass()`, cast, comparaison des champs retenus) qu'il suffit de relire.

Attention : ne cochez que les champs qui définissent l'identité de l'objet. Deux clients de même nom mais de numéros différents doivent rester distincts, donc seul `numero` entre dans `equals`/`hashCode`.

*equals/hashCode* — [`src/Client.java`](src/Client.java) :

```java
public class Client {
    private int numero;
    // ...
    /**
     * Compare deux clients sur leur seul numéro.
     *
     * @param o l'objet à comparer à ce client
     * @return true si o est un client portant le même numéro
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return numero == client.numero;
    }

    /**
     * Renvoie un code de hachage calculé sur le numéro, cohérent avec equals(Object).
     *
     * @return le code de hachage du client
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
```

*equals/hashCode* — [`src/Ingredient.java`](src/Ingredient.java) :

```java
public class Ingredient {
    private String nom;
    // ...
    /**
     * Compare deux ingrédients sur leur seul nom.
     *
     * @param o l'objet à comparer à cet ingrédient
     * @return true si o est un ingrédient portant le même nom
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return nom.equals(that.nom);
    }

    /**
     * Renvoie un code de hachage calculé sur le nom, cohérent avec equals(Object).
     *
     * @return le code de hachage de l'ingrédient
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
```

## La classe `Pizza`

### Question 3

**Énoncé :** Implémentez la classe `Pizza` en tenant compte des remarques suivantes :

*la classe abstraite Pizza — constructeurs (le second invoque le premier)* — [`src/Pizza.java`](src/Pizza.java) :

```java
/**
 * Une pizza, c'est-à-dire un titre, une description et les ingrédients qui la composent.
 *
 * La classe est abstraite : elle porte le comportement commun aux pizzas de la carte
 * (PizzaComposee) et à celles que le client compose lui-même (PizzaComposable).
 * Elle est itérable, de sorte qu'une boucle `for` parcoure directement ses ingrédients.
 */
public abstract class Pizza implements Iterable<Ingredient> {
    /**
     * Le prix de départ d'une pizza, en euros, avant l'ajout du moindre ingrédient.
     */
    public static final double PRIX_BASE = 5;

    private String titre;

    private String description;

    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    /**
     * Crée une pizza sans aucun ingrédient.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     */
    public Pizza(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }

    /**
     * Crée une pizza garnie des ingrédients donnés.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     * @param ingredients les ingrédients composant la pizza, sans doublon
     * @throws IllegalArgumentException si le même ingrédient figure deux fois dans la liste
     */
    public Pizza(String titre, String description, ArrayList<Ingredient> ingredients) {
        this(titre, description);

        for (Ingredient i : ingredients) {
            if (this.ingredients.contains(i)) {
                throw new IllegalArgumentException(
                        "Il ne peut pas y avoir deux fois le même ingrédient dans une pizza.");
            }
            this.ingredients.add(i);
        }
    }
    // ...
}
```

*itérateur, ajouter (sans doublon), supprimer et calcul du prix* — [`src/Pizza.java`](src/Pizza.java) :

```java
public abstract class Pizza implements Iterable<Ingredient> {
    public static final double PRIX_BASE = 5;
    // ...
    /**
     * Renvoie un itérateur sur les ingrédients de la pizza.
     *
     * @return un itérateur sur les ingrédients
     */
    @Override
    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    /**
     * Ajoute un ingrédient à la pizza, sauf s'il s'y trouve déjà.
     *
     * @param ingredient l'ingrédient à ajouter
     * @return true si l'ingrédient a été ajouté, false s'il était déjà présent
     */
    public boolean ajouter(Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            return false;
        }
        return ingredients.add(ingredient);
    }

    /**
     * Retire un ingrédient de la pizza.
     *
     * @param ingredient l'ingrédient à retirer
     * @return true si l'ingrédient a été retiré, false s'il n'était pas présent
     */
    public boolean supprimer(Ingredient ingredient) {
        return ingredients.remove(ingredient);
    }

    /**
     * Calcule le prix de la pizza : le prix de base augmenté du prix de chaque ingrédient.
     *
     * @return le prix de la pizza, en euros
     */
    public double calculerPrix() {
        double prix = PRIX_BASE;
        for (Ingredient i : ingredients) {
            prix += i.getPrix();
        }
        return prix;
    }
    // ...
}
```

## La sous-classe `PizzaComposee`

### Question 4

**Énoncé :** en java, comment fait-on pour invoquer une méthode de la classe parent dans une sous-classe ?

**invoquer une méthode de la classe parent**

Avec le mot-clé `super` : `super.methode(...)` invoque la version de la classe parent d'une méthode redéfinie, et `super(...)` (première instruction d'un constructeur) invoque un constructeur du parent. La classe `PizzaComposee` utilise les deux : `super(titre, description, ingredients)` dans son constructeur, et `super.calculerPrix()` dans `calculerPrix` pour appliquer la remise au prix calculé par `Pizza`.

*la sous-classe PizzaComposee* — [`src/PizzaComposee.java`](src/PizzaComposee.java) :

```java
/**
 * Une pizza de la carte, dont la composition est fixée par la pizzeria.
 *
 * Ses ingrédients sont donnés à la construction et ne peuvent plus changer : ajouter et
 * supprimer lèvent une exception plutôt que de modifier la recette. En contrepartie de
 * cette composition imposée, son prix bénéficie d'une remise.
 */
public class PizzaComposee extends Pizza {

    private static final int REMISE = 15;

    /**
     * Crée une pizza de la carte.
     *
     * @param titre       le titre de la pizza
     * @param description la description de la pizza
     * @param ingredients les ingrédients composant la pizza, sans doublon
     * @throws IllegalArgumentException si le même ingrédient figure deux fois dans la liste
     */
    public PizzaComposee(String titre, String description, ArrayList<Ingredient> ingredients) {
        super(titre, description, ingredients);
    }

    /**
     * Calcule le prix de la pizza, remise appliquée puis arrondi à l'euro supérieur.
     *
     * @return le prix remisé de la pizza, en euros
     */
    @Override
    public double calculerPrix() {
        return Math.ceil(super.calculerPrix() * (1 - REMISE / 100.0));
    }

    /**
     * Refuse l'ajout d'un ingrédient : la composition d'une pizza de la carte est figée.
     *
     * @param ingredient l'ingrédient dont l'ajout est demandé
     * @return jamais : la méthode lève toujours une exception
     * @throws UnsupportedOperationException systématiquement
     */
    @Override
    public boolean ajouter(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }

    /**
     * Refuse le retrait d'un ingrédient : la composition d'une pizza de la carte est figée.
     *
     * @param ingredient l'ingrédient dont le retrait est demandé
     * @return jamais : la méthode lève toujours une exception
     * @throws UnsupportedOperationException systématiquement
     */
    @Override
    public boolean supprimer(Ingredient ingredient) {
        throw new UnsupportedOperationException("Les ingrédients d'une pizza composée ne peuvent pas être modifiés");
    }
}
```

## La sous-classe `PizzaComposable`

### Question 5

**Énoncé :** Implémentez la classe `PizzaComposable` en tenant compte des remarques suivantes :

*la sous-classe PizzaComposable* — [`src/PizzaComposable.java`](src/PizzaComposable.java) :

```java
/**
 * Une pizza composée par un client, qui choisit lui-même ses ingrédients.
 *
 * Contrairement à une PizzaComposee, elle part sans aucun ingrédient et reste modifiable.
 * Elle retient son créateur et l'instant de sa création, tous deux repris dans son titre et sa
 * description.
 */
public class PizzaComposable extends Pizza {
    private LocalDateTime date;

    private Client createur;

    /**
     * Crée une pizza composable vide, au nom de son créateur et datée de l'instant présent.
     *
     * @param createur le client qui compose la pizza
     */
    public PizzaComposable(Client createur) {
        super("Pizza composable du client " + createur.getNumero(),
                "Pizza de " + createur.getNom() + " " + createur.getPrenom());

        this.date = LocalDateTime.now();
        this.createur = createur;
    }

    /**
     * Renvoie l'instant de création de la pizza.
     *
     * @return la date et l'heure de création
     */
    public LocalDateTime getDate() {
        return date;
    }
    // ...
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
