package statemanager;

import java.util.Observable;

public class StateObservable extends Observable {

    private String gameState;

    public void setGameState(String gameState) {
        this.gameState = gameState;
        setChanged();
        notifyObservers(gameState);
    }




}
