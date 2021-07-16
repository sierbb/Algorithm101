package BinarySearch;

/**
 * Given an unsorted integer array, return the local minimum's index.
 * An element at index i is defined as local minimum when it is smaller than all its possible two neighbors a[i - 1] and a[i + 1]
 * (you can think a[-1] = +infinite, and a[a.length] = +infinite)
 *
 * Assumptions:
 * The given array is not null or empty.
 * There are no duplicate elements in the array.
 * There is always one and only one result for each case.
 *
 * Similar to find peak element
 */
public class FindLocalMinimum {

    public int localMinimum(int[] array) {
        //C: We want to find the only one local minium from an unsorted array and return its index in the original array.
        //A: array is not sorted; has only one local minimum; no duplicate elements.
        //R: M1: Linear scan the array and check each i for whether it satisfy a[i]<a[ii+1] && a[i]<a[i-1]. TC:O(n); SC:O(1)
        //M2: If binary search, we must do it on sorted array, sort takes nlogn at least?
        //Actually seems like we can do binary search and takes the smaller neighbors's side to look for the target.
        //e.g. 4, 3, 2, 8, 9, 10; target 2 must be the smallest element of all, since there's on other element is local minimum, they must not trending smaller towards beginnig or end.
        //TC:O(logn); SC:O(1)
        if (array == null || array.length == 0) return -1;
        int start = 0;
        int end = array.length-1;
        while (start <= end){
            int mid = start + (end-start)/2;
            //if both neighbors are larger than array[mid]
            if ( (mid+1== array.length || array[mid] < array[mid+1] ) && ( mid-1 == -1 || array[mid] < array[mid-1] )){
                return mid;
            } else { //there must be one side that is smaller than array[mid], find the smaller side
                if ( mid+1==array.length || mid-1 >= 0 && array[mid-1] < array[mid] ){
                    //if right neighbor is out of bound or if left neighbor(index not out of bound) is smaller, we look at left half of mid
                    end = mid-1;
                }else {
                    //anything else
                    start = mid+1;
                }
            }
        }
        return -1; //if not found any local minimum
    }

    //Test:
    // [6, 4], local minimum is 4
    // round 1: start = 0; end = 1; mid =0, array[mid] = 6
    // since mid-1 <0; we look at right side -> start = 1.
    // round 2: start = 1; end =1; mid = 0, array[mid] = 4 -> satisfy
}
