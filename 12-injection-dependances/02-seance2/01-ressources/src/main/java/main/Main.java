package main;

import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import server.ProxyServer;

public class Main {

    public static void main(String[] args) {
        QueryFactory queryFactory = new QueryFactoryImpl();
        ProxyServer proxyServer = new ProxyServer(queryFactory);
        proxyServer.startServer();
    }

}
