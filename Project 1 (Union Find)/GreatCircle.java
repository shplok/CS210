import stdlib.StdOut;

public class GreatCircle {
    // Entry point.
    public static void main(String[] args) {
        // Accept x1 (double), y1 (double), x2 (double), and y2 (double) as command-line arguments.
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);
        // names with r are in radian form
        // Convert the angles to radians.
        double x1r = Math.toRadians(x1);
        double y1r = Math.toRadians(y1);
        double x2r = Math.toRadians(x2);
        double y2r = Math.toRadians(y2);
        // Calculate great-circle distance d.
        double d = 6359.83 * Math.acos(Math.sin(x1r) * Math.sin(x2r) +
                Math.cos(x1r) * Math.cos(x2r) * Math.cos(y1r - y2r));
        // Write d to standard output.
        StdOut.println(d);
    }
}