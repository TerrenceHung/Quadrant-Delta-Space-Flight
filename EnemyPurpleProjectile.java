import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The projectile that the purple enemy shoots.
 * <p>
 * Changelog v0.1:
 * Copied everything from EnemyBrownProjectile and renamed constructor.
 * Changed ySpeed from 5 to 3.
 * <p>
 * Changelog v0.2:
 * Changed ySpeed from 3 to 4.
 * 
 * @author Terrence Hung
 * @version 0.2
 */
public class EnemyPurpleProjectile extends Projectile
{
    //declare variables
    private int xPosition;
    private int yPosition;
    private int xSpeed = 0;
    private int ySpeed = 4;
    
    /**
     * Constructor for class EnemyPurpleProjectile
     * 
     * @param xPosition Determines the current x position of the projectile.
     * @param yPosition Determines the current y position of the projectile.
     */
    public EnemyPurpleProjectile(int xPosition, int yPosition)
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
