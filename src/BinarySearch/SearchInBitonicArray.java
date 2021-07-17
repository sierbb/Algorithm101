package BinarySearch;

/**
 * Search for a target number in a bitonic array, return the index of the target number if found in the array, or return -1.
 * A bitonic array is a combination of two sequence: the first sequence is a monotonically increasing one and the second sequence is a monotonically decreasing one.
 * Assumptions: The array is not null.
 * Examples:array = {1, 4, 7, 11, 6, 2, -3, -8}, target = 2, return 5.
 */

public class SearchInBitonicArray {
    public int search(int[] array, int target) {
        //C: We want to search the target value in an array that is bitonic (increase then decrease), return its index
        //A: array is not null but maybe empty; target may not in array (return -1); no duplicate element since bitonic
        //R: Use binary search to find the peak in the array; then use binary search to search for the value in two sequence separately.
        //TC:O(n); SC:O(1)
        //corner case 1:
        if (array == null || array.length == 0) return -1;
        if (array.length == 1) return array[0] == target ? 0: -1;
        //corner case 2:
        if (array.length ==2){
            if (array[0] == target) return 0;
            else if (array[1] == target) return 1;
        }
        int start = 0, end = array.length-1, peak = -1;
        while (start <= end){
            //when start == end, we must let start or end able to change the border, otherwise will enter inifinite loop
            //look for the index where it is at a peak
            int mid = start + (end-start)/2;
            //Be careful about the indexOutOfBound issue, when it is at the left/right border or when we found it's the peak
            if (mid-1>=0 && mid+1<array.length){
                if (array[mid-1] < array[mid] && array[mid] < array[mid+1]){
                    //motonimically increase
                    start = mid+1;
                }else if (array[mid-1] > array[mid] && array[mid] > array[mid+1]){
                    //motonimically decrease
                    end = mid-1;
                }else {
                    peak = mid;
                    break;
                }
            }else {
                peak = mid;
                break;
            }
        }
        //[0, peak] is increasing and [peak+1, array.length-1, ] is reversing increasing when reverse, do binary search on both
        int res = binarySearch(array, 0, peak, target);
        return (res != -1? res: reverseBinarySearch(array, array.length-1, peak+1, target));
    }

    private int binarySearch(int[] array, int start, int end, int target){
        while (start <= end){
            int mid = start + (end-start)/2;
            if (array[mid]==target) return mid;
            else if (array[mid]<target) start = mid+1;
            else end=mid-1;
        }
        return -1;
    }

    private int reverseBinarySearch(int[] array, int start, int end, int target){
        while (start >= end){
            int mid = end + (start-end)/2;
            if (array[mid]==target) return mid;
            else if (array[mid]>target) end = mid+1;
            else start=mid-1;
        }
        return -1;
    }

    public static void main(String[] args){
//        int[] input = new int[]{-2,0,6,7,8,10};
        SearchInBitonicArray obj = new SearchInBitonicArray();
        System.out.println(obj.search(new int[]{-2,0}, 0));
//        System.out.println(obj.search(new int[]{-2,0,6,7,8,10}, -2));
//        System.out.println(obj.search(new int[]{1,3,5,8,6,2}, 6));
//        System.out.println(obj.search(new int[]{0,1,6,9,5,3,2,-2,-4,-5}, -2));
    }
}