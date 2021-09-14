package String;

import java.util.*;

public class LongestStringChain {
    public int longestStrChain(String[] words) {
        if (words == null || words.length == 0){
            return 0;
        }
        //sort so we can start from longest word
        Arrays.sort(words, (w1, w2) -> w2.length() - w1.length());
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<words.length; i++){
            map.put(words[i], 1);
        }

        int globalMax = 1;
        for (int i=0; i < words.length; i++){
            int curLen = map.getOrDefault(words[i], 1); //max for current word

            for (int j=0; j < words[i].length(); j++){
                StringBuilder sb = new StringBuilder(words[i]);
                //try remove each char
                char ch = words[i].charAt(j);
                sb.deleteCharAt(j);
                String pred = sb.toString();
                if (map.containsKey(pred)){
                    int curMax = Math.max(map.getOrDefault(pred, 1), curLen+1);
                    map.put(pred, curMax);
                    globalMax = Math.max(globalMax, curMax); //update globalMax if found pred to build the chain
                }
            }

        }
        return globalMax;
    }
}

//clarification:

//DP[i] represetns chain len for word i, which is the length of chain that starts at word i.
//for each of its predecessor in words, their string chain of them = current word chain length +1.
//-> conversion function.

//Map<String, Integer> can be used to store chain length and for quick look up
//Start from longest word, try remove each of the char from word i, if the removed word exists in Map, update predecessor's chain length. -> We can avoid missing processing any of the word.
//Eventually we just get the max value after we finish updating all the words?

//words.length = n
//words[i].length() = m

//TC:O(nm) for each words[i], remove each char from it.
//SC:O(n) for map to store all unique word
