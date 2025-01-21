import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * Super Class for all plants that can be placed on dirtTiles
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Plant extends Tile
{
    protected ObjectID ID;
    
    protected int growthRate;
    protected int maturity; 
    protected int growthStage;
    protected int lifeTime;
    protected boolean mature;
    protected int yield;
    protected int sellPrice;

    //[Growth Stage][Animation Frame];
    protected GreenfootImage[][] growthAnimations;
    protected int animationIndex;
    protected int deltaIndex;
    protected DirtTile myTile;
    protected HashMap<Integer, Integer> yOffsets;
    protected SimpleTimer actTimer;
    
    /**
     * Simple constructor
     */
    public Plant(){
        this(0); 
    }
    /**
     * constructor with offset
     * @param offset the offset of the plant
     */
    public Plant(int offset){
        super(offset);
        myTile = null;
        myPlot = null;
        deltaIndex = 1;
        lifeTime = 0;
        growthStage = 0;
        maturity = 0;
        mature = false;
        //nextFrame would be 0
        animationIndex = -1;

        

        actTimer = new SimpleTimer();
        yOffsets = new HashMap<>();
    }

    public void act()
    {
        super.act();
        if(getWorld() == null){
            return;
        }

        // 1/60 of a second is 16.666 milliseconds
        if(actTimer.millisElapsed() >= 17){
            actTimer.mark();
            if(myTile != null){
                if(myTile.getWorld() != null){
                    setLocation(myTile.getX(), myTile.getY() + yOffsets.get(growthStage) + myTile.getTileYOffset()/2);
                    if(!mature){
                        grow();
                    }
                }
                else{
                    //collects if mature returns seed if not
                    myTile.unPlant();
                    if(mature){

                        CollectionHandler.collect(this);
                    }
                    else{
                        Inventory.add(ID.getSeedID());
                    }
                    if(getWorld() != null){
                        getWorld().removeObject(this);
                    }

                    return;
                }
            }
            //animate
            if(lifeTime % 12 == 0 && growthStage >= 0){
                nextFrame();
            }
            //fade
            if(growthStage >= 0){
                fadeOval(getImage());
                lifeTime++;
            }
        }
    }
    /**
     * abstract method for growing
     */
    public abstract void grow();
    /**
     * checks mouse action
     */
    public void checkMouseAction(){
        if(hoveringThis() && Cursor.leftClicked()){
            if(mature){
                CollectionHandler.collect(this);
                AchievementManager.updateLatestPlant(this.getID());
            }
            else{
                CollectionHandler.shovel(this);
            }
        }
    }
    /**
     * plants itself onto a tile 
     * @param plot the plot of the dirt tile
     * @param tile the tile to plant on
     */
    public void plant(LandPlot plot, DirtTile tile){
        myPlot = plot;
        myTile = tile;
        plot.zSort();
    }
    /**
     * abstract method for animation
     */
    public abstract void nextFrame();
    
    /**
     * sets the y offset of individual growth stages
     * @param growthStage the stage of the plant
     * @param pixels the offset pixels
     */
    public void setYOffset(int growthStage, int pixels){
        yOffsets.put(growthStage, pixels);
    }
    /**
     * gets the sell price of the plant
     * @return int the sell price
     */
    public int getSellPrice(){
        return sellPrice;
    }
    /**
     * gets the growth rate of the plant
     * @return int the growthrate
     */
    public int getGrowthRate(){
        return growthRate;
    }
    /**
     * sets the growth rate
     * @param growthRate the growth rate of the plant
     */
    public void setGrowthRate(int growthRate){
        this.growthRate = growthRate;
    }
    /**
     * gets the ID of the plant
     * @return ObjectID
     */
    public ObjectID getID(){
        return ID;
    }
    /**
     * gets the dirt tile
     * @return DirtTile the tile returned
     */
    public DirtTile getTile(){
        return myTile;
    }
    /**
     * gets the maturity
     * @return int the maturity
     */
    public int getMaturity(){
        return maturity;
    }
    /**
     * sets the maturity
     * @param maturity 
     */
    public void setMaturity(int maturity){
        this.maturity = maturity;
    }
    /**
     * gets the growth stage
     * @return int the growth stage
     */
    public int getGrowthStage(){
        return growthStage;
    }
    /**
     * set growth stage
     * @param growthStage 
     */
    public void setGrowthStage(int growthStage){
        if(this.growthStage > growthStage && mature){
            mature = false;
        }
        this.growthStage = growthStage;
    }
    /**
     * gets the lifetime of the plant
     * @return int
     */
    public int getLifeTime(){
        return lifeTime;
    }
    /**
     * sets the lifetime of the plant
     * @param lifetime
     */
    public void setLifeTime(int lifeTime){
        this.lifeTime = lifeTime;
    }
    /**
     * checks if plant is mature
     * @return boolean is the plant return
     */
    public boolean isMature(){
        return mature;
    }
    /**
     * sets the plant to mature
     * @param mature
     */
    public void setMature(boolean mature){
        this.mature = mature;
    }
    /**
     * gets the yield
     * @return int the yield of the plant
     */
    public int getYield(){
        return yield;
    }
    /**
     * sets the yield of the plant
     * @param the yeild of the plant
     */
    public void setYield(int yield){
        this.yield = yield;
    }
    /**
     * gets the dirtTile that it is on
     */
    public DirtTile getDirtTile(){
        return myTile;
    }
    /**
     * sets the dirtTile that its on
     */
    public void setDirtTile(DirtTile newTile){
        myTile = newTile;
    }
}
