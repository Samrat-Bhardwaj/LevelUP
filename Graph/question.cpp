#include<queue>
#include<vector>
using namespace std;
// leet 200(graph connected components)(gcc) ============================================
 void dfs(vector<vector<char>>& grid, int i, int j, vector<vector<int>>& dirs){
        grid[i][j]='0'; // so that we wont ever come back to this point
            
        for(vector<int> dir:dirs){
           int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 && y>=0 && x<grid.size() && y<grid[0].size() && grid[x][y]=='1'){
                dfs(grid,x,y,dirs);
            }
        }    
    }
    int numIslands(vector<vector<char>>& grid) {
        int count=0;
        
        vector<vector<int>> dirs={{-1,0},{0,1},{1,0},{0,-1}};
        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid[0].size(); j++){
                if(grid[i][j]=='1'){
                    dfs(grid,i,j,dirs); // hum isme 0 kar chuke visited points ko
                    count++;
                }
            }
        }
        return count;
    }

// leet 695 ===========================================================================
     int dfs(vector<vector<int>>& grid, int i, int j, vector<vector<int>>& dirs){
        grid[i][j]=0; // so that we wont ever come back to this point
        
        int ans=0;    
        for(vector<int> dir:dirs){
           int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 && y>=0 && x<grid.size() && y<grid[0].size() && grid[x][y]==1){
                ans+=dfs(grid,x,y,dirs)+1;
            }
        }    
         return ans;
    }
    int maxAreaOfIsland(vector<vector<int>>& grid) {
        int ans=0;
        
        vector<vector<int>> dirs={{-1,0},{0,1},{1,0},{0,-1}};
        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid[0].size(); j++){
                if(grid[i][j]==1){
                    ans=max(ans,dfs(grid,i,j,dirs)+1); //+1 kyunki ek point hum khud bhi to h jo count nhi hora
                }
            }
        }
        return ans;
    }

// leet 130 =======================================================================
     void dfs(vector<vector<char>>& board, int i, int j, vector<vector<int>>& dirs){
        board[i][j]='$'; // marking anything but "X" && "O";
        for(vector<int> dir: dirs){
            int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 && y>=0 && x<board.size() && y<board[0].size() && board[x][y]=='O'){
                dfs(board,x,y,dirs);
            }
        }
        
    }

    void solve(vector<vector<char>>& board) {
         vector<vector<int>> dirs={{-1,0},{0,1},{1,0},{0,-1}};
        for(int i=0; i<board.size(); i++){
            for(int j=0; j<board[0].size(); j++){
                if(i==0 || j==0 || i==board.size()-1 || j==board[0].size()-1) // checking at boundary
                    if(board[i][j]=='O')
                       dfs(board,i,j,dirs);
            }
        }
        
         for(int i=0; i<board.size(); i++){
            for(int j=0; j<board[0].size(); j++){
                if(board[i][j]=='O') board[i][j]='X'; // marking surrounded region
                    if(board[i][j]=='$') // marking back it to O
                         board[i][j]='O';
                    
            }
        }
    }

    // leet 463 ==================================================================== 
     int islandPerimeter(vector<vector<int>>& grid) {
        int num=0;
        int nbr=0;
         vector<vector<int>> dirs={{-1,0},{0,1},{1,0},{0,-1}};
        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid[0].size(); j++){
                if(grid[i][j]==1){
                    num++;
                    for(vector<int> dir: dirs){
                        int x=i+dir[0];
                        int y=j+dir[1];
                        
                        if(x>=0 && y>=0 && x<grid.size() && y<grid[0].size() && grid[x][y]==1){
                            nbr++;
                        }
                    }
                    
                }
            }
        }
        return 4*num-nbr;
    }

    // set-2 -> bfs =============================================================================

    // leet 785 ========================================================================

      //bipartite - if we can color consecutive vtx with 2 different color, then its bipartite
    
    // graph with 0 cycles or with cycles of only even length is always bipartite 
   
    // if odd length cycle, then it is bipartite
    bool isEven(int src, vector<vector<int>>& graph, vector<int>& vis){
        queue<int> que;
        que.push(src);
        
        int color=0;
        while(que.size()>0){
            int s=que.size();
            
            while(s--){
                int vtx=que.front();
                que.pop();
                
                if(vis[vtx]!=-1){ // cycle
                    if(vis[vtx]!=color){ //odd length cycle;
                        return false;
                    }
                    continue;
                }
                vis[vtx]=color;
                for(int e:graph[vtx]){
                    if(vis[e]==-1)
                        que.push(e);
                }
            }
            color=(color+1)%2;
        }
        return true;
    }
    bool isBipartite(vector<vector<int>>& graph){
        int N=graph.size();
        vector<int> vis(N,-1);
        
        for(int i=0; i<N; i++){
            if(vis[i]==-1){
                bool ans=isEven(i,graph,vis);
                
                if(!ans) return false;
            }
        }
        return true;
    }


// leet 1091 ================================================================================
   int shortestPathBinaryMatrix(vector<vector<int>>& grid){
        queue<int> que;
        int n=grid.size();
        int m=grid[0].size();
        if(n==0 || m==0 || grid[0][0]==1) return -1;
        
         vector<vector<int>> dirs={{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
        
        que.push(0);
        int level=1;
        while(que.size()>0){
            int s=que.size();
            while(s--){
                int idx=que.front();
                que.pop();
                
                int r=idx/n;
                int c=idx%m;
                if(r==n-1 && c==m-1) return level;
               
                
                for(vector<int> dir:dirs){
                    int x=r+dir[0];
                    int y=c+dir[1];
                    
                    if(x>=0 && y>=0 && x<n && y<m && grid[x][y]==0){
                        grid[x][y]=1;
                        que.push(x*n+y);
                    }
                }
            }
            level++;
        }
        return -1;
    }