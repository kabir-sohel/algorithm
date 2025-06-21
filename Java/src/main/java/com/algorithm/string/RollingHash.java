package com.algorithm.string;

import java.util.*;

/*
 * problem : 
 * https://leetcode.com/problems/palindrome-partitioning-ii/
 * https://leetcode.com/problems/shortest-palindrome/
 */

class RollingHash {
    int[] dp;
    String s;
    final int inf = 100000;
    public int minCut(String s) {
        dp = new int[s.length() + 1];
        this.s = s;
        Arrays.fill(dp, inf);
        return minCut(0) - 1;
    }

    private int minCut(int pos){
        if(pos == s.length())return 0;
        if(dp[pos] != inf)return dp[pos];

        List<Integer> options = getCutOptions(pos);
        int res = inf;
        for(int option : options){
            res = Math.min(res, 1 + minCut(option + 1));
        }
        return dp[pos] = res;
    }
    private List<Integer> getCutOptions(int pos){
        List<Integer> options = new ArrayList<>();
        //Rolling Hash
        long forwardHash = 0, backwardHash = 0, base = 29, power = 1, mod = 1000000007;
        for(int i = pos; i < s.length(); ++i){
            char ch = s.charAt(i);
            forwardHash = (forwardHash * base + (ch - '0')) % mod;
            backwardHash = (backwardHash + power * (ch - '0')) % mod;
            power = (power * base) % mod;

            //candidate option
            if(forwardHash == backwardHash){
                options.add(i);
            }
        }
        return options;
    }
}
