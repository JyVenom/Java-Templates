import java.util.LinkedList;

public class EulerianTourCircuit {
    private final int V;
    private final LinkedList<LinkedList<Integer>> adj;

    EulerianTourCircuit(int v) {
        V = v;
        adj = new LinkedList<>();
        for (int i = 0; i < v; ++i)
            adj.add(new LinkedList<>());
    }

    void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
    }

    void DFSUtil(int v, boolean[] visited) {
        visited[v] = true;

        for (int n : adj.get(v)) {
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    boolean isConnected() {
        boolean[] visited = new boolean[V];
        int i;
//        for (i = 0; i < V; i++)
//            visited[i] = false;

        for (i = 0; i < V; i++)
            if (adj.get(i).size() != 0)
                break;

        if (i == V)
            return true;

        DFSUtil(i, visited);

        for (i = 0; i < V; i++)
            if (!visited[i] && adj.get(i).size() > 0)
                return false;

        return true;
    }

    int isEulerian() {
        if (!isConnected())
            return 0;

        int odd = 0;
        for (int i = 0; i < V; i++)
            if (adj.get(i).size() % 2 != 0)
                odd++;

        if (odd > 2)
            return 0;

        return (odd == 2) ? 1 : 2; //1 = tour/path (odd == 2), 2 = circuit/cycle (odd == 0)
    }
}
