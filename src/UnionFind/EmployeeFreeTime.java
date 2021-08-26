package UnionFind;

import java.util.*;

class Interval {
    public int start;
    public int end;

    public Interval() {}

    public Interval(int _start, int _end) {
        start = _start;
        end = _end;
    }
};

public class EmployeeFreeTime {

    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> res = new ArrayList<>();
        if (schedule == null || schedule.size() == 0){
            return res;
        }

        PriorityQueue<Node> minHeap = new PriorityQueue<Node>(schedule.size(), new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n1.interval.start - n2.interval.start;
            }
        });

        for (int i= 0; i < schedule.size(); i++){
            minHeap.offer(new Node(schedule.get(i).get(0), i, 0));
        }
        List<Interval> holder = new ArrayList<>();
        while (!minHeap.isEmpty()){
            //compare all n intervals and find the one with smallest start time
            Node curNode = minHeap.poll();
            if ( holder.size() > 0 ){
                Interval last = holder.get(holder.size()-1);
                if (last.end > curNode.interval.start ){
                    //need to merge them
                    last.end = Math.max(last.end, curNode.interval.end);
                }else {
                    holder.add(curNode.interval);
                }
            }else {
                holder.add(curNode.interval);
            }

            if (curNode.intIdx < schedule.get(curNode.emyIdx).size()-1){
                Interval newInt = schedule.get(curNode.emyIdx).get(curNode.intIdx+1);
                minHeap.offer(new Node(newInt, curNode.emyIdx, curNode.intIdx+1));
            }
        }

        //step 3: scan holder again find the free time
        for (int i=1; i < holder.size(); i++){
            if (holder.get(i).start > holder.get(i-1).end){
                res.add(new Interval(holder.get(i-1).end, holder.get(i).start));
            }
        }
        return res;
    }


    class Node{
        Interval interval;
        int emyIdx;
        int intIdx;
        public Node(Interval interval, int emyIdx, int intIdx){
            this.interval = interval;
            this.emyIdx= emyIdx;
            this.intIdx = intIdx;
        }
    }
}

//clarification: guarantee each employee's intervals are sorted by start time
// employees.length = n; employees_intervals.length = m.

//[[[1,2],[5,6]],[[1,3]],[[4,10]]]

//n

//[[1,2],[5,6]]
//        i
//[[1,3]]
//        j
//[[4,10]]
//          k

//holder [ [1,3], [4,10] ]
//out [[3, 4]]


//[[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
//[[1,3],[6,7]]
//         i
//[[2,4]]
//        j
//[[2,5],[9,12]]
//              k

//holder [ [1,5],[6,7], [9,12]]
//out [ [5,6], [7,9]]

//TC:O(mn)*O(n) = O(mn^2) -> use PQ O(mnlogn)
//SC:O(mn)