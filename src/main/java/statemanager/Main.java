package statemanager;

public class Main {

    public static void main(String[] args) {
        Scheduler scheduler = Scheduler.Scheduler();

        long then = System.currentTimeMillis();
        long now = System.currentTimeMillis();

        while(true) {
            while(now - then < 200) {
                now = System.currentTimeMillis();
            }
            scheduler.updateObservable();
        }
    }
}
