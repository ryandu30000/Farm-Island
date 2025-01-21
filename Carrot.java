import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sub Class for Carro
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class Carrot extends Plant
{
    public static final int Y_OFFSET = 32;
    public static final int DEFAULT_GROWTHRATE = 1;
    public static final int GROWTH_STAGES = 4;

    /**
     * Initializes a Carrot plant with the default yield and sell price.
     */
    public Carrot(){
        super(Y_OFFSET);
        ID = ObjectID.CARROT;
        initialize();
        yield = 3;
        sellPrice = 2;
    }

    /**
     * Initializes a Carrot plant with a specified yield.
     * 
     * @param yield The yield of the Carrot plant.
     */
    public Carrot(int yeild){
        super(Y_OFFSET);
        ID = ObjectID.CARROT;
        initialize();
        this.yield = yield;
        sellPrice = 3;
    }

    /**
     * Performs the act of the Carrot plant.
     */
    public void act()
    {
        super.act();
    }

    /**
     * Initializes the Carrot plant with growth animations.
     */
    public void initialize(){
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            setYOffset(stage, -Y_OFFSET);
        }
        
        growthRate = DEFAULT_GROWTHRATE;
        
        growthAnimations = new GreenfootImage[GROWTH_STAGES][3];
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            for(int frame = 0; frame < growthAnimations[stage].length; frame++){
                growthAnimations[stage][frame] = new GreenfootImage("Plants/Carrot/Stage " + stage + "/"+ frame + ".png");
            }
        }

        setImage(growthAnimations[growthStage][0]);
    }

    /**
     * Simulates the growth of the Carrot plant.
     */
    public void grow(){
        maturity += growthRate + myTile.getGrowthMultiplier();
        //1 min to grow fully
        if(maturity % 600 == 0 && growthStage < GROWTH_STAGES - 1){
            growthStage ++;
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }
        
        //when the growthStage is at max the crop is mature
        if(growthStage == GROWTH_STAGES - 1){
            mature = true;
        }
    }

    /**
     * Advances to the next frame of the Carrot plant's growth animation.
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
     * Checks for keypress actions related to the Carrot plant.
     */
    public void checkKeypressAction(){
        
    }

    /**
     * Plays the sound when placing the Carrot plant.
     */
    public void playPlaceSound(){
        
    }

    /**
     * Plays the sound when removing the Carrot plant.
     */
    public void playRemoveSound(){
        
    }

    /**
     * Retrieves the image of the Carrot plant as an item.
     * 
     * @return The image of the Carrot plant as an item.
     */
    public GreenfootImage getItemImage(){
        // Placeholder, needs implementation
        return new GreenfootImage(1,1);
    }
}
