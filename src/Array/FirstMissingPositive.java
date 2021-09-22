package Array;

public class FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0){
            return 1;
        }
        int len = nums.length;
        //Step 1: get rid of negative and numbers larger than n, since first missing must be within [1, n]
        for (int i=0; i<nums.length; i++){
            if (nums[i] <= 0 || nums[i] > len){
                nums[i] = len+1;
            }
        }
        //Step 2: for each element, use it as the index key and put onto the input array to record it has been seen
        for (int i=0; i<nums.length; i++){
            int val = Math.abs(nums[i]); //Get the original abs value because we only care about positive number
            if (val > len){ //the one we just padded
                continue;
            }
            int index = val-1;
            if (nums[index] > 0){
                nums[index] = (-1)*nums[index];
            }
        }
        //Step 3: look for the first element that's not <0, this one has not been seen
        for (int i=0; i<nums.length; i++){
            if (nums[i] > 0){
                return i+1;
            }
        }
        //if nothing found missing, means n+1
        return len+1;
    }
}

//?? i think it is the OA question from MS/colidity

//Find the first missing positive, unsorted
//M1: First sort the array, then find it - O(nlogn)

//M2: If can not sort -> scan through the array and update the smallest number and smallest positive num, largest number so far
//if max <= 0, return 1
//if min > 1, return 1

// for cur, if cur >=2 && cur-1 not exists, set output to cur-1
//   3,2,7,8,1,-1, 12, n = 7, get rid of any number > 7
//ar[3,  2,  7,  1, 4, 6, 5 ], now change sign of ath element to negative if meet number a
//ar[-3, -2,-7, -1,-4,-6, 1]
//4 is the answer

//TC:O(n)
//SC:O(1)