package Math;

public class NextPermutation {

    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length == 0) return;
        //Step 1: Find the first smallest cliff from right side
        int cliff = -1;
        for (int i=nums.length-1; i>=1; i--){
            if (nums[i-1] < nums[i]){
                cliff = i-1;
                break;
            }
        }
        if (cliff == -1){
            //if not find any cliff, sort it in ascending order (means we can reverse it)
            reverse(nums, 0, nums.length-1);
            return;
        }
        //Step2: find the smallest element larger than nums[cliff], nums[cliff, n-1] side is in descending order
        for (int j=nums.length-1; j > cliff; j--){
            if (nums[j] > nums[cliff]){
                swap(nums, j, cliff);
                break;
            }
        }
        //Step 3: reverse all elements from nums[cliff, n-1]
        reverse(nums, cliff+1, nums.length-1);
    }

    private void swap(int[] nums, int idx1, int idx2){
        int tmp = nums[idx1];
        nums[idx1] = nums[idx2];
        nums[idx2] = tmp;
    }

    private void reverse(int[] nums, int idx1, int idx2){
        while (idx1 < idx2){
            swap(nums, idx1, idx2);
            idx1++;
            idx2--;
        }
    }
}

//clarification: nums.length = n
//Step1: find the first descending cliff from right side. (because if 3,2,1 then its already the largest)
//Step2: once find the smaller cliff, swap the smaller with the first element larger than it from right side.
//Step3: After swap, a[i-1] become larger, now we want the elements on right of a[i-1] be smallest, so reverse the numbers

//TC:O(n) to find cliff + O(n) to find the first larger node from right side
//SC:O(1) no extra data structure