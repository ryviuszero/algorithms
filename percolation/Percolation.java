import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              ryviuszero
 *  Coursera User ID:  mywindoes
 *  Last modified:     5/15/2020
 **************************************************************************** */
public class Percolation {
    private final int size;
    private int count;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf1;
    private final WeightedQuickUnionUF uf2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must bigger than zero!");
        size = n;
        count = 0;
        grid = new boolean[size][size];
        uf1 = new WeightedQuickUnionUF(size * size + 2);
        uf2 = new WeightedQuickUnionUF(size * size + 1);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("row and col must be in (1,n)!");
        if (!grid[row - 1][col - 1]) {
            // union
            int tmp = map(row, col);

            // for the first line
            if (row == 1) {
                uf1.union(0, tmp);
                uf2.union(0, tmp);
            }


            // for the last line
            if (row == size)
                uf1.union(size * size + 1, tmp);
            // up
            if (row - 1 > 0 && grid[row - 2][col - 1]) {
                uf1.union(tmp, map(row - 1, col));
                uf2.union(tmp, map(row - 1, col));
            }


            // right
            if (col + 1 <= size && grid[row - 1][col]) {
                uf1.union(tmp, map(row, col + 1));
                uf2.union(tmp, map(row, col + 1));
            }

            // bottom
            if (row + 1 <= size && grid[row][col - 1]) {
                uf1.union(tmp, map(row + 1, col));
                uf2.union(tmp, map(row + 1, col));
            }


            // right
            if (col - 1 > 0 && grid[row - 1][col - 2]) {
                uf1.union(tmp, map(row, col - 1));
                uf2.union(tmp, map(row, col - 1));
            }


            grid[row - 1][col - 1] = true;
            count++;
        }


    }

    private int map(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("row and col must be in (1,n)!");

        return (row - 1) * size + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("row and col must be in (1,n)!");

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("row and col must be in (1,n)!");

        // return uf2.find(0) == uf2.find((row - 1) * size + col);
        return uf2.connected(0, (row - 1) * size + col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        // return uf1.find(0) == uf1.find(size * size + 1);
        return uf1.connected(0, size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("OK!");
    }
}
