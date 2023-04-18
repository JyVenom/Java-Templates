import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Subordinates {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int[] boss = new int[n + 1];
        int[] count = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            int p = fr.nextInt();
            boss[i] = p;
            count[p]++;
        }

        int[] temp = new int[n + 1];
        int head = 0, cnt = 0;
        for (int i = 1; i <= n; i++)
            if (count[i] == 0)
                temp[head + cnt++] = i;
        int[] ans = new int[n + 1];
        Arrays.fill(ans, 1);
        while (cnt > 0) {
            int i = temp[head++];
            cnt--;
            int p = boss[i];
            ans[p] += ans[i];
            if (--count[p] == 0)
                temp[head + cnt++] = p;
        }
        for (int i = 1; i <= n; i++)
            pw.print(ans[i] - 1 + " ");

        pw.close();
    }

    private static class FastReader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
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

        public void close() throws IOException {
            dis.close();
        }
    }
}
