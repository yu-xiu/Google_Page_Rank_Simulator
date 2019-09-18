import java.lang.reflect.Array;
import java.util.ArrayList;

/**
     *  Store the information of a pair of keyword and its counts. The counts will be sorted in heapsort
     *
     */
// Make the class comparable, we need to compare counts
    public class KeywordInformation implements Comparable<KeywordInformation>{
        String keyword;  // Member variables
        int count;
        ArrayList<WebPageInformation> crawledWebPages = new ArrayList<WebPageInformation>();

        /**
         * Constructor
         * @param keyword string
         * @param count integer
         */
        public KeywordInformation(String keyword, int count) {
            this.keyword = keyword;
            this.count = count;
        }

        /**
         * Comparing two count numbers
         * @param other
         * @return positive, negative or 0
         */
        public int compareTo(KeywordInformation other) {
            return this.count - other.count;
        }

        /**
         * Result presentation in string
         * @return string
         */
        public String toString () {
            String s = "Count " + this.count + " Keyword " + this.keyword;
            return s;
        }

    }
