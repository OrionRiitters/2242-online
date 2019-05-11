package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import statemanager.StateObservable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AuthHandler implements HttpHandler {
    StateObservable stateObservable = StateObservable.get_instance();

    public void handle(HttpExchange t) throws IOException {

        String response = null;
        InputStream is = t.getRequestBody();
        //String reqBody = TheServer.readStream(is);

        //JSONObject json = new JSONObject(reqBody);


        for (Integer i : stateObservable.getPlayers()) {
            Integer accum = 0;
            if (i == null) {
                response = "{id: " + accum.toString() + "}";
            }
            accum++;
        }
        if (response == null) {
            response = "{id: \"rejected\"}";
        }

        t.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

}
