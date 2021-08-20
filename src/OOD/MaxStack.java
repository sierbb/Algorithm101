package OOD;

import java.util.*;

public class MaxStack {

    private TreeMap<Integer, List<Node>> map;
    private DoublyLinkedList ddl;

    /** initialize your data structure here. */
    public MaxStack() {
        map = new TreeMap<Integer, List<Node>>();
        ddl = new DoublyLinkedList();
    }

    public void push(int x) {
        //List.add(Node) + map.put()
        //add node to map
        List<Node> list;
        if (map.containsKey(x)){
            list = map.get(x);
        }else{
            list = new ArrayList<Node>();
            map.put(x, list);
        }
        Node newNode = new Node(x);
        list.add(newNode);
        //add node to LinkedList
        ddl.add(newNode);
    }

    public int pop() {
        //list.remove(list.getLast())
        //remove listNode from List, remove Node from map
        if (ddl.size == 0){
            return Integer.MIN_VALUE;
        }
        Node toPop = ddl.getLast();
        ddl.remove(toPop);
        List<Node> list = map.get(toPop.val);
        list.remove(list.size()-1);
        if (list.size() == 0){
            map.remove(toPop.val);
        }
        return toPop.val;
    }

    public int top() {
        //list.getLast();
        if (ddl.size > 0){
            return ddl.getLast().val;
        }
        return Integer.MIN_VALUE;
    }

    public int peekMax() {
        //map.getLastKey()
        return map.lastKey();
    }

    public int popMax() {
        //map.getLastKey() + list.remove()
        int max = peekMax();
        List<Node> list = map.get(max);
        Node toPop = list.get(list.size()-1);
        list.remove(list.get(list.size()-1));
        ddl.remove(toPop);
        if (list.size() == 0){
            map.remove(max);
        }
        return max;
    }

    public static class DoublyLinkedList{

        private Node head;
        private Node tail;
        private int size;

        public DoublyLinkedList(){
            size = 0;
        }

        public void add(Node n){
            if (size == 0){
                head = n;
                tail = n;
                head.next = tail;
                tail.next = head;
            }else {
                tail.next = n;
                n.prev = tail;
                tail = tail.next;
            }
            size++;
        }

        public void remove(Node n){
            //remove specific node from list
            if (n == head){
                if (n == tail){ //only one node
                    head = null;
                    tail = null;
                }else {
                    //more than 1 node, update head
                    n.next.prev = null;
                    head = n.next;
                    n.next = null;
                }
            }else{ //n is either at the middle or at the end
                Node next = n.next;
                if (next != null){
                    next.prev = n.prev;
                }
                if (tail == n){//update tail
                    tail = n.prev;
                }
                n.prev.next = n.next;
                n.next = null;
                n.prev = null;
            }
            size--;
        }

        public Node getLast(){
            //return last node added
            return tail;
        }

    }

    public static class Node{
        private int val;
        private Node prev;
        private Node next;
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

//Clarification:
//Node(prev, next, val) ->
//DoublyLinkedList<Node>
//maxHeap -> to keep track of max
//Map<Integer, List<Node>> -> keep track of val:location of the node
//---> maxHeap + Map -> TreeMap -> O(1) for adding elements, to get Max from TreeMap.lastKey() get hightest key
//TreeMap.firstKey() -> min


//push(logn) -> rebalance heap
//pop(logn) -> rebalance heap
//top(1)
//peekMax (1)
//popMax(logn) -> rebalance heap

//SC:O(n)+O(n)+O(n) = O(n)