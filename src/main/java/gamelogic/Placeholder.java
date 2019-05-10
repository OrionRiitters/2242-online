package gamelogic;

import statemanager.StateObservable;

public class Placeholder {

    public static void consumeBuffer(String[] buffer) {
        Long ms = System.currentTimeMillis();
        String mString = ms.toString();

        StateObservable stateObservable = StateObservable.get_instance();

        for (String s : buffer) {
            if (s != null) {
                stateObservable.setGameState(s);
            }
        }

        if (stateObservable.getGameState() == null) {
            stateObservable.setGameState(mString);
        }

    }

}
