package TwoPointers;

public class MinimumSizeSubarraySum {

    public int minSubArrayLen(int num, int[] nums) {
        //C: Find the subarray that has size >= than num. Return size of the shortest array
        //A: Array is not null or empty; solution may not in the array, return 0;
        //R: M1: use a prefixSum array to help us get the sum of every possible subarray. for i in num, for 0<j<i, get sum of subarray of [j,i] from the prefixSum. O(n^2)
        //M2: We can use two pointers, i=0, j=nums.length-1, with the prefixSum array.
        //TC:O(n); SC:O(n) = prefixSum and hashmap
        //13,1,13, 12,0,18,3, 9,18, 11,9,  8,   3,  4,  7,  13, 14
        //13,14,26,3838,56,59,68,86,97,106,114,117,121,128,141,155-> prefixSum[i], the first i letters's sum
        //  i         ij    -> since all positive, while i<j, subarray sum [i-j] = prefixSum[j]-prefixSum[i] is sum of subarray of [i,j-1] in input array
        //curSum=3, target=31
        //globalMin=(j-i)=2
        int i=0, j=0; //i,j is index of element in input array
        int globalMin = Integer.MAX_VALUE, curSum=nums[0];
        while (j < nums.length ){
            if (curSum < num){
                j++;
                if (j<nums.length){
                    curSum += nums[j];
                }
            }else {
                while (curSum >= num){ //only when we remove elements do we need to update globalMin
                    globalMin = Math.min(globalMin, j-i+1);
                    curSum -= nums[i];
                    i++;
                }
            }
        }
        return globalMin == Integer.MAX_VALUE? 0 : globalMin;
    }

    public static void main(String[] arugs){
        MinimumSizeSubarraySum obj = new MinimumSizeSubarraySum();
//        int[] input = new int[]{13,1,13,12,0,18,3,9,18,11,9,8,3,4,7,13,14};
        int[] input = new int[]{2,3,1,2,4,3};
        System.out.println(obj.minSubArrayLen(7, input));

    }
}
