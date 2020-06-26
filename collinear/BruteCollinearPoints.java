import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class BruteCollinearPoints {
    private List<LineSegment> list;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        validate(points);
        list = new LinkedList<>();
        // Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slope_j = points[j].slopeTo(points[i]);
                for (int m = j + 1; m < points.length; m++) {
                    double slope_m = points[m].slopeTo(points[i]);
                    for (int n = m + 1; n < points.length; n++) {
                        double slope_n = points[n].slopeTo(points[i]);

                        if (slope_j == slope_m && slope_m == slope_n) {
                            List<Point> points_4 = new ArrayList<>(
                                    Arrays.asList(points[i], points[j], points[m], points[n])
                            );

                            Collections.sort(points_4);

                            list.add(new LineSegment(points_4.get(0), points_4.get(3)));

                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments()        // the number of line segments
    {
        return list.size();
    }

    public LineSegment[] segments()                // the line segments{
    {
        LineSegment[] lineSegments = new LineSegment[numberOfSegments()];
        int idx = 0;
        for (LineSegment lineSegment : list) {
            lineSegments[idx++] = lineSegment;
        }
        return lineSegments;
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point point : points) {
            if (point == null)
                throw  new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j])  == 0)
                    throw new IllegalArgumentException();
            }
        }

    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
