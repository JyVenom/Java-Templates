import java.io.*;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Arrays;
import java.util.LinkedList;

public class BFS {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));
        int n = Integer.parseInt(br.readLine());
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        edges.add(new ArrayList<>());
        edges.add(new ArrayList<>());
        edges.add(new ArrayList<>());
        edges.add(new ArrayList<>());
        edges.get(0).add(1);
        edges.get(0).add(2);
        edges.get(1).add(3);
        edges.get(2).add(3);
        int temp = bfs(edges, n);
        int start = 0;
        int[] temp2 = bfs(edges, n, start);

        pw.println(temp);
        pw.println(Arrays.toString(temp2));
        pw.close();
    }
//    private void bfs(ArrayList<ArrayList<Integer>> edges, ArrayList<Integer> queue, boolean[] visited, int at) {
//        visited[at] = true;
//
//        toDo(edges, at);
//
//        for (int child : edges.get(at)) {
//            if (!visited[child] && Collections.binarySearch(queue, child) < 0) {
//                queue.add(child);
//            }
//        }
//        queue.remove(0);
//        bfs(edges, queue, visited, queue.get(0));
//    }

    private static int bfs(ArrayList<ArrayList<Integer>> edges, int n) {
        LinkedList<Integer> queue = new LinkedList<>();
        int[] dist = new int[n];
        queue.offer(0);
        int N = n - 1;

        while (!queue.isEmpty()) {
            int cur = queue.removeFirst();

            if (cur == N) {
                return dist[cur];
            }

            int newDist = dist[cur] + 1;
            for (int next : edges.get(cur)) {
                if (dist[next] == 0) {
                    dist[next] = newDist;
                    queue.offer(next);
                }
            }

        }
        return -1;
    }

    private static int[] bfs(ArrayList<ArrayList<Integer>> edges, int n, int init) {
        LinkedList<Integer> queue = new LinkedList<>();
        int[] dist = new int[n];
        queue.offer(init);

        while (!queue.isEmpty()) {
            int cur = queue.removeFirst();

            int newDist = dist[cur] + 1;
            for (int next : edges.get(cur)) {
                if (next != cur) {
                    if (dist[next] == 0) {
                        dist[next] = newDist;
                        queue.offer(next);
                    }
                }
            }

        }
        return dist;
    }

//    public void toDo(ArrayList<ArrayList<Integer>> edges, int at) {


//    }

}
