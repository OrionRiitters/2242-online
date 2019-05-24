package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import statemanager.InputBuffer;
import statemanager.Scheduler;

import java.io.IOException;
import java.io.InputStream;

/* Takes input stream, writes it to input buffer, then calls on Scheduler to write a response.
 */
public class CmdHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        InputBuffer inputBuffer = InputBuffer.get_instance();

        int slot = inputBuffer.streamToBuffer(is);
        Scheduler scheduler = Scheduler.get_instance();

        scheduler.writeResponse(slot, t);
    }
}
