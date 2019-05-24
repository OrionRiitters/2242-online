

package gamelogic;

import java.util.ArrayList;

/*
 */
public class Collisions {

    Game game;
    Entities entities;

    public Collisions(Game game) {
        this.game = game;
        entities = game.entities;
    }

    /* Checks collisions between all entities and calls collide() between those entities.
     * TODO: Break method into two separate methods: projectile/vessel collisions and vessel/vessel collisions.
     */
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

    /* Check if two entities are colliding
     */
    public boolean checkCollision(Entity e1, Entity e2) {
        return ((e1.getMaxX() >= e2.getMinX() && e1.getMaxY() >= e2.getMinY()) &&
                (e1.getMinX() <= e2.getMaxX() && e1.getMinY() <= e2.getMaxY()));
    }

    /* Moves two entities away from one another
     */
    public void bounce(Entity e1, Entity e2) {
        String relativeDirection = getRelativeDirection(e1, e2);
        Movement.move(e1, relativeDirection);
        Movement.move(e2, Movement.getOppositeDirection(relativeDirection));
    }

    /* Returns e1's relative direction to e2 using the cardinal directions
     */
    public String getRelativeDirection(Entity e1, Entity e2) {
        String direction = "";
        float e1MidX = getEntityMidX(e1);
        float e1MidY = getEntityMidY(e1);
        float e2MidX = getEntityMidX(e2);
        float e2MidY = getEntityMidY(e2);

        direction += (e1MidY < e2MidY) ? Movement.N : Movement.S;
        direction += (e1MidX < e2MidX) ? Movement.W : Movement.E;

        return direction;
    }

    private float getEntityMidX(Entity entity) {
        return (((float)entity.getMaxX()) - ((float)entity.getMinX()) / 2f) +
                (float) entity.getMinX();
    }

    private float getEntityMidY(Entity entity) {
        return (((float)entity.getMaxY()) - ((float)entity.getMinY()) / 2f) +
                (float) entity.getMinY();
    }

}
