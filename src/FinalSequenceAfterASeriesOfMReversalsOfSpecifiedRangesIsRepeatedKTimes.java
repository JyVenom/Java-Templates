import java.io.*;
import java.util.StringTokenizer;

public class FinalSequenceAfterASeriesOfMReversalsOfSpecifiedRangesIsRepeatedKTimes {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //get data
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] moves = new int[m][2];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            moves[i][0] = Integer.parseInt(st.nextToken()) - 1;
            moves[i][1] = Integer.parseInt(st.nextToken()) - 1;
        }

        //simulate for one round (for k = 1)
        int[] order = new int[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        int[] res = order.clone();
        for (int[] move : moves) {
            int half = (move[0] + move[1]) / 2;
            for (int i = move[0], i1 = move[1]; i <= half; i++, i1--) {
                int temp = order[i1];
                order[i1] = order[i];
                order[i] = temp;
            }
        }
        //now simulate for remaining k - 1 rounds using binary exponentiation
        while (k > 0) {
            if (k % 2 == 1) {
                int[] temp = new int[n];
                for (int i = 0; i < n; i++) {
                    temp[i] = res[order[i]];
                }
                res = temp;
            }

            k = k / 2;
            if (k > 0) {
                int[] temp = new int[n];
                for (int i = 0; i < n; i++) {
                    temp[i] = order[order[i]];
                }
                order = temp;
            }
        }

        //print result
        for (int cur : res) {
            pw.println(cur + 1);
        }
        pw.close();
    }
}
