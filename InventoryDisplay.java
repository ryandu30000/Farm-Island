import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Acesses the universal inventory
 * 
 * @author: Zhaoqi Xu 
 * @version: January 2024
 */
public class InventoryDisplay extends SuperSmoothMover
{
    public static final int SPEED = 8;
    public static final int BUTTON_SPACING = 16;
    public static final int BUTTON_OFFSET = 40; 
    public static final int ITEM_SPACING = 82;
    public static final int LINE_SPACING = 100;
    public static final int MARGIN = 164;
    public static final int PAGE_SIZE = 15;

    public ArrayList<ArrayList<GenericItem>> pages;
    private GreenfootImage background;
    private ArrayList<Button> buttons;
    private int direction;
    private boolean open;
    private int page;
    private Button nextPage;
    private Button prevPage;
    private SimpleTimer actTimer;
    
    /**
     * Constructs an InventoryDisplay with the specified buttons.
     *
     * @param buttons The buttons to be displayed in the inventory.
     */
    public InventoryDisplay(ArrayList<Button> buttons){
        this.buttons = buttons;
        initialize();

    }

    /**
     * Adds the InventoryDisplay to the world and initializes button positions.
     *
     * @param w The world to add the InventoryDisplay to.
     */
    public void addedToWorld(World w){

        setLocation(GameWorld.SCREEN_WIDTH + background.getWidth()/2, GameWorld.SCREEN_HEIGHT/2);
        int index = 0;
        for(Button b : buttons){
            w.addObject(b, getX() - background.getWidth()/2 - BUTTON_OFFSET, MARGIN + index * 64 + BUTTON_SPACING);
            index++;
        }

        w.addObject(nextPage, getX() + 130, getY() - 290);
        w.addObject(prevPage, getX() - 110, getY() - 290);
        

    }

    /**
     * Handles actions in each act cycle, such as opening or closing the inventory.
     */
    public void act()
    {
        if(actTimer.millisElapsed() >= 4){
            actTimer.mark();
            if(direction == 1){
                open();
            }
            if(direction == -1){
                close();
            }
        }

    }

    /**
     * Initializes the InventoryDisplay, creating pages and setting up buttons.
     */
    public void initialize(){
        pages = new ArrayList<>();
        
        
        background = new GreenfootImage("Backgrounds/inventory.png");
        nextPage = new MenuButton("Inventory Right");
        prevPage = new MenuButton("Inventory Left");
        
        Font font = new Font("Tekton Pro", true, false,  30);
        SuperTextBox box = new SuperTextBox("INVENTORY", new Color(0,0,0,0), Color.BLACK, font, true, background.getWidth(), 0, null);
        
        background.drawImage(box.getImage(), 10,24);
        actTimer = new SimpleTimer();
        setImage(background);
    }

    /**
     * Checks if the inventory is open.
     *
     * @return True if the inventory is open, false otherwise.
     */
    public boolean isOpen(){
        return open;
    }
    
    /**
     * Checks if the inventory is currently in motion.
     *
     * @return True if the inventory is moving, false otherwise.
     */
    public boolean isMoving(){
        return direction != 0;
    }
    
    /**
     * Removes an item with the specified ObjectID from the inventory.
     *
     * @param ID The ObjectID of the item to be removed.
     */
    public void removeItem(ObjectID ID){
        ArrayList<GenericItem> items = pages.get(page);
        for(GenericItem i : items){
            if(ID.equals(i.getID())){
                i.removeAmountDisplay();
                items.remove(i);
                getWorld().removeObject(i);
                break;
            }
        }

        reloadItems();
    }

    /**
     * Adds an item with the specified ObjectID to the inventory.
     *
     * @param ID The ObjectID of the item to be added.
     */
    public void addItem(ObjectID ID){
        if(pages.isEmpty()){
            page = 0;
            ArrayList<GenericItem> page = new ArrayList<>();
            page.add(new GenericItem(ID));
            pages.add(page);
        }
        else if(pages.get(pages.size() - 1).size() < PAGE_SIZE){
            pages.get(page).add(new GenericItem(ID));
        }
        else{
            ArrayList<GenericItem> page = new ArrayList<>();
            page.add(new GenericItem(ID));
            pages.add(page);
        }
        reloadItems();
    }
    
    /**
     * Reloads items in the inventory based on the current page.
     */
    public void reloadItems(){
        int startX = getX() - 74;
        int startY = getY() - 182;
        int index = 0;
        for(Item i : pages.get(page)){
            int row = index/3;
            int col = index % 3;
            int x = startX + col * ITEM_SPACING;
            int y = startY + row * LINE_SPACING;
            if(i.getWorld() == null){
                getWorld().addObject( i, x, y);
            }
            else{
                i.setLocation(x, y);
            }
            index++;
        }
    }

    /**
     * Forces the inventory to close, removing buttons and updating item positions.
     */
    public void forceClose(){
        setLocation(GameWorld.SCREEN_WIDTH + background.getWidth()/2, getY());
        for(Button b : buttons){
            getWorld().removeObject(b);
        }
        nextPage.setLocation(getX() + 130, getY() - 290);
        prevPage.setLocation(getX() - 110, getY() - 290);
        reloadItems();
        open = false;
    }

    /**
     * Adds buttons to the world at specified positions.
     */
    public void addButtons(){
        int index = 0;
        for(Button b : buttons){
            getWorld().addObject(b, getX() - background.getWidth()/2 - BUTTON_OFFSET, MARGIN + index * 64 + BUTTON_SPACING);
            index++;
        }
    }

    /**
     * Forces the inventory to open, adjusting button positions.
     */
    public void forceOpen(){
        setLocation(GameWorld.SCREEN_WIDTH - background.getWidth()/2, getY());
        for(Button b : buttons){
            b.setLocation(getX() - background.getWidth()/2 - BUTTON_OFFSET, b.getY());
        }
        open = true;
    }

    /**
     * Opens the inventory with smooth animation.
     */
    public void open(){
        open = true;
        direction = 1;
        if(getX() > GameWorld.SCREEN_WIDTH - background.getWidth()/2){
            setLocation(getX() - SPEED, getY());
            for(Button b : buttons){
                b.setLocation(b.getX() - SPEED, b.getY());
            }
            for(Item i : pages.get(page)){
                i.setLocation(i.getX() - SPEED, i.getY());
            }
            nextPage.setLocation(nextPage.getX() - SPEED, nextPage.getY());
            prevPage.setLocation(prevPage.getX() - SPEED, prevPage.getY());
        }
        else{
            direction = 0;
            setLocation(GameWorld.SCREEN_WIDTH - background.getWidth()/2, getY());
            for(Button b : buttons){
                b.setLocation(getX() - background.getWidth()/2 - BUTTON_OFFSET, b.getY());
            }
            reloadItems();
            nextPage.setLocation(getX() + 130, getY() - 290);
            prevPage.setLocation(getX() - 110, getY() - 290);
        }
    }

    /**
     * Closes the inventory with smooth animation.
     */
    public void close(){
        open = false;
        direction = -1;
        if(getX() < GameWorld.SCREEN_WIDTH + background.getWidth()/2){
            setLocation(getX() + SPEED, getY());
            for(Button b : buttons){
                b.setLocation(b.getX() + SPEED, b.getY());
            }
            for(Item i : pages.get(page)){
                i.setLocation(i.getX() + SPEED, i.getY());
            }
            nextPage.setLocation(nextPage.getX() + SPEED, nextPage.getY());
            prevPage.setLocation(prevPage.getX() + SPEED, prevPage.getY());
        }
        else{
            direction = 0;
            setLocation(GameWorld.SCREEN_WIDTH + background.getWidth()/2, getY());
            for(Button b : buttons){
                b.setLocation(getX() - background.getWidth()/2 - BUTTON_OFFSET, b.getY());
            }
            reloadItems();
            nextPage.setLocation(getX() + 130, getY() - 290);
            prevPage.setLocation(getX() - 110, getY() - 290);
        }
    }
}
