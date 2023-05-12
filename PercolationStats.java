import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    int size;
    //int trial;
    double sites;
    double[] arrayTrials;
    //double clock;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        size = n;
        //trial = trials;

        // Do I need to loop thru and do new trials
        arrayTrials = new double[trials];

        if (trials <= 0 || size <= 0) {
            throw new IllegalArgumentException("Out of bounds");
        }

        //needs to start AFTER we declare

        for (int i = 0; i < trials; i++) {
            // gotta be inside the loop to
            Percolation percTrial = new Percolation(n);
            while (!percTrial.percolates()) {

                // Make it random bc
                int col = StdRandom.uniform(n);
                int row = StdRandom.uniform(n);

                percTrial.open(row, col);


            }
            //
            sites = percTrial.numberOfOpenSites();
            arrayTrials[i] = sites / (size * size);
        }
    }
    // dont need this
    // private void assertIfOutOfBounds ( int n){
    //     if (n <= 0 || size <= 0) {
    //         throw new IllegalArgumentException("Out of bounds");
    //     }
    // }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arrayTrials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrayTrials);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double sqrt = Math.sqrt(arrayTrials.length);
        return mean() - ((stddev() * 1.96) / sqrt);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double sqrt = Math.sqrt(arrayTrials.length);
        return mean() - ((1.96 * stddev()) / sqrt);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        Stopwatch clock = new Stopwatch();

        PercolationStats statistic = new PercolationStats(n, trials);


        System.out.println("mean = " + statistic.mean());
        System.out.println("stddev = " + statistic.stddev());
        System.out.println("confidenceLow =   " + statistic.confidenceLow());
        System.out.println("confidenceHigh =  " + statistic.confidenceHigh());
        System.out.println("elapsed time =    " + clock.elapsedTime());
    }
}
