import dsa.*;
import stdlib.StdIn;
import stdlib.StdOut;

public class KdTreePointST<Value> implements PointST<Value> {
    Node root; // reference of 2dTree
    int n; // number of nodes in the tree

    // Constructs an empty symbol table.
    public KdTreePointST() {
        root = null;
        n = 0;
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return n;
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        RectHV rect1 = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY); // creating an inf large rect
        root = put(root, p, value, rect1, true); // true being x-aligned i believe
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(root, p, true);
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (get(p) != null) {
            return true;
        }
        return false;
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        LinkedQueue<Node> q1 = new LinkedQueue<>(); // traversal
        LinkedQueue<Point2D> q2 = new LinkedQueue<>(); // storing & returning values
        q1.enqueue(root);
        Node x;
        if (isEmpty()) {
            return q2;
        }
        while (!q1.isEmpty()) {
            x = q1.dequeue();
            q2.enqueue(x.p);
            if (x.lb != null) {
                q1.enqueue(x.lb);
            }
            if (x.rt != null) {
                q1.enqueue(x.rt);
            }
        }
        return q2;
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> a = new LinkedQueue<>();
        range(root, rect, a);
        return a;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        Point2D t = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        return nearest(root, p, t, true);
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        MaxPQ<Point2D> closestP = new MaxPQ<>(p.distanceToOrder());
        nearest(root, p, k, closestP, true);
        return closestP;
    }

    // Note: In the helper methods that have lr as a parameter, its value specifies how to
    // compare the point p with the point x.p. If true, the points are compared by their
    // x-coordinates; otherwise, the points are compared by their y-coordinates. If the
    // comparison of the coordinates (x or y) is true, the recursive call is made on x.lb;
    // otherwise, the call is made on x.rt.

    // Inserts the given point and value into the KdTree x having rect as its axis-aligned
    // rectangle, and returns a reference to the modified tree.
    private Node put(Node x, Point2D p, Value value, RectHV rect, boolean lr) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (x == null) {
            n++;
            return new Node(p, value, rect);
        }
        if (x.p == p) {
            x.value = value;
        } else if (!lr && p.y() < x.p.y() || lr && p.x() < x.p.x()) {
            if (lr) {
                RectHV lRect = new RectHV(rect.xMin(), rect.yMin(), x.p.x(), rect.yMax());
                x.lb = put(x.lb, p, value, lRect, !lr);
                // bounding box that restricts the region covered by points to left of x
                // max x coord being x.p

                // (makes bounding box representing the left half of original box)
            } else {
                RectHV bRect = new RectHV(rect.xMin(), rect.yMin(), rect.xMax(), x.p.y());
                x.lb = put(x.lb, p, value, bRect, !lr);
            }
        } else {
            if (lr) {
                RectHV rRect = new RectHV(x.p.x(), rect.yMin(), rect.xMax(), rect.yMax());
                x.rt = put(x.rt, p, value, rRect, !lr);
                // bounding box restricting/ representing the right half
            } else {
                RectHV tRect = new RectHV(rect.xMin(), x.p.y(), rect.xMax(), rect.yMax());
                x.rt = put(x.rt, p, value, tRect, !lr);
                // bounding box representing the points atop of current node
            }
        }
        return x;
    }

    // Returns the value associated with the given point in the KdTree x, or null.
    private Value get(Node x, Point2D p, boolean lr) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        if (x == null) {
            return null;
        }
        if (x.p.equals(p)) {
            return x.value;
        }
        if (lr) {
            if (p.x() < x.p.x()) {
                get(x.lb, p, !lr);
            } else {
                get(x.rt, p, !lr);
            }
        } else {
            if (p.y() < x.p.y()) {
                get(x.lb, p, !lr);
            } else {
                get(x.rt, p, !lr);
            }
        }
        return null;
    }

    // Collects in the given queue all the points in the KdTree x that are inside rect.
    private void range(Node x, RectHV rect, LinkedQueue<Point2D> q) {
        if (x == null) {
            return;
        }
        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }
        if (!x.rect.intersects(rect)) { // range search pruning rule
            return;
        }

        range(x.lb, rect, q);
        range(x.rt, rect, q);
    }

    // Returns the point in the KdTree x that is closest to p, or null; nearest is the closest
    // point discovered so far.
    private Point2D nearest(Node x, Point2D p, Point2D nearest, boolean lr) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }

        Point2D t = nearest; // setting a point to the nearest val in order to keep things simple
        boolean travVal; // traversal value to tell you where to travel (left or right subtree)
        if (x == null) {
            return t;
            // a floor in a sense, this is where we start for the
            // nearest point and build closer points.
        }

        if (!x.p.equals(p) && p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearest)) {
            t = x.p; // if we can find a closer point value, update prev point with this one.
        }

        if (lr) {
            if (p.x() > x.p.x()) {
                travVal = false;
                t = nearest(x.rt, p, t, !lr);
            } else {
                travVal = true;
                t = nearest(x.lb, p, t, !lr);
                // else, we traverse left.
            }
        } else {
            if (p.y() > x.p.y()) {
                travVal = false;
                t = nearest(x.rt, p, t, !lr);
            } else {
                travVal = true;
                t = nearest(x.lb, p, t, !lr);
            }
        }
        if (travVal) {
            return nearest(x.rt, p, t, lr);
        }
        return nearest(x.lb, p, t, lr);
    }

    // Collects in the given max-PQ up to k points from the KdTree x that are different from and
    // closest to p.
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, boolean lr) {
        boolean lB;
        // condition to save memory
        if (x == null || pq.size() > k && pq.max().distanceSquaredTo(p) <
                x.rect.distanceSquaredTo(p)) {
            return;
        }
        if (!x.p.equals(p)) {
            pq.insert(x.p);
        }
        // If the point in x is different from the given point, insert it into pq
        if (pq.size() > k) {
            pq.delMax();
        }
        if (!lr && p.y() < x.p.y() || lr && p.x() < x.p.x()) {
            lB = true;
            nearest(x.lb, p, k, pq, !lr);
        } else {
            lB = false;
            nearest(x.rt, p, k, pq, !lr);
        }
        nearest(lB ? x.rt : x.lb, p, k, pq, !lr);
    }

    // A representation of node in a KdTree in two dimensions (ie, a 2dTree). Each node stores a
    // 2d point (the key), a value, an axis-aligned rectangle, and references to the left/bottom
    // and right/top subtrees.
    private class Node {
        private Point2D p;   // the point (key)
        private Value value; // the value
        private RectHV rect; // the axis-aligned rectangle
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Constructs a node given the point (key), the associated value, and the
        // corresponding axis-aligned rectangle.
        Node(Point2D p, Value value, RectHV rect) {
            this.p = p;
            this.value = value;
            this.rect = rect;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<>();
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
