package main;

import domaine.Ingredients;
import domaine.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MockitoSmokeTest {

    @Test
    void mockitoFonctionneSansConnexionInternet() {
        Stock stock = mock(Stock.class);
        when(stock.estDisponible(Ingredients.TOMATE)).thenReturn(true);

        assertTrue(stock.estDisponible(Ingredients.TOMATE));
        verify(stock).estDisponible(Ingredients.TOMATE);
    }

}
