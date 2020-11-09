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
}
