#include<iostream>
#include<vector>
#include<unordered_set>
#include<unordered_map>
#include<algorithm>
#include<utility>
using namespace std;

// leet 1423 ===============================================================================

// max sum of k elements from end of array ============================================

int maxScore(vector<int>& cardPoints, int k) {
        int ans=0;
        int cp=0;
        
        for(int i=0; i<k; i++){
            cp+=cardPoints[i]; // first k elements aage ki side se utha liye 
        }
        
        ans=max(ans,cp);
        
        int j=cardPoints.size()-1;
        for(int i=k-1; i>=0; i--){
            cp=cp - cardPoints[i] + cardPoints[j]; // ek hataya last se aur dusre end se dal liya 
            ans=max(ans,cp);
            j--;
        }
        
        return ans;
    }

// leet 1658 ========================================================================================

// minimum number of elemnts to select to make a given sum ============================

int minOperations(vector<int>& nums, int x) {
        int n=nums.size();
        
        int target=0;
        for(int e:nums)target+=e;
        
        target=target-x;  // we will find the longest subarray with sum=totalSum-x ;
                          // then the answer will be n-size of that subarray
        
        if(target==0) return n;
        unordered_map<int,int> m; // map to find longest subarray of sum==target
        
        m[0]=-1;
        int sum=0;
        int ans=(int)-1e8;
        for(int i=0; i<n; i++){
            sum+=nums[i];
            if(m.find(sum-target)!=m.end()){
                ans=max(ans,i-m[sum-target]);
            }
            m[sum]=i;
        }
        return ans==(int)-1e8 ? -1 : n-ans;
    }

//     