import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class CoinChange {
    private static int minNum(int[] coins, int sum) {
        int[][] data = new int[coins.length + 1][sum + 1];
        Arrays.fill(data[0], Integer.MAX_VALUE / 2);
        data[0][0] = 0;
        for (int i = 1; i <= coins.length; i++) {
            int I = i - 1;
            System.arraycopy(data[I], 0, data[i], 0, coins[I]);
            for (int j = coins[I]; j <= sum; j++) {
                data[i][j] = Math.min(data[I][j], data[i][j - coins[I]] + 1);
            }
        }
        return data[coins.length][sum];
    }

    private static int minNum2(int[] coins, int sum) {
        int sum1 = sum + 1;
        int[] prev = new int[sum1];
        int MAX = Integer.MAX_VALUE / 2;
        Arrays.fill(prev, MAX);
        prev[0] = 0;
        for (int i = 1; i <= coins.length; i++) {
            int I = i - 1;
            int[] cur = new int[sum1];
            System.arraycopy(prev, 0, cur, 0, coins[I]);
            for (int j = coins[I]; j <= sum; j++) {
                cur[j] = Math.min(prev[j], cur[j - coins[I]] + 1);
            }
            prev = cur;
        }
        return prev[sum] != MAX ? prev[sum] : -1;
    }

    private static int numWays(int[] coins, int sum) {
        int[][] data = new int[coins.length + 1][sum + 1];
        data[0][0] = 1;
        for (int i = 1; i <= coins.length; i++) {
            int I = i - 1;
            if (coins[I] >= 0) System.arraycopy(data[i - 1], 0, data[i], 0, coins[I]);
            for (int j = coins[I]; j <= sum; j++) {
                data[i][j] = data[i][j - coins[I]] + data[i - 1][j];
            }
        }
        return data[coins.length][sum];
    }

    private static int numWaysPermutationsMod(int[] coins, int sum, int MOD) {
        int[] dp = new int[sum + 1];
        dp[0] = 1;
        for (int i = 1; i <= sum; i++) {
            long c = 0;
            for (int coin : coins) {
                if (i >= coin) {
                    c += dp[i - coin];
                }
            }
            dp[i] = (int) (c % MOD);
        }
        return dp[sum];
    }

    private static int numWaysCombinationsMod(int[] coins, int sum, int MOD) {
        int[] dp = new int[sum + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= sum; i++) {
                dp[i] = (dp[i] + dp[i - coin]) % MOD;
            }
        }
        return dp[sum];
    }

    private static int maxNum(int[] coins, int sum) {
        int[][] data = new int[coins.length + 1][sum + 1];
        Arrays.fill(data[0], -1);
        data[0][0] = 0;
        for (int i = 0; i < coins.length / 2; i++) {
            int temp = coins[i];
            coins[i] = coins[coins.length - i - 1];
            coins[coins.length - i - 1] = temp;
        }
        for (int i = 1; i <= coins.length; i++) {
            int I = i - 1;
            data[i] = data[I].clone();
            for (int j = coins[I]; j <= sum; j++) {
                if (data[i][j - coins[I]] != -1) {
                    data[i][j] = Math.max(data[i][j], data[i][j - coins[I]] + 1);
                }
            }
        }
        return data[coins.length][sum];
    }

    private static int minType(int[] coins, int sum) {
        ArrayList<ArrayList<HashSet<Integer>>> used = new ArrayList<>();
        for (int i = 0; i <= coins.length; i++) {
            ArrayList<HashSet<Integer>> temp = new ArrayList<>();
            for (int j = 0; j <= sum; j++) {
                temp.add(new HashSet<>());
            }
            used.add(temp);
        }
        int[][] data = new int[coins.length + 1][sum + 1];
        Arrays.fill(data[0], -1);
        data[0][0] = 0;
        for (int i = 1; i <= coins.length; i++) {
            int I = i - 1;
            data[i] = data[I].clone();
            ArrayList<HashSet<Integer>> copy = new ArrayList<>(used.get(I));
            used.set(i, copy);
            for (int j = coins[I]; j <= sum; j++) {
                if (data[i][j - coins[I]] != -1) {
                    int temp = data[i][j - coins[I]] + 1;
                    if (temp > data[i][j]) {
                        data[i][j] = temp;
                        HashSet<Integer> copy2 = new HashSet<>(used.get(i).get(j - coins[I]));
                        used.get(i).set(j, copy2);
                        used.get(i).get(j).add(coins[I]);
                    }
                }
            }
        }
        return used.get(coins.length).get(sum).size();
    }
}
