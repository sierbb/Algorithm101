package myObject;

import java.util.Arrays;

public class HashMap<K, V>{

    private static final int defaultCapacity = 2;
    private static final float defaultLoadFactor = 0.75f; //if it is public, outside can do e.loadFactor

    private Node<K, V>[] array;
    private static int size;  //actual nodes buffered in the array.
    // int size == defaults to public, non-static, non-final int size;
    private static float loadFactor; //factor at which the array must rehash(resize) to reduce hash collision

    public static class Node<K, V>{  //inner class, should be public static
        V value;
        K key;
        Node<K, V> next; //needs a next node so can build a linkedlist

        public Node(K key, V value){ //constructor
            this.value = value;
            this.key = key;
            next = null;
        }

        public V getValue(){
            return this.value;
        }

        public K getKey(){
            return this.key;
        }

        public void setValue(V value){
            this.value = value;
        }
    }

    //constructor 1
    public HashMap(int capacity, float loadFactor){
        if (capacity <= 0){
            throw new IllegalArgumentException("Invalid capacity");
        }
        this.size = 0;
        //can not use genetic type to create array. Need to case to generic type after create the array
        this.array = (Node<K,V>[]) new Node[capacity]; //and java doesn’t know Node’s content until we cast it
        this.loadFactor = loadFactor;
    }

    //constructor 2
    public HashMap(){
        this(defaultCapacity, defaultLoadFactor);
    }


    private int getHashCode(K key){
        if (key == null) {
            return 0;
        }
        return key.hashCode() & 0x7FFFFFFF;   //to make it non-negative
        //if hex, 4 bit each position = 1+2+4+8=15
        //if binary, 0111 1111 1111 1111 1111 1111 1111 1111 --> 1 bit each position
    }

    private int getHashIndex(K key){
        return getHashCode(key) % array.length;
    }

    private boolean equalsKey(K k1, K k2){
        return  k1 == k2 || k1 != null && k1.equals(k2);
    }

    private boolean equalsValue(V v1, V v2){
        return  v1 == v2 || v1 != null && v1.equals(v2);
    }


    public V get(K key){
        int index = getHashIndex(key);
        Node<K, V> curNode = array[index]; //array[index] is linkedlist’s head, can be null
        while (curNode != null){
            if (equalsKey(curNode.getKey(), key)){
                return curNode.getValue();
            }
            curNode = curNode.next;
        } // no key in the hashmap
        return null;
    }

    public V put(K key, V value){
        //if no existing node, insert and return null. If it exists return old value. maintain size
        if (needRehashing()){
            rehash();
        }

        int index = getHashIndex(key);
        Node<K, V> curNode = array[index];
        //different keys (different hashCode) can generate same index, so will try find from the LinkedList for target key
        while (curNode != null){
            if (equalsKey(curNode.getKey(), key)){
                //update value
                V oldValue = curNode.getValue();
                curNode.setValue(value);
                return oldValue; //exit early
            }
            curNode = curNode.next;
        }
        //if not find the Node, put it to array head
        Node<K, V> newNode = (Node<K, V>) new Node(key, value);
        newNode.next = array[index];
        array[index] = newNode;
        size++; //only if insert new node
        return null;
    }


    public V remove(K key){
        //if not exist node, return null. If exist, delete and return old value
        int index = getHashIndex(key);
        Node<K, V> curNode = array[index];
        Node<K, V> dummy = new Node<K, V>(null, null);
        dummy.next = array[index];
        Node<K, V> prevNode = dummy;
        while (curNode != null){
            if (equalsKey(curNode.getKey(), key)){
                prevNode.next = curNode.next;
                array[index] = dummy.next;
                size--;
                return curNode.getValue();
            }
            prevNode = curNode;
            curNode = curNode.next;
        }
        return null;
    }

    public boolean containsValue(V value){
        //loop through all node in the array to find the target value. O(n)
        for (Node<K, V> curNode : array){
            while (	 curNode  != null){
                if (equalsValue(curNode.getValue(), value)){
                    return true;
                }
                curNode = curNode.next;
            }
        }
        return false;
    }

    public boolean containsKey(K key){
        //loop through all node in the target index LinkedList, find the key
        int index = getHashIndex(key);
        Node<K, V> curNode = array[index];

        if (curNode == null){
            return false;
        }
        while (curNode != null){
            if (equalsKey(curNode.getKey(), key)){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    private boolean needRehashing(){
        //when will we want to run this? Each time we put() an Entry
        //return size >= array.length * loadFactor;
        float ratio = (size+0.0f) / array.length;
        return ratio >= loadFactor;
    }

    private void rehash(){
        //extend capacity to double size
        Node<K, V>[] newArray = (Node<K, V>[]) new Node[2*array.length];
        Node<K, V>[] oldArray = array;
        array = newArray;
        //Now array.length has doubled, loop all the elements in array and LinkedLists, calculate new hashIndex for each node
        for (Node<K,V> node : oldArray){
            while ( node != null){
                int index = getHashIndex(node.getKey()); //new index
                Node<K, V> next = node.next; // next node in old array
                //insert into head of linkedlist
                node.next = array[index]; //node take over the linkedlist
                array[index] = node; //put node back as array head
                node = next;
            }
        }

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        //clear all elements in array
        Arrays.fill(this.array, null);
        size = 0;
    }

    public void printIndexAndNode(){
        //print all non-null nodes from array
        System.out.println("\n--------- printing index");
        for (Node<K, V> node : array) {
            if (node == null){
                continue;
            }
            System.out.println("Index: " + getHashIndex(node.getKey()));
            while (node != null){
                System.out.println(" Key: " + node.getKey() + ", Value: " + node.getValue());
                node = node.next;
            }
        }
        System.out.println("--------- Finished printing index\n");
    }

}