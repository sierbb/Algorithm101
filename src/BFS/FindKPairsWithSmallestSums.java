package BFS;

import java.util.*;

/**
 * You are given two integer arrays nums1 and nums2 sorted in ascending order and an integer k.
 */
public class FindKPairsWithSmallestSums {

    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0 || k == 0){
            return res;
        }
        // PriorityQueue<int[]> pq = new PriorityQueue<>((a1, a2) -> nums1[a2[0]]+nums2[a2[1]] - nums1[a1[0]]-nums2[a1[1]]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>(){
            @Override
            public int compare(int[] a1, int[] a2){
                return nums1[a1[0]]+nums2[a1[1]] - nums1[a2[0]]-nums2[a2[1]]; //smaller is higher
            }
        });
        int i = 0;
        int j = 0;
        pq.offer(new int[]{0, 0});
        int count = 0;
        boolean[][] visited = new boolean[nums1.length][nums2.length];
        visited[0][0] = true;
        while (count < k && !pq.isEmpty()){
            //poll k pairs should be enough
            int[] cur = pq.poll();
            List<Integer> sol = new ArrayList<>();
            sol.add(nums1[cur[0]]);
            sol.add(nums2[cur[1]]);
            res.add(sol);
            count++;
            //add its neighbors
            if (cur[0]+1 < nums1.length ){
                visited[cur[0]+1][cur[1]] = true;
                pq.offer(new int[]{cur[0]+1, cur[1]});
            }
            if (cur[1]+1 < nums2.length && !visited[cur[0]][cur[1]+1]){
                visited[cur[0]][cur[1]+1] = true;
                pq.offer(new int[]{cur[0], cur[1]+1});
            }
        }
        return res;
    }

    public static void main(String[] args){
        FindKPairsWithSmallestSums obj = new FindKPairsWithSmallestSums();
        int[] nums1 = new int[]{1,1,2};
        int[] nums2 = new int[]{1,2,3};
        System.out.println(obj.kSmallestPairs(nums1, nums2, 10));
    }
}

//from all the pairs possible, get the k pairs with smallest sum
//both arrays are sorted
///k guarantee < nums1 and nums2.length? No
//M1:
//Start from the smallest pair must be u0+ v0 since sorted
//Then move pointer to the next smallest element from both array each time, get the sum and add to result list; until find k pairs OR when one of the array runs out
//-> question becomes find k smallest value in sorted matrix -> BFS with visited

//TC:O(klogk);
//SC:O(k) for pq size at most 2k (poll one push 2) + O(nums1*nums2) for visited ->