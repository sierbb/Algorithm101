package String;

public class SentenceScreenFitting {

    public int wordsTyping(String[] sentence, int rows, int cols) {
        if (sentence == null || sentence.length == 0 || rows == 0 || cols == 0){
            return -1;
        }

        int[] nextIdx = new int[sentence.length];
        int[] newRow = new int[sentence.length];
        //start to go over each row and see how many words we can fit in, record the index of the word.
        for (int i=0; i< sentence.length; i++){
            int time = 0;   //time we hit a new row
            int curIdx = i; //index of word in sentence
            int curLen = 0;
            while (curLen < cols){
                if (curLen + sentence[curIdx].length() <= cols){
                    //we dont care whether its the last word, as long as word.length can fit in
                    curLen += sentence[curIdx].length()+1; //add a space
                    curIdx++;
                }else {
                    break;
                }
                if (curIdx == sentence.length){
                    curIdx = 0; //reset to index 0 if last word
                    time++;
                }
            }
            //after finish a row, record the nextIdx and times we hit a new sentence -> starting from current word
            newRow[i] = time;
            nextIdx[i] = curIdx;
        }


        int totalCount = 0; //count of sentence fit in so far
        int wordIdx = 0;    //start from word at position 0
        for (int i=0; i < rows; i++){
            totalCount+= newRow[wordIdx];
            wordIdx = nextIdx[wordIdx];
        }
        return totalCount;
    }
}

//clarification:
//number of sentences can fitted
//a row can hold at least 1 word, all rows can holde at least 1 sentence? Maybe not

//Check on one row and one sentence -> how many rows do a sentence needs?
//The length of each words in sentence

//sentence = ["a", "bcd", "e"], rows = 3, cols = 6
//           [2, 3, 2] with spaces in between
//
//row 1: 2+3, index ends at 1
//row 2: 2+2, index ends at 0, count++
//row 3: 3+2, index ends at 2 -> count++

//["i","had","apple","pie"], rows = 4, cols = 5
// [1,  3,     5,     3]
//row 1: 2+3, index ends at 1
//row 2: 5, 2
//row 3: 3, index ends at 3, count++
//row 4: 2+3, index ends at 1

//sentence.length = k
//rows=n
//cols=n
//TC:O(n*k) (TLE), may need to loop through all k for each n?
//SC:O(k) to record length of each word in sentence

//Optimized: pre-calculate times[] and nextIdx[]

//Test1: sentence = ["a", "bcd", "e"], rows = 3, cols = 6
//Test2: sentence = ["i","had","apple","pie"], rows = 4, cols = 5