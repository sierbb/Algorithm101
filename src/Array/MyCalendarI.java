package Array;

import java.util.*;

public class MyCalendarI {

    List<int[]> books;

    public MyCalendarI() {
        books = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int i=0; i < books.size(); i++){
            int[] item = books.get(i);
            if ( start < item[1] && item[0] < end ){
                return false;
            }
        }
        books.add(new int[]{start, end});
        return true;
    }
}


class MyCalendarM2 {

    TreeNode root;
    private boolean found; //global marker represents whether we've found a place to book the current event

    public MyCalendarM2() {
        this.found = false;
        this.root = null;
    }

    public boolean book(int start, int end) {
        //Look up for possible interval in tree
        if (root == null){
            root = new TreeNode(start, end);
            return true;
        }else {
            //we need to return the result that tells us whether we've found the possible interval in BST?
            root = insert(root, start, end);
            return found;
        }
    }

    private TreeNode insert(TreeNode root, int start, int end){
        //Traverse BST and find a possible interval and insert the interval as a treenode if possible
        //base case: found a place to book new event
        if (root == null){
            this.found = true;
            return new TreeNode(start, end);
        }
        if (end <= root.start){
            root.left = insert(root.left, start, end);
        }else if (start >= root.end){
            root.right = insert(root.right, start, end);
        }else {
            //when end > start and start < end, has a conflict, return immediately
            this.found = false;
            return root;
        }
        return root;
    }

    //  (10,20)
    //  /   \
//  (1,2)         if insert (20,30)

    class TreeNode{
        private int start;
        private int end;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
}

/**
 * Your MyCalendar object will be instantiated and called as such:
 * MyCalendar obj = new MyCalendar();
 * boolean param_1 = obj.book(start,end);
 */

//clarification:
//return whether we can book or not when scanning the request
//keep track of the current linar start and end time? And check whether we can insert into them?
//s: 10
//e: 20

//M1: brute force n^2

//  10<15<20, so false;
//  20>20 yes


//M2:
//   [[], [10, 20], [65, 75], [20, 30]]
// <10, s>, <20, e>, * <65, s>, <75, e>

//binary search for time in between, check for the prev and next event and see if we can insert.
//If yes, insert? How to keep it sorted and at the same time each to insert?
//We can construct our own BST and use that to look up possible interval
//TC:O(logn) if BBST
//SC:O(n) for size of BST