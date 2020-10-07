#include <iostream>
#include<vector>
using namespace std;

class Node{
public:
    int val=-1;
    Node* right=nullptr;
    Node* left=nullptr;

    Node(int val){
        this->val=val;
    }
};

int i=0;
Node* construct(vector<int>& arr){
    if(i>=arr.size() || arr[i]==-1){
        i++;
        return nullptr;
    }
    Node* n=new Node(arr[i++]);
    n->left=construct(arr);
    n->right=construct(arr);

    return n;
}

void display(Node* root){
    if(root==nullptr) return;

    string s="";
    s+= root->left==nullptr ? "." : to_string(root->left->val);
    s+= "<- " + to_string(root->val) + " ->";
    s+= root->right ==nullptr ? "." : to_string(root->right->val);

    cout<<s<<endl;

    display(root->left);
    display(root->right);
}

int size(Node* root){
    return root==nullptr ? 0 : size(root->left) + size(root->right) + 1;
}

int height(Node* root){
    return root==nullptr ? -1 : max(height(root->left),height(root->right)) + 1;
}

void solve(){
    vector<int> arr={10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
    Node* root=construct(arr);

    display(root);

    cout<<size(root)<<endl;

    cout<<height(root)<<endl;
    getPrenSucc(root,nullptr,80);
}
Node* pre=nullptr;
Node* succ=nullptr;

// predecessor && sucessor ==========================================================
void getPrenSucc(Node* root, Node* previous, int data){
    if(root==nullptr) return;
    
   
    getPrenSucc(root->left,previous,data);
    
    if(root->val==data){
        pre=previous;
    } 

    if(previous->val==data){
        succ=root;
        return;
    }
    previous=root;
    getPrenSucc(root->right,previous,data);
    
}



int main(){
    solve();
    return 0;
}