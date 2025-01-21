import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class DirtTile here.
 * 
 * @author : Zhaoqi Xu, Carson Cooc
 * @version: January 2024
 */

public class DirtTile extends Tile
{
    public static final int DEFAULT_GROWTH_MULTIPLIER = 1;

    private ObjectID ID = ObjectID.DIRT_TILE;
    //active/projected toggle
    private boolean active;
    private GreenfootImage projectedTile;
    private GreenfootImage activeTile;

    private Plant plant;
    private Seed seed;
    private double growthMultiplier;

    //edit mode toggle
    private boolean offseted;
    
    private GreenfootSound placingDirtSound;
    /**
     * Constructor
     * 
     * @param active state of tile
     * @param row
     * @param col
     */
    public DirtTile(LandPlot plot, int row, int col, boolean active){
        //10 is y offset;
        super(-10);
        Initialize(plot, row, col, active);
    }
    
    public void addedToWorld(World w){
        if(active){
            projectTiles();
        }
        //adjust transparancy
        fadeOval(activeTile);
    }

    public void act()
    {
        super.act();
        if(getWorld() == null){
            return;
        }
        if(active && GameWorld.getEditMode()){
            projectTiles();
        }
        else if(active && !GameWorld.getEditMode()){
            stopProjection();
        }

        fadeOval(activeTile);
    }

    /**
     * Initializes the tile
     * 
     * @param active state of tile
     */
    public void Initialize(LandPlot plot, int row, int col, boolean active){
        this.active = active;
        this.row = row;
        this.col = col;
        myPlot = plot;
        growthMultiplier = DEFAULT_GROWTH_MULTIPLIER;
        //initialize projected to be invisable
        projectedTile = new GreenfootImage("Tiles/Dirt Tile.png");
        projectedTile.setTransparency(TRANSPARENT);
        placingDirtSound = new GreenfootSound ("PlacingDirt.wav");
  
        activeTile = new GreenfootImage(projectedTile);
        if(active){
            setImage(activeTile);
        }
        else{
            setImage(projectedTile);
        }     
    }
    /**
     * sets plant to null
     */
    public void unPlant(){
        plant = null;
    }

    /**
     * used to handle all mouse inputs
     */
    public void checkMouseAction(){
        //this is for inactive tiles to become real tiles
        MouseInfo mouse = Cursor.getMouseInfo();
        if(mouse != null){
            //will set the projectedTiles transparancy 
            if (activeTile.getTransparency() >TRANSLUCENT && GameWorld.getEditMode() &&hoveringThis()){
                projectedTile.setTransparency(TRANSLUCENT + 150);
            }
            else{
                projectedTile.setTransparency(TRANSPARENT);
            }
            
            if (activeTile.getTransparency() >TRANSLUCENT && !active && (clickedThis() || (hoveringThis() && Greenfoot.mouseDragged(null))) && mouse.getButton() == 1) {

                if(Inventory.getAmount(ID) != 0){
                    activate();
                    Inventory.remove(ID);

                    playPlaceSound();
                }
                //temporary

            }

        }
        //check for plant on cursor
        Item item = Cursor.getItem();
        

        if(active && plant == null && activeTile.getTransparency() > TRANSLUCENT && hoveringThis() && Cursor.leftClicked() && item != null && item instanceof Seed){
            Seed seed = (Seed) Cursor.getItem();
            if(!seed.isDisplayed()){
                plant = seed.plant(myPlot, this);
                if(plant!= null){
                    AchievementManager.updateTotalPlant();
                }
            }
        }

        if (hoveringThis() && Greenfoot.isKeyDown("space")){
            myPlot.removeTile(row,col);
        }
    }
    /**
     * sets the plant
     * @param plant the plant to set
     */
    public void setPlant(Plant plant){
        this.plant = plant;
    }
    
    /**
     * used to handle all keypress Actions
     */
    public void checkKeypressAction(){
        //does nothing right now
        //needs to be filled in
    }

    /**
     * projects possible tiles
     */
    public void projectTiles(){
        //loops through each direction
        for(int i = 0; i < directions.length; i++){
            //dcol, drow is where the next tile is located in the plot
            int dcol = directions[i][1];
            int drow = directions[i][0];
            //dx, dy is where the next tile will be
            int dx = directionDistances[i][0];
            int dy = directionDistances[i][1];
            DirtTile neighbour = myPlot.createTile(row + drow, col + dcol);
            if(neighbour != null){
                getWorld().addObject(neighbour, getX() + dx, getY() + dy);
            }
        }
    }
    /**
     * stops all projections
     */
    public void stopProjection(){
        for(int[] direction : directions){
            //dcol = dx, drow = dy
            int newCol = col + direction[0];
            int newRow = row + direction[1];
            /**
             * NEW: changed if statment
             */
            if(newRow > 0 && newRow < LandPlot.GRID_ROWS && newCol > 0 && newCol < LandPlot.GRID_COLS){
                DirtTile neighbour = myPlot.getTile(newRow,  newCol);
                if(neighbour != null && !neighbour.isActive()){
                    myPlot.removeFromPlot(newRow, newCol);
                }
            }

        }
    }

    /**
     * checks if the mouse is hovering and there has been a click
     * @return boolena if user has clicked this tile
     */
    public boolean clickedThis(){
        return hoveringThis() && Greenfoot.mouseClicked(null);
    }

    /**
     * offsets where you can click
     */
    public void clickOffset(){
        if(activeTile.getTransparency() >TRANSLUCENT && clickedThis() && active && !offseted){
            setLocation(getX(), getY() + SELECT_OFFSET);
            offseted = true;
        }    
        else if(offseted && !hoveringThis()){
            setLocation(getX(), getY() - SELECT_OFFSET);
            offseted = false;
        }
    }

    /**
     * gets if a dirtile is affordable
     * @return boolean 
     */
    public boolean getAffordable(){
        return CurrencyHandler.isAffordable(ID);
    }

    /**
     * gets the plant of the dirt tile
     * @return Plant the plant of the dirt tile
     */
    public Plant getPlant(){
        return plant;
    }
    /**
     * play place sound
     */
    public void playPlaceSound(){
        placingDirtSound.play();
    }
    /**
     * play remove sound
     */
    public void playRemoveSound(){
        //start the remove sound here
    }

    /**
     * returns the state of the tile
     */
    public boolean isActive(){
        return active;
    }

    /**
     * activates the tile
     */
    public void activate(){
        setImage(activeTile);
        projectTiles();
        myPlot.zSort();
        active = true;
    }
    /**
     * gets the growth multiplier of the tile
     * @return double the growth multiplier
     */
    public double getGrowthMultiplier(){
        return growthMultiplier;
    }
    /**
     * gets the ID of the tile
     * @return ObjectID the tile
     */
    public ObjectID getID(){
        return ID;
    }
    /**
     * gets the row of the tile
     * @return int the row
     */
    public int getRow(){
        return row;
    }
    /**
     * gets the col of the tile
     * @return int the col
     */
    public int getCol(){
        return col;
    }
}
