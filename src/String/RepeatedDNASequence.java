package String;

import java.util.*;

public class RepeatedDNASequence {

    private static final int L = 10; //length of sequence
    private static final int a = 4;  //hashing factor

    public List<String> findRepeatedDnaSequences(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();
        if (n < L){
            return res;
        }
        //convert char into numbers
        Map<Character, Integer> charToInt = new HashMap<>();
        charToInt.put('A', 0);
        charToInt.put('C', 1);
        charToInt.put('G', 2);
        charToInt.put('T', 3);

        Set<Integer> set = new HashSet<>();
        Set<String> out = new HashSet<>();
        //Scan through the entire sequence, get the hash of each 10-length sequence, and store it into a set
        int hash = 0;
        int aL = (int)Math.pow(a, L); //factor^L
        for (int start = 0; start <= n-L; start++){
            if (start == 0){ //first sequence
                for (int i = 0; i < L; i++){
                    hash = hash * a + charToInt.get(s.charAt(i));
                }
            }else {
                //Calculate new hash for new sequence
                int toRemove = charToInt.get(s.charAt(start-1));
                int toAdd = charToInt.get(s.charAt(start-1+L));
                hash = hash * a - toRemove * aL + toAdd;
            }
            if (set.contains(hash)){
                out.add(s.substring(start, start+L)); //duplicate hash, time to add sequence to output
            }else {
                set.add(hash); //new hash, add to dedupe set
            }
        }
        for (String sequence : out){
            res.add(sequence);
        }
        return res;
    }
}

//clarification: all the consecutive sequences that appear more than once
// also need to be 10-letter long
//s.length() > 10 ? yes

// we need to move sliding window of size 10 and store each sequence into a hashset
// s.length() = n

//M1:
// TC:O( (n-10)*n ), n-10 is number of sliding window, n is length of substring
// SC:O(n-10)

//M2:
//Rolling hash, assume first 10-length sliding window's hash

//AAAAACCCCC next is  AAAACCCCCA = AAAAACCCCC0 - A000000000 + A
// hash                hash * a - C + A
//                              end-1  end

//h=0. a = 4, aL = (int)Math.pow(a, L) = 4^10.
//hash = hash * a - prevLastChar + currentChar
//     = hash * a - nums[start-1] * aL + nums[start-1+L]  - O(1) instead of O(n) to make substring
//  -> check if exists in set, if so, add to result; elsem add new hash to set

//TC:O( (n-L)*1) = O(n-L)
//SC:O(n-L) for dedupe set and out set