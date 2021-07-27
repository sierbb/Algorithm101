package BinarySearch;

import myObject.TreeNode;

/**
 * Given an integer array nums where the elements are sorted in ascending order, convert it to a height-balanced binary search tree.
 * A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.
 */
public class SortedArrayToBST {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0){
            return null;
        }
        return sortedArrayToBSTHelper(nums, 0, nums.length-1);
    }

    private TreeNode sortedArrayToBSTHelper(int[] nums, int start, int end){
        //1.what to expect from child? Child node
        //2.What to do on current recursion? find current root, split array and attach left&right child
        //3.what to return to parrent? Self root
        if (start > end) return null;
        if (start == end) return new TreeNode(nums[start]);
        int mid = start + (end-start)/2;
        TreeNode cur = new TreeNode(nums[mid]);
        cur.left = sortedArrayToBSTHelper(nums, start, mid-1);
        cur.right = sortedArrayToBSTHelper(nums, mid+1, end);
        return cur;
    }
}
