# Atelier 8 – séance 2 : réponses aux questions d'observation

Ce document donne la réponse à la Question 11 de [`08B_2_exercices.md`](08B_2_exercices.md). Réfléchissez-y et testez dans votre code avant de lire la réponse.

## Question 11 : le type de retour covariant

Le compilateur l'accepte parce qu'une redéfinition peut **restreindre** son type de retour à un sous-type : c'est le type de retour covariant. Une `PizzaComposee` est une `Pizza`, si bien que le contrat de `Pizza.copier` (« je renvoie une `Pizza` ») reste respecté : celui qui appelle `copier` sur une variable de type `Pizza` reçoit bien une `Pizza`.

Pour l'appelant, cela évite le transtypage. Sur une variable de type `PizzaComposee`, `copier()` renvoie directement une `PizzaComposee` :

```java
PizzaComposee copie = MenuPizzeria.PIZZA_DIAVOLA.copier();
```

Si la redéfinition gardait `Pizza` comme type de retour, il faudrait écrire `(PizzaComposee) MenuPizzeria.PIZZA_DIAVOLA.copier()`, et le compilateur ne pourrait plus vérifier que la conversion est sûre.

Le changement inverse est refusé : une redéfinition qui annoncerait `Object` élargirait le type de retour, et celui qui attend une `Pizza` pourrait recevoir n'importe quel objet. Le compilateur signale alors un type de retour incompatible.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
