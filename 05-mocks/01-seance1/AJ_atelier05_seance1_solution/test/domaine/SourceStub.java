package domaine;

import source.SourceBibliotheques;
import source.SourceIndisponibleException;

import java.util.ArrayList;
import java.util.List;

// Question 2

/**
 * Stub de SourceBibliotheques : renvoie toujours la réponse fixée à sa création, quel que soit le
 * filtre reçu. Il ne filtre rien, ne calcule rien et ne fait aucun appel réseau.
 */
public class SourceStub implements SourceBibliotheques {

    private final List<Bibliotheque> bibliotheques;

    private final SourceIndisponibleException panne;

    /**
     * Crée un stub qui renvoie toujours les bibliothèques indiquées.
     *
     * @param bibliotheques les bibliothèques à renvoyer
     */
    public SourceStub(List<Bibliotheque> bibliotheques) {
        this.bibliotheques = bibliotheques;
        this.panne = null;
    }

    /**
     * Crée un stub qui simule une source en panne : il lève toujours l'exception indiquée.
     *
     * @param panne l'exception à lever
     */
    public SourceStub(SourceIndisponibleException panne) {
        this.bibliotheques = null;
        this.panne = panne;
    }

    /**
     * Renvoie les bibliothèques fixées à la création, ou lève l'exception fixée à la création.
     *
     * @param filtre ignoré
     * @return une copie des bibliothèques fixées à la création
     * @throws SourceIndisponibleException si le stub a été créé pour simuler une panne
     */
    @Override
    public List<Bibliotheque> rechercher(String filtre) {
        if (panne != null) {
            throw panne;
        }
        return new ArrayList<>(bibliotheques);
    }
}
