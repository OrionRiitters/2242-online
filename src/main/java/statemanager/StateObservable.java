package statemanager;

import java.util.Observable;

public class StateObservable extends Observable {


    private String gameState;
    private Integer[] players = new Integer[4];


    static StateObservable _instance = null;
    /* This class implements the singleton design pattern
     */
    public static StateObservable get_instance() {

        if (_instance == null) {
            _instance = new StateObservable();
        }
        return _instance;
    }


    public void setGameState(String gameState) {
        this.gameState = gameState;
        setChanged();
        notifyObservers(gameState);
    }

    public void setPlayers(Integer[] playerIDArray) {
        for (int i = 0 ; i < 4 ; i++) {
            if (playerIDArray[i] != null) {
                System.out.println(playerIDArray[i]);
                players[i] = playerIDArray[i];
            } else {
                System.out.println("/" + i);
            }
        }
    }

    public String getGameState() { return gameState; }

    public Integer[] getPlayers() {
        return players;
    }






}
