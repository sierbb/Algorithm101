package String;

public class ReverseWordsInASentenceII {
    public String reverseWords(String input) {
        if (input == null || input.length()==0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<input.length(); i++){
            if (i>=1 && input.charAt(i) == ' ' && input.charAt(i-1) == ' '){
                continue;
            }
            sb.append(input.charAt(i));
        }
        while (sb.length() >0 && sb.charAt(0) == ' '){
            sb.deleteCharAt(0);
        }
        while (sb.length() >0 && sb.charAt(sb.length()-1) == ' '){
            sb.deleteCharAt(sb.length()-1);
        }
        //now we have spaces removed, do the reverse
        String clean = sb.toString();
        char[] array = clean.toCharArray();
        reverse(array, 0, array.length-1);
        int start = 0;
        for (int i=0; i<array.length; i++){
            if (array[i]==' '){ //found a word
                reverse(array, start, i-1);
            }
            start=i+1;
        }
        return new String(array);
    }

    private void reverse(char[] array, int start, int end){
        while (start < end){
            char tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            start++;
            end--;
        }
    }

    public static void main(String[] args){
//        String input = " I  love  Google  ";
        String input = "an apple";
        ReverseWordsInASentenceII obj = new ReverseWordsInASentenceII();
        System.out.println(obj.reverseWords(input));
    }
}
