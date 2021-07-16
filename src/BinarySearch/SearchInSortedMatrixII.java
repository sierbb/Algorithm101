package BinarySearch;

import java.util.Arrays;

/**
 * Given a 2D matrix that contains integers only, which each row is sorted in ascending order and each column is also sorted in ascending order.
 * Given a target number, returning the position that the target locates within the matrix.
 * If the target number does not exist in the matrix, return {-1, -1}.
 */

/**
 * Method 1: Binary search row by row
 */
public class SearchInSortedMatrixII {

    public int[] search(int[][] matrix, int target) {
        //C: search for target value in sorted matrix. The matrix can not be consider a sorted 1D array.
        //A: matrix is not null nor empty; target may not be in matrix; has duplicate elements -> have to do binary search row by row
        //R:
        //TC:O(nlogm); SC:O(1)
        int[] res = new int[]{-1, -1};
        if (matrix == null || matrix[0].length == 0) return res;
        int rowIndex = 0;
        while (rowIndex < matrix.length){
            int colIndex = binarySearchRow(matrix, rowIndex, 0, matrix[0].length-1, target);
            if (colIndex != -1) {
                res[0] = rowIndex;
                res[1] = colIndex;
                return res;
            }
            rowIndex++;
        }
        //if not found a valid colIndex, res will be defaults to -1,-1
        return res;
    }

    private int binarySearchRow(int[][] matrix, int row, int start, int end, int target){
        //do binary search on a certain row of a matrix, find the exact target position or return -1
        while (start <= end){
            int mid = start + (end-start)/2;
            if (matrix[row][mid] == target){
                return mid;
            }else if (matrix[row][mid] < target){
                start = mid+1;
            }else {
                end = mid-1;
            }
        }
        return -1;
    }

    public static void main(String[] args){
//        int[][] input = new int[][]{{1,2,5,6,8},{4,5,5,7,11},{7,7,10,11,11},{9,12,14,14,14}};
        int[][] input = new int[][]{{1,2,3,4}};
        SearchInSortedMatrixII obj = new SearchInSortedMatrixII();
        System.out.println(Arrays.toString(obj.search(input, 4)));
    }
}

/***
 * Method 2: Use linear scan, starting from top right corner
 */
class SearchInSortedMatrixIIM2{

    public boolean searchMatrix(int[][] matrix, int target) {
        int i=0;
        int j=matrix[0].length-1; //starting at the last column is the key point here

        while(i <matrix.length && j>=0){
            if(matrix[i][j]== target) return true;
            else if(matrix[i][j] > target) j--;
            else i++;
        }
        return false;
    }
}
