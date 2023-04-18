import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class PrintAllPossibleDistinctArrangementsOfAListOfCharecters {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String s = br.readLine();
        ArrayList<String> all = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            all.add(s.substring(i, i + 1));
        }

        HashSet<String> ans = new HashSet<>();
        findAns(all, ans, "");
        ArrayList<String> ans2 = new ArrayList<>(ans);
        Collections.sort(ans2);

        pw.println(ans2.size());
        for (String an : ans2) {
            pw.println(an);
        }
        pw.close();
    }

    private static void findAns(ArrayList<String> all, HashSet<String> ans, String cur) {
        if (all.size() == 0) {
            ans.add(cur);
            return;
        }
        for (int i = 0; i < all.size(); i++) {
            ArrayList<String> temp = new ArrayList<>(all);
            temp.remove(i);
            String temp2 = cur + all.get(i);
            findAns(temp, ans, temp2);
        }
    }
}
