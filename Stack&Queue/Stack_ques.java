import java.util.Stack;
import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;

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
// same ques for queue

// https://www.geeksforgeeks.org/efficiently-implement-k-stacks-single-array/ -> same for queue


// celebrity problem in O(n) ================================= 

//https://practice.geeksforgeeks.org/problems/the-celebrity-problem/1#

int celebrity(int M[][], int n){
    Stack<Integer> st=new Stack<>();
    
    for(int i=0; i<n; i++) st.push(i);
    
    while(st.size()>1){ // O(n) m possible answer upar aa jayega stack k
        int a=st.pop();
        int b=st.pop();
        
        if(M[a][b]==1){
            st.push(b);
        } else if(M[b][a]==1){
            st.push(a);
        }
    }
    
    if(st.size()==0) return -1; // stack empty to no celebrity
    
    int num=st.pop(); // possible answer
    
    for(int i=0; i<n; i++){ // checking ki num ko sab jante h, vrna return -1;
        if(i==num) continue;
        if(M[i][num]==0) return -1;
    }
    
    for(int j=0; j<n; j++){ // checking ki num kisiko nhi janta
        if(num==j) continue;
        if(M[num][j]==1) return -1;
    }

    return num;
}


// infix evaluation ===========================================================

//solve -> (2+4)*(8/2);
public static int ieo(String str){
    Stack <Integer> s1= new Stack <> ();
    Stack <Character> s2= new Stack <> ();
    for(int i=0; i<str.length(); i++){
        char c=str.charAt(i);
        if(c>='0' && c<='9'){
            s1.push(c-'0');
        } else if(c=='('){
            s2.push(c);
        } 
        else if(c==')'){
            while(!s1.empty() && s2.peek()!='('){
                char op=s2.pop();
                int o2=s1.pop();
                int o1=s1.pop();
                s1.push(solve(o1,o2,op));
            }
            s2.pop();
        } else if(c=='+' || c=='-' || c=='*' || c=='/'){
            while(!s2.empty() && s2.peek()!='(' && prec(c)<=prec(s2.peek())){
                char op=s2.pop();
                int o2=s1.pop();
                int o1=s1.pop();
                s1.push(solve(o1,o2,op));
            }
            s2.push(c);
        }
    }
    
    while(!s2.empty() ){
      char op=s2.pop();
                int o2=s1.pop();
                int o1=s1.pop();
                s1.push(solve(o1,o2,op));  
    }
    
    return s1.pop();
 }
 
public static int solve(int o1, int o2, char op){
      if(op == '+'){
      return o1 + o2;
    } else if(op == '-'){
      return o1 - o2;
    } else if(op == '*'){
      return o1 * o2;
    } else {
      return o1 / o2;
    }
} 
 
 public static int prec(char c){
    if(c=='/' || c=='*'){
        return 2; 
    } else return 1;
}

// postfix evaluation =============================================================

// leet 150 ======

public static int solve_(int a, int b, char ch){
    if(ch=='+'){
        return a+b;
    } else if(ch=='-'){
        return a-b;
    } else if(ch=='*'){
        return a*b;
    } else {
        return a/b;
    }
}
public int evalRPN(String[] tokens) {
    Stack<Integer> st1=new Stack<>();
    
    for(int i=0; i<tokens.length; i++){
        String ch=tokens[i];
        
        if(ch.equals("+") || ch.equals("-") || ch.equals("*") || ch.equals("/")){
            int b=st1.pop();// it is important ki pahle vala piche aaye opeartor k
            int a=st1.pop();
            int c=solve_(a,b,ch.charAt(0));
            st1.push(c);
        } else {
            st1.push(Integer.parseInt(ch));
        }
    }
    return st1.peek();
}

// sort stack using recursion ======================================================================

static void sortedInsert(Stack<Integer> s, int x) 
    { 
        // Base case: Either stack is empty or newly 
        // inserted item is greater than top (more than all 
        // existing) 
        if (s.isEmpty() || x > s.peek())  
        { 
            s.push(x); 
            return; 
        } 
  
        // If top is greater, remove the top item and recur 
        int temp = s.pop(); 
        sortedInsert(s, x); 
  
        // Put back the top item removed earlier 
        s.push(temp); 
    } 
  
    // Method to sort stack 
    static void sortStack(Stack<Integer> s) 
    { 
        // If stack is not empty 
        if (!s.isEmpty())  
        { 
            // Remove the top item 
            int x = s.pop(); 
  
            // Sort remaining stack 
            sortStack(s); 
  
            // Push the top item back in sorted stack 
            sortedInsert(s, x); 
        } 
    } 

// leet 56 =======================================================================================

// merge intervals =======================================

public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals,(a,b)->{
       if(a[0]==b[0]) return a[1]-b[1];
        return a[0]-b[0];
    });
    
    Stack<Integer> st=new Stack<>();
    int n=intervals.length;
    
    for(int i=0; i<n; i++){
        if(i==0){
            st.push(intervals[i][0]);
            st.push(intervals[i][1]);
        } else {
            int b=st.peek();
            if(b < intervals[i][0]){
                st.push(intervals[i][0]);
                st.push(intervals[i][1]);
            } else {
                b=Math.max(b,intervals[i][1]);
                st.pop();
                st.push(b);
            }
        }
    }
    
    int s=st.size()/2;
    int[][] ans=new int[s][2];
    
    for(int i=s-1; i>=0; i--){
        ans[i][1]=st.pop();
        ans[i][0]=st.pop();
    }
    
    return ans;
}


// op problem =======================================================================

//LRU cache

public class questionStack{
    class LRUCache
    {
    
        class Node
        {
            int key = 0;
            int value = 0;
    
            Node next = null;
            Node prev = null;
    
            Node(int key, int value)
            {
                this.key = key;
                this.value = value;
            }
        }
    
        Node head = null;
        Node tail = null;
        int size = 0;
        int maxSize = 0;
    
        void addLast(Node node)
        {
            if (this.size == 0)
            {
                this.head = node;
                this.tail = node;
            }
            else
            {
                this.tail.next = node;
                node.prev = this.tail;
                this.tail = node;
            }
            this.size++;
        }
    
        void removeNode(Node node)
        {
    
            if (this.size == 1)
            {
                this.head = node;
                this.tail = node;
            }
            else if (node.prev == null)
            {
                this.head = node.next;
    
                this.head.prev = null;
                node.next = null;
            }
            else if (node.next == null)
            {
                this.tail = node.prev;
    
                this.tail.next = null;
                node.prev = null;
            }
            else
            {
                Node prev = node.prev;
                Node next = node.next;
    
                prev.next = next;
                next.prev = prev;
    
                node.next = null;
                node.prev = null;
            }
            this.size--;
        }
    
        HashMap<Integer, Node> map=new HashMap<>(); // key, node
        LRUCache(int capacity)
        {
            this.maxSize = capacity;
        }
    
        int get(int key)
        {
            if (!map.containsKey(key))
                return -1;
    
            Node node = map.get(key);
            int rv = node.value;
    
            removeNode(node);
            addLast(node);
    
            return rv;
        }
    
        void put(int key, int value)
        {
            if (!map.containsKey(key)){
                Node node = new Node(key, value);
                map.put(key , node);
                addLast(node);
                if(this.size > this.maxSize){
                   node = this.head;
                    
                   map.remove(node.key);
                   removeNode(node);  
                }
            }
            else
            {
                int val = get(key);
                if (val != value)
                    map.get(key).value = value;
            }
        }
    }
}