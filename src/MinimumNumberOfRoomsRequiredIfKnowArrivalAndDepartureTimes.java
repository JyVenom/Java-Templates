import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MinimumNumberOfRoomsRequiredIfKnowArrivalAndDepartureTimes {
    public static void main(String[] args) throws IOException {
        FastReader fr = new FastReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = fr.nextInt();
        ArrayList<state> states = new ArrayList<>(2 * n);
        for (int i = 0; i < n; i++) {
            states.add(new state(i, fr.nextInt(), true));
            states.add(new state(i, fr.nextInt(), false));
        }

        states.sort((o1, o2) -> {
            if (o1.loc < o2.loc) {
                return -1;
            } else if (o1.loc == o2.loc) {
                if (o1.come && !o2.come) {
                    return -1;
                } else if (o1.come || !o2.come) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        });
        ArrayList<Integer> empty = new ArrayList<>();
        int[] used = new int[n];
        int count = 2;
        used[states.get(0).who] = 1;
        for (int i = 1; i < 2 * n; i++) {
            state cur = states.get(i);

            if (cur.come) {
                if (empty.isEmpty()) {
                    used[cur.who] = count++;
                } else {
                    used[cur.who] = empty.get(0);
                    empty.remove(0);
                }
            } else {
                empty.add(used[cur.who]);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(count - 1).append("\n");
        sb.append(used[0]);
        for (int i = 1; i < n; i++) {
            sb.append(" ").append(used[i]);
        }
        pw.println(sb);
        pw.close();
    }

    private static class state {
        int who, loc;
        boolean come;

        public state(int who, int loc, boolean come) {
            this.who = who;
            this.loc = loc;
            this.come = come;
        }
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
