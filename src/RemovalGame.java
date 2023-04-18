import java.io.IOException;
import java.io.InputStream;

public class RemovalGame {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();

        int n = ir.nextInt();
        int[] arr = new int[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = ir.nextInt();
            sum += arr[i];
        }

        if (n == 1) {
            System.out.print(arr[0]);
        } else {
            long[] prev = new long[n];
            int N = n - 1;
            prev[N] = arr[N];
            for (int i = n - 2; i >= 0; i--) {
                long[] cur = new long[n];
                cur[i] = arr[i];
                for (int j = i + 1; j < n; j++) {
                    cur[j] = Math.max(arr[j] - cur[j - 1], arr[i] - prev[j]);
                }
                prev = cur;
            }
            System.out.print(((sum - prev[N]) / 2) + prev[N]);
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader() {
            this.stream = System.in;
        }

        private int read() throws IOException {
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

        private int nextInt() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
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
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == -1;
        }
    }
}
