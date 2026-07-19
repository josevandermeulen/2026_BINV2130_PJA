package server;

import java.util.Scanner;

import domaine.Query;
import domaine.Query.QueryMethod;
import domaine.QueryFactory;

public class ProxyServer {

    private final QueryFactory queryFactory;

    public ProxyServer(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public void startServer() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String url = scanner.nextLine();
                Query query = this.queryFactory.getQuery();
                query.setMethod(QueryMethod.GET);
                query.setUrl(url);
                QueryHandler queryHandler = new QueryHandler(query);
                queryHandler.sendQueryAndPrintResponse();
            }
        }
    }

}
