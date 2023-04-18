import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ReorderAStringToMakeItAPalindrome {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String s = br.readLine();
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'A']++;
        }

        int odd = 0;
        int ind = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                ind = i;
                if (odd > 1) {
                    break;
                }
            }
        }
        if (odd > 1) {
            pw.println("NO SOLUTION");
        } else {
            StringBuilder ans = new StringBuilder();
            int[] num = new int[26];
            for (int i = 0; i < 26; i++) {
                num[i] = count[i] / 2;
                ans.append(String.valueOf((char) ('A' + i)).repeat(Math.max(0, num[i])));
            }
            if (odd == 1) {
                ans.append((char) ('A' + ind));
            }
            for (int i = 25; i >= 0; i--) {
                ans.append(String.valueOf((char) ('A' + i)).repeat(Math.max(0, num[i])));
            }
            pw.println(ans.toString());
        }

        pw.close();
    }
}
