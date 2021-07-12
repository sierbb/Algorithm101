package KWayProblem;

import java.util.*;

/**
 * Merge K sorted array into one big sorted array in ascending order.
 * Assumptions
 * The input arrayOfArrays is not null, none of the arrays is null either.
 */
class MergeKSortedArrayOld {

    public int[] merge(int[][] arrayOfArrays) {
        //Algorithm: M1: Iterative way. Merge A1 and A2 into A12. Then merge A12 and A3 into A123. TC:O(k^2*n), SC:O(n)
        //M2: Binary reductiion. Merge A1, A2 into A12 and A3, A4 into A34  O(nk). Then merge A12 and A34 into final, logk layers. TC:O(nk*logk), SC:O(n)
        //M3: All together. K pointers and a minheap. Put the k elements into minHeap O(logk), poll the smallest and insert new element, move pointer.
        //The heap needs to record the array, index in array and the value of the element.
        //TC:O(nklogk); SC:O(k)
        if (arrayOfArrays == null || arrayOfArrays.length == 0){
            return new int[0];
        }
        int nArrays = arrayOfArrays.length;
        PriorityQueue<Cell> minHeap = new PriorityQueue<Cell>(new Comparator<Cell>(){
            @Override
            public int compare(Cell c1, Cell c2){
                if (c1.value == c2.value){
                    return 0;
                }
                return c1.value > c2.value ? 1: -1;
            }
        });
        //Imagine k pointers, each pointer initially placed at index 0
        //Put the first elements from each of k arrays into minHeap
        int length = 0;
        for (int i = 0; i < nArrays; i++){
            length+=arrayOfArrays[i].length;
            if (arrayOfArrays[i].length >0){
                minHeap.offer(new Cell(i,0, arrayOfArrays[i][0]));
            }
        }
        int[] res = new int[length];
        int index = 0;
        while (!minHeap.isEmpty()){
            //Each time poll one element from minHeap and offer the next element from the same array into heap
            //If the array runs out, we dont need to do anything
            Cell ele = minHeap.poll();
            if (ele.y + 1 < arrayOfArrays[ele.x].length){
                minHeap.offer(new Cell(ele.x, ele.y + 1, arrayOfArrays[ele.x][ele.y + 1]));
            }
            res[index] = ele.value;
            index++;
        }
        return res;
    }

    class Cell{
        private int x; //array number
        private int y; //index number
        private int value; //number value
        public Cell(int x, int y, int value){
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
}

public class MergeKSortedArray {

    public int[] merge(int[][] arrayOfArrays) {
        //M1:Iterative merge. Merge two arrays at a time. Assume array.length=n, TC:O(nk^2); SC:O(nk)
        //M2:Binary reduction: Merge every two pairs of arrays at at time, A&B, C&D -> AB&CD. Each round TC:O(nklogk); SC:O(nk)
        //M3:Take one elements from each of the array and sort the first k elements using a minHeap of size k. The element should record the indice of array, and the position in the array.
        //So we knows which one is the next element to be added to minHeap. TC:O(nklogk) for insert and poll from minHeap. SC:O(k)
        if (arrayOfArrays == null || arrayOfArrays.length == 0) return new int[0];
        PriorityQueue<Node> minHeap = new PriorityQueue<Node>(arrayOfArrays.length, new Comparator<Node>(){
            @Override
            public int compare(Node n1, Node n2){
                if (n1.val == n2.val) return 0;
                return n1.val > n2.val ? 1:-1;
            }
        });
        //Initialize the pq
        int total = 0;
        for (int i=0; i<arrayOfArrays.length; i++){
            if (arrayOfArrays[i].length > 0){
                minHeap.offer(new Node(i, 0, arrayOfArrays[i][0]));
                total+= arrayOfArrays[i].length;
            }
        }
        int[] res = new int[total];
        int resIdx = 0;
        while (!minHeap.isEmpty()){
            Node cur = minHeap.poll();
            res[resIdx] = cur.val;
            resIdx++;
            if (cur.idx+1 < arrayOfArrays[cur.arrayIdx].length){
                minHeap.offer(new Node(cur.arrayIdx, cur.idx+1, arrayOfArrays[cur.arrayIdx][cur.idx+1]));
            }
        }
        return res;
    }

    static class Node{
        public int arrayIdx;
        public int idx;
        public int val;

        public Node(int arrayIdx, int idx, int val){
            this.arrayIdx = arrayIdx;
            this.idx = idx;
            this.val = val;
        }
    }
}


//    M1 Iterative way
//// two pointers i and j
//// buffer[A1[i], ] --> O(n)
//// A1
//O(2n)
//   A2
//
//           A12 O(3n)
//           A3   A123
//           A4 O(4n)
//           ...
//           Ak
//// 2n + 3n + 4n ... kn
//// TC:O(nk^2); SC:O(kn)
//
//           M2 Binary reduce
//           A1    A12   O(2n)
//           A2                A1234 4n
//           A3    A34   O(2n)
//           A4
//           ...               A5678 4n
//           O(8n)
//           Ak-1  Ak-1,k O(2n)  ...
//           Ak
//           O(nk)          O(nk) ... O(nk)  * O(logk)
//
//           TC:O(nklogk); SC:O(nk)
//
//           M3 merge k array all together, k pointers.
//           A1 i_1
//           A2     i_2
//           A3
//           ...
//           Ak i_k
//
//           array [0, 2, 3, ...] size k
//
//           k elements
//           minheap of size k
//           i_1, i_2... i_k --> minheap<num value, index of the array>.
//        poll minHeap -- > array,
//class Entry{
//    int value;
//    int index;
//    int indiceInArray;
//}
//if i_1 == A1.length-1.
//        TC: O(klogk) (first k elements) + O(2*logk)*(n-1)k  = O(nklogk)
//        SC: O(k)


