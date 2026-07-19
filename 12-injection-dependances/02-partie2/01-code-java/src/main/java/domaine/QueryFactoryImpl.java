package domaine;

public class QueryFactoryImpl implements QueryFactory {

    @Override
    public Query getQuery() {
        return new QueryImpl();
    }

}
