package statemanager;

import Game.Placeholder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Scheduler extends Thread {

    /* Observer interface must be decoupled from Scheduler because
     * a different Observer is used for each HttpExchange and
     * Scheduler manages the HttpExchanges.
     */
    private static Scheduler scheduler_instance = null;
    private InputBuffer inputBuffer = InputBuffer.InputBuffer();
    private StateObservable stateObservable = new StateObservable();
    private StateObserver[] observers = new StateObserver[4];

    /* This class implements the singleton design pattern
     */
    public static Scheduler Scheduler() {

        if (scheduler_instance == null) {
            scheduler_instance = new Scheduler();
            scheduler_instance.createObservers();
            scheduler_instance.attachObservers();

        }
        return scheduler_instance;
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
     * Waits for StateObserver to acquire game state from StateObserved,
     * then writes that state to response in HttpExchange.
     */
    public void writeResponse(int slot, HttpExchange t) {
        StateObserver observer = observers[slot];
        while (observer.getGameState() == null) {
            // Wait
        }
        String response = observer.getGameState();
        System.out.println(response);
        try {
            t.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Output stream closed?");
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            observer.resetGameState();
        }

    }

    public void updateObservable() {
        Long ms = System.currentTimeMillis();
        String mString = ms.toString();
        stateObservable.setGameState(mString);
    }

    public void bufferToGame() {
        updateObservable();
        inputBuffer.flush();

    }

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
