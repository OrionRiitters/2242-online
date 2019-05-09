package statemanager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

public class StateObserver implements Observer {

    private String gameState = null;

    @Override
    public void update(Observable scheduler, Object state) {
        gameState = state.toString();
    }

    public String getGameState() {
        return gameState;
    }

    public void resetGameState() {
        this.gameState = null;
    }

}
