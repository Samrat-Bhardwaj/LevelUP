#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

// Kadane's Algo ==============================================================================

// for finding maximum sum of contiguous subarray ===========

int Kadane(vector<int>& arr, int n){
    int msf=arr[0]; // -> msf= max so far
    int meh=0;      // -> meh= max ending here
    
    for(int i=0; i<n; i++){
        meh+=arr[i];
        if(meh<=0){
            meh=0;
        } else {
            msf=max(msf,meh);
        }
    }
    
    return msf;
}

// leet 287 =================================================

// find dup when numbers between 1-n =======
int findDuplicate(int nums[],int n) {
        for(int i=0; i<n; i++){
            int t=abs(nums[i])-1;
            if(nums[t]<0) return t+1;
            nums[t]*=-1;
        }
        return -1;
}

// count inversions of array  ==================================================================

// https://practice.geeksforgeeks.org/problems/inversion-of-array-1587115620/1

// merge sort lekin gin lenge kitne humko aage piche krne element ---============


#define ll long long int
ll merge(ll arr[],ll temp[],int l, int m, int r){ // merge two sorted array 
    ll inv=0;
    
    int i=l;
    int j=m;
    int k=l;
    
    while((i<=m-1) &&(j<=r)){
        if(arr[i]<=arr[j]){
            temp[k++]=arr[i++];
        } else {
            temp[k++]=arr[j++];
            
            inv+=(m-i);   // ab agr change krna h to m-i aage jayega vo -> isliye count+=(m-i);
        }
    }
    
    while(i<=m-1){ // copying baki ka elements 
        temp[k++]=arr[i++];
    }
    
    while(j<=r){
        temp[k++]=arr[j++];
    }
    
    for(i=l; i<=r; i++){ // copying back elements
        arr[i]=temp[i];
    }
    
    return inv;
}
ll mergeSort(ll arr[],ll temp[], int left, int right){
    ll invert_Count=0;
    
    if(left < right){
        int mid = (right+left)/2;
        
        invert_Count+=mergeSort(arr,temp,left,mid);
        invert_Count+=mergeSort(arr,temp,mid+1,right);
        
        invert_Count+=merge(arr,temp,left,mid+1,right);
    }
    
    return invert_Count;
}
long long int inversionCount(long long arr[], long long n)
{
    ll temp[n]; // helper array for merge sort
    return mergeSort(arr,temp,0,n-1);
}

// how to find next permutation of a given permutation ======================================

// leet 31===============

/* We need to find the first pair of two successive numbers a[i]a[i] and a[i-1]a[i−1], from the right, 
which satisfy a[i] > a[i-1]a[i]>a[i−1]. Now, no rearrangements to the right of a[i-1]a[i−1] can create a 
larger permutation since that subarray consists of numbers in descending order. Thus, we need to rearrange 
the numbers to the right of a[i-1]a[i−1] including itself.

Now, what kind of rearrangement will produce the next larger number? We want to create the permutation 
just larger than the current one. Therefore, we need to replace the number a[i-1]a[i−1] with the number 
which is just larger than itself among the numbers lying to its right section, say a[j]a[j].*/

// pahle last se pahla decreasing number nikalo, phir uske bad usse just bada number last se, swap kro dono ko
    // and then reverse i k age ka array 
    
void reverse(vector<int>& nums, int i){
        int j=nums.size()-1;
        
        while(i<j){
            int temp=nums[i];
            nums[i]=nums[j];
            nums[j]=temp;
            i++;
            j--;
        }
    }

    
    void nextPermutation(vector<int>& nums) {
        int n=nums.size();
        int i=n-2;
        
        while(i>=0 && nums[i+1]<= nums[i]){ // decreasing element 
            i--;
        }
        
        if(i>=0){
            int j=n-1;
            while(j>=0 && nums[j] <= nums[i]){ // nums[i] se bada element 
                j--;
            }
            int temp=nums[i]; // swap 
            nums[i]=nums[j];
            nums[j]=temp;
        }
        
        reverse(nums,i+1); // reverse 
    }

// common elements in 3 sorted arrays========================================

 vector <int> commonElements (int A[], int B[], int C[], int n1, int n2, int n3){
            vector<int> ans;
            int i=0;
            int j=0;
            int k=0;
            
            while(i<n1 && j<n2 && k<n3){
                while(j<n2 && B[j]<A[i]) j++;
                while(k<n3 && C[k]<A[i]) k++;
                
                if(A[i]==B[j] && B[j]==C[k]){
                    ans.push_back(A[i]);
                } 

                int temp=A[i];
                while(A[i]==temp) i++; // taki ek hi element bar bar na aa jaye ans m

            }
            
            return ans;
    }

// best time to buy n sell stocks when only 2 transactions r allowed ===============================

int maxProfit(vector<int>& prices) {
        int n=prices.size();
        
        vector<int> aage(n,0);  // i tak ka max transaction
        vector<int> piche(n,0);  // i k aage ka max transaction  
        
        int minsf=prices[0]; // min so far
        for(int i=1; i<n; i++){
            minsf=min(minsf,prices[i]);
            aage[i]=max(aage[i-1],prices[i]-minsf);
        }
        
        int masf=prices[n-1];
        for(int i=n-2; i>=0; i--){  // max so far 
            masf=max(masf,prices[i]);
            piche[i]=max(piche[i+1],masf-prices[i]);
        }
        
        int ans=0;
        
        for(int i=0; i<n; i++){ 
            ans=max(ans,aage[i]+piche[i]);
        }
        return ans;
}


// triplet sum exist 

bool solve(vector<int>&arr, int n, int x){
    sort(arr.begin(), arr.end()); 
    
    for(int i=0; i<n-2; i++){
        int e=arr[i];
        
        int l=i+1;
        int r=n-1;
        
        while(l<r){
            int csum=e+arr[l]+arr[r];
            if(csum==x){
                return true;
            } 
            else if(csum<x){
                l++;
            } 
            else {
                r--;
            }
        }
    }
    
    return false;
}
void solve(){

}

int main(){
    solve();
    return 0;
}