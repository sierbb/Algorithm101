package myObject;

import java.util.List;

import myObject.ArrayList;
import myObject.TreeNode;
import myObject.ArrayQueue;

public class BinaryTreeConverter {

    public TreeNode constructTree(Integer[] array) {
        //We are using an extra array to store the TreeNode elements, so that we revisit the next level's TreeNodes after adding them to array from last level
        //M1: since it's a complete tree, calculate the index range on each layer, for each node, calculate it's children's index, get childer key from input array, and attach to the node
        //M1-2: can also fill it from bottom level to top, still calculate each node's children index and append children. But this time we can get children from placeholder array(bottom has been filled)
        //TC:O(n); SC:O(n)

        if (array == null || array.length == 0) {
            return null; //root is null
        }
        //assume it is a complete tree, means except possibly last level, all level are filled and nodes are as far left as possible
        TreeNode root = new TreeNode(array[0]);
        int idx = 0;
        TreeNode[] holder = new TreeNode[array.length];
        holder[0] = root;

        while (idx < array.length) { //can improve to the last non-leaf node of the tree -- is it really n/2-1?
            TreeNode cur = holder[idx];
            if (cur == null) {
                idx++;
                continue;
            }
            //fill in every child of each node on this level
            int lChild_idx = idx*2+1;
            //if left_idx is out of bound, we dont do anything
            if (lChild_idx < array.length) {
                if (array[lChild_idx] == null) { //null in array means null node for TreeNode
                    cur.left = null;
                } else {
                    cur.left = new TreeNode(array[lChild_idx]);
                }
                holder[lChild_idx] = cur.left;
            }
            int rChild_idx = idx*2+2;
            //if right_idx is out of bound, then we dont do anything
            if (rChild_idx < array.length) {
                if (array[rChild_idx] == null) {
                    cur.right = null;
                } else {
                    cur.right = new TreeNode(array[rChild_idx]);
                }
                holder[rChild_idx] = cur.right;
            }
            idx++;
        }
        return root;
    }

    public ArrayList<Integer> deconstructureTree(TreeNode root) {
        //deconstructure a tree into array. Using BFS and a fifo
        //we dont know how many nodes yet, so we can use an ArrayList.
        //Termination rule: when queue is empty, means no more node is available
        //TC:O(n), SC:O(n) ~= number of nodes on tree's last level=2^(height-1)=2^(logn-1)=n/2
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (root == null) {
            return res;
        }

        ArrayQueue<TreeNode> fifo = new ArrayQueue<TreeNode>(); //a queue can not accept null node so how do we do it? Use our own queue instead
        fifo.offer(root);
        while(!fifo.isAllNull()) { //isAllNull = true means there are no more non-null nodes left in the tree
            TreeNode cur = fifo.poll();
            if (cur == null) { //our own ArrayQueue can store null node
                res.add(null); //ArrayList accepts null element
                fifo.offer(null); //even if it is null, still need to add to queue, to fake a complete tree view
                fifo.offer(null);
            } else {
                res.add(cur.key);
                fifo.offer(cur.left);
                fifo.offer(cur.right);
            }
        }
        //TODO: what if return type is []Integer, convert it to an array
        return res;
    }
}
