package game;

public class Vessel extends Entity {

    private int health;
    private int vesselID;
    public static int nextVesselID = 0;

    public Vessel(int minX, int minY, int speed, int collideDamage, int health,
                   boolean active, boolean friendly, String direction, int height, int width) {

        super(minX, minY, speed, collideDamage, active, friendly, direction, height, width);

        this.health = health;
        vesselID = nextVesselID; // vesselID's will be used to associate vessels with their projectiles
        nextVesselID++;
    }

    public int getVesselID() { return vesselID; }

    public void setVesselID(int vesselID) { this.vesselID = vesselID; }

    public void setHealth(int i) {
        health = i;
    }

    public int getHealth() {
        return health;
    }

    @Override
    protected void collide(Vessel v) {
        v.setHealth(v.getHealth() - getCollideDamage());
        if (getHealth() <= 0) {setActive(false);}
    }

    protected void routine(){
        // Override this when instantiating a Vessel
    }
    protected void initializeProjectile() {}

}