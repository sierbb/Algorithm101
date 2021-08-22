package String;

import java.util.*;

public class GroupAnagram {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if (strs == null || strs.length == 0){
            return res;
        }
        Map<String, List<String>> map = new HashMap<>();
        for (int i=0; i < strs.length; i++){
            String s = strs[i];
            int[] freq = new int[26]; //for char occurrence
            for (int j=0; j < s.length(); j++){
                freq[s.charAt(j) - 'a']++;
            }
            //create the string for char count
            StringBuilder sb = new StringBuilder();
            for (int k=0; k < freq.length; k++){
                sb.append("#"); //separater!!
                sb.append(freq[k]);
            }
            String charCount = sb.toString();
            if (!map.containsKey(charCount)){
                map.put(charCount, new ArrayList());
            }
            map.get(charCount).add(s);
        }
        //now return the result from map
        for (Map.Entry<String, List<String>> e : map.entrySet()){
            res.add(e.getValue());
        }
        return res;
    }

    public static void main(String[] args){
        GroupAnagram obj = new GroupAnagram();
//        String[] input = new String[]{"eat","tea","tan","ate","nat","bat"};
        String[] input = new String[]{"bdddddddddd","bbbbbbbbbbc"};
        System.out.println(obj.groupAnagrams(input));
    }
}

