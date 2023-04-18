import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AddEdgesToMakeAGraphStronglyConnected {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        int m = r.nextInt();
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = r.nextInt() - 1;
            int b = r.nextInt() - 1;

            edges.get(a).add(b);
            edges.get(b).add(a);
        }
        r.close();

        boolean[] visited = new boolean[n];
        dfs(edges, visited, 0);
        int prev = 1;
        ArrayList<int[]> ans = new ArrayList<>();
        for (int i = 1; i < n; ) {
            if (!visited[i]) {
                dfs(edges, visited, i);
                ans.add(new int[]{prev, ++i});
                prev = i;
            } else {
                i++;
            }
        }

        pw.println(ans.size());
        for (int[] an : ans) {
            pw.println(an[0] + " " + an[1]);
        }
        pw.close();
    }

    private static void dfs(ArrayList<ArrayList<Integer>> edges, boolean[] visited, int at) {
        visited[at] = true;

        for (int next : edges.get(at)) {
            if (!visited[next]) {
                dfs(edges, visited, next);
            }
        }
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
