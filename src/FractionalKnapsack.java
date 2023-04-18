import java.util.Arrays;

public class FractionalKnapsack {
    // function to get maximum value 
    private static double getMaxValue(int[] wt, int[] val, int capacity) {
        item[] items = new item[wt.length];

        for (int i = 0; i < wt.length; i++) {
            items[i] = new item(wt[i], val[i], i);
        }

        // sorting items by value; 
        Arrays.sort(items, (o1, o2) -> o2.cost.compareTo(o1.cost));

        double totalValue = 0d;

        for (item i : items) {
            int curWt = (int) i.wt;
            int curVal = (int) i.val;

            if (capacity - curWt >= 0) {
                // this weight can be picked while 
                capacity = capacity - curWt;
                totalValue += curVal;
            } else {
                // item cant be picked whole 
                double fraction = ((double) capacity / (double) curWt);
                totalValue += (curVal * fraction);
                break;
            }
        }

        return totalValue;
    }

    // Driver code 
    public static void main(String[] args) {
        int[] wt = {10, 40, 20, 30};
        int[] val = {60, 40, 100, 120};
        int capacity = 50;

        double maxValue = getMaxValue(wt, val, capacity);

        // Function call 
        System.out.println("Maximum value we can obtain = " + maxValue);
    }

    // item value class 
    static class item {
        Double cost;
        double wt, val, ind;

        // item value function 
        public item(int wt, int val, int ind) {
            this.wt = wt;
            this.val = val;
            this.ind = ind;
            cost = (double) val / (double) wt;
        }
    }
}
