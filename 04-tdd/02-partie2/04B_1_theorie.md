# Atelier 4 – partie 2 : TDD et évolution du code – Concepts

## Table des matières

1. [Vidéos](#vidéos)
2. [Les tests comme filet de sécurité](#les-tests-comme-filet-de-sécurité)
3. [TDD lors de la mise à jour de fonctionnalités existantes](#tdd-lors-de-la-mise-à-jour-de-fonctionnalités-existantes)
4. [Quand créer de nouveaux tests ?](#quand-créer-de-nouveaux-tests-)
5. [TDD assisté par IA lors d'une évolution](#tdd-assisté-par-ia-lors-dune-évolution)
6. [En résumé](#en-résumé)

## Vidéos

1. [Conception de cas de test - cas positifs et négatifs](https://www.youtube.com/watch?v=_3N1241ywmY)
2. [Conception de cas de test - Cas Limites](https://www.youtube.com/watch?v=gZXVC1h3Im0)
3. [Conception de cas de test - Classes équivalences](https://www.youtube.com/watch?v=cd3eu5MWZu0)

## Les tests comme filet de sécurité

Le premier bénéfice du TDD ne se voit vraiment qu'au moment où le code doit **évoluer**. Quand une application est couverte par des tests écrits avant le code, chaque modification ultérieure peut être faite sereinement : si un changement casse un comportement existant, un test échoue immédiatement et pointe précisément ce qui est cassé. Les tests jouent le rôle de filet de sécurité (*safety net*).

Sans ce filet, chaque évolution demande de re-vérifier manuellement tous les comportements existants — ce que personne ne fait réellement, et c'est ainsi que naissent les régressions.

## TDD lors de la mise à jour de fonctionnalités existantes

Quand une exigence change (par exemple : une tâche n'est plus une simple `String` mais un objet avec un titre et une description), le cycle TDD s'applique de la même manière, mais il démarre des **tests existants** plutôt que d'une page blanche :

1. **Mettre à jour les tests d'abord.** Adaptez les scénarios de tests existants à la nouvelle exigence. Le code ne compile plus ou les tests échouent : c'est la phase *red*, et elle échoue pour de bonnes raisons.
2. **Mettre à jour le code minimum** pour faire passer tous les tests — les anciens adaptés comme les éventuels nouveaux. C'est la phase *green* : quand tout passe, vous avez la garantie que la nouvelle exigence est couverte **et** que rien d'existant n'est cassé.
3. **Refactorer** si nécessaire, toujours sous la protection des tests.

Le jeu consiste donc à faire passer tous les anciens tests avec le nouveau code. Si un ancien scénario n'a plus de sens avec la nouvelle exigence, c'est la spécification qui a changé : mettez à jour le scénario en conséquence, ne le supprimez pas silencieusement.

## Quand créer de nouveaux tests ?

Mettre à jour les tests existants ne suffit pas toujours. Créez de nouveaux scénarios quand la modification introduit :

- **de nouveaux comportements** — chaque nouvelle UC identifiée dans la spécification mérite ses scénarios (cas positifs, négatifs, limites) ;
- **de nouveaux cas limites** — le passage d'une `String` à un objet introduit par exemple la question de l'égalité entre deux objets distincts mais de même contenu (un « clone ») : un scénario doit le couvrir ;
- **de nouvelles règles de validation** — un constructeur qui refuse un titre vide doit être testé au même titre qu'une méthode.

## TDD assisté par IA lors d'une évolution

Le même risque que dans la partie 1 existe ici, avec une variante : quand vous demandez à une IA d'adapter un test existant à une nouvelle exigence, elle peut être tentée de mettre à jour le test **et** le code de production en même temps. Vous perdez alors la vérification essentielle de cette section — voir le test adapté échouer pour de bonnes raisons avant de toucher au code. Demandez d'abord uniquement la mise à jour du test, exécutez-le, puis seulement ensuite la mise à jour du code.

## En résumé

1. Les tests écrits en TDD sont un investissement : ils rendent chaque évolution ultérieure plus rapide et plus sûre.
2. Pour faire évoluer une fonctionnalité : adaptez d'abord les tests (red), puis le code (green), puis refactorez.
3. Complétez avec de nouveaux tests pour chaque nouveau comportement, cas limite ou règle de validation.

---

*Passez aux [exercices](04B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
