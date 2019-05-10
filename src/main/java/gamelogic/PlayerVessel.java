package gamelogic;

import org.json.JSONObject;

import java.util.HashMap;

public class PlayerVessel extends Vessel {

    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private boolean keySpace;
    final int PROJECTILE_SPACING = 10;
    private int projectileAccum = 0;
    private int maxHealth;
    public int playerID;
    public HashMap<String, Boolean> commands;

    Game game;
    Entities entities;

    public PlayerVessel( int playerID, int minX, int minY, int speed, int collideDamage, int health,
                         boolean active, Game game, boolean friendly, String direction, int height,
                        int width) {

        super(minX, minY, speed, collideDamage, health, active, friendly, direction, height, width);

        this.game = game;
        maxHealth = health;
        this.playerID = playerID;

        entities = game.entities;
        keyLeft = false;
        keyRight = false;
        keyUp = false;
        keyDown = false;
        keySpace = false;
    }




    public boolean isKeyLeft() {
        return keyLeft;
    }  // Might not need getters

    public boolean isKeyRight() {
        return keyRight;
    }

    public boolean isKeyUp() {
        return keyUp;
    }

    public boolean isKeyDown() {
        return keyDown;
    }

    public boolean isKeySpace() {
        return keySpace;
    }


    /* The boolean values of 'commands' will be used in routine() to update this object's state.
     * 'ce' is space (it was easier to write the key listeners in index.html by using this name for space).
     */
    protected void setCommands(JSONObject json) {
        commands.put("W", json.getBoolean("W"));
        commands.put("A", json.getBoolean("A"));
        commands.put("S", json.getBoolean("S"));
        commands.put("D", json.getBoolean("D"));
        commands.put("space", json.getBoolean("ce"));
    }

    /* @Override
    protected void routine() { // Move direction based on keys pressed and player's position relative to the
        // frame

        if (isKeyLeft()) Movement.moveW(this,
                getMinX() > 0 ? getSpeed() : 0);
        if (isKeyRight()) Movement.moveE(this,
                getMaxX() < gui.FRAME_WIDTH ? getSpeed() : 0);
        if (isKeyUp()) Movement.moveN(this,
                getMinY() > 0 ? getSpeed() : 0);
        if (isKeyDown()) Movement.moveS(this,
                getMaxY() < gui.FRAME_HEIGHT ? getSpeed() : 0);
        if (isKeySpace()) fire();



    }


     */

    @Override
    protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

        entities.addProjectileToList(new Projectile(getMinX(), getMinY() + 9, 4,
                5, true, getVesselID(), game, true,
                Movement.N, 34, 23, false) {

            @Override
            public void routine() {
                Movement.moveN(this, getSpeed());
            }

            @Override
            protected void collide(Vessel v) {

                v.setHealth(v.getHealth() - getCollideDamage());
                if (v.getHealth() <= 0 && v.getVesselID() != 0){
                    v.setActive(false);
                }
            }

        });
        entities.addProjectileToList(new Projectile(getMinX() + 28, getMinY() + 9,4,
                5, true, getVesselID(), game, true,
                Movement.N, 54, 76, false) {

            @Override
            public void routine() {
                Movement.moveN(this, getSpeed());
            }

            @Override
            protected void collide(Vessel v) {

                v.setHealth(v.getHealth() - getCollideDamage());
                if (v.getHealth() <= 0 && v.getVesselID() != 0){
                    v.setActive(false);
                }
            }
        });
    }

    protected void fire() { // Space out projectile initialization
        projectileAccum++;

        if (projectileAccum == PROJECTILE_SPACING) {
            initializeProjectile();
            projectileAccum = 0;
        }
    }

    @Override
    protected void collide(Vessel v) {
        v.setHealth(v.getHealth() - getCollideDamage());
    }

}