import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Finds the maximum flow rate / cost possible achieved by any of ONE path
 *
 * @author jerry
 * @version 1.0
 * @since 12-19-2020
 */
public class MaxFlowOverCost {
    private static double[] best;

    public static void main(String[] args) throws IOException {
        Reader r = new Reader("file.in");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        int n = r.nextInt();
        int m = r.nextInt();
        ArrayList<ArrayList<int[]>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int a = r.nextInt() - 1;
            int b = r.nextInt() - 1;
            int c = r.nextInt();
            int f = r.nextInt();

            edges.get(a).add(new int[]{b, c, f});
            edges.get(b).add(new int[]{a, c, f});
        }
        r.close();

        boolean[] visited = new boolean[n];
        best = new double[n];
        int N = n - 1;
        dfs(edges, visited, Integer.MAX_VALUE, 0, 0, N);
        double ans = bfs(edges, n);

        pw.println((int) (best[N] * 1000000));
        pw.println((int) (ans * 1000000));
        pw.close();
    }

    //Faster implementation
    //Tries every possible path
    //At each node, if a better/higher rate has ben found to that node, stop processing and return to parent
    private static void dfs(ArrayList<ArrayList<int[]>> edges, boolean[] visited, double curFlow, double curCost, int at, int n) {
        visited[at] = true;

        double rate = curFlow / curCost;
        if (best[at] >= rate) {
            visited[at] = false;
            return;
        }
        best[at] = rate;

        if (at == n) {
            visited[at] = false;
            return;
        }

        for (int[] next : edges.get(at)) {
            if (!visited[next[0]]) {
                dfs(edges, visited, Math.min(curFlow, next[2]), curCost + next[1], next[0], n);
            }
        }

        visited[at] = false;
    }

    //Slower implementation
    //Greedily ony choose highest rate node
    //Return when reach end because rate must be highest because it was greedily processed
    private static double bfs(ArrayList<ArrayList<int[]>> edges, int n) {
        LinkedList<state> queue = new LinkedList<>();
        queue.add(new state(0, Double.POSITIVE_INFINITY, 0.0)); //at, flow, cost
        boolean[] visited = new boolean[n];
        int N = n - 1;

        while (!queue.isEmpty()) {
            state cur = queue.poll();
            visited[cur.at] = true;

            if (cur.at == N) {
                return cur.rate;
            }

            for (int[] next : edges.get(cur.at)) {
                if (!visited[next[0]]) {
                    queue.offer(new state(next[0], Math.min(cur.flow, next[2]), cur.cost + next[1]));
                }
            }
            queue.sort((o1, o2) -> Double.compare(o2.rate, o1.rate));
        }

        return -1; //should never happen
    }

    private static class state {
        private final int at;
        private final double flow, cost, rate;

        public state(int at, double flow, double cost) {
            this.at = at;
            this.flow = flow;
            this.cost = cost;
            this.rate = flow / cost;
        }
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        private Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
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
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        private void close() throws IOException {
            din.close();
        }
    }
}
