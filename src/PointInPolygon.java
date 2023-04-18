import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class PointInPolygon {
    static long INF;

    public static void main(String[] args) throws IOException {
        InputReader ir = new InputReader(System.in);

        int n = ir.readInt();
        int m = ir.readInt();
        Point[] poly = new Point[n];
        for (int i = 0; i < n; i++) {
            poly[i] = new Point(ir.readLong(), ir.readLong());
            INF = Math.max(INF, poly[i].x);
        }

        INF++;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            Point cur = new Point(ir.readLong(), ir.readLong());
            int temp = isInside(poly, n, cur);
            if (temp == -1) {
                sb.append("OUTSIDE");
            } else if (temp == 1) {
                sb.append("INSIDE");
            } else {
                sb.append("BOUNDARY");
            }
            sb.append("\n");
        }
        ir.close();

        System.out.println(sb);
    }

    private static boolean onSegment(Point p, Point q, Point r) {
        return q.x <= Math.max(p.x, r.x) &&
                q.x >= Math.min(p.x, r.x) &&
                q.y <= Math.max(p.y, r.y) &&
                q.y >= Math.min(p.y, r.y);
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private static int orientation(Point p, Point q, Point r) {
        long val1 = (q.y - p.y) * (r.x - q.x);
        long val2 = (q.x - p.x) * (r.y - q.y);

        if (val1 == val2)
            return 0; // collinear
        return (val1 > val2) ? 1 : 2; // clock or counterclockwise
    }

    // The function that returns true if
    // line segment 'p1q1' and 'p2q2' intersect.
    private static boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Find the four orientations needed for
        // general and special cases
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        // p1, q1 and p2 are collinear and
        // p2 lies on segment p1q1
        else if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }

        // p1, q1 and p2 are collinear and
        // q2 lies on segment p1q1
        else if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }

        // p2, q2 and p1 are collinear and
        // p1 lies on segment p2q2
        else if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }

        // p2, q2 and q1 are collinear and
        // q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2);

        // Doesn't fall in any of the above cases
    }

    // Returns true if the point p lies
    // inside the polygon with n vertices
    private static int isInside(Point[] polygon, int n, Point p) {
        Point extreme = new Point(INF, p.y);

        // Count intersections of the above line
        // with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i + 1) % n;

            // Check if the line segment from 'p' to
            // 'extreme' intersects with the line
            // segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                // If the point 'p' is collinear with line
                // segment 'i-next', then check if it lies
                // on segment. If it lies, return true, otherwise false
                if (orientation(polygon[i], p, polygon[next]) == 0) {
                    if (onSegment(polygon[i], p, polygon[next])) {
                        return 2;
                    }
                }
                if (orientation(p, polygon[next], extreme) == 0) {
                    if (orientation(p, polygon[next], polygon[i]) != orientation(p, polygon[next], polygon[(next + 1) % n])) {
                        count--;
                    }
                }
                if (p.y == polygon[next].y && polygon[next].y == polygon[(next + 1) % n].y) {
                    if (orientation(p, polygon[next], polygon[i]) == orientation(p, polygon[(next + 1) % n], polygon[(next + 2) % n])) {
                        count++;
                    }
                }
                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return (count % 2 == 1) ? 1 : -1; // Same as (count%2 == 1)
    }

    private static class Point {
        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class InputReader {
        private final InputStream stream;
        private final byte[] buf = new byte[1 << 16];
        private int curChar;
        private int numChars;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        private void close() throws IOException {
            stream.close();
        }

        private int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        private int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res;
        }

        private long readLong() {
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
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        private boolean isSpaceChar(int c) {
            return c == ' ' || c == '\n';
        }
    }
}
