package server;

import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/*
 * This class sets up contexts for requests, the handler thread pool, and starts on a given port.
 */
public class TheServer extends Thread {
    String port_env = System.getenv("PORT");
    final int PORT = 8080;

    @Override
    public void run() {
        try {
            System.out.println("Starting up server..");
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 4);
            server.createContext("/cmd", new CmdHandler());
            server.createContext("/", new BaseHandler());
            server.createContext("/auth", new AuthHandler());
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
            System.out.println("Server started on port " + PORT + "..");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
