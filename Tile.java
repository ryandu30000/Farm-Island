import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends SuperSmoothMover
{
    //offset used when a tile is clicked
    public static int SELECT_OFFSET = -16;
    protected int tileYOffset;

    //Constants    
    public static final int WIDTH = 128;
    public static final int HEIGHT = 64;
    public static final int MARGIN = 128;

    //Constants to use when fading
    public static final int OPAQUE = 255;

    //used as a threshold of when tiles still have functionality
    //any more faded than "TRANSLUCENT" the tile will cease to function
    public static final int TRANSLUCENT = 50;
    public static final int TRANSPARENT = 0;

    //distance a tile has to travel before becomeing Trasnparent
    public static final double FADE_DISTANCE = 100;
    //radius if you want to fade in a circle/oval
    public static final double FADE_RADIUS = 300;

    //direction of neighbors
    public static final int[][] directionDistances = {{64,32},{-64,-32},{-64,32},{64,-32}};
    public static final int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

    //object information
    protected int row;
    protected int col;
    protected LandPlot myPlot;
    
    /**
     * basic constructor with offset
     * @param offset the offset of the tile
     */
    public Tile(int offset){
        tileYOffset = offset;
    }
    
    /**
     * Act - do whatever the Tile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        GameWorld myWorld = (GameWorld) getWorld();
        
        if(myWorld.isScreen(myWorld.GAME)){
            checkMouseAction();
            checkKeypressAction();
        }
        
    }

    /**
     * Adjust the transparancy of tiles based on thier distance from the center
     * based on an elipse
     */
    public void fadeOval(GreenfootImage image){
        if(this.getWorld() == null){
            return;        
        }
        //change WX and HX to adjust the elipse 
        //WX will adjust the elipse width
        //HX will adjust the elipse hieght
        double WX = 0.4; // "2/5"
        double HX = 2; 
        double deltaX = getX() - myPlot.getX();
        double deltaY = getY() - myPlot.getY() + tileYOffset;
        double distance = Math.sqrt(WX * (deltaX) * (deltaX) + HX * (deltaY) * (deltaY));

        if(distance >= FADE_RADIUS && distance <= FADE_RADIUS + FADE_DISTANCE){
            double delta = FADE_RADIUS + FADE_DISTANCE - distance;
            image.setTransparency((int)(255 * delta/FADE_DISTANCE));
        }
        else if(distance > FADE_RADIUS + FADE_DISTANCE){
            image.setTransparency(TRANSPARENT);
        }
        else {
            image.setTransparency(OPAQUE);
        }

    }
    /**
     * adjust the transparancy of tiles based on thier distance from the center
     * 
     */
    public void fadeRect(GreenfootImage image){
        if(this.getWorld() == null){
            return;
        }    
        //distance X and Y from the edge of the plot
        int plotX = myPlot.getX();
        int plotY = myPlot.getY();
        //64 is an offset to make the square fade earlier
        int plotWidth = myPlot.WIDTH/2 - 64;
        int plotHeight = myPlot.HEIGHT/2 - 64;

        int deltaX = 0, deltaY = 0;

        if(getX() >= plotX + plotWidth || getX() <= plotX - plotWidth){
            deltaX = Math.abs(plotX- getX()) - plotWidth;
        }
        if(getY() >= plotY + plotHeight || getY() <= plotY - plotHeight){
            deltaY = Math.abs(plotY - getY()) - plotHeight;
        }

        //calculate dfistance away from the plot
        int distance = (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if(distance > 0){
            image.setTransparency(OPAQUE - (int) (OPAQUE * Math.min(distance, FADE_DISTANCE)/FADE_DISTANCE));
        }else{
            image.setTransparency(OPAQUE);
        }

    }

    /**
     * handles all mouse input
     */
    public abstract void checkMouseAction();

    /**
     * handles all keyboard input
     */
    public abstract void checkKeypressAction();
    /**
     * plays a sound when tile is placed
     */
    public abstract void playPlaceSound();
    /**
     * plays a sound when tile is removed
     */
    public abstract void playRemoveSound();

    /**
     * Checks if the mouse is hovering exactly on the issometic square
     * @return boolean if the mouse is hovering
     */
    public boolean hoveringThis(){
        MouseInfo mouse = Cursor.getMouseInfo();
        //math to make sure it is just in isometric square
        /**
         * NEW: added Y_OFFSET
         */
        if(mouse != null){
            int yOffset = mouse.getY() - (getY() + tileYOffset);
            int leftBound = getX() - WIDTH/2 + 2 * yOffset;
            int rightBound = getX() + WIDTH/2 - 2 * yOffset;
            if(yOffset > 0){
                leftBound = getX() - WIDTH/2 + 2 * yOffset;
                rightBound = getX() + WIDTH/2 - 2 * yOffset;
            }
            else{
                leftBound = getX() - WIDTH/2 - 2 * yOffset;
                rightBound = getX() + WIDTH/2 + 2 * yOffset;
            }

            int topBound = getY() - HEIGHT/2 + tileYOffset ;
            int bottomBound = getY() + HEIGHT/2 + tileYOffset;
            return mouse.getX() > leftBound && mouse.getX() < rightBound && mouse.getY() < bottomBound && mouse.getY() > topBound;

        }
        return false;
    }
    /**
     * gets the tiles y offset
     * @return int the y offset
     */
    public int getTileYOffset(){
        return tileYOffset;
    }
    /**
     * sets the tiles y offset
     * @param offset the offset of the tile
     */
    public void setTileYOffset(int offset){
        tileYOffset = offset;
    }
}
