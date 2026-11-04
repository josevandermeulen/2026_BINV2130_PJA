package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Filtrage et transformation de listes via des Predicate et des Function, avec et sans l'API Stream.
 */
public class Lambda {

    // Question 14 : allMatches (Predicate) — version générique de la Question 16
    /**
     * Retourne une liste contenant uniquement les éléments qui correspondent
     * au predicat match
     * @param list La liste originale
     * @param match le predicat à respecter
     * @return une liste contenant les éléments qui respectent match
     */
    public static <T> List<T> allMatches(List<T> list, Predicate<T> match) {
        List<T> result = new ArrayList<>();
        list.forEach(i -> {
            if (match.test(i)) {
                result.add(i);
            }
        });
        return result;
    }

    // Question 15 : transformAll (Function) — version générique de la Question 16
    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les éléments transformés par transform
     */
    public static <P, R> List<R> transformAll(List<P> list, Function<P, R> transform) {
        List<R> result = new ArrayList<>();
        list.forEach(i -> result.add(transform.apply(i)));
        return result;
    }

    // Question 17 : filter (API Stream)
    /**
     * Retourne une liste contenant uniquement les Integer qui correspondent
     * au predicat match
     * @param list La liste d'Integer originale
     * @param match le predicat à respecter
     * @return une liste contenant les integer qui respectent match
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> match) {
        return list.stream().filter(match).toList();
    }

    // Question 18 : map (API Stream)
    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste d'Integer originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les integer transformés par transform
     */
    public static <P, R> List<R> map(List<P> list, Function<P, R> transform) {
        return list.stream().map(transform).toList();
    }

}
