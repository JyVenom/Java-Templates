import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Maximum sum of values of squares covered by two bishops such that no square is covered by both of them.
 * In the case below, the solution wouLd be to put the bishops at (2, 2) and (3, 2), getting a total sum (answer) of 12.
 * 1 1 1 1
 * 2 1 1 0
 * 1 1 1 0
 * 1 0 0 1
 *
 * @author Jerry Yang, jerryyang95130@gmail.com
 * @version 1.0
 */
public class TwoBishopsProblem {
    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader();
        PrintWriter pw = new PrintWriter(System.out);

        int n = ir.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = ir.nextInt();
            }
        }

        int n2 = 2 * n;
        int n21 = n2 - 1;
        /*
        pos goes like this:
              _|
            _|
          _|
        _|
        if we chose point 5 (1 based) then the corresponding diagonal would be:
          \   _|
           \_|
          _|\
        _|   \
        neg goes like this:
        _
         |_
           |_
             |_
               |
        if we chose point 5 (1 based) then the corresponding diagonal would be:
        _
         |_  /
           |/_
           /  |_
          /     |

        */
        long[] pos = new long[n21]; //start at (n, 0) then go right, go up. repeat until (0, n)
        long[] neg = new long[n21]; //start at (0, 0) then go right, go down, repeat until (n, n)
        neg[0] = grid[0][0];
        int i2;
        for (int i = 1; i < n; i++) {
            i2 = 2 * i;
            neg[i2] = findSumNeg(grid, i, i, n);
            neg[i2 - 1] = findSumNeg(grid, i - 1, i, n);
        }
        int r = n - 1;
        pos[0] = grid[r][0];
        for (int i = 1; i < n; i++) {
            r--;
            i2 = 2 * i;
            pos[i2] = findSumPos(grid, r, i, n);
            pos[i2 - 1] = findSumPos(grid, r + 1, i, n);
        }

        /*
        no matter where the bishop is placed, its diagonals will both cross the corresponding diagonal exactly once.
        thus, by trying every combination of a point on pos and a point on neg, we can get the result for every possible
        bishop placement.
        _
         |/_   _|
         /  |_|
         \ _| |_
        _|\     |

        _
        \|_/   _|
         \/ |_|
         /\_| |_
        /_|\    |

        _
          |\_   /   _ |
             \|/_ |
            _/|\  | _
        _ | /   \     |

        so, try every valid combo and track the maximum.
        we notice that for the two bishops to not overlap (in the squares they cover), one bishop must have its diagonal cross
        neg at an odd loc and the other bishop at an even loc.
        ao, we loop through neg and try every possible pos loc (not every pos diagonal [diagonal crossing through that
        point in pos] will cross the neg diagonal [diagonal crossing through that point in neg] at a point in the bounds
        of the grid) also, if n is odd then odd neg diagonals [diagonal crossing through an odd point in neg] will only
        intersect odd pos diagonals, and even neg diagonals will only intersect even pos diagonals. but if n is even,
        then odd neg diagonals will intersect with even pos diagonals and even neg diagonals will only intersect with odd
        pos diagonals.
         */
        long[] maxE = new long[]{0, 0, 0};
        long[] maxO = new long[]{0, 0, 1};
        int[] tmp = new int[]{-1, 0};
        int[] tmp2 = new int[2];
        int start = n;
        int end;
        for (int i = 0; i < n; i++) {
            tmp[0]++;
            tmp2[0] = tmp[0];
            tmp2[1] = tmp[1];
            start--; //going to the next neg diagonal means we can now intersect the previous pos diagonal. neg and pos intersect ranges are inversely proportional
            end = n21 - start; //distance start is from 0 is equal to distance end is from 2 * (n - 1);
            if (i % 2 == 0) {
                for (int j = start; j < end; j += 2) {
                    long cur = neg[i] + pos[j] - grid[tmp2[0]][tmp2[1]];
                    if (cur > maxE[0]) {
                        maxE[0] = cur;
                        maxE[1] = tmp2[0];
                        maxE[2] = tmp2[1];
                    }
                    tmp2[0]--;
                    tmp2[1]++;
                }
            } else {
                for (int j = start; j < end; j += 2) {
                    long cur = neg[i] + pos[j] - grid[tmp2[0]][tmp2[1]];
                    if (cur > maxO[0]) {
                        maxO[0] = cur;
                        maxO[1] = tmp2[0];
                        maxO[2] = tmp2[1];
                    }
                    tmp2[0]--;
                    tmp2[1]++;
                }
            }
        }
        for (int i = n; i < n21; i++) {
            tmp[1]++;
            tmp2[0] = tmp[0];
            tmp2[1] = tmp[1];
            start++; //now that we have crossed the halfway point, going to the next neg diagonal means we can now intersect one less pos diagonal.
            end = n21 - start;
            if (i % 2 == 0) {
                for (int j = start; j < end; j += 2) {
                    long cur = neg[i] + pos[j] - grid[tmp2[0]][tmp2[1]];
                    if (cur > maxE[0]) {
                        maxE[0] = cur;
                        maxE[1] = tmp2[0];
                        maxE[2] = tmp2[1];
                    }
                    tmp2[0]--;
                    tmp2[1]++;
                }
            } else {
                for (int j = start; j < end; j += 2) {
                    long cur = neg[i] + pos[j] - grid[tmp2[0]][tmp2[1]];
                    if (cur > maxO[0]) {
                        maxO[0] = cur;
                        maxO[1] = tmp2[0];
                        maxO[2] = tmp2[1];
                    }
                    tmp2[0]--;
                    tmp2[1]++;
                }
            }
        }

        pw.println(maxE[0] + maxO[0]);
        pw.println((maxE[1] + 1) + " " + (maxE[2] + 1) + " " + (maxO[1] + 1) + " " + (maxO[2] + 1));
        pw.close();
    }

    /**
     * @param grid input data
     * @param row  row # of start point middle
     * @param col  col # of start point
     * @param n    size of grid
     * @return sum of elements in upwards diagonal crossing through point (row, col)
     */
    private static long findSumNeg(int[][] grid, int row, int col, int n) {
        long sum = 0;
        int r = row;
        for (int i = col; i < n; i++) {
            if (r < 0) {
                break;
            }
            sum += grid[r][i];
            r--;
        }
        r = row + 1;
        for (int i = col - 1; i >= 0; i--) {
            if (r == n) {
                break;
            }
            sum += grid[r][i];
            r++;
        }
        return sum;
    }

    /**
     * @param grid input data
     * @param row  row # of start point middle
     * @param col  col # of start point
     * @param n    size of grid
     * @return sum of elements in downwards diagonal crossing through point (row, col)
     */
    private static long findSumPos(int[][] grid, int row, int col, int n) {
        long sum = 0;
        int r = row;
        for (int i = col; i < n; i++) {
            if (r == n) {
                break;
            }
            sum += grid[r][i];
            r++;
        }
        r = row - 1;
        for (int i = col - 1; i >= 0; i--) {
            if (r < 0) {
                break;
            }
            sum += grid[r][i];
            r--;
        }
        return sum;
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
