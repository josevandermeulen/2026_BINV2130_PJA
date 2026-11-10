# Atelier 8 : comment le QCM en Markdown est corrigé

Cette page explique comment votre copie est notée, pour que vous sachiez ce qui compte et ce qui
ne compte pas. L'énoncé est dans
[`08_qcm-a-rendre.md`](01-seance1/AJ_atelier08_seance1/08_qcm-a-rendre.md), le corrigé
justifié dans
[`08_qcm-a-rendre-corrige.md`](01-seance1/AJ_atelier08_seance1/08_qcm-a-rendre-corrige.md).

## En deux étapes

1. **Une correction automatique.** Un script lit chaque copie et compare, question par question,
   la case cochée à la bonne réponse.
2. **Une relecture à la main.** Un enseignant relit ensuite chaque copie, sans exception, avant
   que les résultats soient publiés, pour écarter une erreur du script.

Seule la **dernière** version déposée sur mooVin est corrigée.

## Ce que le script vérifie

Chacune des 10 questions vaut **2 points**, pour un total de 20.

| Situation pour une question | Points |
|---|---|
| Une seule case cochée, et c'est la bonne | 2 |
| Une seule case cochée, mais pas la bonne | 0 |
| Aucune case cochée | 0 |
| Plusieurs cases cochées | 0 |

Pas de points négatifs : une réponse fausse ne retire rien.

Une case est cochée quand elle s'écrit `[x]` ou `[X]`, comme dans l'énoncé :

```markdown
- [ ] A) Une proposition que vous ne retenez pas
- [x] B) Celle que vous choisissez
```

Le script retrouve chaque question par son titre (`### Question 1`, `### Question 2`…) et
chaque proposition par sa lettre. Si vous avez supprimé ou renuméroté un titre, ou effacé la
lettre d'une proposition, il ne retrouve plus la question : elle est alors relue à la main.

Une copie qui n'est pas un fichier texte, par exemple un `.docx` enregistré depuis Word ou un
`.pdf`, ne peut pas être lue : elle vaut 0/20.

## Ce qui ne coûte rien

Le nom du fichier n'est pas évalué. Une ligne vide en plus ou en moins, une remarque ajoutée
sous une question, ou une copie en `.txt` plutôt qu'en `.md` ne retirent aucun point, tant que
les titres des questions et les cases restent lisibles.

## Les résultats

Les résultats sont publiés sur mooVin dans un fichier Excel : une ligne par matricule, avec la
note sur 20 et les points obtenus à chacune des 10 questions. Cette note compte dans
l'évaluation continue au même titre qu'un QCM mooVin.

Une erreur dans votre correction ? Signalez-la à votre enseignant de séance ou via le
[formulaire de remarques](https://forms.gle/UhpPjfS36XXmKS2F7), avec votre matricule.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
