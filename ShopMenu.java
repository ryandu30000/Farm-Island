import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.SwingUtilities;
import greenfoot.core.WorldHandler;

/**
 * Write a description of class ShopMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopMenu extends SuperSmoothMover 
{
    public static final int ROW_MAX = 4;
    public static final int SPACING = ShopItem.FRAME_WIDTH + 32;
    public static final int TOP_MARGIN = 256;
    public static final int LEFT_MARGIN = 200;
    private static final int UP = 1;
    private static final int DOWN = -1;

    private static final double ITEM_SPEED = 16;
    private boolean moving;
    private double distanceTraveled;
    private int pageNumber;
    private int direction;

    private ArrayList<ShopItem> itemGallery;
    private ArrayList<SuperTextBox> itemCosts;
    private FeaturedFrame featuredItem;
    private GreenfootImage background;
    private Button returnButton;
    private Button menuUp;
    private Button menuDown;
    private MenuButton purchase;
    private MenuButton plus;
    private MenuButton minus;
    private int purchaseAmount;
    private SuperTextBox amountDisplay;

    private boolean firstOpened;
    private boolean dragging;

    private GameWorld myWorld;

    private SimpleTimer actTimer;
    private SimpleTimer objectTimer;
    /**
     * basic constructor for shop
     * @param galleryIDs an arraylist of Ids to add to the shop
     */
    public ShopMenu(ArrayList<ObjectID> galleryIDs){
        initialize(galleryIDs);
    }

    /**
     * basic constructor for shop
     * @param galleryIDs a hashmap of ObjectID and amount of that object
     */
    public ShopMenu(HashMap<ObjectID, Integer> galleryIDs){
        initialize(galleryIDs);
    }

    public void addedToWorld(World w){
        myWorld = (GameWorld) w;
        addButtons();
        resetShop();
        firstOpened = true;
    }

    public void act(){
        if(myWorld.isScreen(myWorld.SHOP)){
            checkMouseAction();
            checkKeyAction();

            if(actTimer.millisElapsed() >= 17){
                actTimer.mark();
                if(direction != 0){
                    slide();
                }
            }

        }

    }

    /**
     * initialization method
     * @param galleryIDs the arraylist of ID's to initialize
     */
    public void initialize(ArrayList<ObjectID> galleryIDs){
        actTimer = new SimpleTimer();
        objectTimer = new SimpleTimer();

        background = new GreenfootImage("Shop.png");

        setImage(background);

        returnButton = new MenuButton("Shop");
        menuUp = new MenuButton("Arrow");
        menuDown = new MenuButton("Arrow");
        menuDown.setRotation(180);
        itemGallery = new ArrayList<>();
        itemCosts = new ArrayList<>();

        for(ObjectID ID : galleryIDs){
            itemGallery.add(new ShopItem(ID, 1));
        }
    }

    /**
     * initialization method
     * @param galleryIDs the Hashmap of ID's to initialize
     */

    public void initialize(HashMap<ObjectID, Integer> galleryIDs){
        actTimer = new SimpleTimer();
        objectTimer = new SimpleTimer();
        background = new GreenfootImage("Backgrounds/Shop.png");
        background.drawImage(new GreenfootImage("Backgrounds/Shop Mascot.png"), 0, 0);
        setImage(background);

        returnButton = new MenuButton("Shop");
        menuUp = new MenuButton("Shop Up");
        menuDown = new MenuButton("Shop Down");
        purchase = new MenuButton("Buy");
        featuredItem = new FeaturedFrame(purchase);
        plus = new MenuButton("Shop mod");
        minus = new MenuButton("Shop mod");
        plus.drawText("+",-1,-16,30);
        minus.drawText("-",-5,-16,30);
        Font font = new Font("Tekton Pro", true, false,  20);

        amountDisplay = new SuperTextBox(" ", new Color(0,0,0,40), Color.BLACK, font, true, featuredItem.getImage().getWidth(), 0, null);

        itemGallery = new ArrayList<>();
        itemCosts = new ArrayList<>();

        for(ObjectID ID : galleryIDs.keySet()){
            if(galleryIDs.get(ID) == -1){
                itemGallery.add(new ShopItem(ID, true));
            }
            else{
                itemGallery.add(new ShopItem(ID, galleryIDs.get(ID)));
            }

        }
        sortItemGallery();
    }
    /**
     * adds an item to the shop
     * @param ID the ID of the item
     */
    public void addItem(ObjectID ID){
        itemGallery.add(new ShopItem(ID, true));
    }

    public void sortItemGallery(){
        //to be filled
    }
    /**
     * resets the shop
     */
    public void resetShop(){
        int index = 0;
        for(ShopItem item : itemGallery){
            int x = LEFT_MARGIN + SPACING * (index % ROW_MAX);
            int y = TOP_MARGIN + SPACING * (index / ROW_MAX);
            if(item.getWorld() != null){                
                item.setLocation(x, y);
            }
            else{
                myWorld.addObject(item, x,y);

                SuperTextBox costDisplay = item.getCostDisplay();

                itemCosts.add(costDisplay);
                myWorld.addObject(costDisplay, x - 5, y + 70);
            }
            index++;
        }

        featuredItem.reset();
        int x = 1043;
        int y = 400;
        myWorld.addObject(featuredItem, x, y);
        myWorld.addObject(plus, 1105, 460);
        myWorld.addObject(minus, 976, 460);
        myWorld.addObject(amountDisplay, x, 460);
    }
    /**
     * adds the buttons related to the shop
     */
    public void addButtons(){
        int x = LEFT_MARGIN/3;
        int y = GameWorld.SCREEN_HEIGHT - LEFT_MARGIN/3;
        myWorld.addObject(returnButton, x, y);
        getWorld().addObject(returnButton, 64, 656);

        myWorld.addObject(menuUp, 814, 250);

        myWorld.addObject(menuDown,814, 570);
        myWorld.addObject(purchase, 1040, 526);
    }
    /**
     * clears the shop and all its contents
     */
    public void clearShop(){
        ArrayList<Actor> components = new ArrayList<>();
        components.add(returnButton);
        components.add(menuUp);
        components.add(menuDown);
        components.add(purchase);
        components.add(featuredItem);
        components.add(amountDisplay);
        components.add(plus);
        components.add(minus);
        components.addAll(myWorld.getObjects(ShopItem.class));
        components.addAll(itemCosts);
        for(Actor component : components){
            myWorld.removeObject(component);
        }
    }
    /**
     * checks for keyboard action
     */
    public void checkKeyAction(){
        
    }
    /**
     * checks for mouseAction
     */
    public void checkMouseAction(){
        if (returnButton.leftClickedThis() && !firstOpened){
            myWorld.setScreen(myWorld.GAME);
            clearShop();
            myWorld.removeObject(this);
        }
        else{
            firstOpened = false;
        }
        if(direction == 0){
            if(menuUp.leftClickedThis()){
                direction = UP;
                pageNumber++;
            }
            if(menuDown.leftClickedThis()){
                direction = DOWN;
                pageNumber--;
            }
            for(ShopItem item : itemGallery){
                if(Greenfoot.mouseClicked(item)){
                    featuredItem.updateDisplay(item);
                    purchaseAmount = 1;
                    amountDisplay.update(String.valueOf(purchaseAmount));

                }
            }
        }
        if(Greenfoot.mousePressed(null)){
            objectTimer.mark();
            dragging = true;
        }
        if(Greenfoot.mouseClicked(null)){
            dragging = false;
        }
        if(featuredItem.getID() != ObjectID.NONE){
            if(purchase.leftClickedThis()){
                ShopItem item = featuredItem.getItem();
                if(CurrencyHandler.isAffordable(item.getID(), purchaseAmount)){
                    if(item.removeOne()){
                        CurrencyHandler.purchase(item.getID(), purchaseAmount);
                        if(item.getID().equals(ObjectID.DIRT_TILE)){
                            item.updateCostDisplay();
                        }
                    }
                    featuredItem.updatePrice(purchaseAmount);
                }

            }
            if(plus.leftClickedThis()){
                ShopItem item = featuredItem.getItem();
                if(CurrencyHandler.isAffordable(item.getID(), purchaseAmount + 1)){
                    purchaseAmount ++;
                    amountDisplay.update(String.valueOf(purchaseAmount));
                    featuredItem.updatePrice(purchaseAmount);
                }
            }
            else if(plus.hoveringThis() && dragging && objectTimer.millisElapsed() > 1000){
                ShopItem item = featuredItem.getItem();
                if(CurrencyHandler.isAffordable(item.getID(), purchaseAmount + 2)){
                    purchaseAmount += 2;
                    amountDisplay.update(String.valueOf(purchaseAmount));
                    featuredItem.updatePrice(purchaseAmount);
                }
            }
            if(minus.leftClickedThis()){
                if(purchaseAmount - 1 >= 1){
                    purchaseAmount --;
                    amountDisplay.update(String.valueOf(purchaseAmount));
                    featuredItem.updatePrice(purchaseAmount);
                }       
            }
            else if(minus.hoveringThis() && dragging && objectTimer.millisElapsed() > 1000){
                if(purchaseAmount - 2 >= 1){
                    purchaseAmount -= 2;
                    amountDisplay.update(String.valueOf(purchaseAmount));
                    featuredItem.updatePrice(purchaseAmount);
                }  
            }
        }
    }
    /**
     * slides the items up and down
     */
    public void slide(){
        double end = SPACING * 3;
        if(distanceTraveled >= end){
            double delta = distanceTraveled - end;
            for(ShopItem item : itemGallery){
                //redundancy
                if(item.getWorld() == null){
                    break;
                }

                item.setLocation(item.getX(), item.getPreciseY() - delta * direction);
            }
            for(SuperTextBox display : itemCosts){
                //redundancy
                if(display.getWorld() == null){
                    break;
                }
                display.setLocation(display.getX(), display.getPreciseY() -delta * direction);
            }
            distanceTraveled = 0;
            direction = 0;
            return;
        }
        for(ShopItem item : itemGallery){
            //redundancy
            if(item.getWorld() == null){
                break;
            }

            item.setLocation(item.getX(), item.getPreciseY() + ITEM_SPEED * direction);
        }
        for(SuperTextBox display : itemCosts){
            //redundancy
            if(display.getWorld() == null){
                break;
            }
            display.setLocation(display.getX(), display.getPreciseY() + ITEM_SPEED * direction);
        }
        distanceTraveled += ITEM_SPEED;

    }
}
