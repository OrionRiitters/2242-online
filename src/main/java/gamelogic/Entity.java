package gamelogic;

public abstract class Entity { // This abstract class will contain Vessel and Projectile.

    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int speed;
    private int collideDamage;
    private boolean active;
    private String direction;
    private boolean friendly;

    public Entity(int minX, int minY, int speed, int collideDamage, boolean active,
                  boolean friendly, String direction, int height, int width) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = minX + width;
        this.maxY = minY + height;
        this.collideDamage = collideDamage;
        this.active = active;
        this.speed = speed;
        this.friendly = friendly;
        this.direction = direction;
    }

    public boolean isFriendly() {return friendly;}

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getSpeed() {
        return speed;
    }

    public String getDirection() {
        return direction;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setDirection(String direction) {this.direction = direction;}

    public int getCollideDamage() {
        return collideDamage;
    }

    protected abstract void collide(Vessel v); // Implement this in child classes

    protected abstract void routine(); // Implement this in child classes


}