import java.util.*;
public class Graph_1 {

    static class Edge{
        int v=0;
        int w=0;

        public Edge(int v, int w){
            this.v=v;
            this.w=w;
        }
    }

    static int N=7;
    static ArrayList<Edge>[] graph=new ArrayList[N];

    public static void addEdge(int u, int v, int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    public static int searchVtx(int u, int v){
        for(int i=0; i<graph[u].size(); i++){
            Edge e=graph[u].get(i);
            if(e.v==v) return i;
        }
        return -1;
    }

    public static void removeEdge(int u, int v){
        int l1=searchVtx(u, v);
        graph[u].remove(l1);

        int l2=searchVtx(v, u);
        graph[v].remove(u);
    }

    public static void removeVertex(int u){
        for(int i=0; i<graph[u].size(); i++){
            int v=graph[u].get(i).v;
            removeEdge(u, v);
        }
    }

    public static void display(){
        for(int i=0; i<N; i++){
            System.out.print(i+"->");
            for(Edge e: graph[i]){
                System.out.print("("+e.v+", "+e.w+"),");
            }
            System.out.println();
        }
    }

    // we want to go u->v
    public static boolean hasPath(int u, int v, boolean[] visited){
        if(u==v) return true;
        
        visited[u]=true;
        for(Edge e: graph[u]){
            int nbr=e.v;
            if(!visited[nbr])
                if(hasPath(nbr, v, visited)) return true;
        }

        return false;
    }

    // we want to go u->v
    public static int allPaths(int u, int v, boolean[] visited, String path, int weight){
        if(u==v){
            System.out.println(path+"@"+weight);
            return 1;
        }
        int count=0;
        visited[u]=true;

        for(Edge e:graph[u]){
            if(!visited[e.v]){
                count+=allPaths(e.v,v,visited,path+u+" ",weight+e.w);
            }
        }

        visited[u]=false;
        return count;
    }

    public static class pair{
        String path = "";
        int weight = 0;

        pair(String path,int weight){
            this.path = path;
            this.weight = weight;
        }
    }

    public static pair maxWeightPath(int src,int dest,boolean[] vis){
        if(src==dest){
            return new pair(src + "",0);
        }

        vis[src] = true;
        
        pair myAns = new pair("",0);
        for(Edge e : graph[src]){
            if(!vis[e.v]){
                pair recAns = maxWeightPath(e.v,dest,vis);
                if(recAns.weight + e.w > myAns.weight){
                    myAns.weight = recAns.weight + e.w;
                    myAns.path = src + " " + recAns.path;
                }
            }
        }

        vis[src] = false;
        return myAns;
    }

    public static int hamiltonianPath(int src,int des, boolean[] vis, int edgeCount,String psf){
        if(edgeCount== N-1){
            psf+=src+"";
            int idx=searchVtx(src, des);
            if(idx!=-1){
                System.out.println("Cyclic Path-> "+psf);
            } else {
                System.out.println("Hamiltonian Path-> "+psf);
            }
            return 1;
        }

        int ans=0;
        vis[src]=true;
        for(Edge e: graph[src]){
            if(!vis[e.v]){
                ans+=hamiltonianPath(e.v, des, vis, edgeCount+1,psf+src+" ");
            }
        }

        vis[src]=false;
        return ans;
    }

    public static void constructGraph(){
        for(int i=0; i<N; i++) graph[i]=new ArrayList<>();

        addEdge(0, 1, 10);
        addEdge(0, 3, 10);
        addEdge(1, 2, 10);
        addEdge(2, 3, 40);
        addEdge(2, 5, 3);
        addEdge(3, 4, 2);
        addEdge(4, 5, 2);
        addEdge(5, 6, 4);
        addEdge(6, 4, 8);

        display();
        System.out.println(hamiltonianPath(0,0,new boolean[N],0,""));
    }

    
    // bfs ==============================================================================

    public static void BFS_01(int src, boolean[] vis){
        LinkedList<Integer> que=new LinkedList<>();
        que.addLast(src);

        boolean isCycle=false;
        while(que.size()>0){
            int vtx=que.removeFirst();

            if(vis[vtx]==true){
                isCycle=true;
                continue; // because we dont want it to add its neighbours again
            }

            vis[vtx]=true;

            for(Edge e:graph[vtx]){
                if(!vis[e.v])
                    que.addLast(e.v);
            }
        }
    } 


    // bfs with level order withour for loop (dangling/null pointer) ===================================
    public static void BFS_02(int src, int des,boolean[] vis){
        LinkedList<Integer> que=new LinkedList<>();
        que.addLast(src);
        que.addLast(null); // represents end of level

        boolean isCycle=false;
        int level=0;
        while(que.size()>0){
            int vtx=que.removeFirst();

            if(vis[vtx]==true){
                isCycle=true;
                continue; // because we dont want it to add its neighbours again
            }

            vis[vtx]=true;

            if(vtx==des){
                System.out.println(level);
            }

            for(Edge e:graph[vtx]){
                if(!vis[e.v])
                    que.addLast(e.v);
            }

            if(que.getFirst()==null){
                level++;
                que.removeFirst();
                que.addLast(null);
            }
        }
    }

    public static void BFS_03_WithCycle(int src,boolean[] vis){
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);

        boolean cycle = false;
        int level = 0;
        
        int[] dis = new int[N];

        while(que.size() != 0){
            int size = que.size();
            while(size-->0){
                int vtx = que.removeFirst();
            
                if(vis[vtx]){
                    cycle = true;
                    continue;
                }
                
                dis[vtx] = level;

                vis[vtx] = true;
                for(Edge e: graph[vtx]){
                    if(!vis[e.v]){
                        que.addLast(e.v);
                    }
                }                
            }
            level++;
        }
    }

    public static void BFS_04_WithoutCycle(int src,boolean[] vis){
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);
        vis[src] = true;

        int level = 0;
        
        int[] dis = new int[N];

        while(que.size() != 0){
            int size = que.size();
            while(size-->0){
                int vtx = que.removeFirst();
                
                dis[vtx] = level;
                for(Edge e: graph[vtx]){
                    if(!vis[e.v]){
                        vis[e.v] = true;
                        que.addLast(e.v);
                    }
                }                
            }
            level++;
        }
    }

    public static void solve(){
        constructGraph();
    }

    public static void main(String[] args) {
        solve();
    }
}
