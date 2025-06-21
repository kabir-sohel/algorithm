package com.algorithm.geometry;
/*
 * problem : https://leetcode.com/problems/max-points-on-a-line/
 */
import java.util.*;
public class MaximumPoint {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if(n == 1){
            return 1;
        }
        int ans = 2;
        for(int i = 0; i < n; ++i){
            Map<Double, Integer> angleCount = new HashMap<>();
            for(int j = 0; j < n; ++j)if(i != j){
                int[] p1 = points[i];
                int[] p2 = points[j];
                double angle = Math.atan2(p2[1] - p1[1], p2[0] - p1[0]);
                angleCount.merge(angle, 1, Integer::sum);
            }
            ans = Math.max(ans, Collections.max(angleCount.values()) + 1);
        }
        return ans;
    }
}
