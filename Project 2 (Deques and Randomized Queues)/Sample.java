
import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];

        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();

        if (!((mode.equals("+")) || (mode.equals("-")))) {
            throw new IllegalArgumentException("Illegal mode");
        }
        for (int i = lo; i <= hi; i++) {
            q.enqueue(i);
        }
        if (mode.equals("+")) {
            for (int j = 0; j < k; j++) {
                StdOut.println(q.sample());
            }
        }
        if (mode.equals("-")) {
            for (int j = 0; j < k; j++) {
                StdOut.println(q.dequeue());
            }
        }
    }
}
