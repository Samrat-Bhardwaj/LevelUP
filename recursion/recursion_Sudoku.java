import java.util.Arrays;

public class recursion_Sudoku {

    public static void print_su(int[][] matrix) {
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[0].length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean isNumSafe(int[][] matrix, int r, int c, int num){
        //for row;
        for(int i=0; i<9; i++){
            if(matrix[i][c]==num) return false;
        }
        //for col
        for(int i=0; i<9; i++){
            if(matrix[r][i]==num) return false;
        }
        //for matrix
        r=(r/3)*3;
        c=(c/3)*3;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(matrix[i+r][j+c]==num) return false;
            }
        }
        return true;
     }

    public static boolean solve_su(int[][] matrix,int[] loc, int i){
        if(i==loc.length){
            return true;
        }
        int j=loc[i];
        int r=j/9;
        int c=j%9;

        for(int n=1; n<=9; n++){
            if(isNumSafe(matrix,r,c,n)){
                matrix[r][c]=n;
                if(solve_su(matrix, loc, i+1)) return true;
                matrix[r][c]=0;
            }
        }
        return false;
    }

    static int[] rowA=new int[10];
    static int[] colA=new int[10];
    static int[][] matrix=new int[3][3];
    public static boolean solve_su_bit(int[][] matrix,int[] loc, int k){
        if(k==loc.length){
            return true;
        }
        int z=loc[k];
        int i=z/9;
        int j=z%9;

        for(int n=1; n<=9; n++){
            int mask=(1<<n);
            if(((rowA[i] & mask)==0) && ((colA[j] & mask)==0) && ((matrix[i/3][j/3] & mask)==0)){
                rowA[i]^=mask;
                 colA[j]^=mask;
                matrix[i][j]=n;
                if(solve_su_bit(matrix, loc, k+1)) return true;
                matrix[i][j]=0;
                rowA[i]^=mask;
                colA[j]^=mask;
            }
        }
        return false;
    }

    public static void normal_sudoku(int[][] matrix, int count){
        int[] loc=new int[81-count];
        int k=0,z=0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(matrix[i][j]==0){
                    loc[k]=z;
                    k++;
                }
                z++;
            }
        }
        //boolean f=solve_su(matrix,loc,0);
        boolean s=solve_su_bit(matrix,loc,0);
        print_su(matrix);
        System.out.println(s);
    }

    static boolean[]rowS,colS;
    public static void sudokuSolver_utility(){
        rowS=new boolean[10];
        colS=new boolean[10];
        int[][] matrix={{3, 0, 6, 5, 0, 8, 4, 0, 0}, 
        {5, 2, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 8, 7, 0, 0, 0, 0, 3, 1}, 
        {0, 0, 3, 0, 1, 0, 0, 8, 0}, 
        {9, 0, 0, 8, 6, 3, 0, 0, 5}, 
        {0, 5, 0, 0, 9, 0, 6, 0, 0}, 
        {1, 3, 0, 0, 0, 0, 2, 5, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 7, 4}, 
        {0, 0, 5, 2, 0, 6, 3, 0, 0}};
       
        int count=0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(matrix[i][j]!=0){
                    rowS[matrix[i][j]]=colS[matrix[i][j]]=true;
                    count++;
                }
            }
        }
        normal_sudoku(matrix,count);
    }
//============================= CryptArithmetic Problem (send more money) =======================

    public static int getNum(String str){
        int ans=0;
       int j=1;
        for(int i=str.length()-1; i>=0; i--){
            char ch=str.charAt(i);
            int num=wo[ch-'a'];
            ans+=num*j;
            j*=10;
        }
        return ans;
    }
    public static int Crypto(String str, int i){
        if(i==str.length()){
            int x=getNum(s1);
            int y=getNum(s2);
            int z=getNum(s3);
            if(x+y==z){
               // String ans=" "+x+" +" +y + " ="+ z;
                System.out.println(" "+x);
                System.out.println("+"+y);
                System.out.println("-------");
                System.out.println(z);
                System.out.println("======================");
                return 1;
            } else {
                return 0;
            }
        }
        char ch=str.charAt(i);
        int count=0;            
        for(int n=0; n<=9; n++){
            int mask=1<<n;
            if((numUsed&mask)==0){
                numUsed^=mask;
                wo[ch-'a']=n;

                count+=Crypto(str,i+1);

                numUsed^=mask;
                wo[ch-'a']=-1;
            }
        }
        return count;
    }

    static String s1="send";
    static String s2="more";
    static String s3="money";
    static int wo[]=new int[26];
    static int numUsed=0;
    public static void CryptArithmetic(){
        String str=s1+s2+s3;
        int bit=1;
        for(int i=0; i<str.length(); i++){
            char ch=str.charAt(i);
            int mask=(1<<(ch-'a'));
            bit|=mask;
        }
        str="";
        for(int i=1; i<32; i++){
            int mask=1<<i;
            if((bit&mask)!=0){
                str+=(char)(i+'a');
            }
        }
        
        System.out.println(str);
        Arrays.fill(wo,-1);
        int ans=Crypto(str,0);
        System.out.println(ans);
    }
// ================= Cross Word =======================

    public static void print(char[][] box){
        for(int i=0; i<box.length; i++){
            for(int j=0; j<box[0].length; j++){
                System.out.print(box[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static boolean canPlaceH(String wo, int r, int c){
        for(int i=0; i<wo.length(); i++){
            if(c+i >= box[0].length) return false;

            if(box[r][c+i]!='-' && box[r][c+i]!=wo.charAt(i)) return false;
        }
        return true;
    }

    public static int placeWordH(String wo, int r, int c){
        int bit=0;
        for(int i=0; i<wo.length(); i++){
            if(box[r][c+i]=='-'){
                int mask=(1<<i);
                bit|=mask;
                box[r][c+i]=wo.charAt(i);
            }
        }
        return bit;
    }

    public static void unPlace_WordH(int bit, String wo,int r, int c){
        for(int i=0; i<wo.length(); i++){
            int mask=(1<<i);
            if((bit & mask)!=0){
                box[r][c+i]='-';
            }
        }
    }

    public static boolean canPlaceV(String wo, int r, int c){
        for(int i=0; i<wo.length(); i++){
            if(r+i >= box.length) return false;

            if(box[r+i][c]!='-' && box[r+i][c]!=wo.charAt(i)) return false;
        }
        return true;
    }

    public static int placeWordV(String wo, int r, int c){
        int bit=0;
        for(int i=0; i<wo.length(); i++){
            if(box[r+i][c]=='-'){
                int mask=(1<<i);
                bit|=mask;
                box[r+i][c]=wo.charAt(i);
            }
        }
        return bit;
    }

    public static void unPlace_WordV(int bit, String wo,int r, int c){
        for(int i=0; i<wo.length(); i++){
            int mask=(1 << i);
            if((bit & mask)!=0){
                box[r+i][c]='-';
            }
        }
    }

    public static boolean CrossWord_solve(String[] wor, int k){
        if(k==wor.length){
            return true;
        }
        String wo=wor[k];
        for(int i=0; i<box.length; i++){
            for(int j=0; j<box[0].length; j++){
                if(box[i][j]=='-' || box[i][j]==wo.charAt(0)){
                    if(canPlaceH(wo,i,j)){
                        int b=placeWordH(wo,i,j);// we need this bit so that we can unplace only those  
                        if(CrossWord_solve(wor,k+1)) return true; // char that we removed
                        unPlace_WordH(b,wo,i,j);
                    }
                    if(canPlaceV(wo,i,j)){
                        int b=placeWordV(wo,i,j);
                        if(CrossWord_solve(wor,k+1)) return true;
                        unPlace_WordV(b,wo,i,j);
                    }
                }
            }
        }
        return false;
    } 

    static char[][] box={
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '-', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '+', '+', '+', '+', '+'},
        {'+', '-', '-', '-', '-', '-', '-', '+', '+', '+'},
        {'+', '-', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '-', '+', '+', '+', '+'},
        {'+', '+', '+', '+', '+', '+', '+', '+', '+', '+'}};;
    public static void CrossWord(){
        String[] wo={"agra", "norway", "england", "gwalior"};
        boolean f=CrossWord_solve(wo,0);
        System.out.println(f);
        print(box);
    }

    public static void solve() {
        sudokuSolver_utility();
        //CryptArithmetic();  
        //CrossWord();        
    }
    public static void main(String[] args) {
        solve();
    }
}
