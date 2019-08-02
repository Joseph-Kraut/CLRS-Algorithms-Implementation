package chapter8;
import java.util.ArrayDeque;

public class LSDRadixSort {
    public static int[] radixSort(int[] unsortedArray) {
        return radixSortHelper(unsortedArray, true);
    }

    public static int[] radixSort(int[] unsortedArray, boolean ascending) {
        return radixSortHelper(unsortedArray, ascending);
    }

    private static int[] radixSortHelper(int[] unsortedArray, boolean ascending) {
        /*
        Sorts the array with by the least significant digit radix method

        Parameters:
            unsortedArray: The unsorted array we wish to radix sort
            ascending: Whether or not we should sort from smallest to largest element
         */

        // Get radix length
        int maxWidth = getNumDigits(maxElement(unsortedArray));

        // This array stores the elements of each digit
        ArrayDeque<Integer>[] deques = new ArrayDeque[10];

        // Populate the array deques list
        for (int i = 0; i < deques.length; i++) {
            deques[i] = new ArrayDeque<>();
        }

        // Loop over the element width and sort
        int currentDigit, arrayElement, currentPlacementIndex;

        // Loop over digit index
        for (int digitIndex = 1; digitIndex <= maxWidth; digitIndex++) {
            // Loop over array
            for (int elementIndex = 0; elementIndex < unsortedArray.length; elementIndex++) {
                // Get the current array element that we are enqueuing
                arrayElement = unsortedArray[elementIndex];

                // Add this element to the correct queue
                currentDigit = getNthDigit(arrayElement, digitIndex);
                deques[currentDigit].add(arrayElement);
            }

            // Loop over deques and empty them back into the array
            currentPlacementIndex = 0;

            for (int dequeIndex = 0; dequeIndex < 10; dequeIndex++) {
                // Dequeue all elements in the jth deque
                while (deques[dequeIndex].size() > 0) {
                    if (ascending) {
                        unsortedArray[currentPlacementIndex] = deques[dequeIndex].pop();
                        currentPlacementIndex += 1;
                    } else {
                        unsortedArray[unsortedArray.length - currentPlacementIndex - 1] = deques[dequeIndex].removeLast();
                        currentPlacementIndex += 1;
                    }
                }
            }
        }

        return unsortedArray;
    }

    private static int maxElement(int[] array) {
        /*
        Returns the maximum element of an array
         */

        int max = Integer.MIN_VALUE;

        for (int element : array) {
            if (element > max) {
                max = element;
            }
        }

        return max;
    }

    private static int getNumDigits(int number) {
        /*
        Returns the number of digits in a number
         */

        int counter = 0;

        while (number > 0) {
            counter += 1;
            number = Math.floorDiv(number, 10);
        }

        return counter;
    }

    private static int getNthDigit(int number, int n) {
        /*
        Gets the nth least significant digit of number where n is 1 indexed
         */

        return Math.floorDiv(number, (int) Math.pow(10, n - 1)) % 10;
    }
}
