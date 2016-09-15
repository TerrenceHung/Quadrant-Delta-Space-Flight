import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.awt.Font;
import java.awt.Color;
//ENTIRE CLASS BORROWED FROM MR. COHEN'S MOODLE
/**
 * Counter that displays a number or a message.
 * <p>
 * Changelog v2.1:
 * Changed font from Sansserif to Bankgothic Lt BT.
 * <p>
 * Changelog v2.2:
 * Added initial value as a parameter in the constructor.
 * 
 * @author Michael Kolling & Brian Huang & Terrence Hung
 * @version 2.2
 */
public class Counter extends Actor
{   
    private static final int FONT_SIZE = 20;
    private int value = 0;  
    private String message;
    private boolean isCounter = true;

    /**
     * Creates the size, font and length of the Counter
     * @param prefix the size needed for the Counter
     * @param value The initial value for the Counter
     * @param color sets the color of the counter
     * @param isCounter ask if it only want a message (False for message only)
     */
    public Counter(String prefix, int value, Color color,boolean isCounter)
    {
        this.isCounter = isCounter;
        this.value = value; //sets the initial value of the counter
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
