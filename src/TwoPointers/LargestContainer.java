package TwoPointers;

public class LargestContainer {

    /**
     * Given an array of non-negative integers, each of them representing the height of a board perpendicular to the horizontal line, the distance between any two adjacent boards is
     * 1. Consider selecting two boards such that together with the horizontal line they form a container.
     * Find the volume of the largest such container.
     *
     * Assumptions
     * The given array is not null and has size of at least 2
     * Examples
     * { 2, 1, 3, 1, 2, 1 }, the largest container is formed by the two boards of height 2, the volume of the container is 2 * 4 = 8.
     * @param array
     * @return
     */
    public int largest(int[] array) {
        //C: Find the two boarders so the volume between them are the largest. Similar to max water trapped?
        //A: We dont need to care about any boards between the selected two; array is not null or empty;
        //R: M1: Brute force: For each index, start from this index, walk towards left and right side。 O(n^2)
        //M2: Two pointers, start from 0 and n-1, walk towards until left+1=right，if the left is shorter than right, left++; else right--(since we want to explore and see if there are anything taller).
        //Update container volumn when we move a pointer. TC:O(n); SC:O(1)
        if (array == null || array.length <= 1) return 0;
        int left = 0, right = array.length-1;
        int globalMax = 0;
        while (left+1 <= right){
            //stop when left+1 == right, which is the last container
            globalMax = Math.max(globalMax, Math.min(array[left], array[right]) * (right-left));
            if (array[left] <= array[right]) left++;
            else right--;
        }
        return globalMax;
    }
}
