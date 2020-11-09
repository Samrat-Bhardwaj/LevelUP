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

// leet 84 ===================================================================

public int largestRectangleArea(int[] heights) {
    Stack<Integer> st=new Stack<>();
    int n=heights.length;
    st.push(-1);
    int ans=0;
    for(int i=0; i<n; i++){
        while(st.peek()!=-1 && heights[st.peek()] >= heights[i]){
            int h=heights[st.peek()];
            st.pop();
            int w=i-st.peek()-1;
            ans=Math.max(ans,h*w);
            
        }
        // System.out.print(ans+" ");
        st.push(i);
    }
    
    while(st.peek()!=-1){
        int h=heights[st.peek()];
        st.pop();
        int w=n-st.peek()-1;
        ans=Math.max(ans,h*w);
        
    }
    
    return ans;
}


// leet 42====================================================================================

// trapping rain water =========================

public int trap(int[] height) {
    int li=0;
     int n=height.length;
     int ri=n-1;
    int lmax=0;
     int rmax=0;
     int ans=0;
     while(li<ri){
         lmax=Math.max(lmax,height[li]);
         rmax=Math.max(rmax,height[ri]);
         if(lmax<=rmax){
             ans+=lmax-height[li];
             li++;
         } else {
             ans+=rmax-height[ri];
             ri--;
         }
     }
     return ans;
}

// leet 316,1081 =================================================================
// lexiographically smallest subsequence without repeating chars
public String removeDuplicateLetters(String s) {
    int[] freq=new int[26];
    boolean[] seen=new boolean[26];
    
    for(int i=0; i<s.length(); i++){
        char c=s.charAt(i);
        freq[c-'a']++;
    }
    
    Stack<Character> st=new Stack<>();
    int n=s.length();
    for(int i=0; i<n; i++){
        char ch=s.charAt(i);
        
        freq[ch-'a']--;
        if(seen[ch-'a']){
            continue;
        }
        
        while(st.size()>0 && st.peek()>ch && freq[st.peek()-'a'] > 0){
            // making our string smallest(st.peek()>ch), we'll remove only when we are sure we'll
            // get this character again in further string(freq[st.peek()-'a'] > 0).
            seen[st.peek()-'a']=false;
            st.pop();
        }
        seen[ch-'a']=true;
        st.push(ch);
    }
    
    StringBuilder sb=new StringBuilder();
    while(st.size()>0){
        sb.append(st.pop());
    }
    sb.reverse();
    return sb.toString();
}

// leet 155=================================================================

// min stack, top p min rahna chahiye ......

// app-> normal stack, ek min so far rakha, aur minimum element aane p kch aur dalvaya ek equation k
// according aur agr vo min vapis nikalenge to usi equation k according min so far vapis update kr lenge 
// previous vale k

class MinStack {
    Stack<Long> st;// long bcoz int max kisi kisi cases m
    long msf;
    /** initialize your data structure here. */
    public MinStack() {
        st=new Stack<>();
        msf=Long.MAX_VALUE;
    }
    
    public void push(int x) {
        if(st.size()==0){
            msf=(long)x;
            st.push((long)x);
            return;
        }
        long prev=msf; // prev min so far
        msf=Math.min(msf,x);
        
        if(prev>msf){
            st.push((x-prev)+x); // updating befor pushing
        } else {
            st.push((long)x);
        }
    }
    
    public void pop() {
        if(st.peek()>msf){
            st.pop();
        } else {
            msf=(msf-st.peek())+msf; // updating our msf before popping
            st.pop();
        }
    }
    
    public int top() {
        if(st.peek()>msf){
            return st.peek().intValue(); // if we use "Long" we us.intValue else (int) will do
        } else {
            return (int)msf;
        }
    }
    
    public int getMin() {
        return (int)msf;
    }
}


// leet  ===================================================================

// decode string -> 3[a]2[bc] - > aaabcbc 

public String decodeString(String s) {
    Stack<String> st=new Stack<>();
    for(int i=0; i<s.length(); i++){
        if(s.charAt(i)==']'){
            String sb="";
            while(!st.peek().equals("[")){
                sb=st.pop()+sb;
            }
            st.pop();
            int j=Integer.parseInt(st.pop());
            String z=sb;
            while(j-->1){
                sb+=z;
            }
            //System.out.println(sb);
            st.push(sb);
        } else {
            if(Character.isDigit(s.charAt(i))){
                String str="";
                while(s.charAt(i)!='['){
                    //System.out.println(s.charAt(i));
                    str+=s.charAt(i);
                    i++;
                }
                i--;
                st.push(str);
            } else {
            st.push(s.charAt(i)+"");
            }
        }
    }
    String ans="";
    while(st.size()!=0){
        String temp=st.pop();
        ans=temp+ans;
    }
    return ans;
}

// leet 901 ===============================================================

class StockSpanner {
    Stack<Integer> st;
    Stack<Integer> temp;
    int c;
   public StockSpanner() {
       c=0;
       st=new Stack<>();
        temp=new Stack<>();
   }
   
   public int next(int price) {
       int i=0;
       while(!st.isEmpty() && st.peek()<=price){
           st.pop();
           i+=temp.pop();
       }
       st.push(price); 
       temp.push(i+1);
       return i+1;
   }
}

// ques ->  =================================================================================================

// how to make a stack in which we can get middle element or delete middle element in O(1);


// by using a doubly linked list as stack and maintaining a mid pointer which can go forw and backwards

// *************** important ************************// 

// how to implement k stacks using one array ===========


// https://www.geeksforgeeks.org/efficiently-implement-k-stacks-single-array/ 



}