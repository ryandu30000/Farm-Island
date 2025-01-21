import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a wheat plant 
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class Wheat extends Plant
{
    public static final int Y_OFFSET = 32;
    public static final int DEFAULT_GROWTHRATE = 1;
    public static final int GROWTH_STAGES = 4;

    /**
     * Creates a new Wheat instance.
     */
    public Wheat(){
        super(Y_OFFSET);
        sellPrice = 2;
        ID = ObjectID.WHEAT;
        initialize();
        yield = 1;
    }

    /**
     * Creates a new Wheat instance with a specified yield.
     * 
     * @param yield The yield of the Wheat plant.
     */
    public Wheat(int yeild){
        super(32);
        ID = ObjectID.WHEAT;
        initialize();
        this.yield = yield;
    }

    /**
     * Act - do whatever the Wheat wants to do.
     */
    public void act()
    {
        super.act();
    }

    public void initialize(){
        // Set up yOffsets
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            setYOffset(stage, -Y_OFFSET);
        }

        growthRate = DEFAULT_GROWTHRATE;

        growthAnimations = new GreenfootImage[GROWTH_STAGES][5];
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            for(int frame = 0; frame < growthAnimations[stage].length; frame++){
                growthAnimations[stage][frame] = new GreenfootImage("Plants/Wheat/Stage " + stage + "/" + frame + ".png");
            }

        }

        setImage(growthAnimations[growthStage][0]);
    }

    /**
     * Simulates the growth of the Wheat plant.
     */
    public void grow(){
        maturity += growthRate + myTile.getGrowthMultiplier();
        if(maturity % 300 == 0 && growthStage < GROWTH_STAGES - 1){
            growthStage ++;
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }

        // When the growthStage is at max, the crop is mature
        if(growthStage == GROWTH_STAGES - 1){
            mature = true;
        }
    }

    /**
     * Advances to the next frame of the growth animation.
     */
    public void nextFrame(){
        animationIndex += deltaIndex;
        if(animationIndex >= 0 && animationIndex < growthAnimations[growthStage].length){
            fadeOval(growthAnimations[growthStage][animationIndex]);
            setImage(growthAnimations[growthStage][animationIndex]);
        }
        else{
            deltaIndex *= -1;
            animationIndex += 2 * deltaIndex;
            fadeOval(growthAnimations[growthStage][animationIndex]);
            setImage(growthAnimations[growthStage][animationIndex]);
        }
    }

    /**
     * Checks for key press actions (not implemented in this class).
     */
    public void checkKeypressAction(){

    }

    /**
     * Plays the sound when placing the Wheat plant (not implemented in this class).
     */
    public void playPlaceSound(){
    }

    /**
     * Plays the sound when removing the Wheat plant (not implemented in this class).
     */
    public void playRemoveSound(){
    }

    /**
     * Gets the image of the Wheat item (temporary implementation).
     * 
     * @return A GreenfootImage representing the Wheat item.
     */
    public GreenfootImage getItemImage(){
        return new GreenfootImage(1, 1);
    }
}
