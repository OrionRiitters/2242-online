package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

import statemanager.StateObservable;
import org.json.JSONObject;

public class Placeholder {

    private static Game game = new Game();

    public static void consumeBuffer(String[] buffer) {
        Long ms = System.currentTimeMillis();
        String mString = ms.toString();

        StateObservable stateObservable = StateObservable.get_instance();

        for (String s : buffer) {

            
            if (s != null) {
                updatePlayerState(s);
                HashMap<String, Boolean> commands =
                stateObservable.setGameState(commands == null ? null : commands.toString());
            }
        }

        if (stateObservable.getGameState() == null) {
            stateObservable.setGameState(mString);
        }

    }


    /* Documentation for org.json can be found at http://stleary.github.io/JSON-java/index.html
     */
    private static void updatePlayerState(String s) {

        JSONObject json = new JSONObject(s);
        ArrayList<PlayerVessel> players = game.entities.getPlayerVesselList();

        for (PlayerVessel pv : players) {
            if (pv.getVesselID() == json.getDouble("id")) {
                pv.setCommands(json);

            }
        }

    }
}
