package String;

import java.util.*;

public class TextJustification {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        //corner case
        if (words == null || words.length == 0) return res;
        int curLen = 0, offset = 0; //index of words that we scanned
        for (int i=0; i<words.length; i++){
            if (curLen + words[i].length() > maxWidth){
                //words we will put will be [offset, i], should build a sentence
                res.add(buildSentence(words, offset, i-1, curLen, maxWidth, false));
                offset = i; //reset offset for the next sentence
                curLen = words[i].length()+1;  //for an normal extra space
            }else {
                //append current word to curLen
                curLen += words[i].length()+1;  //for an normal extra space
            }
        }
        //Last line, left justified if needed
        if (offset < words.length){
            res.add(buildSentence(words, offset, words.length-1, curLen, maxWidth, true));
        }
        return res;
    }

    private String buildSentence(String[] words, int offset, int end, int curLen, int maxWidth, boolean last){
        curLen -= 1; //need to remove extra space that we added to the last word
        //recalclate space needed and extra space needed
        // int intervals = end - offset; //count of words in current sentence
        //Should be fully justified, so we need to calculate the space we need to append after each word
        int extra = maxWidth - curLen;
        int[] spaces = distributeSpaces(offset, end, extra, last);
        return combineWords(words, offset, spaces);
    }
    
    private int[] distributeSpaces(int offset, int end, int extra, boolean last){
        int[] spaces = new int[end-offset+1];
        Arrays.fill(spaces, 1);
        spaces[spaces.length-1]=0;
        if (!last){
            int idx = 0;
            while (extra > 0){ //evenly distributed extra spaces to each word's interval except the last word
                if (idx == 0 || idx != spaces.length-1){ //if is the first word
                    extra--;
                    spaces[idx]++;
                }  //last word should not add spaces
                idx = idx == spaces.length-1 ? 0: idx+1;
            }
        }else {
            //if last line, left justified
            spaces[spaces.length-1] = extra;
        }
        return spaces;
    }

    private String combineWords(String[] words, int offset, int[] spaces){
        StringBuilder sb = new StringBuilder();
        for (int j=0; j<spaces.length; j++) {
            for (int k = 0; k < words[offset + j].length(); k++) {
                sb.append(words[offset + j].charAt(k));
            }
            while (spaces[j] > 0) {
                sb.append(' ');
                spaces[j]--;
            }
        }
        return sb.toString();
    }

}



//["This", "is", "an", "example", "of", "text", "justification."]
//16

//["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"]
//20

//["Science","is","what","we","understand","well","enough"]

//output: 20
//5/3 -> 3 1 1 wrong
//["Science  is  what we","understand    well","enough              "]

//expected
//5 /3 -> 2 2 1
//["Science  is  what we","understand      well","enough              "]

// 5/3 = 1 ...2 but first and second one is 1+1, rest are 1
// 7/3 = 2 ...1 so first one is 2+1 = 3, other 2
// 9/2 = 4... 1 but is 5 4 -> first one 4+1, other 4
// 13/4 = 3 ...1 but is 4 3 3 3  -> first one 3+1, others 3
// 11/3 = 3...2 so is 3+1, 3+1, 3 ....

//