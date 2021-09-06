package DynamicProgramming;

/**
 * Method 1: Bottom up DP
 */
public class StoneGameIII {

    public String stoneGameIII(int[] stoneValue) {
        if (stoneValue == null || stoneValue.length == 0){
            return "Tie";
        }
        int[] prefixSum = new int[stoneValue.length];
        prefixSum[0] = stoneValue[0];
        for (int i=1; i<stoneValue.length; i++){
            prefixSum[i] = prefixSum[i-1] + stoneValue[i];
        }

        int[] DP = new int[stoneValue.length];
        for (int i=stoneValue.length-1; i>=0; i--){ //base case is at the end
            int curMax = Integer.MIN_VALUE;
            int otherPlayer = 0;
            for (int j=1; j<=3; j++){ //takes 1 to 3 piles start from i
                if (i+j >= stoneValue.length){ //no stones left, take them all - WRONG
                    otherPlayer = 0;
                }else {
                    otherPlayer = DP[i+j];
                }
                //can also do curTake + restSum = curSum
                int restSum = getSum(prefixSum, i, prefixSum.length-1);
                //other player start from i+j, so what i get from current i is restTotal-DP[i+j], because we share the rest total stone
                curMax = Math.max(curMax, restSum - otherPlayer);
            }
            DP[i] = curMax;
        }
        //DP[0] is Alice assume she begins at 0, so calaulate bob and compare
        int totalSum = prefixSum[prefixSum.length-1];
        if (DP[0] > totalSum-DP[0]){
            return "Alice";
        }else if (DP[0] == totalSum-DP[0]){
            return "Tie";
        }else {
            return "Bob";
        }
    }

    private int getSum(int[] prefixSum, int start, int end){
        if (start == 0){
            return prefixSum[end];
        }
        return prefixSum[end] - prefixSum[start-1];
    }
}

//clarification: Beginner can take at most 1 to 3 piles each time.

//M1: DP bottom up
//DP[i] represents the max stone out come for a beginer begins at store[i].
//Since this is not much options, similar to StoneGame II we let current user take 1, 2, 3 piles, calculate the currentTake, and do prefixSum[xx] - DP[current+i+1, n-1] to get possible out.

//What's the difference between this and StoneGameII (DFS+memo)?
//This is a DP Bottom Up (base case is there are only 3 stones left). So we fill from end of DP to DP[0].
//StoneGameII is a DFS top down call (base case when user takes all stones at last). But memo also fills from bottom up though?
//TC:O(n)
//SC:O(n) for DP array


/**
 * Method 2: Minmax
 * Let DP[i] represents the diff of beginner win over player if beginner starts at stone[i].
 * DP[i] = max( curTake - DP[i+k], for 1<=k<=3) -> what i take minus what other wins over me is what i win over him
 */
class StoneGameIIIM2{

    public String stoneGameIII(int[] stoneValue) {
        if (stoneValue == null || stoneValue.length == 0){
            return "Tie";
        }
        int[] DP = new int[stoneValue.length];
        for (int i=stoneValue.length-1; i>=0; i--){
            DP[i] = Integer.MIN_VALUE;
            int nextWin = 0;
            int curTakes = 0;
            for (int j=1; j<=3; j++){
                if (i+j <= stoneValue.length){  //avoid curTakes out of bound
                    nextWin = i+j >= stoneValue.length ? 0 : DP[i+j]; //if DP out of bound set to to 0
                    curTakes += stoneValue[i+j-1];
                    DP[i] = Math.max(DP[i], curTakes - nextWin);
                }
            }
        }
        if (DP[0] > 0){
            return "Alice";
        }else if (DP[0] < 0){
            return "Bob";
        }else {
            return "Tie";
        }
    }

    /**
     * Space Optimized - SC: O(1)
     * TC:O(n)
     */
    public String stoneGameIIISpaceOptimized(int[] stoneValue) {
        if (stoneValue == null || stoneValue.length == 0){
            return "Tie";
        }
        // int[] DP = new int[stoneValue.length];
        int[] DP = new int[4];
        for (int i=stoneValue.length-1; i>=0; i--){
            DP[i % 4] = Integer.MIN_VALUE;
            int nextWin = 0;
            int curTakes = 0;
            for (int j=1; j<=3; j++){
                if (i+j <= stoneValue.length){  //avoid curTakes out of bound
                    nextWin = i+j >= stoneValue.length ? 0 : DP[(i+j)%4]; //if DP out of bound set to to 0
                    curTakes += stoneValue[i+j-1];
                    DP[i % 4] = Math.max(DP[i % 4 ], curTakes - nextWin);
                }
            }
        }
        if (DP[0] > 0){
            return "Alice";
        }else if (DP[0] < 0){
            return "Bob";
        }else {
            return "Tie";
        }
    }
}

//clarification: Each person can only take 3 piles each time.
//Noticed that the pick of previous player won't affect the max score of next player when he begins on DP[i];

//Let DP[i] represents the diff of beginner win over player if beginner starts at stone[i].
//DP[i] = max( curTake - DP[i+k], for 1<=k<=3) -> what i take minus what other wins over me is what i win over him

//Base case is at DP[n-3 ~ n-1] since we can take all three piles and not let the other player take them. (Same as Stone Game II)
//e.g.
//store [1,2,3,6]
//DP    [6,11,9,6] -> you see on position 1~3, its the sum of the rest stones.

//DP
//TC:O(n)
//SC:O(n)


