import server.TheServer;
import statemanager.Scheduler;

public class Main {

    public static void main(String[] args) {
        TheServer server = new TheServer();
        server.start();

        Scheduler scheduler = Scheduler.Scheduler();
        scheduler.run();

    }

}
