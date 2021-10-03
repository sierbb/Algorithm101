package DynamicProgramming;

public class BombEnemy {

    public int maxKilledEnemies(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        // int[] enemyRow = new int[grid.length];
        int[] enemyCol = new int[grid[0].length];
        int globalMax = 0;
        for (int i= 0; i < grid.length; i++){
            int enemyRow = 0; //reset on each row

            for (int j = 0; j < grid[0].length; j++){

                //recalculate row bomb if at the first column
                if ( j == 0 || j > 0 && grid[i][j-1] == 'W'){
                    enemyRow = 0;
                    for (int m=j; m < grid[0].length; m++){
                        if (grid[i][m] == 'E'){
                            enemyRow++;
                        }else if (grid[i][m] == 'W'){
                            break;
                        }
                    }
                }
                //recalculate column bomb if at the first row
                if ( i == 0 || i > 0 && grid[i-1][j] == 'W' ){
                    enemyCol[j] = 0;
                    for (int k=i; k < grid.length; k++){
                        if (grid[k][j] == 'E'){
                            enemyCol[j]++;
                        }else if (grid[k][j] == 'W'){
                            break;
                        }
                    }
                }
                if (grid[i][j] == '0'){
                    globalMax = Math.max(enemyCol[j] + enemyRow, globalMax);
                }
            }
        }
        return globalMax;
    }
}

//clarification:
//bomb kills same rot and column until wall
//do i have only one bomb?

//M1:
//for i, j:
//    calculate the row and column about how many enemy I can kill
//    for row,  <- i -> to find enemy and stop if wall
//    for j,    <- j  -> to find enemy and stop

//TC:O(mn * (m+n))
//SC:O(1)


//M2: calculate how many enemy on each row/column, record that into int[] row and int[]col
//for i, j
//   calculate row once, calculate col once, if hit wall, recalculate once, update globalMax
//

//[["0","E","0","0"],
//["E","0","W","E"],
//["0","E","0","0"]]

//row ->
//[ 1  1    1   1]
//col
//[              ]

//globalmax =


//col top down
//

//TC:O(mn)
//SC:O(max(m.n))
