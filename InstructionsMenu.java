import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Instructions menu that explains to the user how to play the game.
 * <p>
 * Changelog v0.1:
 * Back button has been added.
 * Player can go back to title screen by clicking on the back button.
 * 
 * @author Terrence Hung
 * @version 0.1
 */
public class InstructionsMenu extends World
{
    //declare variables
    private BackButton backButton;
    /**
     * Constructor for objects of class InstructionsMenu.
     * 
     */
    public InstructionsMenu()
    {    
        // Create a new world with 450x600 cells with a cell size of 1x1 pixels.
        super(450, 600, 1); 
        //initialize and add back button to screen
        backButton = new BackButton();
        addObject(backButton, 225, 500);
    }
    
    /**
     * Checks for mouse movement and clicks.
     */
    public void act()
    {
        //checks if mouse is on a button or if it clicked a button
        checkMouse();
    }
    
    /**
     * Checks for mouse movement and clicks.
     */
    public void checkMouse()
    {
        //change the size of the back button if the mouse is on it
        backButton.changeSize();
        //if the player clicks on the back button, then go back to main menu
        if(Greenfoot.mouseClicked(backButton))
            Greenfoot.setWorld(new TitleScreen());
    }
}
