#include<queue>
#include<vector>
#include<bits/stdc++.h>

using namespace std;

// bfs ==============================================================================================


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

// leet 1129 ==================
// another way of bfs ===================

vector<int> shortestAlternatingPaths(int n, vector<vector<int>>& red_edges, vector<vector<int>>& blue_edges) {
        vector<vector<int>> red(n,vector<int>());
        vector<vector<int>> blue(n,vector<int>());
        
        vector<int> ans(n,-1);
        unordered_set<string> se;
        
        for(int i=0; i<red_edges.size(); i++){
            red[red_edges[i][0]].push_back(red_edges[i][1]);
        }
        
        for(int i=0; i<blue_edges.size(); i++){
            blue[blue_edges[i][0]].push_back(blue_edges[i][1]);
        }
        
        queue<pair<int,int>> que;
        que.push({0,0}); // 0 can be blue and red both
        
        int level=0;
        while(que.size()>0){
            int s=que.size();
            while(s--){
                pair<int,int> edge=que.front();que.pop();
                int u=edge.first;
                int color=edge.second;
                
                string key=to_string(u)+" "+to_string(color); //marking our edge
        
                if(se.find(key)!=se.end()) continue;
                se.insert(key);
                
                if(ans[u]==-1) // agr ye if na likhe to badme dubara ye vtx aane p update ho skta
                ans[u]=level;
                
                // 0 vtx se blue red dono dlva skte
                if(u==0 || color==2){ //humko blue mila, to hum bs red edges dalvayenge 
                    for(int e:red[u]){
                        que.push({e,1}); 
                    }
                }
                
                if(u==0 || color==1){ // humko red mila, blue edges dalvaye
                    for(int e:blue[u]){
                        que.push({e,2});
                    }
                }
            }
            level++;
        }
        return ans;
    }

// leet 310 ====================================================================================

void del(vector<vector<int>>& graph, int e, int leaf){
        int i;
        for(i=0; i<graph[e].size(); i++)
            if(graph[e][i]==leaf)
                break;
        
        graph[e].erase(graph[e].begin()+i);
    }
    vector<int> findMinHeightTrees(int n, vector<vector<int>>& edges) {
        if(n<2) return {0};
        int h=(int)1e8;
        vector<int> leaves;
        
        vector<vector<int>> graph(n,vector<int>());
        
        for(vector<int> edge:edges){
            graph[edge[0]].push_back(edge[1]);
            graph[edge[1]].push_back(edge[0]);
        }
        
        for(int i=0; i<n; i++){
            if(graph[i].size()==1) leaves.push_back(i);  //size 1 hoga to bs leaf hi ban skte 
        }
        
        int total=n; // max 2 hi ho skte h MHT coz 2 hi max centre ho skte
        
        while(total>2){ // ab hum dhire dhire mid m jar h kyunki mid k nodes hi banenge hamare root
            total-=leaves.size();
            vector<int> nleaves;
            
            for(int leaf:leaves){
                for(int e:graph[leaf]){
                    del(graph,e,leaf);
                    if(graph[e].size()==1) nleaves.push_back(e);
                }
            }
            leaves=nleaves;
        }
        
        return leaves;
    }
// question on unnion find ===============================================

// leet 547 ========================================

// easy question -> good for practice =====

class Solution {
public:
    vector<int> par;
    
    int findPar(int u){
        if(par[u]==u) return u;
        
        return par[u]=findPar(par[u]);
    }
    int findCircleNum(vector<vector<int>>& M) {
        int n=M.size();
        int count=0;
        
        for(int i=0; i<n; i++) par.push_back(i);
        
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(M[i][j]==1){
                    int p1=findPar(i);
                    int p2=findPar(j);
                    
                    if(p1!=p2){
                        par[p1]=p2;
                        // count--;
                    }
                }
            }
        }
        for(int i=0; i<n; i++)
            if(par[i]==i) 
                count++;
        
        return count;
    }
};

// babbar bhaiya ===================================================================

// leet 133 =====================

class Solution {
public:
    unordered_map<int,Node*> m;
        
    Node* clone_(Node* node){
        if(!node) return nullptr;
        
        if(m.find(node->val)!=m.end()){
            return m[node->val];
        }
        
        
        
        Node* clone=new Node(node->val);
        m[clone->val]=clone;
        for(Node* neighbor:node->neighbors){
            Node* nbr=clone_(neighbor);
            clone->neighbors.push_back(nbr);
        }
        
        return clone;
    }    
    Node* cloneGraph(Node* node) {
        return clone_(node);
    }
};