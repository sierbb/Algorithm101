package PriorityQueue;

import java.util.*;

public class KClosestPointsToOrigin {
    public int[][] kClosest(int[][] points, int k) {
        if (points == null || points.length == 0){
            return new int[][]{};
        }
        Set<String> set = new HashSet();
        PriorityQueue<Cell> maxHeap = new PriorityQueue<>(k, new Comparator<Cell>(){
            @Override
            public int compare(Cell c1, Cell c2){
                if (c2.dis == c1.dis){
                    return 0;
                }
                return c2.dis > c1.dis ? 1 : -1;
            }
        });

        for (int i=0; i<points.length; i++){
            int x = points[i][0];
            int y = points[i][1];
            String cor = x + "," + y;
            if (!set.contains(cor)){
                set.add(cor);
            }else {
                continue; //if we've seen same coordinate, skip
            }
            Cell c = new Cell(points[i][0], points[i][1]);
            if (i < k){
                maxHeap.offer(c);
            }else {
                if (c.dis < maxHeap.peek().dis){
                    maxHeap.poll();
                    maxHeap.offer(c);
                }
            }
        }
        //now we have maxheap has the k smallest points, return them
        int[][] res = new int[k][2];
        for (int i=0; i<k; i++){
            Cell cur = maxHeap.poll();
            res[i] = new int[]{cur.x, cur.y};
        }
        return res;

    }

    class Cell{
        private int x;
        private int y;
        private double dis;

        public Cell(int x, int y){
            this.x = x;
            this.y = y;
            dis = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2));
        }
    }
}

//clarification:
//kth closest point means if point has same distance, they will be consider different points.
//But there could be points that has same coordinates.

//points = [[3,3],[5,-1],[-2,4]], k = 2
//distance = Math.sqrt( Math.pow(x, 2) + Math.pow(y,2) );
//sqrt(3^2 + 3^2)

//sort the arrray of points, to have the one with smallest coordinate to be first?
//there are also positive and negative points -> use a PQ to sort it based on distance?

//points.length = n.
//TC:O(nlogk) for n times offer and poll() from pq
//SC:O(k) for pq size
