import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.HashMap;
import java.util.ArrayList;
/**
 * <a href="https://www.textstudio.com/">Font generator</a>
 * 
 * The world that all game objects reside
 * 
 * @author: Zhaoqi Xu, Ashkan Siassi, Carson Cooc 
 * @version: January 2024
 */

public class GameWorld extends World
{
    //720p resolution 16:9 aspect ratio
    private static boolean editMode;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int ACTS_TO_HOME = 30;
    public static final String GAME = "Game";
    public static final String SHOP = "Shop";
    public static final String ACHIEVEMENT = "Achievement";

    private int previousY;

    private LandPlot landPlot;
    private EquipDisplay equip;
    private Button homeButton;
    private boolean shouldMove;
    private double exactDistancePerFrame;
    private int i;
    private double distance;
    //used to indicate which screen is currently in priority/active
    private String screen;
    private ShopMenu shop;
    private Button openShop;
    private AchievementMenu achievement;
    private Button openAchievement;
    private Button leave;
    private ToggleButton toggle;
    
    private AchievementManager achievementManager;

    private Button openInventory;
    private InventoryDisplay inventoryDisplay;
    private Button openPorcus;
    private PorcusMenu porcus;
    
    private SuperTextBox buildOn;
    private SuperTextBox buildOff;
    
    private SimpleTimer actTimer;
    private SimpleTimer cloudTimer;
    
    private GreenfootSound GamePlayMusic;
    private GreenfootSound ShopMusic;
    
    //for keypress only
    private boolean inventoryMoving;
    
    /**
     * Constructor for objects of class GameWorld.
     * @param savedFile The file path or name of the saved data to initialize the world. If null or empty, the world will be initialized with default settings.
     */
    public GameWorld(String savedFile)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(SCREEN_WIDTH, SCREEN_HEIGHT, 1, false); 
        initialize(savedFile);
    }

    /**
     * Performs the main game logic for the GameWorld.
     * Checks for user input, mouse actions, and key actions when the game screen is set to GAME.
     * Additionally, triggers periodic actions such as updating home islands and spawning clouds based on timers.
     */
    public void act(){
        if(screen.equals(GAME)){
            checkMouseAction();
            checkKeyAction();
            if(actTimer.millisElapsed() >= 17){
                actTimer.mark();
                homeIslands();
                spawnClouds();
            }
        }
    }

    /**
     * Initializes the GameWorld with various components, settings, and objects based on the provided saved file.
     * This method sets up the game environment, including the background, music, buttons, inventory, shop menu,
     * land plot, clouds, and other game elements.
     *
     * @param savedFile The file path or name of the saved data to initialize the game world. If null or empty, default settings will be used for initialization.
     */
    public void initialize(String savedFile){
        
        editMode = false;
        
        actTimer = new SimpleTimer();
        cloudTimer = new SimpleTimer();
        
        GamePlayMusic = new GreenfootSound ("GamePlayMusic.mp3");
        GamePlayMusic.setVolume(50);
        ShopMusic = new GreenfootSound ("ShopMusic.mp3");
        ShopMusic.setVolume(50);
        
        CurrencyHandler.initialize(savedFile, this);
        CollectionHandler.initialize(this);
        
        setBackground(new GreenfootImage("BackGrounds/Game BG.png"));
        //initializes starting screen

        screen = GAME;
        setPaintOrder(SuperTextBox.class, AchievementNotification.class,ForegroundEffect.class, CurrencyHandler.class, Item.class, Button.class, ItemFrame.class, AchievementBanner.class, ShopMenu.class, AchievementMenu.class, PorcusMenu.class, InventoryDisplay.class, Fertilizer.class, Plant.class, DirtTile.class, LandPlot.class);
        
        landPlot = new LandPlot(savedFile);
        addObject(landPlot, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
        
        // Initializes shop menu
        HashMap<ObjectID, Integer> temp = new HashMap<>();
        temp.put(ObjectID.DIRT_TILE, -1);
        //temp.put(ObjectID.FERTILIZER, -1);
        
        temp.put(ObjectID.WHEAT_SEED, -1);
        temp.put(ObjectID.CARROT_SEED, -1);
        temp.put(ObjectID.TOMATO_SEED, -1);

        shop = new ShopMenu(temp);

        // Initializes buttons
        openShop = new GameButton("Shop");
        homeButton = new GameButton("Home");
        openAchievement = new GameButton("Achievement");
        openInventory = new GameButton("Inventory");
        leave = new GameButton("Leave");
        openPorcus = new MenuButton("Porcus H");
        porcus = new PorcusMenu(savedFile, openPorcus, shop);
        addObject(porcus, 0, 0);
        
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(homeButton);
        buttons.add(openShop);
        buttons.add(openInventory);
        buttons.add(openAchievement);
        buttons.add(leave);
        inventoryDisplay = new InventoryDisplay(buttons);
        addObject(inventoryDisplay, SCREEN_WIDTH, SCREEN_HEIGHT/2);
        
        Font font = new Font("Tekton Pro", true, false,  20);
        buildOn = new SuperTextBox("BUILD MODE ON", new Color(0,0,0,0), Color.BLACK, font, true, 200, 0, null);
        buildOff = new SuperTextBox("BUILD MODE OFF", new Color(0,0,0,0), Color.BLACK, font, true, 200, 0, null);
        
        equip = new EquipDisplay(this);
        // Set up the inventory from the previous save
        Inventory.initialize(savedFile, inventoryDisplay, equip, porcus);
        CollectionHandler.initialize(this);
        Inventory.add(ObjectID.SHOVEL, 1);
        Inventory.add(ObjectID.DIAMOND_TOOL, 1);
        
        // Initializes achievement menu
        achievement = new AchievementMenu();
        achievementManager = new AchievementManager();
        
        
        Tool tool = new Tool(ObjectID.DIAMOND_TOOL);
        equip.equipSeed(new Seed(ObjectID.WHEAT_SEED, 1, false));
        equip.equipTool(tool);
        addObject(equip, SCREEN_WIDTH/2, SCREEN_HEIGHT - 64);
        
        toggle = new ToggleButton("Toggle");
        addObject(toggle, equip.getX(), equip.getY() - 64);
        
        addObject(new CurrencyHandler(), 100, 100);
        //addObject(new Seed(ObjectID.WHEAT_SEED, 1, false), 1200, 650);
        //addObject(new Seed(ObjectID.STUBBY_WHEAT_SEED, 0, false), 1100, 650);

        fillClouds();
        GamePlayMusic.playLoop();
        if(savedFile!= null){
            GameInfo.loadAchievements(savedFile);
            //GameInfo.loadPorcus(savedFile, this);
        }
    }
    
    /**
     * Fills the background with cloud objects, this method adjusts the sliding speeds and images that are shown
     */
    public void fillClouds(){
        for(int i = 0; i < 10; i++){
            int cloudNum = Greenfoot.getRandomNumber(6) + 1;
            int startY = (Greenfoot.getRandomNumber(12) + 1) * SCREEN_HEIGHT/12;
            int startX = (Greenfoot.getRandomNumber(12) + 1) * SCREEN_WIDTH/12;
            while(startY < previousY + 32 && startY > previousY - 32){
                startY = (Greenfoot.getRandomNumber(12) + 1) * SCREEN_HEIGHT/12;
            }
            GreenfootImage cloud = new GreenfootImage("BackGrounds/Cloud " + cloudNum + ".png");
            addObject(new Effect(Effect.SLIDE,cloud, SCREEN_WIDTH + cloud.getWidth() - startX, 1.0/(Greenfoot.getRandomNumber(4) + 1.0)), startX, startY);
        }
    }
    
    /**
     * Spawns cloud objects at regular intervals, controlled by the cloudTimer.
     * Clouds are randomly generated with a variety of cloud images, starting positions, and sliding speeds.
     * The method ensures that the newly spawned clouds do not overlap with the previous set of clouds,
     * providing a visually appealing and diverse cloud arrangement.
     * Clouds are spawned off-screen and slide towards the visible game area.
     */
    public void spawnClouds(){

        if(cloudTimer.millisElapsed() > 4000){
            cloudTimer.mark();
            int cloudNum = Greenfoot.getRandomNumber(6) + 1;
            int startY = (Greenfoot.getRandomNumber(12) + 1) * SCREEN_HEIGHT/12;
            while(startY < previousY + 16 && startY > previousY - 16){
                startY = (Greenfoot.getRandomNumber(12) + 1) * SCREEN_HEIGHT/12;
            }
            GreenfootImage cloud = new GreenfootImage("BackGrounds/Cloud " + cloudNum + ".png");
            addObject(new Effect(Effect.SLIDE,cloud, SCREEN_WIDTH + cloud.getWidth() * 2, 1.0/(Greenfoot.getRandomNumber(4) + 1.0)), -cloud.getWidth(), startY);
        }

    }

    /**
     * Moves the home islands towards the center of the screen.
     * Calculates the distance between the center of the screen and the central tile of the land plot.
     * Gradually moves all dirt tiles towards the center in a synchronized manner to create a cohesive effect.
     * The movement occurs over a specified number of acts to home (ACTS_TO_HOME).
     * The dirt tiles rotate to align with the movement direction for a visually consistent animation.
     */
    public void homeIslands(){
        DirtTile centreTile = landPlot.getTile(LandPlot.STARTING_ROW, LandPlot.STARTING_COL);
        int centreX = SCREEN_WIDTH/2;
        int centreY = SCREEN_HEIGHT/2;
        int centreTileX = centreTile.getX();
        int centreTileY = centreTile.getY();
        double deltaX = centreX - centreTileX;
        double deltaY = centreY - centreTileY;
        distance = Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        if(i == ACTS_TO_HOME){
            shouldMove = false;
            i = 0;
        }

        if(shouldMove)
        {
            if(i == 0){
                exactDistancePerFrame = distance/ACTS_TO_HOME;

                centreTile.enableStaticRotation();
                centreTile.turnTowards(centreX, centreY);
                double rotation = centreTile.getPreciseRotation();

                for (Tile object : getObjects(DirtTile.class)) {
                    object.enableStaticRotation();
                    object.setRotation(rotation);
                }

            }
            for (Tile object : getObjects(DirtTile.class)) {
                object.move(exactDistancePerFrame);
            }

            i++;
        }
    }
    
    /**
     * Checks for key actions and responds accordingly.
     * - Pressing the 'b' key switches the screen to the SHOP mode.
     * - Pressing the 'i' or 'e' key toggles the visibility of the inventory display.
     *   The inventory display can only be toggled if it is not currently in a moving state.
     */
    public void checkKeyAction(){
        
        if(Greenfoot.isKeyDown("b")){
            setScreen(SHOP);
        }
        if(!inventoryDisplay.isMoving() && (Greenfoot.isKeyDown("i") || Greenfoot.isKeyDown("e"))){
            if(inventoryDisplay.isOpen()){
                inventoryDisplay.close();
            }
            else{
                inventoryDisplay.open();
            }
        }
        
    }
    
    /**
     * Checks for mouse actions on specific buttons and responds accordingly.
     * - Clicking the "Shop" button switches the screen to the SHOP mode.
     * - Clicking the "Achievement" button switches the screen to the ACHIEVEMENT mode.
     * - Clicking the "Home" button initiates movement of home islands towards the center of the screen.
     * - Clicking the "Inventory" button toggles the visibility of the inventory display.
     * - Clicking the "Leave" button saves the game.
     * - Clicking the "Porcus H" button toggles the visibility of the Porcus menu.
     * 
     * The actions are performed based on the left mouse click event and may depend on the current state of
     * associated elements, such as the home islands' movement state and the visibility of menus.
     */
    public void checkMouseAction(){
        if(openShop.leftClickedThis()){
            setScreen(SHOP);
        }
        if(openAchievement.leftClickedThis()){
            setScreen(ACHIEVEMENT);
        }
        if(homeButton.leftClickedThis() && !shouldMove && distance  != 0){
            shouldMove = true;
        }
        if(openInventory.leftClickedThis()){
            if(inventoryDisplay.isOpen()){
                inventoryDisplay.close();
            }
            else{
                inventoryDisplay.open();
            }
        }    
        if(leave.leftClickedThis()){
            GameInfo.saveGame(this);
        }
        if(toggle.leftClickedThis()){
            setEditMode(toggle.getToggle());
            if(editMode){
                buildOn.getImage().setTransparency(255);
                addObject(new ForegroundEffect(Effect.FADE, buildOn.getImage(), 20, 0.5), toggle.getX(), toggle.getY() - 32);
                equip.deselectAll(); 
            }
            else{
                buildOff.getImage().setTransparency(255);
                addObject(new ForegroundEffect(Effect.FADE, buildOff.getImage(), 20, 0.5), toggle.getX(), toggle.getY() -32);
                equip.selectPrevious();
            }
        }
        if(openPorcus.leftClickedThis()){
            if(porcus.isOpen()){
                porcus.close();
            }
            else{
                porcus.open();
            }
        }
    }

    /**
     * Removes buttons from the inventory display forcibly, ensuring its closure.
     */
    public void removeButtons() {
        removeObject(toggle);
        inventoryDisplay.forceClose();
        porcus.forceClose();
    }
    
    /**
     * Resets buttons in the inventory display by adding them back.
     */
    public void resetButtons() {
        addObject(toggle, equip.getX(), equip.getY() - 64);
        inventoryDisplay.addButtons();
        porcus.reset();
    }

    /**
     * Retrieves the current edit mode status.
     *
     * @return True if the edit mode is enabled, false otherwise.
     */
    public static boolean getEditMode() {
        return editMode;
    }

    /**
     * Sets the edit mode status.
     *
     * @param mode The new edit mode status to be set.
     */
    public static void setEditMode(boolean mode) {
        editMode = mode;
    }

    /**
     * Toggles the edit mode status between true and false.
     */
    public static void toggleEditMode() {
        editMode = !editMode;
    }
    
    /**
     * Checks if the current screen matches the specified screen name.
     *
     * @param screen The screen name to compare with the current screen.
     * @return True if the current screen matches the specified screen name, false otherwise.
     */
    public boolean isScreen(String screen) {
        return this.screen.equals(screen);
    }

    /**
     * Retrieves the name of the current screen.
     *
     * @return The name of the current screen.
     */
    public String getScreen() {
        return screen;
    }


    /**
     * Sets the current screen and performs associated actions based on the screen type.
     * - For GAME screen: Resets buttons, shows equipment display, plays game background music,
     *   and stops shop background music.
     * - For SHOP screen: Removes buttons, hides equipment display, adds the shop menu to the world,
     *   stops game background music, and plays shop background music.
     * - For ACHIEVEMENT screen: Removes buttons and adds the achievement menu to the world.
     *
     * @param screen The screen type to set (GAME, SHOP, ACHIEVEMENT).
     */
    public void setScreen(String screen) {
        this.screen = screen;
        switch(screen){
            case GAME:
                resetButtons();
                equip.showDisplay();
                GamePlayMusic.playLoop();
                ShopMusic.stop();
                break;
            case SHOP:
                removeButtons();     
                equip.hideDisplay();
                addObject(shop, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
                GamePlayMusic.stop();
                ShopMusic.playLoop();
                break;
            case ACHIEVEMENT:
                removeButtons();
                equip.hideDisplay();
                addObject(achievement, SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
                break;
        }
    }
    public LandPlot getLandPlot(){
        return landPlot;
    }    
    /**
     * Callback method called when the scenario has started.
     * Adjusts music playback based on the current screen type.
     */
    public void started() {
        switch (screen) {
            case GAME:
                GamePlayMusic.playLoop();
                break;
            case SHOP:
                ShopMusic.playLoop();
                break;
        }
    }
    
    /**
     * Callback method called when the scenario has stopped.
     * Pauses game background music and shop background music.
     */
    public void stopped() {
        GamePlayMusic.pause();
        ShopMusic.pause();
    }
    
    public PorcusMenu getPorcusMenu(){
        return porcus;
    }
}
