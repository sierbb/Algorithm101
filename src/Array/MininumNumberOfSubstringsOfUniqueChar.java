//package Array;
//
//public class MininumNumberOfSubstringsOfUniqueChar {
//
//    public int MininumNumberOfSubstringsOfUniqueChar(String[] strings){
//
//    }
//
//
//
//
//    public static void main(String[] args){
//        WhetherHasPairsOfInteger obj = new WhetherHasPairsOfInteger();
//
//    }
//
//
//}

// abc = 1
// abca = 2; [abc, a] and [a, bca]

//clarification: split string, such that each substring has unique number, and the split should be least.
// can just linear scan, find the largest first string with unique number, then start with second string and get longest
// use set to record char so far, if see duplicate then clear set and restart.
// return final count of string/session we have.
