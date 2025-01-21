import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class to represent a seed item in the game. It is a subclass of Item. 
 * 
 * @author: Zhaoqi Xu, Carson Cooc 
 * @version: January 2024
 */
public class Seed extends Item {
    public static int totalPlantCount;
    private boolean disapearWhenEmpty;

    private GreenfootImage seedImage;
    //is this item a display item or item in play
    private boolean display;
    private int frame;
    private int actCounter;

    private GreenfootSound[] plantingSound;
    private int soundIndex;

    /**
     * Constructor for the Seed class.
     * 
     * @param ID The ObjectID of the seed.
     * @param amount The initial amount of seeds.
     * @param disapear Whether the seed should disappear when empty.
     */
    public Seed(ObjectID ID, int amount, boolean disapear) {
        disapearWhenEmpty = disapear;
        this.ID = ID;
        Inventory.add(ID, amount);

        seedImage = ID.getDisplayImage();
        double ratio = (double) seedImage.getHeight() / seedImage.getWidth();

        seedImage.scale(32, (int) (32 * ratio + 0.5));

        display = false;
        setImage(seedImage);

        plantingSound = new GreenfootSound[20];
        for (int i = 0; i < plantingSound.length; i++) {
            plantingSound[i] = new GreenfootSound("PlantingSeed.wav");
            plantingSound[i].setVolume(80);
        }
        soundIndex = 0;
    }

    /**
     * Constructor for the Seed class without specifying the amount.
     * 
     * @param ID The ObjectID of the seed.
     * @param amount The initial amount of seeds.
     */
    public Seed(ObjectID ID, int amount) {
        this(ID, amount, true);
    }

    /**
     * Constructor for the Seed class without specifying the amount and disappearance behavior.
     * 
     * @param ID The ObjectID of the seed.
     */
    public Seed(ObjectID ID) {
        this.ID = ID;
        seedImage = ID.getDisplayImage();
        double ratio = (double) seedImage.getHeight()/seedImage.getWidth();
        seedImage.scale(32,(int)(32 * ratio + 0.5));
        display = false;
        setImage(seedImage);
        plantingSound = new GreenfootSound[20];
        for (int i = 0; i < plantingSound.length; i++){
            plantingSound[i] = new GreenfootSound ("PlantingSeed.wav");
            plantingSound[i].setVolume(80);
        }
        soundIndex = 0;
    }

    /**
     * Called when the seed is added to the world.
     */
    public void addedToWorld(World w) {
        super.addedToWorld(w);
    }

    /**
     * Act method for the Seed class.
     * Checks for mouse actions and performs animation.
     */
    public void act() {
        if (getWorld() == null) {
            return;
        }
        actCounter++;
        reposition();
        checkMouseAction();
        animate();
    }

    /**
     * Creates a new plant based on the seed type.
     * 
     * @return The new plant.
     */
    public Plant newPlant() {
        Plant plant = null;
        switch (ID) {
            case WHEAT_SEED:
                plant = new Wheat();
                break;
            case PORCUS_WHEAT_SEED:
                plant = new PorcusWheat();
                break;
            case CARROT_SEED:
                plant = new Carrot();
                break;
            case TOMATO_SEED:
                plant = new Tomato();
                break;   
            case GOLDEN_TOMATO_SEED:
                plant = new GoldenTomato();
                break; 
            case SILVER_TOMATO_SEED:
                plant = new SilverTomato();
                break;  
            case DRAGONFRUIT_SEED:
                plant = new Dragonfruit();
                break;
            case STRAWBERRY_SEED:
                plant = new Strawberry();
                break;
            case BLUEBERRY_SEED:
                plant = new Blueberry();
                break;   
        }
        return plant;
    }

    /**
     * Animates the seed (to be implemented).
     */
    public void animate() {
        // Fill in later
    }

    /**
     * Checks for mouse actions related to the seed.
     */
    public void checkMouseAction() {
        GameWorld myWorld = (GameWorld) getWorld();
        if (!myWorld.isScreen(GameWorld.GAME)) {
            return;
        }
        // Fill in later
    }

    /**
     * Plants the seed and returns the planted plant.
     * 
     * @param plot The land plot where the seed is planted.
     * @param tile The dirt tile where the seed is planted.
     * @return The planted plant.
     */
    public Plant plant(LandPlot plot, DirtTile tile) {
        if (getWorld() == null) {
            return null;
        }

        Plant plant = newPlant();

        if (Inventory.getAmount(ID) > 0 || Inventory.getAmount(ID) < 0) {
            getWorld().addObject(plant, tile.getX(), tile.getY() + tile.getTileYOffset() / 2);
            plant.plant(plot, tile);
            plantingSound();
            Inventory.remove(ID);
            if (Inventory.getAmount(ID) == 0 && disapearWhenEmpty) {
                getWorld().removeObject(this);
            }
        } else {
            return null;
        }
        return plant;
    }

    /**
     * Checks if the mouse is hovering over the seed.
     * 
     * @return True if the mouse is hovering over the seed, false otherwise.
     */
    public boolean hoveringThis() {
        MouseInfo mouse = Cursor.getMouseInfo();
        if (mouse == null) {
            return false;
        }

        int leftBound = getX() - getImage().getWidth() / 2;
        int rightBound = getX() + getImage().getWidth() / 2;
        int topBound = getY() - getImage().getHeight() / 2;
        int bottomBound = getY() + getImage().getHeight() / 2;

        return mouse.getX() < rightBound && mouse.getX() > leftBound && mouse.getY() < bottomBound && mouse.getY() > topBound;
    }

    /**
     * Sets the display state of the seed.
     * 
     * @param toggle True to display, false to hide.
     */
    public void setDisplay(boolean toggle) {
        display = toggle;
    }

    /**
     * Checks if the seed is displayed.
     * 
     * @return True if the seed is displayed, false otherwise.
     */
    public boolean isDisplayed() {
        return display;
    }

    /**
     * Plays the planting sound for the seed.
     */
    public void plantingSound() {
        plantingSound[soundIndex].play();
        soundIndex++;
        if (soundIndex == plantingSound.length) {
            soundIndex = 0;
        }
    }
}
