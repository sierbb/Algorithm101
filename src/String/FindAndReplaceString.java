package String;

import java.util.*;

public class FindAndReplaceString {

    public String findReplaceString(String s, int[] indices, String[] sources, String[] targets) {
        if (s == null || s.length()== 0 || indices == null || indices.length == 0){
            return s;
        }
        char[] array = s.toCharArray();

        Pair[] pairs = new Pair[indices.length];
        for (int i=0; i < indices.length; i++){
            pairs[i] = new Pair(indices[i], sources[i], targets[i]);
        }
        Arrays.sort(pairs, new Comparator<Pair>(){
            @Override
            public int compare(Pair p1, Pair p2){
                return p1.indice - p2.indice;
            }
        });

        //check which indices are matching, as well as checking whether we need to expand the char[]
        List<Integer> validIndices = new ArrayList<>();
        for (int i=0; i < pairs.length; i++){
            //if matching source, mark as valid indice, and see whether we need to expand char[] size
            if (matchSubstring(array, pairs[i].indice,  pairs[i].source)){
                validIndices.add(i);
            }
        }
        //whether or not we need expand size, we still create a new char[] to do it
        StringBuilder sb = new StringBuilder();
        //Iterate through valid indices and do the replacement in-place
        int startIdx = 0;
        for (int i=0; i < validIndices.size(); i++){
            int valid = validIndices.get(i);
            int sourceStartIdx = pairs[valid].indice; //indice we need to replace substring from
            //first copy the substring before the source index
            while (startIdx < sourceStartIdx){
                sb.append(array[startIdx]);
                startIdx++;
            }
            //now copy the source substring
            sb.append(pairs[valid].target);
            startIdx +=  pairs[valid].source.length();
        }
        //still need to copy the rest part if applicable
        while (startIdx < array.length){
            sb.append(array[startIdx]);
            startIdx++;
        }
        return sb.toString();
    }

    private boolean matchSubstring(char[] array, int index, String source){
        int startIdx = index;
        for (int i=0; i < source.length(); i++){
            if (index + i >= array.length){ //out of bound
                return false;
            }
            if (array[index+i] != source.charAt(i)){
                return false;
            }
        }
        return true;
    }

    static class Pair{
        private int indice;
        private String source;
        private String target;
        public Pair(int indice, String source, String target){
            this.indice = indice;
            this.source = source;
            this.target = target;
        }
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