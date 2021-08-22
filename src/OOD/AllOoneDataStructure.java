package OOD;

import java.util.*;

public class AllOoneDataStructure {

    private Map<String, Integer> freqMap = new HashMap<>();
    private Map<Integer, DoubleListBucket> nodeMap = new HashMap<>();
    private DoubleListBucket head; //min
    private DoubleListBucket tail; //max
    private int globalMax = Integer.MIN_VALUE;
    private int globalMin = Integer.MAX_VALUE;

    /** Initialize your data structure here. */
    public AllOoneDataStructure() {
        head = new DoubleListBucket(Integer.MIN_VALUE);
        tail = new DoubleListBucket(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev = head;
        freqMap = new HashMap<>();
        nodeMap = new HashMap<>();
    }

    /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
    public void inc(String key) {
        //1.First update freMap, as key's freq has incr
        if (!freqMap.containsKey(key)){
            freqMap.put(key, 0);
        }
        freqMap.put(key, freqMap.get(key)+1);
        //2. move key from bucket to another bucket
        int freq = freqMap.get(key);
        if (!nodeMap.containsKey(freq)) {
            nodeMap.put(freq, new DoubleListBucket(freq)); //what to do with the new Node
        }
        DoubleListBucket newNode = nodeMap.get(freq);
        newNode.addKey(key);
        DoubleListBucket oldNode = head; //assume oldNode not exists, check whether oldNode exists
        //Idea: Set default to oldNode as head, so whenever oldNode exists, we can always generally insert newNode.
        if (freq-1 > 0){
            oldNode = nodeMap.get(freq-1);
            oldNode.removeKey(key);
        }
        //Add new node right after oldNode, because newNode now has freq +1 then oldNode
        // -> we need to keep the order of nodes by their freq as ascending, as we are using head & tail as min & max value pointer
        //Need to first add newNode then remove oldNode, since removing oldNode will lost the change to insert newNode after old
        if (newNode.set.size() == 1){ //if newNode size == 1, means its a real new Node needs to be inserted to list
            newNode.next = oldNode.next;
            newNode.prev = oldNode;
            oldNode.next = newNode;
            newNode.next.prev = newNode;
        }
        //if no key falls in old freq, do we need to remove the freq key from map and detach oldNode from list)
        //just check whether oldNode is head(should not, if oldNode exists it should not be head directly)
        if (oldNode.set.size() == 0){
            // nodeMap.remove(oldNode.freq);
            if (oldNode != head){
                oldNode.prev.next = oldNode.next;
                oldNode.next.prev = oldNode.prev;
            }
        }
        //if newNode is tail, we aleady deal with it
        //head-> 1 -> 2 -> tail, remove 1
        //      old   new
    }

    /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
    public void dec(String key) {
        //1. move the key to another freq bucket
        //And consider if need to detach old bucket from list, or add new bucket
        int freq = freqMap.get(key)-1;
        freqMap.put(key, freq);
        //Idea：Set default to old, newNode, start from nodemap to find oldNode, newNode; then consider add newNode/remove oldNode
        DoubleListBucket oldNode = nodeMap.get(freq+1);
        oldNode.removeKey(key);
        if (freq > 0){
            DoubleListBucket newNode = nodeMap.getOrDefault(freq, new DoubleListBucket(freq));
            newNode.addKey(key);
            if (newNode.set.size() == 1){ //need to add newNode to list (even for frequency 0), insert before oldNode
                newNode.next = oldNode;
                newNode.prev = oldNode.prev;
                newNode.prev.next = newNode;
                oldNode.prev = newNode;
            }
        }
        if (oldNode.set.size() == 0 && oldNode != tail){ //need to remove oldNode from list
            oldNode.next.prev = oldNode.prev;
            oldNode.prev.next = oldNode.next;
        }
    }

    /** Returns one of the keys with maximal value. */
    public String getMaxKey() {
        //return the key set's iterator()
        if (tail.prev.set.size() != 0){
            return tail.prev.set.iterator().next();
        }
        return "";
    }

    /** Returns one of the keys with Minimal value. */
    public String getMinKey() {
        if (head.next.set.size() != 0){
            return head.next.set.iterator().next();
        }
        return "";
    }

    class DoubleListBucket{
        //act as a bucket for set of key in same frequency
        //Each bucket node is double linked to othet bucket node
        public int freq;
        public DoubleListBucket next;
        public DoubleListBucket prev;
        public Set<String> set;
        public DoubleListBucket(int freq){
            this.freq = freq;
            this.set = new HashSet<String>();
        }

        public void addKey(String key){
            this.set.add(key);
        }

        public void removeKey(String key){
            this.set.remove(key);
        }
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */

