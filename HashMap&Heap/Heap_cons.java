import java.util.ArrayList;

public class Heap_cons {

    public static class Heap{
        ArrayList<Integer> arr=new ArrayList<>();
        boolean isMax=true; // true for maxHeap & false for minHeap;
        

        public Heap(int[] data){
            constructHeap(data);
        }

        public Heap(int[] data, boolean isMax){
            this.isMax=isMax;
            constructHeap(data);
        }

        public int compareTo(int a, int b){
            if(isMax){
                return this.arr.get(a) - this.arr.get(b);
            } else {
                return this.arr.get(b) - this.arr.get(a);
            }
        }

        public void swap(int a, int b){
            int one=this.arr.get(b);
            int two=this.arr.get(a);

            this.arr.set(a,one);
            this.arr.set(b,two);
        }

        public void constructHeap(int[] data){
            for(int e: data) arr.add(e);
            int n=this.arr.size();

            for(int i=n-1; i>=0; i--){
                downHeapify(i);
            }
        }

        public void downHeapify(int pi){
            int maxIdx=pi; 

            int lci=(2*pi)+1;
            int rci=(2*pi)+2;

            if(lci<this.arr.size() && compareTo(lci,maxIdx)>0){
                maxIdx=lci;
            }
            if(rci<this.arr.size() && compareTo(rci,maxIdx)>0){
                maxIdx=rci;
            }

            if(maxIdx!=pi){
                swap(pi,maxIdx);
                downHeapify(maxIdx);
            }
        }

        public void upHeapify(int ci){
            int pi=(ci-1)/2;

            if(pi>=0 && compareTo(ci, pi)>0){
                swap(pi,ci);
                upHeapify(pi);
            }
        }
        public int size(){
            return this.arr.size();
        }

        public void push(int ele){
            this.arr.add(ele);

            upHeapify(size()-1);
        }
        public int top(){
            return this.arr.get(0);
        }

        public int pop(){
            int n=size();
            swap(0,n-1);
            int tr=this.arr.remove(n-1);
            downHeapify(0);

            return tr; 
        }
    }
    public static void solve(){
        int[] arr = { 10, 20, 30, -2, -3, -4, 5, 6, 7, 8, 9, 22, 11, 13 };
        Heap pq=new Heap(arr,false);

        while(pq.size()!=0){
            
            for(int ele: pq.arr) System.out.print(ele + " ");
            System.out.println();

            System.out.print(pq.pop() + " ");
            System.out.println();
        }
    }
    public static void main(String[] args) {
        solve();
    }
}
