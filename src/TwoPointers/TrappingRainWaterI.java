package TwoPointers;

public class TrappingRainWaterI {

    public int trapM3(int[] height) {
        if (height == null || height.length == 0){
            return 0;
        }
        int left = 1;
        int leftMax = height[0];
        int right = height.length-2;
        int rightMax = height[height.length-1];
        int sum = 0;
        while (left <= right){
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax <= rightMax){
                sum += leftMax - height[left];
                left++;
            }else {
                sum += rightMax - height[right];
                right--;
            }
        }
        return sum;
    }


    public int trapM2(int[] height) {
        if (height == null || height.length == 0){
            return 0;
        }
        int[] DPL = new int[height.length];
        int[] DPR = new int[height.length];
        DPL[0] = height[0];
        for (int i=1; i < height.length; i++){
            DPL[i] = height[i] > DPL[i-1]? height[i]: DPL[i-1];
        }
        DPR[height.length-1] = height[height.length-1];
        for (int i=height.length-2; i >= 0; i--){
            DPR[i] = height[i] > DPR[i+1]? height[i]: DPR[i+1];
        }
        //now scan through the array again and calculate area
        int sum = 0;
        for (int i=0; i < height.length; i++){
            int maxWaterHeight = Math.min(DPL[i], DPR[i]);
            sum += maxWaterHeight - height[i];
        }
        return sum;
    }
}

//clarification: get the sum of rain water trapped
//M1: for each index, look at left and right side, get the min(maxLeft, maxRight) = maxWaterHeight.
//curArea += maxWaterHeight-height
//TC:O(n^2), SC:O(1)

//M2: DP linear scan from left and from right to get maxLeft, maxRight
//DPL = []
//DPR = []
//Then can do another linear to calculate water on each bar
//TC:O(n), SC:O(n)

//M3: Greedy
//int left = 0, right = length-1
//Move left and right pointer towards each other, if a[l] <= a[r], maxWaterHeight = a[l], calculate area, l++;
//else j--.

//TC:O(n), SC:O(1)