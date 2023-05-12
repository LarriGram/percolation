import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // do we need global variables
    private int[][] grid;
    // to count all the open sites
    private int count;
    // Should I create another global var for the virtual top and bottom
    private int virtualTop;

    private int virtualBottom;
    // the length of the top and bottom
    int size;

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    // private final QuickFindUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        size = n;
        // Needs to make sure it is able to actually create this
        if (n <= 0) {
            throw new IllegalArgumentException("Not able to construct");
        }
        // Everything is initialized to 0
        grid = new int[n][n];

        weightedQuickUnionUF = new WeightedQuickUnionUF(size * size + 2);

        // weightedQuickUnionUF = new QuickFindUF(size * size + 2);

        virtualTop = size * size;

        virtualBottom = size * size + 1;
    }

    // Helper function
    private void assertIfOutOfBounds(int row, int col) {
        if (row >= grid.length || row < 0) {
            throw new IllegalArgumentException("Row out of bounds");
        }
        if (col >= grid.length || col < 0) {
            throw new IllegalArgumentException("Colum out of bounds");
        }
    }

    // Helper Function 1D array representation
    private int arrayRep(int row, int col) {
        int numberRep = (row * size) + col;
        return numberRep;
    }

    // Can I make another helper function for adding to weighted union
    // yea </3 gotta make it to check the e
    private void neighborhood(int row, int col) {
        assertIfOutOfBounds(row, col);
        if (isOpen(row, col)) {
            //Check before
            if (row != 0 && isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(arrayRep(row, col), arrayRep(row - 1, col));
            }
            if (row != size - 1 && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(arrayRep(row, col), arrayRep(row + 1, col));
            }
            if (col != size - 1 && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(arrayRep(row, col), arrayRep(row, col + 1));
            }
            if (col != 0 && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(arrayRep(row, col), arrayRep(row, col - 1));
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // Checks to make sure it is in bounds
        assertIfOutOfBounds(row, col);
        // All open sites are represented by 1
        if (grid[row][col] == 1) {
            return;
        }

        grid[row][col] = 1;
        count++;

        // Needs to check top row and connect to virtual top
        if (row == 0) {
            weightedQuickUnionUF.union(virtualTop, arrayRep(row, col));
        }
        // Needs to check bottom row and connect it to virtual bottom
        if (row == size - 1) {
            weightedQuickUnionUF.union(virtualBottom, arrayRep(row, col));
        }

        neighborhood(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        // Checks bounds
        assertIfOutOfBounds(row, col);
        // Checks if the grid is already open
        return (grid[row][col] == 1);


    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        assertIfOutOfBounds(row, col);
        // needs to check if the virtual top is in there using find
        if (weightedQuickUnionUF.find(virtualTop) == weightedQuickUnionUF.find(
                arrayRep(row, col))) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (weightedQuickUnionUF.find(virtualTop) == weightedQuickUnionUF.find(virtualBottom)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
