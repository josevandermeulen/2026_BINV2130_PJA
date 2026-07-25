package be.vinci.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Le cœur du validateur : découvre par réflexion les champs annotés d'un objet,
 * lit leur valeur et produit un RapportValidation.
 */
public class Validateur {

    public List<Field> trouverChampsAnnotes(Class<?> classe, Class<? extends Annotation> annotation) {
        // TODO Question 2 : renvoyer les champs déclarés de la classe qui portent
        //  l'annotation reçue en paramètre (getDeclaredFields, isAnnotationPresent),
        //  triés par nom — l'ordre renvoyé par getDeclaredFields() n'est pas garanti
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Violation> validerNonNul(Object objet) {
        // TODO Question 3 : pour chaque champ @NonNul de la classe de l'objet
        //  (Question 2), lire sa valeur — le champ est privé : setAccessible(true)
        //  avant field.get(objet) — et ajouter une Violation si elle est nulle
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Violation> validerLongueurMin(Object objet) {
        // TODO Question 4 : pour chaque champ @LongueurMin, lire l'élément valeur de
        //  l'annotation (getAnnotation(LongueurMin.class).valeur()) et ajouter une
        //  Violation si la valeur du champ est une String plus courte que ce minimum
        //  (un champ nul n'est pas une violation de longueur : c'est l'affaire de @NonNul)
        throw new UnsupportedOperationException("À implémenter");
    }

    public RapportValidation valider(Object objet) {
        // TODO Question 5 : renvoyer un RapportValidation agrégeant toutes les
        //  violations de l'objet (ajouterToutes) : @NonNul (Question 3),
        //  @LongueurMin (Question 4) — puis @Positif (Question 8) et @Valide
        //  (Question 9) si vous faites les parties optionnelles
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Violation> validerPositif(Object objet) {
        // TODO Question 8 (optionnelle) : pour chaque champ @Positif dont la valeur
        //  est un Number, ajouter une Violation si doubleValue() n'est pas
        //  strictement positive
        throw new UnsupportedOperationException("À implémenter");
    }

    public List<Violation> validerRecursivement(Object objet) {
        // TODO Question 9 (optionnelle) : pour chaque champ @Valide non nul, valider
        //  récursivement l'objet référencé (valider) et rapporter ses violations en
        //  préfixant leur champ du nom du champ porteur (par exemple adresse.rue)
        throw new UnsupportedOperationException("À implémenter");
    }

}
