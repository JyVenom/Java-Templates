import java.util.Arrays;

public class NXN_MatrixMultiplication {
    public static void main(String[] args) {
        long[][] a = {{2, 1}, {1, 0}};
        long[][] b = {{1, 2}, {2, 0}};

        System.out.println(Arrays.deepToString(matrixMult(a, b)));
    }

    private static long[][] matrixMult(long[][] a, long[][] b) {
        int n = a.length;
        long[][] ans = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    ans[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return ans;
    }
}
