import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.awt.Font;
import java.awt.Color;
//ENTIRE CLASS BORROWED FROM MR. COHEN'S MOODLE
/**
 * Counter that displays the high score.
 * <p>
 * Changelog v2.1:
 * Changed value to a static variable.
 * 
 * @author Michael Kolling & Brian Huang & Terrence Hung
 * @version 2.1
 */
public class HighScoreCounter extends Actor
{   
    private static final int FONT_SIZE = 20;
    private static int value;  
    private String message;
    private boolean isCounter = true;

    /**
     * Creates the size, font and length of the Counter
     * @param prefix the size needed for the Counter
     * @param color sets the color of the counter
     * @param isCounter ask if it only want a message (False for message only)
     */
    public HighScoreCounter(String prefix,Color color,boolean isCounter)
    {
        this.isCounter = isCounter;
        this.value = value;
        int stringLength = (FONT_SIZE + 1) * 16;
        message = prefix;
        setImage(new GreenfootImage(stringLength, 24));
        GreenfootImage image = getImage();
        image.setColor (color);
        Font font = image.getFont();
        image.setFont(new Font("Bankgothic Lt BT", Font.BOLD, FONT_SIZE));
        updateImage();
    }

    /**
     * Refreshes the Counter
     */
    public void act() {
        updateImage();
    }

    /**
     * Adds a value to the current Counter
     * @param score score to add
     */
    public void add(int score)
    {
        value += score;
    }

    /**
     * Subtracts a value to the current Counter
     * @param score score to subtract
     */
    public void subtract(int score)
    {
        value -= score;
    }

    /**
     * Gets the Value of current Counter
     * @return The return value of counter
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Gets the Message of current Counter
     * @return The return Message of counter
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the Message of current Counter
     * @param message sets the value of the message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Sets the Value of current Counter
     * @param score A Sets the value of the counter
     */
    public void setValue(int score)
    {
        value = score;
    }

    /**
     * Make the image
     */
    private void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        if(isCounter) {
            image.drawString(message + value, 1, 18);
        }
        else {
            image.drawString(message, 1, 18);
        }
    }
}
