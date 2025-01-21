import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Slilder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slider{  
        public static final int CONSUMER_BAR = 0;
        public static final int MANA_BAR = 1;
        public static final int ENERGY_BAR = 2;
        public static final int SETTING_BAR = 3;
        private boolean hideAtMax;
        
        private int maxVal;
        private int currVal;
        private double scale;
        private int width;
        private int height;
        private int borderThickness;
        private Color borderColor;
        private Color fillColor;
        private Color emptyColor;
        public Slider(int width, int height, int maxVal, int preset){
            this(width, height, maxVal);
            switch(preset){
                case CONSUMER_BAR:
                    fillColor = new Color(219,246,235,255); //light blue                
                    borderThickness = 3;
                    break;
                case MANA_BAR:
                    fillColor = Color.BLUE;                                      
                    borderThickness = 1;
                    break;
                default:
                    
                    break;
            }    
        }
     
        public Slider(int width, int height, int maxVal){
            borderThickness = 0;
            this.width = width;
            this.height = height;
            //currVal is set default to max
            this.maxVal = maxVal;
            this.currVal = maxVal;
            
            scale = (double) this.width/this.maxVal;
            
            borderColor = Color.BLACK;
            fillColor = Color.GREEN;
            emptyColor = new Color(88,160,204,255);//dark blue
        }
        
        public Slider makeDeepCopy(){
            Slider newSlider= new Slider(this.width, this.height, this.maxVal);
            newSlider.setBorderThickness(this.borderThickness);
            
            newSlider.setBorderColor(this.borderColor);
            newSlider.setFillColor(this.fillColor);
            newSlider.setEmptyColor(this.emptyColor);
            
            
            return newSlider;
        }
        
        public boolean isFull(){
            //if currVal is equal to maxVal return true
            return (currVal == maxVal);
        }
        
        public boolean isHidden(){
            return hideAtMax;
        }
        public void hideAtMax(){
            hideAtMax = true;
        }
        public void showAtMax(){
            hideAtMax = false;
        }
        //setters and getters!
        public void setMaxVal (int maxVal){
            this.maxVal = maxVal;
        }
        public void setCurrVal (int currVal){
            this.currVal = currVal;
        }
        public void setBorderThickness (int borderThickness){
            this.borderThickness = borderThickness;
        }
        public void setBorderColor (Color borderColor){
            this.borderColor = borderColor;
        }
        public void setFillColor (Color fillColor){
            this.fillColor = fillColor;
        }
        public void setEmptyColor (Color emptyColor){
            this.emptyColor = emptyColor;
        }
        public void changeDimensions (int width, int height){
            this.width = width;
            this.height = height;
        }
        
        
        public int getMaxVal (){
            return maxVal;
        }
        public int getCurrVal (){
            return currVal;
        }
        public double getScale(){
            return scale;
        }
        public int getBorderThickness (){
            return borderThickness;
        }
        public Color getBorderColor (){
            return borderColor;
        }
        public Color getFillColor (){
            return fillColor;
        }
        public Color getEmptyColor (){
            return emptyColor;
        }
        public int getWidth (){
            return width;
        }
        public int getHeight(){
            return height;
        }
        //find percent fill
        public double getPercentFill(){
            return (double) currVal/maxVal;
        }
    }
