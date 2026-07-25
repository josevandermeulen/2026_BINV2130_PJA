package domaine;

/**
 * L'implémentation par défaut de la fabrique de requêtes.
 */
public class QueryFactoryImpl implements QueryFactory {

    @Override
    /**
     * Crée une requête vide, à compléter par son appelant.
     *
     * @return une nouvelle requête
     */
    public Query getQuery() {
        return new QueryImpl();
    }

}
