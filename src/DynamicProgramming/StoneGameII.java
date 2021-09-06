package DynamicProgramming;

public class StoneGameII {

    public int stoneGameII(int[] piles) {
        if (piles == null || piles.length == 0){
            return 0;
        }
        int[] prefixSum = new int[piles.length];
        prefixSum[0] = piles[0];
        for (int i=1; i<piles.length; i++){
            prefixSum[i] = prefixSum[i-1]+piles[i];
        }
        //Run dfs to get the maximum stone for beginner start at position 0 and M=1
        //memo[i][j] represetns max stone get begin from pileStart=0 and M=j.
        int[][] memo = new int[piles.length][piles.length];
        return DFS(prefixSum, 0, 1, memo);
    }

    private int DFS(int[] prefixSum, int pileStart, int curMaxM, int[][] memo){
        //base case is when current user able to take all the piles (so other player can not)
        if (pileStart + 2*curMaxM >= prefixSum.length){
            int res = getSum(prefixSum, pileStart, prefixSum.length-1);
            //we are not able to fill in memo here since it will be outofBound
            //WRONG! memo[pileStart][curMaxM] = res;
            return res;
        }
        if (memo[pileStart][curMaxM] != 0){
            return memo[pileStart][curMaxM];
        }

        //current user can choose to take i piles from current start
        int res = 0;
        for (int i=1; i<=curMaxM*2; i++){
            int curTake = getSum(prefixSum, pileStart, pileStart+i-1);
            int restSum = getSum(prefixSum, pileStart+i, prefixSum.length-1);
            int newM = Math.max(i, curMaxM);
            //curTake + restSum = curTotal -> same as Stone GameIII
            int curRes = curTake + restSum - DFS(prefixSum, pileStart+i, newM, memo);
            res = Math.max(res, curRes);
        }
        memo[pileStart][curMaxM] = res;
        return res;
    }

    private int getSum(int[] prefixSum, int start, int end){
        //includes index start and end
        if (start == 0){
            return prefixSum[end];
        }
        return prefixSum[end]-prefixSum[start-1];
    }
}


//clarification: The latter options depends on the previous options.
//The current player takes (1 <=i <=2M) piles, set M=max(M,i), then the next player can take 1<= i <=2M piles.
//In this case, use DFS + memorization?
//memo[i][j] represetns max stone get begin from pileStart=i and M=j.

//In each recursion, we have DFS(int pileStart, int curMaxPile, int[] prefixSum, int[][] memo)
//Base case is when the current user can take all piles (when he can he should) ->
// pileStart + 2*curMaxPile >= piles.length, takes all and return.

//Then the current player can take piles int i=0; i<2*curMaxPiles; i++.
//DP[pileStart][m] = Max( sum of first i piles + preSum[pileStart+i, n] - DFS(pileStart+i, max(curMaxPile, i), prefixSum, ... ) ) ;

//The only difference here vs Stone Game I is that we may need to actually calculate the DFS for a given pileStart and m since we don't have much base case, and it's a top down process that does not totally rely on previous DP value.
//We only may able to hit the same DP value in the future, since we are only doing a purely top down DP.
//So its more of a memorization, instead of real DP?

//preSum, start=0, i=2, add two elements at current, so next DFS start from 0+2
//[2,7, | 9,4,4]
// s      i

//TC:O(n^2) since memorization?
//SC:O(n^2) for memo matrix