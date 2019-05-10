package statemanager;

import java.util.Observable;

public class StateObservable extends Observable {

    static StateObservable _instance = null;
    /* This class implements the singleton design pattern
     */
    public static StateObservable get_instance() {

        if (_instance == null) {
            _instance = new StateObservable();
        }
        return _instance;
    }

    private String gameState;

    public void setGameState(String gameState) {
        this.gameState = gameState;
        setChanged();
        notifyObservers(gameState);
    }

    public String getGameState() { return gameState; }






}
