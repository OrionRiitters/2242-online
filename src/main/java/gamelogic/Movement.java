package gamelogic;

public class Movement {

    final static String N = "N";
    final static String E = "E";
    final static String S = "S";
    final static String W = "W";
    final static String NE = "NE";
    final static String SE = "SE";
    final static String SW = "SW";
    final static String NW = "NW";

    public static void move(Entity entity, String direction){
        if (direction.equals(E)) {
            moveE(entity, entity.getSpeed());
        } else if (direction.equals(S)) {
            moveS(entity, entity.getSpeed());
        } else if (direction.equals(W)) {
            moveW(entity, entity.getSpeed());
        } else if (direction.equals(N)) {
            moveN(entity, entity.getSpeed());
        } else if (direction.equals(NE)) {
            moveNE(entity, entity.getSpeed());
            entity.setDirection(NE);
        } else if (direction.equals(SE)) {
            moveSE(entity, entity.getSpeed());
        } else if (direction.equals(SW)) {
            moveSW(entity, entity.getSpeed());
        } else if (direction.equals(NW)) {
            moveNW(entity, entity.getSpeed());
        }
    }

    public static void moveE(Entity entity, int distance) {
        entity.setMinX(entity.getMinX() + distance);
        entity.setMaxX(entity.getMaxX() + distance);
        entity.setDirection(E);
    }

    public static void moveW(Entity entity, int distance) {
        entity.setMinX(entity.getMinX() - distance);
        entity.setMaxX(entity.getMaxX() - distance);
        entity.setDirection(W);
    }

    public static void moveS(Entity entity, int distance) {
        entity.setMinY(entity.getMinY() + distance);
        entity.setMaxY(entity.getMaxY() + distance);
        entity.setDirection(S);
    }

    public static void moveN(Entity entity, int distance) {
        entity.setMinY(entity.getMinY() - distance);
        entity.setMaxY(entity.getMaxY() - distance);
        entity.setDirection(N);
    }

    public static void moveNE(Entity entity, int distance) {
        moveN(entity, entity.getSpeed());
        moveE(entity, entity.getSpeed());
        entity.setDirection(NE);
    }

    public static void moveNW(Entity entity, int distance) {
        moveN(entity, entity.getSpeed());
        moveW(entity, entity.getSpeed());
        entity.setDirection(NW);
    }

    public static void moveSE(Entity entity, int distance) {
        moveS(entity, entity.getSpeed());
        moveE(entity, entity.getSpeed());
        entity.setDirection(SE);
    }

    public static void moveSW(Entity entity, int distance) {
        moveS(entity, entity.getSpeed());
        moveW(entity, entity.getSpeed());
        entity.setDirection(SW);
    }

    public static String getOppositeDirection (String direction) {
        if (direction.equals(E)) return W;
        else if (direction.equals(S)) return N;
        else if (direction.equals(W)) return E;
        else if (direction.equals(N)) return S;
        else if (direction.equals(NE)) return SW;
        else if (direction.equals(SE)) return NW;
        else if (direction.equals(SW)) return NE;
        else return SE;
    }

    protected static String randomDirection() throws NullPointerException {
        int randomNum = (int) Math.ceil(Math.random() * 8f); // Create random number 1-8, return random direction
        switch(randomNum) {
            case(1):
                return N;
            case(2):
                return NE;
            case(3):
                return E;
            case(4):
                return SE;
            case(5):
                return S;
            case(6):
                return SW;
            case(7):
                return W;
            case(8):
                return NW;
            default:
                return null;
        }
    }
}