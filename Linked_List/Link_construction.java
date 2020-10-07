public class Link_construction {
    public static class LinkedList{
        protected class Node{
            int val;
            Node next;

            public Node(int val){
                this.val=val;
            }
        }

        private Node head=null;
        private Node tail=null;
        private int size=0;

        //basic===========================================
        public int size(){
            return this.size;
        }

        public boolean isEmpty(){
            return this.size==0;
        }

        //ADD===========================================

        public void addFirst(int data){
            Node node=new Node(data);
            if(this.size==0){
                this.head=node;
                this.tail=node;
            } else {
                Node next=this.head;
                this.head=node;
                node.next=next;
            }
            this.size++;
        }

        public void addLast(int data){
            Node node=new Node(data);
            if(this.size==0){
                this.head=node;
                this.tail=node;
            } else {
                Node prev=this.tail;
                prev.next=node;
                this.tail=node;
            }
            this.size++;
        }

        public void addAt(int data, int idx) throws Exception{
            if(idx<0 || idx>=this.size){
                throw new Exception("InvalidLocation: "+idx);
            }
            Node node=new Node(data);
            if(idx==0){
                addFirst(data);
            } else if(idx==this.size){
                addFirst(data);
            } else {
                Node prev=getNode(idx-1);
                Node next=getNode(idx);

                prev.next=node;
                node.next=next;

                this.size++;
            }
        }
        @Override
        public String toString(){
            StringBuilder sb=new StringBuilder();
            Node temp=this.head;
            sb.append("[");
            while(temp!=null){
                sb.append(temp.val);
                if(temp.next!=null)
                    sb.append(", ");
                temp=temp.next;
            }
            sb.append("]");
            return sb.toString();
        }
        //Remove===================================================
        protected Node removeFirstNode(){
            Node node=this.head;
            if(this.size==1){
                this.head=null;
                this.tail=null;
            } else {
                Node temp=this.head;
                Node temp2=temp.next;
                head=temp2;
                temp.next=null;
            }
            this.size--;
            return node;
        }

        public int removeFirst() throws Exception{
            if(this.size==0){
                throw new Exception("NullPointerException: "+ -1);
            }
            Node node=removeFirstNode();
            return node.val; 
        } 
        
        protected Node removeLastNode(){
            Node node=this.tail;
            if(this.size==1){
                this.head=null;
                this.tail=null;
            } else {
                Node temp=getNode(this.size-2);
                temp.next=null;
                this.tail=temp;
            }
            this.size--;
            return node;
        }

        public int removeLast() throws Exception{
            if(this.size==0){
                throw new Exception("NullPointerException: "+-1);
            }
            Node node=removeLastNode();
            return node.val;
        }

        protected Node removeNodeAt(int idx){
            if(idx==0){
                return removeFirstNode();
            } else if(idx==this.size-1){
                return removeLastNode();
            } 
            Node node=getNode(idx-1);
            Node tr=node.next;
            node.next=node.next.next;
            tr.next=null;
            this.size--;
            return tr;
        }

        public int removeAt(int idx) throws Exception{
            if(idx<0 || idx>=this.size){
                throw new Exception("IndexOutOfBounds: "+idx);
            }
            Node node=removeNodeAt(idx);
            return node.val;
        }
        //get=====================================================
        protected Node getFirstNode(){
            return this.head;
        }
        
        public int getFirst() throws Exception{
            if(this.size==0){
                throw new Exception("NullPointer: samrat");
            }
            Node node=getFirstNode();
            return node.val;
        }

        protected Node getLastNode(){
            return this.tail;
        }

        public int getLast() throws Exception{
            if(this.size==0){
                throw new Exception("NullPointerException");
            }
            Node node=getLastNode();
            return node.val;
        }

        protected Node getNode(int idx) {
            
            Node temp=this.head;
            while(idx-->0){
                temp=temp.next;
            }
            return temp;
        }
        
        public int getAt(int idx) throws Exception{
            if(idx<0 || idx>this.size)
            {
                throw new Exception("IndexOutOfBounds");
            }
            Node node=getNode(idx);
            return node.val;
        }
    }
 
    public static void solve() throws Exception{
        
    }
    public static void main(String[] args)throws Exception{
        solve();
        LinkedList ll=new LinkedList();
        ll.addFirst(10);
        ll.addFirst(20);
        ll.addLast(30);
        ll.addAt(45,2);


        int g=ll.getAt(3);
        System.out.println(ll+" "+g);
    }
}
