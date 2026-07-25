package be.vinci.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marque un champ objet à valider récursivement : les violations de l'objet
 * référencé sont rapportées, préfixées du nom du champ (par exemple adresse.rue).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Valide {
}
