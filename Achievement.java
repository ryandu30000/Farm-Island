import greenfoot.*;

/**
 * Represents an achievement with a name, description, and completion status.
 * 
 * 
 * @author: Ryan Du
 * @version: January 2024
 */
public class Achievement
{
    private GreenfootImage image;
    private String name;
    private String description;
    private boolean completed;
    private String completeImageIcon, incompleteImageIcon;
    private String notificationIcon;
    
    /**
     * Represents an achievement with a name, description, and completion status.
     * 
     * @param name           The name of the achievement.
     * @param description    The description of the achievement.
     * @param type           The type of achievement.
     * @param num            The number associated with the achievement.
     */
    public Achievement(String name, String description, String type, int num)
    {
        this.name = name;
        this.description = description;
        completed = false;
        completeImageIcon = type + "_" + num + "C";
        incompleteImageIcon = type + "_" + num + "I";
        notificationIcon = type + "_" + num;
    }
    
    /**
     * Gets the name of the achievement.
     * 
     * @return String name of the achievement.
     */
    public String getName(){
        return name;
    }
    /**
     * Sets the name of the achievement to the specified name.
     * 
     * @param n The specified name. 
     */
    public void setName(String n){
        name = n;
    }
    /**
     * Gets the description of the achievement.
     * 
     * @return String Description of the achievement.
     */
    public String getDescription(){
        return description;
    }
    /**
     * Sets the description of the achievement to the specified description.
     * 
     * @param d The specified description. 
     */
    public void setDescription(String d){
        description = d;
    }
    /**
     * Gets the completion status of the achievement.
     * 
     * @return boolean Completion status of the achievement.
     */
    public boolean getCompleted(){
        return completed;
    }
    /**
     * Sets the completion status to be true.
     * 
     */
    public void setCompleted(){
        completed = true;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    /**
     * Sets the image of the achievement to the image of the specified String.
     * 
     * @param img The specified String.
     */
    public void setImage(String img){
        image = new GreenfootImage(img);
    }
    /**
     * Gets the incomplete image icon.
     * 
     * @return String The incomplete image icon.
     */
    public String getIncompleteIcon(){
        return incompleteImageIcon;
    }
    /**
     * Gets the complete image icon.
     * 
     * @return String The complete image icon.
     */
    public String getCompleteIcon(){
        return completeImageIcon;
    }
    /**
     * Gets the notification image icon.
     * 
     * @return String The notification image icon.
     */
    public String getNotificationIcon(){
        return notificationIcon;
    }
}
