import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class representing a frame for displaying an item in the world.
 * This class provides a frame with a background and a foreground image to display an item.
 * It allows updating the displayed item and checking if the frame is being hovered. 
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024 
 */
public class ItemFrame extends SuperSmoothMover {

    protected ObjectID ID;
    protected GreenfootImage background;
    protected GreenfootImage foreground;
    protected GreenfootImage mainImage;
    protected int width, height;

    /**
     * Constructs an ItemFrame with a default size of 128x128.
     * 
     * @param ID The ObjectID representing the item to be displayed in the frame.
     */
    public ItemFrame(ObjectID ID) {
        this(ID, 128, 128);
    }

    /**
     * Constructs an ItemFrame with a specified size.
     * 
     * @param ID The ObjectID representing the item to be displayed in the frame.
     * @param width The width of the frame.
     * @param height The height of the frame.
     */
    public ItemFrame(ObjectID ID, int width, int height) {
        this.width = width;
        this.height = height;
        mainImage = new GreenfootImage(width, height);
        foreground = ID.getDisplayImage();
        background = new GreenfootImage("Displays/Frames/Frame.png");
        background.scale(width, height);
        updateID(ID);   
    }
    public ItemFrame(ObjectID ID, String imageName, int width, int height){
        this.width = width;
        this.height = height;
        mainImage = new GreenfootImage(width, height);
        foreground = ID.getDisplayImage();
        background = new GreenfootImage("Displays/Frames/" + imageName + ".png");
        background.scale(width, height);
        updateID(ID); 
    }

    /**
     * Gets the ObjectID of the item displayed in the frame.
     * 
     * @return The ObjectID of the item.
     */
    public ObjectID getID() {
        return ID;
    }

    /**
     * Updates the displayed item in the frame.
     * 
     * @param ID The new ObjectID representing the item to be displayed.
     */
    public void updateID(ObjectID ID) {
        this.ID = ID;
        foreground = ID.getDisplayImage();
        drawFrame();
    }

    /**
     * Draws the frame with the background and the displayed item.
     * Adjusts the scale and position of the foreground image to fit within the frame.
     */
    public void drawFrame() {
        mainImage.clear();
        mainImage.drawImage(background, 0, 0);
        int marginY = (background.getHeight() - foreground.getHeight()) / 2;
        int marginX = (background.getWidth() - foreground.getWidth()) / 2;
        if (marginY < 0 || marginX < 0) {
            double ratio = (double) foreground.getHeight() / foreground.getWidth();
            foreground.scale(background.getWidth(), (int) (background.getWidth() * ratio + 0.5));
            marginY = (background.getHeight() - foreground.getHeight()) / 2;
            marginX = (background.getWidth() - foreground.getWidth()) / 2;
        }
        mainImage.drawImage(foreground, marginX, marginY);
        setImage(mainImage);
    }

    /**
     * Checks if the mouse is hovering over the frame.
     * 
     * @return True if the mouse is hovering over the frame, false otherwise.
     */
    public boolean hoveringThis() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int leftBound = getX() - width / 2;
            int rightBound = getX() + width / 2;
            int topBound = getY() - height / 2;
            int bottomBound = getY() + height / 2;
            return mouse.getX() < rightBound && mouse.getX() > leftBound && mouse.getY() < bottomBound && mouse.getY() > topBound;
        }
        return false;
    }
}
