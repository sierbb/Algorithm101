package OOD;

import java.util.*;

class HitCounter {
    //This is a sliding window hitCounter
    //Queue size == number of requests in past 5 mins
    private Deque<Pair> deque;
    private int total;

    /** Initialize your data structure here. */
    public HitCounter() {
        deque = new LinkedList<>();
        total = 0;
    }

    /** Record a hit, increase the hit count if timestamp are the same
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        if (deque.isEmpty() || !deque.isEmpty() && deque.peekFirst().timestamp != timestamp){
            deque.offerFirst(new Pair(timestamp, 1));
        }else {
            Pair p = deque.pollFirst();
            p.count++;
            deque.offerFirst(p);
        }
        total++;
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        while (!deque.isEmpty() && deque.peekLast().timestamp + 300 <= timestamp){
            total -= deque.peekLast().count;
            deque.pollLast();
        }
        return total;
    }

    class Pair{
        private int timestamp;
        private int count;
        public Pair(int timestamp, int count){
            this.timestamp = timestamp;
            this.count = count;
        }
    }

    public static void main(String[] args){

    }
}

/**
 * Your HitCounter object will be instantiated and called as such:
 * HitCounter obj = new HitCounter();
 * obj.hit(timestamp);
 * int param_2 = obj.getHits(timestamp);
 */

//TC
//hit - O(1)
//getHits - O(n)

//SC:O(n)
