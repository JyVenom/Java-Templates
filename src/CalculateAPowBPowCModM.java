import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CalculateAPowBPowCModM {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        for (int i = 0; i < n; i++) {
            int a = r.nextInt();
            int b = r.nextInt();
            int c = r.nextInt();
            pw.println(binPow(a % 1000000007, binPow(b % 1000000006, c, 1000000006), 1000000007));
        }
        r.close();
        pw.close();
    }

    private static long binPow(long a, long b, int m) {
        assert (b >= 0);
        a %= m; //note: m*m must be less than 2^63 to avoid ll overflow
        long res = 1;
        while (b > 0) {
            if (b % 2 == 1) //if n is odd
                res = res * a % m;
            a = a * a % m;
            b /= 2; //divide by two
        }
        return res;
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
