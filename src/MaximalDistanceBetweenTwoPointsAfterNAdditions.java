import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

public class MaximalDistanceBetweenTwoPointsAfterNAdditions {
    static int[] gap, dsu;

    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int x = fr.nextInt();
        int n = fr.nextInt();
        int n2 = n + 2;
        int[] lights = new int[n2];
        for (int i = 1; i <= n; i++) {
            lights[i] = fr.nextInt();
        }

        lights[0] = 0;
        lights[n + 1] = x;
        Integer[] ord = new Integer[n2];
        for (int i = 0; i < n2; i++)
            ord[i] = i;
        Arrays.sort(ord, Comparator.comparingInt(i -> lights[i]));
        gap = new int[1 + n];
        for (int i = 0; i <= n; i++)
            gap[i] = lights[ord[i + 1]] - lights[ord[i]];
        dsu = new int[1 + n];
        Arrays.fill(dsu, -1);
        for (int i = 0; i < 1 + n + 1; i++)
            lights[ord[i]] = i;
        int temp = 0;
        for (int i = 0; i <= n; i++)
            temp = Math.max(temp, gap[i]);
        int[] ans = new int[n];
        ans[n - 1] = temp;
        for (int i = n; i >= 2; i--)
            ans[i - 2] = temp = Math.max(temp, join(lights[i] - 1, lights[i]));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(ans[i]).append(" ");

        pw.println(sb.toString());
        pw.close();
    }

    private static int find(int i) {
        return dsu[i] < 0 ? i : (dsu[i] = find(dsu[i]));
    }

    private static int join(int i, int j) {
        i = find(i);
        j = find(j);
        if (i != j) {
            if (dsu[i] > dsu[j]) {
                dsu[i] = j;
                gap[j] += gap[i];
                i = j;
            } else {
                if (dsu[i] == dsu[j])
                    dsu[i]--;
                dsu[j] = i;
                gap[i] += gap[j];
            }
        }
        return gap[i];
    }

    private static class FastReader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = dis.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            dis.close();
        }
    }
}
