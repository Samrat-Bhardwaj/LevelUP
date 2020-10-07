public class REV1 {
    static int row[]=new int[10];
    static int col[]=new int[10];
    static int mat[][]=new int[3][3];
    public static boolean sudoku(int[][]matrix, int[] loc, int l){
        if(l==loc.length){
            return true;
        }
        int z=loc[l];
        int i=z/9;
        int j=z%9;

        for(int k=1; k<=9; k++){
            int mask=(1 << k);
            if(((row[i] & mask)==0) && ((col[j] & mask) ==0) && ((mat[i/3][j/3] & mask)==0)){
                row[i]^=mask;
                col[j]^=mask;
                mat[i/3][j/3]^=mask;
                matrix[i][j]=k;

                if(sudoku(matrix,loc,l+1)) return true;

                row[i]^=mask;
                col[j]^=mask;
                mat[i/3][j/3]^=mask;
                matrix[i][j]=0;
            } 
        }
        return false;
    }
    
    public static void solve_sudoku(){
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
                if(matrix[i][j]==0){
                    count++;
                }
            }
        }
        
        int loc[]=new int[count];
        int k=0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(matrix[i][j]==0){
                    loc[k]=(9*i )+ j;
                    k++;
                } else {
                    int mask= 1 << matrix[i][j];
                    row[i]^=mask;
                    col[j]^=mask;
                    mat[i/3][j/3]^=mask;
                }
            }
        }

        sudoku(matrix,loc,0);

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void solve(){
       solve_sudoku(); 
    }

    public static void main(String[] args) {
        solve();
    }
}
