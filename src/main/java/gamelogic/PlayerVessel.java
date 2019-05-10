package gamelogic;

public class PlayerVessel extends Vessel {

    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private boolean keySpace;
    final int PROJECTILE_SPACING = 10;
    private int projectileAccum = 0;
    private int maxHealth;

    Game game;
    Entities entities;

    public PlayerVessel(int minX, int minY, int speed, int collideDamage, int health,
                         boolean active, Game game, boolean friendly, String direction, int height,
                        int width) {

        super(minX, minY, speed, collideDamage, health, active, friendly, direction, height, width);

        this.game = game;
        maxHealth = health;

        entities = game.entities;
        keyLeft = false;
        keyRight = false;
        keyUp = false;
        keyDown = false;
        keySpace = false;

    }


    public void setProjectileAccum(int i) {
        projectileAccum = i;
    }

    public void setKeyLeft(boolean keyLeft) {
        this.keyLeft = keyLeft;
    }

    public void setKeyRight(boolean keyRight) {
        this.keyRight = keyRight;
    }

    public void setKeyUp(boolean keyUp) {
        this.keyUp = keyUp;
    }

    public void setKeyDown(boolean keyDown) {
        this.keyDown = keyDown;
    }

    public void setKeySpace(boolean keySpace) {
        this.keySpace = keySpace;
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

    @Override
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

    @Override
    protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

        entities.addProjectileToList(new Projectile(getMinX(), getMinY() + 9, 4,
                5, true, getVesselID(), game, true,
                Movement.N, false) {

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
                Movement.N, false) {

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