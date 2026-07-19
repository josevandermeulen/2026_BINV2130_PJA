# Atelier 8 : Lecture de fichiers, try-with-resources – partie 1

## Objectif

L'objectif de cet atelier est de savoir lire et écrire des fichiers texte en Java, de comprendre la différence entre exceptions vérifiées et non vérifiées appliquée aux entrées/sorties, et d'utiliser le bloc `try-with-resources` pour garantir la fermeture des ressources.

## Concepts

1. Lecture de fichiers texte (`FileReader`, `BufferedReader`)
2. Écriture de fichiers texte (`FileWriter`, `BufferedWriter`)
3. `try-with-resources`
4. `AutoCloseable`
5. Exceptions vérifiées (`IOException`, `FileNotFoundException`)
6. Gestion d'erreurs de validation dans des données lues
7. Injection de dépendance (les tests fournis utilisent Mockito, atelier 5)

## Vidéos

1. [Apprendre Java #15 Fichier (File / Reader / Writer / Buffer)](https://www.youtube.com/watch?v=iCobhKfvf3M)
2. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Exercices

### Introduction

Une plateforme web enregistre chaque jour les accès de ses utilisateurs dans un fichier de logs (`2026-11-09.log`, `2026-11-10.log`, …), déposé dans un dossier `logs/`. Chaque ligne d'un fichier de logs décrit un accès : un horodatage, un nom d'utilisateur et une adresse IP, séparés par des points-virgules (`;`) :

```
2026-11-09T08:12:44;alice;192.168.0.12
```

Le dossier contient aussi un fichier d'index, `logs/index.txt`, qui liste les noms des fichiers de logs à traiter, un par ligne. Les données ne sont pas parfaitement propres : certaines lignes de logs sont incomplètes, et l'index peut mentionner un fichier qui n'existe pas (par exemple une journée dont les logs n'ont pas encore été déposés).

On souhaite écrire un programme qui charge tous les accès, calcule quelques statistiques (adresses IP uniques, nombre d'accès par utilisateur) et génère un rapport dans un nouveau fichier.

### Consignes

Veuillez créer un nouveau Projet Maven au sein d'IntelliJ nommé `AJ_atelier08_partie1` (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin). Récupérez les classes fournies dans `01-code-java/src/main/java/` (packages `domaine`, `util`, `main`) et les tests fournis dans `01-code-java/src/test/java/` en conservant l'arborescence. Ajoutez les dépendances JUnit 5 et Mockito à votre `pom.xml` (voir le `pom.xml` fourni). Copiez également le dossier `logs/` à la racine de votre projet (pas dans un package ni dans `src/`).

Chaque question est vérifiée par les tests JUnit fournis : exécutez-les directement dans IntelliJ (bouton ▶) pour vérifier votre implémentation au fur et à mesure. La classe [`Main`](01-code-java/src/main/java/main/Main.java) (package `main`) fournie permet en plus de comparer la sortie console à `affichage_Main.txt`.

### Lire le fichier d'index

**Question 1** :

✏️ *A corriger au tableau*

Ouvrez la classe [`LogReader`](01-code-java/src/main/java/util/LogReader.java) (package `util`). Elle implémente l'interface [`LecteurLogs`](01-code-java/src/main/java/util/LecteurLogs.java) (fournie). Complétez la méthode `lireIndex(String chemin)` afin qu'elle :

1. ouvre le fichier avec un `BufferedReader`, dans un bloc `try-with-resources` ;
2. lise le fichier ligne par ligne avec `readLine` ;
3. renvoie la liste des noms de fichiers lus, dans l'ordre du fichier.

Ne traitez pas l'`IOException` ici : la signature de la méthode la propage avec `throws`.

### Lire un fichier de logs

**Question 2** :
Complétez la méthode `lireLignes(String chemin)` de `LogReader` : ouvrez le fichier dans un bloc `try-with-resources`, mais récupérez cette fois toutes les lignes via la méthode `lines` du `BufferedReader`, qui renvoie un `Stream<String>` (à collecter dans une liste).

### Construire un accès

**Question 3** :
Ouvrez la classe [`Acces`](01-code-java/src/main/java/domaine/Acces.java) (package `domaine`) : complétez son constructeur pour valider les trois paramètres via [`Util`](01-code-java/src/main/java/util/Util.java) (aucun ne peut être vide ou `null`).

**Question 4** :
Ouvrez ensuite la classe [`AnalyseLogs`](01-code-java/src/main/java/main/AnalyseLogs.java). Elle reçoit un `LecteurLogs` par injection de dépendance (constructeur). Complétez la méthode `parserLigne(String ligne)` : découpez la ligne avec `split(";")`, vérifiez qu'elle contient exactement trois champs (sinon, levez une `IllegalArgumentException`), et construisez l'`Acces` correspondant.

### Ignorer les lignes invalides

**Question 5** :
Complétez la méthode `parserLignes(List<String> lignes)` : pour chaque ligne, appelez `parserLigne`. Une ligne invalide ne doit pas interrompre le traitement : attrapez l'`IllegalArgumentException`, affichez `"Ligne ignorée : " + ligne` sur la sortie standard, et continuez avec la ligne suivante.

### Combiner index et fichiers de logs

**Question 6** :
Complétez la méthode `chargerTousLesAcces(String dossier)` : lisez l'index (`dossier + "/index.txt"`), puis, pour chaque nom de fichier listé, lisez ses lignes (`dossier + "/" + nomFichier`) et parsez-les. La méthode renvoie la liste de tous les accès valides de tous les fichiers, dans l'ordre.

### Résister à un fichier manquant

**Question 7** :
L'index mentionne `2026-11-12.log`, qui n'existe pas dans le dossier `logs/`. Avec votre code actuel, le chargement s'interrompt avec une `FileNotFoundException`. Modifiez `chargerTousLesAcces` pour qu'un fichier introuvable n'interrompe pas le chargement : attrapez la `FileNotFoundException`, affichez `"Fichier introuvable : " + nomFichier`, et continuez avec le fichier suivant. Remarquez que les autres `IOException` (disque illisible, droits insuffisants, …) continuent, elles, à se propager.

### Statistiques

**Question 8** :
Complétez, en utilisant l'API Stream, la méthode `ipUniques(List<Acces>)` d'`AnalyseLogs` : l'ensemble (`Set<String>`) des adresses IP, sans doublon.

**Question 9** :
Complétez `nombreAccesParUtilisateur(List<Acces>)` : une `Map<String, Long>` associant chaque utilisateur à son nombre d'accès (utilisez `groupingBy` et `counting`).

### Écrire un rapport

**Question 10** :
Complétez la méthode `ecrireRapport(String chemin, List<Acces> acces)` : dans un bloc `try-with-resources` utilisant un `BufferedWriter`, écrivez une ligne par adresse IP unique, triées par ordre alphabétique, suivies d'une dernière ligne `"Nombre d'adresses IP uniques : "` suivie du nombre.

### Test

Exécutez `Main`. Il charge tous les accès du dossier `logs/`, affiche les lignes ignorées et le fichier introuvable, puis les statistiques, et écrit `rapport.txt` à la racine du projet. Comparez la sortie console obtenue à `affichage_Main.txt`.

### Passage à l'échelle

Avec 5 petits fichiers et 2 000 accès, difficile de savoir si votre code tiendrait la charge d'une vraie plateforme. Exécutez [`MainGrandeEchelle`](01-code-java/src/main/java/main/MainGrandeEchelle.java) (fourni) : au premier lancement, il génère 100 fichiers de 10 000 accès (1 000 000 au total) dans `logs-grand-volume/`, puis charge tous les accès, calcule les statistiques et écrit le rapport avec votre code de la partie 1 — en affichant le temps de chaque étape.

## Parties optionnelles

### Valider les adresses IP

**Question 11** :
Certaines lignes bien formées contiennent pourtant une adresse IP impossible, comme `300.0.0.1` (chaque nombre d'une adresse IPv4 est compris entre 0 et 255). Complétez :

1. `ipValide(String ip)` : renvoie `true` si la chaîne est une adresse IPv4 valide : quatre champs séparés par des points (attention, `split` attend une expression régulière : le point s'écrit `"\\."`), chacun étant un entier entre 0 et 255.
2. `accesIpInvalide(List<Acces>)` : la liste des accès dont l'adresse IP n'est pas valide, en utilisant l'API Stream.

### Repérer les adresses IP partagées

**Question 12** :
Une même adresse IP utilisée par plusieurs comptes peut révéler un poste partagé... ou un compte compromis. Complétez `utilisateursParIp(List<Acces>)` : une `Map<String, Set<String>>` associant chaque adresse IP à l'ensemble des utilisateurs qui l'ont utilisée, sans doublon. Utilisez `groupingBy` combiné à `Collectors.mapping(..., Collectors.toSet())`.

---

*Passez à la [théorie suivante](../02-partie2/08B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
