import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;

public class Percolation {
    private int length;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridFull;
    private int virtualTop;
    private int virtualDown;
    private boolean[] openSites;
    private int openNumber;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.length = N;
        this.grid = new WeightedQuickUnionUF(length * length + 2);
        this.gridFull = new WeightedQuickUnionUF(length * length + 1);
        this.openSites = new boolean[length * length];
        virtualTop = length * length;
        virtualDown = length * length + 1;
        openNumber = 0;
    }

    public void open(int row, int col) {
        if (outOfBoundsChecker(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int site = siteFinder(row, col);
        if (isOpen(row, col)) {
            return;
        }
        openSites[site] = true;
        openNumber++;
        if (row == 0) {
            grid.union(virtualTop,site);
            gridFull.union(virtualTop,site);
        }
        if (row == length - 1) {
            grid.union(virtualDown,site);
        }
        if (!nearbySites(row,col).isEmpty()) {
            for (int s : nearbySites(row,col)) {
                if (openSites[s]) {
                    grid.union(s,site);
                    gridFull.union(s,site);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (outOfBoundsChecker(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int site = siteFinder(row, col);
        return openSites[site];
    }

    public boolean isFull(int row, int col) {
        int site = siteFinder(row, col);
        return gridFull.connected(virtualTop, site);
    }

    public int numberOfOpenSites() {
        return openNumber;
    }

    public boolean percolates() {
        return grid.connected(virtualTop,virtualDown);
    }

    private int siteFinder(int row, int col) {
        if (outOfBoundsChecker(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return (row * length + col);
    }

    /** return true if row or col is outside the prescribed range (0,N-1)*/
    private boolean outOfBoundsChecker(int row, int col) {
        return row > length - 1 || col > length - 1 || row < 0 || col < 0;
    }

    private ArrayList<Integer> nearbySites(int row, int col) {
        ArrayList<Integer> returnSites = new ArrayList<>();
        if (!outOfBoundsChecker(row - 1, col)) {
            int site = siteFinder(row - 1, col);
            returnSites.add(site);
        }
        if (!outOfBoundsChecker(row, col + 1)) {
            int site = siteFinder(row, col + 1);
            returnSites.add(site);
        }
        if (!outOfBoundsChecker(row + 1, col)) {
            int site = siteFinder(row + 1, col);
            returnSites.add(site);
        }
        if (!outOfBoundsChecker(row, col - 1)) {
            int site = siteFinder(row , col - 1);
            returnSites.add(site);
        }
        return returnSites;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(50);
        p.open(2,2);
        System.out.println(p.nearbySites(2,2));
        System.out.println(p.siteFinder(2,1));
        System.out.println(p.siteFinder(2,3));
        System.out.println(p.siteFinder(1,2));
        System.out.println(p.siteFinder(3,2));
    }
}
