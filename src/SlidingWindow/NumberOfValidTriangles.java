package SlidingWindow;

import java.util.*;


public class NumberOfValidTriangles {

    public int numOfTriangles(int[] array) {
        //C: Find number of triangles that has three side no duplicate.
        //A: Array not null or empty; array has duplicate elements -> skip same elements? To also avoid duplicate result?
        //R: First sort the array. Then for each element, find from before it the possible number of pairs that has sum larger than it. --> Similar to two sum on sorted array.
        //Use two pointers to find the two sum problem when the third side is fixed.
        if (array == null || array.length == 0) return 0;
        Arrays.sort(array);
        int globalCount = 0;
        for (int i=2; i<array.length; i++){
            //fixed one side and find other two sides with sum larger than nums[i]
            // if (i >=3 && array[i] == array[i-1]) continue; //skip duplicate element for the third edge? No need. They are still different elements.
            int start = 0;
            int end = i-1;
            int count = 0;
            while (start < end){
                if (array[start] + array[end] <= array[i]){
                    start++;
                }else {
                    //since if s+e>i, then any node between start and end can pair with the current end.
                    count+= end-start;
                    end--;
                }
            }
            globalCount+= count;
        }
        return globalCount;
    }

    //Test case:
    //19,52,52,75,37,99,52,195,89,127,71,142,94
    //3,4,5,5

}
