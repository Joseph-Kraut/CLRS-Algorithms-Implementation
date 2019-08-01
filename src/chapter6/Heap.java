package chapter6;

public class Heap<T extends Comparable> {
    // The array representing the heap itself
    private T[] heap;
    private int heapSize;
    private boolean isMaxHeap = true;

    public Heap(T[] startingValues) {
        this.heap = (T[]) new Comparable[startingValues.length * 4];
        this.heapSize = startingValues.length;

        // Add the values to the heap
        for (int i = 0; i < this.heapSize; i++) {
            // Copy the respective value into the heap
            this.heap[i] = startingValues[i];
        }

        // Heapify our array
        this.heapify();
    }

    public Heap(T[] startingValues, boolean maxHeap) {
        this(startingValues);
        this.isMaxHeap = maxHeap;
        this.heapify();
    }

    private static int parent(int index) {
        /*
        Returns the array index of the parent given the index of a child

        Parameters:
            index: The integer index of the child whose parent index we want

        Returns:
            The index of the parent node
         */

        // Parent of root is itself
        if (index == 0) {
            return 0;
        }

        return Math.floorDiv(index + 1, 2) - 1  ;
    }

    private int leftChild(int index) {
        /*
        Returns the left child of the node with index i

        Parameters:
            index: The index of the parent node in the underlying array

        Returns:
            The index of the left child of the parent node at i
         */

        int childIndex = 2 * (index + 1) - 1;

        return childIndex > this.heapSize - 1 ? index : childIndex;
    }

    private int rightChild(int index) {
        /*
        Returns the right child of the node with index i

        Parameters:
            index: The index of the parent node in the underlying array

        Returns:
            The index of the right child of the parent node at i
         */

        int childIndex = 2 * (index + 1);

        return childIndex > this.heapSize - 1 ? index : childIndex;
    }

    private int compareElements(int index1, int index2) {
        /*
        Compares two elements for priority in heap as we may be working with a min or max heap

        Parameters:
            index1: The index of the first element in the compare query
            index2: The index of the second element in the compare query

        Returns:
            -1 if index1 has lower priority than index2
            0 if index1 and index 2 have equal priorities
            1 if index1 has higher priority than index2
         */
        if (index1 == index2) {
            return 0;
        }

        int comparison = this.heap[index1].compareTo(this.heap[index2]);

        if (this.isMaxHeap) {
            // If this is a max heap the compareTo method will work already
            return comparison;
        } else {
            // If this is a min heap we must flip priorities so we multiply by -1
            return -1 * comparison;
        }
    }

    private void swap(int index1, int index2) {
        /*
        Swaps the elements of the heap in index1 and index2
         */

        T temp = this.heap[index1];
        this.heap[index1] = this.heap[index2];
        this.heap[index2] = temp;
    }

    private void sink(int index) {
        /*
        Sinks the node in the selected index as far as possible in the heap

        Parameters:
            index: The index of the node we wish to sink
         */

        while (compareElements(index, leftChild(index)) < 0 || compareElements(index, rightChild(index)) < 0) {
            // While the index is less than one of its children
            if (compareElements(leftChild(index), rightChild(index)) > 0) {
                // Left child is bigger, swap with this element
                swap(index, leftChild(index));
                index = leftChild(index);
            } else {
                // Right child is bigger, swap with that element
                swap(index, rightChild(index));
                index = rightChild(index);
            }
        }
    }

    private void swim(int index) {
        /*
        Swims the node in the selected index as far up the heap as possible

        Parameters:
            index: The index of the node to swim
         */

        while (compareElements(index, parent(index)) > 0) {
            // Swap this element with its parent
            swap(index, parent(index));
            index = parent(index);
        }
    }

    private void heapify() {
        /*
        Makes the current heap into a max heap
         */

        for (int i = this.heapSize; i >= 0; i--) {
            sink(i);
        }
    }

    private void heapResize(double resizeFactor) {
        /*
        Resizes the heap when we need more space for new elements

        Parameters:
            resizeFactor: The factor by which to resize our heap array
         */

        // The new length of the heap after resize
        double heapLength = (double) (this.heap.length + 1) * resizeFactor;
        int newLength = (int) heapLength;

        // Construct a new array
        T[] tempArr = (T[]) new Comparable[newLength];

        // Copy the current array over
        for (int i = 0; i < this.heapSize; i++) {
            tempArr[i] = this.heap[i];
        }

        // Set the heap to this new array
        this.heap = tempArr;
    }

    public void addElement(T element) {
        /*
        Adds an element to the heap

        Parameters:
            element: The element (Comparable) to add to the heap
         */

        // Dynamically resize the heap
        if (this.heapSize == this.heap.length) {
            // At max capacity, resize heap by a factor of 2
            heapResize(2.);
        }

        // Add the element to the array
        this.heap[this.heapSize] = element;

        // Swim the element to maintain heap property
        swim(this.heapSize);

        // Increment the heap size
        this.heapSize += 1;
    }

    public T returnHighestPriority() {
        return this.heap[0];
    }

    public T deleteHighestPriority() {
        if (this.heapSize == 0) {
            return null;
        }

        T topElement = this.heap[0];

        // Swap the top and last element
        swap(0, this.heapSize - 1);

        // Decrement heap size
        this.heapSize -= 1;

        // Finally, sink the swapped top
        sink(0);

        // If our new heap size is under a quarter of the array length, halve the array
        if (this.heapSize < this.heap.length / 4) {
            heapResize(0.5);
        }

        return topElement;
    }

    public void changeValue(int index, T newValue) {
        /*
        Changes the value of the specified index

        Parameters
            index: The index of the element to change value
            newValue: The new value to give this element
         */

        // All we have to do is try sinking and swimming
        this.heap[index] = newValue;

        sink(index);
        swim(index);
    }

    public void printHeap() {
        System.out.print('[');

        if (this.heapSize == 0) {
            System.out.println((']'));
            return;
        }

        // Loop over elements
        for (int i = 0; i < this.heapSize - 1; i++) {
            System.out.print(this.heap[i] + ", ");
        }

        // Print the last element and a closing bracket
        System.out.println(this.heap[this.heapSize - 1] + "]");
    }
}
