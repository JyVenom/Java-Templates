import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class ChessboardAndQueens {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        boolean[][] open = new boolean[8][8];
        for (int i = 0; i < 8; i++) {
            String s = br.readLine();
            for (int j = 0; j < 8; j++) {
                open[i][j] = (s.charAt(j) == '.');
            }
        }

        int[] taken = new int[8];
        Arrays.fill(taken, -1);
        int count = findAns(open, taken, 0, 0);

        pw.println(count);
        pw.close();
    }

    private static int findAns(boolean[][] open, int[] taken, int count, int rem) {
        if (rem == 8) {
            count++;
            return count;
        } else {
            for (int i = 0; i < 8; i++) {
                if (taken[i] == -1) {
                    if (open[rem][i]) {
                        taken[i] = rem;
                        boolean good = true;
                        for (int j = 0; j < 8; j++) {
                            if (j == i) {
                                continue;
                            }
                            if (taken[j] != -1) {
                                if (Math.abs(taken[j] - taken[i]) == Math.abs(j - i)) {
                                    good = false;
                                }
                            }
                        }
                        if (good) {
                            count = findAns(open, taken, count, rem + 1);
                        }
                        taken[i] = -1;
                    }
                }
            }
        }
        return count;
    }
}
