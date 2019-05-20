package gamelogic;

import java.util.ArrayList;

public class Entities {

    Game game;
    final int ID = 0;
    final int X = 1;
    final int Y = 2;

    /* Creates Arraylists of all entities. They are separated into 3 different subclass lists.
    */
    public ArrayList<Vessel> vesselList = new ArrayList<Vessel>(); // Lists to contain all entities
    public ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    private ArrayList<PlayerVessel> playerVesselList = new ArrayList<>();

    public Entities(Game game) {
        this.game = game;
    }

    protected void addVesselToList(Vessel v) {
        vesselList.add(v);
    }

    protected void addPlayerVesselToList(PlayerVessel pv) {
        playerVesselList.add(pv);
    }

    protected void addProjectileToList(Projectile p) {
        projectileList.add(p);
    }

    protected ArrayList<PlayerVessel> getPlayerVesselList() { return playerVesselList; };

    protected ArrayList<Vessel> getVesselList() {
        return vesselList;
    };


    /* This returns the "state" of player vessels. The states consist of what is needed
     * for the client to render a graphic for each player vessel (ID, minX and minY)
     */
    protected ArrayList<Integer[]> getVesselStates() {
        ArrayList<Integer[]> vesselStates = new ArrayList<>();

        for (Vessel v : vesselList) {
            Integer[] state = new Integer[3];

            state[ID] = v.getPlayerID();
            state[X] = v.getMinX();
            state[Y] = v.getMinY();
            vesselStates.add(state);
        }
        return vesselStates;
    }

    protected PlayerVessel getPlayerVessel(int id) {
        for (PlayerVessel pv : playerVesselList) {
            if (pv.getPlayerID() == id) {
                return pv;
            }
        }
        return null;
    }

    protected ArrayList<Integer[]> getProjectileStates() {
        ArrayList<Integer[]> projectileStates = new ArrayList<>();

        for (Projectile p: projectileList) {
            Integer[] state = new Integer[3];
            state[ID] = p.getProjectileID();
            state[X] = p.getMinX();
            state[Y] = p.getMinY();
            projectileStates.add(state);
        }
        return projectileStates;

    }

    /* For each active projectile in bounds, add to buffer list, then clear projectile list and add buffer list
     * to projectile list.
     */
   protected void purgeProjectiles() {
        ArrayList<Projectile> projectileListBuffer = new ArrayList<Projectile>();
        ArrayList<Projectile> projectileListCopy = new ArrayList<Projectile>(projectileList);

        for (Projectile p : projectileList) {
            if (!((p.getMaxX() < 0 || p.getMinX() > game.FRAME_WIDTH) || (p.getMaxY() < 0 ||
                    p.getMinY() > game.FRAME_HEIGHT)) && p.getActive()) {

                p.setProjectileIndex(projectileListBuffer.size());
                projectileListBuffer.add(p);
            }
        }
        projectileList.removeAll(projectileListCopy);
        projectileList.addAll(projectileListBuffer);
    }

    /* For each active vessel, add to buffer list, clear vessel list, and add buffer list to vessel list.
     */
    protected void purgeVessels() {
        ArrayList<Vessel> vesselListBuffer = new ArrayList<Vessel>();
        ArrayList<Vessel> vesselListCopy = new ArrayList<Vessel>(vesselList);

        for (Vessel v : vesselList) {
            if (v.getActive()) {

                v.setVesselID(vesselListBuffer.size());
                vesselListBuffer.add(v);
            }
        }
        vesselList.removeAll(vesselListCopy);
        vesselList.addAll(vesselListBuffer);
    }

    protected void runRoutines() {  // Runs routines of all entities

        for (Vessel v : vesselList) {
            v.routine();
        }
        for (Projectile p : projectileList) {
            p.routine();
        }

    }


    protected void createEnemy1(int minX, int minY) {  // Creates anonymous subclass of vessel

        addVesselToList(new Vessel(minX, minY,2, 2,
                100, true, Movement.E, 30, 36, 100) {

            //String direction = "right";
            int frameWidth = game.FRAME_WIDTH;
            int frameHeight = game.FRAME_HEIGHT;
            int frame = 0;


            @Override
            protected void routine() { // If ship is out of bounds, send it back in bounds

                boolean OOB = false;
                frame = frame <= 64 ? frame + 1 : 0;

                if (getMaxX() > frameWidth) {
                    Movement.moveW(this, getSpeed());
                    OOB = true;
                }
                else if (getMinX() < 0) {
                    Movement.moveE(this, getSpeed());
                    OOB = true;
                }
                if (getMaxY() > frameHeight) {
                    Movement.moveN(this, getSpeed());
                    OOB = true;
                }
                else if (getMinY() < 0) {
                    Movement.moveS(this, getSpeed());
                    OOB = true;
                }

                if (!OOB) {
                    Movement.move(this, this.getDirection());
                    System.out.println(this.getDirection());
                }

                if (frame == 0) {
                    initializeProjectile();
                }
            }

            @Override
            protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

                addProjectileToList(new Projectile(getMinX() + 14, getMaxY() - 5, 4,
                        25, true, getVesselID(), 1001, game,
                        Movement.N,  5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveS(this, getSpeed());
                    }

                });
            }
        });
    }

    protected void createEnemy2(int minX, int minY) { // Creates anonymous subclass of vessel

        addVesselToList(new Vessel(minX, minY,2, 2,
                75, true, Movement.E, 30, 36, 101) {

            //String direction = "right";
            int frameWidth = game.FRAME_WIDTH;
            int frameHeight = game.FRAME_HEIGHT;
            int frame = 0;

            @Override
            protected void routine() { // If ship is out of bounds, send it back in bounds

                boolean OOB = false;
                frame = frame <= 255 ? frame + 1 : 0;

                if (getMaxX() > frameWidth) {
                    Movement.moveW(this, getSpeed());
                    OOB = true;
                }
                else if (getMinX() < 0) {
                    Movement.moveE(this, getSpeed());
                    OOB = true;
                }
                if (getMaxY() > frameHeight) {
                    Movement.moveN(this, getSpeed());
                    OOB = true;
                }
                else if (getMinY() < 0) {
                    Movement.moveS(this, getSpeed());
                    OOB = true;
                }

                if (!OOB && frame != 256) {
                    Movement.move(this, this.getDirection());
                } else if (!OOB) {
                    Movement.move(this, Movement.randomDirection());
                }

                if (frame % 31 == 0) {
                    initializeProjectile();
                }
            }

            @Override
            protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

                addProjectileToList(new Projectile(getMinX() + 14, getMaxY() - 5, 5,
                        20, true, getVesselID(), 1001,  game,
                        Movement.S, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveS(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMinX() + 20, getMaxY() - 5, 5,
                        20, true, getVesselID(), 1001, game,
                        Movement.S, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveS(this, getSpeed());
                    }

                });
            }
        });
    }

    protected void createEnemy3(int minX, int minY) { // Creates anonymous subclass of vessel

        addVesselToList(new Vessel(minX, minY,1, 10,
                75,true, Movement.E, 30, 36, 102) {

            //String direction = "right";
            int frameWidth = game.FRAME_WIDTH;
            int frameHeight = game.FRAME_HEIGHT;
            int frame = 0;

            @Override
            protected void routine() { // If ship is out of bounds, send it back in bounds

                boolean OOB = false;
                frame = frame <= 64 ? frame + 1 : 0;

                if (getMaxX() > frameWidth) {
                    Movement.moveW(this, getSpeed());
                    OOB = true;
                }
                else if (getMinX() < 0) {
                    Movement.moveE(this, getSpeed());
                    OOB = true;
                }
                if (getMaxY() > frameHeight) {
                    Movement.moveN(this, getSpeed());
                    OOB = true;
                }
                else if (getMinY() < 0) {
                    Movement.moveS(this, getSpeed());
                    OOB = true;
                }

                if (!OOB) {
                    Movement.move(this, this.getDirection());
                }

                if (frame == 0) {
                    initializeProjectile();
                }
            }

            @Override
            protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

                addProjectileToList(new Projectile(getMinX() + 10, getMinY(), 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.N, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveN(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMaxX(), getMinY(), 2,
                        30, true, getVesselID(), 1001,  game,
                        Movement.NE, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveNE(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMaxX(), getMinY() + 10, 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.E, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveE(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMaxX(), getMaxY(), 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.SE, 5, 5,false) {

                    @Override
                    public void routine() {
                        Movement.moveSE(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMinX() + 10, getMaxY(), 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.S, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveS(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMinX(), getMaxY(), 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.SW, 5, 5,false) {

                    @Override
                    public void routine() {
                        Movement.moveSW(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMinX(), getMinY() + 10, 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.W, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveW(this, getSpeed());
                    }

                });

                addProjectileToList(new Projectile(getMinX(), getMinY(), 2,
                        30, true, getVesselID(), 1001, game,
                        Movement.NW, 5, 5, false) {

                    @Override
                    public void routine() {
                        Movement.moveNW(this, getSpeed());
                    }

                });
            }
        });
    }

    public void createBoss1(int minX, int minY) { // Creates anonymous subclass of vessel

        addVesselToList(new Vessel(minX, minY,1, 50,
                1000,true,  Movement.E, 61, 85, 103) {

            //String direction = "right";
            int frameWidth = game.FRAME_WIDTH;
            int frameHeight = game.FRAME_HEIGHT;
            int frame = 0;
            //boolean beamSwitch = true;

            @Override
            protected void routine() { // If ship is out of bounds, send it back in bounds

                boolean OOB = false;
                frame = frame <= 511 ? frame + 1 : 0;

                if (getMaxX() > frameWidth) {
                    Movement.moveW(this, getSpeed());
                    OOB = true;
                } else if (getMinX() < 0) {
                    Movement.moveE(this, getSpeed());
                    OOB = true;
                }
                if (getMaxY() > frameHeight) {
                    Movement.moveN(this, getSpeed());
                    OOB = true;
                } else if (getMinY() < 0) {
                    Movement.moveS(this, getSpeed());
                    OOB = true;
                }

                if (!OOB && frame != 128) {
                    Movement.move(this, this.getDirection());
                } else if (!OOB) {
                    Movement.move(this, Movement.randomDirection());
                }
                initializeProjectile();
            }

            @Override
            protected void initializeProjectile() {  // This creates two new projectiles and adds them to entities.projectilesList

                if (frame % 8 == 0 && frame < 255) {
                    addProjectileToList(new Projectile(getMinX(), getMaxY() - 5, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveS(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMinX() + 16, getMaxY() - 5, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveS(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMaxX() - 5, getMaxY() - 5, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveS(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMaxX() - 19, getMaxY() - 5, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveS(this, getSpeed());
                        }
                    });
                }

                if (frame % 8 == 0 && frame > 320) {
                    addProjectileToList(new Projectile(getMaxX() - 20, getMaxY() - 46, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveE(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMaxX() - 20, getMaxY() - 57, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveE(this, getSpeed());
                        }

                    });                    addProjectileToList(new Projectile(getMaxX() - 20, getMaxY() - 46, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveE(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMaxX() - 20, getMaxY() - 57, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveE(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMinX() + 18, getMaxY() - 46, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false){

                        @Override
                        public void routine() {
                            Movement.moveW(this, getSpeed());
                        }

                    });

                    addProjectileToList(new Projectile(getMinX() + 18, getMaxY() - 57, 5,
                            17, true,
                            getVesselID(), 1001, game,  Movement.S, 5, 5,false) {

                        @Override
                        public void routine() {
                            Movement.moveW(this, getSpeed());
                        }

                    });
                }


                if ( 155 < frame && frame < 430) {

                    addProjectileToList(new Projectile(getMinX() + 36, getMaxY() - 38, 7,
                            1, true,
                            getVesselID(), 1002, game,  Movement.S, 10, 10, true) {

                        @Override
                        public void routine() {
                            Movement.moveS(this, getSpeed());
                        }
                    });
                }
            }
        });
    }
}