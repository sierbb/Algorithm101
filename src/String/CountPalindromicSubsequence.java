package String;

public class CountPalindromicSubsequence {

    public int countPalindromicSubsequences(String s) {
        if (s == null){
            return 0;
        }
        int mod = 1000000007;
        int[][] DP = new int[s.length()][s.length()];
        //fill in DP matrix
        for (int i=s.length()-1; i>= 0; i--){
            for (int len = 1; len <= s.length()-i; len++){
                int j = i+len-1;
                if (i==j){
                    DP[i][j] = 1;
                }else {
                    char start = s.charAt(i);
                    char end = s.charAt(j);
                    if (start == end){
                        DP[i][j] = 2* DP[i+1][j-1];
                        //deduplicate
                        int left = i+1;
                        int right = j-1;
                        while (left <= right && s.charAt(left) != start){
                            left++;
                        }
                        while (right >= left && s.charAt(right) != start){
                            right--;
                        }
                        //case 1: no char same as start
                        if (left > right){
                            DP[i][j] += 2;
                        }
                        //case 2: 1 char same as start
                        else if (left == right){
                            DP[i][j] +=1;
                        }
                        //case 3: 2 chars same as start
                        else {
                            DP[i][j] -= DP[left+1][right-1];
                        }
                    }else {
                        //DP[a bc b] == DP[abc] + DP[bcb] - DP[bc]
                        DP[i][j] = DP[i+1][j] + DP[i][j-1] - DP[i+1][j-1];
                    }
                }
                //mod DP[i][j] to prevent overflow
                // DP[i][j] = (int)((DP[i][j] + mod) % mod);
                DP[i][j] = DP[i][j] < 0 ? DP[i][j] + mod : DP[i][j] % mod; //DP can overflow because we are doing times 2 as well as addition in previous steps
            }
        }
        return DP[0][s.length()-1];
    }
}

//clarification: find all the possible palindrome subsequence, and dedupe
//  b c c b
//  s
//        e

//DP[1,2] [c, cc]
//DP[0,3] [c, cc,  bcb, bccb] = 2* DP[1,2]


//DP[i][j] represents number of diff palindromic subsequence within substring of [i, j], including i, j
//if s[i] == s[j], DP[i][j] = DP[i+1][j-1] * 2 -> dedupe
//else DP[i][j] = DP[i+1][j-1].

//dedupe
//ch = s[0] = b
//1.There are no b within s[1,2] -> adding b, bb -> +2
//2.1 b within s[1,2] -> adding bb -> +1
//3.2 b within s[1,2] -> not add, also need to further dedupe the part without any b
//  b b  c c  b   b
// [s             e]   ->    b[s+1,e-1]b and [s+1,e-1] ->  bcb, bccb, bbcbb, bbccbb and  c, cc, bcb, bccb  -> reduce number of s[x, y] where x and y are within the inner pair of b
//    [s+1    e-1]     ->  c, cc, bcb, bccb

//TC:O(n^2 * n) for filling DP matrix, each cell we move pointer to check duplicate char
//SC:O(n^2) for DP matrix

