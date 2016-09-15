import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The projectile that the player shoots out.
 * <p>
 * Changelog v0.1:
 * Added constructor.
 * Projectiles now move up the screen.
 * Projectiles will now be removed from the world when they reach the top of the screen.    
 * <p>
 * Changelog v0.2:
 * Added checkHit method to remove the enemy the bullet comes in contact with.
 * Added instance variable boolean hitShip.
 * <p>
 * Changelog v0.3:
 * Called method addToEnemiesRemoved when a hit is detected in the checkHit method.
 * <p>
 * Changelog v0.4:
 * Rearranged code so it checks if an enemy is hit before it checks if the bullet has reached the edge of the world.
 * Added instance variable boolean inWorld and set it to true.
 * Set inWorld to false if the bullet hits an enemy.
 * The bullet will only be checked for being at the edge of the world if inWorld is true.
 * 100 points will be added to score when the player destroys a brown ship.
 * Removal of ships and etc. will only be run if the ship's beenHit variable is false.
 * Change the ship's beenHit variable to true if it gets hit.
 * <p>
 * Changelog v0.5:
 * Changed all beenHit codes to beenDestroyed.
 * Hitting an enemy now causes it to lose HP.
 * Enemy will only be removed and etc. if its HP is 0.
 * <p>
 * Changelog v0.6:
 * Projectiles can now hit purple enemies.
 * 150 points will be added to score for destroying a purple ship.
 * Projectiles can hit green and yellow enemies.
 * 200 points will be added to score for destroying a green ship.
 * 300 points will be added to score for destroying a yellow ship.
 * 
 * @author Terrence Hung
 * @version 0.6
 */
public class PlayerProjectile extends Projectile
{
    //declare variables
    private int xSpeed = 0;
    private int ySpeed = -5;
    private int xPosition;
    private int yPosition;
    private boolean hitShip = false;
    private boolean inWorld = true;
    /**
     * Constructor for class PlayerProjectile
     * 
     * @param xPosition Determines the current x position of the projectile.
     * @param yPosition Determines the current y position of the projectile.
     */
    public PlayerProjectile(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Moves the projectile up the screen, checks if it hits an enemy, and removes projectile from screen.
     */
    public void act() 
    {
        //move the projectile
        moveProjectile(xSpeed, ySpeed);
        //check if the projectile hits anything
        checkHit();
        //remove projectile if it hits an enemy
        if(hitShip)
        {
            getWorld().removeObject(this);
            inWorld = false;
        }
        //check if projectile is at the top or bottom of the screen and remove it if the bullet is still in the world
        if(inWorld)
            edgeOfWorld();
    }    

    /**
     * Checks if the projectile hits an enemy and sets the enemy's alive variable to false, sets hitShip to true, sets enemy's beenHit variable to true, add to enemiesRemoved in the world, and add points for destroying enemies.
     */
    private void checkHit()
    {
        //assign all the objects the projectile might come in contact with to variables
        EnemyBrown enemyBrown = (EnemyBrown)getOneIntersectingObject(EnemyBrown.class);
        EnemyPurple enemyPurple = (EnemyPurple)getOneIntersectingObject(EnemyPurple.class);
        EnemyGreen enemyGreen = (EnemyGreen)getOneIntersectingObject(EnemyGreen.class);
        EnemyYellow enemyYellow = (EnemyYellow)getOneIntersectingObject(EnemyYellow.class);
        //run the following if statements if a collision is detected and the ship's beenHit variable is false
        if(enemyBrown != null && !(enemyBrown.getBeenDestroyed()))
        {
            //set hitShip to true
            hitShip = true;
            //enemy loses HP
            enemyBrown.loseHP();
            //if the enemy's HP is 0 then do the following
            if(enemyBrown.getHP() == 0)
            {
                //set ship's alive variable to false
                enemyBrown.setAliveToFalse();
                //set ship's beenDestroyed variable to true
                enemyBrown.setBeenDestroyedTrue();
                //add to enemiesRemoved in the world
                ((SpaceWorld)getWorld()).addToEnemiesRemoved();
                //add 100 points for destroying a brown ship
                ((SpaceWorld)getWorld()).addScore(100);
            }
        }
        if(enemyPurple != null && !(enemyPurple.getBeenDestroyed()))
        {
            //set hitShip to true
            hitShip = true;
            //enemy loses HP
            enemyPurple.loseHP();
            //if the enemy's HP is 0 then do the following
            if(enemyPurple.getHP() == 0)
            {
                //set ship's alive variable to false
                enemyPurple.setAliveToFalse();
                //set ship's beenDestroyed variable to true
                enemyPurple.setBeenDestroyedTrue();
                //add to enemiesRemoved in the world
                ((SpaceWorld)getWorld()).addToEnemiesRemoved();
                //add 150 points for destroying a purple ship
                ((SpaceWorld)getWorld()).addScore(150);
            }
        }
        if(enemyGreen != null && !(enemyGreen.getBeenDestroyed()))
        {
            //set hitShip to true
            hitShip = true;
            //enemy loses HP
            enemyGreen.loseHP();
            //if the enemy's HP is 0 then do the following
            if(enemyGreen.getHP() == 0)
            {
                //set ship's alive variable to false
                enemyGreen.setAliveToFalse();
                //set ship's beenDestroyed variable to true
                enemyGreen.setBeenDestroyedTrue();
                //add to enemiesRemoved in the world
                ((SpaceWorld)getWorld()).addToEnemiesRemoved();
                //add 200 points for destroying a green ship
                ((SpaceWorld)getWorld()).addScore(200);
            }
        }
        if(enemyYellow != null && !(enemyYellow.getBeenDestroyed()))
        {
            //set hitShip to true
            hitShip = true;
            //enemy loses HP
            enemyYellow.loseHP();
            //if the enemy's HP is 0 then do the following
            if(enemyYellow.getHP() == 0)
            {
                //set ship's alive variable to false
                enemyYellow.setAliveToFalse();
                //set ship's beenDestroyed variable to true
                enemyYellow.setBeenDestroyedTrue();
                //add to enemiesRemoved in the world
                ((SpaceWorld)getWorld()).addToEnemiesRemoved();
                //add 300 points for destroying a yellow ship
                ((SpaceWorld)getWorld()).addScore(300);
            }   
        }
    }
}
