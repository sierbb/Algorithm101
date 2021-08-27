package DynamicProgramming;

import java.util.*;

public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || wordDict == null){
            return false;
        }
        //use set to quickly look up the words
        Set<String> set = new HashSet<>();
        for (int i=0; i < wordDict.size(); i++){
            set.add(wordDict.get(i));
        }
        //DP[i] represents whetehr can break a word that with first t letter
        boolean[] DP = new boolean[s.length()+1];
        DP[0] = false;
        for (int i=1; i<=s.length(); i++){
            //case 1: the entire string is valid
            if (set.contains(s.substring(0, i))){
                DP[i] = true;
            }else{
                //case 2: partial
                for (int j=0; j<i; j++){ //for each previous index
                    if (DP[j] && set.contains(s.substring(j, i))){ //substring(startIdx, endIdx+1)
                        DP[i] = true;
                        break;
                    }
                }
            }
        }
        return DP[s.length()];
    }
}

//clarification: word can be reused. Same as cut dictionary.
//boolean DP[i] represents whether the s with first i letters can be break into words in wordDict.
//DP[0]=false, DP[1]=false -> manual check.
//for i from 1 to s.length(); DP[i] = for j in 0 to i-1, if DP[j]=true && substring(j+1, i) is in wordDict, the DP[i] = true; else false
//return DP[s.length()];

//s.length() = n;
//TC:O(n^2 * n); for substring on each iteration
//SC:O(n)
