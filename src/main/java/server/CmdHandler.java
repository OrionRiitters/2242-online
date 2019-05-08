package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CmdHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {

        InputStream is = t.getRequestBody();
        System.out.println(is);
        String response = "I am a response!";
        t.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}
