package Array;

import java.util.*;

public class SpiralMatrix {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return res;
        //set starting point and armLen
        int offset = 0; //the start index horizontally and vertically
        int n = matrix.length, m = matrix[0].length;
        int xSize = n;
        int ySize = m;

        while (xSize >1 && ySize >1){
            //while the we dont have any space to loop the elements
            for (int j=0; j < ySize-1; j++){ //top row
                res.add(matrix[offset][offset+j]);
            }
            for (int i=0; i< xSize-1; i++){ //rightmost column
                res.add(matrix[i+offset][offset+ySize-1]);
            }
            for (int j=ySize-1; j>0; j--){ //bottom row
                res.add(matrix[offset+xSize-1][j+offset]);
            }
            for (int i=xSize-1; i>0; i--){ //offset=1; offset+ySize-1
                res.add(matrix[i+offset][offset]);
            }
            offset++;
            xSize-=2;
            ySize-=2;
        }

        //if we still have one cell left
        if (ySize == 1){ // a column left
            for (int i=offset; i<offset+xSize; i++){
                res.add(matrix[i][offset]);
            }
        }else if (xSize == 1){ //a row left
            for (int j=offset; j< offset+ ySize; j++){
                res.add(matrix[offset][j]);
            }
        }
            return res;
    }

    public static void main(String[] args){
        SpiralMatrix obj = new SpiralMatrix();
        int[][] input = new int[][]{{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        System.out.println(obj.spiralOrder(input));
    }
}