# Atelier 4 : remise d'une archive zip

Une remise cotée sur 20 points, qui porte sur la **structure de l'archive que vous déposez**, et non
sur le code Java qu'elle contient.

Cette remise **compte dans l'évaluation continue**, au même titre que les QCM : la note retient les
12 meilleurs résultats sur 15, pour 10 % de la note de l'unité d'enseignement. Une remise non faite
est cotée 0. Si vous déposez plusieurs fois, c'est la **dernière** version déposée qui est cotée.

## Pourquoi cet exercice

L'examen de janvier se déroule sur machine et se termine par le dépôt d'une archive zip contenant
votre code. Une archive mal formée à ce moment-là coûte cher : le correcteur ne retrouve pas vos
classes, et le temps de l'examen est écoulé. Le geste se répète donc ici, à froid, sur du code que
vous avez déjà écrit — il n'y a rien de neuf à programmer.

## Ce qu'on vous demande

Vous remettez le projet `AJ_atelier04_seance1` tel qu'il est à la fin de la séance 1, avec vos
réponses aux questions de [`04A_2_exercices.md`](04A_2_exercices.md).

### Structure exacte de l'archive

À l'ouverture du zip, on doit voir **directement** un dossier `src` et un dossier `tests` :

```
AJ_atelier04_seance1_NOM-Prenom.zip
├── src/
│   └── TodoList.java
└── tests/
    └── TodoListTest.java
```

Le piège est le dossier parent. Si vous faites un clic droit sur le dossier `AJ_atelier04_seance1`
pour le compresser, vous obtenez ceci, qui n'est **pas** conforme :

```
AJ_atelier04_seance1_NOM-Prenom.zip
└── AJ_atelier04_seance1/
    ├── src/
    └── tests/
```

Ce sont bien `src` et `tests` qui doivent se trouver à la racine de l'archive.

### Nom du fichier

`AJ_atelier04_seance1_NOM-Prenom.zip`, avec votre nom de famille en majuscules et votre prénom en
capitale initiale, séparés par un tiret : `AJ_atelier04_seance1_DUPONT-Marie.zip`. Pas d'espace, pas
d'accent, pas de caractère spécial dans le nom du fichier.

### Format

Une archive **`.zip`**. Ni `.rar`, ni `.7z`, ni `.tar.gz` : le correcteur ne les ouvrira pas.

## Comment créer l'archive

Le principe est le même partout : on sélectionne les dossiers `src` et `tests`, et on compresse
**la sélection**, pas le dossier qui les contient.

**Windows** — ouvrez le dossier `AJ_atelier04_seance1` dans l'Explorateur, sélectionnez `src` et
`tests` (clic sur le premier, `Ctrl` enfoncé, clic sur le second), puis clic droit → *Compresser
dans un fichier ZIP*. Renommez ensuite l'archive obtenue.

**macOS** — ouvrez le dossier dans le Finder, sélectionnez `src` et `tests`, clic droit →
*Compresser les 2 éléments*. Vous obtenez `Archive.zip`, à renommer.

**Linux** — dans un terminal, placez-vous dans le dossier du projet :

```bash
cd AJ_atelier04_seance1
zip -r AJ_atelier04_seance1_DUPONT-Marie.zip src tests
```

## Vérifiez avant de déposer

Rouvrez l'archive que vous venez de créer — sous Windows, un double clic suffit à l'explorer sans
la décompresser — et cochez :

- [ ] Le fichier porte l'extension `.zip`.
- [ ] Son nom est `AJ_atelier04_seance1_NOM-Prenom.zip`, avec votre nom et votre prénom.
- [ ] `src` et `tests` apparaissent dès l'ouverture, sans dossier intermédiaire.
- [ ] `src` contient `TodoList.java`.
- [ ] `tests` contient `TodoListTest.java`.
- [ ] Aucun dossier `out`, `.idea` ou `target` ne traîne dans l'archive.

## Barème

| Critère | Points |
|---|---|
| L'archive est un `.zip` valide, qui s'ouvre | 4 |
| `src` et `tests` sont à la racine de l'archive, sans dossier parent | 4 |
| `src/TodoList.java` est présent | 4 |
| `tests/TodoListTest.java` est présent | 4 |
| Le nom du fichier respecte le format demandé | 4 |

Le contenu des classes n'est pas coté ici : un `TodoList` incomplet ne vous fait perdre aucun point
sur cette remise. Vos réponses aux questions de la séance restent évaluées, elles, par la
correction au tableau et par la fiche de solutions de la semaine.

## Dépôt

Déposez votre archive sur mooVin, activité *Remise zip — atelier 4*, **pour le lundi 05/10/2026
à 20h**, en même temps que la fermeture du QCM mooVin de la semaine.

Une archive modèle, `AJ_atelier04_seance1_VANDERMEULEN-Jose.zip`, est publiée après l'échéance à la
racine de la semaine, à côté de la fiche de solutions : ouvrez-la pour comparer sa structure à celle
de votre propre remise.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
