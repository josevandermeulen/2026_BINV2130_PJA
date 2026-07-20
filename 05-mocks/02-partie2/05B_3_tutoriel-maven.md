# Tutoriel : créer un projet Maven avec JUnit 5 et Mockito dans IntelliJ

Ce tutoriel s'applique à la partie 2 de l'atelier 5 et à toutes les semaines suivantes basées sur Maven.

## Étape 1 — Créer le projet Maven

1. Dans IntelliJ, cliquez sur New Project (File → New → Project si vous n'êtes pas dans la fenêtre de départ).
2. Donnez un nom au projet suivant la convention `AJ_atelierNN[_partieX]` (ex. `AJ_atelier05_partie2`) dans Name.
3. Choisissez l'endroit où vous voulez créer votre projet dans Location.
4. Choisissez Maven (et non IntelliJ !) comme Build system.
5. Choisissez le JDK installé sur votre machine (17 ou plus récent).
6. Dans Advanced Settings, donnez le GroupId `be.vinci` (l'ArtifactId reprend automatiquement le nom du projet).
7. Cliquez sur Create.

IntelliJ crée un `pom.xml` minimal et les dossiers `src/main/java` et `src/test/java`.

## Étape 2 — Générer le `pom.xml` avec l'aide d'une IA

Plutôt que de recopier les dépendances à la main, demandez à un assistant IA (Claude Code, Copilot, ChatGPT…) de générer le `pom.xml` complet. Exemple de prompt :

> Génère-moi un `pom.xml` Maven pour un projet Java 17 (groupId `be.vinci`) avec les dépendances JUnit 5 (`junit-jupiter`) et Mockito (`mockito-core` + `mockito-junit-jupiter`), en scope `test`, avec le `maven-compiler-plugin` configuré sur Java 17 et le `maven-surefire-plugin` pour exécuter les tests.

Le `pom.xml` généré devrait ressembler à ceci :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>be.vinci</groupId>
  <artifactId>AJ_atelier05_partie2</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>5.11.0</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-core</artifactId>
      <version>5.21.0</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-junit-jupiter</artifactId>
      <version>5.21.0</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.2.5</version>
      </plugin>
    </plugins>
  </build>
</project>
```

Collez ce contenu dans le `pom.xml` créé à l'étape 1.

## Étape 3 — Recharger le projet Maven

Après avoir modifié le `pom.xml`, une icône Maven (⟳) apparaît en haut à droite de l'éditeur. Cliquez dessus (ou `Ctrl+Maj+O`) pour qu'IntelliJ télécharge les dépendances et les ajoute au classpath.

Si l'icône n'apparaît pas : clic droit sur `pom.xml` → `Maven` → `Reload project`.

## Étape 4 — Vérifier avec une classe et un test

Créez `src/main/java/be/vinci/mocks/Calculator.java` :

```java
package be.vinci.mocks;

public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}
```

Créez `src/test/java/be/vinci/mocks/CalculatorTest.java` :

```java
package be.vinci.mocks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void testAdd() {
        Calculator calculator = new Calculator();
        assertEquals(5, calculator.add(2, 3));
    }
}
```

## Étape 5 — Vérifier que Mockito fonctionne

Ajoutez un test qui crée un mock, pour confirmer que Mockito est bien sur le classpath :

```java
package be.vinci.mocks;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MockitoSmokeTest {

    interface DataService {
        int getData();
    }

    @Test
    void testMock() {
        DataService dataService = Mockito.mock(DataService.class);
        Mockito.when(dataService.getData()).thenReturn(42);
        assertEquals(42, dataService.getData());
    }
}
```

## Étape 6 — Exécuter les tests

- Depuis IntelliJ : clic droit sur `src/test/java` → `Run 'All Tests'`.
- En ligne de commande :

```
mvn test
```

Si les deux tests passent (`CalculatorTest` et `MockitoSmokeTest`), l'environnement Maven + JUnit 5 + Mockito est opérationnel.

## Dépannage

- **IntelliJ ne reconnaît pas `@Test`** : le projet n'a pas été rechargé après modification du `pom.xml` (étape 3).
- **`package org.mockito does not exist`** : même cause, ou dépendance mal orthographiée dans le `pom.xml`.
- **`mvn` introuvable en ligne de commande** : utilisez le terminal intégré d'IntelliJ, qui référence le Maven embarqué, ou installez Maven séparément.

---

*Retour à la [théorie](05B_1_theorie.md).*

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/05-mocks-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
