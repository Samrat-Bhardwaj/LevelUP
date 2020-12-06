#include<queue>
#include<vector>
#include<bits/stdc++.h>

using namespace std;

class Edge{
public:
    int v;
    int w;

    Edge(int v, int w){
        this->v=v;
        this->w=w;
    }
};

int N=7;
vector<vector<Edge>> graph(N,vector<Edge>());

class diPair{
public:
    int vtx;
    int par;
    int wsf;

    diPair(int vtx, int par, int wsf){
        this->vtx=vtx;
        this->par=par;
        this->wsf=wsf;
    }    
};

void addEdge(int u, int v, int wt){
    graph[u].push_back(Edge(v,wt));
}

void makeGraph(vector<vector<int>>& edges){

    for(int i=0; i<edges.size(); i++){
        addEdge(edges[i][0],edges[i][1],edges[i][2]);
    }

}

// dijkstra ======================================================================================
class Compare{
    public:
        bool operator()(diPair a, diPair b){
            return a.wsf < b.wsf;
        }
};

void dijkstra(int src){
    vector<bool> vis(N,false);
    vector<int> dis(N,(int)1e8);
    vector<int> par(N,-1); // to find the shortest path (par p jate rho bar bar);

    priority_queue<diPair, vector<diPair>, Compare> pq;

    pq.push(diPair(src,-1,0));
    dis[src]=0;

    int edgeCount=0; // pq empty vala condition bhi chal skta h lekin it'll be faster

    while(edgeCount<=N-1){
        diPair pair=pq.top();
        pq.pop();

        if(vis[pair.vtx]) continue;

        vis[pair.vtx]=true; // updating vis
        par[pair.vtx]=pair.par; // updating par

        if(pair.vtx!=-1) edgeCount++;

        for(Edge e:graph[pair.vtx]){
            if(!vis[e.v] && e.w+pair.wsf < dis[e.v]){
                dis[e.v]=e.w + pair.wsf;
                pq.push(diPair(e.v,pair.vtx,e.w+pair.wsf)); // parent=pair.vtx;
            }
        }
    }
}


// prims algo =====================================================================================


void solve(){
   vector<vector<int>> edges={{}};
   makeGraph(edges);

   int src=0;
   dijkstra(src); 
}

int main(){
    solve();
    return 0;
}