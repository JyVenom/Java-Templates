import java.io.*;

public class FastIO3 {
    public static void main(String[] args) throws Exception {
        //VARS
//        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        long start;

        //PARAMS
        int t = 10; // Number if trials
        int n = 100000000; // Number of values
        int m = 1000; // Size of values

        //GENERATING INPUT DATA
        System.out.println("Generating Input Data");
        start = System.currentTimeMillis();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.in")));
        for (int i = 0; i < n; i++) {
            pw.print((int) (Math.random() * m) + " ");
        }
        pw.println();
        pw.close();
        System.out.println("Generation done in: " + ((double) (System.currentTimeMillis() - start) / 1000) + " Seconds");

        //START
        System.out.println("Start");
        pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));
        System.gc(); //GC BEFORE NEXT RUN
        {
            //Using Input Reader 1 (custom method)
            int sum = 0;
            for (int j = 0; j < t; j++) {
                System.gc(); //GC BEFORE NEXT RUN
                {
                    InputReader1 in1 = new InputReader1(new FileInputStream("file.in"));
                    start = System.currentTimeMillis();
                    int[] data = new int[n];
                    for (int i = 0; i < n; i++) {
                        data[i] = in1.nextInt(); //don't need to worry about spaces or new line, taken care of by method
                    }
                    sum += (System.currentTimeMillis() - start);
                    pw.println(data[0]);
                }
            }
            System.out.println(sum / t);
        }
        System.gc(); //GC BEFORE NEXT RUN
        {
            //Using Input Reader 2 (custom method)
            int sum = 0;
            for (int j = 0; j < t; j++) {
                System.gc(); //GC BEFORE NEXT RUN
                {
                    InputReader2 in2 = new InputReader2("file.in");
                    start = System.currentTimeMillis();
                    int[] data = new int[n];
                    for (int i = 0; i < n; i++) {
                        data[i] = in2.nextInt(); //don't need to worry about spaces or new line, taken care of by method
                    }
                    sum += (System.currentTimeMillis() - start);
                    pw.println(data[0]);
                }
            }
            System.out.println(sum / t);
        }
        System.gc(); //GC BEFORE NEXT RUN
        {
            //Using Input Reader 3 (custom method)
            int sum = 0;
            for (int j = 0; j < t; j++) {
                System.gc(); //GC BEFORE NEXT RUN
                {
                    InputReader3 in3 = new InputReader3("file.in");
                    start = System.currentTimeMillis();
                    int[] data = new int[n];
                    for (int i = 0; i < n; i++) {
                        data[i] = in3.nextInt(); //don't need to worry about spaces or new line, taken care of by method
                    }
                    sum += (System.currentTimeMillis() - start);
                    pw.println(data[0]);
                }
            }
            System.out.println(sum / t);
        }
        System.gc(); //GC BEFORE NEXT RUN
        {
            //Using Input Reader 4 (custom method)
            int sum = 0;
            for (int j = 0; j < t; j++) {
                System.gc(); //GC BEFORE NEXT RUN
                {
                    InputReader4 in4 = new InputReader4("file.in");
                    start = System.currentTimeMillis();
                    int[] data = new int[n];
                    for (int i = 0; i < n; i++) {
                        data[i] = in4.nextInt(); //don't need to worry about spaces or new line, taken care of by method
                    }
                    sum += (System.currentTimeMillis() - start);
                    pw.println(data[0]);
                }
            }
            System.out.println(sum / t);
        }
        System.gc(); //GC BEFORE NEXT RUN
        {
            //Using Input Reader 5 (custom method)
            int sum = 0;
            for (int j = 0; j < t; j++) {
                System.gc(); //GC BEFORE NEXT RUN
                {
                    InputReader5 in5 = new InputReader5(new FileInputStream("file.in"));
                    start = System.currentTimeMillis();
                    int[] data = new int[n];
                    for (int i = 0; i < n; i++) {
                        data[i] = in5.nextInt(); //don't need to worry about spaces or new line, taken care of by method
                    }
                    sum += (System.currentTimeMillis() - start);
                    pw.println(data[0]);
                }
            }
            System.out.println(sum / t);
        }
//        System.gc(); //GC BEFORE NEXT RUN
//        {
//            //Using Array Streams
//            start = System.currentTimeMillis();
//            int[] data;
//            data = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
//            System.out.println(System.currentTimeMillis() - start);
//            pw.println(data[0]);
//        }

        //close
        pw.close();
    }

    private static class InputReader1 {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader1(InputStream stream) {
            this.stream = stream;
        }

        private void close() throws IOException {
            stream.close();
        }

        private int read() throws IOException {
            if (numChars == -1) {
                throw new IOException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new IOException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        private int nextInt() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new IOException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private String nextLine() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private double nextDouble() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.') {
                if (c == 'e' || c == 'E') {
                    return res * Math.pow(10, nextInt());
                }
                if (c < '0' || c > '9') {
                    throw new IOException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.') {
                c = read();
                double m = 1;
                while (!isSpaceChar(c)) {
                    if (c == 'e' || c == 'E') {
                        return res * Math.pow(10, nextInt());
                    }
                    if (c < '0' || c > '9') {
                        throw new IOException();
                    }
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        private long nextLong() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new IOException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        private interface SpaceCharFilter {
            boolean isSpaceChar(int ch);
        }
    }

    private static class InputReader2 {
        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader2() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public InputReader2(String file_name) throws IOException {
            dis = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private String nextLine() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
        }

//        private String nextLine() throws IOException {
//            byte[] buf = new byte[BUFFER_SIZE]; // line length
//            int cnt = 0, c;
//            while ((c = read()) != -1) {
//                if (c == '\n')
//                    break;
//                buf[cnt++] = (byte) c;
//            }
//            return new String(buf, 0, cnt);
//        }

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

        private double nextDouble() throws IOException {
            double ret = 0, div = 1;
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
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
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

    private static class InputReader3 {
        final private int BUFFER_SIZE = 1 << 16;
        private final FileInputStream fis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader3(String file_name) throws IOException {
            fis = new FileInputStream(file_name);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

//        private String nextLine() throws IOException {
//            byte[] buf = new byte[BUFFER_SIZE]; // line length
//            int cnt = 0, c;
//            while ((c = read()) != -1) {
//                if (c == '\n')
//                    break;
//                buf[cnt++] = (byte) c;
//            }
//            return new String(buf, 0, cnt);
//        }

        private String nextLine() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
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

        private double nextDouble() throws IOException {
            double ret = 0, div = 1;
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
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = fis.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        private void close() throws IOException {
            fis.close();
        }
    }

    private static class InputReader4 {
        final private int BUFFER_SIZE = 1 << 16;
        private final BufferedInputStream bis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader4(String file_name) throws IOException {
            bis = new BufferedInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

//        private String nextLine() throws IOException {
//            byte[] buf = new byte[BUFFER_SIZE]; // line length
//            int cnt = 0, c;
//            while ((c = read()) != -1) {
//                if (c == '\n')
//                    break;
//                buf[cnt++] = (byte) c;
//            }
//            return new String(buf, 0, cnt);
//        }

        private String nextLine() throws IOException {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == -1;
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

        private double nextDouble() throws IOException {
            double ret = 0, div = 1;
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
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = bis.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        private void close() throws IOException {
            bis.close();
        }
    }

    private static class InputReader5 {
        /**
         * The default size of the InputReader's buffer is 2<sup>16</sup>.
         */
        private static final int DEFAULT_BUFFER_SIZE = 1 << 16;

        /**
         * The default stream for the InputReader is standard input.
         */
        private static final InputStream DEFAULT_STREAM = System.in;

        /**
         * The maximum number of accurate decimal digits the method {@link #nextDoubleFast() nextDoubleFast()} can read.
         * Currently this value is set to 21 because it is the maximum number of digits a double precision float can have at the moment.
         */
        private static final int MAX_DECIMAL_PRECISION = 21;
        // End Of File (EOF) character
        private static final byte EOF = -1;
        // New line character: '\n'
        private static final byte NEW_LINE = 10;
        // Space character: ' '
        private static final byte SPACE = 32;
        // Dash character: '-'
        private static final byte DASH = 45;
        // Dot character: '.'
        private static final byte DOT = 46;
        // Primitive double lookup table used for optimizations.
        private static final double[][] doubles = {
                {0.0d, 0.00d, 0.000d, 0.0000d, 0.00000d, 0.000000d, 0.0000000d, 0.00000000d, 0.000000000d, 0.0000000000d, 0.00000000000d, 0.000000000000d, 0.0000000000000d, 0.00000000000000d, 0.000000000000000d, 0.0000000000000000d, 0.00000000000000000d, 0.000000000000000000d, 0.0000000000000000000d, 0.00000000000000000000d, 0.000000000000000000000d},
                {0.1d, 0.01d, 0.001d, 0.0001d, 0.00001d, 0.000001d, 0.0000001d, 0.00000001d, 0.000000001d, 0.0000000001d, 0.00000000001d, 0.000000000001d, 0.0000000000001d, 0.00000000000001d, 0.000000000000001d, 0.0000000000000001d, 0.00000000000000001d, 0.000000000000000001d, 0.0000000000000000001d, 0.00000000000000000001d, 0.000000000000000000001d},
                {0.2d, 0.02d, 0.002d, 0.0002d, 0.00002d, 0.000002d, 0.0000002d, 0.00000002d, 0.000000002d, 0.0000000002d, 0.00000000002d, 0.000000000002d, 0.0000000000002d, 0.00000000000002d, 0.000000000000002d, 0.0000000000000002d, 0.00000000000000002d, 0.000000000000000002d, 0.0000000000000000002d, 0.00000000000000000002d, 0.000000000000000000002d},
                {0.3d, 0.03d, 0.003d, 0.0003d, 0.00003d, 0.000003d, 0.0000003d, 0.00000003d, 0.000000003d, 0.0000000003d, 0.00000000003d, 0.000000000003d, 0.0000000000003d, 0.00000000000003d, 0.000000000000003d, 0.0000000000000003d, 0.00000000000000003d, 0.000000000000000003d, 0.0000000000000000003d, 0.00000000000000000003d, 0.000000000000000000003d},
                {0.4d, 0.04d, 0.004d, 0.0004d, 0.00004d, 0.000004d, 0.0000004d, 0.00000004d, 0.000000004d, 0.0000000004d, 0.00000000004d, 0.000000000004d, 0.0000000000004d, 0.00000000000004d, 0.000000000000004d, 0.0000000000000004d, 0.00000000000000004d, 0.000000000000000004d, 0.0000000000000000004d, 0.00000000000000000004d, 0.000000000000000000004d},
                {0.5d, 0.05d, 0.005d, 0.0005d, 0.00005d, 0.000005d, 0.0000005d, 0.00000005d, 0.000000005d, 0.0000000005d, 0.00000000005d, 0.000000000005d, 0.0000000000005d, 0.00000000000005d, 0.000000000000005d, 0.0000000000000005d, 0.00000000000000005d, 0.000000000000000005d, 0.0000000000000000005d, 0.00000000000000000005d, 0.000000000000000000005d},
                {0.6d, 0.06d, 0.006d, 0.0006d, 0.00006d, 0.000006d, 0.0000006d, 0.00000006d, 0.000000006d, 0.0000000006d, 0.00000000006d, 0.000000000006d, 0.0000000000006d, 0.00000000000006d, 0.000000000000006d, 0.0000000000000006d, 0.00000000000000006d, 0.000000000000000006d, 0.0000000000000000006d, 0.00000000000000000006d, 0.000000000000000000006d},
                {0.7d, 0.07d, 0.007d, 0.0007d, 0.00007d, 0.000007d, 0.0000007d, 0.00000007d, 0.000000007d, 0.0000000007d, 0.00000000007d, 0.000000000007d, 0.0000000000007d, 0.00000000000007d, 0.000000000000007d, 0.0000000000000007d, 0.00000000000000007d, 0.000000000000000007d, 0.0000000000000000007d, 0.00000000000000000007d, 0.000000000000000000007d},
                {0.8d, 0.08d, 0.008d, 0.0008d, 0.00008d, 0.000008d, 0.0000008d, 0.00000008d, 0.000000008d, 0.0000000008d, 0.00000000008d, 0.000000000008d, 0.0000000000008d, 0.00000000000008d, 0.000000000000008d, 0.0000000000000008d, 0.00000000000000008d, 0.000000000000000008d, 0.0000000000000000008d, 0.00000000000000000008d, 0.000000000000000000008d},
                {0.9d, 0.09d, 0.009d, 0.0009d, 0.00009d, 0.000009d, 0.0000009d, 0.00000009d, 0.000000009d, 0.0000000009d, 0.00000000009d, 0.000000000009d, 0.0000000000009d, 0.00000000000009d, 0.000000000000009d, 0.0000000000000009d, 0.00000000000000009d, 0.000000000000000009d, 0.0000000000000000009d, 0.00000000000000000009d, 0.000000000000000000009d}
        };
        // Primitive data type lookup tables used for optimizations
        private static final int[] ints = new int[58];

        static {
            int value = 0;
            for (int i = 48; i < 58; i++) ints[i] = value++;
        }

        // Variables associated with the byte buffer.
        private final byte[] buf;
        private final InputStream stream;
        // 'c' is used to refer to the current character in the stream
        private int c;
        private int bufIndex;
        private int numBytesRead;
        // A reusable character buffer when reading string data.
        private char[] charBuffer;

        /**
         * Create an InputReader that reads from standard input.
         */
        public InputReader5() {
            this(DEFAULT_STREAM, DEFAULT_BUFFER_SIZE);
        }

        /**
         * Create an InputReader that reads from standard input.
         *
         * @param bufferSize The buffer size for this input reader.
         */
        public InputReader5(int bufferSize) {
            this(DEFAULT_STREAM, bufferSize);
        }

        /**
         * Create an InputReader that reads from standard input.
         *
         * @param stream Takes an InputStream as a parameter to read from.
         */
        public InputReader5(InputStream stream) {
            this(stream, DEFAULT_BUFFER_SIZE);
        }

        /**
         * Create an InputReader that reads from standard input.
         *
         * @param stream     Takes an {@link InputStream#InputStream() InputStream} as a parameter to read from.
         * @param bufferSize The size of the buffer to use.
         */
        public InputReader5(InputStream stream, int bufferSize) {
            if (stream == null || bufferSize <= 0)
                throw new IllegalArgumentException();
            buf = new byte[bufferSize];
            charBuffer = new char[128];
            this.stream = stream;
        }

        /**
         * Reads a single character from the input stream.
         *
         * @return Returns the byte value of the next character in the buffer and EOF
         * at the end of the stream.
         * @throws IOException throws exception if there is no more data to read
         */
        private byte read() throws IOException {

            if (numBytesRead == EOF) throw new IOException();

            if (bufIndex >= numBytesRead) {
                bufIndex = 0;
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF)
                    return EOF;
            }

            return buf[bufIndex++];
        }

        /**
         * Read values from the input stream until you reach a character with a
         * higher ASCII value than 'token'.
         *
         * @param token The token is a value which we use to stop reading junk out of
         *              the stream.
         * @return Returns 0 if a value greater than the token was reached or -1 if
         * the end of the stream was reached.
         * @throws IOException Throws exception at end of stream.
         */
        private int readJunk(int token) throws IOException {

            if (numBytesRead == EOF) return EOF;

            // Seek to the first valid position index
            do {

                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > token) return 0;
                    bufIndex++;
                }

                // reload buffer
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF) return EOF;
                bufIndex = 0;

            } while (true);

        }

        /**
         * Reads a single byte from the input stream.
         *
         * @return The next byte in the input stream
         * @throws IOException Throws exception at end of stream.
         */
        private byte nextByte() throws IOException {
            return (byte) nextInt();
        }

        /**
         * Reads a 32 bit signed integer from input stream.
         *
         * @return The next integer value in the stream.
         * @throws IOException Throws exception at end of stream.
         */
        private int nextInt() throws IOException {

            if (readJunk(DASH - 1) == EOF) throw new IOException();
            int sgn = 1, res = 0;

            c = buf[bufIndex];
            if (c == DASH) {
                sgn = -1;
                bufIndex++;
            }

            do {

                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > SPACE) {
                        res = (res << 3) + (res << 1);
                        res += ints[buf[bufIndex++]];
                    } else {
                        bufIndex++;
                        return res * sgn;
                    }
                }

                // Reload buffer
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF) return res * sgn;
                bufIndex = 0;

            } while (true);

        }

        /**
         * Reads a 64 bit signed long from input stream.
         *
         * @return The next long value in the stream.
         * @throws IOException Throws exception at end of stream.
         */
        private long nextLong() throws IOException {

            if (readJunk(DASH - 1) == EOF) throw new IOException();
            int sgn = 1;
            long res = 0L;
            c = buf[bufIndex];
            if (c == DASH) {
                sgn = -1;
                bufIndex++;
            }

            do {

                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > SPACE) {
                        res = (res << 3) + (res << 1);
                        res += ints[buf[bufIndex++]];
                    } else {
                        bufIndex++;
                        return res * sgn;
                    }
                }

                // Reload buffer
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF) return res * sgn;
                bufIndex = 0;

            } while (true);

        }

        /**
         * Doubles the size of the internal char buffer for strings
         */
        private void doubleCharBufferSize() {
            char[] newBuffer = new char[charBuffer.length << 1];
            System.arraycopy(charBuffer, 0, newBuffer, 0, charBuffer.length);
            charBuffer = newBuffer;
        }

        /**
         * Reads a line from the input stream.
         *
         * @return Returns a line from the input stream in the form a String not
         * including the new line character. Returns <code>null</code> when there are
         * no more lines.
         * @throws IOException Throws IOException when something terrible happens.
         */
        private String nextLine() throws IOException {

            try {
                c = read();
            } catch (IOException e) {
                return null;
            }
            if (c == NEW_LINE) return ""; // Empty line
            if (c == EOF) return null; // EOF

            int i = 0;
            charBuffer[i++] = (char) c;

            do {

                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] != NEW_LINE) {
                        if (i == charBuffer.length) doubleCharBufferSize();
                        charBuffer[i++] = (char) buf[bufIndex++];
                    } else {
                        bufIndex++;
                        return new String(charBuffer, 0, i);
                    }
                }

                // Reload buffer
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF)
                    return new String(charBuffer, 0, i);
                bufIndex = 0;

            } while (true);

        }

        // Reads a string of characters from the input stream.
        // The delimiter separating a string of characters is set to be:
        // any ASCII value <= 32 meaning any spaces, new lines, EOF, tabs...
        private String nextString() throws IOException {
            if (numBytesRead == EOF) return null;
            if (readJunk(SPACE) == EOF) return null;

            for (int i = 0; ; ) {
                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > SPACE) {
                        if (i == charBuffer.length) doubleCharBufferSize();
                        charBuffer[i++] = (char) buf[bufIndex++];
                    } else {
                        bufIndex++;
                        return new String(charBuffer, 0, i);
                    }
                }

                // Reload buffer
                numBytesRead = stream.read(buf);
                if (numBytesRead == EOF) return new String(charBuffer, 0, i);
                bufIndex = 0;
            }
        }

        // Returns an exact value a double value from the input stream.
        private double nextDouble() throws IOException {
            String doubleVal = nextString();
            if (doubleVal == null) throw new IOException();
            return Double.parseDouble(doubleVal);
        }

        // Very quickly reads a double value from the input stream (~3x faster than nextDouble()). However,
        // this method may provide a slightly less accurate reading than .nextDouble() if there are a lot
        // of digits (~16+). In particular, it will only read double values with at most 21 digits after
        // the decimal point and the reading my be as inaccurate as ~5*10^-16 from the true value.
        private double nextDoubleFast() throws IOException {
            c = read();
            int sgn = 1;
            while (c <= SPACE) c = read(); // while c is either: ' ', '\n', EOF
            if (c == DASH) {
                sgn = -1;
                c = read();
            }
            double res = 0.0;
            // while c is not: ' ', '\n', '.' or -1
            while (c > DOT) {
                res *= 10.0;
                res += ints[c];
                c = read();
            }
            if (c == DOT) {
                int i = 0;
                c = read();
                // while c is digit and we are less than the maximum decimal precision
                while (c > SPACE && i < MAX_DECIMAL_PRECISION) {
                    res += doubles[ints[c]][i++];
                    c = read();
                }
            }
            return res * sgn;
        }

        // Read an array of n byte values
        private byte[] nextByteArray(int n) throws IOException {
            byte[] ar = new byte[n];
            for (int i = 0; i < n; i++) ar[i] = nextByte();
            return ar;
        }

        // Read an integer array of size n
        private int[] nextIntArray(int n) throws IOException {
            int[] ar = new int[n];
            for (int i = 0; i < n; i++) ar[i] = nextInt();
            return ar;
        }

        // Read a long array of size n
        private long[] nextLongArray(int n) throws IOException {
            long[] ar = new long[n];
            for (int i = 0; i < n; i++) ar[i] = nextLong();
            return ar;
        }

        // read an of doubles of size n
        private double[] nextDoubleArray(int n) throws IOException {
            double[] ar = new double[n];
            for (int i = 0; i < n; i++) ar[i] = nextDouble();
            return ar;
        }

        // Quickly read an array of doubles
        private double[] nextDoubleArrayFast(int n) throws IOException {
            double[] ar = new double[n];
            for (int i = 0; i < n; i++) ar[i] = nextDoubleFast();
            return ar;
        }

        // Read a string array of size n
        private String[] nextStringArray(int n) throws IOException {
            String[] ar = new String[n];
            for (int i = 0; i < n; i++) {
                String str = nextString();
                if (str == null) throw new IOException();
                ar[i] = str;
            }
            return ar;
        }

        // Read a 1-based byte array of size n+1
        private byte[] nextByteArray1(int n) throws IOException {
            byte[] ar = new byte[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextByte();
            return ar;
        }

        // Read a 1-based integer array of size n+1
        private int[] nextIntArray1(int n) throws IOException {
            int[] ar = new int[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextInt();
            return ar;
        }

        // Read a 1-based long array of size n+1
        private long[] nextLongArray1(int n) throws IOException {
            long[] ar = new long[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextLong();
            return ar;
        }

        // Read a 1-based double array of size n+1
        private double[] nextDoubleArray1(int n) throws IOException {
            double[] ar = new double[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextDouble();
            return ar;
        }

        // Quickly read a 1-based double array of size n+1
        private double[] nextDoubleArrayFast1(int n) throws IOException {
            double[] ar = new double[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextDoubleFast();
            return ar;
        }

        // Read a 1-based string array of size n+1
        private String[] nextStringArray1(int n) throws IOException {
            String[] ar = new String[n + 1];
            for (int i = 1; i <= n; i++) ar[i] = nextString();
            return ar;
        }

        // Read a two dimensional matrix of bytes of size rows x cols
        private byte[][] nextByteMatrix(int rows, int cols) throws IOException {
            byte[][] matrix = new byte[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextByte();
            return matrix;
        }

        // Read a two dimensional matrix of ints of size rows x cols
        private int[][] nextIntMatrix(int rows, int cols) throws IOException {
            int[][] matrix = new int[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextInt();
            return matrix;
        }

        // Read a two dimensional matrix of longs of size rows x cols
        private long[][] nextLongMatrix(int rows, int cols) throws IOException {
            long[][] matrix = new long[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextLong();
            return matrix;
        }

        // Read a two dimensional matrix of doubles of size rows x cols
        private double[][] nextDoubleMatrix(int rows, int cols) throws IOException {
            double[][] matrix = new double[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextDouble();
            return matrix;
        }

        // Quickly read a two dimensional matrix of doubles of size rows x cols
        private double[][] nextDoubleMatrixFast(int rows, int cols) throws IOException {
            double[][] matrix = new double[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextDoubleFast();
            return matrix;
        }

        // Read a two dimensional matrix of Strings of size rows x cols
        private String[][] nextStringMatrix(int rows, int cols) throws IOException {
            String[][] matrix = new String[rows][cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    matrix[i][j] = nextString();
            return matrix;
        }

        // Read a 1-based two dimensional matrix of bytes of size rows x cols
        private byte[][] nextByteMatrix1(int rows, int cols) throws IOException {
            byte[][] matrix = new byte[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextByte();
            return matrix;
        }

        // Read a 1-based two dimensional matrix of ints of size rows x cols
        private int[][] nextIntMatrix1(int rows, int cols) throws IOException {
            int[][] matrix = new int[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextInt();
            return matrix;
        }

        // Read a 1-based two dimensional matrix of longs of size rows x cols
        private long[][] nextLongMatrix1(int rows, int cols) throws IOException {
            long[][] matrix = new long[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextLong();
            return matrix;
        }

        // Read a 1-based two dimensional matrix of doubles of size rows x cols
        private double[][] nextDoubleMatrix1(int rows, int cols) throws IOException {
            double[][] matrix = new double[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextDouble();
            return matrix;
        }

        // Quickly read a 1-based two dimensional matrix of doubles of size rows x cols
        private double[][] nextDoubleMatrixFast1(int rows, int cols) throws IOException {
            double[][] matrix = new double[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextDoubleFast();
            return matrix;
        }

        // Read a 1-based two dimensional matrix of Strings of size rows x cols
        private String[][] nextStringMatrix1(int rows, int cols) throws IOException {
            String[][] matrix = new String[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++)
                for (int j = 1; j <= cols; j++)
                    matrix[i][j] = nextString();
            return matrix;
        }

        // Closes the input stream
        private void close() throws IOException {
            stream.close();
        }
    }
}