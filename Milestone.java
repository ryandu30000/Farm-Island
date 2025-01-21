/**
 * Write a description of class Milestone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Milestone  
{
    private ObjectID ID;
    private int amount;
    public Milestone(ObjectID ID, int amount){
        this.ID = ID;
        this.amount = amount;
    }
    public int getAmount(){
        return amount;
    }
    public ObjectID getID(){
        return ID;
    }
}
