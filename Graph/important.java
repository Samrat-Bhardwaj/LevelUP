import java.util.*;
class important{


    // leet 417 ====================================================================================
    int[][] dirs={{0,1},{1,0},{-1,0},{0,-1}};
    public void fillWater(int[][] matrix, int[][] flow, boolean val){
        LinkedList<Integer> que=new LinkedList<>();
        int n=matrix.length;
        int m=matrix[0].length;
        boolean[][] vis=new boolean[n][m];
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if(val){ // pacific ki call
                    if(i==0 || j==0){
                         que.addLast(i*m +j);
                        vis[i][j]=true;
                    }
                } else { // atlantic ki call
                    if(i==n-1 || j==m-1){
                        que.addLast(i*m +j);
                        vis[i][j]=true;
                    }
                }
            }
        }
        
        while(que.size()>0){
            int s=que.size();
            while(s-->0){
                int idx=que.removeFirst();
                
                int r=idx/m;
                int c=idx%m;
                
                for(int[] dir : dirs){
                    int x=r+dir[0];
                    int y=c+dir[1];

                if(x>=0 && y>=0 && x<n && y<m && !vis[x][y]&&matrix[r][c] <= matrix[x][y]){
                    boolean f=true;
                if((val && flow[x][y]==1) || (flow[x][y]==0 && !val)){ //val tells 0 ya 1 
                            flow[x][y]=2;
                            f=false;
                    }
                       
                        if(f) // agr 2 nhi bana pahle to hi banayenge 0 ya 1
                        flow[x][y]=val ? 0 : 1;
                        vis[x][y]=true;
                        que.addLast(x*m + y);
                    }
                }
            }
        }
    }
   
    // agr 0 ya 1 h n/m ka size to har jagah pani jayega 
     public List<List<Integer>> specialCase(int[][] matrix) {
        List<List<Integer>> ans=new ArrayList<>();
         for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                ArrayList<Integer> temp=new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    ans.add(new ArrayList(temp));     
                 
            }
           
        }
         return ans;
     }
    
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> ans=new ArrayList<>();
       int n=matrix.length;
        if(n==0) return ans;
        int m=matrix[0].length;
        if(m==0) return ans;
        if(n==1 || m==1) return specialCase(matrix);
        
        int[][] flow=new int[n][m];
        // making our flow matrix 
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
                if((i==0 && j==m-1)||(i==n-1 && j==0)){
                    flow[i][j]=2;// atlantic pacific har jagah ka pani aayega
                    continue;
                }
                
                if(i==0 || j==0){
                    flow[i][j]=0; // pacific ka pani
                } else if(i==n-1 || j==m-1){
                    flow[i][j]=1; // atlantic ka pani
                } else{
                    flow[i][j]=-1; // kahi ka pani nhi
                }
            }
        }
        
        
        fillWater(matrix,flow,true);     // ek bar pacific valo k liye function
        fillWater(matrix,flow,false);    // ek bar atlantic k liye call     
        
        for(int i=0; i<n; i++){
            for(int j=0; j<m; j++){
               
                if(flow[i][j]==2){ // making our answer
                    ArrayList<Integer> temp=new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    ans.add(new ArrayList(temp));     
                }    
            }
        }
        return ans;
    }


    // leet 1631 =======================================================================

    // good bfs ques
    class Solution {
        int[][] dirs={{0,1},{1,0},{-1,0},{0,-1}};
        // bfs jisme hum bar bar same points bhi dalva rahe h agr acha answer ban jaye to
        public int minimumEffortPath(int[][] heights) {
            int n=heights.length;
            int m=heights[0].length;
            
            PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->{
                return a[0]-b[0];
            });
            
            int[][] vis=new int[n][m]; // works as boolean &
            for(int[] qb: vis) Arrays.fill(qb,(int)1e8);
            pq.add(new int[]{0,0});
            while(pq.size()>0){
                int s=pq.size();
                while(s-->0){
                    int[] pr=pq.remove();
                    int effort=pr[0];
                    int idx=pr[1];
                    
                    int r=idx/m;
                    int c=idx%m;
                    
                    if(effort>=vis[r][c]) continue; // agr aage jake kam ka rasta milega tabhi vaha jayenge
                    vis[r][c]=effort;
                    
                    if(r==n-1 && c==m-1) return effort;
                    for(int[] dir : dirs){
                        int x=r+dir[0];
                        int y=c+dir[1];
                    if(x>=0 && y>=0 && x<n && y<m){
                        // vis[x][y]=true;
                         int tp=Math.max(effort,Math.abs(heights[x][y]-heights[r][c]));
                        pq.add(new int[]{tp,x*m + y});
                    }
                }
            }
        }
            return -1;
    }
    } 
}