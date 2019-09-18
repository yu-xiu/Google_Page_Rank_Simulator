import java.util.ArrayList;

/**
 * This class contains bucketSort function
 * Using this sorting algorithm to sort the all company's names of urls in alphabetical order
 */
public class BucketSort {
    /**
     * Bucket sort
     *
     * using this sorting algorithm to sort companies' names
     * @param A
     */
    void buckSort(ArrayList<WebPageInformation> A) {
        // 26 letters; making B[] size as 26
        int n = 26;
        // Array B stored Strings with the type of ArrayList
        ArrayList<WebPageInformation>[] B = new ArrayList[n];
        // Create empty buckets
        for (int i = 0; i < n; i ++) {
           B[i] = new ArrayList();
        }

        // Insert A[i] into bucket
        for (int i = 0; i < A.size(); i++) {
            String companyName = A.get(i).getCompanyName();
            if (companyName.length() > 0) {
                char c = companyName.charAt(0);
                // Put A[i] into bucket
                B[c - 'a'].add(A.get(i));
            }
        }

        // Use insertion sort to sort elements in buckets
        for (int i = 0; i < n; i++) {
            insertionSort(B[i]);
        }

        // Clear A and put the sorted company names into it
        A.clear();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < B[i].size(); j++) {
                A.add(B[i].get(j));
            }

        }
    }

    /**
     * Insertion sort
     * Use alphabetical order to sort string of
     * It would be called in bucket sort to sort the elements in buckets
     * @param A
     */
    void insertionSort(ArrayList<WebPageInformation> A){
        int j;
        // A for loop to compare the adjacent elements
        for (j = 1; j < A.size(); j++) {
            // Set a key
            WebPageInformation key = A.get(j);
            // Insert A[j] to the sorted sequence
            int i = j - 1;
            String companyName = key.getCompanyName();
            String companyName2 = A.get(i).getCompanyName();
            while(i >= 0 && companyName2.compareTo(companyName)  > 0)
            {
                A.set(i + 1, A.get(i));
                i = i - 1;
                // refresh company name 2
                if (i >= 0) {
                   companyName2 = A.get(i).getCompanyName();
                }
            }
            A.set((i + 1), key);
            //System.out.println(companyName + " " +companyName2);
        }
    }
}
