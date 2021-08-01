public class SumOfTwoIntegers371 {

    public int getSum(int a, int b) {
        if (Math.abs(a) < Math.abs(b)) return getSum(b, a);
        int x = Math.abs(a), y = Math.abs(b);
        //check sign of a and b
        //if diff sign
        int sign = a >= 0 ? 1:-1;
        int answer=0;
        if ( a * b >= 0){
            int carry;
            while ( y != 0){
                answer = x ^ y;
                carry = (x&y)<<1;
                x = answer;
                y = carry;
            }
        }else {
            int borrow;
            while (y != 0){
                answer = x^y;
                borrow = ((~x) & y)<<1;
                x = answer;
                y = borrow;
            }
        }

        return x*sign;
    }
}
