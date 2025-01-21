import java.util.ArrayList;
/**
 * Manages achievements in the game. Sets up the achievements and checks when they are completed.
 * 
 * @author: Ryan Du
 * @version: January 2024
 */
public class AchievementManager
{
    public static final String AGRICULTURE_A = "AgricultureA";
    public static final String AGRICULTURE_B = "AgricultureB";
    public static int totalPlants;
    public static int totalTiles;
    public static ObjectID collectedPlant;
    public static ArrayList<Achievement> agricultureA, agricultureB;
    
    
    /**
     * Initializes the achievements.
     */
    public AchievementManager(){
        totalTiles = 0;
        totalPlants = 0;
        initializeAgricultureA();
        initializeAgricultureB();
    }
    
    /**
     * Sets up the achievements of type AgriculutreA. The achievement is
     * based on the total number of plants made.
     */
    private void initializeAgricultureA(){
        agricultureA = new ArrayList<>();
        agricultureA.add(new Achievement("Green Begginings", "Plant 1 seed!", AGRICULTURE_A, 0));
        agricultureA.add(new Achievement("Novice Planter", "Plant 10 seeds!", AGRICULTURE_A, 1));
        agricultureA.add(new Achievement("Veteran Planter", "Plant 100 seeds!", AGRICULTURE_A, 2));
        agricultureA.add(new Achievement("Planting Master", "Plant 1000 seeds!", AGRICULTURE_A, 3));
    }
    
    /**
     * Sets up the achievements of type AgricultureB. The achievement is 
     * based on unlocking a new plant.
     */
    private void initializeAgricultureB(){
        agricultureB = new ArrayList<>();
        agricultureB.add(new Achievement("Carrot Planter", "Collect Carrot!", AGRICULTURE_B,0));
        agricultureB.add(new Achievement("Tomato Planter", "Collect Tomato!", AGRICULTURE_B,1));
        agricultureB.add(new Achievement("Strawberry Planter", "Collect Strawberry!", AGRICULTURE_B,2));
        agricultureB.add(new Achievement("Golden Tomato Planter", "Collect Golden Tomato!", AGRICULTURE_B,3));
        agricultureB.add(new Achievement("Dragon Fruit Planter", "Collect Dragon Fruit!", AGRICULTURE_B,4));
        agricultureB.add(new Achievement("Porcus Planter", "Collect Porcus Wheat!", AGRICULTURE_B,5));
    }
    
    /**
     * Increases the total plants by 1. Checks if the total plant 
     * matches the requirements for completing an achievement.
     */
    public static void updateTotalPlant(){
        totalPlants ++;
        switch(totalPlants){
            case 1:
                agricultureA.get(0).setCompleted();
                break;
            case 10:
                agricultureA.get(1).setCompleted();
                break;
            case 100:
                agricultureA.get(2).setCompleted();
                break;
            case 1000:
                agricultureA.get(3).setCompleted();
        }
    }
    
    public static void loadAgricultureA(int num){
        if(num >= 1000){
            for(int i = 0; i < 4; i++){
                agricultureA.get(i).setCompleted();
            }
        }
        else if(num >= 100){
            for(int i = 0; i < 3; i++){
                agricultureA.get(i).setCompleted();
            }
        }
        else if(num >= 10){
            for(int i = 0; i < 2; i++){
                agricultureA.get(i).setCompleted();
            }
        }
        else if(num >= 1){
            agricultureA.get(0).setCompleted();
        }
    }
    
    public static void updateLatestPlant(ObjectID id){
        collectedPlant = id;
        switch(collectedPlant){
            case CARROT:
                agricultureB.get(0).setCompleted();
                break;
            case TOMATO:
                agricultureB.get(1).setCompleted();
                break;
            case STRAWBERRY:
                agricultureB.get(2).setCompleted();
                break;
            case GOLDEN_TOMATO:
                agricultureB.get(3).setCompleted();
                break;
            case DRAGONFRUIT:
                agricultureB.get(4).setCompleted();
                break;
            case PORCUS_WHEAT:
                agricultureB.get(5).setCompleted();
                break;
        }
    }
}