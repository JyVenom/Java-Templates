import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;

public class FindAllPrefixEqualToSuffixAndTheirNumberOfAppearances {
    public static void main(String[] args) throws IOException {
        InputReader2 ir = new InputReader2();
        PrintWriter pw = new PrintWriter(System.out);

        char[] s = ir.nextLine().toCharArray();

        int n = s.length;
        int[] pi = new int[n];
        // Alternate way to do lines 25 - 35.
        for (int i = 1; i < n; ++i) {
            int j = pi[i - 1];
            while (j > 0 && s[i] != s[j]) {
                j = pi[j - 1];
            }

            if (s[i] == s[j]) ++j;
            pi[i] = j;
        }
//        int at = 1;
//        int len = 0;
//        while (at < n) {
//            if (s[at] == s[len]) {
//                pi[at++] = ++len;
//            } else if (len != 0) {
//                len = pi[len - 1];
//            } else {
//                pi[at++] = len;
//            }
//        }
        int[] cnt = new int[n + 1];
        for (int i = 1; i < n; i++) {
            cnt[pi[i]]++;
        }
        for (int i = n - 1; i > 1; i--) {
            cnt[pi[i - 1]] += cnt[i];
        }
        for (int i = 0; i <= n; i++) {
            cnt[i]++;
        }
        ArrayDeque<int[]> ans = new ArrayDeque<>();
        int cur = n;
        while (cur > 0) {
            ans.push(new int[]{cur, cnt[cur]});
            cur = pi[cur - 1];
        }

        pw.println(ans.size());
        while (!ans.isEmpty()) {
            pw.println(ans.peek()[0] + " " + ans.pop()[1]);
        }
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

        private String nextLine() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
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
