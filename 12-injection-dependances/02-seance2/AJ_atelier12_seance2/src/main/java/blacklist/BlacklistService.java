package blacklist;

import domaine.Query;

// Renvoie true si l'URL de la query contient un domaine blacklisté
/**
 * Le contrôle des domaines interdits, dépendance du serveur proxy.
 */
public interface BlacklistService {

    /**
     * Vérifie si la requête vise un domaine interdit.
     *
     * @param query la requête à contrôler
     * @return true si l'URL de la requête contient un domaine de la liste noire
     */
    boolean check(Query query);

}
