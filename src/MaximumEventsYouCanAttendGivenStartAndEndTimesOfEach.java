import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class MaximumEventsYouCanAttendGivenStartAndEndTimesOfEach {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = r.nextInt();
        ArrayList<movie> movies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            movies.add(new movie(r.nextInt(), r.nextInt()));
        }

        movies.sort(Comparator.comparingInt(o -> o.end));
        int count = 1;
        int end = movies.get(0).end;
        for (int i = 1; i < movies.size(); i++) {
            if (movies.get(i).start >= end) {
                count++;
                end = movies.get(i).end;
            }
        }

        pw.println(count);
        pw.close();
    }

    private static class movie {
        int start, end;

        public movie(int start, int end) {
            this.start = start;
            this.end = end;
        }
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
