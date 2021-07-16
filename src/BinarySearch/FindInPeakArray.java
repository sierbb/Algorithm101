package BinarySearch;

/**
 * An array Array is a peak array if and only if:
 * There exists some i such that 0 < i < A.length - 1, and
 * A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
 * Given a peak array peakArr, return the minimum index such that peakArr.get(index) == target.  If such an index doesn't exist, return -1.
 *
 * You can't access the peak array directly.  You may only access the array using a PeakArray interface:
 * PeakArray.get(k) returns the element of the array at index k (0-indexed).
 * PeakArray.length() returns the length of the array.
 */

interface PeakArray {
    public int get(int index);

    public int length();
}

public class FindInPeakArray {

    public class Solution {
        public int findInPeakArray(PeakArray peakArr, int target) {
            //C: Find a target value from a peak array. We dont know where the peak position i is. So the array is unsorted until we find the peak.
            //A: array is not null or empty; array must have a peak, so at least 3 elements, 0<i<n-1; target value may not exist
            //R: M1: linear scan (call peak.get(idx)) to find the peak - O(n); then run two binary search on left and right half O(logn) to find target. -> TC:O(n+logn); SC:O(1)
            //M2: Can do binary search on peak array to find peak position.
            // 1,3,4, 7 ,5,3,2,0; -> if a[mid-1]<a[mid]<a[mid+1], then peak is at right side; else on left side. reduce search range by half. - O(logn)
            // then two binary search on two half to find target? TC: O(logn); SC:O(1)
            if (peakArr == null || peakArr.length() == 0) return -1;
            int peakIdx = findPeakIndex(peakArr);
            if (peakIdx == -1) {
                return -1;
            } else if (peakArr.get(peakIdx) == target) {
                return peakIdx;
            } else {
                //search for target from left and right part
                int targetIdx = findTarget(peakArr, 0, peakIdx - 1, target);
                return targetIdx != -1 ? targetIdx : findTarget(peakArr, peakIdx + 1, peakArr.length() - 1, target);
            }
        }

        private int findPeakIndex(PeakArray peakArr) {
            int start = 0;
            int end = peakArr.length() - 1;
            while (start <= end) {
                int mid = start + (end - start) / 2;
                //check whether mid is larger than both of its neighbors
                if ((mid - 1 >= 0 && peakArr.get(mid - 1) < peakArr.get(mid)) && (mid + 1 < peakArr.length() && peakArr.get(mid + 1) < peakArr.get(mid))){
                    return mid;
                }else if (mid + 1 >= peakArr.length() || mid - 1 >= 0 && peakArr.get(mid - 1) > peakArr.get(mid)){
                    end = mid - 1;
                }else{
                    start = mid + 1;
                }
            }
            return -1;
        }

        private int findTarget(PeakArray peakArr, int start, int end, int target) {
            while (start <= end) {
                int mid = start + (end - start) / 2;
                if (peakArr.get(mid) == target) {
                    return mid;
                } else if (peakArr.get(mid) > target) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            return -1;
        }
    }

}
