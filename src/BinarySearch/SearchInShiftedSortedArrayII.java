package BinarySearch;

/**
 * For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions). Find the index i such that A[i] == T or return -1 if there is no such index.
 * Assumptions: There could be duplicate elements in the array.
 * Return the smallest index if target has multiple occurrence.
 */
public class SearchInShiftedSortedArrayII {

    public int search(int[] array, int target) {
        //We need to search for target value in a sorted shifted array. Also need to find the left border of target element.
        //A: array is not null; array is sorted; target may not be in array; there are duplicate elements -> Key change to binary search logic
        //R: Use binary search to find the shifted break point by comparing array[start] and array[mid] each time;
        //a. if array[start]==array[mid], we still dont know whether left or right is sorted, need to check both side
        //b. if array[start]<array[mid], [start, mid] is sorted, can do binary search; also recursively check right side
        //c. if array[start]>array[mid], [mid, n-1] is sorted, can do binary search; keep checking left side
        //TC:O(n) worst case if start==mid; SC:O(1)
        if (array == null || array.length == 0) return -1;
        int[] res = new int[1];
        res[0] = Integer.MAX_VALUE;
        findSmallestTargetInShifted(array, 0, array.length-1, target, res);
        return res[0] == Integer.MAX_VALUE? -1: res[0];
    }

    private void findSmallestTargetInShifted(int[] array, int start, int end, int target, int[] res){
        //check the provided range of array, use binary search to check the unsorted part, until there are no element to look at
        //base case: when no elements to look at
        if (start >= end){ //how to avoid outofbound
            if (array[start] == target) res[0] = Math.min(res[0], start);
            return;
        }
        int mid = start + (end-start)/2;
        if (array[mid] == array[start]){
            //due to duplicate elements, we still need to check both side of mid, can not guarantee either side is sorted
            findSmallestTargetInShifted(array, start, mid-1, target, res);
            findSmallestTargetInShifted(array, mid+1, end, target, res);
        } else if (array[mid] > array[start]){
            //means left side is guaranteed to be sorted, do binary search
            int index = binarySearch(array, start, mid, target);
            if (index != -1) res[0] = Math.min(res[0], index);
            //its still possible there are target element in right side, keep checking
            findSmallestTargetInShifted(array, mid+1, end, target, res);
        } else {
            //array[mid] < array[start], right side is sorted, [mid, n-1]
            int index = binarySearch(array, mid, end, target);
            if (index != -1) res[0] = Math.min(res[0], index);
            //its still possible there are target in left side, keep checking
            findSmallestTargetInShifted(array, start, mid-1, target, res);
        }
    }

    private int binarySearch(int[] array, int start, int end, int target){
        //assume array is sorted, find the smallest index of target
        while (start < end){
            int mid = start+(end-start)/2;
            if (array[mid] == target){
                end = mid; //need to find left border so shrink right border to mid
            }else if (array[mid] < target){
                start = mid+1;
            } else {
                end = mid-1;
            }
        }
        //termination: when start==end
        //there are one element left, check whether it is target
        return array[start] == target? start: -1;
    }
}


//Test: [3,1,1] , 3
//round 1: mid = 1; array[mid]=1<array[start]; -> [mid,n-1] is sorted -> dont find target
//round 2: s=0; end=0; return s directly

//Test: [4,1,1,  1,1, 1, 3,3], 1
//round 1: mid=3, array[3]<array[start], BS[3,n-1] -> found res[0]=5;
//round 2: find(0, 2), mid=1; array[mid]<array[start] -> BS[1,2] -> found res[0]=1
//round 3: find(0, 0) return

//Test: [1,1,1,1,1,1,1,1,1,1, 1, 1,2,1,1,1,1,1,1,1,1,1], target 2, [0,21]
//round 1: mid=10, a[mid]=1; a[mid]==a[start] -> check both side
//round 2: find(11,21), mid=16, a[mid]==a[start] -> it doesn't mean it is sorted when a[mid]==a[start] -> wrong
//round 3: find(11,16), mid=13, a[mid]==a[start] ->
//round 4: find(11,13), mid=12, a[mid]>a[start] -> BS(start, mid) found 12
