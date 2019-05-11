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
                JSONObject json = new JSONObject(s);
                PlayerVessel pv = game.entities.getPlayerVessel(json.getInt("id"));

                updatePlayerState(json, pv);
                game.entities.runRoutines();
                String pos = "";
                for (int i : pv.getPosition()) {
                    pos += (i + " ");
                }
                stateObservable.setGameState(pos);
            }
        }

        if (stateObservable.getGameState() == null) {
            stateObservable.setGameState(mString);
        }

    }


    /* Documentation for org.json can be found at http://stleary.github.io/JSON-java/index.html
     */
    private static void updatePlayerState(JSONObject json, PlayerVessel pv) {
        pv.setCommands(json);
    }
}
