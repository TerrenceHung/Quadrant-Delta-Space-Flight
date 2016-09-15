import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A power up that the player collects.
 * <p>
 * Changelog v0.1:
 * Added moveDown method.
 * Remove the power up if it has reached the bottom of the screen.
 * <p>
 * Changelog v0.2:
 * Decreased move speed from 4 to 3.
 * 
 * @author Terrence Hung
 * @version 0.2
 */
public class PowerUp extends Actor
{
    /**
     * Moves the power up down the screen and removes it from the world if it reaches the bottom.
     */
    public void act() 
    {
        //move the power up down the screen
        moveDown();
        //remove power up from world if it has reached the bottom of the screen
        if(getY() >= 599)
            getWorld().removeObject(this);
    }    
    
    /**
     * Moves the power up down the screen by 3 pixels.
     */
    private void moveDown()
    {
        setLocation(getX(), getY() + 3);
    }
}
