package PriorityQueue;

import java.util.*;

public class TopKFrequentWordsLexi {
    public List<String> topKFrequent(String[] words, int k) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0){
            return res;
        }
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i < words.length; i++){
            map.put(words[i], map.getOrDefault(words[i], 0)+1);
        }
        PriorityQueue<String> minHeap = new PriorityQueue<>(k, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2){
                if (map.get(s1) == map.get(s2)){//string has its own compareTo function, and its in normal lexi order
                    return s2.compareTo(s1); //smaller lexi has higher priority, but need reverse order here
                }
                return map.get(s1) - map.get(s2);
            }
        });
        //Put all elements into minHeap nlogk
        for (Map.Entry<String, Integer> e : map.entrySet()){
            minHeap.offer(e.getKey());
            if (minHeap.size() > k){
                minHeap.poll();
            }
        }
        //Poll out words which will be in ascending order
        String[] out = new String[k];
        for (int i=k-1; i >= 0; i--){
            out[i] = minHeap.poll(); //smaller (and lexi reverse) is the last
        }
        for (int i=0; i < out.length; i++){
            res.add(out[i]);
        }
        return res;
    }

    // private int compareLexi(String s1, String s2){
    //     //return 1 if s2 lexi higher than s1
    //     int i=0, j=0;
    //     while (i < s1.length() && j < s2.length()){
    //         if (s1.charAt(i) == s2.charAt(j)){
    //             i++;
    //             j++;
    //         }else {
    //             return s2.charAt(i) - s1.charAt(j); //reverse lexi order
    //         }
    //     }
    //     if (i < s1.length()){ //if still letter left in s1
    //         return 1;
    //     }else if (j<s2.length()){
    //         return -1;
    //     }
    //     return 0;
    // }
}

//clarification:
//k <= words.length; ?
//sort by lexicographical order if frequency is the same



//Step1: record frequency of each unique word
//Step2: use PQ of size k to sort them by frequency
//TC:O(nlogk) for poll and push to pq for n times
//SC:O(n)+O(k)=O(n) for map and pq
