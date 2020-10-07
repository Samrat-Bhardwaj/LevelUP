import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.HashSet;
public class recursion5 {
    
    static int[][] dirs={{0,-1},{-1,-1},{-1,0},{-1,1},{1,1},{0,1},{1,-1},{1,0}};

    public static int Queen_3_box6_combination(boolean[] box, int qpsf, String ans, int i){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=i; j<box.length; j++){
            if(!box[j]){
                box[j]=true;
                count+=Queen_3_box6_combination(box,qpsf-1," "+ans+"b"+j+"q"+(3-qpsf)+" ",j+1);
                box[j]=false;
            }
        }
        return count;
    }

    public static int Queen_3_box6_permutation(boolean[] box, int qpsf, String ans){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=0; j<box.length; j++){
            if(!box[j]){
                box[j]=true;
                count+=Queen_3_box6_permutation(box,qpsf-1," "+ans+"b"+j+"q"+(3-qpsf)+" ");
                box[j]=false;
            }
        }
        return count;
    }

    public static int Queen_3_box6_combination2D(boolean[][] box, int qpsf, String ans, int i){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        int n=box.length;
        for(int j=i; j<n*n; j++){
            int r=j/n;
            int c=j%n;
            if(!box[r][c]){
                box[r][c]=true;
                count+=Queen_3_box6_combination2D(box,qpsf-1,ans+"("+r+","+c+")",j+1);
                box[r][c]=false;
            }
        }
        return count;
    }

    public static int Queen_3_box6_permutation2D(boolean[][] box2D, int qpsf, String ans){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        int n=box2D.length;
        for(int j=0; j<box2D.length*box2D.length; j++){
            int r=j/n;
            int c=j%n;
            if(!box2D[r][c]){
                box2D[r][c]=true;
                count+=Queen_3_box6_permutation2D(box2D,qpsf-1,ans+"("+r+" "+c+")");
                box2D[r][c]=false;
            }
        }
        return count;
    }


    public static void QueenCombination_Permutation(){
        boolean box[]=new boolean[6];
        int ans1=Queen_3_box6_combination(box,3,"",0);
        System.out.println(ans1);

        System.out.println("========================");

        int ans2=Queen_3_box6_permutation(box,3,"");
        System.out.println(ans2);

        boolean box2D[][]=new boolean[4][4];
        int ans3=Queen_3_box6_combination2D(box2D,4,"",0);
        System.out.println(ans3);

        System.out.println("========================");

        int ans4=Queen_3_box6_permutation2D(box2D,4,"");
        System.out.println(ans4);
    }

    // ================= N_QUEEN ========================================

    public static boolean isQueenSafe(boolean[][] box, int r, int c){
        int n=box.length;
        int m=box[0].length;
        // for permutaions ->
        // for(int i=0; i<n; i++){
        //     for(int[] dir:dirs){
        //         int row=r+(i*dir[0]);
        //         int col=c+(i*dir[1]);
        //         if(row>=0 && col>=0 && row<n && col<m && box[row][col]){
        //             return false;
        //         }
        //     }
        // }

        //for combination ->
        int[][] dir={{0,-1},{-1,-1},{-1,0},{-1,1}}; // coz we are placing previous queens before and
                                                    // moving forward
        for(int d=0; d<4; d++){
            for(int i=0; i<n; i++){
                int row=r+(i*dir[d][0]);
                int col=c+(i*dir[d][1]);
                if(row>=0 && col>=0 && row<n && col<m && box[row][col]){
                    return false;
                }
            }
        }
        return true;
    }

    public static int Nqueen(boolean[][] box, int qpsf, String ans,int i){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        int n=box.length;
        int m=box[0].length;
        for(int j=i; j<n*m; j++){
            int r=j/n;
            int c=j%m;
            if(isQueenSafe(box,r,c)){
                box[r][c]=true;
                count+=Nqueen(box,qpsf-1,ans+"("+r+","+c+")",j+1);
                box[r][c]=false;
            }
        }
        return count;       
    }

    public static int nqueenPermutaions(boolean[][] box, int qpsf, String ans){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        int n=box.length;
        int m=box[0].length;
        for(int j=0; j<box.length*box[0].length; j++){
            int r=j/n;
            int c=j%m;
            if(!box[r][c] && isQueenSafe(box, r, c)){
                box[r][c]=true;
                count+=nqueenPermutaions(box,qpsf-1,ans+"("+r+","+c+")");
                box[r][c]=false;
            }
        }
        return count;
    }

    static boolean[]rowA,colA,diagA,adiagA; //diagA-diagonal array; //adiagA=anti-diagonal array
    public static int Nqueen_fast(int n,int m, int qpsf, String ans,int i){
        if(qpsf==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int j=i; j<n*m; j++){
            int r=j/n;
            int c=j%m;
            if(!rowA[r] && !colA[c] && !diagA[r+c] && !adiagA[c-r+m-1]){
                rowA[r]=!rowA[r];
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+m-1]=!adiagA[c-r+m-1];

                count+=Nqueen_fast(n,m,qpsf-1,ans+"("+r+","+c+")",j+1);
                
                rowA[r]=!rowA[r];
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+m-1]=!adiagA[c-r+m-1];
            }
        }
        return count;       
    }

    public static int Nqueen_fast_02(int n, int r,int tnq, String ans){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int c=0; c<n; c++){

            if(!colA[c] && !diagA[r+c] && !adiagA[c-r+n-1]){
               
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+n-1]=!adiagA[c-r+n-1];

                count+=Nqueen_fast_02(n,r+1,tnq-1,ans+"("+r+","+c+")");
                
              
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+n-1]=!adiagA[c-r+n-1];
            }
        }
        return count;
    }

    public static int Nqueen_fast_subs(int n, int r,int c, int tnq, String ans){
        if(tnq==0 || r>=n || c>=n){
            if(tnq==0){
            System.out.println(ans);
            return 1;
            }
            return 0;
        }
        int count=0;
        

            if(!colA[c] && !diagA[r+c] && !adiagA[c-r+n-1]){
               
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+n-1]=!adiagA[c-r+n-1];

                count+=Nqueen_fast_subs(n,r+1,0,tnq-1,ans+"("+r+","+c+")");
                
              
                colA[c]=!colA[c];
                diagA[r+c]=!diagA[r+c];
                adiagA[c-r+n-1]=!adiagA[c-r+n-1];
            }
        
        count+=Nqueen_fast_subs(n,r,c+1,tnq,ans);
        return count;
    }

    static int colN=0,diagN=0,adiagN=0;
    public static int Nqueen_fast_02_bits(int n, int r,int tnq, String ans){
        if(tnq==0){
            System.out.println(ans);
            return 1;
        }
        int count=0;
        for(int c=0; c<n; c++){

            if(((colN & (1<<c)) ==0) && ((diagN & (1<<(r+c)))==0) && ((adiagN & (1<<(c-r+n-1)))==0)){
               
                colN^=(1<<c);
                diagN^=(1<<(r+c));
                adiagN^=(1<<(c-r+n-1));

                count+=Nqueen_fast_02_bits(n,r+1,tnq-1,ans+"("+r+","+c+")");
                
              
                colN^=(1<<c);
                diagN^=(1<<(r+c));
                adiagN^=(1<<(c-r+n-1));

            }
        }
        return count;
    }

    public static void NQueens(){
        boolean box[][]=new boolean[8][8];
        // int ans=Nqueen(box,8,"",0);
        // System.out.println(ans);

        System.out.println("==================");

        // box=new boolean[4][4];
        // int ans2=nqueenPermutaions(box,4,"");
        // System.out.println(ans2);

        // System.out.println("==================");

        int n=4;
        int m=4;
        box=new boolean[n][m];
        rowA=new boolean[n];
        colA=new boolean[m];
        diagA=new boolean[n+m-1];
        adiagA=new boolean[n+m-1];
      //  int ans3=Nqueen_fast(n,m,8,"",0);
       // System.out.println(ans3);

    //    int ans4=Nqueen_fast_02(4, 0, 4,"");
    //     System.out.println(ans4);

      int ans5=Nqueen_fast_subs(4,0,0,4,"");
      System.out.println(ans5);  

      System.out.println("================================");
      int ans6=Nqueen_fast_02_bits(8,0,8,"");
      System.out.println(ans6);

    }

    public static boolean wbp(String input, int i){
        if(i==input.length()){
            return true;
        }
        StringBuilder sb=new StringBuilder();
        
        boolean f=false;
        while(i<input.length()){
            sb.append(input.charAt(i));
            if(dictionary.contains(sb.toString())){
                f=wbp(input,i+1);
                if(f){
                    return true;
                }
            }
            i++;
        }
        return f;
    }

    public static int wbp_02(String input, int i, String ans){
        if(i==input.length()){
            System.out.println(ans);
            return 1;
        }
        
        int count=0;
        String str="";
        for(int j=i; j<input.length(); j++){
            str=input.substring(i,j+1);
            if(dictionary.contains(str)){
                count+=wbp_02(input, j+1, ans+str+" ");
            }
        }
        return count;
    }

    //static ArrayList<String> dictionary;
    static HashSet<String> dictionary;
    public static void WordBreakProblem(){
            //         Consider the following dictionary 
            // { i, like, sam, sung, samsung, mobile, ice, 
            //   cream, icecream, man, go, mango}
            dictionary=new HashSet<>();
        dictionary.add("i");
        dictionary.add("like");
        dictionary.add("sam");
        dictionary.add("sung");
        dictionary.add("samsung");
        dictionary.add("mobile");
        dictionary.add("ice");
        dictionary.add("cream");
        dictionary.add("icecream");        
        dictionary.add("man");
        dictionary.add("go");
        dictionary.add("mango");
        dictionary.add("and");        
        dictionary.add("ilike");
        String input="ilikesamsungandmango";
        boolean f=wbp(input,0);
        System.out.println(f);

        int ans=wbp_02(input,0,"");    
        System.out.println(ans);    
    }

    public static void solve(){
        //QueenCombination_Permutation();
        NQueens();
        //WordBreakProblem(); 
    }
    public static void main(String[] args) {
        solve();
    }
}