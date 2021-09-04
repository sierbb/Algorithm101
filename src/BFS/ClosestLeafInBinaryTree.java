package BFS;

import myObject.TreeNode;

import java.util.*;

/**
 * Given the root of a binary tree where every node has a unique value and a target integer k,
 * return the value of the nearest leaf node to the target k in the tree.
 * Nearest to a leaf means the least number of edges traveled on the binary tree to reach any leaf of the tree.
 * Also, a node is called a leaf if it has no children.
 */
public class ClosestLeafInBinaryTree {
    public int findClosestLeaf(TreeNode root, int k) {
        //Step 1: build graph by traverse through the root to every leaf node using DFS
        Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
        DFS(null, root, graph);

        //Step 2: start from the target node (map.get(k)), run BFS to calculate path cost until we meet a leaf node
        Set<TreeNode> set = new HashSet<>();
        int[] dis = new int[1];
        for (Map.Entry<TreeNode, List<TreeNode>> e : graph.entrySet()){
            if(e.getKey().key == k){
                return BFS(e.getKey(), dis, graph, set); //return when see a leaf node

            }
        }
        return -1;
    }

    private void DFS(TreeNode parent, TreeNode root, Map<TreeNode, List<TreeNode>> graph){
        //Build edge between parent(current) and children node
        List<TreeNode> out  = graph.getOrDefault(root, new ArrayList<TreeNode>());
        //Can add parent even if parent is null (to distinguich betweeen root and leaf node)
        out.add(parent);
        if (root.left != null){
            out.add(root.left);
            DFS(root, root.left, graph);
        }
        if (root.right != null){
            out.add(root.right);
            DFS(root, root.right, graph);
        }
        graph.put(root, out);
    }

    private int BFS(TreeNode start, int[] dis, Map<TreeNode, List<TreeNode>> graph, Set<TreeNode> set){
        Queue<TreeNode> fifo = new LinkedList<>();
        fifo.offer(start);
        set.add(start);
        while (!fifo.isEmpty()){
            TreeNode cur = fifo.poll();
            if (cur == null){
                continue;
            }
            if (graph.get(cur).size() <= 1){
                //if is a leaf node, return the value, can include itself?
                return cur.key;
            }
            for (TreeNode nei : graph.get(cur)){
                if (!set.contains(nei)){
                    fifo.offer(nei);
                    set.add(nei);
                }
            }
        }
        return -1;
    }

}

//clarification: number of nodes in the path is the length.
//target k must be in tree.
//Path: root-> distance to target

//Consider tree as a graph -> get the shortest distance between target and a leaf (the node without out edges).
//1.How to build the graph? each treenode is a node in graph, bi-direct? The children/parent can both be considered edges.
//-> Use a out Map<TreeNode, list<TreeNode>> to record each node and its out edges
//2.How to efficiently generate children? Since bi-directed, may need to dedupe? Otherwise it may go back to the starting node.
//-> Start from the target node, use the out map to expand with BFS, each layer increase distance by 1?

//TC:O(n) for DFS traverse tree
//SC:O(n) for map + O(height) for DFS

