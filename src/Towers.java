import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Towers {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        int[] cubes = new int[n];
        for (int i = 0; i < n; i++) {
            cubes[i] = fr.nextInt();
        }

        ArrayList<Integer> towers = new ArrayList<>();
        towers.add(cubes[0]);
        for (int i = 1; i < n; i++) {
            if (towers.get(towers.size() - 1) <= cubes[i]) {
                towers.add(cubes[i]);
            } else {
                int loc = binSearch(towers, cubes[i], towers.size() - 1);
                towers.set(loc, cubes[i]);
            }
        }

        pw.println(towers.size());
        pw.close();
    }

    private static int binSearch(ArrayList<Integer> arr, int key, int high) {
        int low = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) > key && (mid == 0 || arr.get(mid - 1) <= key)) {
                return mid;
            } else if (arr.get(mid) <= key) {
                low = mid + 1;
            } else if (arr.get(mid) > key) {
                high = mid - 1;
            }
        }
        return -1;
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
