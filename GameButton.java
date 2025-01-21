import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button that zooms in when hovering
 * 
 * @author: Zhaoqi Xu  
 * @version: January 2024
 */
public class GameButton extends Button
{
    /**
     * Constructs a GameButton with the specified name.
     * @param name The name of the button images.
     */

    private int size;
    public GameButton(String name){
        super(name);
        
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        clickImage[0].scale(80, 80);
        hoverImage[0].scale(90, 90);
        mainImage[0].scale(80, 80);
        setImage(mainImage[0]);
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
    }
    /**
     * Same as above
     * @param size size of button width
     */
    public GameButton(String name, int size){
        super(name);
        this.size = size;
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
        
        double ratio = (double) height/width;
        clickImage[0].scale(size, (int) (size * ratio + 0.5));
        
        hoverImage[0].scale(size + 10, (int) ((size + 10) * ratio + 0.5));
        mainImage[0].scale(size, (int) (size * ratio + 0.5));
        setImage(mainImage[0]);
        
        
        
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
    }
    /**
     * Overrides the act method from the superclass.
     * Calls the superclass act method to handle button interactions.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Checks if the mouse is currently hovering over the button.
     * @return True if the mouse is hovering over the button, false otherwise.
     */
    public boolean hoveringThis(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null && getWorld() != null){
            int leftBound = getX() - width/2;
            int rightBound = getX() + width/2;
            int topBound = getY() - height/2;
            int bottomBound = getY() + height/2;
            return mouse.getX() < rightBound && mouse.getX() > leftBound && mouse.getY() < bottomBound && mouse.getY() > topBound;
        }
        return false;
    }
    
    /**
     * Sets the button image to the main image, representing the release state.
     */
    public void release(){
        setImage(mainImage[0]);
    }
    
    /**
     * Sets the button image to the click image, representing the click state.
     */
    public void click(){
        setImage(clickImage[0]);
    }
    
    /**
     * Sets the button image to the hover image, represnting the hover state 
     */
    public void hover(){
        setImage(hoverImage[0]);
    }
    public void drawText(String text, int x, int y, int textSize){
        Font font = new Font("Tekton Pro", true, false,  textSize);
        SuperTextBox box = new SuperTextBox(text, new Color(0,0,0,0), Color.BLACK, font, true, mainImage[0].getWidth(), 0, null);
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        double ratio = (double) height/width;
        clickImage[0].scale(size, (int) (size * ratio + 0.5));
        hoverImage[0].scale(size + 10, (int) ((size + 10) * ratio + 0.5));
        mainImage[0].scale(size, (int) (size * ratio + 0.5));
        setImage(mainImage[0]);
        
        mainImage[0].drawImage(box.getImage(), x, y);
        hoverImage[0].drawImage(box.getImage(), x, y);
        clickImage[0].drawImage(box.getImage(), x, y);
        setImage(mainImage[0]);
    }
}
