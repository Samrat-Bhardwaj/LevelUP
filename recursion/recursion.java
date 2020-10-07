public class recursion {
    static String[] co = {":;/","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz","&*","+-"};

    public static void keyp(String str, int i, String ans){
        if(i==str.length()){
            System.out.print(ans+" ");
            return;
        }
        int chi=Integer.parseInt(str.charAt(i)+"");
        String wor=co[chi];
        for(int j=0; j<wor.length(); j++){
            keyp(str,i+1,ans+wor.charAt(j));
        }    
    }
    public static void solve(){
        
        String str="34";
        keyp(str,0,"");
    }
    public static void main(String[] args) {
        solve();
    }
}