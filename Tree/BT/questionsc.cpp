#include<iostream>
#include<vector>
using namespace std;

struct TreeNode {
      int val;
      TreeNode *left;
      TreeNode *right;
      TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

struct Node
{
    int data;
    struct Node* left;
    struct Node* right;
    
    Node(int x){
        data = x;
        left = right = NULL;
    }
};

// leet 236 ==================================================================================
bool NodeToRootPath(TreeNode* root, TreeNode* tar, vector<TreeNode*>& ntr){
    if(root==nullptr) return false;
    if(root->val==tar->val){
        ntr.push_back(root);
        return true;
    }

    bool f= NodeToRootPath(root->left,tar,ntr) || NodeToRootPath(root->right,tar,ntr);
    if(f){
        ntr.push_back(root);
        return f;
    }
    return f;
}

TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
    vector<TreeNode*> o;
    NodeToRootPath(root,p,o);
    vector<TreeNode*> t;
    NodeToRootPath(root,q,t);

    int i=o.size()-1;
    int j=t.size()-1;

    if(i<0 || j<0) return nullptr;

    while(i>=0 && j>=0 && o[i]->val==t[j]->val){
        i--;
        j--;
    }
    i++;
    return o[i];
}

//leet 863 ==========================================================================================
void kDown(TreeNode* root, TreeNode* block,int k,vector<int>& ans){
    if(root==NULL || k<0 || root==block) return;

    if(k==0){
        ans.push_back(root->val);
        return;
    }

    kDown(root->left,block,k-1,ans);
    kDown(root->right,block,k-1,ans);
}

int NodeToRoot(TreeNode* root, TreeNode* tar, int k, vector<int>& ans){
    if(root==NULL) return -1;
    if(root->val==tar->val){
        kDown(tar,nullptr,k,ans);  // block will be nothing
        return 1;
    }

    int c=NodeToRoot(root->left,tar,k,ans);
    if(c!=-1){
        kDown(root,root->left,k-c,ans); 
        return c+1;  // aage K-down k liye forward kiya
    }
    int r=NodeToRoot(root->right,tar,k,ans);
    if(r!=-1){
        kDown(root,root->right,k-r,ans); 
        return r+1;   // aage k-down k liye 
    }
    return -1;
}

vector<int> distanceK(TreeNode* root, TreeNode* target, int K) {
    vector<int> ans;
    NodeToRoot(root,target,K,ans);
    return ans;        
}
// leet 543 ===================================================================================
vector<int> dia_02(TreeNode* root){
    if(root==nullptr) return {0,-1};
    
    vector<int> ldh=dia_02(root->left);
        vector<int> rdh=dia_02(root->right);

        int d=max(max(ldh[0],rdh[0]),ldh[1]+rdh[1]+2);
        int h=max(ldh[1],rdh[1])+1;
        return {d,h}; 
}

int diameterOfBinaryTree(TreeNode* root) {
    return dia_02(root)[0];        
}

// leet 112 ==================================================================================
bool some(TreeNode* root, int csum, int sum){
        if(root==nullptr){
            return false;
        }
        csum+=root->val;
        if(root->left==nullptr && root->right==nullptr){
            return csum==sum;
        }
        if(some(root->left,csum,sum)) return true;
        if(some(root->right,csum,sum)) return true;
        
        return false;
}

bool hasPathSum(TreeNode* root, int sum) {
        return some(root,0,sum);
}
// leet 113 ============================================================================
void getAns(TreeNode* root, int sum,vector<vector<int>>& ans,vector<int>& temp){
    if(root==nullptr) return;

    sum-=root->val;
    if(root->right==nullptr && root->left==nullptr){
        if(sum==0){
            temp.push_back(root->val);
            ans.push_back(temp);
            temp.pop_back();
        }
        return;
    }
    temp.push_back(root->val);
    getAns(root->left,sum,ans,temp);
    getAns(root->right,sum,ans,temp);
    temp.pop_back();
}

vector<vector<int>> pathSum(TreeNode* root, int sum) {
    vector<vector<int>> ans;
    vector<int> temp;
    getAns(root,sum,ans,temp);
    return ans;        
}
// https://practice.geeksforgeeks.org/problems/maximum-path-sum/1 =======================================
vector<int> leafToleaf(Node* root){
    if(!root) return {(int)-1e8,(int)-1e8};

    if(root->right==nullptr && root->left==nullptr){
        return {root->data,(int)-1e8};
    }

    vector<int> lf=leafToleaf(root->left);
    vector<int> rf=leafToleaf(root->right);

    int o=max(lf[0],rf[0])+root->data; // ise upar return krna h to kisi ek side ka hi leaf 
    int t=(int)-1e8;                   // le ja skte
    if(lf[0]!=(int)-1e8 && rf[0]!=(int)-1e8){
        t= max (max(lf[1],rf[1]),lf[0]+rf[0]+root->data); // ya to left,right me koi banara h max 
    }                                                // leaf to leaf ya curr node milke banayega

    return {o,t};
}
int maxPathSum(Node* root)
{ 
    // code here  
    return leafToleaf(root)[1];
}
// set-3 ================================================================================

// tree from preorder and inorder =============================================================

// leet 105 =================================================================
int search(vector<int>& in,int tar, int isi, int iei){
        int ans=isi;
        for(;isi<=iei;isi++){
            if(in[isi]==tar){
                ans=isi;
                break;
            }
        }
        return ans;
}

TreeNode* build(vector<int>& pre,int psi,int pei,vector<int>& in,int isi,int iei){
        if(isi>iei) return nullptr;
        
        TreeNode* root=new TreeNode(pre[psi]);
        
        
        int idx=search(in,pre[psi],isi,iei);
        int count=idx-isi; // number of elements in left side 
        //left will have count elements(from psi+1 to psi+count) and rest will be of right(psi+count+1,pei)
        root->left=build(pre,psi+1,psi+count,in,isi,idx-1); 
        root->right=build(pre,psi+count+1,pei,in,idx+1,iei);
        
        return root;    
}

TreeNode* buildTree(vector<int>& pre, vector<int>& in) {
        return build(pre,0,pre.size()-1,in,0,in.size()-1);
}

// make Tree from in & post

// leet 106 ======================================================================

TreeNode* makeTree(vector<int>& in,int isi,int iei,vector<int>& post,int psi,int pei){
        if(psi>pei) return nullptr;
        
        TreeNode* root=new TreeNode(post[pei]);
        
        int i=isi;
        while(in[i]!=post[pei]){
            i++;
        }
        
        int count=i-isi;
        
        root->left=makeTree(in,isi,i-1,post,psi,psi+count-1);
        root->right=makeTree(in,i+1,iei,post,psi+count,pei-1);
        
        return root;
}

TreeNode* buildTree(vector<int>& in, vector<int>& post) {
        int n=in.size();
        return makeTree(in,0,n-1,post,0,n-1);
}
// leet 889 =======================================================================

// tree from post & pre
TreeNode* makeTree(vector<int>& pre,int prsi,int prei,vector<int>& post,int pssi,int psei){
        if(prsi>prei) return nullptr;
        if(prsi==prei) return new TreeNode(pre[prsi]);
        TreeNode* root=new TreeNode(pre[prsi]);
        //cout<<prsi<<" "<<prei<<"\n";
        int i=pssi;
        while(post[i]!=pre[prsi+1]){
            i++;
        }
        int count=i-pssi+1;
        
        root->left=makeTree(pre,prsi+1,prsi+count,post,pssi,pssi+count-1);
        root->right=makeTree(pre,prsi+count+1,prei,post,pssi+count,psei);
        
        return root;
}

TreeNode* constructFromPrePost(vector<int>& pre, vector<int>& post) {
        int n=pre.size();
        return makeTree(pre,0,n-1,post,0,n-1);
}

// leet 968 ================================================================
 //-1 == nhi chahiye camera;
    // 0 == m hi hu camera;
    // 1 == chahiye camera, pls dilva do
int camera=0;
int camera_req(TreeNode* root){
        if(root==nullptr) return -1;
        
        int L=camera_req(root->left);
        int R=camera_req(root->right);
        
        if(L==1 || R==1){// its imp u write this first before next if
            camera++;
            return 0; // mere bacho ko chahiye to m camera ban gya;
        }
        
        if(L==0 || R==0){
            return -1; // camera h bache p par, nahi chahiye2
        }
        
        
        return 1;
}
    
int minCameraCover(TreeNode* root) {
        camera=0;
        if(camera_req(root)==1) camera++;
        return camera;
}

// leet 297============================================================
class Codec {
public:

    // Encodes a tree to a single string.
    string serialize(TreeNode* root) {
        if(root==NULL) return "#";
        
        return to_string(root->val)+","+serialize(root->left)+","+serialize(root->right);
    }

    int i=0;
    TreeNode* des(string data){
        if(i<data.size() && data[i]==',')i++;
        if(i>=data.size() || data[i]=='#'){
            i+=2;
            return nullptr;
        }
        string s="";
        while(i<data.size() && data[i]!=','){
            s+=data[i];
            i++;
        }
        //cout<<s<<" "<<s.size()<<" ";
        TreeNode* node=new TreeNode(stoi(s));
        
        node->left=des(data);
        node->right=des(data);
        cout<<node->val<<" ";
        return node;
    }
    // Decodes your encoded data to tree.
    TreeNode* deserialize(string data) {
        return des(data.substr(0,data.size()-2));   
    }
};

// leet 572 ======================================================================

// how we can use string to represnt tree ===================================
string serialize(TreeNode* t){
        if(t==nullptr) return "#";
        return to_string(t->val)+serialize(t->left)+serialize(t->right);
    }
    bool ans=false;
    string check(TreeNode* root,string str){
        if(root==nullptr) return "#";
        
        string l=check(root->left,str);
        string r=check(root->right,str);
        
        if(l==str || r==str) ans=true;
        
        return to_string(root->val)+l+r;
    }
    bool isSubtree(TreeNode* s, TreeNode* t) {
        // hash string 
        string str=serialize(t);
        string two=check(s,str);
        
        return ans || str==two;
}

void solve(){

}

int main(){
    solve();
}