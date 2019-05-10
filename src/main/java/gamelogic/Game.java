package gamelogic;

import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ConcurrentModificationException;

public class Game {

    public static boolean exitGame = false;

    Entities entities;
    LevelOne levelOne;
   /* Collisions collisions; */

    protected void Game(){ // Game loop, calculates when to call update()
        entities = new Entities(this);
        levelOne = new LevelOne(this);
     /*   collisions = new Collisions(this); */

        exitGame = false;
        Vessel.nextVesselID = 0;
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
