package server;

import java.util.Scanner;

import domaine.Query;
import domaine.Query.QueryMethod;
import domaine.QueryFactory;

/**
 * Le serveur proxy : il lit des URL sur l'entrée standard, écarte celles qui visent un domaine
 * interdit et transmet les autres.
 *
 * Ses deux dépendances — la fabrique de requêtes et le service de liste noire — ne sont jamais
 * construites ici : c'est tout l'objet de la séance, qui les fait passer du constructeur au setter
 * puis à l'injection par annotation.
 */
public class ProxyServer {

    private final QueryFactory queryFactory;

    /**
     * Crée le serveur avec sa fabrique de requêtes.
     *
     * @param queryFactory la fabrique qui produit les requêtes à transmettre
     */
    public ProxyServer(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * Démarre la boucle du proxy : lit une URL, la contrôle, puis la transmet.
     */
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
