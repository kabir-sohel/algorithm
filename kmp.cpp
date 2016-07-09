#include<stdio.h>
#include<algorithm>
#include<iostream>
#include<string.h>
using namespace std;

#define S 1005
char text[S],pat[S];
int T[S],lent,lenp;
int sum,r,n,N,R,C,t,txt,m;

int KMP(){
    int m = 0,i = 0;
    while(m + i < lent){
        if(text[m+i] == pat[i]){
            ++i;
            if(i == lenp)return m;
        }
        else{
            m = m + i - T[i];
            i = max(0,T[i]);
        }
    }
    return -1;
}
void KMPT(){
    T[0] = -1; T[1] = 0;
    for(int i = 2; i <= lenp; ++i){
        int w = T[i-1];
        while(w >=0 && pat[w] != pat[i-1])w = T[w];
        T[i] = ++w;
    }
}
int main(){
    freopen("1.txt","r",stdin);
    //freopen("out.txt","w",stdout);
    while(gets(text)){
        gets(pat);
        lent = strlen(text);
        lenp = strlen(pat);

        KMPT();
        int pos = KMP();
        cout<<pos<<endl;
    }
    return 0;
}

