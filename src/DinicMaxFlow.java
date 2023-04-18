//Better than ford fulkerson

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DinicMaxFlow {

    public static List<List<Edge>> createGraph(int nodes) {
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < nodes; i++)
            graph.add(new ArrayList<>());
        return graph;
    }

    public static void addEdge(List<List<Edge>> graph, int s, int t, int cap) {
        graph.get(s).add(new Edge(t, graph.get(t).size(), cap));
        graph.get(t).add(new Edge(s, graph.get(s).size() - 1, 0));
    }

    static boolean dinicBfs(List<List<Edge>> graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.size()];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph.get(u)) {
                if (dist[e.t] < 0 && e.f < e.cap) {
                    dist[e.t] = dist[u] + 1;
                    Q[sizeQ++] = e.t;
                }
            }
        }
        return dist[dest] >= 0;
    }

    static int dinicDfs(List<List<Edge>> graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph.get(u).size(); ++ptr[u]) {
            Edge e = graph.get(u).get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    graph.get(e.t).get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }

    public static int maxFlow(List<List<Edge>> graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.size()];
        while (dinicBfs(graph, src, dest, dist)) {
            int[] ptr = new int[graph.size()];
            while (true) {
                int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }

    // Usage example
    public static void main(String[] args) {
        List<List<Edge>> graph = createGraph(3);
        addEdge(graph, 0, 1, 3);
        addEdge(graph, 0, 2, 2);
        addEdge(graph, 1, 2, 2);
        System.out.println(maxFlow(graph, 0, 2));
//        System.out.println(4 == maxFlow(graph, 0, 2));
    }

    static class Edge {
        int t, rev, cap, f;

        public Edge(int t, int rev, int cap) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
        }
    }
}