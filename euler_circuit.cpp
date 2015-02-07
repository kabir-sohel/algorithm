/*
 * Euler Circuit Fleury's algorithm,
 * ref : GeeksforGeeks 
 *
 *
 */


#include <stdio.h>
#include <iostream>
#include <list>
#include <algorithm>
using namespace std;

class Graph{
    int N;
    bool isDirected;
    list<int> *edges;
public:
    Graph(int n = 0){
        N = n;
        isDirected = false;
        edges = new list<int>[n];
    }

    ~Graph(){
        delete[] edges;
    }
    void addEdge(int u, int v){
        edges[u].push_back(v);
        edges[v].push_back(u);
    }
    void removeEdge(int u, int v){
        list<int>::iterator iu = find(edges[u].begin(), edges[u].end(), v);
        if(iu != edges[u].end()) *iu = -1;
        list<int>::iterator iv = find(edges[v].begin(), edges[v].end(), u);
        if(iv != edges[v].end()) *iv = -1;
    }
    void printEulerTour();
    void printEulerUtil(int);
    int totalReachable(int v, bool visited[]);
    bool canTakeThisEdge(int u, int v);

};

//function description
void Graph::printEulerTour(){
    int startFrom = 0;
    for(int i = 0; i < N; ++i)if(edges[i].size() % 2){
        startFrom = i;
        break;
    }
    printEulerUtil(startFrom);
    printf("\n");
}
void Graph::printEulerUtil(int u){
    for(list<int>::iterator it = edges[u].begin(); it != edges[u].end(); ++it) if( *it != -1 ){
        int v = *it;
        if(canTakeThisEdge(u, v)){
            cout << u << "->" << v << " ";
            removeEdge(u, v);
            printEulerUtil(v);
        }
    }
}
bool Graph::canTakeThisEdge(int u, int v){
    int totEdge = 0;
    for(list<int>::iterator it = edges[u].begin(); it != edges[u].end(); ++it)if(*it != -1)totEdge++;
    if(totEdge == 1)return true;
    
    bool visited[N];
    memset(visited, 0, N);
    int beforeCount = totalReachable(u, visited);
    removeEdge(u, v);
    memset(visited, 0, N);
    int afterCount = totalReachable(u, visited);    
    addEdge(u, v);
    list<int>::iterator it = find(edges[u].begin(), edges[u].end(), v);
    return beforeCount > afterCount ? false : true;
}
int Graph::totalReachable(int u, bool visited[]){
    visited[u] = true;
    int count = 1;
    for(list<int>::iterator it = edges[u].begin(); it != edges[u].end(); ++it) if( *it != -1 ){
        if(!visited[*it])count += totalReachable(*it, visited);
    }
    return count;
}

void simulateEurlerCircuit(){
    Graph g1(4);
    g1.addEdge(0, 1);g1.addEdge(0, 2);g1.addEdge(1, 2);g1.addEdge(2, 3);
    g1.printEulerTour();

    Graph g2(3);
    g2.addEdge(0, 1);g2.addEdge(1, 2);g2.addEdge(2, 0);
    g2.printEulerTour();

    Graph g3(5);
    g3.addEdge(1, 0);g3.addEdge(0, 2);g3.addEdge(2, 1);g3.addEdge(0, 3);
    g3.addEdge(3, 4);g3.addEdge(3, 2);g3.addEdge(3, 1);g3.addEdge(2, 4);
    g3.printEulerTour();

}


int main(){
    simulateEurlerCircuit();
    return 0;
}
