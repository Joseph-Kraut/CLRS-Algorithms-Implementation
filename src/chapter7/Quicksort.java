package chapter7;

import java.util.Random;

public class Quicksort {

    public static Comparable[] quickSort(Comparable[] unsortedArray) {
        return quickSortHelper(unsortedArray, 0, unsortedArray.length - 1, true);
    }

    public static Comparable[] quickSort(Comparable[] unsortedArray, boolean increasing) {
        // The same method but allows the user to specify increasing or decreasing
        return quickSortHelper(unsortedArray, 0, unsortedArray.length - 1, increasing);
    }

    private static Comparable[] quickSortHelper(Comparable[] unsortedArray, int low, int high, boolean increasing) {
        /*
        Quicksorts the given input array

        Parameters:
            unsortedArray: The array we wish to quicksort
            low: The lowest index to consider for sorting (inclusive)
            high: The highest index to consider for sorting (inclusive)

        Returns:
            The input array, now sorted
         */

        // If we are trying to quicksort a single element
        if (high <= low) {
            return unsortedArray;
        }

        // Get a random partition element and partition around it
        int partitionIndex = low + generateRandomIndex(high - low);

        // First get the element out of the way by swapping it to the end of the array
        swap(unsortedArray, partitionIndex, high);

        // Loop over elements to partition them correctly
        int currentRightPartition = low - 1;

        for (int i = low; i < high; i++) {
            // If this element should be in the left of the partition swap it to reflect this
            if (compareObjects(unsortedArray[i], unsortedArray[high], increasing) <= 0) {
                // Move this element to the left
                currentRightPartition += 1;
                swap(unsortedArray, currentRightPartition, i);
            }
        }

        // Now move the partitioning element right above the left partition
        swap(unsortedArray, currentRightPartition + 1, high);

        // Finally, recursively quicksort the partitions
        // First the left partition
        unsortedArray = quickSortHelper(unsortedArray, low, currentRightPartition, increasing);
        unsortedArray = quickSortHelper(unsortedArray, currentRightPartition + 2, high, increasing);

        return unsortedArray;
    }

    private static int compareObjects(Comparable obj1, Comparable obj2, boolean isIncreasing) {
        /*
        Compares two objects to determine their sorting order

        Parameters:
            obj1: The first object in the comparison
            obj2: The second object in the comparison
            isIncreasing: Whether or not the sorting order is ascending

        Returns:
            -1 if obj1 < obj2
            0 if obj1 == obj2
            1 if obj1 > obj2
         */

        if (isIncreasing) {
            return obj1.compareTo(obj2);
        } else {
            return -1 * obj1.compareTo(obj2);
        }
    }

    private static Object[] swap(Object[] array, int index1, int index2) {
        /*
        Swaps the elements in respective indices

        Parameters:
            array: The array to swap elements in
            index1: The first index to swap
            index2: The second index to swap

        Return:
            The array with the swapped values
         */

        Object temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;

        return array;
    }

    private static int generateRandomIndex(int arrayLength) {
        Random rand = new Random();

        return rand.nextInt(arrayLength);
    }
}
