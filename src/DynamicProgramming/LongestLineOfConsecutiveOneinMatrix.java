package DynamicProgramming;

public class LongestLineOfConsecutiveOneinMatrix {

    public int longestLine(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0){
            return 0;
        }
        //DP to build only one matrix by looking at all four directions
        int globalMax = 0;
        int[][][] dp = new int[mat.length][mat[0].length][4];
        for (int i=0; i < mat.length; i++){
            for (int j=0; j < mat[0].length; j++){
                if (mat[i][j] == 1){
                    //horizontal
                    dp[i][j][0] = j > 0 ? dp[i][j-1][0]+1 : 1; //if out of bound (on the edge, then dp == 1)
                    dp[i][j][1] = i > 0 ? dp[i-1][j][1]+1: 1;
                    dp[i][j][2] = (i > 0 && j> 0) ? dp[i-1][j-1][2]+1: 1;
                    dp[i][j][3] = (j < mat[0].length-1 && i > 0) ? dp[i-1][j+1][3]+1: 1; //j 看右边的值，一般一直都是0吧？
                }
                globalMax = Math.max(globalMax, Math.max(dp[i][j][0], Math.max(dp[i][j][1], Math.max(dp[i][j][2], dp[i][j][3]))));
            }
        }
        return globalMax;
    }
}

//clarification: line can be diagonal so 8 directions, no BFS since we need a straight line, not turning point

//M1: brite force: find all lines exists in the matrix -> top down + bottom up + left right + diagonal
//TC:O(mn); SC:O(max(m, n)) for each line

//M2: Similar to longest corss of 1s !!!!!
//Use four direction matrixs, each matrix is read from horizontal, vertical, diagonal, anti-diagonal. Actually one matrix for each bi-direction since we only care about the total length of a line, not the arm or cross position.
//Use globalMax to update the largest value among the 4 matrixs as we build them.

//TC:O(4*mn) for travesing entire matrix 4 times
//SC:O(mn) for all 4 matrics for 4 directions

//M3: Since for each row in the direction matrix, we only need to maintain that row as we go.
//2D -> 1D to find out largest in each matrix.

//TC:O(mn) for traversing entire matrix
//SC:O(min(m,n)) for only one row or one column