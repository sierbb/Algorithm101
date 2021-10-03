package Tree;

import java.util.*;

public class ClosestBinarySearchTreeValueII {

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        if (root == null || k == 0){
            return res;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>(){
            @Override
            public int compare(Integer i1, Integer i2){
                //the distance to target is larger, the higher priority
                if (i1 == i2){
                    return 0;
                }
                return Math.abs(i1 - target) < Math.abs(i2 - target) ? 1: -1;
            }
        });
        helper(root, maxHeap, target, k);
        //collect all the elements from maxHeap
        while (!maxHeap.isEmpty()){
            res.add(maxHeap.poll());
        }
        return res;
    }

    private void helper(TreeNode root, PriorityQueue<Integer> maxHeap, double target, int k){
        //do inOrder traversal
        if (root == null){
            return;
        }
        helper(root.left, maxHeap, target, k);
        if (maxHeap.size() < k){
            maxHeap.offer(root.val);
        }else {
            if ( Math.abs(root.val - target) < Math.abs(maxHeap.peek() - target) ){
                maxHeap.poll();
                maxHeap.offer(root.val);
            }
        }
        helper(root.right, maxHeap, target, k);
    }
}


class Solution2 {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<>();
        if (root == null || k == 0){
            return res;
        }
        //Sliding window of size k, and see if we need to remove the left most element and add right most element
        helper(root, res, target, k);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res, double target, int k){
        if (root == null){
            return;
        }
        helper(root.left, res, target, k);
        if (res.size() < k){
            res.add(root.val);
        }else {
            if ( Math.abs((double)res.get(0) - target) > Math.abs((double)root.val - target) ){
                res.remove(0);
                res.add(root.val);
            }
        }
        helper(root.right, res, target, k);
    }
}


//clarification:
//BST -> if do inOrder traversal, it will be sorted

//M1: use PQ of size K + inOrder traversal
//TC:O(nlogk)
//SC:O(k) for PQ size

//M2: use sliding window + inOrder traversal
//TC:O(n)
//SC:O(k)