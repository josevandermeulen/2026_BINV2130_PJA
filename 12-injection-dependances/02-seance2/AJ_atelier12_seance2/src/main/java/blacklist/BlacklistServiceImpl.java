package blacklist;

import domaine.Query;

// TODO Question 2 : charger blacklist.properties une seule fois, puis vérifier les domaines
/**
 * Le contrôle des domaines interdits, lus une fois pour toutes dans blacklist.properties.
 *
 * Le fichier est chargé au chargement de la classe, à la racine du projet : le déplacer ou
 * l'oublier fait échouer le démarrage plutôt que le premier appel.
 */
public class BlacklistServiceImpl implements BlacklistService {

    /**
     * Vérifie si la requête vise un domaine interdit.
     *
     * @param query la requête à contrôler
     * @return true si l'URL de la requête contient un domaine de la liste noire
     */
    @Override
    public boolean check(Query query) {
        // TODO Question 2
        throw new UnsupportedOperationException();
    }

}
