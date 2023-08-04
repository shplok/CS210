
import java.util.Arrays;


import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private final Term[] terms; // an array full of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        this.terms = new Term[terms.length]; // setting terms array to new array same size terms
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        int i = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length())); // first term in term with prefix
        int n = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length())); // number of terms with prefix
        if (i == -1) {
            return new Term[0]; // no match corner case
        }
        Term[] matches = new Term[n - i + 1]; // n elements from terms starting i
        for (int i1 = 0; i1 < matches.length; i1++) {
            matches[i1] = terms[i++];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        int i = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length())); // first term starts with prefix
        int j = BinarySearchDeluxe.lastIndexOf(terms, new Term(prefix, 0),
                Term.byPrefixOrder(prefix.length())); // last term starts with prefix
        if (terms.length == 0) {
            return 0; // corner case
        }
        return (j - i + 1); // total number of terms with prefix in them
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
