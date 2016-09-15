import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Purple enemy that will spawn in the game.
 * <p>
 * Changelog v0.1:
 * Added everything from EnemyBrown class into this class with a few changes made to fit this class.
 * Changed hp to 3.
 * Changed fireDelay from 15 to 10.
 * <p>
 * Changelog v0.2:
 * Added explosionSound.
 * Play explosion sound when ship explodes.
 * 
 * @author Terrence Hung
 * @version 0.2
 */
public class EnemyPurple extends Character
{
    //declare variables
    private boolean fireProjectile = false;
    private int fireDelay = 0;
    private boolean alive = true;
    private GreenfootImage[] explosions = new GreenfootImage[7];
    private int explosionCounter = 0;
    private int explosionDelay = -1;
    private boolean droppedPowerUp = false;
    private boolean beenDestroyed = false;
    private boolean exploding = false;
    private int hp = 3;
    private GreenfootSound explosionSound = new GreenfootSound("explosion.wav");
    /**
     * Constructor for class EnemyPurple.
     */
    public EnemyPurple()
    {
        //declare three strings to build file names
        String fileName;
        String fileNamePrefix = "explosion";
        String fileNameSuffix = ".png";
        //loop to populate array
        for(int i = 0; i < explosions.length; i++)
        {
            //build file name
            fileName = fileNamePrefix + (i+1) + fileNameSuffix;
            //assign file to array
            explosions[i] = new GreenfootImage(fileName);
        }
    }

    /**
     * Delays the rate of fire the enemy has, drops power ups, and explodes/removes ship.
     */
    public void act()
    {
        shoot();
        //if fireProjectile is true, then add to the delay counter
        if(fireProjectile)
        {
            fireDelay++;
            //if the fire delay counter has reached 10, set fireProjectile to false and reset delay counter to 0
            if(fireDelay == 10)
            {
                fireProjectile = false;
                fireDelay = 0;
            }
        }
        //run if alive is false
        if(!(alive))
        {
            //if ship is not at the edges of the world and has not dropped a power up before, drop a power up and set droppedPowerUp to true
            if(!(edgeOfWorld()) && !(droppedPowerUp))
            {
                dropPowerUp(getX(), getY());
                droppedPowerUp = true;                
            }
            //if ship is not at the edges of the world, show explosion animation
            if(!(edgeOfWorld()))
                explode();
            //otherwise, remove the ship without exploding
            else
                removeShip();
        }
    }

    /**
     * Spawns a projectile in front of the ship.
     */
    private void shoot()
    {
        //only run if fireProjectile is false
        if(!(fireProjectile || exploding))
        {
            //spawn a projectile 30 pixels below the center of the enemy
            getWorld().addObject(new EnemyPurpleProjectile(getX(), getY() + 30), getX(), getY() + 30);
            //set fireProjectile to true
            fireProjectile = true;
        }
    }

    /**
     * Returns value of alive
     * 
     * @return boolean Return true if player is still alive
     */
    public boolean getAlive()
    {
        return alive;
    }

    /**
     * Sets the value of alive to false.
     */
    public void setAliveToFalse()
    {
        alive = false;
    }

    /**
     * Shows the explosion animation.
     */
    private void explode()
    {
        //increase explosion delay by 1
        explosionDelay++;
        //run if explosion delay is 0
        if(explosionDelay == 0)
        {
            //change player image to first image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
            //set exploding to true
            exploding = true;
            //play explosion sound
            explosionSound.play();
        }
        //run if explosion delay is 5
        else if(explosionDelay == 5)
        {
            //change player image to second image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
        }
        //run if explosion delay is 10
        else if(explosionDelay == 10)
        {
            //change player image to third image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
        }
        //run if explosion delay is 15
        else if(explosionDelay == 15)
        {
            //change player image to fourth image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
        }
        //run if explosion delay is 20
        else if(explosionDelay == 20)
        {
            //change player image to fifth image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
        }
        //run if explosion delay is 25
        else if(explosionDelay == 25)
        {
            //change player image to sixth image in explosion animation
            setImage(explosions[explosionCounter]);
            //add to explosion counter
            explosionCounter++;
        }
        //run if explosion delay is 30
        else if(explosionDelay == 30)
        {
            //change player image to last image in explosion animation
            setImage(explosions[explosionCounter]);
        }
        //run if explosion delay is 35
        else if(explosionDelay == 35)
        {
            //remove the enemy's ship/explosion sprite
            removeShip();
            //change explosion delay back to -1
            explosionDelay = -1;
            //change explosion counter back to 0
            explosionCounter = 0;
        }
    }

    /**
     * Sets boolean beenDestroyed to true.
     */
    public void setBeenDestroyedTrue()
    {
        beenDestroyed = true;
    }

    /**
     * Returns the value of beenDestroyed.
     * 
     * @return boolean Return true if ship has been destroyed before.
     */
    public boolean getBeenDestroyed()
    {
        return beenDestroyed;
    }

    /**
     * Returns the enemy's current HP.
     * 
     * @return int The amount of HP the enemy has left.
     */
    public int getHP()
    {
        return hp;
    }

    /**
     * Subtracts 1 from the enemy's current HP.
     */
    public void loseHP()
    {
        hp--;
    } 
}
