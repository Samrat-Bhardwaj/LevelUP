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

void addEdge(vector<vector<Edge>>& graph,int u, int v, int wt){
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
class primsPair{
  public :  
    int par;
    int vtx;
    int w;

    primsPair(int par, int vtx, int w){
        this->par=par;
        this->vtx=vtx;
        this->w=w;
    }
};

class Compare{
    public:
        bool operator()(primsPair a, primsPair b){
            return a.w < b.w;
        }
};

void primsAlgo(int src){
    vector<vector<Edge>> ngraph(N,vector<Edge>());

    queue<primsPair> que;
    int edgeCount=0;
    vector<int> dis(N,(int)1e8);
    dis[src]=0;
    vector<bool> vis(N,false);

    priority_queue<primsPair, vector<primsPair>, Compare> pq;

    pq.push(primsPair{src,-1,0});
    while(edgeCount<=N-1){
        primsPair p=pq.top();pq.pop();

        if(vis[p.vtx]) continue;

        vis[p.vtx]=true;

        if(p.par!=-1){
            edgeCount++;
            addEdge(ngraph,p.vtx,p.par,p.w);
        }

        for(Edge e:graph[p.vtx]){
            if(!vis[e.v] && dis[e.v] >  e.w){
                dis[e.v]=e.w;
                pq.push(primsPair{e.v,p.vtx,e.w});
            }
        }
    }
    display(ngraph);
}

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