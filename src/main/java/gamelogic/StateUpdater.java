package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONWriter;
import statemanager.StateObservable;
import org.json.JSONObject;

public class StateUpdater {

    private static Game game = new Game();

    /* Iterates over strings in inputBuffer, JSONifying their contents and pushing
     * the JSON to each relevant player.
     */
    public static void consumeBuffer(String[] buffer) {

        for (String s : buffer) {
            if (s != null) {
                JSONObject json = new JSONObject(s);
                int playerID = (json.getInt("id"));

                PlayerVessel pv;
                if (game.entities.getPlayerVessel(playerID) == null) {
                    game.addPlayer(playerID);
                }
                pv = game.entities.getPlayerVessel(playerID);

                updatePlayer(json, pv);
                addPlayerID(pv.getPlayerID());
            }
        }
        updateGameState();
    }
    /* Calls on game to update its state, then calls on Observable to update its own state.
     */
    private static void updateGameState() {
        StateObservable stateObservable = StateObservable.get_instance();
        Integer[] players = game.getPlayers();

        game.updateState();

        String state = writeStateJSON(game.getGameState());
        stateObservable.setPlayers(players);
        stateObservable.setGameState(state);

        if (stateObservable.getGameState() == null) {
            stateObservable.setGameState("Game state failed to set.");
        }
    }

    /* Documentation that is very helpful in understanding the JSONWriter object.
     * http://stleary.github.io/JSON-java/org/json/JSONWriter.html
     */
    private static String writeStateJSON(ArrayList<Integer[]> state) {
        StringBuilder outerString = new StringBuilder();
        JSONWriter outerWriter = new JSONWriter(outerString).array();
        /* Frame value is used in the client to order received game states
         * before the oldest one is rendered to canvas.
         */
        outerWriter.object().key("frame").value(game.getFrame()).endObject();

        for (Integer[] a : state) {
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

    private static void updatePlayer(JSONObject json, PlayerVessel pv) {
        pv.setCommands(json);
    }
}
