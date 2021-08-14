package Array;

public class ValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return false;
        int nRow = board.length, nCol = board[0].length, nMatrix = (nRow/3)*(nRow/3);
        int[] rows = new int[nRow];
        int[] cols = new int[nCol];
        int[] matrixs = new int[nMatrix];
        //Now walk through each filled cell in board and record the bit
        for (int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                if (board[i][j] == '.') continue;
                //check row
                int pos = 1 << (board[i][j] - '1'); //range from 1~9 so position
                if ( (rows[i] & pos) >0){
                    return false;
                }else {
                    rows[i] |= pos;
                }
                //check col
                if ( (cols[j] & pos) >0){
                    return false;
                }else {
                    cols[j] |= pos;
                }
                //check matrix
                int x = i/3, y = j/3;
                int posM = x*3+y;
                if ( (matrixs[posM] & pos) >0){
                    return false;
                }else {
                    matrixs[posM] |= pos;
                }
            }
        }
        return true;
    }
}

//M1: Bitmask for each "Set".
//Create array of int, each int is bitmask of length 9 (1~9).

//Use if (rows[r] & 1 << pos) > 0 to decide whether the bit position has been seen.
//pos is the position to move 1 bit from right side = board[r][j]-'1'.
//if pos=0 means move 0 means 1 at rightmost, means we are checking number 1 at position 0?

//TC:O(mn)
//SC:O(3*n) for 3 int array of size n
