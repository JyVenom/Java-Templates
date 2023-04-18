import java.io.DataInputStream;
import java.io.IOException;

public class MaxSubarraySumIfArrayIsMadeOfMonthsOfGivenLength {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();

        int n = ir.nextInt();
        long x = ir.nextLong();
        long[] days = new long[n];
        for (int i = 0; i < n; i++) {
            days[i] = ir.nextLong();
        }

        long max;
        long sum = 0;
        long rem = x;
        long day = 1;
        int month = 1;
        if (rem > days[0]) {
            rem -= days[0];
            month = n;
            sum += ((1 + days[0]) * days[0]) / 2;
        }
        while (rem > 0) {
            month--;
            if (rem >= days[month]) {
                rem -= days[month];
                sum += ((1 + days[month]) * days[month]) / 2;
            } else {
                day = days[month] - rem + 1;
                sum += ((day + days[month]) * rem) / 2;
                rem = 0;
            }
        }
        max = sum;
        for (int i = 1; i < n; i++) {
            rem = days[i];
            sum += ((1 + days[i]) * days[i]) / 2;
            if (day != 1) {
                long tmp = days[month] - day + 1;
                if (rem >= tmp) {
                    rem -= tmp;
                    sum -= ((day + days[month]) * tmp) / 2;
                    day = 1;
                    month = (month + 1) % n;
                } else {
                    sum -= ((day + (day + rem - 1)) * rem) / 2;
                    day += rem;
                    rem = 0;
                }
            }
            while (rem > 0) {
                if (rem >= days[month]) {
                    rem -= days[month];
                    sum -= ((1 + days[month]) * days[month]) / 2;
                    month = (month + 1) % n;
                } else {
                    sum -= ((1 + rem) * rem) / 2;
                    day = rem + 1;
                    rem = 0;
                }
            }
            if (sum > max) {
                max = sum;
            }
        }

        System.out.println(max);
    }

    private static class InputReader {
        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            return ret;
        }

        private long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
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
}
