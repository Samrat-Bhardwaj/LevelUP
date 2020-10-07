#include<vector>
#include<iostream>
#include<stack>
#include<bits/stdc++.h>
using namespace std;


class Node{
    public:
    int data;
    Node* left=nullptr;
    Node* right=nullptr;

    Node(int data){
        this->data=data;
    }
};

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

// leet 98================================================================================
TreeNode* prev_=nullptr;
    bool isValidBST(TreeNode* root) {
        if(root==nullptr) return true;
        
        if(!isValidBST(root->left)) return false;
        
        if(prev_==nullptr) prev_=root;
        else if(prev_->val >= root->val) return false;
        prev_=root;
        
        if(!isValidBST(root->right)) return false;
        
        return true;
}
// leet some question ====================================================================

Node* insert(Node* root, int val){
    if(root==nullptr) return new Node(val);

    if(val<root->data){
         root->left=insert(root->left,val);
    } else {
        root->right=insert(root->right,val);
    }

    return root;
}

// leet 450 ==========================================================================
int getMax(TreeNode* root){
        int ans=root->val;
        while(root->right!=nullptr){
            ans=root->right->val;
            root=root->right;
        }
        return ans;
}

TreeNode* deleteNode(TreeNode* root, int key) {
        if(root==nullptr) return nullptr;
        
        if(key < root->val){
            root->left=deleteNode(root->left,key);
        } else if(key > root->val){
            root->right=deleteNode(root->right,key);
        } else { // isme tino case h ki agr ek hi bacha hua, ek bhi bacha nhi hua 
            if(root->right==nullptr || root->left==nullptr){
                return root->left==nullptr ? root->right : root->left;
            } else {
                int nKey=getMax(root->left); // ab left subtree ka max hi apna successor hoga
                root->val=nKey; // change kiya, phir delete krva liya
                root->left=deleteNode(root->left,nKey); 
            }
        }
        return root;
}

// question on AVL == leet 1382 ======================================================================
class Solution {
    
int getHeight(TreeNode* A){
    return A==nullptr ? -1 : max(getHeight(A->left), getHeight(A->right))+1; 
}    
int getBal(TreeNode* A){
    int lh=getHeight(A->left);
    int rh=getHeight(A->right);
    
    return lh-rh;
}    
TreeNode* rightRotation(TreeNode* A){
    TreeNode* B=A->left;
    TreeNode* BKaRight=B->right;

    B->right=A;
    A->left=BKaRight;
//jate jate phir check kiya ki right balance sahi to h na 
    B->right=getRotation(A);
// right thik hone k baf m to balane hu na
    return getRotation(B);
}

TreeNode* leftRotation(TreeNode* A){
    TreeNode* B=A->right;
    TreeNode* BkaLeft=B->left;

    B->left=A;
    A->right=BkaLeft;


    //jate jate phir check kiya ki left sahi to h na 
    B->left=getRotation(A);
    // phir left ko sahi krke m to sahi hu na
    return getRotation(B);
}

TreeNode* getRotation(TreeNode* root){
    

    if(getBal(root)>=2){  //ll,lr
        if(getBal(root->left)>=1){
            return rightRotation(root);
        } else {
            root->left=leftRotation(root->left);
            return rightRotation(root);
        }
    } else if(getBal(root) <= -2){
        if(getBal(root->right) >=1){
            root->right=rightRotation(root->right);
            return leftRotation(root);
        } else {
            return leftRotation(root);
        }
    }
    return root;
}
public:
    TreeNode* balanceBST(TreeNode* root) {
        if(root==nullptr) return root;
        // har point p jake check kiya
        root->left=balanceBST(root->left);
        root->right=balanceBST(root->right);
        
        // root update krte rahe
        root=getRotation(root);
        return root;
    }
};

// leet 173 =============================================================================

// iteratively

class BSTIterator {
public:
    stack<TreeNode*>st;
    BSTIterator(TreeNode* root) {
        
        while(root!=nullptr) {
            st.push(root);
            root=root->left;
        }
    }
    
    /** @return the next smallest number */
    int next() {
        TreeNode* rn=st.top();
        int rv=rn->val;
        st.pop();
        rn=rn->right;
        while(rn!=nullptr){
            st.push(rn);
            rn=rn->left;
        }
        return rv;
    }
    
    /** @return whether we have a next smallest number */
    bool hasNext() {
        return st.size()!=0;
    }
};

// BSTree from pre leet 1008 ===========================================================================
int i=0;
TreeNode* makeTree(vector<int>& preorder, int bound){
    if(i>=preorder.size() || preorder[i]> bound) return nullptr;

    TreeNode* root=new TreeNode(preorder[i++]);
    root->left=makeTree(preorder,preorder[i-1]);
    root->right=makeTree(preorder,bound);

    return root;
}

TreeNode* bstFromPreorder(vector<int>& preorder) {
    return makeTree(preorder,INT_MIN); //bound        
}
//BST from postorder ================================================================================
int idx=0;
TreeNode* makeTree(vector<int>& postorder,int lbound, int rbound){
    
}
TreeNode* bstFromPostorder(vector<int>& postorder){
    idx=postorder.size()-1;
    return makeTree(postorder,INT_MIN,INT_MAX); //bound        
}

// leet 230======================================================================================
void addAllLeft(TreeNode* root, stack<TreeNode*>& st){
        while(root!=nullptr){
            st.push(root);
            root=root->left;
        }
}

int kthSmallest(TreeNode* root, int k) {
        stack<TreeNode*> st;
        addAllLeft(root,st);
        
        while(--k>0){
            TreeNode* node=st.top();
            st.pop();
            
            addAllLeft(node->right,st);
        }
        return st.top()->val;
}

// leet 653 =======================================================================

// two pointer in sorted array to findTarget ====================
 bool findTarget(TreeNode* root, int k) {
        stack<TreeNode*> sl,sr;
        
        TreeNode* curr=root;
        while(curr!=nullptr){
            sl.push(curr);
            curr=curr->left;
        }
        curr=root;
        while(curr!=nullptr){
            sr.push(curr);
            curr=curr->right;
        }
        while(sl.size() && sr.size()){
             int s=sl.top()->val+sr.top()->val;
            if(s==k && sl.top()!=sr.top()) return true; // the nodes should not be same
           
            if(s>k){  // sum is more than k to j--;
                TreeNode* tr=sr.top();
                sr.pop();    
                tr=tr->left;
                while(tr!=nullptr){
                    sr.push(tr);
                    tr=tr->right;
                }
            } else { // sum is less than k to i--;
                 TreeNode* tr=sl.top();
                sl.pop();
                tr=tr->right;
                while(tr!=nullptr){
                    sl.push(tr);
                    tr=tr->left;
                }   
            }
        }
        return false;
}

int main(){
    return 0;
}