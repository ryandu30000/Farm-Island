import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopItem here.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class ShopItem extends ItemFrame {

    public static final int FRAME_WIDTH = 128;
    public static final int FRAME_HEIGHT = 128;
    private int stock;
    private boolean unlimited;
    private SuperTextBox costDisplay;
    private Font font;


    /**
     * Constructs a ShopItem with a specified ObjectID and stock.
     * 
     * @param ID    The ObjectID representing the shop item.
     * @param stock The initial stock of the shop item.
     */
    public ShopItem(ObjectID ID, int stock) {
        super(ID, FRAME_WIDTH, FRAME_HEIGHT);
        this.stock = stock;
        background = new GreenfootImage("Displays/Frames/Shop Frame.png");
        GreenfootImage glow = new GreenfootImage("Displays/Frames/Frame Glow.png");
        glow.scale(background.getWidth(), background.getHeight());
        background.drawImage(glow, 0, 0);
        drawFrame();
        font = new Font("Tekton Pro", true, false,  16);
        costDisplay = new SuperTextBox("$" + String.valueOf(CurrencyHandler.getPrice(ID)), new Color(0,0,0,0), Color.BLACK, font, true, 128, 0, new Color(0,0,0,0));
    }

    /**
     * Constructs a ShopItem with a specified ObjectID and unlimited stock.
     * 
     * @param ID        The ObjectID representing the shop item.
     * @param unlimited A boolean indicating if the shop item has unlimited stock.
     */
    public ShopItem(ObjectID ID, boolean unlimited) {
        super(ID, FRAME_WIDTH, FRAME_HEIGHT);
        this.unlimited = unlimited;
        this.stock = stock;
        background = new GreenfootImage("Displays/Frames/Shop Frame.png");
        GreenfootImage glow = new GreenfootImage("Displays/Frames/Frame Glow.png");
        glow.scale(background.getWidth(), background.getHeight());
        background.drawImage(glow, 0, 0);
        drawFrame();
        font = new Font("Tekton Pro", true, false,  16);
        costDisplay = new SuperTextBox("$" + String.valueOf(CurrencyHandler.getPrice(ID)), new Color(0,0,0,0), Color.BLACK, font, true, 128, 0, new Color(0,0,0,0));
    }

    /**
     * Acts to manage fading based on the item's position in the shop.
     */
    public void act() {
        fadeMargin();
    }

    /**
     * Fades the margin of the item based on its position in the shop.
     */
    public void fadeMargin() {
        int topBound = ShopMenu.TOP_MARGIN;
        int bottomBound = GameWorld.SCREEN_HEIGHT - 150;
        
        if(getY() < topBound || getY() > bottomBound){
            int delta = Math.min(Math.abs(topBound - getY()), Math.abs(bottomBound - getY()));
            mainImage.setTransparency(Math.max(255 - delta * 3, 0));
        } else {
            mainImage.setTransparency(255);
        }
    }

    /**
     * Checks if the item is empty (stock is zero).
     * 
     * @return True if the item is empty, false otherwise.
     */
    public boolean isEmpty() {
        return getStock() == 0;
    }

    /**
     * Gets the stock of the shop item.
     * 
     * @return The current stock of the shop item.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock of the shop item.
     * 
     * @param stock The new stock value.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Removes one unit from the stock of the shop item.
     * 
     * @return True if the removal was successful, false if the stock is zero or the item is unlimited.
     */
    public boolean removeOne() {
        if (unlimited) {
            return true;
        }

        if (stock == 0) {
            return false;
        } else {
            stock--;
            if (stock == 0) {
                mainImage.fill();
            }
            return true;
        }
    }

    /**
     * Adds one unit to the stock of the shop item.
     */
    public void addOne() {
        stock++;
    }
    /**
     * updates the cost display
     */
    public void updateCostDisplay(){
        costDisplay.update("$" + String.valueOf(CurrencyHandler.getPrice(ID)));
    }
    /**
     * gets the cost display
     * @return SuperTextBox the cost display
     */
    public SuperTextBox getCostDisplay(){
        return costDisplay;
    }
}
