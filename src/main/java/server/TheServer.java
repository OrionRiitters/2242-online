package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

public class TheServer extends Thread {

    private static final int PORT = 8000;


    /* Documentation for HttpServer and its associated classes can be found at
     * https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html
     */
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

    public static String readStream(InputStream is) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public static String loadStatic() throws Exception {
        File file = new File("/home/orion/IdeaProjects/2242-online/tmp-frontend/index.html");
        try (
                FileInputStream fis = new FileInputStream(file);
        ) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);

            return new String(data, "UTF-8");
        }
    }

}
