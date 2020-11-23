// bst ka ques ko array k ques ki tarah socho (inorder sorted)
public class babar {
    class Node
{
	Node left, right;
	int data;
	
	Node(int d)
	{
		data = d;
		left = right = null;
	}
	
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

// hourse robber-3 (ek ghar rob kro to uska next nhi kr skte) =======================================

// noice approach =======

/* naive ==============================================================

    unordered_map<TreeNode*,int> t;
    unordered_map<TreeNode*,int> f;
    int getMax(TreeNode* root, bool fetch){
        if(root==nullptr) return 0;
        
        int ans=-1;
        if(fetch){
            if(t.find(root)!=t.end())
                return t[root];
        } else {
            if(f.find(root)!=f.end()){
                return f[root];
            }
        }
        
        if(!fetch){
            int temp=getMax(root->left,true)+getMax(root->right,true) + root->val;
            temp=max(temp,getMax(root->right,false)+getMax(root->left,false));
            ans=max(ans,temp);
        } else {
            ans=max(ans,getMax(root->right,false)+getMax(root->left,false));
        }
        if(fetch){
            t[root]=ans;
        } else {
            f[root]=ans;
        }
        
        return ans;
    }
    
    int rob(TreeNode* root) {
        int ans=getMax(root,true);
        ans=max(ans,getMax(root,false));
        
        return ans;
    }

*/

// now rather than calling function two times with true n false, we can just call it 1 time using array
// of two size 

public int[] getMax(TreeNode root){
    if(root==null) return new int[]{0,0};
   
   int[] l=getMax(root.left);
   int[] r=getMax(root.right);
   
   // ye vala robbing, iske bache nhi rob krenge;
   int rob=root.val + l[1] + r[1];
   
   // not robbing ye vala, niche vala rob krna na krna marji hamari;
   int notRob=Math.max(l[0],l[1]) + Math.max(r[0],r[1]);
   
   return new int[]{rob,notRob};
}

public int rob(TreeNode root) {
   int ans[]=getMax(root);
   return Math.max(ans[0],ans[1]);
}
// binary tree to DLL

// https://practice.geeksforgeeks.org/problems/binary-tree-to-dll/1

Node head=null,prev=null;
    Node bToDLL(Node root)
    {
        head=null;prev=null;
	    makeList(root);
	    return head;
    }
    
    void makeList(Node root){
        if(root==null) return ;
        
        makeList(root.left);
        if(prev==null){
            head=root;
        }else{
        root.left=prev;
        prev.right=root;
        }
        prev=root;
        makeList(root.right);
    }

    // https://practice.geeksforgeeks.org/problems/transform-to-sum-tree/1# ========================
    // updating sum on our way
    int getSum(Node* node){
        if(node==nullptr) return 0;
        
        int l=getSum(node->left);
        int r=getSum(node->right);
        
        int temp=node->data;
        
        node->data=l+r;
        
        return l+r+temp;
    }
    void toSumTree(Node *node)
    {
        int l=getSum(node->left);
        int r=getSum(node->right);
        
        node->data=l+r;
        //or simply just
        //getSum(node);
    }
}
