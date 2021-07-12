package KWayProblem;

import java.util.*;

public class MergeKSortedList {

    class ListNode {
        public int value;
        public ListNode next;

        public ListNode(int value) {
            this.value = value;
            next = null;
        }
    }


    public class Solution {
        public ListNode merge(List<ListNode> listOfLists) {
            //M1: Iterative merge. TC:O(nk^2); SC:O(n)
            //M2: Binary reduction. TC:O(nk*logk); SC:O(nk)
            //M3: Merge k elements each time using PQ. TC:O(nklogk); SC:O(k)
            if (listOfLists == null || listOfLists.size() == 0) return null;
            PriorityQueue<Node> minHeap = new PriorityQueue<Node>(listOfLists.size(), new Comparator<Node>() {
                @Override
                public int compare(Node n1, Node n2) {
                    if (n1.node.value == n2.node.value) return 0;
                    return n1.node.value > n2.node.value ? 1 : -1;
                }
            });
            ListNode dummy = new ListNode(-1);
            ListNode cur = dummy;
            for (int i = 0; i < listOfLists.size(); i++) {
                if (listOfLists.get(i) != null) {
                    minHeap.offer(new Node(i, listOfLists.get(i)));
                }
            }
            while (!minHeap.isEmpty()) {
                Node min = minHeap.poll();
                if (min.node.next != null) {
                    minHeap.offer(new Node(min.listIndex, min.node.next));
                }
                cur.next = min.node;
                cur = cur.next;
            }
            return dummy.next;
        }

        class Node {
            //a Node will tells you which linkedlist it is at
            public int listIndex;
            public ListNode node;

            public Node(int listIndex, ListNode node) {
                this.listIndex = listIndex;
                this.node = node;
            }
        }
    }
}
