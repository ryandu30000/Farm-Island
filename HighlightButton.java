import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * button that will highlight when hovering
 * 
 * @author: ZhaoQi Xu 
 * @version: January 2024
 */
public class HighlightButton extends Button
{
    /**
     * Constructs a HighlightButton with the specified name.
     * @param name The name of the button images.
     */
    public HighlightButton(String name){
        super(name);
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + " H.png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
    }
    
    /**
     * Overrides the act method from the superclass
     * Calls the superclass act method to handle button interactions 
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Checks whether or not the mouse is currently hovering the button 
     * @return True if the mouse is hovering over the button, false otherwise
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
     * Sets the button image to the main image, representing the release state 
     */
    public void release(){
        setImage(mainImage[0]);
    }
    
    /**
     * Sets the button image to the click image, representing the clicked state 
     */
    public void click(){
        setImage(clickImage[0]);
    }
    
    /**
     * Sets the button image to the hover image, representing the hover state 
     */
    public void hover(){
        setImage(hoverImage[0]);
    }
    
    /**
     * Draws text on the button with a specified font size 
     * @param text the text to be drawn on the button 
     * @param x the x-coordinate of the text position
     * @param y the y-coordinate of the text position
     * @param textSize the font size for the text 
     */
    public void drawText(String text, int x, int y, int textSize){
        Font font = new Font("Tekton Pro", true, false,  textSize);
        SuperTextBox box = new SuperTextBox(text, new Color(0,0,0,0), Color.BLACK, font, true, mainImage[0].getWidth(), 0, null);
        mainImage[0].drawImage(box.getImage(), x, y);
        hoverImage[0].drawImage(box.getImage(), x, y);
        clickImage[0].drawImage(box.getImage(), x, y);
        setImage(mainImage[0]);
    }
    
    /**
     * Sets the transparancy of the button imahes 
     * @param transparancy The Transparency valud (0 to 255)
     */
    public void setTransparancy(int transparency){
        mainImage[0].setTransparency(transparency);
        hoverImage[0].setTransparency(transparency);
        clickImage[0].setTransparency(transparency);
        setImage(mainImage[0]);
    }
}
