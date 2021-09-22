package DynamicProgramming;

import java.util.*;

public class CuttingWood {

    public int minCost(int n, int[] cuts) {
        if (cuts == null || cuts.length == 0){
            return 0;
        }
        Arrays.sort(cuts);
        int[] pad = new int[cuts.length+2];
        pad[0] = 0;
        pad[pad.length-1] = n;
        for (int i=0; i<cuts.length; i++){
            pad[i+1] = cuts[i];
        }
        //pad[i] now represents the length of wood at each cut position
        //For each piece of wood from cut position [j, i] find the minCost
        int[][] DP = new int[pad.length][pad.length];
        for (int i=1; i < pad.length; i++){
            for (int j=i-1; j>=0; j--){
                if (i-j == 1){ //base case
                    DP[j][i] = 0;
                }else {
                    DP[j][i] = Integer.MAX_VALUE;
                    for (int k=j+1; k<i; k++){
                        DP[j][i] = Math.min(DP[j][i], DP[j][k]+DP[k][i]);
                    }
                    DP[j][i] += pad[i]-pad[j];
                }
            }
        }
        return DP[0][pad.length-1];
    }


    public static void main(String[] args){
        CuttingWood obj = new CuttingWood();
        int[] input = new int[]{1,2,4,5,6};
        System.out.println(obj.minCost(9, input));

    }
}

//clarification:
//Cutting wood and cost is different
//DP[i][j] represents minimum cost of cutting the wood at cuts[i] to cuts[j]
//base case is when the wood can no longer being cut ->
//for i in 0-n, i++; for j in i-1, 0, j--, this way can fill in base case then go from there

//cuts = [0, 1, 3,4,5, 7]
//n = 7
//      [1,  2, 1, 1, 2       ] length of subwood in between cut positions

//size=1
//DP[0][1] -> 0
//DP[1][2] -> 0
//DP[2][3] -> 0
//DP[3][4] -> 0
//DP[4][5] -> 0

//size=2
//DP[0][2] -> DP[0][1] + DP[1][2] + 3-0 = 3;  i< k < j, k=1
//DP[1][3] -> DP[1][2] + DP[2][3] + 4-1 = 3;
//DP[2][4] -> 0+0 + 2 = 2;
//DP[3][5] -> 0+0+ 3 = 3;

//size=3
//DP[0][3] -> min(DP[0][1]+DP[1][3] , DP[0][2] + DP[2][3]) + 4-0 = 7; i< k < j; k=1, 2


//TC:O(n^3)
//SC:O(n^2) for DP array