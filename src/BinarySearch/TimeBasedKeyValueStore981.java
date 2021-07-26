package BinarySearch;

import java.util.*;

public class TimeBasedKeyValueStore981 {
    private Map<String, List<Node>> map;

    /** Initialize your data structure here. */
    public TimeBasedKeyValueStore981() {
        this.map = new HashMap<String, List<Node>>();
    }

    public void set(String key, String value, int timestamp) {
        //use binary search to look for the list to find the position to insert the new node. Use List.set(idx, obj)
        if (!map.containsKey(key)){
            map.put(key, new ArrayList<Node>());
            map.get(key).add(new Node(value, timestamp));
            return;
        }
        int pos = findInsertPosition(map.get(key), timestamp);
        map.get(key).add(pos, new Node(value, timestamp));
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) return "";
        return findLargestSmallerrTarget(map.get(key), timestamp);
    }

    class Node{
        private int timestamp;
        private String value;

        public Node(String value, int timestamp){
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    private int findInsertPosition(List<Node> list, int time){
        //find the position to insert new timestamp to
        int start = 0, end = list.size()-1;
        while (start+1 < end){
            int mid  = start + (end-start)/2;
            if (list.get(mid).timestamp == time) return mid;
            else if (list.get(mid).timestamp > time) end = mid;
            else start = mid;
        }
        //at most two elements left, find out the one that is larger than it?
        if (list.get(start).timestamp >= time) return start-1;
        else if (list.get(end).timestamp <= time) return end+1;
        else return start; //in the middle of start and end
    }

    private String findLargestSmallerrTarget(List<Node> list, int time){
        //find the target in list
        int start = 0, end = list.size()-1;
        while (start+1 < end){
            int mid  = start + (end-start)/2;
            if (list.get(mid).timestamp == time) return list.get(mid).value;
            else if (list.get(mid).timestamp > time) end = mid-1;
            else start = mid;
        }
        //if not found target, we will have two elements left, find out which one is largest smaller
        if (list.get(end).timestamp <= time) return list.get(end).value;
        else if (list.get(start).timestamp <= time && time <= list.get(end).timestamp) return list.get(start).value;
        else return "";
    }

    public static void main(String[] args){
        TimeBasedKeyValueStore981 obj = new TimeBasedKeyValueStore981();
        obj.set("foo","bar",1);
        String param_2 = obj.get("foo",1);
        System.out.println(param_2);
        obj.set("foo","bar2",4);
        String param_3 = obj.get("foo",4);
        System.out.println(param_3);
        String param_4 = obj.get("foo",5);
        System.out.println(param_4);
        String param_5 = obj.get("foo",0);
        System.out.println(param_5);
    }

}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeBasedKeyValueStore obj = new TimeBasedKeyValueStore();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */

