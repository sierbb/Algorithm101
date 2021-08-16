//package OOD;
//
//import java.util.*;
//
///**
// * // This is the interface that allows for creating nested lists.
// * // You should not implement it, or speculate about its implementation
// * public interface NestedInteger {
// *
// *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
// *     public boolean isInteger();
// *
// *     // @return the single integer that this NestedInteger holds, if it holds a single integer
// *     // Return null if this NestedInteger holds a nested list
// *     public Integer getInteger();
// *
// *     // @return the nested list that this NestedInteger holds, if it holds a nested list
// *     // Return empty list if this NestedInteger holds a single integer
// *     public List<NestedInteger> getList();
// * }
// */
//
//public class NestedIterator implements Iterator<Integer> {
//
//    private Deque<NestedInteger> stack = new ArrayDeque<>();
//
//    public NestedIterator(List<NestedInteger> nestedList) {
//        for (int i=nestedList.size()-1; i>=0; i--){
//            stack.offerFirst(nestedList.get(i));
//        }
//    }
//
//    @Override
//    public Integer next() {
//        if (!hasNext()){
//            return -1;
//        }
//        return stack.pollFirst().getInteger();
//    }
//
//    @Override
//    public boolean hasNext() {
//        while (!stack.isEmpty() && !stack.peekFirst().isInteger()){
//            //poll and push the top element back to stack until we see an integer
//            NestedInteger cur = stack.pollFirst();
//            List<NestedInteger> list = cur.getList();
//            for (int i=list.size()-1; i>=0; i--){
//                stack.offerFirst(list.get(i));
//            }
//        }
//        return !stack.isEmpty();
//    }
//}
//
///**
// * Your NestedIterator object will be instantiated and called as such:
// * NestedIterator i = new NestedIterator(nestedList);
// * while (i.hasNext()) v[f()] = i.next();
// */
//
////Clarification:
////Use a stack to flatten the stack top only when we call hasNext.
////If stack top is a list, we flatten it until it is a interger
////[[1,3],2,[4,5]] ->
////stack [ [4,5,], 2, 3, 1
////hasNext() -> check if stack.size() > 0
////next() -> if stack top is a list, pop and flatten it and push it back to stack in reverse order
//
////TC:O(n) ini; hasNext O(1); next O(1) or if stack top is list, O(n/k)
////SC:O(n)
//
//
