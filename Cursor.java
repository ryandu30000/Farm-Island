import greenfoot.*;

/**
 * Write a description of class Cursor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cursor
{
    private static Item holding;
    private static Tool activeTool;
    /**
     * gets the mouse info
     * @return MouseInfo
     */
    public static MouseInfo getMouseInfo(){
        return Greenfoot.getMouseInfo();
    }
    /**
     * releases the held item
     */
    public static void release(){
        holding = null;
    }
        /**
         * equips the tool
         * @param tool
         */
    public static void setTool(Tool tool){
        activeTool = tool;
    }
    /**
     * gets the tool held
     * @return Tool
     */
    public static Tool getTool(){
        return activeTool;
    }
    /**
     * gets the item held
     * @return Item
     */
    public static Item getItem(){
        return holding;
    }
    /**
     * gets the Button last pressed
     */
    public static int getButton(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null){
            return mouse.getButton();
        }
        else{
            return -1;
        }
        
    }
    /**
     * checks if the last click was a left click
     * @return boolean was it left clicked
     */
    
    public static boolean leftClicked(){
        return getButton() == 1;
    }
    /**
     * checks if the last click was a right click
     * @return boolean was it right clicked
     */
    public static boolean rightClicked(){
        return getButton() == 3;
    }
    /**
     * gets the click count of the mouse
     * @return int the click couint
     */
    public static int getClickCount(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        return mouse.getClickCount();
    }
    /**
     * gets the X position of the mouse
     * @return int 
     */
    public static int getX(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(mouse != null){
            return mouse.getX();
        }
        else{
            return 0;
        }
    }
    /**
     * gets the Y osition of the mouse
     * @return int
     */
    public static int getY(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
         if(mouse != null){
            return mouse.getX();
        }
        else{
            return 0;
        }
    }
    /**
     * picks up an item
     * @param item the item to pick up
     */
    public static void pickUp(Item item){
        holding = item;
    }
}
