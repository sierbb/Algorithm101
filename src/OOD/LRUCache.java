package OOD;

import java.util.*;

/**
 * Least recent used cache
 */
public class LRUCache {
    private Map<Integer, Node> index; //index for quick access to the value from key
    private List<Node> storage; //storage for quick add / delete least used node from storage
    private int capacity;
    private int size;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size= 0;
        this.index = new HashMap<>();
        this.storage = new LinkedList<>();
    }

    public int get(int key) {
        Node node = this.index.get(key); //return null if not exists
        if (node == null){
            return -1;
        }
        //update the visited infomation by moving node as the latest
        visitNode(node);
        return node.val;
    }

    public void put(int key, int value) { //O(1)
        //consider setting duplicate key
        if (!index.containsKey(key)){
            Node newN = new Node(key, value);
            //update the node to both index and storage
            insertNode(newN);
        }else {
            //update the value of the existing Node
            Node node = this.index.get(key);
            node.setValue(value);
            visitNode(node); //based on the test, when update it means its recently used
        }
    }

    private void visitNode(Node node){
        //update the node as latest from the storage
        //corner case is node is head; node is the head + tail; node is the tail; no node at all?
        if (node == head && node == tail || node == tail){ //case 1 & 2
            return;
        }
        if (node == head){ //case 3, not the only node, update head
            head = node.next;
            node.next = null;
            tail.next = node;
            node.prev = tail;
        }else {//case 4: node is in middle
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = tail;
            node.next = null;
            tail.next = node;
        }
        tail = node;
    }

    private void insertNode(Node node){
        //assume node does not exist, append node at end of storage list,
        //if list is full capacity, remove the oldest node
        //Case 1: full capactiy, remove and update head
        if (size >= capacity){
            Node oldest = head; //remove from storage
            head = head.next;
            oldest.next = null;
            index.remove(oldest.key); //use the key to reversely remove it from index
            size--;
        }
        //append new node. corner case head == null
        if (head == null){ //means tail is also null, new node becomes the head and tail
            head = node;
            tail = node;
        }else {
            head.prev = null; //update new head's prev
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        index.put(node.key, node); //insert to index
        size++;
    }

    public static class Node{
        private int key;
        private int val;
        private Node next; //doubly-linkedlist
        private Node prev; //doubly-linkedlist

        public Node(int key, int val){
            this.key = key;
            this.val = val;
        }

        public void setValue(int val){
            this.val = val;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */


//clarification: We must be able to read a key's latest value in O(1) -> HashMap to find key, then how to know the latest timestamp?
//Use a linear data structure that can represents time order -> array? simple but not easy to delete.
//-> linkedList? easy to delete insert but takes O(n);
//-> Doubly Linkedlist -> delete insert at O(1) - good

//Map<Key, Node<k,v>> marks the key as well as the location of the node in the storage -> index
//LinkedList<Node> is the real storage, and map can directly access this node.  -> storage

//Put(), O(1), if need to evict, delete head and append to tail -> maintain head and tail
//get(); O(1) to look up key, then use the node ref to get node's value

//Doubly-linkedList node: prev, next, key, val

//Test:
//["LRUCache","put","put","get","put","get","put","get","get","get"]
//[[2],[1,1],[2,2],[1],[3,3],[2],[4,4],[1],[3],[4]]

//["LRUCache","put","put","put","put","get","get"]
//[[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]

//["LRUCache","put","get","put","get","get"]
//[[1],[2,1],[2],[3,2],[2],[3]]