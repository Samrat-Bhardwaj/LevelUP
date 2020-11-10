import java.util.LinkedList;
import java.util.Arrays;
import java.util.Queue;

public class Queue_ques {

// leet 950 =================================================================================

// mast question of usage of deque -> doubly ended queue ===============
// we are basically simulating the question ===
    public int[] deckRevealedIncreasing(int[] deck) {
        int n=deck.length;
        
        int[] ans=new int[n];
        
        Queue<Integer> que=new LinkedList<>();
        
        for(int i=0; i<n; i++) que.add(i); // adding at last
        
        Arrays.sort(deck);
        int i=0;
        while(i<n && que.size()>0){
           ans[que.poll()]=deck[i]; // top se nikala
            que.add(que.poll()); // phirse top se remove krke last m dala
            i++;
        }
        return ans;
    }

// bahot mast question of two pointers(inki jagah we can use queue as well)

//https://practice.geeksforgeeks.org/problems/circular-tour/1#

// leet 134 =========================================================

// petrol bharvao aage jao, konse index se vapis isi index p aa skte h vo btao

public int canCompleteCircuit(int[] petrol, int[] distance) {
        int n=petrol.length;
        if(n==1){ // base case
            if(petrol[0]>=distance[0]) return 0;
            return -1;
        }

         int start=0;
         int end=1;
         
         int cp=petrol[start] - distance[start]; // curr petrol
         
         // jabtak ghumke vapis vahi na pahuch jaye with posivite petrol
         // tab tak start end change hota rahega
         while(end!=start || cp <0){
             // loop to change start if necessary (jab cp negative ho jaye)
             while(start!=end && cp <0){
                 // is loopm ghuse h mtlb start sahi jagah nahi h
                 // to start aage badha diya aur cp mese start vala element
                 // remove kar diya 
                 
                 cp=cp-(petrol[start]-distance[start]);
                 start=(start+1)%n;
                 
                 if(start==0){ // ghum firke vahi aa gye, koi bhi sashi start
                 // point nhi mila
                     return -1;
                 }
             }
             // end aage badhayenge jab tak badha skte h
             
             // adding end point vala petrol pump 
             cp+=petrol[end]-distance[end];
             end=(end+1)%n;
         }
         
         return start ; // or end
}
}
