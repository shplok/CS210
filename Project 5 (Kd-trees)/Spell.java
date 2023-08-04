import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Spell {
    // Entry point.
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] lines = in.readAllLines();
        in.close();

        // Create an ArrayST<String, String> object called st.
        ArrayST<String, String> st = new ArrayST<String, String>();

        // For each line in lines, split it into two tokens using "," as delimiter; insert into
        // st the key-value pair (token 1, token 2).
        for (String l: lines) {
            String[] tokens = l.trim().split(",");
            st.put(tokens[0], tokens[1]);
        }

        // Read from standard input one line at a time; increment a line number counter; split
        // the line into words using "\\b" as the delimiter; for each word in words, if it
        // exists in st, write the (misspelled) word, its line number, and corresponding value
        // (correct spelling) from st.
        int linNum = 0; // line number counter
        while (!StdIn.isEmpty()) {
            String line = StdIn.readLine();
            linNum++; // increment line counter
            String[] words = line.trim().split("\\b");
            for (String word : words) {
                if (st.contains(word)) {
                    StdOut.printf("%s" + ":" + "%d" + " -> %s\n", word, linNum, st.get(word));
                }
            }
        }
    }
}
