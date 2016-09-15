import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The projectile that the brown enemy shoots.
 * <p>
 * Changelog v0.1:
 * Added instance variables.
 * Added constructor.
 * Projectile now moves and will be removed if it reaches the edge of the world.
 * <p>
 * Changelog v0.2
 * Changed ySpeed to 5.
 * 
 * @author Terrence Hung
 * @version 0.2
 */
public class EnemyBrownProjectile extends Projectile
{
    //declare variables
    private int xPosition;
    private int yPosition;
    private int xSpeed = 0;
    private int ySpeed = 5;
    
    /**
     * Constructor for class EnemyBrownProjectile
     * 
     * @param xPosition Determines the current x position of the projectile.
     * @param yPosition Determines the current y position of the projectile.
     */
    public EnemyBrownProjectile(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    /**
     * Moves the projectile down the screen, and removes it from the world.
     */
    public void act() 
    {
        //move projectile
        moveProjectile(xSpeed, ySpeed);
        //remove projectile if it is at the top or bottom of the screen
        edgeOfWorld();
    }    
}
