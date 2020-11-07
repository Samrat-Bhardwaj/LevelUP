#include <iostream>
#include<vector>
#include<utility>
#include<queue>
#include<stack>
#include<algorithm>
#include<unordered_set>
#include<unordered_map>
using namespace std;

using namespace std;


//leet 128 =====================================================================================

// longest AP with common differenve 1 in the given vector=====

int longestConsecutive(vector<int>& nums) {
        unordered_set<int> se;
        int ans=0;
        for(int e: nums) se.insert(e);
        
        for(int e:nums){
            if(se.find(e)==se.end()) continue;
            int k=e;
            while(se.find(k)!=se.end()){
                if(k!=e)
                    se.erase(k); // erasing to reduce time complexity ===
                k--;
            }
            k++;
            int back=e-k;
            
            k=e;
            while(se.find(k)!=se.end()){
                se.erase(k);
                k++;
            }
            k--;
            int forw=k-e;
            ans=max(ans,forw+back+1);
        }
        return ans;
    }

// leet 49-> group anagrams(same chars, diff orientation) =======================

// leet 560 =============================

// number of subarrays having sum==k;
int subarraySum(vector<int>& nums, int k) {
        unordered_map<int,int> m;
        int sum=0;
        int ans=0;
        m[0]=1;
        for(int i=0; i<nums.size(); i++){
            sum+=nums[i];
            
            int c=sum-k; // if k exist somewhere, vo add hua hoga sum me, to vhi check krre
            if(m.find(c)!=m.end()){
                ans+=m[c];
            }
            m[sum]++;
        }
        return ans;
}

// leet 974 ==================================================================

// subarray sum divisible by k ==

int subarraysDivByK(vector<int>& A, int k) {
        int ans=0;
        int sum=0;
        
        unordered_map<int,int> m;
        m[0]=1;
        for(int i=0; i<A.size(); i++){
            sum=(sum+A[i])%k; // agr k se divisible koi sum aya hoga to add hua hoga sum me aur yhi same
                              // remainder aya hoga pahle ................
            if(sum<0) sum+=k; // positive mod chahiye
            
            ans+=m[sum];
            m[sum]++;
        }
        return ans;
    }

    
int main(){
    return 0;
}