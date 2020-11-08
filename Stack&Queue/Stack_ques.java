import java.util.Stack;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.HashSet;

class Stack_ques{

    // leet 1021 ==============================================================

    //bahar vale puri string m jitni bhi bar aaye h hatao
    public String removeOuterParentheses(String S) {
        int temp=0;
        StringBuilder sb=new StringBuilder();
        for(int i=0; i<S.length(); i++){
            if(S.charAt(i)=='(' && temp++>0){
                sb.append(S.charAt(i));
            } else if(S.charAt(i)==')' && temp-->1){                
                 sb.append(S.charAt(i));
            }   
        }
        return sb.toString();
    }

// leet 503 ====

// next greater element in circular array=======

public int[] nextGreaterElements(int[] nums) {
    int n=nums.length;
    int[] ans=new int[n];
    Arrays.fill(ans,-1);
    
    Stack<Integer> st=new Stack<>();
    st.push(-1);
    for(int i=0; i<2*n; i++){
        int idx=i%n;
        while(st.peek()!=-1 && nums[st.peek()] < nums[idx]){
            ans[st.peek()]=nums[idx];
            st.pop();
        }
        if(i<n){
            st.push(i);
        }
    }
    return ans;
}

// leet 1249======================================================================


public String minRemoveToMakeValid(String s) {
    Stack<Integer> st=new Stack<>();
    HashSet<Integer> set=new HashSet<Integer>();
    StringBuilder sb=new StringBuilder();
    for(int i=0; i<s.length(); i++){
        if(s.charAt(i)==')'){
            if(st.size()>0){
                st.pop();
            } else {
                set.add(i);
            }
        }
        if(s.charAt(i)=='('){
            st.push(i);
        }
    }
    
    while(st.size()>0){
        set.add(st.peek());
        st.pop();
    }
    
    for(int i=0; i<s.length(); i++){
        if(!set.contains(i)){
            sb.append(s.charAt(i));
        }
    }
    return sb.toString();
}
}