package chapter8;


public class CountingSort<T> {
    public static int[] countingSort(int[] unsortedArray) {
        /*
        Sorts the array using counting sort

        Parameters:
            unsortedArray: The unsorted array to sort

        Returns:
            The sorted array
         */

        return countingSortHelper(unsortedArray, true);
    }

    public static int[] countingSort(int[] unsortedArray, boolean increasing) {
        return countingSortHelper(unsortedArray, increasing);
    }

    private static int[] countingSortHelper(int[] unsortedArray, boolean increasing) {
        // Determine the max of the array
        int max = Integer.MIN_VALUE;

        for (int element : unsortedArray) {
            // Check against max
            if (element > max) {
                max = element;
            }
        }

        // Determine the min of the array for shifting
        int min = Integer.MAX_VALUE;

        for (int element : unsortedArray) {
            // Check against min
            if (element < min) {
                min = element;
            }
        }

        // Build a counts array
        int[] countArray = new int[max - min + 1];

        // Loop over unsorted array and count occurrences
        for (int element : unsortedArray) {
            countArray[element - min] += 1;
        }

        // Loop over elements and copy them into array
        int currentPlacementIndex = 0; // Where to place the next element when we encounter it
        int count;

        for (int i = 0; i < countArray.length; i++) {
            for (int j = 0; j < countArray[i]; j++) {
                if (increasing) {
                    // Add to the leftmost point
                    unsortedArray[currentPlacementIndex] = i + min;
                    currentPlacementIndex += 1;
                } else {
                    // Add to the rightmost point
                    unsortedArray[unsortedArray.length - currentPlacementIndex - 1] = i + min;
                    currentPlacementIndex += 1;
                }
            }
        }

        return unsortedArray;
    }
}
