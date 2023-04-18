import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Finds all unique positive elements in the given data then tries setting each one as the max and finding the max
 * subarray sum. This can be done by doing kadane's algorithm as usual but skipping over array elements larger that the
 * max we have currently set.
 * Time complexity: ((# of unique positive elements) * n)
 *
 * @author jerry
 * @version 1.0
 */
public class MaxSubarraySumIfRemoveLargestElementInSubarray {
    public static void main(String[] args) throws IOException {
        InputReader2 ir = new InputReader2();
        PrintWriter pw = new PrintWriter(System.out);

        int n = ir.nextInt();
        int[] arr = new int[n];
        HashSet<Integer> all = new HashSet<>();
        for (int i = 0; i < n; i++) {
            arr[i] = ir.nextInt();
            all.add(arr[i]);
        }

        ArrayList<Integer> all2 = new ArrayList<>(all);
        for (int i = 0; i < all2.size(); i++) {
            if (all2.get(i) <= 0) {
                all2.remove(i);
                i--;
            }
        }
        int ans = 0;
        for (Integer max : all2) {
            int cur = 0;
            int min = 0;
            for (int j = 0; j < n; j++) {
                if (arr[j] > max) {
                    continue;
                }
                cur += arr[j];
                min = Math.min(min, cur);
                ans = Math.max(ans, cur - min - max);
            }
        }

        pw.println(ans);
        pw.close();
    }

    private static class InputReader2 {
        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader2() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = dis.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
    }
}
