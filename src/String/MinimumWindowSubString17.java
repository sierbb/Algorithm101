package String;

import java.util.*;

public class MinimumWindowSubString17 {

    public String minWindow(String s, String t) {
        //corner case
        if (s == null || t == null || s.length() == 0 || t.length() == 0 || s.length() < t.length()){
            return "";
        }

        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i<t.length(); i++){
            if (!map.containsKey(t.charAt(i))){
                map.put(t.charAt(i), 0);
            }
            map.put(t.charAt(i), map.get(t.charAt(i))+1);
        }

        //linear scan sliding window
        int i =0, j=0;
        int valid  = 0;
        int globalMin = Integer.MAX_VALUE;
        int oL = 0, oR = 0;
        while (j < s.length()){
            char sc = s.charAt(j);
            if (map.containsKey(sc)){
                map.put(sc, map.get(sc)-1);
                if (map.get(sc) == 0){
                    valid++;
                }
            }
            while (valid == map.size() && i<=j){ //when i==j, we can still remove that only element
                if (j-i < globalMin){
                    globalMin = j-i;
                    oL = i;
                    oR = j;
                }
                char ci = s.charAt(i);
                if (map.containsKey(ci)){
                    map.put(ci, map.get(ci)+1);
                    if (map.get(ci) == 1){
                        valid--;
                    }
                }
                i++;
            }
            j++;
        }

        return globalMin == Integer.MAX_VALUE? "": s.substring(oL, oR+1);
    }

    public static void main(String[] args){
        String s = "ADOBECODEBANC";
        MinimumWindowSubString17 obj = new MinimumWindowSubString17();
        System.out.println(obj.minWindow(s, "ABC"));

    }
}
