import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MaxDepthInTreeFromEachNode {
    private static vertex[] vertices;

    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        int N = n - 1;
        vertices = new vertex[n];
        for (int i = 0; i < n; i++)
            vertices[i] = new vertex();
        for (int i = 0; i < N; i++) {
            int a = r.nextInt() - 1;
            int b = r.nextInt() - 1;
            vertices[a].adj.add(b);
            vertices[b].adj.add(a);
        }
        r.close();

        dfs1(-1, 0);
        dfs2(-1, 0, 0);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(vertices[i].d1).append(" ");

        pw.println(sb);
        pw.close();
    }

    private static void dfs1(int p, int i) {
        vertex u = vertices[i];
        int d1 = -1, d2 = -1;
        for (int j : u.adj)
            if (j != p) {
                dfs1(i, j);
                int d = vertices[j].d1;
                if (d1 < d) {
                    d2 = d1;
                    d1 = d;
                } else if (d2 < d)
                    d2 = d;
            }
        u.d1 = d1 + 1;
        u.d2 = d2 + 1;
    }

    private static void dfs2(int p, int i, int d_) {
        vertex u = vertices[i];
        int d1_ = Math.max(d_, u.d1);
        int d2_ = Math.max(d_, u.d2);
        for (int j : u.adj)
            if (j != p) {
                vertex v = vertices[j];
                dfs2(i, j, (v.d1 + 1 == u.d1 ? d2_ : d1_) + 1);
            }
        u.d1 = d1_;
    }

    private static class vertex {
        ArrayList<Integer> adj = new ArrayList<>();
        int d1, d2;
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
