package chapter9;

import java.util.ArrayList;
import java.util.Random;

public class RandomizedSelect {

    // This data structure will hold partition information during the algorithm run
    private static class ArrayPartition {
        public Comparable[] leftArray;
        public Comparable[] middleArray;
        public Comparable[] rightArray;

        public ArrayPartition(Comparable[] leftArray, Comparable[] middleArray, Comparable[] rightArray) {
            this.leftArray = leftArray;
            this.middleArray = middleArray;
            this.rightArray = rightArray;
        }
    }

    public static Comparable select(Comparable[] array, int index) {
        /*
        Selects and returns the ith largest (0 indexed) element in the array

        Parameters:
            array: The array to select the order statistic from
            index: The index (1 indexed) of the order statistic to return
            low: The low index on the array to consider
            high: The high index on the array to consider

        Returns:
            The element that is the ith largest in the array
         */

        // Partition the array
        ArrayPartition partition = partitionRandomly(array);

        // Get the data from our partition
        Comparable[] leftArray = partition.leftArray;
        Comparable[] middleArray = partition.middleArray;
        Comparable[] rightArray = partition.rightArray;

        // Determine the partition to recurse on
        if (index >= leftArray.length && index < leftArray.length + middleArray.length) {
            // The parition element contains the order statistic, return the partition element
            return middleArray[0];

        } else if (leftArray.length - 1 >= index) {
            // Element must be in the left array so recurse on this array
            return select(leftArray, index);
        } else {
            // The correct order statistic must be in the right hand array
            return select(rightArray, index - leftArray.length - middleArray.length);
        }

    }

    private static ArrayPartition partitionRandomly(Comparable[] array) {
        /*
        Randomly partitions the array and returns it as an ArrayPartition object
         */

        // First, randomly select an element to partition around
        Random rand = new Random();
        int partitionIndex = rand.nextInt(array.length);

        // Array lists to hold the three partitions
        ArrayList<Comparable> leftList = new ArrayList<>();
        ArrayList<Comparable> middleList = new ArrayList<>();
        ArrayList<Comparable> rightList = new ArrayList<>();

        int comparison; // Will hold the value of comparisons for Comparable objects

        for (int i = 0; i < array.length; i++) {
            comparison = array[i].compareTo(array[partitionIndex]);

            if (comparison < 0) {
                // Element is less than partition element, add to left list
                leftList.add(array[i]);
            } else if (comparison == 0) {
                // Element is equal to the partition element, add to middle list
                middleList.add(array[i]);
            } else {
                // Element is greater than the partition element, add to right list
                rightList.add(array[i]);
            }
        }

        Comparable[] leftArr = new Comparable[leftList.size()];
        Comparable[] middleArr = new Comparable[middleList.size()];
        Comparable[] rightArr = new Comparable[rightList.size()];


        return new ArrayPartition(leftList.toArray(leftArr), middleList.toArray(middleArr), rightList.toArray(rightArr));
    }

    private static Comparable[] swap(Comparable[] array, int index1, int index2) {
        /*
        Swaps the elements of the given array at the given indices
         */

        Comparable temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;

        return array;
    }
}
