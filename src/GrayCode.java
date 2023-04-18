import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GrayCode {
    private static final int[] leftTree = {0, 1}; // pass these bits to left subtree
    private static final int[] rightTree = {1, 0}; // pass these bits to right subtree

    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        r.close();

        ArrayList<String> all = grayCode(n);
        StringBuilder sb = new StringBuilder();
        for (String cur : all) {
            sb.append(cur).append("\n");
        }

        pw.print(sb);
        pw.close();
    }

    public static ArrayList<String> grayCode(int n) {
        ArrayList<String> result = new ArrayList<>();
        grayCodeHelper(n, result, new StringBuilder(), leftTree);
        return result;
    }

    private static void grayCodeHelper(int n, ArrayList<String> result, StringBuilder sb, int[] bits) {
        if (n == 0) {
            result.add(sb.toString());
        } else {
            sb.append(bits[0]); // refer to first input bit for left subtree
            grayCodeHelper(n - 1, result, sb, leftTree);
            sb.deleteCharAt(sb.length() - 1);

            sb.append(bits[1]); // refer to second input bit for right subtree
            grayCodeHelper(n - 1, result, sb, rightTree);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 3;
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

        private void close() throws IOException {
            dis.close();
        }
    }
}
