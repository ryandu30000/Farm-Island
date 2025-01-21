import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AchievementBanner here.
 * 
 * @author: Ryan Du
 * @version: January 2024
 */
public class AchievementBanner extends SuperSmoothMover
{
    public static final int WIDTH = 160;
    public static final int HEIGHT = 160;
    private boolean completed;
    private GreenfootImage image;
    private Achievement achievement;
    
    /**
     * Constructs an AchievementBanner with a specified achievement and completion status.
     *
     * @param achievement The Achievement object associated with the banner.
     * @param completed   A boolean indicating whether the achievement is completed.
     */
    public AchievementBanner(Achievement achievement, boolean completed){
        this.achievement = achievement;
        this.completed = completed;
        initialize();
        setImage(image);
    }
    
    /**
     * Initializes the AchievementBanner by loading the appropriate image based on completion status.
     */
    public void initialize(){
        if(completed == false){
            image = new GreenfootImage("Displays/AchievementIcons/" + achievement.getIncompleteIcon() + ".png");
        }
        else{
            image = new GreenfootImage("Displays/AchievementIcons/" + achievement.getCompleteIcon() + ".png");
        }
    }
    
    public Achievement getAchievement(){
        return achievement;
    }
}
