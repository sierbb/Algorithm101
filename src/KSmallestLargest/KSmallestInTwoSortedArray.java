package kSmallestLargest;

import java.util.*;

/**
 * Given two sorted arrays of integers, find the Kth smallest number.
 * Assumptions
 * The two given arrays are not null and at least one of them is not empty
 * K >= 1, K <= total lengths of the two sorted arrays
 *
 * Examples
 * A = {1, 4, 6}, B = {2, 3}, the 3rd smallest number is 3.
 * A = {1, 2, 3, 4}, B = {}, the 2nd smallest number is 2.
 */


/**
 * Method 1: Cut k/2 elements from array a or b each time.
 */
public class KSmallestInTwoSortedArray {
    //Brute force method: Shuixiaoyishui for k times, to merge two sorted array into one. Then read k smallest directly. TC:O(k); SC:O(k)
    public int kth(int[] a, int[] b, int k) {
        //Algorithm: Use binary search to cut k/2 elements from array a or b each time. So time is improved.
        //Rule: Each time, cut k into half, compare a[k/2] and b[k/2] get the larger one to cut k/2 elements (guarantee to be larger than k/2 nodes)
        //TC:O(logk), SC:O(1)
        return kthHelper(a, -1, b, -1, k);
    }

    public int kthHelper(int[] a, int a_idx, int[]b, int b_idx, int k){
        if (a_idx >= a.length-1){ //already full
            return b[b_idx+k];
        }
        if (b_idx >= b.length-1){
            return a[a_idx+k]; //put all the k quota to a
        }
        if (k==1){
            return a[a_idx+1] < b[b_idx+1]? a[a_idx+1]:b[b_idx+1];
        }

        //if k ==2, can still run this function
        if (a_idx+k/2 < a.length && b_idx+k/2 < b.length){
            if (a[a_idx+k/2] <= b[b_idx+k/2]){
                a_idx=a_idx+k/2;
            } else {
                b_idx=b_idx+k/2;
            }
        } else if (a_idx+k/2 > a.length-1){
            //means the other array will have enough nodes, cut the other side
            b_idx=b_idx+k/2;
        } else if (b_idx+k/2 > b.length-1){ //what if original size is smaller than k/2??
            a_idx=a_idx+k/2;
        }
        return kthHelper(a, a_idx, b, b_idx, k-k/2); // k has been cut half
    }

}

/**
 * Method 2:
 */
class SolutionM2{
    public int kth(int[] a, int[] b, int k) {
        //Algorithm: M1: cut k/2 from a or b each time, depends on a[k/2] and b[k/2] which one is smaller. So we reduce search space by 1/2 each time. TC:O(logk); SC:O(1)
        //M2: Assume a[i] is the kth smallest. We must have b[k-i-2]<a[i]< b[k-i-1]. why?? Since we must have
        // 0123 i 456
        // aaaa a aaaaa --> total i+1 elements from array a
        // bbbbbbb  b      b  bb --> total k-i-1 elements from array b -> b[k-i-2] is the last element from b.
        //        k-i-2   k-i-1
        //So since a[i] is the last element from a, and to keep its kth meaning, we can not have b[k-i-2]>= it; nor can have b[k-i-1]<=it.
        //So we will try to find a position i in a to satisfy the above conditions. We can do binary search on it, instead of scannign whole array.
        if (a == null || b == null){
            return -1;
        }
        if (a.length == 0){
            return b[k-1];
        }
        if (b.length == 0){
            return a[k-1];
        }
        //Do binary search to get the kth smallest index on array a, if not found, search in array b
        int idx = findKthHelper(a, b, k);
        if (idx != -1){
            return a[idx];
        }else {
            return b[findKthHelper(b, a, k)];
        }
    }

    private int findKthHelper(int[] a, int[] b, int k){
        //return the index in array a that is the kth smallest from a and b
        int left = 0;
        int right = a.length-1;
        while (left <= right){
            int mid = left+(right-left)/2;
            if (a[mid] < readIndex(b, k-mid-2)){
                //mid is a bit too small - look for index in larger index range
                left = mid+1;
            } else if (a[mid] > readIndex(b, k-mid-1)){
                right = mid-1;
            } else {
                return mid;
            }
        }
        return -1;
        //test
        //(b, a, k);
        //left = 0, right = 1; mid = 0;
        //b[0] < a[3-0-2] = a[1] = 4 ? yes, left=1
        //left = 1; right = 1; mid = 1;
        //b[1] < a[3-1-2] = a[0] = 1; No; b[1] > a[3-1-1] = a[1]; No;
        //return mid = 1; -> b[1] = 3
    }

    private int readIndex(int[] array, int idx){
        //handle when idx is out of bound
        if (idx <0){
            return Integer.MIN_VALUE;
        }else if (idx >= array.length){
            return Integer.MAX_VALUE;
        }else {
            return array[idx];
        }
    }
}
