#include <stdio.h>
#include <iostream>
#include <vector>
using namespace std;

void totient(vector<int> &v){
    int n = v.size();
    v[0] = v[1] = 0;
    for(int i = 2; i < n; ++i)v[i] = i;
    for(int i = 2; i < n; ++i)if(v[i] == i)for(int j = i; j < n; j += i)v[j] = (v[j] / i) * (i - 1);
}

int main(){
    vector<int> v(1000000);
    totient(v);
    for(int i = 0; i <= 100; ++i)cout << i << " " << v[i] << endl;
    return 0;
}
