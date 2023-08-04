import dsa.LinkedQueue;
import stdlib.StdOut;

public class Josephus {
    // Entry point.
    public static void main(String[] args) {
        // Accept n (int) and m (int) as command-line arguments.
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);


        // Create a queue q and enqueue integers 1, 2, ..., n.
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        for (int qplus = 1; qplus < n + 1; qplus++) {
            q.enqueue(qplus);
        }

        // Set i to 0. As long as q is not empty: increment i; dequeue an element (say pos); if m
        // divides i, write pos to standard output, otherwise enqueue pos to q.
        int i = 0;
        while (!q.isEmpty()) {
            i++;
            int pos = q.dequeue();
            if (i % m == 0) {
                StdOut.println(pos);
            } else {
                q.enqueue(pos);
            }
        }
    }
}
