import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button that will toggle on and off
 * 
 * @author Zhaoqi Xu
 * @version January 2023
 */
public class ToggleButton extends Button
{
    private boolean toggle;
    private GreenfootImage ball;
    /**
     * basic constructor
     * 
     * @param name name of image
     */
    public ToggleButton (String name){
        super(name);
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + " On.png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + " Off.png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + " Off.png");
        
        ball = new GreenfootImage("Buttons/" + imageName + " Switch.png");
        
        
        
        width = mainImage[0].getWidth();
        height = mainImage[0].getHeight();
        
        mainImage[0].drawImage(ball, 5, 3);
        hoverImage[0].drawImage(ball, width - 5 - ball.getWidth(), 3);
        clickImage[0].drawImage(ball, width - 5 - ball.getWidth(), 3);
        setImage(mainImage[0]);
    }
    /**
     * checks if the cursor is hovering the button
     * 
     * @return boolean
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
     * Act - do whatever the Toggle wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * gets the state of the button
     * 
     * @return boolean the state of the button
     */
    public boolean getToggle(){
        return toggle;
    }
    
    public void release(){

    }
    /**
     * sets the image acording to toggle
     */
    public void click(){
        toggle = !toggle;
        if(toggle){
            setImage(clickImage[0]);            
        }
        else{
            setImage(mainImage[0]);
        }
    }
    public void hover(){

    }
}
