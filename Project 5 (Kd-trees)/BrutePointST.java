import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

public class BrutePointST<Value> implements PointST<Value> {
    RedBlackBinarySearchTreeST<Point2D, Value> bst; // data type that substitutes
    // 2-3 search tree by only adding a couple other parameters. Super cool!

    // Constructs an empty symbol table.
    public BrutePointST() {
        bst = new RedBlackBinarySearchTreeST<>();
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return bst.size();
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        bst.put(p, value);
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (!bst.isEmpty()) {
            return bst.get(p);
        }
        return null;
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.contains(p);
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        for (Point2D pointT : bst.keys()) {
            if (rect.contains(pointT)) {
                queue.enqueue(pointT);
            }
        }
        return queue;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return nearest(p, 1).iterator().next(); // iterate and collect the first closest point
        // 1 being the nearest point to p in the pq
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        MinPQ<Point2D> minPq = new MinPQ<Point2D>(bst.size(), p.distanceToOrder());
        LinkedQueue<Point2D> q = new LinkedQueue<>();
        // create a min pq with size of bst as initial cap and custom comparator.
        for (Point2D u : bst.keys()) {
            // for each u in bst. keys, if u == that point p, cont and insert u into minPQ.
            if (u.equals(p)) {
                continue;
            }
            minPq.insert(u);
        }
        for (int i = 0; i < k; i++) {
            Point2D dM = minPq.delMin();
            q.enqueue(dM);
        }
        return q;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
