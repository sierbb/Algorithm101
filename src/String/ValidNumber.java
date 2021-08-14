package String;

public class ValidNumber {
    public boolean isNumber(String s) {
        if (s == null || s.length() == 0) return false;
        boolean seenDigit = false;
        boolean seenE = false;
        boolean seenDot = false;
        for (int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if (Character.isDigit(ch)){
                seenDigit = true;
            }else if (ch == 'e' || ch == 'E'){
                if (seenE || !seenDigit){
                    return false;
                }
                seenE = true;
                seenDigit = false; //turn digit off as the last occurrence is e now
            }else if (ch == '+' || ch == '-'){ //must be at 0 or following e
                if ( i>0 && s.charAt(i-1) != 'e' && s.charAt(i) != 'E'){
                    return false;
                }
            }else if (ch == '.'){
                if (seenE || seenDot){
                    return false;
                }
                seenDot = true;
            }else { //other letter
                return false;
            }
        }
        return seenDigit; //The last occurrence must be a digit, not e
    }

}

//follow the rules:
//dot: can only exists before e
//e: can only exists once, can only exists after a digit/dot
//sign: can only exists after e or at start, also need to have
//return: must have seen digit (e or digit counts)

//Double d = Double.parseDouble("2");
