package String;

import java.util.*;

//M1: Stack with reverse string
public class BasicCalculator {

    private static Set<Character> set  = new HashSet<>();

    static {
        set.add('+');
        set.add('-');
        set.add('*');
        set.add('/');
    }

    public int calculate(String s) {

        if (s == null || s.length() == 0){
            return 0;
        }
        Deque<Object> stack = new ArrayDeque<>();

        int base = 0;
        int n=0;
        //Reversely read string to get the expression calculated from left to right
        for (int i=s.length()-1; i>=0; i--){
            char c = s.charAt(i);
            //reading from right to left so 123 -> 3 2 1 until see a non-digit
            if ('0'<=c && c<='9'){
                //maybe its a multi digits
                base = (int)Math.pow(10, n) * (int)(c -'0') + base;
                n++;
            }else {
                //if previous is digit but now not, reset digit helper
                if (n > 0){
                    n=0;
                    stack.offerFirst(base);
                    base = 0;
                }
                if (set.contains(c) || c == ')'){ //if operand or a right bracket, push to stack
                    stack.offerFirst(c);
                }
                //finish a enclosed breacket
                if (c == '('){
                    int out = calculate(stack); //check on stack for the expression added after last bracket
                    stack.pollFirst(); //remove the previous right bracket
                    stack.offerFirst(out);
                }
            }
        }
        if (n > 0){
            stack.offerFirst(base);
        }
        //one more evaluation
        return calculate(stack);
    }

    private int calculate(Deque<Object> stack){
        //pull out element from stack until last bracket and evaluate from left to right
        // 1+ 2-3 --> 1
        int out = 0;
        //why we must poll out the first int, other wise we will not be able to cast the int object to char, only char to int?
        if (!stack.isEmpty()){
            //if first char is not operand, poll it first cast it to int first
            if (!set.contains(stack.peekFirst())){
                out = (int)stack.pollFirst();
            }
        }
        while (!stack.isEmpty() && (char)stack.peekFirst() != ')'){
            char cur = (char)stack.pollFirst(); //1+
            if (cur == '+'){
                out += (int)stack.pollFirst();
            }else {
                out -= (int)stack.pollFirst();
            }
        }
        return out;
    }
}


//M2: Stack without reverse string
class Solution {
    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;
        Deque<Object> stack = new ArrayDeque<>();
        int base = 0;
        int n = 0;
        for (int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            //case 1: digit
            if ('0' <= ch && ch <= '9'){
                //parse the complete number till we see a non-digit
                base = base*10 + ch-'0';
                n++;
            }else {
                if (n > 0) stack.offerFirst(base);
                if (ch == '('){
                    stack.offerFirst(ch);
                }else if (ch == '+' || ch == '-'){
                    stack.offerFirst(ch);
                }else if (ch == ')'){
                    //calculate whats to the left of this bracket
                    int out = calculateHelper(stack);
                    stack.offerFirst(out);
                }
                n=0;
                base = 0;
            }
        }
        if (n > 0){
            stack.offerFirst(base);
        }
        return calculateHelper(stack);
    }

    private int calculateHelper(Deque<Object> stack){
        // stack1 [ 1  -2 +3  ->
        // stack2 [ (  -2 +1
        int sum = 0;
        int num = 0;
        if (!stack.isEmpty()) num = (int)stack.pollFirst();
        while (!stack.isEmpty() && (char)stack.peekFirst() != '('){
            num = (char)stack.peekFirst() == '-'? (-1)*num : num;
            sum+=num;
            num = 0;
            stack.pollFirst(); //operator
            if (!stack.isEmpty()) {
                num = (int)stack.pollFirst();
            }
        }
        if (!stack.isEmpty() && (char)stack.peekFirst() == '('){
            stack.pollFirst(); //poll out '('
        }
        sum+=num;
        return sum;
    }
}

//clarificaton: only +- operator
//s.length = n;
//a number can have more than one digit
//from left to right to calculate

//"(1+(14+5+2)-3)+(6+8)"
//stack [9,+,14 =23
//sum=,
//num=0,

//TC:O(n);
//SC:O(n)
