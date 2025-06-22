package com.algorithm;

public class BinarySearch {
    /*
     * 1. Half-open interval: [left, right) right is exclusive
     * Loop condition: while (left < right)
     * Common in "lower_bound"-style problems
     * Clean when working with indices pointing to valid insertion positions
     */
    public int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length; // right is exclusive
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid; // arr[mid] >= target, this is excluded, beg will converge here
            }
        }
        return left; // or right, both are same here
    }

    /*
     * right is inclusive Loop condition: while (left <= right)
     * Useful when looking for actual values or predecessors
     * Makes it easier to track valid values (e.g., last element < target)
     */
    public static int lowerPredecessor(int[] arr, int target) {
        int left = 0, right = arr.length - 1; //inclusive
        int ans = -1; // default: no element < target

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                ans = mid;      // potential candidate
                left = mid + 1; // try to find a bigger one
            } else {
                right = mid - 1;
            }
        }
        return ans; // index of largest element < target
    }


    public static void test() {
        BinarySearch bs = new BinarySearch();
        int[] arr = {1, 3, 3, 5, 7, 9};
        System.out.println(bs.lowerBound(arr, 3)); // Output: 1
        System.out.println(bs.lowerBound(arr, 4)); // Output: 3
        System.out.println(bs.lowerBound(arr, 10)); // Output: 6


        //test lower Predecessor
        int[] arr2 = {1, 3, 3, 5, 7, 9};
        System.out.println(lowerPredecessor(arr2, 4));  // Output: 2 (value = 3)
        System.out.println(lowerPredecessor(arr2, 1));  // Output: -1 (no element < 1)
        System.out.println(lowerPredecessor(arr2, 10)); // Output: 5 (value = 9)
    }
}

