import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class TowerOfHanoi {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        r.close();

        int num = (1 << n) - 1;
        StringBuilder sb = new StringBuilder(4 * num);
        if (n == 1) {
            sb.append("1 3");
        } else {
            Stack<Integer> a = new Stack<>();
            Stack<Integer> b = new Stack<>();
            Stack<Integer> c = new Stack<>();
            for (int i = n; i > 0; i--) {
                a.add(i);
            }
            if (n % 2 == 0) {
                while (c.size() != n) {
                    if (a.size() == 0) {
                        a.add(b.pop());
                        sb.append("2 1\n");
                    } else if (b.size() == 0) {
                        b.add(a.pop());
                        sb.append("1 2\n");
                    } else {
                        if (a.peek() > b.peek()) {
                            a.add(b.pop());
                            sb.append("2 1\n");
                        } else {
                            b.add(a.pop());
                            sb.append("1 2\n");
                        }
                    }

                    if (c.size() == 0) {
                        c.add(a.pop());
                        sb.append("1 3\n");
                    } else if (a.size() == 0) {
                        a.add(c.pop());
                        sb.append("3 1\n");
                    } else {
                        if (c.peek() > a.peek()) {
                            c.add(a.pop());
                            sb.append("1 3\n");
                        } else {
                            a.add(c.pop());
                            sb.append("3 1\n");
                        }
                    }

                    if (b.size() == 0) {
                        b.add(c.pop());
                        sb.append("3 2\n");
                    } else if (c.size() == 0) {
                        c.add(b.pop());
                        sb.append("2 3\n");
                    } else {
                        if (b.peek() > c.peek()) {
                            b.add(c.pop());
                            sb.append("3 2\n");
                        } else {
                            c.add(b.pop());
                            sb.append("2 3\n");
                        }
                    }
                }
            } else {
                c.add(a.pop());
                sb.append("1 3\n");
                while (c.size() != n) {
                    if (a.size() == 0) {
                        a.add(b.pop());
                        sb.append("2 1\n");
                    } else if (b.size() == 0) {
                        b.add(a.pop());
                        sb.append("1 2\n");
                    } else {
                        if (a.peek() > b.peek()) {
                            a.add(b.pop());
                            sb.append("2 1\n");
                        } else {
                            b.add(a.pop());
                            sb.append("1 2\n");
                        }
                    }

                    if (b.size() == 0) {
                        b.add(c.pop());
                        sb.append("3 2\n");
                    } else if (c.size() == 0) {
                        c.add(b.pop());
                        sb.append("2 3\n");
                    } else {
                        if (b.peek() > c.peek()) {
                            b.add(c.pop());
                            sb.append("3 2\n");
                        } else {
                            c.add(b.pop());
                            sb.append("2 3\n");
                        }
                    }

                    if (c.size() == 0) {
                        c.add(a.pop());
                        sb.append("1 3\n");
                    } else if (a.size() == 0) {
                        a.add(c.pop());
                        sb.append("3 1\n");
                    } else {
                        if (c.peek() > a.peek()) {
                            c.add(a.pop());
                            sb.append("1 3\n");
                        } else {
                            a.add(c.pop());
                            sb.append("3 1\n");
                        }
                    }
                }
            }
        }

        pw.println(num);
        pw.print(sb);
        pw.close();
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
