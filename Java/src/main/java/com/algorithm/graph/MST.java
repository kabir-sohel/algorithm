package com.algorithm.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

class MST {
    class Edge {
        int a, b, dist;
        public Edge(int a, int[] pointA, int b, int[] pointB){
            this.a = a;
            this.b = b;
            this.dist = Math.abs(pointA[0] - pointB[0]) + Math.abs(pointA[1] - pointB[1]); 
        }
    }
    class UnionFind {
        int[] pa;
        public UnionFind(int n){
            pa = new int[n];
            for(int i = 0; i < n; ++i)pa[i] = i;
        }

        public void union(int u, int v){
            int pu = find(u);
            int pv = find(v);
            if(pu == pv)return;
            if(pu < pv)pa[pv] = pu;
            else pa[pu] = pv;
        }
        public int find(int u){
            if(u == pa[u])return u;
            else return pa[u] = find(pa[u]);
        }
    }
    public int minCostConnectPoints(int[][] points) {
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < points.length; ++i){
            for(int j = i + 1; j < points.length; ++j){
                edges.add(new Edge(i, points[i], j, points[j]));
            }
        }
        return kruskals(points.length, edges);
    }

    private int kruskals(int n, List<Edge> edges){
        Collections.sort(edges, (a, b) -> a.dist - b.dist);
        UnionFind uf = new UnionFind(n);
        int takenEdgeCount = 0;
        int distSum = 0;
        for(int i = 0; i < edges.size() && takenEdgeCount < n - 1; ++i){
            int pa = edges.get(i).a;
            int pb = edges.get(i).b;
            if(uf.find(pa) != uf.find(pb)){
                takenEdgeCount++;
                distSum += edges.get(i).dist;
                uf.union(pa, pb);
            }
        }
        return distSum;
    }

    class Node {
        int id, dist; // distance to this node
        public Node(int id, int dist){
            this.id = id;
            this.dist = dist;
        }    
    }

    private int MSTPrims(int[][] points){
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
        int n = points.length;
        pq.offer(new Node(0, 0)); // first node with distance 0;
        int edgeCount = 0, cost = 0;
        boolean[] visited = new boolean[n];
        while(edgeCount < n){ // ideally n - 1, but first node didn't have edge
            Node node = pq.poll();
            int u = node.id;
            int dist = node.dist;
            if(visited[u])continue;
            edgeCount++;
            cost += dist;
            visited[u] = true;

            for(int i = 0; i < n; ++i)if(!visited[i]){
                int nextDist = Math.abs(points[u][0] - points[i][0]) + Math.abs(points[u][1] - points[i][1]);
                Node next = new Node(i, nextDist);
                pq.offer(next);
            }
        }
        return cost;
    }
}
