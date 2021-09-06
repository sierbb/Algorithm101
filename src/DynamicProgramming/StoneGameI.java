package DynamicProgramming;

public class StoneGameI {

    public boolean stoneGame(int[] piles) {
        if (piles == null || piles.length == 0){
            return false;
        }
        //calculate prefixSum for all piles, prefixSum used by index later so uses index here too
        int[] sum = new int[piles.length];
        sum[0] = piles[0];
        for (int i=1; i < piles.length; i++){
            sum[i] = sum[i-1] + piles[i];
        }
        //Fill in DP matrix
        int[][] DP = new int[piles.length][piles.length];
        for (int i=0; i < piles.length; i++){
            for (int j=i; j>=0; j--){
                //we must start with base case first
                if (j==i){
                    DP[j][i] = piles[i]; //if one pile, can only take it
                }else if (j+1==i){
                    DP[j][i] = Math.max(piles[j], piles[i]); //if two piles, take the larger one
                }else {//have more than two piles
                    DP[j][i] = Integer.MIN_VALUE;
                    //case 1: beginner take left side j
                    DP[j][i] = Math.max(DP[j][i], piles[j]+ getSum(sum, j+1, i) - DP[j+1][i]);
                    //case 2: beginner take right side i
                    DP[j][i] = Math.max(DP[j][i], piles[i]+ getSum(sum, j, i-1) - DP[j][i-1]);
                }
            }
        }
        return DP[0][piles.length-1] > sum[piles.length-1] - DP[0][piles.length-1];
    }

    private int getSum(int[] sum, int start, int end){
        //start and end are both index [1, 2, 3, 4]  -> start 2, end 4
        if (start == 0){
            return sum[end];
        }
        return sum[end] - sum[start-1];
    }
}

//clarification: assume both play optimally, and Alice starts first.
//piles.length is even, sum(pile) is odd
//
//DP[i][j] represents the score of starter when start from stone pile [i, j] including i and j, so the score of other player = sum - score
//Base case: DP[i][i] = piles[i]; DP[i][i+1] = max(piles[i], piles[i+1])
//DP[i][j] = max( Alice take left, Alice take right )
//Alice take left = pile[i] + sum[i+1,j] - DP[i+1,j];   //Bob do the DP just like Alice
//Alice take right= pile[j] + sum[i, j-1] - DP[i, j-1]; //Bob do the DP just like Alice

//how do we fill the DP? we need to know DP[i+1, j] and DP[i, j-1] before we know DP[i][j]; start from top down and from right to left

//TC:O(n^2) fill in DP matrix + O(n) prefixSum
//SC:O(n^2)DP matrix + O(n) for prefixSum