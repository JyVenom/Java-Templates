import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Faster than fenwick tree sol
 */
public class MaximumMatchingParenthesisInRangeOneTypeSegmentTreeSolution {
    public static void main(String[] args) throws IOException {
        InputReader2 ir = new InputReader2();
        PrintWriter pw = new PrintWriter(System.out);

        char[] s = ir.nextLine();
        int n = s.length;
        int m = ir.nextInt();

        int[] bal = new int[n + 1];
        int[] len = new int[n + 1];
        int cur = 0;
        int curlen = 0;
        for (int i = 0; i < n; ++i) {
            if (s[i] == '(') {
                ++cur;
            } else {
                --cur;
            }
            if (cur < 0) {
                cur = 0;
            } else {
                ++curlen;
            }
            bal[i + 1] = cur;
            len[i + 1] = curlen;
        }
        SGT sgt = new SGT(bal, 2147483647);
        for (int i = 0; i < m; ++i) {
            int l = ir.nextInt() - 1, r = ir.nextInt(), min = sgt.qry(l, r + 1);
            if (l == r - 1) {
                pw.println(0);
            } else {
                pw.println(len[r] - len[l] - (bal[l] - min) - (bal[r] - min));
            }
        }

        pw.close();
    }

    static class SGT {
        int n;
        int[] tree;
        int qryInitVal;

        SGT(int[] a, int queryInitialValue) {
            n = a.length;
            tree = new int[2 * n];
            qryInitVal = queryInitialValue;
            System.arraycopy(a, 0, tree, n, n);
            for (int i = n - 1; i >= 1; --i) {
                tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
            }
        }

        int qry(int i, int j) {
            int ans = qryInitVal;
            for (i += n, j += n; i < j; i >>= 1, j >>= 1) {
                if ((i & 1) == 1) {
                    ans = Math.min(ans, tree[i++]);
                }
                if ((j & 1) == 1) {
                    ans = Math.min(tree[--j], ans);
                }
            }
            return ans;
        }
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

        private char[] nextLine() throws IOException {
            int c = read();
            StringBuilder res = new StringBuilder();
            while (c != '\n') {
                res.appendCodePoint(c);
                c = read();
            }
            return res.toString().toCharArray();
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
