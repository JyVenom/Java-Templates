import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

//Run a binary search to find answer. Every time run a binary search, store result so will not rerun
public class MaximizeSmallestDistanceBetweenPoints {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader("file.in");
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        int n = r.nextInt();
        int m = r.nextInt();
        int[][] grass = new int[m][2];
        for (int i = 0; i < m; i++) {
            grass[i][0] = r.nextInt();
            grass[i][1] = r.nextInt();
        }
        r.close();

        Arrays.sort(grass, Comparator.comparingInt(o -> o[0]));
        int ans = -1;
        int low = 0;
        int high = (int) (((grass[m - 1][1] - grass[0][0] + 1) / n) * 1.01);
        int[] pos = new int[high + 2];
        while (low <= high) {
            int mid = (low + high) / 2;
            if (pos[mid] == 0) {
                if (possible(grass, mid, n)) {
                    pos[mid] = 1;
                } else {
                    pos[mid] = -1;
                }
            }
            int next = mid + 1;
            if (pos[next] == 0) {
                if (possible(grass, next, n)) {
                    pos[next] = 1;
                } else {
                    pos[next] = -1;
                }
            }
            if (pos[mid] == 1 && pos[next] == -1) {
                ans = mid;
                break;
            } else if (pos[next] == 1) {
                low = next;
            } else if (pos[mid] == -1) {
                high = mid - 1;
            }
        }

        pw.println(ans);
        pw.close();
    }

    private static boolean possible(int[][] grass, int d, int n) {
        int count = 0;
        int min = 0;
        for (int[] cur : grass) {
            int diff;
            if (min < cur[0]) {
                min = cur[0];
                diff = cur[1] - cur[0] + d;
            } else if (min <= cur[1]) {
                diff = cur[1] - min + d;
            } else {
                continue;
            }
            int num = (diff / d);
            count += num;
            min += (num * d);
            if (count >= n) {
                return true;
            }
        }
        return false;
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 24;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf[cnt++] = (byte) c;
            }
            return new String(buf, 0, cnt);
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
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            din.close();
        }
    }
}
