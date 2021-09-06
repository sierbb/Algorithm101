package DynamicProgramming;

public class CountSquareSubmatriceswithAllOnes {

    public int countSquares(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length==0){
            return 0;
        }
        int nRow = matrix.length;
        int nCol = matrix[0].length;
        int[][] DP = new int[nRow][nCol];
        int totalCount = 0;
        for (int i=0; i<nRow; i++){
            if (matrix[i][0] == 1){
                DP[i][0] = 1;
                totalCount+=1;
            }
        }
        for (int j=1; j<nCol; j++){ //avoid duplicates at position 0,0
            if (matrix[0][j] == 1){
                DP[0][j] = 1;
                totalCount+=1;
            }
        }
        //now loop through the matrix and fill in DP array
        for (int i=1; i<nRow; i++){
            for (int j=1; j<nCol; j++){
                if (matrix[i][j] == 1){
                    //check for its three neighbors's value, and get the largest square of 1s ends at m[i][j]
                    DP[i][j] = Math.min(DP[i-1][j], DP[i][j-1]); //0
                    DP[i][j] = Math.min(DP[i][j], DP[i-1][j-1]);
                    DP[i][j]+=1;
                }
                totalCount+=DP[i][j];
            }
        }
        return totalCount;
    }
}

//clarification: similar to largest square of 1s, but now we calculate number of squares
//2D-DP
//M[i][j] represents size of largest square with bottom right corner at m[i][j], as well as the number of square
//TC:O(nm)
//SC:O(mn) -> optimized to O(min(m,n))

//[1,0,1]
//[1,1,0]
//[1,1,0]

//[1,0,1]
//[1,1,0] i
//[1,2,0]

//totalCount = 4+1