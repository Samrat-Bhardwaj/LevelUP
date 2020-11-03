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


void solve(){

}

int main(){
    solve();
    return 0;
}