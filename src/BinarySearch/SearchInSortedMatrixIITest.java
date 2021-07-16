package BinarySearch;

import BinarySearch.SearchInSortedMatrixII;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchInSortedMatrixIITest {

    @Test
    public void testHasCommon() {
        int[] array1 = new int[]{1,2,5,6,8};
        int[] array2 = new int[]{4,5,5,7,11};
        int[] array3 = new int[]{7,7,10,11,11};
        int[] array4 = new int[]{9,12,14,14,14};
        int[][] matrix = new int[][]{array1, array2, array3, array4};
        SearchInSortedMatrixII obj = new SearchInSortedMatrixII();
        int[] res = obj.search(matrix, 10);
        assertArrayEquals(res, new int[]{2, 2});
    }

    @Test
    public void testNoCommon(){
        int[] array1 = new int[]{1,2,5,6,8};
        int[] array2 = new int[]{4,5,5,7,11};
        int[] array3 = new int[]{7,7,10,11,11};
        int[] array4 = new int[]{9,12,14,14,14};
        int[][] matrix = new int[][]{array1, array2, array3, array4};
        SearchInSortedMatrixII obj = new SearchInSortedMatrixII();
        int[] res = obj.search(matrix, 13);
        assertArrayEquals(res, new int[]{-1, -1});
    }
}