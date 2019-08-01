package chapter6;

public class HeapSort<T extends Comparable> {

    public T[] heapSort(T[] inputArr, boolean ascending) {
        Heap<T> heap;

        if (ascending) {
            // Construct a minPQ
            heap = new Heap<T>(inputArr, false);
        } else {
            heap = new Heap<T>(inputArr, true);
        }

        T[] sortedArr = (T[]) new Comparable[inputArr.length];

        for (int i = 0; i < sortedArr.length; i++) {
            // Append the next element
            sortedArr[i] = heap.deleteHighestPriority();
        }

        return sortedArr;
    }
}
