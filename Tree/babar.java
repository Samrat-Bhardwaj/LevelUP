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
