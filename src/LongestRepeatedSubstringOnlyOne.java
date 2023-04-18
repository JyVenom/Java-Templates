import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class LongestRepeatedSubstringOnlyOne {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader(System.in);

        SuffixArray sa = new SuffixArray(ir.nextIntArr());

        System.out.print(sa.lrs());
    }

    private static class SuffixArray {
        int ALPHABET_SZ = 255, N;
        int[] T, lcp, sa, sa2, rank, tmp, c;

        public SuffixArray(int[] text) {
            T = text;
            N = text.length;
            sa = new int[N];
            sa2 = new int[N];
            rank = new int[N];
            c = new int[Math.max(ALPHABET_SZ, N)];
            construct();
            kasai();
        }

        private void construct() {
            int i, p, r;
            for (i = 0; i < N; ++i) c[rank[i] = T[i]]++;
            for (i = 1; i < ALPHABET_SZ; ++i) c[i] += c[i - 1];
            for (i = N - 1; i >= 0; --i) sa[--c[T[i]]] = i;
            for (p = 1; p < N; p <<= 1) {
                for (r = 0, i = N - p; i < N; ++i) sa2[r++] = i;
                for (i = 0; i < N; ++i) if (sa[i] >= p) sa2[r++] = sa[i] - p;
                Arrays.fill(c, 0, ALPHABET_SZ, 0);
                for (i = 0; i < N; ++i) c[rank[i]]++;
                for (i = 1; i < ALPHABET_SZ; ++i) c[i] += c[i - 1];
                for (i = N - 1; i >= 0; --i) sa[--c[rank[sa2[i]]]] = sa2[i];
                for (sa2[sa[0]] = r = 0, i = 1; i < N; ++i) {
                    if (!(rank[sa[i - 1]] == rank[sa[i]]
                            && sa[i - 1] + p < N
                            && sa[i] + p < N
                            && rank[sa[i - 1] + p] == rank[sa[i] + p])) r++;
                    sa2[sa[i]] = r;
                }
                tmp = rank;
                rank = sa2;
                sa2 = tmp;
                if (r == N - 1) break;
                ALPHABET_SZ = r + 1;
            }
        }

        private void kasai() {
            lcp = new int[N];
            int[] inv = new int[N];
            for (int i = 0; i < N; i++) inv[sa[i]] = i;
            for (int i = 0, len = 0; i < N; i++) {
                if (inv[i] > 0) {
                    int k = sa[inv[i] - 1];
                    while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len]) len++;
                    lcp[inv[i] - 1] = len;
                    if (len > 0) len--;
                }
            }
        }

        private String lrs() {
            for (int i = 0; i < N; i++) {
                if (lcp[i] > 0) {
                    int max = i++;
                    for (; i < N; i++) {
                        if (lcp[i] > 0 && lcp[i] > lcp[max]) {
                            max = i;
                        }
                    }
                    return new String(T, sa[max], lcp[max]);
                }
            }
            return "-1";
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 24];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private int read() throws IOException {
            if (numChars == -1) {
                throw new IOException();
            }
            if (curChar >= numChars) {
                curChar = -1;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new IOException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[++curChar];
        }

        private int[] nextIntArr() throws IOException {
            int c = read();
            while (!(c == ' ' || c == '\n' || c == -1)) {
                c = read();
            }
            int end = curChar;
            curChar = -1;
            int[] res = new int[end];
            for (int i = 0; i < end; i++) {
                res[i] = read();
            }
            return res;
        }
    }
}
