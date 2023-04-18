import java.util.ArrayList;

public abstract class DFS {
    private void dfs(int[][] map, boolean[][] visited, int row, int col) {
        visited[row][col] = true;

        toDo(map, row, col);

        if (row > 0 && !visited[row - 1][col]) {
            dfs(map, visited, row - 1, col);
        }
        if (row < map.length - 1 && !visited[row + 1][col]) {
            dfs(map, visited, row + 1, col);
        }
        if (col > 0 && !visited[row][col - 1]) {
            dfs(map, visited, row, col - 1);
        }
        if (col < map[0].length - 1 && !visited[row][col + 1]) {
            dfs(map, visited, row, col + 1);
        }
    }

    private void dfs(ArrayList<ArrayList<Integer>> edges, boolean[] visited, int at) {
        visited[at] = true;

        toDo(edges, at);

        for (int child : edges.get(at)) {
            if (!visited[child]) {
                dfs(edges, visited, child);
            }
        }
    }

    public abstract void toDo(int[][] map, int row, int col);

    public abstract void toDo(ArrayList<ArrayList<Integer>> edges, int at);
}
