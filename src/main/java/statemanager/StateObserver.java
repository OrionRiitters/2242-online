package statemanager;

import java.util.Observable;
import java.util.Observer;

/* Updated when StateObserved's gameState changes.
 * TODO: This interface can be implemented in the scheduler class.
 */
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
