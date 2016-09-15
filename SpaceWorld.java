import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Space shooter game/bad bullet hell attempt.
 * Player must dodge lots of bullets on the screen and shoot back at enemies while listening to Guile's Theme on repeat (if they can even live long enough to hear the entire song).
 * <p>
 * CLASSES BORROWED FROM OTHER PLACES:
 * Counter - borrowed from Mr. Cohen's moodle with slight adjustments made to it.
 * HighScoreCounter - borrowed from Mr. Cohen's moodle with slight adjustments made to it.
 * <p>
 * IMAGE SOURCES:
 * Player spaceship: http://graphicriver.net/item/spaceship-game-pixel-art/3489035?WT.mc_id=GT-tuts-sprite-sheets
 * Background: http://makepixelart.com/artists/bj-heinley/space
 * Enemy Ships: http://www.pixeljoint.com/pixelart/28699.htm#
 * Explosion: http://forum.chaos-project.com/index.php?topic=8831.0
 * <p>
 * KNOWN BUGS:
 * Sometimes the background music pauses and then resumes playing.
 * <p>
 * Changelog v0.1
 * Score bar and high score bar has been placed at the top left of the screen. The values do not change yet.
 * Character icon and lives remaining are displayed at the bottom left.
 * Player's ship is in the middle of the screen.
 * Background has been added.
 * <p>
 * Changelog v0.2:
 * Player can now move the ship around the screen.
 * Player's ship now tilts depending on which direction it's moving.
 * <p>
 * Changelog v0.3:
 * Removed background from SpaceWorld and made it an actor so it's position could be changed.
 * Player can shoot a bullet by pressing z.
 * <p>
 * Changelog v0.4:
 * Changed instance variable from character to player.
 * Changed all character commands to player commands.
 * Added spawnEnemy method to spawn enemies onto the screen.
 * <p>
 * Changelog v0.5:
 * Added instance variable boolean inWorld so only one enemy will spawn for testing purposes.
 * <p>
 * Changelog v0.6:
 * Added if statement in checkKeys method so it only runs if the player is alive.
 * <p>
 * Changelog v0.7:
 * Changed lifeCounter from Counter to LifeCounter class.
 * Used LifeIcon class for player icon at the bottom left.
 * Changed enemyBrown variable to array called brownEnemies.
 * Added instance variable int wave.
 * Changed spawning code so it only runs if inWorld is false, it initializes an array and spawns enemies, sets inWorld to true and increases wave counter.
 * Added moveEnemy method to make enemies move.
 * Added removeEnemy method so enemies will now be removed if they reach the edge of the world.
 * <p>
 * Changelog v0.8:
 * Created method nextWave and moved wave++ from spawnEnemy method to nextWave method.
 * Added two instance variables: int enemiesInWave and int enemiesRemoved.
 * Renamed boolean inWorld to enemiesInWorld.
 * Changed initial value of wave from 0 to 1.
 * Called nextWave() in act method.
 * Added method addToEnemiesRemoved.
 * Added wave 2 to spawnEnemy method.
 * Added instance variable int spawnDelay.
 * spawnDelay counter now increases in between waves and enemies will only spawn after a certain amount of time has passed.
 * moveEnemy(), removeEnemy(), and nextWave() will only run if enemiesInWorld is true.
 * Changed lifeCounter back from LifeCounter to Counter class.
 * Removed LifeCounter class.
 * Added method loseLife.
 * <p>
 * Changelog v0.9:
 * Added method respawn so the player respawns when they die.
 * Added instance variable int invincibleTimer.
 * Increase the value of invincibleTimer and turn off invincibility once it has reached a certain number in the act method.
 * The player's ship will now blink when they are invincible.
 * Player cannot shoot if they are invincible.
 * Added method int getLivesRemaining.
 * Changed all calling of move method to moveShip method.
 * Added addScore method.
 * Only run the code in the respawn method if the player's exploding boolean is false.
 * <p>
 * Changelog v1.0:
 * Wave 2 enemies can be spawned, moved, and removed.
 * Wave 3 enemies can be spawned, moved, and removed.
 * <p>
 * Changelog v1.1:
 * Added array purpleEnemies.
 * Wave 4 enemies can be spawned, moved, and removed.
 * Added array greenEnemies.
 * Added array yellowEnemies.
 * Wave 5 enemies can be spawned, moved, and removed.
 * Wave 6 enemies can be spawned, moved, and removed.
 * Wave 7 enemies can be spawned, moved, and removed.
 * Wave 8 enemies can be spawned, moved, and removed.
 * Wave 9 enemies can be spawned, moved, and removed.
 * Wave 10 enemies can be spawned, moved, and removed.
 * Added instance variable boolean allWavesCleared.
 * After the player has gone through all 10 waves, get a random wave from waves 5-10 inclusive.
 * Removed middle enemy from waves 2 and 7.
 * Changed highScore from Counter class to HighScoreCounter class.
 * Added instance variable GreenfootSound backgroundMusic.
 * Play background music in a loop in constructor.
 * Background music stops when player loses their last life.
 * 
 * @author Terrence Hung
 * @version 1.1
 */
public class SpaceWorld extends World
{
    //declare variables
    private Player player;
    private Counter score;
    private HighScoreCounter highScore;
    private Counter lifeCounter;
    private EnemyBrown[] brownEnemies;
    private boolean enemiesInWorld = false;
    private int wave = 1;
    private int enemiesInWave;
    private int enemiesRemoved;
    private int spawnDelay = 0;
    private int invincibleTimer = 0;
    private EnemyPurple[] purpleEnemies;
    private EnemyGreen[] greenEnemies;
    private EnemyYellow[] yellowEnemies;
    private boolean allWavesCleared = false;
    private GreenfootSound backgroundMusic = new GreenfootSound("Guile's Theme.mp3");
    /**
     * Constructor for objects of class SpaceWorld.
     * 
     */
    public SpaceWorld()
    {    
        // Create a new world with 450x600 cells with a cell size of 1x1 pixels.
        super(450, 600, 1); 
        //add background to world
        addObject(new Background(), 225, 0);
        //initialize player's ship
        player = new Player();
        //place player's ship at the bottom middle of screen
        addObject(player, 225, 525);
        //initialize score and high score counters
        score = new Counter("Score: ", 0, Color.WHITE, true);
        highScore = new HighScoreCounter("High Score: ", Color.WHITE, true);
        //place counters at the top left of screen
        addObject(score, 175, 20);
        addObject(highScore, 175, 40);
        //initialize life counter
        lifeCounter = new Counter("x ", 2, Color.WHITE, true);
        //place life counter at the bottom left of the screen
        addObject(lifeCounter, 220, 580);
        //place picture of player's ship at bottom left of the screen
        addObject(new LifeIcon(), 25, 580);
        //play background music in a loop
        backgroundMusic.playLoop();
    }

    /**
     * Checks which keys have been pressed.
     */
    public void act()
    {
        //check for key presses and change player picture depending on direction they move and invincibility
        checkKeys();
        //spawns enemies on the screen
        spawnEnemy();
        //moves enemies
        moveEnemy();
        //checks if enemies are at the edge of the world
        removeEnemy();
        //starts next wave of enemies
        nextWave();
        //respawn player if they died
        if(!(player.getAlive()))
            respawn();
        //if the player is currently invincible, then add 1 to the invincible timer
        if(player.getInvincible())
            invincibleTimer++;
        //if the invincible timer has reached 70, turn off invincibility and set the timer to 0
        if(invincibleTimer == 70)
        {
            player.setInvincible(false);
            invincibleTimer = 0;
        }
        //stop music if player has lost all their lives
        if(player.getLoseLastLife())
            backgroundMusic.stop();
    }

    /**
     * Checks if keys have been pressed, changes player ship's image, and move player.
     */
    private void checkKeys()
    {
        //declare variables
        int xMove = 0;
        int yMove = 0;
        //only run if player is alive
        if(player.getAlive())
        {
            //set move values depending on which button they press
            if(Greenfoot.isKeyDown("up"))
                yMove = yMove - 4;
            if(Greenfoot.isKeyDown("down"))
                yMove = yMove + 4;
            if(Greenfoot.isKeyDown("left"))
                xMove = xMove - 4;
            if(Greenfoot.isKeyDown("right"))
                xMove = xMove + 4;
            //if player is invincible then run this; this causes a blinking effect to the player's ship
            if(player.getInvincible())
            {
                //if the invincible timer is divisible by 10, then display the player's ship normally
                if(invincibleTimer % 10 == 0)
                {
                    //change the player image if the ship is moving left or right, or return image to original image if ship is not moving in the x direction
                    if(xMove < 0)
                        player.setImage("player tilt left.png");
                    else if(xMove > 0)
                        player.setImage("player tilt right.png");
                    else if(xMove == 0)
                        player.setImage("player normal.png");
                }
                //if the invincible timer ends in the number 5, then display the player's ship as all white
                else if(invincibleTimer % 5 == 0)
                {
                    //change the player image to all white if the ship is moving left or right, or change the player's ship to all white in the normal position if the ship is not moving in the x direction
                    if(xMove < 0)
                        player.setImage("player tilt left blink.png");
                    else if(xMove > 0)
                        player.setImage("player tilt right blink.png");
                    else if(xMove == 0)
                        player.setImage("player normal blink.png");
                }
            }
            //if player is not invincible, then display the player's ship normally
            else if(!(player.getInvincible()))
            {
                //change the player image if the ship is moving left or right, or return image to original image if ship is not moving in the x direction
                if(xMove < 0)
                    player.setImage("player tilt left.png");
                else if(xMove > 0)
                    player.setImage("player tilt right.png");
                else if(xMove == 0)
                    player.setImage("player normal.png");
            }
            //call method to move player
            player.moveShip(xMove, yMove);
            //only let player shoot if they are not invincible
            if(!(player.getInvincible()))
            {
                //shoot a bullet if the player presses z
                if(Greenfoot.isKeyDown("z"))
                    player.shoot();
            }
        }
    }

    /**
     * Spawns enemies onto the screen based on wave number.
     */
    private void spawnEnemy()
    {
        //only run if enemiesInWorld is false
        if(!(enemiesInWorld))
        {
            //spawn enemies according to wave number
            if(wave == 1)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[2];
                    //construct elements in the array
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    //spawn two brown ships on sides of the world and set enemiesInWave to 2
                    addObject(brownEnemies[0], 0, 100);
                    addObject(brownEnemies[1], 450, 100);
                    enemiesInWave = 2;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 2)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[2];
                    //construct elements in the array
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    //spawn two brown ships to the world and set enemiesInWave to 2
                    addObject(brownEnemies[0], 112, 0);
                    addObject(brownEnemies[1], 336, 0);
                    enemiesInWave = 2;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 3)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[6];
                    //construct elements in the array
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    //spawn six brown ships to the world and set enemiesInWave to 6
                    addObject(brownEnemies[0], 150, 0);
                    addObject(brownEnemies[1], 300, 0);
                    addObject(brownEnemies[2], 0, 100);
                    addObject(brownEnemies[3], 450, 100);
                    addObject(brownEnemies[4], 0, 0);
                    addObject(brownEnemies[5], 450, 0);
                    enemiesInWave = 6;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 4)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[4];
                    //initalize purple enemy ship array
                    purpleEnemies = new EnemyPurple[2];
                    //construct elements in the arrays
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    for(int i = 0; i < purpleEnemies.length; i++)
                    {
                        purpleEnemies[i] = new EnemyPurple();
                    }
                    //spawn four brown ships to the world
                    addObject(brownEnemies[0], 0, 250);
                    addObject(brownEnemies[1], 450, 350);
                    addObject(brownEnemies[2], 225, 0);
                    addObject(brownEnemies[3], 225, 0);
                    //spawn two purple ships to the world
                    addObject(purpleEnemies[0], 150, 0);
                    addObject(purpleEnemies[1], 300, 0);
                    //set enemiesInWave to 6
                    enemiesInWave = 6;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 5)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize green enemy ship array
                    greenEnemies = new EnemyGreen[2];
                    //initialize yellow enemy ship array
                    yellowEnemies = new EnemyYellow[2];
                    //construct elements in the arrays
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    for(int i = 0; i < yellowEnemies.length; i++)
                    {
                        yellowEnemies[i] = new EnemyYellow();
                    }
                    //spawn two green ships to the world
                    addObject(greenEnemies[0], 150, 0);
                    addObject(greenEnemies[1], 300, 0);
                    //spawn two yellow ships to the world
                    addObject(yellowEnemies[0], 0, 150);
                    addObject(yellowEnemies[1], 450, 300);
                    //set enemiesInWave to 4
                    enemiesInWave = 4;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            } 
            //the following waves are the previous waves except with more green enemies
            if(wave == 6)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[2];
                    //intialize green enemy ship array
                    greenEnemies = new EnemyGreen[2];
                    //construct elements in the arrays
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    //spawn two brown ships on sides of the world
                    addObject(brownEnemies[0], 0, 100);
                    addObject(brownEnemies[1], 450, 100);
                    //spawn two green ships
                    addObject(greenEnemies[0], 0, 200);
                    addObject(greenEnemies[1], 450, 50);
                    //set enemiesInWave to 4
                    enemiesInWave = 4;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 7)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[2];
                    //initialize green ship array
                    greenEnemies = new EnemyGreen[2];
                    //construct elements in the arrays
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    //spawn two brown ships to the world
                    addObject(brownEnemies[0], 112, 0);
                    addObject(brownEnemies[1], 336, 0);
                    //spawn two green ships to the world
                    addObject(greenEnemies[0], 0, 300);
                    addObject(greenEnemies[1], 450, 300);
                    enemiesInWave = 4;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 8)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[6];
                    //initialize green enemy ship array
                    greenEnemies = new EnemyGreen[2];
                    //construct elements in the array
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    //spawn six brown ships to the world
                    addObject(brownEnemies[0], 150, 0);
                    addObject(brownEnemies[1], 300, 0);
                    addObject(brownEnemies[2], 0, 100);
                    addObject(brownEnemies[3], 450, 100);
                    addObject(brownEnemies[4], 0, 0);
                    addObject(brownEnemies[5], 450, 0);
                    //spawn 2 green ships
                    addObject(greenEnemies[0], 150, 0);
                    addObject(greenEnemies[1], 300, 0);
                    //set enemiesInWave to 8
                    enemiesInWave = 8;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 9)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize brown enemy ship array
                    brownEnemies = new EnemyBrown[4];
                    //initalize purple enemy ship array
                    purpleEnemies = new EnemyPurple[2];
                    //initialize green enemy ship array
                    greenEnemies = new EnemyGreen[2];
                    //construct elements in the arrays
                    for(int i = 0; i < brownEnemies.length; i++)
                    {
                        brownEnemies[i] = new EnemyBrown();
                    }
                    for(int i = 0; i < purpleEnemies.length; i++)
                    {
                        purpleEnemies[i] = new EnemyPurple();
                    }
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    //spawn four brown ships to the world
                    addObject(brownEnemies[0], 0, 250);
                    addObject(brownEnemies[1], 450, 350);
                    addObject(brownEnemies[2], 225, 0);
                    addObject(brownEnemies[3], 225, 0);
                    //spawn two purple ships to the world
                    addObject(purpleEnemies[0], 150, 0);
                    addObject(purpleEnemies[1], 300, 0);
                    //spawn green ships to world
                    addObject(greenEnemies[0], 0, 200);
                    addObject(greenEnemies[1], 450, 400);
                    //set enemiesInWave to 6
                    enemiesInWave = 8;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
            if(wave == 10)
            {
                //increase the spawn delay counter
                spawnDelay++;
                //only spawn enemies once counter has reached 50
                if(spawnDelay == 50)
                {
                    //initialize green enemy ship array
                    greenEnemies = new EnemyGreen[4];
                    //initialize yellow enemy ship array
                    yellowEnemies = new EnemyYellow[2];
                    //construct elements in the arrays
                    for(int i = 0; i < greenEnemies.length; i++)
                    {
                        greenEnemies[i] = new EnemyGreen();
                    }
                    for(int i = 0; i < yellowEnemies.length; i++)
                    {
                        yellowEnemies[i] = new EnemyYellow();
                    }
                    //spawn four green ships to the world
                    addObject(greenEnemies[0], 150, 0);
                    addObject(greenEnemies[1], 300, 0);
                    addObject(greenEnemies[2], 0, 200);
                    addObject(greenEnemies[3], 450, 200);
                    //spawn two yellow ships to the world
                    addObject(yellowEnemies[0], 0, 150);
                    addObject(yellowEnemies[1], 450, 300);
                    //set enemiesInWave to 4
                    enemiesInWave = 6;
                    //set enemiesInWorld to true
                    enemiesInWorld = true;
                    //reset spawn delay
                    spawnDelay = 0;
                }
            }
        }
    }

    /**
     * Moves the enemy ships depending on which wave the player is on.
     */
    private void moveEnemy()
    {
        //only run if enemiesInWorld is true
        if(enemiesInWorld)
        {
            //move wave 1 ships
            if(wave == 1)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(2, 0);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-2, 0);
            }
            //move wave 2 ships
            if(wave == 2)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(2, 4);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-2, 4);
            }
            //move wave 3 ships
            if(wave == 3)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(1, 3);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-1, 3);
                if(brownEnemies[2].getAlive())
                    brownEnemies[2].moveShip(4, 0);
                if(brownEnemies[3].getAlive())
                    brownEnemies[3].moveShip(-4, 0);
                if(brownEnemies[4].getAlive())
                    brownEnemies[4].moveShip(3, 4);
                if(brownEnemies[5].getAlive())
                    brownEnemies[5].moveShip(-3, 4);
            }
            //move wave 4 ships
            if(wave == 4)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(1, 0);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-1, 0);
                if(brownEnemies[2].getAlive())
                    brownEnemies[2].moveShip(3, 4);
                if(brownEnemies[3].getAlive())
                    brownEnemies[3].moveShip(-3, 4);
                if(purpleEnemies[0].getAlive())
                    purpleEnemies[0].moveShip(4, 3);
                if(purpleEnemies[1].getAlive())
                    purpleEnemies[1].moveShip(-4, 3);
            }
            //move wave 5 ships
            if(wave == 5)
            {
                //only move ships if their alive variables are true
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(0, 3);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(0, 3);
                if(yellowEnemies[0].getAlive())
                    yellowEnemies[0].moveShip(6, 0);
                if(yellowEnemies[1].getAlive())
                    yellowEnemies[1].moveShip(-6, 0);
            }
            //move wave 6 ships
            if(wave == 6)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(2, 0);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-2, 0);
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(3, 0);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(-3, 0);
            }
            //move wave 7 ships
            if(wave == 7)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(2, 4);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-2, 4);
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(4, 0);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(-4, 0);
            }
            //move wave 8 ships
            if(wave == 8)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(1, 3);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-1, 3);
                if(brownEnemies[2].getAlive())
                    brownEnemies[2].moveShip(4, 0);
                if(brownEnemies[3].getAlive())
                    brownEnemies[3].moveShip(-4, 0);
                if(brownEnemies[4].getAlive())
                    brownEnemies[4].moveShip(3, 4);
                if(brownEnemies[5].getAlive())
                    brownEnemies[5].moveShip(-3, 4);
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(1, 3);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(-1, 3);
            }
            //move wave 5 ships
            if(wave == 9)
            {
                //only move ships if their alive variables are true
                if(brownEnemies[0].getAlive())
                    brownEnemies[0].moveShip(1, 0);
                if(brownEnemies[1].getAlive())
                    brownEnemies[1].moveShip(-1, 0);
                if(brownEnemies[2].getAlive())
                    brownEnemies[2].moveShip(3, 4);
                if(brownEnemies[3].getAlive())
                    brownEnemies[3].moveShip(-3, 4);
                if(purpleEnemies[0].getAlive())
                    purpleEnemies[0].moveShip(4, 3);
                if(purpleEnemies[1].getAlive())
                    purpleEnemies[1].moveShip(-4, 3);
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(2, -4);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(-2, -4);
            }
            //move wave 10 ships
            if(wave == 10)
            {
                //only move ships if their alive variables are true
                if(greenEnemies[0].getAlive())
                    greenEnemies[0].moveShip(0, 3);
                if(greenEnemies[1].getAlive())
                    greenEnemies[1].moveShip(0, 3);
                if(greenEnemies[2].getAlive())
                    greenEnemies[2].moveShip(2, 0);
                if(greenEnemies[3].getAlive())
                    greenEnemies[3].moveShip(-2, 0);
                if(yellowEnemies[0].getAlive())
                    yellowEnemies[0].moveShip(6, 0);
                if(yellowEnemies[1].getAlive())
                    yellowEnemies[1].moveShip(-6, 0);
            }
        }
    }

    /**
     * Sets the alive variables for the enemies to false if they reach the edge of the world.
     */
    private void removeEnemy()
    {
        //only run if enemiesInWorld is true
        if(enemiesInWorld)
        {
            //set alive variables for wave 1 ships to false if they reach the edge of the world
            if(wave == 1)
            {
                //loop to check each ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
            }
            //set alive variables for wave 2 ships to false if they reach the edge of the world
            if(wave == 2)
            {
                //loop to check each ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
            }
            //set alive variables for wave 3 ships to false if they reach the edge of the world
            if(wave == 3)
            {
                //loop to check each ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
            }
            //set alive variables for wave 4 ships to false if they reach the edge of the world
            if(wave == 4)
            {
                //loop to check each brown ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
                //loop to check each purple ship
                for(int i = 0; i < purpleEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(purpleEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(purpleEnemies[i].edgeOfWorld())
                        {
                            purpleEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 5 ships to false if they reach the edge of the world
            if(wave == 5)
            {
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
                //loop to check each yellow ship
                for(int i = 0; i < yellowEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(yellowEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world,set alive to false and add to enemiesRemoved
                        if(yellowEnemies[i].edgeOfWorld())
                        {
                            yellowEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 6 ships to false if they reach the edge of the world
            if(wave == 6)
            {
                //loop to check each brown ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 2 ships to false if they reach the edge of the world
            if(wave == 7)
            {
                //loop to check each ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 8 ships to false if they reach the edge of the world
            if(wave == 8)
            {
                //loop to check each ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 9 ships to false if they reach the edge of the world
            if(wave == 9)
            {
                //loop to check each brown ship
                for(int i = 0; i < brownEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(brownEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(brownEnemies[i].edgeOfWorld())
                        {
                            brownEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    } 
                }
                //loop to check each purple ship
                for(int i = 0; i < purpleEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(purpleEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(purpleEnemies[i].edgeOfWorld())
                        {
                            purpleEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
            //set alive variables for wave 10 ships to false if they reach the edge of the world
            if(wave == 10)
            {
                //loop to check each green ship
                for(int i = 0; i < greenEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(greenEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world, set alive to false and add to enemiesRemoved
                        if(greenEnemies[i].edgeOfWorld())
                        {
                            greenEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
                //loop to check each yellow ship
                for(int i = 0; i < yellowEnemies.length; i++)
                {
                    //only run if the ship's alive variable is true
                    if(yellowEnemies[i].getAlive())
                    {
                        //if the ship is at the edge of the world,set alive to false and add to enemiesRemoved
                        if(yellowEnemies[i].edgeOfWorld())
                        {
                            yellowEnemies[i].setAliveToFalse();
                            enemiesRemoved++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds to wave counter, sets enemiesInWave and enemiesRemoved to 0, and sets enemiesInWorld to false.
     */
    private void nextWave()
    {
        //only run if enemiesInWorld is true
        if(enemiesInWorld)
        {
            //only run if enemiesInWave is equal to enemiesRemoved
            if(enemiesInWave == enemiesRemoved)
            {
                //if the user just beat wave 10 for the first tim, set allWavesCleared to true
                if(wave == 10 && !(allWavesCleared))
                    allWavesCleared = true;
                //run if all waves cleared is true
                if(allWavesCleared)
                {
                    //make the wave a random number between 5-10 inclusive
                    wave = Greenfoot.getRandomNumber(6) + 5;
                    //set enemiesInWave to 0
                    enemiesInWave = 0;
                    //set enemiesRemoved to 0
                    enemiesRemoved = 0;
                    //set enemiesInWorld to false
                    enemiesInWorld = false;
                }
                //run if user is still going through waves 1-10
                else
                {
                    //increase wave number, set enemiesInWave and enemiesRemoved to 0, and set enemiesInWorld to false
                    wave++;
                    enemiesInWave = 0;
                    enemiesRemoved = 0;
                    enemiesInWorld = false;
                }
            }
        }
    }

    /**
     * Adds 1 to enemiesRemoved.
     */
    public void addToEnemiesRemoved()
    {
        enemiesRemoved++;
    }

    /**
     * Subtracts one from the life counter.
     */
    public void loseLife()
    {
        lifeCounter.subtract(1);
    }

    /**
     * Respawns the player and sets the player's alive variable to true.
     */
    private void respawn()
    {
        if(!(player.getExploding()))
        {
            addObject(player, 225, 525);
            player.setAlive(true);
            player.setInvincible(true);
        }
    }

    /**
     * Gets the number of lives the player has remaining.
     * 
     * @return int The number of lives the player has left.
     */
    public int getLivesRemaining()
    {
        return lifeCounter.getValue();
    }

    /**
     * Adds to player's current score and high score if high score is less than or equal to current score.
     * 
     * @param points The amount of points that will be added to the player's score.
     */
    public void addScore(int points)
    {
        //if high score is less than or equal to current score, update both
        if(score.getValue() >= highScore.getValue())
        {
            score.add(points);
            highScore.setValue(score.getValue());
        }
        //otherwise, update score only
        else
            score.add(points);
    }
}