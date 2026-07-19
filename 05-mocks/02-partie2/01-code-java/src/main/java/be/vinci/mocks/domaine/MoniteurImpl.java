package be.vinci.mocks.domaine;

import be.vinci.mocks.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe domaine.Moniteur représente le moniteur. Elle connaît le nom de celui-ci. Elle renferme
 * également les différents stages qu'il effectue stockés par semaine.
 */
public class MoniteurImpl implements Moniteur {


    /**
     * Le nom du moniteur
     */
    private final String nom;
    /**
     * Les stages effectués par le moniteur par numéro de semaine.
     */
    private final Map<Integer, Stage> stages;

    /**
     * crée un moniteur
     *
     * @param nom Le nom du moniteur
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    public MoniteurImpl(String nom) {
        Util.checkString(nom);
        this.nom = nom;
        stages = new HashMap<>();
    }

    /**
     * renvoie le nom du moniteur
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * recherche si le moniteur est libre durant la semaine passée en paramètre. Le moniteur est libre
     * s'il n'a pas de stage prévu cette semaine-là.
     *
     * @param numeroDeSemaine Le numéro de la semaine
     * @return true si le moniteur est libre durant la semaine passée en paramètre.
     * @throws IllegalArgumentException Exception lancée si le numéro de semaine n'est pas compris
     *                                  entre 1 et 8.
     */
    @Override
    public boolean estLibre(int numeroDeSemaine) {
        if (numeroDeSemaine < 1 || numeroDeSemaine > 8) {
            throw new IllegalArgumentException();
        }
        return !stages.containsKey(numeroDeSemaine);
    }

    // gestion de l'association avec stage

    /**
     * ajoute un stage au moniteur. Ce stage est ajouté si le moniteur n’avait pas de stage cette
     * semaine-là, qu’il fait bien partie des moniteurs pouvant encadrer des stages dans ce sport et
     * si le stage n’avait pas déjà un autre moniteur. La méthode s'assure aussi que le stage retient
     * aussi le moniteur associé.
     *
     * @param stage le stage à ajouter au moniteur.
     * @return true si le moniteur peut effectuer ce stage. Le stage est stocké dans ses stages.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    @Override
    public boolean ajouterStage(Stage stage) {
        if (this.contientStage(stage)) {
            return false;
        }
        if (!estLibre(stage.getNumeroDeSemaine())) {
            return false;
        }
        if (!stage.getSport().contientMoniteur(this)) {
            return false;
        }
        if (stage.getMoniteur() == this) {
            this.stages.put(stage.getNumeroDeSemaine(), stage);
            return true;
        }
        if (stage.getMoniteur() == null) {
            this.stages.put(stage.getNumeroDeSemaine(), stage);
            stage.enregistrerMoniteur(this);
            return true;
        }
        return false;
    }

    /**
     * supprime le stage du moniteur. Ce stage doit se trouver dans ses stages afin de pouvoir le
     * supprimer.
     * <p>
     * La méthode s'assure aussi que le stage supprime aussi le moniteur associé.
     *
     * @param stage le stage à supprimer du moniteur.
     * @return true si le stage peut être enlevé du moniteur. false si le stage ne fait pas partie des
     * stages du moniteur.
     * @throws IllegalArgumentException Exception lancée si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    @Override
    public boolean supprimerStage(Stage stage) {
        if (!this.contientStage(stage)) {
            return false;
        }
        stages.remove(stage.getNumeroDeSemaine());
        stage.supprimerMoniteur();
        return true;
    }

    /**
     * teste si le stage est un stage effectué par le moniteur.
     *
     * @param stage le stage à tester.
     * @return true si le stage est bien un stage du moniteur. false si le stage ne fait pas partie
     * des stages du moniteur.
     * @throws IllegalArgumentException Exception lanc"e si l'un des paramètres n'est pas spécifié ou
     *                                  vide.
     */
    @Override
    public boolean contientStage(Stage stage) {
        Util.checkObject(stage);
        return this.stages.containsValue(stage);
    }

    /**
     * renvoie le nombre de stages effectués par le moniteur
     *
     * @return le nombre de stages.
     */
    @Override
    public int nombreDeStages() {
        return this.stages.size();
    }

    /**
     * renvoie les stages dans une liste
     *
     * @return la liste des stages du moniteur.
     */
    @Override
    public List<Stage> stages() {
        return new ArrayList(stages.values());
    }

}
