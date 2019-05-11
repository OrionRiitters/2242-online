/*package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        try {
            System.out.println("Starting up server..");
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 4);
            server.createContext("/cmd", new CmdHandler());
            server.createContext("/", new BaseHandler());
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
            System.out.println("Server started on port " + PORT + "..");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
*/