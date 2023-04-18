import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Each vertex has a "pay," Pi, where if you visit that node it gives you Pi dollars. You need to start and end at node 0.
 * It cost c * (number of edges traversed)^2 to take that circuit. The goal is to maximize the amount earned - cost to
 * do the circuit. It may be that not going anywhere is the optimal solution.
 *
 * @since 12-20-20
 * @author jerry
 * @version 1.0
 */
public class FindMaximumEarningIfTravelsIsQuadraticCost {
    private static int[] best;
    private static int c;

    public static void main(String[] args) throws IOException {
        Reader r = new Reader("file.in");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        int n = r.nextInt();
        int m = r.nextInt();
        c = r.nextInt();
        int[] earn = new int[n];
        for (int i = 0; i < n; i++) {
            earn[i] = r.nextInt();
        }
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            edges.get(r.nextInt() - 1).add(r.nextInt() - 1);
        }
        r.close();

        best = new int[n];
        Arrays.fill(best, Integer.MIN_VALUE);
        bfs(edges, earn);

        pw.println(best[0]);
        pw.close();
    }

    private static void bfs(ArrayList<ArrayList<Integer>> edges, int[] earn) {
        LinkedList<state> queue = new LinkedList<>();
        queue.offer(new state(0, 0, 0));

        while (!queue.isEmpty()) {
            state cur = queue.poll();

            if (best[cur.at] >= cur.tot) {
                continue;
            }

            best[cur.at] = cur.tot;

            for (int next : edges.get(cur.at)) {
                queue.offer(new state(next, cur.cost + 1, cur.earned + earn[next]));
            }
        }
    }

    private static class state {
        int at;
        int cost, earned, tot;

        public state(int at, int cost, int earned) {
            this.at = at;
            this.cost = cost;
            this.earned = earned;

            tot = earned - (cost * cost * c);
        }
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(String file_name) throws IOException {
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
