package DynamicProgramming;

/**
 * Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
 * You have the following three operations permitted on a word:
 * Insert a character
 * Delete a character
 * Replace a character
 */

public class EditDistance {

    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null ){
            return 0;
        }

        int[][] DP = new int[word1.length()+1][word2.length()+1];
        //base case: 0 letters to form another word
        for (int i=0; i <= word1.length(); i++){
            DP[i][0] = i;
        }
        for (int j=0; j <= word2.length(); j++){
            DP[0][j] = j;
        }
        for (int i=1; i <= word1.length(); i++){
            for (int j=1; j <= word2.length(); j++){
                DP[i][j] = Integer.MAX_VALUE;
                if (word1.charAt(i-1) != word2.charAt(j-1)){
                    DP[i][j] = Math.min(DP[i-1][j], DP[i][j-1]);
                    DP[i][j] = Math.min(DP[i][j], DP[i-1][j-1]);
                    DP[i][j]+=1;
                }else {
                    DP[i][j] = Math.min(DP[i][j], DP[i-1][j-1]);
                }
            }
        }
        return DP[word1.length()][word2.length()];
    }
}
//Input: word1 = "horse", word2 = "ros"
//Output: 3
//Explanation:
//horse -> rorse (replace 'h' with 'r')
//rorse -> rose (remove 'r')
//rose -> ros (remove 'e')


//clarification:
//insert delete replace

//DP[i][j] represents the minimum number of editions to convert word1's first i letters to word2's first j letters
//word1 = "horse"  <- insert, delete
//          i
//word2 = "roe"
//          j

//if not equals
//insert: i, j-1
//delete: i-1, j
//replace; i-1, j-1
//+1

//if equals: i-1,j-1

//TC:O(n^2)
//SC:O(n^2) for DP matrix