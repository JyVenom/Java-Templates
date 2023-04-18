import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

public class HowManyWaysToCreateStringUsingWordsFromAGivenDictionary {
    public static void main(String[] args) {
        InputReader ir = new InputReader();
        PrintWriter pw = new PrintWriter(System.out);

        char[] s = ir.nextCharArray();
        int n = s.length;
        Trie trie = new Trie();
        for (int i = ir.nextInt(); i > 0; i--) {
            char[] word = ir.nextCharArray();
            if (word.length > s.length) {
                continue;
            }
            trie.add(word);
        }

        ModArithmetic mod = new ModArithmetic();
        int[] ways = new int[n + 1];
        ways[n] = 1;
        for (int i = n - 1, w; i >= 0; i--) {
            TrieNode cur = trie.root;
            w = 0;
            for (int j = i; j < n; j++) {
                cur = cur.nxt[s[j] - 'a'];
                if (cur == null) {
                    break;
                }
                w = mod.add(w, mod.mul(cur.count, ways[j + 1]));
            }
            ways[i] = w;
        }

        pw.println(ways[0]);
        pw.close();
    }

    private static final class Trie {
        private final TrieNode root = new TrieNode();

        public void add(char[] s) {
            TrieNode cur = root;
            for (char c : s) {
                int idx = c - 'a';
                if (cur.nxt[idx] == null) {
                    cur.nxt[idx] = new TrieNode();
                }
                cur = cur.nxt[idx];
            }
            ++cur.count;
        }
    }

    private static final class TrieNode {
        int count;
        TrieNode[] nxt = new TrieNode[26];
    }

    private static final class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 21];
        private int curChar;
        private int numChars;

        public InputReader() {
            this.stream = System.in;
        }

        private static boolean isSpaceChar(final int c) {
            return c == 32 || c == 10 || c == 13 || c == 9 || c == -1; // 32 == ' ', 10 == '\n', 13 == '\r', 9 == '\t'
        }

        private int read() {
            if (this.numChars == -1) {
                throw new UnknownError();
            } else {
                if (this.curChar >= this.numChars) {
                    this.curChar = 0;

                    try {
                        this.numChars = this.stream.read(this.buf);
                    } catch (IOException ex) {
                        throw new InputMismatchException();
                    }

                    if (this.numChars <= 0) {
                        return -1;
                    }
                }

                return this.buf[this.curChar++];
            }
        }

        private int nextInt() {
            int c = this.read();
            while (isSpaceChar(c)) {
                c = this.read();
            }

            byte sgn = 1;
            if (c == 45) { // 45 == '-'
                sgn = -1;
                c = this.read();
            }

            int res = 0;

            while (c >= 48 && c <= 57) { // 48 == '0', 57 == '9'
                res *= 10;
                res += c - 48; // 48 == '0'
                c = this.read();
                if (isSpaceChar(c)) {
                    return res * sgn;
                }
            }

            throw new InputMismatchException();
        }

        private String next() {
            int c = this.read();
            while (isSpaceChar(c)) {
                c = this.read();
            }

            StringBuilder result = new StringBuilder();
            result.appendCodePoint(c);

            while (!isSpaceChar(c = this.read())) {
                result.appendCodePoint(c);
            }

            return result.toString();
        }

        private char[] nextCharArray() {
            return next().toCharArray();
        }
    }

    private static final class ModArithmetic {
        private static final int DEFAULT_MOD = 1000000007;
        private final int m;
        private final boolean isPrime;

        public ModArithmetic() {
            this(DEFAULT_MOD, true);
        }

        private ModArithmetic(int mod, boolean isPrime) {
            if (mod <= 0) {
                throw new IllegalArgumentException("Modulo must be > 0");
            }
            this.m = mod;
            this.isPrime = isPrime;
        }

        private int add(int a, int b) {
            a += b;
            if (a >= m) {
                a -= m;
            }
            return a;
        }

        private int mul(int a, int b) {
            long m = a * (long) b;
            if (m >= this.m) {
                m %= this.m;
            }
            return (int) m;
        }

        public String toString() {
            return "ModArithmetic{m=" + m + ", isPrime=" + isPrime + '}';
        }
    }
}
