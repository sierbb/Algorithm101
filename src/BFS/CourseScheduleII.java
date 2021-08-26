package BFS;

import java.util.*;

public class CourseScheduleII {


    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses == 0 || prerequisites == null){
            return new int[0];
        }

        Map<Integer, Integer> in = new HashMap<>();
        Map<Integer, List<Integer>> out = new HashMap<>();
        for (int i=0; i < numCourses; i++){
            in.put(i, 0);
        }
        for (int i=0; i < prerequisites.length; i++){
            int[] pair = prerequisites[i]; //[1, 0]
            int preq = in.getOrDefault(pair[0], 0);
            preq++;
            in.put(pair[0], preq);
            List<Integer> outList = out.getOrDefault(pair[1], new ArrayList<Integer>());
            outList.add(pair[0]);
            out.put(pair[1], outList);
        }


        //Start from the first course, find the other courses to take
        Queue<Integer> fifo = new LinkedList<>(); //store the courses ready to take, 0 preq

        //find the course with 0 prerequisite
        List<Integer> first = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : in.entrySet()){
            if (e.getValue().equals(0)){
                fifo.offer(e.getKey());
                first.add(e.getKey());
            }
        }
        if (first.size() == 0){ //cant start
            return new int[0];
        }
        for (int f : first){
            in.remove(f);
        }

        List<Integer> sol = new ArrayList<>();
        while (!fifo.isEmpty()){
            int cur = fifo.poll();
            sol.add(cur);
            if (out.containsKey(cur)){
                for ( Integer next : out.get(cur) ){
                    in.put(next, in.get(next)-1);
                    if (in.get(next) == 0){
                        //this course is ready
                        fifo.offer(next);
                        in.remove(next);
                    }
                }
            }
        }
        //check if there's courses left, if so, invalid
        if (in.size() > 0){
            return new int[0];
        }
        //convert sol to int[]
        int[] res = new int[sol.size()];
        for (int i=0; i<sol.size(); i++){
            res[i] = sol.get(i);
        }
        return res;
    }
}

//clarification: topological sort
//for each course, we have it adjacency list for in and out
//in: how many courses we should take before it
//out: the courses depends on it

//[[1,0],[2,0],[3,1],[3,2]]
// 0 - out[1, 2], in [] - pick; Map<int, int[]> in; Map<int, int[]> out
// 1 - out[3], in[]
// 2 - out[3], in[]
// 3  - out[],  in[]

//finished:
//0, 1 and 2, 3

//pre.length = n
//TC:O(n) +O(n) + O(n) = O(n)
//SC:O(n)+O(n) = O(n)
