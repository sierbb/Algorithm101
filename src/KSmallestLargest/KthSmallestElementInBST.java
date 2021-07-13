package kSmallestLargest;

import myObject.TreeNode;

/**
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.
 * Note: You may assume k is always valid, 1 <=k <= BST's total elements.
 *
 * Follow up:
 * What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?
 */
public class KthSmallestElementInBST {
    public int kthSmallest(TreeNode root, int k) {
        //Algorithm: BST -> inorder traversal is a sorted array -> first k elements arer kth smallest.
        //Pass a counter in the inOrder traversal. Stop when we reach the kth smallest element.
        //Assume BST is not null.
        //BST is modified often ->
        //TC:O(n) for traversing all treenodes; SC:O(height) for call stack
        if (root.left == null && root.right == null){
            return root.key;
        }
        int[] count = new int[2];
        kthSmallestHelper(root, k, count);
        return count[1];
    }

    public void kthSmallestHelper(TreeNode root, int k, int[] count){
        //InOrder traversal means we will first print the left most child node of the tree.
        //How do we know we need to start the count at that node?
        //What do we expect from children? update counter as they are traversed. What to return to parent? updated counter adding myself.
        if (root == null){
            return;
        }
        if (count[0] == k){ //if we've traversed k elements from the previous round (0, k-1) element, this round it is k
            return;
        }
        //we basically need to check whether we've found k elements after each possible addtion process
        //so should check again after traverse left
        kthSmallestHelper(root.left, k, count);
        if (count[0] == k){
            return;
        }
        count[0] = count[0]+1; //root node
        count[1] = root.key; //do we need to update counter's value along the way?
        kthSmallestHelper(root.right, k, count);
    }
}
