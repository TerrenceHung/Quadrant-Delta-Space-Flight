import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The projectile that the green enemy shoots.
 * <p>
 * Changelog v0.1:
 * Added everything from EnemyBrownProjectile into this class.
 * Added instance variable boolean findPlayer.
 * Projectile will move towards the player when spawned.
 * 
 * @author Terrence Hung
 * @version 0.1
 */
public class EnemyGreenProjectile extends Projectile
{
    //declare variables
    private int xPosition;
    private int yPosition;
    private boolean findPlayer = false;
    /**
     * Constructor for class EnemyGreenProjectile
     * 
     * @param xPosition Determines the current x position of the projectile.
     * @param yPosition Determines the current y position of the projectile.
     */
    public EnemyGreenProjectile(int xPosition, int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    /**
     * Moves the projectile towards player, and removes it from the world.
     */
    public void act() 
    {
        //if findPlayer is false then run this
        if(!(findPlayer))
        {
            //turn projectile towards player
            turnTowards(Player.getXCoordinate(), Player.getYCoordinate());
            //set findPlayer to true
            findPlayer = true;
        }
        //move projectile 5 pixels
        move(5);
        //remove projectile if it is at the top or bottom of the screen
        edgeOfWorld();
    } 
}
