import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class TarjanSCCSimple {
    private static final ArrayList<ArrayList<Integer>> scc = new ArrayList<>();
    private static int Time;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        int N = Integer.parseInt(br.readLine());
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(br.readLine());
            int temp = Integer.parseInt(st.nextToken()) - 1;
            while (temp != -1) {
                list.add(temp);
                temp = Integer.parseInt(st.nextToken()) - 1;
            }
            list.add(i);
            edges.add(list);
        }

        Time = 0;
        SCC(edges, N);

        pw.println(scc);
        pw.close();
    }

    private static void SCCUtil(ArrayList<ArrayList<Integer>> edges, Stack<Integer> st, boolean[] stackMember, int[] low, int[] disc, int u) {
        disc[u] = Time;
        low[u] = Time;
        Time += 1;
        stackMember[u] = true;
        st.push(u);

        int n;

        for (Integer integer : edges.get(u)) {
            n = integer;

            if (disc[n] == -1) {
                SCCUtil(edges, st, stackMember, low, disc, n);
                low[u] = Math.min(low[u], low[n]);
            } else if (stackMember[n]) {
                low[u] = Math.min(low[u], disc[n]);
            }
        }

        int w = -1;
        if (low[u] == disc[u]) {
            ArrayList<Integer> temp = new ArrayList<>();
            while (w != u) {
                w = st.pop();
                temp.add(w);
                stackMember[w] = false;
            }
            scc.add(temp);
        }
    }

    private static void SCC(ArrayList<ArrayList<Integer>> edges, int N) {
        int[] disc = new int[N];
        int[] low = new int[N];
        for (int i = 0; i < N; i++) {
            disc[i] = -1;
            low[i] = -1;
        }

        boolean[] stackMember = new boolean[N];
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < N; i++) {
            if (disc[i] == -1)
                SCCUtil(edges, st, stackMember, low, disc, i);
        }
    }
}
