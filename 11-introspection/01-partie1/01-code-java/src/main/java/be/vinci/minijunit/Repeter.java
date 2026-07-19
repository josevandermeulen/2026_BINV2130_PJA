package be.vinci.minijunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Demande l'exécution répétée d'une méthode de test : le test est invoqué
 * fois fois, sur une nouvelle instance à chaque répétition (comme
 * @RepeatedTest en JUnit).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Repeter {

    int fois();

}
