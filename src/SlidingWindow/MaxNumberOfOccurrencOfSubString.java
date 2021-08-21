package SlidingWindow;

import java.util.*;

public class MaxNumberOfOccurrencOfSubString {

    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        if (s == null || s.length() == 0){
            return 0;
        }
        Map<Character, Integer> unique = new HashMap<>();
        Map<String, Integer> substringMap = new HashMap<>();
        int[] globalMax = new int[1];
        int i = 0;
        int j = 0;
        StringBuilder sb = new StringBuilder();
        while (j < s.length()){
            //The main rule here should be: whether window size is out of range, we use this to decide move any pointer
            //now assume j has not been counted
            char add = s.charAt(j);
            sb.append(add);
            int freq = unique.getOrDefault(add, 0)+1;
            unique.put(add, freq);
            updateMax(unique, substringMap, sb, globalMax, maxLetters, minSize); //update when any pointer moves
            // not increase j yet since we haven't processed i

            //Make j fixed, as long as the window is longer than minSize, do it immediately to check if string satisfy
            while (j-i+1 > minSize && i < j){ //only when > minSize can we remove an element from substring
                //need to move i one step
                char del = s.charAt(i);
                sb.deleteCharAt(0);
                int delFreq = unique.get(del)-1;
                if (delFreq == 0){
                    unique.remove(del);
                }else {
                    unique.put(del, delFreq);
                }
                i++;
                updateMax(unique, substringMap, sb, globalMax, maxLetters, minSize); //update when any pointer moves
            }

            //now we move i haveing j fixed, can move j now
            j++;
        }
        return globalMax[0];
    }

    private void updateMax(Map<Character, Integer> unique, Map<String, Integer> substringMap, StringBuilder sb, int[] globalMax, int maxLetters, int minSize){
        //if substring satisfy unique char count, record it
        if (unique.size() <= maxLetters && sb.length() >= minSize){
            String sub = sb.toString();
            int subFreq = substringMap.getOrDefault(sub, 0)+1;
            substringMap.put(sub, subFreq);
            globalMax[0] = Math.max(subFreq, globalMax[0]);
        }
    }


    public static void main(String[] args){
        String input = "aabcabcab";
        MaxNumberOfOccurrencOfSubString obj = new MaxNumberOfOccurrencOfSubString();
        System.out.println(obj.maxFreq(input, 2, 2,3));

    }
}

//substring rule:
//1.minSize <= substring.length <= maxSize
//2.unique char <= maxLetter

//map{a:2, b:1   }  for unique char and its frequency
// aababcaab   3-4, char <=2
//       i
//         j
//number of unique substring = O(maxLetter^maxSize)
//TC: (maxSize-minSize)*(s.length/(maxSize-minSize))
//SC: O(s.length()) + O(maxLetters^(maxSize-minSize))

//globalMax = 0,
//map{ aab:2, aba:1; bab:1  }
