public class questions{
    public class ListNode {
             int val;
            ListNode next;
            ListNode() {}
            ListNode(int val) { this.val = val; }
           ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode() {}
             TreeNode(int val) { this.val = val; }
             TreeNode(int val, TreeNode left, TreeNode right) {
                 this.val = val;
                 this.left = left;
                 this.right = right;
    }
}
// good question, worth seeing once 
// leet 109 ======================================================================================

public static ListNode getMid(ListNode head){
    ListNode temp=head;
    ListNode fast=head;
    while(fast.next!=null && fast.next.next!=null){
        temp=temp.next;
        fast=fast.next.next;
    }
    return temp;
}

public static TreeNode make(ListNode h){
    if(h==null) return null;
    
    ListNode m=getMid(h);
    TreeNode root=new TreeNode(m.val);
    if(m==h){    // when there is only one or two nodes in linked list   
        TreeNode r=null;
        if(m.next!=null){
            r=new TreeNode(m.next.val);
        }
        root.right=r;
        return root;
    } 
    ListNode temp=h;
    while(temp.next!=m){  //creating first half linkedlist
        temp=temp.next;
    }
    temp.next=null;      // removing link with middle one
    
    ListNode e=m.next;
    m.next=null;        // removing link of midlle with next one
    root.right=make(e);  // recursive calls
    root.left=make(h);
    
    return root;
}

public static TreeNode sortedListToBST(ListNode head) {
    if(head==null) return null;
    
    return make(head);
}

public static void solve(){
        ListNode head=new ListNode(-10);
        int i=5;
        int j=-9;
        ListNode temp=head;
        while(j!=i){
            temp.next=new ListNode(j);
            j++;
        }
        TreeNode root=sortedListToBST(head);    
}
    public static void main(String[] args) {
        solve();
    }
}