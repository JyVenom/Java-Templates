import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class DistanceQueries {
    private static int[] oo, oj, ae, pp, dd, qq;
    private static int count = 1;

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int q = fr.nextInt();
        int m = n - 1;
        int m2 = 1 + m * 2;
        oo = new int[m2];
        oj = new int[m2];
        ae = new int[n];
        pp = new int[n];
        dd = new int[n];
        qq = new int[n];
        int N = n - 1;
        for (int i = 0; i < N; i++) {
            int a = fr.nextInt() - 1;
            int b = fr.nextInt() - 1;
            ae[a] = link(ae[a], b);
            ae[b] = link(ae[b], a);
        }

        Random rand = new Random();
        int r = rand.nextInt(n);
        dfs1(-1, r, 0);
        dfs2(-1, r, r);
        StringBuilder sb = new StringBuilder();
        while (q-- > 0) {
            int i = fr.nextInt() - 1;
            int j = fr.nextInt() - 1;
            int a = lca(i, j);
            sb.append(dd[i] + dd[j] - dd[a] * 2).append("\n");
        }

        pw.print(sb);
        pw.close();
    }

    private static int link(int o, int j) {
        oo[count] = o;
        oj[count] = j;
        return count++;
    }

    private static int dfs1(int p, int i, int d) {
        pp[i] = p;
        dd[i] = d++;
        int k_ = 0, j_ = -1, sum = 1;
        for (int o = ae[i]; o != 0; o = oo[o]) {
            int j = oj[o];
            if (j != p) {
                int k = dfs1(i, j, d);
                if (k_ < k) {
                    k_ = k;
                    j_ = j;
                }
                sum += k;
            }
        }
        qq[i] = j_;
        return sum;
    }

    private static void dfs2(int p, int i, int q) {
        int j_ = qq[i];
        qq[i] = q;
        for (int o = ae[i]; o != 0; o = oo[o]) {
            int j = oj[o];
            if (j != p)
                dfs2(i, j, j == j_ ? q : j);
        }
    }

    private static int lca(int i, int j) {
        while (qq[i] != qq[j])
            if (dd[qq[i]] > dd[qq[j]])
                i = pp[qq[i]];
            else
                j = pp[qq[j]];
        return dd[i] < dd[j] ? i : j;
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

        public void close() throws IOException {
            dis.close();
        }
    }
}