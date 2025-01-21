import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class PorcusMenu here.
 * 
 * @author: Angela Gao, Zhaoqi Xu 
 * @version (a version number or a date)
 */
public class PorcusMenu extends SuperSmoothMover
{
    public static final int SPEED = 8;
    public static final int BUTTON_OFFSET = 84;
    private Button porcus;
    private boolean open;
    private int direction;
    private GreenfootImage background;
    private SimpleTimer actTimer;
    private ItemFrame frame;
    private Button plus;
    private Button minus;
    private Button sell;
    private Button feed;
    private SuperTextBox amountDisplay;
    private int amount;
    private boolean dragging;
    private SimpleTimer objectTimer;
    private ObjectID ID;
    private GreenfootImage[] pigStages;
    private int stage;

    private ArrayList<Milestone> milestones;
    private ArrayList<Milestone> complete;

    private Milestone m1;
    private Milestone m2;

    private ItemFrame milestone1;
    private SuperTextBox status1;
    private int statusAmount1;
    private ItemFrame milestone2;
    private SuperTextBox status2;
    private int statusAmount2;
    
    private boolean[] stageCompletion;
    private ShopMenu shop;

    /**
     * Initializes a PorcusMenu with a given Button.
     * 
     * @param porcus The Button associated with the PorcusMenu.
     * @param savedFile the save file to laoad data.
     * @param shop the shop menu
     */
    public PorcusMenu(String savedFile, Button porcus, ShopMenu shop){
        this.shop = shop;
        
        milestones = new ArrayList<>();
        complete = new ArrayList<>();
        InitializeMilestones();
        this.porcus = porcus;
        ID = ObjectID.NONE;
        pigStages = new GreenfootImage[4];

        for(int i = 0; i < pigStages.length; i++){
            pigStages[i] = new GreenfootImage("Backgrounds/PIG " + i + ".png");
        }

        setPigStage(0);
        
        stageCompletion = new boolean[3];
        
        Font font = new Font("Tekton Pro", true, false,  20);
        m1 = milestones.remove(0);
        m2 = milestones.remove(0);
        milestone1 = new ItemFrame(m1.getID(), "Frame Glow", 32, 32);
        milestone2 = new ItemFrame(m2.getID(), "Frame Glow", 32, 32);
        statusAmount1 = 0;
        statusAmount2 = 0;
        status1 = new SuperTextBox(statusAmount1 + "/" + m1.getAmount(), new Color(0,0,0,0), Color.BLACK, font, false, 86, 0, null);
        status2 = new SuperTextBox(statusAmount2 + "/" + m2.getAmount(), new Color(0,0,0,0), Color.BLACK, font, false, 86, 0, null);

        actTimer = new SimpleTimer();
        frame = new ItemFrame(ID, "Shop Frame", 90, 90);
        plus = new GameButton("Plus", 50);
        minus = new GameButton("Minus", 50);

        objectTimer = new SimpleTimer();
        feed = new GameButton("Buy", 90);
        sell = new GameButton("Buy", 90);
        feed.drawText("FEED", 0, -10);
        sell.drawText("SELL", 0, -10);

        amountDisplay = new SuperTextBox(" ", new Color(0,0,0,0), Color.BLACK, font, true, 180, 0, null);
        amount = 0;
        setImage(background);
        if(savedFile!= null){
            GameInfo.loadPorcus(savedFile, this);
            updateMilestones();
        }
    }

    /**
     * Adds the PorcusMenu and its associated Button to the world.
     * 
     * @param w The world to which the PorcusMenu is added.
     */
    public void addedToWorld(World w){
        setLocation(- background.getWidth()/2, GameWorld.SCREEN_HEIGHT - background.getHeight()/2 - 32);
        w.addObject(porcus, getX() + background.getWidth()/2 + BUTTON_OFFSET, GameWorld.SCREEN_HEIGHT - BUTTON_OFFSET);
        w.addObject(frame, getX() - 45, getY() + 68);
        w.addObject(plus, frame.getX() + 90, frame.getY());
        w.addObject(minus, frame.getX() - 90, frame.getY());
        w.addObject(amountDisplay, frame.getX(), frame.getY() - 56);
        w.addObject(feed, frame.getX() + 48, frame.getY() + 84);
        w.addObject(sell, frame.getX() - 48, frame.getY() + 84);
        w.addObject(milestone1, getX() - 90, getY() - 200);
        w.addObject(milestone2, getX() + 30, milestone1.getY());
        w.addObject(status1, milestone1.getX() + 64, milestone1.getY());
        w.addObject(status2, milestone2.getX() + 64, milestone2.getY());
    }
    /**
     * Sets the stage of the growing pig
     * 
     * @param stage the stage of the pig
     */
    public void setPigStage(int stage){
        background = new GreenfootImage("Backgrounds/Porcus.png");
        background.drawImage(pigStages[stage], 120 - pigStages[stage].getWidth()/2, 160 - pigStages[stage].getHeight()/2);
        setImage(background);
    }
    /**
     * updates
     */
    public void updateID(ObjectID ID){
        switch(ID){
            case WHEAT:
            case CARROT:
            case TOMATO:
            case PORCUS_WHEAT:
            case BLUEBERRY:
            case DRAGONFRUIT:
            case GOLDEN_TOMATO:
            case SILVER_TOMATO:
            case STRAWBERRY:
                amount = Inventory.getAmount(ID);
                amountDisplay.update(String.valueOf(amount));
                this.ID = ID;
                frame.updateID(ID);
                break;
        }
    }
    /**
     * Performs the act of the PorcusMenu.
     */
    public void act()
    {
        checkMileStones();
        checkMouseAction();
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
     * checks for milestone compleations
     */
    public void checkMileStones(){
        if(complete.size() < 10){
            setPigStage(0);
        }
        else if(complete.size()  >= 10 && complete.size()  < 20 && !stageCompletion[0]){
            stageCompletion[0] = true;
            shop.addItem(ObjectID.BLUEBERRY_SEED);
            shop.addItem(ObjectID.STRAWBERRY_SEED);
            setPigStage(1);
        }
        else if(complete.size()  >= 20 && complete.size() < 30 && !stageCompletion[1]){
            stageCompletion[1] = true;
            shop.addItem(ObjectID.GOLDEN_TOMATO_SEED);
            shop.addItem(ObjectID.SILVER_TOMATO_SEED);
            setPigStage(2);
        }
        else if(complete.size()  >= 30 && complete.size() < 40 && !stageCompletion[2]){
            stageCompletion[2] = true;
            shop.addItem(ObjectID.PORCUS_WHEAT_SEED);
            shop.addItem(ObjectID.DRAGONFRUIT_SEED);
            setPigStage(3);
        }
    }
    /**
     * initializes all of the milestones
     */
    public void InitializeMilestones(){
        milestones.add(new Milestone(ObjectID.WHEAT, 10));
        milestones.add(new Milestone(ObjectID.CARROT, 10));
        milestones.add(new Milestone(ObjectID.WHEAT, 15));
        milestones.add(new Milestone(ObjectID.CARROT, 15));
        milestones.add(new Milestone(ObjectID.WHEAT, 25));
        milestones.add(new Milestone(ObjectID.TOMATO, 10));
        milestones.add(new Milestone(ObjectID.WHEAT, 30));
        milestones.add(new Milestone(ObjectID.TOMATO, 30));
        milestones.add(new Milestone(ObjectID.CARROT, 30));
        milestones.add(new Milestone(ObjectID.WHEAT, 50));

        milestones.add(new Milestone(ObjectID.TOMATO, 50));
        milestones.add(new Milestone(ObjectID.CARROT, 50));
        milestones.add(new Milestone(ObjectID.BLUEBERRY, 20));
        milestones.add(new Milestone(ObjectID.STRAWBERRY, 20));
        milestones.add(new Milestone(ObjectID.WHEAT, 75));
        milestones.add(new Milestone(ObjectID.TOMATO, 75));
        milestones.add(new Milestone(ObjectID.STRAWBERRY, 30));
        milestones.add(new Milestone(ObjectID.BLUEBERRY, 50));
        milestones.add(new Milestone(ObjectID.CARROT, 100));
        milestones.add(new Milestone(ObjectID.STRAWBERRY, 50));

        milestones.add(new Milestone(ObjectID.SILVER_TOMATO, 30));
        milestones.add(new Milestone(ObjectID.GOLDEN_TOMATO, 30));
        milestones.add(new Milestone(ObjectID.WHEAT, 100));
        milestones.add(new Milestone(ObjectID.SILVER_TOMATO, 50));
        milestones.add(new Milestone(ObjectID.SILVER_TOMATO, 100));
        milestones.add(new Milestone(ObjectID.SILVER_TOMATO, 200));
        milestones.add(new Milestone(ObjectID.GOLDEN_TOMATO, 50));
        milestones.add(new Milestone(ObjectID.GOLDEN_TOMATO, 100));
        milestones.add(new Milestone(ObjectID.GOLDEN_TOMATO, 200));
        milestones.add(new Milestone(ObjectID.GOLDEN_TOMATO, 300));

        milestones.add(new Milestone(ObjectID.PORCUS_WHEAT, 10));
        milestones.add(new Milestone(ObjectID.DRAGONFRUIT, 100));
        milestones.add(new Milestone(ObjectID.BLUEBERRY, 500));
        milestones.add(new Milestone(ObjectID.TOMATO, 5000));
        milestones.add(new Milestone(ObjectID.WHEAT, 10000));
        milestones.add(new Milestone(ObjectID.PORCUS_WHEAT, 100));
        milestones.add(new Milestone(ObjectID.DRAGONFRUIT, 888));
        milestones.add(new Milestone(ObjectID.STRAWBERRY, 10000));
        milestones.add(new Milestone(ObjectID.CARROT, 100000));
        milestones.add(new Milestone(ObjectID.PORCUS_WHEAT, 10000));
    }
    /**
     * resets the porcus screen
     */
    public void reset(){
        getWorld().addObject(porcus, getX() + background.getWidth()/2 + BUTTON_OFFSET, GameWorld.SCREEN_HEIGHT - BUTTON_OFFSET);
        reposition();
    }
    /**
     * forces the screen to close
     */
    public void forceClose(){
        setLocation(- background.getWidth()/2, GameWorld.SCREEN_HEIGHT - background.getHeight()/2 - 32);
        reposition();
        getWorld().removeObject(porcus);
    }
    /**
     * adds the fed items towards milestone
     * @param ID the ID of the item
     * @param amount the amount of the item
     */
    public void feed(ObjectID ID, int amount){

        if(ID == m1.getID()){
            if(statusAmount1 + amount > m1.getAmount()){
                int delta = statusAmount1 + amount - m1.getAmount();
                if(Inventory.remove(ID, amount - delta)){
                    statusAmount1 += amount - delta;
                    this.amount -= amount - delta;
                }

            }
            else{
                if(Inventory.remove(ID, amount)){
                    statusAmount1 += amount;
                    this.amount -= amount;
                }
            }
            if(statusAmount1 == m1.getAmount()){
                complete.add(m1);
                if(milestones.get(0) != null){
                    statusAmount1 = 0;
                    m1 = milestones.remove(0);
                }
            }
            amountDisplay.update(String.valueOf(this.amount));
            updateMilestones();

        }
        else if(ID == m2.getID()){
            if(statusAmount2 + amount > m2.getAmount()){
                int delta = statusAmount2 + amount - m2.getAmount();
                if(Inventory.remove(ID, amount - delta)){
                    statusAmount2 += amount - delta;
                    this.amount -= amount - delta;
                }

            }
            else{
                if(Inventory.remove(ID, amount)){
                    statusAmount2 += amount;
                    this.amount -= amount;
                }
            }
            if(statusAmount2 == m2.getAmount()){
                complete.add(m2);
                if(milestones.get(0) != null){
                    statusAmount2 = 0;
                    m2 = milestones.remove(0);
                }
            }
            amountDisplay.update(String.valueOf(this.amount));
            updateMilestones();
        }
    }
    /**
     * updates the milestone display
     */
    public void updateMilestones(){
        milestone1.updateID(m1.getID());
        milestone2.updateID(m2.getID());
        status1.update(statusAmount1 + "/" + m1.getAmount());
        status2.update(statusAmount2 + "/" + m2.getAmount());
    }   
    /**
     * checks for mouse input and does tasks accordingly
     */
    public void checkMouseAction(){
        if(Greenfoot.mousePressed(null)){
            objectTimer.mark();
            dragging = true;
        }
        if(Greenfoot.mouseClicked(null)){
            dragging = false;
        }
        if(sell.leftClickedThis()){
            if(Inventory.remove(ID, amount)){
                int sellPrice = CurrencyHandler.getPrice(ID, amount);
                CurrencyHandler.deposit(sellPrice);
                amount = 0;
                amountDisplay.update(String.valueOf(amount));
            }

        }
        if(feed.leftClickedThis()){
            feed(ID, amount);
        }
        if(plus.leftClickedThis()){
            amount++;
            if(amount > Inventory.getAmount(ID)){
                amount = Inventory.getAmount(ID);
            }
            amountDisplay.update(String.valueOf(amount));
        }
        else if(plus.hoveringThis() && dragging && objectTimer.millisElapsed() > 1000){
            amount+=2;
            if(amount > Inventory.getAmount(ID)){
                amount = Inventory.getAmount(ID);
            }
            amountDisplay.update(String.valueOf(amount));
        }
        if(minus.leftClickedThis()){
            amount--;
            if(amount < 0){
                amount = 0;
            }
            amountDisplay.update(String.valueOf(amount));
        }
        else if(minus.hoveringThis() && dragging && objectTimer.millisElapsed() > 1000){
            amount-=2;
            if(amount < 0){
                amount = 0;
            }
            amountDisplay.update(String.valueOf(amount));
        }
    }
    /**
     * repositions all of the items within the menu when moving
     */
    public void reposition(){
        porcus.setLocation(getX() + background.getWidth()/2 + BUTTON_OFFSET, porcus.getY());
        frame.setLocation(getX()-45, getY() + 68);
        plus.setLocation(frame.getX() + 90, frame.getY());
        minus.setLocation(frame.getX() - 90, frame.getY());
        amountDisplay.setLocation(frame.getX(), frame.getY() - 56);
        feed.setLocation(frame.getX() + 48, frame.getY() + 84);
        sell.setLocation(frame.getX() - 48, frame.getY() + 84);
        milestone1.setLocation(getX() - 146, getY() - 205);
        milestone2.setLocation(getX() - 36, milestone1.getY());
        status1.setLocation(milestone1.getX() + 50, milestone1.getY());
        status2.setLocation(milestone2.getX() + 50, milestone2.getY());
    }
    /**
     * Opens the PorcusMenu.
     */
    public void open(){
        open = true;
        direction = 1;
        if(getX() < background.getWidth()/2){
            setLocation(getX() + SPEED, getY());

            reposition();
        }
        else{
            direction = 0;
            setLocation(background.getWidth()/2, getY());
            reposition();
        }
    }

    /**
     * Closes the PorcusMenu.
     */
    public void close(){
        open = false;
        direction = -1;
        if(getX() > - background.getWidth()/2){
            setLocation(getX() - SPEED, getY());

            reposition();
        }
        else{
            direction = 0;
            setLocation(-background.getWidth()/2, getY());

            reposition();
        }
    }

    /**
     * Checks if the PorcusMenu is open.
     * 
     * @return true if the PorcusMenu is open, false otherwise.
     */
    public boolean isOpen(){
        return open;
    }
    /**
     * gets the milestone 1
     * @return Milestone
     */
    public Milestone getMileStone1(){
        return m1;
    }
    /**
     * gets the milestone 2
     * @return Milestone
     */
    public Milestone getMileStone2(){
        return m2;
    }
    /**
     * gets all the incomplete milestones
     * @return ArrayList<Milestone> the list of milestones
     */
    public ArrayList<Milestone> getMileStones(){
        return milestones;
    }
    /**
     * gets all the complete milestones
     * @return ArrayList<Milestone>
     */
    public ArrayList<Milestone> getComplete(){
        return complete;
    }
    /**
     * sets the milestone 1
     * @param m 
     */
    public void setMilestone1(Milestone m){
        m1 = m;
    }
    /**
     * sets the milestone 2
     * @param m
     */
    public void setMilestone2(Milestone m){
        m2 = m;
    }
    /**
     * sets the incomplete milestones
     * @param m
     */
    public void setMilestones(ArrayList<Milestone> m){
        milestones = m;
    }
    /**
     * sets the complete milestones
     * @param m
     */
    public void setComplete(ArrayList<Milestone> c){
        complete = c;
    }
}
