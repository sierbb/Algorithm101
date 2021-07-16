package BinarySearch;

/**
 * Given a sorted integer array. Each integer appears twice except one single element appearing only once. Find this single integer.
 * Example 1:
 * Input: [1,1,2,2,3,3,4,5,5]
 * Output: 4
 * Example 2:
 *
 * Input: [5,5,6,7,7,8,8]
 * Output: 6
 * Assumption:The input array is not null and not empty.
 * Note:Try to do it in Olog(n) time.
 */
public class FindSingleElementInSortedArray {

    public int getSingleElement(int[] nums) {
        //C: Sorted array, find the integer that appears only once, return its value.
        //A: Array is sorted; there must be an answer; array not null or empty; element not consecutive;
        //R: M1: Linear scan to find the one that appears only once. TC:O(n)
        // M2: Bineary search? For a normal element it must have one of its neighbors equals to it. Return that value if found.
        //[1,1,2,2,3,3,4,5,5] -> search 4, left and right not equals to it.
        //search 2, leftIdx=2, right=3; -> mid should = val*2-1; if not, means left side has missing element; else right side.
        //if leftIdx=1, right=2 -> mid should = val*2-2; if not, means left side has missing element; else right side.
        //TC:O(logn); SC:O(1)
        if (nums == null || nums.length == 0) return 0;
        int start = 0;
        int end = nums.length-1;
        while (start <= end){
            int mid = start+(end-start)/2;
            if ( (mid-1<0 || mid-1 >=0 && nums[mid-1] != nums[mid]) && (mid+1 >= nums.length || nums[mid+1]!=nums[mid])){
                return nums[mid];
            }else {
                //if left neighbor == mid, check mid's index see if it is odd
                if (mid-1>=0 && nums[mid-1] == nums[mid]){
                    if (mid %2 != 1){
                        end = mid-2;
                    }else {
                        start = mid+1;
                    }
                } else if (mid+1<=nums.length && nums[mid+1] == nums[mid]){
                    //if right neighbor == mid, still check mid's index is even
                    if (mid %2 != 0){
                        end = mid-1;
                    }else {
                        start = mid+2;
                    }
                }
            }
        }
        return 0;
    }

    //Test 1: 1, 2, 2, 3, 3
    //Round 1: mid = 2, array[2]=2, array[1]=2, array[3]=3. -> 2!= 2*array[2]-1, end=mid-2=0;?
    //Round 2: s=0;e=0, mid=0, -> mid-1<0; returna array[mid]? why

    //Test 2: 2,2,3
    //Round 1: mid=1, array[1]=2, array

    //Test 3: 1
}
