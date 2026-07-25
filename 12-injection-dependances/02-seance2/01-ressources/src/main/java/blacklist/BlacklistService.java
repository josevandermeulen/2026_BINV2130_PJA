package blacklist;

import domaine.Query;

// Renvoie true si l'URL de la query contient un domaine blacklisté
public interface BlacklistService {

    boolean check(Query query);

}
