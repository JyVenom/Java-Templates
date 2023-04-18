import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;

public class LongestRegularBracketSequence {
    public static void main(String[] args) throws IOException {
        InputReader2 ir = new InputReader2();
        PrintWriter pw = new PrintWriter(System.out);

        boolean[] arr = ir.nextBoolArr();

        int max = -1;
        int num = 1;
        int[] c = new int[arr.length];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]) {
                q.addLast(i);
            } else {
                if (q.isEmpty()) {
                    c[i] = -1;
                } else {
                    int temp = q.removeLast();
                    if (temp > 0) {
                        int di1 = temp - 1;
                        if (!arr[di1] && c[di1] != -1) {
                            c[i] = c[di1];
                        } else {
                            c[i] = temp;
                        }
                    } else {
                        c[i] = temp;
                    }
                    int dif = i - c[i];
                    if (dif > max) {
                        max = dif;
                        num = 1;
                    } else if (dif == max) {
                        num++;
                    }
                }
            }
        }
//        int max = 0;
//        int[] c = new int[arr.length];
//        ArrayDeque<Integer> q = new ArrayDeque<>();
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i]) {
//                q.addLast(i);
//            } else {
//                if (q.isEmpty()) {
//                    c[i] = -1;
//                } else {
//                    int temp = q.removeLast();
//                    if (temp > 0) {
//                        int di1 = temp - 1;
//                        if (!arr[di1] && c[di1] != -1) {
//                            c[i] = c[di1];
//                        } else {
//                            c[i] = temp;
//                        }
//                    } else {
//                        c[i] = temp;
//                    }
//                    max = Math.max(max, i - c[i]);
//                }
//            }
//        }
//        if (max == 0) {
//            pw.print("0 1");
//            pw.close();
//            return;
//        }
//        int num = 0;
//        for (int i = 0; i < arr.length; i++) {
//            if (!arr[i] && c[i] != -1 && i - c[i] == max) {
//                num++;
//            }
//        }

        pw.println(++max + " " + num);
        pw.close();
    }

    private static class InputReader2 {
        final private int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader2() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private boolean[] nextBoolArr() throws IOException {
            byte c;
            ArrayDeque<Byte> q = new ArrayDeque<>();
            while ((c = read()) == '(' || c == ')') {
                q.addLast(c);
            }
            boolean[] res = new boolean[q.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = q.removeFirst() == '(';
            }
            return res;
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
