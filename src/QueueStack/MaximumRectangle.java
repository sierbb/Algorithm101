package QueueStack;

import java.util.*;

public class MaximumRectangle {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;
        }
        int nRow = matrix.length;
        int nCol = matrix[0].length;
        int globalMax = 0;
        int[] heights = new int[nCol];
        for (int i=0; i < nRow; i++){
            //update heights by appending 1s to each column  index
            for (int j=0; j < nCol; j++){
                if (matrix[i][j]=='0'){
                    heights[j] = 0;
                }else {
                    heights[j]+=1;
                }
            }
            globalMax = Math.max(globalMax, getLargestRectangleInHistogram(heights));
        }
        return globalMax;
    }

    private int getLargestRectangleInHistogram(int[] heights){
        //get largest rectangle area in histogram heights
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        for (int j=0; j<= heights.length; j++){
            //when to poll from stack? when current height smaller than previous heights
            if (j == heights.length || !stack.isEmpty() && heights[j] < heights[stack.peekFirst()]){
                int curHeight = j == heights.length ? 0 : heights[j];
                while (!stack.isEmpty() && curHeight < heights[stack.peekFirst()]){
                    int prevH = heights[stack.pollFirst()];
                    int leftBoundry = stack.isEmpty() ? 0 : stack.peekFirst()+1;
                    int rightBoundry = j;
                    maxArea = Math.max(maxArea, prevH * (rightBoundry - leftBoundry));
                }
            }
            stack.offerFirst(j);
        }
        return maxArea;
    }
}

//h[3,1,3,2,2] - row 2
//s[          0]

//a[3,5,3,6,2] -> 6


//clarification: Use the method used in histogram method, for each accumulated rows area, calculate largest histrogram and update globalMax.
//Heights[i] represents the height of bar j in histrogram which bottom row at row i
//e.g
//h[3,1,3,2,2] - row 2
//h[4,0,0,3,0]

//matrix.length=n;
//matrix[0].length=m
//for each i in n, have area of historgram  O(n)
//    monotonic stack to find largest rectangle.  O(m)

//TC:O(nm)
//SC:O(m)
