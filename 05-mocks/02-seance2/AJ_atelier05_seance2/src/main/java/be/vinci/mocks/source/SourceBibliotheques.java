package be.vinci.mocks.source;

import be.vinci.mocks.domaine.Bibliotheque;

import java.util.List;

/**
 * Une source de bibliothèques : quelque chose à qui l'on peut demander la liste des bibliothèques
 * qui répondent à un filtre.
 *
 * L'implémentation de production, OdwbClient, interroge l'API d'ODWB par Internet. Le service qui
 * utilise une source ne connaît que cette interface : on peut donc lui en donner une autre, par
 * exemple un faux objet dans les tests.
 */
public interface SourceBibliotheques {

    /**
     * Renvoie les bibliothèques qui répondent au filtre.
     *
     * @param filtre un filtre au format de l'API d'ODWB, par exemple `cp="5000"`, ou une chaîne
     *               vide pour obtenir toutes les bibliothèques
     * @return les bibliothèques trouvées, dans un ordre quelconque ; une liste vide si aucune ne
     *         répond au filtre
     * @throws SourceIndisponibleException si la source ne peut pas répondre (réseau coupé, serveur
     *                                     en panne, réponse illisible, …)
     */
    List<Bibliotheque> rechercher(String filtre);
}
