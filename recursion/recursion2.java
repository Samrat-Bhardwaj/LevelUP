import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class recursion2 {
public static int permutations(String str,String ans){
        if(str.length()==0){
            System.out.println(ans);
            return 1;
        }
        int an=0;
        for(int i=0; i<str.length(); i++){
            String ros=str.substring(0,i)+str.substring(i+1);
            an+=permutations(ros,ans+str.charAt(i)); 
        }
        return an;
    }

    //leet 46
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> temp=new ArrayList<>();
       
        boolean []visit=new boolean[nums.length];
        permu(nums,ans,0,temp,visit);
        return ans;
    }
    public static int permu(int[] nums,List<List<Integer>> ans,int i,List<Integer> temp, boolean[] visit){
        if(i==nums.length){
            ans.add(new ArrayList<>(temp));
            return 1;
        }
        int an=0;
        for(int j=0; j<nums.length; j++){
            if(!visit[j]){
                visit[j]=true;
                temp.add(nums[j]);
                an+=permu(nums,ans,i+1,temp,visit);
                temp.remove(temp.size()-1);
                visit[j]=false;
            }
        }
        return an;
    }
    public static void leet46(){
        int[] nums={1,2,3};
        List<List<Integer>> ans=new ArrayList<>();
        ans=permute(nums); 
        System.out.println(ans);
    }
    //leet 47
    public static void leet47(){
        int[] nums={1,2,1};
        List<List<Integer>> ans=new ArrayList<>();
        ans=permuteUnique(nums); 
        System.out.println(ans);
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> temp=new ArrayList<>();
        boolean []visit=new boolean[nums.length];
        Arrays.sort(nums);
        some(nums,ans,0,visit,temp);
        return ans;
    }
public static int some(int[]nums, List<List<Integer>>ans,int count,boolean[]visit,List<Integer>temp)
{
    if(count==nums.length){
        ans.add(new ArrayList<>(temp));
        return 1;
    }
    int prev=-(int)1e8;
    int an=0;
    for(int i=0; i<nums.length; i++){
        if(!visit[i] && prev!=nums[i]){
            visit[i]=true;
            temp.add(nums[i]);
            an+=some(nums,ans,count+1,visit,temp);
            temp.remove(temp.size()-1);
            visit[i]=false;
            prev=nums[i];
        }
    }
    return an;
}

    public static int maze1(int i, int j,int n, int m, String psf){
        if(i==n-1 && j==m-1){
            System.out.println(psf);
            return 1;
        }
        int ans=0;
        if(i+1<n){
            ans+=maze1(i+1, j, n, m, psf+"D");
        }
        if(j+1<m){
            ans+=maze1(i, j+1, n, m, psf+"R");
        }
        return ans;
    }

    public static void mazePathWithoutJump(){
        int n=3;
        int m=3;
        int numberOfPaths=maze1(0,0,n,m,"");
        System.out.println(numberOfPaths);
    }
    public static int maze2(int i, int j,int n, int m, String psf){
        if(i==n-1 && j==m-1){
            System.out.println(psf);
            return 1;
        }
        int ans=0;
        if(i+1<n){
            ans+=maze2(i+1, j, n, m, psf+"D");
        }
        if(j+1<m){
            ans+=maze2(i, j+1, n, m, psf+"R");
        }
        if(i+1<n && j+1<m){
            ans+=maze2(i+1, j+1, n, m, psf+"H");
        }
        return ans;
    }

    public static void mazePathWithoutJump2(){
        int n=3;
        int m=3;
        int numberOfPaths=maze2(0,0,n,m,"");
        System.out.println(numberOfPaths);
    }
    public static ArrayList<String> maze3(int i, int j,int n, int m){
        if(i==n-1 && j==m-1){
            ArrayList<String> bc=new ArrayList<>();
            bc.add("");
            return bc;
        }
        ArrayList<String> ans=new ArrayList<>();
        for(int jumps=1; jumps<=n; jumps++){
            if(i+jumps<n){
                ArrayList<String> sa=maze3(i+jumps,j,n,m);
                for(String s:sa){
                    ans.add("D"+jumps+s);
                }
            }
            if(j+jumps<m){
                ArrayList<String> sa=maze3(i,j+jumps,n,m);
                for(String s:sa){
                    ans.add("R"+jumps+s);
                }
            }
        }
        return ans;
    }
    public static void mazePathWithJump(){
        int n=3;
        int m=3;
        ArrayList<String> ans=new ArrayList<>();
        ans=maze3(0,0,n,m);
        for(String s:ans){
            System.out.println(s);
        }
        System.out.println(ans.size());
    }
    public static int maze4(int i, int j, int n, int m, String psf, boolean[][] visit){
        if(visit[i][j]){
            return 0;
        }
        if(i==n-1 && j==m-1){
            System.out.println(psf);
            return 1;
        }
        visit[i][j]=true;
        int ans=0;
        if(i+1<n){
            ans+=maze4(i+1, j, n, m, psf+"D",visit);
        }
        if(j+1<m){
            ans+=maze4(i, j+1, n, m, psf+"R",visit);
        }
        if(i-1>=0){
            ans+=maze4(i-1, j, n, m, psf+"U",visit);
        }
        if(j-1>=0){
            ans+=maze4(i, j-1, n, m, psf+"L",visit);
        }
        visit[i][j]=false;
        return ans;
    }

    public static void mazePathWithJump2(){
        int n=3;
        int m=3;
        boolean visit[][]=new boolean[n][m];
        int numberOfPaths=0;
        numberOfPaths=maze4(0,0,n,m,"",visit);

        System.out.println(numberOfPaths);
    }

    public static int maze5(int i, int j, int n, int m,String psf, boolean[][] visit){
        if(visit[i][j]){
            return 0;
        }
        if(i==n-1 && j==m-1){
            System.out.println(psf);
            return 1;
        }
        int num=0;
        visit[i][j]=true;
        for(int jump=0; jump<n; jump++){
            if(j+jump<m)
            num+=maze5(i,j+jump,n,m,psf+"R"+jump,visit);
            if(i+jump<n)
            num+= maze5(i+jump,j,n,m,psf+"D"+jump,visit);
            if(i-jump>=0)
            num+=maze5(i-jump,j,n,m,psf+"U"+jump,visit);
            if(j-jump>=0)
            num+=maze5(i,j-jump,n,m,psf+"L"+jump,visit);
            if(i+jump<n && j-jump>=0)
            num+=maze5(i+jump,j-jump,n,m,psf+"HDL"+jump,visit);
            if(i-jump>=0 && j+jump<m)
            num+=maze5(i-jump,j+jump,n,m,psf+"HUR"+jump,visit);
            if(i+jump<n && j+jump<m)
            num+=maze5(i+jump,j+jump,n,m,psf+"HDR"+jump,visit);
            if(i-jump>=0 && j-jump>=0)
            num+=maze5(i-jump,j-jump,n,m,psf+"HUL"+jump,visit);
        }
         visit[i][j]=false;
        return num;
    }

    public static void mazePathWithJump3(){
        int n=3;
        int m=3;
        boolean visit[][]=new boolean[n][m];
        int numberOfPaths=0;
        numberOfPaths=maze5(0,0,n,m,"",visit);

        System.out.println(numberOfPaths);
    }

    public static void solve(){
        //String str="samrat";
        //int j=permutations(str,"");
        //System.out.println("number of permutations: "+j);
        //leet46();
        //leet47();
        //mazePathWithoutJump(); //right, fown
        //mazePathWithoutJump2(); // 3 irs
        mazePathWithJump(); // return type 
       //mazePathWithJump2(); //  4 irs;
        //mazePathWithJump3(); // 8 irs with infinte jumps;
    }

    public static void main(String[] args) {
        solve();    
    }
}