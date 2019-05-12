package gamelogic;

import java.awt.image.BufferedImage;

public class Projectile extends Entity {

    private int vesselID;
    private int projectileIndex;
    private boolean alwaysActive;
    private int projectileID;
    Game game;
    Entities entities;

    public Projectile(int minX, int minY, int speed, int collideDamage,
                      boolean active, int vesselID, int projectileID, Game game, String direction,
                      int height, int width, boolean alwaysActive) {

        super(minX, minY, speed, collideDamage, active, direction, height, width);
        this.vesselID = vesselID;
        this.projectileID = projectileID;
        this.game = game;
        this.alwaysActive = alwaysActive;
        entities = game.entities;
        projectileIndex = entities.projectileList.size();

    }

    public boolean isAlwaysActive() { return alwaysActive; } // If always active, projectiles do not disappear
    // upon collision
    public void setProjectileIndex(int i) {
        projectileIndex = i;
    }

    public int getProjectileID() { return projectileID; }

    public int getVesselID() { return vesselID; }


    @Override
    protected void collide(Vessel v) {
        v.setHealth(v.getHealth() - getCollideDamage());
        if (v.getHealth() <= 0 && v.getVesselID() != vesselID){ v.setActive(false); }
    }


    protected void routine() {
        // Override this when instantiating a Projectile
    }
}
