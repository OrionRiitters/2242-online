import server.TheServer;
import server.TheServer2;
import statemanager.Scheduler;

public class Main {

    public static void main(String[] args) {

        try {
            TheServer server = new TheServer();
            server.start();

            Scheduler scheduler = Scheduler.get_instance();
            scheduler.initializeGame();
            scheduler.run();
        } catch (Exception e) {
            Main.reinitialize();

        }
    }

    private static void reinitialize() {
        try {
            TheServer server = new TheServer();
            server.start();

            Scheduler scheduler = Scheduler.get_instance();
            scheduler.initializeGame();
            scheduler.run();
        } catch (Exception e) {
            Main.reinitialize();
        }

    }

}
