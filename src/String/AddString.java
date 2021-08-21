package String;

public class AddString {

    public String addStrings(String num1, String num2) {
        if (num1 == null || num2 == null){
            return "";
        }
        int i = num1.length()-1;
        int j = num2.length()-1;
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        while (i>=0 || j >=0){
            int one = i >=0 ? num1.charAt(i)-'0': 0;
            int two = j >=0 ? num2.charAt(j)-'0': 0;
            int num = one + two + carry;
            int val = num % 10;
            carry = num / 10;
            sb.append((char)(val + '0'));
            i--;
            j--;
        }
        if (carry > 0){
            sb.append((char)(carry+'0'));
        }
        return sb.reverse().toString();
    }
}

//clarification: numbers all positive
//carry = 0,
//num=(i+j+carry)%10=1; carry=num/10 = 0;
// 11
//i
//123
//j
//134

//follow up numbers negative?