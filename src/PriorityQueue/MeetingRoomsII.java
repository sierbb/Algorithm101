package PriorityQueue;

import java.util.*;

public class MeetingRoomsII {

    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0){
            return 0;
        }
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int start = 0; //start time
        int globalMax = 0;
        for (int i=0; i<intervals.length; i++){
            start = intervals[i][0];
            //can push end time to stack since not ended
            if (pq.isEmpty()){
                pq.offer(intervals[i][1]);
            }else {
                if (pq.peek() > start){
                    pq.offer(intervals[i][1]);
                }else {
                    while (!pq.isEmpty() && pq.peek() <= start){
                        pq.poll();
                    }
                    pq.offer(intervals[i][1]); //must add the new end!!
                }
            }
            //update stack size for max number of meetings ongoing
            globalMax = Math.max(globalMax, pq.size());
        }
        return globalMax;
    }
}

//clarification:
//room required = most number of overlapping intervals at the same time
//First sort by starting time

//count = 1;
//[[0,10],[5,10],[15,20]]
// stack [ 0, 5, 15,
// pq [10, 10, 20,
//number of meeting rooms -> max size of stackEnd since they aren't pop out

//[[2,4], [7,10]]
//stack [ 2, 7
//pq [ 10
//1


//[[4,9],[4,17],[9,10]]
//PQ[ 17, 10,
// 9

//[[1,5],[8,9],[8,9]]
//pq[5,
// 1


//TC:O(nlogn)sort + O(nlogn) for pq to insert and poll n times for n intervals = O(nlogn)
//SC:O(n) for pq