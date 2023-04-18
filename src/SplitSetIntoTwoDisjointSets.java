import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SplitSetIntoTwoDisjointSets {
    private static boolean[] teams;
    private static boolean good = true;

    public static void main(String[] args) throws Exception {
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

        teams = new boolean[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; good && i < n; i++) {
            if (!visited[i]) {
                dfs(edges, visited, false, i);
            }
        }
        if (good) {
            pw.print(teams[0] ? 2 : 1);
            for (int i = 1; i < n; i++) {
                pw.print(" " + (teams[i] ? 2 : 1));
            }
            pw.println();
        } else {
            pw.println("IMPOSSIBLE");
        }

        pw.close();
    }

    private static void dfs(ArrayList<ArrayList<Integer>> edges, boolean[] visited, boolean team, int at) {
        if (!good) {
            return;
        }

        visited[at] = true;
        teams[at] = team;

        for (int next : edges.get(at)) {
            if (visited[next]) {
                if (teams[next] == team) {
                    good = false;
                    return;
                }
            } else {
                dfs(edges, visited, !team, next);
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
