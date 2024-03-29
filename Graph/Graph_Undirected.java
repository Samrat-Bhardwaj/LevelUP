import java.util.*;
import java.util.ArrayList;

public class Graph_Undirected {
    static int N=7;
    static ArrayList<Integer>[] graph=new ArrayList[N];

    public static void addEdge(int u, int v){
        graph[u].add(v);
    }

    public static void constructGraph(){
        for(int i=0; i<N; i++) graph[i]=new ArrayList<>();

        addEdge(0, 1);
        addEdge(0, 3);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(2, 5);
        addEdge(3, 4);
        addEdge(4, 5);
        addEdge(5, 6);
        addEdge(6, 4);

        display();
    }

    public static void display(){
        for(int i=0; i<N; i++){
            System.out.print(i+"->");
            for(Integer e: graph[i]){
                System.out.print(e+", ");
            }
            System.out.println();
        }
    }
// according to wiki->

// In computer science, a topological sort or topological ordering of a directed graph is a linear ordering 
// of its vertices such that for every directed edge uv from vertex u to vertex v, u comes before v in the 
// ordering. 

// which means when we will have our topological order elements coming first will have edge to elements
// coming last and vice versa is not possible (u will only come before v, always);

// there's no correct topological order if there is cycle in graph 

// topological sort using dfs ===========================================================
    public static void TopoDFS(int src,boolean[] vis, ArrayList<Integer> ans){
        vis[src]=true;

        for(int e:graph[src]){
            if(!vis[e]){
                TopoDFS(e,vis,ans);
            }
        }

        ans.add(src);
    }
    
    public static void TopoDFS(){
        boolean[] vis=new boolean[N];

        ArrayList<Integer> ans=new ArrayList<>();

        for(int i=0; i<N; i++){
            if(!vis[i]){
                TopoDFS(i,vis,ans);
            }
        }
    }

//topo order using bfs (Kahn's Algo) -> detect cycle using bfs 
    public static void TopoBFS(){
        ArrayList<Integer> ans=new ArrayList<>();
        
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
        
        if(ans.size()!=N){
            System.out.println("Cycle");  // detected cycle 
        } else {
            System.out.println(ans);
        }
    }


    // topological sort using dfs ==============================================
   
    // this function is checking cycle -> if true then cycle , else false
    public static boolean TopoDFS(int src, int[] vis, ArrayList<Integer> ans){
        vis[src]=1;
        for(int e:graph[src]){
            if(vis[e]==0){
                if(TopoDFS(e, vis, ans)) return true;    
            } else if(vis[e]==1) return true;
        }

        ans.add(src);
        vis[src]=2;
        return false;
    }

    // 0 -> not visited, 1 -> part of path, 2 -> visited and not a part of path.
    public static void Topodfs(){
        ArrayList<Integer> ans=new ArrayList<>();
        boolean isCycle=false;

        int[] vis=new int[N];
        for(int i=0; i<N; i++){
            if(vis[i]==0)
                isCycle=TopoDFS(i, vis, ans);
            if(isCycle) break;
        }

        if(isCycle) System.out.println("Cycle");
        else System.out.println(ans);
    } 

// leet 329 ==========================================================================

// dp/dfs ques in graph using kahn's algo ==================

    public int longestIncreasingPath(int[][] matrix) {
            
        int n=matrix.length;
        if(n==0) return 0;
        int m=matrix[0].length;
        int[][] indegree=new int[n][m];
        int[][] dirs={{0,1},{1,0},{0,-1},{-1,0}};
        
        //increasing every vertex which have a smaller nbr 
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                for(int[] dir:dirs){
                    int x=i+dir[0];
                    int y=j+dir[1];
                    
                    if(x>=0 && y>=0 && x<n && y<m && matrix[x][y]>matrix[i][j]){
                        indegree[x][y]++; 
                    }
                }
            }
        }
        
        // now only those points having 0 nbr with greater elements 

        LinkedList<Integer> que=new LinkedList<>();
        //making our queue
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(indegree[i][j]==0){
                    que.addLast(i*m + j);
                }
            }
        }
        
        int level=0;
        //applying kahn's algo 
        while(que.size()>0){
            int size=que.size();
            while(size-->0){
                int idx=que.removeFirst();
                
                int i=idx/m;
                int j=idx%m;
                
                for(int[] dir:dirs){
                    int x=i+dir[0];
                    int y=j+dir[1];
                    
                if(x>=0 && y>=0 && x<n && y<m && matrix[x][y]>matrix[i][j] &&--indegree[x][y]==0){
                        que.addLast(x*m +y);
                    }
                }
            }
            level++;
        }
        return level;
    }

// strongly connected components - those components which are a part of cycle i.e. if we can come back
// to that vertex by some path ====

// scc -KOSARAJU ALGO =====================

// vertex printed in one line is one scc
    public static void dfs_ssc(int src, boolean[]visit,ArrayList<Integer> ans){
        visit[src]=true;

        for(int nbr:graph[src]){
            if(!visit[nbr])
                dfs_ssc(nbr,visit,ans);
        }

        ans.add(src);
    }

    public static void dfs_ssc2(ArrayList<Integer>[] ngraph, int src, boolean[]visit){
        visit[src]=true;
        System.out.print(src+" ");
        for(int nbr:ngraph[src]){
            if(!visit[nbr])
                dfs_ssc2(ngraph,nbr,visit);
        }
    }

    public static int SCC(){
        ArrayList<Integer> topo=new ArrayList<>();

        boolean[] visit=new boolean[N];
        for(int i=0; i<N; i++){
            if(!visit[i]){
                dfs_ssc(i,visit,topo);
            }
        }

        // reversing the graph
        ArrayList<Integer>[] nGraph=new ArrayList[N];

        for(int i=0; i<N; i++){
            nGraph[i]=new ArrayList<>();
        }

        for(int i=0; i<N; i++){
            for(int nbr:graph[i]){
                nGraph[nbr].add(i);
            }
        }

        // getting comp
        int count=0;
        visit=new boolean[N];
        for(int i=N-1;i>=0; i--){
            if(!visit[topo.get(i)]){
                count++;
                dfs_ssc2(nGraph, topo.get(i), visit);
                System.out.println();
            }
        }

        return count;
    }

}


