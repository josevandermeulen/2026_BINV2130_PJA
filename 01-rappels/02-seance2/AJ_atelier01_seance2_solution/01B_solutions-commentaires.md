# Atelier 1 – séance 2 : commentaires sur les solutions

Notes sur quelques questions de codage — le raisonnement et les pièges, pas une simple redite du code.

## Question 7 : historique des commandes passées

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

## Question 9 : validation des paramètres

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
