import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class NumberOfEqualPairsAiAjandAkAlWhereijkandlAreInOrder {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();
        PrintWriter pw = new PrintWriter(System.out);

        int t = ir.nextInt();
        for (int i = 0; i < t; i++) {
            int n = ir.nextInt();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = ir.nextInt() - 1;
            }

            int[] cntLeft = new int[n];
            int[] cntRight;
            long ans = 0L;
            int N = n - 1;
            for (int j = 0; j < n; j++) {
                cntRight = new int[n];
                for (int k = N; k > j; k--) {
                    ans += (long) cntLeft[a[k]] * cntRight[a[j]]; //number of a[k] to the left of j (for a[i] to match with a[k]) times number of a[j] to the right of k (for a[l] to match with a[j])
                    cntRight[a[k]]++;
                }
                cntLeft[a[j]]++;
            }

            pw.println(ans);
        }

        pw.close();
    }

    private static class InputReader {
        final private int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader() {
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
