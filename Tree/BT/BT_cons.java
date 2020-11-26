import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.HashSet;
public class BT_cons {

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

    public static class Node{
        int data;
        Node left;
        Node right;

        public Node(int data){
            this.data=data;
        }
    }

    static int idx=0;
    public static Node constructTree(int[] arr){
        if(idx>=arr.length || arr[idx]==-1){
            idx++;
            return null;
        }
        Node node=new Node(arr[idx++]);
        node.left=constructTree(arr);
        node.right=constructTree(arr);

        return node;
    }

    public static void display(Node root){
        if(root==null) return;

        StringBuilder sb=new StringBuilder();
        
        sb.append(root.left==null ? "." : root.left.data+"");
        sb.append("<- "+root.data+" ->");
        sb.append(root.right==null ? "." : root.right.data+"");
        System.out.println(sb);
        display(root.left);
        display(root.right);
    }

    public static int size(Node root){
        return root==null ? 0 : size(root.left)+size(root.right)+1;
    }

    public static int height(Node root){
        return root==null ? -1 : Math.max(height(root.left),height(root.right)) + 1;
    }

    public static ArrayList<Node> NodeToRootPath(Node root, int val){
        if(root.data==val){
            ArrayList<Node> al=new ArrayList<>();
            al.add(root);
            return al;
        }

        if(root.left!=null){
            ArrayList<Node> al=NodeToRootPath(root.left, val);
            if(al.size()>0){
                al.add(root);
            return al;
            }
        }

        if(root.right!=null){
            ArrayList<Node> al=NodeToRootPath(root.right, val);
            if(al.size()>0){
                al.add(root);
            return al;
            }
        }

        return new ArrayList<>();
    }

    // leet 246 ==========================================================================================
    public static Node lowestCommonAncestor(Node root, Node p, Node q) {
        ArrayList<Node> alp=NodeToRootPath(root, p.data);
        ArrayList<Node> alq=NodeToRootPath(root, q.data);

        int i=alp.size()-1;
        int j=alq.size()-1;
        if(i<0 || j<0) return null;
        while(i>=0 && j>=0 && alp.get(i).data==alq.get(j).data){
            i--;
            j--;
        }
        i++;
        return alp.get(i);
    }

    public static Node lca(Node root, int p, int q){
        Node o=new Node(p);
        Node t=new Node(q);
        return lowestCommonAncestor(root,o,t);
}




// BSF / level-order ========================================================================
void levelOrder(Node root){
    LinkedList<Node> que=new LinkedList<>();
    que.addLast(root);
    int level=1;
    while(que.size()>0){
        int s=que.size();
        System.out.print(level+" -> ");
        while(s-->0){
            Node vtx=que.removeFirst();
            System.out.print(vtx.data+" ");
            if(vtx.left!=null)
                que.addLast(vtx.left);

            if(vtx.right!=null)
                que.addLast(vtx.right);   
        }
        level++;
        System.out.println();
    }
}

public static void levelOrder_02(Node root){
    LinkedList<Node> que=new LinkedList<>();
    que.addLast(root);
    que.addLast(null);

    while(que.size()>1){
        Node vtx=que.removeFirst();

        System.out.print(vtx.data+" ");

        if(vtx.left!=null) que.addLast(vtx.left);
        if(vtx.right!=null) que.addLast(vtx.right);

        if(que.getFirst()==null){
            System.out.println();
            que.removeFirst();
            que.addLast(null);
        }
    }
}

//left view of a tree ===============================================================
// one simple idea is to get first element from each level order(which is wrong)

// recursive
public static void leftView(Node root,boolean[] visited, int l){
    if(root==null) return;

    if(!visited[l]){
        visited[l]=true;
        System.out.print(root.data+" ");
    }
    leftView(root.left, visited, l+1);
    leftView(root.right, visited, l+1);
}

// itertaively
static class pair{
    Node node=null;;
    int x=0;
    pair(Node node, int x){
        this.node=node;
        this.x=x;
    }
    @Override
    public String toString(){
        return this.node.data + "";
    }
}

public static List<pair> leftViewIte(Node root){
    if(root==null) return new ArrayList<>();

    LinkedList<pair> que=new LinkedList<>();
    List<pair> ans=new ArrayList<>();

    que.addLast(new pair(root,0));
    int level=0;
    while(que.size()>0){
        int s=que.size();
        while(s-->0){
            pair vtx=que.removeFirst();

            if(level==ans.size()) ans.add(vtx); // when level equal tab we are picking chote vala
            else if(vtx.x < ans.get(level).x) ans.set(level,vtx);

            if(vtx.node.left!=null) que.addLast(new pair(vtx.node.left,vtx.x-1));
            if(vtx.node.right!=null) que.addLast(new pair(vtx.node.right,vtx.x+1));
        }
        level++;
    }
    return ans;
} 

public static void leftView_utility(Node root){
    boolean visited[]=new boolean[height(root)+1];
    leftView(root,visited,0);
    List<pair> leftV=leftViewIte(root);
    System.out.println(leftV);
}

// right view for interview ================================================================
public static List<Integer> rightSideView(Node root){
    if(root==null) return new ArrayList<>();
    LinkedList<Node> que=new LinkedList<>();

    List<Integer> ans=new ArrayList<>();
    que.addLast(root);
    while(que.size()>0){
        int s=que.size();
        int p=-1;
        while(s-->0){
            Node vtx=que.removeFirst();
            if(vtx.left!=null) que.addLast(vtx.left);
            if(vtx.right!=null) que.addLast(vtx.right);

            p=vtx.data;
        }
        ans.add(p);
    }
    return ans;
}

public static void rightSide(Node root){
    List<Integer> list= rightSideView(root);
    System.out.println(list);
}

// vertical Order ===========================================================================
public static void width(Node root, int[] minMax, int v){
    if(root==null) return;

    minMax[0]=Math.min(minMax[0],v);
    minMax[1]=Math.max(minMax[1],v);

    width(root.left,minMax,v-1);
    width(root.right,minMax,v+1);
}

public static ArrayList<Integer>[] VerticalOrder(Node root){
    int minMax[]=new int[2];
    width(root,minMax,0);

    int w=minMax[1]-minMax[0]+1; // +1 because hume nodes se lena dena h
    ArrayList<Integer>[] ans=new ArrayList[w];
    for(int i=0; i<w; i++) ans[i]=new ArrayList<>();
    LinkedList<pair> que=new LinkedList<>();
    que.addLast(new pair(root,-minMax[0])); // kyunki (-)ve nhi hota na index to 0 krdia

    while(que.size()>0){
        int s=que.size();
        while(s-->0){
            pair vtx=que.removeFirst();

            ans[vtx.x].add(vtx.node.data);

            if(vtx.node.left!=null) que.addLast(new pair(vtx.node.left,vtx.x-1));
            if(vtx.node.right!=null) que.addLast(new pair(vtx.node.right,vtx.x+1));
        }
    }
    return ans;
}

public static int[] verticalSum(Node node){
    int[] maxMin = new int[2];
    width(node,maxMin,0);

    int n = maxMin[1] - maxMin[0] + 1;
    int[] ans = new int[n];
    
    LinkedList<pair> que = new LinkedList<>();
    que.addLast(new pair(node,-maxMin[0]));

    while(que.size()!=0){
        int size = que.size();
        while(size-->0){
            pair vtx = que.removeFirst();

            ans[vtx.x]+=vtx.node.data;

            if(vtx.node.left!=null) que.addLast(new pair(vtx.node.left,vtx.x - 1));
            if(vtx.node.right!=null) que.addLast(new pair(vtx.node.right,vtx.x + 1));
        }
    }
    return ans;
}

// diagonal order ===================================================================================
public static int getMaxLeft(Node root){
    if(root==null) return 0;
    int ans=Integer.MAX_VALUE;

    ans=Math.min(getMaxLeft(root.left)-1,ans); // we are making co-ordinates like this
    ans=Math.min(getMaxLeft(root.right),ans);

    return ans;
}
public static void getdiagonalOrder(Node root, ArrayList<Integer>[]ans,int i){
    if(root==null) return;

    ans[0-i].add(root.data);
    getdiagonalOrder(root.left, ans, i-1);
    getdiagonalOrder(root.right, ans, i); // we arent mving i when going right coz we looking from left
}

public static void diagonalOrder(Node root){
    int ma=getMaxLeft(root);
    ArrayList<Integer>[] ans=new ArrayList[0-ma+1];
    for(int i=0; i<ans.length;i++) ans[i]=new ArrayList<>();
    getdiagonalOrder(root,ans,0);

    for(int i=0; i<ans.length; i++){
        System.out.println(ans[i]);
    }
}

// leet 1110 ===========================================================================

static List<TreeNode> ans;
public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        ans=new ArrayList<>();
        HashSet<Integer> set=new HashSet<>();
        
        for(int i=0; i<to_delete.length; i++){
            set.add(to_delete[i]);
        }
        root=delete(root,set);
        if(root!=null && !set.contains(root.val)) ans.add(root);
        return ans;
}
// post order m krna is important kyunki nodes hum niche se nikalenge vrna connection
// pahle h tod diya to niche jaa hi nhi payenge    
public TreeNode delete(TreeNode root, HashSet<Integer> set){
        if(root==null) return null;
        
        root.left=delete(root.left,set);
        root.right=delete(root.right,set);
        
        if(set.contains(root.val)){
            if(root.left!=null){
                ans.add(root.left);
                root.left=null;
            }
            if(root.right!=null){
                ans.add(root.right);
                root.right=null;
            }
            return null;
        }
        
        return root;
}

// set 2====================================================

// predecessor and successor ===============================================================
public static class allSolPair{
    Node prev=null;
    Node pred=null;
    Node succ=null;

    int ceil=(int)1e8;
    int floor=(int)1e8;
}

public static void allSol(Node root, int data, allSolPair ans){
    if(root==null) return;

    if(ans.ceil<root.data) ans.ceil=root.data;
    if(ans.floor>root.data) ans.floor=root.data;

    allSol(root.left,data,ans);

    if(root.data==data) ans.pred=ans.prev;

    if(ans.prev!=null && ans.prev.data==data) ans.succ=root;

    ans.prev=root; // we need to store our previous when we move right
    allSol(root.right, data, ans);

}

// Moris traversal ==============================================================
public static Node rightMost(Node root, Node next){
    while(root.right!=null && root.right!=next){
        root=root.right;
    }
    return root;
}
// important - traversal in O((3-4)n) n O(1) space ===============================================

public static void MorisInTraversal(Node root){
    Node curr=root;// we need to think how we travel in ineorder and print accordingly 
    while(curr!=null){ // thread is used to make so that we can travel easily ===
        Node left=curr.left;
        if(left!=null){
            Node rm=rightMost(left, curr);
            if(rm.right==null){
                rm.right=curr;     // making thread
                curr=left;
            }else{
                System.out.print(curr.data+" ");
                rm.right=null;    // destroying thread
                curr=curr.right;
            }
        } else {
            System.out.print(curr.data+" ");
            curr=curr.right;
        }
    }
}

// moris Preorder traversal ===============================================

public static void MorisPreTraversal(Node root){
    Node curr=root;  // we need to think how we travel in preorder and print accordingly 
    while(curr!=null){ // thread is used to make so that we can travel easily ===
        Node left=curr.left;
        if(left!=null){
            Node rm=rightMost(left, curr);
            if(rm.right==null){
                System.out.print(curr.data+" ");
                rm.right=curr;     // making thread
                curr=left;
            }else{
                
                rm.right=null;    // destroying thread
                curr=curr.right;
            }
        } else {
            System.out.print(curr.data+" ");
            curr=curr.right;
        }
    }
}

// iterative postorder =================================================================
static class Post{
    Node node;
    boolean leftDone;
    boolean selfDone;
    boolean rightDone;

    Post(Node node, boolean a, boolean b, boolean c){
        this.node=node;
        this.leftDone=a;
        this.selfDone=b;
        this.rightDone=c;
    }
}

public static void PostorderIte(Node root){
    Stack<Post> st=new Stack<>();

    st.push(new Post(root, false, false, false));
    while(st.size()>0){
        Post rp=st.peek();
        // same as recursion, pahle left, phir right, phir apan print;
        if(!rp.leftDone){
            rp.leftDone=true;
            if(rp.node.left!=null) st.push(new Post(rp.node.left, false, false, false));
        } else if(!rp.rightDone){
            rp.rightDone=true;
            if(rp.node.right!=null) st.push(new Post(rp.node.right,false,false,false));
        } else if(!rp.selfDone){
            System.out.print(rp.node.data+" ");
             rp.selfDone=true;
        } else {
            st.pop();
        }
    }
}

public static void solve(){
    int[] arr={10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
    idx=0;
    Node root=constructTree(arr);

    display(root);
    //Node node=root;
    //System.out.println(size(root));
    //node=root;
    //System.out.println(height(root));

    // questions ===============================================================================
    //System.out.println(lca(root,90,80).data);
    //leftView_utility(root);
    //levelOrder_02(root);

    // ArrayList<Integer>[] ans=VerticalOrder(root);
    // for(int i=0; i<ans.length; i++){
    //     System.out.println(ans[i]);
    // }

    //diagonalOrder(root);
    MorisInTraversal(root);
    System.out.println();
    MorisPreTraversal(root);
    System.out.println();
    PostorderIte(root);
}

public static void main(String[] args) {
       solve();
    }    
}
