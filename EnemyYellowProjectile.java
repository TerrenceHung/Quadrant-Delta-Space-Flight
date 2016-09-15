import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The projectile the yellow ship shoots out.
 * <p>
 * Changelog v0.1:
 * Copied everything from enemyBrownProjectile and renamed constructor.
 * Changed ySpeed from 5 to 3.
 * 
 * @author Terrence Hung
 * @version 0.1
 */
public class EnemyYellowProjectile extends Projectile
{
    //declare variables
    private int xPosition;
    private int yPosition;
    private int xSpeed = 0;
    private int ySpeed = 3;

    /**
     * Constructor for class EnemyYellowProjectile
     * 
     * @param xPosition Determines the current x position of the projectile.
     * @param yPosition Determines the current y position of the projectile.
     */
    public EnemyYellowProjectile(int xPosition, int yPosition)
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
