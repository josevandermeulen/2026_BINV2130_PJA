# Atelier 12 : Injection de dépendances – partie 2

## Objectif

L'objectif de cette partie est de compléter le proxy de la partie 1 sur deux plans : le rendre **configurable sans recompilation** grâce à un fichier `.properties` (blacklist de domaines interdits), puis pousser l'injection de dépendances jusqu'à sa forme automatique — annotation `@Inject` et injecteur par réflexion, sur le modèle des conteneurs IoC comme Spring.

## Concepts

1. Fichiers `.properties` (configuration d'application)
2. Injection de dépendances par setter
3. Annotations personnalisées
4. Introspection (réflexion)
5. Javadoc
6. Packaging (JAR exécutable)

## Vidéos

1. [Interfaces](https://www.youtube.com/watch?v=tLvlrRXwyUk)
2. [Factory method](https://www.youtube.com/watch?v=c7TR0Y_mey8)
3. [Factory Configuration](https://www.youtube.com/watch?v=FdMuh9MQXMI)
4. [Abstract Factory et injection de dépendance via les constructeurs](https://www.youtube.com/watch?v=eZRXt9l4GVM)

## Exercices

### Introduction

Le proxy de la partie 1 fonctionne, mais il laisse tout passer : n'importe quelle URL entrée en console est visitée. Un vrai proxy web filtre — c'est même sa raison d'être. Nous allons lui ajouter une **blacklist de domaines**, configurée dans un fichier `.properties` modifiable sans recompiler l'application.

L'architecture de la partie 1 (interfaces, factory, injection par constructeur) va rendre cet ajout indolore. Nous en profiterons ensuite pour explorer deux autres formes d'injection de dépendances : par setter, puis **automatique** — une annotation `@Inject` et un injecteur qui assemble l'application par réflexion (atelier 11), comme le fait Spring.

Contrairement à la partie 1, des tests JUnit sont fournis : ils sont rouges au départ et passent au vert au fil des questions (les Questions 3, 4 et 7, interactives, se vérifient manuellement en console).

### Consignes

Dans IntelliJ, créez un nouveau Projet **Maven** nommé `AJ_atelier12_partie2`, avec `be.vinci` comme *GroupId*. Récupérez ensuite le contenu de `01-code-java/` : les sources (`src/main/java`) et les tests (`src/test/java`), en conservant les packages. Les packages `domaine`, `main` et `server` reprennent l'état final de la partie 1 (`../01-partie1/02-solution/`) — vous pouvez aussi repartir de votre propre solution ; les packages `blacklist` et `injection` contiennent les squelettes à compléter dans cet atelier.

Reprenez dans le `pom.xml` les properties de compilation de la partie 1 (version de Java), et ajoutez la dépendance JUnit :

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
```

Après chaque modification du `pom.xml` : clic droit sur le projet → Maven → *Reload project*.

### Blacklist – fichier `.properties`

**Question 1** :

✏️ *A corriger au tableau*

Créez un fichier `blacklist.properties` **à la racine du projet** (jamais dans `src` : il doit rester modifiable sans recompiler l'application), contenant la clé `blacklistedDomains` avec pour valeur la liste des domaines interdits, séparés par des `;`. Utilisez exactement cette valeur — les tests fournis s'appuient dessus :

```properties
blacklistedDomains=google.be;facebook.com;instagram.com
```

**Question 2** :
L'interface [`BlacklistService`](01-code-java/src/main/java/blacklist/BlacklistService.java) (package `blacklist`) est fournie : sa méthode `check` renvoie `true` si l'URL de la `Query` contient un domaine blacklisté. Complétez la classe [`BlacklistServiceImpl`](01-code-java/src/main/java/blacklist/BlacklistServiceImpl.java) : chargez le fichier `blacklist.properties` **une seule fois** (au chargement de la classe) à l'aide de [`java.util.Properties`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Properties.html), puis implémentez `check`.

Astuces :

1. La vérification URL/domaine peut s'écrire en une ligne avec les streams.
2. Contrairement à `QueryImpl`, l'implémentation reste publique : elle est instanciée hors de son package (dans le `main`, puis par l'injecteur à la Question 7).

Les tests de [`BlacklistServiceTest`](01-code-java/src/test/java/blacklist/BlacklistServiceTest.java) couvrent les Questions 1 et 2.

**Question 3** :
Injectez le `BlacklistService` dans [`ProxyServer`](01-code-java/src/main/java/server/ProxyServer.java) par le constructeur, comme pour la `QueryFactory` en partie 1. Avant d'envoyer une requête, le serveur vérifie la query : si elle est blacklistée, il affiche `Domaine bloqué : <url>` en console et n'envoie rien.

Vérifiez manuellement : `https://google.be/` doit être bloquée, `https://openjdk.org/` doit passer. Modifiez ensuite le fichier `.properties` et relancez l'application **sans recompiler** : le nouveau filtrage s'applique.

### Injection par setter

**Question 4** :
Remplacez l'injection par constructeur par une injection **par setter**, pour les deux dépendances de `ProxyServer` (`QueryFactory` et `BlacklistService`) : supprimez le constructeur, ajoutez un setter public par dépendance, et adaptez le `main` (créer le `ProxyServer` sans paramètre, injecter via les setters, puis appeler `startServer`).

Critère de réussite : le comportement est identique à la version par constructeur, et aucune implémentation (`new ...Impl()`) n'est créée dans le package `server`.

### Injection automatique par introspection

**Question 5** :
Complétez l'annotation [`Inject`](01-code-java/src/main/java/injection/Inject.java) (package `injection`) : elle doit être conservée à l'exécution, se poser uniquement sur des champs, et son élément `value` désigne la classe d'implémentation à instancier. La théorie de l'atelier 11 (réflexion, annotations) est votre référence.

**Question 6** :
Complétez la classe [`Injector`](01-code-java/src/main/java/injection/Injector.java) : sa méthode `inject` parcourt les champs déclarés de l'objet reçu, et pour chaque champ annoté `@Inject`, instancie l'implémentation désignée par l'annotation et affecte le champ. Encapsulez toute `ReflectiveOperationException` dans une `RuntimeException`.

Les tests de [`InjectorTest`](01-code-java/src/test/java/injection/InjectorTest.java) couvrent les Questions 5 et 6.

**Question 7** :
Mettez l'injecteur en service : annotez les champs de `ProxyServer` avec `@Inject`, supprimez les setters de la Question 4, et réduisez le `main` à trois lignes :

```java
ProxyServer proxyServer = new ProxyServer();
Injector.inject(proxyServer);
proxyServer.startServer();
```

Plus aucun `new` d'implémentation dans le `main` : c'est l'annotation qui désigne l'implémentation, et l'injecteur qui assemble — le principe des conteneurs IoC comme Spring, en miniature. Vérifiez manuellement en console, comme aux Questions 3 et 4 : le filtrage de la blacklist fonctionne toujours.

## Parties optionnelles

### Javadoc

**Question 8** :
Documentez avec des commentaires Javadoc (`/** ... */`, tags `@param`, `@return`) les interfaces du package `domaine` (`Query`, `QueryFactory`), leurs méthodes et l'énuméré `QueryMethod`. Syntaxe détaillée : https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#tag

**Question 9** :
Générez la documentation HTML dans IntelliJ : menu Tools → Generate JavaDoc, avec un dossier `doc` (à créer dans le projet) comme output directory. Aucun warning ne doit concerner les interfaces du package `domaine`. Ouvrez `doc/index.html` dans un navigateur.

Remarques sur la documentation générée :

1. `QueryImpl` n'y apparaît pas : elle est package-friendly — l'encapsulation cache les implémentations internes.
2. `QueryFactoryImpl` et `BlacklistServiceImpl` y apparaissent : elles sont publiques.
3. La Javadoc des implémentations est reprise depuis les interfaces : inutile de redocumenter, sauf pour ajouter des détails techniques.

### Packaging

**Question 10** :
Créez un JAR exécutable avec IntelliJ en suivant ce tutoriel : https://www.jetbrains.com/guide/java/tutorials/hello-world/packaging-the-application/ — puis exécutez-le dans un terminal avec `java -jar <votre-jar>.jar`. L'application ne démarre pas : `blacklist.properties` est introuvable — il doit se trouver dans le même dossier que le JAR. Copiez-le à côté du JAR et relancez.

**Question 11** :
Le JAR IntelliJ demande des clics : inutilisable dans un pipeline CI/CD. Automatisez le build avec Maven en ajoutant le plugin `maven-assembly-plugin` dans le `pom.xml` (remplacez `fully.qualified.MainClass` par la référence complète de votre classe `Main` — clic droit sur la classe → Copy Path/Reference… → Copy Reference) :

```xml
<plugin>
    <artifactId>maven-assembly-plugin</artifactId>
    <configuration>
        <archive>
            <manifest>
                <mainClass>fully.qualified.MainClass</mainClass>
            </manifest>
        </archive>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
</plugin>
```

Lancez ensuite `mvn clean compile assembly:single` : le JAR avec dépendances est construit dans `target/`, sans aucune manipulation graphique.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/12-injection-dependances-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
