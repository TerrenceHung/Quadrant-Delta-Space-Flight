import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Methods that all of the projectiles can/will use.
 * <p>
 * Changelog v0.1:
 * Added moveProjectile method.
 * Added removeProjectile method.
 * <p>
 * Changelog v0.2:
 * Renamed removeProjectile method to edgeOfWorld.
 * <p>
 * Changelog v0.3:
 * Projectiles will now be removed if they are on the left or right edges of the screen.
 * 
 * @author Terrence Hung
 * @version 0.3
 */
public class Projectile extends Actor
{
    /**
     * Moves the projectile on the screen.
     * 
     * @param xSpeed The speed the projectile will move in the x direction.
     * @param ySpeed The speed the projectile will move in the y direction.
     */
    public void moveProjectile(int xSpeed, int ySpeed)
    {
        setLocation(getX() + xSpeed, getY() + ySpeed);
    }

    /**
     * Removes the projectile if it is on the edge of the screen.
     */
    public void edgeOfWorld()
    {
        //remove projectile if it is at the top of the screen
        if(getY() == 0)
            getWorld().removeObject(this);
        //remove projectile if it is at the bottom of the screen
        else if(getY() == 599)
            getWorld().removeObject(this);
        //remove projectile if it is at the left of the screen
        else if(getX() == 0)
            getWorld().removeObject(this);
        //remove projectile if it is at the right of the screen
        else if(getX() == 449)
            getWorld().removeObject(this);
    }

}
