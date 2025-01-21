import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class for tools that the user can use.
 * 
 * @author: Zhaoqi Xu, Mandy Zhuang
 * @version December 2023
 */

public class Tool extends Item {
    private int durability;
    private int efficiency;
    private boolean unbreakable;
    private GreenfootImage toolImage;
    private int rarity;

    /**
     * Constructor for the Tool class.
     * 
     * @param ID The ObjectID of the tool.
     */
    public Tool(ObjectID ID) {
        this.ID = ID;
        toolImage = ID.getToolImage();
        double ratio = (double) toolImage.getHeight() / toolImage.getWidth();
        toolImage.scale(50, (int) (50 * ratio + 0.5));

        // Set efficiency based on tool type
        setEfficiencyBasedOnType(ID);

        // Set unbreakable status based on tool type
        setUnbreakableStatusBasedOnType(ID);

        setImage(toolImage);
    }
    
    public void addedToWorld(World w){
        super.addedToWorld(w);
    }
    
    /**
     * Gets the efficiency of the tool.
     * 
     * @return int - The efficiency of the tool.
     */
    public int getEfficiency() {
        return efficiency;
    }

    /**
     * Takes away from the durability of the tool.
     */
    public void takeDurability() {
        if (unbreakable) {
            return;
        }
        durability--;
        // Add check to ensure durability doesn't go below zero
        if (durability < 0) {
            durability = 0;
        }
    }

    /**
     * Activates the effect associated with the tool.
     */
    public void activateEffect() {
        // Place effects here
        switch (ID) {

        }
    }

    /**
     * Act method for the Tool class.
     * This method is called whenever the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (getWorld() == null) {
            return;
        }
        reposition();
    }
    /**
     * sets the efficiency based on ID
     * 
     * @param ID the ID of the object
     */
    private void setEfficiencyBasedOnType(ObjectID ID) {
        switch (ID) {
            case BASIC_TOOL:
                efficiency = 1;
                break;
            case DIAMOND_TOOL:
                efficiency = 10;
                break;
            case SHOVEL:
                efficiency = 1;
                break;
        }
    }
    
    /**
     * sets the tool to unbreakable
     * @param ID the ID of the tool
     */
    private void setUnbreakableStatusBasedOnType(ObjectID ID) {
        // Set unbreakable status based on tool type
        unbreakable = (ID == ObjectID.BASIC_TOOL || ID == ObjectID.DIAMOND_TOOL || ID == ObjectID.SHOVEL);
    }
    
}