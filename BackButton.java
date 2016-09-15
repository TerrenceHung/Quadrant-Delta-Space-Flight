import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Back button that appears on the instructions menu.
 * Increases in size when the user puts their mouse over the button.
 * <p>
 * Changelog v0.1:
 * Back button gets bigger depending on whether or not the mouse is on it.
 * 
 * @author Terrence Hung 
 * @version 0.1
 */
public class BackButton extends Actor
{
    /**
     * Change the size of the back button depending whether or not the mouse is on it.
     */
    public void changeSize()
    {
        //if mouse is on the back button, make it bigger
        if(Greenfoot.mouseMoved(this))
            setImage("back button bigger.png");
        //if mouse is on the background, return back button to regular size
        else if(Greenfoot.mouseMoved(getWorld()))
            setImage("back button.png");
    }
}
