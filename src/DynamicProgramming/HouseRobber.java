package DynamicProgramming;

public class HouseRobber {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int[] M = new int[nums.length+1]; //M[i] represents robbing the first i houses
        M[0] = 0; //not robing any house
        M[1] = nums[0]; //if rob current or not
        M[2] = Math.max(M[1], M[0]+nums[1]);
        if (nums.length <= 2){
            return M[nums.length];
        }
        for (int i=3; i<=nums.length; i++){
            //case 1: not rob current house
            M[i] = M[i-1];
            M[i] = Math.max(M[i], M[i-2]+nums[i-1]);
        }
        return M[nums.length];
    }
}

/**
 * All houses at this place are arranged in a circle.
 */
class HouseRobberII {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(robHelper(nums, 0, nums.length - 2), robHelper(nums, 1, nums.length - 1));
    }

    private int robHelper(int[] nums, int start, int end) {
        if (start == end) return nums[start];
        if (start > end) return 0;
        //start, end are both indexes
        int[] M = new int[end - start + 2]; //length=real length+1
        M[0] = 0;
        M[1] = nums[start]; //1 house
        M[2] = Math.max(M[1], M[0] + nums[start+1]); //2 houses
        if (end - start+1 <= 2) {
            return M[M.length - 1];
        }
        for (int i = 3; i < M.length; i++) {
            M[i] = Math.max(M[i - 1], M[i - 2] + nums[start + i - 1]);
        }
        return M[M.length - 1];
    }
}

//M1:
//[1,2,3,1]
// 1
// 3 1
//   2
//=4
//boolean[T,F,T,F]
//TC:O(n!); SC:O(n) for recursion for DFS

//M2: DP
//[1, 2,   3,1]
//[T, ]
//M[0]=M[0];
//
//M[1] min of (if M[0] has been robbed, M[0]; or if M[0] has not been robbed, M[i-2]+c[1])
//M[1] = min of (if rob i M[i-2]+a[i], if not rob i, M[i-1])

