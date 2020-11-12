import java.util.Arrays;
public class recursion4 {
    public static class pair{
        String path;
        int len;
        public pair(String path, int len){
            this.path=path;
            this.len=len;
        }
    }
    /*============for knightsTour ======================= */ 
    static int xMove[] = {2, 1, -1, -2, -2, -1, 1, 2}; 
    static int yMove[] = {1, 2, 2, 1, -1, -2, -2, -1}; 

    static int dir8[][]={{1, 0}, {0, -1}, {-1, 0}, {0, 1}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
    static String dirS[]={"D", "L", "U", "R", "S", "N", "E", "W"};

    public static pair flood_fill_Jump(int i, int j, int n, int m, int[][] visit){
        if(i==n && j==m){
            pair pp=new pair("",0);
            return pp;
        }
        visit[i][j]=1;
        pair ans=new pair("",(int)1e8);
        for(int jump=0; jump<=Math.max(n,m); jump++){
        for(int k=0; k<dir8.length; k++){
            int r=i+jump*dir8[k][0];
            int c=j+jump*dir8[k][1];

            if(r<visit.length && c<visit[0].length && r>=0 && c>=0 && visit[r][c]==0){
                pair smallAns=flood_fill_Jump(r,c,n,m,visit);
                if(smallAns.len+1<ans.len){
                    ans.len=smallAns.len+1;
                    ans.path=dirS[k]+jump+" "+ smallAns.path;
                }
            }
        }
    } 
        visit[i][j]=0;
        return ans;
    }
    
    public static void flood_fill_Jump_utility(){
         int[][] visit=new int[3][3];
        pair ans=flood_fill_Jump(0,0,2,2,visit);
        System.out.println(ans.path+" "+ans.len);
    }
/*=====https://www.geeksforgeeks.org/the-knights-tour-problem-backtracking-1/====*/

    public static boolean knightsTour(int i, int j, int count,boolean visit[][], int[][] arr){
        if(count==63){
            arr[i][j]=count;
            return true; 
        }
        arr[i][j]=count;
        //visit[i][j]=true;
        for(int k=0; k<xMove.length; k++){
            int r=i+xMove[k];
            int c=j+yMove[k];
            if(r>=0 && c>=0 && r<arr.length && c<arr[0].length && /*visit[r][c]*/ arr[r][c]==-1){
                if(knightsTour(r, c, count+1,visit, arr)){
                    return true;
                }
            }
        }
        arr[i][j]=-1;
        //visit[i][j]=false;
        return false;
    }

    public static void knightsTourUtil(){
        int arr[][]=new int[8][8];
        for(int[] ar: arr)Arrays.fill(ar,-1);
        boolean[][] visit=new boolean[8][8];
        knightsTour(0,0,0,visit,arr);
        for(int[] ele: arr){
            for(int e: ele){
                System.out.print(e+" ");
            }
            System.out.println();
        }
    }
/*=================COIN CHANGE SET=======================*/
    public static int coinChangePermutations(int[] coins, int tar, String ans){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int i=0; i<coins.length; i++){
            if(tar-coins[i]>=0)
            count+=coinChangePermutations(coins,tar-coins[i],ans+coins[i]);
        }
        return count;
    }

    public static int coinChangeCombinations(int[] coins, int tar,int i, String ans){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=i; j<coins.length; j++){
            if(tar-coins[j]>=0)
            count+=coinChangeCombinations(coins,tar-coins[j],j,ans+coins[j]);
        }
        return count;
    }

    public static int coinChangePermutations_01(int[] coins,int tar,String ans,boolean[] visit){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=0; j<coins.length; j++){
            if(tar-coins[j]>=0 && !visit[j]){
                visit[j]=true;
                count+=coinChangePermutations_01(coins,tar-coins[j],ans+coins[j],visit);
                visit[j]=false;
            }
        }
        return count;
    }

    public static int coinChangeCombination_01(int[] coins, int tar,int i, String ans, boolean[] visit){
        if(tar==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=i; j<coins.length; j++){
            if(tar-coins[j]>=0 && !visit[j]){
            visit[j]=true;    
            count+=coinChangeCombination_01(coins,tar-coins[j],j,ans+coins[j],visit);
            visit[j]=false;
            }
        }
        return count;
    }

    public static int coinChange_single(int[] coins, int tar, int i, String ans){
        if(tar==0 || i>=coins.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int count=0;
        if(tar-coins[i]>=0)
        count+=coinChange_single(coins,tar-coins[i],i+1,ans+coins[i]);
        count+=coinChange_single(coins,tar,i+1,ans);

        return count;
    }

    public static int coinChangePermutations_subseq(int[] coins, int tar, int i, String ans){
        if(tar==0 || i>=coins.length){
            if(tar==0){
            System.out.println(ans);
            return 1;
            }
            return 0;
        }
        int count=0;
        if(tar-coins[i]>=0)
            count+=coinChangePermutations_subseq(coins,tar-coins[i],0,ans+coins[i]);

        count+=coinChangePermutations_subseq(coins,tar,i+1,ans);    
        return count;
    }

    public static int coinchangeCombination_Subseq(int[] coins, int tar, int i, String ans){
        if(tar==0 || i>=coins.length){
            if(tar==0){
            System.out.println(ans);
            return 1;
            }
            return 0;
        }
        int count=0;
        if(tar-coins[i]>=0)
            count+=coinchangeCombination_Subseq(coins,tar-coins[i],i,ans+coins[i]);

        count+=coinchangeCombination_Subseq(coins,tar,i+1,ans);    
        return count;
    }

    public static int coinchangePermutations_Single_subs(int[]coins,int tar,int i,String ans,boolean[]visit){
        if(tar==0 || i>=coins.length){
            if(tar==0){
                System.out.println(ans);
                return 1;
            }
            return 0;
        }
        int count=0;
        if(tar-coins[i]>=0 && !visit[i]){
            visit[i]=true;
            count+=coinchangePermutations_Single_subs(coins,tar-coins[i],0,ans+coins[i],visit);
            visit[i]=false;
        }
        count+=coinchangePermutations_Single_subs(coins,tar,i+1,ans,visit);
        return count;
    }

    public static void coinChange(){
        int coins[]={2,3,5,7};
        boolean visit[]=new boolean[coins.length];
        int total_permutations=coinChangePermutations(coins,10,"");
        System.out.println("total_permutations: "+total_permutations);

        System.out.println("=========================");

        int total_combinations=coinChangeCombinations(coins,10,0,"");
        System.out.println("total_combinations: "+total_combinations);

        System.out.println("=========================");

        visit=new boolean[coins.length];
        int total_permutations_Single_coin=coinChangePermutations_01(coins, 10, "",visit);
        System.out.println("total_permutations_Single_coin: "+total_permutations_Single_coin);

        System.out.println("==========================");

        int total_combination_using_one_coin_one_time=coinChangeCombination_01(coins,10,0,"",visit);
        System.out.println("total_combination_using_one_coin_one_time: "+total_combination_using_one_coin_one_time);
        
        System.out.println("===========================");

        int total_combination_using_one_coin_one_time_subs=coinChange_single(coins,10,0,"");
        System.out.println("total_combination_using_one_coin_one_time_subs: "+total_combination_using_one_coin_one_time_subs);
    
        System.out.println("============================");

        int total_permutations_subs=coinChangePermutations_subseq(coins,10,0,"");
        System.out.println("total_permutations_subs: "+total_permutations_subs);

        System.out.println("============================");

        int total_combinations_subs=coinchangeCombination_Subseq(coins,10,0,"");
        System.out.println("total_combinations_subs: "+total_combinations_subs);

        System.out.println("============================");

        visit=new boolean[coins.length];
        int total_permu_single_use_subs=coinchangePermutations_Single_subs(coins,10,0,"",visit);
        System.out.println("total_permu_single_use_subs: "+total_permu_single_use_subs);
    }    

    public static void solve(){
        flood_fill_Jump_utility();
        knightsTourUtil();
        coinChange();
    }
    public static void main(String[] args){
        solve();
    }
}