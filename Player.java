import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The character that the player controls.
 * <p>
 * Changelog v0.1:
 * Ship can move around the screen.
 * <p>
 * Changelog v0.2:
 * A projectile can now spawn in front of the ship.
 * Add fire delay so projectiles can't be spammed.
 * <p>
 * Changelog v0.3:
 * Renamed class from Character to Player.
 * Player class is now a subclass of Character.
 * Moved move method from Player class to Character class.
 * <p>
 * Changelog v0.4:
 * Added hitByProjectile method.
 * Called hitByProjectile method in act method.
 * Added instance variable boolean alive.
 * Added getAlive method.
 * Player's ship is now removed when it gets hit by an enemy projectile.
 * Added crashIntoEnemy method.
 * The player's ship will now be removed when it comes in contact with an enemy.
 * <p>
 * Changelog v0.5:
 * Changed player hitbox to center of the ship.
 * <p>
 * Changelog v0.6:
 * Called the loseLife method located in the SpaceWorld class when player is hit by a projectile or when player crashes into an enemy.
 * <p>
 * Changelog v0.7:
 * Added method setAlive to change the alive variable.
 * Added instance variable boolean invincible.
 * Added method getInvincible.
 * Added method setInvincible.
 * The player can only get hit if they are not invincible; corresponding conditions have been added in hitByProjectile() and crashIntoEnemy().
 * Lives will be subtracted when the player dies.
 * Program stops if the player dies on their last life.
 * Changed player removal code from getWorld().removeObject(this) to removeShip().
 * Added instance variable int powerUpCount.
 * Added method getPowerUp.
 * Player can now get dropped power ups.
 * Added instance variable int numberOfBullets and set it to 1.
 * Added method upgradeWeapon.
 * Added numberOfBullet conditions in shoot method.
 * Player can now shoot more than one bullet if they collect enough power ups.
 * 50 points will be added to score when player collects a power up.
 * Added array explosions.
 * Added constructor where it populates explosions array.
 * Added explode method.
 * Added four instance variables: int explosionCounter, int explosionDelay, boolean exploding, and boolean loseLastLife.
 * Show an explosion when the player dies.
 * The ship will no longer be removed from hitByProjectile() or crashIntoEnemy().
 * Ship will now be removed from explode method.
 * Added method getExploding.
 * Program will now be stopped in the explode method instead of the hit detection methods.
 * Added exploding conditions in hitByProjectile() and crashIntoEnemy().
 * Power ups will not be collected if the player is currently exploding.
 * Player's power up count will go back to 0 if they die.
 * <p>
 * Changelog v0.8:
 * Added two static variables: xCoordinate and yCoordinate.
 * Added method static int getXCoordinate.
 * Added method static int getYCoordinate.
 * Updated coordinates in act method.
 * Player can now be hit by a purple enemy's projectile.
 * Player can now collide with purple ships.
 * Changed weapon upgrades from 15 and 30 power ups to 10 and 20 respectively.
 * <p>
 * Changelog v0.9:
 * Player can now collide with green and yellow ships.
 * Player can now be hit by green and yellow ships' projectiles.
 * Enemy projectiles will be removed from the world when the player collides with them.
 * Added method boolean getLoseLastLife.
 * Added explosionSound.
 * Play explosion sound when player explodes.
 * Program goes back to title screen when player dies.
 * 
 * @author Terrence Hung
 * @version 0.9
 */
public class Player extends Character
{   
    //declare variables
    private boolean fireProjectile = false;
    private int fireDelay = 0;
    private boolean alive = true;
    private boolean invincible = false;
    private int powerUpCount = 0;
    private int numberOfBullets = 1;
    private GreenfootImage[] explosions = new GreenfootImage[7];
    private int explosionCounter = 0;
    private int explosionDelay = -1;
    private boolean exploding = false;
    private boolean loseLastLife = false;
    private static int xCoordinate;
    private static int yCoordinate;
    private GreenfootSound explosionSound = new GreenfootSound("explosion.wav");
    /**
     * Constructor for class Player.
     */
    public Player()
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
     * Delays the player's ability to shoot to prevent bullet spam and checks if player is hit by a projectile and removes player's ship if it is.
     */
    public void act()
    {
        //update coordinates
        xCoordinate = this.getX();
        yCoordinate = this.getY();
        //if fireProjectile is true, then add to the delay counter
        if(fireProjectile)
        {
            fireDelay++;
            //once delay counter has reached 10, set fireProjectile to false and reset the delay counter to 0
            if(fireDelay == 10)
            {
                fireProjectile = false;
                fireDelay = 0;
            }
        }
        //check if player picks up a power up
        getPowerUp();
        //upgrade weapon if the player has reached enough power ups
        upgradeWeapon();
        //check if player is hit by a projectile and remove the player's ship if it is
        hitByProjectile();
        //check if player crashes into an enemy and remove the player's ship if it did
        crashIntoEnemy();
        //run explosion animation if the player died
        explode();
    }

    /**
     * Spawns a projectile in front of the player's ship.
     */
    public void shoot()
    {
        //only run if fireProjectile is false
        if(!(fireProjectile))
        {
            //if player only has one bullet
            if(numberOfBullets == 1)
            {
                //spawn projectile 17 pixels above the player's ship
                getWorld().addObject(new PlayerProjectile(getX(), getY() - 17), getX(), getY() - 17);
                //set fireProjectile to true
                fireProjectile = true;
            }
            //if the player has two bullets
            else if(numberOfBullets == 2)
            {
                //spawn projectiles 17 pixels above the player's ship and 10 pixels on each side from the center of the ship
                getWorld().addObject(new PlayerProjectile(getX() - 10, getY() - 17), getX() - 10, getY() - 17);
                getWorld().addObject(new PlayerProjectile(getX() + 10, getY() - 17), getX() + 10, getY() - 17);
                //set fireProjectile to true
                fireProjectile = true;
            }
            //if the player has three bullets
            else if(numberOfBullets == 3)
            {
                //spawn projectiles 17 pixels above the player's ship and 10 pixels on each side from the center of the ship and directly above the center of the ship
                getWorld().addObject(new PlayerProjectile(getX(), getY() - 17), getX(), getY() - 17);
                getWorld().addObject(new PlayerProjectile(getX() - 10, getY() - 17), getX() - 10, getY() - 17);
                getWorld().addObject(new PlayerProjectile(getX() + 10, getY() - 17), getX() + 10, getY() - 17);
                //set fireProjectile to true
                fireProjectile = true;
            }
        }
    }    

    /**
     * Checks if a projectile has hit the player and removes the projectile and the player's ship and sets alive to false.
     */
    private void hitByProjectile()
    {
        //only run if player is not invincible anymore and player is not exploding
        if(!(invincible || exploding))
        {
            //assign the different types of projectiles that can hit the player here
            EnemyBrownProjectile enemyBrownProjectile = (EnemyBrownProjectile)getOneObjectAtOffset(0, 0, EnemyBrownProjectile.class);
            EnemyPurpleProjectile enemyPurpleProjectile = (EnemyPurpleProjectile)getOneObjectAtOffset(0, 0, EnemyPurpleProjectile.class);
            EnemyGreenProjectile enemyGreenProjectile = (EnemyGreenProjectile)getOneObjectAtOffset(0, 0, EnemyGreenProjectile.class);
            EnemyYellowProjectile enemyYellowProjectile = (EnemyYellowProjectile)getOneObjectAtOffset(0, 0, EnemyYellowProjectile.class);
            //only run any of the if statements if the player was hit by a projectile
            if(enemyBrownProjectile != null)
            {
                //subtract one life from the life counter if they aren't on their last life
                if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                    ((SpaceWorld)getWorld()).loseLife();
                //set loseLastLife to true if the player got hit on their last life
                else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                    loseLastLife = true;
                getWorld().removeObject(enemyBrownProjectile);
                //set value of alive to false
                alive = false;
            }
            else if(enemyPurpleProjectile != null)
            {
                //subtract one life from the life counter if they aren't on their last life
                if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                    ((SpaceWorld)getWorld()).loseLife();
                //set loseLastLife to true if the player got hit on their last life
                else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                    loseLastLife = true;
                getWorld().removeObject(enemyPurpleProjectile);
                //set value of alive to false
                alive = false;
            }
            else if(enemyGreenProjectile != null)
            {
                //subtract one life from the life counter if they aren't on their last life
                if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                    ((SpaceWorld)getWorld()).loseLife();
                //set loseLastLife to true if the player got hit on their last life
                else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                    loseLastLife = true;
                getWorld().removeObject(enemyGreenProjectile);
                //set value of alive to false
                alive = false;
            }
            else if(enemyYellowProjectile != null)
            {
                //subtract one life from the life counter if they aren't on their last life
                if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                    ((SpaceWorld)getWorld()).loseLife();
                //set loseLastLife to true if the player got hit on their last life
                else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                    loseLastLife = true;
                getWorld().removeObject(enemyYellowProjectile);
                //set value of alive to false
                alive = false;
            }
        }
    }

    /**
     * Checks if the player has collided with an enemy and removes the player's ship from the world and sets alive to false.
     */
    private void crashIntoEnemy()
    {
        //only run if alive is true
        if(alive)
        {
            //only run if player is not invincible anymore and player is not exploding
            if(!(invincible || exploding))
            {
                //assign different enemies the player can crash into to variables
                EnemyBrown enemyBrown = (EnemyBrown)getOneIntersectingObject(EnemyBrown.class);
                EnemyPurple enemyPurple = (EnemyPurple)getOneIntersectingObject(EnemyPurple.class);
                EnemyGreen enemyGreen = (EnemyGreen)getOneIntersectingObject(EnemyGreen.class);
                EnemyYellow enemyYellow = (EnemyYellow)getOneIntersectingObject(EnemyYellow.class);
                //only run any of the if statements if the player has collided with an enemy
                if(enemyBrown != null)
                {
                    //subtract one life from the life counter if they aren't on their last life
                    if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                        ((SpaceWorld)getWorld()).loseLife();
                    //set loseLastLife to true if the player got hit on their last life
                    else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                        loseLastLife = true;
                    //set alive to false
                    alive = false;
                }
                else if(enemyPurple != null)
                {
                    //subtract one life from the life counter if they aren't on their last life
                    if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                        ((SpaceWorld)getWorld()).loseLife();
                    //set loseLastLife to true if the player got hit on their last life
                    else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                        loseLastLife = true;
                    //set alive to false
                    alive = false;
                }
                else if(enemyGreen != null)
                {
                    //subtract one life from the life counter if they aren't on their last life
                    if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                        ((SpaceWorld)getWorld()).loseLife();
                    //set loseLastLife to true if the player got hit on their last life
                    else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                        loseLastLife = true;
                    //set alive to false
                    alive = false;
                }
                else if(enemyYellow != null)
                {
                    //subtract one life from the life counter if they aren't on their last life
                    if(((SpaceWorld)getWorld()).getLivesRemaining() > 0)
                        ((SpaceWorld)getWorld()).loseLife();
                    //set loseLastLife to true if the player got hit on their last life
                    else if(((SpaceWorld)getWorld()).getLivesRemaining() == 0)
                        loseLastLife = true;
                    //set alive to false
                    alive = false;
                }
            }
        }
    }

    /**
     * Returns value of alive
     * 
     * @return boolean Return true if player is still alive.
     */
    public boolean getAlive()
    {
        return alive;
    }

    /**
     * Changes the value of boolean alive.
     * 
     * @param trueOrFalse The value that the variable alive will be set to.
     */
    public void setAlive(boolean trueOrFalse)
    {
        alive = trueOrFalse;
    }

    /**
     * Returns the value of boolean invincible.
     * 
     * @return boolean Return true if the player is currently invincible.
     */
    public boolean getInvincible()
    {
        return invincible;
    }

    /**
     * Changes the value of boolean invincible.
     * 
     * @param trueOrFalse The value that the variable invincible will be set to.
     */
    public void setInvincible(boolean trueOrFalse)
    {
        invincible = trueOrFalse;
    }

    /**
     * Checks if player touches a power up and if they do, add 50 points to score, remove the power up from the world, and increase the power up counter by 1.
     */
    private void getPowerUp()
    {
        //only run this if the player is not exploding
        if(!(exploding))
        {
            //assign the power up the player can come in contact with to a variable
            PowerUp powerUp = (PowerUp)getOneIntersectingObject(PowerUp.class);
            //run if player gets a power up
            if(powerUp != null)
            {
                //add 50 points to score
                ((SpaceWorld)getWorld()).addScore(50);
                //remove power up from world
                getWorld().removeObject(powerUp);
                //increase power up count by one
                powerUpCount++;
            }
        }
    }

    /**
     * Allow the player to shoot more projectiles if the correct amount of power ups have been collected.
     */
    private void upgradeWeapon()
    {
        //if player has no power ups, only let them shoot 1 bullet
        if(powerUpCount == 0)
            numberOfBullets = 1;
        //if player reaches 10 power ups, then let them shoot 2 bullets
        else if(powerUpCount == 10)
            numberOfBullets = 2;
        //if player reaches 20 power ups, then let them shoot 3 bullets
        else if(powerUpCount == 20)
            numberOfBullets = 3;
    }

    /**
     * Goes through the explosion animation and stops the program if the player has lost their last life.
     */
    private void explode()
    {
        //only go through explosion animations if alive is false
        if(!(alive))
        {
            //increase explosion delay by 1
            explosionDelay++;
            //run if explosion delay is 0
            if(explosionDelay == 0)
            {
                //set exploding to true
                exploding = true;
                //change player image to first image in explosion animation
                setImage(explosions[explosionCounter]);
                //add to explosion counter
                explosionCounter++;
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
                //remove the player's ship/explosion sprite
                removeShip();
                //run if player lost their last life
                if(loseLastLife)
                {
                    //delay the program for a bit so player can look at their score
                    Greenfoot.delay(100);
                    //set the world to the title screen
                    Greenfoot.setWorld(new TitleScreen());
                }
                //set player back to 0 power ups
                powerUpCount = 0;
                //change player's image back to the ship so the ship will show on spawn
                setImage("player normal.png");
                //change explosion delay back to -1
                explosionDelay = -1;
                //change explosion counter back to 0
                explosionCounter = 0;
                //set exploding to false
                exploding = false;
            }
        }
    }

    /**
     * Returns the value of boolean exploding.
     * 
     * @return boolean Return true if the player's ship is currently exploding.
     */
    public boolean getExploding()
    {
        return exploding;
    }

    /**
     * Returns the x-coordinate of the player's ship.
     * 
     * @return int The current x-coordinate of the player's ship.
     */
    public static int getXCoordinate()
    {
        return xCoordinate;
    }

    /**
     * Returns the y-coordinate of the player's ship.
     * 
     * @return int The current y-coordinate of the player's ship.
     */
    public static int getYCoordinate()
    {
        return yCoordinate;
    }

    /**
     * Returns the value of loseLastLife.
     * 
     * @return boolean Return true if loseLastLife is true.
     */
    public boolean getLoseLastLife()
    {
        return loseLastLife;
    }
}