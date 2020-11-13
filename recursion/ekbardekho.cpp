#include <iostream>
#include<vector>
#include<unordered_set>
#include<algorithm>
#include<utility>
using namespace std;

//basically 2 types k recursive functions hote h, return type or void type -> 

// RETURN TYPE -> isme apan jo ans chahiye usko hi return kra dete, subsets etc types question isme easy pdta 

// VOID TYPE -> isme hum apna answer banate chalte h arguments m dalke answer, vo bharta rahta h as function moves

// ek global variables leke bhi hota h -> not considered good ===================


//questions to see one time ==================================================================================


// all subsets of nums array ====================================================
//using return type recursion 
vector<vector<int>> some(vector<int>& nums, int i){
        if(i==nums.size()){
            vector<int> bv;
            return {bv};
        }
        
        vector<vector<int>> sa=some(nums,i+1);

        vector<vector<int>> ans; 
        
        for(vector<int>& a:sa){ // ek bar nums[i] dala, ek bar nhi dala 
            ans.push_back(a);
           vector<int> temp=a;
            temp.push_back(nums[i]);
            // for(int e:temp) cout<<e<<" ";
            ans.push_back(temp);
        }
        return ans;
    }
    vector<vector<int>> subsets(vector<int>& nums) {
        return some(nums,0);
    }

// combination sum-using one coin only one time =====================================================

// isme duplicates banayenge duplicate coin, to unko skip mardo, no dupe ========

// void type recursion
void some(vector<int>& can,int tar,vector<int>& temp,vector<vector<int>>& ans, int idx){
        if(tar==0){
            ans.push_back(temp);
            return;
        }
        
        if(tar<0 || idx>=can.size()) return;
        
        for(int j=idx; j<can.size(); j++){
            if(j>idx && can[j]==can[j-1]) // contniue agr same h to 
                    continue;
                temp.push_back(can[j]);
                some(can,tar-can[j],temp,ans,j+1);
                temp.pop_back();
        }
        
    }
    vector<vector<int>> combinationSum2(vector<int>& can, int tar) {
        sort(can.begin(),can.end());
        vector<vector<int>> ans;
        vector<int> temp;
        some(can,tar,temp,ans,0);
        return ans;
    }

// unique permutations ==============================================================================

vector<vector<int>> ans;
vector<int> temp;

vector<vector<int>> permuteUnique(vector<int>& nums) {
        sort(nums.begin(),nums.end());
        vector<int> visit(nums.size(),0);
        some(nums,visit,0);
        return ans;
}

void some(vector<int>& nums,vector<int>& visit,int i){
        if(i==nums.size()){
            ans.push_back(temp);
        }
        int prev=-(int)1e8;
        for(int j=0; j<nums.size(); j++){
            if(!visit[j] && nums[j]!=prev){
                visit[j]=1;
                temp.push_back(nums[j]);
                some(nums,visit,i+1);
                temp.pop_back();
                visit[j]=0;
                prev=nums[j];
            }
        }
}

// generate parentehsis (Catalan number)==============================================================

 void some(vector<string>& ans, string& asf, int n, int o, int c){
        if(asf.size()==2*n){
            ans.push_back(asf);
            return;
        }
        
        if(o<n){ // agr opening bracket kma h
            asf+="(";
            some(ans,asf,n,o+1,c);
            if(asf.size()>1) asf=asf.substr(0,asf.size()-1);
            else asf="";
        }
        if(c<o){
            asf+=")";
            some(ans,asf,n,o,c+1);
            if(asf.size()>1) asf=asf.substr(0,asf.size()-1);
            else asf="";
        }
    }
vector<string> generateParenthesis(int n) {
        vector<string> ans;
        string temp="";
        some(ans,temp,n,0,0);
        return ans;
}

// pallindrome partitioning ============================================================

void some(int si, int ei,vector<vector<string>>& ans,vector<string>& temp, vector<vector<bool>>& pal,string& s){
    
        if(si==ei+1){
            ans.push_back(temp);
            return;
        }       
        
        for(int cut=si; cut<=ei; cut++){
            if(pal[si][cut]){
                // cout<<cut;
                temp.push_back(s.substr(si,cut+1-si));
                some(cut+1,ei,ans,temp,pal,s);
                temp.pop_back();
            }
        }
    }
    
    vector<vector<string>> partition(string s) {
        int n=s.size();
        vector<vector<bool>> pal(n,vector<bool>(n,false));
        
        for(int gap=0; gap<n; gap++){
            for(int si=0,ei=gap; ei<n; si++,ei++){
                if(gap==0){
                    pal[si][ei]=true;
                } else if(gap==1 && s[si]==s[ei]){
                       pal[si][ei]=true;
                } else if(s[si]==s[ei]){
                    pal[si][ei]=pal[si+1][ei-1];
                }
            }
        }
        
        vector<vector<string>> ans;
        vector<string> temp;
        
        some(0,n-1,ans,temp,pal,s);
        return ans;
    }

// partition into k subsets =====================================================================================================

// leet 698 ========================================================================

// tarika -> 1 === more like permutations (no jrurat of idx(just to count elements)), will give Tle============
bool rec(vector<int>& nums,vector<bool>& vis,int sum,int csum,int k,int idx){
    if(csum==sum){
        k--;
        csum=0;
    }
    if(idx==nums.size() && k==0){
        return true;
    }
    
    for(int j=0; j<nums.size(); j++){
        if(!vis[j] && csum+nums[j]<=sum){
            vis[j]=true;
            bool f=rec(nums,vis,sum,csum+nums[j],k,idx+1);
            if(f) return f;
            vis[j]=false;
        }
    }
    return false;
}

// tarika - 2 -> more like combinations ===========================================================

 //sum-> target; csum -> current sum; k-> number of subsets
    bool rec(vector<int>& nums,vector<bool>& vis,int sum,int csum,int k,int idx){
    if(k==1) return true;
    
    if(csum==sum){
        return rec(nums,vis,sum,0,k-1,0);
    }
    
    for(int j=idx; j<nums.size(); j++){
        if(!vis[j] && csum+nums[j]<=sum){
            vis[j]=true;
            bool f=rec(nums,vis,sum,csum+nums[j],k,j+1);
            if(f) return f;
            vis[j]=false;
        }
    }
        
    return false;
}
    bool canPartitionKSubsets(vector<int>& nums, int K) {
        int N=nums.size();
        int sum=0;
    for(int e:nums) sum+=e;
     
    if(K>=N || K<=0 || sum%K!=0) return false;
    sum=sum/K;
    vector<bool> vis(N,0);
    return rec(nums,vis,sum,0,K,0);
    }

// word break ==============================================================================================

unordered_set<string> se; // set containing words 

void rec(string str,vector<vector<string>>& ans,vector<string>& temp,int i,int j){
    if(j==str.size()){
        if(se.find(str.substr(i,j-i))!=se.end()){
            temp.push_back(str.substr(i,j-i));
            ans.push_back(temp);
            temp.pop_back();
        }
        return;
    }
    
    if(se.find(str.substr(i,j-i))!=se.end()){
        temp.push_back(str.substr(i,j-i));
        rec(str,ans,temp,j,j+1);
        temp.pop_back();
    }
    
    rec(str,ans,temp,i,j+1);
}