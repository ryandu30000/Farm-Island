import greenfoot.*;
/**
 * ObjectID enum represents the unique identifiers for various in-game objects.
 * It provides methods to obtain related images for display purposes. 
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public enum ObjectID  
{
    NONE, DIRT_TILE, LANDPLOT, PORCUS_WHEAT, PORCUS_WHEAT_SEED, WHEAT, WHEAT_SEED, CARROT, CARROT_SEED, TOMATO, TOMATO_SEED, BLUEBERRY, BLUEBERRY_SEED,
    DRAGONFRUIT, DRAGONFRUIT_SEED, STRAWBERRY, STRAWBERRY_SEED, GOLDEN_TOMATO, GOLDEN_TOMATO_SEED, SILVER_TOMATO, SILVER_TOMATO_SEED, 
    BASIC_TOOL, DIAMOND_TOOL, SHOVEL, FERTILIZER;
    
    /**
     * Gets the corresponding seed ID for crops.
     * 
     * @return The seed ID related to the crop, or default WHEAT_SEED if not found.
     */
    public ObjectID getSeedID(){
        switch(this){
            case WHEAT:
                return WHEAT_SEED;
            case PORCUS_WHEAT:
                return PORCUS_WHEAT_SEED; 
            case CARROT:
                return CARROT_SEED;
            case TOMATO:
                return TOMATO_SEED;
            case SILVER_TOMATO:
                return SILVER_TOMATO_SEED;
            case GOLDEN_TOMATO:
                return GOLDEN_TOMATO_SEED;
            case BLUEBERRY:
                return BLUEBERRY_SEED;
            case DRAGONFRUIT:
                return DRAGONFRUIT_SEED;
            case STRAWBERRY: 
                return STRAWBERRY_SEED;
            case FERTILIZER:
                return FERTILIZER;
        }
        return WHEAT_SEED;
    }
    
    /**
     * Gets the display image associated with the object.
     * 
     * @return The GreenfootImage for displaying the object.
     */
    public GreenfootImage getDisplayImage(){
        return new GreenfootImage("Displays/Items/" + this.toString() + ".png");
    }
    
    /**
     * Gets the tool image associated with the object.
     * 
     * @return The GreenfootImage for displaying the tool.
     */
    public GreenfootImage getToolImage(){
        return new GreenfootImage("Tools/" + this.toString() + ".png");
    }
}
