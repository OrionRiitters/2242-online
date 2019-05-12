

package gamelogic;

import java.util.ArrayList;

public class Collisions {

    Game game;
    Entities entities;

    ArrayList<Vessel> checkedVessels = new ArrayList<Vessel>();

    public Collisions(Game game) {
        this.game = game;
        entities = game.entities;
    }
    public void runAllCollisions() {
        ArrayList<Vessel> vl = entities.getVesselList();

        for (int i = 0 ; i < vl.size() ; i++) {
            Vessel vessel = vl.get(i);

            for (int k = i ; k < vl.size(); k++) {
                if (checkCollision(vessel, vl.get(k)) && vessel.getVesselID() != vl.get(k).getVesselID()) {
                    bounce(vessel, vl.get(k));
                    vl.get(k).collide(vessel);
                    vessel.collide(vl.get(k));
                }
            }

            ArrayList<Projectile> pl = entities.projectileList;

            for (Projectile p : pl) {

                if (checkCollision(vessel, p) && p.getVesselID() != vessel.getVesselID()) {
                    p.collide(vl.get(i));
                    p.setActive(false);
                }
            }
        }

    }
    // This method checks collisions between playerVessel and all unfriendly projectiles
 /*   public void runPlayerToProjectileCollisions(Vessel vessel, ArrayList<Projectile> projectileList ) {

        for (int i = 0 ; i < entities.projectileList.size() ; i++) {
            Projectile projectile = projectileList.get(i);
            if (checkCollision(vessel, projectile) && !projectile.isFriendly()) {
                projectile.collide(vessel);
                projectile.setActive(projectile.isAlwaysActive());
            }
        }

    }*/


    public boolean checkCollision(Entity e1, Entity e2) { // Check if two entities are colliding
       // System.out.println(e1.getMinX() + "-" + e1.getMaxX() + "-" + e1.getMinY() + "-" + e1.getMaxY());
       // System.out.println(e2.getMinX() + "-" + e2.getMaxX() + "-" + e2.getMinY() + "-" + e2.getMaxY());
        return ((e1.getMaxX() >= e2.getMinX() && e1.getMaxY() >= e2.getMinY()) &&
                (e1.getMinX() <= e2.getMaxX() && e1.getMinY() <= e2.getMaxY()));
    }

    public void bounce(Entity e1, Entity e2) {     // Moves two entities away from one another
        String relativeDirection = getRelativeDirection(e1, e2);
        Movement.move(e1, relativeDirection);
        Movement.move(e2, Movement.getOppositeDirection(relativeDirection));
    }

    public String getRelativeDirection(Entity e1, Entity e2) {
        // Returns e1's relative direction to e2 using
        String direction = "";                               // the cardinal directions
        float e1MidX = getEntityMidX(e1);
        float e1MidY = getEntityMidY(e1);
        float e2MidX = getEntityMidX(e2);
        float e2MidY = getEntityMidY(e2);

        direction += (e1MidY < e2MidY) ? Movement.N : Movement.S;
        direction += (e1MidX < e2MidX) ? Movement.W : Movement.E;

        return direction;
    }

    private float getEntityMidX(Entity entity) {                // Used to get relative direction
        return (((float)entity.getMaxX()) - ((float)entity.getMinX()) / 2f) +
                (float) entity.getMinX();
    }

    private float getEntityMidY(Entity entity) {
        return (((float)entity.getMaxY()) - ((float)entity.getMinY()) / 2f) +
                (float) entity.getMinY();
    }

}
