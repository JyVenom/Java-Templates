import java.util.ArrayList;

public class StateWithRem {
    private static int N = 100;

    private static class state {
        int row, col, rem;

        private state(int row, int col, int rem) {
            this.row = row;
            this.col = col;
            this.rem = rem;
        }

        private ArrayList<state> genNext() {
            ArrayList<state> temp = new ArrayList<>();

            int rem2;
            if (rem == 0) {
                rem2 = 2;
            } else {
                rem2 = rem - 1;
            }
            if (row > 0) {
                temp.add(new state(row - 1, col, rem2));
            }
            if (col < N) {
                temp.add(new state(row, col + 1, rem2));
            }
            if (row < N) {
                temp.add(new state(row + 1, col, rem2));
            }
            if (col > 0) {
                temp.add(new state(row, col - 1, rem2));
            }

            return temp;
        }
    }
}
