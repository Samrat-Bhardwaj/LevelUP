#include<vector>
using namespace std;



// leet 1473 ====================================================================================

    // if we change houses array and then go forward we are using backtracking anf we can't 
    // apply memoization in backtracking;
    
    // therefore in this ques here, we are just taking components that are changing when we
    // move from 0 to m; and applying dp to that cmops;
    
    // i-iterator through the houses array
    // j- previous color we painted ;
    // k- number of neighbours till now
    
int minCost_rec(vector<int>& hou,vector<vector<int>>& cost,int i,int j,int k, int m,int n,int tar, vector<vector<vector<int>>>& dp){
        if(i==m || k>tar){
            if(k==tar){
                return 0;
            }
            return (int)1e8;
        }
    
    if(dp[i][j][k]!=-1) return dp[i][j][k];
    
    
    int ans=(int)1e8;
    if(hou[i]!=0){
        int temp= hou[i]==j ? k : k+1;
        ans=minCost_rec(hou,cost,i+1,hou[i],temp,m,n,tar,dp);
    } else {
        for(int idx=0; idx<n; idx++){
            if(idx+1!=j){
                ans=min(ans,minCost_rec(hou,cost,i+1,idx+1,k+1,m,n,tar,dp)+cost[i][idx]);
            } else{
                ans=min(ans,minCost_rec(hou,cost,i+1,idx+1,k,m,n,tar,dp)+cost[i][idx]);
            }
        }
    }
    
    return dp[i][j][k]=ans;
}
int minCost(vector<int>& houses, vector<vector<int>>& cost, int m, int n, int target) {
        vector<vector<vector<int>>> dp(m+1,vector<vector<int>>(n+1,vector<int>(target+1,-1)));
    
        int ans=minCost_rec(houses,cost,0,0,0,m,n,target,dp);
            return ans>=(int)1e8 ? -1:ans;
}