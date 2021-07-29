package String;

import java.util.*;

class DecodeString {

    public String decodeString(String s) {
        if (s == null || s.length() == 0){
            return "";
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (int i=0; i<s.length(); i++){
            if (s.charAt(i) != ']'){
                stack.offerFirst(s.charAt(i));
            }else {
                //']' dont add to stack
                List<Character> holder = new ArrayList<>();
                while (!stack.isEmpty() && stack.peek() != '[') {
                    char c = stack.pollFirst();
                    holder.add(c);
                }
                stack.pollFirst(); //poll the '['
                int sum = checkSum(stack);
                //get the integer and multiply the string and push back into stack
                for (int k=0; k<sum; k++){
                    for (int h=holder.size()-1; h>=0; h--){
                        stack.offerFirst(holder.get(h));
                    }
                }
            }
        }
        //now stack have all the elements
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()){
            sb.insert(0, stack.pollFirst());
        }
        return sb.toString();
    }

    private int checkSum(Deque<Character> stack){
        //get the number ends from stack, first element out of stack is the last digit in number
        // 123
        int sum = 0;
        int factor = 1;
        while (!stack.isEmpty() && stack.peek() >='0' && stack.peek() <='9'){
            sum = sum + (stack.pollFirst() - '0')*factor;
            factor *= 10;
        }
        return sum;
    }

    public static void main (String[] args){
        DecodeString obj = new DecodeString();
        String s = "3[a]2[bc]";
        System.out.println(obj.decodeString(s));

    }
}

//M2: Recursion
public class DecodeString394 {
    public String decodeString(String s){
        //M2: use recursion
        if (s == null || s.length() == 0) return "";
        int[] index = new int[]{0};
        return DFS(s, index);
    }

    private String DFS(String s, int[] index){
        //decode the path from index till seeing the first ']'
        StringBuilder sb = new StringBuilder();
        while (index[0] < s.length() && s.charAt(index[0]) != ']'){
            if (!Character.isDigit(s.charAt(index[0]))){
                sb.append(s.charAt(index[0]));
                index[0]++;
            }else {
                //if is a digit, means there will be a child recursion call from current recursion level
                //first deal with the number, left to right order for the digit
                int sum = 0;
                while (index[0] < s.length() && Character.isDigit(s.charAt(index[0]))){
                    sum = sum*10 + s.charAt(index[0])-'0';
                    index[0]++;
                }
                index[0]++; //skip the left bracket '['
                String childS = DFS(s, index);
                index[0]++; //skip right bracket ']'
                //multiply sum with the childS to sb
                while (sum > 0){
                    sb.append(childS);
                    sum--;
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] arys){
        DecodeString obj = new DecodeString();
        String s = "3[a]2[bc]";
        System.out.println(obj.decodeString(s));
    }

}