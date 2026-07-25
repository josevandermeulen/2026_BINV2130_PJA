package main;

import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import server.ProxyServer;

/**
 * Point d'entrée : construit le serveur proxy, lui fournit ses dépendances et le démarre.
 */
public class Main {

    public static void main(String[] args) {
        QueryFactory queryFactory = new QueryFactoryImpl();
        ProxyServer proxyServer = new ProxyServer(queryFactory);
        proxyServer.startServer();
    }

}
