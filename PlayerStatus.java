/**
 * PlayerStatus class represents the status of the player in the game.
 * It provides methods to access and modify player-related status information.
 * 
 * @author: Zhaoqi Xu 
 * @version: January 2024
 */
public class PlayerStatus  
{
    /**
     * Represents the current replant status. If true, the player will replant crops.
     * If false, the player will not replant crops.
     */
    private static boolean replant = true;
    
    /**
     * Returns the current replant status.
     * 
     * @return true if replanting is enabled, false otherwise.
     */
    public static boolean isReplant(){
        return replant;
    }
    
    /**
     * Sets the replant status.
     * 
     * @param replantStatus true to enable replanting, false to disable replanting.
     */
    public static void setReplant(boolean replant){
        replant = replant;
    }
}
