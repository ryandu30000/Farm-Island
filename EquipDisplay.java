import greenfoot.*;

/**
 * A class for displaying the equipped items 
 * 
 * @author: Angela Gao, Zhaoqi Xu 
 * @version: January 2024
 */
public class EquipDisplay extends SuperSmoothMover 
{
    public static final int SPACING = 90;
    private GreenfootImage background;
    private EquipFrame toolFrame;
    private EquipFrame seedFrame;
    private EquipFrame fertilizerFrame;
    private Tool tool;
    private Seed seed;
    private Fertilizer fertilizer;
    private GameWorld w;
    
    private EquipFrame lastSelected;
    
    private GreenfootSound[] clickSound;
    private int soundIndex;

    /**
     * Creates an EquipDisplay with default values.
     */
    public EquipDisplay(GameWorld w)
    {

        this.w = w;
        toolFrame = new EquipFrame(ObjectID.NONE, 86, 86);
        seedFrame = new EquipFrame(ObjectID.NONE, 86, 86);
        fertilizerFrame = new EquipFrame(ObjectID.NONE, 86, 86);

        clickSound = new GreenfootSound[6];
        for(int i = 0; i < clickSound.length; i++){
            clickSound[i] = new GreenfootSound("Clickmp3.mp3");
            clickSound[i].setVolume(90);
        }
    }

    /**
     * Called when the EquipDisplay is added to the world.
     * Initializes the EquipDisplay and shows the display.
     * 
     * @param w The world to which the EquipDisplay is added.
     */
    public void addedToWorld(World w){
        showDisplay();
        //fill in later
    }

    /**
     * Called every act cycle.
     * Checks for mouse actions and updates the location of EquipFrames.
     */
    public void act() {
        if(w.getScreen() == GameWorld.GAME && !w.getEditMode()){
            checkMouseAction();
            checkSelection();
        }

        toolFrame.setLocation(getX() - SPACING, getY());
        seedFrame.setLocation(getX(), getY());
        fertilizerFrame.setLocation(getX() + SPACING, getY()); 
    }
    /**
     * deselects all of the equip frames
     */
    public void deselectAll(){
        seedFrame.unselect();
        fertilizerFrame.unselect();
        toolFrame.unselect();
        checkSelection();
    }
    /**
     * selects the previously used item
     */
    public void selectPrevious(){
        if(lastSelected != null){
            lastSelected.select();
        }
        
    }
    /**
     * updates the screen based on item selection
     */
    public void checkSelection(){
        if(toolFrame.isSelected()){
            lastSelected = toolFrame;
            seedFrame.unselect();
            fertilizerFrame.unselect();
            if(tool != null && tool.getWorld() == null){
                w.addObject(tool, Cursor.getX(), Cursor.getY());
            }
        }
        else{
            if(tool != null && tool.getWorld() != null){
                w.removeObject(tool);
            }
        }
        if(seedFrame.isSelected()){
            lastSelected = seedFrame;
            fertilizerFrame.unselect();
            toolFrame.unselect();
            if(seed != null && seed.getWorld() == null){
                w.addObject(seed, Cursor.getX(), Cursor.getY());
            }
        }
        else{
            if(seed != null && seed.getWorld() != null){
                w.removeObject(seed);
            }
        }
        if(fertilizerFrame.isSelected()){
            lastSelected = fertilizerFrame;
            toolFrame.unselect();
            seedFrame.unselect();
            if(fertilizer != null && fertilizer.getWorld() == null){
                w.addObject(fertilizer, Cursor.getX(), Cursor.getY());
            }
        }
        else{
            if(fertilizer != null && fertilizer.getWorld() != null){
                w.removeObject(fertilizer);
            }
        }
    }
    /**
     * checks for mouse actions and perform tasks accordingly
     */
    public void checkMouseAction(){
        if(toolFrame.hoveringThis() && Greenfoot.mousePressed(null) && Cursor.leftClicked()){
            clickSound();
            if(clickedItself(toolFrame)){
                toolFrame.unselect();
            }
            else{
                toolFrame.select();
                Cursor.pickUp(tool);
                seedFrame.unselect();
                fertilizerFrame.unselect();
            }

        }
        if(seedFrame.hoveringThis() && Greenfoot.mousePressed(null) && Cursor.leftClicked()){
            clickSound();
            if(clickedItself(seedFrame)){
                seedFrame.unselect();
            }
            else{
                Cursor.pickUp(seed);
                seedFrame.select();
                toolFrame.unselect();
                fertilizerFrame.unselect();
            }

        }
        if(fertilizerFrame.hoveringThis() && Greenfoot.mousePressed(null) && Cursor.leftClicked()){
            clickSound();
            if(clickedItself(fertilizerFrame)){
                fertilizerFrame.unselect();
            }
            else{
                Cursor.release();
                fertilizerFrame.select();
                seedFrame.unselect();
                toolFrame.unselect();                
            }

        }
    }
    /**
     * checks if user clicks on the same frame
     * 
     * @param frame the frame that is clicked
     * @return boolean has the user clicked the same frame
     */
    public boolean clickedItself(EquipFrame frame){
        if(frame.isSelected()){
            return true;
        }
        return false;
    }
    /**
     * Displays the EquipFrames in the world.
     */
    public void showDisplay(){
        w.addObject(toolFrame,getX() - SPACING, getY());
        w.addObject(seedFrame, getX(), getY());
        w.addObject(fertilizerFrame, getX() + SPACING, getY());
    }

    /**
     * Hides the EquipFrames from the world.
     * Also removes any equipped items.
     */
    public void hideDisplay() {
        w.removeObject(toolFrame);
        w.removeObject(seedFrame);
        w.removeObject(fertilizerFrame);
        w.removeObject(tool);
        w.removeObject(seed);
    }

    /**
     * Equips a tool to the EquipDisplay.
     * 
     * @param tool The tool to be equipped.
     */
    public void equipTool(Tool tool) {
        if (this.tool != null && this.tool.getWorld() != null) {
            w.removeObject(this.tool);
        }
        Cursor.pickUp(tool);
        this.tool = tool;
        toolFrame.updateID(tool.getID());
        toolFrame.select();
    }

    /**
     * Equips a seed to the EquipDisplay.
     * 
     * @param seed The seed to be equipped.
     */
    public void equipSeed(Seed seed) {
        if (this.seed != null && this.seed.getWorld() != null) {
           w.removeObject(this.seed);
        }
        this.seed = seed;
        Cursor.pickUp(seed);
        seedFrame.updateID(seed.getID());
        seedFrame.select();
    }

    /**
     * Equips a fertilizer to the EquipDisplay.
     * 
     * @param fertilizer The fertilizer to be equipped.
     */
    public void equipFertilizer(Fertilizer fertilizer) {
        if (this.fertilizer != null && this.fertilizer.getWorld() != null) {
            w.removeObject(this.fertilizer);
        }
        Cursor.pickUp(fertilizer);
        this.fertilizer = fertilizer;

        fertilizerFrame.updateID(fertilizer.getID());
        fertilizerFrame.select();                                                             
    }

    /**
     * Unequips the currently equipped tool.
     */
    public void unEquipTool() {
        if (this.tool != null && this.tool.getWorld() != null) {
            getWorld().removeObject(this.tool);
        }

        this.tool = null;
        toolFrame.updateID(ObjectID.NONE);
        toolFrame.unselect();
    }

    /**
     * Unequips the currently equipped seed.
     */
    public void unEquipSeed() {
        if (this.seed != null && this.seed.getWorld() != null) {
            w.removeObject(this.seed);
        }
        this.seed = null;
        seedFrame.updateID(ObjectID.NONE);
        seedFrame.unselect();
    }

    /**
     * Unequips the currently equipped fertilizer.
     */
    public void unEquipFertilizer() {
        if (this.fertilizer != null && this.fertilizer.getWorld() != null) {
            w.removeObject(this.fertilizer);
        }

        this.fertilizer = null;
        fertilizerFrame.updateID(ObjectID.NONE);
        fertilizerFrame.unselect();
    }

    /**
     * Unequips an item based on its ObjectID.
     * 
     * @param ID The ObjectID of the item to be unequipped.
     */
    public void unEquipItem(ObjectID ID) {
        switch (ID) {
            case PORCUS_WHEAT_SEED:
            case WHEAT_SEED:
            case CARROT_SEED:
            case TOMATO_SEED:
            case SILVER_TOMATO_SEED:
            case GOLDEN_TOMATO_SEED:
            case STRAWBERRY_SEED:
            case BLUEBERRY_SEED:
            case DRAGONFRUIT_SEED:
                unEquipSeed();
                break;
            case DIAMOND_TOOL: 
            case BASIC_TOOL:
            case SHOVEL:    
                unEquipTool();
                break;
            case FERTILIZER:
                unEquipFertilizer();
                break;    
        }
    }

    /**
     * Equips an item based on its ObjectID.
     * 
     * @param ID The ObjectID of the item to be equipped.
     */
    public void equipItem(ObjectID ID) {
        switch (ID) {
            case PORCUS_WHEAT_SEED:
            case WHEAT_SEED:
            case CARROT_SEED:
            case TOMATO_SEED:
            case BLUEBERRY_SEED:
            case STRAWBERRY_SEED:
            case DRAGONFRUIT_SEED:
            case SILVER_TOMATO_SEED: 
            case GOLDEN_TOMATO_SEED:
                equipSeed(new Seed(ID));
                break;
            case DIAMOND_TOOL: 
            case BASIC_TOOL:
            case SHOVEL:
                equipTool(new Tool(ID));
                break;
            case FERTILIZER:
                equipFertilizer(new Fertilizer(ID));
                break;    
        }
    }

    /**
     * Plays a click sound.
     * Cycles through different click sounds.
     */
    public void clickSound() {
        clickSound[soundIndex].play();
        soundIndex++;
        if (soundIndex == clickSound.length) {
            soundIndex = 0;
        }
    }
}
