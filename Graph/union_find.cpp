#include<iostream>
#include<vector>

using namespace std;

class Edge
{
public:
    int v = 0;
    int w = 0;

    Edge(int v, int w)
    {
        this->v = v;
        this->w = w;
    }
};

void addEdge(vector<vector<Edge>> &graph, int u, int v, int w)
{
    graph[u].push_back(Edge(v, w));
    graph[v].push_back(Edge(u, w));
}

void display(vector<vector<Edge>> &graph, int N)
{

    for (int i = 0; i < N; i++)
    {
        cout << i << " -> ";
        for (Edge e : graph[i])
        {
            cout << "(" + to_string(e.v) + ", " + to_string(e.w) + ") ";
        }

        cout << endl;
    }
}

// union - find ==================================================================

vector<int> par; // parent array for data compression
vector<int> size;  // size array for size check

int findPar(int u){
    if(par[u]==u) return u;
    return par[u]= findPar(par[u]);  // vapis jate vaqt parent change krte jayenge sare vertex 
}

void merge(int p1, int p2){  // bade vale m chota dalenge coz nepal wiill go easy in india 
    if(size[p1]<size[p2]){
        par[p1]=p2;
        size[p2]+=size[p1];
    } else {
        par[p2]=p1;
        size[p1]+=size[p2];
    }
}

void unionFind(int n,vector<vector<int>>& edges){

    vector<vector<Edge>> ngraph(n,vector<Edge>());

    for(int i=0; i<n; i++){
        par.push_back(i); // mera leader/president/parent m hi hu abhi;
        size.push_back(1); // sabka size abhi 1 hi
    }

    for(vector<int> a:edges){  // a-> {u,v,w};

        int gp1=findPar(a[0]); //global parent 1
        int gp2=findPar(a[1]); // global parent 2 -> gp of v

        if(gp1!=gp2){
            merge(gp1,gp2);
            addEdge(ngraph,a[0],a[1],a[2]);   
        }
    }

    display(ngraph,n);
}



void solve(){

}

void main(){
    solve();
}