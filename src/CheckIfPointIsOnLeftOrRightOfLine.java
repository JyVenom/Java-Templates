import java.io.DataInputStream;
import java.io.IOException;

public class CheckIfPointIsOnLeftOrRightOfLine {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();

        int t = fr.nextInt();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            int x1 = fr.nextInt();
            int y1 = fr.nextInt();
            int x2 = fr.nextInt();
            int y2 = fr.nextInt();
            int x3 = fr.nextInt();
            int y3 = fr.nextInt();

            if (x1 == x2) {
                if (x3 == x1) {
                    sb.append("TOUCH");
                } else if (y1 < y2) {
                    sb.append(x3 < x1 ? "LEFT" : "RIGHT");
                } else {
                    sb.append(x3 < x1 ? "RIGHT" : "LEFT");
                }
            } else if (y1 == y2) {
                if (y3 == y1) {
                    sb.append("TOUCH");
                } else if (x1 < x2) {
                    sb.append(y3 > y1 ? "LEFT" : "RIGHT");
                } else {
                    sb.append(y3 > y1 ? "RIGHT" : "LEFT");
                }
            } else {
                double line = (((double) (y2 - y1) / (double) (x2 - x1)) * (x3 - x1)) + y1;
                if (line == y3) {
                    sb.append("TOUCH");
                } else if (x1 < x2) {
                    sb.append(y3 > line ? "LEFT" : "RIGHT");
                } else {
                    sb.append(y3 > line ? "RIGHT" : "LEFT");
                }
            }
            sb.append("\n");
        }
        fr.close();

        System.out.print(sb);
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
