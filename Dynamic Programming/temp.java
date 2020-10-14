class temp{
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
       
        int[][][]dp=new int[m+1][n+1][target+1];
        
        int ans=find(houses,cost,0,0,0,target,dp);
        return ans>=(int)1e8?-1:ans;
    }

private int find(int[] houses,int[][] cost,int idx,int prev,int nsf,int target,int[][][]dp){
        if(idx>=houses.length){
            if(nsf==target){ 
                return 0;
            
            }
            
            return (int)1e8;
        }
        if(nsf>target) return (int)1e8;
        
        if(dp[idx][prev][nsf] != 0) {
            return dp[idx][prev][nsf];
        }
        
        int payment=(int)1e8;
        if(houses[idx]!=0){
            int nn=prev==houses[idx]?nsf:nsf+1;
            return dp[idx][prev][nsf]=find(houses,cost,idx+1,houses[idx],nn,target,dp);
        }
        for(int i=0;i<cost[idx].length;i++){
            if((i+1)!=prev){
                payment=Math.min(payment,cost[idx][i]+find(houses,cost,idx+1,i+1,nsf+1,target,dp));
            }
            else{
                payment=Math.min(payment,cost[idx][i]+find(houses,cost,idx+1,i+1,nsf,target,dp));
            }
        }
        dp[idx][prev][nsf]=payment;
        return payment;
    }
}