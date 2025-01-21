import java.util.Comparator;

/**
 * Custom comparator for comparing GridPath objects based on their total cost.
 * Used in priority queues or sorting algorithms to prioritize nodes with lower total cost.
 * 
 * @author: Zhaoqi Xu
 * @version: January 2024
 */
public class GridComparator implements Comparator<GridPath> {

    /**
     * Compares two GridPath objects based on their total cost.
     * If the total cost is equal, compares based on the heuristic cost.
     * 
     * @param g1 The first GridPath object to compare.
     * @param g2 The second GridPath object to compare.
     * @return A negative integer if g1 has a lower total cost, a positive integer if g2 has a lower total cost,
     *         or zero if their total costs are equal.
     */
    public int compare(GridPath g1, GridPath g2) {
        if(g1.getTotalCost() != g2.getGCost()){
            return Integer.compare(g1.getTotalCost(), g2.getTotalCost());
        } else {
            return Integer.compare(g1.getHCost(), g2.getHCost());
        }
    }
}
