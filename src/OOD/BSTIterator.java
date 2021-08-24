package OOD;

import myObject.BinaryTreeConverter;
import myObject.TreeNode;

import java.util.*;

public class BSTIterator {

    ArrayDeque<TreeNode> stack = new ArrayDeque<>();
    TreeNode helper;

    public BSTIterator(TreeNode root) {
        //add as many node to stack until to the left most leaf node (smallest)
        helper = root;
        stackUp();
    }

    public int next() {
        TreeNode next = null;
        if (hasNext()){
            next = stack.pollFirst();
            helper = next.right; //when poll, means the node's left subtree is finish polling, go to right
        }
        stackUp();
        return next == null ? Integer.MIN_VALUE : next.key;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    private void stackUp(){
        //to add as many element to the stack after polling current elements, also moves pointer
        while (helper != null){
            stack.offerFirst(helper);
            helper = helper.left;
        }
    }

    public static void main(String[] args){

        BinaryTreeConverter btc = new BinaryTreeConverter();
        Integer[] input = new Integer[]{7,3,15,null,null,9,20};
        myObject.TreeNode root = btc.constructTree(input);

        BSTIterator obj = new BSTIterator(root);
        //"next","next","hasNext","next","hasNext","next","hasNext","next","hasNext
        System.out.println(obj.next());
        System.out.println(obj.next());
        System.out.println(obj.hasNext());
        System.out.println(obj.next());
        System.out.println(obj.hasNext());
        System.out.println(obj.next());
        System.out.println(obj.hasNext());
        System.out.println(obj.next());
        System.out.println(obj.hasNext());

    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */

//clarification: inOrder traversal iterator
//pointer will be like a inOrder traversal array pointer
//   5
// 3   7
//1 4  6 9
//first pointer be smallest element ->
//hasNext -> if root.left != null, root = root.left;

//helper = root; before call hasNext, go all the way down to left most available element
//hasNext = !stack.isEmpty() = true
//stack [ 5,
//        h
//res 1, 3, 4, 6, 7, 9, 5

//when call Next(), pop top element from tree, and keep adding aavilable next element to stack top until null - O(h) -> for available nodes to be call next time
//when call hasNext - O(1)
//when call
//SC:O(h) for stack size be the path from root to leaf
