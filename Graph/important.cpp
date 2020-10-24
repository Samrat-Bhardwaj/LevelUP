#include<queue>
#include<vector>
#include<bits/stdc++.h>

using namespace std;

// leet 934 ================

// good question with both dfs n bfs ===============================
vector<vector<int>> dirs={{0,1},{1,0},{-1,0},{0,-1}};
    void make2(vector<vector<int>>& A, int n,int i, int j){
        A[i][j]=2;
        for(vector<int>& dir : dirs){
            int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 && y>=0 && x<n && y<n && A[x][y]==1){
                make2(A,n,x,y);
            }
        }
    }
    
    int makeBridge(vector<vector<int>>& A, int n){
        queue<int> que;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(A[i][j]==2){
                que.push(i*n+j);
                    A[i][j]=-1;
                }
            }
        }
        
        int ans=0;
        while(que.size()>0){
            int s=que.size();
            for(int i=0; i<s; i++){
                int idx=que.front(); que.pop();
                
                int x=idx/n;
                int y=idx%n;
                
                for(vector<int> dir:dirs){
                    int r=x+dir[0];
                    int c=y+dir[1];
                    
                    if(r>=0 && c>=0 && r<n && c<n){
                        if(A[r][c]==1) return ans;
                        if(A[r][c]==0){
                            que.push(r*n+ c);
                            A[r][c]=-1;     
                        }
                    }
                }
            }
            ans++;
        }
        return ans;
    }
    
    int shortestBridge(vector<vector<int>>& A) {
        int n=A.size();
        
        for(int i=0; i<n; i++){
            bool f=false;
            for(int j=0; j<n; j++){
                if(A[i][j]==1){
                    make2(A,n,i,j);
                    f=true;
                    break;
                }
            }
            if(f) break;
        }
       int ans=makeBridge(A,n);
        
        return ans;
    }