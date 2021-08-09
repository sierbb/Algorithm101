package OOD;

import java.util.*;
import java.util.Date;

public class TimeBasedKeyValueStore {

    private Map<String, List<Node>> map;
    private final Date date= new Date();

    /** Initialize your data structure here. */
    public TimeBasedKeyValueStore() {
        map = new HashMap<>();
    }

    public long set(String key, String value, long timestamp) {
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Node>());
        }
        map.get(key).add(new Node(value, timestamp)); //set timestamp is increasing
        return timestamp;
    }

    public long set(String key, String value){
        //if timestamp is not provided, return system timestamp
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Node>());
        }
        long cur = date.getTime();
        map.get(key).add(new Node(value, cur));
        return cur;
    }

    public String get(String key, long timestamp) {
        if (!map.containsKey(key)) return "";
        List<Node> l = map.get(key);
        Node n = binarySearch(l, timestamp);
        return n== null ? "" : n.s;
    }

    public String get(String key){
        List<Node> list = map.get(key);
        if (list == null){
            return "";
        }
        return list.get(list.size()-1).s;
    }

    public Node binarySearch(List<Node> l, long timestamp){
        if (l.size() == 0) return null;
        int start = 0;
        int end = l.size()-1;
        while (start +1 < end){
            int mid = start + (end-start)/2;
            if (l.get(mid).time == timestamp) return l.get(mid);
            else if (l.get(mid).time > timestamp) {
                end = mid-1;
            } else {
                start = mid;
            }
        }
        //have 1-2 nodes left
        if (l.get(end).time <= timestamp) return l.get(end);
        else if (l.get(start).time <= timestamp) return l.get(start);
        return null;
    }


    class Node{
        private String s;
        private long time;
        public Node(String s, long time){
            this.s = s;
            this.time = time;
        }
    }
}






//clarification:
//
// if set() timestamp are increasing -> sorted List of Node

//TimeMap<String, List<Node>> map

// Node{
//     String val;
//     int time;
// }

//List<Node> -> O(logn) use binary search to get node.timestamp <= target given List<Node> ascendiing by node.time

//set() - O(1)+O(1) = O(1)
//get() - O(1)+O(logn) = O(logn)

//if set() timestamp are not guarantee increasing -> unsorted list of Node
//Can NOT use TreeMap -> set(key, val, timestamp) will be O(logn) to re-balance the tree when insert
//Can use binary search


//if need O(1) for get(val, timestamp) for exact matching timestamp:
//Use HashMap<String, HashMap(timestamp, value)>
