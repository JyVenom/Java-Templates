public class FloydWarshallAllPairsShortestPath {
    public int[][] floydWarshall(int[][] graph) { //graph is an adjacency matrix of edges (storing their costs)
        int v = graph.length;
        int[][] dist = new int[v][v];
        int i, j, k;

        for (i = 0; i < v; i++)
            for (j = 0; j < v; j++)
                dist[i][j] = graph[i][j];

        for (k = 0; k < v; k++) {
            for (i = 0; i < v; i++) {
                for (j = 0; j < v; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }

        return dist; //dist holds the all pair shortest path (dist[a][b] is the shortest path from a to b)
    }
}
