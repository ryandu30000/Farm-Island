import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * abstract class for all type of buttons
 * 
 * @author: Zhaoqi Xu, Carson Cooc 
 * @version: 
 */
public abstract class Button extends SuperSmoothMover
{
    protected GreenfootImage[] mainImage;
    protected GreenfootImage[] hoverImage;
    protected GreenfootImage[] clickImage;
    protected String imageName;
    protected int width, height;
    protected boolean clicked;
    private boolean mouseOver;
    private GreenfootSound hoverSound;
    private GreenfootSound[] releaseSound;
    private GreenfootSound[] clickSound;

    private int soundIndex;
    
    /**
     * In charge of the sounds for the buttom, the changing of display for the buttons (eg; when hovering over button, pressed button, main image)
     * @param imageName the String name of the button images 
     */
    public Button(String imageName){
        this.imageName = imageName;
        mainImage = new GreenfootImage[1];
        hoverImage = new GreenfootImage[1];
        clickImage = new GreenfootImage[1];

        hoverSound = new GreenfootSound ("HoverSoundmp3.mp3");
        hoverSound.setVolume(50);

        releaseSound = new GreenfootSound[6];
        clickSound = new GreenfootSound[6];
        for(int i = 0; i < releaseSound.length; i++){
            releaseSound[i] = new GreenfootSound("Releasemp3.mp3");
            releaseSound[i].setVolume(50);
            clickSound[i] = new GreenfootSound("Clickmp3.mp3");
            clickSound[i].setVolume(90);
        }
    
        soundIndex = 0;
    }

    /**
     * Called when the Button is added to the world.
     * 
     * @param w The world that the Button is added to.
     */
    public void addedToWorld(World w){
        clicked = false;
    }

    /**
     * Checks when the mouse is hovering or pressing a button 
     */
    public void act()
    {
        //mouse pressed + mouse release combo to check for hold
        if(Greenfoot.mousePressed(null) && hoveringThis()){
            click();
            clickSound();
            clicked = true;
        }
        if(Greenfoot.mouseClicked(null) && clicked){
            release();
            releaseSound();
            clicked = false;
        }
        //hovering animation
        if(!clicked){
            if(hoveringThis() != mouseOver){
                mouseOver = ! mouseOver;
                if (mouseOver) // hover begins?
                {
                    hoverSound.play();
                }
            }
            if(hoveringThis()){
                hover();

            }
            else{
                release();
            }
        }
    }

    /**
     * The sound that plays when a button is clicked
     */
    public void clickSound()
    {
        clickSound[soundIndex].play();
        soundIndex++;
        if (soundIndex == clickSound.length){
            soundIndex = 0;
        }
    }

    /**
     * The sound that plays when a button is released 
     */
    public void releaseSound()
    {
        releaseSound[soundIndex].play();
        soundIndex++;
        if (soundIndex == releaseSound.length){
            soundIndex = 0;
        }
    }

    /**
     * Abstract method to be implemented by subclasses.
     * Defines the behavior when the mouse hovers over the button.
     */
    public abstract void hover();

    /**
     * Abstract method to be implemented by subclasses.
     * Defines the behavior when the mouse hovers over the button 
     */
    public abstract void click();
    
    /**
     * Abstract method to be implemented by subclasses.
     * Defines the behavior when the button is released 
     */
    public abstract void release();

    /**
     * Abstract method to be implemented by subclasses.
     * Checks if the mouse is hovering over the button.
     *
     * @return True if the mouse is hovering over the button, false otherwise.
    **/
    public abstract boolean hoveringThis();

    /**
     * Checks if the left mouse button is clicked over the button.
     * @return True if the left mouse button is clicked and the mouse is over the button, false otherwise.
     */
    public boolean leftClickedThis(){
        return Greenfoot.mouseClicked(null) && hoveringThis() && Cursor.leftClicked();
    }
    
    /**
     * Draws text on the button with default settings.
     *
     * @param text The text to be drawn on the button.
     */
    public void drawText(String text){
        drawText(text, 0, 0);
    }
    
    /**
     * Draws text on the button with a specified font size.
     *
     * @param text The text to be drawn on the button.
     * @param size The font size for the text.
     */
    public void drawText(String text, int size){
        drawText(text,0,0, size);
    }
    
    /**
     * Draws text on the button at a specified position.
     *
     * @param text The text to be drawn on the button.
     * @param x    The x-coordinate of the text position.
     * @param y    The y-coordinate of the text position.
     */
    public void drawText(String text, int x, int y){
        drawText(text, x, y, 35);
    }
    
    /**
     * Draws text on the button at a specified position with a specified font size.
     *
     * @param text     The text to be drawn on the button.
     * @param x        The x-coordinate of the text position.
     * @param y        The y-coordinate of the text position.
     * @param textSize The font size for the text.
     */
    public void drawText(String text, int x, int y, int textSize){
        Font font = new Font("Tekton Pro", true, false,  textSize);
        SuperTextBox box = new SuperTextBox(text, new Color(0,0,0,0), Color.BLACK, font, true, mainImage[0].getWidth(), 0, null);
        clickImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        hoverImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");        
        mainImage[0] = new GreenfootImage("Buttons/" + imageName + ".png");
        
        mainImage[0].drawImage(box.getImage(), x, y);
        hoverImage[0].drawImage(box.getImage(), x, y);
        clickImage[0].drawImage(box.getImage(), x, y);
        setImage(mainImage[0]);
    }
}
