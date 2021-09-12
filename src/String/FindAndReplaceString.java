package String;

import java.util.*;

public class FindAndReplaceString {

    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        if (s == null || s.length()== 0 || indices == null || indices.length == 0){
            return s;
        }
        //build the inndice : index list to be sorted
        List<int[]> indexList = new ArrayList<>();
        for (int i=0; i < indices.length; i++){
            indexList.add(new int[]{indices[i], i});
        }
        Collections.sort(indexList, (a1, a2) -> a1[0] - a2[0]);

        //whether or not we need expand size, we still create a new char[] to do it
        StringBuilder sb = new StringBuilder();
        //Iterate through indexList and do the replacement if source matching original substring
        int startIdx = 0;
        for (int i=0; i < indexList.size(); i++){
            int strIdx = indexList.get(i)[0]; //indice we need to replace substring from
            int sourceIdx = indexList.get(i)[1];

            if (matchSubstring(s, strIdx, sources[sourceIdx])){
                //first copy the substring before the source index
                sb.append(s.substring(startIdx, strIdx)); //substring(startIdx, endIdx); -> endIdx is exclusive
                //now copy the source substring
                sb.append(targets[sourceIdx]);
                startIdx = strIdx + sources[sourceIdx].length();
            }
        }
        //still need to copy the rest part if applicable
        if (startIdx < s.length()){
            sb.append(s.substring(startIdx));
        }
        return sb.toString();
    }

    private boolean matchSubstring(String original, int index, String source){
        int startIdx = index;
        for (int i=0; i < source.length(); i++){
            if (index + i >= original.length()){ //out of bound
                return false;
            }
            if (original.charAt(index+i) != source.charAt(i)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        FindAndReplaceString obj = new FindAndReplaceString();
        String s = "vmokgggqzp";
        int[] indices = new int[]{3,5,1};
        String[] sources = new String[] {"kg","ggq","mo"};
        String[] targets = new String[]{"s","so","bfr"};
        System.out.println(obj.findReplaceString(s, indices, sources, targets));
    }
}

//clarification:
//is the indices sorted?

//first check on indice whether the substring matching, if so, replace it inplace -> edit char[]
//before replace, loop through all the matching substring and see if source.length < target.length; if so, need to expand the char[] for the delta

//indices.length = m
//source[i].length = k

//TC:O(mlogm) + O(m*k) + O(s.length)
//SC:O(s.length) worst case when we need to expand the string