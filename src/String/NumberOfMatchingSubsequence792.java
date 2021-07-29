package String;

import java.util.*;

public class NumberOfMatchingSubsequence792 {
    public int numMatchingSubseq(String s, String[] words) {
        //corner case
        if (s == null || words == null || words.length == 0) return 0;
        //build map for position of each unique char
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if (!map.containsKey(c)){
                map.put(c, new ArrayList<Integer>());
            }
            map.get(c).add(i);
        }
        int count = 0;
        for (int i=0; i<words.length; i++){
            if (checkWord(words[i], map)){
                count++;
            }
        }
        return count;
    }

    private boolean checkWord(String word, Map<Character, List<Integer>> map){
        //check each word[i] char by char for possible pos in map
        int pos = -1;
        for (int m=0; m<word.length(); m++){
            char cur = word.charAt(m);
            if (!map.containsKey(cur)){
                return false;
            }
            pos = binarySearch(map.get(cur), pos);
            if (pos == -1){
                return false;
            }
        }
        return true;
    }

    private int binarySearch(List<Integer> list, int pos){
        //find first ele in list larger than pos
        int start = 0, end = list.size()-1;
        while (start+1 < end){
            int mid = start+(end-start)/2;
            if (list.get(mid) > pos) end = mid;
            else start = mid+1;
        }
        //post processing
        if (list.get(start) > pos) return list.get(start);
        else if (list.get(end) > pos) return list.get(end);
        else return -1;
    }

    public static void main(String[] args){
        String[] words = new String[]{"bb", "ace", "aec"};
        String s = "aebcdce";
        NumberOfMatchingSubsequence792 obj = new NumberOfMatchingSubsequence792();
        System.out.println(obj.numMatchingSubseq(s, words));
    }
}
