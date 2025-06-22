package com.algorithm.sorting;
/*
 * problem : https://leetcode.com/problems/reverse-pairs
 */

public class MergeSort {
    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    private int mergeSort(int[] nums, int start, int end){ //all inclusive
        if(start >= end)return 0;
        int mid = (start + end) / 2;
        int count = mergeSort(nums, start, mid) + mergeSort(nums, mid + 1, end);
        //2, 3, 7   : 1, 5
        //count pairs, for each i, find the proper position on the right side
        int j = mid + 1;
        for(int i = start; i <= mid; ++i){
            while(j <= end && nums[i] > 2L * nums[j])j++;
            count += (j - (mid + 1));
        }
        merge(nums, start, mid, end);
        return count;
    }
    //[2, 3, 7], [1,5]
    private void merge(int[] nums, int start, int mid, int end){
        int[] temp = new int[end - start + 1]; //as they are inclusive
        int k = 0; //merged index
        int i = start, j = mid + 1;
        while(i <= mid && j <= end){
            if(nums[i] <= nums[j])temp[k++] = nums[i++];
            else temp[k++] = nums[j++];
        }
        // take the pending amount;
        while(i <= mid)temp[k++] = nums[i++];
        while(j <= end)temp[k++] = nums[j++];
        System.arraycopy(temp, 0, nums, start, temp.length);
    }
}
