# Atelier 11 – séance 2 : réponses aux questions d'observation

Ce document reprend les questions de réflexion (Questions 6 et 7) de la fiche d'exercices avec leur réponse. Réfléchissez et testez avant de le consulter.

## Question 6 : pourquoi `setAccessible(true)` ?

Les champs d'`Etudiant` sont `private` : sans `setAccessible(true)`, `field.get` lance une `IllegalAccessException` — la réflexion respecte par défaut les modificateurs d'accès. L'appel `setAccessible(true)` désactive ce contrôle pour ce `Field` et permet la lecture depuis l'extérieur de la classe.

Ce qu'il faut en déduire : l'encapsulation est une protection **à la compilation**, pas une barrière de sécurité à l'exécution. Un code qui a accès à la réflexion peut lire (et écrire) les champs privés de vos objets. C'est précisément ce qui permet aux frameworks (validation, JPA, sérialisation JSON, injection de dépendances...) de travailler avec vos classes sans exiger de getters publics — mais c'est aussi pourquoi `private` ne suffit jamais à protéger un secret.

## Question 7 : le lien avec `Util.checkObject` et Jakarta Bean Validation

Depuis le début du quadrimestre, vos constructeurs valident leurs paramètres **impérativement** : ils appellent `Util.checkObject(nom)`, `Util.checkString(matricule)`... au moment précis où l'objet est construit, et chaque règle est une ligne de code dans le constructeur.

Le validateur de cet atelier exprime les mêmes règles **déclarativement** : la contrainte est posée sur le champ (`@NonNul`, `@LongueurMin(valeur = 3)`), et c'est un mécanisme générique — le `Validateur`, via la réflexion — qui la fait respecter, pour n'importe quelle classe, sans que celle-ci contienne le moindre code de validation.

C'est exactement l'approche de **Jakarta Bean Validation**, le standard Java de validation : `@NotNull` correspond à notre `@NonNul`, `@Size(min = 3)` à notre `@LongueurMin`, `@Positive` à notre `@Positif` et `@Valid` à notre `@Valide`. Vous le retrouverez dans les frameworks web (Spring, Jakarta EE), où les objets reçus du client sont validés automatiquement à partir de ces annotations.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
