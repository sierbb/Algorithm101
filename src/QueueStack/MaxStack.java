package QueueStack;

import java.util.*;

public class MaxStack {
    private TreeMap<Integer, List<Node>> map; //can have more than one node with same value
    private DoubleLinkedList dll;

    /** initialize your data structure here. */
    //M2: TreeMap + doubly linkedlist
    public MaxStack() {
        this.map = new TreeMap<Integer, List<Node>>(); //keep track of max, defaults to maxHeap
        this.dll = new DoubleLinkedList();
    }

    public void push(int x) {
        //add to stack (ddl)
        Node n = dll.add(x); //return the tail Node
        if (!map.containsKey(x)){
            map.put(x, new ArrayList<Node>());
        }
        map.get(x).add(n); //add the list node so when remove it still valid
    }

    public int pop() {
        //first get element out of ddl then out of treeMap
        Node n = dll.pop(); //unlink
        //remove from treeMap
        List<Node> list = map.get(n.val);
        list.remove(list.size()-1);
        if (list.size() == 0) map.remove(n.val);
        return n.val;
    }

    public int top() {
        return dll.peek();
    }

    public int peekMax() {
        //call treeMap's maximum api?
        return map.lastKey(); //???
    }

    public int popMax() {
        int max = peekMax();
        List<Node> list = map.get(max);
        Node node = list.remove(list.size()-1); //remove top one
        if (list.size() == 0) map.remove(max);
        //pop from doubleLinkedlist
        dll.unlink(node);
        return max;
    }


    class DoubleLinkedList{
        private Node head;
        private Node tail;

        public DoubleLinkedList(){
            this.head = new Node(-1);
            this.tail = new Node(-1);
            head.next = tail;
            tail.prev = head;
        }

        public Node add(int val){
            //add to before the dummy tail (-1)
            Node node = new Node(val);
            node.next = tail;
            node.prev = tail.prev;
            tail.prev.next = node;
            tail.prev = node;
            return node;
            //head <-> 3 <-> 2 ->  tail
        }

        public Node pop(){
            //remove last element before tail
            Node toPop = tail.prev;
            return unlink(toPop);
            //head <-> 3 < 2 >
            //      <-> tail
        }

        public int peek(){
            return tail.prev.val;
        }

        public Node unlink(Node node){
            //unlink a node from linkedList (when we pop)
            node.prev.next = node.next;
            node.next.prev = node.prev;
            return node;
        }

    }

    class Node{
        private int val;
        private Node next;
        private Node prev;
        public Node(int val){
            this.val = val;
        }
    }
}

/**
 * Your MaxStack object will be instantiated and called as such:
 * MaxStack obj = new MaxStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.peekMax();
 * int param_5 = obj.popMax();
 */

//M1: 2 stacks, main stack + buffer
//TC:O(n) for popMax, O(1) topMax, pop O(1), top (1)
//SC:O(n)
//e.g
//use 2 queues/stack TC:O(1) for top, O(n) pop
//q1(holder)      [1 ,3, 2]- popMax(3)
//q2(main stack)  [2]  reassign
// q <- [1]  <- O(n) for each pop, O(n) for top?


//M2:
//Max stack - use maxHeap (nope)/TreeMap to keep the maxinum value of existing elements
//popMax - how to remove it from the queue? Does PQ has api to remove by element? O(logn) to rebalance the PQ
//peekMax - O(1)
//top - O(1)
//pop - O(logn) - need search for it O(n), remove from PQ -> TreeMap O(logn) search for max

//Stack:
//a: Use Deque to push and pull from the same side. - O(1) for top O(n) for popMax - No
//b: Use 1/2 fifo queue, O(n) for popMax - No
//c: Doubly Linkedlist as stack - O(1) to remove elements

// TreeMap<Integer, List<Integer>> O(1) to find ListNode and O(1) to remove node from list for popMax.  + O(logn) to rebalance TreeMap


