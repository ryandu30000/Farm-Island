/**
 * Represents a node in a grid pathfinding system.
 * Each node has information about its position, cost, parent node, and whether it is closed.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */

public class GridPath  
{

    private int gCost, hCost, totalCost;
    private int row,col;
    private GridPath parent;
    private boolean closed;
    public GridPath(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public GridPath getParent() {
        return parent;
    }

    public void setParent(GridPath parent) {
        this.parent = parent;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}

