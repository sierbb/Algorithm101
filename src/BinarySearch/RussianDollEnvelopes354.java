package BinarySearch;

import java.util.*;

class RussianDollEnvelopes354M1 {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) return 0;
        Arrays.sort(envelopes, new Comparator<int[]>(){
            @Override
            public int compare(int[] e1, int[] e2){
                if (e1[1] == e2[1]) return 0;
                return e1[1] > e2[1]? 1 : -1;
            }
        });
        int [] DP = new int[envelopes.length];
        int globalMax = Integer.MIN_VALUE;
        DP[0] = 1; //first can do it once
        for (int i=1; i<envelopes.length; i++){
            int curMax = 1;
            for (int j=0; j<i; j++){
                if (envelopes[i][1] > envelopes[i-1][1] && envelopes[i][0] > envelopes[i-1][0]){
                    curMax = Math.max(curMax, DP[j]+1);
                }
            }
            DP[i] = curMax;
            globalMax = Math.max(globalMax, curMax);
        }
        return globalMax;
    }


    public static void main(String[] args){
        RussianDollEnvelopes354 obj = new RussianDollEnvelopes354();
//        int[][] input = new int[][]{{5,4},{6,4},{6,7},{2,3}};
        int[][] input = new int[][]{{2,1},{4,1},{6,2},{8,3},{10,5},{12,8},{14,13},{16,21},{18,34},{20,55}};
        System.out.println(obj.maxEnvelopes(input));
    }
}


public class RussianDollEnvelopes354 {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) return 0;
        Arrays.sort(envelopes, new Comparator<int[]>(){
            @Override
            public int compare(int[] e1, int[] e2){
                //if equals, take the other dimension when its larger its higher priority
                if (e1[1] == e2[1]) return e2[0]-e1[0];
                return e1[1] > e2[1]? 1 : -1;
            }
        });
        int [] DP = new int[envelopes.length];
        //M2: Use binary search to fill in dp array with the second dimension
        DP[0] = envelopes[0][0];
        int len = 1;
        for (int i=1; i<envelopes.length; i++){ //index from sorted array
            int candid = findSmallestLarger(DP, 0, len-1, envelopes[i][0]);
            if (candid == -1){
                //if not found in DP, append ele to end of DP array
                DP[len] = envelopes[i][0];
                len++;
            }
            else DP[candid] = envelopes[i][0]; //replace previous DP value
        }
        return len;
    }

    private int findSmallestLarger(int[] array, int start, int end, int target){
        //find the first element >= target
        while (start+1 < end){
            int mid = start +(end-start)/2;
            if (array[mid] >=  target) {
                end = mid;
            }else {
                start=mid+1;
            }
        }
        if (array[start] >= target) return start;
        else if (array[end] >= target) return end;
        return -1;
    }


    public static void main(String[] args){
        RussianDollEnvelopes354 obj = new RussianDollEnvelopes354();
//        int[][] input = new int[][]{{5,4},{6,4},{6,7},{2,3}};
        int[][] input = new int[][]{{2,1},{4,1},{6,2},{8,3},{10,5},{12,8},{14,13},{16,21},{18,34},{20,55}};
        System.out.println(obj.maxEnvelopes(input));
    }
}