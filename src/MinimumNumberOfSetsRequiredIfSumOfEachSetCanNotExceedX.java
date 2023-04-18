import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class MinimumNumberOfSetsRequiredIfSumOfEachSetCanNotExceedX {
    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        int x = r.nextInt();
        int[] ppl = new int[n];
        for (int i = 0; i < n; i++) {
            ppl[i] = r.nextInt();
        }

        shuffle(ppl, n);
        Arrays.sort(ppl);
        int count = n;
        for (int i = 0, j = n - 1; i < j; i++) {
            while (i < j && ppl[i] + ppl[j] > x)
                j--;
            if (i < j) {
                j--;
                count--;
            }
        }

        pw.println(count);
        pw.close();
    }

    private static void shuffle(int[] arr, int sz) {
        for (int i = 1; i < sz; i++) {
            int j = rand.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
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
