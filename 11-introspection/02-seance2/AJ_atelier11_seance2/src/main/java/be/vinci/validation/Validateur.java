package be.vinci.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Le validateur générique : il contrôle n'importe quel objet à partir des annotations portées par
 * ses champs.
 *
 * Il ne connaît aucune classe du domaine. Tout passe par l'introspection : lire les champs
 * déclarés, repérer ceux qui portent une annotation, forcer leur accès puis lire leur valeur.
 * Ajouter une contrainte au domaine ne demande donc pas de le modifier.
 */
public class Validateur {

    /**
     * Renvoie les champs d'une classe portant une annotation donnée.
     *
     * @param classe     la classe à inspecter
     * @param annotation l'annotation recherchée
     * @return les champs déclarés qui portent cette annotation
     */
    public List<Field> trouverChampsAnnotes(Class<?> classe, Class<? extends Annotation> annotation) {
        // TODO Question 2 : renvoyer les champs déclarés de la classe qui portent
        //  l'annotation reçue en paramètre (getDeclaredFields, isAnnotationPresent),
        //  triés par nom — l'ordre renvoyé par getDeclaredFields() n'est pas garanti
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Vérifie que les champs annotés « non nul » ont bien une valeur.
     *
     * @param objet l'objet à contrôler
     * @return les violations relevées, vide si aucune
     */
    public List<Violation> validerNonNul(Object objet) {
        // TODO Question 3 : pour chaque champ @NonNul de la classe de l'objet
        //  (Question 2), lire sa valeur — le champ est privé : setAccessible(true)
        //  avant field.get(objet) — et ajouter une Violation si elle est nulle
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Vérifie que les champs annotés d'une longueur minimale l'atteignent.
     *
     * @param objet l'objet à contrôler
     * @return les violations relevées, vide si aucune
     */
    public List<Violation> validerLongueurMin(Object objet) {
        // TODO Question 4 : pour chaque champ @LongueurMin, lire l'élément valeur de
        //  l'annotation (getAnnotation(LongueurMin.class).valeur()) et ajouter une
        //  Violation si la valeur du champ est une String plus courte que ce minimum
        //  (un champ nul n'est pas une violation de longueur : c'est l'affaire de @NonNul)
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Applique toutes les validations connues et rassemble leurs violations.
     *
     * @param objet l'objet à contrôler
     * @return le rapport des violations relevées
     */
    public RapportValidation valider(Object objet) {
        // TODO Question 5 : renvoyer un RapportValidation agrégeant toutes les
        //  violations de l'objet (ajouterToutes) : @NonNul (Question 3),
        //  @LongueurMin (Question 4) — puis @Positif (Question 8) et @Valide
        //  (Question 9) si vous faites les parties optionnelles
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Vérifie que les champs annotés « positif » portent une valeur strictement positive.
     *
     * @param objet l'objet à contrôler
     * @return les violations relevées, vide si aucune
     */
    public List<Violation> validerPositif(Object objet) {
        // TODO Question 8 (optionnelle) : pour chaque champ @Positif dont la valeur
        //  est un Number, ajouter une Violation si doubleValue() n'est pas
        //  strictement positive
        throw new UnsupportedOperationException("À implémenter");
    }

    /**
     * Valide les champs annotés « à valider » en descendant dans l'objet qu'ils portent.
     *
     * C'est ce qui permet de contrôler l'adresse d'un étudiant en même temps que l'étudiant
     * lui-même, sans que le validateur connaisse l'une ou l'autre classe.
     *
     * @param objet l'objet à contrôler
     * @return les violations relevées dans les objets imbriqués, vide si aucune
     */
    public List<Violation> validerRecursivement(Object objet) {
        // TODO Question 9 (optionnelle) : pour chaque champ @Valide non nul, valider
        //  récursivement l'objet référencé (valider) et rapporter ses violations en
        //  préfixant leur champ du nom du champ porteur (par exemple adresse.rue)
        throw new UnsupportedOperationException("À implémenter");
    }

}
