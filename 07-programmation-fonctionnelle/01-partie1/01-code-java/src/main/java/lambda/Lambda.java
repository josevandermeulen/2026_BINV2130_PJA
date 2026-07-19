package lambda;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda {

    /**
     * Retourne une liste contenant uniquement les éléments qui correspondent
     * au predicat match
     * @param list La liste originale
     * @param match le predicat à respecter
     * @return une liste contenant les éléments qui respectent match
     */
    public static <T> List<T> allMatches(List<T> list, Predicate<T> match) {
        //TODO
        return null;
    }

    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les éléments transformés par transform
     */
    public static <P, R> List<R> transformAll(List<P> list, Function<P, R> transform) {
        //TODO
        return null;
    }

    /**
     * Retourne une liste contenant uniquement les éléments qui correspondent
     * au predicat match, en utilisant l'API Stream
     * @param list La liste originale
     * @param match le predicat à respecter
     * @return une liste contenant les éléments qui respectent match
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> match) {
        //TODO
        return null;
    }

    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform, en utilisant l'API Stream
     * @param list La liste originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les éléments transformés par transform
     */
    public static <P, R> List<R> map(List<P> list, Function<P, R> transform) {
        //TODO
        return null;
    }

}
