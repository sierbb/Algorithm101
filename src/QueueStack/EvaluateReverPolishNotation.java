package QueueStack;

import java.util.*;

public class EvaluateReverPolishNotation {

    private Set<String> set = new HashSet<>();

    public int evalRPN(String[] tokens) {
        set.add("+");
        set.add("-");
        set.add("/");
        set.add("*");

        if (tokens == null || tokens.length == 0) return 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i=0; i<tokens.length; i++){
            if (!set.contains(tokens[i])){
                //convert string to int
                stack.offerFirst(stringToInt(tokens[i]));
            }else {
                //attention the order of the calculation
                //what if stack is empty but still want to poll?
                // if (stack.size() < 2){
                //     throw new NoSuchElementException();
                // }
                int i2 = stack.pollFirst();
                int i1 = stack.pollFirst();
                stack.push(calculate(i1, i2, tokens[i]));
            }
        }
        //what's left in the stack is the result
        //if there are more than 1 numbers left in stack, invalid expression
        // if (stack.size() > 1){
        //     throw new InvalidArgumentException();
        // }
        return stack.peekFirst();
    }

    private int calculate(int i1, int i2, String op){
        if (op.equals("+")){
            return i1+i2;
        }else if (op.equals("-")){
            return i1- i2;
        }else if (op.equals("*")){
            return i1*i2;
        }else {
            // if (i2 == 0){
            //     throw new InvalidElementException();
            // }
            return i1/i2;
        }
    }


    private int stringToInt(String s){
        int res = 0;
        int start = 0;
        if (s.charAt(0) == '-'){
            start = 1;
            // if (s.length() == 1){ //no digits following, invalid number
            //     throw new InvalidArgumentException();
            // }
        }
        while (start < s.length()){ //from left most char
            //if there are any invalid character
            // if (s.charAt(start) < '0' || s.charAt(start) > '9'){
            //     throw new InvalidArgumentException();
            // }
            res = res*10 + s.charAt(start)-'0';
            start++;
        }
        return s.charAt(0) == '-' ? res*(-1) : res;
    }
}
