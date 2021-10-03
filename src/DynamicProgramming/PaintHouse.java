package DynamicProgramming;

public class PaintHouse {

    public int minCost(int[][] costs) {

        if (costs == null || costs.length == 0 || costs[0].length == 0){
            return 0;
        }
        int[][] minCost = new int[costs.length][costs[0].length];
        for (int i=0; i < costs.length; i++){
            if (i == 0){ //first house
                for (int j = 0; j < costs[0].length; j++){
                    minCost[i][j] = costs[i][j];
                }
            }else {
                minCost[i][0] = Math.min(minCost[i-1][1], minCost[i-1][2]) + costs[i][0];
                minCost[i][1] = Math.min(minCost[i-1][0], minCost[i-1][2]) + costs[i][1];
                minCost[i][2] = Math.min(minCost[i-1][0], minCost[i-1][1]) + costs[i][2];
            }
        }
        int min = Integer.MAX_VALUE;
        for (int j=0; j < costs[0].length; j++){
            min = Math.min(min, minCost[costs.length-1][j]);
        }
        return min;
    }
}

//clarification:
//can paint 3 colors at most on each cell, no two adjacent house same color
//what is adjacent?

//cost
// r ,b, g
//[[17,2,17], house1
//[16, 16,5],  house2
//[14, 3,19]], house3

//M1: DFS
//TC:O(3* 2^(n-1)) = O(2^n)
//SC:O(n)

//M2: DFS with memorization
//Momorize when we paint house i for color j
//memo[i][j] because if we paint house i red, we must paint house i-1 b or g, and since the price, we will always choose the cheaper one, so this will be fixed and can be reused when we paint upper level houses.
//      r1          b2       g3        house 1
//    b*4   *g5      r*6   X   X   X   house 2   -> house 2 green = min(house1=r, house 1=b) -> fixed
// r* g*     X  b*   X X               house 3   -> house 3 each color, look at house 2's color and pick the cheapest

//TC:O(n*3*2) = O(n)
//SC:O(3n)  O(n)