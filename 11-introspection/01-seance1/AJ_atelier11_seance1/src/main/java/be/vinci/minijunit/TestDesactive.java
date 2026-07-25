package be.vinci.minijunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Désactive une méthode de test : elle apparaît dans le rapport avec le
 * statut IGNORE mais n'est jamais invoquée (comme @Disabled en JUnit).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDesactive {
}
