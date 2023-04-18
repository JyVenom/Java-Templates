import java.io.*;
import java.util.ArrayList;

public class CheckAncestorDescendantRelationshipInATree {
    private static int time = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        //get n
        int n = Integer.parseInt(br.readLine());

        //initialize edges arraylist and add edges
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }
        edges.get(0).add(1);
        edges.get(1).add(0);
        edges.get(1).add(2);
        edges.get(2).add(1);
        edges.get(0).add(3);
        edges.get(1).add(0);

        //find in and out times of each node
        int[][] times = new int[n][2];
        dfs(edges, times, 0, -1);

        //usage of method
        pw.println(isAnc(times, 0, 1));
        pw.println(notAnc(times, 1, 3));
        pw.println(notAnc(times, 0, 1));

        //close the writer
        pw.close();
    }

    private static void dfs(ArrayList<ArrayList<Integer>> edges, int[][] times, int cur, int parent) {
        times[cur][0] = time++;

        for (int next : edges.get(cur)) {
            if (next != parent) {
                dfs(edges, times, next, cur);
            }
        }

        times[cur][1] = time - 1;
    }

    private static boolean isAnc(int[][] times, int a, int b) {
        return times[a][0] < times[b][0] && times[a][1] >= times[b][1];
    }

    private static boolean notAnc(int[][] times, int a, int b) {
//        return isAnc(times, b, a) || times[a][0] > times[b][0] || times[a][1] < times[b][1] || times[a][0] >= times[b][1] || times[a][1] <= times[b][0];
        return isAnc(times, b, a) || times[a][1] < times[b][0] || times[a][0] > times[b][1];
    }
}
