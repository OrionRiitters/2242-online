package statemanager;

import gamelogic.Placeholder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Observer;

public class Scheduler extends Thread {

    /* Observer interface must be decoupled from Scheduler because
     * a different Observer is used for each HttpExchange and
     * Scheduler manages the HttpExchanges.
     */
    private static Scheduler _instance = null;
    private InputBuffer inputBuffer = InputBuffer.get_instance();
    private StateObservable stateObservable = StateObservable.get_instance();
    private StateObserver[] observers = new StateObserver[4];

    /* This class implements the singleton design pattern
     */
    public static Scheduler get_instance() {

        if (_instance == null) {
            _instance = new Scheduler();
            _instance.createObservers();
            _instance.attachObservers();

        }
        return _instance;
    }

    public void createObservers() {
        Arrays.fill(observers, new StateObserver());
    }

    public void attachObservers() {
        for (Observer o : observers) {
            stateObservable.addObserver(o);
        }
    }

    /* Acquires observer location via slot and HttpExchange from HttpHandler.
     * Waits for StateObserver to acquire gamelogic state from StateObserved,
     * then writes that state to response in HttpExchange.
     */
    public void writeResponse(int slot, HttpExchange t, long then) throws IOException {
        StateObserver observer = observers[slot];
        String response;

        /* If gamelogic state was never updated, timeout after 200 ms
         */
        while (observer.getGameState() == null) {
            long now = System.currentTimeMillis();
            if (now - then > 200) break;
        }

        /* Set response to something else when gameState update times out
         */
        response = observer.getGameState() == null ? "Observer timeout" : observer.getGameState();
        try {
            t.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            t.getResponseBody().write(response.getBytes());
        } catch (IOException e) {
            System.out.println(e);

        } finally {
            t.getResponseBody().close();
            observer.resetGameState();
        }
    }
    /* Called by scheduler thread to push client commands to gamelogic
     */
    public void bufferToGame() {
        Placeholder.consumeBuffer(inputBuffer.getBuffer());
        inputBuffer.flush();
    }
    /* Thread run by main to push commands to gamelogic
     */
    @Override
    public void run() {
        long then = System.currentTimeMillis();
        long now = System.currentTimeMillis();

        while(true) {
            while(now - then < 200) {
                now = System.currentTimeMillis();
            }
            then = now;
            bufferToGame();
        }
    }

}
