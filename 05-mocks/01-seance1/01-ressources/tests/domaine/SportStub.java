package domaine;

import java.util.Set;

public class SportStub implements Sport {

    private boolean contientMoniteur;

    public SportStub(boolean contientMoniteur) {
        this.contientMoniteur=contientMoniteur;
    }

    @Override
    public String getIntitule() {
        return null;
    }

    @Override
    public boolean ajouterMoniteur(Moniteur moniteur) {
        return true;
    }

    @Override
    public boolean supprimerMoniteur(Moniteur moniteur) {
        return true;
    }

    @Override
    public boolean contientMoniteur(Moniteur moniteur) {
        return this.contientMoniteur;
    }

    @Override
    public Set<Moniteur> moniteurs() {
        return null;
    }

}
