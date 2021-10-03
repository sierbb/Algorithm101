package DFS;

import java.util.*;

interface NestedInteger {
    //Constructor initializes an empty nested list.
//    public NestedInteger();
//
//    // Constructor initializes a single integer.
//    public NestedInteger(int value);//
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    public boolean isInteger();

   // @return the single integer that this NestedInteger holds, if it holds a single integer
   // Return null if this NestedInteger holds a nested list
   public Integer getInteger();

    // Set this NestedInteger to hold a single integer.
    public void setInteger(int value);

    // Set this NestedInteger to hold a nested list and adds a nested integer to it.
    public void add(NestedInteger ni);

   // @return the nested list that this NestedInteger holds, if it holds a nested list
    // Return empty list if this NestedInteger holds a single integer
    public List<NestedInteger> getList();
 }


public class NestedListWeightSumII {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int[] sum = new int[1];
        if (nestedList.size() == 0){
            return sum[0];
        }
        int[] depthSum = new int[1];
        int[] integerSum = new int[1];
        int[] maxDepth = new int[1];
        DFS(nestedList, 1, depthSum, integerSum, maxDepth);
        System.out.println(depthSum[0]);
        return integerSum[0]* (maxDepth[0]+1) - depthSum[0];
    }

    private void DFS(List<NestedInteger> nestedList, int depth, int[] depthSum, int[] integerSum, int[] maxDepth){
        maxDepth[0] = Math.max(maxDepth[0], depth);
        for (NestedInteger item : nestedList){
            if (item.isInteger()){
                depthSum[0] += item.getInteger() * depth;
                integerSum[0] += item.getInteger();
            }else {
                DFS(item.getList(), depth+1, depthSum, integerSum, maxDepth);
            }
        }
    }
}

//clarification:
//M1: use DFS with one pass
//if maxDepth = n
//depth 1 - weight n-1+1 = n
//depth 2 - weight n-2+1 = n-1
//depth 3 - weight n-3+1 = n-2
//...
//depth n = weight n-n+1 = 1

//So when do DFS, we can have the depth sum = sum(Integer * depth) -> this is the subtraction part
//Then we need to let every Integer * n (maxDepth) then subtract the depth sum
//TC:O(k) k = number of nested element
//SC:o(n) n = maxDepth