import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class LongestPathWithPositiveCycle {
    private static final long MIN = (long) -(2.5e12);
    private static boolean inf = false;

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int m = fr.nextInt();
        ArrayList<ArrayList<int[]>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            edges.get(fr.nextInt() - 1).add(new int[]{fr.nextInt() - 1, fr.nextInt()});
        }

        boolean[] pos = new boolean[n];
        int N = n - 1;
        for (int i = 0; i < n; i++) {
            pos[i] = findPos(edges, new boolean[n], i, N);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                int[] cur = edges.get(i).get(j);

                if (!pos[cur[0]]) {
                    edges.get(i).remove(j);
                    j--;
                }
            }
        }
        long[] max = new long[n];
        Arrays.fill(max, MIN);
        dfs(edges, new boolean[n], max, 0, 0);

        if (inf) {
            pw.println(-1);
        } else {
            pw.println(max[n - 1]);
        }
        pw.close();
    }

    private static boolean findPos(ArrayList<ArrayList<int[]>> edges, boolean[] visited, int at, int n) {
        if (at == n) {
            return true;
        }
        visited[at] = true;

        for (int[] next : edges.get(at)) {
            if (!visited[next[0]]) {
                if (findPos(edges, visited, next[0], n)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static void dfs(ArrayList<ArrayList<int[]>> edges, boolean[] visited, long[] max, long cost, int at) {
        if (visited[at]) {
            inf = true;
            return;
        }

        visited[at] = true;
        max[at] = cost;

        for (int[] next : edges.get(at)) {
            long newCost = cost + next[1];
            if (newCost > max[next[0]]) {
                dfs(edges, visited, max, newCost, next[0]);
                if (inf) {
                    return;
                }
            }
        }

        visited[at] = false;
    }

    private static class FastReader {
        final private int BUFFER_SIZE = 16;
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
