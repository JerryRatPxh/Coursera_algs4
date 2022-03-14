import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] res;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (trials <= 0)
            throw new IllegalArgumentException("trial must be bigger than 0");
        this.trials = trials;
        res = new double[trials];
        int cnt = 0;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while(!percolation.percolates()){
                int position = StdRandom.uniform(n*n);
                int row = position/n + 1;
                int col = position%n + 1;
                while(percolation.isOpen(row, col)){
                    position = StdRandom.uniform(n*n);
                    row = position/n + 1;
                    col = position%n + 1;
                }
                percolation.open(row, col);
                System.out.println("open: " + row + " " + col);
            }

            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    System.out.println("row: " + j + " col : " + k);
                    System.out.println(percolation.isFull(j,k));
                }
            }

            res[cnt++] = (double) percolation.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(res);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(res);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean()-((1.96*this.stddev())/Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean()+((1.96*this.stddev())/Math.sqrt(this.trials));
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n,t);

        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }
}
