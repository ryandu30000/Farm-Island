import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Strawberry here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Strawberry extends Plant
{
    public static final int Y_OFFSET = 32;
    public static final int DEFAULT_GROWTHRATE = 1;
    public static final int GROWTH_STAGES = 4;
    /**
     * Initializes a Strawberry plant with the default yield and sell price.
     */
    public Strawberry(){
        super(Y_OFFSET);
        ID = ObjectID.STRAWBERRY;
        initialize();
        yield = 3;
        sellPrice = 20;
    }

    /**
     * Initializes a Strawberry plant with a specified yield.
     * 
     * @param yield The yield of the Strawberry plant.
     */
    public Strawberry(int yeild){
        super(Y_OFFSET);
        ID = ObjectID.STRAWBERRY;
        initialize();
        this.yield = yield;
        sellPrice = 30;
    }
    
    /**
     * Performs the act of the Strawberry plant.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Initializes the Strawberry plant with growth animations.
     */
    public void initialize(){
        //add yOffsets
        //setYOffset(_growthStage_, _pixels_)
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            setYOffset(stage, -Y_OFFSET);
        }
        
        growthRate = DEFAULT_GROWTHRATE;
        
        growthAnimations = new GreenfootImage[GROWTH_STAGES][3];
        for(int stage = 0; stage < GROWTH_STAGES; stage++){
            for(int frame = 0; frame < growthAnimations[stage].length; frame++){
                growthAnimations[stage][frame] = new GreenfootImage("Plants/Strawberry/Stage " + stage + "/"+ frame + ".png");
            }

        }

        setImage(growthAnimations[growthStage][0]);
    }
    
    /**
     * Simulates the growth of the Strawberry plant.
     */
    public void grow(){
        maturity += growthRate + myTile.getGrowthMultiplier();
        //1 min to grow fully
        if(maturity % 1200 == 0 && growthStage < GROWTH_STAGES - 1){
            growthStage ++;
            //fade before setting
            fadeOval(growthAnimations[growthStage][0]);
            setImage(growthAnimations[growthStage][0]);
        }
        
        //when the growthStage is at max the crop is mature
        if(growthStage == GROWTH_STAGES - 1){ 
            mature = true;
        }
        
    }
    
    /**
     * Advances to the next frame of the Strawberry plant's growth animation.
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
     * Checks for keypress actions related to the Strawberry plant.
     */
    public void checkKeypressAction(){
        
    }

    /**
     * Plays the sound when placing the Strawberry plant.
     */
    public void playPlaceSound(){
        
    }

    /**
     * Plays the sound when removing the Strawberry plant.
     */
    public void playRemoveSound(){
        
    }

    
    //temporary
    /**
     * Retrieves the image of the Strawberry plant as an item.
     * 
     * @return The image of the Strawberry plant as an item.
     */
    public GreenfootImage getItemImage(){
        //fill
        return new GreenfootImage(1,1);
    }
}
