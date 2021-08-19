//package String;
//
//public class WildCardMatchingII {
//
//
//    class Solution {
//
////   static boolean isMatch(String v, String pattern) {
////     //step 1: if has *
////     int startIdx = -1;
////     for (int i=0; i<pattern.length(); i++){
////       if (pattern.charAt(i) == '*'){
////         startIdx = i;
////         break;
////       }
////     }
////     if (startIdx == -1){
////       return v.equals(pattern);
////     }
////     //step 2: if has *
////     //check if v starts with pattern left [0, startIdx-1] pattern left
////     if (pattern.length() -1 > v.length()){
////       return false;
////     }
//
////     //left pattern check
////     int vBegin = 0;
////     while ( vBegin < startIdx){
////       if (v.charAt(vBegin) != pattern.charAt(vBegin)){
////         return false;
////       }
////     }
////     //right pattern check
////     int vEnd = v.length()-1;
////     int pEnd = pattern.length()-1;
////     while ( vEnd >= 0 && pEnd > startIdx){
////       if (v.charAt(vEnd) != pattern.charAt(pEnd)){
////         return false;
////       }
////     }
//
////     return true;
////   }
//
//
//        //ab   bccbbbbbbbbbbbabbbba
//        //      cc
//        //ab   *c*a*
//        //     i
//        // if p[i] == *; func(v[i, n], p[i+1,n]) OR func(v[i+1, n], p[i, n])
//        //remove duplicate starts
//        //
//
//
//    static boolean isMatchMore(String v, int vBegin, String pattern, int pBegin) {
//        if ( vBegin == v.length() && pBegin == pattern.length()){
//            return true;
//        }else {
//            if ( pBegin == pattern.length() && vBegin != v.length()) {
//                return false;
//            }
//            if ( vBegin == v.length() && pBegin != pattern.length()){
//                //abc   abc***
//                if (pattern.charAt(pBegin) != '*'){
//                    return false;
//                }else{
//                    while (pBegin < pattern.length()){
//                        if (pattern.charAt(pBegin) != '*'){
//                            return false;
//                        }
//                    }
//                    return true;
//                }
//
//            }
//        }
//
//        //check whether pattern matching v
//        char pChar = p.charAt(pBegin);
//        if (pChar == '*'){
//            //case 1: matching 0 char in v
//            //case 2: mathing 1 char in v
//            if (isMatchMore(v, vBegin, pattern, pBegin+1) || isMatchMore(v, vBegin+1, pattern, pBegin)){
//                return true;
//            }
//        }
//        if (pChar == v.charAt(vBegin)){
//            if (isMatchMore(v, vBegin+1, pattern, pBegin+1)){
//                return true;
//            }
//        }
//        return false;
//
//    }
//
//
//    public static void main(String[] args) {
//        ArrayList<String> strings = new ArrayList<String>();
//        strings.add("Hello, World!");
//        strings.add("Welcome to CoderPad.");
//        strings.add("This pad is running Java " + Runtime.version().feature());
//
//        for (String string : strings) {
//            System.out.println(string);
//        }
//    }
//}
//
//
////TC: level=max(v.length, pattern.length);
////each level = 2
////O(2^level)
//
////SC:O(level) for call stack
//}
