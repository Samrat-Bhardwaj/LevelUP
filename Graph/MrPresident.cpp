#include<iostream>
#include<vector>
#include<algorithm>
using namespace std;

long long n,m,k;
vector<vector<long long>> edges;
vector<int> par;

int findPar(int u){
    if(par[u]==u) return u;

    return par[u] = findPar(par[u]);
}

long long unionFind(){
    for(int i=0; i<=n; i++) par.push_back(i);

    vector<vector<long long>> arr;

    long long cost=0;
    for(vector<long long> edge : edges){
        int p1=findPar(edge[0]);
        int p2=findPar(edge[1]);

        if(p1!=p2){
            par[p2]=p1;
            cost+=edge[2];
            arr.push_back(edge);
            n--;
        }
    }

    if(n>1) return -1; // meaning we were not able to connect N cities;

    int SuperRoadCount=0;
    for(int i=arr.size()-1; i>=0;i--){
        if(cost<=k) break;
        cost= cost - arr[i][2] + 1; // road tod diya aur 1 maintenance charge added ;
        SuperRoadCount++;
    }

    return cost <=k ? SuperRoadCount : -1; 
}

int main(){
    // fast input output to make the code pass
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);

    cin>>n>>m>>k;

    for(int i=0; i<m; i++){
        int a,b,c;
        cin>>a>>b>>c;
        edges.push_back({a,b,c});
    }

    sort(edges.begin(),edges.end(), [] (auto& a, auto& b){
        return a[2] < b[2];
    });
    cout<<unionFind();

    return 0;
}