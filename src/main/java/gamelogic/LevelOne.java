package gamelogic;

public class LevelOne {

    Game game;
    Entities entities;
    long timeDifference;
    long levelTime = 0;

    int nextRelease = 0; // Sets up variables to control enemy release times
    long[] enemyReleaseTimes = {10000, 20000, 35000, 50000, 65000, 0};


    public LevelOne(Game game) {
        this.game = game;
        entities = game.entities;
        timeDifference = System.currentTimeMillis();
    }


    protected void updateTime(long timeStamp) {
        levelTime = timeStamp - timeDifference;
    }

    protected void checkReleases() { // After x amount of milliseconds, release next wave of enemies
        if (levelTime > enemyReleaseTimes[nextRelease]) {
            switch(nextRelease) {
                case 0:
                    firstRelease();
                    break;
                case 1:
                    secondRelease();
                    break;
                case 2:
                    thirdRelease();
                    break;
                case 3:
                    fourthRelease();
                    break;
                case 4:
                    fifthRelease();
                    break;
                case 5:
                    if (entities.vesselList.size() == 1) {
                        Game.exitGame = true;
                    }
            }

            if (nextRelease < 5) {
                nextRelease++;
            } else {
                nextRelease = 0;
                timeDifference = System.currentTimeMillis();

            }
        }
    }

    private void firstRelease() {  // Each of these releases are called above by checkReleases().
        entities.createEnemy1(-100, 100);
        entities.createEnemy1(-50, 50);
        entities.createEnemy1(-80, 80);
        entities.createEnemy1(-110, 20);
        entities.createEnemy1(-140, 50);
        entities.createEnemy1(-170, 80);

    }

    private void secondRelease() {

        entities.createEnemy2(-30, 20);
        entities.createEnemy2(-500, 50);

        entities.createEnemy2(game.FRAME_WIDTH + 1200, 20);
        entities.createEnemy2(game.FRAME_WIDTH + 1300, 50);
        entities.createEnemy2(game.FRAME_WIDTH + 1450, 20);
        entities.createEnemy2(game.FRAME_WIDTH + 1550, 50);

    }

    private void thirdRelease() {

        entities.createEnemy1(-30, 20);
        entities.createEnemy2(-100, 40);
        entities.createEnemy3(-500, 60);
        entities.createEnemy3(game.FRAME_WIDTH + 500, 10);

    }

    private void fourthRelease() {

        entities.createEnemy1(-33, 24);
        entities.createEnemy1(game.FRAME_WIDTH + 300, 50);
        entities.createEnemy1(-80, 80);
        entities.createEnemy1(game.FRAME_WIDTH + 300, 20);
        entities.createEnemy1(-140, 50);
        entities.createEnemy1(-170, 10);

        entities.createEnemy1(-30, 20);
        entities.createEnemy2(-100, 40);
        entities.createEnemy3(-500, 60);
        entities.createEnemy3(game.FRAME_WIDTH + 500, 10);

    }

    private void fifthRelease() {

        entities.createBoss1(game.FRAME_WIDTH / 2, -100);

    }
}