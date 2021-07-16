package BinarySearch;

/**
 * Given a target integer T and an integer array A, A is sorted in ascending order first, then shifted by an arbitrary number of positions.
 * For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions). Find the index i such that A[i] == T or return -1 if there is no such index.
 * Assumptions
 * There are no duplicate elements in the array.
 */
public class SearchInShiftedSortedArrayI {

    public int search(int[] array, int target) {
        //C: We need to find the target value from a shifted array. If find, return the target's index.
        //A: array is sorted but is shifted; array is not null nor empty; array does not have duplicate elements.
        //R: Q1: We need to use binary search to look for the shifted position. how?
        //compare left/right's value with array[mid], if array[start]>array[mid] means range [mid, n-1] is sorted; ->
        //do binary search on [mid,n-1] and recursively check the unsorted range [0,mid-1]
        //until it has only 1-2 elements left (why?)
        //Q2: how to know we've found the shifted point and can stop the recursion? when only 1-2 elements left -> check the value.
        //Q3: how to keep track of the target's index when we do recursion? If we find target from the sorted part, we can exit early and dont need to go all the way down to 1-2 elements.
        //TC:O(logn); SC:O(1)
        if (array == null || array.length == 0) return -1;
        int[] res = new int[1];
        res[0] = -1;
        findTargetInShifted(array, 0, array.length-1, target, res);
        return res[0];
    }

    private boolean findTargetInShifted(int[] array, int start, int end, int target, int[] res){
        //recursively find the final shifted break point
        if (start >= end){
            if (array[start] == target) res[0] = start;
            else if (array[end] == target) res[0] = end;
            return true;
        }
        int mid = start+(end-start)/2;
        int targetIdx;
        if (array[mid]<array[start]){//means [mid, n-1] is sorted
            targetIdx = binarySearch(array, mid, array.length-1, target);
            if (targetIdx == -1){
                if (findTargetInShifted(array, start, mid-1, target, res)){
                    return true;
                }
            }else {
                res[0] = targetIdx;
                return true;
            }
        }else { //array[mid]>array[start], means [0, mid] is sorted
            targetIdx = binarySearch(array, 0, mid, target);
            if (targetIdx == -1){
                if (findTargetInShifted(array, mid+1, array.length-1, target, res)){
                    return true;
                }
            }else {
                res[0] = targetIdx;
                return true;
            }
        }
        return false;
    }
    //[5,1,3], mid=1, array[mid]=1<array[start]; search [1,3] first -> return -1
    //findshifted(0,0) -> if (start>=end) return true -> here should still check the index otherwise we will not be able to get our answer

    private int binarySearch(int[] array, int start, int end, int target){
        while (start <= end){
            int mid = start+(end-start)/2;
            if (array[mid] == target) return mid;
            else if (array[mid] < target) start = mid+1;
            else end = mid-1;
        }
        return -1;
    }
}
