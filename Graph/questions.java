import java.util.*;
class questions {
    
// leet 200------------------------------------------------------------------------------    
    //int[][] dirs={{1,0},{0,1},{-1,0},{0,-1}};
    void makeIslandWater(int i, int j, char[][] grid){
        
        grid[i][j]=0; // so that we wont come here again, save space of visited array
        
        for(int[] dir:dirs){
            int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 & y>=0 && x<grid.length && y<grid[0].length && grid[x][y]=='1'){
                makeIslandWater(x,y,grid);
            }
        }
    }
    public int numIslands(char[][] grid) {
        int ans=0;
        //boolean[][] visit=new boolean[grid.length][grid[0].length];
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                if(grid[i][j]=='1'){
                    ans++;
                    makeIslandWater(i,j,grid);
                }
            }
        }
        return ans;
    }

    // leet 130 ==========================================================================

    int[][] dirs={{0,1},{1,0},{-1,0},{0,-1}};
    public void dfs(char[][] board, int i, int j){
        board[i][j]='$';
        
        for(int dir[]: dirs){
            int x=i+dir[0];
            int y=j+dir[1];
            
            if(x>=0 && y>=0 && x<board.length && y<board[0].length && board[x][y]=='O'){
                dfs(board,x,y);
            }
        }
    }
    public void solve(char[][] board) {
        int n=board.length;
        if(n==0) return;
        int m=board[0].length;
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(i==0 || j==0 || i==n-1 || j==m-1){
                    if(board[i][j]=='O'){
                        dfs(board,i,j);
                    }
                }
            }
        }
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(board[i][j]=='$'){
                    board[i][j]='O';
                } else if(board[i][j]=='O'){
                    board[i][j]='X';
                }
            }
        }
    }


//set-2 BFS ============================================================================


// set-3 Topo - Directional graph ====================================================================

// leet 210 - method 1(bfs) =================

public ArrayList<Integer> TopoBFS_01(ArrayList<Integer>[] graph){
    ArrayList<Integer> ans=new ArrayList<>();
    int N=graph.length;
    int[] indegree=new int[N];

    LinkedList<Integer> que=new LinkedList<>();

    for(int i=0; i<N; i++){
        for(int e:graph[i]){
            indegree[e]++;
        }
    }

    for(int i=0; i<N; i++)
        if(indegree[i]==0)
            que.addLast(i);

    while(que.size()>0){
        int r=que.removeFirst();
        ans.add(r);

        for(int i:graph[r]){
            if(--indegree[i]==0) que.addLast(i);
        }
    }
    
    return ans;
    }
    public boolean canFinish(int numCourses, int[][] pre) {
        ArrayList<Integer>[] graph=new ArrayList[numCourses];
        
        for(int i=0; i<graph.length; i++){
            graph[i]=new ArrayList<>();
        }
        
        for(int i=0; i<pre.length; i++){
            int u=pre[i][0];
            int v=pre[i][1];
            
            graph[u].add(v);
        }
        
        ArrayList<Integer> ans=TopoBFS_01(graph);
        
        if(ans.size()!=graph.length){
            return false;  // detected cycle 
        } else {
            return true;
        }
    }

    // method-2 =======

    public static boolean TopoDFS( ArrayList<Integer>[] graph,int src, int[] vis, ArrayList<Integer> ans){
        vis[src]=1;
        for(int e:graph[src]){
            if(vis[e]==0){
                if(TopoDFS(graph,e, vis, ans)) return true;    
            } else if(vis[e]==1) return true;
        }

        ans.add(src);
        vis[src]=2;
        return false;
    }

    public boolean canFinish_(int numCourses, int[][] pre) {
        ArrayList<Integer>[] graph=new ArrayList[numCourses];
        int N=graph.length;
        for(int i=0; i<graph.length; i++){
            graph[i]=new ArrayList<>();
        }
        
        for(int i=0; i<pre.length; i++){
            int u=pre[i][0];
            int v=pre[i][1];
            
            graph[u].add(v);
        }
        
        ArrayList<Integer> ans=new ArrayList<>();
        
        boolean isCycle=false;

        int[] vis=new int[N];
        for(int i=0; i<N; i++){
            if(vis[i]==0)
                isCycle=TopoDFS(graph,i, vis, ans);
            if(isCycle) break;
        }

       return !isCycle;
    }


    // leet 210=========================================================

    //method-1 (bfs) ===========

    public ArrayList<Integer> TopoBFS(ArrayList<Integer>[] graph){
        ArrayList<Integer> ans=new ArrayList<>();
        int N=graph.length;
        int[] indegree=new int[N];

        LinkedList<Integer> que=new LinkedList<>();

        for(int i=0; i<N; i++){
            for(int e:graph[i]){
                indegree[e]++;
            }
        }

        for(int i=0; i<N; i++)
            if(indegree[i]==0)
                que.addLast(i);

        while(que.size()>0){
            int r=que.removeFirst();
            ans.add(r);

            for(int i:graph[r]){
                if(--indegree[i]==0) que.addLast(i);
            }
        }
        
        return ans;
    }
    public int[] findOrder(int numCourses, int[][] pre) {
         ArrayList<Integer>[] graph=new ArrayList[numCourses];
        
        for(int i=0; i<graph.length; i++){
            graph[i]=new ArrayList<>();
        }
        
        for(int i=0; i<pre.length; i++){
            int u=pre[i][0];
            int v=pre[i][1];
            
            graph[v].add(u);
        }
        
        ArrayList<Integer> ans=TopoBFS(graph);
        
        
        if(ans.size()!=graph.length){
            return new int[]{};  // detected cycle 
        } else {
            int[] tr=new int[graph.length];
            for(int i=0; i<ans.size(); i++){
                tr[i]=ans.get(i);
            }
           return tr;
        }
    }
    // method-2-> dfs =======================
    public static boolean TopoDFS_( ArrayList<Integer>[] graph,int src, int[] vis, ArrayList<Integer> ans){
        vis[src]=1;
        for(int e:graph[src]){
            if(vis[e]==0){
                if(TopoDFS(graph,e, vis, ans)) return true;    
            } else if(vis[e]==1) return true;
        }

        ans.add(src);
        vis[src]=2;
        return false;
    }
    
    public int[] findOrder_(int numCourses, int[][] pre) {
        ArrayList<Integer>[] graph=new ArrayList[numCourses];
        int N=graph.length;
        for(int i=0; i<graph.length; i++){
            graph[i]=new ArrayList<>();
        }
        
        for(int i=0; i<pre.length; i++){
            int u=pre[i][0];
            int v=pre[i][1];
            
            graph[u].add(v);
        }
        
        ArrayList<Integer> ans=new ArrayList<>();
        
        boolean isCycle=false;
        

        int[] vis=new int[N];
        for(int i=0; i<N; i++){
            if(vis[i]==0)
            isCycle=TopoDFS(graph,i, vis, ans);
            if(isCycle) break;
        }

       if(isCycle) return new int[0];
        else {
            int[] tr=new int[N];
            for(int i=0; i<N; i++){
                tr[i]=ans.get(i);
            }
            return tr;
        }
    }



// leet 802 =================================================================

// nice question based on 
    boolean topo_dfs(int src, int[][] graph, int[] vis){
        if(vis[src]>0) return vis[src]==1;  // this line is imp bcoz if we get 2, we return false
        vis[src]=1;                         // as thats not part of any cycle 
        
        for(int nbr : graph[src]){
            if(vis[nbr]==1) return true;
            if(topo_dfs(nbr,graph,vis)) return true;
        }
        
        vis[src]=2;
        return false;
    }
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int[] vis=new int[graph.length];  // this will keep knowledge of every vtx if its in any cycle
        List<Integer> ans=new ArrayList<>(); // or not 
        
        for(int i=0; i<graph.length; i++){
                if(vis[i]==0){
                    if(!topo_dfs(i,graph,vis)){
                        ans.add(i);
                    }
                } else if(vis[i]==2){  // if its not in any cycle but got visited due to some other vtx
                    ans.add(i);
                }
        }
        return ans;
    }

// union find =================================================================

// leet 684 ========

class Solution {
    int[] par;
    public int findPar(int u){
        if(par[u]==u) return u;
        
        return par[u]=findPar(par[u]);
    }
    
    public int[] findRedundantConnection(int[][] edges) {
        int n=edges.length;
        par=new int[n+1]; //coz there's no 0 edge, instead there is a n-numbered edge
        for(int i=0; i<=n; i++) par[i]=i;
        
        for(int[] edge:edges){
            int p1=findPar(edge[0]);
            int p2=findPar(edge[1]);
            
            if(p1!=p2){
                par[p1]=p2;
            } else {
                return edge;
            }
        }
        return new int[]{};
    }
}
}

