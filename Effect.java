import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class for animations/visual effects 
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class Effect extends SuperSmoothMover {

    public static final int PULSE = 0;
    public static final int ROCK = 1;
    public static final int SLIDE = 2;
    public static final int FADE = 3;
    public static final int FLOAT = 4;
    
    private GreenfootImage image;
    private int effect;
    private double index, deltaIndex;
    private int width, height;
    private int initX, initY;
    private double ratio;
    private double duration;
    private SimpleTimer actTimer;

    /**
     * Constructs an effect using an image file.
     * 
     * @param effect    The type of effect (e.g., PULSE, ROCK).
     * @param image     The image to be displayed for the effect.
     */
    public Effect(int effect, String image){
        this(effect, new GreenfootImage(image));
    }

    /**
     * Constructs an effect using a GreenfootImage.
     * 
     * @param effect    The type of effect (e.g., PULSE, ROCK).
     * @param image     The GreenfootImage to be displayed for the effect.
     */
    public Effect(int effect, GreenfootImage image) {
        this(effect, image, 10);
    }

    /**
     * Constructs an effect with a specified duration.
     * 
     * @param effect    The type of effect (e.g., PULSE, ROCK).
     * @param image     The GreenfootImage to be displayed for the effect.
     * @param duration  The duration of the effect in acts.
     */
    public Effect(int effect, GreenfootImage image, int duration) {
        this(effect, image, duration, 0.1);
    }

    /**
     * Constructs an effect with a specified duration and speed.
     * 
     * @param effect    The type of effect (e.g., PULSE, ROCK).
     * @param image     The GreenfootImage to be displayed for the effect.
     * @param duration  The duration of the effect in acts.
     * @param speed     The speed at which the effect occurs.
     */
    public Effect(int effect, GreenfootImage image, double duration, double speed) {
        actTimer = new SimpleTimer();
        
        this.duration = duration;
        this.image = image;
        this.effect = effect;
        
        width = image.getWidth();
        height = image.getHeight();
        ratio = 1.0 * width / height;
        
        setImage(image);
        index = 0;
        deltaIndex = speed;
    }

    /**
     * Called when the effect is added to the world.
     * Records the initial position of the effect.
     * 
     * @param w The world to which the effect is added.
     */
    public void addedToWorld(World w){
        initX = getX();
        initY = getY();
    }

    /**
     * Acts on the effect based on its type.
     */
    public void act()
    {
        if(actTimer.millisElapsed() >= 17){
            actTimer.mark();
        
            switch(effect){
                case PULSE:
                    pulse();
                    break;
                case ROCK:
                    rock();
                    break;    
                case SLIDE:
                    slide();
                    break;
                case FADE:
                    fade();
                    break;
                case FLOAT:
                    fly();
                    break;
            }
        }
    }

    /**
     * Makes the effect float by fading and moving upwards.
     */
    public void fly(){
        fade();
        enableStaticRotation();
        setRotation(270);
        move(deltaIndex);
    }

    /**
     * Fades the effect.
     * The effect disappears as its transparency decreases.
     */
    public void fade(){
        int newTransparency = image.getTransparency() - 1;
        index += deltaIndex;
        
        if(newTransparency <= 0 || index >= duration){
            getWorld().removeObject(this);
        }
        else{
            image.setTransparency(newTransparency);
        }
    }

    /**
     * Creates a pulsing effect by changing the size of the image.
     */
    public void pulse() {
        GreenfootImage scaled = new GreenfootImage(image);
        if(index <= duration){
            scaled.scale(width + (int) (index * ratio), height + (int) index);
        }
        if(index == 0){
            scaled.scale(width, height);
        }
        
        index += deltaIndex;
        if(index > duration || index < 0){
            deltaIndex *= -1;
        }
        setImage(scaled);
    }

    /**
     * Rocks the effect horizontally.
     */
    public void rock() {
        if (index <= duration) {
            setLocation(initX + (int) index, initY);
        }
        if (index == 0) {
            setLocation(initX, initY);
        }
        
        index += deltaIndex;
        if (index > duration || index < 0) {
            deltaIndex *= -1;
        }
    }
    
    /**
     * Moves the effect to the right until it disappears.
     */
    public void slide() {
        if (index <= duration) {
            enableStaticRotation();
            setRotation(0);
            move(deltaIndex);
            index += deltaIndex;
        } else {
            move(deltaIndex);
            getWorld().removeObject(this);
        }
    }
}
