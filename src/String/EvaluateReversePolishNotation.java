package String;

import java.util.*;

public class EvaluateReversePolishNotation {

    public static Set<String> set = new HashSet<>();

    static {
        set.add("+");
        set.add("-");
        set.add("*");
        set.add("/");
    }

    public Double evalRPN(String[] tokens) throws InvalidPropertiesFormatException {
        if (tokens == null || tokens.length == 0) return null;
        Deque<Double> stack = new ArrayDeque<>();
        for (int i=0; i<tokens.length; i++){
            if (set.contains(tokens[i])){
                Double out = calculate(stack, tokens[i]);
                stack.offerFirst(out);
            }else {
                try{
                    Double num = stringToDouble(tokens[i]); //throws NumberFormatException
                    stack.offerFirst(num);
                }catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        if (stack.size() > 1){ //TODO
            throw new InvalidPropertiesFormatException("Invalid expression");
        }
        //finally only have 1 num left from stack
        return stack.peekFirst();
    }

    public int stringToNum(String s){
        int sign = 1, start = 0, num = 0;
        if (s.charAt(0) == '-') {
            sign = -1;
            start = 1;
        }
        for (int i = start; i < s.length(); i++){
            num = num * 10 + s.charAt(i)-'0';
        }
        return num * sign;
    }

    public Double stringToDouble(String s){
        //convert the string to integer, 123
        int dot = 0;
        for (int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if (ch == '.'){
                if (dot > 0 || i == s.length()-1){ //can not have two dots or dot at the end
                    throw new NumberFormatException("invalid double: " + s);
                }
                dot++;
            }
            else {
                if (ch < '0' || ch > '9'){
                    throw new NumberFormatException("invalid double: " + s);
                }
            }
        }
        if (dot == 0) throw new NumberFormatException("invalid double: " + s);
        try{
            return Double.parseDouble(s);
        } catch (NumberFormatException e){
            e.printStackTrace();
            throw e;
        }
    }

    public Double calculate(Deque<Double> stack, String op){
        // stack [ 2, 3 ->  - /
        if (stack.size() < 2){  //TODO
            throw new NoSuchElementException("Does not have enough number to be processed");
        }
        Double two = stack.pollFirst();
        Double one = stack.pollFirst();
        if (op.equals("+")){
            return one + two;
        } else if (op.equals("-")){
            return one - two;
        } else if (op.equals("*")){
            return one * two;
        } else {
            if (two == 0){  //TODO
                throw new ArithmeticException("Devidor can not be 0");
            }
            return one / two; //will be truncated to zero
        }
    }

    public static void main(String[] args) {
        EvaluateReversePolishNotation obj = new EvaluateReversePolishNotation();
        String[] tokens1 = new String[]{ "2.0","1.0","+","3.0","*"};
        String[] tokens2 = new String[]{ "2.0","+","3.0","*"};
        String[] tokens3 = new String[]{ "2.0","1.0","~","3.0","*"};
        String[] tokens4 = new String[]{ "2.0","1.","+","3.0","*"};
        try{
            System.out.println(obj.evalRPN(tokens4));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

//Clarification: truncate to 0; what if the divider is 0 ? 1/0 -> throw exception
//["2","1","+","3","*"]
// 2+1=3, 3, * = 9
//["4","13","5","/","+"]
//               i
//  4, 2, + = 6
//["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
//                   i
//17, 5 + = 22

//Rule:
//when see operator, look back at two numbers, calculate and add back to the list
//stack [ 9 -> result

//TC:O(n)
//SC:O(n)