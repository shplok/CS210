import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    int [][] tiles; // tiles in board
    int n; // n x n grid
    int hamming; // hamming distance to goal board
    int manhattan; // manhattan distance to goal board
    int blankPos; // pos of blank tile in rmo




    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
        int count = 0;
        this.manhattan = 0;
        this.hamming = 0;
        this.blankPos = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tileat = tileAt(i, j);
                count = (count + 1) % (n * n);

                if (tileat != 0) { // for manhattan dist
                    int r = Math.abs((tileat - 1) / n - i); // row offset
                    int c = Math.abs((tileat - 1) % n - j); // column offset
                    manhattan += r + c;
                }

                if (count != 0 && tileat != count) { // for hamming dist
                    hamming++;
                }

                if (tileat == 0) {
                    blankPos = i * n + j + 1;
                }
            }
        }
    }

    // Returns the size of this board.
    public int size() {
        return  n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return this.hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return this.manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return this.hamming == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int[] allBoard = new int[(n * n) - 1]; // board of n x n without the blank tile
        int boardInd = 0;
        int h = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    allBoard[boardInd] = tiles[i][j];
                    boardInd++;
                } else {
                    h = i;
                }
            }
        }
        long invC = Inversions.count(allBoard);
        if (n % 2 == 1) {
            return (invC % 2 == 0);
        } else {
            return ((invC + h) % 2 == 1);
        }
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<Board>();
        int i = (blankPos - 1) / n; // row index
        int j = (blankPos - 1) % n; // col index

        // for each possible neighbor!

        if (i + 1 < n) { // checks to see if we can move down
            int[][] clone = cloneTiles();
            int ph = clone[i + 1][j];
            clone[i + 1][j] = clone[i][j];
            clone[i][j] = ph;
            // alg to switch values
            Board fromOld = new Board(clone);
            q.enqueue(fromOld);
        }
        if (j + 1 < n) {    // checks to see if we can move right
            int[][] clone = cloneTiles();
            int ph = clone[i][j + 1];
            clone[i][j + 1] = clone[i][j];
            clone[i][j] = ph;
            // alg to switch values
            Board fromOld = new Board(clone);
            q.enqueue(fromOld);
        }
        if (j - 1 >= 0) { // checks to see if we can move left
            int[][] clone = cloneTiles();
            int ph = clone[i][j - 1];
            clone[i][j - 1] = clone[i][j];
            clone[i][j] = ph;
            // alg to switch values
            Board fromOld = new Board(clone);
            q.enqueue(fromOld);
        }
        if (i - 1 >= 0) { // checks to see if we can move up
            int[][] clone = cloneTiles();
            int ph = clone[i - 1][j];
            clone[i - 1][j] = clone[i][j];
            clone[i][j] = ph;
            // alg to switch values
            Board fromOld = new Board(clone);
            q.enqueue(fromOld);
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return (this.toString().equals(other.toString()));
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
