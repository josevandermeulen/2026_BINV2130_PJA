package domaine;

public interface Query {

    String getUrl();

    void setUrl(String url);

    QueryMethod getMethod();

    void setMethod(QueryMethod method);

    enum QueryMethod {
        GET, POST;
    }

}
