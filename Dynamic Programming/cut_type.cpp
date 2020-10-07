#include<iostream>
#include<vector>

using namespace std;

void display_2D(vector<vector<int>>& qb){
    for(vector<int>& temp:qb){
        for(int e:temp) cout<<e<<" ";
        cout<<"\n";
    }
}

//  CUT-TYPE =============== CUT-TYPE =========== CUT-TYPE ==============================
int MCM_rec(vector<int>& arr, int si,int ei, vector<vector<int>>& qb){
    if(si+1==ei) return qb[si][ei]=0;  // in case of single array 
   
    if(qb[si][ei]!=-1) return qb[si][ei];

    int myAns=1e8;
    for(int cut=si+1;cut<ei; cut++){
        int left=MCM_rec(arr,si,cut,qb); // cut k respective left vector ka ans
        int right=MCM_rec(arr,cut,ei,qb); // "  "     "       right  "    "  "

        int recAns=left + arr[si]*arr[ei]*arr[cut] + right;  
        myAns=min(myAns,recAns); // ab is for loop se bahot answers aayenge, usme se minimum=myAns;
    }
    return qb[si][ei]=myAns;
}

int MCM_tab(vector<int>& arr, vector<vector<int>>& qb){
    for(int gap=1; gap<arr.size(); gap++){ // we are startung from gap=1, 0 se krenge bhi to koi farak nhi
        for(int si=0,ei=gap; ei<arr.size(); si++,ei++){ // but 1 se start kiya kyunki si==ei mtlb ek matrix
            if(si+1==ei){                  // p cut lagana, which is absurd 
                qb[si][ei]=0;       // si+1 == ei mtlb ek matrix jo ki 0 operatins legi; 
                continue;
            }

            int myAns=1e8;
            for(int cut=si+1;cut<ei; cut++){
                int left=qb[si][cut];//MCM_rec(arr,si,cut,qb);
                int right=qb[cut][ei];//MCM_rec(arr,cut,ei,qb);

                int recAns=left + arr[si]*arr[ei]*arr[cut] + right;
                myAns=min(myAns,recAns);
            }
            qb[si][ei]=myAns;
        }
    }
    return qb[0][arr.size()-1];
}

void matrixChainMultiplication(){
    vector<int> arr={4,2,3,1,3};
    vector<vector<int>> qb(arr.size(),vector<int>(arr.size(),0));
    int ans=0;
    //ans=MCM_rec(arr,0,arr.size()-1,qb);
    ans=MCM_tab(arr,qb);
    cout<<ans<<"\n";
    display_2D(qb);
}

void cutType(){
    matrixChainMultiplication();
}


void solve(){
    cutType();    
}

int main(){
    solve();
    return 0;
}