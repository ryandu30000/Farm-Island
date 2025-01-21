import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ForegroundEffect here.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class representing foreground effects.
 * These effects are displayed above other objects in the world.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class ForegroundEffect extends Effect {

    /**
     * Constructs a foreground effect using an image file.
     * 
     * @param effect   The type of effect (e.g., FLOAT, SHAKE).
     * @param image    The image to be displayed for the effect.
     */
    public ForegroundEffect(int effect, String image) {
        this(effect, new GreenfootImage(image));
    }

    /**
     * Constructs a foreground effect using a GreenfootImage.
     * 
     * @param effect   The type of effect (e.g., FLOAT, SHAKE).
     * @param image    The GreenfootImage to be displayed for the effect.
     */
    public ForegroundEffect(int effect, GreenfootImage image) {
        this(effect, image, 10);
    }

    /**
     * Constructs a foreground effect with a specified duration.
     * 
     * @param effect    The type of effect (e.g., FLOAT, SHAKE).
     * @param image     The GreenfootImage to be displayed for the effect.
     * @param duration  The duration of the effect in acts.
     */
    public ForegroundEffect(int effect, GreenfootImage image, int duration) {
        this(effect, image, duration, 0.1);
    }

    /**
     * Constructs a foreground effect with a specified duration and speed.
     * 
     * @param effect    The type of effect (e.g., FLOAT, SHAKE).
     * @param image     The GreenfootImage to be displayed for the effect.
     * @param duration  The duration of the effect in acts.
     * @param speed     The speed at which the effect occurs.
     */
    public ForegroundEffect(int effect, GreenfootImage image, double duration, double speed) {
        super(effect, image, duration, speed);
    }

    /**
     * Acts on the foreground effect.
     */
    public void act() {
        super.act();
    }
}

