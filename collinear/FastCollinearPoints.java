import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> list;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        list = new LinkedList<>();
        validate(points);

        Point[] points_slope_sorted = points.clone();

        // Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points_slope_sorted, points[i].slopeOrder());
            int count = 1;
            for (int j = 1; j < points_slope_sorted.length; j++) {
                double slope_to_i = points_slope_sorted[j].slopeTo(points[i]);
                if (j + 1 < points_slope_sorted.length
                        && points_slope_sorted[j + 1].slopeTo(points[i]) == slope_to_i) {
                    count++;
                }
                else {
                    if (count >= 3) {
                        List<Point> line = new ArrayList<>();
                        line.add(points[i]);
                        int k = count;
                        while (k >= 1) {
                            line.add(points_slope_sorted[j - k + 1]);
                            k--;
                        }

                        Collections.sort(line);
                        if (line.get(0).equals(points[i]))
                            list.add(new LineSegment(line.get(0), line.get(count)));
                    }
                    // 几天后才发现了这个count= 1应该放的位置
                    count = 1;
                }
            }
        }


    }


    public int numberOfSegments()        // the number of line segments
    {
        return list.size();
    }

    public LineSegment[] segments()                // the line segments
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
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
