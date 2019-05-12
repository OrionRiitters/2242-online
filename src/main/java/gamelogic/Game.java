package gamelogic;

import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Game {

    final public int FRAME_WIDTH = 640;
    final public int FRAME_HEIGHT = 480;

    protected Integer[] players = new Integer[4];
    protected int frame = 0;

    public static boolean exitGame = false;

    Entities entities;
    LevelOne levelOne;

   Collisions collisions;

    public Game(){ // Game loop, calculates when to call update()
        entities = new Entities(this);
        levelOne = new LevelOne(this);
        collisions = new Collisions(this);

        exitGame = false;
        Vessel.nextVesselID = 0;
    }

    /* Adds all entity states to an ArrayList and returns it.
     */
    public ArrayList<Integer[]> getGameState() {

        ArrayList<Integer[]> allStates = new ArrayList<>();
        allStates.addAll(entities.getVesselStates());
        allStates.addAll(entities.getProjectileStates());
        return allStates;

    }

    protected void updateState() {
        entities.runRoutines();
        entities.purgeProjectiles();
        collisions.runAllCollisions();
        entities.purgeVessels();
        levelOne.updateTime(System.currentTimeMillis());
        levelOne.checkReleases();
    }

    protected int getFrame() {
        frame++;
        return frame;
    }

    protected void addPlayer(int id) {
        PlayerVessel pv = new PlayerVessel(
                 1, 1, 3, 1, 1, true,
                this, "N", 42, 33, id
        );
        entities.addVesselToList(pv);
        entities.addPlayerVesselToList(pv);

    }

    public Integer[] getPlayers() {
        return players;
    }

    public ArrayList<PlayerVessel> getPlayerVessels() {
        return entities.getPlayerVesselList();
    }


    /*
    KEEP FOR REFERENCE

    private void update() { // Call functions to update game here. This is called once every frame
        entities.runRoutines();
        collisions.runAllCollisions();
        collisions.runPlayerToProjectileCollisions(entities.getPlayerVessel(), entities.projectileList);
        entities.purgeProjectiles();
        entities.purgeVessels();
        levelOne.updateTime(timeStamp);
        levelOne.checkReleases();
    }

     */
}
