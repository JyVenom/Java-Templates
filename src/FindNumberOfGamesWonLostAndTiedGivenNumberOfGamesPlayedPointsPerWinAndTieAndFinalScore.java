import java.io.*;
import java.util.InputMismatchException;

public class FindNumberOfGamesWonLostAndTiedGivenNumberOfGamesPlayedPointsPerWinAndTieAndFinalScore {
    public static void main(String[] args) throws Exception {
        FastReader ir = new FastReader(System.in);
        Output pw = new Output(System.out);

        long n = ir.nextLong(), p = ir.nextLong(), w = ir.nextLong(), d = ir.nextLong();
        for (long y = 0; y < w; y++) {
            long x = (p - y * d) / w;
            if (x * w + y * d == p && x >= 0 && x + y <= n) {
                pw.println(x, y, n - x - y);
//                pw.println(x + " " + y + " " + (n - x - y));
                pw.close();
                return;
            }
        }
        pw.println("-1");
        pw.close();

//        InputReader ir = new InputReader();
//        PrintWriter pw = new PrintWriter(System.out);
//
//        long n = ir.nextLong();
//        long p = ir.nextLong();
//        int w = ir.nextInt();
//        int d = ir.nextInt();
//
//        if (w * n < p) {
//            pw.println(-1);
//            pw.close();
//            return;
//        }
//        long wins = (long) Math.ceil((double) p / (double) w);
//        long ties = 0L;
//        long losses = n - wins;
//        int dif = w - d;
//        long cur = wins * w;
//        long tmp = cur - p;
//        long num = (long) Math.ceil((double) tmp / (double) dif);
//        wins -= num;
//        if (wins < 0) {
//            pw.println(-1);
//            pw.close();
//            return;
//        }
//        ties += num;
//        cur -= num * dif;
//        if (cur < p && d == dif) {
//            pw.println(-1);
//            pw.close();
//            return;
//        }
//        while (cur != p) {
//            if (cur > p) {
//                tmp = cur - p;
//                num = (long) Math.ceil((double) tmp / (double) dif);
//                wins -= num;
//                if (wins < 0) {
//                    pw.println(-1);
//                    pw.close();
//                    return;
//                }
//                ties += num;
//                cur -= num * dif;
//            } else {
//                tmp = p - cur;
//                num = (long) Math.ceil((double) tmp / (double) d);
//                losses -= num;
//                if (losses < 0) {
//                    pw.println(-1);
//                    pw.close();
//                    return;
//                }
//                ties += num;
//                cur += num * d;
//            }
//        }
//
//        pw.println(wins + " " + ties + " " + losses);
//        pw.close();
    }

    static class FastReader {
        final private int BUFFER_SIZE = 64;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer;
        private int bytesRead;

        public FastReader(InputStream is) {
            din = new DataInputStream(is);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public long nextLong() {
            long ret = 0;
            byte c = skipToDigit();
            boolean neg = (c == '-');
            if (neg) {
                c = read();
            }
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg) {
                return -ret;
            }
            return ret;
        }

        private boolean isDigit(byte b) {
            return b >= '0' && b <= '9';
        }

        private byte skipToDigit() {
            byte ret = read();
            while (!isDigit(ret) && ret != '-') ret = read();
            return ret;
        }

        private void fillBuffer() {
            try {
                bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
                throw new InputMismatchException();
            }
            if (bytesRead == -1) {
                buffer[0] = -1;
            }
        }

        private byte read() {
            if (bytesRead == -1) {
                throw new InputMismatchException();
            } else if (bufferPointer == bytesRead) {
                fillBuffer();
            }
            return buffer[bufferPointer++];
        }

    }

    private static class InputReader2 {
        private final int BUFFER_SIZE = 64;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader2() {
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

        private long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            }
            while ((c = read()) >= '0' && c <= '9');
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
    }

    static class Output implements Closeable, Flushable {
        public StringBuilder sb;
        public OutputStream os;
        public int BUFFER_SIZE;
        public boolean autoFlush;
        public String LineSeparator;

        public Output(OutputStream os) {
            this(os, 1 << 16);
        }

        public Output(OutputStream os, int bs) {
            BUFFER_SIZE = bs;
            sb = new StringBuilder(BUFFER_SIZE);
            this.os = new BufferedOutputStream(os, 1 << 17);
            autoFlush = false;
            LineSeparator = System.lineSeparator();
        }

        public void print(String s) {
            sb.append(s);
            if (autoFlush) {
                flush();
            } else if (sb.length() > BUFFER_SIZE >> 1) {
                flushToBuffer();
            }
        }

        public void println(Object... o) {
            for (int i = 0; i < o.length; i++) {
                if (i != 0) {
                    print(" ");
                }
                print(String.valueOf(o[i]));
            }
            println();
        }

        public void println(String s) {
            sb.append(s);
            println();
            if (autoFlush) {
                flush();
            } else if (sb.length() > BUFFER_SIZE >> 1) {
                flushToBuffer();
            }
        }

        public void println() {
            sb.append(LineSeparator);
        }

        private void flushToBuffer() {
            try {
                os.write(sb.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb = new StringBuilder(BUFFER_SIZE);
        }

        public void flush() {
            try {
                flushToBuffer();
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void close() {
            flush();
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
