package BFS;

import java.util.*;

public class ShortestDistanceFromAllBuildings {

    public int[][] DIRS = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};

    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0){
            return -1;
        }
        int nRow = grid.length;
        int nCol = grid[0].length;
        int totalHouses = 0;
        //store { pashCost, totalHouses } and totalHouses +1 for each BFS run, so can mark how many housese it can reach
        int[][][] cost = new int[nRow][nCol][2];

        for (int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                if (grid[i][j] == 1){ //only start from a building since undirected graph
                    totalHouses++;
                    BFS(grid, i, j, cost);
                }
            }
        }
        //Now we should scan the cost matrix to find the answer
        int globalMin = Integer.MAX_VALUE;
        for (int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                if (grid[i][j] == 0 && cost[i][j][1] == totalHouses){ //now only check for empty land
                    globalMin = Math.min(globalMin, cost[i][j][0]);
                }
            }
        }
        return globalMin == Integer.MAX_VALUE? -1 : globalMin;
    }

    private void BFS(int[][] grid, int i, int j, int[][][] cost){
        //running a BFS from the current coordinate
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Cell> fifo = new LinkedList<>();
        fifo.offer(new Cell(i, j));
        visited[i][j] = true;
        int pathCost = 0; //will increase costPath for each round of queue
        while (!fifo.isEmpty()){
            int size = fifo.size();
            for (int k=0; k<size; k++){
                Cell cur = fifo.poll();

                if (grid[cur.x][cur.y] == 0){
                    cost[cur.x][cur.y][0] += pathCost; //only record for empty land
                    cost[cur.x][cur.y][1] +=1; //adding 1 for current BFS run
                }

                for ( Cell nei : findNeighbor(cur, grid) ){
                    //we need to only run finNeighbor from cell 0; since 1 is not a valid current point
                    //get neighbors that's only 0 or 1
                    if (!visited[nei.x][nei.y]){
                        //only offer 0 to queue since 0 can pass
                        fifo.offer(nei);
                        visited[nei.x][nei.y] = true;
                    }
                }
            }
            pathCost++;
        }

    }

    private List<Cell> findNeighbor(Cell c, int[][] grid){
        //find neighbors that are only empty land, since building and obstacles can not pass
        List<Cell> neis = new ArrayList<>();
        for (int i=0; i<DIRS.length; i++){
            int newX = c.x+DIRS[i][0];
            int newY = c.y+DIRS[i][1];
            //only consider 0 and 1 places as valid neighbors
            if (newX >=0 && newX < grid.length && newY >=0 && newY <grid[0].length && grid[newX][newY] == 0){
                neis.add(new Cell(newX, newY));
            }
        }
        return neis;
    }

    class Cell{
        private int x;
        private int y;
        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}

//clarification: BFSII
//only move 4 directions;
//need shortest travel distance to ALL house, if not possible return -1
//The ditance = delta x + delta y, which is the jump dis to the building

//BFSI since uniform cost

//number of buildings = k
//grid.length = n
//grid[0].length = m

//for each 1(building), run one BFS to all the 0's, by adjacent cells, record the distance (pathCost++) on the globalCost matrix.  -> O(k*mn);
//durind this process, if we found from the visited[] ? there's 1 that can not be visted in any BFS, that means those buildings can not be reached each other, this is an invalid graph, return -1
//Finally, scan the grid again to find the minimum cost cell, that is the answer -> O(mn)

//TC:O(k*mn) + O(mn)
//SC:O(mn) for cost matrix and visited matrix