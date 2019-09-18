import java.util.ArrayList;

/**
 *
 * Using Quick sort instead of Heap sort in Programming Assignment 2
 *
 *
 * Heapsort is a tool and an efficient sorting algorithm to sort the scores from each web page
 * In this class we will use sorting algorithm functions and Max-priority Queues to sort scores stored in an array
 * Use max heap to sort scores calculated from each web page
 * Contains Parent, Left, Right, MaxHeapify, BuildMaxHeap, Heapsort, MaxHeapInsert, HeapExtractMax, HeapIncreaseKey
 * and HeapMaximum methods to implement the sorting algorithm by sorting the scores obtained from each web page
 */
public class HeapSort {
    // Member variable
    int heapSize;

    /**
     * Parent node is defined by dividing the child index by 2
     * Time complexity is O(1)
     * @param i child index
     * @return index of parent
     */
    private int Parent(int i) {
        return i / 2 ;
    }

    /**
     * Left child node is found by multiplying parent index by 2
     * Time complexity is O(1)
     * @param i parent index
     * @return index of left child
     */
    private int Left(int i) {
        return 2 * i;
    }

    /**
     * Right child node is found by multiplying parent index by 2
     * Time complexity is O(1)
     * @param i parent index
     * @return index of right child
     */
    private int Right(int i) {
        return 2 * i + 1;
    }

    /**
     * Max_Heapify function makes the largest value stored in the root and maintains the max-heap property
     * It lets the value at A[i] "float down" in the max-heap so that the subtree rooted at index i obeys the
     * max-heap property, and we call this function recursively on subtree
     * Time complexity is O(lgn)
     * @param A array, every element type is generic
     * @param i index
     */
    protected < E extends Comparable< ? super E > >
    void MaxHeapify(E[] A, int i) {
        int largest;  // Largest value
        int l = Left(i);  // Left children
        int r = Right(i);  // Right children

        // Comparing A[i] with its left child A[l], if left child A[l] > A[i], largest value is A[l],
        // otherwise A[i] is the maximum
        if (l < heapSize && A[l].compareTo(A[i]) > 0) {
            largest = l;
        } else {
            largest = i;
        }

        // Comparing A[r] with the largest value that found previously, if A[r] is larger，then keeps A[r] as
        // the largest value
        if (r < heapSize && A[r].compareTo(A[largest]) > 0) {
            largest = r;
        }
        // If the largest value is not i, swap A[i] with A[largest]
        if (largest != i) {
            E temp = A[i];
            A[i] = A[largest];
            A[largest] = temp;

            // Call this function recursively
            MaxHeapify(A, largest);
        }
    }

    /**
     * Build-Max-Heap function goes through the remaining nodes of the tree and runs Max_Heapify for each of them
     * It produces a max-heap from an unsorted input array.
     * Time complexity is O(n)
     * @param A array
     */
    protected < E extends Comparable< ? super E > >
    void BuildMaxHeap(E[] A) {
        heapSize = A.length;

        // The first index is A.length/2 and call MaxHeapify at that node. Each iteration in the loop the index of
        // the node goes down by 1 and call the MaxHeapify until the iteration downto 0 since we start count at 0
        for (int i = A.length / 2; i >= 0; i--) {
            MaxHeapify(A, i);
        }
    }

    /**
     * Heapsort function sorts the array in place
     * Time complexity is O(nlgn)
     * @param A array
     */
    public < E extends Comparable< ? super E > >
    void Heapsort(E[] A) {

        // Heapsort starts by using BuildMaxHeap to build a max-heap on the input array A[0..n-1], where n = A.length
        BuildMaxHeap(A);

        // i is the final position of the array, swap A[0] with A[i]
        for (int i = A.length - 1; i > 0;  i-- ) {

            // Move current root to the end
            E temp = A[0];
            A[0] = A[i];
            A[i] = temp;

            // After discarding A[i], the heapSize decrements by 1
            heapSize = heapSize - 1;

            // Call MaxHeapify to keep the max-heap property since the A[0] swap with A[i]
            MaxHeapify(A, 0);
        }
    }


    /**
     * HeapMaximum Function returns the largest element A[0] in the heap
     * Time complexity is O(1)
     * @param A array
     * @return A[0]
     */
    protected < E extends Comparable< ? super E > >
    E HeapMaximum(E[] A) {
        return A[0];
    }

    /**
     * Heap-Extract-Max function removes and returns the largest element A[0] of the heap
     * Time complexity is O(lgn)
     * @param A array
     * @return max
     */
    protected < E extends Comparable< ? super E > >
    E HeapExtractMax(E[] A) {
        // If there is no element, no node would be discarded
        if (heapSize < 1) {
            System.out.println("Error: heap underflow");
            return null;
        }
        // Swap A[0] with A[heapSize - 1]
        E max = A[0];
        A[0] = A[heapSize - 1];
        heapSize = heapSize -1;

        // keep the Max-heap property
        MaxHeapify(A, 0);
        return max;
    }

    /**
     * HeapIncreaseKey function implements the Increase-key operation
     * Increase the value of an element's key, A[i], to a new value k, which is assumed
     * to be at least as large as the current k
     * Time complexity is O(lg)
     * @param A array
     * @param i index
     * @param key generic type key value
     */
    protected < E extends Comparable< ? super E > >
    void HeapIncreaseKey(E[] A, int i, E key ) {

        // A[i] is the element that we want to increase its key, if key < A[i], there is an error
        if (key.compareTo(A[i]) < 0) {
            System.out.println("Error: new key is smaller than current key ");
        }

        // If key = A[i], A[Parent(i)] < A[i], swap A[i] with A[Parent(i)]
        A[i] = key;
        while (i > 0 && A[Parent(i)].compareTo(A[i]) < 0) {
            E temp = A[i];
            A[i] = A[Parent(i)];
            A[Parent(i)] = temp;
            i = Parent(i);
        }
    }

    /**
     * MaxHeapInsert function
     *  It takes as an input the key of the new element to be inserted into the max-heap
     *  Time complexity is O(lgn)
     * @param A array
     * @param key generic key vaule
     */
    protected < E extends Comparable< ? super E > >
    void MaxHeapInsert (E[] A, E key) {

        // Expand the tree by adding a new leaf whose key is negative infinity
        heapSize = heapSize + 1;
        A[heapSize - 1] = null;  // Use null as min value since generic type
        HeapIncreaseKey(A, heapSize, key);

    }

}
