package injection;

// TODO Question 6 : injecter automatiquement les champs annotés @Inject
/**
 * L'injecteur de dépendances : il remplit les champs annotés d'un objet avec l'implémentation
 * qu'ils désignent.
 *
 * C'est le point d'aboutissement de la séance : la classe qui reçoit les dépendances ne les
 * construit plus et ne les reçoit plus par constructeur ou setter — elle déclare ce qu'elle veut,
 * et l'injecteur le fournit par introspection.
 */
public class Injector {

    /**
     * Remplit les champs annotés de l'objet avec une instance de l'implémentation demandée.
     *
     * @param target l'objet dont les dépendances doivent être injectées
     * @throws RuntimeException si une implémentation ne peut pas être instanciée — constructeur
     *                          sans argument absent ou inaccessible
     */
    public static void inject(Object target) {
        // TODO Question 6
        throw new UnsupportedOperationException();
    }

}
