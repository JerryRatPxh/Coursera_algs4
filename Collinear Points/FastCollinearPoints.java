import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points){
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
        Point[] points1 = new Point[N];
        if (N < 4) {
            lineSegments = new LineSegment[0];
            return ;
        }

        Collection<LineSegment> collection = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            //copy of points
            for (int j = i+1; j < N; j++)
                points1[j] = points[j];

            Arrays.sort(points1, i+1, N, points[i].slopeOrder());
            int cnt = 1;
            for (int j = i+1; j < N-1; j++) {
                if (points1[j].slopeTo(points[i]) == points1[j+1].slopeTo(points[i]))
                    cnt++;
                else {
                    if (cnt >= 3) {
                        Point[] points2 = new Point[cnt+1];
                        points2[0] = points[i];
                        int tmp = j;
                        for (int k = 1; k <= cnt; k++)
                            points2[k] = points1[tmp--];
                        Arrays.sort(points2);
                        collection.add(new LineSegment(points2[0], points2[cnt]));
                    }
                    cnt = 1;
                }
            }
            if (cnt >= 3) {
                Point[] points2 = new Point[cnt+1];
                points2[0] = points[i];
                int tmp = N;
                for (int k = 1; k <= cnt; k++)
                    points2[k] = points1[--tmp];
                Arrays.sort(points2);
                collection.add(new LineSegment(points2[0], points2[cnt]));
            }
        }
        lineSegments = collection.toArray(new LineSegment[0]);
    }
    public  int numberOfSegments(){
        return lineSegments.length;
    }
    public LineSegment[] segments(){
        return lineSegments;
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

        //print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}