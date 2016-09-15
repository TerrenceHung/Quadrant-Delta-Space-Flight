import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Methods that all of the characters can/will use.
 * <p>
 * Changelog v0.1:
 * Added move method.
 * <p>
 * Changelog v0.2:
 * Added shipDestroyed method.
 * <p>
 * Changelog v0.3:
 * Removed shipDestroyed method because removal of enemy ships are now done in PlayerProjectile class.
 * <p>
 * Changelog v0.4:
 * Renamed move method to moveShip.
 * removeShip method is now located in this class.
 * Added method dropPowerUp(int, int).
 * <p>
 * Changelog v0.5:
 * Added method boolean edgeOfWorld().
 * 
 * @author Terrence Hung
 * @version 0.5
 */
public class Character extends Actor
{

    /**
     * Moves a spaceship around the screen.
     * 
     * @param xMove How much the ship will move in the x direction
     * @param yMove How much the ship will move in the y direction
     */
    public void moveShip(int xMove, int yMove)
    {
        //sets new location for spaceship
        setLocation(getX() + xMove, getY() + yMove);
    }  

    /**
     * Removes the ship from the world.
     */
    public void removeShip()
    {
        getWorld().removeObject(this);
    }

    /**
     * Drop a power up where the enemy currently is.
     */
    public void dropPowerUp(int xPosition, int yPosition)
    {
        ((SpaceWorld)getWorld()).addObject(new PowerUp(), xPosition, yPosition);
    }
    
    /**
     * Checks if the ship is at the edge of the world.
     * 
     * @return boolean Return true if the ship is at the edge of the world.
     */
    public boolean edgeOfWorld()
    {
        if(this.getX() == 0 || this.getX() == 449 || this.getY() == 0 || this.getY() == 599)
            return true;
        else
            return false;
    }
}
