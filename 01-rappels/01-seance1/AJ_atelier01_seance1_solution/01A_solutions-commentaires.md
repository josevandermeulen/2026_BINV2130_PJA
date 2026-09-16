# Atelier 1 – séance 1 : commentaires sur les solutions

Notes sur quelques questions de codage — le raisonnement et les pièges, pas une simple redite du code.

## Question 2 : equals/hashCode

Ces deux méthodes n'ont pas à être écrites à la main : IntelliJ les génère automatiquement. Placez le curseur dans la classe, ouvrez le menu *Generate* (`Alt+Inser`, ou *Code > Generate*), choisissez *equals() and hashCode()*, puis sélectionnez le(s) champ(s) identifiant(s) — `numero` pour `Client`, `nom` pour `Ingredient`. IntelliJ produit le squelette classique (test `this == o`, test `getClass()`, cast, comparaison des champs retenus) qu'il suffit de relire.

Attention : ne cochez que les champs qui définissent l'identité de l'objet. Deux clients de même nom mais de numéros différents doivent rester distincts, donc seul `numero` entre dans `equals`/`hashCode`.

## Question 4 : invoquer une méthode de la classe parent

Avec le mot-clé `super` : `super.methode(...)` invoque la version de la classe parent d'une méthode redéfinie, et `super(...)` (première instruction d'un constructeur) invoque un constructeur du parent. La classe `PizzaComposee` utilise les deux : `super(titre, description, ingredients)` dans son constructeur, et `super.calculerPrix()` dans `calculerPrix` pour appliquer la remise au prix calculé par `Pizza`.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
