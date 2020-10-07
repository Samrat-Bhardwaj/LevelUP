import java.util.ArrayList;
public class recursionRevision {
    public static void display(int[] arr, int i){
        if(i>=arr.length) return;

        System.out.println(arr[i]);
        display(arr,i+1);
    }

    public static void DisplayArray(){
        int arr[]={1,24,5,2,3,4,17,19,3,3,7,10};
        display(arr,0);
    }
    
    public static void printInceasingDecreasing(int n){
        if(n<0) return;
        System.out.print(n+" ");
        printInceasingDecreasing(n-1);
        System.out.print(n+" ");
    }

    public static void firstIndex(int[] arr, int key, int i){
        if(arr[i]==key){
            System.out.println(i);
            return;
        }
        firstIndex(arr,key,i+1);
    }

    public static int lastIndex(int[] arr, int key, int i){
        if(i>=arr.length) return -1;
        int ans=-1;
        ans=lastIndex(arr,key,i+1);
        if(ans!=-1) return ans;
        if(arr[i]==key){
            ans=i;
        }
        return ans;
    }

    public static void firstLastIndex(){
        int arr[]={1,24,5,2,3,4,17,19,3,3,7,10};
        firstIndex(arr,3,0);
        int ans=lastIndex(arr,3,0);
        System.out.println(ans);
    }

    public static ArrayList<String> getSubs(String str, int i){
        if(i>=str.length()){
            ArrayList<String> ba=new ArrayList<>();
            ba.add("");
            return ba;
        }
        ArrayList<String> smallAns=getSubs(str, i+1);
        ArrayList<String> ans=new ArrayList<>();

        for(String s: smallAns){
            ans.add(s);
            ans.add(str.charAt(i)+s);
        } 
        return ans;
    }

    public static void getSubsequences(){
        String str="abc";
        ArrayList<String> al=new ArrayList<>();
        al=getSubs(str,0);
        for(String s:al){
            System.out.println(s);
        }
    }

    public static int enco_Rec(String str, int i,String asf){
        if(i==str.length()){
            System.out.println(asf);
            return 1;
        }
         if(str.charAt(i)=='0') return 0;
        int count=0;
        count+=enco_Rec(str,i+1,asf+(char)(str.charAt(i)-'0'+'a'-1)+"");
        // if(i+1<str.length())
        // System.out.print("sa "+ Integer.parseInt(str.substring(i,i+2)));
        if(i+1<str.length() && Integer.parseInt(str.substring(i,i+2))<=26){
            count+=enco_Rec(str,i+2,asf+ (char)((Integer.parseInt(str.substring(i,i+2))) +'a'-1));
        }

        return count;
    }

    public static ArrayList<String> enco(String str, int i){
        if(i==str.length()){
            ArrayList<String> bc=new ArrayList<>();
            bc.add("");
            return bc;   
        }
        ArrayList<String> ans=new ArrayList<>();
        ArrayList<String> sa=enco(str,i+1);
        for(String s:sa){
            ans.add((char)(Integer.parseInt(str.charAt(i)+"")-1+'a')+s);
        }
        if(i+1<str.length() && Integer.parseInt(str.substring(i,i+2))<=26){
            ArrayList<String> sa2=enco(str,i+2);
        for(String s:sa2){
            ans.add((char)(Integer.parseInt(str.substring(i,i+2))-1+'a')+s);
        }   
        }
        return ans;
    }

    public static void Encodings(){
        String str="123";
        int al=enco_Rec(str,0,""); 
        System.out.println(al);

        System.out.println("==================");

        ArrayList<String> ans=enco(str,0);
        for(String s:ans){
            System.out.println(s);
        }
        System.out.println(ans.size());
    }

    public static void solve(){
        //printInceasingDecreasing(10);
        //DisplayArray();
        //firstLastIndex();
        //getSubsequences();
        Encodings();
    }
    public static void main(String[] args) {
        solve();    
    }
}