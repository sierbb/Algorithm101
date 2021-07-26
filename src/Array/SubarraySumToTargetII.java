package Array;

import java.util.*;

public class SubarraySumToTargetII {

    public int numOfSubarraySumToK(int[] nums, int k) {
        //C: Find the number of subarray that sums to k. Return the count.
        //A: array not null or empty; sum can fit into 32-bit signed integer; elements can <0
        //R: Build a prefixSum array, and store all index and their prefixSum into Map<Integer, List<Integer>>
        //When we at last can linear scan back(why, because it's easier to do subtract from the end), find numbers of possible subarray from the list of index.
        //for i, find prefixSum[i]-k in map
        //TC:O(n^2); SC:O(n)
        if (nums == null || nums.length == 0) return 0;
        int[] prefixSum = new int[nums.length+1];
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(0, new ArrayList<Integer>());
        map.get(0).add(0);
        for (int i=1; i<=nums.length; i++){ //i is first i letters
            prefixSum[i] = prefixSum[i-1] + nums[i-1];
            if (!map.containsKey(prefixSum[i])){
                map.put(prefixSum[i], new ArrayList<Integer>());
                map.get(prefixSum[i]).add(i);
            }else {
                System.out.println(map.get(prefixSum[i]));
                map.get(prefixSum[i]).add(i);
            }
        }
        //now scan back from the end, look up in map for prefixSum[i]-target
        int count = 0;
        for (int i=nums.length; i>=1; i--){
            int remain = prefixSum[i]-k;
            if (map.containsKey(remain)){
                //we dont want the index that is larger than us, so we must add a condition down there.
                //Still need to do linear scan at worst case?
                List<Integer> indexes = map.get(remain);
                for (Integer idx : indexes){
                    if (idx < i){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args){
        SubarraySumToTargetII obj = new SubarraySumToTargetII();
//        int[] input = new int[]{0,0,2,1,1,1,3,4,3,4,2,6,0,0,3,0,2,3,1,0,0,4,0,4,4,3,2,10,2,3,3,6,1,6,0,4,9,4,1,2,6,1,6,9,8,1,5,1,4,9,9,5,8,8,1,2,0,1,10,2,6,10,2,1,0,5,10,5,6,10,7,4,2,9,8,8,2,6,10,7};
        int[] input = new int[]{0,0,2};
        System.out.println(obj.numOfSubarraySumToK(input, 211));

    }
}
