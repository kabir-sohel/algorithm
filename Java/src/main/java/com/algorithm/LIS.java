package com.algorithm;

import java.util.Arrays;

/*
 * problem : https://leetcode.com/problems/russian-doll-envelopes
 */
public class LIS {
    public int maxEnvelopes(int[][] dolls) {
        // Sort based first, if equals, sort based on second in reverse
        // this ensures that if first values are equal. 2nd item can't be correct LIS
        Arrays.sort(dolls, (a, b) -> {
            if(a[0] != b[0])return a[0] - b[0];
            return b[1] - a[1]; //[1,3],[1,5],[2,3] becomes [1,5],[1,3],[2,3]
        });
        
        int[] nums = new int[dolls.length];
        for(int i = 0; i < nums.length; ++i)nums[i] = dolls[i][1]; //take 2nd items
        return LIS(nums); // ans is the LIS of the 2nd items.
    }

    private int LIS(int[] nums){
        /*
         * Maintains a sorted list and finds lower bound of each element,
         * If num's lower bound (smallest number <= num) exists, replace,
         * if all items are smaller, append. This increases the tail
         */
         int[] sorted = new int[nums.length];
         int len = 0;
         for(int num : nums){
            int index = lowerBound(sorted, len, num);
            sorted[index] = num;
            if(index == len)len++;
         }
         return len;
    }

    private int lowerBound(int[] nums, int len, int target){
        // Since this finds lowest max, converging from right, keep the right open
        int b = 0, e = len; //e is exclusive
        while(b < e){
            int m = b + (e - b) / 2;
            if(nums[m] < target) b = m + 1;
            else e = m;
        }
        return b;
    }
}
