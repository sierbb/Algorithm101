package Array;

public class SudokuSolver {

    public void solveSudoku(char[][] board) {
    doSolve(board, 0, 0);
}

    private boolean doSolve(char[][] board, int row, int col) {
        for (int i = row; i < board.length; i++, col=0) { // note: must reset col here!?
            //every time we do i++, we reset col to 0
            for (int j = col; j < board.length; j++) {
                if (board[i][j] != '.') continue;
                for (char num = '1'; num <= '9'; num++) {
                    if (isValid(board, i, j, num)) {
                        board[i][j] = num;
                        if (doSolve(board, i, j + 1)){
                            //this will return false if we've tried a cell start from i, j+1 to n-1,n-1
                            //if return a true, exit early and we dont have to reset the cells
                            return true;
                        }
                        board[i][j] = '.';
                    }
                }
                //if we try all numbers but have not found one that returns true, returns false?
                return false;
            }
        }
        //if we have try all the rows with all the columns, means we've finished filling the board with a proper answer
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char num) {
        //123456   i = 2, j=1 -> metrixR = i/3 *3 =0, metrixC = j/3 *3 =0; -> block [0,0] top left corner;
        //120456
        //123456   blockCell = board[topleftR + i/3][topleftR + i%3]
        //123456
        //123456
        //123456

        int metrixR = (row / 3) * 3, metrixC = (col / 3) * 3;// Block no. is i/3, first element is i/3*3
        for (int i = 0; i < board.length; i++)
            //if any of the cell on the same row/col/block aleady has num, then it is invalid
            if (board[i][col] == num || board[row][i] == num || board[metrixR + i / 3][metrixC + i % 3] == num)
                return false;
        return true;
    }
}
