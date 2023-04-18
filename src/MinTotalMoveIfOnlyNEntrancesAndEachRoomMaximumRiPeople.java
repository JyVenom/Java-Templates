import java.io.*;
import java.util.StringTokenizer;

public class MinTotalMoveIfOnlyNEntrancesAndEachRoomMaximumRiPeople {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] r = new int[n];
        for (int i = 0; i < n; i++) {
            r[i] = Integer.parseInt(br.readLine());
        }

        long min = findMinMove(r, n, k);

        pw.println(min);
        pw.close();
    }

    private static long findMinMove(int[] r, int n, int k) { //n rooms, k entrances, room i can hold r[i] cows
        long min = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) { //start
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) { //shift by i
                arr[j] = r[(j + i) % n];
            }

            long[][] dp = new long[k][n]; //init dp and 1 door case (dp[x][y] means use x + 1 doors and y + 1 rooms
            for (int j = 1; j < n; j++) {
                dp[0][j] = dp[0][j - 1] + ((long) j) * ((long) arr[j]);
            }

            for (int j = 1; j < k; j++) { //use j doors (starts at 1 because already processed 0)
                for (int l = j + 1; l < n; l++) { //go up to l doors (dp)
                    dp[j][l] = dp[j - 1][l - 1];
                    long cows = arr[l];
                    long sum = cows;
                    for (int m = l - 1; m > 0; m--) { //put the door at m instead of at l
                        dp[j][l] = Math.min(dp[j][l], dp[j - 1][m - 1] + sum);
                        cows += arr[m];
                        sum += cows;
                    }
                }
            }

            min = Math.min(min, dp[k - 1][n - 1]);
        }

        return min;
    }
}
