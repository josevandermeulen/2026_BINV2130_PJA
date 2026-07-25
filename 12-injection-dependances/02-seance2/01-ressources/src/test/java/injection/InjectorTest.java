package injection;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import domaine.QueryFactory;
import domaine.QueryFactoryImpl;

public class InjectorTest {

    // Cible d'injection factice : permet de tester l'Injector sans démarrer le ProxyServer
    static class Cible {

        @Inject(QueryFactoryImpl.class)
        QueryFactory queryFactory;

        String sansAnnotation;

    }

    @Nested
    @DisplayName("Question 5 : annotation @Inject")
    class Question5 {

        @Test
        @DisplayName("Rétention RUNTIME : l'annotation est lisible par réflexion")
        void retentionRuntime() {
            Retention retention = Inject.class.getAnnotation(Retention.class);
            assertNotNull(retention, "@Inject doit porter @Retention");
            assertEquals(RetentionPolicy.RUNTIME, retention.value());
        }

        @Test
        @DisplayName("Cible FIELD : l'annotation se pose sur des champs")
        void cibleField() {
            Target target = Inject.class.getAnnotation(Target.class);
            assertNotNull(target, "@Inject doit porter @Target");
            assertArrayEquals(new ElementType[]{ElementType.FIELD}, target.value());
        }

    }

    @Nested
    @DisplayName("Question 6 : Injector")
    class Question6 {

        @Test
        @DisplayName("Le champ annoté reçoit une instance de l'implémentation déclarée")
        void champAnnoteInjecte() {
            Cible cible = new Cible();
            Injector.inject(cible);
            assertNotNull(cible.queryFactory);
            assertInstanceOf(QueryFactoryImpl.class, cible.queryFactory);
        }

        @Test
        @DisplayName("Un champ non annoté n'est pas touché")
        void champNonAnnoteIgnore() {
            Cible cible = new Cible();
            Injector.inject(cible);
            assertNull(cible.sansAnnotation);
        }

    }

}
