package Tree;


class TreeNode {
  int val;
    TreeNode left;
  TreeNode right;
  TreeNode() {}
  TreeNode(int val) { this.val = val; }
  TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
  }
}

public class BinaryTreeLongestConsecutiveSequenceII549 {

    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        longestConsecutiveHelper(root, max);
        return max[0];
    }

    private int[] longestConsecutiveHelper(TreeNode root, int[] max){
        if (root == null) return new int[]{0,0};
        int[] out = new int[2];
        out[0] = 1;
        out[1] = 1;
        if (root.left != null){
            int[] left = longestConsecutiveHelper(root.left, max);
            if (root.left.val+1 == root.val){
                out[1] = Math.max(left[1]+1, out[1]);
            }else if (root.left.val-1==root.val){
                out[0] = Math.max(left[0]+1, out[0]);
            }
        }
        if (root.right != null){
            int[] right = longestConsecutiveHelper(root.right, max);
            if (root.right.val+1==root.val){
                out[1] = Math.max(right[1]+1, out[1]);
            }else if (root.right.val-1==root.val){
                out[0] = Math.max(right[0]+1, out[0]);
            }
        }
        //update globalMax
        max[0] = Math.max(max[0], out[0]+out[1]-1);
        return out;
    }
}

//Assume tree is not empty; A path can be any node to any node (clarify)
//Can break several times; consecutive can be asc or desc

//Use recursion
//int[asc, desc] -> incuding root itself
//case 1: if left+1==root: desc = left[1]+1;   check on right and update desc
//case 2: if left-1==root: asc=left[0];  check on right and update asc
//update max on each node: max= max(max, asc+desc-1);
//to return the [asc, desc] to parent recursion

//  3 [3,3]
// / \
//2   4
//  /   / \
// 1    5  5 [1,1]

