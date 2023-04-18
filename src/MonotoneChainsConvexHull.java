import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Comparator;

public class MonotoneChainsConvexHull {
    // Small epsilon used for double value comparison.
    private static final double EPS = 1e-5;

    public static void main(String[] args) {
        Point2D[] points = new Point2D[13];

        points[0] = new Point2D.Double(0, 5);
        points[1] = new Point2D.Double(-1, 1);
        points[2] = new Point2D.Double(0, 1);
        points[3] = new Point2D.Double(1, 1);
        points[4] = new Point2D.Double(-5, 0);
        points[5] = new Point2D.Double(-1, 0);
        points[6] = new Point2D.Double(0, 0);
        points[7] = new Point2D.Double(1, 0);
        points[8] = new Point2D.Double(5, 0);
        points[9] = new Point2D.Double(-1, -1);
        points[10] = new Point2D.Double(0, -1);
        points[11] = new Point2D.Double(1, -1);
        points[12] = new Point2D.Double(0, -5);

        Point2D[] hull = convexHull(points);
        for (Point2D point2D : hull) {
            System.out.println(point2D);
        }
    }

    // Use the monotone chains algorithm to find the
    // convex hull of a set of points in O(nlogn) time.
    public static Point2D[] convexHull(Point2D[] pts) {

        int n = pts.length, k = 0;
        if (n <= 1) return pts;

        Point2D[] hull = new Point2D[2 * n];
        Arrays.sort(pts, new PointComparator());

        // Build upper chain.
        for (Point2D pt : pts) {
            while (k >= 2 && orientation(hull[k - 2], hull[k - 1], pt) <= 0) k--;
            hull[k++] = pt;
        }

        int lastUpperChainIndex = k;

        // Build lower chain.
        for (int i = n - 2; i >= 0; i--) {
            while (k > lastUpperChainIndex && orientation(hull[k - 2], hull[k - 1], pts[i]) <= 0) k--;
            hull[k++] = pts[i];
        }

        // Conserve only unique points.
        int index = 1;
        Point2D lastPt = hull[0];
        for (int i = 1; i < k - 1; i++) {
            if (!hull[i].equals(lastPt)) {
                hull[index++] = lastPt = hull[i];
            }
        }

        return Arrays.copyOfRange(hull, 0, index);
    }

    // To find orientation of point 'c' relative to the line segment (a, b).
    // Imagine yourself standing at point 'a' looking out towards point 'b'.
    // Returns  0 if all three points are collinear.
    // Returns -1 if 'c' is clockwise to segment (a, b), i.e right of line formed by the segment.
    // Returns +1 if 'c' is counter clockwise to segment (a, b), i.e left of line
    // formed by the segment.
    private static int orientation(Point2D a, Point2D b, Point2D c) {
        double value =
                (b.getY() - a.getY()) * (c.getX() - b.getX())
                        - (b.getX() - a.getX()) * (c.getY() - b.getY());
        if (Math.abs(value) < EPS) return 0;
        return (value > 0) ? -1 : +1;
    }

    // Sorts points by first x coordinate and then y coordinate.
    private static class PointComparator implements Comparator<Point2D> {
        public int compare(Point2D p1, Point2D p2) {
            if (Math.abs(p1.getX() - p2.getX()) < EPS) {
                if (Math.abs(p1.getY() - p2.getY()) < EPS) return 0;
                else if (p1.getY() > p2.getY()) return 1;
            } else if (p1.getX() > p2.getX()) return 1;
            return -1;
        }
    }
}
