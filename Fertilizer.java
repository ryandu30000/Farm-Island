import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class to represent the fertilizer item in the game. It is a subclass of Item.
 * 
 * @author: Ryan Du
 * @version: January 2024
 */
public class Fertilizer extends Item
{
    private boolean disapearWhenEmpty;
    private GreenfootImage fertilizerImage;
    private ObjectID ID;
    private boolean display;
    private int fixedX, fixedY;
    private static int fertilizerSize;
    
    /**
     * Constructor for Fertilizer class.
     * 
     * @param ID The ObjectID of the fertilizer.
     * @param amount The initial amount of fertilizer.
     * @param disappear When true, the fertilizer disappears when empty.
     */
    public Fertilizer(ObjectID ID, int amount, boolean disappear) {
        fertilizerSize = 5;
        disapearWhenEmpty = disappear;

        // Add fertilizer to inventory
        Inventory.add(ID, amount);
        this.ID = ID;

        fertilizerImage = ID.getSeedID().getDisplayImage();
        display = false;
        setImage(fertilizerImage);
    }

    /**
     * Constructor for Fertilizer class with default disappear behavior (false).
     * 
     * @param ID The ObjectID of the fertilizer.
     * @param amount The initial amount of fertilizer.
     */
    public Fertilizer(ObjectID ID, int amount) {
        this(ID, amount, false);
    }

    /**
     * Constructor for Fertilizer class with default amount (1).
     * 
     * @param ID The ObjectID of the fertilizer.
     */
    public Fertilizer(ObjectID ID) {
        this(ID, -1);
    }

    /**
     * Called when the fertilizer is added to the world.
     * Fixes the initial location of the fertilizer.
     */
    public void addedToWorld() {
        fixLocation(getX(), getY());
    }

    /**
     * Act method for Fertilizer class.
     * Checks for mouse actions and repositions the fertilizer.
     */
    public void act() {
        if (getWorld() == null) {
            return;
        }
        reposition();
        checkMouseAction();
    }

    /**
     * Repositions the fertilizer based on whether it is held in the cursor or not.
     */
    public void reposition() {
        // Check if the fertilizer is held in the cursor
        if (Cursor.getItem() == this) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                setLocation(mouse.getX(), mouse.getY());
            }
        }
    }

    /**
     * Checks for mouse actions related to the fertilizer.
     */
    public void checkMouseAction() {
        if (Greenfoot.mousePressed(this)) {
            Cursor.pickUp(this);
        }
        if (Greenfoot.mouseClicked(this)) {
            Cursor.release();
        }
    }

    /**
     * Fixes the initial location of the fertilizer.
     * 
     * @param x The X-coordinate of the fixed location.
     * @param y The Y-coordinate of the fixed location.
     */
    public void fixLocation(int x, int y) {
        fixedX = x;
        fixedY = y;
    }

    /**
     * Removes one unit of fertilizer from the inventory.
     */
    public void removeOne(){
        fertilizerSize --;
    }
    
    public int getFertilizerSize(){
        return fertilizerSize;
    }
}