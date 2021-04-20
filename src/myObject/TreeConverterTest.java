package myObject;

import java.util.List;
import myObject.BinaryTreeConverter;

public class TreeConverterTest {

    public TreeNode getSmallestLargeNode(TreeNode root){
        //find and deattach the smallest node from the tree and return that node. Assume root is not null
        TreeNode cur = root.left;
        TreeNode prev = root;
        while (cur.left != null){
            prev = cur;
            cur = cur.left;
        }
        prev.left = cur.right;
        return cur;
    }


    public static void main(String[] args) {
        BinaryTreeConverter btc = new BinaryTreeConverter();
        Integer[] input = new Integer[]{1, 3, 4, 2, null, null, 6};
//		Integer[] input = new Integer[]{5,4,6};
        TreeNode root = btc.constructTree(input);
        System.out.println(root.key);
        System.out.println(root.left.key);
        System.out.println(root.right.key);

        List<Integer> treeArray = btc.deconstructureTree(root);
        System.out.println(treeArray);

        TreeConverterTest mbt = new TreeConverterTest();
        TreeNode res = mbt.getSmallestLargeNode(root);
        System.out.println(res.key);

    }
}
