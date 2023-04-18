import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CalculateAPowBModM {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        for (int i = 0; i < n; i++) {
            int a = fr.nextInt();
            int b = fr.nextInt();
            pw.println(binPow(a, b, (long) 1e9 + 7));
        }
        fr.close();
        pw.close();
    }

    public static long binPow(long x, long n, long m) {
        assert (n >= 0);
        x %= m; //note: m*m must be less than 2^63 to avoid ll overflow
        long res = 1;
        while (n > 0) {
            if (n % 2 == 1) //if n is odd
                res = res * x % m;
            x = x * x % m;
            n /= 2; //divide by two
        }
        return res;
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
