import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CopyOfCarrot here.
 * 
 * @author: Zhaoqi Xu 
 * @version: January 2024
 */
public class Tomato extends Plant
{
    public static final int Y_OFFSET = 32;
    public static final int DEFAULT_GROWTHRATE = 1;
    public static final int GROWTH_STAGES = 4;

    /**
     * Initializes a Tomato plant with the default yield and sell price.
     */
    public Tomato(){
        super(Y_OFFSET);
        ID = ObjectID.TOMATO;
        initialize();
        yield = 3;
        sellPrice = 3;
    }

    /**
     * Initializes a Tomato plant with a specified yield.
     * 
     * @param yield The yield of the Tomato plant.
     */
    public Tomato(int yeild){
        super(Y_OFFSET);
        ID = ObjectID.TOMATO;
        initialize();
        this.yield = yield;
    }

    /**
     * Performs the act of the Tomato plant.
     */
    public void act()
    {
        super.act();
    }

    /**
     * Initializes the Tomato plant with growth animations.
     */
    public void initialize(){
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            setYOffset(stage, -Y_OFFSET);
        }

        growthRate = DEFAULT_GROWTHRATE;

        growthAnimations = new GreenfootImage[GROWTH_STAGES][3];
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            for(int frame = 0; frame < growthAnimations[stage].length; frame++){
                growthAnimations[stage][frame] = new GreenfootImage("Plants/Tomato/Stage " + stage + "/"+ frame + ".png");
            }
        }

        setImage(growthAnimations[growthStage][0]);
    }

    /**
     * Simulates the growth of the Tomato plant.
     */
    public void grow(){
        maturity += growthRate;
        if(maturity % 1800 == 0 && growthStage < GROWTH_STAGES - 2){
            growthStage++;
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }
        else if(maturity % 3600 == 0 && growthStage < GROWTH_STAGES - 1){
            growthStage++;
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }

        if(growthStage == GROWTH_STAGES - 1){
            mature = true;
        }
    }

    /**
     * Advances to the next frame of the Tomato plant's growth animation.
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
     * Checks for keypress actions related to the Tomato plant.
     */
    public void checkKeypressAction(){

    }

    /**
     * Plays the sound when placing the Tomato plant.
     */
    public void playPlaceSound(){

    }

    /**
     * Plays the sound when removing the Tomato plant.
     */
    public void playRemoveSound(){

    }

    /**
     * Retrieves the image of the Tomato plant as an item.
     * 
     * @return The image of the Tomato plant as an item.
     */
    public GreenfootImage getItemImage(){
        // Placeholder, needs implementation
        return new GreenfootImage(1, 1);
    }
}
