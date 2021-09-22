package Array;

import java.util.*;

public class MyCalendarIII {
    TreeMap<Integer, Integer> map;

    public MyCalendarIII() {
        map = new TreeMap<>(); //defaults to sort key ascending, so map<start, freq> and <end, freq>
    }

    public int book(int start, int end) {
        //Let treemap help us sort the time ascending
        map.put(start, map.getOrDefault(start, 0)+1);
        map.put(end, map.getOrDefault(end, 0)-1);
        //for each book input here, we will iterate through the treeMap (not polling, but only return values???)
        //to check how many open and close case so far
        int active = 0;
        int max = 0;
        for (Integer num : map.values()){
            //sum the open + close events in time order
            active += num;
            max = Math.max(max, active);
        }
        return max;
    }
}

/**
 * A k-booking happens when k events have some non-empty intersection (i.e., there is some time that is common to all k events.)
 *
 * You are given some events [start, end), after each given event, return an integer k representing the maximum k-booking between all the previous events.
 * Your MyCalendarThree object will be instantiated and called as such:
 * MyCalendarThree obj = new MyCalendarThree();
 * int param_1 = obj.book(start,end);
 */


//claification: largest k booking across all the events so far, for every event comes in
// [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
// [10, 20]                   [50, 60],
// [10,       40]       [25,     55]
//[5, 15]
//[5, 10]

//List[ <int[5, 10],2]>, <int[10,15], 3>, <int[15,20], 2>, <int[20,40], 1>, <int[50, 60], 1> ]


//[5, 15], [5, 10], [10, 40], [10, 20], [25, 55], [50, 60]
//PQ[5, 5, 10,10,10, 15, 20, 25, 40, 50, 55, 60

//active 2-1,+1,+1, -1,-1,+1, -1,+1,-1,-1  -> 3 is the max in progress
//TC:O(n) to add n elements to map
//SC:O(n) for TreeMap