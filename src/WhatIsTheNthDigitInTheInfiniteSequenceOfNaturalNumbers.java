import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class WhatIsTheNthDigitInTheInfiniteSequenceOfNaturalNumbers {
    public static void main(String[] args) throws IOException {
        Reader r = new Reader();
        PrintWriter pw = new PrintWriter(System.out);

        int q = r.nextInt();

        StringBuilder sb = new StringBuilder(3 * q);
        long loc, dec, ten, prod, num, loc1;
        int mod;
        for (int i = 0; i < q; i++) {
            loc = r.nextLong();

            if (loc <= 9) {
                sb.append(loc);
            } else {
                dec = 1;
                ten = 1;
                prod = 9;
                while (prod < loc) {
                    loc -= prod;

                    dec++;
                    ten *= 10;
                    prod = 9 * dec * ten;
                }

                loc1 = loc - 1;
                num = ten + ((loc1) / dec);
                mod = (int) ((loc1) % dec);
                sb.append(String.valueOf(num).charAt(mod));
            }
            sb.append("\n");
        }
        r.close();

        pw.print(sb);
        pw.close();
    }

    private static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
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

        public long nextLong() throws IOException {
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

        private void close() throws IOException {
            dis.close();
        }
    }
}
