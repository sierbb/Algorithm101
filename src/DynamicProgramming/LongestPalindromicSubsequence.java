package DynamicProgramming;

public class LongestPalindromicSubsequence {

    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        int[][] DP = new int[s.length()][s.length()];
        for (int i=s.length()-1; i>=0; i--){
            for (int len=1; len <= s.length()-i; len++){
                int j=i+len-1;
                if (i==j){
                    DP[i][j] = 1;
                }else {
                    //need to compare moving out one char?
                    DP[i][j] = s.charAt(i) == s.charAt(j) ? DP[i+1][j-1] +2 : Math.max(DP[i+1][j], DP[i][j-1]);
                }
            }
        }
        return DP[0][s.length()-1];
    }
}

//clarification:
//DP[i][j] represents the longest subsequence from within [i, j]
//DP[i][j] = s[i] == s[j] ? DP[i+1][j-1] + 2 : DP[i+1][j-1]

//base case: DP[i][i] and DP[i][i+1]

//to know DP[i][j] we must know (i+1, j-1)

//TC:O(n^2)
//SC:O(n^2)