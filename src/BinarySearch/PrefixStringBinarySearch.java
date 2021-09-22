package BinarySearch;

import java.util.*;

/**
 *  find all phone numbers from the prefix (trie tree / prefix tree)
 *
 *  M2: Use binary search to do it without using extra space
 */
public class PrefixStringBinarySearch {

    public List<String> findPrefix(List<String> list, String prefix){
        //assume list is already sorted alphebatically
        int left = searchForLeft(list, prefix);
        int right = searchForRight(list, prefix);
        //["TOA","TOMA", "TOMB" ] -> left: 1, right: 2
        if (left == -1 || right == -1) return new ArrayList<String>();
        return list.subList(left, right+1);
    }

    public int searchForLeft(List<String> list, String input){
        //search for the left boundry of range that matches input
        //e.g. input = “TOM”; in ["TOA","TOM","TOMA", "TOMB", "TOMBBBB", "TON" ] -> return 1
        //                               mid
        // if mid > input, j=mid; if mid < input, i=mid+1; if mid == input, return mid
        // ["TOA","TOB" ] TOM
        //          ij
        int i = 0, j = list.size()-1;
        while (i < j){ //stop when i==j, since left boundry we can limit to the one smaller/larger than it
            int mid = i+(j-i)/2;
            if ( list.get(mid).equals(input)){
                return mid;
            }
            else if (checkElementLargerThanInput(list.get(mid), input) ){
                j = mid;
            }else {
                i = mid+1;
            }
        }
        //still need to check whether prefix matches input, if not return -1
        String prefix = getSubString(list.get(i), input);
        if (!prefix.equals(input)) {
            return -1;
        }
        return i;
    }

    public int searchForRight(List<String> list, String input){
        //Find the right boundry of input within the list
        //e.g. input = “TOM”; in ["TOA","TOM","TOMA", "TOMB", "TOMBBBB", "TON" ] -> return 4
        //                                                        mid
        //!! Keypoint: different rule, need to compare prefix / substring of element vs input
        // if mid > input, j=mid; if mid < input, i=mid+1; if mid == input, return mid
        // ["TOA", "TOB", "TON"] TOM
        //          i        j
        int i=0, j = list.size()-1;
        while ( i+1 < j ){ //stop when i+1==j, since right boundry we can not guarantee if an equal element must be its right boundry
            int mid = i+(j-i)/2;
            //compare same length of substring to input
            //if substring > input, j = mid-1;
            //else substring <= input, i=mid;
            //if substring.length() < input.length()
            String substring = getSubString(list.get(mid), input);
            if ( !checkElementLargerThanInput(substring, input)){
                i = mid;
            }else {
                j = mid-1;
            }
        }
        //final comparison
        if (getSubString(list.get(j), input).equals(input)){
            return j;
        }
        else if ( checkElementLargerThanInput(getSubString(list.get(j), input), input)){
            if ( getSubString(list.get(i), input).equals(input)){
                return i;
            }
        }
        return -1;
    }

    private String getSubString(String s, String input){
        if (s.length() <= input.length()){
            return s;
        }
        return s.substring(0, input.length());
    }

    private boolean checkElementLargerThanInput(String ele, String input){
        //check length?
        if ( ele == null || input == null){
            return false;
        }
        int i= 0, j=0;
        while (i < ele.length() && j < input.length()){
            if (ele.charAt(i) == input.charAt(j)){
                i++;
                j++;
            }else if (ele.charAt(i) < input.charAt(j)){
                return false;
            }else {
                return true;
            }
        }
        //if any of the string runs out
        if (ele.length() > input.length()){
            return true;
        }
        return false;
    }

    public static void main(String[] args){
        PrefixStringBinarySearch obj = new PrefixStringBinarySearch();

        List<String> input = new ArrayList<String>();
        input.add("TOA");
        input.add("TOMA");
        input.add("TOMB");
        input.add("TON");
        System.out.println(obj.findPrefix(input, "TOM"));

        List<String> input2 = new ArrayList<String>();
        input2.add("TOA");
        input2.add("TOMA");
        input2.add("TON");
        System.out.println(obj.findPrefix(input2, "TOM"));

        List<String> input3 = new ArrayList<String>();
        input3.add("TOA");
//        input3.add("TOB");
        input3.add("TON");
        System.out.println(obj.findPrefix(input3, "TOM"));
    }

}


// “TOM A”
//         “TOM B”
//         “PTER A”
//         “PETER C”
//
//         prefix “TOM”
//
//         prefix “PTER”
//
//         prefix “P”
//         [PETER A, (TOM), TOM A, TOM B, ]  prefix TOM

//left b <= right b
//return array
//
//return “”
