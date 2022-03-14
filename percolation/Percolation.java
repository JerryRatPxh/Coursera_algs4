import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private boolean[][] opened;
    private int openedSites;
    private int virtualtop;
    private int virtualbot;

    private boolean validate(int row, int col)
    {
        return row > 0 && col > 0 && row <= opened.length && col <= opened.length;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0)
            throw new IllegalArgumentException("n must be bigger than 0");
        uf = new WeightedQuickUnionUF(n*n+2);
        opened = new boolean[n][n];
        openedSites = 0;
        virtualtop = n*n;
        virtualbot = n*n+1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (!validate(row, col))
            throw new IllegalArgumentException("args are not correct");
        if (opened[row - 1][col - 1])
            return;
        opened[row-1][col-1] = true;
        int n = opened.length;
        if (row == 1)
            uf.union(virtualtop,n*(row-1)+col-1);
        if (row == n)
            uf.union(virtualbot,n*(row-1)+col-1);
        if (validate(row-1, col) && isOpen(row-1, col))
            uf.union(n*(row-1)+col-1,n*(row-2)+col-1);
        if (validate(row+1, col) && isOpen(row+1, col))
            uf.union(n*(row-1)+col-1,n*row+col-1);
        if (validate(row, col-1) && isOpen(row, col-1))
            uf.union(n*(row-1)+col-1,n*(row-1)+col-2);
        if (validate(row, col+1) && isOpen(row, col+1))
            uf.union(n*(row-1)+col-1,n*(row-1)+col);
        openedSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (!validate(row, col))
            throw new IllegalArgumentException("args are not correct");
        return opened[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (!validate(row, col))
            throw new IllegalArgumentException("args are not correct");
        if(!isOpen(row, col))
            return false;
        if(row == 1)
            return true;
        int n = opened.length;
        return uf.find(n*(row-1)+col-1) == uf.find(virtualtop);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(virtualbot) == uf.find(virtualtop);
    }

    public static void main(String[] args) {

    }
}