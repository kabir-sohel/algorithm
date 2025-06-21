package com.algorithm.datastructure;
/*
 * problem : https://leetcode.com/problems/number-of-islands-ii/description/
 */

import java.util.*;

public class UnionFind {
    Map<Integer, Integer> pa;
    public UnionFind(){
        pa = new HashMap<>();
    }

    public boolean exists(int u){
        return pa.containsKey(u);
    }

    public int find(int u){
        if(u == pa.get(u))return u;
        int pu = find(pa.get(u));
        pa.put(u, pu);
        return pu;
    }

    public void set(int u){
        pa.put(u, u);
    }

    public void union(int u, int v){
        int pu = find(u);
        int pv = find(v);
        if(pu < pv){
            pa.put(pv, pu);
        }
        else pa.put(pu, pv);
    }
}
