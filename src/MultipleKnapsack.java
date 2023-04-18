import java.util.Iterator;
import java.util.LinkedList;

public class MultipleKnapsack {
    public static void main(String[] args) {
        //Setting up knapsacks and items.
        MultipleKnapsack2 knapsacks = new MultipleKnapsack2();
        knapsacks.addKnapsack(new Knapsack(20, "s1"));
        knapsacks.addKnapsack(new Knapsack(15, "s2"));
        knapsacks.addKnapsack(new Knapsack(12, "s3"));
        knapsacks.addKnapsack(new Knapsack(5, "s4"));

        LinkedList<KnapsackItem> items = new LinkedList<>();
        items.add(new KnapsackItem(5, 10, "i1"));
        items.add(new KnapsackItem(10, 5, "i2"));
        items.add(new KnapsackItem(5, 2, "i3"));
        items.add(new KnapsackItem(7, 17, "i4"));
        items.add(new KnapsackItem(15, 8, "i5"));
        items.add(new KnapsackItem(20, 30, "i6"));
        items.add(new KnapsackItem(15, 8, "i7"));
        items.add(new KnapsackItem(20, 30, "i8"));


        knapsacks.greedyMultipleKnapsack2(items);
        knapsacks.calculateValue();
        MultipleKnapsack2 result = knapsacks.neighborSearch(knapsacks);
        result.printResult();
    }


    public static class MultipleKnapsack2 {

        private LinkedList<Knapsack> knapsacks;
        private LinkedList<KnapsackItem> items;
        private int value;

        /**
         * Constructor that instantiates necessary objects
         */
        public MultipleKnapsack2() {
            knapsacks = new LinkedList<>();
            items = new LinkedList<>();
        }

        /**
         * Method that gets all neighbors a current solution to the Multiple Knapsack Problem has
         */
        public LinkedList<MultipleKnapsack2> getNeighbors(LinkedList<Knapsack> knapsacks, LinkedList<KnapsackItem> items) {
            LinkedList<MultipleKnapsack2> knapsackNeighbors = new LinkedList<>();

            for (int gKnapsack = 0; gKnapsack < knapsacks.size(); gKnapsack++) {
                for (int gItem = 0; gItem < knapsacks.get(gKnapsack).getItems().size(); gItem++) {
                    for (int lKnapsack = 0; lKnapsack < knapsacks.size(); lKnapsack++) {
                        for (int lItem = 0; lItem < knapsacks.get(lKnapsack).getItems().size(); lItem++) {
                            Knapsack globalKnapsack = knapsacks.get(gKnapsack);
                            Knapsack localKnapsack = knapsacks.get(lKnapsack);

                            if (!globalKnapsack.equals(localKnapsack)) {
                                LinkedList<KnapsackItem> globalItems = globalKnapsack.getItems();
                                LinkedList<KnapsackItem> localItems = localKnapsack.getItems();
                                if (globalItems.get(gItem).getWeight() <= localItems.get(lItem).getWeight() + localKnapsack.getCap()) {

                                    MultipleKnapsack2 neighbor = new MultipleKnapsack2();
                                    LinkedList<Knapsack> currentKnapsack = new LinkedList<>();
                                    LinkedList<KnapsackItem> currentItems = new LinkedList<>(items);
                                    for (Knapsack knapsack : knapsacks) {
                                        if (knapsack.equals(localKnapsack)) {
                                            Knapsack local = new Knapsack(knapsack);
                                            local.setCap(localKnapsack.getCap() + localItems.get(lItem).getWeight() - globalItems.get(gItem).getWeight());
                                            local.getItems().set(lItem, globalItems.get(gItem));
                                            currentKnapsack.add(local);
                                        } else if (knapsack.equals(globalKnapsack)) {
                                            Knapsack global = new Knapsack(knapsack);
                                            global.setCap(global.getCap() + global.getItems().get(gItem).getWeight());
                                            global.getItems().remove(gItem);
                                            currentKnapsack.add(global);
                                        } else {
                                            currentKnapsack.add(new Knapsack(knapsack));
                                        }
                                    }

                                    neighbor.setKnapsacks(currentKnapsack);
                                    neighbor.setItems(currentItems);
                                    neighbor.shuffleItemsInKnapsacks();
                                    neighbor.greedyMultipleKnapsack2(currentItems);
                                    neighbor.calculateValue();
                                    knapsackNeighbors.add(neighbor);
                                }
                            }
                        }
                    }
                }
            }

            return knapsackNeighbors;
        }

        /**
         * Method that tries to find neighbors for a solution that have a better outcome than the solution itself
         */
        public MultipleKnapsack2 neighborSearch(MultipleKnapsack2 knapsacks) {
            LinkedList<MultipleKnapsack2> neighbors = getNeighbors(knapsacks.getKnapsacks(), knapsacks.getItems());
            for (MultipleKnapsack2 neighbor : neighbors) {
                if (neighbor.getValue() > knapsacks.getValue()) {
                    knapsacks = neighborSearch(neighbor);
                }
            }

            return knapsacks;
        }

        /**
         * Method that shuffles or packs the items so that there's space for other items to be added.
         */
        public void shuffleItemsInKnapsacks() {
            LinkedList<KnapsackItem> itemsInKnapsacks = new LinkedList<>();
            for (Knapsack knapsack : knapsacks) {
                itemsInKnapsacks.addAll(knapsack.getItems());
            }

            itemsInKnapsacks.sort((i1, i2) -> Integer.compare(i2.getWeight(), i1.getWeight()));

            for (Knapsack knapsack : knapsacks) {
                knapsack.getItems().clear();
                knapsack.resetCap();
                for (Iterator<KnapsackItem> it = itemsInKnapsacks.iterator(); it.hasNext(); ) {
                    KnapsackItem item = it.next();
                    if (item.getWeight() <= knapsack.getCap()) {
                        knapsack.addItem(item);
                        it.remove();
                    }
                }
            }
        }

        /**
         * Method that solves the Multiple Knapsack Problem by a greedy approach
         */
        public void greedyMultipleKnapsack2(LinkedList<KnapsackItem> items) {

            items.sort((i1, i2) -> Double.compare(i2.getValueByWeight(), i1.getValueByWeight()));

            Knapsack bestKnapsack;
            double bestWeightDifference;
            double currentWeightDifference;

            for (KnapsackItem item : items) {
                if (!this.items.contains(item)) {
                    this.items.add(item);
                }
                bestWeightDifference = Integer.MAX_VALUE;
                bestKnapsack = null;
                for (Knapsack knapsack : knapsacks) {
                    if (knapsack.getCap() >= item.getWeight()) {
                        currentWeightDifference = knapsack.getCap() - item.getWeight();
                        if (currentWeightDifference < bestWeightDifference && currentWeightDifference > 0) {
                            bestWeightDifference = currentWeightDifference;
                            bestKnapsack = knapsack;
                        }
                    }
                }
                if (bestKnapsack != null) {
                    bestKnapsack.addItem(item);
                    this.items.remove(item);
                }
            }
        }

        /**
         * Method that calculates a MultipleKnapsack2's total value
         */
        public void calculateValue() {
            int value = 0;

            for (Knapsack knapsack : knapsacks) {
                for (KnapsackItem item : knapsack.getItems()) {
                    value += item.getValue();
                }
            }

            this.value = value;
        }

        /**
         * Method that prints out the result of a MultipleKnapsack2
         */
        public void printResult() {
            for (Knapsack knapsack : knapsacks) {
                System.out.println("Knapsack\n" + "Name: " + knapsack.getName()
                        + "\nStart weight: " + knapsack.getStartWeight() + "\nWeight-Cap: " + knapsack.getCap() + "\n");
                for (KnapsackItem item : knapsack.getItems()) {
                    System.out.println("Item\n" + "Name: " + item.getName()
                            + "\nValue: " + item.getValue() + "\nWeight: " + item.getWeight());
                }
                System.out.println("---------------------------");
            }

            System.out.println("Total value: " + value);
        }

        /**
         * Method that gets the total value of a MultipleKnapsack2
         */
        public int getValue() {
            return value;
        }

        /**
         * Method that gets all of the knapsacks in the MultipleKnapsack2
         */
        public LinkedList<Knapsack> getKnapsacks() {
            return knapsacks;
        }

        /**
         * Method that sets all of the knapsacks
         */
        public void setKnapsacks(LinkedList<Knapsack> knapsacks) {
            this.knapsacks = knapsacks;
        }

        /**
         * Method that gets all of the items that are not in a knapsack already
         */
        public LinkedList<KnapsackItem> getItems() {
            return items;
        }

        /**
         * Method that sets the items that are not in a knapsack already
         */
        public void setItems(LinkedList<KnapsackItem> items) {
            this.items = items;
        }

        /**
         * Method that adds a knapsack into the MultipleKnapsack2
         */
        public void addKnapsack(Knapsack knapsack) {
            knapsacks.add(knapsack);
        }

    }

    public static class Knapsack {

        private final int startWeight;
        private final String name;
        private final LinkedList<KnapsackItem> items;
        private int cap;

        /**
         * Constructor that creates a new knapsack with a cap, name and a startWeight value
         */
        public Knapsack(int cap, String name) {
            this.cap = cap;
            this.name = name;
            this.startWeight = cap;
            items = new LinkedList<>();
        }

        /**
         * Copy constructor which copies a knapsack object and creates a new identical one
         */
        public Knapsack(Knapsack knapsack) {
            this.cap = knapsack.getCap();
            this.startWeight = knapsack.getStartWeight();
            this.name = knapsack.getName();
            this.items = new LinkedList<>(knapsack.getItems());
        }

        /**
         * Adds an item into the item-list and updates the cap so it's up to date
         */
        public void addItem(KnapsackItem item) {
            if (item != null) {
                items.add(item);
                cap = cap - item.getWeight();
            }
        }

        /**
         * States the cap to the initial value of the knapsack.
         */
        public void resetCap() {
            cap = startWeight;
        }

        /**
         * Method that returns the knapsack's startWeight
         */
        public int getStartWeight() {
            return startWeight;
        }

        /**
         * Method that returns the knapsack's cap
         */
        public int getCap() {
            return cap;
        }

        /**
         * Sets the cap to the value provided to the method
         */
        public void setCap(int cap) {
            this.cap = cap;
        }

        /**
         * Method that returns the knapsack's name
         */
        public String getName() {
            return name;
        }

        /**
         * Method that returns the items the knapsack is currently holding
         */
        public LinkedList<KnapsackItem> getItems() {
            return items;
        }
    }

    public static class KnapsackItem {

        private final int weight;
        private final int value;
        private final double valueByWeight;
        private final String name;

        /**
         * Constructor that instantiates weight, value and name for an item.
         */
        public KnapsackItem(int weight, int value, String name) {
            this.weight = weight;
            this.value = value;
            valueByWeight = (double) value / (double) weight;
            this.name = name;
        }

        /**
         * Method that gets the value / weight value from an item
         */
        public double getValueByWeight() {
            return valueByWeight;
        }

        /**
         * Method that returns the weight an item has
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Method that gets the value an item has
         */
        public int getValue() {
            return value;
        }

        /**
         * Method that sets the name of an item
         */
        public String getName() {
            return name;
        }
    }
}
