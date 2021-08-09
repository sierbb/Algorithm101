package String;

import java.util.*;

public class NestedListWeightSumInString {
    public int depthSum(String nestlists) {
        //corner case
        if (nestlists == null || nestlists.length() == 0) return 0;
        Deque<Character> stack = new ArrayDeque<>();
        int total = 0, depth = 0;
        for (int i=0; i<nestlists.length(); i++){
            char cur = nestlists.charAt(i);
            if (isDigit(cur) || cur == '[' || cur == ',' || cur == '-'){
                if (cur == '['){
                    depth++;
                }
                stack.offerFirst(cur);
            }else { //must be ']', start calculate
                total += calculate(stack, depth);
                stack.pollFirst(); //poll the left '['
                depth--;
            }
        }
        //calculate the last brack if stack is not empty
        if (!stack.isEmpty()){
            total += calculate(stack, depth);
        }
        return total;
    }

    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    private int calculate(Deque<Character> stack, int depth){
        //pop digits out of stack and calculate weight sum
        int out = 0;
        int num = 0;
        int n = 0;
        while (!stack.isEmpty() && stack.peekFirst() != '['){
            //try to get individual integer
            // [ 4  5, 6 5 ->
            //  5 + 60 = 65
            char cur = stack.pollFirst();
            if (cur != ','){
                if (cur != '-'){
                    num += Math.pow(10, n)*(cur -'0');
                    n++;
                }else{
                    num*= -1;
                }
            }else {
                out += num*depth;
                num = 0;
                n = 0;
            }
        }
        out += num*depth; //add last num
        return out;
    }
}

/**
 * Given a nested list of integers represented by a string without blank, parse the string and  return the sum of all integers in the list weighted by their depth.
 *
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 * /**
 *  *
 *  * Example 1:
 *  * Given the list "[[1,1],2,[1,1]]", return 10. (four 1's at depth 2, one 2 at depth 1)
 *
 */


//Clarification: without blank
//will int be <0 ? yes.
//Parse the string, when see [ or digit, push to stack, when see ], we pop from stack and process the bracket(add the depth when calculate).
//
//[[1,1],2,[1,1]]
//depth=1,
//stack: empty, ends
//sum=10
//1: 1*2+1*2=4
//2: 1*2+1*2=4
//3: 2*1=2

//[1,[4,[6]]]
//depth=0;
//sum=27;
//stack:
//1: 6*3=18;
//2: 4*2=8
//3: 1*1=1

//TC:O(n)
//SC:O(n)