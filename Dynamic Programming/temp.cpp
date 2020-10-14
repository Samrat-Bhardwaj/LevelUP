#include<iostream>
#include<vector>

using namespace std;

int minCost_rec(vector<int>& hou,vector<vector<int>>& cost,int i,int j,int k, int m,int n,int tar){
        if(i==m){
            for(int z=0; z<m; z++){
                cout<<hou[z]<<" ";
            }
            cout<<"\n";
            if(k==tar){
                return 0;
            }
            return -1;
        }
    
    int ans=0;
    if(i!=0){
        if(hou[i]!=0){
            if(hou[i-1]!=hou[i]){
                k+=1;
            }
                if(k>tar) return -1;
                ans=minCost_rec(hou,cost,i+1,j,k,m,n,tar);
        } else if(k==tar){
            hou[i]=j+1;
            int temp=minCost_rec(hou,cost,i+1,j,k,m,n,tar);
            if(temp!=-1){
                ans=temp+cost[i][j];
            } else {
                hou[i]=0;
                return -1;
            }
            hou[i]=0;
        } else {
            int temp=(int)1e8;
            for(int z=0; z<n; z++){
                hou[i]=z+1;
                if(hou[i-1]!=hou[i]) k+=1;
                int yo=minCost_rec(hou,cost,i+1,z,k,m,n,tar);
                if(yo!=-1) temp=min(temp,yo+cost[i][z]);
            }
            hou[i]=0;
            if(temp==(int)1e8) return -1;
            ans=temp;
        }
    } else {
        if(hou[i]!=0){
            j=hou[i]-1;
            k+=1;
            int temp=minCost_rec(hou,cost,i+1,j,k,m,n,tar);
            if(temp!=-1){
                ans=temp;
            } else {
                return -1;
            }
        } else {
            int temp=(int)1e8;
            for(int z=0; z<n; z++){
                hou[i]=z+1;
               
                int yo=minCost_rec(hou,cost,i+1,z,k+1,m,n,tar);
                if(yo!=-1) temp=min(temp,yo+cost[i][z]);
            }
            hou[i]=0;
            if(temp==(int)1e8) return -1;
            ans=temp;
        }
    }
    return ans;
}

int minCost(vector<int>& houses, vector<vector<int>>& cost, int m, int n, int target) {
        return minCost_rec(houses,cost,0,0,0,m,n,target);
}