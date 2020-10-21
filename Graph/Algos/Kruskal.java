import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

public class Kruskal {

    public static class Edge{
        int v = 0;
        int w = 0;

        public Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }
    
    static int N = 0;
    
    @SuppressWarnings("unchecked")
    static ArrayList<Edge>[] graph;

    public static void addEdge(int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    public static void display(){
        for(int i=0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e: graph[i]){
                System.out.print("(" + e.v + ", " + e.w + "), ");
            }

            System.out.println();
        }
    }

    // kruskal ====================================================================
    // applied to get minimum spanning tree ====================

    //Kruskal -> nothing but union find with sorted array of edges(Sorting based on weight of edges);
    
    static int[] par;
    static int[] size;

    public static int  findPar(int u){
        if(par[u]==u) return u;

        return par[u]=findPar(par[u]);
    } 

    public static void merge(int p1, int p2){
        if(size[p1]<size[p2]){
            par[p1]=p2;
            size[p2]+=size[p1];
        } else {
            par[p2]=p1;
            size[p1]+=size[p2];
        }
    }

    public static void unionFind(int NoOfVtx, int[][] edges){
        par=new int[NoOfVtx];
        size=new int[NoOfVtx];

        for(int i=0; i<NoOfVtx; i++){
            par[i]=i;
            size[i]=1;
        }

        for(int i=0; i<edges.length; i++){
            int p1=findPar(edges[i][0]);
            int p2=findPar(edges[i][1]);

            if(p1!=p2){
                merge(p1,p2);
                addEdge(edges[i][0], edges[i][1], edges[i][2]);
            }
        }
    }

    public static void KruskalAlgo(int n,int[][] edges){
        graph=new ArrayList[N];

        for(int i=0; i<N; i++) graph[i]=new ArrayList<>();

        Arrays.sort(edges,(a,b)->{
            return a[2]-b[2]; // this - other -> default, increasing             
        });                   // in cpp '-' is replaced with '<'

        unionFind(n, edges);

        display();
    }

    public static void solve(){
        N = 9;
        int[][] edges = {{0,1,4},{0,7,8},{1,7,11},{1,2,8},{7,8,7},{7,6,1},{2,8,2},{8,6,6},{2,5,4},{2,3,7},{6,5,2},{3,4,9},{3,5,14},{5,4,10}};
        KruskalAlgo(9,edges);     
    }
    public static void main(String[] args) {
        solve();
    }
}
