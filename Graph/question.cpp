#include<queue>
#include<vector>
#include<bits/stdc++.h>
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

    // leet 994 ============================================================================
    int orangesRotting(vector<vector<int>>& grid) {
        queue<int> que;
        
        int n=grid.size();
        int m=grid[0].size();
        int num=0; // number of fresh oranges
        int level=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(grid[i][j]==1){
                    num++;
                }
                if(grid[i][j]==2){
                    que.push((i*m) + j);
                }
            }
        }
        
        if(num==0) return 0;
        vector<vector<int>> dirs={{0,1},{-1,0},{1,0},{0,-1}};
        while(que.size()>0){
            int s=que.size();
            while(s--){
                int idx=que.front();
                que.pop();
                
                int r=idx/m;
                int c=idx%m;
                
                if(grid[r][c]==0) continue;
                
                for(vector<int> dir:dirs){
                    int x=r+dir[0];
                    int y=c+dir[1];
                    
                    if(x>=0 && y>=0 && x<n && y<m && grid[x][y]==1){
                        grid[x][y]=2; // rotten 
                        num--;
                        que.push((x*m) +y);
                    }
                }
            }
            level++;
            if(num==0) break;
        }  
        return num==0 ? level : -1; // at the end level will be time
    }

// leet 286 ========================================================================

// https://www.lintcode.com/problem/walls-and-gates/description ===================

 void wallsAndGates(vector<vector<int>> &rooms) {
        // write your code here
        queue<int> que;
        
        int n=rooms.size();
        int m=rooms[0].size();
        
        if(n==0) return;
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(rooms[i][j]==0){
                    que.push(i*m + j);
                }
            }
        }
        
        int level=0;
        vector<vector<int>> dirs={{0,1},{-1,0},{1,0},{0,-1}};
        
        while(que.size()>0){
            int s=que.size();
            while(s--){
                int idx=que.front();
                que.pop();
                
                int r=idx/m;
                int c=idx%m;
                
                for(vector<int> dir: dirs){
                    int x=r+dir[0];
                    int y=c+dir[1];
                    
                    if(x>=0 && y>=0 && x<n && y<m && rooms[x][y]==2147483647){
                        rooms[x][y]=level+1;
                        que.push(x*m +y);
                    }
                }
            }
            level++;
        }
    }

    // leet 296 ===========================================================================

    // https://www.lintcode.com/problem/best-meeting-point/description =================

    // method-1, pure bfs, will give TLE when everywhere its 1;

    int minTotalDistance(vector<vector<int>> &grid) {
        // Write your code here
        queue<int> que;
        
        int n=grid.size();
        int m=grid[0].size();
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(grid[i][j]==1){
                    que.push(i*m +j);
                    grid[i][j]=0;
                }        
            }
        }
        vector<vector<int>> dirs={{0,1},{-1,0},{1,0},{0,-1}};
        while(que.size()>0){ //applying bfs for every poisition where its 1;
            vector<vector<bool>> vis(n,vector<bool>(m,false));
            int i=que.front();
            que.pop();
            
            queue<int> temp;
            temp.push(i);
            int dis=1;
            while(temp.size()>0){
                int s=temp.size();
                
                while(s--){
                    int idx=temp.front();
                    temp.pop();
                    
                    int r=idx/m;
                    int c=idx%m;
                    vis[r][c]=true;
                    for(vector<int> dir: dirs){
                        int x=r+dir[0];
                        int y=c+dir[1];
                        
                        if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]){
                            grid[x][y]+=dis;
                            vis[x][y]=true;
                            temp.push(x*m +y);
                        }
                    }
                }
                dis++;
            }
        }
        
        int ans=(int)1e8;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                //cout<<grid[i][j]<<" ";
                ans=min(ans,grid[i][j]);
            }
           // cout<<"\n";
        }
        return ans;
    }

    /* method-2, can be used when bfs fail(using manhattan principle ====================*/

    //manhattan principle -> The distance is calculated using Manhattan Distance, 
    //where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
    int getDistance(vector<int>& points){
        sort(points.begin(),points.end());

        int res=0;
        int i=0;
        int j=points.size()-1;
        while(i<j){
            res+=points[j]-points[i]; // |p2 - p1|
            i++;
            j--;
        }
        return res;
    }

    int minTotalDistance(vector<vector<int>> &grid) {
        vector<int> xpoints;
        vector<int> ypoints;

        for(int i=0; i<grid.size(); i++){
            for(int j=0; j<grid[0].size(); j++){
                if(grid[i][j]==1){
                    xpoints.push_back(i); // every x-point where 1 is
                    ypoints.push_back(j); // every y-point where 1 is
                }
            }
        }

        int ans=0;
        ans+=getDistance(xpoints);
        ans+=getDistance(ypoints);

        return ans;
    }

// leet 542 =============================================================================

    vector<vector<int>> dirs={{0,1},{1,0},{-1,0},{0,-1}};
    
    vector<vector<int>> updateMatrix(vector<vector<int>>& matrix){
        int n=matrix.size();
        int m=matrix[0].size();
        queue<int> que;
        vector<vector<int>> ans(n,vector<int>(m,-1));
        vector<vector<bool>> visit(n,vector<bool>(m,false));
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(matrix[i][j]==0){
                    que.push(i*m +j);  // pushing all 0 in our ques because these 0 have power 
                    ans[i][j]=0;       // to update the ans 
                } else {                    
                    ans[i][j]=(int)1e8;
                }
            }
        }
        
        // we are not keeping visited because we are just moving in four directions 
        // we can improve runtime by adding visited 
        while(que.size()){
            int idx=que.front(); que.pop();
            
            int i=idx/m;
            int j=idx%m;
            
            for(vector<int>& dir:dirs){
                    int x=i+dir[0];
                    int y=j+dir[1];

                    if(x>=0 && y>=0 && x<n && y<m){
                        if(ans[i][j]+1<ans[x][y]){  // if curr ans + 1 has power to update 
                            ans[x][y]=ans[i][j]+1;  // ans, we update it and add it to queue
                            que.push((x*m) + y);   // as it may update further answers 
                        } 
                        
                    }
                }
        }
        
        return ans;
    }

// leet 815 =================================================================
        int numBusesToDestination(vector<vector<int>>& routes, int S, int T) {
         if(S==T) return 0;
        
        unordered_map<int,vector<int>> ma;
        unordered_set<int> se;
        
        int n=routes.size();
        
        for(int i=0; i<n; i++){
            for(int busStand:routes[i]){
                ma[busStand].push_back(i);  // konse stand p kon konsi bus ati h, uska map
            }
        }
        
        queue<int> que;
        vector<bool> vis(n,false); // to see which bus we have already visited
        que.push(S);  
        se.insert(S);
        
        int level=0;
        while(que.size()>0){
            int size=que.size();
            while(size--){
                int src=que.front(); que.pop();
                
                for(int bus:ma[src]){ 
                    if(vis[bus]) continue; //boarding those buses which we haven't yet
                    
                    vis[bus]=true;
                    for(int busStand: routes[bus]){
                        if(se.find(busStand)==se.end()){  //getting to those stand only 
                            if(busStand==T) return level+1;// which we have not visited yet
                                                            // using the bus
                            que.push(busStand);
                        }
                    }
                }
            }
            level++;
        }
        return -1;
    }    

// union find ====================================================================

// leet 684 =============

class Solution {
public:
    vector<int> par;
    int findPar(int u){
        if(par[u]==u) return u;
        return par[u]=findPar(par[u]);
    }
    
    vector<int> findRedundantConnection(vector<vector<int>>& edges) {
        int n=edges.size();
        for(int i=0; i<=n; i++){
            par.push_back(i);
        }
        
        for(vector<int> a : edges){
            int gp1=findPar(a[0]);
            int gp2=findPar(a[1]);
            
            if(gp1!=gp2){   // connecting edge between these two by merging
                par[gp1]=par[gp2]; // or par[gp2]=par[gp1];
            } else {
                return a;  // if same parent, then this edge is giving cycle;
            }
        }
        return {};
    }
};

// leetcode 1061 ===================

// https://nataliekung.gitbook.io/ladder_code/dfs/lexicographically-smallest-equivalent-string
vector<int> par(26,-1);
int findPar(int u){
    if(par[u]==u) return u;

    return par[u]=findPar(par[u]);
}

string smallestEquivalentString(string A, string B, string S){
   
    for(int i=0; i<26; i++){
        par[i]=i;
    }

    int n=A.size();

    for(int i=0; i<n; i++){
        int gp1=findPar(A[i]-'a');
        int gp2=findPar(B[i]-'a'); //finding par

        par[gp1]=par[gp2]=min(gp1,gp2); // merging 
    }

    string ans="";

    for(int i=0; i<n; i++){
        int p=findPar(S[i]-'a');
        ans+=(char)(p+'a');
    }

    return ans;
}


// leet 839 =============================================================

    class Solution {
public:
    vector<int> par;
    int findPar(int u){
        if(par[u]==u) return u;
        
        return par[u]=findPar(par[u]);
    }
    
    bool isSimilar(string s, string t){
        int count=0;
        for(int i=0; i<s.size(); i++){
            if(s[i]!=t[i] && ++count > 2) return false;
        }
        return true;
    }
    
    int numSimilarGroups(vector<string>& A) {
        int n=A.size();
        par.resize(A.size(),0);
        
        for(int i=0; i<n; i++) par[i]=i;
        
        int count=n;
        for(int i=0; i<n; i++){
            int p1=findPar(i);
            
            for(int j=i+1; j<n; j++){
                if(isSimilar(A[i],A[j])){ 
                    int p2=findPar(j);
                    if(p1!=p2){
                        par[p2]=p1;  // if we make p2 parent of p1, parent of i will change & will have 
                                    // to find it again
                        count--;  // if we are merging two things, we are reducing groups
                    }
                }
            }
        }
        return count;
    }
};

// Number of island(leet 200) and its size using union find =========================

class Solution {
public:
    vector<int> par;
    vector<int> size;
    int findPar(int u){
        if(par[u]==u) return u;
        
        return par[u]=findPar(par[u]);
    }
    int numIslands(vector<vector<char>>& grid) {
        if(grid.size()==0 || grid[0].size()==0) return 0;
        int n=grid.size();
        int m=grid[0].size();
        int count=0;
        
        //par.resize(n,vector<int>(m,0)); //but we are just increasing size here by pushing
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(grid[i][j]=='1'){
                    count++; // increasing count of possible islands 
                    par.push_back(i*m + j); 
                    size.push_back(1);
                } else {
                    par.push_back(-1);
                    size.push_back(0);
                }
            }
        }
        
        vector<vector<int>> dirs={{0,1},{1,0}};//we need to check only 2 directions
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(grid[i][j]=='0') continue; // if its '0' we dont want to find its parents
                   int p1=findPar(i*m+j);
                
                for(vector<int> dir:dirs){
                    int x=i+dir[0];
                    int y=j+dir[1];
                    
                    if(x<n && y<m && grid[x][y]=='1'){
                        int p2=findPar(x*m+y);
                        if(p1!=p2){
                            count--; // if same parents, it means we are merging two islands
                            par[p2]=p1;//if we do par[p1]=p2; parent of grid[i][j] will
                                       //change and we will have to calculate it again
                            size[p2]+=size[p1];
                        }    
                    }
                }
            }
        }
// now if we want to find total area of islands, we'll traverse the size array and we'll add only
// those in which par[i]==i (coz they are the connected components); 


// if we hadn't have used the count, we would have to loop through the par array and counted the 
// comps with par[i]==i (gcc basically)

        return count;
    }
};

// leet 305(number of island 2) =============================

// https://ttzztt.gitbooks.io/lc/content/number-of-islands-ii.html ===============================

vector<vector<int>> dirs={{-1,0},{0,-1},{1,0},{0,1}};
vector<int> par;

int findPar(int u){
    if(par[u]==u) return u;

    return par[u]=findPar(par[u]);
}

vector<int> numIslands2(int n, int m, vector<vector<int>> positions){
    par.resize(n*m,0);

    for(int i=0; i<n*m; i++) par[i]=i;

    int count=0;
    vector<int> ans;
    vector<vector<int>> grid(n,vector<int>(m,0));

    for(int i=0; i<positions.size(); i++){
        int x=positions[i][0];
        int y=positions[i][1];

        if(grid[x][y]==0) {
        int p1=findPar(x*m +y);
        count++;
        for(vector<int> dir:dirs){
            int r=x+dir[0];
            int c=y+dir[1];

            if(r>=0 && c>=0 && r<n && c<m && grid[r][c]==1){
                int p2=findPar(r*m +c);
                if(p1!=p2){
                    par[p2]=p1;
                    count--;
                }
            }
        }
       }
       ans.push_back(count);
    }
    return ans;
}