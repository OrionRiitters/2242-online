package gamelogic;

import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ConcurrentModificationException;

public class Game {

    final public int FRAME_WIDTH = 640;
    final public int FRAME_HEIGHT = 480;

    public static boolean exitGame = false;

    Entities entities;
    LevelOne levelOne;
   /* Collisions collisions; */

    public Game(){ // Game loop, calculates when to call update()
        entities = new Entities(this);
        levelOne = new LevelOne(this);

        addPlayer(1);
     /*   collisions = new Collisions(this); */

        exitGame = false;
        Vessel.nextVesselID = 0;
    }

    protected void addPlayer(int id) {
        PlayerVessel pv = new PlayerVessel(
                id, 1, 1, 1, 1, 1, true,
                this, true, "N", 4, 5
        );
        entities.addPlayerVesselToList(pv);

    }

    /*
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
