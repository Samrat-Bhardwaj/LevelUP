#include <iostream>
#include<vector>
#include<utility>
using namespace std;
int mod=(int)1e9+7;

void display_1D(vector<int>& qb){
        for(int e:qb) cout<<e<<" ";
        cout<<"\n";
}

void display_2D(vector<vector<int>>& qb){
    for(vector<int> temp:qb){
        for(int e:temp) cout<<e<<" ";
        cout<<"\n";
    }
}

// string set ===========================================================================================

//quiestion related to lcs ===

// https://practice.geeksforgeeks.org/problems/longest-repeating-subsequence/0
int DP(string str, int n, vector<vector<int>>& dp) {

        int N = n, M = n;
        for (n = 1; n <= N; n++) {
            for (int m = 1; m <= M; m++) {
                if(n==0 || m==0) {
                    dp[n][m]= str[n]==str[m] ? 1 : 0;
                    continue;
                }
                
                int ans=0;
                if(str[n-1]==str[m-1] && n!=m){
                    ans=dp[n-1][m-1]+1;//rec(n-1,m-1,str,dp)+1;
                } else {
                    ans=max(dp[n-1][m],dp[n][m-1]);//max(rec(n-1,m,str,dp),rec(n,m-1,str,dp));
                }
                
                dp[n][m]=ans;
            }
        }
        return dp[N][M];
    }

int rec(int n, int m, string str, vector<vector<int>>& dp){
    if(n==0 || m==0) return str[n]==str[m] ? 1 : 0;
    
    if(dp[n][m]!=-1) return dp[n][m];
    
    int ans=0;
    if(str[n-1]==str[m-1] && n!=m){
        ans=rec(n-1,m-1,str,dp)+1;
    } else {
        ans=max(rec(n-1,m,str,dp),rec(n,m-1,str,dp));
    }
    
    return dp[n][m]=ans;
}

int solve(int n,string s1){
    vector<vector<int>> dp(n+1,vector<int>(n+1,0));
    // return rec(n-1,n,s1,dp);
    return DP(s1,n,dp);
}

// LCS of 3 strings ===========================================================

// https://practice.geeksforgeeks.org/problems/lcs-of-three-strings/0

 int LCSubseq_DP(string str1, int n, string str2, int m,string s3, int o, vector<vector<vector<int>>>& dp) {

        int N = n, M = m,O=o;
        for (n = 0; n <= N; n++) {
            for (m = 0; m <= M; m++) {
                for(o=0; o<=O; o++){
                    if (n == 0 || m == 0 || o==0) {
                        dp[n][m][o] = 0;
                        continue;
                    }
                    if (str1[n-1] == str2[m-1] && str1[n-1]==s3[o-1])
                        dp[n][m][o] = dp[n - 1][m - 1][o-1] + 1;
                    else
                        dp[n][m][o] = max(dp[n - 1][m][o], max(dp[n][m - 1][o],dp[n][m][o-1]));
                }
            }
        }
        return dp[N][M][O];
    }
int solve(int n, int m, int o,string s1, string s2,string s3){
    vector<vector<vector<int>>> dp(n+1,vector<vector<int>>(m+1,vector<int>(o+1,0)));
    return LCSubseq_DP(s1,n,s2,m,s3,o,dp);
}

// KADAne's Algo ===========================================================================================

// https://practice.geeksforgeeks.org/problems/kadanes-algorithm/0#

int Kadane(vector<int>& arr, int n){
    int msf=-(int)1e8; // initializing it with int_min 
    int meh=0;
    
    for(int i=0; i<n; i++){
        meh+=arr[i];
        if(msf<meh){
            msf=meh;
        }
        if(meh<0){
            meh=0;
        }
    }
    
    return msf;
}

// https://practice.geeksforgeeks.org/problems/maximum-difference-of-zeros-and-ones-in-binary-string4111/1

	int maxSubstring(string S)
	{
	    int n=S.size();
	    vector<int> arr(n,0);
	    for(int i=0; i<n; i++){
	        arr[i] = S[i]=='0' ? 1 : -1;
	    }
	    
	    // KADANE'S ALGO
	    
	    int msf=-1; // max-so-far
	    int meh=0; // max-ending-here;
	    
	    
	    for(int i=0; i<n; i++){ // if meh is less than 0 -> meh=0 else update msf
	        meh+=arr[i];
	        if(meh<0){
	            meh=0;
	        } else {
	            msf=max(msf,meh);
	        }
	    }
	    
	    return msf;
	}


// leet 376 ============================================================================
// https://practice.geeksforgeeks.org/problems/longest-alternating-subsequence/0 

// longest alteranting subsequence -> x1 < x2 > x3 < x4 > x5 ..... or x1 > x2 < x3 > x4 .............


// dp[i][0] = Length of the longest alternating subsequence 
//          ending at index i and last element is greater
//          than its previous element
// dp[i][1] = Length of the longest alternating subsequence 
//           ending at index i and last element is smaller
//           than its previous element


int solve(vector<int>& arr, int n){
    if(n==0) return 0;
    vector<vector<int>> dp(n,vector<int>(2,1));
    
    int ans=1; // minimum one 
    for(int i=1; i<n; i++){
        for(int j=0; j<i; j++){
            
            // agr arr[i] chota aya 
            if(arr[i]<arr[j] && dp[i][1] < dp[j][0]+1){
                dp[i][1] = dp[j][0] + 1;
            } else if(arr[j] < arr[i] && dp[i][0] < dp[j][1]+ 1){ // agr bada aya to
                dp[i][0]=dp[j][1]+1;
            }
            
            if(ans < max(dp[i][1],dp[i][0])){
                ans=max(dp[i][0],dp[i][1]);
            }
        }
    }
    return ans;
}


// leet 474 ===============================================================

vector<int> calc(string str){
        vector<int> ans(2,0);
        
        for(char c:str){
            if(c=='0') ans[0]++;
            else ans[1]++;
        }
        return ans;
    }
    int findMaxForm(vector<string>& strs, int m, int n) {
        int l=strs.size();
        vector<vector<vector<int>>> dp(l+1,vector<vector<int>>(m+1,vector<int>(n+1,0)));
        //dp[i][j][k]= number of i strings with j number of 0's and k number of 1's;
        
        for(int i=1; i<=l;i++){
            vector<int> nums=calc(strs[i-1]); //number of 0 n 1 in strs[i-1];
            
            for(int j=0; j<=m; j++){
                for(int k=0; k<=n; k++){
                    if(j>=nums[0] && k>= nums[1]){ // agr hum limit se bahar nhi jare to 
                                                    // le skte h
                        dp[i][j][k]=max(dp[i-1][j][k],dp[i-1][j-nums[0]][k-nums[1]]+1);
                    } else { // nhi lenge
                        dp[i][j][k]=dp[i-1][j][k];
                    }
                }
            }
        }
        return dp[l][m][n];

        // we can save space by taking 2D dp ;
        // for(string str:strs){
        //     same inner loops as above
        // }
    }
void solve(){

}

int main(){
    solve();
    return 0;
}