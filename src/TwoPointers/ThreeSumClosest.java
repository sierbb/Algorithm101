package TwoPointers;

import java.util.*;

public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;
        Arrays.sort(nums);
        int sum = nums[0]+nums[1]+nums[2];
        for (int i=2; i<nums.length; i++){
            //do 2sum closest on nums[i+1, nums.length-1] find
            int twoSum = TwoSumClosest(nums, 0, i-1, target-nums[i]);
            sum = Math.abs(twoSum+nums[i]-target) < Math.abs(sum-target)? twoSum+nums[i] : sum;
        }
        return sum;
    }

    private int TwoSumClosest(int[] nums, int start, int end, int target){
        int twoSum = nums[start] + nums[end];
        int closest = twoSum;
        while (start+1 < end){
            if (twoSum == target) return twoSum;
            if (twoSum > target){
                end--;
            }else {
                start++;
            }
            twoSum=nums[start]+nums[end];
            closest = Math.abs(closest-target) < Math.abs(twoSum-target)? closest : twoSum;
        }
        return closest;
    }

    public static void main(String[] args){
        ThreeSumClosest obj = new ThreeSumClosest();
        int[] input = new int[]{0,1,2};
        System.out.println(obj.threeSumClosest(input, 2));
    }
}
