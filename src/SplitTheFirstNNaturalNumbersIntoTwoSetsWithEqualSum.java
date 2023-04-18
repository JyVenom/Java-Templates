import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class SplitTheFirstNNaturalNumbersIntoTwoSetsWithEqualSum {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Integer>> ans = findAns(n);
        if (ans.size() == 0) {
            pw.println("NO");
        } else {
            pw.println("YES");

            pw.println(ans.get(0).size());
            pw.print(ans.get(0).get(0));
            for (int i = 1; i < ans.get(0).size(); i++) {
                pw.println(" " + ans.get(0).get(i));
            }
            pw.println();

            pw.println(ans.get(1).size());
            pw.print(ans.get(1).get(0));
            for (int i = 1; i < ans.get(1).size(); i++) {
                pw.println(" " + ans.get(1).get(i));
            }
            pw.println();
        }

        pw.close();
    }

    public static ArrayList<ArrayList<Integer>> findAns(int n) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (n <= 2) {
            return ans;
        }

        long value = ((long) n * (n + 1)) / 2;
        if (value % 2 == 1) {
            return ans;
        }

        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

        if (n % 2 == 0) {
            int turn = 1;
            int start = 1;
            int last = n;
            while (start < last) {
                if (turn == 1) {
                    a.add(start);
                    a.add(last);
                    turn = 0;
                } else {
                    b.add(start);
                    b.add(last);
                    turn = 1;
                }

                start++;
                last--;
            }
        } else {
            long rem = value / 2;
            HashSet<Integer> vis = new HashSet<>();
            vis.add(0);

            for (int i = n; i >= 1; i--) {
                if (rem > i) {
                    a.add(i);
                    vis.add(i);
                    rem -= i;
                } else {
                    a.add((int) rem);
                    vis.add((int) rem);
                    break;
                }
            }

            for (int i = 1; i <= n; i++) {
                if (!vis.contains(i))
                    b.add(i);
            }
        }

        ans.add(a);
        ans.add(b);
        return ans;
    }
}
