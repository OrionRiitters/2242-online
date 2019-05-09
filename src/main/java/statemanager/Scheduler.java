package statemanager;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class Scheduler {

    private Observable stateObservable = new StateObservable();
    private Observer[] observers = new Observer[4];

    public void Scheduler() {
        createObservers();
    }

    public void createObservers() {
        Arrays.fill(observers, new StateObserver());
    }

}
