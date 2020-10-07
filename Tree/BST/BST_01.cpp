#include<vector>
#include<iostream>
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

Node* construct_Tree(vector<int>& arr, int si, int ei){
    if(si>ei) return nullptr;
    if(si==ei) return new Node(arr[si]);

    int m=(si+ei)/2;
    Node* root=new Node(arr[m]);
    root->left=construct_Tree(arr,si,m-1);
    root->right=construct_Tree(arr,m+1,ei);

    return root;
}

void display(Node* root){
    if(root==nullptr) return;

    string s="";
    s+= root->left!=nullptr ? to_string(root->left->data)  : "."; 
    s+="<- " + to_string(root->data) + " ->";
    s+= root->right!=nullptr ? to_string(root->right->data)  : ".";

    cout<<s<<"\n";

    display(root->left);
    display(root->right); 
}

// leet 1008 make Tree from preOrder ================================================================
// O(n2) method
TreeNode* attachNext(TreeNode* root, int data){
        if(!root){
            TreeNode* n=new TreeNode(data);
            return n;
        }
        if(data>root->val){
            root->right=attachNext(root->right,data);
        } else if(data<root->val){
            root->left=attachNext(root->left,data);
        }
       
        return root;
}

TreeNode* bstFromPreorder(vector<int>& pre) {
        TreeNode* root=new TreeNode(pre[0]);
        for(int i=1; i<pre.size(); i++){
            root=attachNext(root,pre[i]);  // takin one element and attaching - O(n2);
        }
        return root;
}
// o(n) method =====
int i=0; // static int for iteration;
TreeNode* makeTree(vector<int>& preorder, int bound){
    if(i>=preorder.size() || preorder[i]> bound) return nullptr;

    TreeNode* root=new TreeNode(preorder[i++]);
    root->left=makeTree(preorder,preorder[i-1]);  // bound for left tree will be root->val
    root->right=makeTree(preorder,bound); //bound for right subtree will be max

    return root;
}

TreeNode* bstFromPreorder(vector<int>& preorder) {
    return makeTree(preorder,INT_MAX); //bound=isse jada value ka tum value is tree m nhi dal skte        
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


// predecessor and successor ===============================================================
 

// in case of binary tree predecesoor= floor and successor=ceil ===========================

void allSolPair(Node* root, int data){
    Node* pre=nullptr;
    Node* succ=nullptr;
    // dry run
    while(root!=nullptr){
        if(root->data == data){
            if(root->left!=nullptr){
                pre=root->left;
                while(pre->right!=nullptr){
                    pre=pre->right;
                }
            }
            if(root->right!=nullptr){
                succ=root->right;
                while(succ->left!=nullptr){
                    succ=succ->left;
                }
            }
            break;
        } else if(root->data < data){
            pre=root;
            root=root->right;
        } else {
            succ=root;
            root=root->left;
        }
    }
}

void questions(){

}

void solve(){
    vector<int> arr={10,20,30,40,50,60,70,80};
    Node* root=construct_Tree(arr,0,arr.size()-1);
    display(root);

    questions();
}

int main(){
    solve();
    return 0;
}