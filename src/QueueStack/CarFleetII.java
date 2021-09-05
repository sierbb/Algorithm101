package QueueStack;

import java.util.*;

public class CarFleetII {

    public double[] getCollisionTimesM1(int[][] cars) {
        if (cars == null || cars.length == 0 || cars[0].length == 0) {
            return new double[0];
        }
        double[] res = new double[cars.length];
        Arrays.fill(res, -1.0);
        //from rightmost pairs, for each car, find the first car in the right that will intersect with current
        for (int i = cars.length - 2; i >= 0; i--) {
            int[] cur = cars[i];
            double minTime = Integer.MAX_VALUE; //minTime to intersect for current car
            for (int j = i + 1; j < cars.length; j++) { //for each car after if
                if (cars[j][1] < cur[1]) { //only if car j speed smaller than cur will they intersect
                    //calculate the possible intersect time, and compare to the time j already has (to intersect with cars after j)
                    double intersectTime = (double) (cars[j][0] - cur[0]) / (double) (cur[1] - cars[j][1]);
                    if (res[j] == -1.0 || intersectTime < res[j]) {
                        //if j does not intersect with others or if cur intersect with j before it intersect with others, this is a possible minTime
                        //Since here we must have intersectTime > res[j] will we need to keep checking the next j, otherwise we can break here.
                        //because if res[j] is increasing, then the previous res[i] only need to compare with the next res[j] to get its intersectTime(must be min)
                        minTime = Math.min(minTime, intersectTime);
                        break;
                    }
                }
            }
            res[i] = minTime == Integer.MAX_VALUE ? -1.0 : minTime;
        }
        return res;
    }


    public double[] getCollisionTimes(int[][] cars) {
        if (cars == null || cars.length == 0 || cars[0].length == 0){
            return new double[0];
        }
        double[] res = new double[cars.length];
        Arrays.fill(res, -1.0);
        Deque<Integer> stack = new ArrayDeque<>();
        stack.offerFirst(cars.length-1);
        //from rightmost pairs, for each car, find the first car in the right that will intersect with current
        for (int i = cars.length-2; i >= 0; i--){
            int[] cur = cars[i];
            while (!stack.isEmpty()){
                //keep peek on the next j until we see the first j we are able to collide with
                //this means current speed > j's speed, AND current collide with j before j collide with its next cars
                int nextIdx = stack.peekFirst();
                int[] next = cars[nextIdx];
                if (cur[1] <= next[1] || 1.0*(next[0]-cur[0])/(cur[1]-next[1]) > res[nextIdx] && res[nextIdx] >0 ){ //if cur can not collide with j, poll j and check on next
                    stack.poll();
                }else {
                    break; //if can collide, this will be the final collide time for current car, we record the collide time
                }
            }
            if (!stack.isEmpty()){ //record collide time for current car
                int collideIdx = stack.peekFirst();
                int[] collide = cars[collideIdx];
                res[i] = 1.0*(collide[0]-cur[0])/(cur[1]-collide[1]);
            }
            //finally, push cur to stack
            stack.offerFirst(i);
        }
        return res;
    }


    public static void main(String[] args){
        CarFleetII obj = new CarFleetII();
        int[][] input = new int[][]{{1,2},{2,1},{4,3},{7,2}};
        System.out.println(Arrays.toString(obj.getCollisionTimes(input)));
    }


}
// [[3,4],[5,4],[6,3],[9,1]]
//  2      1     1.5    -1
//   cur
//  [3,4]  -> [9,1]  3+4x = 9+1x -> 3x=6, x=2, [3,4]&[6,3]? 3+4x=6+3x, x=3,no
//  [5,4]  -> [5,4]&[6,3] -> 5+4x=6+3x, x=1; if [5,4]&[9,1], 3x=4,x=1.7, No - done
//  [6,3]  -> [9,1]   6+3x=9+1x -> 2x=3, x=1.5  - done


//M1: (TLE) for each car, linear scan and look forward
//TC:O(n^2)
//SC:O(1)

//M2: use monotonic stack
//TC:O(n)
//SC:O(n)

