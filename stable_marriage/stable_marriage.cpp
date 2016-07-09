/* Stable Marriage Algorithm */

#include<iostream>
#include<stdio.h>
#include<algorithm>
#include<string.h>
using namespace std;
#define S 1005
/* index starts from 0
 * man[i] contains the index of woman ith man matches or -1 if still unengaged, same goes for woman[i]
 * mchoice[i][..], preferance list of ith man, same goes for wchoice[i][..] for the ith woman
 * curWoman[i] is the next woman i'th man wants to propose to
 */

int n, man[S], woman[S], mchoice[S][S], wchoice[S][S], cur_woman[S];


bool can_re_engage(int w, int prev, int cur){
   for(int i = 0; i < n; ++i){
       if(mchoice[w][i] == cur)return true;
       if(mchoice[w][i] == prev)return false;
   }
   return false;
}
void print(){

        printf("\n\nPrinting Stable Marriage\n");
        for(int i = 0; i < n; ++i){
            printf("man %d will be engaged to woman %d\n", i, man[i]);
        }   
}
int main(){
    freopen("1.txt", "r", stdin);
    while(scanf("%d", &n) == 1){
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
                scanf("%d", &mchoice[i][j]);
                mchoice[i][j]--;
            }
        }
        for(int i = 0; i < n; ++i){
            for(int j = 0; j < n; ++j){
                scanf("%d", &wchoice[i][j]);
                wchoice[i][j]--;
            }
        }
        /*
         * Initiall no one is engaged
         */
        memset(man, -1, sizeof man);
        memset(woman, -1, sizeof woman);
        memset(cur_woman, 0, sizeof cur_woman);
        int total_match = 0;
        while(total_match < n){
            for(int i = 0; i < n; ++i)if(man[i] == -1){
                int next_woman = mchoice[i][ cur_woman[i] ];
                ++cur_woman[i];
                if(woman[next_woman] == -1){
                    //next_woman is free
                    man[i] = next_woman;
                    woman[next_woman] = i;
                    total_match++;
                }else {
                    bool re_engage = can_re_engage(next_woman, woman[next_woman], i);
                    if(re_engage){
                        int dumped_man = woman[next_woman];
                        woman[next_woman] = i;
                        man[dumped_man] = -1;
                        man[i] = next_woman;

                    }
                }
            }
        }
        print();
    }
    return 0;
}
