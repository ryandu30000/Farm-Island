import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EquipFrame here.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class EquipFrame extends ItemFrame {

    public static final Color HIGHLIGHT_COLOR = new Color(190, 149, 92);
    public static final int MARGIN = 4;
    private GreenfootImage highlight;
    private Tool tool;
    private boolean selected;

    /**
     * Constructs an EquipFrame with a specified ID, width, and height.
     * 
     * @param ID The ObjectID representing the equipped item.
     * @param width The width of the frame.
     * @param height The height of the frame.
     */
    public EquipFrame(ObjectID ID, int width, int height) {
        super(ID, width, height);
        mainImage = new GreenfootImage(width, height);
        background = new GreenfootImage("Displays/Frames/Wooden Frame.png");
        background.scale(width, height);
        updateID(ID);
        updateHighlight();
    }

    /**
     * Handles actions to be performed in each act cycle.
     * Checks for mouse actions to determine if the frame is being hovered.
     */
    public void act() {
        checkMouseAction();
    }

    /**
     * Checks if the mouse is hovering over the frame and updates the frame accordingly.
     */
    public void checkMouseAction() {
        if (hoveringThis() || selected) {
            setImage(highlight);
        } else {
            setImage(mainImage);
        }
    }
    /**
     * checks if selected
     * @return boolean selected
     */    
    public boolean isSelected(){
        return selected;
    }
    

    /**
     * Selects the frame, indicating that the equipped item is selected.
     */
    public void select() {
        selected = true;
    }

    /**
     * Unselects the frame, indicating that the equipped item is not selected.
     */
    public void unselect() {
        selected = false;
    }

    /**
     * Updates the highlight image based on the main image and margin.
     */
    public void updateHighlight() {
        highlight = new GreenfootImage(mainImage.getWidth() + 2 * MARGIN, mainImage.getHeight() + 2 * MARGIN);
        highlight.setColor(HIGHLIGHT_COLOR);
        highlight.fill();
        highlight.drawImage(mainImage, MARGIN, MARGIN);
    }

    /**
     * Updates the displayed item in the frame and adjusts its scale.
     * 
     * @param ID The new ObjectID representing the equipped item.
     */
    public void updateID(ObjectID ID) {
        super.updateID(ID);
        double ratio = (double) foreground.getHeight() / foreground.getWidth();
        foreground.scale(64, (int) (64 * ratio + 0.5));
        updateHighlight();
    }
}
