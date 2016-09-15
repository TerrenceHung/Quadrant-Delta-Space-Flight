import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Background that moves downwards.
 * <p>
 * Changelog v0.1:
 * Background now continuously moves downward.
 * <p>
 * Changelog v0.2:
 * Increased move speed from 3 to 7.
 * 
 * @author Terrence Hung 
 * @version 0.2
 */
public class Background extends Actor
{
    /**
     * Moves the background downwards.
     */
    public void act() 
    {
        //move the background down by 7 pixels at a time
        setLocation(getX(), getY() + 7);
        //if the background has moved all the way down then reset the Y position to 0 so it can move down again
        if(getY() >= 599)
            setLocation(getX(), 0);
    }    
}
