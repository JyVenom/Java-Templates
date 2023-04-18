import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ForestQueries {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        int q = r.nextInt();
        int N = n + 1;
        int[][] forest = new int[N][N];
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (r.isTree()) {
                    forest[i][j] = 1;
                }
            }
            r.read();
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                forest[i][j] += forest[i][j - 1];
            }
        }
        for (int j = 1; j < N; j++) {
            for (int i = 1; i < N; i++) {
                forest[i][j] += forest[i - 1][j];
            }
        }
        StringBuilder sb = new StringBuilder();
        while (q-- > 0) {
            int y1 = r.nextInt();
            int x1 = r.nextInt();
            int y2 = r.nextInt();
            int x2 = r.nextInt();
            sb.append(forest[y2][x2] - forest[y1 - 1][x2] - forest[y2][x1 - 1] + forest[y1 - 1][x1 - 1]).append("\n");
        }

        pw.println(sb);
        pw.close();
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
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

        private boolean isTree() throws IOException {
            return read() == '*';
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