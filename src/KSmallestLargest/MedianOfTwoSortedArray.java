package KSmallestLargest;

/**
 * Given two sorted arrays of integers in ascending order, find the median value.
 *
 * Assumptions
 * The two given array are not null and at least one of them is not empty
 * The two given array are guaranteed to be sorted
 * Examples
 * A = {1, 4, 6}, B = {2, 3}, median is 3
 * A = {1, 4}, B = {2, 3}, median is 2.5
 */
public class MedianOfTwoSortedArray {
    public double median(int[] a, int[] b) {
        //C: Find median of two sorted array. Median is the average of middle two elements if total count is even, and the middle one if total count is odd.
        //A: two arrays are sorted; both of them not null but can be empty
        //R: if total count of element is odd, then median index k= (a.length+b.length+1)/2;
        //if total count of elements is even, then two median index k1= (a.length+b.length-1)/2; k2=(a.length+b.length+1)/2;
        //Since both arrays are sorted, it is equals to use binary search to find the k+1 th smallest element in two arrays.
        //Similarly assume k in a, if not found it in a, find it in b
        //TC:O(log(a)) ; SC:O(1)
        if (a==null || b==null) return -1;
        if (a.length == 0){
            if (b.length % 2 == 1) return b[(b.length)/2];
            else return (double)(b[(b.length-1)/2]+b[(b.length+1)/2])/2;
        }
        if (b.length == 0){
            if (a.length % 2 == 1) return a[(a.length)/2];
            else return (double)(a[(a.length-1)/2]+a[(a.length+1)/2])/2;
        }
        //Do binary search in a
        int aLen = a.length;
        int bLen = b.length;
        if ((aLen+bLen)%2 == 1){
            //if total count is odd, find the kth element
            int medianIdx = (aLen+bLen)/2;
            int idx = getKthIndexElement(a, b, medianIdx);
            if (idx == -1){
                idx = getKthIndexElement(b, a, medianIdx);
                return b[idx];
            }
            return a[idx];
        }else{
            //if total count is even, find the average of k1th and k2th element
            int medianIdx1 = (aLen+bLen-1)/2;
            int medianIdx2 = (aLen+bLen+1)/2;
            int idx1 = getKthIndexElement(a, b, medianIdx1);
            int median1;
            int median2;
            if (idx1 == -1){
                idx1 = getKthIndexElement(b, a, medianIdx1);
                median1 = b[idx1];
            } else {
                median1 = a[idx1];
            }
            int idx2 = getKthIndexElement(a, b, medianIdx2);
            if (idx2 == -1){
                idx2 = getKthIndexElement(b, a, medianIdx2);
                median2 = b[idx2];
            } else {
                median2 = a[idx2];
            }
            return (double)(median1 + median2)/2;
        }
    }

    private int getKthIndexElement(int[] a, int[] b, int k){
        int start = 0;
        int end = a.length-1;
        while (start <= end){
            int mid = start+(end-start)/2;
            if (a[mid] > readIdx(b, k+1-mid-2) && a[mid] < readIdx(b, k+1-mid-1)){
                return mid;
            }else if (a[mid] < readIdx(b, k+1-mid-2)){
                start = mid+1;
            } else {
                end = mid-1;
            }
        }
        return -1;
    }

    private int readIdx(int[] array, int idx){
        if (idx >= array.length) return Integer.MAX_VALUE;
        if (idx < 0) return Integer.MIN_VALUE;
        return array[idx];
    }
}