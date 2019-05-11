package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONWriter;
import statemanager.StateObservable;
import org.json.JSONObject;

public class Placeholder {

    private static Game game = new Game();

    public static void consumeBuffer(String[] buffer) {

        for (String s : buffer) {
            if (s != null) {
                JSONObject json = new JSONObject(s);
                int playerID = json.getInt("id");

                PlayerVessel pv = game.entities.getPlayerVessel(playerID);

                updatePlayer(json, pv);
                addPlayerID(pv.getPlayerID());
                updateGameState();
            }
        }
    }

    private static void updateGameState() {
        StateObservable stateObservable = StateObservable.get_instance();
        game.entities.runRoutines();

        Integer[] players = game.getPlayers();

        String state = writeStateJSON(game.getGameState());

        stateObservable.setGameState(state);
        if (stateObservable.getGameState() == null) {
            stateObservable.setGameState("Game state failed to set.");
        }
    }

    /*  Documentation that is very helpful in understanding the JSONWriter object.
     * http://stleary.github.io/JSON-java/org/json/JSONWriter.html
     */
    private static String writeStateJSON(ArrayList<Integer[]> state) {
        StringBuilder outerString = new StringBuilder();
        JSONWriter outerWriter = new JSONWriter(outerString).array();

        for (Integer[] a : state) {
          //  StringBuilder innerString = new StringBuilder();
            JSONWriter innerWriter = outerWriter.array();
            for (Integer i : a) {
                innerWriter.value(i);
            }
            innerWriter.endArray();

        }
        outerWriter.endArray();
        return outerString.toString();
    }

    private static void addPlayerID(int id) {
        game.players[id] = id;
    }


    /* Documentation for org.json can be found at http://stleary.github.io/JSON-java/index.html
     */
    private static void updatePlayer(JSONObject json, PlayerVessel pv) {
        pv.setCommands(json);
    }
}
