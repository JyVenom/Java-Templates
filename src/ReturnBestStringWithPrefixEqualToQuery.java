import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashMap;

public class ReturnBestStringWithPrefixEqualToQuery {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = ir.nextInt();
        Trie t = new Trie();
        if (n > 0) {
            int[] rs = new int[n];
            for (int i = 0; i < n; i++) {
                rs[i] = ir.nextInt();
            }
            for (int i = 0; i < n; i++) {
                t.insert(ir.nextCharArr(), rs[i], i + 1);
            }
        }

        int q = ir.nextInt();
        for (int i = 0; i < q; i++) {
            pw.println(t.findMax(ir.nextCharArr()));
        }
        ir.close();

        pw.close();
    }

    private static class Trie {
        private final char rootCharacter = '\0';
        private final Node root = new Node(rootCharacter, -1, -1);

        public void insert(char[] key, int val, int num) {
            Node node = root;

            for (char ch : key) {
                Node nextNode = node.children.get(ch);

                if (nextNode == null) {
                    nextNode = new Node(ch, val, num);
                    node.addChild(nextNode, ch);
                }

                node = nextNode;
                node.updateMax(val, num);
                node.count += 1;
            }

            if (node != root) node.isWordEnding = true;
        }

        public int findMax(char[] key) {
            Node node = root;

            for (char ch : key) {
                if (node == null) return 1;
                node = node.children.get(ch);
            }

            if (node != null) return node.maxInd;
            return 1;
        }

        private static class Node {
            char ch;
            int val, num, max = 0, maxInd = 0;
            int count = 0;
            boolean isWordEnding = false;
            HashMap<Character, Node> children = new HashMap<>();

            public Node(char ch, int val, int num) {
                this.ch = ch;
                this.val = val;
                this.num = num;
            }

            public void updateMax(int val, int num) {
                if (val > max) {
                    max = val;
                    maxInd = num;
                }
            }

            public void addChild(Node node, char c) {
                children.put(c, node);
            }
        }
    }

    private static class InputReader {
        final private int BUFFER_SIZE = 1 << 16;
        private final DataInputStream dis;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public InputReader() {
            dis = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private char[] nextCharArr() throws IOException {
            byte c = read();
            ArrayDeque<Byte> q = new ArrayDeque<>();
            do {
                q.addLast(c);
                c = read();
            } while (c != '\n');
            char[] res = new char[q.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = (char) (byte) q.removeFirst();
            }
            return res;
        }

        private int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
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

        private void close() throws IOException {
            dis.close();
        }
    }
}
