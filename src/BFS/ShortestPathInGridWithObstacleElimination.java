package BFS;

import java.util.*;
import java.util.LinkedList;

public class ShortestPathInGridWithObstacleElimination {

    int[][] DIRS = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

    public int shortestPath(int[][] grid, int k) {
        //corner case: no path if no grid
        if (grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        //Run BFS from the only starting point, to find shortest path to reach destination point
        //visited allows for different quota usage to reach the same cell
        boolean[][][] visited = new boolean[grid.length][grid[0].length][k+1];
        Queue<int[]> fifo = new LinkedList<>();
        fifo.offer(new int[]{0, 0, 0});
        visited[0][0][0] = true;
        //we need to record the path cost, so we do level order traversal
        int cost = 0;
        while (!fifo.isEmpty()){
            int size = fifo.size();
            for (int i=0; i<size; i++){
                int[] cur = fifo.poll();
                //if we have reached the destination, return current cost
                if (cur[0] == grid.length-1 && cur[1] == grid[0].length-1){
                    return cost;
                }
                for (int m=0; m<DIRS.length; m++){
                    int newX = cur[0]+DIRS[m][0];
                    int newY = cur[1]+DIRS[m][1];
                    if (newX >=0 && newX <grid.length && newY >=0 && newY <grid[0].length){
                        int newK = grid[newX][newY] == 1 ? cur[2]+1 : cur[2];
                        //if not out of bound and quota not used up and not visited before on such cost
                        if (newK <= k && !visited[newX][newY][newK]){
                            fifo.offer(new int[]{newX, newY, newK});
                            visited[newX][newY][newK] = true;
                        }
                    }
                }
            }
            //increase the path cost as we go over another round
            cost++;
        }
        return -1; //if we are not able to reach destination from the BFS, return -1
    }

    public static void main(String[] args){
        ShortestPathInGridWithObstacleElimination obj = new ShortestPathInGridWithObstacleElimination();
        int[][] grid = new int[][]{{0,0,0},{1,1,0},{0,0,0},{0,1,1},{0,0,0}};
        System.out.println(obj.shortestPath(grid, 1));

    }

}

//clarification: 1 is obstacle, eliminate k most
//Find shortest path
// [[0,0,0],
//  [1,1,0],
//  [0,0,0],
//  [0,1,1],
//  [0,0,0]],

//find the path to destination with the least obstacles -> that will be the path?
//BFS to find shortest path rather than use DFS.
//When see obstacle(from the shortest path), we can just use the quota, if quota is used up, means we are not able to do that.
//How to deal with it is used up from one path, but it should actually be used in another path?
//So we used a 3D visited matrix to allow the same cell being visited more than once if the quota is different(cost differnt too).
//That means we are exploring different path cost reaching the same cell? yes.
//Also point is, we dont have the global quota as we expore different path, each path has its own quota to exhast, so the quota from one path won't affect the quota from another path. This way we can still explore the shortest, and at the same time isolate the quota usage for different paths!

//Essentially there are (k + 1) BFSs going on in parallel, and each one is independent of the other. So we need to keep track of the visited nodes for each BFS (one BFS means traversal by removing a constant amount of obstacles).

//TC:O(V*k) = (mnk) -> explore different path with different quota usage
//SC:O(V*K) for visited matrix