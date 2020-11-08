import java.util.*;
import java.util.Stack;

public class stack123 {
    public static int[] nextGreaterRight(int[] arr) {
        Stack<Integer> st=new Stack<>();
        int[] ans=new int[arr.length];
        int n=arr.length;
        ans[n-1]=n;
        st.push(n);
        st.push(n-1);
        for(int i=arr.length-1; i>=0; i--){
            int num=arr[i];
            while(st.peek()!=n && arr[st.peek()]<=num){
                st.pop();
            }
            if(st.peek()==n) arr[i]=n;
            else arr[i]=st.peek();
            st.push(i);
        }
        return ans;
    }
    public static void solve(){
        int[]arr={5,6,4,3,2,8,9,7};
        int[] ans=nextGreaterRight(arr);
        for(int ele: ans){
            System.out.print(ele+" ");
        }
    }
    public static void main(String[] args) {
        solve();   
    }
}