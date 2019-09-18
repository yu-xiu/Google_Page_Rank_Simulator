/**
 * This QuickSort class contains the quickSort method and partition method
 * Using quickSort method to sort 30 web pages
 */
public class QuickSort {
    /**
     * Partitioning the array A[p..r]. Choosing r, the last element of the array, as a pivot.
     * Putting all the elements that less than the value of A[r] before A[i], and put all the elements that greater than
     * the value of A[i], then swap A[i+1] with A[r]. In the quickSort method, the partition method will be recursively
     * called, then all the sub arrays would be partitioned and apply quickSort to the sub arrays.
     * @param A
     * @param p
     * @param r
     * @return
     */
    static protected < E extends Comparable< ? super E > >
    int partition(E[] A, int p, int r) {
        // x is a pivot
        E x = A[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++ ) {
            if (j < r && A[j].compareTo(x) <= 0) {
                i = i + 1;
                // Swap A[i] with A[j]
                E temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            }
        }
        // Swap A[i + 1] with A[r]
        E temp =  A[i + 1];
        A[i + 1] = A[r];
        A[r] = temp;
        return i + 1;
    }

    /**
     * quickSort function
     * @param A
     * @param p
     * @param r
     */
    protected < E extends Comparable< ? super E > >
    void quickSort(E[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }
}
