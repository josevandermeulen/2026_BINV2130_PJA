# Atelier 8 : Lecture de fichiers, try-with-resources – partie 2

## Objectif

Cet atelier est un exercice récapitulatif : autour d'une implémentation simplifiée de l'algorithme **HyperLogLog** (dont le cœur vous est fourni — voir [`08B_1_theorie.md`](08B_1_theorie.md)), il combine les streams et collections (ateliers 2, 6 et 7), la lecture de fichiers de la partie 1, et les tests unitaires avec stubs et mocks (ateliers 3 et 5).

## Concepts

1. HyperLogLog (théorie : [`08B_1_theorie.md`](08B_1_theorie.md) — le cœur de l'algorithme est fourni)
2. Streams (`IntStream`, `map`, `distinct`, agrégation numérique)
3. Collections (`Map`, `Collectors.groupingBy`)
4. Réutilisation de la lecture de fichiers de la partie 1
5. Tests unitaires JUnit
6. Stubs et mocks Mockito (révision de l'atelier 5)
7. Injection de dépendance

## Vidéos

1. [Apprendre Java #15 Fichier (File / Reader / Writer / Buffer)](https://www.youtube.com/watch?v=iCobhKfvf3M)
2. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk)

## Exercices

### Introduction

La plateforme web de la partie 1 a grandi : ses fichiers de logs contiennent maintenant des milliers d'accès par jour. L'équipe sécurité veut suivre le nombre d'**adresses IP uniques** qui se connectent. Une solution évidente serait de stocker toutes les adresses déjà vues dans un `Set`, mais si le trafic grandissait fortement, cela finirait par consommer beaucoup de mémoire pour ne connaître, au final, qu'un seul nombre.

On va donc utiliser une version simplifiée de l'algorithme **HyperLogLog**, qui estime ce nombre à partir d'un petit tableau de compteurs (la précision est passée au constructeur ; l'atelier travaille partout avec 4 bits d'index, soit 16 compteurs), plutôt que de garder chaque adresse en mémoire. Lisez d'abord [`08B_1_theorie.md`](08B_1_theorie.md) : le principe (registres, zéros de tête, rôle du hash) y est expliqué, et les méthodes qui relèvent de manipulations de bits (`DefaultHasher.hash`, `HyperLogLog.ajouter`) vous sont **fournies déjà implémentées**. Votre travail porte uniquement sur la matière des ateliers précédents : streams, lecture de fichiers, tests.

L'estimation obtenue reste approximative, en particulier avec seulement 16 registres : ce n'est pas un défaut de votre code, c'est la nature même de l'algorithme.

### Consignes

Récupérez le dossier `01-code-java/` complet, renommez-le `AJ_atelier08_partie2` et ouvrez-le dans IntelliJ : c'est un projet Maven prêt à l'emploi, dont les dépendances JUnit 5 et Mockito sont **embarquées** dans son dossier `repo/` — il fonctionne donc aussi sans connexion internet (voir le [tutoriel Maven](../../05-mocks/02-partie2/05B_3_tutoriel-maven.md) si besoin).

Reprenez ensuite votre solution de la partie 1 (ou celle fournie dans [`../01-partie1/02-solution/`](../01-partie1/02-solution/)) : copiez les classes des packages `domaine`, `util` et `main` dans `src/main/java/`, à côté des nouvelles classes fournies ([`Hasher`](01-code-java/src/main/java/domaine/Hasher.java), [`DefaultHasher`](01-code-java/src/main/java/domaine/DefaultHasher.java), [`HyperLogLog`](01-code-java/src/main/java/domaine/HyperLogLog.java), [`AnalyseIp`](01-code-java/src/main/java/main/AnalyseIp.java), le nouveau [`Main`](01-code-java/src/main/java/main/Main.java)). Les tests sont déjà dans `src/test/java/`, et le dossier `logs/` (2 000 accès sur quatre journées) est déjà à la racine du projet.

Exécutez les tests directement dans IntelliJ (clic droit sur `src/test/java` → *Run 'All Tests'*) pour vérifier votre implémentation au fur et à mesure, et comparez la sortie de `Main` à `affichage_Main.txt`.

### Vérification de l'installation

✏️ *A corriger au tableau*

Avant de commencer, vérifiez que le dépôt Maven embarqué (`repo/`) fonctionne bien sans connexion internet : exécutez [`MockitoSmokeTest`](01-code-java/src/test/java/main/MockitoSmokeTest.java) (fourni, package `main` des tests) — il crée un mock avec Mockito et vérifie son comportement. S'il passe, JUnit 5 et Mockito sont correctement résolus depuis `repo/`, sans toucher au réseau.

### Estimer la cardinalité

**Question 1** :
Ouvrez la classe `HyperLogLog` (package `domaine`). Le constructeur et la méthode `ajouter` sont fournis. Complétez `estimerCardinalite`, qui applique la formule donnée dans la théorie à partir du tableau `registres` :

```
estimation = ALPHA * m² / Σ(2⁻ʳᵉᵍⁱˢᵗʳᵉ⁽ⁱ⁾)
```

`m` est le nombre de registres (`registres.length`) et `ALPHA` correspond à l'attribut `alpha`, initialisé par le constructeur fourni en fonction de la précision demandée. Utilisez l'API Stream (`IntStream.of(registres)`, `mapToDouble`, `sum`) pour calculer `Σ(2⁻ʳᵉᵍⁱˢᵗʳᵉ⁽ⁱ⁾)`, plutôt qu'une boucle classique.

### Compter les adresses IP uniques

**Question 2** :
Ouvrez la classe `AnalyseIp` et complétez, en utilisant l'API Stream, `compterIpUniques(List<Acces>)` : le nombre **exact** d'adresses IP distinctes (`map`, `distinct`, `count`).

**Question 3** :
Complétez `estimerIpUniques(List<Acces>, HyperLogLog)` : ajoute l'adresse IP de chaque accès au `HyperLogLog` reçu (`map`, `forEach`), puis renvoie l'estimation.

### Estimer jour par jour

L'équipe sécurité veut aussi suivre l'évolution du trafic : combien d'adresses IP uniques par jour, plutôt qu'un seul total agrégé.

**Question 4** :

✏️ *A corriger au tableau*

Complétez `estimerIpUniquesParJour(List<Acces>, Hasher)` dans `AnalyseIp` : elle doit renvoyer une `Map<String, Long>` associant chaque jour (au format `"2026-11-09.log"`, soit les 10 premiers caractères de l'horodatage suivis de `.log`) à son estimation HyperLogLog. Utilisez `Collectors.groupingBy` (avec `TreeMap::new` comme fournisseur de Map, pour un affichage trié chronologiquement) et, comme aval de regroupement, `Collectors.collectingAndThen(Collectors.toList(), ...)` pour transformer chaque groupe d'accès en une estimation (un nouveau `HyperLogLog` par jour).

### Test

Exécutez `Main` (fourni) : il charge les accès du dossier `logs/` grâce à votre `AnalyseLogs` de la partie 1, puis affiche le nombre d'accès valides, le nombre réel d'adresses IP uniques, l'estimation HyperLogLog globale et l'estimation jour par jour. Comparez la sortie console à `affichage_Main.txt` : ces valeurs (628 pour 580 adresses réelles au total, soit environ 8 % d'écart) sont déterministes, vous devez obtenir exactement les mêmes.

### Écrire un stub

Comme `HyperLogLog` dépend de `Hasher` pour hacher les valeurs, écrire un test unitaire déterministe est délicat avec l'implémentation réelle : on ne contrôle pas facilement quel registre sera modifié. Comme à l'atelier 5, on va donc écrire un **stub** : un faux `Hasher`, configuré pour toujours renvoyer une valeur fixe, peu importe la valeur reçue.

**Question 5** :
Complétez, dans `src/test/java/domaine/`, la classe [`HasherStub`](01-code-java/src/test/java/domaine/HasherStub.java) implémentant `Hasher` : un seul attribut (le hash fixe à renvoyer), initialisé dans le constructeur, et renvoyé tel quel par `hash(String)`.

### Tester `HyperLogLog`

**Question 6** :
Complétez `ajouterMetAJourLeBonRegistreAvecLeBonNombreDeZeros` dans [`HyperLogLogTest`](01-code-java/src/test/java/domaine/HyperLogLogTest.java) : créez un `HyperLogLog` à 4 bits d'index avec un `HasherStub` fixé à `0x12345678`, appelez `ajouter` une fois avec n'importe quelle valeur, puis vérifiez que le registre d'index `1` vaut `3` et qu'un autre registre (par exemple l'index `0`) est resté à `0`.

**Question 7** :
Complétez `ajouterNeDiminueJamaisUnRegistreDejaPlusGrand` : créez un `HyperLogLog` à 4 bits d'index avec un `HasherStub` fixé à `0x1FFFFFFF` (index `1`, nombre de zéros `1`), forcez directement `registres[1]` à `5`, appelez `ajouter`, et vérifiez que le registre est resté à `5`.

**Question 8** :
Complétez `estimationProcheDuNombreReelDeValeursDistinctes` : créez un `HyperLogLog` à 4 bits d'index avec un vrai `DefaultHasher`, ajoutez-y 500 valeurs distinctes (chacune répétée un nombre variable de fois, pour simuler des doublons), puis vérifiez que `estimerCardinalite` reste dans une marge de ±30 % du nombre réel de valeurs distinctes.

### Passage à l'échelle

Avec 5 petits fichiers et 2 000 accès, difficile de sentir l'intérêt réel de HyperLogLog. Exécutez [`MainGrandeEchelle`](01-code-java/src/main/java/main/MainGrandeEchelle.java) (fourni) : au premier lancement, il génère 100 fichiers de 10 000 accès (1 000 000 au total) dans `logs-grand-volume/`, puis compare, avec les mêmes classes que le `Main` précédent, le comptage exact (`Set<String>`, une entrée par IP unique) et l'estimation HyperLogLog — en affichant le temps de chaque étape.

Vous devriez observer un écart d'estimation du même ordre que celui de la théorie (environ 25 % avec 16 registres), quel que soit le volume : la précision dépend du nombre de registres, pas du nombre d'accès. Ce qui change radicalement, en revanche, c'est la mémoire : le `Set` retient une chaîne par adresse IP unique (près d'un million d'entrées ici), alors que HyperLogLog se contente de ses 16 registres (64 octets), qu'il y ait 2 000 ou 1 000 000 d'accès.

## Parties optionnelles

### Le stub remplacé par un mock Mockito

**Question 9** :
À l'atelier 5, vous avez vu que Mockito permet de créer un faux objet sans écrire de classe dédiée. Dans [`HyperLogLogMockitoTest`](01-code-java/src/test/java/domaine/HyperLogLogMockitoTest.java), réécrivez le test de la Question 6 en remplaçant le `HasherStub` par un mock Mockito (`mock(Hasher.class)`, `when(...).thenReturn(...)`), et vérifiez en plus, avec `verify`, que `hash` est appelé exactement une fois.

---

*Passez à la [théorie suivante](../../09-threads-completablefuture/01-partie1/09A_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
