import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class dijkstra_prims {
    public static class Edge{
        int v = 0;
        int w = 0;

        public Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }
    
    static int N = 0;
    
    // @SuppressWarnings("unchecked")
    // static ArrayList<Edge>[] graph;

    public static void addEdge(ArrayList<Edge>[] graph,int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    public static void display(int N,ArrayList<Edge>[] graph){
        for(int i=0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e: graph[i]){
                System.out.print("(" + e.v + ", " + e.w + "), ");
            }

            System.out.println();
        }
    }

// prims algo =========================================================================

//-> used to make mst using pq
    class primsPair{
        int par;
        int vtx;
        int w;

        public primsPair(int vtx,int par, int w){
            this.vtx=vtx;
            this.par=par;
            this.w=w;
        }
    }


    public static void prims(int src,int n, ArrayList<Edge>[] graph){
        ArrayList<Edge>[] ngraph=new ArrayList<>[n];

        for(int i=0; i<n; i++) ngraph[i]=new ArrayList<>();

        PriorityQueue<primsPair> pq=new PriorityQueue<>((a,b)->{
            return a.w - b.w;
        });

        boolean[] vis=new boolean[n];
        int[] dis=new int[n];
        Arrays.fill(dis,Integer.MAX_VALUE);

        pq.add(new primsPair(src,-1,0));
        dis[src]=0;
        int edgeCount=0;
        while(edgeCount<= n-1){
            primsPair pair=pq.remove();

            if(vis[pair.vtx]){ // cycle check
                continue;
            }

            vis[pair.vtx]=true;

            if(pair.par!=-1){
                edgeCount++;
                addEdge(ngraph,pair.vtx,pair.par,pair.w);
            }

            for(Edge e:graph[pair.vtx]){
                if(!vis[e.v] && dis[e.v] > e.w){
                    dis[e.v] = e.w;
                    pq.add(new primsPair(e.v,pair.vtx,e.w));
                }
            }
        }
        display(N, ngraph);
    }    

    // dijkstra also ======================================================================

    //-> used ot tell the shortest distance from 0 to every vtx;

    class Dijkstrapair{
        int vtx;
        int par;
        int wsf;

        public Dijkstrapair(int vtx, int par, int wsf){
            this.vtx=vtx;
            this.par=par;
            this.wsf=wsf;
        }
    }

    public static void dijkstra(int src,int N, ArrayList<Edge>[] graph){
        boolean[] vis=new boolean[N]; // iske bina bhi kam chla jayega but it speeds the code
        int[] dis=new int[N];
        int[] par=new int[N]; // in case we want to find the minimum path, we'll use parent array

        Arrays.fill(dis,(int)1e8);
        Arrays.fill(par,-1);

        PriorityQueue<Dijkstrapair> pq=new PriorityQueue<>((a,b)->{
            return a.wsf-b.wsf;
        });

        pq.add(new Dijkstrapair(src, -1, 0));
        dis[src]=0;

        int edgeCount=0;
        while(edgeCount<=N-1){
            Dijkstrapair pair=pq.remove();

            if(vis[pair.vtx]) continue;

            vis[pair.vtx]=true;
            par[pair.vtx] = pair.par;

            if(pair.vtx!=-1) edgeCount++;

            for(Edge e:graph[pair.vtx]){
                if(!vis[e.v] && dis[e.v]> e.w + pair.wsf){
                    dis[e.v]=e.w + pair.wsf;
                    pq.add(new Dijkstrapair(e.v, pair.vtx, e.w + pair.wsf));
                }
            }
        }
    }


// bellman ford ======================================================
// works for same purpose as dijkstra but will work even if we have a negative edge + we get negative 
// cycle as well 

public static void BellmanFord_01(int N, int[][] edges,int src){
    int dis[]=new int[N];

    Arrays.fill(dis,(int)1e8);

    dis[src]=0;

    boolean negtiveCycle=false;

    for(int i=1; i<=N; i++){
        boolean isAnyUpdate=false;

        int[] ndis=new int[N];

        for(int j=0; j<N; j++) ndis[j]=dis[j];

        for(int[] e: edges){
            if(dis[e[0]]!=1e8 && dis[e[0]]+e[2]< ndis[e[1]){
                ndis[e[1]]=dis[e[0]] + e[2];
                isAnyUpdate=true;
            }
        }
        if(isAnyUpdate && i==N) negtiveCycle=true;

        if(!isAnyUpdate) break;
        dis=ndis;
    }
}

public static void BellmanFord_02(int N, int[][] edges,int src){
    int dis[]=new int[N];

    Arrays.fill(dis,(int)1e8);

    dis[src]=0;

    boolean negtiveCycle=false;

    for(int i=1; i<=N; i++){
        boolean isAnyUpdate=false;

        for(int[] e: edges){
            if(dis[e[0]]!=1e8 && dis[e[0]]+e[2]< dis[e[1]){
                dis[e[1]]=dis[e[0]] + e[2];
                isAnyUpdate=true;
            }
        }
        if(isAnyUpdate && i==N) negtiveCycle=true;

        if(!isAnyUpdate) break;
    }
}
}