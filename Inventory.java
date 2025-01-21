import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.ArrayList;

/**
* This class manages the inventory of items attained by the user.
 * It provides methods for adding, removing, and accessing items in the inventory.
 * The inventory is stored in a HashMap with ObjectID as keys and quantities as values.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class Inventory
{
    private static HashMap <ObjectID, Integer> inventory;
    private static InventoryDisplay display;
    private static EquipDisplay equip;
    private static PorcusMenu porcus;
    public void act()
    {
        // Add your action code here.
    }
    
    /**
     * Initializes the inventory with data from a save file.
     * @param saveFile The path to the save file.
     * @param d The InventoryDisplay to update UI.
     * @param e The EquipDisplay to manage equipped items.
     */
    public static void initialize(String saveFile, InventoryDisplay d, EquipDisplay e, PorcusMenu p){
        equip = e;
        display = d;
        porcus = p;
        inventory = new HashMap<>();
        loadGame(saveFile);
    }
    
    /**
     * Loads the inventory data from a save file.
     * @param saveFile The path to the save file.
     */
    public static void loadGame(String saveFile){
        //will read from game save file
        resetData();
        if(saveFile!= null){
            GameInfo.loadInventory(saveFile);
        }
    }
    

    /**
     * Resets the inventory data by clearing the HashMap.
     */
    public static void resetData() {
        inventory.clear();
    }
    public static void feedItem(ObjectID ID){
        porcus.updateID(ID);
    }

    /**
     * Equips an item in the inventory.
     * @param ID The ObjectID of the item to be equipped.
     */
    public static void equipItem(ObjectID ID) {
        equip.equipItem(ID);
    }
    
    /**
     * Gets the quantity of a specific item in the inventory.
     * @param id The ObjectID of the item.
     * @return The quantity of the item in the inventory.
     */
    public static int getAmount(ObjectID id){
        if(!inventory.containsKey(id)){
            return 0;
        }
        return inventory.get(id);
    }
   
    /**
     * Removes all instances of a specific item from the inventory.
     *
     * @param id The ObjectID of the item to be removed.
     * @return {@code true} if the item was successfully removed, {@code false} otherwise.
     */
    public static boolean removeAll(ObjectID id){
        if(inventory.containsKey(id)){;
            inventory.remove(id);
            return true;
        }
        
        return false;
    }
    
    /**
     * Removes a specified quantity of a specific item from the inventory.
     * If the quantity becomes zero, it also updates the UI and equipment display.
     *
     * @param id     The ObjectID of the item to be removed.
     * @param amount The quantity to be removed.
     * @return {@code true} if the item was successfully removed, {@code false} otherwise.
     */
    public static boolean remove(ObjectID id, int amount){
        if(inventory.containsKey(id)){
            if(inventory.get(id) - amount == 0){
                inventory.remove(id);
                display.removeItem(id);
                equip.unEquipItem(id);
            }
            else if(inventory.get(id) - amount < 0){
                return false;
            }
            else{
                inventory.put(id,inventory.get(id) - amount);
            }
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Removes a single instance of a specific item from the inventory.
     *
     * @param id The ObjectID of the item to be removed.
     * @return {@code true} if the item was successfully removed, {@code false} otherwise.
     */
    public static boolean remove(ObjectID id) {
        return remove(id, 1);
    }

    /**
     * Adds a single instance of an item to the inventory.
     * If the item is not already in the inventory, it updates the UI and equipment display.
     *
     * @param id The ObjectID of the item to be added.
     */
    public static void add(ObjectID id) {
        add(id, 1);
    }

    /**
     * Adds a specified quantity of an item to the inventory.
     * If the item is not already in the inventory, it updates the UI and equipment display.
     *
     * @param id     The ObjectID of the item to be added.
     * @param amount The quantity to be added.
     */
    public static void add(ObjectID id, int amount) {
        if (!inventory.containsKey(id)) {
            inventory.put(id, amount);
            display.addItem(id);
            equip.equipItem(id);
        } else {
            inventory.put(id, inventory.get(id) + amount);
        }
    }

    /**
     * Outputs the current contents of the inventory.
     * For debugging purposes only.
     */
    public static void output() {
        System.out.println("--------------------");
        for (ObjectID id : inventory.keySet()) {
            System.out.println(id + " : " + inventory.get(id));
        }
    }

    /**
     * Gets the entire inventory as a HashMap.
     *
     * @return The HashMap representing the inventory.
     */
    public static HashMap<ObjectID, Integer> getInventory() {
        return inventory;
    }
}
