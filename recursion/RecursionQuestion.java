public class RecursionQuestion {
    static int[][] irs={{0,1},{-1,0},{1,0},{0,-1}};
    static String yo="";
    static int yoo=Integer.MAX_VALUE;
    // https://www.geeksforgeeks.org/find-shortest-safe-route-in-a-path-with-landmines/ ====
    public static int rec(int[][] matrix, boolean[][] visit, int i, int j, int ans,String psf){
         psf+="("+i+", "+j+") ";
         
         if(j==matrix[0].length-1){
            if(matrix[i][j]!=0){
                if(ans<yoo){
                    yoo=ans;
                    yo=psf;
                }
                return ans;
            }
            return Integer.MAX_VALUE;    
        }
        
        int n=matrix.length;
        int m=matrix[0].length;
        int y=Integer.MAX_VALUE;
        for(int[] ir: irs){
            int r=(i+ir[0]);
            int c=(j+ir[1]);

            if(r>=0 && c>=0 && r<matrix.length && c<matrix[0].length && visit[r][c]==false && matrix[r][c]!=0 ){
                visit[r][c]=true;
                y=Math.min(y,rec(matrix,visit,r,c,ans+matrix[i][j],psf));
                visit[r][c]=false;                
                }
        }
        return y;
    }

    public static void ShortestPath(){
        int matrix[][]={
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, 
            { 1, 0, 1, 1, 1, 1, 1, 1, 0, 1 }, 
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 0, 1, 1, 1, 1, 0, 1, 1, 1, 1 }, 
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
            { 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 } 
        };
        int ans=Integer.MAX_VALUE;
        boolean visit[][]=new boolean[matrix.length][matrix[0].length];
        for(int i=0; i<matrix.length-1; i++){
            for(int j=0; j<matrix[0].length-1; j++){
                if(matrix[i+1][j]==0){
                    visit[i][j]=true;
                }
                if(matrix[i][j+1]==0){
                    visit[i][j]=true;
                }
                if(matrix[i][j]==0){
                    visit[i][j]=true;
                    visit[i+1][j]=true;
                    visit[i][j+1]=true;
                }
            }
        }
        for(int i=0;i<matrix.length; i++){
            if(!visit[i][0])
            ans=Math.min(ans,rec(matrix,visit,i,0,0,""));
        }
        System.out.println(yo);
        System.out.println(ans);
    }
//==  https://www.geeksforgeeks.org/tug-of-war/ ==================================
    static int ans=Integer.MAX_VALUE;
public static void minDiff(int[]arr,boolean[] sol, boolean[]curr,int sum,int n, int i, int csum, int cnoe){
    if(i==n) return;

    if((n/2 - cnoe) > (n-i)) return;

    minDiff(arr, sol, curr, sum, n, i+1, csum, cnoe);

    curr[i]=true;
    cnoe++;
    csum+=arr[i];
    if(cnoe==n/2){
        if(Math.abs(sum/2 - csum) < ans){
            ans=Math.abs(sum/2 - csum);
            for(int j=0; j<n; j++){
                sol[j]=curr[j];
            }
        }
    } else {
        minDiff(arr, sol, curr, sum, n, i+1, csum, cnoe);
    }
    curr[i]=false;
}

public static void TugOfWar(int[] arr){
        
        int n=arr.length;
        boolean sol[] = new  boolean[n];
        boolean curr[]= new  boolean[n];
        int sum=0;
        for(int ele:arr)sum+=ele;

        minDiff(arr,sol,curr,sum,n,0,0,0);

        System.out.println(ans);

        System.out.println("Set-1 ");
        for(int i=0; i<n; i++){
            if(sol[i]) System.out.print(arr[i]+" ");
        }

        System.out.println("Set-2 ");
        for(int i=0; i<n; i++){
            if(!sol[i]) System.out.print(arr[i]+" ");
        }
    }


    public static void solve(){
        //ShortestPath();
        int arr[]={23, 45, -34, 12, 0, 98, -99, 4, 189, -1, 4};
        TugOfWar(arr);
        System.out.println("===========================");
        int arr2[]={3, 4, 5, -3, 100, 1, 89, 54, 23, 20};
        TugOfWar(arr2);
    }
    public static void main(String[] args) {
        solve();
    }    
}
