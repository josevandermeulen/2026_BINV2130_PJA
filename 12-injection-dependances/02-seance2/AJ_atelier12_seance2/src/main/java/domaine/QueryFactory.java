package domaine;

/**
 * La fabrique de requêtes, dépendance du serveur proxy.
 *
 * Passer par une fabrique plutôt que d'instancier directement une requête est ce qui rend
 * l'implémentation interchangeable — et donc injectable.
 */
public interface QueryFactory {

    Query getQuery();

}
