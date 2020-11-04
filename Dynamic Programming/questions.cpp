#include <iostream>
#include<vector>
#include<bits/stdc++>
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

// friends pairing ========================================================================
int cfp(int n,vector<int>& qb){
    if(n<=1) return qb[n]=1;

    if(qb[n]!=0) return qb[n];

    int single=cfp(n-1,qb) % mod;           // if person wanna stay single (coz uska man)
    int pairUp=(cfp(n-2,qb) * (n-1)) % mod;  // *(n-1) because if one person wanna pair up, he can do it 
                                            // with (n-1) person
    return qb[n]=(single + pairUp)%mod;                                        
}

int tab_cfp(int n, vector<int>& qb){
    int N=n;
    for(n=0; n<=N; n++){
            if(n<=1){ 
                qb[n]=1;
                continue;
            }

        int single=qb[n-1];//cfp(n-1,qb) % mod;          
        int pairUp=qb[n-2]*(n-1);//(cfp(n-2,qb) * (n-1)) % mod;

        qb[n]=(single + pairUp)%mod;
    }
    return qb[N];
}

int countFriendsPairings(int n) { 
    vector<int> qb(n+1,0);
    int ans=0;
    //ans=cfp(n,qb);
    // lets see the pattern of memoization dp

    //display_1D(qb);

    ans=tab_cfp(n,qb);
    return ans;
}

// https://www.geeksforgeeks.org/count-number-of-ways-to-partition-a-set-into-k-subsets/ ===============
int kSubsets_rec(int n, int k, vector<vector<int>>& qb){
    if(n==k || k==1) return qb[k][n]=1;

    if(qb[k][n]!=-1) return qb[k][n];

    int single=kSubsets_rec(n-1,k-1,qb);//n-1 ke k-1 subsets lado, ek size ka m bana dunga
    int partofGroup=kSubsets_rec(n-1,k,qb)*k;//n-1 ke k subsets lado, m sabme jake part ban skta ab

    return  qb[k][n]=single+partofGroup;
}

int kSubsets_tab(int n, int k, vector<vector<int>>& qb){
    int N=n;
    cout<<N;
    int K=k;
        for(k=1; k<=K; k++){
            for(n=k; n<=N; n++){
                if(n==k || k==1){ 
                     qb[k][n]=1;
                     continue;
                }

                int single=qb[k-1][n-1];
                int partofGroup=qb[k][n-1] * k;

                qb[k][n]=single+partofGroup;
        }
    }
    return qb[K][N];
}

int kSubsets(int n, int k){
    vector<vector<int>> qb(k+1,vector<int>(n+1,-1));
    int ans=0;
   // ans=kSubsets_rec(n,k,qb);   

    ans=kSubsets_tab(n,k,qb);
     display_2D(qb);
    return ans;
}
//string set ====================================================================================

//longest-pallindromic-substring=================================================================

pair<string,int> longestPallindromicSubsequence_rec(string str, int i, int j,vector<vector<pair<string,int>>>& qb){
    if(i==j) return qb[i][j]={""+str[i],1};
    if(i>j) return qb[i][j]={"",0};

    if(qb[i][j].second!=0) return qb[i][j];

    pair<string,int> maxp={"",0};
    if(str[i]==str[j]){
        pair<string,int> temp=longestPallindromicSubsequence_rec(str,i+1,j-1,qb);
        temp.second+=2; 
            maxp.first=str[i] + temp.first + str[j];
            maxp.second=temp.second; 
    } else {
        pair<string,int> temp=longestPallindromicSubsequence_rec(str,i,j-1,qb);
        pair<string,int> temp1=longestPallindromicSubsequence_rec(str,i+1,j,qb);

        if(temp.second<temp1.second){
            temp=temp1;
        }
        if(maxp.second<temp.second){
            maxp.second=temp.second;
            maxp.first=temp.first;
        }
    }
    return qb[i][j]=maxp;
}

pair<string,int> longestPallindromicSubsequence_tab(string str, int i, int j,vector<vector<pair<string,int>>>& qb){
    for(int gap=0; gap<str.size(); gap++){
        for(i=0,j=gap; j<str.size(); i++,j++){
            if(i==j) {
                qb[i][j]={""+str[i],1};
                continue;
            }

            pair<string,int> maxp={"",0};
            if(str[i]==str[j]){
                pair<string,int> temp=qb[i+1][j-1];//longestPallindromicSubsequence_rec(str,i+1,j-1,qb); 
                    maxp.first=str[i] + temp.first + str[j];
                    maxp.second=temp.second+2; 
            } else {
                pair<string,int> temp=qb[i][j-1];//longestPallindromicSubsequence_rec(str,i,j-1,qb);
                pair<string,int> temp1=qb[i+1][j];//longestPallindromicSubsequence_rec(str,i+1,j,qb);

                if(temp.second<temp1.second){
                    temp=temp1;
                }
                if(maxp.second<temp.second){
                    maxp.second=temp.second;
                    maxp.first=temp.first;
                }
            }
            qb[i][j]=maxp;
        }
    }
    return qb[0][str.size()-1];
}

void longestPallindromicSubsequence(string str){
    
    vector<vector<pair<string,int>>> qb(str.size(),vector<pair<string,int>>(str.size(),{"",0}));
   pair<string,int> ans=longestPallindromicSubsequence_tab(str,0,str.size()-1,qb);

    cout<<ans.first<<" "; 
    cout<<ans.second<<"\n";
}

// leet 115 ===============================================================================
int numDistinct_rec(string s,string t, int n, int m,vector<vector<int>>& qb){
    if(n<m) return qb[n][m]=0;
    if(m==0) return qb[n][m]=1;

    if(qb[n][m]!=-1) return qb[n][m];

    if(s[n-1]==t[m-1]){
        return qb[n][m]=numDistinct_rec(s,t,n-1,m-1,qb) + numDistinct_rec(s,t,n-1,m,qb);
    } else {
        return qb[n][m]=numDistinct_rec(s,t,n-1,m,qb);
    }
}

int numDistinct_tab(string s,string t, int n, int m,vector<vector<long long>>& qb){
    int N=n;
    int M=m;
    for(n=0;n<=N; n++){
        for(m=0;m<=M;m++){
                if(n<m) {qb[n][m]=0;continue;}
                if(m==0) { qb[n][m]=1;continue;}

               
                if(s[n-1]==t[m-1]){
                     qb[n][m]=qb[n-1][m-1] + qb[n-1][m];//
                     //numDistinct_rec(s,t,n-1,m-1,qb) + numDistinct_rec(s,t,n-1,m,qb);
                } else {
                    qb[n][m]=qb[n-1][m];//numDistinct_rec(s,t,n-1,m,qb);
                }

            }      
        }
        return qb[N][M];
    }
    

int numDistinct(string s, string t) {
    int n=s.size();
    int m=t.size();
    vector<vector<long long>> qb(n+1,vector<long long>(m+1,-1));
    
    int ans=0;
    //ans= numDistinct_rec(s,t,n,m,qb);
    ans=numDistinct_tab(s,t,n,m,qb);
    //display_2D(qb);
    return ans;
}

// leet 1035==========================================================================
int maxUncrossedLines_rec(vector<int>&A, vector<int>&B, int n,int m,vector<vector<int>>& qb){
    if(n==0||m==0){
        return 0;
    }
    int ans=0;
    if(qb[n][m]!=-1) return qb[n][m];
    if(A[n-1]==B[m-1]){
        ans=maxUncrossedLines_rec(A,B,n-1,m-1,qb)+1;
    } else {
        ans=max(maxUncrossedLines_rec(A,B,n-1,m,qb),maxUncrossedLines_rec(A,B,n,m-1,qb));
    }
    return qb[n][m]=ans;
}

int maxUncrossedLines_tab(vector<int>&A, vector<int>&B, int n,int m,vector<vector<int>>& qb){
    int N=n;
    int M=m;
    for(n=1;n<=N;n++){
        for(m=1;m<=M;m++){
            
            int ans=0;
            if(A[n-1]==B[m-1]){
                ans=qb[n-1][m-1]+1;//maxUncrossedLines_rec(A,B,n-1,m-1,qb)+1;
            } else {
                ans=max(qb[n-1][m],qb[n][m-1]);
                //max(maxUncrossedLines_rec(A,B,n-1,m,qb),maxUncrossedLines_rec(A,B,n,m-1,qb));
            }
            qb[n][m]=ans;
        }
    }
    return qb[N][M];
}

int maxUncrossedLines(vector<int>& A, vector<int>& B) {
    int n=A.size();
    int m=B.size();
    vector<vector<int>> qb(n+1,vector<int>(m+1,0));
    int ans=0;
    //ans=maxUncrossedLines_rec(A,B,n,m,qb);
    ans=maxUncrossedLines_tab(A,B,n,m,qb);
    display_2D(qb);
    return ans;        
}

// leet 72==========================================================================
int minDistance_rec(string s, string t, int n, int m,vector<vector<int>>& qb){
    if(n==0 || m==0){
        return qb[n][m]= m==0 ? n:m;
    }
    if(qb[n][m]!=-1) return qb[n][m];
    if(s[n-1]==t[m-1]){
        return qb[n][m]=minDistance_rec(s,t,n-1,m-1,qb);
    } else {
        int delete_=minDistance_rec(s,t,n-1,m,qb);
        int replace=minDistance_rec(s,t,n-1,m-1,qb);
        int insert=minDistance_rec(s,t,n,m-1,qb);

        return qb[n][m]=min(min(replace,insert),delete_)+1;
    }
}

int minDistance_tab(string s, string t, int n, int m,vector<vector<int>>& qb){
   int N=n;
   int M=m;
   for(n=0;n<=N;n++){
       for(m=0;m<=M;m++){
                if(n==0 || m==0){
                     qb[n][m]= m==0 ? n:m;
                     continue;
                }
                
                if(s[n-1]==t[m-1]){
                     qb[n][m]=qb[n-1][m-1];//minDistance_rec(s,t,n-1,m-1,qb);
                } else {
                    int delete_=qb[n-1][m];//minDistance_rec(s,t,n-1,m,qb);
                    int replace=qb[n-1][m-1];//minDistance_rec(s,t,n-1,m-1,qb);
                    int insert=qb[n][m-1];//minDistance_rec(s,t,n,m-1,qb);

                    qb[n][m]=min(min(replace,insert),delete_)+1;
                }
       }
   }
   return qb[N][M];
}

int minDistance(string s, string t) {
       vector<vector<int>> qb(s.size()+1,vector<int>(t.size()+1,0));
        int ans=0;
        //ans=minDistance_rec(s,t,s.size(),t.size(),qb);
        ans=minDistance_tab(s,t,s.size(),t.size(),qb);
        display_2D(qb);
        return ans;
}

// https://www.geeksforgeeks.org/count-palindromic-subsequence-given-string/ ===========
int countPalindromicSubsequences_(string str,int i,int j,vector<vector<int>>& qb){
    if(i==j) return qb[i][j]=1;
    if(i>j) return qb[i][j]=0;

    int a=countPalindromicSubsequences_(str,i+1,j-1,qb);
    int b=countPalindromicSubsequences_(str,i+1,j,qb);
    int c=countPalindromicSubsequences_(str,i,j-1,qb);

    if(str[i]==str[j]){
        return qb[i][j]=(a+1) + (b+c-a);  // coz of duplicates;
    } else {
        return qb[i][j]=b+c-a;
    }
}

int countPalindromicSubsequences(string S) {
         vector<vector<int>> qb(S.size()+1,vector<int>(S.size()+1,-1));
        countPalindromicSubsequences_(S,0,S.size()-1,qb);
}
// leet 940=======================================================================
   int distinctSubseqII(string str) {
    vector<long long> loc(26,-1);  // for storing index of last occurenece of char 
    int n=str.size();
    str="$"+str;
    int m=(int)1e9+7;
    vector<long long> qb(n+1,0);
    qb[0]=1;  //  for khali subsequence="";
    for(int i=1; i<=n; i++){
        qb[i]=(2* qb[i-1]%m)%m;   /// pahle vale se double to hoga hi
            
        int j=str[i]-'a';
        if(loc[j]!=-1){
            qb[i]=(qb[i]%m-(qb[loc[j]-1]%m) + m)%m;  // +m because subtract bhi kiya h pahle
        } 
        
        loc[j]=i;
    }       
    return qb[n]-1;  // last m "" ye subtract;
}

// LIS set ========================================================================

// leet 354 =========================================================================
static bool compare(vector<int>& a, vector<int>& b){
        if(a[0]==b[0]) return a[1]>b[1];
        else return a[0]<b[0];
}

int maxEnvelopes(vector<vector<int>>& env){
        sort(env.begin(),env.end(),compare); // comapartor 
        int ans=0;
        vector<int> dp(env.size(),1);
        for(int i=0; i<env.size(); i++){
            for(int j=i-1; j>=0; j--){
                if(env[i][1] > env[j][1]){
                    dp[i]=max(dp[j]+1,dp[i]);
                }
            }
            ans=max(ans,dp[i]);
        }
        return ans;
}

// leet 673====================================================================
 int findNumberOfLIS(vector<int>& nums) {
    int n=nums.size();
    vector<int> dp(n,1);
    vector<int> count(n,1);

    int maxLen=0;
    int maxCount=0;

    for(int i=0; i<n; i++){
        for(int j=i-1; j>=0; j--){
            if(nums[i]>nums[j]){
                if(dp[i]<dp[j]+1){  // simple math.max ki kagah if else laga rahe taki count 
                    dp[i]=dp[j]+1;  // sath chal ske
                    count[i]=count[j];
                } else if(dp[i]==dp[j]+1){
                    count[i]+=count[j];
                }
            }
        }
        if(dp[i]>maxLen){   // updating our answers
            maxLen=dp[i];
            maxCount=count[i];
        } else if(maxLen==dp[i]){
            maxCount+=count[i];
        }
    }        
    return maxCount;
}

// leet 1027 =====================================

// we can use 2d vector of constraint size instead of vector of map
// this giving TLE
int longestArithSeqLength(vector<int>& A) {
        int n=A.size();
        vector<unordered_map<int,int>> dp(n);
        
        int ans=2;
        for(int i=0; i<n; i++){
            for(int j=0; j<i; j++){
                int v=A[i]-A[j];
                if(dp[j].find(v)==dp[j].end()){
                    dp[i][v]=2;
                } else {
                    dp[i][v]=dp[j][v]+1;
                    ans=max(ans,dp[i][v]);
                }
            }
        }
        return ans;
    }

// leet 494===============================================================
 int some(vector<int>& nums,int tar,vector<vector<int>>& qb,int sum, int i){
        if(sum==tar && i==nums.size()){
            if(sum>=0) qb[i][sum]=1;
            return 1;
        }
        if(i>=nums.size()) return 0;
        if(sum>=0 && qb[i][sum]!=-1) return qb[i][sum];
        
        int ans=0;
        ans+=some(nums,tar,qb,sum+nums[i],i+1);
        ans+=some(nums,tar,qb,sum-nums[i],i+1);
        
        if(sum>=0) qb[i][sum]=ans;
        return ans;
}

int findTargetSumWays(vector<int>& nums, int S) {
        int sum=0;
        for(int num:nums)sum+=num;
        vector<vector<int>> qb(nums.size()+1,vector<int>(2*sum+1,-1));
        return some(nums,S,qb,0,0);
}

// Other good important question ==================

// leet 1277 ==============================================================
//count binary square in a matrix 
int countSquares(vector<vector<int>>& matrix) {
        int n=matrix.size();
        int m=matrix[0].size();
        
        vector<vector<int>> qb(n,vector<int>(m,0));
        
        int ans=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(i==0 || j==0){
                    ans+=qb[i][j]=matrix[i][j];
                } else if(matrix[i][j]==1){
                    qb[i][j]=min(qb[i][j-1],min(qb[i-1][j],qb[i-1][j-1]))+1; // qb se compare 
                                                //                  kyunki aane vala 1 can be part of many squares                                                                    part ban skta baki square ka
                    ans+=qb[i][j];
                }
            }
        }
        return ans;
    }


//  CUT-TYPE =============== CUT-TYPE =========== CUT-TYPE ==============================
void matrixChainMultiplication(){
    vector<int> arr={40, 20, 30, 10, 30};
    
}

void cutType(){
    matrixChainMultiplication();
}

void stringSet(){
    //longestPallindromicSubsequence("forgeeksskeegfor");
    // leet 115 ================
    //cout<<numDistinct("rabbbit","rabbit");

    //leet 1035====================================
    vector<int> A={2,5,1,2,5};
    vector<int> B={10,5,2,1,5,2};
    //cout<<maxUncrossedLines(A,B);

    // leet 72==========================
    cout<<minDistance("horse","ros");

    // leet 940=============
    cout<<distinctSubseqII("aba");
}

void solve(){
    //cout<<countFriendsPairings(4)<<endl;
    
    //cout<<kSubsets(5,3);

    //stringSet();

    cutType();
}

int main(){
    solve();
    return 0;
}