package server;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AuthHandler {

    public void handle(HttpExchange t) throws IOException {

        String response = null;
        InputStream is = t.getRequestBody();
        String reqBody = TheServer.readStream(is);

        JSONObject json = new JSONObject(reqBody);



        t.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}
