import java.io.*;
import java.util.StringTokenizer;

/**
 * have 2 sets of points and need to minimize total cost (cost to move from point a to b is their distance squared)
 * start on a1 (first point in first set) and send on ax (last point of first set).
 * need to visit each exactly once in order (doesn't have to be a1a2a3...axb1b2b3...by, can be a1b1b2a2a3a4b4...ax)
 * in other words, the final path is set a and b interwoven together (such that removing all points from set b from the
 * final sequence will result in set a exactly (with the same points and in the same order) and vice versa)
 *
 * if going from point a and b passes through c and c is already visited, the cost is still dist from a to be squared,
 * not dist from a to c squared plus dist from b to c squared
 * if c not yet visited, then can do dist from a to c squared plus dist from b to c squared, but must add c to final
 * path, so if doing so causes points to not be visited in order, then can not do so
 *
 * @author jerry
 * @version 1.0
 * @since 2020-11-27
 */
public class MinCostToJoinTwoSetsKeepingOrder {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int G = Integer.parseInt(st.nextToken());
        long[][] h = new long[H][2];
        long[][] g = new long[G][2];
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());
            h[i][0] = Integer.parseInt(st.nextToken());
            h[i][1] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < G; i++) {
            st = new StringTokenizer(br.readLine());
            g[i][0] = Integer.parseInt(st.nextToken());
            g[i][1] = Integer.parseInt(st.nextToken());
        }

        long[][][] dp = new long[H + 1][G + 1][2]; //0 for h, 1 for g
        long half = Long.MAX_VALUE / 2;
        for (int i = 0; i <= H; i++) {
            for (int j = 0; j <= G; j++) {
                dp[i][j][0] = half;
                dp[i][j][1] = half;
            }
        }
        dp[1][0][0] = 0;
        dp[1][1][1] = findDist(h[0], g[0]);
        for (int i = 2; i <= G; i++) {
            int I = i - 1;
            dp[1][i][1] = dp[1][I][1] + findDist(g[i - 2], g[I]);
        }
        dp[2][1][0] = dp[1][1][1] + findDist(g[0], h[1]);
        for (int i = 2; i <= H; i++) {
            int I = i - 1;
            int I2 = i - 2;
            dp[i][0][0] = dp[I][0][0] + findDist(h[I2], h[I]);
            if (i > 2) {
                dp[i][1][0] = Math.min(dp[I][1][0] + findDist(h[I2], h[I]), dp[I][1][1] + findDist(g[0], h[I]));
            }
            dp[i][1][1] = dp[i][0][0] + findDist(h[I], g[0]);
            for (int j = 2; j <= G; j++) {
                int J = j - 1;
                //add a h cow
                dp[i][j][0] = Math.min(dp[I][j][0] + findDist(h[I2], h[I]), dp[I][j][1] + findDist(g[J], h[I]));
                //add a g cow
                dp[i][j][1] = Math.min(dp[i][J][0] + findDist(h[I], g[J]), dp[i][J][1] + findDist(g[j - 2], g[j - 1]));
            }
        }

        pw.println(dp[H][G][0]);
        pw.close();
    }

    private static long findDist(long[] from, long[] to) {
        long x = to[0] - from[0];
        long y = to[1] - from[1];
        return (x * x) + (y * y);
    }
}
