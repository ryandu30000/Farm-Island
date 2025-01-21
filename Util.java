import greenfoot.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;


/**
 * Modular utilities
 * 
 * @author Jordan Cohen 
 * @version 1.0
 */
public class Util
{
    
    /**
     * A z-sort method which will sort Actors so that Actors that are
     * displayed "higher" on the screen (lower y values) will show up underneath
     * Actors that are drawn "lower" on the screen (higher y values), creating a
     * better perspective. 
     */
    public static void zSort (ArrayList<Actor> actorsToSort, World world){
        ArrayList<ActorContent> acList = new ArrayList<ActorContent>();
        for (Actor a : actorsToSort){
            if(a.getWorld() != null)
                acList.add (new ActorContent (a, a.getX(), a.getY()));
        }    
        Collections.sort(acList);
        for (ActorContent a : acList){
            Actor actor  = a.getActor();
            world.removeObject(actor);
            world.addObject(actor, a.getX(), a.getY());
        }
    }
}

/**
 * Container to hold and Actor and an LOCAL position (position x, y on the WINDOW's Canvas, 0,0 in top left)
 */
class ActorContent implements Comparable <ActorContent> {
    private Actor actor;
    private int xx, yy;
    public ActorContent(Actor actor, int xx, int yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (int x, int y){
        xx = x;
        yy = y;
    }

    public int getX() {
        return xx;
    }

    public int getY() {
        return yy;
    }

    public Actor getActor(){
        return actor;
    }

    public String toString () {
        return "Actor: " + actor + " at " + xx + ", " + yy;
    }

    public int compareTo (ActorContent a){
        return this.getY() - a.getY();
    }

}
