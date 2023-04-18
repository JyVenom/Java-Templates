import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Slower than segment tree sol (but more understandable and it uses fwt, which is better)
 */
public class MaximumMatchingParenthesisInRangeOneTypeFenwickTreeSolution {
    public static void main(String[] args) throws IOException {
        InputReader2 ir = new InputReader2();
        PrintWriter pw = new PrintWriter(System.out);

        char[] s = ir.nextLine();

        int n = s.length;
//        ArrayDeque<int[]> pairs = new ArrayDeque<>(n);
        ArrayList<int[]> pairs = new ArrayList<>(n);
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 1; i < n; i++) {
            if (s[i] == '(') {
                stack.addLast(i);
            } else {
                if (!stack.isEmpty()) {
//                    pairs.addLast(new int[]{stack.removeLast(), i});
                    pairs.add(new int[]{stack.removeLast(), i});
                }
            }
        }

        int m = ir.nextInt();
        Query[] queries = new Query[m];
        for (int i = 0; i < m; i++) {
            queries[i] = new Query(ir.nextInt(), ir.nextInt(), i);
        }

        Arrays.sort(queries);
        int[] ans = new int[m];
        FenwickTree fwt = new FenwickTree(n);
        for (int i = 0, j = 0; i < m; i++) {
            Query cur = queries[i];
//            while (j < pairs.size() && pairs.getFirst()[1] <= cur.r) {
//                fwt.add(pairs.removeFirst()[0]);
//            }
            while (j < pairs.size() && pairs.get(j)[1] <= cur.r) {
                fwt.add(pairs.get(j++)[0]);
            }
            ans[cur.ind] = fwt.prefixSum(cur.r) - fwt.prefixSum(cur.l - 1);
        }

        for (int i = 0; i < m; i++) {
            pw.println(ans[i] << 1);
        }
        pw.close();
    }

    private static class Query implements Comparable<Query> {
        int l, r, ind;

        public Query(int l, int r, int ind) {
            this.l = l;
            this.r = r;
            this.ind = ind;
        }

        @Override
        public int compareTo(Query o) {
            return Integer.compare(r, o.r);
        }
    }

    private static class FenwickTree {
        final int N;
        private final int[] tree;

        public FenwickTree(int sz) {
            tree = new int[(N = sz + 1)];
        }

        private static int lsb(int i) {
            return i & -i;
        }

        private int prefixSum(int i) {
            if (i == -1) {
                return 0;
            }
            int sum = 0;
            while (i != 0) {
                sum += tree[i];
                i &= ~lsb(i);
            }
            return sum;
        }

        private void add(int i) {
            while (i < N) {
                tree[i] += 1;
                i += lsb(i);
            }
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
            res.append(" ");
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
