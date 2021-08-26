package TwoPointers;

import java.util.*;

public class MeetingScheduler {

    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        List<Integer> res = new ArrayList<>();
        if (slots1 == null || slots2 == null || slots1.length == 0 || slots2.length == 0){
            return res;
        }

        //sort the time slots first by start time
        Arrays.sort(slots1, (s1, s2) -> s1[0] - s2[0]);
        Arrays.sort(slots2, (s1, s2) -> s1[0] - s2[0]);

        int i=0, j=0;
        while (i < slots1.length && j < slots2.length){
            //check for the overlapping time slots
            //First find the next overlapping time slots
            int[] slot1 = slots1[i];
            int[] slot2 = slots2[j];

            //calculate the overlapping length
            int end = Math.min(slot1[1], slot2[1]);
            int start = Math.max(slot1[0], slot2[0]);
            int curLap = end - start;
            if (curLap >= duration){
                List<Integer> out = new ArrayList<>();
                out.add(start);
                out.add(start + duration);
                return out;
            }
            //if the slot that will ends earlier, check whether we want to keep the other slots alive
            if (slot1[1] > slot2[1]){
                if (j+1 < slots2.length && slots2[j+1][0] < slot1[1]){
                    //if next slot1 starts later than current slot 2's end, move i
                    j++;
                }else {
                    i++;
                }
            } else if (slot1[1] < slot2[1]){
                //if next slot1 starts later than current slot 2's end, move i
                if (i+1 < slots1.length && slots1[i+1][0] < slot2[1]){
                    //if next slot1 starts later than current slot 2's end, move i
                    i++;
                }else {
                    j++;
                }
            }

        }
        return new ArrayList<Integer>();
    }
}

//clarification: get one earliest time slots works for both people (no overlapping and of duration).
//each person not have conflicing time slots.

//slots1 = [[10,50],[60,120],[140,210]]
//            i
//slots2 = [[0,15],[15,20]]
//                    j

//scan through
//one: [60, 120] -> 10 < 12 -> 60+8 = 68
//two: [60, 70]
//free time:

//TC:O(mlogm + nlogn) + O(m+n)
//SC:O(1)


//[[216397070,363167701],[98730764,158208909],[441003187,466254040],[558239978,678368334],[683942980,717766451]]
//[[50490609,222653186],[512711631,670791418],[730229023,802410205],[812553104,891266775],[230032010,399152578]]
//456085




