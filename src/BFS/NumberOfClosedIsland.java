package BFS;

import java.util.*;

public class NumberOfClosedIsland {

    int[][] DIRS = new int[][]{{0,1}, {0,-1}, {1,0}, {-1,0}};

    public int closedIsland(int[][] grid) {
        //corner case
        if (grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        int[] count = new int[1];
        int nRow = grid.length;
        int nCol = grid[0].length;
        boolean[][] visited = new boolean[nRow][nCol];
        for (int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                if (grid[i][j] == 0){ //only traverse 0 grid
                    BFS(grid, i, j, visited, count);
                }
            }
        }
        return count[0];
    }

    private void BFS(int[][] grid, int i, int j, boolean[][] visited, int[] count){
        if (visited[i][j]){ //has visited
            return;
        }
        boolean reachEdge = isEdge(i, j, grid);
        Queue<Cell> fifo = new LinkedList<>();
        fifo.offer(new Cell(i, j));
        visited[i][j] = true;
        //expand its neighbors that has not been visited that are 0s
        while (!fifo.isEmpty()){
            Cell cur = fifo.poll();
            for (int k=0; k<DIRS.length; k++){
                int newI = cur.x+DIRS[k][0];
                int newJ = cur.y+DIRS[k][1];
                if (newI >= 0 && newI < grid.length && newJ >=0 && newJ < grid[0].length && grid[newI][newJ] == 0 && !visited[newI][newJ]){
                    //if the neighbor reach the edge, then we mark this component as invalid but still finish traverse it
                    if (isEdge(newI, newJ, grid)){
                        reachEdge = true;
                    }
                    fifo.offer(new Cell(newI, newJ));
                    visited[newI][newJ] = true;
                }

            }
        }
        if (!reachEdge){ //only when this component not reaching the edge, can it be consider a closed island
            count[0]++;
        }
    }


    private boolean isEdge(int x, int y, int[][] grid){
        return x == 0 || x == grid.length-1 || y == 0 || y == grid[0].length-1;
    }

    static class Cell{
        int x;
        int y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args){
        int[][] grid = new int[][]{{1,1,1,1,1,1,1,0},{1,0,0,0,0,1,1,0},{1,0,1,0,1,1,1,0},{1,0,0,0,0,1,0,1},{1,1,1,1,1,1,1,0}};
        NumberOfClosedIsland obj = new NumberOfClosedIsland();
        System.out.println(obj.closedIsland(grid));
    }

}

//Find all cells surrounded by 1
//Clarification: start from a cell, find how many unconnected components it has that's all 0?
//Also the components can not hit the edge, so if a component read a cell that has x=0 || x=nCol-1 || y=0 || y=nRow-1, then we can dropped the component

//for i
//  for j
//    run a BFS with global boolean[][] visited, count++ only if after the current expand, we haven't hit any edge?
//
//TC:O(mn*mn) for each cell, run BFS
//SC:O(mn) for visited, q for holding neighbor cells
