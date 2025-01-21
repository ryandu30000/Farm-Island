import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The MenuButton class represents a button with additional features for a menu. It extends the Button class and provides specific implementations for button behavior 
 * and appearance in the context of a menu
 * 
 * The button can be clicked, hovered over, and released, triggering specific actions. The appearance of the button changes based on these interactions, and can also 
 * have an offset effect when hovered over. Additionally, text can be drawn on the button 
 * 
 * @author: Zhaoqi Xu 
 * @version January 2024
 */
public class MenuButton extends Button
{
    private boolean offseted;
    private int offset;
    
    /**
     * Constructs a MenuButton with the specified image name.
     * @param imageName The name of the button images.
     */
    public MenuButton(String imageName){
        //(imageName, width, height)
        super(imageName);
        
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + " 1.png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        setImage(mainImage[0]);
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
    }
    
    /**
     * Constructs a MenuButton with the specified image name and text.
     *
     * @param imageName The name of the button images.
     * @param text      The text to be drawn on the button.
     */
    public MenuButton(String imageName, String text){
        super(imageName);
        
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + " 1.png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        setImage(mainImage[0]);
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
        mainImage[0].drawString(text,10,height - 10);
    }
    
    /**
     * Overrides the addedToWorld method from the superclass.
     * Calls the superclass addedToWorld method to handle initialization.
     */
    public void addedToWorld(World w){
        super.addedToWorld(w);
        offset = 5;
        offseted = false;
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
     * Overrides the hover method from the superclass.
     * Adds an offset effect when hovered over and sets the hover image.
     */
    public void hover(){
        if(!offseted){
            setLocation(getX() + offset, getY() - 2 * offset);
            offseted = true;
        }
        setImage(hoverImage[0]);        
    }

    /**
     * Overrides the click method from the superclass.
     * Adds an offset effect when clicked and sets the click image.
     */
    public void click(){
        if(!offseted){
            setLocation(getX() + offset, getY() - 2 * offset);
            offseted = true;
        }
        setImage(clickImage[0]);
    }

    /**
     * Overrides the release method from the superclass.
     * Removes the offset effect when released and sets the main image.
     */
    public void release(){
        if(offseted) {
            setLocation(getX() - offset, getY() + 2 * offset);
            offseted = false;
        }
        setImage(mainImage[0]);
    }
    
    /**
     * Overrides the hoveringThis method from the superclass.
     * Takes into account the offset effect when checking for hovering.
     *
     * @return True if the mouse is hovering over the button, false otherwise.
     */
    public boolean hoveringThis(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null && getWorld() != null){
            int leftBound = getX() - width/2;
            int rightBound = getX() + width/2;
            int topBound = getY() - height/2;
            int bottomBound = getY() + height/2;
            if(offseted){
                leftBound -= offset;
                rightBound -= offset;
                topBound += 2 * offset;
                bottomBound += 2 * offset;
            }
            
            return mouse.getX() < rightBound && mouse.getX() > leftBound && mouse.getY() < bottomBound && mouse.getY() > topBound;
        }
        return false;
    }
    
    /**
     * Overrides the drawText method from the superclass.
     * Draws text on the button with a specified font size.
     *
     * @param text     The text to be drawn on the button.
     * @param x        The x-coordinate of the text position.
     * @param y        The y-coordinate of the text position.
     * @param textSize The font size for the text.
     */
    public void drawText(String text, int x, int y, int textSize){
        Font font = new Font("Tekton Pro", true, false,  textSize);
        SuperTextBox box = new SuperTextBox(text, new Color(0,0,0,0), Color.BLACK, font, true, mainImage[0].getWidth(), 0, null);
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + " 1.png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        
        mainImage[0].drawImage(box.getImage(), x, y);
        hoverImage[0].drawImage(box.getImage(), x, y);
        clickImage[0].drawImage(box.getImage(), x, y);
        setImage(mainImage[0]);
    }
}
