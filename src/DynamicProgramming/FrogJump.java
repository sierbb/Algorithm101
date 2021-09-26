package DynamicProgramming;

import java.util.*;

public class FrogJump {

    public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0){
            return false;
        }
        int[] min = new int[stones.length];
        int[] max = new int[stones.length];
        Map<Integer, HashSet<Integer>> map = new HashMap<>(); //-> Map<stone index, Set<steps from previous stones>>
        for (int i=0; i<stones.length; i++){
            map.put(i, new HashSet<Integer>());
        }
        map.get(0).add(1); //can do 1 step from position 0

        for (int i = 1; i < stones.length; i++){
            //look back at j, see if j can reach i, also update i's step set
            for (int j = i-1; j >= 0; j--){
                //if j can reach i, i and j are indexes
                int steps = stones[i] - stones[j];
                if (map.get(j).contains(steps)){ //j can reach i
                    map.get(i).add(steps-1);
                    map.get(i).add(steps);
                    map.get(i).add(steps+1);
                }
            }
            //if no one can reach i, i's set will not be updated
        }
        //check whether last index has been updated
        return map.get(stones.length-1).size() > 0;
    }
}

//clarification: first jump must be 1 unit
//The next jump unit based on last jump unit
//only jump on forward direction

// [0,1,3,5,6,8,12,17] -> stones that exists. So 2, 4, 7, ... stone not exists
//  1,2,3,3,

//          6 -> from 5: jump 1, 6 get 1~ 2
//            -> from 3: jump 3, 6 get 2~ 4


//M1: DFS
//at each position, 5
//argument: set(stones), curPos, lastStep

//next jump: 3 cases:
//case 1: curPos+lastStep-1 in set?, lastStep-1
//case 2: curPos+lastStep in set?, lastStep
//case 3: curPos+lastStep+1 in set? , lastStep+1

//for each stone, have 3 options -> base case when curPos == stones.length-1
//TC:O(3^ stones.length)
//SC:O(stones.length) for recursion call stack

//M2:
//Map<Integer i, Set<Integer>> represents the possible steps we can go from position i

//TC:O(n^2)
//SC:O(n)