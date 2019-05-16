package server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BaseHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        String response = null;
        InputStream is = t.getRequestBody();

        try {
            response = GenerateHTML.getHTML();
        } catch (Exception e) {
            System.out.println(e);
        }
        t.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
