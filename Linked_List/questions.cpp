#include<iostream>
using namespace std;

// Ques -> Can we reverse a linked list in less than O(n) ?

// Ans  -> No we cant until its DLL with head n Tail pointers (then we can just swap head n tail and tada
// its reversed);


// Ques -> Why Quick Sort preferred for Arrays and Merge Sort for Linked Lists?

// Ans -> https://www.geeksforgeeks.org/why-quick-sort-preferred-for-arrays-and-merge-sort-for-linked-lists/


struct ListNode
{
    int val;
    ListNode *next;

    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

struct Node
{
    int data;
    Node* next;
    
    Node(int x){
        data = x;
        next = NULL;
    }
};

// https://practice.geeksforgeeks.org/problems/delete-nodes-having-greater-value-on-right/1# 

// adjacent right bada hua to curr ko hatado ==============================================

// good question on recursion in linked list ===================================

Node *compute(Node *head)
{
    if(head==nullptr || head->next==nullptr){
        return head;
    }
    
    Node* temp=compute(head->next);
    
    if(head->data < temp->data){
        return temp;
    }
    head->next=temp;
    return head;
}



// good question, should attempt 
// leet 82====================================================================================
 ListNode* deleteDuplicates(ListNode* head) {
         if(head==nullptr || head->next==nullptr){
            return head;
        }
        
        ListNode* prev=new ListNode(-1); // in case head vala dup nikle to usko hatane k liye
        prev->next=head; // connected to head
        ListNode* tr=prev;  // to return pointer 
        while(prev && prev->next){
            if(prev->next->next && prev->next->val==prev->next->next->val){  // in case we get a duplicate
                ListNode* temp=prev->next; // saving next
                prev->next=nullptr; //deleting next
                
                while(temp->next && temp->val==temp->next->val){ //deleting until no dup;
                    ListNode* t=temp->next;
                    temp->next=nullptr;
                    temp=t;   
                }
                
                prev->next=temp->next; // linking after removing dup
                temp->next=nullptr;
            } else
            prev=prev->next; 
        }
        return tr->val==-1 ? tr->next : tr; // tr==-1 if head is not duplicate; 
}

// easy not so good question - same as odd even 
// leet 86 ======================================================================================

ListNode* partition(ListNode* head, int x) {
        if(head==nullptr || head->next==nullptr){
            return head;
        }
        
        ListNode* low=new ListNode(-1);
        ListNode* lp=low;
        
        ListNode* over=new ListNode(-1);
        ListNode* op=over;
        
        ListNode* curr=head;
        
        while(curr){
            if(curr->val < x){
                lp->next=curr;
                lp=lp->next;
            } else {
                op->next=curr;
                op=op->next;
            }
            
            ListNode* temp=curr->next; 
            curr->next=nullptr;       //disconnecting the link 
            curr=temp;
        }
        
        lp->next=over->next;
        return low->next;
}

// good question, should reverse ans linked list rather than every ll
// leet 445 =========================================================================================
 ListNode* reverse(ListNode* head){
        ListNode* prev=nullptr;
        ListNode* curr=head;
        
        while(curr){
            ListNode* next=curr->next;
            
            curr->next=prev;
            prev=curr;
            curr=next;
        }
        return prev;
    }
    ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {
        if(l1==NULL || l2==NULL) return nullptr;
     
        l1=reverse(l1);  // reversing 
        l2=reverse(l2);

        ListNode* t1=l1;
        ListNode* t2=l2;

        ListNode* dummy=new ListNode(-1); // ans ll
         ListNode* curr=dummy; 
        int c=0;
        while(t1!=nullptr || t2!=nullptr){
            int val=0; 
            if(t1 && t2){
                val=t1->val + t2->val + c;
                t1=t1->next;
                t2=t2->next;
            } else if(t1){
                val=t1->val+c;
                t1=t1->next;
            } else if(t2) {
                val=t2->val+c;
                t2=t2->next;
            }
            c=val/10;
            curr->next=new ListNode(val%10);
            curr=curr->next;
        }
        if(c!=0){  // if at last there's one carry left
            curr->next=new ListNode(c);
        }

        l1=reverse(l1); // reversing again
        l2=reverse(l2);
        curr=reverse(dummy->next);
        
        return curr;
    }


ListNode* cons(){
    ListNode* head=new ListNode(1);
    ListNode* temp=head;
    int i=10;
    int j=2;
    while(i--){
        temp->next=new ListNode(j);
        j++;
        temp=temp->next;
    }
    return head;
}


void solve(){
   ListNode* head=cons();
   ListNode* ans=deleteDuplicates(head);
   cout<<ans->val;
}

int main(){
    solve();
    return 0;
}