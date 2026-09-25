# Atelier 2 – séance 2 : réponses aux questions d'observation

Ce document reprend les questions de réflexion posées au fil de la séance 2
(Questions 3, 4, 6, 8, 9, 13 et 17). Réfléchissez (et testez !) avant de les
consulter.

## Question 3 : quelle structure de données pour le livre ?

L'introduction donne deux contraintes. D'abord « les recettes seront triées par ordre de difficulté » : on parle de tri, il faut donc retenir un ordre. Une simple liste conserverait l'ordre d'insertion, mais elle accepte les doublons — or un livre de recettes n'en a pas. Un `Set` interdit les doublons mais n'est pas trié. La bonne base est donc un `SortedSet` : un ensemble trié, sans doublons.

Ensuite « pouvoir récupérer facilement tous les plats d'un certain type » : un unique `SortedSet` ne permet pas de retrouver rapidement tous les plats d'un type donné. C'est là qu'une `Map` aide : une `Map<Type, SortedSet<Plat>>`, dont la clé est le type de plat et la valeur l'ensemble trié des plats de ce type. Avec 3 types, la `Map` contient 3 `SortedSet<Plat>`.

## Question 4 : pourquoi une Map plutôt que 3 attributs SortedSet ?

Avec trois attributs `SortedSet<Plat>` (un par type), chaque méthode du livre (`ajouterPlat`, `supprimerPlat`, `toString`, …) devrait dupliquer sa logique trois fois — ou enchaîner des `if`/`switch` sur le type. Et si un quatrième type de plat apparaît un jour (une soupe ? un amuse-bouche ?), il faudrait ajouter un attribut **et** modifier toutes ces méthodes. Avec la `Map<Plat.Type, SortedSet<Plat>>`, le type est une simple clé : les méthodes s'écrivent une seule fois (`plats.get(type)`), et un nouveau type de plat ne demande aucun changement dans `Livre`.

## Question 6 : Comparable ou Comparator ?

Ici, le `Comparator` est préférable. Si on laissait `Plat` implémenter `Comparable`, la classe `Plat` déciderait une fois pour toutes de son ordre naturel (par difficulté). Mais imaginons un second type de livre trié par coût : on ne pourrait plus, car `compareTo` est unique par classe. De plus, changer ce `compareTo` influencerait toutes les structures qui reposent sur l'ordre naturel de `Plat`. En donnant un `Comparator` au `TreeSet`, c'est la classe `Livre` qui porte la responsabilité de l'ordre : on peut définir plusieurs ordres différents sans toucher à `Plat`, et personne ne risque de casser ce tri en modifiant `Plat`.

## Question 8 : que va afficher le bout de code ?

Le code ajoute deux entrées (« Waterzooi » n'est pas une entrée : il finit dans `PLAT` ; « Croquettes au fromage » est une `ENTREE`), puis tente de supprimer un plat qui n'a jamais été ajouté (« Toasts aux champignons »). On s'attend à ce que ce `supprimerPlat` ne fasse rien. Or, avec un `compare` qui ne trie que sur la difficulté, « Toasts aux champignons » (difficulté `XXX`, type `ENTREE`) est vu comme **identique** aux « Croquettes au fromage » (même difficulté `XXX`, même type `ENTREE`) : les croquettes disparaissent alors du second affichage, ce qui surprend.

## Question 9 : pourquoi ce résultat ?

Dans un `TreeSet`, ce n'est pas `equals` qui décide de l'identité de deux éléments, mais la méthode de comparaison : si `compare(a, b)` renvoie 0, les deux éléments sont considérés comme égaux (pour l'ajout, mais aussi pour `contains` et `remove`). En ne triant que sur la difficulté, deux plats de même difficulté sont déclarés égaux, donc `supprimerPlat` retire le mauvais plat. La correction consiste à départager les plats distincts : trier d'abord par difficulté, puis par nom. Deux plats ne sont alors identiques que s'ils ont la même difficulté **et** le même nom.

## Question 13 : pourquoi les deux affichages == sont-ils différents ?

Un `Integer` est un objet (wrapper autour d'un `int`). Écrire `Integer a = 127;` déclenche de l'autoboxing : Java appelle en réalité `Integer.valueOf(127)`. Or `Integer.valueOf` garde en cache les instances pour les valeurs entre -128 et 127 et renvoie toujours la même instance pour une même valeur dans cet intervalle. C'est pour ça que `a == b` est vrai : `a` et `b` pointent vers le même objet en cache.

En dehors de cet intervalle (comme 200), `valueOf` crée une nouvelle instance à chaque appel. `c` et `d` sont donc deux objets distincts, et `==` compare des références différentes : le résultat est `false`.

Conclusion : ne jamais comparer des wrappers (`Integer`, `Long`, …) avec `==`. Il faut utiliser `equals` (ou `.intValue()` pour comparer les valeurs primitives).

## Question 17 : sans redéfinition de clone dans Plat, qu'aurait affiché le test ?

`Object.clone` fait une copie superficielle : il copie la valeur de chaque champ, y compris les références. La `List<Instruction>` et le `Set<IngredientQuantifie>` de la copie pointeraient donc vers exactement les **mêmes** objets que l'original. Ajouter une instruction ou modifier un ingrédient sur la copie aurait donc aussi modifié l'original — silencieusement, puisqu'aucune exception ne se produit. C'est le piège classique du clone superficiel appliqué à des champs mutables.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
