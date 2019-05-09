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
            response = loadStatic();
        } catch (Exception e) {
            System.out.println(e);
        }
        t.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    public String loadStatic() throws Exception {
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
