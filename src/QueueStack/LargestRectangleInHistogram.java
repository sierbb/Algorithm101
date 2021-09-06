package QueueStack;

import java.util.*;

public class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0){
            return 0;
        }
        //M2: use monotonic stack, stores the index of each bar
        Deque<Integer> stack = new ArrayDeque<>();
        int globalMax = 0;

        for (int i=0; i <= heights.length; i++){ //pad a 0 at the end to finish the pop
            //when to pop from stack: when height is shorted than stack top
            if ( i== heights.length || !stack.isEmpty() && heights[i] < heights[stack.peekFirst()]){
                int curHeight = i==heights.length? 0 : heights[i];
                while (!stack.isEmpty() && curHeight < heights[stack.peekFirst()]){
                    int preH = heights[stack.pollFirst()];
                    //after poll, left boundry = peek + 1
                    int leftBoundry = stack.isEmpty() ? 0 : stack.peekFirst()+1;
                    int rightBoundry = i;
                    globalMax = Math.max(globalMax, preH * (rightBoundry - leftBoundry));
                }
            }
            //Now to push to stack, when height is increasing or stack is empty
            stack.offerFirst(i);
        }
        return globalMax;
    }
}

//clarification: calculate the area of the lagest rectangles in histogram

//M1: for i, check on left and right for the far most boundry that is higher than heights[i]
//TC:O(n^2) for nested scan
//SC:O(1)

//M2: Monotonic stack
//Scan from left to right, use stack to keep track of the increasing height's bars. (monitonic increasing in height)
//If a bar has shorted height, means its the right boundry of the previous bars, so we can calculate the max rectangle area for the bars before
//if height[i] > stack.peek.height, pop() and calculate area, pop until stack.peek.height is smaller or equal.

//    [2,1,5,6, 2,3]
//s   [  1,          0
//a   [2,5,10,6,6,3
//max = 10

//TC:O(n)
//SC:O(n)

