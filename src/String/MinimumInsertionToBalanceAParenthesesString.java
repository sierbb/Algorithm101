package String;

public class MinimumInsertionToBalanceAParenthesesString {

    public int minInsertions(String s) {
        if (s == null || s.length() == 0){
            return 0;
        }
        int left = 0;
        int right = 0;
        int count = 0;
        for (int i=0; i < s.length(); i++){
            char cur = s.charAt(i);
            if (cur == '('){
                //if is left, need to clean up previous brackets
                if (right == 1){
                    if (left > 0){
                        count += 1;
                        left--;
                    }else {
                        count += 2;
                    }
                    right = 0;
                }
                left++;
            }else { // ')'
                right++;
                if (right == 2){
                    if (left >0){
                        left--;
                    }else {
                        count++;
                    }
                    right = 0;
                }
            }
        }
        //clean up the rest of brackets, if still left, means they can not match
        if (left > 0){ //left : right = 1:2
            count += 2*left - right;
        }else if (right > 0){
            //only right left. if right is odd, right / 2 = x -> left; right % 2 = y -> 1 right + 1 left
            count += right / 2;
            count += right % 2 == 1? 2 : 0;
        }
        return count;
    }
}

//clarification: one left matches two right
//Use stack to keep track of number of open left, and fill in if needed
// "((  )) )"
//l[              (               (
//right ) -> add )

//   "))())("
//l[ (               (   (
//
//right

//termination: when point at the end || left stack is empty
//TC:O(n)
//SC:O(1)