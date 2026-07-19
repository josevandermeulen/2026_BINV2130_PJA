package main;

import domaine.Hasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MockitoSmokeTest {

    @Test
    void mockitoFonctionneSansConnexionInternet() {
        Hasher hasher = mock(Hasher.class);
        when(hasher.hash("valeur")).thenReturn(42);

        assertEquals(42, hasher.hash("valeur"));
        verify(hasher).hash("valeur");
    }

}
