import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class NumberOfDistinctTypesOfNodesInEachSubtree {
    private static final ArrayDeque<Node> stack = new ArrayDeque<>();

    public static void main(String[] args) {
        FastScanner in = new FastScanner();
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = in.nextInt();
        Node[] nodes = new Node[n];
        int[] ans = new int[n], en = new int[n], val = new int[n], id = new int[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node();
            val[i] = in.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt() - 1, v = in.nextInt() - 1;
            nodes[u].adj.add(nodes[v]);
            nodes[v].adj.add(nodes[u]);
        }

        DFS(nodes[0]);
        Fenwick fenwick = new Fenwick(n);
        int last = 0;
        for (Node node : nodes) {
            en[node.start] = node.end;
            id[node.start] = last;
            last++;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            int v = val[id[i]];
            int next = map.getOrDefault(v, -1);
            if (next != -1) {
                fenwick.add(next);
            }
            map.put(v, i);

            ans[id[i]] = fenwick.prefix(en[i]) - fenwick.prefix(i - 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int x : ans) sb.append(x).append(' ');
        out.print(sb);
        out.close();
    }

    static void DFS(Node node) {
        stack.add(node);
        int clock = -1;
        outer:
        while (!stack.isEmpty()) {
            node = stack.peekLast();
            if (node.ptr == -1) {
                node.visited = true;
                node.start = ++clock;
            }

            for (node.ptr++; node.ptr < node.adj.size(); node.ptr++) {
                Node neigh = node.adj.get(node.ptr);
                if (neigh.visited) continue;
                stack.add(neigh);
                continue outer;
            }

            node.end = clock;
            stack.pollLast();
        }
    }


    private static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n];
            for (int i = 0; i < n; i++) {
                bit[i]++;
                if (i + ((i + 1) & -(i + 1)) < n)
                    bit[i + ((i + 1) & -(i + 1))] += bit[i];
            }
        }

        void add(int i) {
            if (i < 0) return;
            while (i < bit.length) {
                bit[i] -= 1;
                i += Integer.lowestOneBit(i + 1);
            }
        }

        int prefix(int i) {
            if (i >= bit.length) return 0;
            int ret = 0;
            while (i >= 0) {
                ret += bit[i];
                i -= Integer.lowestOneBit(i + 1);
            }
            return ret;
        }
    }


    private static class Node {
        int start, end;
        ArrayList<Node> adj = new ArrayList<>();
        int ptr = -1;
        boolean visited = false;
    }

    private static class FastScanner {
        private final int BUFFER_SIZE = 1 << 22;
        private final DataInputStream inputStream;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastScanner() {
            inputStream = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private int nextInt() {
            int ret = 0;
            byte c = read();
            while (c <= ' ') c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            return ret;
        }

        private void fillBuffer() {
            try {
                bytesRead = inputStream.read(buffer, bufferPointer = 0, BUFFER_SIZE);
                if (bytesRead == -1) buffer[0] = -1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private byte read() {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }
}
