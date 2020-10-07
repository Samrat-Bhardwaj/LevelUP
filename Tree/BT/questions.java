import java.util.List;
import java.util.*;
import java.util.ArrayList;
public class questions {
    public class TreeNode {
             int val;
             TreeNode left;
             TreeNode right;
             TreeNode(int x) { val = x; }
    }
    class Node{
    int data;
    Node left, right;

    Node(int item)
    {
        data = item;
        left = right = null;
    }
}
    // leet 236 ======================================================================================
    public static ArrayList<TreeNode> NodeToRootPath(TreeNode root, int v){
        if(root.val==v){
            ArrayList<TreeNode> al=new ArrayList<>();
            al.add(root);
            return al;
        }

        if(root.left!=null){
            ArrayList<TreeNode> all=NodeToRootPath(root.left, v);
            if(all.size()>0){
                all.add(root);
                return all;
            }
        }

        if(root.right!=null){
            ArrayList<TreeNode> alr=NodeToRootPath(root.right, v);
            if(alr.size()>0){
                alr.add(root);
                return alr;
            }
        }

        return new ArrayList<>();
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode node=root;
        ArrayList<TreeNode> alp=NodeToRootPath(node, p.val);
        ArrayList<TreeNode> alq=NodeToRootPath(node, q.val);
       
        
        int i=alp.size()-1;
        int j=alq.size()-1;
        if(i<0 || j<0) return null;
        while(i>=0 && j>=0 && alp.get(i).val==alq.get(j).val){
            i--;
            j--;
        }
        i++;
        return alp.get(i);
    }

    // leet 863 ============================================================================================
    public boolean NodeToRootPath2(TreeNode root, TreeNode tar, ArrayList<TreeNode> al){
        if(root==null) return false;
        if(root.val==tar.val){al.add(root); return true;} //adding the node

        boolean f=NodeToRootPath2(root.left, tar, al) || NodeToRootPath2(root.right, tar, al);
        if(f){
            al.add(root);
            return true;
        }
        return false;
    }
    
    // we dont need root in args
    public void getKlevelDown(TreeNode root, TreeNode t, TreeNode block, int k){
        if(t==null|| t==block || k<0){
            return;
        }
        if(k==0){
            ans.add(t.val);
            return;
        }
        getKlevelDown(root, t.right, block, k-1);
        getKlevelDown(root, t.left, block, k-1);
    }

    static List<Integer> ans;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        ArrayList<TreeNode> al=new ArrayList<>();
        NodeToRootPath2(root,target,al);
        
        ans=new ArrayList<>();
        for(int i=0; i<al.size(); i++){
            // we need to bloc one node previous coz it will make us move towards target block
            getKlevelDown(root,al.get(i),i==0 ? null : al.get(i-1),K-i);
        }
        return ans;
    }
    // static nahi banate, go see cpp approach .....
    // leet 836 with constant space ================================================================
    static TreeNode block=null;
    static TreeNode t=null;
    static int k=0;
    static List<Integer> ans2;
    public boolean NodeToRootPath3(TreeNode root, TreeNode target){
        if(root==null){
            return false;
        }
        if(root.val==target.val){
            getKdown(root,null,k);
            block=root;
            k--;
            return true;
        }

        boolean f=NodeToRootPath3(root.left, target) || NodeToRootPath3(root.right, target);
        if(f){
            getKdown(root, block, k);
            block=root;
            k--;
            return true;
        }
        return false;
    }
    public static void getKdown(TreeNode t, TreeNode block, int k){
        if(t==null || t==block || k<0) return;

        if(k==0){
            ans2.add(t.val);
            return;
        }

        getKdown(t.left, block, k-1);
        getKdown(t.right, block, k-1);
    }
    public List<Integer> distanceK2(TreeNode root, TreeNode target, int K) {
        ans2=new ArrayList<>();
        k=K;
        NodeToRootPath3(root,target);        
       
        return ans2;
    }

    // leet 543  ==============================================================================
    public int height(TreeNode root){
        if(root==null) return -1;

        return Math.max(height(root.left),height(root.right))+1;
    }

    public int dia_01(TreeNode root){
        if(root==null) return 0;

        int ld=dia_01(root.left);
        int rd=dia_01(root.right);

        int lh=height(root.left);
        int rh=height(root.right);

        return Math.max(Math.max(ld,rd),lh+rh+2); //+2 for our length;
    }

    public int[] dia_02(TreeNode root){
        if(root==null) return new int[]{0,-1};

        int[] ldh=dia_02(root.left);
        int[] rdh=dia_02(root.right);

        int d=Math.max(Math.max(ldh[0],rdh[0]),ldh[1]+rdh[1]+2);
        int h=Math.max(ldh[1],rdh[1])+1;
        return new int[]{d,h}; 
    }

    static int ans1=0;
    public int dia_03(TreeNode root){
        if(root==null) return -1;

        int ld=dia_03(root.left);
        int rd=dia_03(root.right);

        ans1=Math.max(ans1,ld+rd+2);
        return Math.max(ld,rd)+1;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        //return dia_01(root);
        //return dia_02(root)[0];
        ans1=0;
        dia_03(root);
        return ans1;
    }

// leet 113 ====================================================================================
public void some(TreeNode root,List<Integer> temp,List<List<Integer>> ans,int csum,int sum){
    if(root==null) return; 
    
    temp.add(root.val);  // jo aya rakh liya 
    csum+=root.val;      // jo aya rakh liya part-2;
    
    if(root.left==null && root.right==null){
        if(csum==sum){
            ans.add(new ArrayList<>(temp));
        }
        temp.remove(temp.size()-1);  // this is important, to remove last component before returning
        return;
    }
    
    some(root.left,temp,ans,csum,sum); // after this step wont remove component kyunki yahi se to right 
    some(root.right,temp,ans,csum,sum); // jare h, ab right se jab vapis aake upar jayenge tab remove 
    temp.remove(temp.size()-1);  // karenge, yaha p;
}

public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<Integer> temp=new ArrayList<>();
        List<List<Integer>> ans=new ArrayList<>();
        some(root,temp,ans,0,sum);
        return ans;
}
// https://practice.geeksforgeeks.org/problems/maximum-path-sum/1
static int ans3=(int)-1e8;
int some(Node root){
    if(root==null){
        return (int)-1e8;
    }
    if(root.right==null && root.left==null){
        return root.data;
    }
    
    int lh=some(root.left);
    int rh=some(root.right);

    // if(lh!=(int)-1e8 && rh!=(int)-1e8){
    //     ans3=Math.max(ans3,lh+rh+root.data);
    // }
    //=======  OR ===========================
    if(root.left!=null && root.right!=null){
        ans3=Math.max(ans3,lh+rh+root.data);
    }

    return Math.max(lh,rh)+root.data;
}
int maxPathSum(Node root)
{   ans3=(int)-1e8;
    some(root);
    return ans3;
}

// leet 124 =================================================================================== 
// idx-0 represent ab tak ka max sum sare node add krke uske niche k, idx-1 represent our answer
// till root node;
public int[] anyToany(TreeNode root){ 
    if(root==null) return new int[]{(int)-1e8,(int)-1e8}; 

    //marji h ye if lagna ya nhi lagna, code will work without this as well
    if(root.left==null && root.right==null){
        return new int[]{root.val,root.val}; // one node can return only one value
    }
    
    int[] la=anyToany(root.left); // left array
    int[] ra=anyToany(root.right); // right array
    
    int lm=la[0]; 
    int rm=ra[0];
    
    //we dont need to compare this because left aur right p hum already compare karke aye h;
    int o=Math.max(lm,rm); // left-sum ya right-sum mese koi ek nikal skta max 
    
    
    int t=(int)-1e8; // left-sum + root.val ya right-sum+root.val mese bhi koi max ho skta
    if(lm!=(int)-1e8){
        t=Math.max(t,lm+root.val);
    }
    if(rm!=(int)-1e8){
        t=Math.max(t,rm+root.val);
    }
    
    int first=Math.max(root.val,t); // humko node to node krna, isliye ya to "t" hoga, ya khud root.val
                                    // hoga
    int th=(int)-1e8;
    if(lm!=(int)-1e8 && rm!=(int)-1e8){
        th=lm+rm+root.val;         // left max+ right-max + root.val bhi aklea kafi ho skta
    }
    
    int f=Math.max(Math.max(o,th),first);
    
    int se=Math.max(Math.max(f,la[1]),ra[1]);
    
    return new int[]{first,se};
}
public int maxPathSum(TreeNode root) {
   return anyToany(root)[1];
}
// concise code
public int[] anyToany1(TreeNode root){
    if(root==null) return new int[]{(int)-1e8,(int)-1e8};

    int[] la=anyToany1(root.left);
    int[] ra=anyToany1(root.right);
    
    int lm=la[0];
    int rm=ra[0];
    
    int first=(int)-1e8;
    if(lm!=(int)-1e8){
        first=Math.max(first,lm+root.val);
    }
    if(rm!=(int)-1e8){
        first=Math.max(first,rm+root.val);
    }
    first=Math.max(root.val,first);
    
    int th=first;
    if(lm!=(int)-1e8 && rm!=(int)-1e8){
        th=lm+rm+root.val;
    }
    
    int second=Math.max(Math.max(ra[1],la[1]),Math.max(th,first));
    
    return new int[]{first,second};
}
public int maxPathSum1(TreeNode root) {
   return anyToany1(root)[1];
}
// set-2 ===============================================================================

// leet 979 =================================================(same as cameras - 968)
public int getCoins(TreeNode root){
    if(root==null) return 0;
    
    int L=getCoins(root.right);
    int R=getCoins(root.left);
    
    ans5+=Math.abs(L)+Math.abs(R); // we are addimg number of coins we want ;
    
    return root.val+L+R-1; // we are returning number of coins required ;
}

static int ans5=0;
public int distributeCoins(TreeNode root) {
    ans5=0;
    getCoins(root);
    return ans5;
}

//leet 1130 (stack ka mast question)====================================================
public int mctFromLeafValues(int[] arr) {
    int res=0;
    Stack<Integer> st=new Stack<>();
    st.push(Integer.MAX_VALUE);
    for(int a:arr){
        while(st.peek()<=a){ // ab tak k lowest jo use nhi huye bs unhi ko banayenge
            int m=st.pop(); // hum apna subtree ka part
            res+=m*Math.min(a,st.peek()); // ab 2 option, left subtree ya right 
        }                                 // vo yaha se aya
        st.push(a);
    }
    while(st.size()>2){
        res+=st.pop()*st.peek();
    }
    return res;
}

//https://practice.geeksforgeeks.org/problems/binary-tree-to-dll/1

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
        root.left=prev; // making our tree in inorder
        prev.right=root;
        }
        prev=root; // WE ALWAYS MAKE OUR PREVIOUS BEFOR GOING ON RIGHT 

        makeList(root.right);
}

//https://practice.geeksforgeeks.org/problems/binary-tree-to-cdll/1 ======================

Node head1=null,prev1=null;
Node bTreeToClist(Node root)
    {
        head1=null;prev1=null;
	    makeList(root);
	    prev1.right=head1;
	    head1.left=prev1;
	    return head;
}

// leet 652 find dup subtree ==============================================================================

// good question, very good question ==================

public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
    List<TreeNode> res=new ArrayList<>();
    HashMap<String,Integer> map=new HashMap<>();
    getRes(root,map,res);
    return res;
}

public String getRes(TreeNode root, HashMap<String,Integer> map, List<TreeNode> res){
    if(root==null) return "#"; // "#" respresents end of subtree
    
    String L=getRes(root.left,map,res);
    String R=getRes(root.right,map,res);
    
    String str=root.val+","+L+","+R; // this will be unique for every subtree;
    
    if(map.getOrDefault(str,0)==1) res.add(root); // agr ek bar pahle aya tabhi dalvaya
    map.put(str,map.getOrDefault(str,0)+1);
    
    return str;
}

// leet 279 =============================================================




public static void solve(){

}
    public static void main(String[] args) {
        solve();    
    }
}
