package Array;

import java.util.*;

/**
 * Given a circular integer array,  find the next greater element of each element in it. If no such an element then give it -1.
 */
public class NextGreaterNumberII {

    public int[] nextGreaterElement(int[] nums) {
        //C: We want to find the next greater element of each element. And its a circlar array. Return the value of the next larger element.
        //A: array is not null or empty; return -1 if not found
        //R: M1: brute force O(n^2);
        //M2: Use a monotone stack to store the index smaller than n, then when scan through each element, if current element larger than stack top, means current is the next larger of stack top.
        //Then pop stack top and check on next stack top. If stack top is larger than current, push current to stack top.
        //TC:O(n); SC:O(n) for space for stack
        if (nums == null || nums.length == 0) return new int[0];
        //initialization
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        //Scan through the circular array, keep previous smaller element in stack, and check whether current element is the next greater of the stack tops.
        for (int i=0; i<nums.length*2; i++){
            int curIdx = i%nums.length;
            while (!stack.isEmpty() && nums[stack.peekFirst()] < nums[curIdx]){
                res[stack.peekFirst()] = nums[curIdx];
                stack.pollFirst();
            }
            if (i < nums.length){
                stack.offerFirst(i);
            }
        }
        return res;
    }
}
