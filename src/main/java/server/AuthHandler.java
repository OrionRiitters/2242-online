package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONWriter;
import statemanager.StateObservable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* This class accesses current player ID's from StateObservable and
 * if there are less than 4 current players, writes a new ID to the output stream.
 */
public class AuthHandler implements HttpHandler {
    StateObservable stateObservable = StateObservable.get_instance();

    public void handle(HttpExchange t) throws IOException {

        String response = null;
        InputStream is = t.getRequestBody();

        Integer accum = 0;
        for (Integer i : stateObservable.getPlayers()) {
            if (i == null) {
                StringBuilder string = new StringBuilder();
                JSONWriter writer = new JSONWriter(string).object();
                writer.key("id").value(accum).endObject();

                response = string.toString();
                break;
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
