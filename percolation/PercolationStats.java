/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException(
                    "n must bigger than 0 / trials must  bigger than 0!");

        double[] res = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (perc.isOpen(row, col)) {
                    continue;
                }
                perc.open(row, col);
            }
            res[i] = perc.numberOfOpenSites() * 1.0 / (n * n);
        }

        // mean
        mean = StdStats.mean(res);

        // stddev
        stddev = StdStats.stddev(res);

        // confidenceLo
        confidenceLo = mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
        confidenceHi = mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);

        // mean = StdStats.mean(res);
        // stddev = StdStats.stddev(res);
        // confidenceLo = mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
        // confidenceHi = mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);


    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);

        System.out.println("mean\t= " + stats.mean());
        System.out.println("stddev\t= " + stats.stddev());
        System.out.println(
                "95% confindece interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }
}
