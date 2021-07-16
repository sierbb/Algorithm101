package KWayProblem;

import org.junit.Test;

import KWayProblem.SearchCommonElementInSortedMatrix;
import static org.junit.jupiter.api.Assertions.*;

public class SearchCommonElementInSortedMatrixTest {

    @Test
    public void testHasCommonWithDiffColumnCount() {
        int[] array1 = new int[]{1,2,3,4,10,11,100,200};
        int[] array2 = new int[]{5,6,7,8,9,10,10,11,100,101};
        int[] array3 = new int[]{2,3,5,8,9,12,99,100};
        int[][] matrix = new int[][]{array1, array2, array3};
        SearchCommonElementInSortedMatrix obj = new SearchCommonElementInSortedMatrix();
        int res = obj.search(matrix);
        assertEquals(res, 100);
    }

    @Test
    public void testHasCommonWithSameColumnCount() {
        int[] array1 = new int[]{1};
        int[] array2 = new int[]{1};
        int[] array3 = new int[]{1};
        int[] array4 = new int[]{1};
        int[][] matrix = new int[][]{array1, array2, array3, array4};
        SearchCommonElementInSortedMatrix obj = new SearchCommonElementInSortedMatrix();
        int res = obj.search(matrix);
        assertEquals(res, 1);
    }

    @Test
    public void testNoCommon(){
        int[] array1 = new int[]{1,2,3,4,10,11,200};
        int[] array2 = new int[]{5,6,7,8,9,11,101};
        int[] array3 = new int[]{2,3,5,8,9,12,99};
        int[][] matrix = new int[][]{array1, array2, array3};
        SearchCommonElementInSortedMatrix obj = new SearchCommonElementInSortedMatrix();
        int res = obj.search(matrix);
        assertEquals(res, -1);
    }
}