# Atelier 8 : Lecture de fichiers et compléments Java – séance 1 : solutions

*Énoncé : [`08A_2_exercices.md`](../AJ_atelier08_seance1/08A_2_exercices.md) — théorie : [`08A_1_theorie.md`](../AJ_atelier08_seance1/08A_1_theorie.md).*

## Lire le fichier d'index

### Question 1

**Énoncé :** Ouvrez la classe `LecteurFichiers` (package `util`). Elle implémente l'interface `LecteurCommandes` (fournie). Complétez la méthode `lireIndex(String chemin)` : ouvrez le fichier avec un `BufferedReader` dans un bloc `try-with-resources`, lisez-le ligne […]

[`src/main/java/util/LecteurFichiers.java`](src/main/java/util/LecteurFichiers.java) :

```java
public class LecteurFichiers implements LecteurCommandes {
    // ...
    /**
     * Lit le fichier d'index et renvoie les noms des fichiers de commandes qu'il contient.
     *
     * @param chemin le chemin du fichier d'index
     * @return la liste des noms de fichiers, dans l'ordre du fichier
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    @Override
    public List<String> lireIndex(String chemin) throws IOException {
        List<String> noms = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            while (ligne != null) {
                noms.add(ligne);
                ligne = lecteur.readLine();
            }
        }
        return noms;
    }
    // ...
}
```

*Vérifié par : `LecteurFichiersTest` (classe `Question1`).*

## Lire un fichier de commandes

### Question 2

**Énoncé :** Complétez la méthode `lireLignes(String chemin)` de `LecteurFichiers` : ouvrez le fichier dans un bloc `try-with-resources`, mais récupérez cette fois toutes les lignes via la méthode `lines` du `BufferedReader`, qui renvoie un `Stream<String>` (à […]

[`src/main/java/util/LecteurFichiers.java`](src/main/java/util/LecteurFichiers.java) :

```java
public class LecteurFichiers implements LecteurCommandes {
    // ...
    /**
     * Lit un fichier de commandes et renvoie toutes ses lignes.
     *
     * @param chemin le chemin du fichier de commandes
     * @return la liste des lignes, dans l'ordre du fichier
     * @throws IOException si le fichier ne peut pas être lu
     */
    @Override
    public List<String> lireLignes(String chemin) throws IOException {
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            return lecteur.lines().collect(Collectors.toList());
        }
    }

}
```

*Vérifié par : `LecteurFichiersTest` (classe `Question2`).*

## Construire une commande

### Question 3

**Énoncé :** Ouvrez la classe `AnalyseCommandes` (package `main`). Elle reçoit la `Pizzeria` et un `LecteurCommandes` dans son constructeur. Complétez la méthode `parserLigne(String ligne)`, qui convertit une ligne en commande clôturée. Découpez la ligne avec […]

**parserLigne**

L'ordre des vérifications compte. Le constructeur de `Commande` enregistre la commande comme
commande en cours du client : si la quantité n'était vérifiée qu'après, une ligne refusée
laisserait au client une commande en cours jamais clôturée, et toutes ses lignes suivantes
seraient refusées à leur tour. On vérifie donc les quatre champs d'abord, on crée la commande
ensuite, et on la clôture tout de suite. Deux exceptions arrivent d'ailleurs sans qu'on les lève
soi-même : `Integer.parseInt` lève une `NumberFormatException`, qui est une sous-classe
d'`IllegalArgumentException`, alors que `LocalDateTime.parse` lève une `DateTimeParseException`,
qui n'en est pas une : il faut l'attraper et lever une `IllegalArgumentException` à la place.

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    private Pizzeria pizzeria;
    // ...
    /**
     * Convertit une ligne de fichier en commande clôturée.
     *
     * La ligne compte quatre champs séparés par des points-virgules : la date et l'heure, le
     * téléphone du client, le titre de la pizza et la quantité. Tous les champs sont vérifiés avant
     * de créer la commande : une commande créée puis abandonnée resterait la commande en cours du
     * client, et bloquerait toutes ses commandes suivantes.
     *
     * @param ligne la ligne à convertir
     * @return la commande correspondante, déjà clôturée
     * @throws IllegalArgumentException si la ligne est null ou vide, si elle ne compte pas quatre
     *                                  champs, si la date est mal formée, si le client ou la pizza
     *                                  est inconnu, ou si la quantité n'est pas un entier
     *                                  strictement positif
     */
    public Commande parserLigne(String ligne) {
        Util.checkString(ligne);
        String[] champs = ligne.split(";");
        if (champs.length != 4) {
            throw new IllegalArgumentException("Nombre de champs incorrect : " + ligne);
        }

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(champs[0]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date mal formée : " + champs[0]);
        }

        Client client = pizzeria.rechercherClient(champs[1]);
        if (client == null) {
            throw new IllegalArgumentException("Client inconnu : " + champs[1]);
        }

        Pizza pizza = pizzeria.rechercherPizza(champs[2]);
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza inconnue : " + champs[2]);
        }

        // NumberFormatException est une sous-classe d'IllegalArgumentException
        int quantite = Integer.parseInt(champs[3]);
        Util.checkStrictlyPositive(quantite);

        Commande commande = new Commande(client, date);
        commande.ajouter(pizza, quantite);
        client.cloturerCommandeEnCours();
        return commande;
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question3`).*

## Ignorer les lignes invalides

### Question 4

**Énoncé :** Complétez la méthode `parserLignes(List<String> lignes)` : pour chaque ligne, appelez `parserLigne`. Une ligne invalide ne doit pas interrompre le traitement : attrapez l'`IllegalArgumentException`, affichez `"Ligne ignorée : " + ligne` sur la sortie […]

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    // ...
    /**
     * Convertit un ensemble de lignes en commandes, en ignorant celles qui sont malformées.
     *
     * Une ligne rejetée est signalée sur la sortie standard, mais n'interrompt pas la conversion
     * des suivantes.
     *
     * @param lignes les lignes à convertir
     * @return les commandes obtenues, sans les lignes rejetées
     */
    public List<Commande> parserLignes(List<String> lignes) {
        List<Commande> commandes = new ArrayList<>();
        for (String ligne : lignes) {
            try {
                commandes.add(parserLigne(ligne));
            } catch (IllegalArgumentException e) {
                System.out.println("Ligne ignorée : " + ligne);
            }
        }
        return commandes;
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question4`).*

## Charger tous les fichiers

### Question 5

**Énoncé :** Complétez la méthode `chargerToutesLesCommandes(String dossier)` : lisez l'index (`dossier + "/index.txt"`), puis, pour chaque nom de fichier listé, lisez ses lignes (`dossier + "/" + nomFichier`) et parsez-les. L'index mentionne `2026-11-12.csv`, qui […]

**chargerToutesLesCommandes**

Le `try/catch` entoure la lecture d'**un** fichier, à l'intérieur de la boucle : c'est ce qui
permet de passer au fichier suivant. Placé autour de la boucle, il arrêterait le chargement au
premier fichier manquant. Seule la `FileNotFoundException` est attrapée : un index illisible, ou
toute autre `IOException`, continue de se propager, comme le déclare le `throws IOException`.

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    private Pizzeria pizzeria;
    private LecteurCommandes lecteur;
    // ...
    /**
     * Charge les commandes de tous les fichiers référencés par l'index du dossier, et les
     * enregistre dans la pizzeria.
     *
     * Un fichier annoncé par l'index mais introuvable est signalé puis ignoré ; en revanche, un
     * index illisible interrompt le chargement.
     *
     * @param dossier le dossier contenant l'index et les fichiers de commandes
     * @return les commandes de tous les fichiers lisibles, dans l'ordre de l'index
     * @throws IOException si le fichier d'index ne peut pas être lu
     */
    public List<Commande> chargerToutesLesCommandes(String dossier) throws IOException {
        List<Commande> commandes = new ArrayList<>();
        for (String nomFichier : lecteur.lireIndex(dossier + "/index.txt")) {
            try {
                commandes.addAll(parserLignes(lecteur.lireLignes(dossier + "/" + nomFichier)));
            } catch (FileNotFoundException e) {
                System.out.println("Fichier introuvable : " + nomFichier);
            }
        }

        for (Commande commande : commandes) {
            pizzeria.ajouterCommande(commande);
        }
        return commandes;
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question5`).*

## Statistiques

### Question 6

**Énoncé :** Complétez, en utilisant l'API Stream sur les commandes de la pizzeria, la méthode `chiffreAffairesParJour()` : une `Map<LocalDate, Double>` associant chaque jour au montant total de ses commandes, triée par date. Utilisez `groupingBy` avec trois arguments […]

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    private Pizzeria pizzeria;
    // ...
    /**
     * Calcule le chiffre d'affaires de chaque jour, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque jour au montant total de ses commandes, triée par date
     */
    public Map<LocalDate, Double> chiffreAffairesParJour() {
        return pizzeria.getCommandes().stream()
                .collect(Collectors.groupingBy(commande -> commande.getDate().toLocalDate(),
                        TreeMap::new,
                        Collectors.summingDouble(Commande::calculerMontantTotal)));
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question6`).*

### Question 7

**Énoncé :** Complétez `pizzasVenduesParCategorie()` : une `Map<Categorie, Integer>` associant chaque catégorie au nombre de pizzas vendues. Les commandes chargées n'ont qu'une ligne, mais une commande peut en général en contenir plusieurs : passez d'un flux de […]

**pizzasVenduesParCategorie**

Une commande contient plusieurs lignes : `flatMap` aplatit le flux des commandes en un seul flux
de lignes, sur lequel le regroupement par catégorie additionne les quantités
(`summingInt`), et non le nombre de lignes (`counting`).

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    private Pizzeria pizzeria;
    // ...
    /**
     * Compte les pizzas vendues dans chaque catégorie, à partir des commandes de la pizzeria.
     *
     * @return une table associant chaque catégorie vendue au nombre de pizzas vendues
     */
    public Map<Categorie, Integer> pizzasVenduesParCategorie() {
        return pizzeria.getCommandes().stream()
                .flatMap(commande -> commande.getLignes().stream())
                .collect(Collectors.groupingBy(ligne -> ligne.getPizza().getCategorie(),
                        Collectors.summingInt(LigneDeCommande::getQuantite)));
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question7`).*

## Écrire un rapport

### Question 8

**Énoncé :** Complétez la méthode `ecrireRapport(String chemin)` : dans un bloc `try-with-resources` utilisant un `BufferedWriter`, écrivez une ligne par jour, par date croissante, au format `"2026-11-09 : 829.0 euros"`, suivie d'une dernière ligne `"Chiffre d'affaires […]

[`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    // ...
    /**
     * Écrit dans un fichier le chiffre d'affaires de chaque jour, par date croissante, suivi du
     * chiffre d'affaires total.
     *
     * @param chemin le chemin du fichier à écrire
     * @throws IOException si le fichier ne peut pas être écrit
     */
    public void ecrireRapport(String chemin) throws IOException {
        Map<LocalDate, Double> parJour = chiffreAffairesParJour();
        double total = 0;
        try (BufferedWriter redacteur = new BufferedWriter(new FileWriter(chemin))) {
            for (Map.Entry<LocalDate, Double> jour : parJour.entrySet()) {
                redacteur.write(jour.getKey() + " : " + jour.getValue() + " euros");
                redacteur.newLine();
                total += jour.getValue();
            }
            redacteur.write("Chiffre d'affaires total : " + total + " euros");
            redacteur.newLine();
        }
    }
    // ...
}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question8`).*

## Parties optionnelles

### Le meilleur client

#### Question 9

**Énoncé :** Complétez `meilleurClient()` : le client qui a dépensé le plus, toutes commandes de la pizzeria confondues, dans un `Optional<Client>` (vide si la pizzeria n'a aucune commande). Regroupez les commandes par client en additionnant leurs montants, puis […]

*optionnelle* — [`src/main/java/main/AnalyseCommandes.java`](src/main/java/main/AnalyseCommandes.java) :

```java
public class AnalyseCommandes {
    private Pizzeria pizzeria;
    // ...
    /**
     * Renvoie le client qui a dépensé le plus, toutes commandes de la pizzeria confondues.
     *
     * @return le meilleur client, ou un Optional vide si la pizzeria n'a aucune commande
     */
    public Optional<Client> meilleurClient() {
        return pizzeria.getCommandes().stream()
                .collect(Collectors.groupingBy(Commande::getClient,
                        Collectors.summingDouble(Commande::calculerMontantTotal)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

}
```

*Vérifié par : `AnalyseCommandesTest` (classe `Question9`).*

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
