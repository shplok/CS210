import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
// list as the underlying data structure.
public class LinkedDeque<Item> implements Iterable<Item> {
    private Node first; // front of the deque
    private Node last; // back of the deque
    private int n; // the total size of the deque

    // Constructs an empty deque.
    public LinkedDeque() {
        first = null;
        last = null;
        n = 0;
    }

    // Returns true if this deque is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of items in this deque.
    public int size() {
        return n;
    }

    // Adds item to the front of this deque.
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        Node oldFirst = first;  // creating a new node for the old first pos
        first = new Node();
        first.item = item;      // the new item is now first
        first.next = oldFirst;      // the item after that is now old first
        first.prev = null;      // it is the first item

        if (isEmpty()) {
            last = first;       // if it is empty, then they should be the same
        } else {
            oldFirst.prev = first;      // otherwise point to the same node (last and first)
        }
        n++;

    }

    // Adds item to the back of this deque.
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;
    }

    // Returns the item at the front of this deque.
    public Item peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return first.item;
    }

    // Removes and returns the item at the front of this deque.
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
            last = null;
            first = null;
        } else {
            first.prev = null;
        }
        return item;
    }

    // Returns the item at the back of this deque.
    public Item peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return last.item;
    }

    // Removes and returns the item at the back of this deque.
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            last.next = null;
        }
        return item;
    }

    // Returns an iterator to iterate over the items in this deque from front to back.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Returns a string representation of this deque.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // A deque iterator.
    private class DequeIterator implements Iterator<Item> {
        Node current; // current node in the iterator

        // Constructs an iterator.
        public DequeIterator() {
            current = first;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current != null;
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // A data type to represent a doubly-linked list. Each node in the list stores a generic item
    // and references to the next and previous nodes in the list.
    private class Node {
        private Item item;  // the item
        private Node next;  // the next node
        private Node prev;  // the previous node
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}
