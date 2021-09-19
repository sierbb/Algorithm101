package String;

import java.util.*;

public class CountVowelsPermutation {

    public int countVowelPermutation(int n) {
        if (n == 0){
            return 0;
        }
        //-> aDP[i] represents string of length i ends at a, the number of permutation.
        long[] aDP = new long[n+1];
        long[] eDP = new long[n+1];
        long[] iDP = new long[n+1];
        long[] oDP = new long[n+1];
        long[] uDP = new long[n+1];
        aDP[1] = 1L; //1 letter can form 1 string of length 1
        eDP[1] = 1L;
        iDP[1] = 1L;
        oDP[1] = 1L;
        uDP[1] = 1L;

        int MOD = 1000000007;
        //now go from length of 2 to n, fill in each DP according to tthe last element they can follow
        for (int i=2; i<=n ; i++){
            aDP[i] = (eDP[i-1]+iDP[i-1]+uDP[i-1]) % MOD; //a can follow those letter
            eDP[i] = (aDP[i-1]+iDP[i-1]) % MOD;
            iDP[i] = (eDP[i-1]+oDP[i-1]) % MOD;
            oDP[i] = (iDP[i-1]) % MOD;
            uDP[i] = (iDP[i-1]+oDP[i-1]) %MOD;
        };
        long sum = (aDP[n] + eDP[n] + iDP[n] + oDP[n] + uDP[n]);
        return (int)(sum % MOD);

    }
}


//DP to do bottom up?

//Since we have 5 options for a char, and each option based on the other option on previous position ->
//multiple DP array, each represents one vowel option.
//-> aDP[i] represents string of length i ends at a, the number of permutation.
//To fill in each DP, we look for specific other DP where i can follow.
//At the end its the sum of all vowel's last number.

//a follows e,i,u
//e follows a,i
//i follows e,o
//o follows i
//u follows i,o

//TC:O(n);
//SC:O(n)

/**
 * DFS method
 */
class CountVowelsPermutationDFS {

    private char[] vowels = new char[]{'a', 'e', 'i', 'o', 'u'};

    public int countVowelPermutation(int n) {
        if (n == 0){
            return 0;
        }
        char[] sol = new char[n]; //holder to place the char
        int [] count = new int[1];
        DFS(n, 0, sol, count);
        int MOD = 1000000007;
        return (int) (count[0] % MOD);
    }

    private void DFS(int n, int level, char[] sol, int[] count){
        if (level == n){
            count[0]++;
            return;
        }
        //place aeiou to current possition
        for (int i=0; i < vowels.length; i++){
            if (level == 0){
                sol[level] = vowels[i]; //will override last option
                DFS(n, level+1, sol, count);
            }else {
                char prev = sol[level-1];
                char cur = vowels[i];
                if (cur == 'a' && (prev == 'e' || prev == 'i' || prev == 'u')){
                    sol[level] = cur; //will override last option
                    DFS(n, level+1, sol, count);
                }else if (cur == 'e' && (prev == 'a' || prev == 'i')){
                    sol[level] = cur;
                    DFS(n, level+1, sol, count);
                }else if (cur == 'i' && (prev == 'e'|| prev == 'o')){
                    sol[level] = cur;
                    DFS(n, level+1, sol, count);
                }else if (cur == 'o' && (prev == 'i')){
                    sol[level] = cur;
                    DFS(n, level+1, sol, count);
                }else if (cur == 'u' && (prev == 'i' || prev == 'o')){
                    sol[level] = cur;
                    DFS(n, level+1, sol, count);
                }
            }
        }
    }

    // if n > log5(2^32), then it may be too large? Need to mod 10^9 + 7
    //TC:O(5^n) for all possible string
    //SC:O(n) for recursion call stack

    public static void main (String[] args){
        CountVowelsPermutationDFS obj = new CountVowelsPermutationDFS();
        int input = 2;
        System.out.println(obj.countVowelPermutation(input));

    }
}