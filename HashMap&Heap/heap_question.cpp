#include <iostream>
#include<vector>
#include<utility>
#include<queue>
#include<stack>
#include<algorithm>
#include<unordered_set>
#include<unordered_map>
using namespace std;

// Given a binary tree, we need to check it has heap property or not, Binary tree need to fulfill 
// the following two conditions for being a heap –
//      1.It should be a complete tree (i.e. all levels except last should be full).
//      2.Every node’s value should be greater than or equal to its child node (considering max-heap).


// ques -2 ================================================================

// convert min heap to max heap =================

// solution =====
// just call downHeapify from last half elements 


// ques -3 ===============================================================

// convert bST to min heap ========
// here arr-> inorder traversal of bst (sorted array)=====

void BSTToMinHeap(Node *root, vector<int> arr, int *i) 
{ 
    if (root == NULL) 
        return; 
  
    // first copy data at index 'i' of 'arr' to 
    // the node 
    root->data = arr[++*i]; 
  
    // then recur on left subtree 
    BSTToMinHeap(root->left, arr, i); 
  
    // now recur on right subtree 
    BSTToMinHeap(root->right, arr, i); 
} 

// leet 215 =====================================================
// kth largest element ==========

int findKthLargest(vector<int>& nums, int k) {
        priority_queue<int, vector<int> , greater<int> > pq; // --> max heap 
                    // we write vector<int> here because pq is implemente using vector in cpp; 
        
        for(int ele: nums){
            pq.push(ele);
            
            if(pq.size()>k)
                pq.pop();
        }
        return pq.top();
    }

// leet 378 ============================================================

// kth smallest in matrix where rows n colums are sorted===

// we can use pq of pair as well;
int kthSmallest(vector<vector<int>>& matrix, int k) {
        int n=matrix.size();
        
        priority_queue<vector<int>, vector<vector<int>>, greater<vector<int>>> pq; // it'll compare on the basis of zeroth element
        
        for(int i=0; i<n; i++){
            pq.push({matrix[i][0],i,0});
        }
        
        while(k-->1){
            vector<int> p=pq.top();
            pq.pop();
            
            int r=p[1];
            int c=p[2];
            
            if(c+1 < n){
                pq.push({matrix[r][c+1],r,c+1});
            }
        }
        return pq.top()[0];
}

// how to make class for using in pq ====

// leet 973 =========================================================================
// k closest pair from origin ================

class Solution {
public:
    class closePair{
        public:
        int i=0;
        int j=0;
        
        closePair(int i, int j){
            this->i=i;
            this->j=j;
        }
    };
    
    class compare{
        public:
        bool operator()(closePair& a,closePair& b){
            int f=a.i*a.i + a.j*a.j;
            int s=b.i*b.i + b.j*b.j;
            
            return f < s;
        }
    };
    
    vector<vector<int>> kClosest(vector<vector<int>>& points, int K) {
        priority_queue<closePair,vector<closePair>,compare> pq;
        
        for(int i=0; i<points.size(); i++){
            pq.push(closePair(points[i][0],points[i][1]));
            
            if(pq.size()>K)
                pq.pop();
        }
        
        vector<vector<int>> ans;
        while(K--){
            closePair rp=pq.top();
            pq.pop();
            
            ans.push_back({rp.i,rp.j});
        }
        return ans;
    }
};



// leet 295 ==================================================\

// median in stream of data ===============

class MedianFinder {
public:
    /** initialize your data structure here. */
    priority_queue<int> pqg;
    priority_queue<int,vector<int>,greater<int>> pqs;
    MedianFinder() {
        
    }
    
    void addNum(int num) {
        if(pqg.empty() || num<=pqg.top()){
            pqg.push(num);
        } else {
            pqs.push(num);
        }
        if(pqg.size()>pqs.size()+1){
            int r=pqg.top();
            pqs.push(r);
            pqg.pop();
        } else if(pqg.size()<pqs.size()){
            int r=pqs.top();
            pqg.push(r);
            pqs.pop();
        }
    }
    
    double findMedian() {
        if(pqg.size()==pqs.size() && pqg.size()!=0) return (pqg.top()+pqs.top())*1.0/2;
        else return pqg.top();
    }
};


// leet 239 =====================================================

// approach is next greater on right nikala phir sliding window for loop use krke ==
vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        int n=nums.size();
        vector<int> ans(n-k+1);
        stack<int> st;
        
        vector<int> ngr(n,0);
        ngr[n-1]=n;
        st.push(-1);
        
        for(int i=n-1; i>=0; i--){
            while(st.top()!=-1 && nums[st.top()]<=nums[i]){
                st.pop();
            }
            if(st.top()==-1){
                ngr[i]=n;
            } else {
                ngr[i]=st.top();
            }
            st.push(i);
        }
        
        int j=0;
        for(int i=0; i<n-k+1; i++){
            if(i>j){
                j=i;
            }
            // we are basically getting our max in this window
            while((i+k) > ngr[j]){
                j=ngr[j];
            }
            ans[i]=nums[j];
        }
        
        return ans;
    }

// leet 692 ====================================================================

// making our class outside the class =============
unordered_map<string, int> m;
class compare {
    public:
        bool operator()(string a, string b){
            if(m[a]==m[b]){
                return a<b;
            }
            return m[b] < m[a];
    }
};
class Solution {
public:
    vector<string> topKFrequent(vector<string>& wor, int k) {
        vector<string> ans;
        m.clear();// static ban gya vo islie jruri ye
        
        for(string& s:wor) m[s]++;
        priority_queue<string,vector<string>,compare>pq;
        
        unordered_set<string> se;
        for(string w:wor){
            if(se.find(w)==se.end())
                pq.push(w);
            se.insert(w);
            if(pq.size()>k)
                pq.pop();
            
        }
        
        while(k-- > 0){
            string t=pq.top();
            pq.pop();
            
            ans.push_back(t);
        }
        
        sort(ans.begin(),ans.end());
        
        return ans;
    }
};

// leet 632 ======================================================================

// smallest range in k lists ==========

// approach-> normal sort k list but while traversing we'll update our ans 
class Solution {
public:
    class compare{
    public:
    bool operator()(vector<int>& a, vector<int>& b){
        return a[0] > b[0];
    }
};
    vector<int> smallestRange(vector<vector<int>>& nums) {
        int n=nums.size();
        
        priority_queue<vector<int>,vector<vector<int>>, compare> pq;
        
        int max_=(int)-1e8; // max element in pq at any time (abhi h ya pahle aa chuka)
        for(int i=0; i<n; i++){
            pq.push({nums[i][0],i,0});
            max_=max(max_,nums[i][0]);
        }
        
        int start=0,end=0;
        int range=(int)1e8;
        while(pq.size()==n){ //-> this is how we'll make sure we are gtting element from every list 
            vector<int> curr=pq.top();
            pq.pop();
            
            int val=curr[0];
            if(max_ -val < range){ // updating our max
                range=max_ - val;
                start=val;
                end=max_;
            }
            // cout<<val<<" "<<max_<<" "<<range<<" "<<"\n";
            int i=curr[1];
            int j=curr[2];
            if(j+1 < nums[i].size()){
                pq.push({nums[i][j+1],i,j+1});
                max_=max(max_,nums[i][j+1]);  // updating max when putting new element 
            }
        }
        return {start,end};
    }
};

int main(){
    return 0;
}