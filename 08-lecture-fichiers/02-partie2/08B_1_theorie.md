# Atelier 8 : HyperLogLog – Concepts

Les exercices associés à ces concepts se trouvent dans `08B_2_exercices.md`. Cette fiche reprend le contenu de la vidéo ci-dessous ; regardez-la d'abord, elle explique le principe en images en 8 minutes.

## Table des matières

1. [Vidéos](#vidéos)
2. [Le problème : compter des vues uniques](#le-problème--compter-des-vues-uniques)
3. [L'idée : la rareté d'un hash](#lidée--la-rareté-dun-hash)
4. [Les buckets](#les-buckets)
5. [L'estimation : moyenne harmonique](#lestimation--moyenne-harmonique)
6. [Précision et mémoire figée](#précision-et-mémoire-figée)
7. [L'importance du hash](#limportance-du-hash)
8. [Fusionner des compteurs](#fusionner-des-compteurs)
9. [Petit résumé](#petit-résumé)

## Vidéos

1. [Comment compter des millions de vues ? HyperLogLog](https://www.youtube.com/watch?v=OWBT86qoEqk) (Grafikart — l'[article compagnon](https://grafikart.fr/tutoriels/hyperloglog-2345) reprend le contenu par écrit)

## Le problème : compter des vues uniques

Mise en situation : vous êtes ingénieur chez YouTube, et on vous demande de compter le nombre de vues sous chaque vidéo. On vous envoie des identifiants — pour l'exemple, des adresses IP — et votre objectif est de dire combien d'adresses IP **différentes** vous avez rencontrées.

Premier réflexe : sauvegarder l'ensemble des adresses IP déjà vues (un tableau, un `Set`), et renvoyer leur nombre. C'est exact, mais ça ne tient pas la charge : même si une IPv4 ne pèse que 4 octets, une vidéo à 10 millions de vues demande déjà 40 Mo... juste pour afficher un compteur. Et la mémoire continue de grandir avec le trafic.

Deuxième réflexe : un simple compteur qu'on incrémente à chaque visite. Mémoire minimale, mais faux : une personne qui revient avec la même IP est comptée plusieurs fois.

Il faut donc une solution qui ignore les doublons **sans** mémoriser tout ce qu'elle a vu. C'est là qu'intervient **HyperLogLog**, un algorithme *probabiliste* : il ne compte pas exactement, il **estime** — en échange d'une occupation mémoire minuscule et fixe.

## L'idée : la rareté d'un hash

HyperLogLog se base sur la probabilité d'obtenir un élément rare.

On commence par calculer un **hash** de chaque identifiant : un nombre dont les bits doivent être répartis de manière **uniforme** (autant de chances de tomber n'importe où dans la plage de valeurs), et rapide à calculer. On regarde alors les zéros au début de sa représentation binaire :

- une chance sur 2 d'avoir un zéro initial ;
- une chance sur 4 d'avoir deux zéros d'affilée ;
- une chance sur 8 d'en avoir trois — et, de proche en proche, une chance sur 2ᵏ d'en avoir k.

Concrètement, sur les premiers bits d'un hash :

- `1011...` → aucun zéro initial (une chance sur 2 que le premier bit soit un `1` : le cas le plus courant, autant que tous les autres cas réunis) ;
- `0110...` → un zéro initial (une chance sur 4 : un `0` puis un `1`) ;
- `0001...` → trois zéros initiaux (une chance sur 16 : trois `0` puis un `1` — déjà nettement plus rare).

Si, dans le flux d'adresses IP, on en a rencontré une dont le hash a 17 zéros initiaux, c'est un hash rare : un tel hash n'apparaît, en moyenne, qu'une fois sur 2¹⁷ (environ 130 000) — il a probablement fallu rencontrer beaucoup d'adresses différentes pour le croiser. À partir de cette rareté, on peut « revenir en arrière » et estimer combien d'éléments distincts ont été vus. Et ajouter dix fois la **même** adresse ne change rien : son hash est identique, il ne bat aucun record — c'est ce qui rend l'algorithme insensible aux doublons.

Un record unique est cependant très instable : avec seulement 3 visiteurs, si le hash du troisième a par malchance beaucoup de zéros, on croira avoir vu des milliers d'adresses. Comme toute statistique, ce raisonnement ne devient fiable qu'avec beaucoup d'éléments — sur un petit flux, un seul coup de malchance fausse tout.

## Les buckets

Pour stabiliser l'estimation, on regarde la rareté **en groupant** les valeurs dans des *buckets* (dans notre code, le tableau s'appelle `registres`) :

1. On calcule le hash de la valeur.
2. Les premiers bits donnent le **numéro de bucket** — leur nombre est la **précision**, passée au constructeur de notre classe ; cet atelier utilise 4 bits, donc 16 buckets (`int[16]`).
3. Sur les bits restants, on compte les zéros initiaux.
4. Le bucket garde le **maximum** rencontré jusqu'ici (jamais moins).

Une adresse arrive, elle tombe dans le bucket 1 avec un zéro initial : le bucket 1 vaut 1. Une autre tombe dans le bucket 2 avec deux zéros : le bucket 2 vaut 2. Une troisième tombe aussi dans le bucket 1, avec plus de zéros que le record actuel : on remplace le maximum. Et ainsi de suite sur tout le flux.

Exemple complet avec le hash `0x12345678` (celui utilisé dans les tests de l'exercice) :

```
hash  = 0x12345678 = 0001 0010 0011 0100 0101 0110 0111 1000
index = les 4 premiers bits → 0001 = bucket 1
reste = les 28 bits restants → 0010 0011 0100 0101 0110 0111 1000
```

Le reste commence par deux zéros puis un `1` : le premier bit à `1` est en position 3. L'implémentation fournie enregistre cette position — le nombre de zéros initiaux plus un, même idée que dans la vidéo, simplement décalée de un. `registres[1]` passe donc à `3`, sauf s'il valait déjà plus.

## L'estimation : moyenne harmonique

À la fin, chaque bucket contient le record de « son » seizième des valeurs. Pour combiner ces records, on utilise une moyenne **harmonique** : son avantage est que les valeurs extrêmes pèsent moins — une adresse exceptionnellement rare n'écrase pas l'estimation. Corrigée par une constante, elle donne :

```
estimation = ALPHA * m² / Σ(2⁻ʳᵉᵍⁱˢᵗʳᵉ⁽ⁱ⁾)
```

où `m` est le nombre de buckets et `ALPHA` une constante qui dépend de `m` (`0.673` pour `m = 16` — le constructeur fourni initialise l'attribut `alpha` pour vous, selon la précision choisie). La formule est fournie telle quelle : elle vient du papier original de l'algorithme, dont les mathématiques dépassent le cadre du cours.

Exemple chiffré : imaginons qu'après un flux, les 16 registres valent tous `3`. Alors `Σ(2⁻ʳᵉᵍⁱˢᵗʳᵉ⁽ⁱ⁾) = 16 × 2⁻³ = 2`, et l'estimation vaut `0.673 × 16² / 2 ≈ 86` valeurs distinctes. Si maintenant un seul registre bat un record improbable et saute à `17`, la somme devient `15 × 2⁻³ + 2⁻¹⁷ ≈ 1.875` : l'estimation ne monte qu'à ≈ 92, pas à des milliers — c'est la moyenne harmonique qui amortit la valeur extrême.

## Précision et mémoire figée

La démonstration de la vidéo donne les ordres de grandeur :

- avec des buckets sur **4 bits** (nos 16 registres) et 100 000 IP générées, l'estimation tombe autour de 74 000 : environ **25 % d'erreur**. C'est le choix de cet atelier, volontairement petit pour rester lisible — l'imprécision que vous observerez n'est pas un bug de votre code ;
- avec des buckets sur **14 bits**, soit 2¹⁴ = 16 384 buckets, la même expérience donne 100 935 : environ **1 % d'erreur**.

Dans le code de l'atelier, cette précision est le second paramètre du constructeur : `new HyperLogLog(hasher, 4)` donne les 16 registres utilisés partout ici ; rien ne vous empêche, une fois l'atelier terminé, de passer `14` et de comparer les estimations.

Et le point clé : la mémoire est **figée**. Avec un hash de 64 bits et 14 bits de bucket, il reste 50 bits, donc au plus 50 zéros initiaux à mémoriser — 6 bits par bucket suffisent. Au total : 16 384 × 6 bits ≈ **12 Ko**, que la vidéo compte 300 vues ou 500 millions. Contrairement à l'approche naïve, la taille ne grandit pas avec le trafic. C'est pour cela qu'on retrouve HyperLogLog dans Redis (commande `PFCOUNT`) ou BigQuery (`APPROX_COUNT_DISTINCT`).

Pour réduire encore l'erreur, deux axes : augmenter le nombre de buckets (au prix de la mémoire), ou croiser plusieurs algorithmes de hachage et moyenner leurs estimations (au prix du temps de calcul).

## L'importance du hash

Toute la mécanique d'HyperLogLog suppose que les hashs se comportent comme des tirages au sort parfaits : chaque valeur doit avoir autant de chances d'atterrir dans n'importe quel bucket, avec n'importe quel nombre de zéros. Si le hasard est truqué, tout le raisonnement s'effondre.

Or le hash « naturel » de Java, `String.hashCode()`, est justement truqué pour nos données. Des chaînes qui se ressemblent (`"user1"`, `"user2"`, des adresses IP qui ne changent que par le dernier chiffre...) donnent des hashs qui se ressemblent aussi : leur écriture binaire commence presque toujours par les mêmes bits. Et c'est précisément ce début qu'on utilise pour choisir le bucket. Résultat : presque toutes les valeurs s'entasseraient dans 2 ou 3 buckets, les autres resteraient vides, et l'estimation n'aurait plus rien à voir avec la réalité.

La parade : « secouer » les bits avant de s'en servir, comme on bat un jeu de cartes. Le code ci-dessous (le « finalizer » de MurmurHash3, une recette éprouvée) mélange le résultat de `hashCode()` de façon qu'un changement minuscule en entrée chamboule des bits partout dans le résultat :

```java
int hash = valeur.hashCode();
hash ^= (hash >>> 16);
hash *= 0x85ebca6b;
hash ^= (hash >>> 13);
hash *= 0xc2b2ae35;
hash ^= (hash >>> 16);
```

Dans l'exercice, ce mélange vous est fourni (classe `DefaultHasher`), tout comme la méthode `ajouter` de `HyperLogLog` : ils reposent sur des manipulations de bits qui dépassent le cadre du cours. Retenez surtout l'idée : sans un hash bien mélangé, HyperLogLog ne fonctionne pas. (Petite différence avec la vidéo : elle raisonne sur un hash de 64 bits ; notre implémentation utilise les 32 bits d'un `int` Java — le principe est identique.)

## Fusionner des compteurs

Dernier atout d'HyperLogLog : deux compteurs se **fusionnent** facilement. Si deux serveurs ont chacun compté leur propre trafic, il suffit de prendre, bucket par bucket, le maximum des deux valeurs, puis de refaire le calcul d'estimation sur le tableau fusionné : on obtient le compte des visiteurs uniques des deux serveurs réunis, sans jamais avoir échangé la moindre adresse IP. Par exemple, sur les quatre premiers registres :

| registre | serveur A | serveur B | fusion (max) |
|---------:|----------:|----------:|-------------:|
| 0        | 2         | 4         | 4            |
| 1        | 8         | 7         | 8            |
| 2        | 5         | 5         | 5            |
| 3        | 0         | 3         | 3            |

## Petit résumé

1. Compter exactement des valeurs distinctes demande soit trop de mémoire (tout stocker), soit donne un résultat faux (compteur simple, doublons comptés) ; HyperLogLog les **estime** avec une mémoire minuscule et fixe.
2. L'idée : un hash avec k zéros initiaux a une chance sur 2ᵏ d'exister ; en avoir croisé un très rare suggère qu'on a vu beaucoup de valeurs distinctes — et les doublons ne battent jamais de record.
3. Les premiers bits du hash choisissent un **bucket** (précision passée au constructeur — ici 4 bits, 16 registres) ; chaque bucket garde le maximum de zéros initiaux observé.
4. Une moyenne harmonique des buckets, corrigée par `ALPHA`, donne l'estimation : `ALPHA * m² / Σ(2⁻ʳᵉᵍⁱˢᵗʳᵉ⁽ⁱ⁾)`.
5. Précision : ~25 % d'erreur avec 16 buckets (notre atelier), ~1 % avec 16 384 buckets pour ~12 Ko de mémoire figée — d'où son usage dans Redis et BigQuery.
6. Le hash doit être uniforme : `String.hashCode()` seul ne suffit pas, on mélange ses bits (fourni dans `DefaultHasher`).
7. Deux compteurs HyperLogLog se fusionnent en prenant le maximum bucket par bucket.

---

*Passez aux [exercices](08B_2_exercices.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/08-lecture-fichiers-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
