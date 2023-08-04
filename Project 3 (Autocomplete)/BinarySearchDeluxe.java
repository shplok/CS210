import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        int low = 0; // bounds for binary search
        int hi = a.length - 1;
        int index = -1;
        if (a.length <= 0) {
            return -1;
        }
        while (low <= hi) {
            int mid = low + (hi - low) / 2;
            // if key is less than the middle pos, make mid - 1 the new hi
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
                // if key is greater than old mid pos, make mid + 1 the new low.
            } else if (c.compare(key, a[mid]) > 0) {
                low = mid + 1;
            } else {
                hi = mid - 1;   // change the new hi value and make index == mid.
                index = mid;
            }
        }
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        if (a.length <= 0) {
            return -1;
        }

        int low = 0; // bounds for binary search
        int hi = a.length - 1;
        int index = -1;

        while (low <= hi) {
            int mid = low + (hi - low) / 2;
            // if key is less than the middle pos, make mid - 1 the new hi
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
                // if key is greater than old mid pos, make mid + 1 the new low.
            } else if (c.compare(key, a[mid]) > 0) {
                low = mid +1;
            } else {
                low = mid + 1;   // change the new hi value and make index == mid.
                index = mid;
            }
        }
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
