import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;


    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if (points == null)
            throw new IllegalArgumentException("points array can't be null");
        for (Point point:points) {
            if (point == null)
                throw new IllegalArgumentException("points in array can't be null");
        }
        for (int i = 0; i < points.length-1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException("points in array can't repeat");
            }
        }
        int N = points.length;
        if (N < 4) {
            lineSegments = new LineSegment[0];
            return ;
        }
        Collection<LineSegment> collection = new ArrayList<>();
        for (int i = 0; i < N-3; i++) {
            for (int j = i+1; j < N-2; j++) {
                for (int k = j+1; k < N-1; k++) {
                    for (int l = k+1; l < N; l++) {
                        if(points[i].slopeTo(points[j]) == points[k].slopeTo(points[l])
                        && points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                            Point[] points1 = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(points1);
                            collection.add(new LineSegment(points1[0], points1[3]));
                        }
                    }
                }
            }
        }
        lineSegments = collection.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public  int numberOfSegments(){
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    public static void main(String[] args) {
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

        //print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
