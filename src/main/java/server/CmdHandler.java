package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import statemanager.InputBuffer;
import statemanager.Scheduler;

import java.io.IOException;
import java.io.InputStream;

public class CmdHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();
        InputBuffer inputBuffer = InputBuffer.InputBuffer();

        int slot = inputBuffer.streamToBuffer(is);
        Scheduler scheduler = Scheduler.Scheduler();

        long now = System.currentTimeMillis();
        scheduler.writeResponse(slot, t, now);
    }
}
