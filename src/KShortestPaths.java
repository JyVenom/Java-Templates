import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KShortestPaths {
    private final int n;
    private final ArrayList<ArrayList<Edge>> graph;

    public KShortestPaths(int n) {
        this.n = n;
        graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, long cost) {
        graph.get(from).add(new Edge(to, cost));
    }

    public ArrayList<Long> dijkstra(int start, int end, int k) {
        ArrayList<Long> ans = new ArrayList<>();
        int[] count = new int[n];
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o.cost));
        pq.add(new State(start, 0L));

        while (!pq.isEmpty() && count[end] < k) {
            State cur = pq.poll();
            int nodeId = cur.at;
            long minValue = cur.cost;

            count[nodeId]++;
            if (nodeId == end) {
                ans.add(minValue);
            }
            if (count[nodeId] <= k) {
                for (Edge edge : graph.get(nodeId)) {
                    pq.add(new State(edge.to, minValue + edge.cost));
                }
            }
        }

        return ans;
    }

    private static class State {
        int at;
        long cost;

        public State(int at, long cost) {
            this.at = at;
            this.cost = cost;
        }
    }

    public static class Edge {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }
}
