import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class BinomialCoefficientsModM {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = ir.readInt();

        int M = 1000000007, mxA = 1000001;
        long[] iv = new long[mxA];
        long[] f1 = new long[mxA];
        long[] f2 = new long[mxA];
        iv[1] = 1;
        for (int i = 2; i < mxA; i++) {
            iv[i] = M - M / i * iv[M % i] % M;
        }
        f1[0] = f2[0] = 1;
        for (int i = 1; i < mxA; i++) {
            f1[i] = f1[i - 1] * i % M;
            f2[i] = f2[i - 1] * iv[i] % M;
        }
        StringBuilder sb = new StringBuilder(n * 3);
        while (n-- > 0) {
            int a = ir.readInt(), b = ir.readInt();
            sb.append(f1[a] * f2[b] % M * f2[a - b] % M).append("\n");
        }
        ir.close();

        pw.print(sb);
        pw.close();
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 24];
        private int curChar;
        private int numChars;

        public InputReader() {
            this.stream = System.in;
        }

        public void close() throws IOException {
            stream.close();
        }

        public int read() throws IOException {
            if (numChars == -1) {
                throw new IOException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new IOException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int readInt() throws IOException {
            int c = read();
            while ((c == ' ') || (c == '\n') || (c == '\r') || (c == '\t') || (c == -1)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new IOException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!((c == ' ') || (c == '\n') || (c == '\r') || (c == '\t') || (c == -1)));
            return res * sgn;
        }
    }
}
