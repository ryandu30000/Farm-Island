import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class for to represent a generic item in the game. It is a subclass of Item. 
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class GenericItem extends Item {
    public static final Color CLEAR = new Color(0, 0, 0, 0);
    private Font sFont;
    private Font lFont;
    private GreenfootImage image;
    private GreenfootImage hover;
    private SuperTextBox name;
    private SuperTextBox amount;
    private int width, height;

    private GreenfootSound hoverSound;
    private GreenfootSound[] clickSound;
    private int soundIndex;
    private boolean mouseOver;

    /**
     * Constructor for the GenericItem class.
     * @param ID The ObjectID of the item.
     */
    public GenericItem(ObjectID ID) {
        this.ID = ID;

        hoverSound = new GreenfootSound("HoverSoundmp3.mp3");
        hoverSound.setVolume(50);
        clickSound = new GreenfootSound[6];
        for (int i = 0; i < clickSound.length; i++) {
            clickSound[i] = new GreenfootSound("Clickmp3.mp3");
            clickSound[i].setVolume(90);
        }

        sFont = new Font("Tekton Pro", true, false, 16);
        lFont = new Font("Tekton Pro", true, false, 20);
        String[] nameSplit = ID.toString().split("_");
        String nameString = "";
        for (int i = 0; i < nameSplit.length; i++) {
            nameString += nameSplit[i] + " ";
        }
        name = new SuperTextBox(nameString, CLEAR, Color.BLACK, sFont, true, 128, 0, CLEAR);
        amount = new SuperTextBox(String.valueOf(Inventory.getAmount(ID)), CLEAR, Color.BLACK, lFont, true, 128, 0, CLEAR);
        image = new GreenfootImage(ID.getDisplayImage());
        hover = new GreenfootImage(image);
        double ratio = (double) image.getHeight() / image.getWidth();
        image.scale(56, (int) (56 * ratio + 0.5));
        hover.scale(60, (int) (60 * ratio + 0.5));
        width = 56;
        height = (int) (56 * ratio + 0.5);
        setImage(image);
    }

    /**
     * Called when the item is added to the world.
     */
    public void addedToWorld(World w) {

    }

    /**
     * Act method for the GenericItem class.
     * Checks for mouse actions.
     */
    public void act() {
        checkMouseAction();
    }

    /**
     * Checks for mouse actions related to the item.
     */
    public void checkMouseAction() {
        if (hoveringThis() && Greenfoot.mouseClicked(null) && Cursor.leftClicked()) {
            clickSound();
            Inventory.equipItem(ID);
            Inventory.feedItem(ID);
        }

        if (hoveringThis() != mouseOver) {
            mouseOver = !mouseOver;
            if (mouseOver) // hover begins?
            {
                hoverSound.play();
            }
        }
        World w = getWorld();

        if(hoveringThis()){

            w.addObject(name, getX(), getY() -40);
            //amount.update(String.valueOf(Inventory.getAmount(ID)));
            //w.addObject(amount, getX() + 28, getY() + 32);
            setImage(hover);
        }
        else{
            w.removeObject(amount);
            w.removeObject(name);
            setImage(image);
        }
        amount.update(String.valueOf(Inventory.getAmount(ID)));
        w.addObject(amount, getX() + 28, getY() + 32);
    }

    /**
     * Checks if the mouse is hovering over the item.
     * 
     * @return True if the mouse is hovering over the item, false otherwise.
     */
    public boolean hoveringThis() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int leftBound = getX() - width / 2;
            int rightBound = getX() + width / 2;
            int topBound = getY() - height / 2;
            int bottomBound = getY() + height / 2;
            return mouse.getX() < rightBound && mouse.getX() > leftBound && mouse.getY() < bottomBound
                    && mouse.getY() > topBound;
        }
        return false;
    }

    /**
     * Plays the click sound for the item.
     */
    public void clickSound() {
        clickSound[soundIndex].play();
        soundIndex++;
        if (soundIndex == clickSound.length){
            soundIndex = 0;
        }
    }
    /**
     * removes the amount text from world
     */
    public void removeAmountDisplay(){
        getWorld().removeObject(amount);
    }
}
