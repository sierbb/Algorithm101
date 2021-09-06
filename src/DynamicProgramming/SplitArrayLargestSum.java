package DynamicProgramming;

public class SplitArrayLargestSum {

    public int splitArray(int[] nums, int m) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        //1.create prefixsum array for calculate subarray sum
        int[] prefixSum = new int[nums.length+1];
        for (int i=1; i<=nums.length; i++){
            prefixSum[i] = prefixSum[i-1]+nums[i-1];
        }
        //2.DP[i][j] represents largest subarray sum for splitting i letters into j parts
        int[][] DP = new int[nums.length+1][m+1];
        //initialize as max valud for impossible split
        for (int i=0; i<=nums.length; i++){
            for (int j=0; j<=m; j++){
                DP[i][j] = Integer.MAX_VALUE;
            }
        }
        //base case, 0 elements split into 0 part, valid
        //other base case, when i==0 or j==0, impossible
        DP[0][0] = 0;
        for (int i=1; i<=nums.length; i++){ //first i letters
            for (int j=1; j<=m; j++){
                for (int k=0; k<=i-1; k++){ //first k letters
                    //if any left large part is MAX_VALUE(impossible), means current split is impossible, max can help deal with it
                    DP[i][j] = Math.min(DP[i][j], Math.max(DP[k][j-1], prefixSum[i]-prefixSum[k]));
                }
            }
        }
        return DP[nums.length][m];
    }
}

//Clarification: split into m arrays
//minimize largest sum among these m arrays -> to make the sum of m as even as possible

//M1: DP
//DP[i][j] represents split array of i letters into j arrays -> and the largest subarray sum?
//DP[i][j] = min{for k in [0, i-1], DP[k][j-1] + sum(nums[k],nums[k+1],...nums[i-1]) }
//         = min{for k in [0, i-1], max(DP[k][j-1], subarray sum of [k,i-1])}  -> left large part，right smaller part's largest subarray sum，get the larger of them.
//Then we want smaller largest subarray sum over all split
// i from [0, n], j from [0, m] at most m arrays

//prefixSum - To get subarray sum
//how do we fill in the DP matrix? since we need DP[k][j-1] before we know DP[i][j], we fill from top left corner.
//return DP[n][m]
//TC:O(n^2*m)
//SC:O(mn)