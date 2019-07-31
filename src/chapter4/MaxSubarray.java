package chapter4;

import java.util.Arrays;

public class MaxSubarray{

    public static void main(String[] args) {
        // Simple test cases for sanity checking
        double[] testArray = new double[] {-1.0, 2.0, 3.0, -1.0, 5.0};
        double[] maxSubarray = maxSubarray(testArray);

        System.out.println(Arrays.toString(maxSubarray));
    }

    private static double[] maxCrossingSubarray(double[] arr, int lowIndex, int middleIndex, int highIndex) {
        /*
        This method finds the maximum contiguous sub-array that crosses middleIndex

        Parameters:
            arr: The array to find the maximum crossing sub-array of
            lowIndex: The minimum index (inclusive) to consider for the sub-array
            middleIndex: The middle index that we must cross in our sub-array
            highIndex: The maximum index (inclusive) that we consider for the sub-array


        Returns: A tuple of (bestIndexLeft, bestIndexRight) that establish the inclusive bounds on the best
        crossing sub-array
         */

        // First find the starting index in left sub-array
        float sum = 0;
        float bestSumLeft = Float.NEGATIVE_INFINITY;
        int bestLeftIndex = middleIndex;

        // Determine starting index in left subarray
        for (int i = middleIndex; i >= lowIndex; i--) {
            // Loop over the array elements from middle extending left
            sum += arr[i];

            if (sum > bestSumLeft) {
                bestLeftIndex = i;
                bestSumLeft = sum;
            }
        }

        // Determine the right hand bound
        sum = 0;
        float bestSumRight = Float.NEGATIVE_INFINITY;
        int bestRightIndex = middleIndex;

        // Loop over the right sub-array tracking the highest sum
        for (int i = middleIndex; i <= highIndex; i++) {
            sum += arr[i];

            if (sum > bestSumRight) {
                bestRightIndex = i;
                bestSumRight = sum;
            }
        }

        // Catch an edge case where the best sub-array is just the middle element
        if (bestLeftIndex == bestRightIndex) {
            return new double[] {bestLeftIndex, bestRightIndex, bestSumLeft};
        }

        // Otherwise return a normal crossing sub-array
        return new double[] {bestLeftIndex, bestRightIndex, bestSumLeft + bestSumRight - arr[middleIndex]};
    }

    private static double[] maxSubarrayHelper(double[] arr, int low, int high) {
        /*
        Returns the maximum sub-array of the
        Parameters:
            arr: The array that we wish to find the maximum sub-array of
            low: The minimum index (inclusive) that we can search in the array for
            high: The maximum index (inclusive) that we can search in the array for

        Returns:
            An array of doubles containing the low boundary, high boundary, and sum of array
         */

        // Use divide an conquer strategy
        if (low == high) {
            // We have reached a recursive base case in which case we return the single element
            return new double[] {low, high, arr[low]};
        }

        // Otherwise we split the array into left and right and recurse
        int middleIndex = (int) Math.floor((low + high) / 2);

        double[] leftArrData = maxSubarrayHelper(arr, low, middleIndex);
        double[] rightArrData = maxSubarrayHelper(arr, middleIndex + 1, high);
        double[] crossingArrData = maxCrossingSubarray(arr, low, middleIndex, high);

        if (leftArrData[2] > rightArrData[2] && leftArrData[2] > crossingArrData[2]) {
            // Left array is the highest sub-array here, so return leftArrData
            return leftArrData;
        } else if (rightArrData[2] > leftArrData[2] && rightArrData[2] > crossingArrData[2]) {
            // Right sub-array is the largest, so return that
            return rightArrData;
        } else {
            // Crossing sub-array is the largest, return that
            return crossingArrData;
        }
    }

    public static double[] maxSubarray(double[] arr) {
        if (arr.length == 0) {
            // Empty array, return null values
            return new double[] {0.0, 0.0, 0.0};
        }

        // Call the helper method
        return maxSubarrayHelper(arr, 0, arr.length - 1);
    }
}
