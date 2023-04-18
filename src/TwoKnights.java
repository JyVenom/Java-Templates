import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TwoKnights {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        int n = Integer.parseInt(br.readLine());

        pw.println(0);
        if (n > 1) {
            pw.println(6);
            if (n > 2) {
                pw.println(28);
                long prev = 28;
                for (int i = 4; i <= n; i++) {
                    int I = i - 1;

                    int add = 2 * i - 1;
                    long temp = prev + (long) add * I * I;
                    temp -= 8L * i - 18;
                    temp += ((2L * i - 1) * (2L * I) - 4) / 2;
                    pw.println(temp);
                    prev = temp;
                }
            }
        }
        pw.close();
    }
}
