package DynamicProgramming;

/**
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 */
public class LongestIncreasingSubsequence {

    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        int globalMax = 1;
        int[] DP = new int[nums.length];
        DP[0] = 1;
        for (int i=1; i<nums.length; i++){
            DP[i] = 1;
            for (int j=0; j<i; j++){
                if (nums[i] > nums[j]){
                    DP[i] = Math.max(DP[i], DP[j]+1);
                }
            }
            globalMax = Math.max(globalMax, DP[i]);
        }
        return globalMax;
    }
}

//clarification: subsequence can not be continuous

//linear scan and look back
//DP[i] represetns longest subsequence that ends at i
//for i
//  for 0<= j < i; if nums[i] > nums[j], DP[i] = Math.max(DP[i], DP[j]+1);

//update globalMax
//TC:O(n^2)
//SC:O(n)

class LongestIncreasingSubsequenceM2{

    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        int[] DP = new int[nums.length];
        int pointer = 0;
        DP[0] = nums[0];
        for (int i=1; i < nums.length; i++){
            int index = findSmallestLarger(DP, pointer, nums[i]);
            if (index != -1){
                DP[index] = nums[i];
            }else {
                pointer++;
                DP[pointer] = nums[i];
            }
        }
        return pointer+1; //length of DP
    }

    private int findSmallestLarger(int[] DP, int pointer, int num){
        //search from [0, pointer] for the smallest element larger or equal to num
        int start = 0;
        int end = pointer;
        while (start < end){
            int mid = start + (end-start)/2;
            if (DP[mid] >= num){ //mid larger, but not sure whether it is the smallest ele or not
                end = mid;
            }else {
                start = mid+1;
            }
        }
        // 7 -> num=7; return -1
        //     se
        if (DP[start] >= num){
            return start;
        }
        return -1;
    }
}

//clarification:
//further improve the time complexity, we need change our DP representation
//DP[i] represents the smallest element that forms an ascending subsequence of length i+1
//So we are building our increasing subsequence

//DP[] itself is increasing
//for ele in nums:
//  binary search in DP for the smallest element ex > ele, if exist, replace ex with ele. -> now DP[j] is smallest.

//return length of DP

//TC:O(nlogn)
//SC:O(n)


