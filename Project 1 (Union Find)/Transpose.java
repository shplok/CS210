import stdlib.StdArrayIO;

public class Transpose {
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        double[][] x = StdArrayIO.readDouble2D();
        StdArrayIO.print(transpose(x));
    }

    // Returns a new matrix that is the transpose of x.
    private static double[][] transpose(double[][] x) {
        // Create a new 2D matrix t (for transpose) with dimensions n x m, where m x n are the
        // dimensions of x.
        double [][]t = new double[x[0].length][x.length];
        // x[0].len being n and x.len being m

        // For each 0 <= i < m and 0 <= j < n, set t[j][i] to x[i][j].
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                t[j][i] = x[i][j];
            }
        }

        // Return t.
        return t;
    }
}