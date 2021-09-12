package DynamicProgramming;

public class PartitionEqualSubsetSum {

    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length == 0){
            return true;
        }
        int totalSum = 0;
        for (int i=0; i < nums.length; i++){
            totalSum+= nums[i];
        }
        if (totalSum % 2 == 1){
            return false;
        }
        int half = (int)(totalSum*1/2);
        boolean[][] DP = new boolean[nums.length+1][half+1];
        //base case 1
        // Arrays.fills(DP, false);
        //base case2: DP[0][j] = false; //no element can not form any sum
        for (int i=0; i <= nums.length; i++){
            DP[i][0] = true; //can use 0 element to achieve 0 sum
        }

        for (int i = 1; i <= nums.length; i++){
            for (int j = 0; j <= half; j++){ //if j starts directly from nums[i-1], the previous items will not fit
                //case 1: not take the ith num
                //case 2: take the ith num if nums[i] can fit
                DP[i][j] = DP[i-1][j];
                if (j-nums[i-1] >= 0){
                    DP[i][j] = DP[i][j] || DP[i-1][j-nums[i-1]];
                }
            }
        }
        return DP[nums.length][half];
    }

    public static void main(String[] args){
        PartitionEqualSubsetSum obj = new PartitionEqualSubsetSum();
        int[] input = new int[]{1,5,10,6};
        System.out.println(obj.canPartition(input));

    }
}

//clarification: sum of elements in both subsets is equal == half of the sum

//Step 1: calculate total sum
//Step 2: get the elements into a group such that its sum == 1/2 total sum

//DP[i][j] represetns whether we can find in first i elements to satisfy sum of j -> bool
//case 1: dont put the ith num
//case 2: put the ith num
//take the OR between them
//DP[i][j] = DP[i-1][j] || DP[i-1][j-nums[i-1];

//TC:O(nums.length * sum)
//SC:O(nums.length * sum)