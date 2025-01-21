import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a Porcus Wheat plant in the game.
 * 
 * @author: Angela Gao, Zhaoqi Xu
 * @version: January 2024
 */
public class PorcusWheat extends Plant implements ItemConvertible
{
    public static final int DEFAULT_GROWTHRATE = 1;
    public static final int GROWTH_STAGES = 3;
    /**
     * Initializes a Porcus Wheat plant with the default yield and sell price.
     */
    public PorcusWheat(){
        super();
        ID = ObjectID.PORCUS_WHEAT;
        initialize();
        yield = 5;
        sellPrice = 200;
    }

    /**
     * Initializes a Porcus Wheat plant with a specified yield.
     * 
     * @param yield The yield of the Porcus Wheat plant.
     */
    public PorcusWheat(int yield){
        super();
        initialize();
        this.yield = yield;
    }

    /**
     * Performs the act of the Porcus Wheat plant.
     */
    public void act()
    {
        super.act();
    }

    /**
     * Initializes the Porcus Wheat plant with growth animations.
     */
    public void initialize(){
        growthRate = DEFAULT_GROWTHRATE;

        growthAnimations = new GreenfootImage[GROWTH_STAGES][3];
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            // Initialize offsets
            setYOffset(stage, -16);
            for(int frame = 0; frame < growthAnimations[stage].length; frame++){
                growthAnimations[stage][frame] = new GreenfootImage("Plants/PorcusWheat/Stage " + stage + "/" + frame + ".png");
            }
        }

        setImage(growthAnimations[growthStage][0]);
    }

    /**
     * Simulates the growth of the Porcus Wheat plant.
     */
    public void grow(){
        maturity += growthRate + myTile.getGrowthMultiplier();
        if(maturity % 12000 == 0 && growthStage < GROWTH_STAGES - 1){
            growthStage ++;
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }

        if(growthStage == GROWTH_STAGES - 1){
            mature = true;
        }
    }

    /**
     * Advances to the next frame of the Porcus Wheat plant's growth animation.
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
     * Checks for keypress actions related to the Porcus Wheat plant.
     */
    public void checkKeypressAction(){

    }

    /**
     * Plays the sound when placing the Porcus Wheat plant.
     */
    public void playPlaceSound(){

    }

    /**
     * Plays the sound when removing the Porcus Wheat plant.
     */
    public void playRemoveSound(){

    }

    /**
     * Retrieves the image of the Porcus Wheat plant as an item.
     * 
     * @return The image of the Porcus Wheat plant as an item.
     */
    public GreenfootImage getItemImage(){
        // Placeholder, needs implementation
        return new GreenfootImage("Stubby Wheat Stage 0.png");
    }
}
