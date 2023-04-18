import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class PathToEscapeGraphWithPursuers {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int m = fr.nextInt();
        char[][] chars = new char[n][m];
        for (int i = 0; i < n; i++) {
            chars[i] = fr.getCharArr();
        }

        int MAX = n * m;
        int[] monsters = new int[MAX * 2];
        int head = 0, cnt = 0;
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], MAX);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (chars[i][j] == 'M') {
                    dist[i][j] = 0;
                    monsters[cnt++] = i;
                    monsters[cnt++] = j;
                }
            }
        }
        int[] row = {-1, 0, 0, 1};
        int[] col = {0, -1, 1, 0};
        char[] dirs = {'U', 'L', 'R', 'D'};
        while (cnt > 0) {
            int i = monsters[head++];
            int j = monsters[head++];
            cnt -= 2;
            int d = dist[i][j] + 1;
            for (int k = 0; k < 4; k++) {
                int i_ = i + row[k];
                int j_ = j + col[k];
                if (i_ >= 0 && i_ < n && j_ >= 0 && j_ < m && chars[i_][j_] != '#' && dist[i_][j_] > d) {
                    dist[i_][j_] = d;
                    monsters[head + cnt++] = i_;
                    monsters[head + cnt++] = j_;
                }
            }
        }
        head = 0;
        cnt = 0;
        out:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (chars[i][j] == 'A') {
                    dist[i][j] = 0;
                    monsters[head + cnt++] = i;
                    monsters[head + cnt++] = j;
                    break out;
                }
            }
        }
        int[][] dir = new int[n][m];
        int N = n - 1;
        int M = m - 1;
        while (cnt > 0) {
            int i = monsters[head++];
            int j = monsters[head++];
            cnt -= 2;
            if (i == 0 || i == N || j == 0 || j == M) {
                pw.println("YES");
                pw.println(dist[i][j]);
                StringBuilder sb = new StringBuilder();
                while (dist[i][j] > 0) {
                    int h = dir[i][j];
                    sb.append(dirs[h]);
                    i -= row[h];
                    j -= col[h];
                }
                pw.print(sb.reverse());
                pw.close();
                return;
            }
            int d = dist[i][j] + 1;
            for (int k = 0; k < 4; k++) {
                int i_ = i + row[k];
                int j_ = j + col[k];
                if (chars[i_][j_] != '#' && dist[i_][j_] > d) {
                    dist[i_][j_] = d;
                    dir[i_][j_] = k;
                    monsters[head + cnt++] = i_;
                    monsters[head + cnt++] = j_;
                }
            }
        }
        pw.println("NO");
        pw.close();
    }

    private static class FastReader {
        final private int BUFFER_SIZE = 1 << 20;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastReader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private char[] getCharArr() throws IOException {
            char[] buf = new char[1024]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (char) c;
            }
            return buf;
        }

        private int nextInt() throws IOException {
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
