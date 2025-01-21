import greenfoot.*;
/**
 * Write a description of class Item here.
 * 
 * @author: Zhaoqi Xu
 * @version Zhaoqi Xu
 */
public class Item extends SuperSmoothMover {

    // The unique identifier for the item
    protected ObjectID ID;

    // The fixed initial position of the item
    protected int fixedX, fixedY;

    /**
     * Called when the item is added to the world.
     * Fixes the initial location of the item.
     * 
     * @param w The world to which the item is added.
     */
    public void addedToWorld(World w) {
        fixLocation(getX(), getY());
    }

    /**
     * Repositions the item based on whether it is held in the cursor or not.
     */
    public void reposition() {
        // Check if the item is held in the cursor
        if (Cursor.getItem() == this) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                setLocation(mouse.getX(), mouse.getY());
            }
        } else {
            setLocation(fixedX, fixedY);
        }
    }

    /**
     * Fixes the initial location of the item.
     * 
     * @param x The X-coordinate of the fixed location.
     * @param y The Y-coordinate of the fixed location.
     */
    public void fixLocation(int x, int y) {
        fixedX = x;
        fixedY = y;
    }

    /**
     * Gets the ObjectID of the item.
     * 
     * @return The ObjectID of the item.
     */
    public ObjectID getID() {
        return ID;
    }
}