package com.algorithm.datastructure;
/*
 * source : https://www.topcoder.com/thrive/articles/Binary%20Indexed%20Trees
 */

import java.util.Arrays;

public class BIT {
    private int n;
    int[] tree;
    public BIT(int n){
        this.n = n;
        tree = new int[n + 1];
    }
    public BIT(int n, int[] nums){
        this.n = n;
        tree = new int[n + 1];
        for(int i = 0; i < nums.length; ++i){
            update(nums[i], 1);
        }
    }

    public void update(int idx, int val){
        while(idx <= n){
            tree[idx] += val;
            idx += (idx & -idx);
        }
    }

    public int read(int idx){
        int sum = 0;
        while(idx > 0){
            sum += tree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    public int readSingle(int idx){
        int sum = tree[idx];
        if(idx == 0)return sum;

        // say, idx 12, (1100) it stores freq of 4 numbers(9,10,11,12), we need to reduce it.
        int stopIdx = idx - (idx & -idx); //stop at 8, which contains 1 - 8 freq
        idx--; //start subtracting the lower numbers value, 11, 
        while(idx != stopIdx){
            sum -= tree[idx];
            idx -= (idx & -idx);
        }
        return sum;
    }

    private void printTree(){
        System.out.println("\n" + "Index : \n");
        for(int i = 0; i <= n; ++i){
            System.out.print(String.format("%4d", i));
        }
        System.out.println("\n" + "tree : \n");
        for(int i = 0; i <= n; ++i){
            System.out.print(String.format("%4d", tree[i]));
        }
        System.out.println("\n" + "cumulative : \n");
        for(int i = 0; i <= n; ++i){
            System.out.print(String.format("%4d", read(i)));
        }
        System.out.println("\n" + "F : \n");
        for(int i = 0; i <= n; ++i){
            System.out.print(String.format("%4d", readSingle(i)));
        }
    }

    public static void test(){
        int[] nums = {1, 3, 3, 8, 4, 5, 6, 6, 6, 8, 8, 8, 9, 9, 10, 10, 10, 10, 10, 11, 11, 12, 12, 13, 13, 13, 14, 16, 16};
        System.out.println("Numbers are : \n" + Arrays.toString(nums));
        BIT bit = new BIT(20, nums);
        bit.printTree();

    }
}
