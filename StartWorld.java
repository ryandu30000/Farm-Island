import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import java.lang.NullPointerException;


/**
 * Game description:
 * The game is a farming game where we start off with a single block. Through farming we are able to expand our land and unlock various types of plants
 * by feeing porcus the pig. Have fun :)
 * 
 * Goal:
 * - expand your island
 * - feed the pig to the fattest form (stage 4)
 * - unlock and plant Porcus Wheat!!!
 * 
 * UI help/How to play:
 * - right click drag to move islands
 * - left click equip slot to use item
 * - hoe used to harvest plants
 * - shovel used to remove plants (not harvest)
 * - equip seed and left click island to plant it (you can hold and drag)
 * - the pig upgrade requirments are on the top of the pig menu
 * - go to the shop to purchase seeds
 * - click on inventory item to equip it or feed the pig
 * 
 * Saving:
 * - Save your progress by clicking the save button, and type in the file name.
 * - To load your game just click on a previous save file.
 * 
 * Buttons:
 * - press the home button in case you lose your islands
 * - space bar to remove islands
 * - toggle button to switch betwee building modes
 * - open left pig menu to feed the pig and unlock new plants
 * - save button to save progress
 * - achievement button to view achievments
 * - inventory button to slide open inventory
 * 
 * features:
 * - pig has 4 stages that it can grow to
 * - islands can move to expand in a 32 x 32 area
 * - interactive shop, inventory, and equip
 * - numerous unlockable plants
 * - So much more!!! (play the game please)
 * 
 * Known Bugs/redudant visuals:
 * - fertilizer not working so the third inventory slot will not be filled
 * - inventory arrows do not do anything
 * - shop allows you to scroll to infinity and numbers do not fade properly
 * 
 * @author: Zhaoqi Xu, Ryan Du, Carson Cooc
 * @version: January 2024
 */
public class StartWorld extends World
{
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    
    private GreenfootImage background = new GreenfootImage("BackGrounds/Start BG Back.png");
    private GreenfootImage clouds = new GreenfootImage("BackGrounds/Start BG Clouds.png");
    private GreenfootImage island = new GreenfootImage("BackGrounds/Start BG Island.png");
    
    private GreenfootImage screen = new GreenfootImage(430, 720);

    private HighlightButton startButton;
    private HighlightButton load;
    private HighlightButton settings;
    private HighlightButton quit;

    private Scanner scan;
    private Scanner fileScanner;
    private String fileName;
    private static ArrayList<String> saveFile;

    private GreenfootSound TitleScreenMusic;
    /**
     * Constructor for this class 
     */
    public StartWorld()
    {   
        super(SCREEN_WIDTH, SCREEN_HEIGHT, 1, false);
        //screen.setColor(new Color(255,255,255,45));
        //screen.fillPolygon(new int[] {0,0,430,390}, new int[] {0,720,720,0}, 4);

        //background.drawImage(screen, 0, 0);
        background.drawImage(new GreenfootImage("Backgrounds/Title.png"), 120, 0);
        setBackground(background);

        initialize();
    }

    /**
     * Responds to button clicks in the TitleScreen by executing corresponding actions.
     * - Clicking the "Start" button stops the title screen music and transitions to the GameWorld.
     * - Clicking the "Load" button prompts the user to enter a file name via the console,
     *   attempts to load the specified file, and transitions to the GameWorld if successful.
     * - Clicking the "Settings" button transitions to the SettingsWorld and stops the title screen music.
     * - Clicking the "Quit" button terminates the Greenfoot application.
     */
    public void act(){
        //button actions here
        if(startButton.leftClickedThis()){
            TitleScreenMusic.stop();
            Greenfoot.setWorld(new GameWorld(null));
        }
        if(load.leftClickedThis()){
            JFileChooser fileChooser = new JFileChooser("Saves");
            fileChooser.setDialogTitle("Select Your File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                try{
                    fileName = selectedFile.getAbsolutePath();
                    try{
                        fileScanner = new Scanner (new File(fileName));
                        TitleScreenMusic.stop();
                        Greenfoot.setWorld(new GameWorld(fileName));
                    }
                    catch(FileNotFoundException e){
                        System.out.println("Invalid File");
                    }
                    fileScanner.close();
                }
                catch(NullPointerException e){
                    
                }
            }
        }
        if(settings.leftClickedThis()){
            Greenfoot.setWorld(new SettingsWorld());
            TitleScreenMusic.stop();
        }
        if(quit.leftClickedThis()){
            Greenfoot.stop();
        }
    }

    /**
     * Initializes the TitleScreen world by setting up background music, paint order,
     * and creating buttons for starting, loading, adjusting settings, and quitting the game.
     * The buttons are configured with transparency and text, and added to the world.
     * Additional effects, such as a rocking island and pulsating clouds, are also added for visual appeal.
     */
    public void initialize(){
        TitleScreenMusic = new GreenfootSound ("TItleScreenMusic.mp3");
        TitleScreenMusic.setVolume(50);
        setPaintOrder(Button.class, Effect.class);
        startButton = new HighlightButton("Start Menu");
        load = new HighlightButton("Start Menu");
        settings = new HighlightButton("Start Menu");
        quit = new HighlightButton("Start Menu");
        
        startButton.setTransparancy(230);
        load.setTransparancy(230);
        settings.setTransparancy(230);
        quit.setTransparancy(230);
        
        
        startButton.drawText("START",0,34, 30);
        load.drawText("LOAD",0,34, 30);
        settings.drawText("SETTINGS",0,34, 30);
        quit.drawText("QUIT",0,34, 30);
        
        
        
        addObject(startButton,SCREEN_WIDTH/2,290);
        addObject(load,SCREEN_WIDTH/2,430);
        //addObject(settings,SCREEN_WIDTH/2,430);
        addObject(quit,SCREEN_WIDTH/2,570);
        addObject(new Effect(Effect.ROCK, island), SCREEN_WIDTH/2, SCREEN_HEIGHT/2 + 50);
        addObject(new Effect(Effect.PULSE, clouds), SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
    }

    /**
     * Callback method called when the TitleScreen scenario has started.
     * Initiates the playback of background music for the TitleScreen.
     */
    public void started() {
        TitleScreenMusic.playLoop();
    }
    
    /**
     * Callback method called when the TitleScreen scenario has stopped.
     * Pauses the background music for the TitleScreen.
     */
    public void stopped() {
        TitleScreenMusic.pause();
    }
}
