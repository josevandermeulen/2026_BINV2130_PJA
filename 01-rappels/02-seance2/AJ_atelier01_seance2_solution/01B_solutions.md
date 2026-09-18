# Atelier 1 : Rappel orienté objet – séance 2 : solutions

*Énoncé : [`01B_2_exercices.md`](../AJ_atelier01_seance2/01B_2_exercices.md) — théorie : [`01B_1_theorie.md`](../AJ_atelier01_seance2/01B_1_theorie.md).*

## La classe `LigneDeCommande`

### Question 1

**Énoncé :** Implémentez la classe `LigneDeCommande` selon le diagramme de classes ci-dessus. Le prix unitaire représente le prix unitaire de la pizza. Il est gardé car, comme les prix peuvent varier au cours du temps, il est important de garder le prix au moment de la […]

*la classe LigneDeCommande* — [`src/LigneDeCommande.java`](src/LigneDeCommande.java) :

```java
/**
 * Une ligne de commande : une pizza, la quantité commandée et le prix unitaire retenu au moment de
 * la commande.
 *
 * Le prix est figé à la création : une modification ultérieure du prix de la pizza ne change pas le
 * montant d'une commande déjà passée.
 */
public class LigneDeCommande {

    /**
     * la pizza commandée
     */
    private Pizza pizza;

    /**
     * la quantité de pizza commandée
     */
    private int quantite;

    /**
     * le prix unitaire de la pizza commandée
     */
    private double prixUnitaire;

    /**
     * Crée une ligne de commande pour la pizza passée en paramètre et dans la
     * quantité passée en paramètre.
     *
     * @param quantite la quantité de pizza commandée
     * @param pizza    la pizza commandée
     */
    public LigneDeCommande(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        setQuantite(quantite);

        this.pizza = pizza;
        this.prixUnitaire = pizza.calculerPrix();
    }

    /**
     * renvoie la pizza de la ligne de commande
     * @return la pizza
     */
    public Pizza getPizza() {
        return pizza;
    }

    /**
     * renvoie la quantité de la pizza de la ligne de commande
     *
     * @return la quantité commandée
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * met à jour la quantité
     *
     * @param quantite la nouvelle quantité, strictement positive
     */
    public void setQuantite(int quantite) {
        Util.checkStrictlyPositive(quantite);
        this.quantite = quantite;
    }

    /**
     * renvoie le prix unitaire de la pizza de la ligne de commande
     *
     * @return le prix unitaire de la pizza
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    /**
     * calcule le prix total de la ligne de commande en considérant les quantités
     * commandées.
     * @return le prix total de la ligne de commande
     */
    public double calculerPrixTotal() {
        return quantite * prixUnitaire;
    }

    /**
     * Convertit la ligne de commande sous forme de String.
     *
     * @return représentation textuelle de la ligne de commande
     */
    @Override
    public String toString() {
        return quantite + " " + pizza.getTitre() + "  à " + prixUnitaire;
    }

}
```

## Associations entre `Commande` et `Client`

### Question 2

**Énoncé :** Commencez par créer la classe `Commande`. Dans un premier temps, ne mettez que les attributs, les getters demandés et la méthode `iterator`.

*la classe Commande — déclaration et attributs* — [`src/Commande.java`](src/Commande.java) :

```java
/**
 * La commande d'un client, composée d'une ligne par pizza commandée.
 *
 * Un client n'a qu'une commande en cours à la fois : tant qu'elle n'est pas clôturée, aucune autre
 * ne peut être créée pour lui. Les méthodes de modification ne font donc rien si la commande n'est
 * plus celle en cours du client. Chaque commande reçoit un numéro unique, qui l'identifie.
 */
public class Commande implements Iterable<LigneDeCommande> {
    private static int numeroSuivant = 1;

    private int numero;

    private Client client;

    private LocalDateTime date;

    private ArrayList<LigneDeCommande> lignesCommandes = new ArrayList<>();
    // ...
}
```

*getters* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private int numero;
    private Client client;
    private LocalDateTime date;
    // ...
    /**
     * Renvoie le numéro identifiant la commande.
     *
     * @return le numéro de la commande
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Renvoie le client qui a passé la commande.
     *
     * @return le client de la commande
     */
    public Client getClient() {
        return client;
    }

    /**
     * Renvoie l'instant auquel la commande a été passée.
     *
     * @return la date et l'heure de la commande
     */
    public LocalDateTime getDate() {
        return date;
    }
    // ...
}
```

*iterator* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    // ...
    /**
     * Renvoie un itérateur sur les lignes de la commande.
     *
     * @return un itérateur sur les lignes de commande
     */
    @Override
    public Iterator<LigneDeCommande> iterator() {
        return lignesCommandes.iterator();
    }
    // ...
}
```

### Question 3

**Énoncé :** Complétez ensuite la classe `Client` en ajoutant ce qui est mis en bleu dans le diagramme de classes et en tenant compte des remarques suivantes :

*la classe Client — déclaration et attributs* — [`src/Client.java`](src/Client.java) :

```java
/**
 * Un client de la pizzeria, avec sa commande en cours et l'historique de ses commandes passées.
 *
 * Chaque client reçoit à sa création un numéro unique, attribué automatiquement par un compteur
 * partagé par toutes les instances ; c'est ce numéro, et lui seul, qui identifie un client. Il ne
 * peut avoir qu'une commande en cours à la fois : elle doit être clôturée avant qu'une autre puisse
 * être enregistrée. Le client est itérable sur ses commandes passées.
 */
public class Client implements Iterable<Commande> {
    private static int numeroSuivant = 1;

    private int numero;

    private String nom;

    private String prenom;

    private String telephone;

    private Commande commandeEnCours;
    // ...
}
```

*accès à la commande en cours* — [`src/Client.java`](src/Client.java) :

```java
public class Client implements Iterable<Commande> {
    private Commande commandeEnCours;
    // ...
    /**
     * Renvoie la commande que le client n'a pas encore clôturée.
     *
     * @return la commande en cours, ou null si le client n'en a aucune
     */
    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }
    // ...
}
```

*enregistrer et clôturer la commande en cours* — [`src/Client.java`](src/Client.java) :

```java
public class Client implements Iterable<Commande> {
    private Commande commandeEnCours;
    // ...
    /**
     * Enregistre une commande comme commande en cours du client.
     *
     * L'enregistrement échoue si le client a déjà une commande en cours, si la commande a été
     * passée par un autre client, ou si elle figure déjà dans l'historique de ses commandes.
     *
     * @param commande la commande à enregistrer
     * @return true si la commande est devenue la commande en cours
     * @throws IllegalArgumentException si la commande est null
     */
    public boolean enregistrer(Commande commande) {
        Util.checkObject(commande);
        if (this.commandeEnCours != null) {
            return false;
        }

        if (!commande.getClient().equals(this)) {
            return false;
        }

        // (Question 7) refuser l'enregistrement d'une commande déjà passée
        if (commandesPassees.contains(commande)) {
            return false;
        }
        this.commandeEnCours = commande;
        return true;
    }

    /**
     * Clôture la commande en cours et l'archive dans l'historique des commandes passées.
     *
     * @return true si une commande a été clôturée, false si le client n'en avait aucune en cours
     */
    public boolean cloturerCommandeEnCours() {
        if (this.commandeEnCours == null) {
            return false;
        }

        // (Question 7) archiver la commande clôturée dans l'historique
        this.commandesPassees.add(this.commandeEnCours);
        this.commandeEnCours = null;
        return true;
    }
    // ...
}
```

### Question 4

**Énoncé :** Ajoutez maintenant le constructeur de la classe `Commande` en tenant compte des remarques suivantes :

*le constructeur* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private static int numeroSuivant = 1;
    private int numero;
    private Client client;
    private LocalDateTime date;
    // ...
    /**
     * Crée une commande pour un client et l'enregistre comme sa commande en cours.
     *
     * @param client le client qui passe la commande
     * @throws IllegalArgumentException si le client est null, ou s'il a déjà une commande en cours
     */
    public Commande(Client client) {
        Util.checkObject(client);
        this.client = client;
        if (!client.enregistrer(this)) {
            throw new IllegalArgumentException(
                    "impossible de créer une commande pour un client ayant encore une commande en cours");
        }

        this.numero = numeroSuivant;
        numeroSuivant++;
        this.date = LocalDateTime.now();
    }
    // ...
}
```

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

## Suite de la classe `Commande`

### Question 5

**Énoncé :** Ajoutez les méthodes manquantes de la classe `Commande` en tenant compte des remarques suivantes :

*ajouter* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private Client client;
    // ...
    /**
     * Ajoute une quantité d'une pizza à la commande. Si la pizza y figure déjà, la quantité de sa
     * ligne est augmentée plutôt que de créer une seconde ligne.
     *
     * @param pizza    la pizza à commander
     * @param quantite le nombre d'exemplaires à ajouter, strictement positif
     * @return true si l'ajout a eu lieu, false si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null ou si la quantité n'est pas strictement
     *                                  positive
     */
    public boolean ajouter(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        Util.checkStrictlyPositive(quantite);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                l.setQuantite(l.getQuantite() + quantite);
                return true;
            }
        }
        return lignesCommandes.add(new LigneDeCommande(pizza, quantite));
    }

    /**
     * Ajoute un exemplaire d'une pizza à la commande.
     *
     * @param pizza la pizza à commander
     * @return true si l'ajout a eu lieu, false si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean ajouter(Pizza pizza) {
        return this.ajouter(pizza, 1);
    }
    // ...
}
```

*calculerMontantTotal/detailler* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    // ...
    /**
     * Calcule le montant de la commande, soit la somme du prix total de chacune de ses lignes.
     *
     * @return le montant total de la commande, en euros
     */
    public double calculerMontantTotal() {
        double total = 0;
        for (LigneDeCommande l : lignesCommandes) {
            total += l.calculerPrixTotal();
        }
        return total;
    }

    /**
     * Renvoie le détail des lignes de la commande, une par ligne de texte.
     *
     * @return le détail de la commande, ou une chaîne vide si la commande ne contient aucune ligne
     */
    public String detailler() {
        String lignes = "";
        for (LigneDeCommande l : lignesCommandes) {
            lignes += l.toString() + "\n";
        }
        if (lignes.equals("")) {
            return lignes;
        }
        return lignes.substring(0, lignes.length() - 1);
    }
    // ...
}
```

## Égalité structurelle sur les pizzas

### Question 6

**Énoncé :** Ajoutez, dans les classes adéquates, les méthodes nécessaires pour définir ces égalités structurelles.

*equals/hashCode* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private int numero;
    // ...
    /**
     * Compare deux commandes sur leur seul numéro.
     *
     * @param o l'objet à comparer à cette commande
     * @return true si o est une commande portant le même numéro
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        return numero == commande.numero;
    }

    /**
     * Renvoie un code de hachage calculé sur le numéro, cohérent avec equals(Object).
     *
     * @return le code de hachage de la commande
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
    // ...
}
```

*equals/hashCode* — [`src/Pizza.java`](src/Pizza.java) :

```java
public abstract class Pizza implements Iterable<Ingredient> {
    private String titre;
    // ...
    /**
     * Compare deux pizzas sur leur seul titre.
     *
     * @param o l'objet à comparer à cette pizza
     * @return true si o est une pizza portant le même titre
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pizza that = (Pizza) o;
        return titre.equals(that.titre);
    }

    /**
     * Renvoie un code de hachage calculé sur le titre, cohérent avec equals(Object).
     *
     * @return le code de hachage de la pizza
     */
    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }
    // ...
}
```

*equals/hashCode (titre + date)* — [`src/PizzaComposable.java`](src/PizzaComposable.java) :

```java
public class PizzaComposable extends Pizza {
    private LocalDateTime date;
    // ...
    /**
     * Compare deux pizzas composables sur leur titre et leur créateur.
     *
     * @param o l'objet à comparer à cette pizza
     * @return true si o est une pizza composable équivalente
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PizzaComposable that = (PizzaComposable) o;
        return date.equals(that.date);
    }

    /**
     * Renvoie un code de hachage cohérent avec equals(Object).
     *
     * @return le code de hachage de la pizza composable
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
```

## Parties optionnelles

### Historique des commandes d'un client

#### Question 7

**Énoncé :** Implémentez cet historique des commandes passées dans la classe `Client`.

**historique des commandes passées**

L'historique des commandes passées — `Client.java` :

```java
public class Client implements Iterable<Commande> {
    // ...
    private ArrayList<Commande> commandesPassees = new ArrayList<>();
    // ...
}
```

Le parcours de l'historique des commandes passées — `Client.java` :

```java
public class Client implements Iterable<Commande> {
    // ...
    @Override
    public Iterator<Commande> iterator() {
        return commandesPassees.iterator();
    }
    // ...
}
```

L'historique ajoute quatre éléments bleus à `Client` : l'attribut `commandesPassees` et la méthode `iterator` (extraits ci-dessus), plus deux modifications au cœur des méthodes de la Question 3.

Dans `enregistrer`, refuser une commande déjà archivée, pour ne pas ré-enregistrer une commande clôturée :

```java
public boolean enregistrer(Commande commande) {
    // ...
    if (commandesPassees.contains(commande)) {
        return false;
    }
    this.commandeEnCours = commande;
    return true;
}
```

Dans `cloturerCommandeEnCours`, archiver la commande courante avant de remettre `commandeEnCours` à `null` :

```java
public boolean cloturerCommandeEnCours() {
    // ...
    this.commandesPassees.add(this.commandeEnCours);
    this.commandeEnCours = null;
    return true;
}
```

Piège classique : oublier d'archiver avant de mettre `commandeEnCours` à `null`, ce qui perd la référence vers la commande clôturée.

### Possibilité de supprimer des pizzas d'une commande

#### Question 8

**Énoncé :** Implémentez les méthodes `retirer` et `supprimer` ci-dessus dans la classe `Commande`.

*retirer/supprimer* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private Client client;
    // ...
    /**
     * Retire une quantité d'une pizza de la commande. La ligne disparaît si la quantité retirée est
     * exactement celle commandée ; rien n'est retiré si elle est supérieure.
     *
     * @param pizza    la pizza concernée
     * @param quantite le nombre d'exemplaires à retirer, strictement positif
     * @return true si le retrait a eu lieu, false si la pizza n'est pas commandée en quantité
     *         suffisante ou si la commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null ou si la quantité n'est pas strictement
     *                                  positive
     */
    public boolean retirer(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        Util.checkStrictlyPositive(quantite);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                if (l.getQuantite() < quantite) {
                    return false;
                }
                if (l.getQuantite() == quantite) {
                    lignesCommandes.remove(l);
                } else {
                    l.setQuantite(l.getQuantite() - quantite);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retire un exemplaire d'une pizza de la commande.
     *
     * @param pizza la pizza concernée
     * @return true si le retrait a eu lieu
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean retirer(Pizza pizza) {
        return this.retirer(pizza, 1);
    }
    // ...
}
```

*supprimer* — [`src/Commande.java`](src/Commande.java) :

```java
public class Commande implements Iterable<LigneDeCommande> {
    private Client client;
    // ...
    /**
     * Retire de la commande la ligne d'une pizza, quelle que soit la quantité commandée.
     *
     * @param pizza la pizza à retirer de la commande
     * @return true si une ligne a été supprimée, false si la pizza n'était pas commandée ou si la
     *         commande n'est plus celle en cours du client
     * @throws IllegalArgumentException si la pizza est null
     */
    public boolean supprimer(Pizza pizza) {
        Util.checkObject(pizza);

        if (client.getCommandeEnCours() != this) {
            return false;
        }

        for (LigneDeCommande l : lignesCommandes) {
            if (l.getPizza().equals(pizza)) {
                //on peut supprimer directement car on arrête le parcours juste après la suppression
                lignesCommandes.remove(l);
                return true;
            }
        }
        return false;
    }
    // ...
}
```

### Test des paramètres des méthodes/constructeurs

#### Question 9

**Énoncé :** Ajoutez ces tests de validité des paramètres dans les méthodes et constructeurs concernés.

**validation des paramètres**

Le contrôle des paramètres est centralisé dans l'interface `Util` (`checkObject`, `checkString`, `checkStrictlyPositive`, …), qui lance une `IllegalArgumentException` sur une entrée invalide. On appelle ces méthodes **en tête** de chaque constructeur ou setter concerné, avant toute affectation :

- `Client` : `checkString` sur `nom`, `prenom`, `telephone` ;
- `Commande` : `checkObject` sur `client` ;
- `LigneDeCommande` : `checkObject` sur `pizza` (la quantité est validée via `setQuantite` → `checkStrictlyPositive`) ;
- `Pizza` : `checkString` sur `titre`/`description`, `checkObject` sur la liste d'ingrédients ;
- `Ingredient` : `checkString` sur `nom` (constructeur), `checkStrictlyPositive` sur le prix (`setPrix`) ;
- `PizzaComposable` : `checkObject` sur `createur`. Comme `super(...)` doit rester la première instruction et exploite déjà `createur`, la validation passe par une méthode privée `checkCreateur` qui contrôle puis retourne l'objet ;
- `Commande.ajouter`/`retirer` : `checkObject` sur la pizza, `checkStrictlyPositive` sur la quantité ; `Commande.supprimer` : `checkObject` sur la pizza.

Ces appels apparaissent dans le code des questions correspondantes (constructeurs et méthodes) ci-dessus.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
