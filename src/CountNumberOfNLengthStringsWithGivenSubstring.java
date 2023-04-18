import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CountNumberOfNLengthStringsWithGivenSubstring {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());
        char[] cc = br.readLine().toCharArray();

        int m = cc.length;
        int[] numMatch = new int[m];
        numMatch[0] = -1;
        for (int j = 1; j < m; j++) {
            int i = numMatch[j - 1] + 1;
            while (cc[i] != cc[j]) {
                if (i == 0) {
                    i--;
                    break;
                }
                i = numMatch[i - 1] + 1;
            }
            numMatch[j] = i;
        }
        int[][] delta = new int[m][26];
        for (int j = 0; j < m; j++)
            for (int c = 0; c < 26; c++) {
                int i = j;
                while (cc[i] != c + 'A') {
                    if (i == 0) {
                        i--;
                        break;
                    }
                    i = numMatch[i - 1] + 1;
                }
                delta[j][c] = i + 1;
            }
        int[][] dp = new int[n + 1][m + 1];
        dp[0][0] = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                int x = dp[i][j];
                if (x == 0)
                    continue;
                for (int c = 0; c < 26; c++) {
                    int j_ = delta[j][c];
                    dp[i + 1][j_] = (dp[i + 1][j_] + x) % 1000000007;
                }
            }
        long ans = 0, q = 1;
        for (int i = n; i >= m; i--) {
            ans = (ans + dp[i][m] * q) % 1000000007;
            q = q * 26 % 1000000007;
        }

        pw.println(ans);
        pw.close();
    }
}
