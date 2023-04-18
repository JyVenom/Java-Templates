import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class CountNumberOfTimesAPatternIsPresentInAString {
    private static byte[] bytes;
    private static int bufferPointer;

    private static byte[] scan() {
        int n = bytes.length;
        while (bufferPointer < n && bytes[bufferPointer] < 33) bufferPointer++;
        int i = bufferPointer;
        while (bufferPointer < n && bytes[bufferPointer] > 32) bufferPointer++;
        return Arrays.copyOfRange(bytes, i, bufferPointer);
    }

    public static void main(String[] args) {
        PrintWriter pw = new PrintWriter(System.out);

        try {
            bytes = System.in.readAllBytes();
        } catch (IOException ignored) {
        }
        byte[] s = scan();
        byte[] pat = scan();

        int n = s.length;
        int m = pat.length;
        int m1 = m + 1;
        byte[] all = Arrays.copyOf(pat, n + m1);
        System.arraycopy(s, 0, all, m1, n);
        int[] locs = new int[n + m1];
        locs[0] = -1;
        int cnt = 0;
        for (int j = 1; j < m1 + n; j++) {
            int i = locs[j - 1] + 1;
            while (all[i] != all[j]) {
                if (i == 0) {
                    i = -1;
                    break;
                }
                i = locs[i - 1] + 1;
            }
            locs[j] = i;
            if (i + 1 == m)
                cnt++;
        }

        pw.println(cnt);
        pw.close();
    }
}
