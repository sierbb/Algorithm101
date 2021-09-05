package QueueStack;

import java.util.*;

public class CarFleetI {
    public int carFleet(int target, int[] position, int[] speed) {
        if (position == null || speed == null || position.length == 0 || speed.length == 0){
            return 0;
        }
        //First calculate the time to get to target for each car
        double[][] cars = new double[position.length][2];
        for (int i = 0; i < position.length; i++){
            cars[i] = new double[]{ position[i], 1.0*(target-position[i])/speed[i]};
        }
        //sort by position
        Arrays.sort(cars, (a1, a2) -> Double.compare(a1[0], a2[0])); //smaller position has higher priority
        //scan from last car to first car, if the time it takes is larger, then its a new fleet
        //why it has to be from the last position? Because only the car after it can catch up with it (to check the targetTime)
        int count = 0;
        double maxTime = 0;
        for (int i = position.length-1; i >= 0; i--){
            if (cars[i][1] > maxTime){
                maxTime = cars[i][1];
                count++;
            }
        }
        return count;
    }
}

//clarification: find the final number of fleets at destination
//if two cars becomes a fleet, it will drive at same speed, may meet another car and becomes a new fleet
//assumption:
//Is the position array sorted? No.
//initial positions are unique
//target = 12
//first sort the target time: O(nlogn)

//eg1
//pos     [0, 3,5,8,10]  target=12
//speed   [1, 9,1,4, 2]
//time    [12,1,7,1 1]  -> time to get to target

//sort by target time
//[12,7,1,1,1]
//ouput: 3

//TC:O(nlogn) for sorting
//SC:O(n) for sorting space