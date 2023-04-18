import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class SplitSetIntoTwoSubsetsWithMinimalDifferenceOfSums {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        int[] weights = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        long ans = Long.MAX_VALUE;
        for (int mask = 0; mask < (1 << n); mask++) {
            long s1 = 0;
            long s2 = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) > 0) {
                    s1 += weights[i];
                } else {
                    s2 += weights[i];
                }
            }
            ans = Math.min(ans, Math.abs(s1 - s2));
        }

        pw.println(ans);
        pw.close();
    }
}
