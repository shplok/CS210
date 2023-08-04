import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> dDeque = new LinkedDeque<String>();
        while (!StdIn.isEmpty()) {
            String w = StdIn.readString();
            if (dDeque.isEmpty()) {
                dDeque.addFirst(w);
                
            } else if (less(w, dDeque.peekFirst()) || w == dDeque.peekFirst()) {
                dDeque.addFirst(w);
            } else if (less(dDeque.peekLast(), w) || dDeque.peekLast() == w) {
                dDeque.addLast(w);
            } else {
                LinkedStack<String> sStack = new LinkedStack<String>();
                while (less(dDeque.peekFirst(), w)) {
                    sStack.push(dDeque.removeFirst());
                }
                dDeque.addFirst(w);
                while (!sStack.isEmpty()) {
                    dDeque.addFirst(sStack.pop());
                }
            }
        }
        for (String j : dDeque) {
            StdOut.println(j);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
