package UnionFind;

import java.util.*;

public class MergeIntervals {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0){
            return new int[0][0];
        }
        Arrays.sort(intervals, (a, b)-> Integer.compare(a[0], b[0]));

        List<int[]> holder = new ArrayList<>();
        holder.add(intervals[0]);
        for (int i=1; i<intervals.length; i++){
            int[] last = holder.get(holder.size()-1);
            if (intervals[i][0] > last[1]){
                holder.add(intervals[i]);
            }else {
                //need to merge them, by updating the end time of last element with current interval's end time
                last[1] = Math.max(last[1], intervals[i][1]);
            }
        }
        int[][] res = new int[holder.size()][2];
        for (int i=0; i<holder.size(); i++){
            res[i] = holder.get(i);
        }
        return res;
    }
}

//clarification: is the intervals sorted by startint point?
// intervals.length = n

//[[1,3],[2,6],[8,10],[15,18] [20,22]
// add                   add    i

// 1,2 3,6 -> if not then continue?
//[3,6],[8,10]  -> 3,10
//3,10 15,18 -> 3,18

//1,2 3,18
//TC:O(nlogn)sort + O(n) linear scan
//SC:O(n) to holder sorted intervals + O(logn) for sorting call stack(linkedList mergesort)

//[[1,4],[2,3]]

//M2:???