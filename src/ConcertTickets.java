import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class ConcertTickets {
    private static int[] pos;

    public static void main(String[] args) throws IOException {
        Reader fr = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int m = fr.nextInt();
        int[] prices = new int[n + 1];
        for (int i = 1; i <= n; i++)
            prices[i] = fr.nextInt();

        Arrays.sort(prices);
        pos = new int[n + 1];
        for (int i = 0; i <= n; i++)
            pos[i] = i;
        while (m-- > 0) {
            int cur = fr.nextInt();
            int lower = 0, upper = n + 1;
            while (upper - lower > 1) {
                int mid = (lower + upper) / 2;
                if (prices[mid] <= cur)
                    lower = mid;
                else
                    upper = mid;
            }
            if (lower == 0) {
                pw.println(-1);
                continue;
            }
            int i = find(lower);
            if (i == 0) {
                pw.println(-1);
                continue;
            }
            pw.println(prices[i]);
            pos[i] = i - 1;
        }

        fr.close();
        pw.close();
    }

    private static int find(int i) {
        return pos[i] == i ? i : (pos[i] = find(pos[i]));
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
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

        private void close() throws IOException {
            dis.close();
        }
    }
}
	