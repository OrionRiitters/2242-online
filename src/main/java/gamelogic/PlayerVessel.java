package gamelogic;

import org.json.JSONObject;

import java.util.HashMap;

public class PlayerVessel extends Vessel {

    private int[] position = new int[2];
    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private boolean keySpace;
    final int PROJECTILE_SPACING = 10;
    private int projectileAccum = 1;
    private int maxHealth;
    public HashMap<String, Boolean> commands = new HashMap<>(5);

    Game game;
    Entities entities;

    public PlayerVessel( int minX, int minY, int speed, int collideDamage, int health,
                         boolean active, Game game, String direction, int height,
                        int width, int playerID) {

        super(minX, minY, speed, collideDamage, health, active,  direction, height, width, playerID);

        position[0] = minX;
        position[1] = minY;
        this.game = game;
        maxHealth = health;
        this.playerID = playerID;
        entities = game.entities;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public boolean isPlayer() { return true; }

    public int[] getPosition() {
        position[0] = getMinX();
        position[1] = getMinY();

        return position;
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

     @Override
    protected void routine() { // Move direction based on keys pressed and player's position relative to the
        // frame
        if (commands.get("A")) {
            Movement.moveW(this, getMinX() > 0 ? getSpeed() : 0);
        }
        if (commands.get("D")) Movement.moveE(this,
                getMaxX() < game.FRAME_WIDTH ? getSpeed() : 0);
        if (commands.get("W")) Movement.moveN(this,
                getMinY() > 0 ? getSpeed() : 0);
        if (commands.get("S")) Movement.moveS(this,
                getMaxY() < game.FRAME_HEIGHT ? getSpeed() : 0);
        if (commands.get("space")) fire();
    }

    @Override
    protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

        entities.addProjectileToList(new Projectile(getMinX(), getMinY() + 9, 4,
                4, true, getVesselID(), 1000, game,
                Movement.N, 5, 5, false) {

            @Override
            public void routine() {
                Movement.moveN(this, getSpeed());
            }

            @Override
            protected void collide(Vessel v) {

                v.setHealth(v.getHealth() - getCollideDamage());
                if (v.getHealth() <= 0){
                    v.setActive(false);
                }
            }

        });
        entities.addProjectileToList(new Projectile(getMinX() + 28, getMinY() + 9,4,
                4, true, getVesselID(), 1000,game,
                Movement.N, 5, 5, false) {

            @Override
            public void routine() {
                Movement.moveN(this, getSpeed());
            }

            @Override
            protected void collide(Vessel v) {

                v.setHealth(v.getHealth() - getCollideDamage());
                if (v.getHealth() <= 0){
                    v.setActive(false);
                }
            }
        });
    }

    /* Initialize projectile, and add integer to projectileAccum.
     */
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