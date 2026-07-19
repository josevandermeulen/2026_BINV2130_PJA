package be.vinci.minijunit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Donne un libellé lisible à une méthode de test : le rapport affiche ce
 * libellé à la place du nom de la méthode (comme @DisplayName en JUnit).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Affichage {

    String valeur();

}
