#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

// search in a rotated sorted array list jaha p rotation index we dont know
// leet 33
int search(vector<int>& nums, int target) {
        // searching the minimum value =====
        
        int n=nums.size();
        int lo=0;
        int hi=n-1;
        
        while(lo<hi){
            int m=(lo+hi)/2;
            if(nums[m]>nums[hi]) lo=m+1; // m vala bigger than hi vala to uske aage vala rot-
            else hi=m;                   // -ation point ho skta, aage while loop se confirm
                                        // kar lenge ye
        }
        
        int rot=lo; // the rotation point 
        lo=0;
        
        hi=n-1;
        while(lo<=hi){ //normal binary search 
            int mi=(lo+hi)/2;
            int realMi=(mi+rot)%n; // aage leke chale gye rotation point se 
            
            if(nums[realMi]==target) return realMi;
            else if(nums[realMi]<target) lo=mi+1;  // update actual vala mi krna 
            else hi=mi-1;
        }
        return -1;
    }

    // find first missing number n repeating number in an an array between 1-n ==================

    vector<int> findTwoElement(vector<int> arr, int n) {
        int r=0;
        int m=0;
        
        for(int i=0; i<n; i++){
            int j=abs(arr[i]);
            if(arr[j-1]<0) r=j;
            if(arr[j-1]>0)
                arr[j-1]*=-1;
        }
        
        for(int i=0; i<n; i++){
            if(arr[i]>0) m=i+1;
        }
        
        return {r,m};
        
    }