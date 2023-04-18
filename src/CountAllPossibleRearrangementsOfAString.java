import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class CountAllPossibleRearrangementsOfAString {
    public static long binPow(long a, long b) {
        assert (b >= 0);
        a %= 1000000007; //note: m*m must be less than 2^63 to avoid ll overflow
        long res = 1;
        while (b > 0) {
            if (b % 2 == 1) //if n is odd
                res = res * a % 1000000007;
            a = a * a % 1000000007;
            b /= 2; //divide by two
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        char[] s = r.readLine().toCharArray();
        r.close();

        int n = s.length;
        int[] count = new int[26];
        for (char c : s) {
            count[c - 'a']++;
        }
        long[] factorials = new long[n + 1];
        long f = 1;
        for (int i = 0; i <= n; i++) {
            factorials[i] = f;
            f = f * (i + 1) % 1000000007;
        }
        long ans = factorials[n];
        for (int i = 0; i < 26; i++) {
            ans = ans * binPow(factorials[count[i]], 1000000005) % 1000000007;
        }

        pw.println(ans);
        pw.close();
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

        public String readLine() throws IOException {
            byte[] buf = new byte[BUFFER_SIZE]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
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
