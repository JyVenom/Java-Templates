import java.util.ArrayList;

public class SplitCut {
    private static void DFS(ArrayList<ArrayList<Integer>> edges, boolean[] visited, int avoid, int end, int at) {
        visited[at] = true;
        if (at == end) {
            return;
        }

        for (int next : edges.get(at)) {
            if (next != avoid) {
                if (!visited[next]) {
                    DFS(edges, visited, avoid, end, next);
                    if (visited[end]) {
                        return;
                    }
                }
            }
        }
    }

    private static void DFS2(ArrayList<ArrayList<Integer>> edges, boolean[] visited, int avoid, int at) {
        visited[at] = true;

        for (int next : edges.get(at)) {
            if (next != avoid) {
                if (!visited[next]) {
                    DFS2(edges, visited, avoid, next);
                }
            }
        }
    }

    private static boolean DFS3(ArrayList<ArrayList<Integer>> edges, boolean[] visited, boolean[] first, int end, int at) {
        visited[at] = true;

        for (int next : edges.get(at)) {
            if (first[next]) {
                return true;
            }
            if (!visited[next]) {
                if (next != end) {
                    if (DFS3(edges, visited, first, end, next)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> findPosSplitPoints(ArrayList<ArrayList<Integer>> edges) { //edges is adjacency list of directed edges.
        int N = edges.size();
        int n = N - 1;
        edges.remove(n);

        ArrayList<Integer> ans1 = new ArrayList<>();
        for (int i = 1; i < n; i++) { //find point that are required to get to end (if remove then can not get to end)
            boolean[] visited = new boolean[N];
            DFS(edges, visited, i, n, 0);
            if (!visited[n]) {
                ans1.add(i);
            }
        }
        ArrayList<Integer> ans2 = new ArrayList<>(ans1);
        for (int i = 0; i < ans2.size(); i++) {
            int cur = ans2.get(i);
            boolean[] visited = new boolean[N];
            DFS2(edges, visited, cur, 0); //find every point that can still be visited if remove point cur starting at point 0
            boolean[] visited2 = new boolean[N];
            if (DFS3(edges, visited2, visited, n, cur)) { //if can reach a point in first half (all the points that come before cur. if cur is a split point, then the first sunset/tree/etc.) from second half
                ans2.remove(i); //it is not a split point, so remove
                i--;
            }
        }

        return ans2; //everything left must be a split point
    }
}
