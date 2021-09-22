package Array;

import java.util.*;

public class MyCalendarII {

    List<int[]> overlay;
    List<int[]> noConflict;

    public MyCalendarII() {
        overlay = new ArrayList<>();
        noConflict = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int i=0; i < overlay.size(); i++){
            int[] overitem = overlay.get(i);
            if (end > overitem[0] && start < overitem[1]){
                return false; //conflict with doubled book
            }
        }
        //now that we know it's not triple book, check whether it is actually overlay
        for (int j=0; j < noConflict.size(); j++){
            int[] single = noConflict.get(j);
            if (end > single[0] && start < single[1]){
                //merge the overlay interval
                overlay.add(new int[]{Math.max(start, single[0]), Math.min(end, single[1])});
            }
        }
        //now we know it's not overlay, add to nonConflict list
        noConflict.add(new int[]{start, end});
        return true;
    }


    public static void main(String[] args){
        MyCalendarII obj = new MyCalendarII();
        int[][] input = new int[][]{{47,50},{1,10},{27,36},{40,47},{20,27},{15,23},{10,18},{27,36},{17,25}};
        for (int i=0; i<input.length; i++){
            System.out.println(obj.book(input[i][0], input[i][1]));
        }
    }
}

/**
 *
 * You are implementing a program to use as your calendar. We can add a new event if adding the event will not cause a triple booking.
 *
 * A triple booking happens when three events have some non-empty intersection (i.e., some moment is common to all the three events.).
 *
 * Your MyCalendarTwo object will be instantiated and called as such:
 * MyCalendarTwo obj = new MyCalendarTwo();
 * boolean param_1 = obj.book(start,end);
 */
//clarification:
//M1: Brute force
//maintain a overlay list and a non-conflict list
//if conflict with


//[10,20],[50,60],[10,40],[5,15],[5,10],[25,55]

//double [5,15],[5,10],[10,20],[10,40],[25,55]
//single [10,20],[25,55], [50,60]