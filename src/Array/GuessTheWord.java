package Array;

import java.util.*;

/**
 * // This is the Master's API interface.
 * // You should not implement it, or speculate about its implementation

 */

interface Master {
    public int guess(String word);
}

public class GuessTheWord {

    private Random rand = new Random();

    public void findSecretWord(String[] wordlist, Master master) {
        if (wordlist == null || wordlist.length == 0){
            return;
        }
        //Placeholder for wordlist after each call
        List<String> newWordList = new ArrayList<>();
        for (int i=0; i < wordlist.length; i++){
            newWordList.add(wordlist[i]);
        }
        for (int i=0; i < 10; i++){
            String guess = newWordList.get(rand.nextInt(newWordList.size()));
            int x = master.guess(guess);
            if (x == 6){
                return;
            }
            List<String> oldWordList = newWordList;
            newWordList = new ArrayList<>();
            //After each call, we will narrow down our wordlist by keep only those have same match with the guessed word
            for (String word : oldWordList){
                if (getMatches(word, guess) == x){
                    newWordList.add(word);
                }
            }
        }
    }

    private int getMatches(String s1, String s2){
        int i=0;
        int match = 0;
        while (i < s1.length() && i < s2.length()){
            if (s1.charAt(i) == s2.charAt(i)){
                match++;
            }
            i++;
        }
        return match;
    }

}

//Clarification:
//do we really need to guess it out within 10 calls? No, but the algorithm should make its best to guess the word in as little call as possibe.

//Strategy: We will make user of each master.guess() call to help us narrow down the word list as much as possible each time.
//wordlist.length = n

//Step 1:
//for (int i=0; i<10; i++){  - O(10)
//   if (master.guess(word) != 6){
//     do something to narrow down wordlist  - O(?)
//   else
//     get the word, break


//Step2: What we get from master.guess() is number of char matches, so we should only keep those word in wordlist that has x matches with the word we guess after each call.  - O(n)
//int x = master.guess(word1);
//List<String> newWordlist;
//for word : wordlist:
//    if (getMatches(word, word1) == x) - O(6)
//        newWordlist.add(word);

//Step 3: Q: which word to guess from candidate list for each call?
//a. pick the first word
//b. randomly pick - pick this?
//Probability of guess(word) == x -> p = C(6,x) * (1/26)^x * (25/26)*(6-x) -> for x position in 6
//Sum of P(0)+ ... + P(6) = 65%

//TC:O(10n)
//SC:O(n) for keeping the wordlist after each call