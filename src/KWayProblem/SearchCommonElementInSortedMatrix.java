package KWayProblem;

import java.util.*;

/**
 * Given a 2D integer matrix, where every row is sorted in ascending order. How to find a common element in all rows. If there is no common element, then returns -1.
 *
 * Example
 * matrix = { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 2, 3, 4, 8 } }
 * the common element is 4.
 */
class SearchCommonElementInSortedMatrixM2 {

    public int search(int[][] matrix) {
        //C: Every row is in ascending order, column not sorted. Find all common elements in all rows. -> common elements in k sorted array -> find only one common element in k sorted arrays
        //A: matrix column is not sorted; matrix not null nor empty; common element may not exists.
        //There may be more than one common elements between any random pairs of arrays.
        //M1: For each element in first row, do binary search in all other rows to see if exists. TC:O(m*n*logm); SC:O(1)
        //M2: Iterative way. Find list of common elements for each pair, do all pairs and find the final common elements. TC:O(mn); SC:O(m)

        //corner case:
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return -1;
        if (matrix.length == 1) return -1;
        //when matrix has at least two rows
        int[] common = findCommon(matrix[0], matrix[1]);
        for (int i=2; i<matrix.length; i++){
            common = findCommon(matrix[i], common);
            if (common.length == 0) return -1;
        }
        //if we get to the end, there supposed to be only one common element
        return common[0];
    }

    private int[] findCommon(int[] one, int[] two){
        //use two pointers to find common elements from two sorted arrays
        int[] out = new int[Math.min(one.length, two.length)];
        int i = 0;
        int j = 0;
        int idx = 0;
        while (i < one.length && j < two.length){
            if (one[i] == two[j]){
                out[idx] = one[i];
                i++;
                j++;
                idx++;
            }else if (one[i] < two[j]) i++;
            else j++;
        }
        return Arrays.copyOfRange(out, 0, idx);
    }

    // private boolean binarySearch(int[] array, int target){
    //   //binary search target in sorted array
    //   int start = 0;
    //   int end = array.length-1;
    //   while (start <= end){
    //     int mid = start + (end-start)/2;
    //     if (array[mid] == target) return true;
    //     else if (array[mid] > target) end = mid-1;
    //     else start = mid+1;
    //   }
    //   return false;
    // }
}

class SearchCommonElementInSortedMatrix{

    public int search(int[][] matrix) {
        //C: we want to search for the only one common element from a matrix with each row sorted.
        //A: matrix is sorted in row, not column; there will be only one common element if exists; matrix is not null nor empty.
        //R: M1: Use iterative way to compare every pair of rows, find the common list of elements, until all the rows are checked. - TC:O(mn); SC:O(m)
        //M2: Take the last column index as starting point of all arrays. Update the minimum element among the k pointers each round, reduce index if a row's element is larger than current cminimum.
        //Move until all rows has the same element value, which will be the common element.
        if (matrix == null || matrix.length <= 1 || matrix[0].length == 0) return -1;

        //use an array to store the points on each row, each element in points is the index pointer on each row
        int[] points = new int[matrix.length];
        for (int i=0; i<matrix.length;i++){
            points[i] = matrix[i].length-1;
        }
        int minRow = 0; //row index
        while (points[minRow] >=0){
            //Find the smallest val within last column index each round
            for (int i=0; i<matrix.length; i++){
                if (matrix[i][points[i]] < matrix[minRow][points[minRow]]){
                    minRow = i;
                }
            }
            int minCount = 0; //initialize minCount on each round
            for (int i=0; i<matrix.length; i++){
                if ( matrix[i][points[i]] > matrix[minRow][points[minRow]]){
                    if (points[i]==0){
                        return -1; //can not find a common element when a row runs out
                    }
                    points[i]--;
                }else {
                    minCount++;

                }
            }
            if (minCount == matrix.length) return matrix[minRow][points[minRow]];
        }
        return -1;
    }

}

