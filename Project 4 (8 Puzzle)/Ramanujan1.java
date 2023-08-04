import stdlib.StdOut;

public class Ramanujan1 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int a = 1; a * a * a <= n; a++) {
            for (int b = a + 1; a * a * a + b * b * b <= n; b++) {
                for (int c = a + 1; c * c * c <= n; c++) {
                    for (int d = c + 1; c * c * c + d * d * d <= n; d++) {
                        int x = a * a * a + b * b * b;
                        int y = c * c * c + d * d * d;
                        if (x == y) {
                            StdOut.printf("%d = %d^3 + %d^3 = %d^3 + %d^3\n", x, a, b, c, d);
                        }
                    }
                }
            }
        }
    }
}
