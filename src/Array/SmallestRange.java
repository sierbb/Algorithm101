package Array;

import java.util.*;

public class SmallestRange {
    public int[] smallestRange(int[][] arrays) {
        //Smallest range size k
        if (arrays == null || arrays.length == 0) return new int[0];
        //initiliza data structure
        PriorityQueue<Node> minHeap = new PriorityQueue<Node>(arrays.length, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                return n1.val - n2.val;
            }
        });
        int range;
        int max = Integer.MIN_VALUE;
        int min;
        // int [] indexes = new int[array.length]; //defaults to 0
        for (int i=0; i<arrays.length; i++){
            minHeap.offer(new Node(arrays[i][0], i, 0));
            max = Math.max(max, arrays[i][0]);
        }
        min = minHeap.peek().val;
        range = max - min;
        int globalMin = min;
        int globalMax = max;
        //move pointer of min element each time
        boolean stop = false;
        while (!minHeap.isEmpty() && !stop){
            Node cur = minHeap.poll();
            min = cur.val;
            if (max-min < range){
                //need to make sure we take smaller start as min and max
                range = max-min;
                globalMin = min;
                globalMax = max;
            }
            //move pointer and update the max
            if (cur.posIdx == arrays[cur.arrayIdx].length-1){
                stop = true;
            }else {
                int newVal = arrays[cur.arrayIdx][cur.posIdx+1];
                max = Math.max(max, newVal);
                minHeap.offer(new Node(newVal, cur.arrayIdx, cur.posIdx+1));
            }
        }
        return new int[]{globalMin, globalMax};
    }

    class Node{
        private int val;
        private int arrayIdx;
        private int posIdx;
        public Node(int val, int arrayIdx, int posIdx){
            this.val = val;
            this.arrayIdx = arrayIdx;
            this.posIdx = posIdx;
        }
    }

}

