import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AchievementNotification here.
 * 
 * @author: Ryan Du 
 * @version: January 2024
 */
public class AchievementNotification extends SuperSmoothMover
{
    public static int WIDTH = 255;
    public static int HEIGHT = 44;
    private GreenfootImage image;
    private Achievement achievement;
    
    /**
     * Set's the respective image for the respective achievement 
     * @param achievement 
     */
    public AchievementNotification(Achievement achievement){
        this.achievement = achievement;
        initialize();
        setImage(image);
    }
    
    /**
     * Intializes the achievement notification
     */
    public void initialize(){
        image = new GreenfootImage("Displays/AchievementBanners/" + achievement.getNotificationIcon() + ".png");
    }
}
