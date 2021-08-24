package Tree;

import myObject.BinaryTreeConverter;
import myObject.TreeNode;

import java.util.*;

//public class SerializeAndDeserializeTree {

//    // Encodes a tree to a single string.
//    public String serialize(TreeNode root) {
//        if (root == null){
//            return "";
//        }
//        Queue<TreeNode> fifo = new LinkedList<>();
//        fifo.offer(root);
//        StringBuilder sb = new StringBuilder();
//        while (!fifo.isEmpty()){
//            int size = fifo.size();
//            StringBuilder curSb = new StringBuilder();
//            for (int i=0; i<size; i++){
//                TreeNode cur = fif   // Encodes a tree to a single string.
////    public String serialize(TreeNode root) {
////        if (root == null){
////            return "";
////        }
////        Queue<TreeNode> fifo = new LinkedList<>();
////        fifo.offer(root);
////        StringBuilder sb = new StringBuilder();
////        while (!fifo.isEmpty()){
////            int size = fifo.size();
////            StringBuilder curSb = new StringBuilder();
////            for (int i=0; i<size; i++){
////                TreeNode cur = fifo.poll();
////                if (cur == null){
////                    if (sb.length() > 0){
////                        sb.append(',');
////                    }
////                    sb.append('n');
////                    continue;
////                }
////                //add this node to sb, form a string for cur.val, 123 -> ‘321’ in sb
////                int val = cur.key;
////                while (val > 0){
////                    int digit = val % 10;
////                    curSb.append((char)(digit + '0'));
////                    val = val / 10;
////                }
////                if (sb.length() > 0){
////                    sb.append(",");
////                }
////                sb.append(curSb.reverse());
////                curSb = new StringBuilder();
////                fifo.offer(cur.left);
////                fifo.offer(cur.right);
////            }
////        }
////        //have the sb with all the elements
////        return sb.toString();
////    }
////
////
////    // Decodes your encoded data to tree.
////    public TreeNode deserialize(String data) {
////        if (data == null || data.length() == 0){ //corner case
////            return null;
////        }
////        //Split String into multiple strings by “,”, build the node from there
////        List<String> list = new ArrayList<>();
////        StringBuilder sb = new StringBuilder();
////        for (int i=0; i < data.length(); i++){
////            //gather digits for a new element
////            char ch = data.charAt(i);
////            if (ch == ','){
////                //reset the sb
////                list.add(sb.toString());
////                sb = new StringBuilder();
////            }else {
////                sb.append(ch);
////            }
////        }
////        list.add(sb.toString()); //add the last string
////        if (list.size() == 0){ //if list not getting anything, return null
////            return null;
////        }
////        //now we have a list of string, each for a node. First root node is the first element
////        Queue<Pair> fifo = new LinkedList<>();
////        TreeNode root = new TreeNode(stringToInt(list.get(0)));
////        fifo.offer(new Pair(root, 0));
////        while (!fifo.isEmpty()){
////            Pair cur = fifo.poll();
////            int lIdx = cur.posIdx*2+1;
////            int rIdx = cur.posIdx*2+2;
////            if (lIdx < list.size() && !list.get(lIdx).equals("n")){
////                TreeNode left = new TreeNode(stringToInt(list.get(lIdx)));
////                cur.node.left = left;
////                fifo.offer(new Pair(left, lIdx));
////            }
////            if (rIdx < list.size() && !list.get(rIdx).equals("n")){
////                TreeNode right = new TreeNode(stringToInt(list.get(rIdx)));
////                cur.node.right = right;
////                fifo.offer(new Pair(right, rIdx));
////            }
////        }
////        return root;
////    }
////
////    private int stringToInt(String s){
////        //convert string to int
////        int base = 0;
////        for (int i=0; i<s.length(); i++){
////            base = base * 10 + s.charAt(i) - '0';
////        }
////        return base;
////    }
////
////
////    static class Pair{
////        private TreeNode node;
////        private int posIdx;
////        public Pair(TreeNode node, int posIdx){
////            this.node = node;
////            this.posIdx = posIdx;
////        }
////    }o.poll();
//                if (cur == null){
//                    if (sb.length() > 0){
//                        sb.append(',');
//                    }
//                    sb.append('n');
//                    continue;
//                }
//                //add this node to sb, form a string for cur.val, 123 -> ‘321’ in sb
//                int val = cur.key;
//                while (val > 0){
//                    int digit = val % 10;
//                    curSb.append((char)(digit + '0'));
//                    val = val / 10;
//                }
//                if (sb.length() > 0){
//                    sb.append(",");
//                }
//                sb.append(curSb.reverse());
//                curSb = new StringBuilder();
//                fifo.offer(cur.left);
//                fifo.offer(cur.right);
//            }
//        }
//        //have the sb with all the elements
//        return sb.toString();
//    }
//
//
//    // Decodes your encoded data to tree.
//    public TreeNode deserialize(String data) {
//        if (data == null || data.length() == 0){ //corner case
//            return null;
//        }
//        //Split String into multiple strings by “,”, build the node from there
//        List<String> list = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        for (int i=0; i < data.length(); i++){
//            //gather digits for a new element
//            char ch = data.charAt(i);
//            if (ch == ','){
//                //reset the sb
//                list.add(sb.toString());
//                sb = new StringBuilder();
//            }else {
//                sb.append(ch);
//            }
//        }
//        list.add(sb.toString()); //add the last string
//        if (list.size() == 0){ //if list not getting anything, return null
//            return null;
//        }
//        //now we have a list of string, each for a node. First root node is the first element
//        Queue<Pair> fifo = new LinkedList<>();
//        TreeNode root = new TreeNode(stringToInt(list.get(0)));
//        fifo.offer(new Pair(root, 0));
//        while (!fifo.isEmpty()){
//            Pair cur = fifo.poll();
//            int lIdx = cur.posIdx*2+1;
//            int rIdx = cur.posIdx*2+2;
//            if (lIdx < list.size() && !list.get(lIdx).equals("n")){
//                TreeNode left = new TreeNode(stringToInt(list.get(lIdx)));
//                cur.node.left = left;
//                fifo.offer(new Pair(left, lIdx));
//            }
//            if (rIdx < list.size() && !list.get(rIdx).equals("n")){
//                TreeNode right = new TreeNode(stringToInt(list.get(rIdx)));
//                cur.node.right = right;
//                fifo.offer(new Pair(right, rIdx));
//            }
//        }
//        return root;
//    }
//
//    private int stringToInt(String s){
//        //convert string to int
//        int base = 0;
//        for (int i=0; i<s.length(); i++){
//            base = base * 10 + s.charAt(i) - '0';
//        }
//        return base;
//    }
//
//
//    static class Pair{
//        private TreeNode node;
//        private int posIdx;
//        public Pair(TreeNode node, int posIdx){
//            this.node = node;
//            this.posIdx = posIdx;
//        }
//    }
//}


public class SerializeAndDeserializeTree {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TreeNode root, StringBuilder sb){
        if (root == null){
            sb.append("null");
            sb.append(','); //separator
            return;
        }
        sb.append(String.valueOf(root.key));
        sb.append(','); //separator
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0){
            return null;
        }
        String[] dataArray = data.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(dataArray));
        if (list.get(0).equals("null")){
            return null;
        }
        return deserializeHelper(list);
    }

    private TreeNode deserializeHelper(List<String> list){
        if (list.size() == 0 ) {
            return null;
        }
        if (list.get(0).equals("null")){ //we must also remove this null so the next node can be access by other recursion function
            list.remove(0);
            return null;
        }
        //attach the newly created nodes to root
        TreeNode newRoot = new TreeNode(Integer.valueOf(list.get(0)));
        list.remove(0); //remove so next recursion call will access the left node
        newRoot.left = deserializeHelper(list);
        newRoot.right = deserializeHelper(list);
        return newRoot;
    }


// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

//clarifiaction: use preOrder traversal to traverse the tree
//root -> left -> right
//serialize:
//compress into String by appending elements to the end in each recursion call

//TC:O(n)
//SC:O(n) for call stack DFSO(height) + O(n) string builder to store the middle elements

    public static void main(String[] args){
        BinaryTreeConverter btc = new BinaryTreeConverter();
        Integer[] input = new Integer[]{1,2,3,null,null,4,5,6,7}; //???
        myObject.TreeNode root = btc.constructTree(input);
        myObject.ArrayList<Integer> treeArray = btc.deconstructureTree(root);
        System.out.println(Arrays.toString(treeArray.toArray()));

        SerializeAndDeserializeTree deser = new SerializeAndDeserializeTree();
        SerializeAndDeserializeTree ser = new SerializeAndDeserializeTree();
        String serialized = ser.serialize(root);
        System.out.println(serialized);
        TreeNode ans = deser.deserialize(serialized);
        System.out.println(ans.key);
        System.out.println(ans.left.key);
        System.out.println(ans.right.key);
    }

}