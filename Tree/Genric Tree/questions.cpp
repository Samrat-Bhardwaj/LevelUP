#include<stack>
#include<vector>

using namespace std;
class Node {
public:
    int val;
    vector<Node*> children;

    Node() {}

    Node(int _val) {
        val = _val;
    }

    Node(int _val, vector<Node*> _children) {
        val = _val;
        children = _children;
    }
};

// iterative postorder =====================================================================
vector<int> postorder(Node* root) {
        vector<int> ans;
        stack<Node*> st1;
        stack<Node*> st2;
        if(root!=nullptr)
            st1.push(root);
        
        while(st1.size()){
            Node* r=st1.top(); st1.pop();
    
            st2.push(r);
            
            for(Node* child:r->children){
                st1.push(child);
            }
        }
        
        while(st2.size()){
            ans.push_back(st2.top()->val);
            st2.pop();
        }
        return ans;
}