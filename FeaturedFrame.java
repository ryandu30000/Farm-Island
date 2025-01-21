import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class representing a frame for displaying a featured item in the shop.
 * This class extends ItemFrame and adds functionality to display additional information about the featured item.
 *  
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class FeaturedFrame extends ItemFrame {

    public static final int FRAME_WIDTH = 288;
    public static final int FRAME_HEIGHT = 450;
    private ShopItem item;
    private int cost;
    private MenuButton purchase;
    private Font font;

    /**
     * Constructs a FeaturedFrame with a specified purchase button.
     * 
     * @param purchase The MenuButton representing the purchase button for the featured item.
     */
    public FeaturedFrame(MenuButton purchase) {
        super(ObjectID.NONE, FRAME_WIDTH, FRAME_HEIGHT);
        cost = CurrencyHandler.getPrice(ID);
        this.purchase = purchase;
        font = new Font("Tekton Pro", true, false, 30);
        SuperTextBox box = new SuperTextBox(" ", new Color(0, 0, 0, 15), Color.BLACK, font, true, FRAME_WIDTH, 0, null);
        background = new GreenfootImage(FRAME_WIDTH, FRAME_HEIGHT);
        background.drawImage(box.getImage(), 0, 70);
        drawFrame();
    }

    /**
     * Updates the display of the featured item with additional information.
     * 
     * @param item The ShopItem representing the featured item.
     */
    public void updateDisplay(ShopItem item) {
        ID = item.getID();
        String[] nameSplit = ID.toString().split("_");
        String nameString = "";
        for (int i = 0; i < nameSplit.length; i++) {
            nameString += nameSplit[i] + " ";
        }
        SuperTextBox box = new SuperTextBox(nameString, new Color(0, 0, 0, 15), Color.BLACK, font, true, FRAME_WIDTH, 0, null);
        background.clear();
        background.drawImage(box.getImage(), 0, 70);
        this.item = item;
        cost = CurrencyHandler.getPrice(ID);
        purchase.drawText("$" + cost, 0, -8);
        updateID(ID);
    }

    /**
     * Updates the displayed price based on the specified amount.
     * 
     * @param amount The amount used to calculate the updated price.
     */
    public void updatePrice(int amount) {
        cost = CurrencyHandler.getPrice(ID, amount);
        purchase.drawText("$" + cost, 0, -8);
    }

    /**
     * Updates the displayed item in the frame and adjusts its scale.
     * 
     * @param ID The new ObjectID representing the featured item.
     */
    public void updateID(ObjectID ID) {
        this.ID = ID;
        foreground = ID.getDisplayImage();
        if (foreground.getWidth() < 128) {
            double ratio = (double) foreground.getHeight() / foreground.getWidth();
            foreground.scale(86, (int) (86 * ratio + 0.5));
        }
        drawFrame();
    }

    /**
     * Draws the frame with the updated information.
     */
    public void drawFrame() {
        mainImage.clear();
        mainImage.drawImage(background, 0, 0);
        int marginY = (background.getHeight() - foreground.getHeight()) / 2;
        int marginX = (background.getWidth() - foreground.getWidth()) / 2;
        if (marginY < 0 || marginX < 0) {
            double ratio = (double) foreground.getHeight() / foreground.getWidth();
            foreground.scale(background.getWidth(), (int) (background.getWidth() * ratio + 0.5));
            marginY = (background.getHeight() - foreground.getHeight()) / 2;
            marginX = (background.getWidth() - foreground.getWidth()) / 2;
        }
        mainImage.drawImage(foreground, marginX, marginY - 35);
        setImage(mainImage);
    }

    /**
     * Resets the frame to display default information.
     */
    public void reset() {
        SuperTextBox box = new SuperTextBox("CHOOSE ITEM", new Color(0, 0, 0, 15), Color.BLACK, font, true, FRAME_WIDTH, 0, null);
        background.clear();
        background.drawImage(box.getImage(), 0, 70);
        purchase.drawText(" ", 0, -8);
        updateID(ObjectID.NONE);
    }

    /**
     * Gets the ShopItem associated with the featured frame.
     * 
     * @return The ShopItem associated with the featured frame.
     */
    public ShopItem getItem() {
        return item;
    }
}