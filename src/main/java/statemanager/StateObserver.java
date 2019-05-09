package statemanager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

public class StateObserver implements Observer {

    private OutputStream os;

    @Override
    public void update(Observable scheduler, Object state) {
        String stateStr = state.toString();
        try {
            os.write(stateStr.getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

}
