package be.vinci.minijunit;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Le cœur du mini-JUnit : découvre les méthodes annotées @MonTest d'une classe
 * par réflexion, les exécute et produit un RapportExecution.
 */
public class TestRunner {

    public List<Method> trouverMethodesDeTest(Class<?> classeDeTest) {
        // TODO Question 2 : renvoyer les méthodes déclarées de la classe qui portent
        //  l'annotation @MonTest (isAnnotationPresent), triées par nom — l'ordre renvoyé
        //  par getDeclaredMethods() n'est pas garanti
        throw new UnsupportedOperationException("À implémenter");
    }

    public RapportExecution executerTests(Class<?> classeDeTest) {
        // TODO Question 3 : pour chaque méthode de test (Question 2), créer une NOUVELLE
        //  instance de la classe (getDeclaredConstructor().newInstance()), invoquer la
        //  méthode, et ajouter un ResultatTest au rapport :
        //  - REUSSITE si l'invocation se passe bien ;
        //  - ECHEC si la méthode a lancé une AssertionError (la cause de
        //    l'InvocationTargetException), avec son message ;
        //  - ERREUR pour toute autre exception.
        // TODO Question 5 : avant chaque test, invoquer sur l'instance la ou les
        //  méthodes annotées @AvantChaqueTest
        // TODO Question 6 : si la méthode porte @Affichage, sa valeur devient le libellé
        //  du ResultatTest (constructeur à quatre paramètres) ; sinon, le libellé reste
        //  le nom de la méthode
        // TODO Question 7 : si la méthode porte @Repeter, l'invoquer fois fois — une
        //  NOUVELLE instance par répétition, un ResultatTest par répétition
        // TODO Question 10 (optionnelle) : si la méthode porte @TestDesactive, ne pas
        //  l'invoquer : ajouter directement un ResultatTest au statut IGNORE (message null)
        throw new UnsupportedOperationException("À implémenter");
    }

}
