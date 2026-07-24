![BINV2130 — Programmation Java : avancé](assets/banner.png)

# BINV2130 — Programmation Java : avancé (2026)

Ressources du cours **BINV2130** (Haute École Léonard de Vinci) : théorie, exercices et solutions des ateliers Java hebdomadaires.

**Professeur :** José Vander Meulen

## Organisation

Classe inversée : la théorie de chaque chapitre se prépare en autonomie (fiches, vidéos, codes sources), puis est mise en pratique lors d'une séance d'atelier hebdomadaire de 4h en présentiel. Une solution des exercices est publiée en fin de semaine.

Le créneau de théorie en autonomie a lieu le **vendredi matin**, et prépare le chapitre de la **semaine suivante** — sauf à la rentrée, où il a lieu le lundi matin 14/09/2026 pour le chapitre de cette semaine-là. Le rythme d'une semaine est donc : préparation le vendredi matin, QCM sur mooVin le lundi à 20h, atelier en présentiel dans la foulée.

- ▶️ [Playlist YouTube](01-playlist-youtube.md)
- 📅 [Calendrier](00-calendrier.md)
- 📄 [Cheat sheets](cheat-sheets/)

## Chapitres

| # | Thème |
|---|-------|
| 01 | Rappels |
| 02 | Collections et énumérés |
| 03 | JUnit |
| 04 | TDD |
| 05 | Mocks |
| 06 | Streams |
| 07 | Programmation fonctionnelle |
| 08 | Lecture de fichiers |
| 09 | Threads et CompletableFuture |
| 10 | HyperLogLog et asynchrone |
| 11 | Introspection |
| 12 | Injection de dépendances |

Chaque chapitre contient la théorie (`*_1_theorie.md`), les exercices (`*_2_exercices.md`), le code de départ (`01-code-java`), la solution (`02-solution`) et le questionnaire d'auto-évaluation (`NN_quiz.md`).

![Structure d'un atelier](assets/structure-diagram.png)

## Récupérer les ressources

Le dépôt du cours se trouve à l'adresse [https://github.com/josevandermeulen/2026_BINV2130_PJA](https://github.com/josevandermeulen/2026_BINV2130_PJA).

Il évolue tout au long du quadrimestre : les solutions sont publiées semaine après semaine et les documents existants peuvent être corrigés. Plutôt que de retélécharger un ZIP à chaque fois, clonez-le une seule fois et mettez-le à jour au fil des semaines.

En ligne de commande :

```
git clone https://github.com/josevandermeulen/2026_BINV2130_PJA.git
git pull                                              # à refaire chaque semaine
```

Si vous n'êtes pas à l'aise avec Git en ligne de commande, utilisez [GitHub Desktop](https://desktop.github.com/) : *File > Clone repository > URL*, collez l'adresse ci-dessus, choisissez un dossier local et cliquez sur *Clone*. Ensuite, le bouton *Fetch origin* / *Pull origin* suffit pour récupérer les nouveautés.

**Attention : travaillez toujours dans vos propres projets IntelliJ (`AJ_atelierNN_partieX`), en dehors du dossier cloné. Si vous modifiez les fichiers du dépôt, la prochaine mise à jour entrera en conflit avec vos changements.**

## Lire et écrire du Markdown hors ligne

Tout le cours est écrit en Markdown (`.md`), et l'examen de janvier se déroule sur machine avec
ces seuls fichiers, **sans connexion**. Ce n'est pas GitHub qui rend le Markdown lisible, c'est
GitHub *en ligne* : le jour de l'examen, il faudra ouvrir les fichiers autrement. Autant prendre
l'habitude tout de suite.

- **IntelliJ IDEA** — le plus simple, puisque vous l'avez déjà. Ouvrez le `.md` : trois icônes en
  haut à droite de l'éditeur basculent entre *Éditeur*, *Éditeur + aperçu* et *Aperçu*. L'aperçu
  affiche les titres, les blocs de code, les tableaux et les cases à cocher, sans connexion.
- **Visual Studio Code** — `Ctrl+Shift+V` pour l'aperçu, `Ctrl+K` puis `V` pour l'afficher à côté
  du texte.
- **Bloc-notes ou Notepad++** — aucun rendu, vous voyez le texte source tel quel. C'est suffisant
  pour lire, et c'est de toute façon ce que vous éditez quand vous écrivez dans un `.md`.

Deux pièges :

- **N'utilisez pas Word.** Il enregistrera votre travail en `.docx`, qui n'est pas un fichier
  Markdown.
- **Le navigateur ne rend rien.** Ouvrir un `.md` dans Edge ou Chrome affiche le texte brut, ou
  télécharge le fichier.

## Évaluation

- **Examen (90 %)** : examen sur machine en janvier, portant sur des exercices pratiques de programmation. Il pourra également comporter des questions de théorie tirées de la banque décrite ci-dessous. Accès aux supports et à la Javadoc. IA générative interdite. Mêmes modalités en seconde session.
- **Évaluation continue (10 %)** : les QCM hebdomadaires sur mooVin, sans points négatifs, plus un QCM remis sous la forme d'un fichier Markdown complété (voir ci-dessous). La note est calculée sur les **11 meilleurs résultats des 13 QCM** ; un QCM non réalisé est coté 0. Cette note ne peut faire l'objet ni d'une seconde session ni d'une remédiation : celle du premier quadrimestre est reportée telle quelle en seconde session.

Un QCM de 20 questions par chapitre est proposé sur mooVin, portant sur la théorie de la semaine. Il **ferme le lundi à 20h**, sauf les semaines où ce lundi ne s'y prête pas (rentrée, jour férié, congé) : la date exacte de chaque QCM est dans la colonne *QCM* du [calendrier](00-calendrier.md). Le questionnaire est ensuite publié dans ce dépôt (`NN_quiz.md`, à la racine du chapitre) le jour même à partir de 20h15, avec les bonnes réponses et leur justification. Ces QCM forment aussi une **banque de questions** dans laquelle l'examen pourra puiser : les travailler au fil des semaines est directement utile pour l'examen.

Un **treizième QCM** s'ajoute aux douze, en semaine 8. Il porte sur la théorie et la pratique des chapitres déjà étudiés et se fait en autonomie. Il est publié avec la théorie et les exercices de la semaine, sans date de sortie particulière ; vous copiez le fichier de l'énoncé, le renommez `NOM_Prenom.md`, cochez une case par question et le déposez sur mooVin **pour le lundi 09/11/2026 à 20h**, en même temps que la fermeture du QCM mooVin de la semaine 8. Il compte comme les autres. Sa raison d'être : l'examen se déroule sur machine avec les seuls fichiers Markdown du cours, et rédiger ses réponses dans ce format est un geste qui vaut d'être répété avant janvier.

## Feedback

Votre retour sur le cours est le bienvenu. Trois canaux existent :

1. **Directement au professeur** — venez lui parler pendant ou après une séance, ou envoyez-lui un mail. C'est le canal le plus rapide et le plus efficace.
2. **Le conseil de département** — vos délégués y relaient les remarques de la classe. Il ne se réunit qu'une fois, au milieu du quadrimestre : utile pour les questions de fond, mais peu réactif.
3. **Le questionnaire en ligne** — [remplissez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7). Vous choisissez d'y répondre de manière anonyme ou non.

## Licence

Ce matériel est publié sous licence [CC BY-NC-SA 4.0](LICENSE) (Attribution, pas d'utilisation commerciale, partage dans les mêmes conditions).
