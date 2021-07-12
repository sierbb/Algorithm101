package BinaryTree;

import myObject.TreeNode;

import java.util.*;

/**
 * 667. K Closest Elements in BST
 * Given a binary search tree, a target number and an integer k, return k closest elements to the target. 1 <= K <= number of nodes in the tree.
 *
 * Note:
 * In case of same distance, smaller number is considered closer.
 */
public class KClosestElementInBST {
    public List<Integer> kClosestBST(TreeNode root, int target, int k) {
        //Algorithm: M1: BST -> inOrder traversal is sorted array -> binary search find k closest.
        //TC:O(n)inOrder + O(logn) binary search; SC:O(height) for inOrder traversal
        List<Integer> in = new ArrayList<>();
        inOrder(root, in);
        //from the inOrder traversal result, first find the closest element to target
        int left = 0;
        int right = in.size()-1;
        int closest = -1;
        while (left+1 < right){
            int mid = left+(right-left)/2;
            if (in.get(mid) == target){
                closest = mid;
                break;
            }else if (in.get(mid) > target){
                right = mid;
            }else {
                left = mid;
            }
        }
        closest = closest == -1? Math.abs(in.get(left)-target) <= Math.abs(in.get(right)-target) ? left : right : closest;
        //Find the other k-1 closest elements starting from the closest element
        List<Integer> res = new ArrayList<Integer>();
        res.add(in.get(closest));
        int count = 1;
        int i = closest-1;
        int j = closest+1;
        while (count < k && i>=0 && j<in.size()){
            if (Math.abs(in.get(i)-target) <= Math.abs(in.get(j)-target)){
                res.add(in.get(i));
                i--;
            } else {
                res.add(in.get(j));
                j++;
            }
            count++;
        }
        while (count < k){
            if (i>=0){
                res.add(in.get(i));
                i--;
            }
            if (j<in.size()){
                res.add(in.get(j));
                j++;
            }
            count++;
        }
        return res;
    }

    private void inOrder (TreeNode root, List<Integer> res){
        if (root == null){
            return;
        }
        inOrder(root.left, res);
        res.add(root.key);
        inOrder(root.right, res);
    }
}

class SolutionM2{
    public List<Integer> kClosestBST(TreeNode root, int target, int k) {
        //Algorithm: M1: turn BST into inOrder traversal which is a sorted array, then pick the k closest from the sorted array
        //M2: do inOrder traversal, and use size k sliding window to get the k closest elements. (in-place improvement of 1)
        //M3: First find the closest TreeNode in BST(the other problem), then 对树做中心开花？too hard
        //TC:O(n)traversal+O(k)remove list = O(n+k); SC:O(k)sliding window+O(height) recursion = O(k+height)
        List<Integer> res = new ArrayList<Integer>();
        if (root == null){
            return res;
        }
        kClosestBSTHelper(root, res, target, k);
        return res;
    }

    private void kClosestBSTHelper(TreeNode root, List<Integer> list, int target, int k){
        //inOrder traversal, base case: root == null
        if (root == null){
            return;
        }
        //inOrder: left > root > right
        kClosestBSTHelper(root.left, list, target, k);
        if (list.size() < k){
            list.add(root.key);
        } else { //if list is full, we can check whether the new(root) node is closer than the first element(the one to be remove from sliding window)
            if (Math.abs(list.get(0)-target) > Math.abs(root.key-target)){
                list.remove(0);
                list.add(root.key);
            }
        }
        kClosestBSTHelper(root.right, list, target, k);
    }

}
