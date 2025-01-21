import java.util.HashMap;
import greenfoot.*;
/**
 * The CurrencyHandler class manages the in-game currency, purchases, and pricing of items.
 * It handles the player's money balance, the price index for different items, and provides
 * functionality for making purchases, checking affordability, adjusting prices, and more.
 * 
 * This class also initializes the currency display in the game world and updates it during the game.
 * 
 * @author: Angela Gao, Zhaoqi Xu
 * @version: January 2024
 */
public class CurrencyHandler extends SuperSmoothMover 
{
    private static int money;

    private static HashMap<ObjectID, Integer> priceIndex;

    private static SuperTextBox CashDisplay;
    
    private static GameWorld myWorld;
    
    private static GreenfootImage background;
    private static GreenfootImage myImage;
    
    private static int x, y;
    
    /**
     * Constructs a CurrencyHandler instance.
     */
    public CurrencyHandler(){
        //Empty Constructor 
    }
    
    /**
     * Called when the CurrencyHandler is added to the world.
     * Initializes the x and y coordinates for the currency display.
     *
     * @param w The world to which the CurrencyHandler is added.
     */
    public void addedToWorld(World w){
        x = getX();
        y = getY();
    }
    
    /**
     * Called on each frame update.
     * Updates the currency display in the game world.
     */
    public void act(){
        myImage.clear();
        myImage.drawImage(background, 0, 0);
        myImage.drawString("$" + money, 14, myImage.getHeight()/2);
        setImage(myImage);

    }

    /**
     * Initializes the CurrencyHandler with the specified saved file and GameWorld.
     * Loads the initial money balance, price index, and sets up the currency display.
     *
     * @param savedFile The saved file to load information from.
     * @param w         The GameWorld instance associated with the CurrencyHandler.
     */
    public static void initialize(String savedFile, GameWorld w){
        myWorld = w;
        background = new GreenfootImage("BackGrounds/Money.png");
        myImage = new GreenfootImage(background.getWidth(), background.getHeight());
        myImage.setFont(new Font("Tekton Pro", true, false,  20));
        priceIndex = new HashMap<>();
        priceIndex.put(ObjectID.DIRT_TILE, 10);

        priceIndex.put(ObjectID.PORCUS_WHEAT_SEED, 888);
        priceIndex.put(ObjectID.PORCUS_WHEAT, 100);
        priceIndex.put(ObjectID.WHEAT_SEED, 1);
        priceIndex.put(ObjectID.WHEAT, 1);
        priceIndex.put(ObjectID.CARROT_SEED, 2);
        priceIndex.put(ObjectID.CARROT, 2);
        priceIndex.put(ObjectID.TOMATO_SEED, 10);
        priceIndex.put(ObjectID.TOMATO, 5);
        priceIndex.put(ObjectID.BLUEBERRY_SEED, 15);
        priceIndex.put(ObjectID.BLUEBERRY, 7);
        priceIndex.put(ObjectID.STRAWBERRY_SEED, 30);
        priceIndex.put(ObjectID.STRAWBERRY, 15);
        priceIndex.put(ObjectID.SILVER_TOMATO_SEED, 40);
        priceIndex.put(ObjectID.SILVER_TOMATO, 20);
        priceIndex.put(ObjectID.GOLDEN_TOMATO_SEED, 50);
        priceIndex.put(ObjectID.GOLDEN_TOMATO, 25);
        priceIndex.put(ObjectID.DRAGONFRUIT_SEED, 75);
        priceIndex.put(ObjectID.DRAGONFRUIT, 38);

        priceIndex.put(ObjectID.DIAMOND_TOOL, 1000);
        priceIndex.put(ObjectID.BASIC_TOOL, 10);
        priceIndex.put(ObjectID.FERTILIZER, 5);
        resetBalance();
        if(savedFile != null){
            GameInfo.loadMoney(savedFile);
        }
    }

    /**
     * Handles the purchase of items with the specified ID and amount.
     * Deducts the cost from the money balance and adjusts the price index.
     *
     * @param ID     The ObjectID of the item to be purchased.
     * @param amount The quantity of the item to be purchased.
     */
    public static void purchase(ObjectID ID, int amount){
        if(isAffordable(ID, amount)){
            int total = 0;
            if(ID == ObjectID.DIRT_TILE){
                for(int i = 0; i < amount; i++){
                    total += (AchievementManager.totalTiles + i) * 5;
                }
                AchievementManager.totalTiles += amount;
            }
            else{
                total += priceIndex.get(ID) * amount;
            }
            money -= total;
            adjustPrice(ID);
            Inventory.add(ID, amount);
        }
    }

    /**
     * Checks if the player can afford to purchase an item with the specified ID.
     *
     * @param ID The ObjectID of the item to be checked.
     * @return True if the player can afford the item, false otherwise.
     */
    public static boolean isAffordable(ObjectID ID){
        return isAffordable(ID, 1);
    }

    /**
     * Checks if the player can afford to purchase a specific quantity of an item with the specified ID.
     *
     * @param ID     The ObjectID of the item to be checked.
     * @param amount The quantity of the item to be checked.
     * @return True if the player can afford the specified quantity of the item, false otherwise.
     */
    public static boolean isAffordable(ObjectID ID, int amount){
        int total = 0;
        if(ID == ObjectID.DIRT_TILE){
            for(int i = 0; i < amount; i++){
                total += (AchievementManager.totalTiles + i) * 5;
            }
        }
        else{
            total += priceIndex.get(ID) * amount;
        }
        return money >= total;
    }

    /**
     * Adjusts the price of an item based on certain conditions.
     *
     * @param ID The ObjectID of the item for which the price is adjusted.
     */
    public static void adjustPrice(ObjectID ID){

    }

    /**
     * Gets the price of an item with the specified ID.
     *
     * @param ID The ObjectID of the item.
     * @return The price of the item.
     */
    public static int getPrice(ObjectID ID){
        return getPrice(ID, 1);
    }

    /**
     * Gets the total price for a specific quantity of an item with the specified ID.
     *
     * @param ID     The ObjectID of the item.
     * @param amount The quantity of the item.
     * @return The total price for the specified quantity of the item.
     */
    public static int getPrice(ObjectID ID, int amount){

        if(priceIndex.containsKey(ID)){
            int total = 0;
            if(ID == ObjectID.DIRT_TILE){
                for(int i = 0; i < amount; i++){
                    total += (AchievementManager.totalTiles + i) * 5;
                }
            }
            else{
                total += priceIndex.get(ID) * amount;
            }

            return total;
        }
        else{
            return -1;
        }
    }

    /**
     * Gets the current money balance.
     *
     * @return The current money balance.
     */
    public static int getBallance(){
        return money;
    }

    /**
     * Deposits a specified amount of money to the player's balance.
     * Updates the currency display with a floating effect.
     *
     * @param amount The amount of money to deposit.
     */
    public static void deposit(int amount){
        GreenfootImage effect = new GreenfootImage(myImage.getWidth(), 20);
        effect.setColor(new Color(123,168,124));
        effect.setFont(new Font("Tekton Pro", true, false,  20));
        effect.drawString("$" + amount,0, 20);
        myWorld.addObject(new ForegroundEffect(Effect.FLOAT, effect, 20, 0.5), x + 14, y - 10);
        money += amount;
    }

    /**
     * Withdraws a specified amount of money from the player's balance.
     *
     * @param amount The amount of money to withdraw.
     */
    public static void withdraw (int amount){
        money -= amount;
    }

    /**
     * Resets the player's money balance to zero.
     */
    private static void resetBalance(){
        money = 0;
    }
}
