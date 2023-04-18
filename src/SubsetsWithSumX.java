import java.io.IOException;

public class SubsetsWithSumX {
    private static long[] temp;

    static long[] build(int[] arr, int l, int r) {
        long[] res = new long[1 << r - l];
        for (int i = 1; l < r; l++, i <<= 1) {
            int cur = arr[l];
            for (int j = 0; j < i; j++) {
                temp[j] = res[j];
                temp[i + j] = res[j] + cur;
            }
            int p = 0, q = i, q_ = i + i, h = 0;
            while (p < i && q < q_) {
                if (temp[p] < temp[q]) {
                    res[h++] = temp[p++];
                } else {
                    res[h++] = temp[q++];
                }
            }
            while (p < i) {
                res[h++] = temp[p++];
            }
            while (q < q_) {
                res[h++] = temp[q++];
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {

        int n = 4;
        int x = 5;
        int[] arr = new int[n];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;
        arr[3] = 2;

        int k0 = n / 2, k1 = n - k0, n0 = 1 << k0, n1 = 1 << k1;
        temp = new long[n1];
        long[] a0 = build(arr, 0, k0);
        long[] a1 = build(arr, k0, n);
        long ans = 0;
        for (int i = 0, p = n1 - 1, q = p; i < n0; i++) {
            long y = 0;
            while (q >= 0 && (y = a0[i] + a1[q]) > x) {
                q--;
            }
            if (q < 0) {
                break;
            }
            if (y == x) {
                if (p > q) {
                    p = q;
                }
                while (p > 0 && a1[p - 1] == a1[q]) {
                    p--;
                }
                ans += q - p + 1;
            }
        }

        System.out.println(ans);
    }
}
